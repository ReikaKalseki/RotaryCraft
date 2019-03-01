/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Minetweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;

public class MinetweakerHelper {

	public static ItemStack toStack(IItemStack iStack) {
		return MineTweakerMC.getItemStack(iStack);
	}

	public static ItemStack getStack(IIngredient ingredient) {
		if (ingredient == null)
			return null;
		if (ingredient instanceof IOreDictEntry) {
			return OreDictionary.getOres(toString((IOreDictEntry)ingredient)).get(0);
		}
		else if (ingredient instanceof IItemStack) {
			return MineTweakerMC.getItemStack((IItemStack)ingredient);
		}
		else
			return null;
	}

	public static List<ItemStack> getStacks(IIngredient ingredient)
	{
		if (ingredient == null)
			return null;
		if (ingredient instanceof IOreDictEntry) {
			return OreDictionary.getOres(toString((IOreDictEntry)ingredient));
		}
		else if (ingredient instanceof IItemStack) {
			ArrayList<ItemStack> result = new ArrayList<ItemStack>();
			result.add(MineTweakerMC.getItemStack((IItemStack)ingredient));
			return result;
		}
		return null;
	}

	public static String toString(IOreDictEntry entry) {
		return entry.getName();
	}
}
