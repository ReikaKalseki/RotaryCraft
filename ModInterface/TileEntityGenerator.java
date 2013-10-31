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
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.electricity.ElectricityPack;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGenerator extends TileEntityPowerReceiver implements IElectrical {

	public static final int OUTPUT_VOLTAGE = 24000;
	public static final float POWER_FACTOR = 0.875F;

	private ForgeDirection facingDir;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.GENERATOR.ordinal();
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
		//ReikaJavaLibrary.pConsole(this.getGenCurrent()+"A * "+OUTPUT_VOLTAGE+"V = "+this.getGenCurrent()*OUTPUT_VOLTAGE+"W");
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 0:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.EAST;
			break;
		}
	}

	@Override
	public boolean canConnect(ForgeDirection direction) {
		return direction == facingDir.getOpposite();
	}

	@Override
	public float receiveElectricity(ForgeDirection from, ElectricityPack receive, boolean doReceive) {
		return 0;
	}

	@Override
	public ElectricityPack provideElectricity(ForgeDirection from, ElectricityPack request, boolean doProvide) {
		return this.getProduction();
	}

	@Override
	public float getRequest(ForgeDirection direction) {
		return 0;
	}

	@Override
	public float getProvide(ForgeDirection direction) {
		return this.getGenCurrent()*OUTPUT_VOLTAGE;
	}

	@Override
	public float getVoltage() {
		return OUTPUT_VOLTAGE;
	}

	public ElectricityPack getProduction() {
		ElectricityPack e = new ElectricityPack(this.getGenCurrent(), OUTPUT_VOLTAGE);
		return e;
	}

	private float getGenCurrent() {
		return power/(float)OUTPUT_VOLTAGE;//*POWER_FACTOR;
	}

}
