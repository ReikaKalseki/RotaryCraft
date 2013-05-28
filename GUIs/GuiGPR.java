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

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;

public class GuiGPR extends GuiPowerOnlyMachine
{

    private TileEntityGPR gpr;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;
    public static final int UNIT = 2;

    public GuiGPR(EntityPlayer player, TileEntityGPR GPR)
    {
        super(new CoreContainer(player, GPR), GPR);
        gpr = GPR;
    	ySize = 240;
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
        String i = "/Reika/RotaryCraft/Textures/GUI/gprgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2+1;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        this.drawRadar2(j, k);
        this.drawPowerTab(j, k);
    }

    private void drawRadar(int a, int b) {
    	int width = gpr.getBounds()[1]-gpr.getBounds()[0];
    	for (int i = 0; i < 256; i++) {
    		for (int j = 0; j < width; j++) {
    			this.drawRect(a+15+UNIT*i, b+15*UNIT*j, a+15+UNIT*i+4, b+15*UNIT*j+4, 0xff000000); //gpr.colors[j][i]
    		}
    	}
    }

    @Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, StatCollector.translateToLocal(gpr.getName()), xSize/2, 6, 4210752);
    }

    private void drawRadar2(int a, int b) {
    	int width = gpr.getBounds()[1]-gpr.getBounds()[0];
    	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", width));
    	for (int j = gpr.getBounds()[0]; j <= gpr.getBounds()[1]; j++) {
	    	for (int i = 0; i < gpr.yCoord; i++) {
	    		this.drawRect(a+7+UNIT*j, b+16+UNIT*i, a+7+UNIT+UNIT*j, b+16+UNIT*i+UNIT, gpr.colors[i][j]);
	    	}
    	}
    }
}
