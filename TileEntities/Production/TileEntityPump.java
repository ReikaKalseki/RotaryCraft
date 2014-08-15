/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityPump extends TileEntityPowerReceiver implements PipeConnector, IFluidHandler, DiscreteFunction {

	private BlockArray blocks = new BlockArray();

	private int soundtick = 200;

	public int damage = 0;

	public final static int CAPACITY = 24*1000;

	private HybridTank tank = new HybridTank("pump", CAPACITY);

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
		Fluid f = FluidRegistry.lookupFluidForBlock(idbelow);
		if (f == null)
			return;
		if (blocks.isEmpty()) {
			blocks.setLiquid(idbelow.getMaterial());
			blocks.recursiveAddLiquidWithBounds(world, x, y-1, z, x-16, y-2, z-16, x+16, y-1, z+16);
			blocks.reverseBlockOrder();
		}
		if (damage > 400)
			power = 0;
		//ReikaJavaLibrary.pConsole(FMLCommonHandler.instance().getEffectiveSide()+" for "+blocks.getSize());
		if (blocks.isEmpty())
			return;
		if (power >= MINPOWER && torque >= MINTORQUE && this.getLevel() < CAPACITY && tickcount >= this.getOperationTime()) {
			//int loc[] = this.findSourceBlock(world, x, y, z);
			int[] loc = blocks.getNextAndMoveOn();
			//ReikaJavaLibrary.pConsole(loc[0]+"  "+loc[1]+"  "+loc[2]+"  for side "+FMLCommonHandler.instance().getEffectiveSide());
			this.harvest(world, x, y, z, loc);
			tickcount = 0;
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.liquidID));
		}

		if (power > MINPOWER && soundtick >= 100) {
			soundtick = 0;
			SoundRegistry.PUMP.playSoundAtBlock(world, x, y, z, 0.5F, 1);
		}
		if (power > MINPOWER)
			this.suckUpMobs(world, x, y, z);
	}

	private void suckUpMobs(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y-1, z, x+1, y, z+1);
		List inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLivingBase e = (EntityLivingBase)inbox.get(i);
			e.attackEntityFrom(DamageSource.generic, 5);
		}
		if (inbox.size() > 0 && !ReikaEntityHelper.allAreDead(inbox, false))
			damage++;
		if (damage > 400)
			this.breakPump(world, x, y, z);
	}

	private void breakPump(World world, int x, int y, int z) {
		world.playSoundEffect(x, y, z, "random.break", 1F, 1F);
		//for (int i = 0; i < 8; i++)
		//	ReikaItemHelper.dropItem(world, x+par5Random.nextDouble(), y+par5Random.nextDouble(), z+par5Random.nextDouble(), ItemStacks.scrap);
	}

	public void harvest(World world, int x, int y, int z, int[] loc) {
		if (world.isRemote)
			return;
		if (!this.isSource(world, loc[0], loc[1], loc[2]))
			return;
		if (tank.getLevel() >= CAPACITY)
			return;
		Block liqid = world.getBlock(loc[0], loc[1], loc[2]);
		Fluid fluid = FluidRegistry.lookupFluidForBlock(liqid);
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d  %d", loc[0], loc[1], loc[2], world.getBlock(loc[0], loc[1], loc[2])));
		if (!ReikaWorldHelper.is1p9InfiniteLava(world, loc[0], loc[1], loc[2]))
			world.setBlock(loc[0], loc[1], loc[2], Blocks.air);
		int mult = 1;
		if (this.canMultiply(fluid)) {
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
		if (fluid.equals(FluidRegistry.WATER))
			RotaryAchievements.PUMP.triggerAchievement(this.getPlacer());
		tank.addLiquid(1000*mult, fluid);
		world.markBlockForUpdate(loc[0], loc[1], loc[2]);
	}

	private boolean canMultiply(Fluid fluid) {
		if (fluid.equals(FluidRegistry.WATER))
			return true;
		return false;
	}

	public boolean isSource(World world, int x, int y, int z) {
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d, %d, %d, %d", x,y,z,(int)id));
		//ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 49);
		boolean dmg0 = (world.getBlockMetadata(x, y, z) == 0);
		Block liqid = world.getBlock(x, y, z);
		if (liqid == Blocks.air)
			return false;
		Fluid f2 = FluidRegistry.lookupFluidForBlock(liqid);
		Fluid f = tank.getActualFluid();
		if (f2 == null)
			return false;
		boolean liq = (f2.equals(f) || f == null);
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.valueOf(liq)+"  "+String.valueOf(dmg0));
		return (dmg0 && liq);
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

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setInteger("dmg", damage);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
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
		if (power < MINPOWER)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PUMP;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
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
		return this.drain(from, resource.amount, doDrain);
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
		return from.offsetY == 0;
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
}