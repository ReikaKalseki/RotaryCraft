/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerItemFilter;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter.MatchData;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter.MatchDisplay;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter.MatchType;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter.SettingType;

public class GuiItemFilter extends GuiPowerOnlyMachine
{
	private final TileEntityItemFilter filter;

	private static final int LINES = 5;

	private SettingType page = SettingType.BASIC;
	private ArrayList<MatchDisplay> display;
	private int nbtListPos = 0;

	private MatchData lastData;

	public GuiItemFilter(EntityPlayer p5ep, TileEntityItemFilter te)
	{
		super(new ContainerItemFilter(p5ep, te), te);
		xSize = 256;
		ySize = 217;
		ep = p5ep;
		filter = te;
	}

	@Override
	public void initGui() {
		super.initGui();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		MatchData data = filter.getData();
		if (data != null) {
			display = null;
			switch(page) {
				case BASIC:
					display = data.getMainDisplay();
					break;
				case NBT:
					display = data.getNBTDisplay();
					break;
				case ORE:
					display = data.getOreDisplay();
					break;
				case CLASS:
					display = data.getClassDisplay();
					break;
			}
			if (display != null) {
				int d = page == SettingType.NBT ? 1 : 0;
				int max = Math.min(nbtListPos+display.size(), nbtListPos+5-d);
				for (int i = nbtListPos; i < max && i < display.size()+d; i++) {
					int i2 = i-nbtListPos+d;
					MatchDisplay m = display.get(i);
					int u = m.getSetting() == MatchType.MATCH ? 0 : 9;
					int v = m.getSetting() == MatchType.MISMATCH ? 54 : 63;
					buttonList.add(new ImagedGuiButton(i, j+30, k+18+i2*16, 9, 9, u, v, "Textures/GUI/buttons.png", RotaryCraft.class));
				}
			}
		}
		buttonList.add(new GuiButton(-1, j+30, k+100, 20, 20, "<"));
		buttonList.add(new GuiButton(-2, j+50, k+100, 20, 20, ">"));
		if (page == SettingType.NBT) {
			if (!display.isEmpty()) {
				for (int i = 0; i < 3; i++) {
					int u = i == 0 ? 0 : 9;
					int v = i == 1 ? 54 : 63;
					buttonList.add(new ImagedGuiButton(-5-i, j+30+i*10, k+18+0, 9, 9, u, v, "Textures/GUI/buttons.png", RotaryCraft.class));
				}
			}
			if (display != null && display.size() > LINES) {
				buttonList.add(new GuiButton(-3, j+70, k+100, 20, 20, "+"));
				buttonList.add(new GuiButton(-4, j+90, k+100, 20, 20, "-"));
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		if (this.isClickTooSoon())
			return;

		if (b.id == -1) {
			page = page.previous();
		}
		else if (b.id == -2) {
			page = page.next();
		}
		else if (b.id == -3) {
			int d = page == SettingType.NBT ? 1 : 0;
			if (display != null && nbtListPos < display.size()-LINES+d)
				nbtListPos++;
		}
		else if (b.id == -4) {
			if (nbtListPos > 0)
				nbtListPos--;
		}
		else if (b.id == -5 || b.id == -6 || b.id == -7) {
			for (MatchDisplay m : display) {
				while (m.getSetting().ordinal() != (-b.id)-5)
					m.increment();
			}
			this.sendData();
		}
		else if (b.id >= 0 && b.id != 24000) {
			MatchDisplay m = display.get(b.id);
			m.increment();
			this.sendData();
		}
		this.initGui();
	}

	private void sendData() {
		//ReikaJavaLibrary.pConsole(filter.getData());
		NBTTagCompound nbt = filter.getData().writeToNBT();
		//ReikaJavaLibrary.pConsole(nbt);
		nbt.setInteger("posX", tile.xCoord);
		nbt.setInteger("posY", tile.yCoord);
		nbt.setInteger("posZ", tile.zCoord);
		ReikaPacketHelper.sendNBTPacket(RotaryCraft.packetChannel, PacketRegistry.FILTERSETTING.ordinal(), nbt, PacketTarget.server);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int dx = this.inventoryLabelLeft() ? 176 : xSize-50;
		fontRendererObj.drawString("Blacklist", dx, (ySize - 96) + 3, 4210752);

		MatchData data = filter.getData();
		if (data != lastData)
			this.initGui();
		lastData = data;

		int x = api.getMouseRealX();
		int y = api.getMouseRealY();

		if (filter.getData() != null) {
			/*
			int tx = 44;
			int ty = 16;
			Topology<String> t = match.getTopology();
			Map<String, Integer> map = t.getDepthMap();
			if (!map.isEmpty()) {
				fontRendererObj.drawString("NBT Data:", tx, ty, 0x000000);
				ty += fontRendererObj.FONT_HEIGHT+2;
				for (String s : map.keySet()) {
					if (!s.isEmpty()) {
						int lvl = map.get(s);
						s = s+": "+match.getTagString(s);
						for (int i = 0; i < lvl; i++) {
							s = "  "+s;
						}
						fontRendererObj.drawString(s, tx, ty, 0x000000);
						ty += fontRendererObj.FONT_HEIGHT+2;
					}
				}
			}
			 */

			if (display != null) {
				if (display.isEmpty()) {
					fontRendererObj.drawString("[No Values]", 42, 19, 0x000000);
				}

				int d = page == SettingType.NBT ? 1 : 0;
				int max = Math.min(nbtListPos+display.size(), nbtListPos+LINES-d);
				for (int i = nbtListPos; i < max && i < display.size()+d; i++) {
					int i2 = i-nbtListPos+d;
					MatchDisplay m = display.get(i);
					int tx = 42;
					int ty = 19+i2*16;
					String s = m.displayName+" ("+m.value+"): ";
					fontRendererObj.drawString(s, tx, ty, 0x000000);
					fontRendererObj.drawString(m.getSetting().name, tx+fontRendererObj.getStringWidth(s), ty, m.getSetting().color);
				}

				if (page == SettingType.NBT) {
					if (!display.isEmpty()) {
						int tx = 42;
						int ty = 19+0*16;
						fontRendererObj.drawString("Toggle All", tx+22, ty, 0x000000);
					}
				}
			}
		}
	}

	@Override
	protected boolean inventoryLabelLeft() {
		return true;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
	}

	@Override
	protected String getGuiTexture() {
		return "filtergui2";
	}
}
