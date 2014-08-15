/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerSorter;
import Reika.RotaryCraft.TileEntities.TileEntitySorting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GuiSorter extends GuiPowerOnlyMachine {

	private TileEntitySorting tile;

	public GuiSorter(EntityPlayer player, TileEntitySorting te) {
		super(new ContainerSorter(player, te), te);
		tile = te;
		xSize = 176;
		ySize = 180;
		ep = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		int dy = 22;
		int x = 8;
		int y = 18;
		int l = tile.LENGTH;
		for (int k = 0; k < l*3; k++) {
			ItemStack is = tile.getMapping(k);
			if (is != null) {
				api.drawItemStack(itemRender, fontRendererObj, is, x+k%l*18, y+k/l*dy);
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "sortergui";
	}

}