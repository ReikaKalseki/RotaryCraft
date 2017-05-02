/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.ChromatiCraft.API.Interfaces.WorldRift;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityClutch extends TileEntity1DTransmitter {

	public boolean needsRedstone = true;

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
		if (this.isOutputEnabled()) {
			if (this.isProvider(te)) {
				if (m == MachineRegistry.SHAFT) {
					TileEntityShaft devicein = (TileEntityShaft)te;
					if (devicein.isCross()) {
						this.readFromCross(devicein);
						return;
					}
					else if (devicein.isWritingTo(this)) {
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
						this.readFromSplitter(world, x, y, z, devicein);
						torquein = torque;
						omegain = omega;
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
			else if (te instanceof WorldRift) {
				WorldRift sr = (WorldRift)te;
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

	public boolean isOutputEnabled() {
		return this.hasRedstoneSignal() == needsRedstone;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("redstone", needsRedstone);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		needsRedstone = NBT.getBoolean("redstone");
	}
}
