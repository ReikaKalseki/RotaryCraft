/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFuelLine extends TileEntityPiping {

	private int fuel = 0;
	private Fluid fluid;

	private boolean isAcceptableFuel(Fluid f) {
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		return false;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FUELLINE.ordinal();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.VALVE || m == MachineRegistry.SEPARATION || m == MachineRegistry.BYPASS;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.obsidian.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return fuel > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return fluid;
	}

	@Override
	public int getLiquidLevel() {
		return fuel;
	}

	@Override
	protected void setFluid(Fluid f) {
		fluid = f;
	}

	@Override
	protected void setLevel(int amt) {
		fuel = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return true;
	}

	@Override
	protected void onIntake(TileEntity te) {

	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return f.equals(FluidRegistry.getFluid("rc ethanol")) || f.equals(FluidRegistry.getFluid("jet fuel"));
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public Block getPipeBlockType() {
		return Block.obsidian;
	}
}
