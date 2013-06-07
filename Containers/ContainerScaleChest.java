/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;

public class ContainerScaleChest extends CoreContainer
{
	private IInventory lowerScaleChestInventory;
	private TileEntityScaleableChest chest;
	private int size;

	public ContainerScaleChest(EntityPlayer player, TileEntityScaleableChest par2IInventory)
	{
		super(player, par2IInventory);
		lowerScaleChestInventory = par2IInventory;
		chest = par2IInventory;
		size = par2IInventory.getSizeInventory();
		par2IInventory.openChest();
		this.setSlots(player, par2IInventory);
	}

	public void reset(EntityPlayer player, TileEntityScaleableChest te) {
		//inventorySlots.clear();
		//this.func_94533_d();
		//this.setSlots(par1IInventory, te);
	}

	private void setSlots(EntityPlayer player, TileEntityScaleableChest te) {
		int var3 = 0;
		int var4 = 8;
		int var5 = 18;
		int page = chest.page;
		int rowsize = size;
		if (rowsize > chest.MAXROWS*9)
			rowsize = chest.MAXROWS*9;
		if (page == chest.getMaxPage()) {
			rowsize = size-(page*9*chest.MAXROWS);
		}
		int rows = (int)Math.ceil(rowsize/9D);
		int x = 0; int y = 0;
		//ReikaJavaLibrary.pConsole(size);
		//ReikaJavaLibrary.pConsole(rowsize);
		//ReikaJavaLibrary.pConsole(page);

		for (var3 = 0; var3 < rowsize; var3++) {
			y = var5+var3/9*18; x = var4+18*(var3%9);
			//ReikaJavaLibrary.pConsole(var3+"  ->   "+x+", "+y);
			this.addSlotToContainer(new Slot(te, var3+page*9*chest.MAXROWS, x, y));
		}
		var5 = chest.MAXROWS*18+31;//rows*18+31;
		//var4 = 44+18*(rows-1);
		int k;
		for (k = 0; k < 3; ++k) {
			for (int m = 0; m < 9; ++m) {
				var3 = m+9*k;
				y = var5+k*18; x = var4+18*m;
				this.addSlotToContainer(new Slot(player.inventory, m + k * 9 + 9, x, y));
			}
		}
		var5 += 58;
		for (k = 0; k < 9; ++k) {
			y = var5; x = var4+18*k;
			this.addSlotToContainer(new Slot(player.inventory, k, x, y));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		if (player != null)
			return true;
		boolean b = chest.isUseableByPlayer(player);
		if (!b) {
			chest.closeChest();
			chest.lidAngle = 1;
		}
		return b;
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);
		lowerScaleChestInventory.closeChest();
	}

	public IInventory getLowerScaleChestInventory()
	{
		return lowerScaleChestInventory;
	}
}
