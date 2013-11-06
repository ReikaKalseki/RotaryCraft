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

public class TileEntitySeparatorPipe extends TileEntityPiping {

	private Fluid fluid;
	private int level;

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.FUELLINE || m == MachineRegistry.HOSE;
	}

	private boolean canIntakeFluid(String s) {
		return this.canIntakeFluid(FluidRegistry.getFluid(s));
	}

	private ForgeDirection getDirectionDir() {
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ? ForgeDirection.UP : ForgeDirection.DOWN;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.blockLapis.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return level > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return fluid;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SEPARATION.ordinal();
	}

	@Override
	public int getLiquidLevel() {
		return level;
	}

	@Override
	protected void setFluid(Fluid f) {
		fluid = f;
	}

	@Override
	protected void setLevel(int amt) {
		level = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return false;
	}

	@Override
	protected void onIntake(TileEntity te) {

	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return true;
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return side.offsetY == 0;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return side == this.getDirectionDir();
	}
}
