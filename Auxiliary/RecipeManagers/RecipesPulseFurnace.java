/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.ItemMaterialController;
import Reika.DragonAPI.Instantiable.ItemMaterial;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MekToolHandler.Materials;
import Reika.DragonAPI.ModInteract.ItemHandlers.MekToolHandler.Tools;
import Reika.DragonAPI.ModInteract.ItemHandlers.RedstoneArsenalHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ThaumItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.PulseFurnaceManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesPulseFurnace extends RecipeHandler implements PulseFurnaceManager {

	private static final RecipesPulseFurnace PulseFurnaceBase = new RecipesPulseFurnace();

	private ItemHashMap<PulseJetRecipe> recipes = new ItemHashMap();

	public static final RecipesPulseFurnace getRecipes()
	{
		return PulseFurnaceBase;
	}

	private RecipesPulseFurnace() {
		super(MachineRegistry.PULSEJET);
		RecipeInterface.pulsefurn = this;

		this.addSmelting(Blocks.obsidian, BlockRegistry.BLASTGLASS.getCraftedProduct(1), 850, RecipeLevel.CORE);
		this.addSmelting(Items.iron_ingot, ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 2), 900, RecipeLevel.CORE);

		this.addRecycling();

		this.addSmelting(ItemStacks.redgolddust, ItemStacks.redgoldingot, RecipeLevel.CORE);

		//addSmelting(RotaryCraft.shaftcraft, 10, new ItemStack(Items.iron_ingot, 1, 0));	//scrap
		//addSmelting(RotaryCraft.shaftcraft, 9, new ItemStack(RotaryCraft.shaftcraft, 1, 1));	//Iron scrap
	}

	public static class PulseJetRecipe implements MachineRecipe {

		private final ItemStack input;
		private final ItemStack output;

		public final int requiredTemperature;

		private PulseJetRecipe(ItemStack in, ItemStack out) {
			this(in, out, getDefaultMeltingTemp(in));
		}

		private PulseJetRecipe(ItemStack in, ItemStack out, int temp) {
			input = in;
			output = out;

			requiredTemperature = temp;
		}

		public ItemStack getInput() {
			return input.copy();
		}

		public ItemStack getOutput() {
			return output.copy();
		}

		public boolean makesItem(ItemStack is) {
			return ReikaItemHelper.matchStacks(is, output);
		}

		@Override
		public String getUniqueID() {
			return fullID(input)+">"+fullID(output);
		}

		@Override
		public String getAllInfo() {
			return "Smelting "+fullID(input)+" into "+fullID(output);
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(input, output);
		}

	}

	private void addRecycling() {
		this.addRecycling(Items.chainmail_helmet, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.chainmail_boots, new ItemStack(Items.iron_ingot, 2, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.chainmail_leggings, new ItemStack(Items.iron_ingot, 4, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.chainmail_chestplate, new ItemStack(Items.iron_ingot, 5, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.iron_helmet, new ItemStack(Items.iron_ingot, 5, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_chestplate, new ItemStack(Items.iron_ingot, 8, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_leggings, new ItemStack(Items.iron_ingot, 7, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_boots, new ItemStack(Items.iron_ingot, 4, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.iron_hoe, new ItemStack(Items.iron_ingot, 2, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_shovel, new ItemStack(Items.iron_ingot, 1, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_axe, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_pickaxe, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_sword, new ItemStack(Items.iron_ingot, 2, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.golden_helmet, new ItemStack(Items.gold_ingot, 5, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_chestplate, new ItemStack(Items.gold_ingot, 8, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_leggings, new ItemStack(Items.gold_ingot, 7, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_boots, new ItemStack(Items.gold_ingot, 4, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.golden_axe, new ItemStack(Items.gold_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_sword, new ItemStack(Items.gold_ingot, 2, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_shovel, new ItemStack(Items.gold_ingot, 1, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_pickaxe, new ItemStack(Items.gold_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_hoe, new ItemStack(Items.gold_ingot, 2, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.diamond_helmet, new ItemStack(Items.diamond, 5, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_chestplate, new ItemStack(Items.diamond, 8, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_leggings, new ItemStack(Items.diamond, 7, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_boots, new ItemStack(Items.diamond, 4, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.diamond_axe, new ItemStack(Items.diamond, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_sword, new ItemStack(Items.diamond, 2, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_shovel, new ItemStack(Items.diamond, 1, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_pickaxe, new ItemStack(Items.diamond, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_hoe, new ItemStack(Items.diamond, 2, 0), RecipeLevel.PROTECTED);

		this.addRecycling(Items.iron_horse_armor, new ItemStack(Items.iron_ingot, 7), RecipeLevel.PROTECTED);
		this.addRecycling(Items.diamond_horse_armor, new ItemStack(Items.diamond, 7), RecipeLevel.PROTECTED);
		this.addRecycling(Items.golden_horse_armor, new ItemStack(Items.gold_ingot, 7), RecipeLevel.PROTECTED);

		this.addRecycling(Items.flint_and_steel, new ItemStack(Items.iron_ingot, 1, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.bucket, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.water_bucket, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.lava_bucket, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.milk_bucket, new ItemStack(Items.iron_ingot, 3, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.minecart, new ItemStack(Items.iron_ingot, 5, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.iron_door, new ItemStack(Items.iron_ingot, 6, 0), RecipeLevel.PROTECTED);
		this.addRecycling(Items.cauldron, new ItemStack(Items.iron_ingot, 7, 0), RecipeLevel.PROTECTED);
		if (!Loader.isModLoaded("DragonRealmCore"))
			this.addRecycling(Items.hopper_minecart, new ItemStack(Items.iron_ingot, 10, 0), RecipeLevel.PROTECTED);

		this.addRecycling(ItemRegistry.STEELHELMET.getItemInstance(), this.getSizedSteel(5), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELBOOTS.getItemInstance(), this.getSizedSteel(4), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELCHEST.getItemInstance(), this.getSizedSteel(8), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELLEGS.getItemInstance(), this.getSizedSteel(7), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELAXE.getItemInstance(), this.getSizedSteel(3), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELPICK.getItemInstance(), this.getSizedSteel(3), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELSHOVEL.getItemInstance(), this.getSizedSteel(1), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELHOE.getItemInstance(), this.getSizedSteel(2), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELSHEARS.getItemInstance(), this.getSizedSteel(2), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELSICKLE.getItemInstance(), this.getSizedSteel(3), RecipeLevel.PROTECTED);
		this.addRecycling(ItemRegistry.STEELSWORD.getItemInstance(), this.getSizedSteel(2), RecipeLevel.PROTECTED);

		if (!ModList.RAILCRAFT.isLoaded()) { //exploit fix; he changes recipes
			this.addRecycling(Blocks.detector_rail, new ItemStack(Items.iron_ingot, 1, 0), RecipeLevel.PERIPHERAL);	//1 ingot per block of rail
			this.addRecycling(Blocks.golden_rail, new ItemStack(Items.gold_ingot, 1, 0), RecipeLevel.PERIPHERAL);
		}
	}

	private void addRecycling(Block in, ItemStack out, RecipeLevel rl) {
		this.addRecycling(new ItemStack(in), out, rl);
	}

	private void addRecycling(Item in, ItemStack out, RecipeLevel rl) {
		this.addRecycling(new ItemStack(in), out, rl);
	}

	private void addRecycling(ItemStack in, ItemStack out, RecipeLevel rl) {
		if (in.getItem() instanceof ItemSword || in.getItem() instanceof ItemTool || in.getItem() instanceof ItemArmor) {
			in = in.copy();
			in.setItemDamage(OreDictionary.WILDCARD_VALUE);
		}
		List<IRecipe> li = ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), in);
		if (li != null && li.size() > 1) {
			RotaryCraft.logger.log("Skipping recycling of "+this.fullID(in)+" to "+this.fullID(out)+" due to multiple production recipes");
			return;
		}
		PulseJetRecipe rec = this.addSmelting(in, out, rl);
	}

	private ItemStack getSizedSteel(int size) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, size);
	}

	public void addAPISmelting(ItemStack in, ItemStack itemstack) {
		this.addSmelting(in, itemstack, RecipeLevel.API);
	}

	public void addSmelting(ItemStack in, ItemStack itemstack) {
		this.addSmelting(in, itemstack, RecipeLevel.CORE);
	}

	private void addSmelting(ItemStack in, ItemStack itemstack, int temp, RecipeLevel rl) {
		PulseJetRecipe rec = new PulseJetRecipe(in, itemstack, temp);
		this.createRecipe(rec, rl);
	}

	private PulseJetRecipe addSmelting(ItemStack in, ItemStack itemstack, RecipeLevel rl) {
		PulseJetRecipe rec = new PulseJetRecipe(in, itemstack);
		this.createRecipe(rec, rl);
		return rec;
	}

	private void createRecipe(PulseJetRecipe rec, RecipeLevel rl) {
		recipes.put(rec.input, rec);
		this.onAddRecipe(rec, rl);
	}

	private void addSmelting(Block b, ItemStack itemstack, RecipeLevel rl) {
		this.addSmelting(new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE), itemstack, rl);
	}

	private void addSmelting(Block b, ItemStack itemstack, int temp, RecipeLevel rl) {
		this.addSmelting(new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE), itemstack, temp, rl);
	}

	private void addSmelting(Item i, ItemStack itemstack, RecipeLevel rl) {
		this.addSmelting(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), itemstack, rl);
	}

	private void addSmelting(Item i, ItemStack itemstack, int temp, RecipeLevel rl) {
		this.addSmelting(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), itemstack, temp, rl);
	}

	public void addCustomRecipe(ItemStack in, ItemStack output, int temp) {
		if (temp < 0)
			temp = getDefaultMeltingTemp(in);
		this.addSmelting(in, output, temp, RecipeLevel.CUSTOM);
	}

	public PulseJetRecipe getSmeltingResult(ItemStack item) {
		PulseJetRecipe ret = item != null ? recipes.get(item) : null;
		return ret;
	}

	public List<PulseJetRecipe> getSources(ItemStack result) {
		List<PulseJetRecipe> li = new ArrayList();
		for (ItemStack in : recipes.keySet()) {
			PulseJetRecipe out = this.getSmeltingResult(in);
			if (out != null && ReikaItemHelper.matchStacks(result, out.output))
				li.add(out);
		}
		return li;
	}

	public boolean isProduct(ItemStack item) {
		for (PulseJetRecipe pjr : recipes.values()) {
			if (pjr.makesItem(item))
				return true;
		}
		return false;
	}

	public boolean isSmeltable(ItemStack ingredient) {
		return this.getSmeltingResult(ingredient) != null;
	}

	public Collection<ItemStack> getAllSmeltables() {
		return Collections.unmodifiableCollection(recipes.keySet());
	}

	@Override
	public void addPostLoadRecipes() {
		if (ModList.THERMALFOUNDATION.isLoaded()) {
			ItemStack enderdust = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "dustEnderium", 1);
			ItemStack enderingot = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "ingotEnderium", 1);
			if (enderdust == null || enderingot == null)
				RotaryCraft.logger.logError("No item found for TE3 enderium crafting!");
			else
				this.addSmelting(enderdust, enderingot, RecipeLevel.MODINTERACT);
		}

		if (ModList.ARSENAL.isLoaded()) {
			ItemStack fluxdust = RedstoneArsenalHandler.getInstance().getFluxDust();
			ItemStack fluxingot = RedstoneArsenalHandler.getInstance().getFluxIngot();
			if (fluxdust == null || fluxingot == null)
				RotaryCraft.logger.logError("No item found for Redstone Arsenal fluxed ingot crafting!");
			else
				this.addSmelting(fluxdust, fluxingot, RecipeLevel.MODINTERACT);
		}

		if (ModList.IC2.isLoaded()) {
			ItemStack[] items = {
					IC2Handler.IC2Stacks.BRONZEAXE.getItem(),
					IC2Handler.IC2Stacks.BRONZEPICK.getItem(),
					IC2Handler.IC2Stacks.BRONZEHOE.getItem(),
					IC2Handler.IC2Stacks.BRONZESWORD.getItem(),
					IC2Handler.IC2Stacks.BRONZESHOVEL.getItem(),
					IC2Handler.IC2Stacks.BRONZEHELMET.getItem(),
					IC2Handler.IC2Stacks.BRONZECHESTPLATE.getItem(),
					IC2Handler.IC2Stacks.BRONZELEGGINGS.getItem(),
					IC2Handler.IC2Stacks.BRONZEBOOTS.getItem(),
			};

			int[] n = {
					3, 3, 2, 2, 1, 5, 8, 7, 4
			};

			ItemStack out = OreDictionary.getOres("ingotBronze").get(0);
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					this.addRecycling(items[i].getItem(), ReikaItemHelper.getSizedItemStack(out, n[i]), RecipeLevel.MODINTERACT);
				}
			}
		}

		if (ModList.RAILCRAFT.isLoaded()) {
			Object[] items = {
					"tool.steel.pickaxe",
					"tool.steel.axe",
					"tool.steel.sword",
					"tool.steel.hoe",
					"tool.steel.shovel",
					"armor.steel.helmet",
					"armor.steel.plate",
					"armor.steel.legs",
					"armor.steel.boots",
					"tool.steel.shears",
					"tool.crowbar",
					"tool.crowbar.reinforced"
			};

			int[] n = {
					3, 3, 2, 2, 1, 5, 8, 7, 4, 2, 3, 3
			};

			for (int i = 0; i < items.length; i++) {
				items[i] = ReikaItemHelper.lookupItem(ModList.RAILCRAFT, (String)items[i], 0);
			}

			ItemStack out = ReikaItemHelper.lookupItem(ModList.RAILCRAFT, "ingot", 0);
			if (out == null) {
				List<ItemStack> li = OreDictionary.getOres("ingotSteel");
				out = li.isEmpty() ? ItemStacks.steelingot.copy() : li.get(0);
			}
			ItemStack[] outarr = ReikaArrayHelper.getArrayOf(out, items.length);
			outarr[outarr.length-2] = new ItemStack(Items.iron_ingot);

			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					ItemStack outb = outarr[i];
					this.addRecycling(((ItemStack)items[i]).getItem(), ReikaItemHelper.getSizedItemStack(outb, n[i]), RecipeLevel.MODINTERACT);
				}
			}
		}

		if (ModList.ENDERIO.isLoaded()) {
			Object[] items = {
					"item.darkSteel_pickaxe",
					"item.darkSteel_axe",
					"item.darkSteel_sword",
					"item.darkSteel_helmet",
					"item.darkSteel_chestplate",
					"item.darkSteel_leggings",
					"item.darkSteel_boots",
					"item.darkSteel_shears",
			};

			int[] n = {
					3, 3, 2, 5, 8, 7, 4, 2
			};

			for (int i = 0; i < items.length; i++) {
				items[i] = ReikaItemHelper.lookupItem(ModList.ENDERIO, (String)items[i], 0);
			}

			ItemStack out = ReikaItemHelper.lookupItem(ModList.ENDERIO, "itemAlloy", 6);
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					this.addRecycling(((ItemStack)items[i]).getItem(), ReikaItemHelper.getSizedItemStack(out, n[i]), RecipeLevel.MODINTERACT);
				}
			}
		}

		if (ModList.THAUMCRAFT.isLoaded()) {
			Object[] items = {
					"ItemPickThaumium",
					"ItemAxeThaumium",
					"ItemSwordThaumium",
					"ItemHoeThaumium",
					"ItemShovelThaumium",
					"ItemHelmetThaumium",
					"ItemChestplateThaumium",
					"ItemLeggingsThaumium"
			};

			int[] n = {
					3, 3, 2, 2, 1, 5, 8, 7
			};

			for (int i = 0; i < items.length; i++) {
				items[i] = ReikaItemHelper.lookupItem(ModList.THAUMCRAFT, (String)items[i], 0);
			}

			ItemStack out = ThaumItemHelper.ItemEntry.THAUMIUM.getItem();
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					this.addRecycling(((ItemStack)items[i]).getItem(), ReikaItemHelper.getSizedItemStack(out, n[i]), RecipeLevel.MODINTERACT);
				}
				else {
					RotaryCraft.logger.logError("Could not find thaumium tool #"+i);
				}
			}
		}

		if (ModList.MEKTOOLS.isLoaded()) {
			for (Materials m : Materials.values()) {
				ItemStack out = m.getRawMaterial();
				for (Tools t : Tools.values()) {
					Item i = m.getItem(t);
					if (i != null) {
						this.addRecycling(i, ReikaItemHelper.getSizedItemStack(out, t.getNumberIngots(m)), RecipeLevel.MODINTERACT);
					}
				}
			}
		}
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipes.removeValue((PulseJetRecipe)recipe);
	}

	@Override
	protected boolean addCustomRecipe(String n, LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), lb.getChild("input_nbt"), false);
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		int temp = getDefaultMeltingTemp(in);
		if (lb.containsKey("required_temperature")) {
			temp = lb.getInt("required_temperature");
			if (temp <= 200) {
				throw new IllegalArgumentException("Temperature is too low");
			}
			else if (temp >= TileEntityPulseFurnace.MAXTEMP) {
				throw new IllegalArgumentException("Recipe is impossible - temperature exceeds maximum");
			}
		}
		this.verifyOutputItem(out);
		this.addCustomRecipe(in, out, temp);
		return true;
	}

	private static int getDefaultMeltingTemp(ItemStack is) {
		if (ItemMaterialController.instance.getMaterial(is) == ItemMaterial.OBSIDIAN)
			return ItemMaterialController.instance.getMeltingPoint(is);
		return MathHelper.clamp_int(ItemMaterialController.instance.getMeltingPoint(is)/2, 400, 850);
	}
}
