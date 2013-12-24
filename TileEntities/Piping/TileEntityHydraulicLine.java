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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityHydraulicLine extends RotaryCraftTileEntity {

	private int pressure;
	private int flow;
	private ForgeDirection in;
	private ForgeDirection out;

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.HYDRAULICLINE.ordinal();
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
		ForgeDirection dir = this.getInput();
		int dx = x+dir.offsetX;
		int dy = y+dir.offsetY;
		int dz = z+dir.offsetZ;
		MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
		if (m == MachineRegistry.HYDRAULICLINE) {
			TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(dx, dy, dz);
			if (te.getOutput() == this.getInput().getOpposite()) {
				pressure = (int) (te.getPressure() > 0 ? te.getPressure()-64/ReikaMathLibrary.logbase(te.getPressure(), 2) : 0);
				flow = te.getFlowRate();
			}
			else {
				pressure = 0;
				flow = 0;
			}
		}/*

		dir = this.getOutput();
		dx = x+dir.offsetX;
		dy = y+dir.offsetY;
		dz = z+dir.offsetZ;
		m = MachineRegistry.getMachine(world, dx, dy, dz);
		if (m == MachineRegistry.HYDRAULICLINE) {
			TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(dx, dy, dz);
			if (te.getInput() == this.getOutput().getOpposite()) {
				te.setPressure(pressure);
				te.setFlowRate(flow);
			}
		}*/
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int p) {
		pressure = p;
	}

	public int getFlowRate() {
		return flow;
	}

	public void setFlowRate(int p) {
		flow = p;
	}

	public boolean isConnectedOnSide(ForgeDirection side) {
		return side == in || side == out;
	}

	public ForgeDirection getInput() {
		return in != null ? in : ForgeDirection.UNKNOWN;
	}

	public ForgeDirection getOutput() {
		return out != null ? out : ForgeDirection.UNKNOWN;
	}

	public ForgeDirection setInput(ForgeDirection side) {
		return in = side;
	}

	public ForgeDirection setOutput(ForgeDirection side) {
		return out = side;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		pressure = NBT.getInteger("press");
		flow = NBT.getInteger("rate");

		in = dirs[NBT.getInteger("input")];
		out = dirs[NBT.getInteger("output")];
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setInteger("press", pressure);
		NBT.setInteger("rate", flow);

		NBT.setInteger("output", this.getOutput().ordinal());
		NBT.setInteger("input", this.getInput().ordinal());
	}

	public boolean isConnectedToMachine(ForgeDirection dir) {
		if (dir != this.getInput() && dir != this.getOutput())
			return false;
		int dx = xCoord+dir.offsetX;
		int dy = yCoord+dir.offsetY;
		int dz = zCoord+dir.offsetZ;
		MachineRegistry m = MachineRegistry.getMachine(worldObj, dx, dy, dz);
		return m == MachineRegistry.HYDRAULIC || m == MachineRegistry.ROUTER;
	}

}
