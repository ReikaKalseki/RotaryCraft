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
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSplitter extends GuiNonPoweredMachine
{
	public int mode;

	private TileEntitySplitter splitter;

	int x;
	int y;

	public GuiSplitter(EntityPlayer p5ep, TileEntitySplitter Splitter)
	{
		super(new CoreContainer(p5ep, Splitter), Splitter);
		splitter = Splitter;
		ySize = 140;
		ep = p5ep;
		mode = splitter.getRatioFromMode();
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		buttonList.add(new GuiButton(32, j+8, -1+k+32, 72, 20, "31:1 Inline"));
		buttonList.add(new GuiButton(16, j+8, -1+k+52, 72, 20, "15:1 Inline"));
		buttonList.add(new GuiButton(8, j+8, -1+k+72, 72, 20, "7:1 Inline"));
		buttonList.add(new GuiButton(4, j+8, -1+k+92, 72, 20, "3:1 Inline"));

		buttonList.add(new GuiButton(1, j+52, -1+k+114, 72, 20, "1:1 Even"));

		buttonList.add(new GuiButton(-32, j+96, -1+k+32, 72, 20, "1:31 Bend"));
		buttonList.add(new GuiButton(-16, j+96, -1+k+52, 72, 20, "1:15 Bend"));
		buttonList.add(new GuiButton(-8, j+96, -1+k+72, 72, 20, "1:7 Bend"));
		buttonList.add(new GuiButton(-4, j+96, -1+k+92, 72, 20, "1:3 Bend"));
	}

	public void updateMode(int md) {
		splitter.setMode(md);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id <= 32) {
			//this.updateMode(button.id);
			mode = button.id;
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.SPLITTER.getMinValue(), splitter, mode);
		}
		this.updateScreen();
		this.updateMode(mode);

	}

	@Override
	public String getGuiTexture() {
		return "splittergui";
	}
}