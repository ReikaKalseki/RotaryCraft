/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;

import Reika.RotaryCraft.Base.GuiEngine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerPerformance;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;

public class GuiPerformance extends GuiEngine
{
	private TileEntityPerformanceEngine engine;

	public GuiPerformance(EntityPlayer p5ep, TileEntityPerformanceEngine te) {
		super(new ContainerPerformance(p5ep, te), te, p5ep);
		engine = te;
		xSize = 176;
		ySize = 166;
	}

	@Override
	protected int getFuelBarXPos() {
		return 81;
	}

	@Override
	protected int getFuelBarYPos() {
		return 16;
	}

	@Override
	protected int getFuelBarXSize() {
		return 7;
	}

	@Override
	protected int getFuelBarYSize() {
		return 55;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i2 = engine.getWaterScaled(54);
		int i3 = engine.getTempScaled(54);
		this.drawTexturedModalRect(j+41, k+71-i2, 193, 55-i2, 5, i2);
		this.drawTexturedModalRect(j+128, k+71-i3, 177, 99-i3, 9, i3);

		int i1 = engine.getFuelScaled(54);
		this.drawTexturedModalRect(j+82, k+71-i1, 200, 55-i1, 6, i1);
		int i4 = engine.getAdditivesScaled(54);
		this.drawTexturedModalRect(j+89, k+71-i4, 207, 55-i4, 6, i4);
	}

	@Override
	protected String getGuiTexture() {
		return "perfgui";
	}
}
