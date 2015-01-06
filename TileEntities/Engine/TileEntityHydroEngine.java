/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Engine;

import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

public class TileEntityHydroEngine extends TileEntityEngine {

	public boolean failed;
	private boolean bedrock;

	@Override
	protected void consumeFuel() {

	}

	@Override
	protected void internalizeFuel() {

	}

	public boolean isBedrock() {
		return bedrock;
	}

	@Override
	public void setDataFromPlacer(ItemStack is) {
		if (is.stackTagCompound != null) {
			bedrock = is.stackTagCompound.getBoolean("bed");
		}
	}

	@Override
	protected boolean getRequirements(World world, int x, int y, int z, int meta) {

		boolean hasLube = !lubricant.isEmpty() && lubricant.getActualFluid().equals(FluidRegistry.getFluid("lubricant"));
		if (hasLube)
			this.distributeLubricant(world, x, y, z);
		else
			return false;

		if (this.doesBlockObstructBlades(world, x, y+1, z)) {
			omega = 0;
			return false;
		}
		if (this.doesBlockObstructBlades(world, x, y-1, z)) {
			omega = 0;
			return false;
		}

		int[] pos = this.getWaterColumnPos();
		Block id = world.getBlock(pos[0], y, pos[1]);
		if (id == Blocks.flowing_lava || id == Blocks.lava) {
			if (ReikaRandomHelper.doWithChance(2)) {
				world.createExplosion(null, x+0.5, y+0.5, z+0.5, 2, true);
				world.setBlockToAir(x, y, z);
			}
		}
		if (id != Blocks.water && id != Blocks.flowing_water)
			return false;
		if (!ReikaWorldHelper.isLiquidAColumn(world, pos[0], y, pos[1]))
			return false;

		for (int i = -1; i <= 1; i++) {
			if (this.doesBlockObstructBlades(world, 2*x-pos[0], y+i, 2*z-pos[1])) {
				omega = 0;
				return false;
			}
		}
		return true;
	}

