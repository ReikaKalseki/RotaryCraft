/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import cpw.mods.fml.common.registry.GameRegistry;

public final class OreForcer {

	public static void forceCompatibility() {
		//test();
		for (int i = 0; i < APIRegistry.apiList.length; i++) {
			if (APIRegistry.apiList[i].conditionsMet()) {
				String mod = APIRegistry.apiList[i].getModLabel();
				try {
					force(APIRegistry.apiList[i]);
				}
				catch (NullPointerException e) {
					RotaryCraft.logger.log("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (ClassCastException e) {
					RotaryCraft.logger.log("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}/*
				catch (IOException e) {
		if (ConfigRegistry.LOGLOADING.getState())
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}*/
				catch (ArrayIndexOutOfBoundsException e) {
					RotaryCraft.logger.log("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IndexOutOfBoundsException e) {
					RotaryCraft.logger.log("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					RotaryCraft.logger.log("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
			}
		}
	}

	private static void test() {
		List<IRecipe> li = CraftingManager.getInstance().getRecipeList();
		ReikaJavaLibrary.pConsole("Loading "+li.size()+" recipes.");
		ItemStack piston = new ItemStack(Block.pistonBase);
		for (int i = 0; i < li.size(); i++) {
			IRecipe ir = li.get(i);
			if (ir instanceof ShapedRecipes) {
				ShapedRecipes sr = (ShapedRecipes)ir;
				ItemStack[] in = sr.recipeItems;
				int num = in.length;
				boolean[] has = new boolean[num];
				for (int j = 0; j < num; j++) {
					if (ReikaItemHelper.matchStacks(piston, in[j]))
						has[j] = true;
				}
				if (ReikaArrayHelper.containsTrue(has))
					ReikaJavaLibrary.pConsole(sr);
			}
		}
	}

	private static void force(APIRegistry api) {
		RotaryCraft.logger.log("ROTARYCRAFT: Forcing compatibility with "+api.getModLabel());
		switch(api) {
		case APPLIEDENERGISTICS:
			intercraftQuartz();
			break;
		case BUILDCRAFTENERGY:
			break;
		case BUILDCRAFTFACTORY:
			break;
		case BUILDCRAFTTRANSPORT:
			break;
		case FORESTRY:
			intercraftApatite();
			break;
		case GREGTECH:
			break;
		case INDUSTRIALCRAFT:
			break;
		case THAUMCRAFT:
			registerThaumcraft();
			break;
		case MFFS:
			intercraftForcicium();
			break;
		case REDPOWER:
			break;
		case BOP:
			break;
		case BXL:
			break;
		case NATURA:
			break;
		case TWILIGHT:
			break;
		case MINEFACTORY:
			break;
		case DARTCRAFT:
			registerDart();
			break;
		}
	}

	private static void registerDart() {
		try {
			Class block = Class.forName("bluedart.block.DartBlock");
			Class item = Class.forName("bluedart.item.DartItem");
			Field ore = block.getField("powerOre");
			Field force = item.getField("gemForce");
			Block powerOre = (Block)ore.get(null);
			ItemStack gem = new ItemStack((Item)force.get(null));
			GameRegistry.addShapelessRecipe(gem, ItemStacks.getModOreIngot(ModOreList.FORCE));
			RotaryCraft.logger.log("ROTARYCRAFT: RotaryCraft force gems can now be crafted into DartCraft force gems!");
			OreDictionary.registerOre(ModOreList.FORCE.getOreDictNames()[0], powerOre);
			RotaryCraft.logger.log("ROTARYCRAFT: Power ore registered to ore dictionary!");
			RotaryCraft.dartOre = new DartOreHandler(powerOre.blockID);
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: DartCraft Item class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: DartCraft item field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Cannot read DartCraft items (Security Exception)! Force not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal argument for reading DartCraft items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal access exception for reading DartCraft items!");
			e.printStackTrace();
		}
	}

	private static void intercraftForcicium() {
		try {
			Class mf = Class.forName("mods.mffs.common.ModularForceFieldSystem");
			Field item = mf.getField("MFFSitemForcicium");
			ItemStack forc = new ItemStack((Item)item.get(null));
			GameRegistry.addShapelessRecipe(forc, ItemStacks.getModOreIngot(ModOreList.MONAZIT));
			RotaryCraft.logger.log("ROTARYCRAFT: RotaryCraft monazit can now be crafted into MFFS forcicium!");
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: MFFS Item class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: MFFS item field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Cannot read MFFS items (Security Exception)! Monazit not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal argument for reading MFFS items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal access exception for reading MFFS items!");
			e.printStackTrace();
		}
	}

	private static void intercraftQuartz() {
		try {
			Class ae = Class.forName("appeng.api.Materials");
			Field item = ae.getField("matQuartz");
			ItemStack quartz = (ItemStack)item.get(null);
			GameRegistry.addShapelessRecipe(quartz, ItemStacks.getModOreIngot(ModOreList.CERTUSQUARTZ));
			RotaryCraft.logger.log("ROTARYCRAFT: RotaryCraft certus quartz can now be crafted into AppliedEnergistics certus quartz!");
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: AppliedEnergistics Item class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: AppliedEnergistics item field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Cannot read AppliedEnergistics items (Security Exception)! Certus Quartz not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal argument for reading AppliedEnergistics items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal access exception for reading AppliedEnergistics items!");
			e.printStackTrace();
		}
	}

	private static void intercraftApatite() {
		try {
			Class forest = Class.forName("forestry.core.config.ForestryItem");
			Field apa = forest.getField("apatite");
			Item item = (Item)apa.get(null);
			ItemStack apatite = new ItemStack(item.itemID, 1, 0);
			GameRegistry.addShapelessRecipe(apatite, ItemStacks.getModOreIngot(ModOreList.APATITE));
			RotaryCraft.logger.log("ROTARYCRAFT: RotaryCraft apatite can now be crafted into Forestry apatite!");
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Forestry Config class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Forestry config field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Cannot read Forestry config (Security Exception)! Apatite not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal argument for reading Forestry config!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal access exception for reading Forestry config!");
			e.printStackTrace();
		}
	}

	private static void registerThaumcraft() {
		try {
			Class thaum = Class.forName("thaumcraft.common.Config");
			Field ore = thaum.getField("blockCustomOre");
			Field item = thaum.getField("itemResource");
			Field shard = thaum.getField("itemShard");

			Block oreBlock = (Block)ore.get(null);
			Item oreItem = (Item)item.get(null);
			Item oreShard = (Item)shard.get(null);

			RotaryCraft.thaumOre = new ThaumOreHandler(oreBlock.blockID, oreItem.itemID, oreShard.itemID);

			RotaryCraft.logger.log("ROTARYCRAFT: Thaumcraft ores are being registered to Ore Dictionary!");

			for (int i = 0; i < ModOreList.oreList.length; i++) {
				ModOreList o = ModOreList.oreList[i];
				if (o.isThaumcraft()) {
					OreDictionary.registerOre(o.getOreDictNames()[0], RotaryCraft.thaumOre.getOre(o));
					OreDictionary.registerOre(o.getProductLabel(), RotaryCraft.thaumOre.getItem(o));
					o.reloadOreList();
					GameRegistry.addShapelessRecipe(RotaryCraft.thaumOre.getItem(o), ItemStacks.getModOreIngot(o));
					RotaryCraft.logger.log("ROTARYCRAFT: Registering "+o.getName());
					RotaryCraft.logger.log("ROTARYCRAFT: "+o.getName()+" can now be crafted with RotaryCraft equivalents!");
				}
			}
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Thaumcraft Config class not found! Cannot read its items for ore dictionary registration!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Thaumcraft config field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Cannot read Thaumcraft config (Security Exception)! Ores not registered!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal argument for reading Thaumcraft config!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.log("ROTARYCRAFT: Illegal access exception for reading Thaumcraft config!");
			e.printStackTrace();
		}
	}

}
