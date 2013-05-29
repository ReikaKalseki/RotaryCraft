/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;

public class ItemChargingRecipeHandler implements IRecipe {

	private ItemStack[] slots;//Use a list!
	List<ItemStack> craftItems = new ArrayList<ItemStack>();
	private int springdmg;
	private int tooldmg;

	@Override
	public boolean matches(InventoryCrafting ic, World world) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {
		slots = ReikaInventoryHelper.convertCraftToItemStacks(ic);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRecipeSize() {
		//this.slots = ReikaInventoryHelper.convertCraftToItemStacks(ic);
		return slots.length;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	private boolean checkHasTool(InventoryCrafting ic) {
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack is = ic.getStackInSlot(i);
			if (is != null) {
				if (is.getItem() instanceof ItemChargedTool)
					return true;
			}
		}
		return false;
	}
}
