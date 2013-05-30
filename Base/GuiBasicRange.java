/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Mouse;

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnumPackets;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;

public class GuiBasicRange extends GuiPowerOnlyMachine
{
	private TileEntityPowerReceiver te;
	public int range;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private EntityPlayer player;
	int x;
	int y;
	private GuiTextField input;

	public GuiBasicRange(EntityPlayer player, TileEntityPowerReceiver tile)
	{
		super(new CoreContainer(player, tile), tile);
		te = tile;
		ySize = 46;
		this.player = player;
		range = ((RangedEffect)te).getRange();
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
			if (te instanceof TileEntityForceField)
				ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, EnumPackets.FORCE.getMinValue(), te, player, range);
			else if (te instanceof TileEntityContainment)
				ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, EnumPackets.CONTAINMENT.getMinValue(), te, player, range);
			return;
		}
		range = Integer.parseInt(input.getText());
		if (range >= 0) {
			if (te instanceof TileEntityForceField)
				ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, EnumPackets.FORCE.getMinValue(), te, player, range);
			else if (te instanceof TileEntityContainment)
				ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, EnumPackets.CONTAINMENT.getMinValue(), te, player, range);
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		fontRenderer.drawString("Field Radius:", xSize/2-72, 25, 4210752);
		if (!input.isFocused()) {
			fontRenderer.drawString(String.format("%d", ((RangedEffect)te).getRange()), xSize/2+6, 25, 0xffffffff);
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
		if (range > ((RangedEffect)te).getMaxRange())
			color = 0xff0000;
		ImagedGuiButton.drawCenteredStringNoShadow(fontRenderer, String.format("(%d)", ((RangedEffect)te).getRange()), j+xSize/2+58, k+25, color);
	}

	@Override
	public String getGuiTexture() {
		return "playerdetectorgui";
	}
}
