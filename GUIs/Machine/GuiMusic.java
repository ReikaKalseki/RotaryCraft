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

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.ColorButton;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Instantiable.GUI.ItemIconButton;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
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
				new ItemStack(Block.grass),
				new ItemStack(Block.planks),
				new ItemStack(Block.portal),
				new ItemStack(Block.stone),
				new ItemStack(Block.sand),
				new ItemStack(Block.glass)
		};
		for (int i = 0; i < 6; i++)
			buttonList.add(new ItemIconButton(400+i, j+152+16*i, k+53, 0, items[i]));
	}

	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);

	}

	@Override
	public void mouseClicked(int i, int j, int k) { //delete note on right-click
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
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		boolean flag = true;
		if (button.id < 24000) {
			if (button.id == 100) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+1, music, 0, 0, 0, 0); //data not used
			}
			else if (button.id == 101) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+2, music, 0, 0, 0, 0);
			}
			else if (button.id == 102) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+3, music, 0, 0, 0, 0); //demo
			}
			else if (button.id == 103) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+4, music, activeChannel, activeType.ordinal(), 0, 0); //rest
			}
			else if (button.id == 104) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+5, music, activeChannel, 0, 0, 0); //bksp
			}
			else if (button.id == 105) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+6, music, activeChannel, 0, 0, 0); //ch clr
			}
			else if (button.id == 106) {
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+7, music, 0, 0, 0, 0); //clrall
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
				ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue(), music, button.id, activeChannel, activeType.ordinal(), activeVoice.ordinal());
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

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Note Length", 51, 42, 0);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Instrument", 200, 42, 0);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Channel Select", xSize/2, 85, 0);

		int dx = (activeVoice.ordinal()-1)*16;
		int color = TileEntityMusicBox.getColorForChannel(activeChannel).getRGB();
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
	public String getGuiTexture() {
		return "musicgui";
	}

	private static class PianoKeyboard extends Gui {

		public final int guiX;
		public final int guiY;
		private final GuiMusic guiInstance;

		private final ArrayList<PianoKey> keyList = new ArrayList();

		private static final KeyShape[] shapeList = {
			KeyShape.LEFT,
			KeyShape.BLACK,
			KeyShape.MIDDLE,
			KeyShape.BLACK,
			KeyShape.MIDDLE,
			KeyShape.BLACK,
			KeyShape.RIGHT,
			KeyShape.LEFT,
			KeyShape.BLACK,
			KeyShape.MIDDLE,
			KeyShape.BLACK,
			KeyShape.RIGHT
		};

		public PianoKeyboard(int x, int y, GuiMusic gui) {
			guiX = x;
			guiY = y;
			guiInstance = gui;

			int dx = 6;
			for (int m = 0; m < 4; m++) {
				for (int i = 0; i <= 4; i += 2) {
					int id = i+1+m*12;
					keyList.add(new PianoKey(id, x+dx+i*4, y+1, this.getShapeFromIndex(id), guiInstance));
				}
				dx += 32;
				for (int i = 0; i <= 2; i += 2) {
					int id = i+8+m*12;
					keyList.add(new PianoKey(id, x+dx+i*4, y+1, this.getShapeFromIndex(id), guiInstance));
				}
				dx += 24;
			}

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

				//buttonList.add(new InvisibleButton(id, x, k+150, w, 37, ""));
				keyList.add(new PianoKey(id, x+i*4, y+1, this.getShapeFromIndex(id), guiInstance));
			}

		}

		private KeyShape getShapeFromIndex(int i) {
			if (i == 48)
				return KeyShape.WHITE;
			return shapeList[i%shapeList.length];
		}

		public void mouseClicked(int x, int y, int button)
		{
			for (int i = 0; i < keyList.size(); i++) {
				PianoKey key = keyList.get(i);
				if (key.mousePressed(Minecraft.getMinecraft(), x, y)) {
					guiInstance.actionPerformed(key);
					return;
				}
			}
		}

		public void drawKeys()
		{
			ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/GUI/musicbuttons.png");
			this.drawTexturedModalRect(guiX, guiY, 0, 64, 232, 37);

			Minecraft mc = Minecraft.getMinecraft();
			GL11.glEnable(GL11.GL_BLEND);
			for (int i = 0; i < keyList.size(); i++) {
				PianoKey key = keyList.get(i);
				key.drawButton(mc, 0, 0);
			}
			GL11.glDisable(GL11.GL_BLEND);

			mc.fontRenderer.drawString("F", guiX-6, guiY+28, 0);
			mc.fontRenderer.drawString("F", guiX+233, guiY+28, 0);
		}
	}

	private static class PianoKey extends GuiButton {

		public final KeyShape hitbox;
		private int alpha = 0;
		private final GuiMusic guiInstance;

		public PianoKey(int note, int x, int y, KeyShape shape, GuiMusic gui) {
			super(note, x, y, 0, 0, "");
			hitbox = shape;
			guiInstance = gui;
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			Color c2 = TileEntityMusicBox.getColorForChannel(guiInstance.activeChannel);
			Color c = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alpha);
			int rgb = c.getRGB();
			if (alpha > 0) {
				switch(hitbox) {
				case BLACK:
					this.drawRect(xPosition+1, yPosition+1, xPosition+3, yPosition+20, rgb);
					break;
				case LEFT:
					this.drawRect(xPosition+1, yPosition, xPosition+6, yPosition+35, rgb);
					this.drawRect(xPosition+6, yPosition+21, xPosition+7, yPosition+35, rgb);
					break;
				case MIDDLE:
					this.drawRect(xPosition+2, yPosition, xPosition+6, yPosition+21, rgb);
					this.drawRect(xPosition+1, yPosition+21, xPosition+7, yPosition+35, rgb);
					break;
				case RIGHT:
					this.drawRect(xPosition+2, yPosition, xPosition+7, yPosition+35, rgb);
					this.drawRect(xPosition+1, yPosition+21, xPosition+2, yPosition+35, rgb);
					break;
				case WHITE:
					this.drawRect(xPosition+1, yPosition, xPosition+7, yPosition+35, rgb);
					break;
				default:
					break;
				}
				alpha--;
			}
		}

		@Override
		public boolean mousePressed(Minecraft mc, int x, int y)
		{
			ReikaGuiAPI api = ReikaGuiAPI.instance;
			boolean flag = false;
			switch(hitbox) {
			case BLACK:
				if (api.isMouseInBox(xPosition, xPosition+4, yPosition, yPosition+21))
					flag = true;
				break;
			case LEFT:
				if (api.isMouseInBox(xPosition+1, xPosition+6, yPosition, yPosition+35))
					flag = true;
				if (api.isMouseInBox(xPosition+5, xPosition+7, yPosition+21, yPosition+35))
					flag = true;
				break;
			case MIDDLE:
				if (api.isMouseInBox(xPosition+2, xPosition+6, yPosition, yPosition+35))
					flag = true;
				if (api.isMouseInBox(xPosition+1, xPosition+7, yPosition+21, yPosition+35))
					flag = true;
				break;
			case RIGHT:
				if (api.isMouseInBox(xPosition+2, xPosition+7, yPosition, yPosition+35))
					flag = true;
				if (api.isMouseInBox(xPosition+1, xPosition+7, yPosition+21, yPosition+35))
					flag = true;
				break;
			case WHITE:
				if (api.isMouseInBox(xPosition+1, xPosition+7, yPosition, yPosition+35))
					flag = true;
				break;
			default:
				break;
			}

			if (flag)
				alpha = 255;
			//ReikaJavaLibrary.pConsole(alpha);
			return flag;
		}

	}

	private static enum KeyShape {
		WHITE(),
		BLACK(),
		LEFT(),
		RIGHT(),
		MIDDLE();

		private KeyShape() {

		}
	}

}
