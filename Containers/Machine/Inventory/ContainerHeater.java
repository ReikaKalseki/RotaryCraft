/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;

public class ContainerHeater extends ContainerIOMachine {

	IInventory lowerInv;

	public ContainerHeater(EntityPlayer player, TileEntityHeater te) {
		super(player, te);
		lowerInv = (IInventory)te;
		int numRows = lowerInv.getSizeInventory() / 9;
		lowerInv.openInventory();
		int var3 = (numRows - 4) * 18;
		int var4;
		int var5;
		int py = 18;
		for (var4 = 0; var4 < numRows; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlotToContainer(new Slot(lowerInv, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
			}
		}
		for (var4 = 0; var4 < 3; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlotToContainer(new Slot(player.inventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3+py));
			}
		}

		for (var4 = 0; var4 < 9; ++var4)
		{
			this.addSlotToContainer(new Slot(player.inventory, var4, 8 + var4 * 18, 161 + var3+py));
		}
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public final void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		lowerInv.closeInventory();
	}

	public final IInventory getLowerInventory()
	{
		return lowerInv;
	}

}
