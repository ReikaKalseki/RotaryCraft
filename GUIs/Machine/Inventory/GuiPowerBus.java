/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
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
			int u = tile.isSideSpeedMode(dir) ? 54 : 36;
			if (tile.canHaveItemInSlot(dir))
				buttonList.add(new ImagedGuiButton(i, dx[i], dy[i], 18, 18, u, 36, file, RotaryCraft.class));
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 24000) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[button.id+2];
			tile.setMode(dir, !tile.isSideSpeedMode(dir));
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.POWERBUS.getMinValue(), tile, button.id);
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int[] x = {101, 101, 65, 137};
		int[] y = {32, 104, 68, 68};
		for (int i = 0; i < 4; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i+2];
			int dx = x[i];
			int dy = y[i];
			if (tile.canHaveItemInSlot(dir)) {
				this.drawRect(dx, dy, dx+18, dy+18, this.getColorForSide(dir));
			}
			else {
				String tex = "/Reika/RotaryCraft/Textures/GUI/"+this.getGuiTexture()+".png";
				ReikaTextureHelper.bindTexture(RotaryCraft.class, tex);
				GL11.glColor3f(1, 1, 1);
				this.drawTexturedModalRect(dx, dy, 8, 8, 18, 18);
			}
		}
	}

	private int getColorForSide(ForgeDirection dir) {
		switch(dir) {
		case EAST:
			return 0x44ffff00;
		case NORTH:
			return 0x440000ff;
		case SOUTH:
			return 0x44ff0000;
		case WEST:
			return 0x4400ff00;
		default:
			return 0x44ffffff;
		}
	}

	@Override
	public String getGuiTexture() {
		return "bus";
	}

}
