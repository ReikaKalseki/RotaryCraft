/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

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
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.Event.FireworkLaunchEvent;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFireworkMachine extends InventoriedPowerReceiver implements EnchantableMachine, DiscreteFunction, ConditionalOperation {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	public boolean idle = false;

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
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;
		if (!this.canCraftARocket()) {
			idle = true;
			return;
		}
		idle = false;
		ItemStack rocket = null;
		boolean hasStar = ReikaInventoryHelper.checkForItem(Item.fireworkCharge.itemID, inv);
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
					star = ReikaInventoryHelper.findAndDecrStack2(Item.fireworkCharge.itemID, -1, this.inv);
				else {
					int slot = ReikaInventoryHelper.locateInInventory(Item.fireworkCharge.itemID, this.inv);
					if (slot != -1)
						star = this.getStackInSlot(slot);
				}*/
				int slot = rand.nextInt(inv.length);
				while (inv[slot] == null || inv[slot].itemID != Item.fireworkCharge.itemID) {
					slot = rand.nextInt(inv.length);
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
		MinecraftForge.EVENT_BUS.post(new FireworkLaunchEvent(this, firework));
		//-------TEST CODE----------
		//EntityItem ent = new EntityItem(world, x, y+1, z, star);
		//world.spawnEntityInWorld(ent);

		if (this.hasEnchantments()) {
			for (int i = 0; i < 8; i++) {
				world.spawnParticle("portal", -0.5+x+2*rand.nextDouble(), y+rand.nextDouble(), -0.5+z+2*rand.nextDouble(), 0, 0, 0);
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
		int chance = rand.nextInt(excess/8);
		return (chance == 0);
	}

	private boolean canCraftARocket() {
		boolean haveDye = false;
		boolean have2Gunpowder = false;
		boolean havePaper = false;
		boolean haveStar = false;
		boolean have1Gunpowder = false;
		have1Gunpowder = ReikaInventoryHelper.checkForItem(Item.gunpowder.itemID, inv);
		haveStar = ReikaInventoryHelper.checkForItem(Item.fireworkCharge.itemID, inv);
		haveDye = ReikaInventoryHelper.checkForItem(Item.dyePowder.itemID, inv);
		havePaper = ReikaInventoryHelper.checkForItem(Item.paper.itemID, inv);
		int numgunpowder = 0;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (inv[i].itemID == Item.gunpowder.itemID) {
					numgunpowder += inv[i].stackSize;
					if (numgunpowder >= 2) {
						have2Gunpowder = true;
						i = inv.length;
					}
				}
			}
		}
		return (havePaper && (haveDye && have2Gunpowder) || (have1Gunpowder && haveStar));
	}

	private boolean canMakeRocketFromStar() {
		boolean hasPaper = ReikaInventoryHelper.checkForItem(Item.paper.itemID, inv);
		boolean hasGunpowder = ReikaInventoryHelper.checkForItem(Item.gunpowder.itemID, inv);
		boolean hasStar = ReikaInventoryHelper.checkForItem(Item.fireworkCharge.itemID, inv);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(hasPaper)+" "+String.valueOf(hasGunpowder)+" "+String.valueOf(hasStar));
		return (hasPaper && hasGunpowder && hasStar);
	}

	private ItemStack starToRocket(ItemStack star) {
		ItemStack product = null;
		ItemStack gunpowder = new ItemStack(Item.gunpowder.itemID, 1, 0);
		int numgunpowder = rand.nextInt(3)+1; // 1-3
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

	private ReikaDyeHelper pickRandomColor(boolean decr) { //Returns a random color dye from inv
		int color = -1;
		boolean[] hasColors = new boolean[16]; // To save CPU time, see below
		boolean hasDye = false;
		for (int i = 0; i < inv.length; i++) {
			ReikaDyeHelper dye = ReikaDyeHelper.getColorFromItem(inv[i]);
			if (dye != null) {
				hasDye = true;
				hasColors[dye.ordinal()] = true;
			}
		}
		if (!hasDye)
			return null;
		while (color == -1) {
			ReikaDyeHelper randcolor = ReikaDyeHelper.getRandomColor();
			while (!hasColors[randcolor.ordinal()])
				randcolor = ReikaDyeHelper.getRandomColor();
			for (int j = 0; j < inv.length; j++) {
				if (inv[j] != null) {
					ReikaDyeHelper dye2 = ReikaDyeHelper.getColorFromItem(inv[j]);
					if (dye2 == randcolor) {
						if (decr) {
							ReikaInventoryHelper.decrStack(j, inv);
						}
						return randcolor;
					}
				}
			}
		}
		return null;
	}

	private boolean getIngredient(int id, boolean decr) {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (inv[i].itemID == id) {
					if (decr) {
						ReikaInventoryHelper.decrStack(i, inv);
					}
					return true;
				}
			}
		}
		return false;
	}

	private int getShape() {
		boolean[] hasShape = new boolean[4];
		int shape = rand.nextInt(4);
		if (ReikaInventoryHelper.checkForItem(Item.fireballCharge.itemID, inv)) {
			hasShape[0] = true;
		}
		if (ReikaInventoryHelper.checkForItem(Item.goldNugget.itemID, inv)) {
			hasShape[1] = true;
		}
		if (ReikaInventoryHelper.checkForItem(Item.feather.itemID, inv)) {
			hasShape[2] = true;
		}
		if (ReikaInventoryHelper.checkForItem(Item.skull.itemID, inv)) {
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
			shape = rand.nextInt(4);
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
			ReikaInventoryHelper.findAndDecrStack(id, -1, inv);
		//else
		//return 0;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(shape));
		return shape+1;
	}

	private ItemStack randomRecipe() {
		ReikaDyeHelper dyeColor = this.pickRandomColor(this.consumeChance() && ReikaInventoryHelper.checkForItem(Item.gunpowder.itemID, inv)); //Dye metadata to craft with - 0-15
		boolean hasDiamond = false;
		boolean hasGlowstone = false;
		int shape = this.getShape(); //Shape modifiers - Fire charge, gold nugget, feather, head, or nothing
		ItemStack gunpowder = new ItemStack(Item.gunpowder.itemID, 1, 64);
		ItemStack diamond = new ItemStack(Item.diamond.itemID, 1, 0);
		ItemStack glowstone = new ItemStack(Item.glowstone.itemID, 1, 0);

		ItemStack[] inputitems = new ItemStack[5];
		if (dyeColor != null)
			inputitems[1] = new ItemStack(Item.dyePowder.itemID, 1, dyeColor.ordinal());
		else
			inputitems[1] = null;
		if (inputitems[1] == null) // If missing dye
			return null;
		if (this.getIngredient(Item.gunpowder.itemID, this.consumeChance()))
			inputitems[0] = gunpowder;
		if (inputitems[0] == null) // If missing gunpowder
			return null;
		if (rand.nextInt(2) == 0) {
			if (this.getIngredient(Item.diamond.itemID, this.consumeChance()))
				hasDiamond = true;
		}
		if (rand.nextInt(2) == 0) {
			if (this.getIngredient(Item.glowstone.itemID, this.consumeChance()))
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
		if (rand.nextInt(2) == 0) {
			ReikaDyeHelper dyeColor2 = this.pickRandomColor(this.consumeChance());
			if (dyeColor2 == null)
				return output; //Bypass
			ItemStack newcolor = new ItemStack(Item.dyePowder.itemID, 1, dyeColor2.ordinal());
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
				else if (var10.itemID == Item.glowstone.itemID)
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
						else if (var14.itemID == Item.glowstone.itemID)
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
		return 27;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				if (lvl > 0)
					NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FIREWORK;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
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

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList<Enchantment>();
		li.add(Enchantment.silkTouch);
		return li;
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

	@Override
	public int getOperationTime() {
		return DurationRegistry.FIREWORK.getOperationTime(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Items";
	}
}
