/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerHeater;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;

public class GuiHeater extends GuiMachine
{
	private IInventory upperHeaterInventory;
	private TileEntityHeater heater;
	private GuiTextField input;
	public int temperature;

	int x;
	int y;

	private int inventoryRows = 0;

	public GuiHeater(EntityPlayer p5ep, IInventory par2IInventory, TileEntityHeater te)
	{
		super(new ContainerHeater(p5ep, te), te);
		ep = p5ep;
		upperHeaterInventory = ep.inventory;
		allowUserInput = false;
		short var3 = 256;
		int var4 = var3 - 108;
		inventoryRows = par2IInventory.getSizeInventory() / 9;
		ySize = var4 + inventoryRows * 18;
		heater = te;
		temperature = te.setTemperature;
		ySize = 167;
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
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.HEATER.getMinValue(), heater, temperature);
			return;
		}
		temperature = ReikaJavaLibrary.safeIntParse(input.getText());
		if (temperature >= 0)
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.HEATER.getMinValue(), heater, temperature);
		heater.setTemperature = temperature;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		api.drawCenteredStringNoShadow(fontRenderer, tile.getMultiValuedName(), xSize/2, 5, 4210752);
		if (tile instanceof IInventory)
			fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) +3, 4210752);

		fontRenderer.drawString("Temperature Control:", 26, 59, 4210752);
		if (!input.isFocused()) {
			fontRenderer.drawString(String.format("%d", heater.setTemperature), 140, 59, 0xffffffff);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		input.drawTextBox();
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, ySize-20);
		this.drawTexturedModalRect(xSize+var5, var6+4+ySize-20, 0, 157, 42, 6);

		long frac = (heater.power*29L)/heater.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-145, 0, 0, (int)frac, 4);

		frac = heater.omega*29L/heater.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-85, 0, 0, (int)frac, 4);

		frac = heater.torque*29L/heater.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-25, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		api.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+69, 0xff000000);
		api.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", heater.power, heater.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "heatergui";
	}
}
