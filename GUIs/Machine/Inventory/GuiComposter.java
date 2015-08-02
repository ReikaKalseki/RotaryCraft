/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerComposter;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;

public class GuiComposter extends GuiNonPoweredMachine
{
	private TileEntityComposter comp;

	public GuiComposter(EntityPlayer p5ep, TileEntityComposter Composter)
	{
		super(new ContainerComposter(p5ep, Composter), Composter);
		comp = Composter;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i1 = comp.getScaledTimer(48);
		this.drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);

		int i2 = comp.getScaledTemperature(54);
		if (i2 > 54)
			i2 = 54;
		this.drawTexturedModalRect(j+24, k+70-i2, 177, 86-i2, 9, i2);
	}

	@Override
	protected String getGuiTexture() {
		return "compostergui";
	}
}
