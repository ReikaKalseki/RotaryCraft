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

import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerBlower;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBlower;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GuiBlower extends GuiPowerOnlyMachine {

	private TileEntityBlower tile;
	private boolean[] controls;

	public GuiBlower(EntityPlayer player, TileEntityBlower te) {
		super(new ContainerBlower(player, te), te);
		tile = te;
		xSize = 176;
		ySize = 192;
		ep = player;

		controls = new boolean[4];
		controls[0] = te.isWhitelist;
		controls[1] = te.checkMeta;
		controls[2] = te.checkNBT;
		controls[3] = !te.useOreDict;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		String s = "Textures/GUI/blowergui.png";

		for (int i = 0; i < 4; i++) {
			int u = 176;
			if (controls[i])
				u += 18;
			buttonList.add(new ImagedGuiButton(i, j+25+36*i, k+64, 18, 18, u, 54+18*i, s, RotaryCraft.class));
		}
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);

		if (b.id < 24000) {
			ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.BLOWER.getMinValue()+b.id, tile, 0);
			controls[b.id] = !controls[b.id];
		}

		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int dy = 18;
		int x = 8;
		int y = 21;
		for (int i = 0; i < tile.matchingItems.length; i++) {
			ItemStack is = tile.matchingItems[i];
			if (is != null) {
				api.drawItemStack(itemRender, fontRendererObj, is, x+i%9*18, y+i/9*dy);
			}
		}

		if (api.isMouseInBox(j+25, j+43, k+64, k+82)) {
			api.drawTooltipAt(fontRendererObj, controls[0] ? "Whitelist" : "Blacklist", api.getMouseRealX()-j+50, api.getMouseRealY()-k);
		}
		if (api.isMouseInBox(j+25+36, j+43+36, k+64, k+82)) {
			api.drawTooltipAt(fontRendererObj, controls[1] ? "Use Metadata" : "Ignore Metadata", api.getMouseRealX()-j+80, api.getMouseRealY()-k);
		}
		if (api.isMouseInBox(j+25+36*2, j+43+36*2, k+64, k+82)) {
			api.drawTooltipAt(fontRendererObj, controls[2] ? "Use NBT" : "Ignore NBT", api.getMouseRealX()-j, api.getMouseRealY()-k);
		}
		if (api.isMouseInBox(j+25+36*3, j+43+36*3, k+64, k+82)) {
			api.drawTooltipAt(fontRendererObj, controls[3] ? "Match Exact" : "Use Ore Dictionary", api.getMouseRealX()-j, api.getMouseRealY()-k);
		}
	}

	@Override
	public String getGuiTexture() {
		return "blowergui";
	}

}