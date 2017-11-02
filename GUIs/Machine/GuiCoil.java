/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

public class GuiCoil extends GuiNonPoweredMachine
{

	private int omega;
	private int torque;
	private GuiTextField input;
	private GuiTextField input2;

	private TileEntityAdvancedGear coil;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	public GuiCoil(EntityPlayer p5ep, TileEntityAdvancedGear AdvancedGear)
	{
		super(new CoreContainer(p5ep, AdvancedGear), AdvancedGear);
		coil = AdvancedGear;
		ySize = coil.isCreative() ? 72 : 105;
		xSize = 176;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		input = new GuiTextField(fontRendererObj, j+xSize/2-15, k+30, 56, 16);
		input.setFocused(false);
		input.setMaxStringLength(8);
		input2 = new GuiTextField(fontRendererObj, j+xSize/2-15, k+60, 56, 16);
		input2.setFocused(false);
		input2.setMaxStringLength(8);
	}

	@Override
	protected void keyTyped(char c, int i){
		super.keyTyped(c, i);
		input.textboxKeyTyped(c, i);
		input2.textboxKeyTyped(c, i);
	}

	@Override
	protected void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
		input2.mouseClicked(i, j, k);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		boolean valid1 = true;
		boolean valid2 = true;
		if (input.getText().isEmpty() && input2.getText().isEmpty()) {
			return;
		}
		if (input.getText().isEmpty())
			valid1 = false;
		if (input2.getText().isEmpty())
			valid2 = false;
		if (!input.getText().isEmpty() && !(input.getText().matches("^[0-9 ]+$"))) {
			omega = 0;
			input.deleteFromCursor(-1);
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.COIL.getMinValue(), coil, omega);
			valid1 = false;
		}
		if (!input2.getText().isEmpty() && !(input2.getText().matches("^[0-9 ]+$"))) {
			torque = 0;
			input2.deleteFromCursor(-1);
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.COIL.getMaxValue(), coil, torque);
			valid2 = false;
		}
		if (!valid1 && !valid2)
			return;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("435");
		//System.out.println(input.getText());
		if (valid1) {
			omega = ReikaJavaLibrary.safeIntParse(input.getText());
			if (omega >= 0) {
				if (omega > RotaryConfig.omegalimit)
					omega = RotaryConfig.omegalimit;
				if (omega > coil.getMaximumEmission()) {
					omega = coil.getMaximumEmission();
					input.setText(String.valueOf(coil.getMaximumEmission()));
				}
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.COIL.getMinValue(), coil, omega);
			}
		}
		if (valid2) {
			torque = ReikaJavaLibrary.safeIntParse(input2.getText());
			if (torque >= 0) {
				if (torque > RotaryConfig.torquelimit)
					torque = RotaryConfig.torquelimit;
				if (torque > coil.getMaximumEmission()) {
					torque = coil.getMaximumEmission();
					input2.setText(String.valueOf(coil.getMaximumEmission()));
				}
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.COIL.getMaxValue(), coil, torque);
			}
		}
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

		ReikaTextureHelper.bindFontTexture();

		fontRendererObj.drawString("Output Speed", xSize/2-82, 22, 4210752);
		if (!coil.isCreative())
			fontRendererObj.drawString(String.format("(Max %d)", coil.getMaximumEmission()), xSize/2-82, 37, 4210752);
		fontRendererObj.drawString("Output Torque", xSize/2-82, 52, 4210752);

		fontRendererObj.drawString("rad/s", xSize/2+53, 22, 4210752);
		fontRendererObj.drawString("Nm", xSize/2+53, 52, 4210752);

		if (!coil.isCreative()) {
			double e = coil.getEnergy()/20D;
			String s = String.format("Stored Energy: %.3f%sJ", ReikaMathLibrary.getThousandBase(e), ReikaEngLibrary.getSIPrefix(e));
			fontRendererObj.drawString(s, xSize/2-82, 80-8, 4210752);

			long max = coil.getMaxStorageCapacity();
			s = String.format("Max Energy: %.3f%sJ", ReikaMathLibrary.getThousandBase(max), ReikaEngLibrary.getSIPrefix(max));
			fontRendererObj.drawString(s, xSize/2-82, 80-8+14, 4210752);

			String i = "/Reika/RotaryCraft/Textures/GUI/"+this.getGuiTexture()+".png";
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ReikaTextureHelper.bindTexture(RotaryCraft.class, i);
			int h = (int)(e*40/max);
			if (e > 0 && h == 0)
				h = 1;
			this.drawTexturedModalRect(128, 57, 178, 2, h, 40);
		}

		if (!input.isFocused())
			fontRendererObj.drawString(String.format("%d", coil.getReleaseOmega()), xSize/2-3, 22, 0xffffffff);
		if (!input2.isFocused())
			fontRendererObj.drawString(String.format("%d", coil.getReleaseTorque()), xSize/2-3, 52, 0xffffffff);

		super.drawGuiContainerForegroundLayer(a, b);
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
	}

	@Override
	protected String getGuiTexture() {
		return coil.isCreative() ? "coilgui" : "coilgui2";
	}

}
