/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PlantMaterials;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;

public final class HandbookAuxData {
	/** One GuiHandbook.SECOND in nanoGuiHandbook.SECONDs. */
	private static int tickcount;

	public static List<IRecipe> getWorktable() {
		return WorktableRecipes.getInstance().getRecipeListCopy();
	}

	public static void drawPage(FontRenderer f, int screen, int page, int subpage, int dx, int dy) {
		RenderItem ri = new RenderItem();
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
			ArrayList<ItemStack> modores = ModOreList.getAllRegisteredOreBlocks();
			int time = 1000000000;
			int k = (int)((System.nanoTime()/time)%(ReikaOreHelper.oreList.length+modores.size()));
			boolean van = k < ReikaOreHelper.oreList.length;
			ItemStack[] in = new ItemStack[4];
			ItemStack[] out = new ItemStack[4];
			String oreName;
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
				ModOreList ore = ModOreList.getModOreFromOre(modores.get(i));
				in[0] = modores.get(i);
				in[1] = ExtractorModOres.getDustProduct(ore);
				in[2] = ExtractorModOres.getSlurryProduct(ore);
				in[3] = ExtractorModOres.getSolutionProduct(ore);

				out[0] = ExtractorModOres.getDustProduct(ore);
				out[1] = ExtractorModOres.getSlurryProduct(ore);
				out[2] = ExtractorModOres.getSolutionProduct(ore);
				out[3] = ExtractorModOres.getFlakeProduct(ore);

				oreName = ore.getName();
			}

			ReikaGuiAPI.instance.drawExtractor(ri, f, dx+66, dy+17, in, dx+66, dy+59, out);
			String[] words = oreName.split(" ");
			for (int i = 0; i < words.length; i++)
				f.drawString(words[i], dx+194, dy+60+f.FONT_HEIGHT*i-words.length*f.FONT_HEIGHT/2, 0);
		}
		else if (h == HandbookRegistry.FLAKES) {
			ArrayList<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
				li.add(ReikaOreHelper.oreList[i].getResource());
			}
			for (int i = 0; i < ModOreList.oreList.length; i++) {
				li.add(ItemStacks.getModOreIngot(ModOreList.oreList[i]));
			}
			ItemStack in;
			int time = (int)((System.nanoTime()/1000000000)%li.size());
			boolean van = time < ReikaOreHelper.oreList.length;
			int i = time-ReikaOreHelper.oreList.length;
			String oreName;
			if (van) {
				in = new ItemStack(RotaryCraft.extracts, 1, time+24);
				oreName = ReikaOreHelper.oreList[time].getName();
			}
			else {
				in = ExtractorModOres.getFlakeProduct(ModOreList.oreList[i]);
				oreName = ModOreList.oreList[i].getName();
			}
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, in, dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, li.get(time), dx+145, dy+28);

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
			ReikaGuiAPI.instance.drawCompressor(ri, f, dx+66, dy+14, in, dx+120, dy+41, out);
		}
		else if (h == HandbookRegistry.GLASS) {
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(Block.obsidian), dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStackWithTooltip(ri, f, new ItemStack(RotaryCraft.obsidianglass), dx+145, dy+28);
		}
		else if (h == HandbookRegistry.YEAST) {
			ArrayList<ItemStack> out = new ArrayList<ItemStack>();
			ArrayList<ItemStack[]> in = new ArrayList<ItemStack[]>();
			out.add(ItemRegistry.YEAST.getStackOf());
			in.add(new ItemStack[]{new ItemStack(Item.sugar), new ItemStack(Item.bucketWater), new ItemStack(Block.dirt)});

			for (int i = 0; i < PlantMaterials.plantList.length; i++) {
				if (PlantMaterials.plantList[i] == PlantMaterials.SAPLING || PlantMaterials.plantList[i] == PlantMaterials.LEAVES) {
					for (int j = 0; j < ReikaTreeHelper.treeList.length; j++) {
						ItemStack icon = PlantMaterials.plantList[i] == PlantMaterials.SAPLING ? new ItemStack(Block.sapling, 1, j) : new ItemStack(Block.leaves, 1, j);
						out.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.plantList[i].getPlantValue()));
						in.add(new ItemStack[]{ItemRegistry.YEAST.getStackOf(), icon, new ItemStack(Item.bucketWater)});
					}
				}
				else {
					out.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.plantList[i].getPlantValue()));
					in.add(new ItemStack[]{ItemRegistry.YEAST.getStackOf(), PlantMaterials.plantList[i].getPlantItemForIcon(), new ItemStack(Item.bucketWater)});
				}
			}

			for (int i = 0; i < ModWoodList.woodList.length; i++) {
				if (ModWoodList.woodList[i].exists()) {
					out.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.SAPLING.getPlantValue()*TileEntityFermenter.getModSaplingValue(ModWoodList.woodList[i])));
					in.add(new ItemStack[]{ItemRegistry.YEAST.getStackOf(), ModWoodList.woodList[i].getCorrespondingSapling(), new ItemStack(Item.bucketWater)});

					out.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.LEAVES.getPlantValue()*TileEntityFermenter.getModSaplingValue(ModWoodList.woodList[i])));
					in.add(new ItemStack[]{ItemRegistry.YEAST.getStackOf(), ModWoodList.woodList[i].getCorrespondingLeaf(), new ItemStack(Item.bucketWater)});
				}
			}

			if (ModList.DYETREES.isLoaded()) {
				for (int j = 0; j < 16; j++) {
					Class tree = Class.forName("Reika.DyeTrees.API.TreeGetter");
					Method sapling = tree.getMethod("getDyeSapling", int.class);
					Method leaf = tree.getMethod("getDyeLeaf", int.class);
					out.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.SAPLING.getPlantValue()));
					in.add(new ItemStack[]{ItemRegistry.YEAST.getStackOf(), (ItemStack)sapling.invoke(j), new ItemStack(Item.bucketWater)});

					out.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.LEAVES.getPlantValue()));
					in.add(new ItemStack[]{ItemRegistry.YEAST.getStackOf(), (ItemStack)leaf.invoke(j), new ItemStack(Item.bucketWater)});
				}
			}

			int k = (int)((System.nanoTime()/2000000000)%(in.size()));
			ReikaGuiAPI.instance.drawFermenter(ri, f, dx+102, dy+18, in.get(k), dx+159, dy+32, out.get(k));
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
	}
}
