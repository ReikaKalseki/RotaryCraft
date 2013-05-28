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
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.TileEntityGearBevel;

public class GuiBevel extends GuiNonPoweredMachine
{
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int posn;

	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int in;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private int out;
	/** Cyan-Blue, Yellow-Black, Orange-Magenta (0,1; 2,3; 4,5) */
	private boolean[] isValid = {true, true, true, true, true, true};

    private TileEntityGearBevel bevel;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiBevel(EntityPlayer player, TileEntityGearBevel GearBevel)
    {
        super(new CoreContainer(player, GearBevel), GearBevel);
        bevel = GearBevel;
    	ySize = 192;
    	this.player = player;
    	posn = GearBevel.direction;
    	this.getIOFromDirection();
    }

    @Override
    public void initGui() {
        int j = (width - xSize) / 2-2;
        int k = (height - ySize) / 2 - 12;/*
            buttonList.add(new GuiButton(0, j+8, -1+k+32, 40, 20, "E->N"));
            buttonList.add(new GuiButton(1, j+8, -1+k+52, 40, 20, "S->E"));
            buttonList.add(new GuiButton(2, j+8, -1+k+72, 40, 20, "W->S"));
            buttonList.add(new GuiButton(3, j+8, -1+k+92, 40, 20, "N->W"));

            buttonList.add(new GuiButton(4, j+60, -1+k+32, 40, 20, "E->S"));
            buttonList.add(new GuiButton(5, j+60, -1+k+52, 40, 20, "S->W"));
            buttonList.add(new GuiButton(6, j+60, -1+k+72, 40, 20, "W->N"));
            buttonList.add(new GuiButton(7, j+60, -1+k+92, 40, 20, "N->E"));

            buttonList.add(new GuiButton(8, j+112, -1+k+32, 40, 20, "E->U"));
            buttonList.add(new GuiButton(9, j+112, -1+k+52, 40, 20, "S->U"));
            buttonList.add(new GuiButton(10, j+112, -1+k+72, 40, 20, "W->U"));
            buttonList.add(new GuiButton(11, j+112, -1+k+92, 40, 20, "N->U"));

            buttonList.add(new GuiButton(12, j+8, -1+8+k+112, 40, 20, "U->W"));
            buttonList.add(new GuiButton(13, j+8, -1+8+k+132, 40, 20, "U->N"));
            buttonList.add(new GuiButton(14, j+8, -1+8+k+152, 40, 20, "U->E"));
            buttonList.add(new GuiButton(15, j+8, -1+8+k+172, 40, 20, "U->S"));

            buttonList.add(new GuiButton(16, j+60, -1+8+k+112, 40, 20, "E->D"));
            buttonList.add(new GuiButton(17, j+60, -1+8+k+132, 40, 20, "S->D"));
            buttonList.add(new GuiButton(18, j+60, -1+8+k+152, 40, 20, "W->D"));
            buttonList.add(new GuiButton(19, j+60, -1+8+k+172, 40, 20, "N->D"));

            buttonList.add(new GuiButton(20, j+112, -1+8+k+112, 40, 20, "D->W"));
            buttonList.add(new GuiButton(21, j+112, -1+8+k+132, 40, 20, "D->N"));
            buttonList.add(new GuiButton(22, j+112, -1+8+k+152, 40, 20, "D->E"));
            buttonList.add(new GuiButton(23, j+112, -1+8+k+172, 40, 20, "D->S"));*/
        buttonList.clear();

        String file = "/Reika/RotaryCraft/Textures/GUI/bevelgui2.png";
        int px = 176;
        for (int i = 0; i < 6; i++) {
        	if (in == i)
        		buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px+18, i*18, 0, file));
        	else
        		buttonList.add(new ImagedGuiButton(i, j+40, k+8+48+i*22, 18, 18, px, i*18, 0, file));
        }
        for (int i = 0; i < 6; i++) {
        	if (isValid[i]) {
        		if (out == i)
            		buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px+18, i*18, 0, file));
        		else
            		buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, px, i*18, 0, file));
        	}
        	else
        		buttonList.add(new ImagedGuiButton(i+6, j+xSize-40-18, k+8+48+i*22, 18, 18, 212, 0, 0, file));
        }
    }

	public void getIOFromDirection() {
		switch(posn) {

		}
	}

	public void getDirectionFromIO() {
		System.out.print(bevel.colorNames[in]+" to "+bevel.colorNames[out]+" -> data: ");
		switch(in) {
		case 0:
			switch(out) {
			case 2:
				posn = 13;
			break;
			case 3:
				posn = 15;
			break;
			case 4:
				posn = 12;
			break;
			case 5:
				posn = 14;
			break;
			}
		break;
		case 1:
			switch(out) {
			case 2:
				posn = 21;
			break;
			case 3:
				posn = 23;
			break;
			case 4:
				posn = 20;
			break;
			case 5:
				posn = 22;
			break;
			}
		break;
		case 2:
			switch(out) {
			case 0:
				posn = 17;
			break;
			case 1:
				posn = 9;
			break;
			case 4:
				posn = 5;
			break;
			case 5:
				posn = 1;
			break;
			}
		break;
		case 3:
			switch(out) {
			case 0:
				posn = 19;
			break;
			case 1:
				posn = 11;
			break;
			case 4:
				posn = 3;
			break;
			case 5:
				posn = 7;
			break;
			}
		break;
		case 4:
			switch(out) {
			case 0:
				posn = 16;
			break;
			case 1:
				posn = 8;
			break;
			case 2:
				posn = 0;
			break;
			case 3:
				posn = 4;
			break;
			}
		break;
		case 5:
			switch(out) {
			case 0:
				posn = 18;
			break;
			case 1:
				posn = 10;
			break;
			case 2:
				posn = 6;
			break;
			case 3:
				posn = 2;
			break;
			}
		break;
		}
	}

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }

    private void setValidStates() {
    	for (int i = 0; i < 6; i++)
    		isValid[i] = true;
    	isValid[in] = false;
    	if (in%2 == 0)
    		isValid[in+1] = false;
    	else
    		isValid[in-1] = false;
    }

    @Override
    public void actionPerformed(GuiButton button) {
    	if (button.id < 6) {
    		in = button.id;
    		this.setValidStates();
    	}
    	else {
    		if (!isValid[button.id-6])
    			return;
    		out = button.id-6;
    	}
    	this.getDirectionFromIO();
    	this.initGui();
        ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 4, bevel, player, posn);
    }

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	x = Mouse.getX();
    	y = Mouse.getY();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2-2;
        int k = (height - ySize) / 2;
        //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 4210752);
        fontRenderer.drawString("Bevel Gears", j+xSize/2-30, k+5, 4210752);

        fontRenderer.drawString("Input Side", j+24, k+32, 4210752);
        fontRenderer.drawString("Output Side", j+99, k+32, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/bevelgui2.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

    }

}
