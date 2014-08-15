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

import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerPerformance;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;

import net.minecraft.entity.player.EntityPlayer;

public class GuiPerformance extends GuiNonPoweredMachine
{
	private TileEntityPerformanceEngine Engine;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;

	public GuiPerformance(EntityPlayer p5ep, TileEntityPerformanceEngine te)
	{
		super(new ContainerPerformance(p5ep, te), te);
		Engine = te;
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
		if (api.isMouseInBox(j+81, j+88, k+16, k+71)) {
			int time = Engine.getFuelDuration();
			String sg = String.format("Fuel: %s", ReikaFormatHelper.getSecondsAsClock(time));
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

		int i2 = Engine.getWaterScaled(54);
		int i3 = Engine.getTempScaled(54);
		this.drawTexturedModalRect(j+41, k+71-i2, 193, 55-i2, 5, i2);
		this.drawTexturedModalRect(j+128, k+71-i3, 177, 99-i3, 9, i3);

		int i1 = Engine.getFuelScaled(54);
		this.drawTexturedModalRect(j+82, k+71-i1, 200, 55-i1, 6, i1);
		int i4 = Engine.getAdditivesScaled(54);
		this.drawTexturedModalRect(j+89, k+71-i4, 207, 55-i4, 6, i4);
	}

	@Override
	public String getGuiTexture() {
		return "perfgui";
	}
}