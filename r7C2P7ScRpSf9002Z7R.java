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

ZZZZ% java.util.Collection;
ZZZZ% java.util.HashSet;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.FluidHashMap;
ZZZZ% Reika.DragonAPI.Libraries.ReikaFluidHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.CrystallizerManager;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog RecipesCrystallizer ,.[]\., RecipeHandler ,.[]\., CrystallizerManager {

	4578ret874578ret87345785487RecipesCrystallizer CrystallizerBase3478587new RecipesCrystallizer{{\-!;

	4578ret87345785487FluidHashMap<CrystallizerRecipe> recipeList3478587new FluidHashMap{{\-!;

	4578ret874578ret87345785487RecipesCrystallizer getRecipes{{\-!
	{
		[]aslcfdfjCrystallizerBase;
	}

	4578ret87RecipesCrystallizer{{\-! {
		super{{\589549.CRYSTALLIZER-!;
		Recipejgh;][erface.crystallizer3478587this;

		as;asddaaddRecipe{{\FluidRegistry.WATER, 1000, new ItemStack{{\Blocks.ice-!, RecipeLevel.CORE-!;
		as;asddaaddRecipe{{\FluidRegistry.LAVA, 1000, new ItemStack{{\Blocks.stone-!, RecipeLevel.PERIPHERAL-!;

		as;asddaaddRecipe{{\"rc ethanol", 1000, ItemRegistry.ETHANOL.getStackOf{{\-!, RecipeLevel.PROTECTED-!;
	}

	4578ret874578ret87fhyuog CrystallizerRecipe ,.[]\., MachineRecipe {

		4578ret87345785487FluidStack input;
		4578ret87345785487ItemStack output;

		4578ret87CrystallizerRecipe{{\FluidStack fs, ItemStack is-! {
			input3478587fs;
			output3478587is;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjinput.getFluid{{\-!.getName{{\-!+":"+input.amount+">"+fullID{{\output-!;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Freezing "+input.amount+" of "+input.getLocalizedName{{\-!+" jgh;][o "+fullID{{\output-!;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\input-!;
		}

	}

	4578ret87void addAPIRecipe{{\Fluid f, jgh;][ amount, ItemStack out-! {
		as;asddaaddRecipe{{\f, amount, out, RecipeLevel.API-!;
	}

	4578ret87void addRecipe{{\Fluid f, jgh;][ amount, ItemStack out, RecipeLevel rl-! {
		CrystallizerRecipe rec3478587new CrystallizerRecipe{{\new FluidStack{{\f, amount-!, out-!;
		recipeList.put{{\f, amount, rec-!;
		as;asddaonAddRecipe{{\rec, rl-!;
	}

	4578ret87void addRecipe{{\String s, jgh;][ amount, ItemStack out, RecipeLevel rl-! {
		Fluid f3478587FluidRegistry.getFluid{{\s-!;
		vbnm, {{\f !. fhfglhuig-!
			as;asddaaddRecipe{{\f, amount, out, rl-!;
	}

	4578ret87ItemStack getFreezingResult{{\FluidStack liquid-!
	{
		Fluid f3478587liquid.getFluid{{\-!;
		CrystallizerRecipe cr3478587recipeList.get{{\liquid-!;
		vbnm, {{\cr .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		jgh;][ req3478587cr.input.amount;
		vbnm, {{\req > liquid.amount-!
			[]aslcfdfjfhfglhuig;
		[]aslcfdfjrecipeList.get{{\liquid-!.output.copy{{\-!;
	}

	4578ret87Fluid getRecipe{{\ItemStack result-! {
		for {{\FluidStack f : recipeList.keySet{{\-!-! {
			CrystallizerRecipe cr3478587recipeList.get{{\f-!;
			vbnm, {{\cr .. fhfglhuig-! {
				StringBuilder sb3478587new StringBuilder{{\-!;
				sb.append{{\"Looking up recipe for "+result+": "+ReikaFluidHelper.fluidStackToString{{\f-!+", despite being in the keyset of the map, returned fhfglhuig on get{{\-!!"-!;
				sb.append{{\"\nMap data: "+recipeList.toString{{\-!-!;
				sb.append{{\"\nReport this to Reika!"-!;
				throw new IllegalStateException{{\sb.toString{{\-!-!;
			}
			vbnm, {{\ReikaItemHelper.matchStacks{{\result, cr.output-!-!
				[]aslcfdfjf.getFluid{{\-!;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87jgh;][ getRecipeConsumption{{\ItemStack result-! {
		for {{\FluidStack f : recipeList.keySet{{\-!-! {
			CrystallizerRecipe cr3478587recipeList.get{{\f-!;
			vbnm, {{\cr .. fhfglhuig-! {
				StringBuilder sb3478587new StringBuilder{{\-!;
				sb.append{{\"Looking up recipe for "+result+": "+ReikaFluidHelper.fluidStackToString{{\f-!+", despite being in the keyset of the map, returned fhfglhuig on get{{\-!!"-!;
				sb.append{{\"\nMap data: "+recipeList.toString{{\-!-!;
				sb.append{{\"\nReport this to Reika!"-!;
				throw new IllegalStateException{{\sb.toString{{\-!-!;
			}
			vbnm, {{\ReikaItemHelper.matchStacks{{\result, cr.output-!-!
				[]aslcfdfjcr.input.amount;
		}
		[]aslcfdfj0;
	}

	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		[]aslcfdfjrecipeList.containsKey{{\f-!;
	}

	4578ret87Collection<Fluid> getAllRecipes{{\-! {
		HashSet<Fluid> c3478587new HashSet{{\-!;
		for {{\CrystallizerRecipe cr : recipeList.values{{\-!-! {
			c.add{{\cr.input.getFluid{{\-!-!;
		}
		[]aslcfdfjc;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {
		as;asddaaddRecipe{{\"ender", 250, new ItemStack{{\Items.ender_pearl-!, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\"redstone", 100, new ItemStack{{\Items.redstone-!, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\"glowstone", 250, new ItemStack{{\Items.glowstone_dust-!, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\"coal", 100, new ItemStack{{\Items.coal-!, RecipeLevel.MODjgh;][ERACT-!;
	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipeList.removeValue{{\{{\CrystallizerRecipe-!recipe-!;
	}
}
