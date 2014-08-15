/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import Reika.ChromatiCraft.API.SpaceRift;
import Reika.DragonAPI.Instantiable.WorldLocation;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityClutch extends TileEntity1DTransmitter {

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		Block b = world.getBlock(x, y, z);

		this.getIOSides(world, x, y, z, meta, true);
		this.transferPower(world, x, y, z, meta);
		power = (long)omega*(long)torque;

		this.basicPowerReceiver();
	}

	@Override
	protected void transferPower(World world, int x, int y, int z, int meta) {
		omegain = torquein = 0;
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		int dx = x+read.offsetX;
		int dy = y+read.offsetY;
		int dz = z+read.offsetZ;
		MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
		TileEntity te = isCentered ? this.getAdjacentTileEntity(read) : world.getTileEntity(dx, dy, dz);
		if (world.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			if (this.isProvider(te)) {
				if (m == MachineRegistry.SHAFT) {
					TileEntityShaft devicein = (TileEntityShaft)te;
					if (devicein.isCross()) {
						this.readFromCross(devicein);
						return;
					}
					if (devicein.isWritingTo(this)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
				}
				if (te instanceof SimpleProvider) {
					this.copyStandardPower(te);
				}
				if (m == MachineRegistry.POWERBUS) {
					TileEntityPowerBus pwr = (TileEntityPowerBus)te;
					ForgeDirection dir = this.getInputForgeDirection().getOpposite();
					omegain = pwr.getSpeedToSide(dir);
					torquein = pwr.getTorqueToSide(dir);
				}
				if (te instanceof ShaftPowerEmitter) {
					ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
					if (sp.isEmitting() && sp.canWriteTo(read.getOpposite())) {
						torquein = sp.getTorque();
						omegain = sp.getOmega();
					}
				}
				if (m == MachineRegistry.SPLITTER) {
					TileEntitySplitter devicein = (TileEntitySplitter)te;
					if (devicein.isSplitting()) {
						this.readFromSplitter(devicein);
						return;
					}
					else if (devicein.isWritingTo(this)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
				}
				omega = omegain;
				torque = torquein;
			}
			else if (te instanceof SpaceRift) {
				SpaceRift sr = (SpaceRift)te;
				WorldLocation loc = sr.getLinkTarget();
				if (loc != null)
					this.transferPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta);
			}
			else {
				omega = 0;
				torque = 0;
				power = 0;
				return;
			}
		}
		else {
			omega = torque = 0;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CLUTCH;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}