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

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerHeater;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHeater extends GuiMachine
{
    private IInventory upperHeaterInventory;
    private TileEntityHeater heater;
    private GuiTextField input;
    public int temperature;
    private EntityPlayer player;
    int x;
    int y;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public GuiHeater(EntityPlayer player, IInventory par2IInventory, TileEntityHeater te)
    {
        super(new ContainerHeater(player, te), te);
        upperHeaterInventory = player.inventory;
        allowUserInput = false;
        short var3 = 256;
        int var4 = var3 - 108;
        inventoryRows = par2IInventory.getSizeInventory() / 9;
        ySize = var4 + inventoryRows * 18;
        heater = te;
        temperature = te.setTemperature;
    }

    @Override
    public void initGui() {
    	super.initGui();
        int j = (width - xSize) / 2+8;
        int k = (height - ySize) / 2 - 12;
        input = new GuiTextField(fontRenderer, j+xSize/2+40, k+67, 32, 16);
        input.setFocused(false);
        input.setMaxStringLength(4);
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

    @Override
	public void updateScreen() {
    	super.updateScreen();
    	x = Mouse.getX();
    	y = Mouse.getY();
    	if (input.getText().isEmpty()) {
    		return;
    	}
    	if (!(input.getText().matches("^[0-9 ]+$"))) {
    		temperature = 0;
    		input.deleteFromCursor(-1);
    		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 10, heater, player, temperature);
    		return;
    	}
    	temperature = Integer.parseInt(input.getText());
    	if (temperature >= 0)
    		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 10, heater, player, temperature);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRenderer.drawString(this.heater.getMultiValuedName(), 8, 6, 4210752);
        fontRenderer.drawString("Temperature Control:", 26, 59, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal(upperHeaterInventory.getInvName()), 8, ySize - 96 -14, 4210752);
        if (!input.isFocused()) {
        	fontRenderer.drawString(String.format("%d", heater.setTemperature), 140, 59, 0xffffffff);
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        String var4 = "/Reika/RotaryCraft/Textures/GUI/heatergui.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(var4);
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
        //this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        //this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
        input.drawTextBox();

        this.drawPowerTab(var5, var6);
    }

    @Override
	protected void drawPowerTab(int var5, int var6) {
    	String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	mc.renderEngine.bindTexture(var4);
    	this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-20);

    	long frac = (heater.power*29L)/heater.MINPOWER;
    	if (frac > 29)
    		frac = 29;
    	this.drawTexturedModalRect(xSize+var5+5, ySize+var6-162, 0, 0, (int)frac, 4);

    	frac = heater.omega*29L/heater.MINSPEED;
    	if (frac > 29)
    		frac = 29;
    	this.drawTexturedModalRect(xSize+var5+5, ySize+var6-102, 0, 0, (int)frac, 4);

    	frac = heater.torque*29L/heater.MINTORQUE;
    	if (frac > 29)
    		frac = 29;
    	this.drawTexturedModalRect(xSize+var5+5, ySize+var6-42, 0, 0, (int)frac, 4);

    	ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
    	ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+69, 0xff000000);
    	ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+129, 0xff000000);
    	//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", heater.power, heater.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
    }
}
