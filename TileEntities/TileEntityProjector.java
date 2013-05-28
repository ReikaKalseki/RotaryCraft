/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;

public class TileEntityProjector extends TileEntityInventoriedPowerReceiver implements RangedEffect {

	public static final int MAXCHANNELS = 24;
	public static final int DELAY = 400;

	public int channel = 0;
	public boolean on = false;
	public boolean emptySlide = true;

	private ItemStack[] slides = new ItemStack[MAXCHANNELS];

	public boolean canProject(int x2, int y2, int z2) {

		return true;
	}

    @Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
    	super.updateTileEntity();
    	tickcount++;
    	this.getIOSides(world, x, y, z, meta);
    	this.getPower(false, false);
    	if (power < MINPOWER) {
    		on = false;
    		return;
    	}
    	on = true;
    	if (tickcount >= DELAY) {
    		tickcount = 0;
    		this.cycleInv();
    	}
    	this.getChannelFromActiveSlide();
    }

    private void getChannelFromActiveSlide() {
    	if (slides[0] == null) {
    		emptySlide = true;
    		channel = 0;
    		return;
    	}
    	if (slides[0].itemID == Item.eyeOfEnder.itemID) {
        	emptySlide = false;
        	channel = -1;
    	}
    	if (slides[0].itemID != RotaryCraft.slides.itemID) {
    		emptySlide = true;
    		return;
    	}
    	emptySlide = false;
		channel = slides[0].getItemDamage();
	}

	public void cycleInv() {
    	ItemStack active = slides[0];
    	for (int i = 0; i < slides.length-1; i++) {
    		slides[i] = slides[i+1];
    	}
    	slides[slides.length-1] = active;
    	worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "Reika.RotaryCraft.projector", 1F, 1F);
    }

	public void getIOSides(World world, int x, int y, int z, int metadata) {
    	switch(metadata) {
    	case 0:
    		readx = x+1;
    		readz = z;
    		ready = y;
    	break;
    	case 1:
    		readx = x-1;
    		readz = z;
    		ready = y;
    	break;
    	case 2:
    		readz = z-1;
    		readx = x;
    		ready = y;
    	break;
    	case 3:
    		readz = z+1;
    		readx = x;
    		ready = y;
    	break;
    	}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public int getRange() {
		int x; int z;
		switch(this.getBlockMetadata()) {
		case 0:
			x = xCoord-1;
			while (x >= xCoord-12 && worldObj.getBlockId(x, yCoord, zCoord) == 0)
				x--;
		return x-xCoord+1;
		case 1:
			x = xCoord+1;
			while (x <= xCoord+12+1 && worldObj.getBlockId(x, yCoord, zCoord) == 0)
				x++;
		return -(x-xCoord);
		case 2:
			z = zCoord+1;
			while (z <= zCoord+1+12 && worldObj.getBlockId(xCoord, yCoord, z) == 0)
				z++;
		return -(z-zCoord);
		case 3:
			z = zCoord-1;
			while (z >= zCoord-12 && worldObj.getBlockId(xCoord, yCoord, z) == 0)
				z--;
		return z-zCoord+1;
		default:
			return 0;
		}
	}

	public boolean canShow() {
		int r = this.getRange();
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int a = 0; int b = 0;
		switch(this.getBlockMetadata()) {
		case 0:
			x += r-1;
			a = 1;
		break;
		case 1:
			x -= r;
			a = 1;
		break;
		case 2:
			z -= r;
			b = 1;
		break;
		case 3:
			z += r-1;
			b = 1;
		break;
		}
		int x2 = x; int z2 = z;
		switch(this.getBlockMetadata()) {
		case 0:
			x2++;
		break;
		case 1:
			x2--;
		break;
		case 2:
			z2--;
		break;
		case 3:
			z2++;
		break;
		}
		World world = worldObj;
		for (int k = 0; k <= 4; k++) {
			for (int i = -3; i <= 3; i++) {
				int id = world.getBlockId(x+b*i, y+k, z+a*i);
				if (id == 0 || !Block.blocksList[id].isOpaqueCube()) {
					return false;
				}
				if (!this.canProject(x+b*i, y+k, z+a*i))
					return false;
			}
		}
		for (int k = 0; k <= 4; k++) {
			for (int i = -3; i <= 3; i++) {
				int id = world.getBlockId(x2+b*i, y+k, z2+a*i);
				if (id != 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PROJECTOR.ordinal();
	}

	@Override
	public int getSizeInventory() {
		return MAXCHANNELS;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slides[i];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		if (slides[par1] != null)
		{
			if (slides[par1].stackSize <= par2)
			{
				ItemStack itemstack = slides[par1];
				slides[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = slides[par1].splitStack(par2);

			if (slides[par1].stackSize == 0)
			{
				slides[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (slides[par1] != null)
		{
			ItemStack itemstack = slides[par1];
			slides[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is) {
		slides[i] = is;
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return is.itemID == RotaryCraft.slides.itemID;
	}

	 @Override
	 public void readFromNBT(NBTTagCompound NBT)
	 {
		 super.readFromNBT(NBT);
	     channel = NBT.getInteger("ch");
	     emptySlide = NBT.getBoolean("empty");
		 NBTTagList nbttaglist = NBT.getTagList("Items");
		 slides = new ItemStack[this.getSizeInventory()];

		 for (int i = 0; i < nbttaglist.tagCount(); i++)
		 {
			 NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			 byte byte0 = nbttagcompound.getByte("Slot");

			 if (byte0 >= 0 && byte0 < slides.length)
			 {
				 slides[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			 }
		 }
	 }

	 /**
	  * Writes a tile entity to NBT.  Maybe was not saving slides since seems to be acting like
	  * extends TileEntityPowerReceiver, NOT InventoriedPowerReceiver
	  */
	 @Override
	 public void writeToNBT(NBTTagCompound NBT)
	 {
		 super.writeToNBT(NBT);
	     NBT.setInteger("ch", channel);
	     NBT.setBoolean("empty", emptySlide);
		 NBTTagList nbttaglist = new NBTTagList();

		 for (int i = 0; i < slides.length; i++)
		 {
			 if (slides[i] != null)
			 {
				 NBTTagCompound nbttagcompound = new NBTTagCompound();
				 nbttagcompound.setByte("Slot", (byte)i);
				 slides[i].writeToNBT(nbttagcompound);
				 nbttaglist.appendTag(nbttagcompound);
			 }
		 }

		 NBT.setTag("Items", nbttaglist);
	 }

	@Override
	public int getMaxRange() {
		return 8;
	}

}
