/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerEthanol;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class GuiEthanol extends GuiNonPoweredMachine
{
	private TileEntityEngine Ethanol;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;

	public GuiEthanol(EntityPlayer p5ep, TileEntityEngine te)
	{
		super(new ContainerEthanol(p5ep, te), te);
		Ethanol = te;
		xSize = 176;
		ySize = 166;
		ep = p5ep;
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
		int i1 = Ethanol.getEthanolScaled(54);
		this.drawTexturedModalRect(j+85, k+71-i1, 200, 55-i1, 5, i1);

	}

	@Override
	public String getGuiTexture() {
		return "ethanolgui";
	}
}
