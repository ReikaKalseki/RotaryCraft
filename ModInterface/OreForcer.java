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
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.ModInteract.BCMachineHandler;
import Reika.DragonAPI.ModInteract.DartItemHandler;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModInteract.TinkerToolHandler;
import Reika.DragonAPI.ModInteract.TwilightBlockHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public final class OreForcer {

	public static void forceCompatibility() {
		for (int i = 0; i < APIRegistry.apiList.length; i++) {
			APIRegistry mod = APIRegistry.apiList[i];
			if (mod.conditionsMet()) {
				try {
					force(mod);
				}
				catch (NullPointerException e) {
					RotaryCraft.logger.logError("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (ClassCastException e) {
					RotaryCraft.logger.logError("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (ArrayIndexOutOfBoundsException e) {
					RotaryCraft.logger.logError("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IndexOutOfBoundsException e) {
					RotaryCraft.logger.logError("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					RotaryCraft.logger.logError("ROTARYCRAFT: Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private static void force(APIRegistry api) {
		RotaryCraft.logger.log("ROTARYCRAFT: Forcing compatibility with "+api);
		switch(api) {
		case BUILDCRAFTFACTORY:
			ReikaJavaLibrary.initClass(BCMachineHandler.class);
			break;
		case APPLIEDENERGISTICS:
			intercraftQuartz();
			break;
		case FORESTRY:
			intercraftApatite();
			break;
		case THAUMCRAFT:
			ReikaJavaLibrary.initClass(ThaumOreHandler.class);
			if (ConfigRegistry.MODORES.getState())
				registerThaumcraft();
			break;
		case MFFS:
			intercraftForcicium();
			break;
		case DARTCRAFT:
			if (ConfigRegistry.MODORES.getState())
				registerDart();
			breakForceWrench();
			ReikaJavaLibrary.initClass(DartOreHandler.class);
			ReikaJavaLibrary.initClass(DartItemHandler.class);
			break;
		case TINKERER:
			ReikaJavaLibrary.initClass(TinkerToolHandler.class);
			break;
		case TWILIGHT:
			ReikaJavaLibrary.initClass(TwilightBlockHandler.class);
			break;
		}
	}

	private static void breakForceWrench() {
		try {
			Class api = Class.forName("bluedart.api.DartAPI");
			Field blacklist = api.getField("tileBlacklist");
			ArrayList list = (ArrayList)blacklist.get(null);
			RotaryCraft.logger.log("ROTARYCRAFT: Breaking force wrench on RotaryCraft machines!");
			for (int i = 0; i < MachineRegistry.machineList.length; i++) {
				Class machine = MachineRegistry.machineList[i].getTEClass();
				list.add(machine);
				RotaryCraft.logger.log("ROTARYCRAFT: Force wrench no longer works on "+MachineRegistry.machineList[i].getName()+"!");
			}
			blacklist.set(null, list);
		}
		catch (ClassNotFoundException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: DartAPI class not found!");
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: DartAPI TileBlackList field not found!");
			e.printStackTrace();
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: DartAPI class threw security exception! "+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Could not add argument to DartAPI list!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: DartAPI class fields not accessible!");
			e.printStackTrace();
		}
	}

	private static void registerDart() {
		GameRegistry.addShapelessRecipe(DartOreHandler.getInstance().getForceGem(), ItemStacks.getModOreIngot(ModOreList.FORCE));
		RotaryCraft.logger.log("ROTARYCRAFT: RotaryCraft force gems can now be crafted into DartCraft force gems!");
		DartOreHandler.getInstance().forceOreRegistration();
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
			RotaryCraft.logger.logError("ROTARYCRAFT: MFFS Item class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: MFFS item field not found!");
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Cannot read MFFS items (Security Exception)! Monazit not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Illegal argument for reading MFFS items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Illegal access exception for reading MFFS items!");
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
			RotaryCraft.logger.logError("ROTARYCRAFT: AppliedEnergistics Item class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: AppliedEnergistics item field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Cannot read AppliedEnergistics items (Security Exception)! Certus Quartz not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Illegal argument for reading AppliedEnergistics items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Illegal access exception for reading AppliedEnergistics items!");
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
			RotaryCraft.logger.logError("ROTARYCRAFT: Forestry Config class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Forestry config field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Cannot read Forestry config (Security Exception)! Apatite not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Illegal argument for reading Forestry config!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("ROTARYCRAFT: Illegal access exception for reading Forestry config!");
			e.printStackTrace();
		}
	}

	private static void registerThaumcraft() {
		ThaumOreHandler.getInstance().forceOreRegistration();
		RotaryCraft.logger.log("ROTARYCRAFT: Adding ore item conversion recipes!");
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList o = ModOreList.oreList[i];
			if (o.isThaumcraft()) {
				GameRegistry.addShapelessRecipe(ThaumOreHandler.getInstance().getItem(o), ItemStacks.getModOreIngot(o));
				RotaryCraft.logger.log("ROTARYCRAFT: "+o.getName()+" can now be crafted with RotaryCraft equivalents!");
			}
		}
	}

}
