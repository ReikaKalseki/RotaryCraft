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
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBevelGear extends TileEntity1DTransmitter implements GuiController {

	public int direction;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		power = (long)omega*(long)torque;
		this.getIOSides(world, x, y, z);
		this.transferPower(world, x, y, z, meta);

		this.basicPowerReceiver();
	}

	public void getIOSides(World world, int x, int y, int z) {
		//ReikaJavaLibrary.pConsole(direction, Side.SERVER);
		switch(direction) {
		case 0://-x -> -z
			read = ForgeDirection.WEST;
			write = ForgeDirection.NORTH;
			break;
		case 1:
			read = ForgeDirection.NORTH;
			write = ForgeDirection.EAST;
			break;
		case 2:
			read = ForgeDirection.EAST;
			write = ForgeDirection.SOUTH;
			break;
		case 3:
			read = ForgeDirection.SOUTH;
			write = ForgeDirection.WEST;
			break;
		case 4:
			read = ForgeDirection.WEST;
			write = ForgeDirection.SOUTH;
			break;
		case 5:
			read = ForgeDirection.NORTH;
			write = ForgeDirection.WEST;
			break;
		case 6:
			write = ForgeDirection.NORTH;
			read = ForgeDirection.EAST;
			break;
		case 7:
			read = ForgeDirection.SOUTH;
			write = ForgeDirection.EAST;
			break;
		case 8:	//VERTICAL POSITIONS - going up from flat
			read = ForgeDirection.WEST;
			write = ForgeDirection.UP;
			break;
		case 9:
			read = ForgeDirection.NORTH;
			write = ForgeDirection.UP;
			break;
		case 10:
			read = ForgeDirection.EAST;
			write = ForgeDirection.UP;
			break;
		case 11:
			read = ForgeDirection.SOUTH;
			write = ForgeDirection.UP;
			break;
		case 12: //VERTICAL POSITIONS - going flat from up
			read = ForgeDirection.DOWN;
			write = ForgeDirection.WEST;
			break;
		case 13:
			read = ForgeDirection.DOWN;
			write = ForgeDirection.NORTH;
			break;
		case 14:
			read = ForgeDirection.DOWN;
			write = ForgeDirection.EAST;
			break;
		case 15:
			read = ForgeDirection.DOWN;
			write = ForgeDirection.SOUTH;
			break;
		case 16: //VERTICAL POSITIONS - going down from flat
			write = ForgeDirection.DOWN;
			read = ForgeDirection.WEST;
			break;
		case 17:
			write = ForgeDirection.DOWN;
			read = ForgeDirection.NORTH;
			break;
		case 18:
			write = ForgeDirection.DOWN;
			read = ForgeDirection.EAST;
			break;
		case 19:
			write = ForgeDirection.DOWN;
			read = ForgeDirection.SOUTH;
			break;
		case 20: //VERTICAL POSITIONS - going flat from down
			read = ForgeDirection.UP;
			write = ForgeDirection.WEST;
			break;
		case 21:
			read = ForgeDirection.UP;
			write = ForgeDirection.NORTH;
			break;
		case 22:
			read = ForgeDirection.UP;
			write = ForgeDirection.EAST;
			break;
		case 23:
			read = ForgeDirection.UP;
			write = ForgeDirection.SOUTH;
			break;
		}
		//ReikaWorldHelper.legacySetBlockWithNotify(world, readx, ready, readz, 4);
		//ReikaWorldHelper.legacySetBlockWithNotify(world, writex, writey, writez, 49);
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

		omega = omegain;
		torque = torquein;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("dir", direction);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		direction = NBT.getInteger("dir");
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
		return MachineRegistry.BEVELGEARS;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void onEMP() {}
}