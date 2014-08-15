/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public abstract class SprinklerBlock extends RotaryCraftTileEntity implements PipeConnector, RangedEffect {

	private int liquid;
	private int pressure;
	private StepTimer soundTimer = new StepTimer(40);

	private void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		ForgeDirection dir = this.getPipeDirection();
		int dx = x+dir.offsetX;
		int dy = y+dir.offsetY;
		int dz = z+dir.offsetZ;
		if (MachineRegistry.getMachine(world, dx, dy, dz) == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getTileEntity(dx, dy, dz);
			if (tile != null && tile.contains(FluidRegistry.WATER) && tile.getFluidLevel() > 0) {
				if (liquid < this.getCapacity()) {
					oldLevel = tile.getFluidLevel();
					int toremove = tile.getFluidLevel()/4+1;
					tile.removeLiquid(toremove);
					liquid = ReikaMathLibrary.extrema(liquid+oldLevel/4+1, 0, "max");
				}
				pressure = tile.getPressure();
			}
		}
		if (liquid > this.getCapacity())
			liquid = this.getCapacity();
	}

	public abstract int getCapacity();

	public abstract int getWaterConsumption();

	public abstract ForgeDirection getPipeDirection();

	@Override
	public final void updateEntity(World world, int x, int y, int z, int meta) {
		this.getLiq(world, x, y, z, meta);

		if (this.canPerformEffects()) {
			this.performEffects(world, x, y, z);
			soundTimer.update();
			if (soundTimer.checkCap()) {
				SoundRegistry.SPRINKLER.playSoundAtBlock(world, x, y, z, 1, 1);
			}
			liquid -= this.getWaterConsumption();
		}
	}

	public final boolean canPerformEffects() {
		return this.getRange() > 0 && liquid >= this.getWaterConsumption();
	}

	protected abstract void performEffects(World world, int x, int y, int z);

	public final int getWater() {
		return liquid;
	}

	public final int getPressure() {
		return pressure;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("press", pressure);
		NBT.setInteger("lvl", liquid);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		pressure = NBT.getInteger("press");
		liquid = NBT.getInteger("lvl");

		if (liquid < 0)
			liquid = 0;
		if (liquid > this.getCapacity())
			liquid = this.getCapacity();
		if (pressure < 0)
			pressure = 0;
	}

	@Override
	public final int getRange() {
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
	public final int getMaxRange() {
		return 8;
	}

	@Override
	public final boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side == this.getPipeDirection() ? p == MachineRegistry.PIPE : false;
	}

	@Override
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!resource.getFluid().equals(FluidRegistry.WATER))
			return 0;
		int toadd = Math.min(resource.amount, this.getCapacity()-liquid);
		liquid += toadd;
		return toadd;
	}

	public boolean canFill(ForgeDirection side, Fluid f) {
		return f.equals(FluidRegistry.WATER) && side == this.getPipeDirection();
	}

	@Override
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public final Flow getFlowForSide(ForgeDirection side) {
		return side == this.getPipeDirection() ? Flow.INPUT : Flow.NONE;
	}

}