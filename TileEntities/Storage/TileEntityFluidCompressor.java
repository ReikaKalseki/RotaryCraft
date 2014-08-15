/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Storage;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityFluidCompressor extends TileEntityPowerReceiver implements IFluidHandler, PipeConnector, NBTMachine {

	private HybridTank tank = new HybridTank("gastank", 1000000000);
	private int timer = 0;

	private static final ArrayList<Fluid> creativeFluids = new ArrayList();

	public int getCapacity(Fluid f) {
		if (power < MINPOWER || torque < MINTORQUE)
			return 0;
		int log2 = (int)(ReikaMathLibrary.logbase(torque, 2)/2);
		long power = ReikaMathLibrary.longpow(10, log2);
		int factor = f.isGaseous() ? 8 : 1;
		long frac = factor*(power/40);
		return (int)Math.min(frac, tank.getCapacity());
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	public boolean isEmpty() {
		return tank.isEmpty();
	}

	public int getLevel() {
		return tank.getLevel();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();/*
		if (this.getLevel() > this.getCapacity()) {
			timer++;
			//ReikaJavaLibrary.pConsole(timer, Side.SERVER);
			if (timer > 400) {
				world.setBlockToAir(x, y, z);
				world.createExplosion(null, x+0.5, y+0.5, z+0.5, 8F, ConfigRegistry.BLOCKDAMAGE.getState());
			}
			else if (timer > 0) {
				if (rand.nextInt(410-timer) < 10) {
					ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz");
					ReikaParticleHelper.SMOKE.spawnAroundBlock(world, x, y, z, 8);
				}
			}
		}
		else
			timer = 0;*/
		//ReikaJavaLibrary.pConsole(tank.getLevel()/1000D+"/"+this.getCapacity()/1000D, Side.SERVER);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GASTANK;
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
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.getFluid() == null)
			return 0;
		int toadd = Math.min(resource.amount, this.getCapacity(resource.getFluid())-tank.getLevel());
		if (toadd <= 0)
			return 0;
		FluidStack fs = new FluidStack(resource.getFluid(), toadd);
		return this.canFill(from, resource.getFluid()) ? tank.fill(fs, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;//fluid.isGaseous();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == ForgeDirection.UP ? Flow.OUTPUT : Flow.INPUT;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	public NBTTagCompound getTagsToWriteToStack() {
		if (this.isEmpty())
			return null;
		NBTTagCompound NBT = new NBTTagCompound();
		Fluid f = this.getFluid();
		int level = this.getLevel();
		ReikaNBTHelper.writeFluidToNBT(NBT, f);
		NBT.setInteger("lvl", level);
		return NBT;
	}

	@Override
	public void setDataFromItemStackTag(NBTTagCompound NBT) {
		if (NBT == null) {
			tank.empty();
			return;
		}
		Fluid f = ReikaNBTHelper.getFluidFromNBT(NBT);
		int level = NBT.getInteger("lvl");
		if (f != null && level > 0)
			tank.setContents(level, f);
	}

	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		ArrayList<NBTTagCompound> li = new ArrayList();
		for (int i = 0; i < creativeFluids.size(); i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("lvl", 1000000000);
			ReikaNBTHelper.writeFluidToNBT(nbt, creativeFluids.get(i));
			li.add(nbt);
		}
		return li;
	}

	public static void initCreativeFluids() {
		creativeFluids.clear();
		addCreativeFluid("hydrofluoric acid");
		addCreativeFluid("uranium hexafluoride");
		addCreativeFluid("rc ammonia");
		addCreativeFluid("rc chlorine");
		addCreativeFluid("rc co2");
		addCreativeFluid("rc oxygen");
		addCreativeFluid("rc deuterium");
		addCreativeFluid("rc tritium");
	}

	private static void addCreativeFluid(String name) {
		Fluid f = FluidRegistry.getFluid(name);
		if (f != null)
			creativeFluids.add(f);
	}

	public ArrayList<String> getDisplayTags(NBTTagCompound nbt) {
		ArrayList li = new ArrayList();
		Fluid f = ReikaNBTHelper.getFluidFromNBT(nbt);
		if (f != null) {
			String fluid = f.getLocalizedName();
			int amt = nbt.getInteger("lvl");
			if (amt > 0) {
				String amount = String.format("%d", amt/1000);
				String contents = "Contents: "+amount+"B of "+fluid;
				li.add(contents);
			}
		}
		return li;
	}

}