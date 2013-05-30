/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.entity.player.EntityPlayer;

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerPerformance;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class GuiPerformance extends GuiNonPoweredMachine
{
	private TileEntityEngine Engine;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private EntityPlayer player;
	int x;
	int y;

	public GuiPerformance(EntityPlayer player, TileEntityEngine tile)
	{
		super(new ContainerPerformance(player, tile), tile);
		Engine = tile;
		xSize = 176;
		ySize = 166;
		this.player = player;
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

		int i2 = Engine.getLiquidScaled(54);
		int i3 = Engine.getTempScaled(54);
		this.drawTexturedModalRect(j+41, k+71-i2, 193, 55-i2, 5, i2);
		this.drawTexturedModalRect(j+128, k+71-i3, 177, 99-i3, 9, i3);

		int i1 = Engine.getEthanolScaled(54);
		this.drawTexturedModalRect(j+82, k+71-i1, 200, 55-i1, 6, i1);
		int i4 = Engine.getAdditivesScaled(54);
		this.drawTexturedModalRect(j+89, k+71-i4, 207, 55-i4, 6, i4);
	}

	@Override
	public String getGuiTexture() {
		return "perfgui";
	}
}
