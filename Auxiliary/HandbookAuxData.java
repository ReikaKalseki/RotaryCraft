/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.Icon;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Instantiable.ItemReq;
import Reika.DragonAPI.Instantiable.Data.ArrayMap;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.MachineRecipeRenderer;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MobBait;
import Reika.RotaryCraft.Registry.PlantMaterials;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer;

import com.google.common.collect.TreeMultimap;

public final class HandbookAuxData {
	/** One GuiHandbook.SECOND in nanoGuiHandbook.SECONDs. */
	private static int tickcount;

	public static List<IRecipe> getWorktable() {
		return WorktableRecipes.getInstance().getRecipeListCopy();
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
			MachineRegistry m = MachineRegistry.machineList[i];
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
				in[1] = new ItemStack(RotaryCraft.extracts.itemID, 1, k);
				in[2] = new ItemStack(RotaryCraft.extracts.itemID, 1, k+8);
				in[3] = new ItemStack(RotaryCraft.extracts.itemID, 1, k+16);

				out[0] = new ItemStack(RotaryCraft.extracts.itemID, 1, k);
				out[1] = new ItemStack(RotaryCraft.extracts.itemID, 1, k+8);
				out[2] = new ItemStack(RotaryCraft.extracts.itemID, 1, k+16);
				out[3] = new ItemStack(RotaryCraft.extracts.itemID, 1, k+24);

				oreName = ReikaOreHelper.oreList[k].getName();
			}
			else {
				int i = k-ReikaOreHelper.oreList.length;
				//ReikaJavaLibrary.pConsoleIf(modores.get(i)+" at "+i+" ("+""+")");
				ItemStack is = modores.get(i);
				ModOreList ore = ModOreList.getModOreFromOre(is);
				if (ore == null) {
					ReikaJavaLibrary.pConsole("DRAGONAPI: ItemStack "+is.getDisplayName()+" ("+is.itemID+":"+is.getItemDamage()+")");
					ReikaJavaLibrary.pConsole("has no mod ore list entry, yet was registered as such during load!");
					ReikaJavaLibrary.pConsole("Contact both mod developers immediately!");
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
		out = (ItemRegistry.YEAST.getStackOf());
		in = (new ItemStack[]{new ItemStack(Item.sugar), new ItemStack(Block.dirt)});
		args = new ItemStack[]{out, in[0], in[1]};
		fermenter.add(args);

		for (int i = 0; i < PlantMaterials.plantList.length; i++) {
			if (PlantMaterials.plantList[i] == PlantMaterials.SAPLING || PlantMaterials.plantList[i] == PlantMaterials.LEAVES) {
				for (int j = 0; j < ReikaTreeHelper.treeList.length; j++) {
					ItemStack icon = PlantMaterials.plantList[i] == PlantMaterials.SAPLING ? new ItemStack(Block.sapling, 1, j) : new ItemStack(Block.leaves, 1, j);
					out = (ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.plantList[i].getPlantValue()));
					in = (new ItemStack[]{ItemRegistry.YEAST.getStackOf(), icon});
					args = new ItemStack[]{out, in[0], in[1]};
					fermenter.add(args);
				}
			}
			else {
				out = (ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.plantList[i].getPlantValue()));
				in = (new ItemStack[]{ItemRegistry.YEAST.getStackOf(), PlantMaterials.plantList[i].getPlantItemForIcon()});
				args = new ItemStack[]{out, in[0], in[1]};
				fermenter.add(args);
			}
		}

