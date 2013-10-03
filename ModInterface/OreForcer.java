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
import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.EnumTag;
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.ModInteract.AppEngHandler;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.ForestryHandler;
import Reika.DragonAPI.ModInteract.MekanismHandler;
import Reika.DragonAPI.ModInteract.ReikaThaumHelper;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public final class OreForcer {

	public static void forceCompatibility() {
		for (int i = 0; i < ModList.modList.length; i++) {
			ModList mod = ModList.modList[i];
			if (mod.isLoaded()) {
				try {
					force(mod);
				}
				catch (NullPointerException e) {
					RotaryCraft.logger.logError("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (ClassCastException e) {
					RotaryCraft.logger.logError("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (ArrayIndexOutOfBoundsException e) {
					RotaryCraft.logger.logError("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IndexOutOfBoundsException e) {
					RotaryCraft.logger.logError("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					RotaryCraft.logger.logError("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private static void force(ModList api) {
		if (api != ModList.ROTARYCRAFT)
			RotaryCraft.logger.log("Forcing compatibility with "+api);
		switch(api) {
		case APPLIEDENERGISTICS:
			intercraftQuartz();
			break;
		case FORESTRY:
			intercraftApatite();
			break;
		case THAUMCRAFT:
			addThaumAspects();
			if (ConfigRegistry.MODORES.getState())
				registerThaumcraft();
			break;
		case MFFS:
			intercraftForcicium();
			break;
		case MEKANISM:
			registerOsmium();
			break;
		case DARTCRAFT:
			if (ConfigRegistry.MODORES.getState())
				registerDart();
			breakForceWrench();
			break;
		}
	}

	private static void registerOsmium() {
		OreDictionary.registerOre("oreOsmium", new ItemStack(MekanismHandler.getInstance().oreID, 1, 0));
	}

	private static void addThaumAspects() {
		ReikaThaumHelper.addAspects(ItemRegistry.CANOLA.getStackOf(), EnumTag.EXCHANGE, 2, EnumTag.CROP, 1, EnumTag.MECHANISM, 1);
		ReikaThaumHelper.addAspects(ItemRegistry.YEAST.getStackOf(), EnumTag.EXCHANGE, 4, EnumTag.FUNGUS, 4);

		ReikaThaumHelper.addAspects(ItemRegistry.BEDAXE.getStackOf(), EnumTag.TOOL, 24);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDPICK.getStackOf(), EnumTag.TOOL, 24);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSHOVEL.getStackOf(), EnumTag.TOOL, 18);

		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(0), EnumTag.VOID, 1, EnumTag.METAL, 13, EnumTag.MOTION, 2, EnumTag.MECHANISM, 2);
		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(1), EnumTag.VOID, 1, EnumTag.METAL, 13, EnumTag.FIRE, 3, EnumTag.POWER, 12);
		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(2), EnumTag.VOID, 1, EnumTag.METAL, 13, EnumTag.POWER, 7, EnumTag.PLANT, 3);

		ReikaThaumHelper.addAspects(ItemRegistry.SHELL.getStackOf(), EnumTag.DESTRUCTION, 12, EnumTag.FIRE, 8);

		ReikaThaumHelper.addAspects(ItemStacks.steelingot, EnumTag.METAL, 10, EnumTag.MECHANISM, 6);
		ReikaThaumHelper.addAspects(ItemStacks.netherrackdust, EnumTag.FIRE, 4);
		ReikaThaumHelper.addAspects(ItemStacks.sludge, EnumTag.POWER, 1);
		ReikaThaumHelper.addAspects(ItemStacks.sawdust, EnumTag.WOOD, 1);
		ReikaThaumHelper.addAspects(ItemStacks.nitrate, EnumTag.FLUX, 1);
		ReikaThaumHelper.addAspects(ItemStacks.anthracite, EnumTag.FIRE, 2, EnumTag.POWER, 2);
		ReikaThaumHelper.addAspects(ItemStacks.lonsda, EnumTag.VALUABLE, 4);

		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			int num = m.getNumberMetadatas();
			ReikaThaumHelper.clearAspects(m.getCraftedProduct());
		}
	}

	private static void breakForceWrench() {
		try {
			Class api = Class.forName("bluedart.api.DartAPI");
			Field blacklist = api.getField("tileBlacklist");
			ArrayList list = (ArrayList)blacklist.get(null);
			RotaryCraft.logger.log("Breaking force wrench on RotaryCraft machines!");
			for (int i = 0; i < MachineRegistry.machineList.length; i++) {
				Class machine = MachineRegistry.machineList[i].getTEClass();
				list.add(machine);
				RotaryCraft.logger.log("Force wrench no longer works on "+MachineRegistry.machineList[i].getName()+"!");
			}
			blacklist.set(null, list);
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.logError("DartAPI class not found!");
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("DartAPI TileBlackList field not found!");
			e.printStackTrace();
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("DartAPI class threw security exception! "+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("Could not add argument to DartAPI list!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("DartAPI class fields not accessible!");
			e.printStackTrace();
		}
	}

	private static void registerDart() {
		GameRegistry.addShapelessRecipe(DartOreHandler.getInstance().getForceGem(), ItemStacks.getModOreIngot(ModOreList.FORCE));
		RotaryCraft.logger.log("RotaryCraft force gems can now be crafted into DartCraft force gems!");
		DartOreHandler.getInstance().forceOreRegistration();
	}

	private static void intercraftForcicium() {
		try {
			Class mf = Class.forName("mods.mffs.common.ModularForceFieldSystem");
			Field item = mf.getField("MFFSitemForcicium");
			ItemStack forc = new ItemStack((Item)item.get(null));
			GameRegistry.addShapelessRecipe(forc, ItemStacks.getModOreIngot(ModOreList.MONAZIT));
			RotaryCraft.logger.log("RotaryCraft monazit can now be crafted into MFFS forcicium!");
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.logError("MFFS Item class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("MFFS item field not found!");
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("Cannot read MFFS items (Security Exception)! Monazit not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("Illegal argument for reading MFFS items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("Illegal access exception for reading MFFS items!");
			e.printStackTrace();
		}
	}

	private static void intercraftQuartz() {
		ItemStack quartz = AppEngHandler.getInstance().getCertusQuartz();
		GameRegistry.addShapelessRecipe(quartz, ItemStacks.getModOreIngot(ModOreList.CERTUSQUARTZ));
		RotaryCraft.logger.log("RotaryCraft certus quartz can now be crafted into AppliedEnergistics certus quartz!");
	}

	private static void intercraftApatite() {
		ItemStack apatite = new ItemStack(ForestryHandler.getInstance().apatiteID, 1, 0);
		GameRegistry.addShapelessRecipe(apatite, ItemStacks.getModOreIngot(ModOreList.APATITE));
		RotaryCraft.logger.log("RotaryCraft apatite can now be crafted into Forestry apatite!");
	}

	private static void registerThaumcraft() {
		ThaumOreHandler.getInstance().forceOreRegistration();
		RotaryCraft.logger.log("Adding ore item conversion recipes!");
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList o = ModOreList.oreList[i];
			if (o.isThaumcraft()) {
				GameRegistry.addShapelessRecipe(ThaumOreHandler.getInstance().getItem(o), ItemStacks.getModOreIngot(o));
				RotaryCraft.logger.log(o.getName()+" can now be crafted with RotaryCraft equivalents!");
			}
		}
	}

}
