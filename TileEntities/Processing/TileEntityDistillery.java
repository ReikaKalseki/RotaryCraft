/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDistillery extends PoweredLiquidIO {

	private int tickcount = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		Conversion conv = this.getConversion();
		if (tickcount >= 5) {
			if (this.canMake(conv)) {
				this.make(conv);
			}
			tickcount = 0;
		}
		else {
			tickcount++;
		}
		//ReikaJavaLibrary.pConsole(input+":"+output, Side.SERVER);
	}

	private void make(Conversion conv) {
		input.removeLiquid(conv.getRequiredAmount());
		output.addLiquid(conv.getProductionAmount(), conv.outputFluid);
	}

	private boolean canMake(Conversion conv) {
		if (conv == null)
			return false;
		if (power < conv.minPower)
			return false;
		if (torque < conv.minTorque)
			return false;
		return input.getLevel() >= conv.getRequiredAmount() && output.canTakeIn(conv.outputFluid, conv.getProductionAmount());
	}

	private Conversion getConversion() {
		if (input.isEmpty())
			return null;
		for (int i = 0; i < Conversion.list.length; i++) {
			Conversion c = Conversion.list[i];
			if (c.validate()) {
				if (c.inputFluid.equals(input.getActualFluid()))
					return c;
			}
		}
		return null;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe() || m == MachineRegistry.HOSE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		for (int i = 0; i < Conversion.list.length; i++) {
			Conversion c = Conversion.list[i];
			if (c.validate()) {
				if (c.inputFluid.equals(f))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to == ForgeDirection.UP;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from != ForgeDirection.UP;
	}

	@Override
	public boolean canIntakeFromPipe(MachineRegistry p) {
		return p.isStandardPipe() || p == MachineRegistry.HOSE || p == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canOutputToPipe(MachineRegistry p) {
		return p == MachineRegistry.HOSE || p.isStandardPipe() || p == MachineRegistry.FUELLINE;
	}

	@Override
	public int getCapacity() {
		return 6000;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.DISTILLER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private static enum Conversion {
		OIL("oil", "rc lubricant", 6, 2048, 8192),
		ETHANOL1("bioethanol", "rc ethanol", 1, 512, 131072),
		ETHANOL2("biofuel", "rc ethanol", -2, 512, 131072);

		public final Fluid inputFluid;
		public final Fluid outputFluid;
		private final int conversionFactor;

		public final long minPower;
		public final int minTorque;

		public static final Conversion[] list = values();

		private Conversion(String in, String out, int factor, int mint, long minp) {
			inputFluid = FluidRegistry.getFluid(in);
			outputFluid = FluidRegistry.getFluid(out);
			conversionFactor = factor;

			minTorque = mint;
			minPower = minp;
		}

		public int getProductionAmount() {
			return conversionFactor > 0 ? conversionFactor : 1;
		}

		public int getRequiredAmount() {
			return conversionFactor > 0 ? 1 : -conversionFactor;
		}

		public boolean validate() {
			return inputFluid != null && outputFluid != null;
		}

		@Override
		public String toString() {
			String name1 = inputFluid.getLocalizedName();
			String name2 = outputFluid.getLocalizedName();
			return name1+" ("+this.getRequiredAmount()+" mB) + ["+minTorque+" Nm & "+minPower+"W] -> "+name2+" ("+this.getProductionAmount()+" mB)";
		}
	}

	public static String getValidConversions() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Conversion.list.length; i++) {
			Conversion c = Conversion.list[i];
			if (c.validate()) {
				sb.append(c.toString());
				if (i < Conversion.list.length-1)
					sb.append("\n");
			}
		}
		return sb.toString();
	}

}
