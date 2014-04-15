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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerBigFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityBigFurnace;

public class GuiBigFurnace extends GuiPowerOnlyMachine
{
	private TileEntityBigFurnace te;

	public GuiBigFurnace(EntityPlayer p5ep, TileEntityBigFurnace BigFurnace)
	{
		super(new ContainerBigFurnace(p5ep, BigFurnace), BigFurnace);
		te = BigFurnace;
		ep = p5ep;
		xSize = 190;
		ySize = 207;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		int c = 0;
		if (te.getTemperature() >= 1000)
			c = 4;
		else if (te.getTemperature() >= 100)
			c = 2;
		api.drawCenteredStringNoShadow(fontRenderer, String.valueOf(te.getTemperature())+"C", xSize-13-c, 6, 4210752);

		if (!te.isEmpty()) {
			int i2 = te.getLavaScaled(91);
			int x = 173;
			int y = 108-i2+1;
			GL11.glColor4f(1, 1, 1, 1);
			Icon ico = FluidRegistry.LAVA.getStillIcon();
			ReikaLiquidRenderer.bindFluidTexture(FluidRegistry.LAVA);
			this.drawTexturedModelRectFromIcon(x, y, ico, 10, i2);
		}
		if (api.isMouseInBox(j+172, j+183, k+17, k+109)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRenderer, String.format("%d/%d", te.getLevel(), te.getCapacity()), mx-j, my-k);
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

		int i1 = te.getCookScaled(17);
		this.drawTexturedModalRect(j+7, k+55, 0, 208, 162, i1);
	}

	@Override
	public String getGuiTexture() {
		return "bigfurngui";
	}
}
