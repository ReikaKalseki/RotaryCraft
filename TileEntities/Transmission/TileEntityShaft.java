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

	public void crossReadFromCross(TileEntityShaft cross, int dir) {
		reading2Dir = true;
		if (xCoord == cross.writex && zCoord == cross.writez) {
			readomega[dir] = cross.readomega[0];
			readtorque[dir] = cross.readtorque[0];
		}
		else if (xCoord == cross.writex2 && zCoord == cross.writez2) {
			readomega[dir] = cross.readomega[1];
			readtorque[dir] = cross.readtorque[1];
		}
		else
			return; //not its output
	}

	public void readFromCross(TileEntityShaft cross) {
		reading2Dir = true;
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

	//FIX THIS;
	@Override
	public void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
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
	}

	public boolean isCross() {
		return this.getBlockMetadata() >= 6;
	}

	public void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			readx = x+1;
			writex = x-1;
			readz = writez = z;
			ready = writey = y;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			readz = writez = z;
			ready = writey = y;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			readx = writex = x;
			ready = writey = y;
			break;
		case 3:
			readz = z-1;
			writez = z+1;
			readx = writex = x;
			ready = writey = y;
			break;
		case 4:	//moving up
			readx = writex = x;
			readz = writez = z;
			ready = y-1;
			writey = y+1;
			break;
		case 5:	//moving down
			readx = writex = x;
			readz = writez = z;
			ready = y+1;
			writey = y-1;
			break;
		case 6:	//cross - has 4 states
			readx = x+1;
			writex = x-1;
			readz = z;
			writez = z;
			readx2 = x;
			readz2 = z+1;
			writex2 = x;
			writez2 = z-1;
			ready = writey = y;
			break;
		case 7:	//cross - has 4 states
			readx = x+1;
			writex = x-1;
			readz = z;
			writez = z;
			readx2 = x;
			readz2 = z-1;
			writex2 = x;
			writez2 = z+1;
			ready = writey = y;
			break;
		case 8:	//cross - has 4 states
			readx = x-1;
			writex = x+1;
			readz = z;
			writez = z;
			readx2 = x;
			readz2 = z-1;
			writex2 = x;
			writez2 = z+1;
			ready = writey = y;
			break;
		case 9:	//cross - has 4 states
			readx = x-1;
			writex = x+1;
			readz = z;
			writez = z;
			readx2 = x;
			readz2 = z+1;
			writex2 = x;
			writez2 = z-1;
			ready = writey = y;
			break;
		}
	}

	private void crossTransfer(World world) {
		ready = ready2 = ready;
		readomega[0] = 0;
		readomega[1] = 0;
		readtorque[0] = 0;
		readtorque[1] = 0;
		TileEntity te1 = worldObj.getBlockTileEntity(readx, ready, readz);
		TileEntity te2 = worldObj.getBlockTileEntity(readx2, ready2, readz2);
		if (this.isProvider(te1)) {
			MachineRegistry m = MachineRegistry.getMachine(world, readx, ready, readz);
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft devicein = (TileEntityShaft)te1;
				if (devicein.isCross()) {
					this.crossReadFromCross(devicein, 0);
					return;
				}
				else if (devicein.writex == xCoord && devicein.writey == yCoord && devicein.writez == zCoord) {
					readomega[0] = devicein.omega;
					readtorque[0] = devicein.torque;
				}
			}
			if (te1 instanceof SimpleProvider) {
				readtorque[0] = ((TileEntityIOMachine)te1).torque;
				readomega[0] = ((TileEntityIOMachine)te1).omega;
			}
			if (te1 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te1;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
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
				else if (devicein.writex == xCoord && devicein.writez == zCoord) {
					readtorque[0] = devicein.torque;
					readomega[0] = devicein.omega;
				}
			}
		}
		if (this.isProvider(te2)) {
			MachineRegistry m2 = MachineRegistry.getMachine(world, readx2, ready2, readz2);
			if (m2 == MachineRegistry.SHAFT) {
				TileEntityShaft devicein2 = (TileEntityShaft)te2;
				if (devicein2.isCross()) {
					this.crossReadFromCross(devicein2, 1);
					return;
				}
				else if (devicein2.writex == xCoord && devicein2.writey == yCoord && devicein2.writez == zCoord) {
					readomega[1] = devicein2.omega;
					readtorque[1] = devicein2.torque;
				}
			}
			if (te2 instanceof SimpleProvider) {
				readtorque[1] = ((TileEntityIOMachine)te2).torque;
				readomega[1] = ((TileEntityIOMachine)te2).omega;
			}
			if (te2 instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter sp = (ShaftPowerEmitter)te2;
				if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
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
				else if (devicein2.writex == xCoord && devicein2.writez == zCoord) {
					readtorque[1] = devicein2.torque;
					readomega[1] = devicein2.omega;
				}
			}
		}
		this.writeToPowerReceiverAt(world, writex, yCoord, writez, readomega[0], readtorque[0]);
		this.writeToPowerReceiverAt(world, writex2, yCoord, writez2, readomega[1], readtorque[1]);
	}

	@Override
	public void transferPower(World world, int x, int y, int z, int meta) {
		reading2Dir = false;
		if (this.isCross()) {
			this.crossTransfer(world);
			return;
		}
		omegain = torquein = 0;
		TileEntity te = worldObj.getBlockTileEntity(readx, ready, readz);
		if (!this.isProvider(te)) {
			omega = 0;
			torque = 0;
			power = 0;
			return;
		}
		MachineRegistry m = MachineRegistry.getMachine(world, readx, ready, readz);
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft devicein = (TileEntityShaft)te;
			if (devicein.isCross()) {
				this.readFromCross(devicein);
				return;
			}
			else if (devicein.writex == x && devicein.writey == y && devicein.writez == z) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}
		if (te instanceof SimpleProvider) {
			this.copyStandardPower(worldObj, readx, ready, readz);
		}
		if (m == MachineRegistry.POWERBUS) {
			TileEntityPowerBus pwr = (TileEntityPowerBus)te;
			ForgeDirection dir = this.getInputForgeDirection().getOpposite();
			omegain = pwr.getSpeedToSide(dir);
			torquein = pwr.getTorqueToSide(dir);
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
			if (devicein.isSplitting()) {
				this.readFromSplitter(devicein);
				return;
			}
			else if (devicein.writex == x && devicein.writez == z) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
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
	public void animateWithTick(World world, int x, int y, int z) {
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
			boolean read1 = io.xCoord == writex && io.zCoord == writez;
			if (read1) {
				return PowerSourceList.getAllFrom(worldObj, readx, ready, readz, this, caller);
			}
			else {
				return PowerSourceList.getAllFrom(worldObj, readx2, ready2, readz2, this, caller);
			}
		}
		else
			return super.getPowerSources(io, caller);
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
