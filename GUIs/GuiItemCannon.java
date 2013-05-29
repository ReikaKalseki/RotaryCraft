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
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;

public class GuiItemCannon extends GuiPowerOnlyMachine
{
	private TileEntityItemCannon tile;
	private GuiTextField input;
	private GuiTextField input2;
	private GuiTextField input3;

	private int[] target = new int[3];

	int x;
	int y;

	EntityPlayer player;

	public GuiItemCannon(EntityPlayer player, TileEntityItemCannon ItemCannon)
	{
		super(new ContainerItemCannon(player, ItemCannon), ItemCannon);
		tile = ItemCannon;
		ySize = 236;
		ySize = 170;
		target = tile.target;
		this.player = player;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		//this.buttonList.add(new GuiButton(0, j+xSize/2-48, -1+k+32, 80, 20, "Trajectory"));
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
		ByteArrayOutputStream bos = new ByteArrayOutputStream(20); // 6 ints
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(drops));
			outputStream.writeInt(a);
			if (a == 32)
				outputStream.writeInt(target[0]);
			if (a == 33)
				outputStream.writeInt(target[1]);
			if (a == 34)
				outputStream.writeInt(target[2]);
			outputStream.writeInt(tile.xCoord);
			outputStream.writeInt(tile.yCoord);
			outputStream.writeInt(tile.zCoord);

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
			target[0] = 0;
			input.deleteFromCursor(-1);
			this.sendPacket(32);
			valid1 = false;
		}
		if (!input2.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input2.getText())) {
			target[1] = 0;
			input2.deleteFromCursor(-1);
			this.sendPacket(33);
			valid2 = false;
		}
		if (!input3.getText().isEmpty() && !ReikaJavaLibrary.isValidInteger(input3.getText())) {
			target[2] = 0;
			input3.deleteFromCursor(-1);
			this.sendPacket(34);
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
			target[0] = Integer.parseInt(input.getText());
			this.sendPacket(32);
		}
		if (valid2) {
			target[1] = Integer.parseInt(input2.getText());
			this.sendPacket(33);
		}
		if (valid3) {
			target[2] = Integer.parseInt(input3.getText());
			this.sendPacket(34);
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
		//fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), xSize-58, (ySize - 96) + 2, 4210752);
		fontRenderer.drawString(tile.getMultiValuedName(), xSize/3, 4, 4210752);

		fontRenderer.drawString("Target X", xSize/3-20, 18, 4210752);
		fontRenderer.drawString("Target Y", xSize/3-20, 34, 4210752);
		fontRenderer.drawString("Target Z", xSize/3-20, 51, 4210752);

		if (!input.isFocused())
			fontRenderer.drawString(String.format("%d", target[0]), 100, 18, 0xffffffff);
		if (!input2.isFocused())
			fontRenderer.drawString(String.format("%d", target[1]), 100, 34, 0xffffffff);
		if (!input3.isFocused())
			fontRenderer.drawString(String.format("%d", target[2]), 100, 50, 0xffffffff);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		String i;
		i = "/Reika/RotaryCraft/Textures/GUI/targetgui.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		input.drawTextBox();
		input2.drawTextBox();
		input3.drawTextBox();

		this.drawPowerTab(j, k);
	}
}
