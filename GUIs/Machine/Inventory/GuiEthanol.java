/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerEthanol;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityGasEngine;

public class GuiEthanol extends GuiNonPoweredMachine
{
	private TileEntityGasEngine engine;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;

	public GuiEthanol(EntityPlayer p5ep, TileEntityGasEngine te)
	{
		super(new ContainerEthanol(p5ep, te), te);
		engine = te;
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
		if (api.isMouseInBox(j+84, j+90, k+16, k+71)) {
			int time = engine.getFuelDuration();
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
		int i1 = engine.getFuelScaled(54);
		this.drawTexturedModalRect(j+85, k+71-i1, 200, 55-i1, 5, i1);

	}

	@Override
	protected String getGuiTexture() {
		return "ethanolgui";
	}
}
