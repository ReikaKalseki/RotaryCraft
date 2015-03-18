/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerSteam;
import Reika.RotaryCraft.TileEntities.Engine.TileEntitySteamEngine;

public class GuiSteam extends GuiNonPoweredMachine
{
	private TileEntitySteamEngine Steam;

	public GuiSteam(EntityPlayer p5ep, TileEntitySteamEngine te)
	{
		super(new ContainerSteam(p5ep, te), te);
		Steam = te;
		xSize = 176;
		ySize = 79;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i2 = Steam.getWaterScaled(54);
		int i3 = Steam.getTempScaled(54);
		this.drawTexturedModalRect(j+49, k+71-i2, 193, 55-i2, 5, i2);
		this.drawTexturedModalRect(j+119, k+71-i3, 177, 99-i3, 9, i3);
	}

	@Override
	public boolean labelInventory() {
		return false;
	}

	@Override
	protected String getGuiTexture() {
		return "steamgui";
	}

}
