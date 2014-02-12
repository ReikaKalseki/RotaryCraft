/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;

public class TileEntityLawnSprinkler extends RotaryCraftTileEntity implements PipeConnector, RangedEffect {

	public static final int CAPACITY = 6000;

	private int liquid;
	private int pressure;
	private StepTimer soundTimer = new StepTimer(40);

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LAWNSPRINKLER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y-1, z);
			if (tile != null && tile.contains(FluidRegistry.WATER) && tile.getLiquidLevel() > 0) {
				if (liquid < CAPACITY) {
					int oldLevel = tile.getLiquidLevel();
					int toremove = tile.getLiquidLevel()/4+1;
					tile.removeLiquid(toremove);
					liquid = ReikaMathLibrary.extrema(liquid+oldLevel/4+1, 0, "max");
				}
				pressure = tile.getPressure();
			}
		}

		if (this.getRange() > 0 && liquid > 0) {
			if (!world.isRemote) {
				for (int k = 0; k < 3; k++) {
					this.accelerateGrowth(world, x, y, z);
					this.extinguishFire(world, x, y, z);
				}
				if (pressure > 10000)
					this.damageMobs(world, x, y, z);
			}
			this.spreadWater(world, x, y, z);
			soundTimer.update();
			if (soundTimer.checkCap()) {
				SoundRegistry.SPRINKLER.playSoundAtBlock(world, x, y, z);
			}
		}
	}

	private void extinguishFire(World world, int x, int y, int z) {
		int r = this.getRange();
		int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		for (int i = y; i > y-4; i--) {
			int id = world.getBlockId(rx, i, rz);
			if (id == Block.fire.blockID) {
				world.setBlock(rx, i, rz, 0);
				ReikaSoundHelper.playSoundAtBlock(world, rx, i, rz, "random.fizz");
			}
			else if (id != 0 && Block.blocksList[id].isOpaqueCube())
				i = -999;
		}
	}

	private void accelerateGrowth(World world, int x, int y, int z) {
		int r = this.getRange();
		int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		for (int i = y; i > y-4; i--) {
			int id = world.getBlockId(rx, i, rz);
			int meta = world.getBlockMetadata(rx, i, rz);
			if (id == Block.tilledField.blockID) {
				if (meta < 8)
					world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
				i = -999;
			}
			else if (id != 0 && Block.blocksList[id].isOpaqueCube())
				i = -999;
			else if (rand.nextInt(15) == 0) {
				ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
				ModCropList modcrop = ModCropList.getModCrop(id, meta);
				if (crop != null && !crop.isRipe(meta)) {
					world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
				}
				if (modcrop != null && !modcrop.isRipe(world, rx, i, rz)) {
					world.setBlockMetadataWithNotify(rx, i, rz, meta+1, 3);
				}
			}
		}
	}

	private void damageMobs(World world, int x, int y, int z) {
		int r = this.getRange();
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).offset(0, 1, 0).expand(r, 1, r);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < li.size(); i++) {
			EntityLivingBase e = li.get(i);
			e.attackEntityFrom(DamageSource.generic, 0.5F);
		}
	}

	private void spreadWater(World world, int x, int y, int z) {
		int d = Math.max(0, ConfigRegistry.SPRINKLER.getValue());

		double ypos = y+0.125;
		double vel;
		double r = this.getRange()/10D;

		double py = y-0.1875D+0.5;
		for (int i = 0; i < rand.nextInt(1+d); i++) {
			double px = x-1+2*rand.nextFloat();
			double pz = z-1+2*rand.nextFloat();
			world.spawnParticle("splash", px+0.5, py, pz+0.5, 0, 0, 0);
		}

		for (vel = 0; vel < r; vel += 0.1) {
			py = y-0.1875D+1.5;
			for (int i = 0; i < rand.nextInt(5+d*4); i++) {
				double vx = vel*(-1+rand.nextFloat()*2);
				vx *= 0.5;
				double vz = vel*(-1+rand.nextFloat()*2);
				vz *= 0.5;
				double px = x-1+2*rand.nextFloat();
				double pz = z-1+2*rand.nextFloat();
				world.spawnParticle("splash", px+0.5, py, pz+0.5, vx, 0, vz);
			}
		}
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side == ForgeDirection.DOWN ? p == MachineRegistry.PIPE : false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!resource.getFluid().equals(FluidRegistry.WATER))
			return 0;
		int toadd = Math.min(resource.amount, CAPACITY-liquid);
		liquid += toadd;
		return toadd;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.DOWN ? Flow.INPUT : Flow.NONE;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBT.setInteger("press", pressure);
		NBT.setInteger("lvl", liquid);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		pressure = NBT.getInteger("pressure");
		liquid = NBT.getInteger("lvl");
	}

	@Override
	public int getRange() {
		int val = 0;
		if (pressure <= 0)
			return 0;
		val = pressure/80;
		if (val > this.getMaxRange())
			val = this.getMaxRange();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", val));
		return val;
	}

	@Override
	public int getMaxRange() {
		return 8;
	}

}
