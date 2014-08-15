/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiSpawnerController extends GuiPowerOnlyMachine
{

	private int timer;
	private boolean disabled;

	private TileEntitySpawnerController spawnercontroller;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;
	private GuiTextField input;
	boolean hasPower;

	public GuiSpawnerController(EntityPlayer p5ep, TileEntitySpawnerController spw)
	{
		super(new CoreContainer(p5ep, spw), spw);
		spawnercontroller = spw;
		ySize = 75;
		ep = p5ep;
		timer = spawnercontroller.setDelay;
		disabled = spawnercontroller.disable;
		hasPower = (spawnercontroller.power >= spawnercontroller.machine.getMinPower());
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		if (hasPower) {
			buttonList.add(new GuiButton(0, j+xSize/2-48, -1+k+32, 80, 20, "Disable/Enable"));
			input = new GuiTextField(fontRendererObj, j+xSize/2-7, k+59, 26, 16);
			input.setFocused(false);
			input.setMaxStringLength(3);
		}
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);
		if (hasPower)
			input.textboxKeyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		if (hasPower)
			input.mouseClicked(i, j, k);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id == 0) {
			if (spawnercontroller.disable)
				disabled = false;
			else
				disabled = true;
		}
		int dat;
		if (disabled)
			dat = -1;
		else
			dat = timer;
		ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.SPAWNER.getMinValue(), spawnercontroller, dat);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		x = Mouse.getX();
		y = Mouse.getY();
		if (hasPower) {
			if (input.getText().isEmpty()) {
				return;
			}
			if (!(input.getText().matches("^[0-9 ]+$"))) {
				timer = spawnercontroller.BASEDELAY;
				input.deleteFromCursor(-1);
				int dat;
				if (disabled)
					dat = -1;
				else
					dat = timer;
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.SPAWNER.getMinValue(), spawnercontroller, dat);
				return;
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
			//System.out.println(input.getText());
			timer = ReikaJavaLibrary.safeIntParse(input.getText());
			int dat;
			if (disabled)
				dat = -1;
			else
				dat = timer;
			if (timer >= 0)
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.SPAWNER.getMinValue(), spawnercontroller, dat);
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (hasPower) {
			int color = 4210752;
			if (disabled)
				color = 0xcccccc;
			fontRendererObj.drawString("Spawn Delay:", xSize/2-64, 51, color);
			if (!input.isFocused() && !disabled) {
				fontRendererObj.drawString(String.format("%d", spawnercontroller.setDelay), xSize/2+5, 51, 0xffffffff);
			}
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		if (!hasPower) {
			String i = "/Reika/RotaryCraft/Textures/GUI/spawnercontrollergui.png";
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ReikaTextureHelper.bindTexture(RotaryCraft.class, i);
			this.drawTexturedModalRect(j, k, 0, ySize, xSize, ySize);
		}

		if (hasPower) {
			if (!disabled)
				input.drawTextBox();
			int color = 4210752;
			if (timer < spawnercontroller.getOperationTime())
				color = 0xff0000;
			if (disabled) {
				color = 0xaaaaaa;
				ImagedGuiButton.drawCenteredStringNoShadow(fontRendererObj, "Infinity", j+xSize/2+28, k+51, color);
			}
			else
				ImagedGuiButton.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", spawnercontroller.getDelay()), j+xSize/2+58, k+51, color);
		}
	}

	@Override
	public String getGuiTexture() {
		return "spawnercontrollergui";
	}

}