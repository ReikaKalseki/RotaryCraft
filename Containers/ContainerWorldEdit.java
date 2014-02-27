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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.WorldEditHelper;

public class ContainerWorldEdit extends Container
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
	private World worldObj;

	public ContainerWorldEdit(EntityPlayer player, World par2World)
	{
		worldObj = par2World;
		int var6;
		int var7;

		this.addSlotToContainer(new Slot(craftMatrix, 0, 80, 35));

		for (var6 = 0; var6 < 3; ++var6)
			for (var7 = 0; var7 < 9; ++var7)
				this.addSlotToContainer(new Slot(player.inventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
		for (var6 = 0; var6 < 9; ++var6)
			this.addSlotToContainer(new Slot(player.inventory, var6, 8 + var6 * 18, 142));
		this.onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		ItemStack var3 = craftMatrix.getStackInSlotOnClosing(0);
		if (var3 != null) {
			if (!ReikaInventoryHelper.addToIInv(var3, par1EntityPlayer.inventory)) {
				if (!worldObj.isRemote)
					par1EntityPlayer.dropPlayerItem(var3);
			}
			if (FluidContainerRegistry.isFilledContainer(var3)) {
				Fluid liq = FluidContainerRegistry.getFluidForFilledItem(var3).getFluid();
				if (liq.canBePlacedInWorld())
					WorldEditHelper.addCommand(par1EntityPlayer, liq.getBlockID(), 0);
				return;
			}
			if (!(var3.getItem() instanceof ItemBlock))
				return;
			WorldEditHelper.addCommand(par1EntityPlayer, var3.itemID, var3.getItemDamage());
		}
		else {
			WorldEditHelper.addCommand(par1EntityPlayer, 0, 0);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return this.getSlot(0).getStack();
	}
}
