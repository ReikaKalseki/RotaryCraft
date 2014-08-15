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

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerCrystallizer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCrystallizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

public class GuiCrystallizer extends GuiMachine
{
	private TileEntityCrystallizer te;

	public GuiCrystallizer(EntityPlayer p5ep, TileEntityCrystallizer Crystallizer)
	{
		super(new ContainerCrystallizer(p5ep, Crystallizer), Crystallizer);
		te = Crystallizer;
		ep = p5ep;
		xSize = 176;
		ySize = 166;
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

		if (!te.isEmpty()) {
			int i2 = te.getLiquidScaled(72);
			int x = 8;
			int y = 78-i2+1;
			GL11.glColor4f(1, 1, 1, 1);
			Fluid f = te.getContainedFluid();
			IIcon ico = f.getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
			this.drawTexturedModelRectFromIcon(x, y, ico, 16, i2);

			String s = String.format("%d C", te.getFreezingPoint());
			api.drawCenteredStringNoShadow(fontRendererObj, s, xSize/2, 56, 0);
		}
		if (api.isMouseInBox(j+7, j+24, k+6, k+79)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRendererObj, String.format("%d/%d", te.getLevel(), te.getCapacity()), mx-j, my-k);
		}
		String s = String.format("%d C", te.getTemperature());
		api.drawCenteredStringNoShadow(fontRendererObj, s, 50, 30, 0);
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

		int i1 = te.getProgressScaled(44);
		this.drawTexturedModalRect(j+29, k+41, 178, 1, i1, 4);
	}

	@Override
	public String getGuiTexture() {
		return "crystalgui";
	}

	@Override
	protected void drawPowerTab(int j, int k) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+j, k+4, 0, 4, 42, ySize-4);

		long frac = (te.power*29L)/te.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+j+5, ySize+k-144, 0, 0, (int)frac, 4);

		frac = (int)(te.omega*29L)/te.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+j+5, ySize+k-84, 0, 0, (int)frac, 4);

		frac = (int)(te.torque*29L)/te.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+j+5, ySize+k-24, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRendererObj, "Power:", xSize+j+20, k+9, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Speed:", xSize+j+20, k+69, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Torque:", xSize+j+20, k+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRendererObj, String.format("%d/%d", te.power, te.MINPOWER), xSize+j+16, k+16, 0xff000000);
	}
}