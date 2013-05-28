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
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerGrinder;
import Reika.RotaryCraft.TileEntities.TileEntityGrinder;

public class GuiGrinder extends GuiMachine
{
    private TileEntityGrinder tile;

    public GuiGrinder(EntityPlayer player, TileEntityGrinder Grinder)
    {
        super(new ContainerGrinder(player, Grinder), Grinder);
        tile = Grinder;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), xSize-58, (ySize - 96) + 2, 4210752);
        fontRenderer.drawString("Grinder", xSize/3+9, 6, 4210752);
        fontRenderer.drawString("Lubricant", 5, 11, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/grindergui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        int i1 = tile.getCookProgressScaled(48);
        this.drawTexturedModalRect(j + 99, k + 34, 176, 14, 1*(i1)+1, 16);

        int i2 = tile.getLubricantScaled(55);
        int i3 = 0;
        if (i2 != 0)
        	i3 = 1;
        this.drawTexturedModalRect(j + 24, ySize/2+k-7-i2, 176, 126-i2, 8, i2);

        this.drawPowerTab(j, k);
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
}
