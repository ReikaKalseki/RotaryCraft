package RotaryCraft.ModInterface.Minetweaker;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

import java.util.ArrayList;
import java.util.List;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MinetweakerHelper {
    
	public static ItemStack toStack(IItemStack iStack) {
        return getItemStack(iStack);
    }
	
	public static ItemStack getStack(IIngredient ingredient) {
		if (ingredient == null) return null;
        if (ingredient instanceof IOreDictEntry) {
            return OreDictionary.getOres(toString((IOreDictEntry) ingredient)).get(0);
        } else if (ingredient instanceof IItemStack) {
            return getItemStack((IItemStack) ingredient);
        }
	}

    public static List<ItemStack> getStacks(IIngredient ingredient)
    {
        if (ingredient == null) return null;
        if (ingredient instanceof IOreDictEntry) {
            return OreDictionary.getOres(toString((IOreDictEntry) ingredient));
        } else if (ingredient instanceof IItemStack) {
            ArrayList<ItemStack> result = new ArrayList<ItemStack>();
            result.add(getItemStack((IItemStack)ingredient));
			return result;
        }
        return null;
    }

    public static String toString(IOreDictEntry entry) {
        return ((IOreDictEntry) entry).getName();
    }
}