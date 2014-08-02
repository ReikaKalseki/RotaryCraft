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

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import Reika.ChromatiCraft.API.SpaceRift;
import Reika.DragonAPI.Instantiable.WorldLocation;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.API.Event.ShaftFailureEvent;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntity1DTransmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityShaft extends TileEntity1DTransmitter {
	public int[] readtorque = new int[2];
	public int[] readomega = new int[2];
	public boolean reading2Dir = false; //Is reading a 2-direction block (splitter, cross)

	public float crossphi1 = 0;
	public float crossphi2 = 0;

	private MaterialRegistry type;
	private boolean failed = false;

	public TileEntityShaft(MaterialRegistry type) {
		if (type == null)
			type = MaterialRegistry.WOOD;
		this.type = type;
	}

	public TileEntityShaft() {
		this(MaterialRegistry.WOOD);
	}

	public MaterialRegistry getShaftType() {
		return type;
	}

	public void fail(World world, int x, int y, int z, boolean speed) {
		MinecraftForge.EVENT_BUS.post(new ShaftFailureEvent(this, speed, type));
		failed = true;
		if (speed) {
			world.createExplosion(null, x+0.5, y+0.5, z+0.5, 1F, true);
			ItemStack item = null;
			switch(type) {
			case WOOD:
				item = new ItemStack(ItemStacks.sawdust.itemID, 1, ItemStacks.sawdust.getItemDamage());
				break;
			case STONE:
				item = new ItemStack(Block.gravel, 1, 0);
				break;
			case STEEL:
				item = new ItemStack(ItemStacks.scrap.itemID, 1, ItemStacks.scrap.getItemDamage());
				break;
			case DIAMOND:
				item = new ItemStack(Item.diamond, 1, 0);
				break;
			case BEDROCK:
				item = new ItemStack(ItemStacks.bedrockdust.itemID, 1, ItemStacks.bedrockdust.getItemDamage());
				break;
			}
			EntityItem ei = new EntityItem(world, x+0.5, y+1.25, z+0.5, item);
			ei.motionY = 0.4F+0.6F*rand.nextFloat();
			ei.motionX = rand.nextFloat()/5;
			ei.motionZ = rand.nextFloat()/5;
			if (world.isRemote)
				return;
			ei.velocityChanged = true;
			if (rand.nextInt(24) == 0)
				world.spawnEntityInWorld(ei);
		}
		else {
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.break", 1F, 1F);
			ItemStack item = null;
			switch(type) {
			case WOOD:
				item = new ItemStack(Item.stick, 1, 0);
				break;
			case STONE:
				item = new ItemStack(Block.cobblestone, 1, 0);
				break;
			case STEEL:
				item = new ItemStack(ItemStacks.shaftitem.itemID, 1, ItemStacks.shaftitem.getItemDamage());
				break;
			case DIAMOND:
				item = new ItemStack(Item.diamond, 1, 0);
				break;
			case BEDROCK:
				item = new ItemStack(ItemStacks.bedrockdust.itemID, 1, ItemStacks.bedrockdust.getItemDamage());
				break;
			}
			EntityItem ei = new EntityItem(world, x+0.5, y+1, z+0.5, item);
			ei.motionY = rand.nextFloat()/5;
			ei.motionX = rand.nextFloat()/5;
			ei.motionZ = rand.nextFloat()/5;
			if (world.isRemote)
				return;
			ei.velocityChanged = true;
			if (rand.nextInt(24) == 0)
				world.spawnEntityInWorld(ei);
		}
	}

	public void repair() {
		failed = false;
	}

	public boolean failed() {
		return failed;
	}

	public void testFailure() {
		if (ReikaEngLibrary.mat_rotfailure(type.getDensity(), 0.0625, ReikaMathLibrary.doubpow(omega, 1-(0.11D*type.ordinal())), type.getTensileStrength())) {
			this.fail(worldObj, xCoord, yCoord, zCoord, true);
		}
		else if (ReikaEngLibrary.mat_twistfailure(torque, 0.0625, type.getShearStrength()/16D)) {
			this.fail(worldObj, xCoord, yCoord, zCoord, false);
		}
	}

	//No read/write y2 since vertical shafts will not have cross equivalent
	//(no way to make them look structurally sound)

	private void crossReadFromCross(TileEntityShaft cross, int dir) {
		reading2Dir = true;
		if (cross.isWritingTo(this)) {
			readomega[dir] = cross.readomega[0];
			readtorque[dir] = cross.readtorque[0];
		}
		else if (cross.isWritingTo2(this)) {
			readomega[dir] = cross.readomega[1];
			readtorque[dir] = cross.readtorque[1];
		}
		else
			return; //not its output
	}

	@Override
	protected void readFromCross(TileEntityShaft cross) {
		reading2Dir = true;
		super.readFromCross(cross);
	}

	private void crossReadFromSplitter(TileEntitySplitter spl, int dir) {
		reading2Dir = true;
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
			readomega[dir] = spl.omega; //omega always constant
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("INLINE!  %d  %d  FOR %d", spl.omega, spl.torque, sratio));
			if (sratio == 1) { //Even split, favorbent irrelevant
				readtorque[dir] = spl.torque/2;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", this.readtorque[dir], this.readomega[dir]));
				return;
			}
			if (favorbent) {
				readtorque[dir] = spl.torque/sratio;
			}
			else {
				readtorque[dir] = (int)(spl.torque*((sratio-1D)/(sratio)));
			}
		}
		else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
			readomega[dir] = spl.omega; //omega always constant
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("BEND!");
			if (sratio == 1) { //Even split, favorbent irrelevant
				readtorque[dir] = spl.torque/2;
				return;
			}
			if (favorbent) {
				readtorque[dir] = (int)(spl.torque*((sratio-1D)/(sratio)));
			}
			else {
				readtorque[dir] = spl.torque/sratio;
			}
		}
		else { //We are not one of its write-to blocks
			readtorque[dir] = 0;
			readomega[dir] = 0;
			return;
		}
	}

	@Override
	protected void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
		reading2Dir = true;
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
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("INLINE!  %d  %d  FOR %d", spl.omega, spl.torque, sratio));
			if (sratio == 1) { //Even split, favorbent irrelevant
				torque = spl.torque/2;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", this.torque, this.omega));
				return;
			}
			if (favorbent) {
				torque = spl.torque/sratio;
			}
			else {
				torque = (int)(spl.torque*((sratio-1D)/(sratio)));
			}
		}
		else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
			omega = spl.omega; //omega always constant
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("BEND!");
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
		else { //We are not one of its write-to blocks
			torque = 0;
			omega = 0;
			power = 0;
			return;
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		ratio = 1;
		if (failed) {
			torque = 0;
			omega = 0;
			power = 0;
			return;
		}
		//this.testFailure();
		this.getIOSides(world, x, y, z, meta);
		this.transferPower(world, x, y, z, meta);

		//ReikaJavaLibrary.pConsole(Arrays.toString(readtorque)+":"+Arrays.toString(readomega), Side.SERVER);
	}

	public boolean isCross() {
		return this.getBlockMetadata() >= 6;
	}

	public void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			read = ForgeDirection.EAST;
			write = ForgeDirection.WEST;
			break;
		case 1:
			read = ForgeDirection.WEST;
			write = ForgeDirection.EAST;
			break;
		case 2:
			read = ForgeDirection.SOUTH;
			write = ForgeDirection.NORTH;
			break;
		case 3:
			read = ForgeDirection.NORTH;
			write = ForgeDirection.SOUTH;
			break;
		case 4:	//moving up
			read = ForgeDirection.DOWN;
			write = ForgeDirection.UP;
			break;
		case 5:	//moving down
			read = ForgeDirection.UP;
			write = ForgeDirection.DOWN;
			break;
		case 6:	//cross - has 4 states
			read = ForgeDirection.EAST;
			read2 = ForgeDirection.SOUTH;
			write = ForgeDirection.WEST;
			write2 = ForgeDirection.NORTH;
			break;
		case 7:	//cross - has 4 states
			read = ForgeDirection.EAST;
			read2 = ForgeDirection.NORTH;
			write = ForgeDirection.WEST;
			write2 = ForgeDirection.SOUTH;
			break;
		case 8:	//cross - has 4 states
			read = ForgeDirection.WEST;
			read2 = ForgeDirection.NORTH;
			write = ForgeDirection.EAST;
			write2 = ForgeDirection.SOUTH;
			break;
		case 9:	//cross - has 4 states
			read = ForgeDirection.WEST;
			read2 = ForgeDirection.SOUTH;
			write = ForgeDirection.EAST;
			write2 = ForgeDirection.NORTH;
			break;
		}
	}

	private void crossTransfer(World world, int x, int y, int z, boolean check1, boolean check2) {
		if (check1 && check2) {
			readomega[0] = 0;
			readomega[1] = 0;
			readtorque[0] = 0;
			readtorque[1] = 0;
		}
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		int dx = x+read.offsetX;
		int dy = y+read.offsetY;
		int dz = z+read.offsetZ;
		int dx2 = x+read2.offsetX;
		int dy2 = y+read2.offsetY;
		int dz2 = z+read2.offsetZ;
		MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
		TileEntity te1 = isCentered ? this.getAdjacentTileEntity(read) : world.getBlockTileEntity(dx, dy, dz);
		MachineRegistry m2 = isCentered ? this.getMachine(read2) : MachineRegistry.getMachine(world, dx2, dy2, dz2);
		TileEntity te2 = isCentered ? this.getAdjacentTileEntity(read2) : world.getBlockTileEntity(dx2, dy2, dz2);


		//ReikaJavaLibrary.pConsole(read.name()+":"+read2.name(), Side.SERVER);

		//ReikaJavaLibrary.pConsole(te1, Side.SERVER);

		if (check1) {
			if (this.isProvider(te1)) {
				if (m == MachineRegistry.SHAFT) {
					TileEntityShaft devicein = (TileEntityShaft)te1;
					if (devicein.isCross()) {
						this.crossReadFromCross(devicein, 0);
						return;
					}
					else if (devicein.isWritingTo(this)) {
						readomega[0] = devicein.omega;
						readtorque[0] = devicein.torque;
					}
				}
				if (te1 instanceof SimpleProvider) {
					if (((TileEntityIOMachine)te1).isWritingTo(this)) {
						readtorque[0] = ((TileEntityIOMachine)te1).torque;
						readomega[0] = ((TileEntityIOMachine)te1).omega;
					}
				}
				if (te1 instanceof ShaftPowerEmitter) {
					ShaftPowerEmitter sp = (ShaftPowerEmitter)te1;
					if (sp.isEmitting() && sp.canWriteTo(read.getOpposite())) {
						readtorque[0] = sp.getTorque();
						readomega[0] = sp.getOmega();
					}
				}
				if (m == MachineRegistry.SPLITTER) {
					TileEntitySplitter devicein = (TileEntitySplitter)te1;
					if (devicein.isSplitting()) {
						this.crossReadFromSplitter(devicein, 0);
						return;
					}
					else if (devicein.isWritingTo(this)) {
						readtorque[0] = devicein.torque;
						readomega[0] = devicein.omega;
					}
				}
			}
			else if (te1 instanceof SpaceRift) {
				SpaceRift sr = (SpaceRift)te1;
				WorldLocation loc = sr.getLinkTarget();
				if (loc != null)
					this.crossTransfer(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, true, false);
			}
			else {
				readtorque[0] = 0;
				readomega[0] = 0;
			}
		}

		if (check2) {
			if (this.isProvider(te2)) {
				if (m2 == MachineRegistry.SHAFT) {
					TileEntityShaft devicein2 = (TileEntityShaft)te2;
					if (devicein2.isCross()) {
						this.crossReadFromCross(devicein2, 1);
						return;
					}
					else if (devicein2.isWritingTo(this)) {
						if (((TileEntityIOMachine)te2).isWritingTo(this)) {
							readomega[1] = devicein2.omega;
							readtorque[1] = devicein2.torque;
						}
					}
				}
				if (te2 instanceof SimpleProvider) {
					readtorque[1] = ((TileEntityIOMachine)te2).torque;
					readomega[1] = ((TileEntityIOMachine)te2).omega;
				}
				if (te2 instanceof ShaftPowerEmitter) {
					ShaftPowerEmitter sp = (ShaftPowerEmitter)te2;
					if (sp.isEmitting() && sp.canWriteTo(read2.getOpposite())) {
						readtorque[1] = sp.getTorque();
						readomega[1] = sp.getOmega();
					}
				}
				if (m2 == MachineRegistry.SPLITTER) {
					TileEntitySplitter devicein2 = (TileEntitySplitter)te2;
					if (devicein2.isSplitting()) {
						this.crossReadFromSplitter(devicein2, 1);
						return;
					}
					else if (devicein2.isWritingTo(this)) {
						readtorque[1] = devicein2.torque;
						readomega[1] = devicein2.omega;
					}
				}
			}
			else if (te2 instanceof SpaceRift) {
				SpaceRift sr = (SpaceRift)te2;
				WorldLocation loc = sr.getLinkTarget();
				if (loc != null)
					this.crossTransfer(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord, false, true);
			}
			else {
				readtorque[1] = 0;
				readomega[1] = 0;
			}
		}

		if (!check1 || !check2)
			return;

		//ReikaJavaLibrary.pConsole(Arrays.toString(readtorque)+":"+Arrays.toString(readomega), Side.SERVER);

		this.writeToPowerReceiver(write, readomega[0], readtorque[0]);
		this.writeToPowerReceiver(write2, readomega[1], readtorque[1]);

	}

	@Override
	protected void transferPower(World world, int x, int y, int z, int meta) {
		reading2Dir = false;
		if (this.isCross()) {
			this.crossTransfer(world, x, y, z, true, true);
			return;
		}
		omegain = torquein = 0;
		boolean isCentered = x == xCoord && y == yCoord && z == zCoord;
		int dx = x+read.offsetX;
		int dy = y+read.offsetY;
		int dz = z+read.offsetZ;
		MachineRegistry m = isCentered ? this.getMachine(read) : MachineRegistry.getMachine(world, dx, dy, dz);
		TileEntity te = isCentered ? this.getAdjacentTileEntity(read) : world.getBlockTileEntity(dx, dy, dz);
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
					this.readFromSplitter(devicein);
					return;
				}
				else if (devicein.isWritingTo(this)) {
					torquein = devicein.torque;
					omegain = devicein.omega;
				}
			}
		}
		else if (te instanceof SpaceRift) {
			SpaceRift sr = (SpaceRift)te;
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

		omega = omegain / ratio;
		torque = torquein * ratio;
		power = omega*torque;

		this.basicPowerReceiver();

		if (!type.isInfiniteStrength())
			this.testFailure();

		if (omega >= 32000000 && !failed) {
			RotaryAchievements.MRADS32.triggerAchievement(this.getPlacer());
		}
		if (power >= 1000000000 && !failed)
			RotaryAchievements.GIGAWATT.triggerAchievement(this.getPlacer());
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setBoolean("failed", failed);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		failed = NBT.getBoolean("failed");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		NBT.setInteger("type", type.ordinal());
		super.writeToNBT(NBT);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		type = MaterialRegistry.setType(NBT.getInteger("type"));
		super.readFromNBT(NBT);
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
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.25);

		crossphi1 += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(readomega[0]+1, 2), 1.25);
		crossphi2 += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(readomega[1]+1, 2), 1.25);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SHAFT;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void onEMP() {}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		if (this.isCross()) {
			boolean read1 = this.isWritingTo(io);
			if (read1) {
				return PowerSourceList.getAllFrom(worldObj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller);
			}
			else {
				return PowerSourceList.getAllFrom(worldObj, read2, xCoord+read2.offsetX, yCoord+read2.offsetY, zCoord+read2.offsetZ, this, caller);
			}
		}
		else
			return super.getPowerSources(io, caller);
	}
}
