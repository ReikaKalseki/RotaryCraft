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
import Reika.RotaryCraft.Containers.ContainerEthanol;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class GuiEthanol extends GuiNonPoweredMachine
{
    private TileEntityEngine Ethanol;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiEthanol(EntityPlayer player, TileEntityEngine tile)
    {
        super(new ContainerEthanol(player, tile), tile);
        Ethanol = tile;
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
        String i = "/Reika/RotaryCraft/Textures/GUI/ethanolgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        //int i2 = Ethanol.getLiquidScaled(54);
        //int i3 = Ethanol.getTempScaled(54);
        //drawTexturedModalRect(j+49, k+71-i2, 193, 55-i2, 5, i2);
        //drawTexturedModalRect(j+119, k+71-i3, 177, 99-i3, 9, i3);

        int i1 = Ethanol.getEthanolScaled(54);
        this.drawTexturedModalRect(j+85, k+71-i1, 200, 55-i1, 5, i1);

    }
}
