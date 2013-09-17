/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntity1DTransmitter;
import Reika.RotaryCraft.Models.ModelBevel;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGearBevel extends TileEntity1DTransmitter implements GuiController {

	public int direction;

	public void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
			omega = spl.omega; //omega always constant
			if (sratio == 1) { //Even split, favorbent irrelevant
				torque = spl.torque/2;
				return;
			}
			if (favorbent) {
				torque = spl.torque/sratio;
			}
			else {
				torque = (int)(spl.torque*((sratio-1D))/sratio);
			}
		}
		else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
			omega = spl.omega; //omega always constant
			if (sratio == 1) { //Even split, favorbent irrelevant
				torque = spl.torque/2;
				return;
			}
			if (favorbent) {
				torque = (int)(spl.torque*((sratio-1D)/(sratio)));
			}
			else {
				torque = spl.torque/sratio;
			}
		}
		else //We are not one of its write-to blocks
			return;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		power = omega*torque;
		int id = worldObj.getBlockId(x, y, z);
		this.getIOSides(world, x, y, z);
		this.transferPower(world, x, y, z, meta);

		this.basicPowerReceiver();
	}

	public void getIOSides(World world, int x, int y, int z) {
		switch(direction) {
		case 0://-x -> -z
			readx = x-1;
			writex = x;
			readz = z;
			writez = z-1;
			ready = y;
			writey = y;
			break;
		case 1:
			readx = x;
			writex = x+1;
			readz = z-1;
			writez = z;
			ready = y;
			writey = y;
			break;
		case 2:
			readz = z;
			writez = z+1;
			readx = x+1;
			writex = x;
			ready = y;
			writey = y;
			break;
		case 3:
			writez = z;
			readz = z+1;
			writex = x-1;
			readx = x;
			ready = y;
			writey = y;
			break;
		case 4:
			readx = x-1;
			writex = x;
			readz = z;
			writez = z+1;
			ready = y;
			writey = y;
			break;
		case 5:
			readx = x;
			writex = x-1;
			readz = z-1;
			writez = z;
			ready = y;
			writey = y;
			break;
		case 6:
			readz = z;
			writez = z-1;
			readx = x+1;
			writex = x;
			ready = y;
			writey = y;
			break;
		case 7:
			writez = z;
			readz = z+1;
			writex = x+1;
			readx = x;
			ready = y;
			writey = y;
			break;
		case 8:	//VERTICAL POSITIONS - going up from flat
			readx = x-1;
			readz = z;
			ready = y;
			writex = x;
			writez = z;
			writey = y+1;
			break;
		case 9:
			readx = x;
			readz = z-1;
			ready = y;
			writex = x;
			writez = z;
			writey = y+1;
			break;
		case 10:
			readz = z;
			readx = x+1;
			ready = y;
			writex = x;
			writez = z;
			writey = y+1;
			break;
		case 11:
			readz = z+1;
			readx = x;
			ready = y;
			writex = x;
			writez = z;
			writey = y+1;
			break;
		case 12: //VERTICAL POSITIONS - going flat from up
			readx = x;
			readz = z;
			ready = y-1;
			writez = z;
			writex = x-1;
			writey = y;
			break;
		case 13:
			readx = x;
			readz = z;
			ready = y-1;
			writez = z-1;
			writex = x;
			writey = y;
			break;
		case 14:
			readz = z;
			readx = x;
			ready = y-1;
			writez = z;
			writex = x+1;
			writey = y;
			break;
		case 15:
			readz = z;
			readx = x;
			ready = y-1;
			writez = z+1;
			writex = x;
			writey = y;
			break;
		case 16: //VERTICAL POSITIONS - going down from flat
			readx = x-1;
			readz = z;
			ready = y;
			writez = z;
			writex = x;
			writey = y-1;
			break;
		case 17:
			readx = x;
			readz = z-1;
			ready = y;
			writez = z;
			writex = x;
			writey = y-1;
			break;
		case 18:
			readz = z;
			readx = x+1;
			ready = y;
			writez = z;
			writex = x;
			writey = y-1;
			break;
		case 19:
			readz = z+1;
			readx = x;
			ready = y;
			writez = z;
			writex = x;
			writey = y-1;
			break;
		case 20: //VERTICAL POSITIONS - going flat from down
			readx = x;
			readz = z;
			ready = y+1;
			writez = z;
			writex = x-1;
			writey = y;
			break;
		case 21:
			readx = x;
			readz = z;
			ready = y+1;
			writez = z-1;
			writex = x;
			writey = y;
			break;
		case 22:
			readz = z;
			readx = x;
			ready = y+1;
			writez = z;
			writex = x+1;
			writey = y;
			break;
		case 23:
			readz = z;
			readx = x;
			ready = y+1;
			writez = z+1;
			writex = x;
			writey = y;
			break;
		}
		//ReikaWorldHelper.legacySetBlockWithNotify(world, readx, ready, readz, 4);
		//ReikaWorldHelper.legacySetBlockWithNotify(world, writex, writey, writez, 49);
	}

	public void readFromCross(TileEntityShaft cross) {
		if (xCoord == cross.writex && zCoord == cross.writez) {
			omega = cross.readomega[0];
			torque = cross.readtorque[0];
		}
		else if (xCoord == cross.writex2 && zCoord == cross.writez2) {
			omega = cross.readomega[1];
			torque = cross.readtorque[1];
		}
		else
			return; //not its output
	}

	@Override
	public void transferPower(World world, int x, int y, int z, int meta) {
		omegain = torquein = 0;
		TileEntity te = worldObj.getBlockTileEntity(readx, ready, readz);
		if (this.isProvider(te) && this.isIDTEMatch(worldObj, readx, ready, readz)) {
			MachineRegistry m = MachineRegistry.machineList[((RotaryCraftTileEntity)(te)).getMachineIndex()];
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)world.getBlockTileEntity(readx, ready, readz);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein);
					return;
				}
				if (devicein.writex == x && devicein.writey == y && devicein.writez == z) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te instanceof SimpleProvider) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (te instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)world.getBlockTileEntity(readx, y, readz);
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein);
					return;
				}
				else if (devicein.writex == x && devicein.writez == z) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}

		omega = omegain;
		torque = torquein;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("dir", direction);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		direction = NBT.getInteger("dir");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelBevel();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BEVELGEARS.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void onEMP() {}
}
