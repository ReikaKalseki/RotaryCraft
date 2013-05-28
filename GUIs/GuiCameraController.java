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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiCameraController extends GuiScreen {

	private EntityPlayer ep;

    private int x;
    private int y;
    private static final int xSize = 194;
    private static final int ySize = 161;

    private int channel = 0;

	public GuiCameraController(EntityPlayer player) {
		ep = player;
		ItemStack is = ep.getCurrentEquippedItem();
		if (is != null) {

		}
	}

    @Override
    public void initGui() {
    	super.initGui();
    	buttonList.clear();
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2 - 8;


    }

    @Override
	public boolean doesGuiPauseGame()
    {
        return true;
    }

    @Override
    public void actionPerformed(GuiButton button) {

    }

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	x = Mouse.getX();
    	y = Mouse.getY();
    }

    @Override
	public void drawScreen(int x, int y, float f)
    {
    	String var4 = "/Reika/RotaryCraft/Textures/GUI/calcgui.png";
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	mc.renderEngine.bindTexture(var4);

    	int posX = (width - xSize) / 2;
    	int posY = (height - ySize) / 2 - 8;

    	this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
    	super.drawScreen(x, y, f);
    }
}
