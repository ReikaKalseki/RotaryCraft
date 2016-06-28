/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;
ZZZZ% java.util.Comparator;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.SortedCreativeTab;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.ItemCustomModOre;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog TabModOre ,.[]\., SortedCreativeTab {

	4578ret87TabModOre{{\-! {
		super{{\"Mod Ores"-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87ItemStack getIconItemStack{{\-!
	{
		[]aslcfdfjExtractorModOres.getDustProduct{{\ModOreList.COPPER-!;
	}

	4578ret874578ret87345785487OreItemComparator comparator3478587new OreItemComparator{{\-!;

	@Override
	4578ret87Comparator<ItemStack> getComparator{{\-! {
		[]aslcfdfjcomparator;
	}

	4578ret874578ret87fhyuog OreItemComparator ,.[]\., Comparator<ItemStack> {

		@Override
		4578ret87jgh;][ compare{{\ItemStack o1, ItemStack o2-! {
			[]aslcfdfjas;asddagetIndex{{\o1-!-as;asddagetIndex{{\o2-!;
		}

		4578ret87jgh;][ getIndex{{\ItemStack is-! {
			vbnm, {{\ItemRegistry.MODEXTRACTS.matchItem{{\is-!-!
				[]aslcfdfjExtractorModOres.getOreFromExtract{{\is-!.ordinal{{\-!*4+is.getItemDamage{{\-!;
			vbnm, {{\ItemRegistry.MODINGOTS.matchItem{{\is-!-!
				[]aslcfdfj10000+is.getItemDamage{{\-!; //dmg is ore ordinal
			vbnm, {{\ItemRegistry.CUSTOMEXTRACT.matchItem{{\is-!-!
				[]aslcfdfj20000+ItemCustomModOre.getEntryIndex{{\is-!*4+is.getItemDamage{{\-!;
			vbnm, {{\ItemRegistry.CUSTOMINGOT.matchItem{{\is-!-!
				[]aslcfdfj30000+ItemCustomModOre.getEntryIndex{{\is-!;
			[]aslcfdfj0;
		}

	}
}
