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
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;

public class GuiMobRadar extends GuiPowerOnlyMachine
{

    private TileEntityMobRadar radar;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;
    public static final int UNIT = 4;
    private boolean hostile = true;
    private boolean animal = true;
    private boolean players = true;
    private int direction;

    public GuiMobRadar(EntityPlayer player, TileEntityMobRadar MobRadar)
    {
        super(new CoreContainer(player, MobRadar), MobRadar);
        radar = MobRadar;
    	ySize = 223;
    	xSize = 214;
    	this.player = player;
    	hostile = radar.hostile;
    	animal = radar.animal;
    	players = radar.player;
        direction = MathHelper.floor_double((player.rotationYaw * 4F) / 360F + 0.5D);
        while (direction > 3)
        	direction -= 4;
        while (direction < 0)
        	direction += 4;
    }
    /*
    @Override
    public void initGui() {
        int j = (width - xSize) / 2+8;
        int k = (height - ySize) / 2 - 12;
    }*/


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
        String i = "/Reika/RotaryCraft/Textures/GUI/mobradargui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2+1;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

    	this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+5+radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);
    	this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+5-radar.getRange()*UNIT, 0xffffffff);
    	this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2-radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);
    	this.drawRect(j+xSize/2+radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);

        this.drawRadar2(j, k);
        this.drawPowerTab(j, k);
    }

    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, radar.getName(), j+xSize/2, k+5, 4210752);
    }

    private void drawRadar2(int a, int b) {
    	int width = radar.getBounds()[1]-radar.getBounds()[0];
    	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", width));
    	for (int i = radar.getBounds()[0]; i <= radar.getBounds()[1]; i++) {
	    	for (int j = radar.getBounds()[0]; j <=	 radar.getBounds()[1]; j++) {
	    		//this.drawRect(a+7+UNIT*i, b+16+UNIT*j, a+7+UNIT+UNIT*i, b+16+UNIT*j+UNIT, radar.colors[j][i]);
	    		this.drawMobIcon(a+7, b+16, UNIT*i, UNIT*j, radar.mobs[i][j], i, j);
	    	}
    	}
    }

    private void drawMobIcon(int a, int b, int x, int y, int id, int i, int j) {
    	String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	mc.renderEngine.bindTexture(var4);
    	int v = 2*UNIT*(id/16);
    	int u = 2*UNIT*(id-(v/UNIT/2)*16);
    	if (id == -1) {
    		u = 2*UNIT;
    		v = 0;
    	}
    	this.drawTexturedModalRect(a+x-UNIT/2, b+y-UNIT/2, u, v, UNIT*2, UNIT*2);
    }
}
