/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.List;

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class ChargingRecipe implements IRecipe {
	
	private ItemStack output;

	public ChargingRecipe(ItemStack par1ItemStack) {
		this.output = par1ItemStack;
	}

	@Override
	public boolean matches(InventoryCrafting ic, World world) {
		if (ReikaInventoryHelper.checkForItem(ic, output.itemID, -1) && ReikaInventoryHelper.checkForItem(ic, RotaryCraft.wind.itemID, -1))
			return true;
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {
		return output;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

}
