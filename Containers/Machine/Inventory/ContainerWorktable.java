/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.GUI.Slot.SlotApprovedItems;
import Reika.DragonAPI.Interfaces.CraftingContainer;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;

public class ContainerWorktable extends CraftingContainer<WorktableRecipe> {

	private TileEntityWorktable table;

	public ContainerWorktable(EntityPlayer player, TileEntityWorktable te, World world, boolean gui) {
		super(player, te, world, gui);
		int dx = 0;
		table = te;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(te, i*3+j, dx+26+j*18, 17+i*18));
			}
		}
		dx += 96-28+4;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotFurnace(player, te, 9+i*3+j, dx+26+j*18, 17+i*18));
			}
		}

		this.addSlotToContainer(new SlotApprovedItems(te, 18, 6, 53).addItem(ItemRegistry.CRAFTPATTERN.getItemInstance()));

		/*
		dx = 153;
		int dy = 84;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				//this.addSlotToContainer(new GhostSlot(te, 18+i*3+j, dx+26+j*18, dy+i*18));
				this.addSlotToContainer(new SlotXItems(te, 18+i*3+j, dx+26+j*18, dy+i*18, 1));
			}
		}
		 */

		this.updateCraftMatrix();

		this.addPlayerInventory(player);
		this.onCraftMatrixChanged();
	}

	@Override
	protected WorktableRecipe getRecipe(InventoryCrafting craftMatrix, World world) {
		return WorktableRecipes.getInstance().findMatchingRecipe(craftMatrix, world);
	}

	@Override
	protected ItemStack getOutput(WorktableRecipe wr) {
		return wr.isRecycling() ? wr.getRecycling().getRecipeOutput() : wr.getOutput();
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
}
