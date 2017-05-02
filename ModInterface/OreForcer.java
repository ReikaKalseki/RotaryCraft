/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Base.ModHandlerBase;
import Reika.DragonAPI.Exception.ModReflectionException;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.AppEngHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.DartOreHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.FactorizationHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.GalacticCraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.DragonAPI.ModInteract.ItemHandlers.LegacyMagicCropHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicCropHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicCropHandler.EssenceType;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicaOreHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MekanismHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MimicryHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.QuantumOreHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.RailcraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ThaumOreHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.VeryLegacyMagicCropHandler;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public final class OreForcer {

	public static final OreForcer instance = new OreForcer();

	private OreForcer() {

	}

	public void forceCompatibility() {
		for (int i = 0; i < ModList.modList.length; i++) {
			ModList mod = ModList.modList[i];
			if (mod.isLoaded()) {
				try {
					this.force(mod);
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
					RotaryCraft.logger.logError(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void force(ModList api) {
		if (!api.isReikasMod())
			RotaryCraft.logger.log("Forcing compatibility with "+api);
		switch(api) {
			case APPENG:
				this.intercraftQuartz();
				break;
			case FORESTRY:
				this.intercraftApatite();
				break;
			case THAUMCRAFT:
				if (ConfigRegistry.MODORES.getState())
					this.registerThaumcraft();
				this.intercraftThaumcraft();
				break;
			case MFFS:
				this.intercraftForcicium();
				break;
			case MEKANISM:
				this.registerOsmium();
				break;
			case DARTCRAFT:
				if (ConfigRegistry.MODORES.getState())
					this.registerDart();
				this.intercraftDart();
				this.breakForceWrench();
				break;
			case ARSMAGICA:
				if (ConfigRegistry.MODORES.getState())
					this.registerMagica();
				this.intercraftMagica();
				break;
			case TRANSITIONAL:
				this.intercraftMagmanite();
				break;
			case RAILCRAFT:
				this.intercraftFirestone();
				break;
			case IC2:
				this.convertUranium();
				break;
			case MAGICCROPS:
				if (ConfigRegistry.MODORES.getState())
					this.registerEssence();
				this.intercraftEssence();
				break;
			case MIMICRY:
				this.intercraftMimichite();
				break;
			case FACTORIZATION:
				this.intercraftDarkIron();
				break;
			case QCRAFT:
				if (ConfigRegistry.MODORES.getState())
					this.registerQuantum();
				this.intercraftQuantum();
				break;
			case PROJRED:
				this.intercraftPRGems();
				break;
			case GALACTICRAFT:
				this.intercraftSilicon();
				break;
			case DRACONICEVO:
				this.intercraftDraconium();
				break;
		}
	}

	private void intercraftDraconium() {
		ItemStack dust = ReikaItemHelper.lookupItem(ModList.DRACONICEVO, "draconiumDust", 0);
		if (dust == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.DRACONICEVO, "Null Item for Draconium");
		GameRegistry.addShapelessRecipe(dust, ItemStacks.getModOreIngot(ModOreList.DRACONIUM));
	}

	private void intercraftSilicon() {
		Item id = GalacticCraftHandler.getInstance().basicItemID;
		if (id == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.GALACTICRAFT, "Null Item for Silicon");
		ItemStack silicon = new ItemStack(id, 1, GalacticCraftHandler.siliconMeta);
		GameRegistry.addShapelessRecipe(silicon, ItemStacks.getModOreIngot(ModOreList.SILICON));
	}

	private void intercraftPRGems() {
		ItemStack ruby = this.getPRGem("gemRuby");
		ItemStack sapphire = this.getPRGem("gemSapphire");
		ItemStack peridot = this.getPRGem("gemPeridot");
		if (ruby != null)
			GameRegistry.addShapelessRecipe(ItemStacks.getModOreIngot(ModOreList.RUBY), ruby);
		if (sapphire != null)
			GameRegistry.addShapelessRecipe(ItemStacks.getModOreIngot(ModOreList.SAPPHIRE), sapphire);
		if (peridot != null)
			GameRegistry.addShapelessRecipe(ItemStacks.getModOreIngot(ModOreList.PERIDOT), peridot);
		RotaryCraft.logger.log("RotaryCraft gems can now be crafted into Project Red gems!");
	}

	private ItemStack getPRGem(String oredict) {
		ArrayList<ItemStack> gems = OreDictionary.getOres(oredict);
		for (int i = 0; i < gems.size(); i++) {
			ItemStack is = gems.get(i);
			if (!ItemRegistry.MODINGOTS.matchItem(is)) {
				return is;
			}
		}
		return null;
	}

	private void registerQuantum() {
		QuantumOreHandler.getInstance().forceOreRegistration();
	}

	private void intercraftQuantum() {
		if (QuantumOreHandler.getInstance().dustID != null) {
			ItemStack ore = new ItemStack(QuantumOreHandler.getInstance().dustID, 1, 0);
			GameRegistry.addShapelessRecipe(ore, ItemStacks.getModOreIngot(ModOreList.QUANTUM));
			RotaryCraft.logger.log("RotaryCraft quantum dust can now be crafted into QCraft quantum dust!");
		}
	}

	private void intercraftDarkIron() {
		if (FactorizationHandler.getInstance().ingotID != null) {
			ItemStack ingot = new ItemStack(FactorizationHandler.getInstance().ingotID, 1, 0);
			GameRegistry.addShapelessRecipe(ingot, ItemStacks.getModOreIngot(ModOreList.DARKIRON));
			RotaryCraft.logger.log("RotaryCraft dark iron ingots can now be crafted into Factorization equivalents!");
		}
	}

	private void intercraftMimichite() {
		if (MimicryHandler.getInstance().itemID != null) {
			ItemStack ore = new ItemStack(MimicryHandler.getInstance().itemID, 1, 0);
			GameRegistry.addShapelessRecipe(ore, ItemStacks.getModOreIngot(ModOreList.MIMICHITE));
			RotaryCraft.logger.log("RotaryCraft mimichite items can now be crafted into Mimicry mimichite!");
		}
	}

	private void registerEssence() {
		ModHandlerBase h = ModList.MAGICCROPS.getHandler("Handler");
		if (h instanceof MagicCropHandler)
			((MagicCropHandler)h).registerEssence();
		if (h instanceof LegacyMagicCropHandler)
			((LegacyMagicCropHandler)h).registerEssence();
		if (h instanceof VeryLegacyMagicCropHandler)
			((VeryLegacyMagicCropHandler)h).registerEssence();
	}

	private void intercraftEssence() {
		ItemStack ore = EssenceType.ESSENCE.getEssence();
		GameRegistry.addShapelessRecipe(ore, ItemStacks.getModOreIngot(ModOreList.ESSENCE));
		RotaryCraft.logger.log("RotaryCraft essence items can now be crafted into Magic Crops essence!");
	}

	private void convertUranium() {
		ItemStack u = IC2Handler.IC2Stacks.PURECRUSHEDU.getItem();
		if (u == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.IC2, "Null ItemStack for Uranium");
		if (IC2Handler.IC2Stacks.IRIDIUM.getItem() == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.IC2, "Null Item for Iridium");
		GameRegistry.addShapelessRecipe(IC2Handler.IC2Stacks.IRIDIUM.getItem(), ItemStacks.getModOreIngot(ModOreList.IRIDIUM));
		RotaryCraft.logger.log("RotaryCraft iridium ingots can now be crafted into IC2 Iridium items!");
		GameRegistry.addShapelessRecipe(u, ItemStacks.getModOreIngot(ModOreList.URANIUM));
		RotaryCraft.logger.log("RotaryCraft uranium ingots can now be crafted into IC2 purified crushed uranium!");
	}

	private void intercraftFirestone() {
		Item item = RailcraftHandler.getInstance().firestoneID;
		if (item == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.RAILCRAFT, "Null ItemStack for Firestone");
		GameRegistry.addShapelessRecipe(new ItemStack(item), ItemStacks.getModOreIngot(ModOreList.FIRESTONE));
		RotaryCraft.logger.log("RotaryCraft firestone can now be crafted into RailCraft firestone!");
	}

	private void intercraftMagmanite() {
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

	private void intercraftMagica() {
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

	private void registerMagica() {
		MagicaOreHandler.getInstance().forceOreRegistration();
	}

	private void registerOsmium() {
		if (MekanismHandler.getInstance().oreID == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.MEKANISM, "Null Item for Osmium");
		OreDictionary.registerOre("oreOsmium", new ItemStack(MekanismHandler.getInstance().oreID, 1, MekanismHandler.osmiumMeta));
	}

	private void breakForceWrench() {
		try {
			Class api = Class.forName("bluedart.api.DartAPI");
			Field blacklist = api.getField("tileBlacklist");
			ArrayList list = (ArrayList)blacklist.get(null);
			RotaryCraft.logger.log("Breaking force wrench on RotaryCraft machines!");
			for (int i = 0; i < MachineRegistry.machineList.length; i++) {
				Class machine = MachineRegistry.machineList.get(i).getTEClass();
				list.add(machine);
				RotaryCraft.logger.log("Force wrench no longer works on "+MachineRegistry.machineList.get(i).getName()+"!");
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

	private void intercraftDart() {
		GameRegistry.addShapelessRecipe(DartOreHandler.getInstance().getForceGem(), ItemStacks.getModOreIngot(ModOreList.FORCE));
		RotaryCraft.logger.log("RotaryCraft force gems can now be crafted into DartCraft force gems!");
	}

	private void registerDart() {
		DartOreHandler.getInstance().forceOreRegistration();
	}

	private void intercraftForcicium() {
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

	private void intercraftQuartz() {
		ItemStack quartz = AppEngHandler.getInstance().getCertusQuartz();
		if (quartz == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.APPENG, "Null ItemStack for Certus Quartz");
		GameRegistry.addShapelessRecipe(quartz, ItemStacks.getModOreIngot(ModOreList.CERTUSQUARTZ));
		RotaryCraft.logger.log("RotaryCraft certus quartz can now be crafted into AppliedEnergistics certus quartz!");
	}

	private void intercraftApatite() {
		Item id = ForestryHandler.ItemEntry.APATITE.getItem();
		if (id == null)
			throw new ModReflectionException(RotaryCraft.instance, ModList.FORESTRY, "Null Item for Apatite");
		ItemStack apatite = new ItemStack(id, 1, 0);
		GameRegistry.addShapelessRecipe(apatite, ItemStacks.getModOreIngot(ModOreList.APATITE));
		RotaryCraft.logger.log("RotaryCraft apatite can now be crafted into Forestry apatite!");
	}

	private void intercraftThaumcraft() {
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

	private void registerThaumcraft() {
		ThaumOreHandler.getInstance().forceOreRegistration();
	}

}
