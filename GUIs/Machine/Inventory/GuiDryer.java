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
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerDryingBed;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDryingBed;

public class GuiDryer extends GuiNonPoweredMachine
{
	private TileEntityDryingBed te;

	public GuiDryer(EntityPlayer p5ep, TileEntityDryingBed DryingBed)
	{
		super(new ContainerDryingBed(p5ep, DryingBed), DryingBed);
		te = DryingBed;
		ep = p5ep;
		xSize = 176;
		ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (!te.isEmpty()) {
			int i2 = te.getLiquidScaled(72);
			int x = 8;
			int y = 78-i2+1;
			GL11.glColor4f(1, 1, 1, 1);
			Fluid f = te.getContainedFluid();
			IIcon ico = f.getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
			this.drawTexturedModelRectFromIcon(x, y, ico, 16, i2);
		}
		if (api.isMouseInBox(j+7, j+24, k+6, k+79)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRendererObj, String.format("%d/%d", te.getLevel(), te.getCapacity()), mx-j, my-k);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i1 = te.getProgressScaled(91);
		this.drawTexturedModalRect(j+29, k+41, 1, 169, i1, 4);
	}

	@Override
	protected String getGuiTexture() {
		return "drygui";
	}
}
