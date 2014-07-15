/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;

public enum ExtractorBonus {

	GOLD(ItemStacks.goldoresolution, ItemStacks.silverflakes, 0.125F),
	IRON(ItemStacks.ironoresolution, ItemStacks.tungstenflakes, 0.025F),
	COAL(ItemStacks.coaloresolution, new ItemStack(Item.gunpowder), 0.0625F, ModList.REACTORCRAFT, ExtractorModOres.getFlakeProduct(ModOreList.PITCHBLENDE), ModList.IC2, ExtractorModOres.getFlakeProduct(ModOreList.URANIUM)), //Nod to gregtech
	COPPER(ExtractorModOres.getSolutionProduct(ModOreList.COPPER), ItemStacks.goldoreflakes, 0.125F),
	LEAD(ExtractorModOres.getSolutionProduct(ModOreList.LEAD), ExtractorModOres.getFlakeProduct(ModOreList.NICKEL), 0.25F),
	NETHERGOLD(ExtractorModOres.getSolutionProduct(ModOreList.NETHERGOLD), ItemStacks.silverflakes, 0.125F),
	NETHERIRON(ExtractorModOres.getSolutionProduct(ModOreList.NETHERIRON), ItemStacks.aluminumpowder, 0.125F),
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
	LAPIS(ItemStacks.lapisoresolution, ItemStacks.aluminumpowder, 0.125F),
	RUBY(ExtractorModOres.getSolutionProduct(ModOreList.RUBY), ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM), 0.0625F),
	SAPPHIRE(ExtractorModOres.getSolutionProduct(ModOreList.SAPPHIRE), ExtractorModOres.getFlakeProduct(ModOreList.ALUMINUM), 0.0625F),
	QUARTZ(ItemStacks.netherquartzsolution, ExtractorModOres.getFlakeProduct(ModOreList.CERTUSQUARTZ), 0.0625F),
	CERTUS(ExtractorModOres.getSolutionProduct(ModOreList.CERTUSQUARTZ), ItemStacks.netherquartzflakes, 0.5F),
	COBALT(ExtractorModOres.getSolutionProduct(ModOreList.COBALT), ExtractorModOres.getFlakeProduct(ModOreList.NICKEL), 0.125F),
	REDSTONE(ItemStacks.redoresolution, ItemStacks.aluminumpowder, 0.25F);

	private static final ExtractorBonus[] bonusList = values();

	private ItemStack bonusItem;
	private ItemStack sourceItem;
	private float probability;
	private boolean isVariable = false;
	private List<ItemStack> modBonus;
	private List<ModList> bonusMods;
	private boolean hasReq = false;
	private ModList requirementMod;

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

	public static ExtractorBonus getBonusForIngredient(ItemStack is) {
		for (int i = 0; i < bonusList.length; i++) {
			ItemStack in = bonusList[i].sourceItem;
			//ReikaJavaLibrary.pConsole(bonusList[i]+" > "+in+"  >:<  "+is);
			if (ReikaItemHelper.matchStacks(is, in))
				return bonusList[i];
		}
		return null;
	}

	public ItemStack getBonus() {
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

	public void addBonusToItemStack(ItemStack[] inv, int slot) {
		ItemStack bonus = this.getBonus();
		if (bonus == null)
			return;
		int chance = (int)(1F/probability);
		Random r = new Random();
		if (r.nextInt(chance) > 0)
			return;
		if (inv[slot] != null) {
			if (!ReikaItemHelper.matchStacks(inv[slot], bonus))
				return;
			if (inv[slot].stackSize+bonus.stackSize > inv[slot].getMaxStackSize())
				return;
			inv[slot].stackSize += bonus.stackSize;
		}
		else {
			inv[slot] = bonus;
		}
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

}
