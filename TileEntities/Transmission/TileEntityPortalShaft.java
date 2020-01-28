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

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.APIPacketHandler.PacketIDs;
import Reika.DragonAPI.DragonAPIInit;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.ChunkManager;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Interfaces.TileEntity.ChunkLoadingTile;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaMystcraftHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.MystCraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.TwilightForestHandler;
import Reika.RotaryCraft.API.Interfaces.ComplexIO;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

public class TileEntityPortalShaft extends TileEntity1DTransmitter implements ChunkLoadingTile {

	private PortalType type;
	public MaterialRegistry material;

	private WorldLocation otherShaft;

	public static enum PortalType {
		NETHER(),
		END(),
		TWILIGHT(),
		MYSTCRAFT();
	}

	public int getCurrentDimID() {
		return worldObj.provider.dimensionId;
	}

	public int getTargetDimID() {
		int id = this.getCurrentDimID();
		switch(type) {
			case END:
				return id == 0 ? 1 : 0;
			case MYSTCRAFT: //portal has a book slot?
				return this.getMystCraftTarget();
			case NETHER:
				return id == 0 ? -1 : 0;
			case TWILIGHT:
				return id == 0 ? TwilightForestHandler.getInstance().dimensionID : 0;
			default:
				return id;
		}
	}

	private int getMystCraftTarget() {
		int x = xCoord+write.offsetX;
		int y = yCoord+write.offsetY;
		int z = zCoord+write.offsetZ;
		return ReikaMystcraftHelper.getTargetDimensionIDFromPortalBlock(worldObj, x, y, z);
	}

	public void setPortalType(World world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		if (id == Blocks.portal)
			type = PortalType.NETHER;
		if (id == Blocks.end_portal)
			type = PortalType.END;
		if (ModList.MYSTCRAFT.isLoaded() && id == MystCraftHandler.getInstance().portalID)
			type = PortalType.MYSTCRAFT;
		if (ModList.TWILIGHT.isLoaded() && id == TwilightForestHandler.BlockEntry.PORTAL.getBlock())
			type = PortalType.TWILIGHT;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		if (ReikaBlockHelper.isPortalBlock(world, x+write.offsetX, y+write.offsetY, z+write.offsetZ)) {
			this.transferPower(world, x, y, z, meta);
			this.emitPower(world, x, y, z);
		}

		if (!world.isRemote && otherShaft != null) {
			if (otherShaft.getTileEntity() instanceof TileEntityPortalShaft) {

			}
			else {
				power = 0;

				world.setBlock(x, y, z, MachineRegistry.SHAFT.getBlock(), MachineRegistry.SHAFT.getBlockMetadata(), 3);
				TileEntityShaft ts = new TileEntityShaft(this.getShaftType());
				ts.setBlockMetadata(this.getBlockMetadata());
				world.setTileEntity(x, y, z, ts);
			}
		}
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		super.onFirstTick(world, x, y, z);

		ChunkManager.instance.loadChunks(this);
	}

	private int getTargetDimensionBy(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		int id = world.provider.dimensionId;
		//ReikaJavaLibrary.pConsole(id+":"+block+" @ "+x+", "+y+", "+z, Side.SERVER);
		//ReikaJavaLibrary.pConsole(id+":"+block+" @ "+x+", "+y+", "+z, id == 7);
		switch(type) {
			case END:
				return block == Blocks.end_portal ? id == 0 ? 1 : 0 : Integer.MIN_VALUE;
			case MYSTCRAFT: //portal has a book slot?
				return ReikaMystcraftHelper.getTargetDimensionIDFromPortalBlock(world, x, y, z);
			case NETHER:
				return block == Blocks.portal ? id == 0 ? -1 : 0 : Integer.MIN_VALUE;
			case TWILIGHT:
				return id == 0 ? TwilightForestHandler.getInstance().dimensionID : 0;
			default:
				return id;
		}
	}

