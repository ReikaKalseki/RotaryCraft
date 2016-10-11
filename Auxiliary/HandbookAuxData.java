/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IIcon;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Auxiliary.Trackers.PackModificationTracker;
import Reika.DragonAPI.Auxiliary.Trackers.PackModificationTracker.PackModification;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Instantiable.Alert;
import Reika.DragonAPI.Instantiable.ItemReq;
import Reika.DragonAPI.Instantiable.Data.Maps.ArrayMap;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.MachineRecipeRenderer;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.MulchMaterials;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastFurnacePattern;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Items.Tools.ItemJetPack.PackUpgrades;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MobBait;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer.BiomeTransform;

import com.google.common.collect.TreeMultimap;

public final class HandbookAuxData {
	/** One GuiHandbook.SECOND in nanoGuiHandbook.SECONDs. */
	private static int tickcount;

	private static final ReikaGuiAPI api = ReikaGuiAPI.instance;

	public static List<IRecipe> getWorktable() {
		return WorktableRecipes.getInstance().getDisplayList();
	}

	private static final ArrayList<Object[][]> extracts = new ArrayList();
	private static final ArrayList<ItemStack> flakes = new ArrayList();
	private static final ArrayList<ItemStack[]> fermenter = new ArrayList();

	private static final ArrayMap<HandbookRegistry> tabMappings = new ArrayMap(2);

	private static final TreeMultimap<Long, MachineRegistry> powerData = TreeMultimap.create();
	private static final TreeMultimap<Integer, MachineRegistry> torqueData = TreeMultimap.create();
	private static final TreeMultimap<Integer, MachineRegistry> speedData = TreeMultimap.create();

	static {
		load();

		mapHandbook();
	}

	private static void load() {
		addFlakes();
		addExtracts();
		addPlants();

		addPowerData();
	}

