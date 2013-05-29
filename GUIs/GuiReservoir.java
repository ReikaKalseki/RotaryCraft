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
import Reika.RotaryCraft.Containers.ContainerReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;

public class GuiReservoir extends GuiNonPoweredMachine
{
	boolean water = false;

    private TileEntityReservoir Reservoir;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiReservoir(EntityPlayer player, TileEntityReservoir tile)
    {
        super(new ContainerReservoir(player, tile), tile);
        Reservoir = tile;
        xSize = 176;
    	ySize = 166;
    	this.player = player;
    	water = (tile.liquidID == 8 || tile.liquidID == 9);
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
    	ModLoader.openGUI(player, new GuiReservoir(player, Reservoir));
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
        String i = "/Reika/RotaryCraft/Textures/GUI/reservoirgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        int i2 = Reservoir.getLiquidScaled(44);
        if (i2 > 44)
        	i2 = 44;
        int i3 = 0;
        if (i2 != 0)
        	i3 = 1;
        int i4 = 0;
        if (!water)
        	i4 = 8;
        this.drawTexturedModalRect(j +xSize/2-4, ySize/2+k-13-i2, 176+i4, 0, 8, i2);

        //int i1 = ReservoirInventory.getCookProgressScaled(48);
        //drawTexturedModalRect(j + 79, k + 34, 176, 14, 1*(i1)+1, 16);
    }

}
