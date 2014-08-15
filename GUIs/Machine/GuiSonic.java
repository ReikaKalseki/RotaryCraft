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
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySonicWeapon;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

public class GuiSonic extends GuiPowerOnlyMachine
{
	private TileEntitySonicWeapon sonic;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;
	private GuiTextField input;
	private GuiTextField input2;

	private long freq;
	private long vol;

	private int dB;

	public GuiSonic(EntityPlayer p5ep, TileEntitySonicWeapon SonicWeapon)
	{
		super(new CoreContainer(p5ep, SonicWeapon), SonicWeapon);
		sonic = SonicWeapon;
		ySize = 92;
		if (!sonic.DECIBELMODE)
			xSize = 235;
		if (!sonic.ENABLEFREQ)
			ySize = 56;
		ep = p5ep;
		vol = SonicWeapon.setvolume;
		freq = SonicWeapon.setpitch;

		dB = (int)(10*Math.log10(vol));
		if (dB < 0)
			dB = 0;

		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.freq));
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		int xo = 0;
		if (sonic.DECIBELMODE)
			xo = 48;
		input = new GuiTextField(fontRendererObj, j+xSize/2-62, k+73, 86, 16);
		input.setFocused(false);
		input.setMaxStringLength(12);
		input2 = new GuiTextField(fontRendererObj, j+xSize/2-62+xo, k+43, 86-xo, 16);
		input2.setFocused(false);
		if (sonic.DECIBELMODE)
			input2.setMaxStringLength(3);
		else
			input2.setMaxStringLength(12);
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);
		if (sonic.ENABLEFREQ)
			input.textboxKeyTyped(c, i);
		input2.textboxKeyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		if (sonic.ENABLEFREQ)
			input.mouseClicked(i, j, k);
		input2.mouseClicked(i, j, k);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		boolean valid1 = true;
		boolean valid2 = true;
		x = Mouse.getX();
		y = Mouse.getY();
		if (input.getText().isEmpty() && input2.getText().isEmpty()) {
			return;
		}
		if (input.getText().isEmpty())
			valid1 = false;
		if (input2.getText().isEmpty())
			valid2 = false;
		if (!input.getText().isEmpty() && !(input.getText().matches("^[0-9 ]+$"))) {
			freq = 0;
			input.deleteFromCursor(-1);
			ReikaPacketHelper.sendLongDataPacket(RotaryCraft.packetChannel, PacketRegistry.SONIC.getMinValue(), sonic, freq);
			valid1 = false;
		}
		if (!input2.getText().isEmpty() && !(input2.getText().matches("^[0-9 ]+$"))) {
			dB = 0;
			this.getVolFromdB();
			input2.deleteFromCursor(-1);
			ReikaPacketHelper.sendLongDataPacket(RotaryCraft.packetChannel, PacketRegistry.SONIC.getMaxValue(), sonic, vol);
			valid2 = false;
		}
		if (!valid1 && !valid2)
			return;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
		//System.out.println(input.getText());
		if (valid1) {
			freq = Long.parseLong(input.getText());
			if (freq >= 0)
				ReikaPacketHelper.sendLongDataPacket(RotaryCraft.packetChannel, PacketRegistry.SONIC.getMinValue(), sonic, freq);
		}
		if (valid2) {
			dB = ReikaJavaLibrary.safeIntParse(input2.getText());
			if (dB >= 0) {
				this.getVolFromdB();
				ReikaPacketHelper.sendLongDataPacket(RotaryCraft.packetChannel, PacketRegistry.SONIC.getMaxValue(), sonic, vol);
			}
		}
	}

	public void getVolFromdB() {
		double d = dB/10D;
		d = ReikaMathLibrary.doubpow(10, d);
		//ReikaJavaLibrary.pConsole(d);
		vol = (long)(d);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int xo = 0;
		if (sonic.DECIBELMODE)
			xo = 48;

		super.drawGuiContainerForegroundLayer(a, b);

		fontRendererObj.drawString("Volume:", xSize/2-92+xo, 35, 4210752);
		if (sonic.ENABLEFREQ)
			fontRendererObj.drawString("Frequency:", xSize/2-113, 65, 4210752);
		if (sonic.ENABLEFREQ) {
			if (!input.isFocused())
				fontRendererObj.drawString(String.format("%d", sonic.setpitch), xSize/2-50, 65, 0xffffffff);
		}
		if (!input2.isFocused()) {
			if (sonic.DECIBELMODE)
				fontRendererObj.drawString(String.format("%d", dB), xSize/2-50+xo, 35, 0xffffffff);
			else
				fontRendererObj.drawString(String.format("%d", sonic.setvolume), xSize/2-50+xo, 35, 0xffffffff);
		}


		if (sonic.DECIBELMODE)
			fontRendererObj.drawString("dB", xSize/2+38, 35, 4210752);
		else
			fontRendererObj.drawString("W/m^2", xSize/2+38, 35, 4210752);
		if (sonic.ENABLEFREQ)
			fontRendererObj.drawString("Hz", xSize/2+38, 65, 4210752);
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
		if (sonic.ENABLEFREQ)
			input.drawTextBox();
		input2.drawTextBox();

		int color = 4210752;
		if (vol > sonic.getMaxVolume())
			color = 0xff0000;
		//ImagedGuiButton.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", sonic.getVolume()), j+xSize/2+85, k+65, color);
		color = 4210752;
		this.drawPowerTab(j, k);
		if (!sonic.ENABLEFREQ)
			return;
		if (freq > sonic.getMaxPitch())
			color = 0xff0000;
		//ImagedGuiButton.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", sonic.getPitch()), j+xSize/2+85, k+35, color);
	}

	@Override
	public String getGuiTexture() {
		return "sonicgui3";
	}
}