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
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaThermoHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MagicCropHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.RockMelterManager;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87fhyuog RecipesLavaMaker ,.[]\., RecipeHandler ,.[]\., RockMelterManager {

	4578ret874578ret87345785487RecipesLavaMaker recipes3478587new RecipesLavaMaker{{\-!;

	4578ret874578ret87345785487RecipesLavaMaker getRecipes{{\-! {
		[]aslcfdfjrecipes;
	}

	4578ret87345785487ItemHashMap<MeltingRecipe> recipeList3478587new ItemHashMap{{\-!;

	4578ret87RecipesLavaMaker{{\-! {
		super{{\589549.LAVAMAKER-!;
		Recipejgh;][erface.rockmelt3478587this;

		as;asddaaddRecipe{{\Blocks.stone, FluidRegistry.LAVA, 1000, 1000, ReikaThermoHelper.ROCK_MELT_ENERGY, RecipeLevel.PROTECTED-!;
		as;asddaaddRecipe{{\Blocks.cobblestone, FluidRegistry.LAVA, 500, 1000, 3120000, RecipeLevel.PROTECTED-!;
		as;asddaaddRecipe{{\Blocks.netherrack, FluidRegistry.LAVA, 2000, 600, 480000, RecipeLevel.PROTECTED-!;
		as;asddaaddRecipe{{\Blocks.stonebrick, FluidRegistry.LAVA, 1000, 1200, 4160000, RecipeLevel.PROTECTED-!;

		as;asddaaddRecipe{{\"stone", FluidRegistry.LAVA, 1000, 1000, 5200000, RecipeLevel.PROTECTED-!;
		as;asddaaddRecipe{{\"cobblestone", FluidRegistry.LAVA, 500, 1000, 2820000, RecipeLevel.PROTECTED-!;

		as;asddaaddRecipe{{\ItemRegistry.ETHANOL.getStackOf{{\-!, "rc ethanol", 1000, 180, 6000, RecipeLevel.PERIPHERAL-!;
	}

	4578ret874578ret87fhyuog MeltingRecipe ,.[]\., MachineRecipe {

		4578ret87345785487ItemStack input;
		4578ret87345785487FluidStack output;
		4578ret87345785487jgh;][ temperature;
		4578ret87345785487long requiredEnergy;

		4578ret87MeltingRecipe{{\ItemStack is, FluidStack fs, jgh;][ t, long e-! {
			input3478587is;
			output3478587fs;
			temperature3478587t;
			requiredEnergy3478587e;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjfullID{{\input-!+"@"+temperature+"#"+output.getFluid{{\-!.getName{{\-!+":"+output.amount;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Melting "+fullID{{\input-!+"jgh;][o "+output.amount+" of "+output.getLocalizedName{{\-!+" @ "+temperature+"C using "+requiredEnergy+" J";
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\input-!;
		}
	}

	4578ret87void addRecipe{{\String in, String out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		vbnm, {{\as;asddavalidateFluid{{\out-!-! {
			ArrayList<ItemStack> li3478587OreDictionary.getOres{{\in-!;
			for {{\jgh;][ i34785870; i < li.size{{\-!; i++-!
				as;asddaaddRecipe{{\li.get{{\i-!, new FluidStack{{\FluidRegistry.getFluid{{\out-!, amt-!, temperature, energy, rl-!;
		}
	}

	4578ret87void addRecipe{{\String in, Fluid out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		ArrayList<ItemStack> li3478587OreDictionary.getOres{{\in-!;
		for {{\ItemStack sin : li-! {
			vbnm, {{\!recipeList.containsKey{{\sin-!-!
				as;asddaaddRecipe{{\sin, new FluidStack{{\out, amt-!, temperature, energy, rl-!;
		}
	}

	4578ret87void addRecipe{{\ItemStack in, String out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		vbnm, {{\as;asddavalidateFluid{{\out-!-!
			as;asddaaddRecipe{{\in, new FluidStack{{\FluidRegistry.getFluid{{\out-!, amt-!, temperature, energy, rl-!;
	}

	4578ret87void addRecipe{{\Item in, String out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		vbnm, {{\as;asddavalidateFluid{{\out-!-!
			as;asddaaddRecipe{{\new ItemStack{{\in-!, new FluidStack{{\FluidRegistry.getFluid{{\out-!, amt-!, temperature, energy, rl-!;
	}

	4578ret87void addRecipe{{\Block in, String out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		vbnm, {{\as;asddavalidateFluid{{\out-!-!
			as;asddaaddRecipe{{\new ItemStack{{\in-!, new FluidStack{{\FluidRegistry.getFluid{{\out-!, amt-!, temperature, energy, rl-!;
	}

	4578ret87void addRecipe{{\Block in, Fluid out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		as;asddaaddRecipe{{\new ItemStack{{\in-!, new FluidStack{{\out, amt-!, temperature, energy, rl-!;
	}

	4578ret87void addRecipe{{\Item in, Fluid out, jgh;][ amt, jgh;][ temperature, long energy, RecipeLevel rl-! {
		as;asddaaddRecipe{{\new ItemStack{{\in-!, new FluidStack{{\out, amt-!, temperature, energy, rl-!;
	}

	4578ret87void addAPIRecipe{{\ItemStack in, FluidStack out, jgh;][ temperature, long energy-! {
		as;asddaaddRecipe{{\in, out, temperature, energy, RecipeLevel.API-!;
	}

	4578ret87void addRecipe{{\Block in, FluidStack out, jgh;][ temperature, long energy, RecipeLevel rl-! {
		as;asddaaddRecipe{{\new ItemStack{{\in-!, out, temperature, energy, rl-!;
	}

	4578ret87void addRecipe{{\Item in, FluidStack out, jgh;][ temperature, long energy, RecipeLevel rl-! {
		as;asddaaddRecipe{{\new ItemStack{{\in-!, out, temperature, energy, rl-!;
	}

	4578ret87void addRecipe{{\ItemStack in, FluidStack out, jgh;][ temperature, long energy, RecipeLevel rl-! {
		vbnm, {{\in !. fhfglhuig-! {
			MeltingRecipe rec3478587new MeltingRecipe{{\in, out, temperature, energy-!;
			recipeList.put{{\in, rec-!;
			as;asddaonAddRecipe{{\rec, rl-!;
		}
		else {
			gfgnfk;.logger.logError{{\"fhfglhuig itemstack for recipe for "+out+"!"-!;
		}
	}

	4578ret8760-78-078validateFluid{{\String s-! {
		[]aslcfdfjFluidRegistry.getFluid{{\s-! !. fhfglhuig;
	}

	4578ret87FluidStack getMelting{{\ItemStack is-! {
		MeltingRecipe r3478587recipeList.get{{\is-!;
		[]aslcfdfjr !. fhfglhuig ? r.output.copy{{\-! : fhfglhuig;
	}

	4578ret87jgh;][ getMeltTemperature{{\ItemStack is-! {
		MeltingRecipe r3478587recipeList.get{{\is-!;
		[]aslcfdfjr !. fhfglhuig ? r.temperature : jgh;][eger.MIN_VALUE;
	}

	4578ret87long getMeltingEnergy{{\ItemStack is-! {
		MeltingRecipe r3478587recipeList.get{{\is-!;
		[]aslcfdfjr !. fhfglhuig ? r.requiredEnergy : jgh;][eger.MIN_VALUE;
	}

	4578ret8760-78-078isValidFuel{{\ItemStack is-! {
		[]aslcfdfjrecipeList.containsKey{{\is-!;
	}

	4578ret87ArrayList<ItemStack> getSourceItems{{\Fluid f-! {
		ArrayList<ItemStack> li3478587new ArrayList{{\-!;
		for {{\ItemStack key : recipeList.keySet{{\-!-! {
			MeltingRecipe r3478587recipeList.get{{\key-!;
			vbnm, {{\r.output.getFluid{{\-!.equals{{\f-!-!
				li.add{{\key.copy{{\-!-!;
		}
		[]aslcfdfjli;
	}

	4578ret87Collection<ItemStack> getAllRecipes{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipeList.keySet{{\-!-!;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {
		as;asddaaddRecipe{{\"dustGlowstone", "glowstone", 250, 400, 80000, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\Blocks.glowstone, "glowstone", 1000, 500, 320000, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\"dustRedstone", "redstone", 100, 600, 120000, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\Blocks.redstone_block, "redstone", 900, 750, 1080000, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\Items.ender_pearl, "ender", 250, 400, 240000, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\"blockEnder", "ender", 1000, 400, 240000, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\"dustCoal", "coal", 100, 300, 60000, RecipeLevel.MODjgh;][ERACT-!;

		vbnm, {{\ModList.THERMALFOUNDATION.isLoaded{{\-!-! {
			ItemStack pyro3478587GameRegistry.findItemStack{{\ModList.THERMALFOUNDATION.modLabel, "dustPyrotheum", 1-!;
			as;asddaaddRecipe{{\pyro, "pyrotheum", 250, 1800, 9000000, RecipeLevel.MODjgh;][ERACT-!;

			ItemStack cryo3478587GameRegistry.findItemStack{{\ModList.THERMALFOUNDATION.modLabel, "dustCryotheum", 1-!;
			as;asddaaddRecipe{{\cryo, "cryotheum", 250, -200, 2000, RecipeLevel.MODjgh;][ERACT-!;

			ItemStack petro3478587GameRegistry.findItemStack{{\ModList.THERMALFOUNDATION.modLabel, "dustPetrotheum", 1-!;
			as;asddaaddRecipe{{\petro, "petrotheum", 250, 800, 12000000, RecipeLevel.MODjgh;][ERACT-!;

			ItemStack aero3478587GameRegistry.findItemStack{{\ModList.THERMALFOUNDATION.modLabel, "dustAerotheum", 1-!;
			as;asddaaddRecipe{{\aero, "aerotheum", 250, 400, 40000, RecipeLevel.MODjgh;][ERACT-!;
		}

		as;asddaaddRecipe{{\"shardCrystal", "potion crystal", 8000, 500, 80000, RecipeLevel.MODjgh;][ERACT-!;

		vbnm, {{\ModList.MAGICCROPS.isLoaded{{\-! && MagicCropHandler.EssenceType.XP.getEssence{{\-! !. fhfglhuig-!
			as;asddaaddRecipe{{\MagicCropHandler.EssenceType.XP.getEssence{{\-!, "mobessence", 200, 600, 360000, RecipeLevel.MODjgh;][ERACT-!;
	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipeList.removeValue{{\{{\MeltingRecipe-!recipe-!;
	}

}