	private void distributeLubricant(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (dir == this.getWriteDirection() || dir.getOpposite() == this.getWriteDirection()) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
				if (m == MachineRegistry.ENGINE) {
					TileEntityEngine eng = (TileEntityEngine)this.getAdjacentTileEntity(dir);
					if (eng instanceof TileEntityHydroEngine) {
						TileEntityHydroEngine hy = (TileEntityHydroEngine)eng;
						int it = hy.lubricant.getLevel();
						int dL = lubricant.getLevel()-it;
						if (dL > 3) {
							hy.lubricant.addLiquid(dL/4, FluidRegistry.getFluid("lubricant"));
							lubricant.removeLiquid(dL/4);
						}
					}
				}
				else if (m == MachineRegistry.RESERVOIR) {
					TileEntityReservoir te = (TileEntityReservoir)this.getAdjacentTileEntity(dir);
					if (!lubricant.isEmpty() && te.canAcceptFluid(FluidRegistry.getFluid("lubricant"))) {
						int amt = Math.min(this.getLube(), te.CAPACITY-te.getLevel());
						if (amt > 0) {
							te.addLiquid(amt, FluidRegistry.getFluid("lubricant"));
							lubricant.removeLiquid(amt);
						}
					}
				}
			}
		}
		if (!failed && !lubricant.isEmpty() && omega > 0) {
			if (world.getWorldTime()%10 == 0)
				lubricant.removeLiquid(1);
		}
	}

	private boolean doesBlockObstructBlades(World world, int x, int y, int z) {
		return !failed && !ReikaWorldHelper.softBlocks(world, x, y, z);
	}

	private int[] getWaterColumnPos() {
		int[] pos = {xCoord, zCoord};
		switch(this.getBlockMetadata()) {
		case 0:
			pos[1] += -1;
			break;
		case 1:
			pos[1] += 1;
			break;
		case 2:
			pos[0] += 1;
			break;
		case 3:
			pos[0] += -1;
			break;
		}
		return pos;
	}

	private float getHydroFactor(World world, int x, int y, int z) {
		double grav = ReikaPhysicsHelper.g;
		if (InterfaceCache.IGALACTICWORLD.instanceOf(world.provider)) {
			IGalacticraftWorldProvider ig = (IGalacticraftWorldProvider)world.provider;
			grav += ig.getGravity()*10;
		}
		int[] pos = this.getWaterColumnPos();
		double dy = (ReikaWorldHelper.findWaterSurface(world, pos[0], y, pos[1])-y)-0.5;
		double v = Math.sqrt(2*grav*dy);
		double mdot = ReikaEngLibrary.rhowater*v;
		double P = 0.25*mdot*dy;
		if (P >= type.getPower())
			return 1;
		return (float)(P/type.getPower());
	}

	private void dealPanelDamage(World world, int x, int y, int z, int meta) {
		int a = 0; int b = 0;
		if (meta < 2)
			b = 1;
		else
			a = 1;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(a, 1, b);
		List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase ent : in) {
			ent.attackEntityFrom(RotaryCraft.hydrokinetic, 1);
		}
	}

	private boolean isPartOfArray() {
		return this.isBackEndOfArray() || this.isFrontOfArray();
	}

	public boolean isBackEndOfArray() {
		MachineRegistry to = this.getMachine(write);
		if (to == MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)this.getAdjacentTileEntity(write);
			return te.getEngineType() == EngineType.HYDRO && !((TileEntityHydroEngine)te).failed;
		}
		return false;
	}

	public boolean isFrontOfArray() {
		MachineRegistry from = MachineRegistry.getMachine(worldObj, backx, yCoord, backz);
		MachineRegistry to = this.getMachine(write);
		if (from == MachineRegistry.ENGINE && to != MachineRegistry.ENGINE) {
			TileEntityEngine te = (TileEntityEngine)worldObj.getTileEntity(backx, yCoord, backz);
			return te.getEngineType() == EngineType.HYDRO;
		}
		TileEntity te = this.getAdjacentTileEntity(write);
		if (te instanceof TileEntityHydroEngine) {
			return ((TileEntityHydroEngine)te).failed;
		}
		return false;
	}

	private int getArrayTorqueMultiplier() {
		ArrayList<TileEntityHydroEngine> li = new ArrayList();
		int size = 1;
		TileEntity te = this.getAdjacentTileEntity(write.getOpposite());
		while (te instanceof TileEntityHydroEngine && te != this && !li.contains(te)) {
			TileEntityHydroEngine eng = (TileEntityHydroEngine)te;
			li.add(eng);
			if (eng.getRequirements(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, eng.getBlockMetadata())) {
				if (eng.omega == omega && !eng.failed) {
					//float fac = eng.getHydroFactor(worldObj, xyz[0], xyz[1], xyz[2], true);
					size++;
					te = eng.getAdjacentTileEntity(eng.write.getOpposite());
				}
				else {
					ReikaParticleHelper.CRITICAL.spawnAroundBlock(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, 5);
					if (rand.nextInt(3) == 0)
						ReikaSoundHelper.playSoundAtBlock(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, "mob.blaze.hit");
					break;
				}
			}
		}
		return size;
	}

	@Override
	protected void playSounds(World world, int x, int y, int z, float pitchMultiplier, float volume) {
		soundtick++;
		if (this.isMuffled(world, x, y, z)) {
			volume *= 0.3125F;
		}

		if (soundtick < this.getSoundLength(1F/pitchMultiplier) && soundtick < 2000)
			return;
		soundtick = 0;

		if (this.isFrontOfArray() || !this.isPartOfArray())
			SoundRegistry.HYDRO.playSoundAtBlock(world, x, y, z, 1F*volume, 0.9F*pitchMultiplier);
	}

	@Override
	public int getFuelLevel() {
		return 0;
	}

	@Override
	protected int getMaxSpeed(World world, int x, int y, int z, int meta) {
		return Math.max(1, (int)(EngineType.HYDRO.getSpeed()*this.getHydroFactor(world, x, y, z)));
	}

	@Override
	protected int getGenTorque(World world, int x, int y, int z, int meta) {
		if (failed)
			return 1;
		int fac = this.getArrayTorqueMultiplier();
		int torque = (int)(EngineType.HYDRO.getTorque()*this.getHydroFactor(world, x, y, z)*fac);
		int r = bedrock ? 16 : 4;
		double ratio = (double)torque/EngineType.HYDRO.getTorque();
		if (ratio > r) {
			this.fail(world, x, y, z);
		}
		return torque;
	}

	private void fail(World world, int x, int y, int z) {
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.break");
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode", 0.2F, 0.5F);
		/*
		TileEntity te = this.getAdjacentTileEntity(write.getOpposite());
		while (te instanceof TileEntityHydroEngine) {
			TileEntityHydroEngine eng = (TileEntityHydroEngine)te;
			eng.getGenTorque(world, eng.xCoord, eng.yCoord, eng.zCoord, eng.getBlockMetadata());
			te = eng.getAdjacentTileEntity(write.getOpposite());
		}*/
		failed = true;
	}

	@Override
	protected void affectSurroundings(World world, int x, int y, int z, int meta) {
		this.dealPanelDamage(world, x, y, z, meta);
		this.spawnParticles(world, x, y, z);
		if (failed) {
			ForgeDirection dir = this.getWriteDirection();
			ForgeDirection left = ReikaDirectionHelper.getLeftBy90(dir);
			for (int i = -1; i <= y; i++) {
				ReikaWorldHelper.dropAndDestroyBlockAt(world, x+left.offsetX, y+i, z+left.offsetZ, false);
			}
			ReikaWorldHelper.dropAndDestroyBlockAt(world, x, y+1, z, false);
			ReikaWorldHelper.dropAndDestroyBlockAt(world, x, y-1, z, false);
		}
	}

	private void spawnParticles(World world, int x, int y, int z) {
		int[] xz = this.getWaterColumnPos();
		ReikaParticleHelper.RAIN.spawnAroundBlock(world, x, y, z, 16);
		ReikaParticleHelper.RAIN.spawnAroundBlock(world, xz[0], y, xz[1], 16);
		if (failed) {
			if (rand.nextInt(5) == 0)
				ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "mob.blaze.hit");
			ReikaParticleHelper.CRITICAL.spawnAroundBlockWithOutset(world, x, y, z, 3, 0.25);
		}
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		failed = NBT.getBoolean("fail");
		bedrock = NBT.getBoolean("bedrock");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("fail", failed);
		NBT.setBoolean("bedrock", bedrock);
	}

	public void makeBedrock() {
		bedrock = true;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		PowerSourceList psl = super.getPowerSources(io, caller);
		ArrayList<TileEntityHydroEngine> li = new ArrayList();
		ArrayList<TileEntityHydroEngine> li2 = new ArrayList();
		TileEntity te = this.getAdjacentTileEntity(write.getOpposite());
		while (te instanceof TileEntityHydroEngine && te != this && !li2.contains(te)) {
			TileEntityHydroEngine eng = (TileEntityHydroEngine)te;
			li2.add(eng);
			if (eng.getRequirements(worldObj, eng.xCoord, eng.yCoord, eng.zCoord, eng.getBlockMetadata())) {
				if (eng.omega == omega && !eng.failed) {
					li.add(eng);
					te = eng.getAdjacentTileEntity(eng.write.getOpposite());
				}

			}
		}
		for (int i = 0; i < li.size(); i++) {
			psl.addSource(li.get(i));
		}
		return psl;
	}
}
