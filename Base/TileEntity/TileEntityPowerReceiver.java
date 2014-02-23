/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public abstract class TileEntityPowerReceiver extends TileEntityIOMachine {

	public final long MINPOWER;
	public final int MINTORQUE;
	public final int MINSPEED;

	public PowerReceivers machine;

	private long[][] powerin = new long[4][3]; //stores P, T, omega

	public TileEntityPowerReceiver() {
		//ReikaJavaLibrary.pConsole(this.getClass()+" goes to "+this.getMachineIndex());
		machine = PowerReceivers.getEnumFromMachineIndex(this.getMachineIndex());
		if (machine == null) {
			MINPOWER = 0;
			MINSPEED = 0;
			MINTORQUE = 0;
			return;//throw new RuntimeException("Machine "+this.getName()+" in "+this.getClass()+" has no enum! Case?");
		}
		if (!machine.hasMultiValuedPower()) {
			MINPOWER = machine.getMinPower();
			MINSPEED = machine.getMinSpeed();
			MINTORQUE = machine.getMinTorque();
		}
		else {
			MINPOWER = 0;
			MINSPEED = 0;
			MINTORQUE = 0;
		}
	}

	@Override
	public void updateTileEntity() {
		super.updateTileEntity();
		if (MINPOWER == -1) {
			ReikaJavaLibrary.pConsole(this.getName()+" has not registered its power!");
			ReikaChatHelper.write(this.getName()+" has not registered its power!");
		}
	}

	private void clear() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++)
				powerin[i][j] = 0;
	}

	public long[] returnHighest(int num) {
		//this.clear();
		long[] val = new long[3];
		for (int i = 0; i < num; i++) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", powerin[i][0], i));
			if (powerin[i][0] > val[0]) { //decide based on max power
				for (int j = 0; j < 3; j++) {
					val[j] = powerin[i][j];
					//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d  %d", powerin[i][j], i, j));
				}
			}
		}
		return val;
	}

	public void getIOSidesDefault(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 1:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 2:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			break;
		case 3:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			break;
		case 4:	//moving up
			readx = xCoord;
			readz = zCoord;
			ready = yCoord-1;
			break;
		case 5:	//moving down
			readx = xCoord;
			readz = zCoord;
			ready = yCoord+1;
			break;
		}
	}

	public void readFromCross(TileEntityShaft cross, int slot) {
		//ReikaChatHelper.writeInt(slot+400);
		if (xCoord == cross.writex && zCoord == cross.writez) {
			//ReikaChatHelper.writeInt(cross.readomega[0]);
			powerin[slot][2] = cross.readomega[0];
			powerin[slot][1] = cross.readtorque[0];
			powerin[slot][0] = powerin[slot][1]*powerin[slot][2];
		}
		else if (xCoord == cross.writex2 && zCoord == cross.writez2) {
			powerin[slot][2] = cross.readomega[1];
			powerin[slot][1] = cross.readtorque[1];
			powerin[slot][0] = powerin[slot][1]*powerin[slot][2];
		}
		else
			return; //not its output
	}

	public void readFromSplitter(TileEntitySplitter spl, int slot) { //Complex enough to deserve its own function
		int ratio = spl.getRatioFromMode();
		if (ratio == 0)
			return;
		boolean favorbent = false;
		if (ratio < 0) {
			favorbent = true;
			ratio = -ratio;
		}
		if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
			powerin[slot][2] = spl.omega; //omega always constant
			if (ratio == 1) { //Even split, favorbent irrelevant
				powerin[slot][1] = spl.torque/2;
				powerin[slot][0] = spl.omega*spl.torque/2;
				return;
			}
			if (favorbent) {
				powerin[slot][1] = spl.torque/ratio;
			}
			else {
				powerin[slot][1] = (int)(spl.torque*((ratio-1D)/(ratio)));
			}
			powerin[slot][0] = powerin[slot][1]*powerin[slot][2];
		}
		else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
			powerin[slot][2] = spl.omega; //omega always constant
			if (ratio == 1) { //Even split, favorbent irrelevant
				powerin[slot][1] = spl.torque/2;
				powerin[slot][0] = spl.omega*spl.torque/2;
				return;
			}
			if (favorbent) {
				powerin[slot][1] = (int)(spl.torque*((ratio-1D)/(ratio)));
			}
			else {
				powerin[slot][1] = spl.torque/ratio;
			}
			powerin[slot][0] = powerin[slot][1]*powerin[slot][2];
		}
		else { //We are not one of its write-to blocks
			powerin[slot][0] = 0;
			powerin[slot][1] = 0;
			powerin[slot][2] = 0;
			return;
		}
	}

	public void getPower(boolean doublesided) {
		this.clear();
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", this.readx, this.ready, this.readz));
		TileEntity te = worldObj.getBlockTileEntity(readx, ready, readz);
		if (this.isProvider(te)) {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, readx, ready, readz);
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				if (devicein.writex == xCoord && devicein.writey == yCoord && devicein.writez == zCoord) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
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
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				else if (devicein.writex == xCoord && devicein.writez == zCoord) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d   %d", this.torquein, this.omegain));
			powerin[0][0] = torquein*omegain;
			powerin[0][1] = torquein;
			powerin[0][2] = omegain;
		}
		else {
			torquein = 0;
			omegain = 0;
		}

		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));

		if (!doublesided) {
			torque = torquein;
			omega = omegain;
			power = (long)omega*(long)torque;
			return;
		}
		torquein = 0;
		omegain = 0;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d  %d", powerin[0][0], powerin[0][1], powerin[0][2]));

		te = worldObj.getBlockTileEntity(readx2, ready2, readz2);
		if (this.isProvider(te)) {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, readx2, ready2, readz2);
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
				}
				if (devicein.writex == xCoord && devicein.writey == yCoord && devicein.writez == zCoord) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (te instanceof SimpleProvider) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (te instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
				}
				else if (devicein.writex == xCoord && devicein.writez == zCoord) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d   %d", torquein, omegain));
			powerin[1][0] = torquein*omegain;
			powerin[1][1] = torquein;
			powerin[1][2] = omegain;
		}
		else {
			torquein = 0;
			omegain = 0;
		}
		long[] powers = this.returnHighest(2);
		torque = (int) powers[1];
		omega = (int) powers[2];
		power = (long)torque*(long)omega;
	}

	public void getPower4Sided(int stepx, int stepy, int stepz) {
		readx = xCoord+1+stepx;
		readx2 = xCoord-1+stepx;
		readx3 = readx4 = xCoord+stepx;
		ready = ready2 = ready3 = ready4 = yCoord+stepy;
		readz = readz2 = zCoord+stepz;
		readz3 = zCoord+1+stepz;
		readz4 = zCoord-1+stepz;
		this.setPointingOffset(stepx, stepy, stepz);

		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", this.readx, this.ready, this.readz));
		MachineRegistry id1 = MachineRegistry.getMachine(worldObj, readx, ready, readz);
		MachineRegistry id2 = MachineRegistry.getMachine(worldObj, readx2, ready2, readz2);
		MachineRegistry id3 = MachineRegistry.getMachine(worldObj, readx3, ready3, readz3);
		MachineRegistry id4 = MachineRegistry.getMachine(worldObj, readx4, ready4, readz4);
		TileEntity te1 = worldObj.getBlockTileEntity(readx, ready, readz);
		TileEntity te2 = worldObj.getBlockTileEntity(readx2, ready2, readz2);
		TileEntity te3 = worldObj.getBlockTileEntity(readx3, ready3, readz3);
		TileEntity te4 = worldObj.getBlockTileEntity(readx4, ready4, readz4);

		if (this.isProvider(te1)) {
			if (id1 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te1;
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (id1 == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te1;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (te1 instanceof SimpleProvider) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (te1 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te1;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id1 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te1;
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		powerin[0][0] = torquein*omegain;
		powerin[0][1] = torquein;
		powerin[0][2] = omegain;
		torquein = 0;
		omegain = 0;


		if (this.isProvider(te2)) {
			if (id2 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te2;
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
					// ReikaChatHelper.writeInt(torquein);
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (id2 == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te2;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (te2 instanceof SimpleProvider) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (te2 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te2;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id2 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te2;
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		powerin[1][0] = torquein*omegain;
		powerin[1][1] = torquein;
		powerin[1][2] = omegain;
		// ReikaChatHelper.writeInt(powerin[1][0]);
		torquein = 0;
		omegain = 0;

		if (this.isProvider(te3)) {
			if (id3 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te3;
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 2);
					torquein = (int) powerin[2][1];
					omegain = (int) powerin[2][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (id3 == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te3;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (te3 instanceof SimpleProvider) {
				this.copyStandardPower(worldObj, readx3, ready3, readz3);
			}
			if (te3 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te3;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id3 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te3;
				// ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(devicein));
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 2);
					torquein = (int) powerin[2][1];
					omegain = (int) powerin[2][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		powerin[2][0] = torquein*omegain;
		powerin[2][1] = torquein;
		powerin[2][2] = omegain;
		torquein = 0;
		omegain = 0;

		if (this.isProvider(te4)) {
			if (id4 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te4;
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 3);
					torquein = (int) powerin[3][1];
					omegain = (int) powerin[3][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (id4 == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te4;
				ForgeDirection dir = this.getInputForgeDirection().getOpposite();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
			}
			if (te4 instanceof SimpleProvider) {
				this.copyStandardPower(worldObj, readx4, ready4, readz4);
			}

			if (te4 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te4;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id4 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te4;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(devicein));
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 3);
					torquein = (int) powerin[3][1];
					omegain = (int) powerin[3][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		powerin[3][0] = torquein*omegain;
		powerin[3][1] = torquein;
		powerin[3][2] = omegain;
		torquein = 0;
		omegain = 0;

		long[] powers = this.returnHighest(4);
		torque = (int) powers[1];
		omega = (int) powers[2];
		power = (long)torque*(long)omega;

	}

	public void getPowerBelow() {
		readx = xCoord;
		ready = yCoord-1;
		readz = zCoord;
		this.getPower(false);
	}

	public void getPowerAbove() {
		readx = xCoord;
		ready = yCoord+1;
		readz = zCoord;
		this.getPower(false);
	}

	public void getSummativeSidedPower() {
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		long[][] powers = new long[2][6];
		if (this.getMachine().getMinY(this) == 0) {
			this.getPowerBelow();
			powers[0][0] = omega;
			powers[1][0] = torque;
		}
		if (this.getMachine().getMaxY(this) == 1) {
			this.getPowerAbove();
			powers[0][1] = omega;
			powers[1][1] = torque;
		}
		ready = y;
		readx = x+1;
		readz = z;
		if (this.getMachine().getMaxX(this) == 1) {
			this.getPower(false);
			powers[0][2] = omega;
			powers[1][2] = torque;
		}
		readx = x-1;
		if (this.getMachine().getMinX(this) == 0) {
			this.getPower(false);
			powers[0][3] = omega;
			powers[1][3] = torque;
		}
		readx = x;
		readz = z+1;
		if (this.getMachine().getMaxZ(this) == 1) {
			this.getPower(false);
			powers[0][4] = omega;
			powers[1][4] = torque;
		}
		readz = z-1;
		if (this.getMachine().getMinZ(this) == 0) {
			this.getPower(false);
			powers[0][5] = omega;
			powers[1][5] = torque;
		}
		readx = Integer.MIN_VALUE;
		ready = Integer.MIN_VALUE;
		readz = Integer.MIN_VALUE;
		isOmniSided = true;
		torque = 0;
		omega = 0;
		power = 0;
		boolean unequal = false;
		unequal = !ReikaArrayHelper.allNonZerosEqual(powers[0]);
		if (unequal) {
			worldObj.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), rand.nextFloat()/2F, rand.nextFloat(), rand.nextFloat()/2F);
			if (rand.nextInt(5) == 0)
				worldObj.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F, 1F);
			return;
		}
		int i = 0;
		while(powers[0][i] == 0 && i < 5) {
			i++;
		}
		omega = (int)powers[0][i];
		torque = (int)ReikaArrayHelper.sumArray(powers[1]);
		power = (long)omega * (long)torque;
	}

	@Override
	public boolean canProvidePower() {
		return false;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();
		if (readx != Integer.MIN_VALUE && ready != Integer.MIN_VALUE && readz != Integer.MIN_VALUE) {
			pwr.addAll(pwr.getAllFrom(worldObj, readx, ready, readz, this, caller));
		}
		if (readx2 != Integer.MIN_VALUE && ready2 != Integer.MIN_VALUE && readz2 != Integer.MIN_VALUE) {
			pwr.addAll(pwr.getAllFrom(worldObj, readx2, ready2, readz2, this, caller));
		}
		if (readx3 != Integer.MIN_VALUE && ready3 != Integer.MIN_VALUE && readz3 != Integer.MIN_VALUE) {
			pwr.addAll(pwr.getAllFrom(worldObj, readx3, ready3, readz3, this, caller));
		}
		if (readx4 != Integer.MIN_VALUE && ready4 != Integer.MIN_VALUE && readz4 != Integer.MIN_VALUE) {
			pwr.addAll(pwr.getAllFrom(worldObj, readx4, ready4, readz4, this, caller));
		}
		return pwr;
	}
}
