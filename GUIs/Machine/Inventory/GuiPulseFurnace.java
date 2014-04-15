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

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerPulseFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPulseFurnace extends GuiMachine {

	private TileEntityPulseFurnace puls;

	public GuiPulseFurnace(EntityPlayer p5ep, TileEntityPulseFurnace pulseFurnace)
	{
		super(new ContainerPulseFurnace(p5ep, pulseFurnace), pulseFurnace);
		puls = pulseFurnace;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (api.isMouseInBox(j+90, j+96, k+17, k+68)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRenderer, String.format("%d/%d", puls.getFuel(), puls.MAXFUEL), mx-j, my-k);
		}
		if (api.isMouseInBox(j+20, j+30, k+15, k+70)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRenderer, String.format("%dC", puls.temperature), mx-j, my-k);
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

		int i1 = puls.getCookProgressScaled(10);
		int i2 = puls.getFuelScaled(52);
		int i3 = puls.getWaterScaled(52);
		int i4 = puls.getTempScaled(54);
		if (i4 < 9)
			i4 = 9;
		int i5 = puls.getFireScaled(38);
		this.drawTexturedModalRect(j + 131, k + 36, 215, 55, 4, 1*(i1));
		this.drawTexturedModalRect(j + 91, k + 68-i2, 248, 53-i2, 5, 1*(i2));
		this.drawTexturedModalRect(j + 59, k + 68-i3, 199, 53-i3, 5, 1*(i3));
		this.drawTexturedModalRect(j + 20, k + 70-i4, 176, 55-i4, 11, 1*(i4));
		this.drawTexturedModalRect(j + 115, k + 61-i5, 177, 95-i5, 9, 1*(i5));
		this.drawTexturedModalRect(j + 142, k + 61-i5, 204, 95-i5, 9, 1*(i5));
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-4);

		long frac = (puls.power*29L)/puls.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-144, 0, 0, (int)frac, 4);

		frac = (int)(puls.omega*29L)/puls.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-84, 0, 0, (int)frac, 4);

		frac = (int)(puls.torque*29L)/puls.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-24, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		api.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		api.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", puls.power, puls.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "pulsejetgui";
	}

}
