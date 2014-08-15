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

import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;

import net.minecraft.entity.player.EntityPlayer;

public class GuiAerosolizer extends GuiPowerOnlyMachine
{
	TileEntityAerosolizer aero;

	public GuiAerosolizer(EntityPlayer p5ep, TileEntityAerosolizer Aerosolizer)
	{
		super(new ContainerAerosolizer(p5ep, Aerosolizer), Aerosolizer);
		aero = Aerosolizer;
		ep = p5ep;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;

		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int amount = aero.potionLevel[3*i+j]/4;
				//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d %d %d %d %d %d %d %d %d", aero.potionLevel[0], aero.potionLevel[1], aero.potionLevel[2], aero.potionLevel[3], aero.potionLevel[4], aero.potionLevel[5], aero.potionLevel[6], aero.potionLevel[7], aero.potionLevel[8]));
				api.fillBar(var5+62+18*j, var6+17+18*i, 16, var6+17+18*i+16, aero.slotColor[3*i+j]+0xff000000, amount, 16, true);
				api.drawCenteredStringNoShadow(fontRendererObj, String.format("%d", aero.potionLevel[3*i+j]), var5+70+18*j, var6+22+18*i, 0xff000000);
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "aerosolizergui";
	}
}