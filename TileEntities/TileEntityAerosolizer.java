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

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaPotionHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Models.ModelAerosolizer;

public class TileEntityAerosolizer extends TileEntityInventoriedPowerReceiver implements RangedEffect, IInventory {

	public static final int MAXRANGE = RotaryConfig.maxaerorange;
	public static final int CAPACITY = 64;

	public int potionLevel[] = new int[9];
	public int potionDamage[] = new int[9];
	private int potionIDs[] = new int[9];
	private int copies[] = new int[9];

	public int[] slotColor = new int[9];


	private int tickcount2 = 0;

	public boolean idle = false;

	private ItemStack[] contents = new ItemStack[9];

	public void testIdle() {
		boolean empty = true;
		for (int i = 0; i < 9; i++) {
			if (potionDamage[i] > 8192)
				empty = false;
		}
		idle = empty;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		power = omega*torque;
		//this.getIOSides(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		this.getSummativeSidedPower();
		tickcount++;
		tickcount2++;
		this.consumeBottles();
		this.colorize();
		for (int i = 0; i < 9; i++)
			potionIDs[i] = ReikaPotionHelper.getPotionID(potionDamage[i]);
		if (power < MINPOWER)
			return;
		this.testIdle();
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d  %d", this.potionDamage.length, this.potionLevel.length, 0, 0));
		for (int i = 0; i < 9; i++) {
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.potionLevel[i]));
			if (tickcount2 >= 20/ReikaMathLibrary.extrema(this.getMultiplier(i), 1, "max")) {
				//this.potionDamage[i] = this.getPotion(i);
				AxisAlignedBB room = this.getRoom(world, x, y, z, meta);
				if (potionLevel[i] > 0)
					this.dispense2(world, x, y, z, meta, room, i);
				if (i == 8)
					tickcount2 = 0;
			}
			if (tickcount >= 2400 && potionLevel[i] > 0) {
				potionLevel[i]--;
				if (i == 8)
					tickcount = 0;
			}
			if (potionLevel[i] <= 0) {
				potionLevel[i] = 0;
				if (i == 8)
					tickcount = 0;
			}
		}
	}

	public void colorize() {
		for (int i = 0; i < 9; i++) {
			if (potionIDs[i] > 0)
				slotColor[i] = Potion.potionTypes[potionIDs[i]].getLiquidColor();
			else
				slotColor[i] = 0xff000000; //solid black
		}
	}

	public void consumeBottles() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return;
		for (int i = 0; i < 9; i++) {
			ItemStack inslot = this.getStackInSlot(i);
			if (inslot != null) {
				int size = inslot.stackSize;
				if (inslot.itemID == Item.potion.itemID && (inslot.getItemDamage() == potionDamage[i] || potionLevel[i] == 0) && potionLevel[i] < CAPACITY) {
					int potionID = ReikaPotionHelper.getPotionID(inslot.getItemDamage());
					if (potionID != -1) {
						if (!Potion.potionTypes[potionID].isInstant()) {
							boolean extended = PotionHelper.checkFlag(inslot.getItemDamage(), 6); //Bit 6 is enhanced
							boolean level2 = PotionHelper.checkFlag(inslot.getItemDamage(), 5); //Bit 5 is extended
							if (potionLevel[i] == 0) {
								potionDamage[i] = inslot.getItemDamage();
							}
							potionLevel[i] += size;
							if (extended && (potionLevel[i]+2 <= CAPACITY))
								potionLevel[i] += 2;
							//this.decrStackSize(i, 1);
							//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("+%d", size)+String.valueOf(FMLCommonHandler.instance().getEffectiveSide()));
							this.setInventorySlotContents(i, new ItemStack(Item.glassBottle.itemID, size, 0));
							if (potionLevel[i] > CAPACITY)
								potionLevel[i] = CAPACITY;
						}
					}
				}
			}
		}
	}

	public int getLiquidScaled(int par1, int par2)
	{
		return (par2*par1)/CAPACITY;
	}

	public int getPotion(int i) {
		if (this.getStackInSlot(i) != null) {
			if (this.getStackInSlot(i).stackSize > 0 && this.getStackInSlot(i).getItem() instanceof ItemPotion) {
				int dmg = this.getStackInSlot(i).getItemDamage();
				return dmg;
			}
			else
				return 0;
		}
		else {
			return 0;
		}
	}

	public AxisAlignedBB getRoom(World world, int x, int y, int z, int meta) {
		int minx = x;
		int maxx = x+1;
		int miny = y;
		int maxy = y+1;
		int minz = z;
		int maxz = z+1;

		boolean exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x+i, y, z)])
				exit = true;
			else
				maxx = x+i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x-i, y, z)])
				exit = true;
			else
				minx = x-i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y+i, z)])
				exit = true;
			else
				maxy = y+i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y-i, z)])
				exit = true;
			else
				miny = x-i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y, z+i)])
				exit = true;
			else
				maxz = z+i;
		}
		exit = false;
		for (int i = 1; i < this.getRange() && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y, z-i)])
				exit = true;
			else
				minz = z-i;
		}
		exit = false;/*
		ReikaWorldHelper.legacySetBlockWithNotify(world, minx, miny, minz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, maxx, miny, minz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, minx, maxy, minz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, maxx, maxy, minz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, minx, miny, maxz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, maxx, miny, maxz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, minx, maxy, maxz, 49);
		ReikaWorldHelper.legacySetBlockWithNotify(world, maxx, maxy, maxz, 49);*/

		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz);
	}

	public void dispense2(World world, int x, int y, int z, int meta, AxisAlignedBB room, int i) { // id, duration, amplifier
		if (!worldObj.isRemote) {
			List effects = Item.potion.getEffects(potionDamage[i]);
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", this.potionDamage[i]));
			if (effects != null && !effects.isEmpty()) {
				List inroom = worldObj.getEntitiesWithinAABB(EntityLiving.class, room);
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", inroom.size()));
				if (inroom != null && !inroom.isEmpty()) {
					Iterator iter = inroom.iterator();
					while (iter.hasNext()) {
						EntityLiving mob = (EntityLiving)iter.next();
						Iterator potioneffects = effects.iterator();
						while (potioneffects.hasNext()) {
							PotionEffect effect = (PotionEffect)potioneffects.next();
							int id = effect.getPotionID();
							if (!Potion.potionTypes[id].isInstant()) {
								int bonus = this.getMultiplier(i) - 1;  //-1 since adding
								if (effect.getAmplifier() == 1)
									bonus *= 2;
								mob.addPotionEffect(new PotionEffect(id, 100, effect.getAmplifier()+bonus));
								//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", effect.getAmplifier()));
							}
						}
					}
				}
			}
		}
	}

	public int getMultiplier(int i) {
		if (potionIDs[i] == -1)
			return 0;
		int[] number = new int[Potion.potionTypes.length];
		number = this.countCopies();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", this.potionIDs[i], number[this.potionIDs[i]]));
		return (number[potionIDs[i]]);
	}

	//method to return number of occurrences of the numbers in diceRolls
	public int[] countCopies() {
		int occurrence[] = new int[Potion.potionTypes.length]; //to hold the counts

		for(int i = 0; i < 9; i++) { //Loop over the dice rolls array
			int value = potionIDs[i]; //Get the value of the next roll
			if (value != -1) {
				occurrence[value]++; //Increment the value in the count array;
				//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", occurrence[value]));
			}
		}
		return occurrence; //return the counts
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList var2 = NBT.getTagList("Items");
		contents = new ItemStack[potionDamage.length];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < contents.length)
			{
				contents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		potionDamage = NBT.getIntArray("damages");
		potionLevel = NBT.getIntArray("levels");
		potionIDs = NBT.getIntArray("IDs");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < contents.length; ++var3)
		{
			if (contents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				contents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		NBT.setTag("Items", var2);
		NBT.setIntArray("damages", potionDamage);
		NBT.setIntArray("levels", potionLevel);
		NBT.setIntArray("IDs", potionIDs);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return contents.length;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return contents[par1];
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (contents[par1] != null)
		{
			if (contents[par1].stackSize <= par2)
			{
				ItemStack itemstack = contents[par1];
				contents[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = contents[par1].splitStack(par2);

			if (contents[par1].stackSize <= 0)
			{
				contents[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	/**


	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (contents[par1] != null)
		{
			ItemStack itemstack = contents[par1];
			contents[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 *
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		contents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelAerosolizer();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getRange() {
		return MAXRANGE;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.AEROSOLIZER.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return is.itemID == Item.potion.itemID;
	}

	@Override
	public int getMaxRange() {
		return RotaryConfig.maxaerorange;
	}
}
