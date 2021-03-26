/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWayMap;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap.CollectionType;
import Reika.DragonAPI.Interfaces.Registry.OreType;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
import Reika.RotaryCraft.ModInterface.ItemCustomModOre;

public enum ExtractorBonus {

	GOLD(ItemStacks.goldsolution, ItemStacks.silverflakes, 0.125F),
	IRON(ItemStacks.ironsolution, ItemStacks.tungstenflakes, 0.025F),
	COAL(ItemStacks.coalsolution, new ItemStack(Items.gunpowder), 0.0625F, ModList.REACTORCRAFT, ExtractorModOres.getFlakeProduct(ModOreList.PITCHBLENDE), ModList.IC2, ExtractorModOres.getFlakeProduct(ModOreList.URANIUM)), //Nod to gregtech
	COPPER(ExtractorModOres.getSolutionProduct(ModOreList.COPPER), ItemStacks.goldoreflakes, 0.25F),
	LEAD(ExtractorModOres.getSolutionProduct(ModOreList.LEAD), ExtractorModOres.getFlakeProduct(ModOreList.NICKEL), 0.25F),
	NETHERGOLD(ExtractorModOres.getSolutionProduct(ModOreList.NETHERGOLD), ItemStacks.silverflakes, 0.125F),
	NETHERIRON(ExtractorModOres.getSolutionProduct(ModOreList.NETHERIRON), ItemStacks.tungstenflakes, 0.05F),
	SILVER(ExtractorModOres.getSolutionProduct(ModOreList.SILVER), ExtractorModOres.getFlakeProduct(ModOreList.IRIDIUM), 0.01F, ModList.IC2),
	PLATINUM(ExtractorModOres.getSolutionProduct(ModOreList.PLATINUM), ExtractorModOres.getFlakeProduct(ModOreList.IRIDIUM), 0.0625F, ModList.IC2),
	NETHERPLATINUM(ExtractorModOres.getSolutionProduct(ModOreList.NETHERPLATINUM), ExtractorModOres.getFlakeProduct(ModOreList.IRIDIUM), 0.125F, ModList.IC2),
	FERROUS(ExtractorModOres.getSolutionProduct(ModOreList.NICKEL), ExtractorModOres.getFlakeProduct(ModOreList.PLATINUM), 0.5F), //Since GregTech does it
	NETHERNICKEL(ExtractorModOres.getSolutionProduct(ModOreList.NETHERNICKEL), ExtractorModOres.getFlakeProduct(ModOreList.PLATINUM), 0.5F),
	SODALITE(ExtractorModOres.getSolutionProduct(ModOreList.SODALITE), ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM), 1F),
	PYRITE(ExtractorModOres.getSolutionProduct(ModOreList.PYRITE), ExtractorModOres.getFlakeProduct(ModOreList.SULFUR), 0.4F),
	BAUXITE(ExtractorModOres.getSolutionProduct(ModOreList.BAUXITE), ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM), 0.25F),
	IRIDIUM(ExtractorModOres.getSolutionProduct(ModOreList.IRIDIUM), ExtractorModOres.getFlakeProduct(ModOreList.PLATINUM), 0.5F),
	TUNGSTEN(ExtractorModOres.getSolutionProduct(ModOreList.TUNGSTEN), ItemStacks.ironoreflakes, 0.75F),
	OSMIUM(ExtractorModOres.getSolutionProduct(ModOreList.OSMIUM), ItemStacks.ironoreflakes, 0.125F),
	LAPIS(ItemStacks.lapissolution, getAluminumOutput(), 0.125F),
	RUBY(ExtractorModOres.getSolutionProduct(ModOreList.RUBY), ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM), 0.0625F),
	SAPPHIRE(ExtractorModOres.getSolutionProduct(ModOreList.SAPPHIRE), ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM), 0.0625F),
	QUARTZ(ItemStacks.quartzsolution, ExtractorModOres.getFlakeProduct(ModOreList.CERTUSQUARTZ), 0.0625F),
	CERTUS(ExtractorModOres.getSolutionProduct(ModOreList.CERTUSQUARTZ), ItemStacks.quartzflakes, 0.5F),
	COBALT(ExtractorModOres.getSolutionProduct(ModOreList.COBALT), ExtractorModOres.getFlakeProduct(ModOreList.NICKEL), 0.125F),
	REDSTONE(ItemStacks.redsolution, getAluminumOutput(), 0.25F),
	MAGNETITE(ExtractorModOres.getSolutionProduct(ModOreList.MAGNETITE), ItemStacks.ironoreflakes, 0.2F),
	MONAZIT(ExtractorModOres.getSolutionProduct(ModOreList.MONAZIT), ExtractorModOres.getFlakeProduct(ModOreList.THORIUM), 0.15F, ModList.REACTORCRAFT),
	EMERALD(ItemStacks.emeraldsolution, ExtractorModOres.getFlakeProduct(ModOreList.RUBY), 0.1F);

	private ItemStack bonusItem;
	private ItemStack sourceItem;
	private float probability;
	private boolean isVariable = false;
	private List<ItemStack> modBonus;
	private List<ModList> bonusMods;
	private boolean hasReq = false;
	private ModList requirementMod;

	private static final ExtractorBonus[] bonusList = values();
	private static final ItemHashMap<ItemStack> itemmap = new ItemHashMap().setOneWay();
	private static final ItemHashMap<ExtractorBonus> bonusmap = new ItemHashMap().setOneWay();
	private static final OneWayMap<OreType, OreType> oremap = new OneWayMap();
	private static final MultiMap<OreType, OreType> backwards = new MultiMap(CollectionType.HASHSET).setNullEmpty();
	private static final Random rand = new Random();

	private ExtractorBonus(ItemStack in, ItemStack is, float chance, ModList req) {
		bonusItem = is.copy();
		sourceItem = in.copy();
		probability = chance;

		hasReq = true;
		requirementMod = req;
		if (req.isLoaded())
			RotaryCraft.logger.log(req.getDisplayName()+" is loaded. Adding extractor bonus "+this.toString());
		else
			RotaryCraft.logger.log(req.getDisplayName()+" is not loaded. Skipping extractor bonus "+this.toString());
	}

	private ExtractorBonus(ItemStack in, ItemStack is, float chance) {
		bonusItem = is.copy();
		sourceItem = in.copy();
		probability = chance;

		RotaryCraft.logger.log("Adding extractor bonus "+this.toString());
	}

	private ExtractorBonus(ItemStack in, ItemStack is, float chance, Object... mods) {
		bonusItem = is.copy();
		sourceItem = in.copy();
		probability = chance;

		isVariable = true;
		if (mods.length%2 != 0)
			throw new IllegalArgumentException("Every mod must have a specified bonus!");
		modBonus = new ArrayList();
		bonusMods = new ArrayList();
		for (int i = 0; i < mods.length; i += 2) {
			ModList mod = (ModList)mods[i];
			ItemStack extra = (ItemStack)mods[i+1];
			modBonus.add(extra.copy());
			bonusMods.add(mod);
		}

		RotaryCraft.logger.log("Adding extractor bonus "+this.toString());
	}

	private static ItemStack getAluminumOutput() {
		return ConfigRegistry.OREALUDUST.getState() ? ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM) : ItemStacks.aluminumpowder;
	}

	public static void addCustomOreDelegate(CustomExtractEntry cus) {
		OreType base = cus.nativeOre;
		if (base == null)
			return;

		ItemStack out = null;
		if (base instanceof ReikaOreHelper)
			out = ItemStacks.getSolution((ReikaOreHelper)base);
		else if (base instanceof ModOreList)
			out = ExtractorModOres.getSolutionProduct((ModOreList)base);
		else
			RotaryCraft.logger.logError("Could not map custom extract "+cus.displayName+" to "+base.name()+"; does not have a recognized ore type.");

		registerAlias(ItemCustomModOre.getItem(cus.ordinal, ExtractorStage.SOLUTION), out);
	}

	private static void registerAlias(ItemStack is, ItemStack imi) {
		ExtractorBonus b = getBonusForIngredient(imi);
		if (b == null) {
			throw new IllegalArgumentException("Registering an item "+is+" as an alias of unmapped item "+imi+"!");
		}
		mapBonus(is, b.getBonusItem(), b);
	}

	public static ExtractorBonus getBonusForIngredient(ItemStack is) {
		return bonusmap.get(is);
	}

	public ItemStack getBonusItem() {
		if (hasReq && !requirementMod.isLoaded())
			return null;
		if (isVariable) {
			for (int i = 0; i < bonusMods.size(); i++) {
				if (bonusMods.get(i).isLoaded())
					return modBonus.get(i).copy();
			}
		}
		return bonusItem.copy();
	}

	public boolean doBonus() {
		int chance = (int)(1F/probability);
		return rand.nextInt(chance) == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isVariable) {
			sb.append("Bonuses from ");
			sb.append(StatCollector.translateToLocal(sourceItem.getDisplayName()));
			sb.append(":\n");
			for (int i = 0; i < bonusMods.size(); i++) {
				ModList mod = bonusMods.get(i);
				ItemStack item = modBonus.get(i);
				sb.append("\t\t\t");
				sb.append(mod.getDisplayName());
				sb.append(": ");
				sb.append(StatCollector.translateToLocal(item.getDisplayName()));
				sb.append(" (");
				sb.append(this.getBonusPercent());
				sb.append("% chance)");
				if (i < bonusMods.size()-1)
					sb.append("\n");
			}
		}
		else if (hasReq) {
			sb.append("Bonus of ");
			sb.append(StatCollector.translateToLocal(bonusItem.getDisplayName()));
			sb.append(" from ");
			sb.append(StatCollector.translateToLocal(sourceItem.getDisplayName()));
			sb.append(" (");
			sb.append(this.getBonusPercent());
			sb.append("% chance); ");
			sb.append("Requires ");
			sb.append(requirementMod.getDisplayName());
		}
		else {
			sb.append("Bonus of ");
			sb.append(StatCollector.translateToLocal(bonusItem.getDisplayName()));
			sb.append(" from ");
			sb.append(StatCollector.translateToLocal(sourceItem.getDisplayName()));
			sb.append(" (");
			sb.append(this.getBonusPercent());
			sb.append("% chance)");
		}
		return sb.toString();
	}

	public float getBonusPercent() {
		return probability*100F;
	}

	public static boolean hasBonus(ModOreList ore) {
		return oremap.containsKey(ore);
	}

	public static boolean isGivenAsBonus(ModOreList ore) {
		return backwards.containsKey(ore);
	}

	private static void mapBonus(ItemStack src, ItemStack bon, ExtractorBonus b) {
		OreType in = ExtractorModOres.getOreFromExtract(src);
		OreType out = ExtractorModOres.getOreFromExtract(bon);
		oremap.put(in, out);
		backwards.addValue(out, in);
		itemmap.put(src, bon);
		bonusmap.put(src, b);
	}

	static {
		for (int i = 0; i < bonusList.length; i++) {
			ExtractorBonus b = bonusList[i];
			ItemStack is = b.sourceItem;
			ItemStack bon = b.getBonusItem();
			if (bon != null) {
				mapBonus(is, bon, b);
			}
		}
	}

}
