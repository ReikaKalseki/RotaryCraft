/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityPump extends TileEntityPowerReceiver implements PipeConnector, IFluidHandler, DiscreteFunction {

	private BlockArray blocks = new BlockArray();

	private int soundtick = 200;

	private int damage = 0;
	public int duplicationAmount;

	public final static int CAPACITY = 24*1000;

	private final HybridTank tank = new HybridTank("pump", CAPACITY);

	/** Rate of conversion - one power++ = one tick-- per operation */
	public static final int FALLOFF = 256; //256W per 1 kPa

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		soundtick++;
		tickcount++;
		this.getIOSides(world, x, y, z, this.getBlockMetadata());
		this.getPower(true);
		power = (long)omega*(long)torque;
		Block idbelow = world.getBlock(x, y-1, z);
		if (idbelow == Blocks.air)
			return;
		Fluid f = ReikaFluidHelper.lookupFluidForBlock(idbelow);
		if (f == null)
			return;
		if (blocks.isEmpty()) {
			blocks.recursiveAddLiquidWithBounds(world, x, y-1, z, x-16, y-2, z-16, x+16, y-1, z+16, f);
			blocks.reverseBlockOrder();
		}
		if (damage > 400)
			power = 0;
		//ReikaJavaLibrary.pConsole(FMLCommonHandler.instance().getEffectiveSide()+" for "+blocks.getSize());
		if (blocks.isEmpty())
			return;
		if (power >= MINPOWER && torque >= MINTORQUE && this.getLevel() < CAPACITY && tickcount >= this.getOperationTime()) {
			//int loc[] = this.findSourceBlock(world, x, y, z);
			Coordinate loc = blocks.getNextAndMoveOn();
			//ReikaJavaLibrary.pConsole(loc.xCoord+"  "+loc.yCoord+"  "+loc.zCoord+"  for side "+FMLCommonHandler.instance().getEffectiveSide());
			this.harvest(world, x, y, z, loc);
			tickcount = 0;
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.liquidID));
		}

		if (power > MINPOWER && torque >= MINTORQUE && soundtick >= 100) {
			soundtick = 0;
			SoundRegistry.PUMP.playSoundAtBlock(world, x, y, z, 0.5F, 1);
		}
		if (power > MINPOWER && torque >= MINTORQUE)
			this.suckUpMobs(world, x, y, z);
	}

	private void suckUpMobs(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y-1, z, x+1, y, z+1);
		List<EntityLivingBase> inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase e : inbox) {
			e.attackEntityFrom(DamageSource.generic, 5);
		}
		if (inbox.size() > 0 && !ReikaEntityHelper.allAreDead(inbox, false))
			damage++;
		if (damage >= 400)
			this.breakPump(world, x, y, z);
	}

	public boolean isBroken() {
		return damage >= 400;
	}

	private void breakPump(World world, int x, int y, int z) {
		world.playSoundEffect(x, y, z, "random.break", 1F, 1F);
		//for (int i = 0; i < 8; i++)
		//	ReikaItemHelper.dropItem(world, x+par5Random.nextDouble(), y+par5Random.nextDouble(), z+par5Random.nextDouble(), ItemStacks.scrap);
	}

	public void harvest(World world, int x, int y, int z, Coordinate loc) {
		if (world.isRemote)
			return;
		FluidStack fs = ReikaWorldHelper.getDrainableFluid(world, loc.xCoord, loc.yCoord, loc.zCoord);
		if (fs == null)
			return;
		if (fs == null || !tank.canTakeIn(fs))
			return;
		Fluid f = fs.getFluid();
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d  %d", loc.xCoord, loc.yCoord, loc.zCoord, world.getBlock(loc.xCoord, loc.yCoord, loc.zCoord)));
		if (f != FluidRegistry.LAVA || !ReikaWorldHelper.is1p9InfiniteLava(world, loc.xCoord, loc.yCoord, loc.zCoord))
			world.setBlock(loc.xCoord, loc.yCoord, loc.zCoord, Blocks.air);
		int mult = 1;
		if (this.canMultiply(f)) {
			if (power/MINPOWER >= 16)
				mult *= 2;
			if (power/MINPOWER >= 64)
				mult *= 2;
			if (power/MINPOWER >= 256)
				mult *= 2;
			if (power/MINPOWER >= 1024)
				mult *= 2;
			if (power/MINPOWER >= 4096)
				mult *= 2;
		}
		if (f.equals(FluidRegistry.WATER))
			RotaryAchievements.PUMP.triggerAchievement(this.getPlacer());
		duplicationAmount = (int)(mult*ConfigRegistry.getFreeWaterProduction());
		tank.addLiquid(fs.amount*mult, f);
		world.markBlockForUpdate(loc.xCoord, loc.yCoord, loc.zCoord);
	}

	private boolean canMultiply(Fluid fluid) {
		if (fluid.equals(FluidRegistry.WATER))
			return true;
		return false;
	}

	public boolean isSource(World world, int x, int y, int z) {
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d, %d, %d, %d", x,y,z,(int)id));
		//ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 49);
		Block liqid = world.getBlock(x, y, z);
		if (!(liqid instanceof BlockFluidBase || liqid instanceof BlockLiquid))
			return false;
		boolean srcmeta = liqid instanceof BlockFluidFinite ? world.getBlockMetadata(x, y, z) == 7 : world.getBlockMetadata(x, y, z) == 0;
		Fluid f2 = ReikaFluidHelper.lookupFluidForBlock(liqid);
		Fluid f = tank.getActualFluid();
		if (f2 == null)
			return false;
		boolean liq = f2.equals(f) || f == null;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.valueOf(liq)+"  "+String.valueOf(dmg0));
		return srcmeta && liq;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
			case 1:
				read = ForgeDirection.EAST;
				read2 = ForgeDirection.WEST;
				break;
			case 0:
				read = ForgeDirection.NORTH;
				read2 = ForgeDirection.SOUTH;
				break;
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setInteger("dmg", damage);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		damage = NBT.getInteger("dmg");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.PUMP;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side.offsetY == 0;
	}

	@Override
	public void onEMP() {}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.canDrain(from, resource.getFluid()) ? tank.drain(resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from.offsetY != 0)
			return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from.offsetY == 0 && ReikaFluidHelper.isFluidDrainableFromTank(fluid, tank);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getLiquid() {
		return tank.getActualFluid();
	}

	public void removeLiquid(int amt) {
		tank.removeLiquid(amt);
	}

	public void setEmpty() {
		tank.empty();
	}

	public void addLiquid(int amt, Fluid f) {
		tank.addLiquid(amt, f);
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side.offsetY == 0 ? Flow.OUTPUT : Flow.NONE;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.PUMP.getOperationTime(omega);
	}

	public int getMaxBackPressure() {
		return 1000*(1+ReikaMathLibrary.logbase2(torque/MINTORQUE));
	}
}
