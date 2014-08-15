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

import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerDefoliator;
import Reika.RotaryCraft.TileEntities.World.TileEntityDefoliator;

import net.minecraft.entity.player.EntityPlayer;

public class GuiDefoliator extends GuiPowerOnlyMachine
{
	private TileEntityDefoliator tile;
	int x;
	int y;

	public GuiDefoliator(EntityPlayer p5ep, TileEntityDefoliator te)
	{
		super(new ContainerDefoliator(p5ep, te), te);
		tile = te;
		xSize = 176;
		ySize = 166;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerForegroundLayer(a, b);
		int x = api.getMouseRealX();
		int y = api.getMouseRealY();
		if (api.isMouseInBox(j+133, j+150, k+16, k+69)) {
			int lvl = tile.getLevel();
			String sg = String.format("Poison: %d/%d", lvl, tile.CAPACITY);
			api.drawTooltipAt(fontRendererObj, sg, x-j, y-k);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int i1 = tile.getPoisonScaled(52);
		this.drawTexturedModalRect(j+134, k+69-i1, 177, 69-i1, 16, i1);

	}

	@Override
	public String getGuiTexture() {
		return "defoliatorgui";
	}
}