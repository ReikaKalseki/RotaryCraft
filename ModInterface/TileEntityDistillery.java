/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDistillery extends PoweredLiquidIO {

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
		Conversion conv = this.getConversion();
		if (this.canMake(conv))
			this.make(conv);
		//ReikaJavaLibrary.pConsole(input+":"+output, Side.SERVER);
	}

	private void make(Conversion conv) {
		input.removeLiquid(conv.getRequiredAmount());
		output.addLiquid(conv.getProductionAmount(), conv.outputFluid);
	}

	private boolean canMake(Conversion conv) {
		if (conv == null)
			return false;
		if (power < MINPOWER)
			return false;
		if (torque < MINTORQUE)
			return false;
		return input.getLevel() >= conv.getRequiredAmount() && output.canTakeIn(conv.outputFluid, conv.getProductionAmount());
	}

	public void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			readx = x+1;
			readz = z;
			ready = y;
			break;
		case 1:
			readx = x-1;
			readz = z;
			ready = y;
			break;
		case 2:
			readz = z+1;
			readx = x;
			ready = y;
			break;
		case 3:
			readz = z-1;
			readx = x;
			ready = y;
			break;
		}
	}

	private Conversion getConversion() {
		if (input.isEmpty())
			return null;
		for (int i = 0; i < Conversion.list.length; i++) {
			Conversion c = Conversion.list[i];
			if (c.inputFluid.equals(input.getActualFluid()))
				return c;
		}
		return null;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.HOSE;
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		for (int i = 0; i < Conversion.list.length; i++) {
			Conversion c = Conversion.list[i];
			if (c.inputFluid.equals(f))
				return true;
		}
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to == ForgeDirection.UP;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from == ForgeDirection.DOWN;
	}

	@Override
	public boolean canIntakeFromPipe(MachineRegistry p) {
		return p == MachineRegistry.PIPE;
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p == MachineRegistry.HOSE;
	}

	@Override
	public int getCapacity() {
		return 6000;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.DISTILLER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public static enum Conversion {
		OIL(FluidRegistry.getFluid("oil"), FluidRegistry.getFluid("lubricant"), 6);

		public final Fluid inputFluid;
		public final Fluid outputFluid;
		private final int conversionFactor;

		public static final Conversion[] list = values();

		private Conversion(Fluid in, Fluid out, int factor) {
			inputFluid = in;
			outputFluid = out;
			conversionFactor = factor;
		}

		public int getProductionAmount() {
			return conversionFactor > 0 ? conversionFactor : 1;
		}

		public int getRequiredAmount() {
			return conversionFactor > 0 ? 1 : -conversionFactor;
		}
	}

}
