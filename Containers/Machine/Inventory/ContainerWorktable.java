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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.DragonAPI.Auxiliary.Trackers.KeyWatcher;
import Reika.DragonAPI.Auxiliary.Trackers.KeyWatcher.Key;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.Slot.SlotApprovedItems;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;

public class ContainerWorktable extends CoreContainer {

	private World world;
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private TileEntityWorktable tile;
	private boolean noUpdate;

	public ContainerWorktable(EntityPlayer player, TileEntityWorktable te, World worldObj) {
		super(player, te);
		world = worldObj;
		int dx = 0;
		tile = te;

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
		this.onCraftMatrixChanged(craftMatrix);
	}

	private void updateCraftMatrix() {
		for (int i = 0; i < 9; i++) {
			ItemStack stack = tile.getStackInSlot(i);
			noUpdate = true;
			craftMatrix.setInventorySlotContents(i, stack);
		}
	}

	@Override
	public ItemStack slotClick(int slot, int par2, int action, EntityPlayer ep) {
		/*
		if (slot >= 18 && slot < tile.getSizeInventory()) {
			ItemStack held = ep.inventory.getItemStack();
			tile.setMapping(slot, ReikaItemHelper.getSizedItemStack(held, 1));
			return held;
		}
		 */

		//if (action == 4 && slot >= 18 && slot < tile.getSizeInventory())
		//	action = 0;

		ItemStack is = super.slotClick(slot, par2, action, ep);
		this.updateCraftMatrix();
		this.onCraftMatrixChanged(craftMatrix);
		InventoryPlayer ip = ep.inventory;
		//ReikaJavaLibrary.pConsole(ip.getItemStack());
		if (tile.craftable && tile.isReadyToCraft() && slot == 13) {
			ItemStack drop = ip.getItemStack();
			WorktableRecipe wr = WorktableRecipes.getInstance().findMatchingRecipe(craftMatrix, world);
			ItemStack craft = wr.isRecycling() ? wr.getRecycling().getRecipeOutput() : wr.getOutput();
			if (drop != null && (!ReikaItemHelper.matchStacks(drop, craft) || drop.stackSize+craft.stackSize > drop.getMaxStackSize()))
				return is;
			this.craft(wr, ep);
			craft.onCrafting(world, ep, craft.stackSize);
			if (drop == null)
				ip.setItemStack(tile.getStackInSlot(13));
			else
				drop.stackSize += tile.getStackInSlot(13).stackSize;
			tile.setInventorySlotContents(13, null);
		}
		return is;
	}

	private void craft(WorktableRecipe wr, EntityPlayer ep) {
		tile.handleCrafting(wr, ep, KeyWatcher.instance.isKeyDown(ep, Key.LSHIFT));
		this.updateCraftMatrix();
		tile.craftable = false;
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		if (noUpdate) {
			noUpdate = false;
			return;
		}
		WorktableRecipe wr = WorktableRecipes.getInstance().findMatchingRecipe(craftMatrix, world);
		if (wr == null) {
			tile.craftable = false;
			tile.setToCraft(null);
			return;
		}
		ItemStack is = wr.isRecycling() ? wr.getRecycling().getRecipeOutput() : wr.getOutput();
		ItemStack slot13 = tile.getStackInSlot(13);
		if (slot13 != null) {
			if (is.getItem() != slot13.getItem())
				return;
			if (is.getItemDamage() != slot13.getItemDamage())
				return;
			if (slot13.stackSize >= slot13.getMaxStackSize())
				return;
		}
		tile.craftable = true;
		tile.setToCraft(is);
	}
}
