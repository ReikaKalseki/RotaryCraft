/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Storage;

import Reika.DragonAPI.Interfaces.MultiPageInventory;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Containers.ContainerScaleChest;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class TileEntityScaleableChest extends InventoriedPowerReceiver implements MultiPageInventory {

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

	public int page;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return MAXSIZE;
	}

	public int getNumberSlots() {
		int size;
		if (power < MINPOWER)
			size = 9;
		else
			size = 9+(int)(power-MINPOWER)/FALLOFF;
		if (size >= inv.length)
			size = inv.length;
		return size;
	}

	public int getNumPowerChanges() {
		return numchanges;
	}

	public int getMaxPage() {
		int size = this.getNumberSlots();
		size /= 9D;
		size /= MAXROWS;
		return Math.min(this.getMaxPages(), (int)Math.ceil(size));
	}

	@Override
	public int getNumberPages() {
		return this.getMaxPage();
	}

	@Override
	public int getSlotsOnPage(int page) {
		int max = this.getMaxPage();
		if (page == max)
			return this.getNumberSlots()-max*9*MAXROWS;
		else if (page < max)
			return 9*MAXROWS;
		else
			return 0;
	}

	public int getCurrentPage() {
		return page;
	}

	public static int getMaxPages() {
		int size = MAXSIZE;
		size /= 9;
		size /= MAXROWS;
		return size;
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
			this.getTileEntityBlockID().dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getMachineIndex(), 0);
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
	public boolean isUseableByPlayer(EntityPlayer ep) {
		if (numchanges > 0)
			return false;
		if (power < MINPOWER)
			return false;
		return super.isPlayerAccessible(ep);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

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
			List list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - f, yCoord - f, zCoord - f, xCoord + 1 + f, yCoord + 1 + f, zCoord + 1 + f));
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
			read = ForgeDirection.EAST;
			break;
		case 1:
			read = ForgeDirection.WEST;
			break;
		case 3:
			read = ForgeDirection.SOUTH;
			break;
		case 2:
			read = ForgeDirection.NORTH;
			break;
		}
	}

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
	public void openInventory()
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
	public void closeInventory()
	{
		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getTileEntityBlockID(), 1, numUsingPlayers);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getTileEntityBlockID());
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, this.getTileEntityBlockID());
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("chng", numchanges);
		NBT.setInteger("player", numUsingPlayers);

		NBT.setInteger("pg", page);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		numchanges = NBT.getInteger("chng");
		numUsingPlayers = NBT.getInteger("player");

		page = NBT.getInteger("pg");
	}

	public void writeInventoryToItem(ItemStack is) {
		is.stackTagCompound = new NBTTagCompound();

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("Slot", (short)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
				//ReikaJavaLibrary.pConsole(i+":"+inv[i]);
			}
		}

		is.stackTagCompound.setTag("Items", nbttaglist);
	}

	public void readInventoryFromItem(ItemStack is) {
		if (is.stackTagCompound != null) {
			NBTTagList nbttaglist = is.stackTagCompound.getTagList("Items", is.stackTagCompound.getId());
			inv = new ItemStack[this.getSizeInventory()];

			for (int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				short byte0 = nbttagcompound.getShort("Slot");

				if (byte0 >= 0 && byte0 < inv.length) {
					inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
				}
				else {
					RotaryCraft.logger.logError(this+" tried to load an inventory slot "+byte0+" from NBT!");
				}
			}
		}
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SCALECHEST;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot < this.getNumberSlots();
	}

	@Override
	public int getRedstoneOverride() {
		return 15*this.getSizeInventory()/MAXSIZE;
	}
}