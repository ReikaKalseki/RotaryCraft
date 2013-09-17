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
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public class GuiSplitter extends GuiNonPoweredMachine
{
	public int mode;

	private TileEntitySplitter splitter;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;

	public GuiSplitter(EntityPlayer p5ep, TileEntitySplitter Splitter)
	{
		super(new CoreContainer(p5ep, Splitter), Splitter);
		splitter = Splitter;
		ySize = 140;
		ep = p5ep;
		mode = splitter.splitmode;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, j+8, -1+k+32, 72, 20, "31:1 Inline"));
		buttonList.add(new GuiButton(1, j+8, -1+k+52, 72, 20, "15:1 Inline"));
		buttonList.add(new GuiButton(2, j+8, -1+k+72, 72, 20, "7:1 Inline"));
		buttonList.add(new GuiButton(3, j+8, -1+k+92, 72, 20, "3:1 Inline"));

		buttonList.add(new GuiButton(8, j+52, -1+k+114, 72, 20, "1:1 Even"));

		buttonList.add(new GuiButton(4, j+96, -1+k+32, 72, 20, "1:31 Bend"));
		buttonList.add(new GuiButton(5, j+96, -1+k+52, 72, 20, "1:15 Bend"));
		buttonList.add(new GuiButton(6, j+96, -1+k+72, 72, 20, "1:7 Bend"));
		buttonList.add(new GuiButton(7, j+96, -1+k+92, 72, 20, "1:3 Bend"));

		//this.buttonList.add(new GuiButton(9, j+52, -1+k+140, 72, 20, "Merge"));
	}

	public void updateMode(int md) {
		splitter.splitmode = md;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id <= 8) {
			//this.updateMode(button.id);
			mode = button.id;
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.SPLITTER.getMinValue(), splitter, ep, mode);
		}
		if (button.id == 9)
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.SPLITTER.getMaxValue(), splitter, ep, 0);
		this.updateScreen();

	}

	@Override
	public String getGuiTexture() {
		return "splittergui";
	}
}
