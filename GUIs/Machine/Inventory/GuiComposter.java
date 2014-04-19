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
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerComposter;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;

public class GuiComposter extends GuiNonPoweredMachine
{
	private TileEntityComposter ferm;

	public GuiComposter(EntityPlayer p5ep, TileEntityComposter Composter)
	{
		super(new ContainerComposter(p5ep, Composter), Composter);
		ferm = Composter;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i1 = ferm.getScaledTimer(48);
		this.drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);
	}

	@Override
	public String getGuiTexture() {
		return "compostergui";
	}
}
