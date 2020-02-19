/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
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
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMatchFilter extends Container
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
	private World worldObj;

	public ContainerMatchFilter(EntityPlayer player, World par2World)
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
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return this.getSlot(0).getStack();
	}
}
