/*******************************************************************************
 * @author Reika
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
import Reika.RotaryCraft.Containers.ContainerJet;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class GuiJet extends GuiNonPoweredMachine
{
    private TileEntityEngine eng;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiJet(EntityPlayer player, TileEntityEngine tile)
    {
        super(new ContainerJet(player, tile), tile);
        eng = tile;
        xSize = 176;
    	ySize = 166;
    	this.player = player;
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/jetgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        int i2 = eng.getJetFuelScaled(54);
        this.drawTexturedModalRect(j+85, k+71-i2, 207, 55-i2, 5, i2);

        //int i1 = SteamInventory.getCookProgressScaled(48);
        //drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);
    }
}
