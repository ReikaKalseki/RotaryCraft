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
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;
import Reika.RotaryCraft.Containers.ContainerCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

public class GuiCannon extends GuiPowerOnlyMachine
{
	private TileEntityLaunchCannon tnt;
	private GuiTextField input;
	private GuiTextField input2;
	private GuiTextField input3;
	private GuiTextField input4;

	private double phi;
	private double theta;
	private double phid;
	private double thetad;
	private int velocity;
	private int[] target = new int[3];
	private int fuse;

	int x;
	int y;

	private boolean targetMode;

	EntityPlayer player;

	public GuiCannon(EntityPlayer p5ep, TileEntityLaunchCannon Cannon)
	{
		super(new ContainerCannon(p5ep, Cannon), Cannon);
		tnt = Cannon;
		phi = tnt.phi;
		theta = tnt.theta;
		velocity = tnt.velocity;
		targetMode = tnt.targetMode;
		if (targetMode) {
			ySize = 170;
			target = tnt.target;
		}
		else {
			xSize = 212;
			ySize = 236;
		}
		thetad = theta;
		phid = phi;
		theta = ReikaPhysicsHelper.degToRad(theta);
		phi = ReikaPhysicsHelper.degToRad(phi);
		ep = p5ep;
		fuse = tnt instanceof TileEntityTNTCannon ? ((TileEntityTNTCannon)tnt).selectedFuse : 0;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		//this.buttonList.add(new GuiButton(0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"));
		if (targetMode) {
			input = new GuiTextField(fontRendererObj, j+xSize/2, k+26, 46, 16);
			input.setFocused(false);
			input.setMaxStringLength(6);
			input2 = new GuiTextField(fontRendererObj, j+xSize/2, k+42, 46, 16);
			input2.setFocused(false);
			input2.setMaxStringLength(6);
			input3 = new GuiTextField(fontRendererObj, j+xSize/2, k+58, 46, 16);
			input3.setFocused(false);
			input3.setMaxStringLength(6);

			//offscreen
			input4 = new GuiTextField(fontRendererObj, -100, -100, 0, 0);
			input4.setFocused(false);
			input4.setMaxStringLength(0);
		}
		else {
			input = new GuiTextField(fontRendererObj, j+xSize/2+22+18, k+104, 26, 16);
			input.setFocused(false);
			input.setMaxStringLength(3);
			input2 = new GuiTextField(fontRendererObj, j+xSize/2-65-18, k+104, 26, 16);
			input2.setFocused(false);
			input2.setMaxStringLength(3);
			input3 = new GuiTextField(fontRendererObj, j+xSize/2+22+18, k+124, 26, 16);
			input3.setFocused(false);
			input3.setMaxStringLength(3);
			input4 = new GuiTextField(fontRendererObj, j+xSize/2-49, k+124, 26, 16);
			input4.setFocused(false);
			input4.setMaxStringLength(3);
		}
	}
	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);
		input.textboxKeyTyped(c, i);
		input2.textboxKeyTyped(c, i);
		input3.textboxKeyTyped(c, i);
		input4.textboxKeyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
		input2.mouseClicked(i, j, k);
		input3.mouseClicked(i, j, k);
		input4.mouseClicked(i, j, k);
	}

	public void sendPacket(int a) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(32); // 8 ints
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(drops));
			int b = 0;
			if (tile instanceof TileEntityLaunchCannon)
				b = a+PacketRegistry.CANNON.getMinValue();
			if (tile instanceof TileEntityItemCannon)
				b = a+PacketRegistry.ITEMCANNON.getMinValue();
			//ReikaJavaLibrary.pConsole("Sending packet number "+b+" Type "+PacketRegistry.getEnum(b));
			outputStream.writeInt(b);
			if (targetMode) {
				outputStream.writeInt(1);
				if (a == 0)
					outputStream.writeInt(target[0]);
				if (a == 1)
					outputStream.writeInt(target[1]);
				if (a == 2)
					outputStream.writeInt(target[2]);
				if (a == 3)
					outputStream.writeInt(fuse);
			}
			else {
				outputStream.writeInt(0);
				if (a == 0)
					outputStream.writeInt((int)phid);
				if (a == 1)
					outputStream.writeInt((int)thetad);
				if (a == 2)
					outputStream.writeInt(velocity);
				if (a == 3)
					outputStream.writeInt(fuse);
			}
			outputStream.writeInt(tile.xCoord);
			outputStream.writeInt(tile.yCoord);
			outputStream.writeInt(tile.zCoord);

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
		x = Mouse.getX();
		y = Mouse.getY();
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
			if (targetMode)
				target[0] = 0;
			else
				phid = 0;
			input.deleteFromCursor(-1);
			this.sendPacket(0);
			valid1 = false;
		}
		if (!input2.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input2.getText())) {
			if (targetMode)
				target[1] = 0;
			else
				thetad = 0;
			input2.deleteFromCursor(-1);
			this.sendPacket(1);
			valid2 = false;
		}
		if (!input3.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input3.getText())) {
			if (targetMode)
				target[2] = 0;
			else
				velocity = 0;
			input3.deleteFromCursor(-1);
			this.sendPacket(2);
			valid3 = false;
		}
		if (!input4.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input4.getText())) {
			fuse = 0;
			input4.deleteFromCursor(-1);
			this.sendPacket(3);
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
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
		//System.out.println(input.getText());
		if (valid1) {
			if (targetMode) {
				target[0] = ReikaJavaLibrary.safeIntParse(input.getText());
				this.sendPacket(0);
			}
			else {
				phid = ReikaJavaLibrary.safeIntParse(input.getText());
				while (phid > 360) {
					phid -= 360;
				}
				if (phid >= 0)
					this.sendPacket(0);
			}
		}
		if (valid2) {
			if (targetMode) {
				target[1] = ReikaJavaLibrary.safeIntParse(input2.getText());
				this.sendPacket(1);
			}
			else {
				thetad = ReikaJavaLibrary.safeIntParse(input2.getText());
				if (thetad > 90) {
					thetad = 90;
				}
				if (thetad >= 0)
					this.sendPacket(1);
			}
		}
		if (valid3) {
			if (targetMode) {
				target[2] = ReikaJavaLibrary.safeIntParse(input3.getText());
				this.sendPacket(2);
			}
			else {
				velocity = ReikaJavaLibrary.safeIntParse(input3.getText());
				if (velocity < 0) {
					velocity = 0;
				}
				if (velocity >= 0)
					this.sendPacket(2);
			}
		}
		if (valid4) {
			fuse = ReikaJavaLibrary.safeIntParse(input4.getText());
			this.sendPacket(3);
		}
		if (targetMode)
			return;
		theta = ReikaPhysicsHelper.degToRad(thetad);
		phi = ReikaPhysicsHelper.degToRad(phid);
	}

	@Override
	public boolean labelInventory() {
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

		super.drawGuiContainerForegroundLayer(a, b);

		if (targetMode) {
			fontRendererObj.drawString("Target X", xSize/3-20, 18, 4210752);
			fontRendererObj.drawString("Target Y", xSize/3-20, 34, 4210752);
			fontRendererObj.drawString("Target Z", xSize/3-20, 51, 4210752);
		}
		else {
			fontRendererObj.drawString("Launch Angle", xSize/3-46-12, 80, 4210752);
			fontRendererObj.drawString("Compass Angle", xSize/3+36+24, 80, 4210752);
			fontRendererObj.drawString("Velocity", xSize/3-26+24+44, 116, 4210752);
			if (tnt instanceof TileEntityTNTCannon)
				fontRendererObj.drawString("Fuse Time", xSize/3-26-32, 116, 4210752);
		}

		if (targetMode) {
			if (!input.isFocused())
				fontRendererObj.drawString(String.format("%d", target[0]), 100, 18, 0xffffffff);
			if (!input2.isFocused())
				fontRendererObj.drawString(String.format("%d", target[1]), 100, 34, 0xffffffff);
			if (!input3.isFocused())
				fontRendererObj.drawString(String.format("%d", target[2]), 100, 50, 0xffffffff);
		}
		else {
			if (!input.isFocused())
				fontRendererObj.drawString(String.format("%d", tnt.phi), 122+36, 96, 0xffffffff);
			if (!input2.isFocused())
				fontRendererObj.drawString(String.format("%d", tnt.theta), 35, 96, 0xffffffff);
			if (!input3.isFocused())
				fontRendererObj.drawString(String.format("%d", tnt.velocity), 122+36, 116, 0xffffffff);
			if (tnt instanceof TileEntityTNTCannon)
				if (!input4.isFocused())
					fontRendererObj.drawString(String.format("%d", fuse), 122-54, 116, 0xffffffff);
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

		input.drawTextBox();
		input2.drawTextBox();
		input3.drawTextBox();
		if (tnt instanceof TileEntityTNTCannon)
			input4.drawTextBox();

		if (!targetMode) {
			this.drawGrid(j, k);
			this.drawAngles(j, k);
		}
	}

	private void drawAngles(int j, int k) {
		int basex1 = 16+j;
		int basey1 = 73+k;
		int x2 = basex1+(int)(57*Math.cos(theta));
		int y2 = basey1-(int)(57*Math.sin(theta));
		int basex3 = 131+j+36;
		int basey3 = 45+k;/*
    	if (phid >= 90 && phid <= 270) {
    		basey3--;
    	}
       	if (phid >= 180 && phid <= 360) {
    		basex3++;
    	}*/
		int x4 = basex3+(int)(30*Math.cos(theta)*Math.cos(phi));
		int y4 = basey3+(int)(30*Math.cos(theta)*Math.sin(phi));

		api.drawLine(basex1, basey1, x2, y2, 0xffffffff);
		api.drawLine(basex3, basey3, x4, y4, 0xffffffff);
	}

	private void drawGrid(int j, int k) {
		int color = 0x008800;
		int basex1 = 16+j;
		int basey1 = 73+k;
		int basex3 = 131+j+36;
		int basey3 = 45+k;
		for (int i = 0; i < 7; i++) {
			int size = 57;
			if (i == 1 || i == 5)
				size = 60;
			if (i == 2 || i == 4)
				size = 66;
			if (i == 3)
				size = 80;
			int x2 = basex1+(int)(size*Math.cos(ReikaPhysicsHelper.degToRad(i*15)));
			int y2 = basey1-(int)(size*Math.sin(ReikaPhysicsHelper.degToRad(i*15)));
			api.drawLine(basex1, basey1, x2, y2, color);
		}
		for (int i = 0; i < 8; i++) {
			int size = 30;
			int x4 = basex3-18-88+xSize/2+(int)(size*Math.cos(ReikaPhysicsHelper.degToRad(i*45)));
			int y4 = basey3-118+ySize/2-(int)(size*Math.sin(ReikaPhysicsHelper.degToRad(i*45)));
			api.drawLine(basex3, basey3, x4, y4, color);
		}
		for (int i = 0; i < 3; i++) {
			int size = 30;
			api.drawCircle(basex3, basey3, (int)(size*Math.cos(ReikaPhysicsHelper.degToRad(i*30))), color);
		}
	}

	@Override
	public String getGuiTexture() {
		if (targetMode)
			return "targetgui";
		else
			return "cannongui";
	}
}