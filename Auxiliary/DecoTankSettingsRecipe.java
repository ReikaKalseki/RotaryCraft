/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank.TankFlags;

public class DecoTankSettingsRecipe implements IRecipe {

	private final ItemStack basicItem = BlockRegistry.DECOTANK.getStackOf();

	public DecoTankSettingsRecipe() {

	}

	public static final String NBT_TAG = "decotank_settings";

	@Override
	public boolean matches(InventoryCrafting ics, World world) {
		int tnk = 0;
		int any = 0;
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack in = ics.getStackInSlot(i);
			if (in != null) {
				any++;
				if (ReikaItemHelper.matchStacks(in, basicItem)) {
					tnk++;
				}
				else {
					boolean recognized = false;
					for (int k = 0; k < TankFlags.list.length; k++) {
						TankFlags f = TankFlags.list[k];
						if (f.isItem(in)) {
							recognized = true;
						}
					}
					if (!recognized)
						return false;
				}
			}
		}
		return tnk == 1 && any > 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ics) {
		if (!this.matches(ics, null))
			return null;
		ItemStack tank = this.getTank(ics);
		int meta = tank.getItemDamage();
		for (int k = 0; k < TankFlags.list.length; k++) {
			TankFlags f = TankFlags.list[k];
			if (f.toggle(ics)) {
				meta = meta ^ f.getItemMetadataBit();
			}
		}
		ItemStack is = basicItem.copy();
		is.setItemDamage(meta);
		is.stackTagCompound = tank.stackTagCompound != null ? (NBTTagCompound)tank.stackTagCompound.copy() : new NBTTagCompound(); //Keep fluids
		//is.stackTagCompound.setBoolean(NBT_TAG, true);
		return is;
	}

	@Override
	public int getRecipeSize() {
		return 5;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return basicItem.copy();
	}

	private ItemStack getTank(InventoryCrafting ics) {
		for (int i = 0; i < ics.getSizeInventory(); i++) {
			ItemStack in = ics.getStackInSlot(i);
			if (ReikaItemHelper.matchStacks(in, basicItem))
				return in;
		}
		return null;
	}

}