	private Coordinate getScaledCoordinates(int x, int y, int z, World source, World target) {
		if (source == null || target == null)
			return null;
		int tg = target.provider.dimensionId;
		int src = source.provider.dimensionId;
		if (src != -1 && tg == -1) { //to nether
			x = x/8;
			z = z/8;
		}
		if (src == -1 && tg != -1) { //from nether
			x *= 8;
			z *= 8;
		}
		//ReikaJavaLibrary.pConsole(src+">"+tg+" @ "+x+","+y+","+z, Side.SERVER);
		return new Coordinate(x, y, z);
	}

	private void emitPower(World world, int x, int y, int z) {
		Location c = this.getOutputLocation(world, x, y, z);
		if (c == null)
			return;
		int dx = c.posX;
		int dy = c.posY;
		int dz = c.posZ;
		int ax = c.posXA;
		int ay = c.posYA;
		int az = c.posZA;
		World age = c.world;
		MachineRegistry m = MachineRegistry.getMachine(age, dx, dy, dz);
		//ReikaJavaLibrary.pConsole(x+", "+y+", "+z+":"+dx+", "+dy+", "+dz+" >> "+age.getBlock(dx, dy, dz), Side.SERVER);
		//ReikaJavaLibrary.pConsole(x+", "+y+", "+z+":"+dx+", "+dy+", "+dz+" >> "+m, Side.SERVER);
		//ReikaJavaLibrary.pConsole(dx+", "+dy+", "+dz+" >> "+m, Side.SERVER);
		//ReikaJavaLibrary.pConsole(dx+", "+dy+", "+dz+" >> "+m, dim == 7);
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft te = (TileEntityShaft)age.getTileEntity(dx, dy, dz);
			int terx = te.xCoord+te.getReadDirection().offsetX;
			int tery = te.yCoord+te.getReadDirection().offsetY;
			int terz = te.zCoord+te.getReadDirection().offsetZ;
			//ReikaJavaLibrary.pConsole(terx+","+tery+","+terz, Side.SERVER);
			if (terx == ax && tery == ay && terz == az) {
				Block tid = MachineRegistry.PORTALSHAFT.getBlock();
				int tmeta = MachineRegistry.PORTALSHAFT.getBlockMetadata();
				//ReikaJavaLibrary.pConsole(tid+":"+tmeta);
				age.setBlockToAir(dx, dy, dz);
				age.setBlock(dx, dy, dz, tid, tmeta, 3);
				TileEntityPortalShaft ps = (TileEntityPortalShaft)age.getTileEntity(dx, dy, dz);//new TileEntityPortalShaft();
				ps.read = te.getReadDirection();
				ps.setBlockMetadata(te.getBlockMetadata());
				ps.setPortalType(age, ax, ay, az);
				ps.material = material;
				ps.otherShaft = new WorldLocation(this);
				otherShaft = new WorldLocation(age, dx, dy, dz);
				ReikaPacketHelper.sendUpdatePacket(DragonAPIInit.packetChannel, PacketIDs.TILEDELETE.ordinal(), ps, new PacketTarget.RadiusTarget(this, 32));
			}
		}
		else if (m == MachineRegistry.PORTALSHAFT) {
			TileEntityPortalShaft te = (TileEntityPortalShaft)age.getTileEntity(dx, dy, dz);
			int terx = te.xCoord+te.getReadDirection().offsetX;
			int tery = te.yCoord+te.getReadDirection().offsetY;
			int terz = te.zCoord+te.getReadDirection().offsetZ;
			if (terx == ax && tery == ay && terz == az) {
				te.power = power;
				te.omega = omega;
				te.torque = torque;
			}
		}
	}

	private Location getOutputLocation(World world, int x, int y, int z) {
		//use dimensionmanager to set power
		int dim = this.getTargetDimID();
		//ReikaJavaLibrary.pConsole(writex+":"+writey+":"+writez, Side.SERVER);
		//ReikaJavaLibrary.pConsole(dim, Side.SERVER);
		World age = DimensionManager.getWorld(dim);
		//ReikaJavaLibrary.pConsole(age);
		Coordinate loc = this.getScaledCoordinates(x+write.offsetX, y+write.offsetY, z+write.offsetZ, world, age);
		if (loc == null)
			return null;
		int ax = loc.xCoord;
		int ay = loc.yCoord;
		int az = loc.zCoord;
		if (age != null/* && age.checkChunksExist(ax, ay, az, ax, ay, az)*/) {
			int tg = this.getTargetDimensionBy(age, ax, ay, az);
			//ReikaJavaLibrary.pConsole(write+": "+tg+": "+ax+","+ay+","+az, Side.SERVER);
			//ReikaJavaLibrary.pConsole(tg, dim == 7);
			if (tg == world.provider.dimensionId) {
				//ReikaJavaLibrary.pConsole(writex+", "+writey+", "+writez+" >> "+Blocks.blocksList[id], Side.SERVER);
				Coordinate c2 = this.getScaledCoordinates(x, y, z, world, age);
				return new Location(age, loc.xCoord+write.offsetX, loc.yCoord+write.offsetY, loc.zCoord+write.offsetZ, ax, ay, az);
			}
		}
		return null;
	}

	public void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
			case 0:
				read = ForgeDirection.EAST;
				write = read.getOpposite();
				break;
			case 1:
				read = ForgeDirection.WEST;
				write = read.getOpposite();
				break;
			case 2:
				read = ForgeDirection.SOUTH;
				write = read.getOpposite();
				break;
			case 3:
				read = ForgeDirection.NORTH;
				write = read.getOpposite();
				break;
			case 4:	//moving up
				read = ForgeDirection.DOWN;
				write = read.getOpposite();
				break;
			case 5:	//moving down
				read = ForgeDirection.UP;
				write = read.getOpposite();
				break;
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.25);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PORTALSHAFT;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	protected void transferPower(World world, int x, int y, int z, int meta) {
		omegain = torquein = 0;
		MachineRegistry m = this.getMachine(read);
		TileEntity te = this.getAdjacentTileEntity(read);
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
			if (m.isComplexIO()) {
				ComplexIO pwr = (ComplexIO)te;
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
		else {
			omega = torque = 0;
		}
		power = (long)torque*(long)omega;
	}

	@Override
	public void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		type = PortalType.values()[NBT.getInteger("portal")];

		material = MaterialRegistry.setType(NBT.getInteger("mat"));
	}

	@Override
	public void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		if (type != null)
			NBT.setInteger("portal", type.ordinal());
		if (material != null)
			NBT.setInteger("mat", material.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
	}

	public MaterialRegistry getShaftType() {
		if (this.isInWorld() && material != null) {
			return material;
		}
		return MaterialRegistry.STEEL;
	}

	public boolean isEnteringPortal() {
		if (write == null)
			return false;
		return ReikaBlockHelper.isPortalBlock(worldObj, xCoord+write.offsetX, yCoord+write.offsetY, zCoord+write.offsetZ);
	}

	public boolean isExitingPortal() {
		if (read == null)
			return false;
		return ReikaBlockHelper.isPortalBlock(worldObj, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ);
	}

	@Override
	public void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		Location loc = this.getOutputLocation(worldObj, xCoord, yCoord, zCoord);
		c.add(loc.world.getTileEntity(loc.posX, loc.posY, loc.posZ));
	}

	private static class Location {

		private final World world;
		private final int posX;
		private final int posY;
		private final int posZ;
		private final int posXA;
		private final int posYA;
		private final int posZA;

		private Location(World world, int x, int y, int z, int xa, int ya, int za) {
			this.world = world;
			posX = x;
			posY = y;
			posZ = z;
			posXA = xa;
			posYA = ya;
			posZA = za;
		}

	}

	@Override
	public void breakBlock() {
		ChunkManager.instance.unloadChunks(this);
	}

	@Override
	public Collection<ChunkCoordIntPair> getChunksToLoad() {
		return ChunkManager.getChunkSquare(xCoord, zCoord, 1);
	}

}
