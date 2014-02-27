/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;

public class GuiCalculator extends GuiScreen {

	private int mx;
	private int my;
	private static final int xSize = 194;
	private static final int ySize = 161;
	private EntityPlayer ep;
	public World worldObj;

	public GuiCalculator(EntityPlayer p5ep, World world)
	{
		ep = p5ep;
		worldObj = world;
	}
	/*
    @Override
    public void initGui() {
    	super.initGui();
    	this.buttonList.clear();

        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2 - 8;
    }*/

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	public void actionPerformed(GuiButton button) {

	}

	public void refreshScreen() {
		int lastx = mx;
		int lasty = my;
		mc.thePlayer.closeScreen();
		ModLoader.openGUI(ep, new GuiCalculator(ep, worldObj));
		Mouse.setCursorPosition(lastx, lasty);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		mx = Mouse.getX();
		my = Mouse.getY();
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		String var4 = "/Reika/RotaryCraft/Textures/GUI/calcgui.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
		this.drawKeys();
		super.drawScreen(x, y, f);
	}

	private void drawKeys() {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2 - 8;
		int color = 0x000000;/*
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "π", j+16, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "Int", j+16, k+141-54, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "Exp", j+34, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "tanh", j+34, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "cosh", j+34, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "sinh", j+34, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "e^x", j+34, k+141-72, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "Mod", j+52, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "tan", j+52, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "cos", j+52, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "sin", j+52, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "ln", j+52, k+141-72, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "log", j+70, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "x^3", j+70, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "x^y", j+70, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "x^2", j+70, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "(", j+70, k+141-72, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "10^x", j+88, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "x^0.3", j+88, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "x^.y", j+88, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "n!", j+88, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, ")", j+88, k+141-72, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "0", j+106+9, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "1", j+106, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "4", j+106, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "7", j+106, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "Bk", j+106, k+141-72, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "MC", j+106, k+141-90, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "2", j+124, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "5", j+124, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "8", j+124, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "CE", j+124, k+141-72, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "MR", j+124, k+141-90, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, ".", j+142, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "3", j+142, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "6", j+142, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "9", j+142, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "C", j+142, k+141-72, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "MS", j+142, k+141-90, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "+", j+160, k+141, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "-", j+160, k+141-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "*", j+160, k+141-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "÷", j+160, k+141-54, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "±", j+160, k+141-72, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "M-", j+160, k+141-90, color);

    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "=", j+178, k+131, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "1/x", j+178, k+131-26, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "%", j+178, k+131-26-18, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "√", j+178, k+131-26-36, color);
    	ImagedGuiButton.drawCenteredStringNoShadow(this.fontRenderer, "M-", j+178, k+131-26-54, color);*/
	}
}
