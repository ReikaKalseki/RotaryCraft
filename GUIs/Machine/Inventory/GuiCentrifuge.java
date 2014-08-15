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
import Reika.RotaryCraft.Containers.ContainerCentrifuge;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCentrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

public class GuiCentrifuge extends GuiMachine
{
	private TileEntityCentrifuge cent;

	int x;
	int y;

	public GuiCentrifuge(EntityPlayer p5ep, TileEntityCentrifuge tilef)
	{
		super(new ContainerCentrifuge(p5ep, tilef), tilef);
		cent = tilef;
		xSize = 176;
		ySize = 166;
		ep = p5ep;
	}

	@Override
	protected boolean inventoryLabelLeft() {
		return true;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (api.isMouseInBox(j+151, j+168, k+7, k+78)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			int level = cent.getLevel();
			if (level > 0) {
				String name = cent.getFluid().getLocalizedName();
				api.drawTooltipAt(fontRendererObj, String.format("%s: %d/%d", name, cent.getLevel(), cent.CAPACITY), mx-j, my-k);
			}
			else {
				api.drawTooltipAt(fontRendererObj, String.format("0/%d mB", cent.CAPACITY), mx-j, my-k);
			}
		}
		if (cent.getLevel() > 0) {
			int i2 = cent.getLiquidScaled(70);
			int x = 152;
			int y = 77-i2+1;
			GL11.glColor4f(1, 1, 1, 1);
			Fluid f = cent.getFluid();
			IIcon ico = f.getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
			this.drawTexturedModelRectFromIcon(x, y, ico, 16, i2);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i3 = cent.getProgressScaled(37);
		if (i3 > 37)
			i3 = 37;
		this.drawTexturedModalRect(j+45, k+27, 178, 1, i3, 37);
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-4);

		long frac = (cent.power*29L)/cent.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-144, 0, 0, (int)frac, 4);

		frac = cent.omega*29L/cent.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-84, 0, 0, (int)frac, 4);

		frac = cent.torque*29L/cent.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-24, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRendererObj, String.format("%d/%d", fct.power, fct.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "centrifugegui";
	}
}