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
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySplitter extends TileEntityTransmissionMachine implements GuiController, ShaftMerger {

	public int[] writeinline = new int[2]; //xz coords
	public int[] writebend = new int[2]; //xz coords

	private int torquein2;
	private int omegain2;
	public int splitmode = 0;


	private int cheatCount = 0;
	private int cheatTick = 0;
	private int[] lastTorque = new int[7]; //torque 1-7 ticks ago

	private int pow2;

	public int getRatioFromMode() {
		switch(splitmode) {
		case -1:
			return 0; //if merge, return 0
		case 0:
			return 32; //31:1
		case 4:
			return -32; //31:1, favor bent
		case 1:
			return 16; //15:1
		case 5:
			return -16; //15:1, favor bent
		case 2:
			return 8; //7:1
		case 6:
			return -8; //7:1, favor bent
		case 3:
			return 4; //3:1
		case 7:
			return -4; //3:1, favor bent
		case 8:
			return 1; //1:1
		default:
			return 9999999; //will know something is wrong
		}
	}

	/**
	 * Returns block data at the location of this entity (client-only). Call parent one.
	 *//*
    public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }*/

	public boolean testForLoopCheat() { //logic: if for last 5 cycles lasttorque+torque2 = this.torque(now)
		boolean allstep = true;
		boolean allstep2 = true;

		if (torque != lastTorque[0]) {
			for (int i = 6; i > 0; i--) {
				lastTorque[i] = lastTorque[i-1]; //array shift
			}
			lastTorque[0] = torque;
		}

		for (int i = 0; i < 6; i++) {
			if (lastTorque[i+1]+torquein != lastTorque[i] || lastTorque[i] == 0)
				allstep = false;
		}
		for (int i = 0; i < 6; i++) {
			if (lastTorque[i+1]+torquein2 != lastTorque[i] || lastTorque[i] == 0)
				allstep2 = false;
		}

		return (allstep || allstep2);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		this.getIOSides(world, x, y, z, meta);
		this.transferPower(world, x, y, z, meta);
		power = (long)omega*(long)torque;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0://-z and +x -> -x
			readx = xCoord+1;
			readz2 = zCoord-1;
			writex = xCoord-1;
			readx2 = xCoord;
			readz = writez = zCoord;
			break;
		case 1: //+z and +x -> -z
			readx = xCoord+1;
			readz2 = zCoord+1;
			writex = readx2 = xCoord;
			readz = zCoord;
			writez = zCoord-1;
			break;
		case 2: //+z and -x -> +x
			readz = zCoord+1;
			readz2 = zCoord;
			readx2 = xCoord-1;
			writez = zCoord;
			readx = xCoord;
			writex = xCoord+1;
			break;
		case 3: //-z and -x -> +z
			writez = zCoord+1;
			readz2 = zCoord;
			readx2 = xCoord-1;
			readz = zCoord-1;
			writex = readx = xCoord;
			break;
		case 4://+z and +x -> -x
			readx = xCoord+1;
			readz2 = zCoord+1;
			writex = xCoord-1;
			readx2 = xCoord;
			readz = writez = zCoord;
			break;
		case 5: //+z and -x -> -z
			readx = xCoord-1;
			readz2 = zCoord+1;
			writex = readx2 = xCoord;
			readz = zCoord;
			writez = zCoord-1;
			break;
		case 6: //-z and -x -> +x
			readz = zCoord-1;
			readz2 = zCoord;
			readx2 = xCoord-1;
			writez = zCoord;
			readx = xCoord;
			writex = xCoord+1;
			break;
		case 7: //-z and +x -> +z
			writez = zCoord+1;
			readz2 = zCoord;
			readx2 = xCoord+1;
			readz = zCoord-1;
			writex = readx = xCoord;
			break;
			//---------------------------SPLITTER-----------------------------------
		case 8://-z and +x <- -x
			readx = xCoord-1;
			readz = zCoord;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord+1;
			writeinline[1] = zCoord;
			writebend[0] = xCoord;
			writebend[1] = zCoord-1;
			break;
		case 9: //+z and +x <- -z
			readx = xCoord;
			readz = zCoord-1;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord;
			writeinline[1] = zCoord+1;
			writebend[0] = xCoord+1;
			writebend[1] = zCoord;
			break;
		case 10: //+z and -x <- +x
			readz = zCoord;
			readx = xCoord+1;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord-1;
			writeinline[1] = zCoord;
			writebend[0] = xCoord;
			writebend[1] = zCoord+1;
			break;
		case 11: //-z and -x <- +z
			readz = zCoord+1;
			readx = xCoord;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord;
			writeinline[1] = zCoord-1;
			writebend[0] = xCoord-1;
			writebend[1] = zCoord;
			break;
		case 12://+z and +x <- -x
			readx = xCoord-1;
			readz = zCoord;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord+1;
			writeinline[1] = zCoord;
			writebend[0] = xCoord;
			writebend[1] = zCoord+1;
			break;
		case 13: //+z and -x <- -z
			readx = xCoord;
			readz = zCoord-1;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord;
			writeinline[1] = zCoord+1;
			writebend[0] = xCoord-1;
			writebend[1] = zCoord;
			break;
		case 14: //-z and -x <- +x
			readz = zCoord;
			readx = xCoord+1;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord-1;
			writeinline[1] = zCoord;
			writebend[0] = xCoord;
			writebend[1] = zCoord-1;
			break;
		case 15: //-z and +x <- +z
			readz = zCoord+1;
			readx = xCoord;
			readx2 = Integer.MIN_VALUE;
			readz2 = Integer.MIN_VALUE;
			writex = Integer.MIN_VALUE;
			writez = Integer.MIN_VALUE;
			writeinline[0] = xCoord;
			writeinline[1] = zCoord-1;
			writebend[0] = xCoord+1;
			writebend[1] = zCoord;
			break;
		}
		//ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, this.writex, this.yCoord, this.writez, 44);
		//ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, this.writex2, this.yCoord, this.writez2, 79);
		//ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, this.readx, this.yCoord, this.readz, 20);
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

	public void mergeReadFromCross(TileEntityShaft cross, int side) {
		if (xCoord == cross.writex && zCoord == cross.writez) {
			if (side == 0) {
				omegain = cross.readomega[0];
				torquein = cross.readtorque[0];
			}
			if (side == 1) {
				omegain2 = cross.readomega[0];
				torquein2 = cross.readtorque[0];
			}
		}
		else if (xCoord == cross.writex2 && zCoord == cross.writez2) {
			if (side == 0) {
				omegain = cross.readomega[1];
				torquein = cross.readtorque[1];
			}
			if (side == 1) {
				omegain2 = cross.readomega[1];
				torquein2 = cross.readtorque[1];
			}
		}
		else
			return; //not its output
	}

	public void transferPower(World world, int x, int y, int z, int meta) {
		ready = y;
		ready2 = y;
		omegain = torquein = 0;
		if (!this.isSplitting()) {
			TileEntity te = world.getBlockTileEntity(readx, ready, readz);
			TileEntity te2 = world.getBlockTileEntity(readx2, ready2, readz2);
			MachineRegistry m = MachineRegistry.getMachine(world, readx, ready, readz);
			MachineRegistry m2 = MachineRegistry.getMachine(world, readx2, ready2, readz2);
			if (this.isProvider(te)) {
				if (m == MachineRegistry.SHAFT) {
					TileEntityShaft devicein = (TileEntityShaft)te;
					if (devicein.isCross()) {
						this.mergeReadFromCross(devicein, 0);
						//  return;
					}
					else if (devicein.writex == x && devicein.writez == z) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
				}
				if (te instanceof SimpleProvider) {
					TileEntityIOMachine devicein = (TileEntityIOMachine)te;
					if (devicein.writex == x && devicein.writez == z) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
					else
						torquein = omegain = 0;
				}
				if (te instanceof ShaftPowerEmitter) {
					ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
					if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
						torquein = sp.getTorque();
						omegain = sp.getOmega();
					}
					else
						torquein2 = omegain2 = 0;
				}
				if (m == MachineRegistry.SPLITTER) {
					TileEntitySplitter devicein = (TileEntitySplitter)te;
					if (devicein.isSplitting()) {
						this.readFromSplitter(devicein);
					}
					else if (devicein.writex == x && devicein.writez == z) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
				}
			}
			else {
				torquein = omegain = 0;
			}
			if (this.isProvider(te2)) {
				if (m2 == MachineRegistry.SHAFT) {
					TileEntityShaft devicein2 = (TileEntityShaft)te2;
					if (devicein2.isCross()) {
						this.mergeReadFromCross(devicein2, 1);
						// ReikaChatHelper.writeInt(this.omegain2);
						// return;
					}
					else if (devicein2.writex == x && devicein2.writez == z) {
						torquein2 = devicein2.torque;
						omegain2 = devicein2.omega;
					}
				}
				if (te2 instanceof SimpleProvider) {
					TileEntityIOMachine devicein = (TileEntityIOMachine)te2;
					if (devicein.writex == x && devicein.writez == z) {
						torquein2 = devicein.torque;
						omegain2 = devicein.omega;
					}
					else
						torquein2 = omegain2 = 0;
				}
				if (te2 instanceof ShaftPowerEmitter) {
					ShaftPowerEmitter sp = (ShaftPowerEmitter)te2;
					if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
						torquein2 = sp.getTorque();
						omegain2 = sp.getOmega();
					}
					else
						torquein2 = omegain2 = 0;
				}
				if (m2 == MachineRegistry.SPLITTER) {
					TileEntitySplitter devicein2 = (TileEntitySplitter)te2;
					if (devicein2.isSplitting()) {
						this.readFromSplitter(devicein2);
					}
					else if (devicein2.writex == x && devicein2.writez == z) {
						torquein2 = devicein2.torque;
						omegain2 = devicein2.omega;
					}
				}
			}
			else {
				torquein2 = omegain2 = 0;
			}

			PowerSourceList in1 = PowerSourceList.getAllFrom(world, readx, ready, readz, this, this);
			PowerSourceList in2 = PowerSourceList.getAllFrom(world, readx2, ready2, readz2, this, this);
			if (this.isLoopingPower(in1, in2)) {
				omega = omegain;
				torque = torquein;
				power = omega*torque;
				return;
			}

			if (omegain == omegain2) {
				omega = omegain;
				torque = torquein+torquein2;
			}
			else {
				omega = ReikaMathLibrary.extrema(omegain, omegain2, "max");
				if (omega == omegain)
					torque = torquein;
				else
					torque = torquein2;
				if (omegain != 0 && omegain2 != 0) {
					world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
				}
				this.basicPowerReceiver();
			}
		}
		else {
			//--------------------------------------------------------------------------------------------
			//------################-------Splitter mode (dmg >= 8)-------##########################------
			//--------------------------------------------------------------------------------------------
			ready = y;
			MachineRegistry m = MachineRegistry.getMachine(world, readx, ready, readz);
			//ReikaJavaLibrary.pConsole(readx+"  "+readz+"  "+m);
			TileEntity te = world.getBlockTileEntity(readx, ready, readz);
			if (te == null || !(te instanceof TileEntityIOMachine)) {
				torque = omega = 0;
				return;
			}
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.isCross()) {
					this.readFromCross(devicein);
					//ReikaChatHelper.writeInt(this.torque);
					return;
				}
				if (devicein.writex == x && devicein.writey == y && devicein.writez == z) {
					torque = devicein.torque;
					omega = devicein.omega;
				}
				else
					torque = omega = 0;
			}
			if (te instanceof SimpleProvider) {
				TileEntityIOMachine devicein = (TileEntityIOMachine)te;
				if (devicein.writex == x && devicein.writez == z) {
					torque = devicein.torque;
					omega = devicein.omega;
				}
				else
					torque = omega = 0;
			}
			if (te instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torque = sp.getTorque();
					omega = sp.getOmega();
				}
				else
					torque = omega = 0;
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein);
					torque = torquein;
					omega = omegain;
				}
				else if (devicein.writex == x && devicein.writez == z) {
					torque = devicein.torque;
					omega = devicein.omega;
				}
				else
					torque = omega = 0;
			}
			power = torque*omega;
			this.writeToReceiver();
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d * %d = %d", this.omega, this.torque, this.power));
		}
	}

	public boolean isSplitting() {
		return this.getBlockMetadata() >= 8;
	}

	private boolean isLoopingPower(PowerSourceList in1, PowerSourceList in2) {
		if (torquein*omegain != 0 && in1.getMaxGennablePower() == 0) {
			omegain = omegain2;
			torquein = torquein2;
			return true;
		}
		if (torquein2*omegain2 != 0 && in2.getMaxGennablePower() == 0) {
			omegain2 = omegain;
			torquein2 = torquein;
			return true;
		}
		return false;
	}

	private void writeToReceiver() {
		int t1;
		int t2;
		int x1 = writeinline[0];
		int z1 = writeinline[1];
		int x2 = writebend[0];
		int z2 = writebend[1];
		int y = yCoord;
		World world = worldObj;

		int ratio = this.getRatioFromMode();
		if (ratio == 0)
			return;
		boolean favorbent = false;
		if (ratio < 0) {
			favorbent = true;
			ratio = -ratio;
		}
		if (ratio == 1) { //Even split, favorbent irrelevant
			t1 = torque/2;
			return;
		}
		if (favorbent) {
			t1 = torque/ratio;
		}
		else {
			t1 = (int)(torque*((ratio-1D)/(ratio)));
		}
		if (ratio == 1) { //Even split, favorbent irrelevant
			t2 = torque/2;
		}
		if (favorbent) {
			t2 = (int)(torque*((ratio-1D)/(ratio)));
		}
		else {
			t2 = torque/ratio;
		}

		this.writeToPowerReceiverAt(world, x1, y, z1, omega, t1);
		this.writeToPowerReceiverAt(world, x2, y, z2, omega, t2);
	}

	@Override
	public void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
		omegain = spl.omega; //omegain always constant
		int ratio = spl.getRatioFromMode();
		if (ratio == 0)
			return;
		boolean favorbent = false;
		if (ratio < 0) {
			favorbent = true;
			ratio = -ratio;
		}
		if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
			if (ratio == 1) { //Even split, favorbent irrelevant
				torquein = spl.torque/2;
				return;
			}
			if (favorbent) {
				torquein = spl.torque/ratio;
			}
			else {
				torquein = (int)(spl.torque*((ratio-1D)/(ratio)));
			}
		}
		else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
			omegain = spl.omega; //omegain always constant
			if (ratio == 1) { //Even split, favorbent irrelevant
				torquein = spl.torque/2;
				return;
			}
			if (favorbent) {
				torquein = (int)(spl.torque*((ratio-1D)/(ratio)));
			}
			else {
				torquein = spl.torque/ratio;
			}
		}
		else { //We are not one of its write-to blocks
			torquein = 0;
			omegain = 0;
			return;
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("mode", splitmode);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		splitmode = NBT.getInteger("mode");
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
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
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPLITTER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();
		if (this.equals(caller)) {
			return pwr;
		}
		if (caller == null)
			caller = this;
		if (!this.isSplitting()) { //merge
			PowerSourceList in1 = pwr.getAllFrom(worldObj, readx, ready, readz, this, caller);
			PowerSourceList in2 = pwr.getAllFrom(worldObj, readx2, ready2, readz2, this, caller);
			pwr.addAll(in1);
			pwr.addAll(in2);
			return pwr;
		}
		else {
			return PowerSourceList.getAllFrom(worldObj, readx, ready, readz, this, caller);
		}
	}

	@Override
	public boolean isFlipped() {
		return isFlipped;
	}

	@Override
	public void setFlipped(boolean set) {
		isFlipped = set;
	}
}
