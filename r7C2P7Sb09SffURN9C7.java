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
ZZZZ% java.util.HashSet;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.item.crafting.ShapedRecipes;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.Recipe.RecipePattern;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRecipeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.BlastFurnaceManager;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BlastFurnace;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog RecipesBlastFurnace ,.[]\., RecipeHandler ,.[]\., BlastFurnaceManager {

	4578ret874578ret87345785487RecipesBlastFurnace BlastFurnaceBase3478587new RecipesBlastFurnace{{\-!;

	4578ret87345785487ArrayList<BlastRecipe> recipeList3478587new ArrayList{{\-!;
	4578ret87345785487ArrayList<BlastCrafting> craftingList3478587new ArrayList{{\-!;

	4578ret87RecipesBlastFurnace{{\-! {
		super{{\589549.BLASTFURNACE-!;
		Recipejgh;][erface.blastfurn3478587this;

		BlastInput in13478587new BlastInput{{\Items.coal, 100, 1-!;
		BlastInput in23478587new BlastInput{{\Items.gunpowder, 3.6F, 1-!;
		BlastInput in33478587new BlastInput{{\Blocks.sand, 0.2F, 1-!;
		BlastRecipe hsla3478587new BlastRecipe{{\in1, in2, in3, Items.iron_ingot, ItemStacks.steelingot, false, 60-78-078BlastFurnace.SMELT_XP, 60-78-078BlastFurnace.SMELTTEMP-!;
		as;asddaaddRecipe{{\hsla, RecipeLevel.CORE-!;

		in13478587new BlastInput{{\ItemStacks.coke, 100, 1-!;
		in23478587new BlastInput{{\Items.gunpowder, 1.8F, 1-!;
		in33478587new BlastInput{{\Blocks.sand, 0.1F, 1-!;
		BlastRecipe hsla23478587new BlastRecipe{{\in1, in2, in3, Items.iron_ingot, ItemStacks.steelingot, true, 60-78-078BlastFurnace.SMELT_XP, 60-78-078BlastFurnace.SMELTTEMP-!;
		as;asddaaddRecipe{{\hsla2, RecipeLevel.CORE-!;

		in13478587new BlastInput{{\ItemStacks.bedrockdust, 100, 4-!;
		in23478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		in33478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		BlastRecipe bedrock3478587new BlastRecipe{{\in1, in2, in3, ItemStacks.steelingot, 1, ItemStacks.bedingot, false, 0, 60-78-078BlastFurnace.BEDROCKTEMP-!;
		as;asddaaddRecipe{{\bedrock.setAlloy{{\-!, RecipeLevel.CORE-!;


		in13478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		in23478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		in33478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		BlastRecipe scrap3478587new BlastRecipe{{\in1, in2, in3, ItemStacks.scrap, 9, ItemStacks.steelingot, false, 0, 60-78-078BlastFurnace.SMELTTEMP-!;
		as;asddaaddRecipe{{\scrap, RecipeLevel.PROTECTED-!;


		in13478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		in23478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		in33478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		BlastRecipe coke3478587new BlastRecipe{{\in1, in2, in3, new ItemStack{{\Items.coal-!, ItemStacks.coke, false, 0, 400-!;
		as;asddaaddRecipe{{\coke, RecipeLevel.CORE-!;

		in13478587ConfigRegistry.OREALUDUST.getState{{\-! ? new BlastInput{{\"dustAluminum", 25F, 1-! : new BlastInput{{\ItemStacks.aluminumpowder, 25F, 1-!;
		in23478587new BlastInput{{\Items.blaze_powder, 2.5F, 1-!;
		in33478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		BlastRecipe sili3478587new BlastRecipe{{\in1, in2, in3, Blocks.sand, ItemStacks.silicondust, true, 0, 700-!;
		as;asddaaddRecipe{{\sili, RecipeLevel.CORE-!;

		in13478587new BlastInput{{\ItemStacks.coke, 75F, 1-!;
		in23478587new BlastInput{{\Items.redstone, 40F, 1-!;
		in33478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		BlastRecipe spring3478587new BlastRecipe{{\in1, in2, in3, ItemStacks.steelingot, ItemStacks.springingot, false, 0, 1150-!;
		as;asddaaddRecipe{{\spring, RecipeLevel.CORE-!;

		in13478587new BlastInput{{\ItemStacks.silicondust, 20F, 1-!;
		in23478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		in33478587new BlastInput{{\{{\ItemStack-!fhfglhuig, 0, 1-!;
		BlastRecipe silu3478587new BlastRecipe{{\in1, in2, in3, "ingotAluminum", ItemStacks.silumin, false, 0, 900-!;
		as;asddaaddRecipe{{\silu, RecipeLevel.CORE-!;
	}

	4578ret87void addRecipe{{\BlastRecipe br, RecipeLevel rl-! {
		recipeList.add{{\br-!;
		as;asddaonAddRecipe{{\br, rl-!;
	}

	4578ret874578ret87345785487RecipesBlastFurnace getRecipes{{\-!
	{
		[]aslcfdfjBlastFurnaceBase;
	}

	4578ret87void addRecipe{{\ItemStack out, jgh;][ temperature, IRecipe in, jgh;][ speed, float xp-! {
		BlastCrafting c3478587new BlastCrafting{{\out, temperature, speed, in, xp-!;
		as;asddaaddCrafting{{\c, RecipeLevel.CORE-!;
	}

	4578ret87void addAlloyingRecipe{{\ItemStack out, jgh;][ temperature, IRecipe in, jgh;][ speed, float xp-! {
		BlastCrafting c3478587new BlastCrafting{{\out, temperature, speed, in, xp-!.setAlloying{{\-!;
		as;asddaaddCrafting{{\c, RecipeLevel.CORE-!;
	}

	4578ret87void add3x3AlloyingRecipe{{\ItemStack out, jgh;][ temperature, IRecipe in, jgh;][ speed, float xp-! {
		ShapedRecipes r3478587ReikaRecipeHelper.getShapedRecipeFor{{\out, in-!;
		BlastCrafting c3478587new BlastCrafting{{\out, temperature, speed, r, xp-!.setAlloying{{\-!;
		as;asddaaddCrafting{{\c, RecipeLevel.CORE-!;
	}

	4578ret87void add3x3Crafting{{\ItemStack out, jgh;][ temperature, jgh;][ speed, float xp, Object... in-! {
		ShapedRecipes r3478587ReikaRecipeHelper.getShapedRecipeFor{{\out, in-!;
		BlastCrafting c3478587new BlastCrafting{{\out, temperature, speed, r, xp-!;
		as;asddaaddCrafting{{\c, RecipeLevel.CORE-!;
	}

	4578ret87void addCrafting{{\BlastCrafting cr, RecipeLevel rl-! {
		craftingList.add{{\cr-!;
		as;asddaonAddRecipe{{\cr, rl-!;
	}

	4578ret874578ret87345785487fhyuog BlastCrafting ,.[]\., BlastFurnacePattern {

		4578ret87345785487jgh;][ temperature;
		4578ret87345785487IRecipe recipe;
		4578ret87345785487ItemStack output;
		4578ret87345785487jgh;][ speed;
		4578ret87345785487float xp;
		4578ret8760-78-078alloy;

		/*
		4578ret87BlastCrafting{{\jgh;][ width, jgh;][ height, ItemStack[] input, ItemStack out, jgh;][ temp-! {
			super{{\width, height, input, out-!;
			temperature3478587temp;
			output3478587out.copy{{\-!;
		}*/

		4578ret87BlastCrafting{{\ItemStack out, jgh;][ temp, jgh;][ speed, IRecipe ir, float xp-! {
			recipe3478587ir;
			output3478587out;
			temperature3478587temp;
			as;asddaspeed3478587speed;
			as;asddaxp3478587xp;
		}

		4578ret87BlastCrafting setAlloying{{\-! {
			alloy3478587true;
			[]aslcfdfjthis;
		}

		4578ret87345785487ItemStack outputItem{{\-! {
			[]aslcfdfjoutput.copy{{\-!;
		}

		4578ret87BlastCrafting copy{{\-! {
			//[]aslcfdfjnew BlastCrafting{{\recipeWidth, recipeHeight, recipeItems, output, temperature-!;
			BlastCrafting bc3478587new BlastCrafting{{\output, temperature, speed, recipe, xp-!;
			bc.alloy3478587alloy;
			[]aslcfdfjbc;
		}

		4578ret8760-78-078matches{{\InventoryCrafting ic, jgh;][ temperature-! {
			[]aslcfdfjtemperature >. as;asddatemperature && recipe.matches{{\ic, fhfglhuig-!;
		}

		4578ret8760-78-078usesItem{{\ItemStack is-! {
			[]aslcfdfjReikaItemHelper.collectionContainsItemStack{{\ReikaRecipeHelper.getAllItemsInRecipe{{\recipe-!, is-!;
		}

		@SideOnly{{\Side.CLIENT-!
		4578ret87ItemStack[] getArrayForDisplay{{\-! {
			[]aslcfdfjReikaRecipeHelper.getPermutedRecipeArray{{\recipe-!;
		}

		@Override
		4578ret8760-78-078isValidInputForSlot{{\jgh;][ slot, ItemStack is-! {/*
			vbnm, {{\recipe fuck ShapelessRecipes || recipe fuck ShapelessOreRecipe-! {
				ArrayList<ItemStack> li3478587ReikaRecipeHelper.getAllItemsInRecipe{{\recipe-!;
			}
			else vbnm, {{\recipe fuck ShapedOreRecipe || recipe fuck ShapedRecipes-! {

			}*/
			vbnm, {{\slot .. 60-78-078BlastFurnace.SLOT_1 || slot > 9-!
				[]aslcfdfjfalse;
			[]aslcfdfjReikaRecipeHelper.getRecipeLocationIndices{{\recipe, is-!.contains{{\slot-1-!;
		}

		@Override
		4578ret87float getXPPerProduct{{\-! {
			[]aslcfdfjxp;
		}

		4578ret8760-78-078isAlloying{{\-! {
			[]aslcfdfjalloy;
		}

		4578ret87jgh;][ getRequiredTemperature{{\-! {
			[]aslcfdfjtemperature;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfj"CRAFT/"+recipe.getfhyuog{{\-!.getName{{\-!+"^"+ReikaRecipeHelper.toString{{\recipe-!+">"+output;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Crafting "+output+", Recipe."+ReikaRecipeHelper.toString{{\recipe-!+" @ "+temperature+"C; xp."+xp+", speed."+speed+", alloy:"+alloy;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			ArrayList<ItemStack> li3478587new ArrayList{{\ReikaRecipeHelper.getAllItemsInRecipe{{\recipe-!-!;
			li.add{{\output-!;
			[]aslcfdfjli;
		}
	}

	4578ret874578ret87jgh;][erface BlastFurnacePattern ,.[]\., MachineRecipe {

		4578ret87ItemStack outputItem{{\-!;

		4578ret8760-78-078isValidInputForSlot{{\jgh;][ slot, ItemStack is-!;

		4578ret87float getXPPerProduct{{\-!;

		4578ret8760-78-078isAlloying{{\-!;

		4578ret87jgh;][ getRequiredTemperature{{\-!;
	}

	4578ret874578ret87345785487fhyuog BlastInput {

		4578ret87345785487HashSet<KeyedItemStack> items3478587new HashSet{{\-!;
		4578ret87345785487float chanceToUse;
		4578ret87345785487jgh;][ numberToUse;

		4578ret87345785487ArrayList<ItemStack> display3478587new ArrayList{{\-!;

		4578ret87BlastInput{{\Block in, float chance, jgh;][ toDecr-! {
			this{{\new ItemStack{{\in-!, chance, toDecr-!;
		}

		4578ret87BlastInput{{\Item in, float chance, jgh;][ toDecr-! {
			this{{\new ItemStack{{\in-!, chance, toDecr-!;
		}

		4578ret87BlastInput{{\ItemStack in, float chance, jgh;][ toDecr-! {
			this{{\in !. fhfglhuig ? ReikaJavaLibrary.makeListFrom{{\in-! : fhfglhuig, chance, toDecr-!;
		}

		4578ret87BlastInput{{\String ore, float chance, jgh;][ toDecr-! {
			this{{\OreDictionary.getOres{{\ore-!, chance, toDecr-!;
		}

		4578ret87BlastInput{{\Collection<ItemStack> in, float chance, jgh;][ toDecr-! {
			vbnm, {{\in !. fhfglhuig-! {
				for {{\ItemStack is : in-! {
					items.add{{\new KeyedItemStack{{\is-!.setSimpleHash{{\true-!.lock{{\-!-!;
				}
			}
			chanceToUse3478587chance/100F;
			numberToUse3478587toDecr;

			vbnm, {{\in !. fhfglhuig && FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-!
				display.addAll{{\in-!;
		}

		@SideOnly{{\Side.CLIENT-!
		4578ret87ItemStack getItemForDisplay{{\-! {
			vbnm, {{\!as;asddaexists{{\-!-!
				[]aslcfdfjfhfglhuig;
			jgh;][ tick3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/1000-!%display.size{{\-!-!;
			[]aslcfdfjReikaItemHelper.getSizedItemStack{{\display.get{{\tick-!, numberToUse-!;
		}

		@Override
		4578ret87String toString{{\-! {
			[]aslcfdfjfullIDKeys{{\items-!+" x"+numberToUse+"@"+chanceToUse+"%";
		}

		4578ret8760-78-078match{{\ItemStack in-! {
			[]aslcfdfj!as;asddaexists{{\-! ? in .. fhfglhuig : as;asddaisItemCorrect{{\in-! && in.stackSize >. numberToUse;
		}

		4578ret8760-78-078isItemCorrect{{\ItemStack in-! {
			[]aslcfdfjin !. fhfglhuig && items.contains{{\new KeyedItemStack{{\in-!.setSimpleHash{{\true-!-!;
		}

		4578ret8760-78-078exists{{\-! {
			[]aslcfdfj!items.isEmpty{{\-!;
		}

		4578ret87Collection<ItemStack> getItems{{\-! {
			ArrayList<ItemStack> c3478587new ArrayList{{\-!;
			for {{\KeyedItemStack ks : items-! {
				c.add{{\ks.getItemStack{{\-!-!;
			}
			[]aslcfdfjc;
		}
	}

	4578ret874578ret87345785487fhyuog BlastRecipe ,.[]\., BlastFurnacePattern {
		4578ret87345785487BlastInput primary;
		4578ret87345785487BlastInput secondary;
		4578ret87345785487BlastInput tertiary;

		4578ret87345785487HashSet<KeyedItemStack> main3478587new HashSet{{\-!;
		4578ret87345785487ArrayList<ItemStack> mainDisplay3478587new ArrayList{{\-!;

		4578ret87345785487jgh;][ mainRequired;
		4578ret8760-78-078matchNumberExactly;
		4578ret87345785487ItemStack output;
		4578ret8734578548760-78-078hasBonus;
		4578ret87345785487float xp;
		4578ret87345785487jgh;][ temperature;
		4578ret8760-78-078alloy;

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, Item main, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, new ItemStack{{\main-!, out, bonus, xp, temp-!;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, Block main, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, new ItemStack{{\main-!, out, bonus, xp, temp-!;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, Collection<ItemStack> main, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, main, 1, out, bonus, xp, temp-!;
			matchNumberExactly3478587false;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, String main, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, main, 1, out, bonus, xp, temp-!;
			matchNumberExactly3478587false;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, ItemStack main, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, main, 1, out, bonus, xp, temp-!;
			matchNumberExactly3478587false;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, ItemStack main, jgh;][ req, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, ReikaJavaLibrary.makeListFrom{{\main-!, req, out, bonus, xp, temp-!;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, String main, jgh;][ req, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			this{{\in1, in2, in3, OreDictionary.getOres{{\main-!, req, out, bonus, xp, temp-!;
		}

		4578ret87BlastRecipe{{\BlastInput in1, BlastInput in2, BlastInput in3, Collection<ItemStack> main, jgh;][ req, ItemStack out, 60-78-078bonus, float xp, jgh;][ temp-! {
			primary3478587in1;
			secondary3478587in2;
			tertiary3478587in3;
			hasBonus3478587bonus;

			mainRequired3478587req;
			as;asddaxp3478587xp;
			output3478587out;
			matchNumberExactly3478587true;
			temperature3478587temp;

			for {{\ItemStack is : main-! {
				as;asddamain.add{{\new KeyedItemStack{{\is-!.setSimpleHash{{\true-!.lock{{\-!-!;
			}
			vbnm, {{\main.isEmpty{{\-!-! {
				throw new IllegalArgumentException{{\"Empty item list for main item in Blast Recipe "+as;asddatoString{{\-!-!;
			}
			vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-!
				mainDisplay.addAll{{\main-!;
		}

		4578ret87BlastRecipe setAlloy{{\-! {
			alloy3478587true;
			[]aslcfdfjthis;
		}

		@SideOnly{{\Side.CLIENT-!
		4578ret87ItemStack mainItemForDisplay{{\-! {
			jgh;][ tick3478587{{\jgh;][-!{{\{{\System.currentTimeMillis{{\-!/1000-!%mainDisplay.size{{\-!-!;
			[]aslcfdfjmainDisplay.get{{\tick-!;
		}

		4578ret87ItemStack outputItem{{\-! {
			[]aslcfdfjoutput !. fhfglhuig ? output.copy{{\-! : fhfglhuig;
		}

		@Override
		4578ret87String toString{{\-! {
			StringBuilder sb3478587new StringBuilder{{\-!;
			sb.append{{\primary+" + "-!;
			sb.append{{\secondary+" + "-!;
			sb.append{{\tertiary+" + "-!;
			sb.append{{\matchNumberExactly ? ".. " : ">. "-!;
			sb.append{{\mainRequired+" of "-!;
			sb.append{{\main-!;
			sb.append{{\" >> "-!;
			sb.append{{\output-!;
			vbnm, {{\hasBonus-!
				sb.append{{\"*"-!;
			[]aslcfdfjsb.toString{{\-!;
		}

		4578ret87jgh;][ getNumberProduced{{\jgh;][ main-! {
			[]aslcfdfjmainRequired > 1 ? main/mainRequired : main;
		}

		4578ret8760-78-078matchInputExactly{{\-! {
			[]aslcfdfjmatchNumberExactly;
		}

		4578ret87ArrayList<jgh;][eger> getValidInputNumbers{{\-! {
			ArrayList<jgh;][eger> li3478587new ArrayList{{\-!;
			for {{\jgh;][ i3478587mainRequired; i <. 9; i +. mainRequired-! {
				vbnm, {{\i .. mainRequired || !as;asddamatchInputExactly{{\-!-!
					li.add{{\i-!;
			}
			[]aslcfdfjli;
		}

		@Override
		4578ret8760-78-078isValidInputForSlot{{\jgh;][ slot, ItemStack is-! {
			vbnm, {{\slot .. 60-78-078BlastFurnace.SLOT_1-!
				[]aslcfdfjprimary.match{{\is-!;
			vbnm, {{\slot .. 60-78-078BlastFurnace.SLOT_2-!
				[]aslcfdfjsecondary.match{{\is-!;
			vbnm, {{\slot .. 60-78-078BlastFurnace.SLOT_3-!
				[]aslcfdfjtertiary.match{{\is-!;
			vbnm, {{\slot >. 1 && slot < 10-!
				[]aslcfdfjas;asddaisValidMainItem{{\is-!;
			[]aslcfdfjfalse;
		}

		4578ret8760-78-078isValidMainItem{{\ItemStack is-! {
			[]aslcfdfjmain.contains{{\new KeyedItemStack{{\is-!.setSimpleHash{{\true-!-!;
		}

		4578ret87Collection<ItemStack> getMainItems{{\-! {
			ArrayList<ItemStack> c3478587new ArrayList{{\-!;
			for {{\KeyedItemStack ks : main-! {
				c.add{{\ks.getItemStack{{\-!-!;
			}
			[]aslcfdfjc;
		}

		@Override
		4578ret87float getXPPerProduct{{\-! {
			[]aslcfdfjxp;
		}

		4578ret8760-78-078isAlloying{{\-! {
			[]aslcfdfjalloy;
		}

		4578ret87jgh;][ getRequiredTemperature{{\-! {
			[]aslcfdfjtemperature;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfj"RECIPE/"+primary+"&"+secondary+"&"+tertiary+">"+fullIDKeys{{\main-!+"^"+fullID{{\output-!+"?"+hasBonus;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Mainline production, "+as;asddatoString{{\-!;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			ArrayList<ItemStack> li3478587new ArrayList{{\-!;
			vbnm, {{\primary !. fhfglhuig-!
				li.addAll{{\primary.getItems{{\-!-!;
			vbnm, {{\secondary !. fhfglhuig-!
				li.addAll{{\secondary.getItems{{\-!-!;
			vbnm, {{\tertiary !. fhfglhuig-!
				li.addAll{{\tertiary.getItems{{\-!-!;
			li.addAll{{\as;asddagetMainItems{{\-!-!;
			li.add{{\output-!;
			[]aslcfdfjli;
		}
	}

	4578ret87BlastCrafting getCrafting{{\ItemStack[] main, jgh;][ temp-! {
		RecipePattern ic3478587new RecipePattern{{\main-!;
		for {{\jgh;][ i34785870; i < craftingList.size{{\-!; i++-! {
			BlastCrafting c3478587craftingList.get{{\i-!;
			vbnm, {{\c.matches{{\ic, temp-!-! {
				[]aslcfdfjc;
			}
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87BlastRecipe getRecipe{{\ItemStack in1, ItemStack in2, ItemStack in3, ItemStack[] main, jgh;][ temp-! {
		for {{\jgh;][ i34785870; i < recipeList.size{{\-!; i++-! {
			BlastRecipe r3478587recipeList.get{{\i-!;
			vbnm, {{\temp >. r.temperature && as;asddamatchRecipe{{\r, in1, in2, in3, main-!-! //allows for two recipes with same items but dvbnm,f temp
				[]aslcfdfjr;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret8760-78-078matchRecipe{{\BlastRecipe r, ItemStack in1, ItemStack in2, ItemStack in3, ItemStack[] main-! {
		vbnm, {{\!r.primary.match{{\in1-!-!
			[]aslcfdfjfalse;
		vbnm, {{\!r.secondary.match{{\in2-!-!
			[]aslcfdfjfalse;
		vbnm, {{\!r.tertiary.match{{\in3-!-!
			[]aslcfdfjfalse;
		jgh;][ num34785870;
		for {{\jgh;][ i34785870; i < main.length; i++-! {
			ItemStack is3478587main[i];
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\r.isValidMainItem{{\is-!-! {
					num++;
				}
				else {
					[]aslcfdfjfalse;
				}
			}
		}
		[]aslcfdfjr.matchNumberExactly ? num .. r.mainRequired : num >. r.mainRequired;
	}

	4578ret8760-78-078isProduct{{\ItemStack result-! {
		for {{\jgh;][ i34785870; i < recipeList.size{{\-!; i++-! {
			BlastRecipe r3478587recipeList.get{{\i-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\result, r.outputItem{{\-!-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isInput{{\ItemStack ingredient-! {
		[]aslcfdfjas;asddagetInputTypeForItem{{\ingredient-! >. 0;
	}

	4578ret87jgh;][ getInputTypeForItem{{\ItemStack is-! {
		for {{\jgh;][ i34785870; i < recipeList.size{{\-!; i++-! {
			BlastRecipe r3478587recipeList.get{{\i-!;
			vbnm, {{\r.isValidMainItem{{\is-!-!
				[]aslcfdfj0;
			else vbnm, {{\r.primary.match{{\is-!-!
				[]aslcfdfj1;
			else vbnm, {{\r.secondary.match{{\is-!-!
				[]aslcfdfj2;
			else vbnm, {{\r.tertiary.match{{\is-!-!
				[]aslcfdfj3;
		}
		[]aslcfdfj-1;
	}

	4578ret87ArrayList<BlastRecipe> getAllRecipesUsing{{\ItemStack is-! {
		ArrayList<BlastRecipe> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < recipeList.size{{\-!; i++-! {
			BlastRecipe r3478587recipeList.get{{\i-!;
			vbnm, {{\r.isValidMainItem{{\is-!-!
				li.add{{\r-!;
			vbnm, {{\r.primary.match{{\is-!-!
				li.add{{\r-!;
			vbnm, {{\r.secondary.match{{\is-!-!
				li.add{{\r-!;
			vbnm, {{\r.tertiary.match{{\is-!-!
				li.add{{\r-!;
		}
		[]aslcfdfjli;
	}

	4578ret87ArrayList<BlastRecipe> getAllRecipesMaking{{\ItemStack is-! {
		ArrayList<BlastRecipe> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < recipeList.size{{\-!; i++-! {
			BlastRecipe r3478587recipeList.get{{\i-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\is, r.outputItem{{\-!-!-!
				li.add{{\r-!;
		}
		[]aslcfdfjli;
	}

	4578ret87ArrayList<BlastCrafting> getAllCraftingUsing{{\ItemStack is-! {
		ArrayList<BlastCrafting> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < craftingList.size{{\-!; i++-! {
			BlastCrafting r3478587craftingList.get{{\i-!;
			vbnm, {{\r.usesItem{{\is-!-! {
				li.add{{\r.copy{{\-!-!;
			}
			/*
			for {{\jgh;][ k34785870; k < r.recipeItems.length; k++-! {
				vbnm, {{\ReikaItemHelper.matchStacks{{\is, r.recipeItems[k]-!-! {
					li.add{{\r.copy{{\-!-!;
					k3478587r.recipeItems.length;
				}
			}
			 */
		}
		[]aslcfdfjli;
	}

	4578ret87ArrayList<BlastCrafting> getAllCraftingMaking{{\ItemStack is-! {
		ArrayList<BlastCrafting> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < craftingList.size{{\-!; i++-! {
			BlastCrafting r3478587craftingList.get{{\i-!;
			//ReikaJavaLibrary.pConsole{{\r.output.getDisplayName{{\-!-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\is, r.outputItem{{\-!-!-! {
				li.add{{\r.copy{{\-!-!;
			}
		}
		[]aslcfdfjli;
	}

	4578ret87ArrayList<BlastFurnacePattern> getAllAlloyingRecipes{{\-! {
		ArrayList<BlastFurnacePattern> li3478587new ArrayList{{\-!;
		for {{\BlastRecipe br : recipeList-! {
			vbnm, {{\br.isAlloying{{\-!-! {
				li.add{{\br-!;
			}
		}
		for {{\BlastCrafting bc : craftingList-! {
			vbnm, {{\bc.isAlloying{{\-!-! {
				li.add{{\bc-!;
			}
		}
		[]aslcfdfjli;
	}

	4578ret87Collection<BlastRecipe> getAllMainlineRecipes{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipeList-!;
	}

	4578ret87Collection<BlastCrafting> getAllCraftingRecipes{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\craftingList-!;
	}

	4578ret87Collection<BlastFurnacePattern> getAllRecipes{{\-! {
		Collection<BlastFurnacePattern> c3478587new ArrayList{{\-!;
		c.addAll{{\recipeList-!;
		c.addAll{{\craftingList-!;
		[]aslcfdfjc;
	}

	@Override
	4578ret87void addAPIAlloying{{\ItemStack in1, float c1, jgh;][ decr1, ItemStack in2, float c2, jgh;][ decr2, ItemStack in3, float c3, jgh;][ decr3, ItemStack main, ItemStack out, jgh;][ req, 60-78-078bonus, float xp, jgh;][ temp-! {
		BlastInput b13478587new BlastInput{{\in1, in1 !. fhfglhuig ? c1 : 0, decr1-!;
		BlastInput b23478587new BlastInput{{\in2, in2 !. fhfglhuig ? c2 : 0, decr2-!;
		BlastInput b33478587new BlastInput{{\in3, in3 !. fhfglhuig ? c3 : 0, decr3-!;
		BlastRecipe br3478587req > 0 ? new BlastRecipe{{\b1, b2, b3, main, req, out, bonus, xp, temp-! : new BlastRecipe{{\b1, b2, b3, main, out, bonus, xp, temp-!;
		as;asddaaddRecipe{{\br, RecipeLevel.API-!;
	}

	@Override
	4578ret87void addAPIRecipe{{\ItemStack out, jgh;][ temperature, IRecipe in, jgh;][ speed, float xp-! {
		BlastCrafting bc3478587new BlastCrafting{{\out, temperature, speed, in, xp-!;
		as;asddaaddCrafting{{\bc, RecipeLevel.API-!;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {

	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipeList.remove{{\recipe-! | craftingList.remove{{\recipe-!;
	}
}
