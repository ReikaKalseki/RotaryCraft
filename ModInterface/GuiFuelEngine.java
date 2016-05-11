/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;

public class GuiFuelEngine extends GuiNonPoweredMachine
{
	private final TileEntityFuelEngine eng;

	public GuiFuelEngine(EntityPlayer p5ep, TileEntityFuelEngine te)
	{
		super(new ContainerFuelEngine(p5ep, te), te);
		eng = te;
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
			int time = eng.getFuelDuration();
			String sg = String.format("Fuel: %s", ReikaFormatHelper.getSecondsAsClock(time));
			api.drawTooltipAt(fontRendererObj, sg, x-j, y-k);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i2 = eng.getFuelScaled(54);
		int i3 = eng.getWaterScaled(54);
		int i4 = eng.getLubricantScaled(54);
		int i5 = eng.getTemperatureScaled(54);

		this.drawTexturedModalRect(j+85, k+71-i2, 207, 55-i2, 5, i2);
		this.drawTexturedModalRect(j+31, k+71-i3, 214, 55-i3, 5, i3);
		this.drawTexturedModalRect(j+58, k+71-i4, 221, 55-i4, 5, i4);

		this.drawTexturedModalRect(j+138, k+71-i5, 177, 99-i5, 9, i5);
	}

	@Override
	protected String getGuiTexture() {
		return "fuelenggui";
	}
}
