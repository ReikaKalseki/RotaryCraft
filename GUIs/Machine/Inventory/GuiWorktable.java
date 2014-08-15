/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiWorktable extends GuiNonPoweredMachine {

	TileEntityWorktable table;

	public GuiWorktable(EntityPlayer pl, TileEntityWorktable te, World world) {
		super(new ContainerWorktable(pl, te, world), te);
		ep = pl;
		table = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		if (!table.craftable)
			return;
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		this.drawTexturedModalRect(j+79, k+35, 176, 35, 18, 15);
		api.drawItemStackWithTooltip(itemRender, fontRendererObj, table.getToCraft(), j+116, k+35);
	}

	@Override
	public String getGuiTexture() {
		return "worktablegui";
	}
}