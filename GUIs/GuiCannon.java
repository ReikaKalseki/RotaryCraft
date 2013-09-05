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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.input.Mouse;

import Reika.DragonAPI.Auxiliary.PacketTypes;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Base.TileEntityLaunchCannon;
import Reika.RotaryCraft.Containers.ContainerCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class GuiCannon extends GuiPowerOnlyMachine
{
	private TileEntityLaunchCannon tnt;
	private GuiTextField input;
	private GuiTextField input2;
	private GuiTextField input3;

	private double phi;
	private double theta;
	private double phid;
	private double thetad;
	private int velocity;
	private int[] target = new int[3];

	int x;
	int y;

	private boolean targetMode;

	EntityPlayer player;

	public GuiCannon(EntityPlayer p5ep, TileEntityLaunchCannon Cannon)
	{
		super(new ContainerCannon(p5ep, Cannon), Cannon);
		tnt = Cannon;
		ySize = 236;
		phi = tnt.phi;
		theta = tnt.theta;
		velocity = tnt.velocity;
		targetMode = tnt.targetMode;
		if (targetMode) {
			ySize = 170;
			target = tnt.target;
		}
		thetad = theta;
		phid = phi;
		theta = ReikaPhysicsHelper.degToRad(theta);
		phi = ReikaPhysicsHelper.degToRad(phi);
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		//this.buttonList.add(new GuiButton(0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"));
		if (targetMode) {
			input = new GuiTextField(fontRenderer, j+xSize/2, k+26, 46, 16);
			input.setFocused(false);
			input.setMaxStringLength(6);
			input2 = new GuiTextField(fontRenderer, j+xSize/2, k+42, 46, 16);
			input2.setFocused(false);
			input2.setMaxStringLength(6);
			input3 = new GuiTextField(fontRenderer, j+xSize/2, k+58, 46, 16);
			input3.setFocused(false);
			input3.setMaxStringLength(6);
		}
		else {
			input = new GuiTextField(fontRenderer, j+xSize/2+22, k+104, 26, 16);
			input.setFocused(false);
			input.setMaxStringLength(3);
			input2 = new GuiTextField(fontRenderer, j+xSize/2-65, k+104, 26, 16);
			input2.setFocused(false);
			input2.setMaxStringLength(3);
			input3 = new GuiTextField(fontRenderer, j+xSize/2+22, k+124, 26, 16);
			input3.setFocused(false);
			input3.setMaxStringLength(3);
		}
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
			outputStream.writeInt(PacketTypes.DATA.ordinal());
			int b = 0;
			if (tile instanceof TileEntityTNTCannon)
				b = a+PacketRegistry.CANNON.getMinValue();
			if (tile instanceof TileEntityItemCannon)
				b = a+PacketRegistry.ITEMCANNON.getMinValue();
			ReikaJavaLibrary.pConsole("Sending packet number "+b+" Type "+PacketRegistry.getEnum(b));
			outputStream.writeInt(b);
			if (targetMode) {
				outputStream.writeInt(1);
				if (a == 12)
					outputStream.writeInt(target[0]);
				if (a == 13)
					outputStream.writeInt(target[1]);
				if (a == 14)
					outputStream.writeInt(target[2]);
			}
			else {
				outputStream.writeInt(0);
				if (a == 12)
					outputStream.writeInt((int)phid);
				if (a == 13)
					outputStream.writeInt((int)thetad);
				if (a == 14)
					outputStream.writeInt(velocity);
			}
			outputStream.writeInt(tnt.xCoord);
			outputStream.writeInt(tnt.yCoord);
			outputStream.writeInt(tnt.zCoord);

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "RotaryCraftData";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER) {
			// We are on the server side.
			EntityPlayerMP player2 = (EntityPlayerMP) player;
		} else if (side == Side.CLIENT) {
			// We are on the client side.
			EntityClientPlayerMP player2 = (EntityClientPlayerMP) player;
			PacketDispatcher.sendPacketToServer(packet);
		} else {
			// We are on the Bukkit server.
		}
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
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
		//System.out.println(input.getText());
		if (valid1) {
			if (targetMode) {
				target[0] = Integer.parseInt(input.getText());
				this.sendPacket(0);
			}
			else {
				phid = Integer.parseInt(input.getText());
				while (phid > 360) {
					phid -= 360;
				}
				if (phid >= 0)
					this.sendPacket(0);
			}
		}
		if (valid2) {
			if (targetMode) {
				target[1] = Integer.parseInt(input2.getText());
				this.sendPacket(1);
			}
			else {
				thetad = Integer.parseInt(input2.getText());
				if (thetad > 90) {
					thetad = 90;
				}
				if (thetad >= 0)
					this.sendPacket(1);
			}
		}
		if (valid3) {
			if (targetMode) {
				target[2] = Integer.parseInt(input3.getText());
				this.sendPacket(2);
			}
			else {
				velocity = Integer.parseInt(input3.getText());
				if (velocity < 0) {
					velocity = 0;
				}
				if (velocity >= 0)
					this.sendPacket(2);
			}
		}
		if (targetMode)
			return;
		theta = ReikaPhysicsHelper.degToRad(thetad);
		phi = ReikaPhysicsHelper.degToRad(phid);
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
			fontRenderer.drawString("Target X", xSize/3-20, 18, 4210752);
			fontRenderer.drawString("Target Y", xSize/3-20, 34, 4210752);
			fontRenderer.drawString("Target Z", xSize/3-20, 51, 4210752);
		}
		else {
			fontRenderer.drawString("Launch Angle", xSize/3-46, 80, 4210752);
			fontRenderer.drawString("Compass Angle", xSize/3+36, 80, 4210752);
			fontRenderer.drawString("Launch Velocity", xSize/3-26, 116, 4210752);
		}

		if (targetMode) {
			if (!input.isFocused())
				fontRenderer.drawString(String.format("%d", target[0]), 100, 18, 0xffffffff);
			if (!input2.isFocused())
				fontRenderer.drawString(String.format("%d", target[1]), 100, 34, 0xffffffff);
			if (!input3.isFocused())
				fontRenderer.drawString(String.format("%d", target[2]), 100, 50, 0xffffffff);
		}
		else {
			if (!input.isFocused())
				fontRenderer.drawString(String.format("%d", tnt.phi), 122, 96, 0xffffffff);
			if (!input2.isFocused())
				fontRenderer.drawString(String.format("%d", tnt.theta), 35, 96, 0xffffffff);
			if (!input3.isFocused())
				fontRenderer.drawString(String.format("%d", tnt.velocity), 122, 116, 0xffffffff);
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
		int basex3 = 131+j;
		int basey3 = 45+k;/*
    	if (phid >= 90 && phid <= 270) {
    		basey3--;
    	}
       	if (phid >= 180 && phid <= 360) {
    		basex3++;
    	}*/
		int x4 = basex3+(int)(30*Math.cos(theta)*Math.cos(phi));
		int y4 = basey3+(int)(30*Math.cos(theta)*Math.sin(phi));

		ReikaGuiAPI.instance.drawLine(basex1, basey1, x2, y2, 0xffffffff);
		ReikaGuiAPI.instance.drawLine(basex3, basey3, x4, y4, 0xffffffff);
	}

	private void drawGrid(int j, int k) {
		int color = 0x008800;
		int basex1 = 16+j;
		int basey1 = 73+k;
		int basex3 = 131+j;
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
			ReikaGuiAPI.instance.drawLine(basex1, basey1, x2, y2, color);
		}
		for (int i = 0; i < 8; i++) {
			int size = 30;
			int x4 = basex3-88+xSize/2+(int)(size*Math.cos(ReikaPhysicsHelper.degToRad(i*45)));
			int y4 = basey3-118+ySize/2-(int)(size*Math.sin(ReikaPhysicsHelper.degToRad(i*45)));
			ReikaGuiAPI.instance.drawLine(basex3, basey3, x4, y4, color);
		}
		for (int i = 0; i < 3; i++) {
			int size = 30;
			ReikaGuiAPI.instance.drawCircle(basex3, basey3, (int)(size*Math.cos(ReikaPhysicsHelper.degToRad(i*30))), color);
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
