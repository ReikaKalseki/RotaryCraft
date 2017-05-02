/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerItemCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;

public class GuiItemCannon extends GuiPowerOnlyMachine
{
	private TileEntityItemCannon ica;
	private GuiTextField input;
	private GuiTextField input2;
	private GuiTextField input3;
	private GuiTextField input4; //dim

	private WorldLocation target;

	private int targetDim;
	private int targetX;
	private int targetY;
	private int targetZ;

	public GuiItemCannon(EntityPlayer p5ep, TileEntityItemCannon ItemCannon)
	{
		super(new ContainerItemCannon(p5ep, ItemCannon), ItemCannon);
		ica = ItemCannon;
		ySize = 236;
		ySize = 170;
		target = ica.getTarget();
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		//this.buttonList.add(new GuiButton(0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"));
		input = new GuiTextField(fontRendererObj, j+xSize/2+25, k+26, 46, 16);
		input.setFocused(false);
		input.setMaxStringLength(6);
		input2 = new GuiTextField(fontRendererObj, j+xSize/2+25, k+42, 46, 16);
		input2.setFocused(false);
		input2.setMaxStringLength(6);
		input3 = new GuiTextField(fontRendererObj, j+xSize/2+25, k+58, 46, 16);
		input3.setFocused(false);
		input3.setMaxStringLength(6);
		input4 = new GuiTextField(fontRendererObj, j+xSize/4-30-6, k+50, 26, 16);
		input4.setFocused(false);
		input4.setMaxStringLength(6);
	}

	@Override
	protected void keyTyped(char c, int i){
		super.keyTyped(c, i);
		input.textboxKeyTyped(c, i);
		input2.textboxKeyTyped(c, i);
		input3.textboxKeyTyped(c, i);
		input4.textboxKeyTyped(c, i);
	}

	@Override
	protected void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
		input2.mouseClicked(i, j, k);
		input3.mouseClicked(i, j, k);
		input4.mouseClicked(i, j, k);
	}

	private void sendPacket() {
		target = new WorldLocation(targetDim, targetX, targetY, targetZ);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(24); // 6 ints
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(drops));
			outputStream.writeInt(PacketRegistry.ITEMCANNON.getMinValue());
			outputStream.writeInt(target.dimensionID);
			outputStream.writeInt(target.xCoord);
			outputStream.writeInt(target.yCoord);
			outputStream.writeInt(target.zCoord);
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
		boolean valid4 = true;
		if (input.getText().isEmpty() && input2.getText().isEmpty() && input3.getText().isEmpty() && input4.getText().isEmpty()) {
			return;
		}
		if (input.getText().isEmpty())
			valid1 = false;
		if (input2.getText().isEmpty())
			valid2 = false;
		if (input3.getText().isEmpty())
			valid3 = false;
		if (input4.getText().isEmpty())
			valid4 = false;
		if (!input.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input.getText())) {
			targetX = 0;
			input.deleteFromCursor(-1);
			this.sendPacket();
			valid1 = false;
		}
		if (!input2.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input2.getText())) {
			targetY = 0;
			input2.deleteFromCursor(-1);
			this.sendPacket();
			valid2 = false;
		}
		if (!input3.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input3.getText())) {
			targetZ = 0;
			input3.deleteFromCursor(-1);
			this.sendPacket();
			valid3 = false;
		}
		if (!input4.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input4.getText())) {
			targetDim = 0;
			input4.deleteFromCursor(-1);
			this.sendPacket();
			valid4 = false;
		}
		if (!valid1 && !valid2 && !valid3 && !valid4)
			return;
		if (input.getText().contentEquals("-"))
			valid1 = false;
		if (input2.getText().contentEquals("-"))
			valid2 = false;
		if (input3.getText().contentEquals("-"))
			valid3 = false;
		if (input4.getText().contentEquals("-"))
			valid4 = false;
		if (valid1) {
			targetX = ReikaJavaLibrary.safeIntParse(input.getText());
			this.sendPacket();
		}
		if (valid2) {
			targetY = ReikaJavaLibrary.safeIntParse(input2.getText());
			this.sendPacket();
		}
		if (valid3) {
			targetZ = ReikaJavaLibrary.safeIntParse(input3.getText());
			this.sendPacket();
		}
		if (valid4) {
			targetDim = ReikaJavaLibrary.safeIntParse(input4.getText());
			this.sendPacket();
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

		fontRendererObj.drawString("Target X", xSize/3+10, 18, 4210752);
		fontRendererObj.drawString("Target Y", xSize/3+10, 34, 4210752);
		fontRendererObj.drawString("Target Z", xSize/3+10, 51, 4210752);
		fontRendererObj.drawString("Target Dim", xSize/4-32-6, 26, 4210752);

		if (!input.isFocused())
			fontRendererObj.drawString(String.format("%d", targetX), 125, 18, 0xffffffff);
		if (!input2.isFocused())
			fontRendererObj.drawString(String.format("%d", targetY), 125, 34, 0xffffffff);
		if (!input3.isFocused())
			fontRendererObj.drawString(String.format("%d", targetZ), 125, 50, 0xffffffff);
		if (!input4.isFocused())
			fontRendererObj.drawString(String.format("%d", targetDim), 20, 42, 0xffffffff);
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
		input4.drawTextBox();
	}

	@Override
	protected String getGuiTexture() {
		return "targetgui";
	}
}
