/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Containers.ContainerScaleChest;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;


public class TileEntityScaleableChest extends InventoriedPowerReceiver {

	public static final int FALLOFF = 128;
	public static final int MAXROWS = 6;
	public static final int MAXSIZE = 594;

	public static final int POWERCHANGEAGE = 20; //1s
	private ArrayList<Integer> powerchanges = new ArrayList<Integer>();
	private int numchanges;
	private boolean lastpower;
	private int resetTick = 0;

	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;

	/** The angle of the lid last tick */
	public float prevLidAngle;

	public int numUsingPlayers;

	private ItemStack[] inventory = new ItemStack[MAXSIZE];

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		int size;
		if (power < MINPOWER)
			size = 9;
		else
			size = 9+(int)(power-MINPOWER)/FALLOFF;
		if (size >= inventory.length)
			size = inventory.length;
		return size;
	}

	public int getNumPowerChanges() {
		return numchanges;
	}

	public int getMaxPage() {
		int size = this.getSizeInventory();
		size /= 9D;
		size /= MAXROWS;
		return Math.min(this.getMaxPages(), (int)Math.ceil(size));
	}

	public static int getMaxPages() {
		int size = MAXSIZE;
		size /= 9D;
		size /= MAXROWS;
		return Math.min((int)Math.ceil(size), 10);
	}

	private boolean testInconsistentPower() {
		for (int i = 0; i < powerchanges.size(); i++) {
			int b = powerchanges.get(i);
			b++;
			powerchanges.set(i, b);
		}
		for(Iterator itr = powerchanges.iterator(); itr.hasNext();) {
			int c = (Integer)itr.next();
			if (c > POWERCHANGEAGE)
				itr.remove();
		}
		boolean pw = (power >= MINPOWER);
		if (pw != lastpower) {
			int a = 0;
			powerchanges.add(a);
		}
		numchanges = powerchanges.size();
		lastpower = pw;
		if (numchanges > 10 && !worldObj.isRemote) {
			Block.blocksList[this.getTileEntityBlockID()].dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getMachineIndex(), 0);
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			worldObj.createExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 4F, ConfigRegistry.BLOCKDAMAGE.getState());
		}
		else if (numchanges > 8) {
			for (int i = 0; i < numchanges/3; i++)
				worldObj.spawnParticle("smoke", xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), 0, 0, 0);
			if (rand.nextInt(19-numchanges) == 0)
				worldObj.createExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 0F, false);
		}
		else if (numchanges > 3) {
			for (int i = 0; i < numchanges/3; i++)
				worldObj.spawnParticle("smoke", xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), 0, 0, 0);
			if (rand.nextInt(11-numchanges) == 0)
				worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.fizz", 1F, 1F);
		}
		else
			return false;
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i >= inventory.length) {
			ReikaJavaLibrary.pConsole("Minecraft tried to access nonexistent stack "+(i+1)+"/"+MAXSIZE+"!");
			return null;
		}
		return inventory[i];
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer ep) {
		if (numchanges > 0)
			return false;
		if (power < MINPOWER)
			return false;
		return super.isPlayerAccessible(ep);
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);
		if (this.testInconsistentPower())
			return;
		if (power < MINPOWER) {
			lidAngle = 0;
			prevLidAngle = 0;
			return;
		}
		float f;
		if (!worldObj.isRemote && numUsingPlayers != 0) {
			numUsingPlayers = 0;
			f = 5.0F;
			List list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(xCoord - f, yCoord - f, zCoord - f, xCoord + 1 + f, yCoord + 1 + f, zCoord + 1 + f));
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityPlayer entityplayer = (EntityPlayer)iterator.next();
				if (entityplayer.openContainer instanceof ContainerScaleChest) {
					IInventory iinventory = ((ContainerScaleChest)entityplayer.openContainer).getLowerScaleChestInventory();
					if (iinventory == this)
						++numUsingPlayers;
				}
			}
		}
		prevLidAngle = lidAngle;
		f = 0.1F;
		double d0;
		if (numUsingPlayers > 0 && lidAngle == 0.0F) {
			double d1 = xCoord + 0.5D;
			d0 = zCoord + 0.5D;
			worldObj.playSoundEffect(d1, yCoord + 0.5D, d0, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
			float f1 = lidAngle;
			if (numUsingPlayers > 0)
				lidAngle += f;
			else
				lidAngle -= f;
			if (lidAngle > 1.0F)
				lidAngle = 1.0F;
			float f2 = 0.5F;
			if (lidAngle < f2 && f1 >= f2) {
				d0 = xCoord + 0.5D;
				double d2 = zCoord + 0.5D;
				worldObj.playSoundEffect(d0, yCoord + 0.5D, d2, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (lidAngle < 0.0F)
				lidAngle = 0.0F;
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
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
		case 3:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			break;
		case 2:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			break;
		}
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int par1, int par2)
	{
		if (par1 == 1) {
			numUsingPlayers = par2;
			return true;
		}
		else
			return super.receiveClientEvent(par1, par2);
	}

	@Override
	public void openChest()
	{
		if (power < MINPOWER)
			return;
		if (numUsingPlayers < 0)
			numUsingPlayers = 0;
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getTileEntityBlockID(), 1, numUsingPlayers);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getTileEntityBlockID());
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, this.getTileEntityBlockID());
	}

	@Override
	public void closeChest()
	{
		if (MachineRegistry.getMachine(worldObj, xCoord, yCoord, zCoord) == MachineRegistry.SCALECHEST)
		{
			--numUsingPlayers;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getTileEntityBlockID(), 1, numUsingPlayers);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getTileEntityBlockID());
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, this.getTileEntityBlockID());
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		//for (int i = 0; i < powerchanges.size(); i++)
		//	NBT.setInteger("pwr"+String.valueOf(i), powerchanges.get(i));

		NBT.setInteger("chng", numchanges);
		NBT.setInteger("player", numUsingPlayers);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("Slot", (short)i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		//ArrayList<Integer> pwr = new ArrayList<Integer>();

		numchanges = NBT.getInteger("chng");
		numUsingPlayers = NBT.getInteger("player");


		NBTTagList nbttaglist = NBT.getTagList("Items");
		inventory = new ItemStack[MAXSIZE];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			short short0 = nbttagcompound.getShort("Slot");

			if (short0 >= 0 && short0 < inventory.length)
			{
				inventory[short0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SCALECHEST;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return 15*this.getSizeInventory()/MAXSIZE;
	}

}
