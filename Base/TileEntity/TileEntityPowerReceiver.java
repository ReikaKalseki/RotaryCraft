/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

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

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityPowerReceiver extends TileEntityIOMachine {

	public final long MINPOWER;
	public final int MINTORQUE;
	public final int MINSPEED;

	private long prevpower;

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

	public final long getScaledOmega(int a) {
		return MINSPEED > 0 ? Math.min(a, a*omega/MINSPEED) : (omega > 0 ? a : 0);
	}

	public final long getScaledTorque(int a) {
		return MINTORQUE > 0 ? Math.min(a, a*torque/MINTORQUE) : (torque > 0 ? a : 0);
	}

	public final long getScaledPower(int a) {
		return MINPOWER > 0 ? Math.min(a, a*power/MINPOWER) : (power > 0 ? a : 0);
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
			read = ForgeDirection.EAST;
			break;
		case 1:
			read = ForgeDirection.WEST;
			break;
		case 2:
			read = ForgeDirection.SOUTH;
			break;
		case 3:
			read = ForgeDirection.NORTH;
			break;
		case 4:	//moving up
			read = ForgeDirection.DOWN;
			break;
		case 5:	//moving down
			read = ForgeDirection.UP;
			break;
		}
	}

	private void readFromCross(TileEntityShaft cross, int slot) {
		//ReikaChatHelper.writeInt(slot+400);
		if (cross.isWritingTo(this)) {
			//ReikaChatHelper.writeInt(cross.readomega[0]);
			powerin[slot][2] = cross.readomega[0];
			powerin[slot][1] = cross.readtorque[0];
			powerin[slot][0] = powerin[slot][1]*powerin[slot][2];
		}
		else if (cross.isWritingTo2(this)) {
			powerin[slot][2] = cross.readomega[1];
			powerin[slot][1] = cross.readtorque[1];
			powerin[slot][0] = powerin[slot][1]*powerin[slot][2];
		}
		else
			return; //not its output
	}

	protected void readFromSplitter(TileEntitySplitter spl, int slot) { //Complex enough to deserve its own function
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
		TileEntity te = this.getAdjacentTileEntity(read);
		if (this.isProvider(te)) {
			MachineRegistry m = this.getMachine(read);
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.isCross()) {
					this.readFromCross(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				if (devicein.isWritingTo(this)) {
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
				this.copyStandardPower(te);
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
					this.readFromSplitter(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				else if (devicein.isWritingTo(this)) {
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
			if (power != prevpower) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				prevpower = power;
			}
			return;
		}
		torquein = 0;
		omegain = 0;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d  %d", powerin[0][0], powerin[0][1], powerin[0][2]));

		te = this.getAdjacentTileEntity(read2);
		if (this.isProvider(te)) {
			MachineRegistry m = this.getMachine(read2);
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te;
				if (devicein.isCross()) {
					this.readFromCross(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
				}
				if (devicein.isWritingTo(this)) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus pwr = (TileEntityPowerBus)te;
				ForgeDirection dir = this.getInputForgeDirection();
				omegain = pwr.getSpeedToSide(dir);
				torquein = pwr.getTorqueToSide(dir);
				//ReikaJavaLibrary.pConsole(omegain, doublesided && this.getSide() == Side.SERVER);
			}
			if (te instanceof SimpleProvider) {
				this.copyStandardPower(te);
			}
			if (te instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
				if (sp.isEmitting() && sp.canWriteTo(read2.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te;
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
				}
				else if (devicein.isWritingTo(this)) {
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
		if (power != prevpower) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevpower = power;
		}
	}

	public void getOffsetPower4Sided(int stepx, int stepy, int stepz) {
		read = ForgeDirection.EAST;
		read2 = ForgeDirection.WEST;
		read3 = ForgeDirection.SOUTH;
		read4 = ForgeDirection.NORTH;
		this.setPointingOffset(stepx, stepy, stepz);

		int x1 = xCoord+stepx+read.offsetX;
		int y1 = yCoord+stepy+read.offsetY;
		int z1 = zCoord+stepz+read.offsetZ;
		int x2 = xCoord+stepx+read2.offsetX;
		int y2 = yCoord+stepy+read2.offsetY;
		int z2 = zCoord+stepz+read2.offsetZ;
		int x3 = xCoord+stepx+read3.offsetX;
		int y3 = yCoord+stepy+read3.offsetY;
		int z3 = zCoord+stepz+read3.offsetZ;
		int x4 = xCoord+stepx+read4.offsetX;
		int y4 = yCoord+stepy+read4.offsetY;
		int z4 = zCoord+stepz+read4.offsetZ;

		MachineRegistry id1 = MachineRegistry.getMachine(worldObj, x1, y1, z1);
		MachineRegistry id2 = MachineRegistry.getMachine(worldObj, x2, y2, z2);
		MachineRegistry id3 = MachineRegistry.getMachine(worldObj, x3, y3, z3);
		MachineRegistry id4 = MachineRegistry.getMachine(worldObj, x4, y4, z4);
		TileEntity te1 = this.getTileEntity(x1, y1, z1);
		TileEntity te2 = this.getTileEntity(x2, y2, z2);
		TileEntity te3 = this.getTileEntity(x3, y3, z3);
		TileEntity te4 = this.getTileEntity(x4, y4, z4);

		if (this.isProvider(te1)) {
			if (id1 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te1;
				if (devicein.isCross()) {
					this.readFromCross(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				else if (devicein.isWritingTo(this)) {
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
				this.copyStandardPower(te1);
			}
			if (te1 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te1;
				if (sp.isEmitting() && sp.canWriteTo(read.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id1 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te1;
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein, 0);
					torquein = (int) powerin[0][1];
					omegain = (int) powerin[0][2];
				}
				else if (devicein.isWritingTo(this)) {
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
				if (devicein.isCross()) {
					this.readFromCross(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
					// ReikaChatHelper.writeInt(torquein);
				}
				else if (devicein.isWritingTo(this)) {
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
				this.copyStandardPower(te2);
			}
			if (te2 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te2;
				if (sp.isEmitting() && sp.canWriteTo(read2.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id2 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te2;
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein, 1);
					torquein = (int) powerin[1][1];
					omegain = (int) powerin[1][2];
				}
				else if (devicein.isWritingTo(this)) {
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
				if (devicein.isCross()) {
					this.readFromCross(devicein, 2);
					torquein = (int) powerin[2][1];
					omegain = (int) powerin[2][2];
				}
				else if (devicein.isWritingTo(this)) {
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
				this.copyStandardPower(te3);
			}
			if (te3 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te3;
				if (sp.isEmitting() && sp.canWriteTo(read3.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id3 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te3;
				// ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(devicein));
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein, 2);
					torquein = (int) powerin[2][1];
					omegain = (int) powerin[2][2];
				}
				else if (devicein.isWritingTo(this)) {
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
				if (devicein.isCross()) {
					this.readFromCross(devicein, 3);
					torquein = (int) powerin[3][1];
					omegain = (int) powerin[3][2];
				}
				else if (devicein.isWritingTo(this)) {
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
				this.copyStandardPower(te4);
			}

			if (te4 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te4;
				if (sp.isEmitting() && sp.canWriteTo(read4.getOpposite())) {
					torquein = sp.getTorque();
					omegain = sp.getOmega();
				}
			}
			if (id4 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)te4;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(devicein));
				if (devicein.isSplitting()) {
					this.readFromSplitter(devicein, 3);
					torquein = (int) powerin[3][1];
					omegain = (int) powerin[3][2];
				}
				else if (devicein.isWritingTo(this)) {
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
		if (power != prevpower) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevpower = power;
		}
	}

	public void getPowerBelow() {
		read = ForgeDirection.DOWN;
		this.getPower(false);
	}

	public void getPowerAbove() {
		read = ForgeDirection.UP;
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
		read = ForgeDirection.EAST;
		if (this.getMachine().getMaxX(this) == 1) {
			this.getPower(false);
			powers[0][2] = omega;
			powers[1][2] = torque;
		}
		read = ForgeDirection.WEST;
		if (this.getMachine().getMinX(this) == 0) {
			this.getPower(false);
			powers[0][3] = omega;
			powers[1][3] = torque;
		}
		read = ForgeDirection.SOUTH;
		if (this.getMachine().getMaxZ(this) == 1) {
			this.getPower(false);
			powers[0][4] = omega;
			powers[1][4] = torque;
		}
		read = ForgeDirection.NORTH;
		if (this.getMachine().getMinZ(this) == 0) {
			this.getPower(false);
			powers[0][5] = omega;
			powers[1][5] = torque;
		}
		read = null;
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
			if (power != prevpower) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				prevpower = power;
			}
			return;
		}
		int i = 0;
		while(powers[0][i] == 0 && i < 5) {
			i++;
		}
		omega = (int)powers[0][i];
		torque = (int)ReikaArrayHelper.sumArray(powers[1]);
		power = (long)omega * (long)torque;
		if (power != prevpower) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevpower = power;
		}
	}

	@Override
	public boolean canProvidePower() {
		return false;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();
		if (read != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller));
		}
		if (read2 != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read2, xCoord+read2.offsetX, yCoord+read2.offsetY, zCoord+read2.offsetZ, this, caller));
		}
		if (read3 != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read3, xCoord+read3.offsetX, yCoord+read3.offsetY, zCoord+read3.offsetZ, this, caller));
		}
		if (read4 != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read4, xCoord+read4.offsetX, yCoord+read4.offsetY, zCoord+read4.offsetZ, this, caller));
		}
		return pwr;
	}
}