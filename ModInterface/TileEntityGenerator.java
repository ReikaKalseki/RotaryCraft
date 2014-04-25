/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.electricity.IVoltageOutput;
import universalelectricity.api.energy.IEnergyInterface;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGenerator extends TileEntityPowerReceiver implements IEnergyInterface, IVoltageOutput {

	public static final int OUTPUT_VOLTAGE = 24000;
	public static final float POWER_FACTOR = 0.875F;

	private ForgeDirection facingDir;

	public ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GENERATOR;
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
		this.getPower(false);
		//ReikaJavaLibrary.pConsole(this.getGenCurrent()+"A * "+OUTPUT_VOLTAGE+"V = "+this.getGenCurrent()*OUTPUT_VOLTAGE+"W");

		if (power > 0) {
			int dx = x+facingDir.offsetX;
			int dy = y+facingDir.offsetY;
			int dz = z+facingDir.offsetZ;
			int id = world.getBlockId(dx, dy, dz);
			if (id != 0) {
				Block b = Block.blocksList[id];
				int metadata = world.getBlockMetadata(dx, dy, dz);
				if (b.hasTileEntity(metadata)) {
					TileEntity te = world.getBlockTileEntity(dx, dy, dz);
					if (te instanceof IEnergyInterface) {
						IEnergyInterface ie = (IEnergyInterface)te;
						if (ie.canConnect(facingDir, this)) {
							long energy = (long)(this.getGenCurrent()*OUTPUT_VOLTAGE);
							ie.onReceiveEnergy(facingDir, energy, true);
						}
					}
				}
			}
		}
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			facingDir = ForgeDirection.EAST;
			break;
		}
		read = facingDir;
		write = read.getOpposite();
	}

	@Override
	public boolean canConnect(ForgeDirection direction, Object src) {
		return direction == this.getFacing().getOpposite();
	}

	private float getGenCurrent() {
		return power/(float)OUTPUT_VOLTAGE;//*POWER_FACTOR;
	}

	@Override
	public long getVoltageOutput(ForgeDirection side) {
		return side == facingDir ? OUTPUT_VOLTAGE : 0;
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive) {
		return 0;
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract) {
		return from == facingDir ? (long)(this.getGenCurrent()*OUTPUT_VOLTAGE) : 0;
	}

}
