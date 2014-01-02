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
import thaumcraft.api.aspects.Aspect;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Exception.ModReflectionException;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.ModInteract.AppEngHandler;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.ForestryHandler;
import Reika.DragonAPI.ModInteract.IC2Handler;
import Reika.DragonAPI.ModInteract.MagicCropHandler;
import Reika.DragonAPI.ModInteract.MagicaOreHandler;
import Reika.DragonAPI.ModInteract.MekanismHandler;
import Reika.DragonAPI.ModInteract.MimicryHandler;
import Reika.DragonAPI.ModInteract.QuantumOreHandler;
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
				catch (ModReflectionException e) {
					ReikaJavaLibrary.pConsole(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private static void force(ModList api) {
		if (!api.isReikasMod())
			RotaryCraft.logger.log("Forcing compatibility with "+api);
		switch(api) {
		case APPENG:
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
		case ARSMAGICA:
			if (ConfigRegistry.MODORES.getState())
				registerMagica();
			break;
		case TRANSITIONAL:
			intercraftMagmanite();
			break;
		case RAILCRAFT:
			intercraftFirestone();
			break;
		case IC2:
			convertUranium();
			break;
		case MAGICCROPS:
			if (ConfigRegistry.MODORES.getState())
				registerEssence();
			break;
		case MIMICRY:
			intercraftMimichite();
			break;
		case QCRAFT:
			if (ConfigRegistry.MODORES.getState())
				registerQuantum();
		}
	}

	private static void registerQuantum() {
		QuantumOreHandler.getInstance().forceOreRegistration();
		ItemStack ore = new ItemStack(QuantumOreHandler.getInstance().dustID, 1, 0);
		GameRegistry.addShapelessRecipe(ore, ItemStacks.getModOreIngot(ModOreList.QUANTUM));
		RotaryCraft.logger.log("RotaryCraft quantum dust can now be crafted into QCraft quantum dust!");
	}

	private static void intercraftMimichite() {
		ItemStack ore = new ItemStack(MimicryHandler.getInstance().itemID, 1, 0);
		GameRegistry.addShapelessRecipe(ore, ItemStacks.getModOreIngot(ModOreList.MIMICHITE));
		RotaryCraft.logger.log("RotaryCraft essence items can now be crafted into Mimicry mimichite!");
	}

	private static void registerEssence() {
		MagicCropHandler.getInstance().registerEssence();
		ItemStack ore = new ItemStack(MagicCropHandler.getInstance().essenceID, 1, 0);
		GameRegistry.addShapelessRecipe(ore, ItemStacks.getModOreIngot(ModOreList.ESSENCE));
		RotaryCraft.logger.log("RotaryCraft essence items can now be crafted into Magic Crops essence!");
	}

	private static void convertUranium() {
		ItemStack u = IC2Handler.getInstance().getPurifiedCrushedUranium();
		if (u == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.IC2, "Null ItemStack for Uranium");
		if (IC2Handler.getInstance().iridiumID == -1)
			throw new ModReflectionException(RotaryCraft.instance, ModList.IC2, "Null Item for Iridium");
		ItemStack ir = new ItemStack(IC2Handler.getInstance().iridiumID, 1, 0);
		GameRegistry.addShapelessRecipe(ir, ItemStacks.getModOreIngot(ModOreList.IRIDIUM));
		RotaryCraft.logger.log("RotaryCraft iridium ingots can now be crafted into IC2 Iridium items!");
		GameRegistry.addShapelessRecipe(u, ItemStacks.getModOreIngot(ModOreList.URANIUM));
		RotaryCraft.logger.log("RotaryCraft uranium ingots can now be crafted into IC2 purified crushed uranium!");
	}

	private static void intercraftFirestone() {
		Item item = GameRegistry.findItem(ModList.RAILCRAFT.getModLabel(), "railcraft.firestone.raw");
		if (item == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.RAILCRAFT, "Null ItemStack for Firestone");
		GameRegistry.addShapelessRecipe(new ItemStack(item), ItemStacks.getModOreIngot(ModOreList.FIRESTONE));
		RotaryCraft.logger.log("RotaryCraft firestone can now be crafted into RailCraft firestone!");
	}

	private static void intercraftMagmanite() {
		Class trans = ModList.TRANSITIONAL.getItemClass();
		try {
			Field f = trans.getField("MagmaDrop");
			Item i = (Item)f.get(null);
			if (i == null)
				throw new ModReflectionException(RotaryCraft.instance, ModList.TRANSITIONAL, "Null ItemStack for Magmanite Drop");
			GameRegistry.addShapelessRecipe(new ItemStack(i), ItemStacks.getModOreIngot(ModOreList.MAGMANITE));
			RotaryCraft.logger.log("RotaryCraft magmanite can now be crafted into Transitional Assistance magmanite!");
		}
		catch (NoSuchFieldException e) {
			RotaryCraft.logger.logError("Transitional Assistance item field not found!");
		}
		catch (SecurityException e) {
			RotaryCraft.logger.logError("Cannot read Transitional Assistance items (Security Exception)! Magmanite not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			RotaryCraft.logger.logError("Illegal argument for reading Transitional Assistance items!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			RotaryCraft.logger.logError("Illegal access exception for reading Transitional Assistance items!");
			e.printStackTrace();
		}
	}

	private static void registerMagica() {
		MagicaOreHandler.getInstance().forceOreRegistration();
		RotaryCraft.logger.log("Adding ore item conversion recipes!");
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList o = ModOreList.oreList[i];
			if (o.isArsMagica()) {
				ItemStack is = MagicaOreHandler.getInstance().getItem(o);
				if (is == null)
					throw new ModReflectionException(RotaryCraft.instance, ModList.ARSMAGICA, "Null ItemStack for Ars Magica "+o);
				GameRegistry.addShapelessRecipe(is, ItemStacks.getModOreIngot(o));
				RotaryCraft.logger.log(o.displayName+" can now be crafted with RotaryCraft equivalents!");
			}
		}
	}

	private static void registerOsmium() {
		if (MekanismHandler.getInstance().oreID == -1)
			throw new ModReflectionException(RotaryCraft.instance, ModList.MEKANISM, "Null Item for Osmium");
		OreDictionary.registerOre("oreOsmium", new ItemStack(MekanismHandler.getInstance().oreID, 1, MekanismHandler.osmiumMeta));
	}

	private static void addThaumAspects() {
		RotaryCraft.logger.log("Adding ThaumCraft aspects.");
		ReikaThaumHelper.addAspects(ItemRegistry.CANOLA.getStackOf(), Aspect.EXCHANGE, 2, Aspect.CROP, 1, Aspect.MECHANISM, 1);
		ReikaThaumHelper.addAspects(ItemRegistry.YEAST.getStackOf(), Aspect.EXCHANGE, 4);

		ReikaThaumHelper.addAspects(ItemRegistry.BEDAXE.getStackOf(), Aspect.TOOL, 96);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDPICK.getStackOf(), Aspect.TOOL, 96);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSHOVEL.getStackOf(), Aspect.TOOL, 72);

		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(0), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.MOTION, 2, Aspect.MECHANISM, 2);
		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(1), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.FIRE, 3, Aspect.ENERGY, 12);
		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(2), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.ENERGY, 7, Aspect.PLANT, 3);

		ReikaThaumHelper.addAspects(ItemRegistry.SHELL.getStackOf(), Aspect.FIRE, 8);

		ReikaThaumHelper.addAspects(ItemStacks.steelingot, Aspect.METAL, 10, Aspect.MECHANISM, 6);
		ReikaThaumHelper.addAspects(ItemStacks.netherrackdust, Aspect.FIRE, 4);
		ReikaThaumHelper.addAspects(ItemStacks.sludge, Aspect.ENERGY, 1);
		ReikaThaumHelper.addAspects(ItemStacks.sawdust, Aspect.TREE, 1);
		ReikaThaumHelper.addAspects(ItemStacks.anthracite, Aspect.FIRE, 2, Aspect.ENERGY, 2);

		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
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
			Class mf = ModList.MFFS.getItemClass();
			Field item = mf.getField("MFFSitemForcicium");
			ItemStack forc = new ItemStack((Item)item.get(null));
			GameRegistry.addShapelessRecipe(forc, ItemStacks.getModOreIngot(ModOreList.MONAZIT));
			RotaryCraft.logger.log("RotaryCraft monazit can now be crafted into MFFS forcicium!");
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
		if (quartz == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.APPENG, "Null ItemStack for Certus Quartz");
		GameRegistry.addShapelessRecipe(quartz, ItemStacks.getModOreIngot(ModOreList.CERTUSQUARTZ));
		RotaryCraft.logger.log("RotaryCraft certus quartz can now be crafted into AppliedEnergistics certus quartz!");
	}

	private static void intercraftApatite() {
		int id = ForestryHandler.getInstance().apatiteID;
		if (id == -1)
			throw new ModReflectionException(RotaryCraft.instance, ModList.FORESTRY, "Null Item for Apatite");
		ItemStack apatite = new ItemStack(id, 1, 0);
		GameRegistry.addShapelessRecipe(apatite, ItemStacks.getModOreIngot(ModOreList.APATITE));
		RotaryCraft.logger.log("RotaryCraft apatite can now be crafted into Forestry apatite!");
	}

	private static void registerThaumcraft() {
		ThaumOreHandler.getInstance().forceOreRegistration();
		RotaryCraft.logger.log("Adding ore item conversion recipes!");
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList o = ModOreList.oreList[i];
			if (o.isThaumcraft()) {
				ItemStack is = ThaumOreHandler.getInstance().getItem(o);
				GameRegistry.addShapelessRecipe(is, ItemStacks.getModOreIngot(o));
				if (is == null)
					throw new ModReflectionException(RotaryCraft.instance, ModList.THAUMCRAFT, "Null ItemStack for Thaumcraft's "+o);
				RotaryCraft.logger.log(o.displayName+" can now be crafted with RotaryCraft equivalents!");
			}
		}
	}

}
