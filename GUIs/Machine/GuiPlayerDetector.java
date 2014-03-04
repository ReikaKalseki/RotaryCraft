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

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;

public class GuiPlayerDetector extends GuiNonPoweredMachine
{
	private TileEntityPlayerDetector playerdetector;
	public int range;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;
	private GuiTextField input;

	public GuiPlayerDetector(EntityPlayer p5ep, TileEntityPlayerDetector PlayerDetector)
	{
		super(new CoreContainer(p5ep, PlayerDetector), PlayerDetector);
		playerdetector = PlayerDetector;
		ySize = 46;
		ep = p5ep;
		range = playerdetector.selectedrange;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.range));
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		input = new GuiTextField(fontRenderer, j+xSize/2-6, k+33, 26, 16);
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

	@Override
	public void updateScreen() {
		super.updateScreen();
		x = Mouse.getX();
		y = Mouse.getY();
		if (input.getText().isEmpty()) {
			return;
		}
		if (!(input.getText().matches("^[0-9 ]+$"))) {
			range = 0;
			input.deleteFromCursor(-1);
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.DETECTOR.getMinValue(), playerdetector, range);
			return;
		}
		range = ReikaJavaLibrary.safeIntParse(input.getText());
		if (range >= 0)
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.DETECTOR.getMinValue(), playerdetector, range);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		fontRenderer.drawString("Detection Range:", xSize/2-82, 25, 4210752);
		if (!input.isFocused()) {
			fontRenderer.drawString(String.format("%d", playerdetector.selectedrange), xSize/2+6, 25, 0xffffffff);
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
		int color = 4210752;
		if (range > playerdetector.getMaxRange())
			color = 0xff0000;
		ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", playerdetector.getRange()), j+xSize/2+58, k+25, color);
	}

	@Override
	public String getGuiTexture() {
		return "playerdetectorgui";
	}

}
