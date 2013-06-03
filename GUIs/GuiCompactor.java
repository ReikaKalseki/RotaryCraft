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

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;

public class GuiCompactor extends GuiMachine
{
	private TileEntityCompactor comp;

	public GuiCompactor(EntityPlayer p5ep, TileEntityCompactor Compactor)
	{
		super(new ContainerCompactor(p5ep, Compactor), Compactor);
		comp = Compactor;
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
		int i1 = comp.getCookProgressScaled(30);
		int i2 = comp.getTemperatureScaled(54);
		int i3 = comp.getPressureScaled(54);
		if (i1 != 0)
			i1++;
		if (i2 < 9)
			i2 = 9;
		this.drawTexturedModalRect(j + 46, k + 14, 193, 32, 1*(i1), 58);
		this.drawTexturedModalRect(j + 147, k + 70-i3, 176, 82-i3, 4, i3);
		this.drawTexturedModalRect(j + 118, k + 70-i2, 182, 86-i2, 9, i2);
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-4);

		long frac = (comp.power*29L)/comp.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-144, 0, 0, (int)frac, 4);

		frac = (int)(comp.omega*29L)/comp.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-84, 0, 0, (int)frac, 4);

		frac = (int)(comp.torque*29L)/comp.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-24, 0, 0, (int)frac, 4);

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", comp.power, comp.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "compactorgui2";
	}
}
