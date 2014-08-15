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
import Reika.RotaryCraft.Containers.ContainerFridge;
import Reika.RotaryCraft.TileEntities.Production.TileEntityRefrigerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

public class GuiFridge extends GuiMachine
{
	private TileEntityRefrigerator te;

	public GuiFridge(EntityPlayer p5ep, TileEntityRefrigerator Fridge)
	{
		super(new ContainerFridge(p5ep, Fridge), Fridge);
		te = Fridge;
		ep = p5ep;
		ySize = 188;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i1 = te.getProgressScaled(145);
		this.drawTexturedModalRect(j+7, k+17, 0, 189, i1, 67);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (te.getLevel() > 0) {
			int i2 = te.getLiquidScaled(72);
			int x = 152;
			int y = 89-i2+1;
			GL11.glColor4f(1, 1, 1, 1);
			Fluid f = te.getContainedFluid();
			IIcon ico = f.getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
			this.drawTexturedModelRectFromIcon(x, y, ico, 16, i2);
		}
		if (api.isMouseInBox(j+152, j+167, k+18, k+89)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRendererObj, String.format("%d/%d", te.getLevel(), te.getCapacity()), mx-j, my-k);
			if (te.getLevel() > 0) {
				Fluid f = te.getContainedFluid();
				String s = f.getLocalizedName();
				api.drawTooltipAt(fontRendererObj, s, mx-j, my-k+12);
			}
		}
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-24);

		long frac = (te.power*29L)/te.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-166, 0, 0, (int)frac, 4);

		frac = (int)(te.omega*29L)/te.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-106, 0, 0, (int)frac, 4);

		frac = (int)(te.torque*29L)/te.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-46, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRendererObj, String.format("%d/%d", te.power, te.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "fridgegui";
	}
}