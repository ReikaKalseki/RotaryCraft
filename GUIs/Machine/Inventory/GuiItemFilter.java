/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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

		MatchData match = filter.getData();
		if (match != null) {
			display = null;
			switch(page) {
				case BASIC:
					display = match.getMainDisplay();
					break;
				case NBT:
					display = match.getNBTDisplay();
					break;
				case ORE:
					display = match.getOreDisplay();
					break;
				case CLASS:
					display = match.getClassDisplay();
					break;
			}
			if (display != null) {
				int max = Math.min(nbtListPos+display.size(), nbtListPos+5);
				for (int i = nbtListPos; i < max && i < display.size(); i++) {
					int i2 = i-nbtListPos;
					MatchDisplay m = display.get(i);
					int u = m.getSetting() == MatchType.MATCH ? 0 : 9;
					int v = m.getSetting() == MatchType.MISMATCH ? 54 : 63;
					buttonList.add(new ImagedGuiButton(i, j+30, k+18+i2*16, 9, 9, u, v, "Textures/GUI/buttons.png", RotaryCraft.class));
				}
			}
		}
		buttonList.add(new GuiButton(-1, j+30, k+100, 20, 20, "<"));
		buttonList.add(new GuiButton(-2, j+50, k+100, 20, 20, ">"));
		if (page == SettingType.NBT && display != null && display.size() > LINES) {
			buttonList.add(new GuiButton(-3, j+70, k+100, 20, 20, "+"));
			buttonList.add(new GuiButton(-4, j+90, k+100, 20, 20, "-"));
		}
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		super.actionPerformed(b);

		if (b.id == -1) {
			page = page.previous();
		}
		else if (b.id == -2) {
			page = page.next();
		}
		else if (b.id == -3) {
			if (display != null && nbtListPos < display.size()-LINES)
				nbtListPos++;
		}
		else if (b.id == -4) {
			if (nbtListPos > 0)
				nbtListPos--;
		}
		else if (b.id >= 0 && b.id != 24000) {
			MatchDisplay m = display.get(b.id);
			m.increment();
			//ReikaJavaLibrary.pConsole(filter.getData());
			NBTTagCompound nbt = filter.getData().writeToNBT();
			//ReikaJavaLibrary.pConsole(nbt);
			nbt.setInteger("posX", tile.xCoord);
			nbt.setInteger("posY", tile.yCoord);
			nbt.setInteger("posZ", tile.zCoord);
			ReikaPacketHelper.sendNBTPacket(RotaryCraft.packetChannel, PacketRegistry.FILTERSETTING.getMinValue(), nbt, new PacketTarget.ServerTarget());
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int x = api.getMouseRealX();
		int y = api.getMouseRealY();

		MatchData match = filter.getData();
		if (match != null) {
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

				int max = Math.min(nbtListPos+display.size(), nbtListPos+LINES);
				for (int i = nbtListPos; i < max && i < display.size(); i++) {
					int i2 = i-nbtListPos;
					MatchDisplay m = display.get(i);
					int tx = 42;
					int ty = 19+i2*16;
					String s = m.displayName+" ("+m.value+"): ";
					fontRendererObj.drawString(s, tx, ty, 0x000000);
					fontRendererObj.drawString(m.getSetting().name, tx+fontRendererObj.getStringWidth(s), ty, m.getSetting().color);
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
		return "filtergui";
	}
}
