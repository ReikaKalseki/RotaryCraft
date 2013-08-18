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
import java.util.HashMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFireworkMachine extends TileEntityInventoriedPowerReceiver implements EnchantableMachine {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	public boolean idle = false;

	public ItemStack[] inventory = new ItemStack[27]; //small chest size
	//public ItemStack[] starInventory = new ItemStack[27];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (world.isRemote)
			return;

		if (power < MINPOWER)
			return;
		if (omega < MINSPEED)
			return;
		if (!this.operationComplete(tickcount, 0))
			return;
		tickcount = 0;
		if (!this.canCraftARocket()) {
			idle = true;
			return;
		}
		idle = false;
		ItemStack rocket = null;
		boolean hasStar = ReikaInventoryHelper.checkForItem(Item.fireworkCharge.itemID, inventory);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(hasStar));
		if (!hasStar) {
			ItemStack star = this.randomRecipe();
			if (star == null)
				return;
			rocket = this.starToRocket(star);
		}
		else {
			if (this.canMakeRocketFromStar()) {
				ItemStack star = null;/*
				if (this.consumeChance())
					star = ReikaInventoryHelper.findAndDecrStack2(Item.fireworkCharge.itemID, -1, this.inventory);
				else {
					int slot = ReikaInventoryHelper.locateInInventory(Item.fireworkCharge.itemID, this.inventory);
					if (slot != -1)
						star = this.getStackInSlot(slot);
				}*/
				int slot = par5Random.nextInt(inventory.length);
				while (inventory[slot] == null || inventory[slot].itemID != Item.fireworkCharge.itemID) {
					slot = par5Random.nextInt(inventory.length);
				}
				star = this.getStackInSlot(slot);
				if (this.consumeChance())
					this.decrStackSize(slot, 1);

				//ReikaChatHelper.writeItemStack(world, star);
				rocket = this.starToRocket(star);
			}
		}
		//ReikaChatHelper.writeItemStack(world, rocket);
		if (rocket == null)
			return;
		EntityFireworkRocket firework = new EntityFireworkRocket(world, x+0.5, y+1.25, z+0.5, rocket);
		world.spawnEntityInWorld(firework);
		//-------TEST CODE----------
		//EntityItem ent = new EntityItem(world, x, y+1, z, star);
		//world.spawnEntityInWorld(ent);

		if (this.hasEnchantments()) {
			for (int i = 0; i < 8; i++) {
				world.spawnParticle("portal", -0.5+x+2*par5Random.nextDouble(), y+par5Random.nextDouble(), -0.5+z+2*par5Random.nextDouble(), 0, 0, 0);
			}
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	private boolean consumeChance() {
		if (this.hasEnchantment(Enchantment.silkTouch))
			return false;
		int excess = (int)(power/MINPOWER);
		int chance = par5Random.nextInt(excess/8);
		return (chance == 0);
	}

	private boolean canCraftARocket() {
		boolean haveDye = false;
		boolean have2Gunpowder = false;
		boolean havePaper = false;
		boolean haveStar = false;
		boolean have1Gunpowder = false;
		have1Gunpowder = ReikaInventoryHelper.checkForItem(Item.gunpowder.itemID, inventory);
		haveStar = ReikaInventoryHelper.checkForItem(Item.fireworkCharge.itemID, inventory);
		haveDye = ReikaInventoryHelper.checkForItem(Item.dyePowder.itemID, inventory);
		havePaper = ReikaInventoryHelper.checkForItem(Item.paper.itemID, inventory);
		int numgunpowder = 0;
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (inventory[i].itemID == Item.gunpowder.itemID) {
					numgunpowder += inventory[i].stackSize;
					if (numgunpowder >= 2) {
						have2Gunpowder = true;
						i = inventory.length;
					}
				}
			}
		}
		return (havePaper && (haveDye && have2Gunpowder) || (have1Gunpowder && haveStar));
	}

	private boolean canMakeRocketFromStar() {
		boolean hasPaper = ReikaInventoryHelper.checkForItem(Item.paper.itemID, inventory);
		boolean hasGunpowder = ReikaInventoryHelper.checkForItem(Item.gunpowder.itemID, inventory);
		boolean hasStar = ReikaInventoryHelper.checkForItem(Item.fireworkCharge.itemID, inventory);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(hasPaper)+" "+String.valueOf(hasGunpowder)+" "+String.valueOf(hasStar));
		return (hasPaper && hasGunpowder && hasStar);
	}

	private ItemStack starToRocket(ItemStack star) {
		ItemStack product = null;
		ItemStack gunpowder = new ItemStack(Item.gunpowder.itemID, 1, 0);
		int numgunpowder = par5Random.nextInt(3)+1; // 1-3
		ItemStack paper = new ItemStack(Item.paper.itemID, 1, 0);
		ItemStack[] ingredients = new ItemStack[5];
		if (this.getIngredient(Item.gunpowder.itemID, this.canMakeRocketFromStar() && this.consumeChance()))
			ingredients[1] = gunpowder;
		if (numgunpowder >= 2 && this.getIngredient(Item.gunpowder.itemID, this.consumeChance()))
			ingredients[2] = gunpowder;
		if (numgunpowder >= 3 && this.getIngredient(Item.gunpowder.itemID, this.consumeChance()))
			ingredients[3] = gunpowder;
		if (this.getIngredient(Item.paper.itemID, this.consumeChance()))
			ingredients[4] = paper;
		ingredients[0] = star;
		product = this.setNBT(ingredients);
		return product;
	}

	private int pickRandomColor(boolean decr) { //Returns a random color dye from inventory
		int color = -1;
		boolean[] hasColors = new boolean[16]; // To save CPU time, see below
		boolean hasDye = false;
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (inventory[i].itemID == Item.dyePowder.itemID) {
					hasDye = true;
					hasColors[inventory[i].getItemDamage()] = true;
				}
			}
		}
		if (!hasDye)
			return -1;
		while (color == -1) {
			int randcolor = par5Random.nextInt(16);
			while (!hasColors[randcolor])
				randcolor = par5Random.nextInt(16);
			for (int j = 0; j < inventory.length; j++) {
				if (inventory[j] != null) {
					if (inventory[j].itemID == Item.dyePowder.itemID && inventory[j].getItemDamage() == randcolor) {
						if (decr) {
							ReikaInventoryHelper.decrStack(j, inventory);
						}
						return randcolor;
					}
				}
			}
		}
		return -1;
	}

	private boolean getIngredient(int id, boolean decr) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (inventory[i].itemID == id) {
					if (decr) {
						ReikaInventoryHelper.decrStack(i, inventory);
					}
					return true;
				}
			}
		}
		return false;
	}

	private int getShape() {
		boolean[] hasShape = new boolean[4];
		int shape = par5Random.nextInt(4);
		if (ReikaInventoryHelper.checkForItem(Item.fireballCharge.itemID, inventory)) {
			hasShape[0] = true;
		}
		if (ReikaInventoryHelper.checkForItem(Item.goldNugget.itemID, inventory)) {
			hasShape[1] = true;
		}
		if (ReikaInventoryHelper.checkForItem(Item.feather.itemID, inventory)) {
			hasShape[2] = true;
		}
		if (ReikaInventoryHelper.checkForItem(Item.skull.itemID, inventory)) {
			hasShape[3] = true;
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(hasShape[0]));
		boolean noShapes = true;
		for (int i = 0; i < hasShape.length; i++) {
			if (hasShape[i]) {
				noShapes = false;
				i = hasShape.length;
			}
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(noShapes));
		if (noShapes)
			return -1;
		while(!hasShape[shape]) {
			shape = par5Random.nextInt(4);
		}
		int slot = -1;
		int id = -1;
		switch (shape) {
		case 0:
			id = Item.fireballCharge.itemID;
			break;
		case 1:
			id = Item.goldNugget.itemID;
			break;
		case 2:
			id = Item.feather.itemID;
			break;
		case 3:
			id = Item.skull.itemID;
			break;
		}
		if (id != -1 && this.consumeChance())
			ReikaInventoryHelper.findAndDecrStack(id, -1, inventory);
		//else
		//return 0;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(shape));
		return shape+1;
	}

	private ItemStack randomRecipe() {
		int dyeColor = this.pickRandomColor(this.consumeChance() && ReikaInventoryHelper.checkForItem(Item.gunpowder.itemID, inventory)); //Dye metadata to craft with - 0-15
		boolean hasDiamond = false;
		boolean hasGlowstone = false;
		int shape = this.getShape(); //Shape modifiers - Fire charge, gold nugget, feather, head, or nothing
		ItemStack gunpowder = new ItemStack(Item.gunpowder.itemID, 1, 64);
		ItemStack diamond = new ItemStack(Item.diamond.itemID, 1, 0);
		ItemStack glowstone = new ItemStack(Item.lightStoneDust.itemID, 1, 0);

		ItemStack[] inputitems = new ItemStack[5];
		if (dyeColor != -1)
			inputitems[1] = new ItemStack(Item.dyePowder.itemID, 1, dyeColor);
		else
			inputitems[1] = null;
		if (inputitems[1] == null) // If missing dye
			return null;
		if (this.getIngredient(Item.gunpowder.itemID, this.consumeChance()))
			inputitems[0] = gunpowder;
		if (inputitems[0] == null) // If missing gunpowder
			return null;
		if (par5Random.nextInt(2) == 0) {
			if (this.getIngredient(Item.diamond.itemID, this.consumeChance()))
				hasDiamond = true;
		}
		if (par5Random.nextInt(2) == 0) {
			if (this.getIngredient(Item.lightStoneDust.itemID, this.consumeChance()))
				hasGlowstone = true;
		}
		if (hasDiamond)
			inputitems[2] = diamond;
		if (hasGlowstone)
			inputitems[3] = glowstone;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(shape));
		switch(shape) {
		case 1:
			inputitems[4] = new ItemStack(Item.fireballCharge.itemID, 1, 0);
			break;
		case 2:
			inputitems[4] = new ItemStack(Item.goldNugget.itemID, 1, 0);
			break;
		case 3:
			inputitems[4] = new ItemStack(Item.feather.itemID, 1, 0);
			break;
		case 4:
			inputitems[4] = new ItemStack(Item.skull.itemID, 1, 0);
			break;
		default:
			inputitems[4] = null;
		}
		/*
		for (int k = 0; k < inputitems.length; k++) {
			ReikaChatHelper.writeItemStack(this.worldObj, inputitems[k]);
		}*/

		ItemStack output = this.setNBT(inputitems);
		if (par5Random.nextInt(2) == 0) {
			int dyeColor2 = this.pickRandomColor(this.consumeChance());
			if (dyeColor2 == -1)
				return output; //Bypass
			ItemStack newcolor = new ItemStack(Item.dyePowder.itemID, 1, dyeColor2);
			output = this.colorBlend(output, newcolor);
		}
		return output;
	}

	private ItemStack colorBlend(ItemStack base, ItemStack blendcolor) {
		ItemStack[] inputitems = new ItemStack[2];
		inputitems[0] = base;
		inputitems[1] = blendcolor;
		return this.setNBT(inputitems);
	}

	private ItemStack setNBT(ItemStack[] inputitems) {
		ItemStack field_92102_a = null;
		int var3 = 0;
		int var4 = 0;
		int var5 = 0;
		int var6 = 0;
		int var7 = 0;
		int var8 = 0;

		for (int var9 = 0; var9 < inputitems.length; ++var9)
		{
			ItemStack var10 = inputitems[var9];

			if (var10 != null)
			{
				if (var10.itemID == Item.gunpowder.itemID)
				{
					++var4;
				}
				else if (var10.itemID == Item.fireworkCharge.itemID)
				{
					++var6;
				}
				else if (var10.itemID == Item.dyePowder.itemID)
				{
					++var5;
				}
				else if (var10.itemID == Item.paper.itemID)
				{
					++var3;
				}
				else if (var10.itemID == Item.lightStoneDust.itemID)
				{
					++var7;
				}
				else if (var10.itemID == Item.diamond.itemID)
				{
					++var7;
				}
				else if (var10.itemID == Item.fireballCharge.itemID)
				{
					++var8;
				}
				else if (var10.itemID == Item.feather.itemID)
				{
					++var8;
				}
				else if (var10.itemID == Item.goldNugget.itemID)
				{
					++var8;
				}
				else
				{
					if (var10.itemID != Item.skull.itemID)
					{
						return field_92102_a;
					}

					++var8;
				}
			}
		}

		var7 += var5 + var8;

		if (var4 <= 3 && var3 <= 1)
		{
			NBTTagCompound var15;
			NBTTagCompound var18;

			if (var4 >= 1 && var3 == 1 && var7 == 0)
			{
				field_92102_a = new ItemStack(Item.firework);

				var15 = new NBTTagCompound();
				if (var6 > 0)
				{
					var18 = new NBTTagCompound("Fireworks");
					NBTTagList var25 = new NBTTagList("Explosions");

					for (int var22 = 0; var22 < inputitems.length; ++var22)
					{
						ItemStack var26 = inputitems[var22];

						if (var26 != null && var26.itemID == Item.fireworkCharge.itemID && var26.hasTagCompound() && var26.getTagCompound().hasKey("Explosion"))
						{
							var25.appendTag(var26.getTagCompound().getCompoundTag("Explosion"));
						}
					}

					var18.setTag("Explosions", var25);
					var18.setByte("Flight", (byte)var4);
					var15.setTag("Fireworks", var18);
				}

				field_92102_a.setTagCompound(var15);
				return field_92102_a;
			}
			else if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1)
			{
				field_92102_a = new ItemStack(Item.fireworkCharge);
				var15 = new NBTTagCompound();
				var18 = new NBTTagCompound("Explosion");
				byte var21 = 0;
				ArrayList var12 = new ArrayList();

				for (int var13 = 0; var13 < inputitems.length; ++var13)
				{
					ItemStack var14 = inputitems[var13];

					if (var14 != null)
					{
						if (var14.itemID == Item.dyePowder.itemID)
						{
							var12.add(Integer.valueOf(ItemDye.dyeColors[var14.getItemDamage()]));
						}
						else if (var14.itemID == Item.lightStoneDust.itemID)
						{
							var18.setBoolean("Flicker", true);
						}
						else if (var14.itemID == Item.diamond.itemID)
						{
							var18.setBoolean("Trail", true);
						}
						else if (var14.itemID == Item.fireballCharge.itemID)
						{
							var21 = 1;
						}
						else if (var14.itemID == Item.feather.itemID)
						{
							var21 = 4;
						}
						else if (var14.itemID == Item.goldNugget.itemID)
						{
							var21 = 2;
						}
						else if (var14.itemID == Item.skull.itemID)
						{
							var21 = 3;
						}
					}
				}

				int[] var24 = new int[var12.size()];

				for (int var27 = 0; var27 < var24.length; ++var27)
				{
					var24[var27] = ((Integer)var12.get(var27)).intValue();
				}

				var18.setIntArray("Colors", var24);
				var18.setByte("Type", var21);
				var15.setTag("Explosion", var18);
				field_92102_a.setTagCompound(var15);
				return field_92102_a;
			}
			else if (var4 == 0 && var3 == 0 && var6 == 1 && var5 > 0 && var5 == var7)
			{
				ArrayList var16 = new ArrayList();

				for (int var20 = 0; var20 < inputitems.length; ++var20)
				{
					ItemStack var11 = inputitems[var20];

					if (var11 != null)
					{
						if (var11.itemID == Item.dyePowder.itemID)
						{
							var16.add(Integer.valueOf(ItemDye.dyeColors[var11.getItemDamage()]));
						}
						else if (var11.itemID == Item.fireworkCharge.itemID)
						{
							field_92102_a = var11.copy();
							field_92102_a.stackSize = 1;
						}
					}
				}

				int[] var17 = new int[var16.size()];

				for (int var19 = 0; var19 < var17.length; ++var19)
				{
					var17[var19] = ((Integer)var16.get(var19)).intValue();
				}

				if (field_92102_a != null && field_92102_a.hasTagCompound())
				{
					NBTTagCompound var23 = field_92102_a.getTagCompound().getCompoundTag("Explosion");

					if (var23 == null)
					{
						return field_92102_a;
					}
					else
					{
						var23.setIntArray("FadeColors", var17);
						return field_92102_a;
					}
				}
				else
				{
					return field_92102_a;
				}
			}
			else
			{
				return field_92102_a;
			}
		}
		else
		{
			return field_92102_a;
		}
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return inventory[var1];
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);

		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
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
	public int getMachineIndex() {
		return MachineRegistry.FIREWORK.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return ReikaItemHelper.isFireworkIngredient(is.itemID);
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canCraftARocket())
			return 15;
		return 0;
	}

	@Override
	public boolean applyEnchants(ItemStack is) {
		boolean accepted = false;
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			enchantments.put(Enchantment.silkTouch, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch, is));
			accepted = true;
		}
		return accepted;
	}

	@Override
	public HashMap<Enchantment,Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantments().containsKey(e);
	}

	@Override
	public boolean hasEnchantments() {
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				if (this.getEnchantment(Enchantment.enchantmentsList[i]) > 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public int getEnchantment(Enchantment e) {
		if (!this.hasEnchantment(e))
			return 0;
		else
			return this.getEnchantments().get(e);
	}
}
