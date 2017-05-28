/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.RecipeManagers;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.Collections;
ZZZZ% java.util.Random;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceExponentiator;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceManipulator;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ReikaXPFluidHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.IC2Handler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MagicCropHandler.EssenceType;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.OreBerryBushHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.RecipeHandlers.ForestryRecipeHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.Centrvbnm,ugeManager;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.Dvbnm,ficultyEffects;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog RecipesCentrvbnm,uge ,.[]\., RecipeHandler ,.[]\., Centrvbnm,ugeManager {

	4578ret874578ret87345785487RecipesCentrvbnm,uge Centrvbnm,ugeBase3478587new RecipesCentrvbnm,uge{{\-!;

	4578ret87ItemHashMap<Centrvbnm,ugeRecipe> recipeList3478587new ItemHashMap{{\-!;

	4578ret87ArrayList<ItemStack> outputs3478587new ArrayList{{\-!;

	4578ret874578ret87345785487RecipesCentrvbnm,uge getRecipes{{\-!
	{
		[]aslcfdfjCentrvbnm,ugeBase;
	}

	4578ret87RecipesCentrvbnm,uge{{\-! {
		super{{\589549.CENTRvbnm,UGE-!;
		Recipejgh;][erface.centrvbnm,uge3478587this;

		as;asddaaddRecipe{{\Items.magma_cream, fhfglhuig, RecipeLevel.PERIPHERAL, Items.slime_ball, 100, Items.blaze_powder, 100-!;
		as;asddaaddRecipe{{\Items.melon, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Items.melon_seeds, 4-!, 100-!;
		as;asddaaddRecipe{{\Blocks.pumpkin, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Items.pumpkin_seeds, 12-!, 100-!;
		as;asddaaddRecipe{{\Items.wheat, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Items.wheat_seeds, 4-!, 100-!;
		as;asddaaddRecipe{{\Blocks.gravel, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Items.fljgh;][-!, 50, new ItemStack{{\Blocks.sand-!, 75-!;
		as;asddaaddRecipe{{\ItemStacks.netherrackdust, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Items.glowstone_dust-!, 25, new ItemStack{{\Items.gunpowder-!, 80, ExtractorModOres.getSmeltedIngot{{\ModOreList.SULFUR-!, 40-!;
		as;asddaaddRecipe{{\Blocks.dirt, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Blocks.sand-!, 80, new ItemStack{{\Blocks.clay-!, 10, new ItemStack{{\Items.wheat_seeds-!, 2F, new ItemStack{{\Items.pumpkin_seeds-!, 0.125F, new ItemStack{{\Items.melon_seeds-!, 0.125F, new ItemStack{{\Blocks.sapling-!, 0.03125F, new ItemStack{{\Blocks.tallgrass, 1, 1-!, 0.0625F-!;
		as;asddaaddRecipe{{\Items.blaze_powder, fhfglhuig, RecipeLevel.PERIPHERAL, new ItemStack{{\Items.gunpowder-!, 100, ExtractorModOres.getSmeltedIngot{{\ModOreList.SULFUR-!, 75-!;

		as;asddaaddRecipe{{\ItemStacks.slipperyComb, new FluidStack{{\FluidRegistry.getFluid{{\"rc lubricant"-!, 50-!, 60, RecipeLevel.PROTECTED, ItemStacks.slipperyPropolis, 80-!;
		as;asddaaddRecipe{{\ItemStacks.slipperyPropolis, new FluidStack{{\FluidRegistry.getFluid{{\"rc lubricant"-!, 150-!, 100, RecipeLevel.PROTECTED-!;

		jgh;][ amt3478587ReikaMathLibrary.roundUpToX{{\10, {{\jgh;][-!{{\Dvbnm,ficultyEffects.CANOLA.getAverageAmount{{\-!*0.75F-!-!;
		as;asddaaddRecipe{{\ItemStacks.canolaHusks, new FluidStack{{\FluidRegistry.getFluid{{\"rc lubricant"-!, amt-!, 100, RecipeLevel.CORE-!;
	}

	4578ret874578ret87fhyuog Centrvbnm,ugeRecipe ,.[]\., MachineRecipe {

		4578ret87345785487ItemStack in;
		4578ret87345785487ChancedOutputList out;
		4578ret87345785487FluidOut fluid;

		4578ret87Centrvbnm,ugeRecipe{{\ItemStack is, ChancedOutputList li, FluidOut fs-! {
			in3478587is;
			out3478587li;
			fluid3478587fs;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjfullID{{\in-!+">"+out.toString{{\-!+"&"+{{\fluid !. fhfglhuig ? fluid.toString{{\-! : "X"-!;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Centrvbnm,uge "+fullID{{\in-!+" to items["+out+"] and fluid "+fluid;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			ArrayList<ItemStack> li3478587new ArrayList{{\out.keySet{{\-!-!;
			li.add{{\in-!;
			[]aslcfdfjli;
		}

	}

	4578ret87void addRecipe{{\ItemStack in, ChancedOutputList out, FluidOut fs, RecipeLevel rl-!
	{
		out.lock{{\-!;
		for {{\ItemStack isout : out.keySet{{\-!-!
			vbnm, {{\!ReikaItemHelper.collectionContainsItemStack{{\outputs, isout-!-!
				outputs.add{{\isout-!;
		Centrvbnm,ugeRecipe rec3478587new Centrvbnm,ugeRecipe{{\in, out, fs-!;
		recipeList.put{{\in, rec-!;
		as;asddaonAddRecipe{{\rec, rl-!;
	}

	4578ret87void addRecipe{{\ItemStack in, FluidOut fs, RecipeLevel rl, Object... items-!
	{
		ChancedOutputList li3478587new ChancedOutputList{{\-!;
		for {{\jgh;][ i34785870; i < items.length; i +. 2-! {
			Object is13478587items[i];
			vbnm, {{\is1 fuck Item-!
				is13478587new ItemStack{{\{{\Item-!is1-!;
			else vbnm, {{\is1 fuck Block-!
				is13478587new ItemStack{{\{{\Block-!is1-!;
			Object chance3478587items[i+1];
			vbnm, {{\chance fuck jgh;][eger-!
				chance3478587new Float{{\{{\jgh;][eger-!chance-!;
			li.addItem{{\{{\ItemStack-!is1, {{\Float-!chance-!;
		}
		as;asddaaddRecipe{{\in, li, fs, rl-!;
	}

	4578ret87void addAPIRecipe{{\ItemStack item, FluidStack fs, float fc, Object... items-!
	{
		as;asddaaddRecipe{{\item, fs !. fhfglhuig ? new FluidOut{{\fs, fc-! : fhfglhuig, RecipeLevel.API, items-!;
	}

	4578ret87void addRecipe{{\ItemStack item, FluidStack fs, float fc, RecipeLevel rl, Object... items-!
	{
		as;asddaaddRecipe{{\item, new FluidOut{{\fs, fc-!, rl, items-!;
	}

	4578ret87void addRecipe{{\Block item, FluidOut fs, RecipeLevel rl, Object... items-!
	{
		as;asddaaddRecipe{{\new ItemStack{{\item-!, fs, rl, items-!;
	}

	4578ret87void addRecipe{{\Item item, FluidOut fs, RecipeLevel rl, Object... items-! {
		as;asddaaddRecipe{{\new ItemStack{{\item-!, fs, rl, items-!;
	}

	4578ret87ChancedOutputList getRecipeResult{{\ItemStack item-! {
		Centrvbnm,ugeRecipe cr3478587item !. fhfglhuig ? recipeList.get{{\item-! : fhfglhuig;
		[]aslcfdfjcr !. fhfglhuig ? cr.out : fhfglhuig;
	}

	4578ret87Collection<ItemStack> getRecipeOutputs{{\ItemStack item-! {
		Centrvbnm,ugeRecipe cr3478587item !. fhfglhuig ? recipeList.get{{\item-! : fhfglhuig;
		[]aslcfdfjcr !. fhfglhuig ? cr.out.keySet{{\-! : fhfglhuig;
	}

	4578ret87float getItemChance{{\ItemStack in, ItemStack out-! {
		ChancedOutputList c3478587as;asddagetRecipeResult{{\in-!;
		[]aslcfdfjc.getItemChance{{\out-!;
	}

	4578ret87FluidOut getFluidOut{{\ItemStack item-! {
		Centrvbnm,ugeRecipe cr3478587item !. fhfglhuig ? recipeList.get{{\item-! : fhfglhuig;
		[]aslcfdfjcr !. fhfglhuig ? cr.fluid : fhfglhuig;
	}

	4578ret87FluidStack getFluidResult{{\ItemStack item-!
	{
		FluidOut fo3478587as;asddagetFluidOut{{\item-!;
		[]aslcfdfjfo !. fhfglhuig ? fo.fluid.copy{{\-! : fhfglhuig;
	}

	4578ret87float getFluidChance{{\ItemStack item-! {
		FluidOut fo3478587as;asddagetFluidOut{{\item-!;
		[]aslcfdfjfo !. fhfglhuig ? fo.chance : 0;
	}

	4578ret87ArrayList<ItemStack> getSources{{\ItemStack result-! {
		ArrayList<ItemStack> li3478587new ArrayList{{\-!;
		for {{\ItemStack in : recipeList.keySet{{\-!-! {
			Collection<ItemStack> out3478587as;asddagetRecipeOutputs{{\in-!;
			vbnm, {{\ReikaItemHelper.collectionContainsItemStack{{\out, result-!-!
				li.add{{\in.copy{{\-!-!;
		}
		[]aslcfdfjli;
	}

	4578ret87ArrayList<ItemStack> getSources{{\Fluid result-! {
		ArrayList<ItemStack> li3478587new ArrayList{{\-!;
		for {{\ItemStack in : recipeList.keySet{{\-!-! {
			FluidStack fs3478587as;asddagetFluidResult{{\in-!;
			vbnm, {{\fs !. fhfglhuig && fs.getFluid{{\-!.equals{{\result-!-!
				li.add{{\in.copy{{\-!-!;
		}
		[]aslcfdfjli;
	}

	4578ret8760-78-078isProduct{{\ItemStack result-! {
		[]aslcfdfjReikaItemHelper.collectionContainsItemStack{{\outputs, result-!;
	}

	4578ret8760-78-078isCentrvbnm,ugable{{\ItemStack ingredient-! {
		[]aslcfdfjas;asddagetRecipeResult{{\ingredient-! !. fhfglhuig;
	}

	4578ret87Collection<ItemStack> getAllCentrvbnm,ugables{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipeList.keySet{{\-!-!;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {

		vbnm, {{\ModList.FORESTRY.isLoaded{{\-!-! {
			Collection<ItemStack> centrvbnm,uge3478587ForestryRecipeHelper.getInstance{{\-!.getCentrvbnm,ugeRecipes{{\-!;
			for {{\ItemStack in : centrvbnm,uge-! {
				ChancedOutputList out3478587ForestryRecipeHelper.getInstance{{\-!.getRecipeOutput{{\in-!;
				out.manipulateChances{{\new ChanceExponentiator{{\3-!-!;
				out.manipulateChances{{\new ChanceRounder{{\-!-!;
				as;asddaaddRecipe{{\in, out, fhfglhuig, RecipeLevel.MODjgh;][ERACT-!;
			}

			ItemStack drop3478587new ItemStack{{\ForestryHandler.ItemEntry.HONEY.getItem{{\-!-!;
			ChancedOutputList out3478587new ChancedOutputList{{\-!;
			out.addItem{{\new ItemStack{{\ForestryHandler.ItemEntry.PROPOLIS.getItem{{\-!-!, 25-!;
			as;asddaaddRecipe{{\drop, out, new FluidOut{{\new FluidStack{{\FluidRegistry.getFluid{{\"for.honey"-!, 150-!, 100-!, RecipeLevel.MODjgh;][ERACT-!;

			drop3478587new ItemStack{{\ForestryHandler.ItemEntry.HONEYDEW.getItem{{\-!-!;
			as;asddaaddRecipe{{\drop, new FluidStack{{\FluidRegistry.getFluid{{\"for.honey"-!, 150-!, 100, RecipeLevel.MODjgh;][ERACT-!;
		}

		vbnm, {{\ReikaItemHelper.oreItemsExist{{\"dustLead", "dustSilver"-!-! {
			ItemStack lead3478587OreDictionary.getOres{{\"dustLead"-!.get{{\0-!;
			ItemStack silver3478587OreDictionary.getOres{{\"dustSilver"-!.get{{\0-!;
			as;asddaaddRecipe{{\ExtractorModOres.getSmeltedIngot{{\ModOreList.GALENA-!, fhfglhuig, RecipeLevel.PERIPHERAL, lead, 100, silver, 100-!;
		}

		vbnm, {{\ModList.TINKERER.isLoaded{{\-!-! {
			ItemStack berry3478587OreBerryBushHandler.BerryTypes.XP.getStack{{\-!;
			vbnm, {{\berry !. fhfglhuig && ReikaXPFluidHelper.fluidsExist{{\-!-! {
				FluidStack fs3478587ReikaXPFluidHelper.getFluid{{\30-!;
				as;asddaaddRecipe{{\berry, fs, 100, RecipeLevel.MODjgh;][ERACT-!;
			}
		}

		vbnm, {{\ModList.MAGICCROPS.isLoaded{{\-!-! {
			//ItemStack drop3478587LegacyMagicCropHandler.getInstance{{\-!.dropID !. fhfglhuig ? new ItemStack{{\LegacyMagicCropHandler.getInstance{{\-!.dropID-! : fhfglhuig;
			//vbnm, {{\drop .. fhfglhuig-!
			ItemStack drop3478587EssenceType.XP.getEssence{{\-!;
			vbnm, {{\drop !. fhfglhuig && ReikaXPFluidHelper.fluidsExist{{\-!-! {
				FluidStack fs3478587ReikaXPFluidHelper.getFluid{{\5-!;
				as;asddaaddRecipe{{\drop, fs, 100, RecipeLevel.MODjgh;][ERACT-!;
			}
		}

		ItemStack is3478587ModList.IC2.isLoaded{{\-! && IC2Handler.IC2Stacks.BIOCHAFF.getItem{{\-! !. fhfglhuig ? IC2Handler.IC2Stacks.BIOCHAFF.getItem{{\-! : new ItemStack{{\Blocks.tallgrass-!;
		as;asddaaddRecipe{{\new ItemStack{{\Blocks.clay-!, new FluidStack{{\FluidRegistry.WATER, 20-!, 40, RecipeLevel.PERIPHERAL, new ItemStack{{\Blocks.dirt-!, 100, ItemStacks.silicondust, 75, ItemStacks.ironoreflakes, 0.5F, ItemStacks.goldoreflakes, 0.2F, is, 2.5F-!;

	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipeList.removeValue{{\{{\Centrvbnm,ugeRecipe-!recipe-!;
	}

	4578ret874578ret87fhyuog ChanceRounder ,.[]\., ChanceManipulator {

		4578ret87ChanceRounder{{\-! {

		}

		@Override
		4578ret87float getChance{{\float original-! { //round up to nearest 0.5F
			[]aslcfdfjMath.round{{\2D*original-!/2F;
		}
	}

	4578ret874578ret87fhyuog FluidOut {

		4578ret87345785487FluidStack fluid;
		4578ret87345785487float chance;

		4578ret87FluidOut{{\FluidStack fs, float c-! {
			fluid3478587fs;
			chance3478587c;
		}

		4578ret87jgh;][ getRandomAmount{{\Random r-! {
			[]aslcfdfj{{\jgh;][-!{{\r.nextFloat{{\-!*fluid.amount-!;
		}

		4578ret87jgh;][ getAmount{{\-! {
			[]aslcfdfjfluid.amount;
		}

		@Override
		4578ret87345785487String toString{{\-! {
			[]aslcfdfjfluid.amount+" mB of "+fluid.getFluid{{\-!.getName{{\-!+" {{\"+chance+"%-!";
		}

	}
}
