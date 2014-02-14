/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.InvisibleButton;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Instrument;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.NoteLength;

public class GuiMusic extends GuiNonPoweredMachine
{
	private TileEntityMusicBox music;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;

	private int activeChannel;
	private NoteLength activeType = NoteLength.WHOLE;
	private Instrument activeVoice = Instrument.GUITAR;

	public GuiMusic(EntityPlayer p5ep, TileEntityMusicBox MusicBox)
	{
		super(new CoreContainer(p5ep, MusicBox), MusicBox);
		music = MusicBox;
		ySize = 217;
		xSize = 256;
		ep = p5ep;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String note = "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png";
		String put = "/Reika/RotaryCraft/Textures/GUI/buttons.png";

		buttonList.add(new GuiButton(100, j+40, k+20, 40, 20, "Save"));
		buttonList.add(new GuiButton(101, j+80, k+20, 40, 20, "Load"));

		int dx = 18;
		for (int m = 0; m < 4; m++) {
			for (int i = 0; i <= 4; i += 2) {
				buttonList.add(new InvisibleButton(i+1+m*12, j+dx+i*4, k+150, 5, 21, ""));
			}
			dx += 32;
			for (int i = 0; i <= 2; i += 2) {
				buttonList.add(new InvisibleButton(i+8+m*12, j+dx+i*4, k+150, 5, 21, ""));
			}
			dx += 24;
		}

		dx = 14;
		for (int i = 0; i <= 56; i += 2) {
			int id = i;
			if (id >= 8)
				id--;
			if (id >= 13)
				id--;
			if (id >= 20)
				id--;
			if (id >= 25)
				id--;
			if (id >= 32)
				id--;
			if (id >= 37)
				id--;
			if (id >= 44)
				id--;
			if (id >= 49)
				id--;
			int x = j+dx+i*4;
			int w = 4;
			if (i == 0 || i == 8 || i == 14 || i == 22 || i == 28 || i == 36 || i == 42 || i == 50 || i == 56) {
				x -= 2;
				w = 6;
			}
			if (i == 6 || i == 12 || i == 20 || i == 26 || i == 34 || i == 40 || i == 48 || i == 54) {
				w = 6;
			}
			if (i == 56)
				w = 8;
			buttonList.add(new InvisibleButton(id, x, k+150, w, 37, ""));

			if (i < 56) {
				int oldid = id;
				if (i%4 == 0) {
					x += w;
				}
				else {
					x -= 2;
				}
				if (id == 11 || id == 18 || id == 35 || id == 42)
					id++;
				else if (id == 12 || id == 19 || id == 36 || id == 43)
					id--;
				buttonList.add(new InvisibleButton(id, x, k+172, 2, 15, ""));
				id = oldid;
			}

			if (i == 2 || i == 10 || i == 14 || i == 18 || i == 22 || i == 30 || i == 38 || i == 42 || i == 46 || i == 50) {
				x += 2+w;
				buttonList.add(new InvisibleButton(id, x, k+172, 2, 15, ""));
				buttonList.add(new InvisibleButton(id+2, x+2, k+172, 2, 15, ""));
			}
		}
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);

	}

	@Override
	public void mouseClicked(int i, int j, int k) { //delete note on right-click
		if (k == 0) {
			for (int l = 0; l < buttonList.size(); l++) {
				GuiButton guibutton = (GuiButton)buttonList.get(l);
				if (guibutton.mousePressed(mc, i, j)) {
					this.actionPerformed(guibutton);
					if (guibutton.id >= 100)
						mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					return; //to avoid double presses
				}
			}
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 24000) {
			if (button.id == 100) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+1, music, ep, 0);
			}
			else if (button.id == 101) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+2, music, ep, 0);
			}
			else {
				ReikaJavaLibrary.pConsole(button.id);
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue(), music, ep, button.id);
			}
		}
		this.initGui();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

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

		ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png");
		this.drawTexturedModalRect(j+xSize/2-232/2, k+150, 0, 64, 232, 37);
	}

	@Override
	public String getGuiTexture() {
		return "musicgui";
	}
}
