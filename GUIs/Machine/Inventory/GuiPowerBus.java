/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerPowerBus;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;

public class GuiPowerBus extends GuiNonPoweredMachine {

	private final TileEntityPowerBus tile;

	public GuiPowerBus(EntityPlayer ep, TileEntityPowerBus te) {
		super(new ContainerPowerBus(ep, te), te);
		this.ep = ep;
		tile = te;

		xSize = 220;
		ySize = 220;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2-2;
		int k = (height - ySize) / 2 - 12;

		String file = "/Reika/RotaryCraft/Textures/GUI/buttons.png";

		int[] dx = {j+103, j+103, j+49, j+139+18};
		int[] dy = {k+26, k+134, k+66+18-4, k+66+18-4};

		for (int i = 0; i < 4; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i+2];
			//int u = tile.isSideSpeedMode(dir) ? 18 : 0;
			if (!tile.isReceivingFromSide(dir))
				buttonList.add(new ImagedGuiButton(i, dx[i], dy[i], 18, 18, "", 18, 18, 0, false, file, RotaryCraft.class));
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 24000) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[button.id+2];
			tile.setMode(dir, !tile.isSideSpeedMode(dir));
			ReikaJavaLibrary.pConsole(button.id+":"+dir);
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.POWERBUS.getMinValue(), tile, ep, button.id);
		}
		this.initGui();
	}

	@Override
	public String getGuiTexture() {
		return "bus";
	}

}
