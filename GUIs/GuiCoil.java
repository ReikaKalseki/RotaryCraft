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

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;

public class GuiCoil extends GuiNonPoweredMachine
{

	private int omega;
	private int torque;
    private GuiTextField input;
    private GuiTextField input2;

    private TileEntityAdvancedGear coil;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;

    public GuiCoil(EntityPlayer player, TileEntityAdvancedGear AdvancedGear)
    {
        super(new CoreContainer(player, AdvancedGear), AdvancedGear);
        coil = AdvancedGear;
    	ySize = 72;
    	xSize = 176;
    	this.player = player;
    }

    @Override
    public void initGui() {
        int j = (width - xSize) / 2+8;
        int k = (height - ySize) / 2 - 12;
        input = new GuiTextField(fontRenderer, j+xSize/2-15, k+30, 56, 16);
        input.setFocused(false);
        input.setMaxStringLength(8);
        input2 = new GuiTextField(fontRenderer, j+xSize/2-15, k+60, 56, 16);
        input2.setFocused(false);
        input2.setMaxStringLength(8);
    }

    @Override
	public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
    	input.textboxKeyTyped(c, i);
    	input2.textboxKeyTyped(c, i);
    }

    @Override
	public void mouseClicked(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	input.mouseClicked(i, j, k);
    	input2.mouseClicked(i, j, k);
    }

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	boolean valid1 = true;
    	boolean valid2 = true;
    	x = Mouse.getX();
    	y = Mouse.getY();
	    if (input.getText().isEmpty() && input2.getText().isEmpty()) {
	    	return;
	    }
	    if (input.getText().isEmpty())
	    	valid1 = false;
	    if (input2.getText().isEmpty())
	    	valid2 = false;
	    if (!input.getText().isEmpty() && !(input.getText().matches("^[0-9 ]+$"))) {
	    	omega = 0;
	    	input.deleteFromCursor(-1);
	    	ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 19, coil, player, omega);
	    	valid1 = false;
	    }
	    if (!input2.getText().isEmpty() && !(input2.getText().matches("^[0-9 ]+$"))) {
	    	torque = 0;
	    	input2.deleteFromCursor(-1);
	    	ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 20, coil, player, torque);
	    	valid2 = false;
	    }
	    if (!valid1 && !valid2)
	    	return;
	    //ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
	    //System.out.println(input.getText());
	    if (valid1) {
		    omega = Integer.parseInt(input.getText());
		    if (omega >= 0) {
		    	if (omega > RotaryConfig.omegalimit)
		    		omega = RotaryConfig.omegalimit;
		    	ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 19, coil, player, omega);
		    }
		}
	    if (valid2) {
		    torque = Integer.parseInt(input2.getText());
		    if (torque >= 0) {
		    	if (torque > RotaryConfig.torquelimit)
		    		torque = RotaryConfig.torquelimit;
		    	ReikaPacketHelper.sendPacket(mod_RotaryCraft.packetChannel, 20, coil, player, torque);
		    }
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

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 4210752);
        fontRenderer.drawString("Industrial Coil", j+xSize/2-32, k+5, 4210752);
        fontRenderer.drawString("Output Speed", j+xSize/2-82, k+22, 4210752);
        fontRenderer.drawString("Output Torque", j+xSize/2-82, k+52, 4210752);

        fontRenderer.drawString("rad/s", j+xSize/2+53, k+22, 4210752);
        fontRenderer.drawString("Nm", j+xSize/2+53, k+52, 4210752);

        if (!input.isFocused())
        	fontRenderer.drawString(String.format("%d", coil.releaseOmega), j+xSize/2-3, k+22, 0xffffffff);
        if (!input2.isFocused())
        	fontRenderer.drawString(String.format("%d", coil.releaseTorque), j+xSize/2-3, k+52, 0xffffffff);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/coilgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

        input.drawTextBox();
        input2.drawTextBox();
    }

}
