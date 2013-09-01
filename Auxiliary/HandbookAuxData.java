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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import Reika.DragonAPI.Libraries.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaOreHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.PlantMaterials;

public final class HandbookAuxData {
	/** One GuiHandbook.SECOND in nanoGuiHandbook.SECONDs. */
	private static int tickcount;

	public static List<IRecipe> getWorktable() {
		return WorktableRecipes.getInstance().getRecipeListCopy();
	}

	public static void drawPage(FontRenderer f, int screen, int page, int subpage, int dx, int dy) {
		RenderItem ri = new RenderItem();
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h.isCrafting()) {
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
			ReikaGuiAPI.instance.drawItemStack(ri, f, in, dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStack(ri, f, li.get(time), dx+145, dy+28);

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
			ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Block.obsidian), dx+87, dy+28);
			ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(RotaryCraft.obsidianglass), dx+145, dy+28);
		}
		else if (h == HandbookRegistry.YEAST) {
			int k = (int)((System.nanoTime()/2000000000)%(PlantMaterials.plantList.length+1));
			ItemStack out = ItemRegistry.YEAST.getStackOf();
			ItemStack[] in = {new ItemStack(Item.sugar), new ItemStack(Item.bucketWater), new ItemStack(Block.dirt)};
			if (k != 0) {
				out = ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, PlantMaterials.plantList[k-1].getPlantValue());
				in = new ItemStack[]{ItemRegistry.YEAST.getStackOf(), PlantMaterials.plantList[k-1].getPlantItemForIcon(), new ItemStack(Item.bucketWater)};
			}
			ReikaGuiAPI.instance.drawFermenter(ri, f, dx+102, dy+18, in, dx+159, dy+32, out);
		}
		else if (h == HandbookRegistry.NETHERDUST) {
			if ((System.nanoTime()/2000000000)%2 == 0) {
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Block.netherrack), dx+87, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.netherrackdust, dx+145, dy+28);
			}
			else {
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Block.slowSand), dx+87, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.tar, dx+145, dy+28);
			}
		}
		else if (h == HandbookRegistry.SALT) {
			ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Item.bucketWater), dx+90, dy+28);
			ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.salt, dx+166, dy+28);
		}
		else if (h == HandbookRegistry.SAWDUST) {
			int k = (int)((System.nanoTime()/2000000000)%5);
			switch (k) {
			case 0:
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Item.bucketWater), dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+36, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Block.stone), dx+72, dy+46);
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Block.stone), dx+72+18, dy+46);
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Block.stone), dx+72+36, dy+46);
				ReikaGuiAPI.instance.drawItemStack(ri, f, new ItemStack(Item.paper.itemID, 8, 0), dx+166, dy+28);
				break;
			case 1:
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaItemHelper.oakWood, dx+166, dy+28);
				break;
			case 2:
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaDyeHelper.BLACK.getStackOf(), dx+72+36, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaItemHelper.spruceWood, dx+166, dy+28);
				break;
			case 3:
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaDyeHelper.WHITE.getStackOf(), dx+72+36, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaItemHelper.birchWood, dx+166, dy+28);
				break;
			case 4:
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaDyeHelper.RED.getStackOf(), dx+72+36, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+10);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ItemStacks.sawdust, dx+72+18, dy+28);
				ReikaGuiAPI.instance.drawItemStack(ri, f, ReikaItemHelper.jungleWood, dx+166, dy+28);
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
		//else if (h == HandbookRegistry.SCRAP) {
		//	List<IRecipe> li = new ArrayList<IRecipe>();
		//	li.addAll(ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), ItemStacks.ironscrap));
		//	li.addAll(ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), ItemStacks.scrap));
		//	ReikaGuiAPI.instance.drawCustomRecipeList(ri, f, li, dx+72, dy+18, dx+162, dy+32);
		//}
	}
}
