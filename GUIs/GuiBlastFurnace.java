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
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;

public class GuiBlastFurnace extends GuiNonPoweredMachine
{
    private TileEntityBlastFurnace tile;

    public GuiBlastFurnace(EntityPlayer player, TileEntityBlastFurnace BlastFurnace)
    {
        super(new ContainerBlastFurnace(player, BlastFurnace), BlastFurnace);
        tile = BlastFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), xSize-168, (ySize - 96) + 2, 4210752);
        fontRenderer.drawString("Blast Furnace", xSize/3-4, 6, 4210752);
        int c = 0;
        if (tile.temperature >= 1000)
        	c = 1;
        ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, String.valueOf(tile.temperature)+"C", 17+c, 6, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/blastfurngui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        int i1 = tile.getCookScaled(24);
        this.drawTexturedModalRect(j+119, k+34, 176, 14, i1+1, 16);
        int i2 = tile.getTemperatureScaled(54);
        this.drawTexturedModalRect(j + 11, k + 70-i2, 176, 86-i2, 10, i2);
    }
}
