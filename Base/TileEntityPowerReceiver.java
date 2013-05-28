/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.EnumReceivers;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;

public abstract class TileEntityPowerReceiver extends TileEntityIOMachine {

	public final int MINPOWER;
	public final int MINTORQUE;
	public final int MINSPEED;

	public EnumReceivers machine;

	private int[][] powerin = new int[4][3]; //stores P, T, omega

	public TileEntityPowerReceiver() {
		//ReikaJavaLibrary.pConsole(this.getClass()+" goes to "+this.getMachineIndex());
		machine = EnumReceivers.getEnumFromMachineIndex(this.getMachineIndex());
		if (machine == null)
			throw new RuntimeException("Machine "+this.getName()+" in "+this.getClass()+" has no enum! Case?");
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

	public int[] returnHighest(int num) {
		//this.clear();
		int[] val = new int[3];
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
		else //We are not one of its write-to blocks
			return;
	}

	public void getPower(boolean doublesided, boolean vertical) {
		this.clear();
		if (!vertical)
			;//this.getIOSidesDefault(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata());
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", this.readx, this.ready, this.readz));
		TileEntity te = worldObj.getBlockTileEntity(readx, ready, readz);
		if (this.isProvider(te) && this.isIDTEMatch(worldObj, readx, ready, readz)) {
			MachineRegistry m = MachineRegistry.machineList[((RotaryCraftTileEntity)(te)).getMachineIndex()];
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)worldObj.getBlockTileEntity(readx, ready, readz);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 0);
					torquein = powerin[0][1];
					omegain = powerin[0][2];
				}
				if (devicein.writex == xCoord && devicein.writey == yCoord && devicein.writez == zCoord) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te instanceof TileEntityEngine) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (te instanceof TileEntity1DTransmitter) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (te instanceof TileEntityFlywheel) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (m == MachineRegistry.WINDER) {
				TileEntityWinder devicein = (TileEntityWinder)worldObj.getBlockTileEntity(readx, ready, readz);
				if (!devicein.winding)
					if ((devicein.writex == xCoord && devicein.writez == zCoord)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)worldObj.getBlockTileEntity(readx, ready, readz);
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 0);
					torquein = powerin[0][1];
					omegain = powerin[0][2];
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
			power = omega*torque;
			return;
		}
		torquein = 0;
		omegain = 0;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d  %d", powerin[0][0], powerin[0][1], powerin[0][2]));

		te = worldObj.getBlockTileEntity(readx2, ready2, readz2);
		if (this.isProvider(te) && this.isIDTEMatch(worldObj, readx2, ready2, readz2)) {
			MachineRegistry m = MachineRegistry.machineList[((RotaryCraftTileEntity)(te)).getMachineIndex()];
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)worldObj.getBlockTileEntity(readx2, ready2, readz2);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 1);
					torquein = powerin[1][1];
					omegain = powerin[1][2];
				}
				if (devicein.writex == xCoord && devicein.writey == yCoord && devicein.writez == zCoord) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te instanceof TileEntityEngine) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (te instanceof TileEntity1DTransmitter) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (te instanceof TileEntityFlywheel) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (m == MachineRegistry.WINDER) {
				TileEntityWinder devicein = (TileEntityWinder)worldObj.getBlockTileEntity(readx2, ready2, readz2);
				if (!devicein.winding)
					if ((devicein.writex == xCoord && devicein.writez == zCoord)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
			}
			if (m == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)worldObj.getBlockTileEntity(readx2, yCoord, readz2);
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 1);
					torquein = powerin[1][1];
					omegain = powerin[1][2];
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
		int[] powers = this.returnHighest(2);
		torque = powers[1];
		omega = powers[2];
		power = torque*omega;
	}

	public void getPower4Sided(int stepx, int stepy, int stepz) {
		readx = xCoord+1+stepx;
		readx2 = xCoord-1+stepx;
		readx3 = readx4 = xCoord+stepx;
		ready = ready2 = ready3 = ready4 = yCoord+stepy;
		readz = readz2 = zCoord+stepz;
		readz3 = zCoord+1+stepz;
		readz4 = zCoord-1+stepz;

		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d", this.readx, this.ready, this.readz));
		MachineRegistry id1 = MachineRegistry.getMachine(worldObj, readx, ready, readz);
		MachineRegistry id2 = MachineRegistry.getMachine(worldObj, readx2, ready2, readz2);
		MachineRegistry id3 = MachineRegistry.getMachine(worldObj, readx3, ready3, readz3);
		MachineRegistry id4 = MachineRegistry.getMachine(worldObj, readx4, ready4, readz4);
		TileEntity te1 = worldObj.getBlockTileEntity(readx, ready, readz);
		TileEntity te2 = worldObj.getBlockTileEntity(readx2, ready2, readz2);
		TileEntity te3 = worldObj.getBlockTileEntity(readx3, ready3, readz3);
		TileEntity te4 = worldObj.getBlockTileEntity(readx4, ready4, readz4);

		if (this.isProvider(te1) && this.isIDTEMatch(worldObj, readx, ready, readz)) {
			if (id1 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)worldObj.getBlockTileEntity(readx, ready, readz);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 0);
					torquein = powerin[0][1];
					omegain = powerin[0][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te1 instanceof TileEntityEngine) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (te1 instanceof TileEntity1DTransmitter) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (te1 instanceof TileEntityFlywheel) {
				this.copyStandardPower(worldObj, readx, ready, readz);
			}
			if (id1 == MachineRegistry.WINDER) {
				TileEntityWinder devicein = (TileEntityWinder)worldObj.getBlockTileEntity(readx, ready, readz);
				if (!devicein.winding)
					if ((devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
			}
			if (id1 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)worldObj.getBlockTileEntity(readx, ready, readz);
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 0);
					torquein = powerin[0][1];
					omegain = powerin[0][2];
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


		if (this.isProvider(te2) && this.isIDTEMatch(worldObj, readx2, ready2, readz2)) {
			if (id2 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)worldObj.getBlockTileEntity(readx2, ready2, readz2);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 1);
					torquein = powerin[1][1];
					omegain = powerin[1][2];
					// ReikaChatHelper.writeInt(torquein);
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te2 instanceof TileEntityEngine) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (te2 instanceof TileEntity1DTransmitter) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (te2 instanceof TileEntityFlywheel) {
				this.copyStandardPower(worldObj, readx2, ready2, readz2);
			}
			if (id2 == MachineRegistry.WINDER) {
				TileEntityWinder devicein = (TileEntityWinder)worldObj.getBlockTileEntity(readx2, ready2, readz2);
				if (!devicein.winding)
					if ((devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
			}
			if (id2 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)worldObj.getBlockTileEntity(readx2, ready2, readz2);
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 1);
					torquein = powerin[1][1];
					omegain = powerin[1][2];
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

		if (this.isProvider(te3) && this.isIDTEMatch(worldObj, readx3, ready3, readz3)) {
			if (id3 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)worldObj.getBlockTileEntity(readx3, ready3, readz3);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 2);
					torquein = powerin[2][1];
					omegain = powerin[2][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te3 instanceof TileEntityEngine) {
				this.copyStandardPower(worldObj, readx3, ready3, readz3);
			}
			if (te3 instanceof TileEntity1DTransmitter) {
				this.copyStandardPower(worldObj, readx3, ready3, readz3);
			}
			if (te3 instanceof TileEntityFlywheel) {
				this.copyStandardPower(worldObj, readx3, ready3, readz3);
			}
			if (id3 == MachineRegistry.WINDER) {
				TileEntityWinder devicein = (TileEntityWinder)worldObj.getBlockTileEntity(readx3, ready3, readz3);
				if (!devicein.winding)
					if ((devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
			}
			if (id3 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)worldObj.getBlockTileEntity(readx3, ready3, readz3);
				// ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(devicein));
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 2);
					torquein = powerin[2][1];
					omegain = powerin[2][2];
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

		if (this.isProvider(te4) && this.isIDTEMatch(worldObj, readx4, ready4, readz4)) {
			if (id4 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)worldObj.getBlockTileEntity(readx4, ready4, readz4);
				if (devicein.getBlockMetadata() >= 6) {
					this.readFromCross(devicein, 3);
					torquein = powerin[3][1];
					omegain = powerin[3][2];
				}
				else if (devicein.writex == xCoord+stepx && devicein.writey == yCoord+stepy && devicein.writez == zCoord+stepz) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
			if (te4 instanceof TileEntityEngine) {
				this.copyStandardPower(worldObj, readx4, ready4, readz4);
			}
			if (te4 instanceof TileEntity1DTransmitter) {
				this.copyStandardPower(worldObj, readx4, ready4, readz4);
			}
			if (te4 instanceof TileEntityFlywheel) {
				this.copyStandardPower(worldObj, readx4, ready4, readz4);
			}
			if (id4 == MachineRegistry.WINDER) {
				TileEntityWinder devicein = (TileEntityWinder)worldObj.getBlockTileEntity(readx4, ready4, readz4);
				if (!devicein.winding)
					if ((devicein.writex == xCoord+stepx && devicein.writez == zCoord+stepz)) {
						torquein = devicein.torque;
						omegain = devicein.omega;
					}
			}
			if (id4 == MachineRegistry.SPLITTER) {
				TileEntitySplitter devicein = (TileEntitySplitter)worldObj.getBlockTileEntity(readx4, ready4, readz4);
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(devicein));
				if (devicein.getBlockMetadata() >= 8) {
					this.readFromSplitter(devicein, 3);
					torquein = powerin[3][1];
					omegain = powerin[3][2];
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

		int[] powers = this.returnHighest(4);
		torque = powers[1];
		omega = powers[2];
		power = torque*omega;

	}

	public void getPowerBelow() {
		readx = xCoord;
		ready = yCoord-1;
		readz = zCoord;
		this.getPower(false, true);
	}

	public void getPowerAbove() {
		readx = xCoord;
		ready = yCoord+1;
		readz = zCoord;
		this.getPower(false, true);
	}

	public void getSummativeSidedPower() {
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int[][] powers = new int[2][6];
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
			this.getPower(false, false);
			powers[0][2] = omega;
			powers[1][2] = torque;
		}
		readx = x-1;
		if (this.getMachine().getMinX(this) == 0) {
			this.getPower(false, false);
			powers[0][3] = omega;
			powers[1][3] = torque;
		}
		readx = x;
		readz = z+1;
		if (this.getMachine().getMaxZ(this) == 1) {
			this.getPower(false, false);
			powers[0][4] = omega;
			powers[1][4] = torque;
		}
		readz = z-1;
		if (this.getMachine().getMinZ(this) == 0) {
			this.getPower(false, false);
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
			worldObj.spawnParticle("crit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), par5Random.nextFloat()/2F, par5Random.nextFloat(), par5Random.nextFloat()/2F);
			if (par5Random.nextInt(5) == 0)
				worldObj.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F, 1F);
			return;
		}
		int i = 0;
		while(powers[0][i] == 0 && i < 5) {
			i++;
		}
		omega = powers[0][i];
		torque = ReikaArrayHelper.sumArray(powers[1]);
		power = omega * torque;
	}

	public boolean operationComplete(int ticks, int stage) {
		MachineRegistry machine = MachineRegistry.getMachine(worldObj, xCoord, yCoord, zCoord);
		ticks++; // since tickcount starts at zero
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d %d", machine, this.xCoord, this.yCoord, this.zCoord));
		if (machine == MachineRegistry.BEDROCKBREAKER) {
			if (ticks < 2)
				return false;
			int time = 600-(int)(30*ReikaMathLibrary.logbase(omega, 2));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.BORER) {
			if (ticks < 2)
				return false;
			int time = 720-(int)(40*ReikaMathLibrary.logbase(omega, 2));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.PUMP) {
			if (ticks < 5)
				return false;
			int time = 4*(int)(50D/(1+ReikaMathLibrary.logbase(omega, 2)));/*
    		if (!this.worldObj.isRemote)
    			ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", ticks, time));*/
			return (ticks >= time);
		}
		if (machine == MachineRegistry.GRINDER) {
			if (ticks < 2)
				return false;
			int time = (3600-(int)(240*ReikaMathLibrary.logbase(omega, 2)))/4;
			return (ticks >= time);
		}
		if (machine == MachineRegistry.FRACTIONATOR) {
			if (ticks < 10)
				return false;
			int time = 2*(400-(int)(20*ReikaMathLibrary.logbase(omega, 2)));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.FERMENTER) {
			if (ticks < 2)
				return false;
			int time = 600-(int)(40*ReikaMathLibrary.logbase(omega, 2));
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d >= %d? ", ticks, time)+String.valueOf(ticks >= time));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.EXTRACTOR) {
			if (ticks < 2)
				return false;
			int time;
			switch (stage) {
			case 1:
				time = 30*(30-(int)(2*ReikaMathLibrary.logbase(omega, 2)));
				return (ticks >= time);
			case 2:
				time = (800-(int)(40*ReikaMathLibrary.logbase(omega, 2)))/2;
				return (ticks >= time);
			case 3:
				time = 600-(int)(30*ReikaMathLibrary.logbase(omega, 2));
				return (ticks >= time);
			case 4:
				time = 1200-(int)(80*ReikaMathLibrary.logbase(omega, 2));
				return (ticks >= time);
			default:
				return false;
			}
		}
		if (machine == MachineRegistry.COMPACTOR) {
			if (ticks < 2)
				return false;
			int time = 10*(60-(int)(3*ReikaMathLibrary.logbase(omega, 2)));
			time *= stage;
			return (ticks >= time);
		}
		if (machine == MachineRegistry.WOODCUTTER) {
			if (ticks < 2)
				return false;
			int time = 300-(int)(20*ReikaMathLibrary.logbase(omega, 2));
			//if (!this.worldObj.isRemote)
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", ticks, time));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.OBSIDIAN) {
			if (ticks < 2)
				return false;
			int time = 800-(int)(60*ReikaMathLibrary.logbase(omega, 2));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.HEATER) {
			if (ticks < 2)
				return false;
			int time = 200-(int)(10*ReikaMathLibrary.logbase(omega, 2));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.FIREWORK) {
			if (ticks < 2)
				return false;
			int time = 300-(int)(16*ReikaMathLibrary.logbase(omega, 2));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.WINDER) {
			if (ticks < 2)
				return false;
			int time = 300-(int)(16*ReikaMathLibrary.logbase(omega, 2));
			return (true);
		}
		if (machine == MachineRegistry.MAGNETIZER) {
			if (ticks < 2)
				return false;
			int time = (400-(int)(20*ReikaMathLibrary.logbase(omega, 2)));
			return (ticks >= time);
		}
		if (machine == MachineRegistry.PURIFIER) {
			if (ticks < 2)
				return false;
			int time = 2*(400-(int)(20*ReikaMathLibrary.logbase(omega, 2)));
			return (ticks >= time);
		}
		if (machine != null)
			ReikaChatHelper.write(String.format("Non-speed machine called operationComplete! ID: %d; Coords %d %d %d", machine, xCoord, yCoord, zCoord));
		return true; //should never happen
	}

	public int operationTime(int omegap, int stage) {
		MachineRegistry machine = MachineRegistry.getMachine(worldObj, xCoord, yCoord, zCoord);
		int time = -1;
		//ReikaChatHelper.writeInt(omegap);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d %d", machine, this.xCoord, this.yCoord, this.zCoord));
		if (machine == MachineRegistry.BEDROCKBREAKER) {
			time = 600-(int)(30*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.BORER) {
			time = 720-(int)(40*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.PUMP) {
			time = 4*(int)(50D/(1+ReikaMathLibrary.logbase(omegap, 2)));
		}
		if (machine == MachineRegistry.GRINDER) {
			time = (3600-(int)(240*ReikaMathLibrary.logbase(omegap, 2)))/4;
		}
		if (machine == MachineRegistry.FRACTIONATOR) {
			time = 2*(400-(int)(20*ReikaMathLibrary.logbase(omegap, 2)));
		}
		if (machine == MachineRegistry.FERMENTER) {
			time = 600-(int)(40*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.EXTRACTOR) {
			switch (stage) {
			case 1:
				time = 30*(30-(int)(2*ReikaMathLibrary.logbase(omegap, 2)));
				break;
			case 2:
				time = (800-(int)(40*ReikaMathLibrary.logbase(omegap, 2)))/2;
				break;
			case 3:
				time = 600-(int)(30*ReikaMathLibrary.logbase(omegap, 2));
				break;
			case 4:
				time = 1200-(int)(80*ReikaMathLibrary.logbase(omegap, 2));
				break;
			}
		}
		if (machine == MachineRegistry.COMPACTOR) {
			time = 10*(60-(int)(3*ReikaMathLibrary.logbase(omegap, 2)));
			time *= stage;
		}
		if (machine == MachineRegistry.WOODCUTTER) {
			time = 300-(int)(20*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.OBSIDIAN) {
			time = 800-(int)(60*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.HEATER) {
			time = 200-(int)(10*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.FIREWORK) {
			time = 300-(int)(16*ReikaMathLibrary.logbase(omegap, 2));
		}
		if (machine == MachineRegistry.WINDER) {
			time = 1-(int)(16*ReikaMathLibrary.logbase(1, 2));
		}
		if (machine == MachineRegistry.MAGNETIZER) {
			time = (400-(int)(20*ReikaMathLibrary.logbase(omega, 2)));
		}
		if (machine == MachineRegistry.PURIFIER) {
			time = 2*(400-(int)(20*ReikaMathLibrary.logbase(omega, 2)));
		}
		if (machine != null && time == -1) {
			ReikaChatHelper.write(String.format("Non-speed machine called operationTime! Coords %d %d %d", machine, xCoord, yCoord, zCoord));
			return -1; //should never happen
		}
		if (time == 0)
			return 1;
		return time;
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		double dist = ReikaMathLibrary.py3d(xCoord+0.5-var1.posX, yCoord+0.5-var1.posY, zCoord+0.5-var1.posZ);
		return (dist <= 8);
	}

	@Override
	public boolean canProvidePower() {
		return false;
	}
}
