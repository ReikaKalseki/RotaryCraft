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
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;

public class GuiSonic extends GuiPowerOnlyMachine
{
    private TileEntitySonicWeapon sonic;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;
    private GuiTextField input;
    private GuiTextField input2;

    private long freq;
    private long vol;

    private int dB;

    public GuiSonic(EntityPlayer player, TileEntitySonicWeapon SonicWeapon)
    {
        super(new CoreContainer(player, SonicWeapon), SonicWeapon);
        sonic = SonicWeapon;
    	ySize = 92;
    	if (!sonic.DECIBELMODE)
    		xSize = 235;
    	if (!sonic.ENABLEFREQ)
    		ySize = 56;
    	this.player = player;
    	vol = SonicWeapon.setvolume;
    	freq = SonicWeapon.setpitch;

    	dB = (int)(10*Math.log10(vol));
    	if (dB < 0)
    		dB = 0;

    	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.freq));
    }

    @Override
    public void initGui() {
        int j = (width - xSize) / 2+8;
        int k = (height - ySize) / 2 - 12;
        int xo = 0;
        if (sonic.DECIBELMODE)
        	xo = 48;
	    input = new GuiTextField(fontRenderer, j+xSize/2-62, k+73, 86, 16);
	    input.setFocused(false);
	    input.setMaxStringLength(12);
        input2 = new GuiTextField(fontRenderer, j+xSize/2-62+xo, k+43, 86-xo, 16);
        input2.setFocused(false);
        if (sonic.DECIBELMODE)
        	input2.setMaxStringLength(3);
        else
        	input2.setMaxStringLength(12);
    }

    @Override
	public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
    	if (sonic.ENABLEFREQ)
    		input.textboxKeyTyped(c, i);
    	input2.textboxKeyTyped(c, i);
    }

    @Override
	public void mouseClicked(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	if (sonic.ENABLEFREQ)
    		input.mouseClicked(i, j, k);
    	input2.mouseClicked(i, j, k);
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
    public void actionPerformed(GuiButton button) {
    	ReikaPacketHelper.sendLongPacket(mod_RotaryCraft.packetChannel, 16, sonic, player, vol);
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
	    	freq = 0;
	    	input.deleteFromCursor(-1);
	    	ReikaPacketHelper.sendLongPacket(mod_RotaryCraft.packetChannel, 15, sonic, player, freq);
	    	valid1 = false;
	    }
	    if (!input2.getText().isEmpty() && !(input2.getText().matches("^[0-9 ]+$"))) {
	    	dB = 0;
	    	this.getVolFromdB();
	    	input2.deleteFromCursor(-1);
	    	ReikaPacketHelper.sendLongPacket(mod_RotaryCraft.packetChannel, 16, sonic, player, vol);
	    	valid2 = false;
	    }
	    if (!valid1 && !valid2)
	    	return;
	    //ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
	    //System.out.println(input.getText());
	    if (valid1) {
		    freq = Long.parseLong(input.getText());
		    if (freq >= 0)
		    	ReikaPacketHelper.sendLongPacket(mod_RotaryCraft.packetChannel, 15, sonic, player, freq);
		}
	    if (valid2) {
		    dB = Integer.parseInt(input2.getText());
		    if (dB >= 0) {
		    	this.getVolFromdB();
		    	ReikaPacketHelper.sendLongPacket(mod_RotaryCraft.packetChannel, 16, sonic, player, vol);
		    }
	    }
    }

    public void getVolFromdB() {
    	double d = dB/10D;
    	d = ReikaMathLibrary.doubpow(10, d);
    	//ReikaJavaLibrary.pConsole(d);
    	vol = (long)(d);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        int xo = 0;
        if (sonic.DECIBELMODE)
        	xo = 48;
        //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 4210752);
        fontRenderer.drawString("Sonic Weapon", j+xSize/2-29-xo/24, k+5, 4210752);
        fontRenderer.drawString("Volume:", j+xSize/2-92+xo, k+35, 4210752);
        if (sonic.ENABLEFREQ)
        	fontRenderer.drawString("Frequency:", j+xSize/2-113, k+65, 4210752);
        if (sonic.ENABLEFREQ) {
	        if (!input.isFocused())
	        	fontRenderer.drawString(String.format("%d", sonic.setpitch), j+xSize/2-50, k+65, 0xffffffff);
        }
        if (!input2.isFocused()) {
        	if (sonic.DECIBELMODE)
        		fontRenderer.drawString(String.format("%d", dB), j+xSize/2-50+xo, k+35, 0xffffffff);
        	else
        		fontRenderer.drawString(String.format("%d", sonic.setvolume), j+xSize/2-50+xo, k+35, 0xffffffff);
        }


        if (sonic.DECIBELMODE)
        	fontRenderer.drawString("dB", j+xSize/2+38, k+35, 4210752);
        else
        	fontRenderer.drawString("W/m^2", j+xSize/2+38, k+35, 4210752);
        if (sonic.ENABLEFREQ)
        	fontRenderer.drawString("Hz", j+xSize/2+38, k+65, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i;
        if (sonic.ENABLEFREQ)
        	i = "/Reika/RotaryCraft/Textures/GUI/sonicgui.png";
        else if (sonic.DECIBELMODE)
        	i = "/Reika/RotaryCraft/Textures/GUI/sonicgui3.png";
        else
        	i = "/Reika/RotaryCraft/Textures/GUI/sonicgui2.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        if (sonic.ENABLEFREQ)
        	input.drawTextBox();
        input2.drawTextBox();

        int color = 4210752;
        if (vol > sonic.getMaxVolume())
        	color = 0xff0000;
        //ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", sonic.getVolume()), j+xSize/2+85, k+65, color);
        color = 4210752;
        this.drawPowerTab(j, k);
        if (!sonic.ENABLEFREQ)
        	return;
        if (freq > sonic.getMaxPitch())
        	color = 0xff0000;
        //ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", sonic.getPitch()), j+xSize/2+85, k+35, color);
    }
}
