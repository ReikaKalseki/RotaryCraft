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
import net.minecraft.src.ModLoader;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerSteam;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class GuiSteam extends GuiNonPoweredMachine
{
    private TileEntityEngine Steam;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiSteam(EntityPlayer player, TileEntityEngine tile)
    {
        super(new ContainerSteam(player, tile), tile);
        Steam = tile;
        xSize = 176;
    	ySize = 166;
    	this.player = player;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
/*
    @Override
    public void actionPerformed(GuiButton button) {
            if (button.id == 7) {
            	this.toggleDrops();
            }
            if (button.id < 7) {
            	this.updateMode(button.id);
            }
            this.refreshScreen();
    }*/

    public void refreshScreen() {
    	int lastx = x;
    	int lasty = y;
    	mc.thePlayer.closeScreen();
    	ModLoader.openGUI(player, new GuiSteam(player, Steam));
    	Mouse.setCursorPosition(lastx, lasty);
    }

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	x = Mouse.getX();
    	y = Mouse.getY();
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/steamgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        int i2 = Steam.getLiquidScaled(54);
        int i3 = Steam.getTempScaled(54);
        this.drawTexturedModalRect(j+49, k+71-i2, 193, 55-i2, 5, i2);
        this.drawTexturedModalRect(j+119, k+71-i3, 177, 99-i3, 9, i3);

        //int i1 = SteamInventory.getCookProgressScaled(48);
        //drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);
    }

}
