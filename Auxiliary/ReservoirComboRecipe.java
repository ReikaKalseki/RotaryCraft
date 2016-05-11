/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

public class ReservoirComboRecipe implements IRecipe {

	private final ItemStack basicItem = MachineRegistry.RESERVOIR.getCraftedProduct();

	public ReservoirComboRecipe() {

	}

	public static final String NBT_TAG = "reservoir_combine";

	@Override
	public boolean matches(InventoryCrafting ics, World world) {
		int res = 0;
		int any = 0;
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack in = ics.getStackInSlot(i);
			if (in != null) {
				any++;
				if (any > 2)
					return false;
				if (ReikaItemHelper.matchStacks(in, basicItem)) {
					res++;
				}
			}
		}
		if (any == res && res == 2) {
			ItemStack[] in = this.getReservoirs(ics);
			TileEntityReservoir te1 = new TileEntityReservoir();
			TileEntityReservoir te2 = new TileEntityReservoir();
			te1.setDataFromItemStackTag(in[0].stackTagCompound);
			te2.setDataFromItemStackTag(in[1].stackTagCompound);
			if (te1.getFluid() == te2.getFluid() && te1.getFluid() != null && te1.getLevel()+te2.getLevel() <= te1.CAPACITY)
				return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ics) {
		if (!this.matches(ics, null))
			return null;
		ItemStack[] in = this.getReservoirs(ics);
		ItemStack is = basicItem.copy();
		TileEntityReservoir te = new TileEntityReservoir();
		te.setDataFromItemStackTag(in[0].stackTagCompound);
		te.combineDataFromItemStackTag(in[1].stackTagCompound);
		is.stackTagCompound = te.getTagsToWriteToStack();
		is.stackTagCompound.setBoolean(NBT_TAG, true);
		return is;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return basicItem.copy();
	}

	private ItemStack[] getReservoirs(InventoryCrafting ics) {
		ItemStack[] dat = new ItemStack[2];
		int idx = 0;
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack in = ics.getStackInSlot(i);
			if (in != null) {
				dat[idx] = in;
				idx++;
			}
		}
		return dat;
	}

}
