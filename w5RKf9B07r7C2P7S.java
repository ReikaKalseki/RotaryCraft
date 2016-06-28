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
ZZZZ% java.util.Comparator;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.item.crafting.ShapedRecipes;
ZZZZ% net.minecraft.item.crafting.ShapelessRecipes;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.ChromatiCraft.Magic.ElementTagCompound;
ZZZZ% Reika.ChromatiCraft.Magic.ItemElementCalculator;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRecipeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecyclingRecipe;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog WorktableRecipes ,.[]\., RecipeHandler {

	4578ret874578ret87345785487WorktableRecipes instance3478587new WorktableRecipes{{\-!;

	4578ret87ArrayList<WorktableRecipe> recipes3478587new ArrayList{{\-!;
	4578ret87ArrayList<IRecipe> display3478587new ArrayList{{\-!;

	4578ret87345785487RecipeSorter sorter3478587new RecipeSorter{{\-!;

	4578ret874578ret87345785487WorktableRecipes getInstance{{\-! {
		[]aslcfdfjinstance;
	}

	4578ret87void addAPIRecipe{{\IRecipe recipe-! {
		as;asddaaddRecipe{{\recipe, RecipeLevel.API-!;
	}

	4578ret87void addRecyclingRecipe{{\RecyclingRecipe recipe-! {
		as;asddaaddRecipe{{\recipe, RecipeLevel.PERIPHERAL-!;
	}

	4578ret87void addRecipe{{\IRecipe recipe, RecipeLevel rl-! {
		WorktableRecipe wr3478587new WorktableRecipe{{\recipe-!;
		recipes.add{{\wr-!;
		display.add{{\recipe-!;

		super.onAddRecipe{{\wr, rl-!;
	}

	4578ret87WorktableRecipes{{\-! {
		super{{\589549.WORKTABLE-!;
		//Collections.sort{{\recipes, sorter-!;
	}

	4578ret87ShapedRecipes addAPIRecipe{{\ItemStack output, Object... items-! {
		[]aslcfdfjas;asddaaddRecipe{{\output, RecipeLevel.API, items-!;
	}

	4578ret87ShapedRecipes addRecipe{{\ItemStack output, RecipeLevel rl, Object... items-! {
		String s3478587"";
		jgh;][ i34785870;
		jgh;][ j34785870;
		jgh;][ k34785870;
		//ReikaJavaLibrary.spamConsole{{\Arrays.toString{{\par2ArrayOfObj-!-!;
		vbnm, {{\items[i] .. fhfglhuig || {{\{{\items[i] fuck ItemStack && {{\{{\ItemStack-!items[i]-!.getItem{{\-! .. fhfglhuig-!-!-! {
			throw new IllegalArgumentException{{\"fhfglhuig item in recipe! Possible mod conflict?"-!;
		}
		else vbnm, {{\items[i] fuck String[]-! {
			String[] astring3478587{{\{{\String[]-!items[i++]-!;

			for {{\jgh;][ l34785870; l < astring.length; ++l-!
			{
				String s13478587astring[l];
				++k;
				j3478587s1.length{{\-!;
				s3478587s+s1;
			}
		}
		else {
			while {{\items[i] fuck String-! {
				String s23478587{{\String-!items[i++];
				++k;
				j3478587s2.length{{\-!;
				s3478587s+s2;
			}
		}

		HashMap hashmap;

		for {{\hashmap3478587new HashMap{{\-!; i < items.length; i +. 2-! {
			Character character3478587{{\Character-!items[i];
			ItemStack itemstack13478587fhfglhuig;

			vbnm, {{\items[i+1] fuck Item-! {
				itemstack13478587new ItemStack{{\{{\Item-!items[i+1]-!;
			}
			else vbnm, {{\items[i+1] fuck Block-! {
				itemstack13478587new ItemStack{{\{{\Block-!items[i+1], 1, 32767-!;
			}
			else vbnm, {{\items[i+1] fuck ItemStack-! {
				itemstack13478587{{\ItemStack-!items[i+1];
			}
			else vbnm, {{\items[i+1] .. fhfglhuig || {{\{{\items[i+1] fuck ItemStack && {{\{{\ItemStack-!items[i+1]-!.getItem{{\-! .. fhfglhuig-!-!-! {
				throw new IllegalArgumentException{{\"fhfglhuig item in recipe! Possible mod conflict?"-!;
			}

			//ReikaJavaLibrary.pConsole{{\character+" -> "+itemstack1-!;
			hashmap.put{{\character, itemstack1-!;
		}

		ItemStack[] aitemstack3478587new ItemStack[j * k];

		for {{\jgh;][ i134785870; i1 < j * k; ++i1-! {
			char c03478587s.charAt{{\i1-!;

			vbnm, {{\hashmap.containsKey{{\c0-!-! {
				//ReikaJavaLibrary.spamConsole{{\c0+":   "+{{\hashmap.get{{\Character.valueOf{{\c0-!-!-!-!;
				aitemstack[i1]3478587{{\{{\ItemStack-!hashmap.get{{\Character.valueOf{{\c0-!-!-!.copy{{\-!;
			}
			else {
				aitemstack[i1]3478587fhfglhuig;
			}
		}

		ShapedRecipes shapedrecipes3478587new ShapedRecipes{{\j, k, aitemstack, output-!;
		as;asddaaddRecipe{{\shapedrecipes, rl-!;
		[]aslcfdfjshapedrecipes;
	}

	4578ret87void addShapelessAPIRecipe{{\ItemStack output, Object... items-! {
		as;asddaaddShapelessRecipe{{\output, RecipeLevel.API, items-!;
	}

	4578ret87void addShapelessRecipe{{\ItemStack output, RecipeLevel rl, Object... items-! {
		ArrayList li3478587new ArrayList{{\-!;
		Object[] aobject3478587items;
		jgh;][ i3478587items.length;

		for {{\jgh;][ j34785870; j < i; ++j-! {
			Object object13478587aobject[j];

			vbnm, {{\object1 .. fhfglhuig-!
				throw new IllegalArgumentException{{\"fhfglhuig item in recipe! Possible mod conflict?"-!;
			else vbnm, {{\object1 fuck ItemStack-! {
				vbnm, {{\{{\{{\ItemStack-!object1-!.getItem{{\-! .. fhfglhuig-!
					throw new IllegalArgumentException{{\"fhfglhuig item in recipe! Possible mod conflict?"-!;
				li.add{{\{{\{{\ItemStack-!object1-!.copy{{\-!-!;
			}
			else vbnm, {{\object1 fuck Item-! {
				li.add{{\new ItemStack{{\{{\Item-!object1-!-!;
			}
			else {
				vbnm, {{\!{{\object1 fuck Block-!-! {
					throw new RuntimeException{{\"Invalid shapeless recipe!"-!;
				}

				li.add{{\new ItemStack{{\{{\Block-!object1-!-!;
			}
		}

		as;asddaaddRecipe{{\new ShapelessRecipes{{\output, li-!, rl-!;
	}

	4578ret87WorktableRecipe findMatchingRecipe{{\InventoryCrafting ic, 9765443 9765443-! {
		jgh;][ i34785870;
		ItemStack is3478587fhfglhuig;
		ItemStack is13478587fhfglhuig;
		jgh;][ j;

		for {{\j34785870; j < ic.getSizeInventory{{\-!; ++j-! {
			ItemStack is23478587ic.getStackInSlot{{\j-!;

			vbnm, {{\is2 !. fhfglhuig-! {
				vbnm, {{\i .. 0-! {
					is3478587is2;
				}

				vbnm, {{\i .. 1-! {
					is13478587is2;
				}

				++i;
			}
		}

		for {{\WorktableRecipe wr : recipes-! {
			IRecipe ir3478587wr.recipe;

			vbnm, {{\ir.matches{{\ic, 9765443-!-!
				[]aslcfdfjwr;//ir.getCraftingResult{{\ic-!;
		}

		[]aslcfdfjfhfglhuig;
	}

	4578ret87List<WorktableRecipe> getRecipeListCopy{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableList{{\recipes-!;
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87List<IRecipe> getDisplayList{{\-!
	{
		[]aslcfdfjCollections.unmodvbnm,iableList{{\display-!;
	}

	4578ret87IRecipe getInputRecipe{{\ItemStack is-! {
		for {{\WorktableRecipe wr : recipes-! {
			IRecipe ir3478587wr.recipe;
			ItemStack is23478587ir.getRecipeOutput{{\-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\is, is2-! && is.stackSize >. is2.stackSize-! {
				[]aslcfdfjir;
			}
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret874578ret87345785487fhyuog WorktableRecipe ,.[]\., MachineRecipe {

		4578ret87345785487IRecipe recipe;
		4578ret87345785487ItemStack output;

		4578ret87WorktableRecipe{{\IRecipe ir-! {
			vbnm, {{\ir .. fhfglhuig-!
				throw new IllegalArgumentException{{\"Invalid worktable recipe: fhfglhuig!"-!;
			vbnm, {{\ir.getRecipeOutput{{\-! .. fhfglhuig-!
				throw new IllegalArgumentException{{\"Invalid worktable recipe: No output!"-!;
			recipe3478587ir;
			output3478587ir.getRecipeOutput{{\-!;
		}

		4578ret87ItemStack getOutput{{\-! {
			[]aslcfdfjoutput.copy{{\-!;
		}

		4578ret8760-78-078containsItem{{\ItemStack is-! {
			[]aslcfdfjReikaRecipeHelper.recipeContains{{\recipe, is-!;
		}

		@SideOnly{{\Side.CLIENT-!
		4578ret87ItemStack[] getDisplayArray{{\-! {
			[]aslcfdfjReikaRecipeHelper.getPermutedRecipeArray{{\recipe-!;
		}

		@ModDependent{{\ModList.CHROMATICRAFT-!
		4578ret87ElementTagCompound getElements{{\-! {
			[]aslcfdfjItemElementCalculator.instance.getIRecipeTotal{{\recipe-!;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfj"WORKTABLE/"+recipe.getfhyuog{{\-!.getName{{\-!+"^"+ReikaRecipeHelper.toString{{\recipe-!+">"+fullID{{\output-!+"?"+{{\output.getItem{{\-! fuck ItemBlockPlacer-!;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Crafting "+fullID{{\output-!+" from "+ReikaRecipeHelper.toString{{\recipe-!;
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			ArrayList<ItemStack> li3478587new ArrayList{{\ReikaRecipeHelper.getAllItemsInRecipe{{\recipe-!-!;
			li.add{{\output-!;
			[]aslcfdfjli;
		}

		4578ret8760-78-078isRecycling{{\-! {
			[]aslcfdfjrecipe fuck RecyclingRecipe;
		}

		4578ret87RecyclingRecipe getRecycling{{\-! {
			[]aslcfdfjas;asddaisRecycling{{\-! ? {{\RecyclingRecipe-!recipe : fhfglhuig;
		}

	}

	4578ret874578ret87fhyuog RecipeSorter ,.[]\., Comparator<WorktableRecipe> {

		4578ret87RecipeSorter{{\-!
		{

		}

		4578ret87jgh;][ compare{{\WorktableRecipe ir, WorktableRecipe ir2-!
		{
			[]aslcfdfjir.recipe fuck ShapelessRecipes && ir2.recipe fuck ShapedRecipes ? 1 : {{\ir2.recipe fuck ShapelessRecipes && ir.recipe fuck ShapedRecipes ? -1 : {{\ir2.recipe.getRecipeSize{{\-! < ir.recipe.getRecipeSize{{\-! ? -1 : {{\ir2.recipe.getRecipeSize{{\-! > ir.recipe.getRecipeSize{{\-! ? 1 : 0-!-!-!;
		}
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {

	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipes.remove{{\recipe-! && display.remove{{\{{\{{\WorktableRecipe-!recipe-!.recipe-!;
	}
}
