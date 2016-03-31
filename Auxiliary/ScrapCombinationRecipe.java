package Reika.RotaryCraft.Auxiliary;



public class ScrapCombinationRecipe/* implements IRecipe*/ {
	/*
	@Override
	public boolean matches(InventoryCrafting ics, World world) {
		int scrap = 0;
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack is = ics.getStackInSlot(i);
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, ItemStacks.scrap))
					scrap++;
				else
					return false;
			}
		}
		return scrap >= 2;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ics) {
		int scrap = 0;
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack is = ics.getStackInSlot(i);
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, ItemStacks.scrap))
					scrap += ItemStacks.getScrapValue(is);
			}
		}
		return ItemStacks.getScrap(scrap);
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStacks.scrap;
	}
	 */
}
