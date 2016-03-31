package Reika.RotaryCraft.Auxiliary;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;


public class RecyclingRecipe implements IRecipe {

	private final ItemStack input;
	public final int scrapValue;

	public RecyclingRecipe(ItemStack is, int value) {
		input = is;
		scrapValue = value;
	}

	@Override
	public boolean matches(InventoryCrafting ics, World world) {
		int num = 0;
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack is = ics.getStackInSlot(i);
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, input))
					num++;
				else
					return false;
			}
		}
		return num == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ics) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, scrapValue);//ItemStacks.getScrap(scrapValue);
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStacks.scrap;
	}

}
