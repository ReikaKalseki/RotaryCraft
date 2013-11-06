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
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityHose extends TileEntityPiping {

	public int lubricant = 0;

	@Override
	public int getMachineIndex() {
		return MachineRegistry.HOSE.ordinal();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE || m == MachineRegistry.VALVE || m == MachineRegistry.SEPARATION;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.planks.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return lubricant > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return FluidRegistry.getFluid("lubricant");
	}

	@Override
	public int getLiquidLevel() {
		return lubricant;
	}

	@Override
	protected void setFluid(Fluid f) { }

	@Override
	protected void setLevel(int amt) {
		lubricant = amt;
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
		return f.equals(FluidRegistry.getFluid("lubricant"));
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return true;
	}
}
