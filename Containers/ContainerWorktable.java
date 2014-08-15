/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.Event.WorktableCraftEvent;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

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
	public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer ep) {
		ItemStack is = super.slotClick(slot, par2, par3, ep);
		this.updateCraftMatrix();
		this.onCraftMatrixChanged(craftMatrix);
		InventoryPlayer ip = ep.inventory;
		//ReikaJavaLibrary.pConsole(ip.getItemStack());
		if (tile.craftable && slot == 13) {
			ItemStack drop = ip.getItemStack();
			ItemStack craft = WorktableRecipes.getInstance().findMatchingRecipe(craftMatrix, world);
			if (drop != null && (!ReikaItemHelper.matchStacks(drop, craft) || drop.stackSize+craft.stackSize > drop.getMaxStackSize()))
				return is;
			this.craft();
			craft.onCrafting(world, ep, craft.stackSize);
			if (drop == null)
				ip.setItemStack(tile.getStackInSlot(13));
			else
				drop.stackSize += tile.getStackInSlot(13).stackSize;
			tile.setInventorySlotContents(13, null);
		}
		return is;
	}

	private void craft() {
		ItemStack is = WorktableRecipes.getInstance().findMatchingRecipe(craftMatrix, world);
		ItemStack slot13 = tile.getStackInSlot(13);
		if (slot13 != null)
			tile.setInventorySlotContents(13, ReikaItemHelper.getSizedItemStack(slot13, slot13.stackSize+is.stackSize));
		else
			tile.setInventorySlotContents(13, is);
		for (int i = 0; i < 9; ++i) {
			ItemStack item = tile.getStackInSlot(i);
			if (item != null) {
				//noUpdate = true;
				if (item.stackSize == 1)
					tile.setInventorySlotContents(i, null);
				else
					tile.getStackInSlot(i).stackSize--;
			}
		}
		SoundRegistry.CRAFT.playSoundAtBlock(world, tile.xCoord, tile.yCoord, tile.zCoord, 0.3F, 1.5F);
		MinecraftForge.EVENT_BUS.post(new WorktableCraftEvent(tile, ep.getCommandSenderName(), false, is));
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
		ItemStack is = WorktableRecipes.getInstance().findMatchingRecipe(craftMatrix, world);
		if (is == null) {
			tile.craftable = false;
			tile.setToCraft(null);
			return;
		}
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