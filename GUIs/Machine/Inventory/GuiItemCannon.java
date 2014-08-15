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

import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerItemCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

public class GuiItemCannon extends GuiPowerOnlyMachine
{
	private TileEntityItemCannon ica;
	private GuiTextField input;
	private GuiTextField input2;
	private GuiTextField input3;

	private int[] target = new int[3];

	int x;
	int y;

	public GuiItemCannon(EntityPlayer p5ep, TileEntityItemCannon ItemCannon)
	{
		super(new ContainerItemCannon(p5ep, ItemCannon), ItemCannon);
		ica = ItemCannon;
		ySize = 236;
		ySize = 170;
		target = ica.target;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		//this.buttonList.add(new GuiButton(0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"));
		input = new GuiTextField(fontRendererObj, j+xSize/2, k+26, 46, 16);
		input.setFocused(false);
		input.setMaxStringLength(6);
		input2 = new GuiTextField(fontRendererObj, j+xSize/2, k+42, 46, 16);
		input2.setFocused(false);
		input2.setMaxStringLength(6);
		input3 = new GuiTextField(fontRendererObj, j+xSize/2, k+58, 46, 16);
		input3.setFocused(false);
		input3.setMaxStringLength(6);
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);
		input.textboxKeyTyped(c, i);
		input2.textboxKeyTyped(c, i);
		input3.textboxKeyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
		input2.mouseClicked(i, j, k);
		input3.mouseClicked(i, j, k);
	}

	public void sendPacket(int a) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(24); // 6 ints
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(drops));
			outputStream.writeInt(a);
			if (a == PacketRegistry.ITEMCANNON.getMinValue())
				outputStream.writeInt(target[0]);
			if (a == PacketRegistry.ITEMCANNON.getMinValue()+1)
				outputStream.writeInt(target[1]);
			if (a == PacketRegistry.ITEMCANNON.getMinValue()+2)
				outputStream.writeInt(target[2]);
			outputStream.writeInt(ica.xCoord);
			outputStream.writeInt(ica.yCoord);
			outputStream.writeInt(ica.zCoord);

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, bos);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		boolean valid1 = true;
		boolean valid2 = true;
		boolean valid3 = true;
		x = Mouse.getX();
		y = Mouse.getY();
		if (input.getText().isEmpty() && input2.getText().isEmpty() && input3.getText().isEmpty()) {
			return;
		}
		if (input.getText().isEmpty())
			valid1 = false;
		if (input2.getText().isEmpty())
			valid2 = false;
		if (input3.getText().isEmpty())
			valid3 = false;
		if (!input.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input.getText())) {
			target[0] = 0;
			input.deleteFromCursor(-1);
			this.sendPacket(PacketRegistry.ITEMCANNON.getMinValue());
			valid1 = false;
		}
		if (!input2.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input2.getText())) {
			target[1] = 0;
			input2.deleteFromCursor(-1);
			this.sendPacket(PacketRegistry.ITEMCANNON.getMinValue()+1);
			valid2 = false;
		}
		if (!input3.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input3.getText())) {
			target[2] = 0;
			input3.deleteFromCursor(-1);
			this.sendPacket(PacketRegistry.ITEMCANNON.getMinValue()+2);
			valid2 = false;
		}
		if (!valid1 && !valid2 && !valid3)
			return;
		if (input.getText().contentEquals("-"))
			valid1 = false;
		if (input2.getText().contentEquals("-"))
			valid2 = false;
		if (input3.getText().contentEquals("-"))
			valid3 = false;
		if (valid1) {
			target[0] = ReikaJavaLibrary.safeIntParse(input.getText());
			this.sendPacket(PacketRegistry.ITEMCANNON.getMinValue());
		}
		if (valid2) {
			target[1] = ReikaJavaLibrary.safeIntParse(input2.getText());
			this.sendPacket(PacketRegistry.ITEMCANNON.getMinValue()+1);
		}
		if (valid3) {
			target[2] = ReikaJavaLibrary.safeIntParse(input3.getText());
			this.sendPacket(PacketRegistry.ITEMCANNON.getMinValue()+2);
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

		fontRendererObj.drawString("Target X", xSize/3-20, 18, 4210752);
		fontRendererObj.drawString("Target Y", xSize/3-20, 34, 4210752);
		fontRendererObj.drawString("Target Z", xSize/3-20, 51, 4210752);

		if (!input.isFocused())
			fontRendererObj.drawString(String.format("%d", target[0]), 100, 18, 0xffffffff);
		if (!input2.isFocused())
			fontRendererObj.drawString(String.format("%d", target[1]), 100, 34, 0xffffffff);
		if (!input3.isFocused())
			fontRendererObj.drawString(String.format("%d", target[2]), 100, 50, 0xffffffff);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		input.drawTextBox();
		input2.drawTextBox();
		input3.drawTextBox();
	}

	@Override
	public String getGuiTexture() {
		return "targetgui";
	}
}