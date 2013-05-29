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

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerCCTV;
import Reika.RotaryCraft.TileEntities.TileEntityCCTV;

public class GuiCCTV extends GuiNonPoweredMachine {

	public GuiCCTV(EntityPlayer player, TileEntityCCTV tile) {
		super(new ContainerCCTV(player, tile), tile);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;

        String i = "/Reika/RotaryCraft/Textures/GUI/cctvgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		this.drawPowerTab(j, k);
	}
}