		for (int i = 0; i < ModWoodList.woodList.length; i++) {
			if (ModWoodList.woodList[i].exists()) {
				out = (ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.SAPLING.getPlantValue()*TileEntityFermenter.getModWoodValue(ModWoodList.woodList[i])));
				in = (new ItemStack[]{ItemRegistry.YEAST.getStackOf(), ModWoodList.woodList[i].getCorrespondingSapling()});
				args = new ItemStack[]{out, in[0], in[1]};
				fermenter.add(args);

				out = (ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.LEAVES.getPlantValue()*TileEntityFermenter.getModWoodValue(ModWoodList.woodList[i])));
				in = (new ItemStack[]{ItemRegistry.YEAST.getStackOf(), ModWoodList.woodList[i].getCorrespondingLeaf()});
				args = new ItemStack[]{out, in[0], in[1]};
				fermenter.add(args);
			}
		}

		if (ModList.DYETREES.isLoaded()) {
			try {
				Class tree = Class.forName("Reika.DyeTrees.API.TreeGetter");
				Method sapling = tree.getMethod("getDyeSapling", int.class);
				Method leaf = tree.getMethod("getHeldDyeLeaf", int.class);
				for (int j = 0; j < 16; j++) {
					out = (ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.SAPLING.getPlantValue()));
					in = (new ItemStack[]{ItemRegistry.YEAST.getStackOf(), (ItemStack)sapling.invoke(null, j)});
					args = new ItemStack[]{out, in[0], in[1]};
					fermenter.add(args);

					out = (ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.LEAVES.getPlantValue()));
					in = (new ItemStack[]{ItemRegistry.YEAST.getStackOf(), (ItemStack)leaf.invoke(null, j)});
					args = new ItemStack[]{out, in[0], in[1]};
					fermenter.add(args);
				}
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public static void drawPage(FontRenderer f, RenderItem ri, int screen, int page, int subpage, int dx, int dy) {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h.isMachine() || h.isTrans() || h.isEngine()) {
			List<ItemStack> out = h.getCrafting();
			if (out == null || out.size() <= 0)
				return;
			if (h.isCustomRecipe()) {
				ReikaGuiAPI.instance.drawCustomRecipes(ri, f, out, getWorktable(), dx+72-18, dy+18, dx-1620, dy+32);
			}
			else {
				ReikaGuiAPI.instance.drawCustomRecipes(ri, f, out, CraftingManager.getInstance().getRecipeList(), dx+72-18, dy+18, dx-1620, dy+32);
			}
		}
		else if (h.isCrafting()) {
			List<ItemStack> out = h.getCrafting();
			if (out == null || out.size() <= 0)
				return;
			if (h.isCustomRecipe()) {
				ReikaGuiAPI.instance.drawCustomRecipes(ri, f, out, getWorktable(), dx+72, dy+18, dx+162, dy+32);
			}
			else {
				ReikaGuiAPI.instance.drawCustomRecipes(ri, f, out, CraftingManager.getInstance().getRecipeList(), dx+72, dy+18, dx+162, dy+32);
			}
		}
		else if (h.isSmelting()) {
			ItemStack out = h.getSmelting();
			ReikaGuiAPI.instance.drawSmelting(ri, f, out, dx+87, dy+36, dx+141, dy+32);
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
				in = new ItemStack(RotaryCraft.extracts, 1, time+24);
				oreName = ReikaOreHelper.oreList[time].getName();
			}
			else {
				in = ExtractorModOres.getFlakeProduct(ModOreList.oreList[i]);
				oreName = ModOreList.oreList[i].displayName;
			}
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, in, dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, flakes.get(time), dx+145, dy+28);

			String[] words = oreName.split(" ");
			for (int k = 0; k < words.length; k++)
				f.drawString(words[k], dx+168, dy+36+f.FONT_HEIGHT*k-words.length*f.FONT_HEIGHT/2, 0);
		}
		else if (h == HandbookRegistry.COMPACTS) {
			ItemStack in = new ItemStack(Item.coal);
			ItemStack out = new ItemStack(Item.diamond.itemID, 2, 0);
			int k = (int)((System.nanoTime()/2000000000)%4);
			if (k != 0)
				in = new ItemStack(RotaryCraft.compacts.itemID, 1, k-1);
			if (k != 3)
				out = new ItemStack(RotaryCraft.compacts.itemID, 2, k);
			MachineRecipeRenderer.instance.drawCompressor(dx+66, dy+14, in, dx+120, dy+41, out);
		}
		else if (h == HandbookRegistry.GLASS) {
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.obsidian), dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(RotaryCraft.blastglass), dx+145, dy+28);
		}
		else if (h == HandbookRegistry.JETPACK) {
			int k = (int)((System.nanoTime()/2000000000)%2);
			if (k == 0) {
				ItemStack out = ItemRegistry.JETPACK.getEnchantedStack();
				ArrayList li = ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), out);
				ReikaGuiAPI.instance.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
			}
			else {
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemRegistry.BEDCHEST.getEnchantedStack(), dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemRegistry.JETPACK.getStackOf(), dx+90, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemRegistry.BEDPACK.getEnchantedStack(), dx+166, dy+28);
			}
		}
		else if (h == HandbookRegistry.JUMPBOOTS) {
			int k = (int)((System.nanoTime()/2000000000)%2);
			if (k == 0) {
				ItemStack out = ItemRegistry.JUMP.getStackOf();
				List li = ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), out);
				ReikaGuiAPI.instance.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
			}
			else {
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemRegistry.BEDBOOTS.getEnchantedStack(), dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemRegistry.JUMP.getStackOf(), dx+90, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemRegistry.BEDJUMP.getEnchantedStack(), dx+166, dy+28);
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
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.netherrack), dx+87, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.netherrackdust, dx+145, dy+28);
			}
			else {
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.slowSand), dx+87, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.tar, dx+145, dy+28);
			}
		}
		else if (h == HandbookRegistry.SILVERINGOT) {
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.silverflakes, dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.silveringot, dx+145, dy+28);
		}
		else if (h == HandbookRegistry.SALT) {
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Item.bucketWater), dx+90, dy+28);
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.salt, dx+166, dy+28);
		}
		else if (h == HandbookRegistry.SAWDUST) {
			int k = (int)((System.nanoTime()/2000000000)%5);
			switch (k) {
			case 0:
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Item.bucketWater), dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+36, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.stone), dx+72, dy+46);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.stone), dx+72+18, dy+46);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.stone), dx+72+36, dy+46);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Item.paper.itemID, 8, 0), dx+166, dy+28);
				break;
			case 1:
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaItemHelper.oakWood, dx+166, dy+28);
				break;
			case 2:
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaDyeHelper.BLACK.getStackOf(), dx+72+36, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaItemHelper.spruceWood, dx+166, dy+28);
				break;
			case 3:
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaDyeHelper.WHITE.getStackOf(), dx+72+36, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaItemHelper.birchWood, dx+166, dy+28);
				break;
			case 4:
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaDyeHelper.RED.getStackOf(), dx+72+36, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, ReikaItemHelper.jungleWood, dx+166, dy+28);
				break;
			}
		}
		else if (h == HandbookRegistry.RAILGUNAMMO) {
			List<IRecipe> li = new ArrayList<IRecipe>();
			for (int i = 0; i < ItemRegistry.RAILGUN.getNumberMetadatas(); i++) {
				li.addAll(ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), ItemRegistry.RAILGUN.getStackOfMetadata(i)));
			}
			ReikaGuiAPI.instance.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
		}
		else if (h == HandbookRegistry.BEDTOOLS) {
			ArrayList<ItemStack> li = new ArrayList();
			for (int i = 0; i < ItemRegistry.itemList.length; i++) {
				ItemRegistry ir = ItemRegistry.itemList[i];
				if (ir.isBedrockTool()) {
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
		else if (h == HandbookRegistry.BEDINGOT) {
			ItemStack is = ItemStacks.bedingot;
			List<BlastRecipe> c = RecipesBlastFurnace.getRecipes().getAllRecipesMaking(is);
			MachineRecipeRenderer.instance.drawBlastFurnace(dx+99, dy+18, dx+185, dy+36, c.get(0));
		}
		else if (h == HandbookRegistry.STEELINGOT) {
			ItemStack is = ItemStacks.steelingot;
			List<BlastRecipe> c = RecipesBlastFurnace.getRecipes().getAllRecipesMaking(is);
			MachineRecipeRenderer.instance.drawBlastFurnace(dx+99, dy+18, dx+185, dy+36, c.get(0));
		}
	}

	public static void drawGraphics(HandbookRegistry h, int posX, int posY, int xSize, int ySize, FontRenderer font, RenderItem item, int subpage) {
		try {
			if (h == HandbookRegistry.TERMS) {
				int xc = posX+xSize/2; int yc = posY+43; int r = 35;
				ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
				ReikaGuiAPI.instance.drawLine(xc, yc, xc+r, yc, 0);
				ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+r-0.459*r), (int)(yc-0.841*r), 0);/*
    		for (float i = 0; i < 1; i += 0.1)
    			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(i)*r), (int)(yc-Math.sin(i)*r), 0x000000);*/
				String s = "One radian";
				font.drawString(s, xc+r+10, yc-4, 0x000000);
			}
			else if (h == HandbookRegistry.PHYSICS) {
				int xc = posX+xSize/8; int yc = posY+45; int r = 5;
				ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
				ReikaGuiAPI.instance.drawLine(xc, yc, xc+45, yc, 0x0000ff);
				ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+45, yc+20, 0xff0000);
				ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+50, yc+5, 0xff0000);
				ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+40, yc+5, 0xff0000);
				font.drawString("Distance", xc+4, yc-10, 0x0000ff);
				font.drawString("Force", xc+30, yc+20, 0xff0000);

				ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc-1.4*r), xc-r, yc-r*2-2, 0x8800ff);
				ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc-1.4*r), xc-2*r-2, yc, 0x8800ff);
				ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc+1.4*r), xc-2*r-2, yc, 0x8800ff);
				ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc+1.4*r), xc-r, yc+r*2+2, 0x8800ff);
				ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-r, yc+r*2+2, 0x8800ff);
				ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2+7, 0x8800ff);
				ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2-3, 0x8800ff);
				font.drawString("Torque", xc-24, yc+18, 0x8800ff);

				r = 35; xc = posX+xSize/2+r+r/2; yc = posY+43;
				ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
				double a = 57.3*System.nanoTime()/1000000000%360;
				double b = 3*57.3*System.nanoTime()/1000000000%360;
				ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(a))*r), (int)(yc+Math.sin(Math.toRadians(a))*r), 0xff0000);
				ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(b))*r), (int)(yc+Math.sin(Math.toRadians(b))*r), 0x0000ff);

				font.drawString("1 rad/s", xc+r-4, yc+18, 0xff0000);
				font.drawString("3 rad/s", xc+r-4, yc+18+10, 0x0000ff);
			}
			if (h == HandbookRegistry.BAITBOX && subpage == 1) {
				RenderItem ri = item;
				int k = (int)((System.nanoTime()/2000000000)%MobBait.baitList.length);
				MobBait b = MobBait.baitList[k];
				int u = b.getMobIconU();
				int v = b.getMobIconV();
				ItemStack is1 = b.getAttractorItemStack();
				ItemStack is2 = b.getRepellentItemStack();
				ReikaGuiAPI.instance.drawItemStack(ri, font, is1, posX+162, posY+27);
				ReikaGuiAPI.instance.drawItemStack(ri, font, is2, posX+162, posY+27+18);
				String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
				int UNIT = 4;
				ReikaGuiAPI.instance.drawTexturedModalRect(posX+88-UNIT/2, posY+41-UNIT/2, u, v, UNIT*2, UNIT*2);
				font.drawString("Attractor", posX+110, posY+30, 0);
				font.drawString("Repellent", posX+110, posY+48, 0);
				//RenderManager.instance.renderEntityWithPosYaw(new EntityCreeper(Minecraft.getMinecraft().theWorld), 120, 60, 0, 0, 0);
			}
			else if (h == HandbookRegistry.TERRA && subpage == 1) {
				RenderItem ri = item;
				ArrayList<Object[]> transforms = TileEntityTerraformer.getTransformList();
				int time = 2000000000;
				int k = (int)((System.nanoTime()/time)%transforms.size());
				String tex = "/Reika/RotaryCraft/Textures/GUI/biomes.png";
				ReikaTextureHelper.bindTexture(RotaryCraft.class, tex);
				Object[] data = transforms.get(k);
				BiomeGenBase from = (BiomeGenBase)data[0];
				BiomeGenBase from_ = from;
				from = ReikaBiomeHelper.getParentBiomeType(from);
				BiomeGenBase to = (BiomeGenBase)data[1];
				ReikaGuiAPI.instance.drawTexturedModalRect(posX+16, posY+22, 32*(from.biomeID%8), 32*(from.biomeID/8), 32, 32);
				ReikaGuiAPI.instance.drawTexturedModalRect(posX+80, posY+22, 32*(to.biomeID%8), 32*(to.biomeID/8), 32, 32);
				String name = ReikaStringParser.splitCamelCase(from_.biomeName);
				String[] words = name.split(" ");
				for (int i = 0; i < words.length; i++) {
					ReikaGuiAPI.instance.drawCenteredStringNoShadow(font, words[i], posX+33, posY+57+i*font.FONT_HEIGHT, 0);
				}
				String name2 = ReikaStringParser.splitCamelCase(to.biomeName);
				String[] words2 = name2.split(" ");
				for (int i = 0; i < words2.length; i++) {
					ReikaGuiAPI.instance.drawCenteredStringNoShadow(font, words2[i], posX+97, posY+57+i*font.FONT_HEIGHT, 0);
				}
				font.drawString(String.format("%.3f kW", (Integer)data[2]/1000D), posX+116, posY+22, 0);
				FluidStack liq = (FluidStack)data[3];
				if (liq != null) {
					GL11.glColor4f(1, 1, 1, 1);
					ReikaLiquidRenderer.bindFluidTexture(liq.getFluid());
					Icon ico = liq.getFluid().getIcon();
					ReikaGuiAPI.instance.drawTexturedModelRectFromIcon(posX+116, posY+38, ico, 16, 16);
					ReikaGuiAPI.instance.drawTexturedModelRectFromIcon(posX+116+16, posY+38, ico, 16, 16);
					//ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, liq.asItemStack(), posX+116, posY+38);
					//ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, liq.asItemStack(), posX+116+16, posY+38);
					ReikaGuiAPI.instance.drawCenteredStringNoShadow(font, String.format("%d", liq.amount), posX+116+16, posY+38+5, 0);
				}
				List<ItemReq> li = (List<ItemReq>)data[4];
				for (int i = 0; i < li.size(); i++) {
					ItemStack is = li.get(i).asItemStack();
					ReikaGuiAPI.instance.drawItemStack(ri, font, is, posX+190, posY+8+i*18);
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
							ReikaGuiAPI.instance.drawItemStackWithTooltip(item, font, is, x, y);
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

					ReikaGuiAPI.instance.drawItemStack(item, font, is, x, y);

					GL11.glColor4f(1, 1, 1, 1);
					if (ReikaGuiAPI.instance.isMouseInBox(x, x+17, y, y+17)) {
						for (int j = 0; j < d.getNumberStages(); j++) {
							//ReikaGuiAPI.instance.drawTooltipAt(font, d.getDisplayTime(j), mx, my);
							ReikaRenderHelper.disableLighting();
							font.drawString(d.getDisplayTime(j), posX+10, posY+150+j*10, 0xffffff);
						}
					}

					k++;
				}
			}
			else if (h == HandbookRegistry.COMPUTERCRAFT) {
				if (subpage > 0) {
					List<LuaMethod> li = LuaMethod.getMethods();
					int di = (subpage-1)*36;
					int max = Math.min(di+36, MachineRegistry.machineList.length);
					for (int i = di; i < max; i++) {
						MachineRegistry m = MachineRegistry.machineList[i];
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
						ReikaGuiAPI.instance.drawItemStackWithTooltip(item, font, is, x, y);
						if (ReikaGuiAPI.instance.isMouseInBox(x, x+17, y, y+17)) {
							int k = 0;
							for (int j = 0; j < li.size(); j++) {
								LuaMethod cur = li.get(j);
								if (cur.isClassInstanceOf(m.getTEClass())) {
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getPowerDataSize() {
		return powerData.keySet().size();
	}
}
