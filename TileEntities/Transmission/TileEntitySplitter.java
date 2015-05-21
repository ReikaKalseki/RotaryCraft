/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.ChromatiCraft.API.WorldRift;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySplitter extends TileEntityTransmissionMachine implements GuiController, ShaftMerger, NBTMachine {

	//public int[] writeinline = new int[2]; //xz coords
	//public int[] writebend = new int[2]; //xz coords

	private int torquein2;
	private int omegain2;
	private int splitmode = 1;

	public boolean failed;
	private boolean bedrock;

	private int overloadTick = 0;

	private int pow2;

	public int getRatioFromMode() {
		return splitmode;
	}

	public void setMode(int mode) {
		splitmode = mode;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		if (failed) {
			omega = torque = 0;
		}
		else {
			this.getIOSides(world, x, y, z, meta);
			this.transferPower(world, x, y, z, meta, true, true);
		}
		power = (long)omega*(long)torque;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0://-z and +x -> -x
			read = ForgeDirection.EAST;
			read2 = ForgeDirection.NORTH;
			write = ForgeDirection.WEST;
			write2 = null;
			break;
		case 1: //+z and +x -> -z
			read = ForgeDirection.EAST;
			read2 = ForgeDirection.SOUTH;
			write = ForgeDirection.NORTH;
			write2 = null;
			break;
		case 2: //+z and -x -> +x
			read = ForgeDirection.SOUTH;
			read2 = ForgeDirection.WEST;
			write = ForgeDirection.EAST;
			write2 = null;
			break;
		case 3: //-z and -x -> +z
			read = ForgeDirection.NORTH;
			read2 = ForgeDirection.WEST;
			write = ForgeDirection.SOUTH;
			write2 = null;
			break;
		case 4://+z and +x -> -x
			read = ForgeDirection.EAST;
			read2 = ForgeDirection.SOUTH;
			write = ForgeDirection.WEST;
			write2 = null;
			break;
		case 5: //+z and -x -> -z
			read = ForgeDirection.WEST;
			read2 = ForgeDirection.SOUTH;
			write = ForgeDirection.NORTH;
			write2 = null;
			break;
		case 6: //-z and -x -> +x
			read = ForgeDirection.NORTH;
			read2 = ForgeDirection.WEST;
			write = ForgeDirection.EAST;
			write2 = null;
			break;
		case 7: //-z and +x -> +z
			read = ForgeDirection.NORTH;
			read2 = ForgeDirection.EAST;
			write = ForgeDirection.SOUTH;
			write2 = null;
			break;
			//---------------------------SPLITTER-----------------------------------
		case 8://-z and +x <- -x
			read2 = null;
			read = ForgeDirection.WEST;
			write = ForgeDirection.EAST;
			write2 = ForgeDirection.NORTH;
			break;
		case 9: //+z and +x <- -z
			read = ForgeDirection.NORTH;
			read2 = null;
			write = ForgeDirection.SOUTH;
			write2 = ForgeDirection.EAST;
			break;
		case 10: //+z and -x <- +x
			read = ForgeDirection.EAST;
			read2 = null;
			write = ForgeDirection.WEST;
			write2 = ForgeDirection.SOUTH;
			break;
		case 11: //-z and -x <- +z
			read = ForgeDirection.SOUTH;
			read2 = null;
			write = ForgeDirection.NORTH;
			write2 = ForgeDirection.WEST;
			break;
		case 12://+z and +x <- -x
			read = ForgeDirection.WEST;
			read2 = null;
			write = ForgeDirection.EAST;
			write2 = ForgeDirection.SOUTH;
			break;
		case 13: //+z and -x <- -z
			read = ForgeDirection.NORTH;
			read2 = null;
			write = ForgeDirection.SOUTH;
			write2 = ForgeDirection.WEST;
			break;
		case 14: //-z and -x <- +x
			read = ForgeDirection.EAST;
			read2 = null;
			write = ForgeDirection.WEST;
			write2 = ForgeDirection.NORTH;
			break;
		case 15: //-z and +x <- +z
			read = ForgeDirection.SOUTH;
			read2 = null;
			write = ForgeDirection.NORTH;
			write2 = ForgeDirection.EAST;
			break;
		}
		/*
		if (write != null) {
			writeinline[0] = xCoord+write.offsetX;
			writeinline[1] = zCoord+write.offsetZ;
		}
		else {
			writeinline[0] = Integer.MIN_VALUE;
			writeinline[1] = Integer.MIN_VALUE;
		}
		if (write2 != null) {
			writebend[0] = xCoord+write2.offsetX;
			writebend[1] = zCoord+write2.offsetZ;
		}
		else {
			writebend[0] = Integer.MIN_VALUE;
			writebend[1] = Integer.MIN_VALUE;
		}
		 */
		//ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, this.writex, this.yCoord, this.writez, 44);
		//ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, this.writex2, this.yCoord, this.writez2, 79);
		//ReikaWorldHelper.legacySetBlockWithNotify(this.worldObj, this.readx, this.yCoord, this.readz, 20);
	}

	protected void readFromCross(TileEntityShaft cross) {
		if (cross.isWritingTo(this)) {
			omega = cross.readomega[0];
			torque = cross.readtorque[0];
		}
		else if (cross.isWritingTo2(this)) {
			omega = cross.readomega[1];
			torque = cross.readtorque[1];
		}
		else
			return; //not its output
	}

	private void mergeReadFromCross(TileEntityShaft cross, int side) {
		if (cross.isWritingTo(this)) {
			if (side == 0) {
				omegain = cross.readomega[0];
				torquein = cross.readtorque[0];
			}
			if (side == 1) {
				omegain2 = cross.readomega[0];
				torquein2 = cross.readtorque[0];
			}
		}
		else if (cross.isWritingTo2(this)) {
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

	protected void transferPower(World world, int x, int y, int z, int meta, boolean check1, boolean check2) {
		if (worldObj.isRemote && !RotaryAux.getPowerOnClient)
			return;
		if (check1 && check2)
			omegain = torquein = 0;
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		if (!this.isSplitting()) {
			int dx = x+read.offsetX;
			int dy = y+read.offsetY;
			int dz = z+read.offsetZ;
			int dx2 = x+read2.offsetX;
			int dy2 = y+read2.offsetY;
			int dz2 = z+read2.offsetZ;
			MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
			TileEntity te = isCentered ? this.getAdjacentTileEntity(read) : world.getTileEntity(dx, dy, dz);
			MachineRegistry m2 = isCentered ? this.getMachine(read2) : MachineRegistry.getMachine(world, dx2, dy2, dz2);
			TileEntity te2 = isCentered ? this.getAdjacentTileEntity(read2) : world.getTileEntity(dx2, dy2, dz2);
			if (check1) {
				if (this.isProvider(te)) {
					if (m == MachineRegistry.SHAFT) {
						TileEntityShaft devicein = (TileEntityShaft)te;
						if (devicein.isCross()) {
							this.mergeReadFromCross(devicein, 0);
							//  return;
						}
						else if (devicein.isWritingTo(this)) {
							torquein = devicein.torque;
							omegain = devicein.omega;
						}
					}
					if (te instanceof SimpleProvider) {
						TileEntityIOMachine devicein = (TileEntityIOMachine)te;
						if (devicein.isWritingTo(this)) {
							torquein = devicein.torque;
							omegain = devicein.omega;
						}
						else if (devicein.isWritingTo2(this)) {
							torquein = devicein.torque;
							omegain = devicein.omega;
						}
						else
							torquein = omegain = 0;
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
						else
							torquein = omegain = 0;
					}
					if (m == MachineRegistry.SPLITTER) {
						TileEntitySplitter devicein = (TileEntitySplitter)te;
						if (devicein.isSplitting()) {
							this.readFromSplitter(world, x, y, z, devicein);
						}
						else if (devicein.isWritingTo(this)) {
							torquein = devicein.torque;
							omegain = devicein.omega;
						}
					}
				}
				else if (te instanceof WorldRift) {
					WorldRift sr = (WorldRift)te;
					WorldLocation loc = sr.getLinkTarget();
					if (loc != null)
						this.transferPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta, true, false);
				}
				else {
					omegain = 0;
					torquein = 0;
				}
			}
			if (check2) {
				if (this.isProvider(te2)) {
					if (m2 == MachineRegistry.SHAFT) {
						TileEntityShaft devicein2 = (TileEntityShaft)te2;
						if (devicein2.isCross()) {
							this.mergeReadFromCross(devicein2, 1);
							// ReikaChatHelper.writeInt(this.omegain2);
							// return;
						}
						else if (devicein2.isWritingTo(this)) {
							torquein2 = devicein2.torque;
							omegain2 = devicein2.omega;
						}
					}
					if (te2 instanceof SimpleProvider) {
						TileEntityIOMachine devicein = (TileEntityIOMachine)te2;
						if (devicein.isWritingTo(this)) {
							torquein2 = devicein.torque;
							omegain2 = devicein.omega;
						}
						else
							torquein2 = omegain2 = 0;
					}
					if (m2 == MachineRegistry.POWERBUS) {
						TileEntityPowerBus pwr = (TileEntityPowerBus)te2;
						ForgeDirection dir = this.getInputForgeDirection().getOpposite();
						omegain2 = pwr.getSpeedToSide(dir);
						torquein2 = pwr.getTorqueToSide(dir);
					}
					if (te2 instanceof ShaftPowerEmitter) {
						ShaftPowerEmitter sp = (ShaftPowerEmitter)te2;
						if (sp.isEmitting() && sp.canWriteTo(read2.getOpposite())) {
							torquein2 = sp.getTorque();
							omegain2 = sp.getOmega();
						}
						else
							torquein2 = omegain2 = 0;
					}
					if (m2 == MachineRegistry.SPLITTER) {
						TileEntitySplitter devicein2 = (TileEntitySplitter)te2;
						if (devicein2.isSplitting()) {
							this.readFromSplitter(world, x, y, z, devicein2);
						}
						else if (devicein2.isWritingTo(this)) {
							torquein2 = devicein2.torque;
							omegain2 = devicein2.omega;
						}
					}
				}
				else if (te2 instanceof WorldRift) {
					WorldRift sr = (WorldRift)te2;
					WorldLocation loc = sr.getLinkTarget();
					if (loc != null)
						this.transferPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta, false, true);
				}
				else {
					omegain2 = 0;
					torquein2 = 0;
				}
			}
			if (!check1 || !check2)
				return;

			PowerSourceList in1 = null;
			PowerSourceList in2 = null;
			if (read != null && read2 != null) {
				//ReikaJavaLibrary.pConsole("====================", Side.SERVER, xCoord == -1011);
				in1 = PowerSourceList.getAllFrom(world, read, x+read.offsetX, y+read.offsetY, z+read.offsetZ, this, this);
				//ReikaJavaLibrary.pConsole("--------------", Side.SERVER, xCoord == -1011);
				in2 = PowerSourceList.getAllFrom(world, read2, x+read2.offsetX, y+read2.offsetY, z+read2.offsetZ, this, this);
				//ReikaJavaLibrary.pConsole("==================", Side.SERVER, xCoord == -1011);

				if (this.isLoopingPower(in1, in2)) {
					omega = torque = 0;
					power = 0;
					this.fail();
				}
			}

			boolean overload = false;
			if (!this.canCombine(in1, in2, torquein, torquein2)) {
				overloadTick++;
				overload = true;
				if (overloadTick > 20) {
					this.fail();
					overloadTick = 0;
				}
				else {
					world.spawnParticle("crit", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), -0.5+rand.nextFloat(), rand.nextFloat(), -0.5+rand.nextFloat());
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
				}
			}
			else if (omegain == omegain2) {
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
			}
			if (!overload)
				overloadTick = 0;
			this.basicPowerReceiver();
		}
		else {
			//--------------------------------------------------------------------------------------------
			//------################-------Splitter mode (dmg >= 8)-------##########################------
			//--------------------------------------------------------------------------------------------
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
						//ReikaChatHelper.writeInt(this.torque);
						return;
					}
					if (devicein.isWritingTo(this)) {
						torque = devicein.torque;
						omega = devicein.omega;
					}
					else
						torque = omega = 0;
				}
				if (te instanceof SimpleProvider) {
					TileEntityIOMachine devicein = (TileEntityIOMachine)te;
					if (devicein.isWritingTo(this)) {
						torque = devicein.torque;
						omega = devicein.omega;
					}
					else
						torque = omega = 0;
				}
				if (te instanceof ShaftPowerEmitter) {
					ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
					if (sp.isEmitting() && sp.canWriteTo(read.getOpposite())) {
						torque = sp.getTorque();
						omega = sp.getOmega();
					}
					else
						torque = omega = 0;
				}

				if (m == MachineRegistry.POWERBUS) {
					TileEntityPowerBus pwr = (TileEntityPowerBus)te;
					ForgeDirection dir = this.getInputForgeDirection().getOpposite();
					omegain = pwr.getSpeedToSide(dir);
					torquein = pwr.getTorqueToSide(dir);
				}
				if (m == MachineRegistry.SPLITTER) {
					TileEntitySplitter devicein = (TileEntitySplitter)te;
					if (devicein.isSplitting()) {
						this.readFromSplitter(world, x, y, z, devicein);
						torque = torquein;
						omega = omegain;
					}
					else if (devicein.isWritingTo(this)) {
						torque = devicein.torque;
						omega = devicein.omega;
					}
					else
						torque = omega = 0;
				}
			}
			else if (te instanceof WorldRift) {
				WorldRift sr = (WorldRift)te;
				WorldLocation loc = sr.getLinkTarget();
				if (loc != null)
					this.transferPower(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, meta, false, false);
			}
			else {
				omega = 0;
				torque = 0;
			}
			power = (long)torque*(long)omega;
			this.writeToReceiver();
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d * %d = %d", this.omega, this.torque, this.power));
		}
	}

	private boolean canCombine(PowerSourceList in1, PowerSourceList in2, int t1, int t2) {
		PowerSourceList combo = PowerSourceList.combine(in1, in2, this);
		if (combo.isLooping())
			return false;
		if (t1 == 0 || t2 == 0)
			return true;
		return bedrock || !combo.isEngineSpam();
	}

	private void fail() {
		worldObj.createExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 4, false);
		failed = true;
	}

	public boolean isSplitting() {
		return this.getBlockMetadata() >= 8;
	}

	public boolean isBedrock() {
		return bedrock;
	}

	private boolean isLoopingPower(PowerSourceList in1, PowerSourceList in2) {
		/*
		if (torquein*omegain != 0 && in1.getRealMaxPower() == 0) {
			omegain = omegain2;
			torquein = torquein2;
			return true;
		}
		if (torquein2*omegain2 != 0 && in2.getRealMaxPower() == 0) {
			omegain2 = omegain;
			torquein2 = torquein;
			return true;

		}*/
		int st = torquein+torquein2;
		int so = omegain+omegain2;
		//ReikaJavaLibrary.pConsole(in1, Side.SERVER, xCoord == -1011);
		return st > 0 && so > 0 && (in1.passesThrough(this) || in2.passesThrough(this));
	}

	private void writeToReceiver() {
		int t1 = 0;
		int t2 = 0;
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
		}
		else if (favorbent) {
			t1 = torque/ratio;
		}
		else {
			t1 = (int)(torque*((ratio-1D)/(ratio)));
		}
		if (ratio == 1) { //Even split, favorbent irrelevant
			t2 = torque/2;
		}
		else if (favorbent) {
			t2 = (int)(torque*((ratio-1D)/(ratio)));
		}
		else {
			t2 = torque/ratio;
		}
		this.writeToPowerReceiver(write, omega, t1);
		this.writeToPowerReceiver(write2, omega, t2);
	}

	@Override
	protected void readFromSplitter(World world, int x, int y, int z, TileEntitySplitter spl) { //Complex enough to deserve its own function
		omegain = spl.omega; //omegain always constant
		int ratio = spl.getRatioFromMode();
		if (ratio == 0)
			return;
		boolean favorbent = false;
		if (ratio < 0) {
			favorbent = true;
			ratio = -ratio;
		}
		if (xCoord == x+spl.getWriteDirection().offsetX && zCoord == spl.getWriteDirection().offsetZ) { //We are the inline
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
		else if (xCoord == x+spl.getWriteDirection2().offsetX && zCoord == spl.getWriteDirection2().offsetZ) { //We are the bend
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

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("mode", splitmode);
		NBT.setBoolean("fail", failed);
		NBT.setBoolean("bedrock", bedrock);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		splitmode = NBT.getInteger("mode");
		failed = NBT.getBoolean("fail");
		bedrock = NBT.getBoolean("bedrock");
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
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();
		if (caller == null)
			caller = this;
		if (!this.isSplitting()) { //merge
			if (read != null && read2 != null) {
				PowerSourceList in1 = pwr.getAllFrom(worldObj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller);
				PowerSourceList in2 = pwr.getAllFrom(worldObj, read2, xCoord+read2.offsetX, yCoord+read2.offsetY, zCoord+read2.offsetZ, this, caller);
				pwr.addAll(in1);
				pwr.addAll(in2);
			}
			return pwr;
		}
		else {
			return PowerSourceList.getAllFrom(worldObj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller);
		}
	}

	public String getRatioForDisplay() {
		switch(splitmode) {
		case 32:
			return "31:1 Inline";
		case -32:
			return "1:31 Bend";
		case 16:
			return "15:1 Inline";
		case -16:
			return "1:15 Bend";
		case 8:
			return "7:1 Inline";
		case -8:
			return "1:7 Bend";
		case 4:
			return "3:1 Inline";
		case -4:
			return "1:3 Bend";
		case 1:
			return "1:1 Even";
		default:
			return "ERROR";
		}
	}

	@Override
	public int getWriteX() {
		return write != null ? xCoord+write.offsetX : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteY() {
		return yCoord;
	}

	@Override
	public int getWriteZ() {
		return write != null ? zCoord+write.offsetZ : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteX2() {
		return write2 != null ? xCoord+write2.offsetX : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteY2() {
		return yCoord;
	}

	@Override
	public int getWriteZ2() {
		return write2 != null ? zCoord+write2.offsetZ : Integer.MIN_VALUE;
	}

	@Override
	public NBTTagCompound getTagsToWriteToStack() {
		NBTTagCompound NBT = new NBTTagCompound();
		NBT.setBoolean("bedrock", bedrock);
		return NBT;
	}

	@Override
	public void setDataFromItemStackTag(NBTTagCompound NBT) {
		bedrock = NBT != null && NBT.getBoolean("bedrock");
	}

	@Override
	public ArrayList<NBTTagCompound> getCreativeModeVariants() {
		ArrayList<NBTTagCompound> li = new ArrayList();
		li.add(new NBTTagCompound());
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("bedrock", true);
		li.add(nbt);
		return li;
	}

	@Override
	public ArrayList<String> getDisplayTags(NBTTagCompound NBT) {
		ArrayList<String> li = new ArrayList();
		li.add(NBT != null && NBT.getBoolean("bedrock") ? "Bedrock" : "Steel");
		return li;
	}

	public void setBedrock() {
		bedrock = true;
	}

	@Override
	public void onPowerLooped(PowerSourceList pwr) {
		if (power > 0)
			this.fail();
	}

	@Override
	public final void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		c.add(this.getAdjacentTileEntity(write));
		if (this.isSplitting()) {
			c.add(this.getAdjacentTileEntity(write2));
		}
	}
}
