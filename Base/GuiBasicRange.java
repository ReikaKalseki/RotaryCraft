/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

public class GuiBasicRange extends GuiPowerOnlyMachine
{
	public int range;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;
	private GuiTextField input;

	public GuiBasicRange(EntityPlayer p5ep, TileEntityPowerReceiver te)
	{
		super(new CoreContainer(p5ep, te), te);
		pwr = te;
		ySize = 46;
		ep = p5ep;
		range = ((RangedEffect)pwr).getRange();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", this.range));
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		input = new GuiTextField(fontRendererObj, j+xSize/2-6, k+33, 26, 16);
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
			if (pwr instanceof TileEntityForceField)
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.FORCE.getMinValue(), pwr, range);
			else if (pwr instanceof TileEntityContainment)
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CONTAINMENT.getMinValue(), pwr, range);
			return;
		}
		range = Integer.parseInt(input.getText());
		if (range >= 0) {
			if (pwr instanceof TileEntityForceField)
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.FORCE.getMinValue(), pwr, range);
			else if (pwr instanceof TileEntityContainment)
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CONTAINMENT.getMinValue(), pwr, range);
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		fontRendererObj.drawString("Field Radius:", xSize/2-72, 25, 4210752);
		if (!input.isFocused()) {
			fontRendererObj.drawString(String.format("%d", ((RangedEffect)pwr).getRange()), xSize/2+6, 25, 0xffffffff);
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
		if (range > ((RangedEffect)pwr).getMaxRange())
			color = 0xff0000;
		ImagedGuiButton.drawCenteredStringNoShadow(fontRendererObj, String.format("(%d)", ((RangedEffect)pwr).getRange()), j+xSize/2+58, k+25, color);
	}

	@Override
	public String getGuiTexture() {
		return "playerdetectorgui";
	}
}