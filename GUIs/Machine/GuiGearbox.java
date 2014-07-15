/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

public class GuiGearbox extends GuiNonPoweredMachine
{
	private TileEntityGearbox gbx;

	public GuiGearbox(EntityPlayer p5ep, TileEntityGearbox Gearbox)
	{
		super(new ContainerGearbox(p5ep, Gearbox), Gearbox);
		gbx = Gearbox;
		ep = p5ep;
		ySize = 84;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		fontRenderer.drawString("Lubricant", 5, 12, 4210752);

		fontRenderer.drawString("Damage:", 68, 60, 0x000000);
		int damage = gbx.getDamagePercent();
		if (damage < 10)
			fontRenderer.drawString(String.format("%5d%s", damage, "%"), 122, 60, 0x00ff00);
		if (damage < 25 && damage >= 10)
			fontRenderer.drawString(String.format("%5d%s", damage, "%"), 122, 60, 0x55ff00);
		if (damage < 50 && damage >= 25)
			fontRenderer.drawString(String.format("%5d%s", damage, "%"), 122, 60, 0xffff00);
		if (damage < 80 && damage >= 50)
			fontRenderer.drawString(String.format("%5d%s", damage, "%"), 122, 60, 0xff5500);
		if (damage >= 80)
			fontRenderer.drawString(String.format("%5d%s", damage, "%"), 122, 60, 0xff0000);

		fontRenderer.drawString("Ratio:", 80, 24, 0x000000);
		fontRenderer.drawString("Mode:", 80, 36, 0x000000);
		fontRenderer.drawString("Power:", 74, 48, 0x000000);

		fontRenderer.drawString(String.format("%5d ", gbx.getRatio()), 127, 24, 0x000000);
		if (gbx.reduction)
			fontRenderer.drawString("Torque", 115, 36, 0x000000);
		else
			fontRenderer.drawString(" Speed", 115, 36, 0x000000);

		if (gbx.power < 1000)
			fontRenderer.drawString(String.format("%3d  W", gbx.power), 122, 48, 0x000000);
		if (gbx.power < 1000000 && gbx.power >= 1000)
			fontRenderer.drawString(String.format("%.1f kW", gbx.power/1000D), 112, 48, 0x000000);
		if (gbx.power >= 1000000)
			fontRenderer.drawString(String.format("%.1f MW", gbx.power/1000000D), 112, 48, 0x000000);

		if (api.isMouseInBox(j+23, j+32, k+20, k+76)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRenderer, String.format("%.1f/%d", gbx.getLubricant()/1000F, gbx.getMaxLubricant()/1000), mx-j, my-k);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i2 = gbx.getLubricantScaled(55);
		int i3 = 0;
		if (i2 != 0)
			i3 = 1;
		this.drawTexturedModalRect(j + 24, ySize/2+k+34-i2, 176, 126-i2, 8, i2);
	}

	@Override
	public String getGuiTexture() {
		return "gearboxgui";
	}
}
