/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.ImmutablePair;

import Reika.ChromatiCraft.API.Interfaces.WorldRift;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Interfaces.TileEntity.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Power.PowerAcceptor;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

import com.google.common.collect.HashBiMap;

public class TileEntityBevelGear extends TileEntity1DTransmitter implements GuiController {

	private static final HashBiMap<Integer, ImmutablePair<ForgeDirection, ForgeDirection>> directions = HashBiMap.create();

	static {
		/*
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			for (int k = 0; k < 6; k++) {
				ForgeDirection dir2 = ForgeDirection.VALID_DIRECTIONS[k];
				if (dir != dir2 && dir != dir2.getOpposite()) {
					registerDirection(dir, dir2);
				}
			}
		}
		 */
		for (int i = 0; i < 24; i++) { //do it this way to preserve ordering
			registerDirectionValue(i);
		}
	}

	private static void registerDirectionValue(int value) {
		ForgeDirection read = null;
		ForgeDirection write = null;
		switch(value) {
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
		registerDirection(read, write);
	}

	private static void registerDirection(ForgeDirection read, ForgeDirection write) {
		int idx = directions.size();
		directions.put(idx, new ImmutablePair(read, write));
		RotaryCraft.logger.log("Registered bevel direction #"+idx+": "+read+" to "+write);
	}

	public static HashBiMap<Integer, ImmutablePair<ForgeDirection, ForgeDirection>> getDirectionMap() {
		return HashBiMap.create(directions);
	}

	public static boolean isValid(ForgeDirection read, ForgeDirection write) {
		return directions.inverse().containsKey(new ImmutablePair(read, write));
	}

	public int direction;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		power = (long)omega*(long)torque;
		this.getIOSides(world, x, y, z);
		this.transferPower(world, x, y, z, meta);

		this.basicPowerReceiver();
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		ForgeDirection write = null;
		ForgeDirection read = null;
		for (int i = 0; i < 6; i++) {
			TileEntity te = this.getAdjacentTileEntity(dirs[i]);
			if (te instanceof TileEntityIOMachine) {
				TileEntityIOMachine io = (TileEntityIOMachine)te;
				if (write == null) {
					if (io.isWritingToCoordinate(x, y, z) || io.isWritingToCoordinate2(x, y, z)) {
						write = dirs[i];
					}
				}
				if (read == null) {
					if (io.isReadingFrom(this) || io.isReadingFrom2(this) || io.isReadingFrom3(this) || io.isReadingFrom4(this)) {
						read = dirs[i];
					}
				}
			}
			else if (te instanceof PowerAcceptor) {
				if (read == null) {
					if (((PowerAcceptor)te).canReadFrom(dirs[i].getOpposite())) {
						read = dirs[i];
					}
				}
			}
		}
		if (write != null && read != null && read != write) {
			direction = directions.inverse().get(new ImmutablePair(read, write));
		}
	}

	public void getIOSides(World world, int x, int y, int z) {
		ImmutablePair<ForgeDirection, ForgeDirection> dirs = directions.get(direction);
		read = dirs.left;
		write = dirs.right;
	}

	@Override
	protected void transferPower(World world, int x, int y, int z, int meta) {
		if (worldObj.isRemote && !RotaryAux.getPowerOnClient)
			return;
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

		omega = omegain;
		torque = torquein;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setInteger("posn", direction);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		direction = NBT.getInteger("posn");
		//Try to preserve the directionality after the system was changed:
		if (!NBT.hasKey("posn"))
			direction = directions.inverse().get(new ImmutablePair(read, write));
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
