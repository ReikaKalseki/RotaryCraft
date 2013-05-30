/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;

@SideOnly(Side.CLIENT)
public class GuiPulseFurnace extends GuiMachine {

	private TileEntityPulseFurnace tile;

	public GuiPulseFurnace(EntityPlayer player, TileEntityPulseFurnace pulseFurnace)
	{
		super(new ContainerPulseFurnace(player, pulseFurnace), pulseFurnace);
		tile = pulseFurnace;
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

		int i1 = tile.getCookProgressScaled(10);
		int i2 = tile.getFuelScaled(52);
		int i3 = tile.getWaterScaled(52);
		int i4 = tile.getTempScaled(54);
		if (i4 < 9)
			i4 = 9;
		int i5 = tile.getFireScaled(38);
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
		mc.renderEngine.bindTexture(var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-4);

		long frac = (tile.power*29L)/tile.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-144, 0, 0, (int)frac, 4);

		frac = (int)(tile.omega*29L)/tile.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-84, 0, 0, (int)frac, 4);

		frac = (int)(tile.torque*29L)/tile.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-24, 0, 0, (int)frac, 4);

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", tile.power, tile.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "pulsejetgui";
	}

}