	public static void addPowerData() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList.get(i);
			if (!m.isDummiedOut()) {
				if (m.isPowerReceiver() && !m.isModConversionEngine() && !m.isPoweredTransmissionMachine()) {
					PowerReceivers p = m.getPowerReceiverEntry();
					if (p != null) {
						long minp = p.getMinPowerForDisplay();
						int mint = p.getMinTorqueForDisplay();
						int mins = p.getMinSpeedForDisplay();

						powerData.put(minp, m);
						torqueData.put(mint, m);
						speedData.put(mins, m);
					}
					else
						throw new RegistrationException(RotaryCraft.instance, "Machine "+m+" is a power receiver but has no power Enum!");
				}
			}
		}
	}

	private static void mapHandbook() {
		for (int i = 0; i < HandbookRegistry.tabList.length; i++) {
			HandbookRegistry h = HandbookRegistry.tabList[i];
			tabMappings.putV(h, h.getScreen(), h.getPage());
		}
	}

	public static HandbookRegistry getMapping(int screen, int page) {
		return tabMappings.getV(screen, page);
	}

	public static void reload() {
		load();
	}

	private static void addFlakes() {
		for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
			flakes.add(ReikaOreHelper.oreList[i].getResource());
		}
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			flakes.add(ItemStacks.getModOreIngot(ModOreList.oreList[i]));
		}
	}

	private static void addExtracts() {
		ArrayList<ItemStack> modores = ModOreList.getAllRegisteredOreBlocks();
		int num = ReikaOreHelper.oreList.length+modores.size();
		for (int k = 0; k < num; k++) {
			boolean van = k < ReikaOreHelper.oreList.length;
			String oreName;
			ItemStack[] in = new ItemStack[4];
			ItemStack[] out = new ItemStack[4];
			if (van) {
				in[0] = ReikaOreHelper.oreList[k].getOreBlock();
				in[1] = ItemRegistry.EXTRACTS.getStackOfMetadata(k);
				in[2] = ItemRegistry.EXTRACTS.getStackOfMetadata(k+8);
				in[3] = ItemRegistry.EXTRACTS.getStackOfMetadata(k+16);

				out[0] = ItemRegistry.EXTRACTS.getStackOfMetadata(k);
				out[1] = ItemRegistry.EXTRACTS.getStackOfMetadata(k+8);
				out[2] = ItemRegistry.EXTRACTS.getStackOfMetadata(k+16);
				out[3] = ItemRegistry.EXTRACTS.getStackOfMetadata(k+24);

				oreName = ReikaOreHelper.oreList[k].getName();
			}
			else {
				int i = k-ReikaOreHelper.oreList.length;
				//DragonAPICore.logIf(modores.get(i)+" at "+i+" ("+""+")");
				ItemStack is = modores.get(i);
				ModOreList ore = ModOreList.getModOreFromOre(is);
				if (ore == null) {
					DragonAPICore.logError("ItemStack "+is.getDisplayName()+" ("+is.getItem()+":"+is.getItemDamage()+")");
					DragonAPICore.logError("has no mod ore list entry, yet was registered as such during load!");
					DragonAPICore.logError("Contact both mod developers immediately!");
					oreName = "ERROR";
				}
				else {
					in[0] = modores.get(i);
					in[1] = ExtractorModOres.getDustProduct(ore);
					in[2] = ExtractorModOres.getSlurryProduct(ore);
					in[3] = ExtractorModOres.getSolutionProduct(ore);

					out[0] = ExtractorModOres.getDustProduct(ore);
					out[1] = ExtractorModOres.getSlurryProduct(ore);
					out[2] = ExtractorModOres.getSolutionProduct(ore);
					out[3] = ExtractorModOres.getFlakeProduct(ore);

					oreName = ore.displayName;
				}
			}
			Object[][] obj = {in, out, new String[]{oreName}};
			extracts.add(obj);
			//RotaryCraft.logger.log("Adding extractor entry to handbook: "+oreName+" "+Arrays.toString(in)+";"+Arrays.toString(out));
		}
	}

	private static void addPlants() {
		ItemStack out;
		ItemStack[] in = new ItemStack[2];
		ItemStack[] args;
		if (ConfigRegistry.enableFermenterYeast()) {
			out = (ItemRegistry.YEAST.getStackOf());
			in = new ItemStack[]{new ItemStack(Items.sugar), new ItemStack(Blocks.dirt)};
			args = new ItemStack[]{out, in[0], in[1]};
			fermenter.add(args);
		}

		Collection<ItemStack> li = MulchMaterials.instance.getAllValidPlants();
		for (ItemStack plant : li) {
			int num = MulchMaterials.instance.getPlantValue(plant);
			out = ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, num);
			fermenter.add(new ItemStack[]{out, ItemRegistry.YEAST.getStackOf(), plant});
		}
	}

	public static void drawPage(FontRenderer f, RenderItem ri, int screen, int page, int subpage, int dx, int dy) {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h.isMachine() || h.isTrans() || h.isEngine()) {
			List<ItemStack> out = h.getCrafting();
			if (out == null || out.size() <= 0)
				return;
			if (h.isCustomRecipe()) {
				api.drawCustomRecipes(ri, f, out, getWorktable(), dx+72-18, dy+18, dx-1620, dy+32);
			}
			else {
				api.drawCustomRecipes(ri, f, out, CraftingManager.getInstance().getRecipeList(), dx+72-18, dy+18, dx-1620, dy+32);
			}
		}
		else if (h.isCrafting()) {
			List<ItemStack> out = h.getCrafting();
			if (out == null || out.size() <= 0)
				return;
			if (h.isCustomRecipe()) {
				api.drawCustomRecipes(ri, f, out, getWorktable(), dx+72, dy+18, dx+162, dy+32);
			}
			else {
				api.drawCustomRecipes(ri, f, out, CraftingManager.getInstance().getRecipeList(), dx+72, dy+18, dx+162, dy+32);
			}
		}
		else if (h.isSmelting()) {
			ItemStack out = h.getSmelting();
			api.drawSmelting(ri, f, out, dx+87, dy+36, dx+141, dy+32);
			if (h == HandbookRegistry.TUNGSTEN) {
				api.drawItemStackWithTooltip(ri, f, ItemStacks.tungstenflakes, dx+87, dy+28);
			}
		}
		else if (h == HandbookRegistry.EXTRACTS) {
			int time = 1000000000;
			int k = (int)((System.nanoTime()/time)%(extracts.size()));
			Object[][] obj = extracts.get(k);
			ItemStack[] in = (ItemStack[])obj[0];
			ItemStack[] out = (ItemStack[])obj[1];
			String oreName = (String)obj[2][0];

			MachineRecipeRenderer.instance.drawExtractor(dx+66, dy+17, in, dx+66, dy+59, out);
			String[] words = oreName.split(" ");
			for (int i = 0; i < words.length; i++)
				f.drawString(words[i], dx+194, dy+60+f.FONT_HEIGHT*i-words.length*f.FONT_HEIGHT/2, 0);
		}
		else if (h == HandbookRegistry.FLAKES) {
			ItemStack in;
			int time = (int)((System.nanoTime()/1000000000)%flakes.size());
			boolean van = time < ReikaOreHelper.oreList.length;
			int i = time-ReikaOreHelper.oreList.length;
			String oreName;
			if (van) {
				in = ItemRegistry.EXTRACTS.getStackOfMetadata(time+24);
				oreName = ReikaOreHelper.oreList[time].getName();
			}
			else {
				in = ExtractorModOres.getFlakeProduct(ModOreList.oreList[i]);
				oreName = ModOreList.oreList[i].displayName;
			}
			api.drawItemStackWithTooltip(ri, f, in, dx+87, dy+28);
			api.drawItemStackWithTooltip(ri, f, flakes.get(time), dx+145, dy+28);

			String[] words = oreName.split(" ");
			for (int k = 0; k < words.length; k++)
				f.drawString(words[k], dx+168, dy+36+f.FONT_HEIGHT*k-words.length*f.FONT_HEIGHT/2, 0);
		}
		else if (h == HandbookRegistry.COMPACTS) {
			ItemStack in = new ItemStack(Items.coal);
			ItemStack out = new ItemStack(Items.diamond, 2, 0);
			int k = (int)((System.nanoTime()/2000000000)%4);
			if (k != 0)
				in = ItemRegistry.COMPACTS.getCraftedMetadataProduct(1, k-1);
			if (k != 3)
				out = ItemRegistry.COMPACTS.getCraftedMetadataProduct(2, k);
			MachineRecipeRenderer.instance.drawCompressor(dx+66, dy+14, in, dx+120, dy+41, out);
		}
		else if (h == HandbookRegistry.GLASS) {
			api.drawItemStackWithTooltip(ri, f, new ItemStack(Blocks.obsidian), dx+87, dy+28);
			api.drawItemStackWithTooltip(ri, f, BlockRegistry.BLASTGLASS.getStackOf(), dx+145, dy+28);
		}
		else if (h == HandbookRegistry.JETPACK) {
			int k = (int)((System.nanoTime()/6000000000L)%5);
			int k2 = (int)((System.nanoTime()/3000000000L)%2);
			int k3 = (int)((System.nanoTime()/2000000000L)%3);
			if (k == 0) {
				ItemStack out = ItemRegistry.JETPACK.getEnchantedStack();
				ArrayList li = ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), out);
				api.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
			}
			else if (k == 1) {
				ItemStack plate = k2 == 0 ? ItemRegistry.STEELCHEST.getStackOf() : ItemRegistry.BEDCHEST.getEnchantedStack();
				ItemStack out = k2 == 0 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				api.drawItemStackWithTooltip(ri, f, plate, dx+72, dy+10);
				api.drawItemStackWithTooltip(ri, f, ItemRegistry.JETPACK.getStackOf(), dx+90, dy+10);
				api.drawItemStackWithTooltip(ri, f, out, dx+166, dy+28);
			}
			else if (k == 2) { //wing
				ItemStack ing = k3 != 2 ? ItemStacks.steelingot : ItemStacks.bedingot;
				ItemStack pack = k3 == 0 ? ItemRegistry.JETPACK.getStackOf() : k3 == 1 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				ItemStack out = k3 == 0 ? ItemRegistry.JETPACK.getStackOf() : k3 == 1 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				PackUpgrades.WING.enable(out, true);
				api.drawItemStackWithTooltip(ri, f, ing, dx+72, dy+10);
				api.drawItemStackWithTooltip(ri, f, ing, dx+90, dy+10);
				api.drawItemStackWithTooltip(ri, f, ing, dx+108, dy+10);
				api.drawItemStackWithTooltip(ri, f, pack, dx+90, dy+28);
				api.drawItemStack(ri, f, out, dx+166, dy+28);
				api.drawMultilineTooltip(out, dx+166, dy+28);
			}
			else if (k == 3) { //cooling
				ItemStack pack = k3 == 0 ? ItemRegistry.JETPACK.getStackOf() : k3 == 1 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				ItemStack out = k3 == 0 ? ItemRegistry.JETPACK.getStackOf() : k3 == 1 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				PackUpgrades.COOLING.enable(out, true);
				api.drawItemStackWithTooltip(ri, f, MachineRegistry.COOLINGFIN.getCraftedProduct(), dx+72, dy+28);
				api.drawItemStackWithTooltip(ri, f, MachineRegistry.COOLINGFIN.getCraftedProduct(), dx+108, dy+28);
				api.drawItemStackWithTooltip(ri, f, pack, dx+90, dy+28);
				api.drawItemStack(ri, f, out, dx+166, dy+28);
				api.drawMultilineTooltip(out, dx+166, dy+28);

			}
			else if (k == 4) { //thrust boost
				ItemStack pack = k3 == 0 ? ItemRegistry.JETPACK.getStackOf() : k3 == 1 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				ItemStack out = k3 == 0 ? ItemRegistry.JETPACK.getStackOf() : k3 == 1 ? ItemRegistry.STEELPACK.getStackOf() : ItemRegistry.BEDPACK.getEnchantedStack();
				PackUpgrades.JET.enable(out, true);
				api.drawItemStackWithTooltip(ri, f, EngineType.JET.getCraftedProduct(), dx+90, dy+46);
				api.drawItemStackWithTooltip(ri, f, pack, dx+90, dy+28);
				api.drawItemStack(ri, f, out, dx+166, dy+28);
				api.drawMultilineTooltip(out, dx+166, dy+28);
			}
		}
		else if (h == HandbookRegistry.JUMPBOOTS) {
			int k = (int)((System.nanoTime()/2000000000)%2);
			if (k == 0) {
				ItemStack out = ItemRegistry.JUMP.getStackOf();
				List li = ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), out);
				api.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
			}
			else {
				api.drawItemStackWithTooltip(ri, f, ItemRegistry.BEDBOOTS.getEnchantedStack(), dx+72, dy+10);
				api.drawItemStackWithTooltip(ri, f, ItemRegistry.JUMP.getStackOf(), dx+90, dy+10);
				api.drawItemStackWithTooltip(ri, f, ItemRegistry.BEDJUMP.getEnchantedStack(), dx+166, dy+28);
			}
		}
		else if (h == HandbookRegistry.YEAST) {
			int k = (int)((System.nanoTime()/1000000000)%fermenter.size());
			ItemStack[] args = fermenter.get(k);
			ItemStack[] in = new ItemStack[]{args[1], args[2]};
			ItemStack out = args[0];
			MachineRecipeRenderer.instance.drawFermenter(dx+102, dy+18, in, dx+159, dy+32, out);
		}
		else if (h == HandbookRegistry.NETHERDUST) {
			if ((System.nanoTime()/2000000000)%2 == 0) {
				api.drawItemStackWithTooltip(ri, f, new ItemStack(Blocks.netherrack), dx+87, dy+28);
				api.drawItemStackWithTooltip(ri, f, ItemStacks.netherrackdust, dx+145, dy+28);
			}
			else {
				api.drawItemStackWithTooltip(ri, f, new ItemStack(Blocks.soul_sand), dx+87, dy+28);
				api.drawItemStackWithTooltip(ri, f, ItemStacks.tar, dx+145, dy+28);
			}
		}
		else if (h == HandbookRegistry.SILVERINGOT) {
			api.drawItemStackWithTooltip(ri, f, ItemStacks.silverflakes, dx+87, dy+28);
			api.drawItemStackWithTooltip(ri, f, ItemStacks.silveringot, dx+145, dy+28);
		}
		else if (h == HandbookRegistry.SALT) {
			api.drawItemStackWithTooltip(ri, f, new ItemStack(Items.water_bucket), dx+90, dy+28);
			api.drawItemStackWithTooltip(ri, f, ItemStacks.salt, dx+166, dy+28);
		}
		else if (h == HandbookRegistry.SAWDUST) {
			int k = (int)((System.nanoTime()/2000000000)%5);
			switch (k) {
				case 0:
					api.drawItemStackWithTooltip(ri, f, new ItemStack(Items.water_bucket), dx+72+18, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+36, dy+28);
					api.drawItemStackWithTooltip(ri, f, new ItemStack(Blocks.stone), dx+72, dy+46);
					api.drawItemStackWithTooltip(ri, f, new ItemStack(Blocks.stone), dx+72+18, dy+46);
					api.drawItemStackWithTooltip(ri, f, new ItemStack(Blocks.stone), dx+72+36, dy+46);
					api.drawItemStackWithTooltip(ri, f, new ItemStack(Items.paper, 8, 0), dx+166, dy+28);
					break;
				case 1:
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
					api.drawItemStackWithTooltip(ri, f, ReikaItemHelper.oakWood, dx+166, dy+28);
					break;
				case 2:
					api.drawItemStackWithTooltip(ri, f, ReikaDyeHelper.BLACK.getStackOf(), dx+72+36, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
					api.drawItemStackWithTooltip(ri, f, ReikaItemHelper.spruceWood, dx+166, dy+28);
					break;
				case 3:
					api.drawItemStackWithTooltip(ri, f, ReikaDyeHelper.WHITE.getStackOf(), dx+72+36, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
					api.drawItemStackWithTooltip(ri, f, ReikaItemHelper.birchWood, dx+166, dy+28);
					break;
				case 4:
					api.drawItemStackWithTooltip(ri, f, ReikaDyeHelper.RED.getStackOf(), dx+72+36, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
					api.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
					api.drawItemStackWithTooltip(ri, f, ReikaItemHelper.jungleWood, dx+166, dy+28);
					break;
			}
		}
		else if (h == HandbookRegistry.RAILGUNAMMO) {
			List<IRecipe> li = new ArrayList<IRecipe>();
			for (int i = 0; i < ItemRegistry.RAILGUN.getNumberMetadatas(); i++) {
				li.addAll(ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), ItemRegistry.RAILGUN.getStackOfMetadata(i)));
			}
			api.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
		}
		else if (h == HandbookRegistry.BEDTOOLS) {
			ArrayList<ItemStack> li = new ArrayList();
			for (int i = 0; i < ItemRegistry.itemList.length; i++) {
				ItemRegistry ir = ItemRegistry.itemList[i];
				if (ir.isBedrockTool() && !ir.isDummiedOut()) {
					li.add(ir.getEnchantedStack());
				}
			}
			int index = (int)((System.currentTimeMillis()/2000)%li.size());
			ItemStack is = li.get(index);
			MachineRecipeRenderer.instance.drawBlastFurnaceCrafting(dx+99, dy+18, dx+181, dy+32, is);
		}
		else if (h == HandbookRegistry.BEDARMOR) {
			ArrayList<ItemStack> li = new ArrayList();
			for (int i = 0; i < ItemRegistry.itemList.length; i++) {
				ItemRegistry ir = ItemRegistry.itemList[i];
				if (ir.isBedrockArmor()) {
					li.add(ir.getEnchantedStack());
				}
			}
			int index = (int)((System.currentTimeMillis()/2000)%li.size());
			ItemStack is = li.get(index);
			MachineRecipeRenderer.instance.drawBlastFurnaceCrafting(dx+99, dy+18, dx+181, dy+32, is);
		}
		else if (h == HandbookRegistry.STRONGSPRING) {
			ItemStack is = ItemRegistry.STRONGCOIL.getStackOf();
			MachineRecipeRenderer.instance.drawBlastFurnaceCrafting(dx+99, dy+18, dx+181, dy+32, is);
		}
		//else if (h == HandbookRegistry.BEDINGOT) {
		//	ItemStack is = ItemStacks.bedingot;
		//	List<BlastRecipe> c = RecipesBlastFurnace.getRecipes().getAllRecipesMaking(is);
		//	MachineRecipeRenderer.instance.drawBlastFurnace(dx+99, dy+18, dx+185, dy+36, c.get(0));
		//}
		else if (h == HandbookRegistry.ALLOYING) {
			List<BlastFurnacePattern> c = RecipesBlastFurnace.getRecipes().getAllAlloyingRecipes();
			int index = (int)((System.currentTimeMillis()/2000)%c.size());
			BlastFurnacePattern p = c.get(index);
			if (p instanceof BlastRecipe)
				MachineRecipeRenderer.instance.drawBlastFurnace(dx+99, dy+18, dx+185, dy+36, (BlastRecipe)p);
			else if (p instanceof BlastCrafting)
				MachineRecipeRenderer.instance.drawBlastFurnaceCrafting(dx+99, dy+18, dx+181, dy+32, (BlastCrafting)p);
			else
				DragonAPICore.logError(p+" to make "+p.outputItem()+" is an invalid (unrenderable) recipe!");
			api.drawCenteredStringNoShadow(f, p.getRequiredTemperature()+"C", dx+54, dy+66, 0);
		}
		else if (h == HandbookRegistry.COKE) {
			List<BlastRecipe> c = RecipesBlastFurnace.getRecipes().getAllRecipesMaking(ItemStacks.coke);
			int index = (int)((System.currentTimeMillis()/2000)%c.size());
			BlastRecipe p = c.get(index);
			MachineRecipeRenderer.instance.drawBlastFurnace(dx+99, dy+18, dx+185, dy+36, p);
			api.drawCenteredStringNoShadow(f, p.temperature+"C", dx+54, dy+66, 0);
		}
		else if (h == HandbookRegistry.STEELINGOT) {
			ItemStack is = ItemStacks.steelingot;
			List<BlastRecipe> c = RecipesBlastFurnace.getRecipes().getAllRecipesMaking(is);
			int index = (int)((System.currentTimeMillis()/2000)%c.size());
			MachineRecipeRenderer.instance.drawBlastFurnace(dx+99, dy+18, dx+185, dy+36, c.get(index));
		}
	}

	public static void drawGraphics(HandbookRegistry h, int posX, int posY, int xSize, int ySize, FontRenderer font, RenderItem item, int subpage) {
		try {
			if (h == HandbookRegistry.TERMS) {
				int xc = posX+xSize/2; int yc = posY+43; int r = 35;
				api.drawCircle(xc, yc, r, 0);
				api.drawLine(xc, yc, xc+r, yc, 0);
				api.drawLine(xc, yc, (int)(xc+r-0.459*r), (int)(yc-0.841*r), 0);/*
    		for (float i = 0; i < 1; i += 0.1)
    			api.drawLine(xc, yc, (int)(xc+Math.cos(i)*r), (int)(yc-Math.sin(i)*r), 0x000000);*/
				String s = "One radian";
				font.drawString(s, xc+r+10, yc-4, 0x000000);
			}
			else if (h == HandbookRegistry.PHYSICS) {
				int r = 5;
				int xc = posX+xSize/8;
				int yc = posY+45;
				api.drawCircle(xc, yc, r, 0);
				api.drawLine(xc, yc, xc+45, yc, 0x0000ff);
				api.drawLine(xc+45, yc, xc+45, yc+20, 0xff0000);
				api.drawLine(xc+45, yc, xc+50, yc+5, 0xff0000);
				api.drawLine(xc+45, yc, xc+40, yc+5, 0xff0000);
				font.drawString("Distance", xc+4, yc-10, 0x0000ff);
				font.drawString("Force", xc+30, yc+20, 0xff0000);

				api.drawLine(xc-2*r, (int)(yc-1.4*r), xc-r, yc-r*2-2, 0x8800ff);
				api.drawLine(xc-2*r, (int)(yc-1.4*r), xc-2*r-2, yc, 0x8800ff);
				api.drawLine(xc-2*r, (int)(yc+1.4*r), xc-2*r-2, yc, 0x8800ff);
				api.drawLine(xc-2*r, (int)(yc+1.4*r), xc-r, yc+r*2+2, 0x8800ff);
				api.drawLine(xc+2, yc+r*2+2, xc-r, yc+r*2+2, 0x8800ff);
				api.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2+7, 0x8800ff);
				api.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2-3, 0x8800ff);
				font.drawString("Torque", xc-24, yc+18, 0x8800ff);

				r = 35;
				xc = posX+xSize/2+r+r/2;
				yc = posY+43;
				api.drawCircle(xc, yc, r, 0);

				int n1 = 2;
				int n2 = 4;
				double a = 57.3*System.nanoTime()/1000000000%360;
				double b = n1*57.3*System.nanoTime()/1000000000%360;
				double c = n2*57.3*System.nanoTime()/1000000000%360;
				api.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(a))*r), (int)(yc+Math.sin(Math.toRadians(a))*r), 0xff0000);
				api.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(b))*r), (int)(yc+Math.sin(Math.toRadians(b))*r), 0x0000ff);
				api.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(c))*r), (int)(yc+Math.sin(Math.toRadians(c))*r), 0x00a000);

				int dx = 2;
				int dy = 6;
				font.drawString("1 rad/s", xc+r-4+dx, yc+18-dy, 0xff0000);
				font.drawString(n1+" rad/s", xc+r-4+dx, yc+18+10-dy, 0x0000ff);
				font.drawString(n2+" rad/s", xc+r-4+dx, yc+18+20-dy, 0x00a000);
			}
			if (h == HandbookRegistry.BAITBOX && subpage == 1) {
				RenderItem ri = item;
				int k = (int)((System.nanoTime()/2000000000)%MobBait.baitList.length);
				MobBait b = MobBait.baitList[k];
				int u = b.getMobIconU();
				int v = b.getMobIconV();
				ItemStack is1 = b.getAttractorItemStack();
				ItemStack is2 = b.getRepellentItemStack();
				api.drawItemStack(ri, font, is1, posX+162, posY+27);
				api.drawItemStack(ri, font, is2, posX+162, posY+27+18);
				String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
				int UNIT = 4;
				api.drawTexturedModalRect(posX+88-UNIT/2, posY+41-UNIT/2, u, v, UNIT*2, UNIT*2);
				font.drawString("Attractor", posX+110, posY+30, 0);
				font.drawString("Repellent", posX+110, posY+48, 0);
				//RenderManager.instance.renderEntityWithPosYaw(new EntityCreeper(Minecraft.getMinecraft().theWorld), 120, 60, 0, 0, 0);
			}
			else if (h == HandbookRegistry.TERRA && subpage == 1) {
				RenderItem ri = item;
				ArrayList<BiomeTransform> transforms = TileEntityTerraformer.getTransformList();
				int time = 2000000000;
				int k = (int)((System.nanoTime()/time)%transforms.size());
				String tex = "/Reika/RotaryCraft/Textures/GUI/biomes.png";
				ReikaTextureHelper.bindTexture(RotaryCraft.class, tex);
				GL11.glColor4f(1, 1, 1, 1);
				BiomeTransform data = transforms.get(k);
				BiomeGenBase from = data.change.start;
				BiomeGenBase from_ = from;
				from = ReikaBiomeHelper.getParentBiomeType(from);
				BiomeGenBase to = data.change.finish;
				api.drawTexturedModalRect(posX+16, posY+22, 32*(from.biomeID%8), 32*(from.biomeID/8), 32, 32);
				api.drawTexturedModalRect(posX+80, posY+22, 32*(to.biomeID%8), 32*(to.biomeID/8), 32, 32);
				String name = ReikaStringParser.splitCamelCase(from_.biomeName);
				String[] words = name.split(" ");
				for (int i = 0; i < words.length; i++) {
					api.drawCenteredStringNoShadow(font, words[i], posX+33, posY+57+i*font.FONT_HEIGHT, 0);
				}
				String name2 = ReikaStringParser.splitCamelCase(to.biomeName);
				String[] words2 = name2.split(" ");
				for (int i = 0; i < words2.length; i++) {
					api.drawCenteredStringNoShadow(font, words2[i], posX+97, posY+57+i*font.FONT_HEIGHT, 0);
				}
				font.drawString(String.format("%.3f kW", data.power/1000D), posX+116, posY+22, 0);
				FluidStack liq = data.getFluid();
				if (liq != null) {
					GL11.glColor4f(1, 1, 1, 1);
					api.drawCenteredStringNoShadow(font, String.format("%d", liq.amount), posX+116+16, posY+38+5, 0);
					ReikaLiquidRenderer.bindFluidTexture(liq.getFluid());
					GL11.glColor4f(1, 1, 1, 1);
					IIcon ico = liq.getFluid().getIcon();
					api.drawTexturedModelRectFromIcon(posX+116, posY+38, ico, 16, 16);
					api.drawTexturedModelRectFromIcon(posX+116+16, posY+38, ico, 16, 16);
					//api.drawItemStack(ri, fontRenderer, liq.asItemStack(), posX+116, posY+38);
					//api.drawItemStack(ri, fontRenderer, liq.asItemStack(), posX+116+16, posY+38);
				}
				Collection<ItemReq> li = data.getItems();
				int i = 0;
				for (ItemReq r : li) {
					ItemStack is = r.asItemStack();
					api.drawItemStack(ri, font, is, posX+190, posY+8+i*18);
					i++;
				}
			}
			else if (h == HandbookRegistry.TIERS) {
				int maxw = 11;
				NavigableSet<Long> s = powerData.keySet();
				int t = 0;
				for (long key : s) {
					if (t == subpage) {
						String sg = String.format("- %d W", key);
						font.drawString(sg, posX+font.getStringWidth("Machine Tiers")+14, posY+6, 0);
						NavigableSet<MachineRegistry> c = powerData.get(key);
						int k = 0;
						int n = 0;
						for (MachineRegistry m : c) {
							ItemStack is = m.getCraftedProduct();
							if (k > maxw) {
								k = 0;
								n++;
							}
							int x = posX+k*18+10;
							int y = posY+n*18+29;
							api.drawItemStackWithTooltip(item, font, is, x, y);
							k++;
						}
					}
					t++;
				}
				RenderHelper.disableStandardItemLighting();
			}
			else if (h == HandbookRegistry.TIMING) {
				int k = 0;
				int n = 0;
				for (int i = 0; i < DurationRegistry.durationList.length; i++) {
					DurationRegistry d = DurationRegistry.durationList[i];
					MachineRegistry m = d.getMachine();
					ItemStack is = m.getCraftedProduct();
					int maxw = 11;
					if (k > maxw) {
						k = 0;
						n++;
					}
					int x = posX+k*18+10;
					int y = posY+n*18+29;

					api.drawItemStackWithTooltip(item, font, is, x, y);

					GL11.glColor4f(1, 1, 1, 1);
					if (api.isMouseInBox(x, x+17, y, y+17)) {
						for (int j = 0; j < d.getNumberStages(); j++) {
							//api.drawTooltipAt(font, d.getDisplayTime(j), mx, my);
							ReikaRenderHelper.disableLighting();
							int c = m.canDoMultiPerTick() ? 0x80ff80 : 0xffffff;
							font.drawString(d.getDisplayTime(j), posX+10, posY+150+j*10, c);
						}
					}

					k++;
				}
			}
			else if (h == HandbookRegistry.COMPUTERCRAFT) {
				if (subpage > 0) {
					Collection<LuaMethod> li = LuaMethod.getMethods();
					int di = (subpage-1)*36;
					int max = Math.min(di+36, MachineRegistry.machineList.length);
					for (int i = di; i < max; i++) {
						MachineRegistry m = MachineRegistry.machineList.get(i);
						ItemStack is = m.getCraftedProduct();
						if (m.hasSubdivisions()) {
							int meta = m.getNumberSubtypes();
							int time = (int)(System.currentTimeMillis()/1600)%meta;
							is = m.getCraftedMetadataProduct(time);
						}
						int r = (i-di)/12;
						int c = i%12;
						int x = posX+c*18+10;
						int y = posY+r*18+20;
						api.drawItemStackWithTooltip(item, font, is, x, y);
						if (api.isMouseInBox(x, x+17, y, y+17)) {
							int k = 0;
							for (LuaMethod cur : li) {
								if (cur.isDocumented() && cur.isClassInstanceOf(m.getTEClass())) {
									ReikaRenderHelper.disableLighting();
									String s = cur.getReturnType().displayName+" "+cur.displayName+"("+cur.getArgsAsString()+")";
									font.drawString(s, posX+11, posY+88+k*10, 0xffffff);
									k++;
								}
							}
						}
					}
				}
			}
			else if (h == HandbookRegistry.ALERTS) {
				String title = "These are the config settings that have been changed from the defaults, and may have significant "+
						"changes to the gameplay. If you have further questions, or you wish for these changes to be undone, contact "+
						"your server admin or modpack creator.";
				font.drawSplitString(title, posX+8, posY+20, 220, 0x333333);
				List<Alert> li = HandbookNotifications.instance.getNewAlerts();
				if (li.isEmpty()) {
					font.drawSplitString("All config settings are identical to defaults.", posX+10, posY+88, 245, 0xffffff);
					font.drawSplitString("Your gameplay is in line with what has been intended.", posX+10, posY+98, 245, 0xffffff);
				}
				else {
					int dy = 0;
					int base = subpage*3;
					int max = Math.min(base+3, li.size());
					for (int i = base; i < max; i++) {
						Alert a = li.get(i);
						String msg = a.getMessage();
						font.drawSplitString(msg, posX+10, posY+88+dy*44, 245, 0xffffff);
						dy++;
					}
				}
			}
			else if (h == HandbookRegistry.PACKMODS) {
				String title = "These are changes made to the way the mod works by the creator of the pack. None of these are normal " +
						"behavior of the mod, and any negative effects of these changes should be discussed with the pack creator, not " +
						"the mod developer.";
				font.drawSplitString(title, posX+8, posY+20, 220, 0x333333);
				List<PackModification> li = PackModificationTracker.instance.getModifications(RotaryCraft.instance);
				if (li == null || li.isEmpty()) {
					font.drawSplitString("No changes were made to the mod.", posX+10, posY+88, 245, 0xffffff);
					font.drawSplitString("Your gameplay is in line with what has been intended.", posX+10, posY+98, 245, 0xffffff);
				}
				else {
					int dy = 0;
					int base = subpage*3;
					int max = Math.min(base+3, li.size());
					for (int i = base; i < max; i++) {
						PackModification a = li.get(i);
						String msg = a.toString();
						font.drawSplitString(msg, posX+10, posY+88+dy*44, 245, 0xffffff);
						dy++;
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getPowerDataSize() {
		return powerData.keySet().size();
	}
}
