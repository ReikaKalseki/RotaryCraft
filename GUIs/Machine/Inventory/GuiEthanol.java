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
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerEthanol;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityGasEngine;

public class GuiEthanol extends GuiEngine
{
	private TileEntityGasEngine engine;

	public GuiEthanol(EntityPlayer p5ep, TileEntityGasEngine te)
	{
		super(new ContainerEthanol(p5ep, te), te, p5ep);
		engine = te;
		xSize = 176;
		ySize = 166;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int i1 = engine.getFuelScaled(54);
		this.drawTexturedModalRect(j+85, k+71-i1, 200, 55-i1, 5, i1);

	}

	@Override
	protected String getGuiTexture() {
		return "ethanolgui";
	}

	@Override
	protected int getFuelBarXPos() {
		return 84;
	}

	@Override
	protected int getFuelBarYPos() {
		return 16;
	}

	@Override
	protected int getFuelBarXSize() {
		return 6;
	}

	@Override
	protected int getFuelBarYSize() {
		return 55;
	}
}
