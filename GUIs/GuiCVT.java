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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerCVT;
import Reika.RotaryCraft.Registry.EnumPackets;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;

public class GuiCVT extends GuiNonPoweredMachine
{
	private TileEntityAdvancedGear cvt;
	public int ratio;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;
	private GuiTextField input;

	//Make gui look cool (like connecting spindles with belts)

	public GuiCVT(EntityPlayer p5ep, TileEntityAdvancedGear AdvancedGear)
	{
		super(new ContainerCVT(p5ep, AdvancedGear), AdvancedGear);
		cvt = AdvancedGear;
		ySize = 237;
		xSize = 240;
		ep = p5ep;
		ratio = cvt.ratio;
		if (ratio > cvt.getMaxRatio())
			ratio = cvt.getMaxRatio();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.ratio));
	}

	@Override
	public void initGui() {
		super.initGui();
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

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (ratio > cvt.getMaxRatio())
			ratio = cvt.getMaxRatio();
		ratio = -ratio;
		if (button.id == 0)
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, EnumPackets.CVT.getMinValue(), cvt, ep, ratio);

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
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, EnumPackets.CVT.getMinValue(), cvt, ep, ratio);
			return;
		}
		ratio = Integer.parseInt(input.getText());
		if (ratio != 0)
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, EnumPackets.CVT.getMinValue(), cvt, ep, ratio);
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
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		input.drawTextBox();
		if (ratio > cvt.getMaxRatio())
			ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", cvt.getMaxRatio()), j+xSize/2+88, k+31, 0xff0000);
		else if (ratio == 0)
			ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, "(1)", j+xSize/2+88, k+31, 0xff0000);
		else
			ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", Math.abs(cvt.ratio)), j+xSize/2+88, k+31, 4210752);
	}

	@Override
	public String getGuiTexture() {
		return "cvtgui";
	}

}
