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

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerCVT;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;

public class GuiCVT extends GuiNonPoweredMachine
{
    private TileEntityAdvancedGear cvt;
	public int ratio;
    //private World worldObj = ModLoader.getMinecraftInstance().theWorld;
    private EntityPlayer player;
    int x;
    int y;
    private GuiTextField input;

    //Make it require more belts (add lots of inv slots) for higher ratios
    //1 for 2x, 1+2 for 4x, 1+2+4 for 8x, 1+2+4+8 for 16x
    // = SUM_1^(n-1){2^i}, for i = ratio (also = ratio-1 belts)
    //Make gui look cool (like connecting spindles with belts)

    public GuiCVT(EntityPlayer player, TileEntityAdvancedGear AdvancedGear)
    {
        super(new ContainerCVT(player, AdvancedGear), AdvancedGear);
        cvt = AdvancedGear;
    	ySize = 237;
    	xSize = 240;
    	this.player = player;
    	ratio = cvt.ratio;
    	if (ratio > cvt.getMaxRatio())
    		ratio = cvt.getMaxRatio();
    	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.ratio));
    }

    @Override
    public void initGui() {
    	super.initGui();
    	buttonList.clear();
        int j = (width - xSize) / 2+8;
        int k = (height - ySize) / 2 - 12;
        if (ratio > 0)
        	buttonList.add(new GuiButton(0, j+xSize/2-6, -1+k+64, 80, 20, "Speed"));
        else
        	buttonList.add(new GuiButton(0, j+xSize/2-6, -1+k+64, 80, 20, "Torque"));
        input = new GuiTextField(fontRenderer, j+xSize/2+24, k+39, 26, 16);
        input.setFocused(false);
        input.setMaxStringLength(3);
    }

    @Override
	public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
    	input.textboxKeyTyped(c, i);
    }

    @Override
	public void mouseClicked(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	input.mouseClicked(i, j, k);
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
    	if (ratio > cvt.getMaxRatio())
    		ratio = cvt.getMaxRatio();
    	ratio = -ratio;
        ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 11, cvt, player, ratio);

         	super.updateScreen();
         	x = Mouse.getX();
         	y = Mouse.getY();
         	this.initGui();
    }

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	x = Mouse.getX();
    	y = Mouse.getY();

    	if (input.getText().isEmpty())
    		return;
    	if (!(input.getText().matches("^[0-9 ]+$"))) {
    		ratio = 1;
    		input.deleteFromCursor(-1);
    		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 11, cvt, player, ratio);
    		return;
    	}
    	ratio = Integer.parseInt(input.getText());
    	if (ratio != 0)
    		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 11, cvt, player, ratio);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        fontRenderer.drawString("Continuously Variable Transmission", xSize/2-87, 5, 4210752);
        fontRenderer.drawString("Belt Ratio:", xSize/2-32, 31, 4210752);
        if (!input.isFocused()) {
        	fontRenderer.drawString(String.format("%d", Math.abs(cvt.ratio)), xSize/2+36, 31, 0xffffffff);
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String i = "/Reika/RotaryCraft/Textures/GUI/cvtgui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        input.drawTextBox();
        if (ratio > cvt.getMaxRatio())
        	ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", cvt.getMaxRatio()), j+xSize/2+88, k+31, 0xff0000);
        else if (ratio == 0)
        	ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, "(1)", j+xSize/2+88, k+31, 0xff0000);
        else
        	ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", Math.abs(cvt.ratio)), j+xSize/2+88, k+31, 4210752);
    }

}
