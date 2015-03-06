/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.ChromatiCraft.API.WorldRift;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.API.Power.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
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

	protected final void getPower(boolean doubleSided) {
		this.getPower(worldObj, xCoord, yCoord, zCoord, doubleSided);
	}

	private void getPower(World world, int x, int y, int z, boolean doubleSided) {
		if (worldObj.isRemote && !RotaryAux.getPowerOnClient)
			return;
		this.clear();
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", this.readx, this.ready, this.readz));
		int dx = x+read.offsetX;
		int dy = y+read.offsetY;
		int dz = z+read.offsetZ;
		MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
		TileEntity te = isCentered ? this.getAdjacentTileEntity(read) : world.getTileEntity(dx, dy, dz);
		if (this.isProvider(te)) {
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
				else {
					torquein = omegain = 0;
				}
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d   %d", this.torquein, this.omegain));
			powerin[0][0] = torquein*omegain;
			powerin[0][1] = torquein;
			powerin[0][2] = omegain;
		}
		else if (te instanceof WorldRift) {
			WorldRift sr = (WorldRift)te;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null)
				this.getPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, doubleSided);
			else {
				torquein = omegain = 0;
			}
		}
		else {
			torquein = 0;
			omegain = 0;
		}

		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));

		if (!doubleSided) {
			torque = torquein;
			omega = omegain;
			power = (long)omega*(long)torque;
			if (power != prevpower) {
				//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				prevpower = power;
			}
			return;
		}
		torquein = 0;
		omegain = 0;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d  %d", powerin[0][0], powerin[0][1], powerin[0][2]));

		dx = x+read2.offsetX;
		dy = y+read2.offsetY;
		dz = z+read2.offsetZ;
		m = isCentered ? this.getMachine(read2) : MachineRegistry.getMachine(world, dx, dy, dz);
		te = isCentered ? this.getAdjacentTileEntity(read2) : world.getTileEntity(dx, dy, dz);
		if (this.isProvider(te)) {
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
				else {
					torquein = omegain = 0;
				}
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d   %d", torquein, omegain));
			powerin[1][0] = torquein*omegain;
			powerin[1][1] = torquein;
			powerin[1][2] = omegain;
		}
		else if (te instanceof WorldRift) {
			WorldRift sr = (WorldRift)te;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null)
				this.getPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, doubleSided);
			else {
				torquein = omegain = 0;
			}
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
			//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevpower = power;
		}
	}

	protected final void getOffsetPower4Sided(int stepx, int stepy, int stepz) {
		this.getOffsetPower4Sided(worldObj, xCoord, yCoord, zCoord, stepx, stepy, stepz);
	}

	private void getOffsetPower4Sided(World world, int x, int y, int z, int stepx, int stepy, int stepz) {
		if (worldObj.isRemote && !RotaryAux.getPowerOnClient)
			return;
		read = ForgeDirection.EAST;
		read2 = ForgeDirection.WEST;
		read3 = ForgeDirection.SOUTH;
		read4 = ForgeDirection.NORTH;
		this.setPointingOffset(stepx, stepy, stepz);

		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;

		int x1 = x+stepx+read.offsetX;
		int y1 = y+stepy+read.offsetY;
		int z1 = z+stepz+read.offsetZ;
		int x2 = x+stepx+read2.offsetX;
		int y2 = y+stepy+read2.offsetY;
		int z2 = z+stepz+read2.offsetZ;
		int x3 = x+stepx+read3.offsetX;
		int y3 = y+stepy+read3.offsetY;
		int z3 = z+stepz+read3.offsetZ;
		int x4 = x+stepx+read4.offsetX;
		int y4 = y+stepy+read4.offsetY;
		int z4 = z+stepz+read4.offsetZ;

		MachineRegistry id1 = MachineRegistry.getMachine(world, x1, y1, z1);
		MachineRegistry id2 = MachineRegistry.getMachine(world, x2, y2, z2);
		MachineRegistry id3 = MachineRegistry.getMachine(world, x3, y3, z3);
		MachineRegistry id4 = MachineRegistry.getMachine(world, x4, y4, z4);
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
				else {
					torquein = omegain = 0;
				}
			}
		}
		else if (te1 instanceof WorldRift) {
			WorldRift sr = (WorldRift)te1;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null) {
				this.getPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, false);
			}
			else {
				torque = omega = 0;
			}
			torquein = torque;
			omegain = omega;
			omega = torque = 0;
			power = 0;
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
				else {
					torquein = omegain = 0;
				}
			}
		}
		else if (te2 instanceof WorldRift) {
			WorldRift sr = (WorldRift)te2;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null) {
				read = read2;
				this.getPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, false);
			}
			else {
				torque = omega = 0;
			}
			torquein = torque;
			omegain = omega;
			omega = torque = 0;
			power = 0;
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
				else {
					torquein = omegain = 0;
				}
			}
		}
		else if (te3 instanceof WorldRift) {
			WorldRift sr = (WorldRift)te3;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null) {
				read = read3;
				this.getPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, false);
			}
			else {
				torque = omega = 0;
			}
			torquein = torque;
			omegain = omega;
			omega = torque = 0;
			power = 0;
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
				else {
					torquein = omegain = 0;
				}
			}
		}
		else if (te4 instanceof WorldRift) {
			WorldRift sr = (WorldRift)te4;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null) {
				read = read4;
				this.getPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, false);
			}
			else {
				torque = omega = 0;
			}
			torquein = torque;
			omegain = omega;
			omega = torque = 0;
			power = 0;
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
			//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevpower = power;
		}
	}

	protected final void getPowerBelow() {
		read = ForgeDirection.DOWN;
		this.getPower(false);
	}

	protected final void getPowerAbove() {
		read = ForgeDirection.UP;
		this.getPower(false);
	}

	protected final void getSummativeSidedPower() {
		isOmniSided = true;
		if (worldObj.isRemote && !RotaryAux.getPowerOnClient)
			return;
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
				//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
			//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevpower = power;
		}
	}

	@Override
	public boolean canProvidePower() {
		return false;
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();
		if (read != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read, xCoord+read.offsetX+this.getPointingOffsetX(), yCoord+read.offsetY+this.getPointingOffsetY(), zCoord+read.offsetZ+this.getPointingOffsetZ(), this, caller));
		}
		if (read2 != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read2, xCoord+read2.offsetX+this.getPointingOffsetX(), yCoord+read2.offsetY+this.getPointingOffsetY(), zCoord+read2.offsetZ+this.getPointingOffsetZ(), this, caller));
		}
		if (read3 != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read3, xCoord+read3.offsetX+this.getPointingOffsetX(), yCoord+read3.offsetY+this.getPointingOffsetY(), zCoord+read3.offsetZ+this.getPointingOffsetZ(), this, caller));
		}
		if (read4 != null) {
			pwr.addAll(pwr.getAllFrom(worldObj, read4, xCoord+read4.offsetX+this.getPointingOffsetX(), yCoord+read4.offsetY+this.getPointingOffsetY(), zCoord+read4.offsetZ+this.getPointingOffsetZ(), this, caller));
		}
		return pwr;
	}
}
