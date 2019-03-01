/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ColorButton;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Instantiable.GUI.ItemIconButton;
import Reika.DragonAPI.Instantiable.GUI.PianoKeyboard;
import Reika.DragonAPI.Instantiable.GUI.PianoKeyboard.MusicGui;
import Reika.DragonAPI.Instantiable.GUI.PianoKeyboard.PianoKey;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Instrument;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.NoteLength;

public class GuiMusic extends GuiNonPoweredMachine implements MusicGui {

	private TileEntityMusicBox music;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	int x;
	int y;

	private int activeChannel = 0;
	private NoteLength activeType = NoteLength.WHOLE;
	private Instrument activeVoice = Instrument.GUITAR;

	private PianoKeyboard input;

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

		buttonList.add(new GuiButton(100, j+10, k+6, 40, 20, "Save"));
		buttonList.add(new GuiButton(101, j+50, k+6, 40, 20, "Load"));
		buttonList.add(new GuiButton(102, j+xSize/2+40, k+6, 80, 20, "Load Demo"));

		buttonList.add(new GuiButton(103, j+20, k+160, xSize-40, 20, "Add Rest"));

		buttonList.add(new GuiButton(104, j+20, k+185, 64, 20, "Backspace"));
		buttonList.add(new GuiButton(105, j+84, k+185, 88, 20, "Clear Channel"));
		buttonList.add(new GuiButton(106, j+172, k+185, 64, 20, "Clear Music"));

		for (int i = 0; i < 16; i++) {
			ColorButton b = new ColorButton(200+i, j+9+i*15, k+95, 12, 12, music.getColorForChannel(i));
			if (activeChannel == i)
				b.isSelected = true;
			buttonList.add(b);
		}

		input = new PianoKeyboard(j+xSize/2-116, k+120, this);

		int[] offset = new int[5];
		offset[activeType.ordinal()] = 80;
		for (int i = 0; i < 5; i++) {
			buttonList.add(new ImagedGuiButton(300+i, j+10+16*i, k+53, 16, 16, i*16+offset[i], 32, note, RotaryCraft.class));
		}

		ItemStack[] items = {
				new ItemStack(Blocks.grass),
				new ItemStack(Blocks.planks),
				new ItemStack(Blocks.portal),
				new ItemStack(Blocks.stone),
				new ItemStack(Blocks.sand),
				new ItemStack(Blocks.glass)
		};
		for (int i = 0; i < 6; i++)
			buttonList.add(new ItemIconButton(400+i, j+152+16*i, k+53, 0, items[i]));
	}

	@Override
	protected void keyTyped(char c, int i) {
		super.keyTyped(c, i);

	}

	@Override
	protected void mouseClicked(int i, int j, int k) { //delete note on right-click
		/*
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
		}*/
		super.mouseClicked(i, j, k);
		input.mouseClicked(i, j, k);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		boolean flag = true;
		if (button.id < 24000) {
			if (button.id == 100) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+1, music, 0, 0, 0, 0); //data not used
			}
			else if (button.id == 101) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+2, music, 0, 0, 0, 0);
			}
			else if (button.id == 102) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+3, music, 0, 0, 0, 0); //demo
			}
			else if (button.id == 103) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+4, music, activeChannel, activeType.ordinal(), 0, 0); //rest
			}
			else if (button.id == 104) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+5, music, activeChannel, 0, 0, 0); //bksp
			}
			else if (button.id == 105) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+6, music, activeChannel, 0, 0, 0); //ch clr
			}
			else if (button.id == 106) {
				ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+7, music, 0, 0, 0, 0); //clrall
			}
			else if (button.id >= 400) {
				int i = button.id-400+1;
				activeVoice = Instrument.values()[i];
			}
			else if (button.id >= 300) {
				int i = button.id-300;
				activeType = NoteLength.values()[i];
			}
			else if (button.id >= 200) {
				activeChannel = button.id-200;
			}
			else {
				//ReikaJavaLibrary.pConsole(button.id);
				flag = false;
				music.sendNote(button.id, activeChannel, activeType, activeVoice);
			}
		}
		if (flag) {
			this.initGui();
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		super.drawGuiContainerForegroundLayer(a, b);

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, "Note Length", 51, 42, 0);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, "Instrument", 200, 42, 0);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, "Channel Select", xSize/2, 85, 0);

		int dx = (activeVoice.ordinal()-1)*16;
		int color = TileEntityMusicBox.getColorForChannel(activeChannel);
		ReikaGuiAPI.instance.drawLine(152+dx, 53, 152+dx, 69, 0xff000000);
		ReikaGuiAPI.instance.drawLine(152+dx, 53, 168+dx, 53, 0xff000000);
		ReikaGuiAPI.instance.drawLine(168+dx, 53, 168+dx, 69, 0xff000000);
		ReikaGuiAPI.instance.drawLine(152+dx, 69, 168+dx, 69, 0xff000000);

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

		//ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png");
		//this.drawTexturedModalRect(j+xSize/2-232/2, k+150, 0, 64, 232, 37);

		input.drawKeys();
	}

	@Override
	protected String getGuiTexture() {
		return "musicgui";
	}

	@Override
	public int getActiveChannel() {
		return activeChannel;
	}

	@Override
	public void onKeyPressed(PianoKey key) {
		this.actionPerformed(key);
	}

	@Override
	public int getColorForChannel(int channel) {
		return TileEntityMusicBox.getColorForChannel(channel);
	}

	@Override
	public void bindKeyboardTexture() {
		ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png");
	}

}
