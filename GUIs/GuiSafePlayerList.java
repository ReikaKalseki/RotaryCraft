/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Registry.PacketRegistry;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class GuiSafePlayerList extends GuiScreen {

	private int xSize = 226;
	private int ySize = 204;

	private String playerName;

	private String activePlayer;

	private int listOffset = 0;

	private TileEntityAimedCannon te;
	private List<String> playerList;

	private EntityPlayer ep;

	private long buttontime;
	protected int buttontimer = 0;

	private static final int colsize = 8;

	public GuiSafePlayerList(EntityPlayer e, TileEntityAimedCannon tile) {
		ep = e;
		te = tile;
		playerList = te.getCopyOfSafePlayerList();
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int width = 180;

		int dx = 10;//xSize/2-width/2;//(i/colsize)*width;
		for (int i = listOffset; i < ReikaMathLibrary.extrema(playerList.size(), listOffset+colsize, "absmin"); i++) {
			int dy = 12+((i-listOffset)%colsize)*22;
			buttonList.add(new GuiButton(i, j+dx, k+dy, width, 20, playerList.get(i)));
		}

		buttonList.add(new GuiButton(1000000, j+dx+width+6, 11+k, 20, 20, "^"));
		buttonList.add(new GuiButton(1000001, j+dx+width+6, 11+k+colsize*20-5, 20, 20, "v"));
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (buttontimer > 0)
			return;
		buttontimer = 20;
		if (button.id >= 1000000) {
			if (button.id == 1000000) {
				if (listOffset > 0)
					listOffset --;
			}
			else {
				if (listOffset < playerList.size()-colsize)
					listOffset++;
			}
			this.initGui();
			return;
		}
		activePlayer = playerList.get(button.id);
		ReikaPacketHelper.sendStringPacket(RotaryCraft.packetChannel, PacketRegistry.SAFEPLAYER.getMinValue(), activePlayer, te);
		playerList.remove(button.id);
		this.initGui();
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		if (System.nanoTime()-buttontime > 100000000) {
			buttontime = System.nanoTime();
			buttontimer = 0;
		}
		String title = te.getPlacerName()+"'s "+te.getName()+" Whitelist";

		String var4 = "/Reika/RotaryCraft/Textures/GUI/safeplayergui.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, title, posX+xSize/2, posY+6, 4210752);
		super.drawScreen(x, y, f);
	}

}