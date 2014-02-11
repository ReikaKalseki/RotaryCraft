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
import net.minecraft.util.MathHelper;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.FractionalGuiButton;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Interfaces.FractionalButtonGui;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMusicHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Instrument;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Note;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.NoteLength;

public class GuiMusic extends GuiNonPoweredMachine implements FractionalButtonGui
{
	private TileEntityMusicBox music;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;

	private int minIndex;
	private int maxIndex;

	private NoteLength activeType = NoteLength.WHOLE;
	private Instrument activeVoice = Instrument.GUITAR;

	public GuiMusic(EntityPlayer p5ep, TileEntityMusicBox MusicBox)
	{
		super(new CoreContainer(p5ep, MusicBox), MusicBox);
		music = MusicBox;
		ySize = 217;
		xSize = 256;
		ep = p5ep;
		minIndex = 0;
		maxIndex = Math.min(8, music.musicQueue.size());
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String note = "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png";
		String put = "/Reika/RotaryCraft/Textures/GUI/buttons.png";

		buttonList.add(new ImagedGuiButton(1000, j+16, k+ySize/2-24, 12, 48, 90, 60, put, RotaryCraft.class));
		buttonList.add(new ImagedGuiButton(1001, j+228, k+ySize/2-24, 12, 48, 90, 108, put, RotaryCraft.class));

		//buttonList.add(new FractionalGuiButton(2, j+xSize/2-95, k+ySize/2-16, 190, 5, "", 12, 1, this));
		for (int i = 0; i < 33; i++) {
			FractionalGuiButton frac = new FractionalGuiButton(i, j+xSize/2-96, k+ySize/2-70+4*i+(i+1)/2, 192, 5-i%2, "", 9, 1, this);
			frac.shouldRender = false;
			buttonList.add(frac);
		}
	}

	@Override
	public void keyTyped(char c, int i){
		super.keyTyped(c, i);

	}

	@Override
	public void mouseClicked(int i, int j, int k) { //delete note on right-click
		super.mouseClicked(i, j, k);
		if (i > 117 && i < 310 && false) {
			int index = minIndex+MathHelper.ceiling_double_int((i-126)/24D);
			int pitch = this.getNoteIndexByClickPos(j);
			ReikaJavaLibrary.pConsole(j+":"+pitch);
			if (k == 0) {
				music.musicQueue.add(index, new Note(activeType, pitch, activeVoice));
			}
			else if (k == 1) {
				music.musicQueue.remove(index-1);
			}
		}
	}

	private int getNoteIndexByClickPos(int y) {
		if (y < 146) {
			if (y > 140)
				return 18;
			else if (y > 138)
				return 19;
			else if (y > 136)
				return 20;
		}
		return 0;
	}

	@Override
	public void onFractionalClick(int button, int fx, int fy) {
		int note = 32-button;
		int index = fx+minIndex;
		ReikaJavaLibrary.pConsole(note+":"+index);
		music.musicQueue.add(index, new Note(activeType, note, activeVoice));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id < 24000) {
			if (button.id == 1000 && minIndex > 0) {
				minIndex--;
				maxIndex--;
			}
			if (button.id == 1001 && maxIndex < music.musicQueue.size()) {
				minIndex++;
				maxIndex++;
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
		//fontRenderer.drawString("Channel", xSize/2-118, 41, 4210752);
		//fontRenderer.drawString("Voice", xSize/2-24, 41, 4210752);
		//fontRenderer.drawString("Volume", xSize/2+49, 41, 4210752);
		//fontRenderer.drawString("%", xSize/2+114, 41, 4210752);

		for (int i = minIndex; i < maxIndex; i++) {
			Note n = music.musicQueue.get(i);
			if (n != null) {
				this.drawNote(i-minIndex, n);
			}
		}

		for (int i = 0; i < 33; i++) {
			int x = xSize/2-96+2;
			int y = ySize/2-70+4*i+(i+1)/2;
			if (i%2 == 0)
				ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, this.getNoteName(32-i), x, y, 0);
		}
	}

	private String getNoteName(int value) {
		return ReikaMusicHelper.getNoteName(value);
	}

	private void drawNote(int i, Note n) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int x = xSize/2-73+i*21;
		int y = 185-this.getNoteVOffset(n.pitch);

		this.drawNote(x, y, n.length);
	}

	private int getNoteVOffset(int pitch) {
		pitch -= 2;
		int dy = 4+pitch*2;
		dy += 45;
		if (pitch >= 21)
			dy += 4;
		if (pitch >= 25)
			dy += 3;
		return dy;
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

		//this.drawRect(j+xSize/2-96, k+ySize/2-73, j+xSize/2+96, k+ySize/2+83, 0xffffffff);
		//this.drawRect(j+xSize/2-95, k+ySize/2-72, j+xSize/2+95, k+ySize/2+82, 0xff000000);
		for (int i = -8; i < 9; i++) //8 9
			this.drawRect(j+xSize/2-94, k+ySize/2-4+i*9+5, j+xSize/2+94, k+ySize/2+4+i*9+5, 0xffffffff);
	}

	private void drawNote(int x, int y, NoteLength note) {
		this.drawRect(x-4, y-3, x+4, y+3, 0xff000000);
		this.drawRect(x-3, y-4, x+3, y+4, 0xff000000);
		if (note.tickLength >= NoteLength.HALF.tickLength) {
			this.drawRect(x-3, y-2, x+3, y+2, 0xffffffff);
		}
		if (note.tickLength < NoteLength.WHOLE.tickLength) {
			this.drawRect(x+3, y-22, x+4, y-2, 0xff000000);
		}
		if (note.tickLength < NoteLength.QUARTER.tickLength) {
			ReikaGuiAPI.instance.drawLine(x+4, y-22, x+8, y-8, 0x000000);
		}
		if (note.tickLength < NoteLength.EIGHTH.tickLength) {
			ReikaGuiAPI.instance.drawLine(x+4, y-16, x+8, y-3, 0x000000);
		}
	}

	private int getYFromPitch(int pitch) {
		return 0;
	}

	@Override
	public String getGuiTexture() {
		return "musicgui";
	}
}
