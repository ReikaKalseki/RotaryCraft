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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import cpw.mods.fml.common.registry.GameRegistry;

public final class OreForcer {

	public static void forceCompatibility() {
		for (int i = 0; i < APIRegistry.apiList.length; i++) {
			if (APIRegistry.apiList[i].conditionsMet()) {
				String mod = APIRegistry.apiList[i].getModLabel();
				try {
					force(APIRegistry.apiList[i]);
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
			RotaryCraft.logger.logError("ROTARYCRAFT: MFFS item field not found! "+e.getMessage());
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
