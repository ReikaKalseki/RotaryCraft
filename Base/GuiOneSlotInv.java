/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

public class GuiOneSlotInv extends GuiMachine {

	protected IInventory iinv;
	public GuiOneSlotInv(EntityPlayer pl, Container par1Container, RotaryCraftTileEntity te) {
		super(par1Container, te);
		iinv = (IInventory)te;
		xSize = 176;
		ySize = 166;
		ep = pl;
	}

	@Override
	protected final void drawPowerTab(int j, int k) {}

	@Override
	public String getGuiTexture() {
		return "basic_gui_oneslot";
	}

}
