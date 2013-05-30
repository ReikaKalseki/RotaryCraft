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
import Reika.RotaryCraft.Containers.ContainerJet;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class GuiJet extends GuiNonPoweredMachine
{
	private TileEntityEngine eng;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private EntityPlayer player;
	int x;
	int y;

	public GuiJet(EntityPlayer player, TileEntityEngine tile)
	{
		super(new ContainerJet(player, tile), tile);
		eng = tile;
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

		int i2 = eng.getJetFuelScaled(54);
		this.drawTexturedModalRect(j+85, k+71-i2, 207, 55-i2, 5, i2);
	}

	@Override
	public String getGuiTexture() {
		return "jetgui";
	}
}
