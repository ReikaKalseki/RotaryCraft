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

import Reika.DragonAPI.APIPacketHandler.PacketIDs;
import Reika.DragonAPI.DragonAPIInit;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModInteract.ReikaMystcraftHelper;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;

import com.xcompwiz.mystcraft.api.MystObjects;

public class TileEntityPortalShaft extends TileEntity1DTransmitter {

	private PortalType type;
	public MaterialRegistry material;

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
		if (ModList.MYSTCRAFT.isLoaded() && MystObjects.portal != null && MystObjects.portal == id)
			type = PortalType.MYSTCRAFT;
		if (ModList.TWILIGHT.isLoaded() && id == TwilightForestHandler.getInstance().portalID)
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

	private int[] getScaledCoordinates(int x, int y, int z, World source, World target) {
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
		return new int[]{x, y, z};
	}

	private void emitPower(World world, int x, int y, int z) {
		//use dimensionmanager to set power
		int dim = this.getTargetDimID();
		//ReikaJavaLibrary.pConsole(writex+":"+writey+":"+writez, Side.SERVER);
		//ReikaJavaLibrary.pConsole(dim, Side.SERVER);
		World age = DimensionManager.getWorld(dim);
		int[] coords = this.getScaledCoordinates(x+write.offsetX, y+write.offsetY, z+write.offsetZ, world, age);
		int ax = coords[0];
		int ay = coords[1];
		int az = coords[2];
		if (age != null && age.checkChunksExist(ax, ay, az, ax, ay, az)) {
			int tg = this.getTargetDimensionBy(age, ax, ay, az);
			//ReikaJavaLibrary.pConsole(write+": "+tg+": "+ax+","+ay+","+az, Side.SERVER);
			//ReikaJavaLibrary.pConsole(tg, dim == 7);
			if (tg == world.provider.dimensionId) {
				//ReikaJavaLibrary.pConsole(writex+", "+writey+", "+writez+" >> "+Blocks.blocksList[id], Side.SERVER);
				int[] c2 = this.getScaledCoordinates(x+write.offsetX*2, y+write.offsetY*2, z+write.offsetZ*2, world, age);
				int dx = c2[0];
				int dy = c2[1];
				int dz = c2[2];
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
						int tmeta = MachineRegistry.PORTALSHAFT.getMachineMetadata();
						//ReikaJavaLibrary.pConsole(tid+":"+tmeta);
						age.setBlockToAir(dx, dy, dz);
						age.setBlock(dx, dy, dz, tid, tmeta, 3);
						TileEntityPortalShaft ps = (TileEntityPortalShaft)age.getTileEntity(dx, dy, dz);//new TileEntityPortalShaft();
						ps.read = te.getReadDirection();
						ps.setBlockMetadata(te.getBlockMetadata());
						ps.setPortalType(age, ax, ay, az);
						ps.material = material;
						ReikaPacketHelper.sendUpdatePacket(DragonAPIInit.packetChannel, PacketIDs.TILEDELETE.ordinal(), ps);
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
		}
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
					this.readFromSplitter(devicein);
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

}