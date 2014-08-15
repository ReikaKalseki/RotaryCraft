/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityComposter extends InventoriedRCTileEntity implements TemperatureTE {

	private int temperature;
	private StepTimer tempTimer = new StepTimer(20);
	private StepTimer timer = new StepTimer(100);
	public int composterCookTime;

	public static final int MINTEMP = 40;
	public static final int MAXTEMP = 70;

	private static enum CompostMatter {

		CRAP(1, Items.egg, Items.cookie, Items.wheat),
		SUGARCANE(2, Items.reeds),
		PLANT(1, Blocks.sapling, Blocks.waterlily, Blocks.red_flower, Blocks.yellow_flower, Blocks.brown_mushroom, Blocks.red_mushroom),
		LEAF(2, Blocks.leaves),
		MEAT(4, Items.beef, Items.cooked_beef, Items.cooked_porkchop, Items.porkchop, Items.cooked_chicken, Items.chicken),
		FISH(3, Items.cooked_fished, Items.fish),
		VEGGIE(2, Items.potato, Items.carrot, Items.baked_potato, Items.poisonous_potato, Items.bread, Items.apple, Items.melon),
		MOBS(3, Items.rotten_flesh, Items.spider_eye);
		//podzol for 1.7

		private ArrayList<ItemStack> items = new ArrayList();
		public final int value;

		public static final CompostMatter[] list = values();

		private CompostMatter(int value, Block... items) {
			this.value = value;
			for (int i = 0; i < items.length; i++)
				this.items.add(new ItemStack(items[i]));
		}

		private CompostMatter(int value, Item... items) {
			this.value = value;
			for (int i = 0; i < items.length; i++)
				this.items.add(new ItemStack(items[i]));
		}

		private CompostMatter(int value, ItemStack... items) {
			this.value = value;
			for (int i = 0; i < items.length; i++)
				this.items.add(items[i]);
		}

		public static CompostMatter getMatterType(ItemStack is) {
			for (int i = 0; i < list.length; i++) {
				CompostMatter c = list[i];
				if (ReikaItemHelper.listContainsItemStack(c.items, is))
					return c;
			}
			return null;
		}

		public List<ItemStack> getAllItems() {
			return ReikaJavaLibrary.copyList(items);
		}
	}

	public static final ArrayList<ItemStack> getAllCompostables() {
		ArrayList<ItemStack> items = new ArrayList();
		for (int i = 0; i < CompostMatter.list.length; i++) {
			items.addAll(CompostMatter.list[i].getAllItems());
		}
		return items;
	}

	public static int getCompostValue(ItemStack is) {
		CompostMatter c = CompostMatter.getMatterType(is);
		return c != null ? c.value : 0;
	}

	public int getScaledTimer(int a) {
		return a*composterCookTime/timer.getCap();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tempTimer.update();
		if (tempTimer.checkCap()) {
			this.updateTemperature(world, x, y, z, meta);
		}

		int value = this.getCompostValue();
		if (value <= 0)
			return;

		int time = 1+(temperature-40)/4;
		timer.update(time);
		if (timer.checkCap()) {
			ReikaInventoryHelper.decrStack(0, inv);
			ReikaInventoryHelper.addOrSetStack(ReikaItemHelper.getSizedItemStack(ItemStacks.compost, value), inv, 2);
			if (rand.nextInt(75-temperature) == 0)
				ReikaInventoryHelper.decrStack(1, inv);
			composterCookTime = 0;
		}
		composterCookTime = timer.getTick();
	}

	private int getCompostValue() {
		if (temperature < MINTEMP || temperature > MAXTEMP)
			return 0;
		if (inv[0] == null || inv[1] == null)
			return 0;
		if (inv[1].getItem() != ItemRegistry.YEAST.getItemInstance())
			return 0;
		CompostMatter c = CompostMatter.getMatterType(inv[0]);
		if (c == null)
			return 0;
		if (inv[2] == null)
			return c.value;
		if (!ReikaItemHelper.matchStacks(inv[2], ItemStacks.compost))
			return 0;
		return inv[2].stackSize+c.value <= inv[2].getMaxStackSize() ? c.value : 0;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == 2)
			return false;
		if (i == 1)
			return itemstack.getItem() == ItemRegistry.YEAST.getItemInstance();
		return CompostMatter.getMatterType(itemstack) != null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return ReikaItemHelper.matchStacks(itemstack, ItemStacks.compost);
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		ForgeDirection waterside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.water);
		if (waterside != null) {
			Tamb -= 5;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
		if (iceside != null) {
			Tamb -= 15;
		}
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.fire);
		if (fireside != null) {
			Tamb += 50;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.lava);
		if (lavaside != null) {
			Tamb += 200;
		}
		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.COMPOSTER;
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
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");

		composterCookTime = NBT.getInteger("timer");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);

		NBT.setInteger("timer", composterCookTime);
	}

}