/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Registry.HandbookRegistry;

public class GuiHandbook extends GuiScreen
{

	private int mx;
	private int my;
	protected final int xSize = 256;
	protected final int ySize = 220;
	public World worldObj;
	private EntityPlayer player;

	/** One second in nanoseconds. */
	public static final long SECOND = 2000000000;

	private static final int descX = 8;
	private static final int descY = 88;

	protected byte screen = 0;
	protected byte page = 0;
	protected byte subpage = 0;
	private byte bcg;
	private int tickcount;

	public static long time;
	private long buttontime;
	public static int i = 0;
	private int buttoni = 0;
	protected int buttontimer = 0;

	private static int staticwidth;
	private static int staticheight;

	public static final String icons1 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookicons.png";
	public static final String icons2 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookicons2.png";
	public static final String terrain = "/Reika/RotaryCraft/Textures/Terrain/textures.png";
	public static final String vanillaitems = "/gui/items.png";

	public static final int TOCSTART = 0;
	public static final int INFOSTART = 1;
	public static final int ENGINESTART = 3;
	public static final int TRANSSTART = 5;
	public static final int MACHINESTART = 7;
	public static final int TOOLSTART = 14;
	public static final int CRAFTSTART = 16;
	public static final int RESOURCESTART = 22;
	public static final int MISCSTART = 2;

	public static final int MAXPAGE = 24;

	@SuppressWarnings("unused")
	public GuiHandbook(EntityPlayer p5ep, World world, int s, int p)
	{
		//super();
		player = p5ep;
		worldObj = world;
		if (MAXPAGE < MISCSTART) {
			ReikaJavaLibrary.spamConsole("HANDBOOK MAX PAGE IS TOO LOW, CANNOT ACCESS HIGH PAGES!");
		}
		staticwidth = xSize;
		staticheight = ySize;

		screen = (byte)s;
		page = (byte)p;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2 - 8;

		String file = HandbookRegistry.TOC.getTabImageFile();
		buttonList.add(new ImagedGuiButton(10, j-20, 17+k+163, 20, 20, "-", 220, 0, 0, false, file)); //Prev Page
		buttonList.add(new ImagedGuiButton(11, j-20, 17+k+143, 20, 20, "+", 220, 20, 0, false, file));	//Next page
		buttonList.add(new ImagedGuiButton(15, j-20, 17+k+183, 20, 20, "<<", 220, 20, 0, false, file));	//Next page
		buttonList.add(new GuiButton(12, j+xSize-27, k+6, 20, 20, "X"));	//Close gui button

		if (screen >= ENGINESTART && screen < CRAFTSTART) {
			buttonList.add(new GuiButton(13, j+xSize-27, k+40, 20, 20, ">"));
			buttonList.add(new GuiButton(14, j+xSize-27, k+60, 20, 20, "<"));
		}
		HandbookRegistry.addRelevantButtons(j, k, screen, buttonList);
	}


	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 12) {
			mc.thePlayer.closeScreen();
			return;
		}
		if (buttontimer > 0)
			return;
		buttontimer = 20;
		if (button.id == 15) {
			screen = 0;
			page = 0;
			subpage = 0;
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (button.id == 10) {
			if (screen > 0) {
				screen--;
				page = 0;
				subpage = 0;
			}
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (button.id == 11) {
			if (screen < MAXPAGE) {
				screen++;
				page = 0;
				subpage = 0;
			}
			else {
				screen = 0;
				page = 0;
				subpage = 0;
			}
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (screen >= TOCSTART && screen < INFOSTART) {
			switch(button.id) {
			case 0:
				screen = INFOSTART;
				break;
			case 1:
				screen = ENGINESTART;
				break;
			case 2:
				screen = TRANSSTART;
				break;
			case 3:
				screen = MACHINESTART;
				break;
			case 4:
				screen = TOOLSTART;
				break;
			case 5:
				screen = CRAFTSTART;
				break;
			case 6:
				screen = RESOURCESTART;
				break;
			case 7:
				screen = MISCSTART;
				break;
			}
			this.initGui();
			page = 0;
			subpage = 0;
			return;
		}
		if (button.id == 13) {
			if (subpage != 1)
				subpage++;
			this.initGui();
			return;
		}
		if (button.id == 14) {
			if (subpage != 0)
				subpage--;
			this.initGui();
			return;
		}
		time = System.nanoTime();
		i = 0;
		page = (byte)button.id;
		subpage = 0;
	}

	public void refreshScreen() {
		int lastx = mx;
		int lasty = my;
		mc.thePlayer.closeScreen();
		//ModLoader.openGUI(player, new GuiHandbook(player, worldObj));
		Mouse.setCursorPosition(lastx, lasty);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		mx = Mouse.getX();
		my = Mouse.getY();
	}

	/** 0 = crafting, 1 = plain, 2 = smelt, 3 = extractor, 4 = compressor, 5 = fermenter, 6 = fractionator, 7 = grinder, 8 = blast */
	public byte getGuiLayout() {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h.isPlainGui())
			return 1;
		if (subpage == 1)
			return 1;
		if (h == HandbookRegistry.STEELINGOT)
			return 8;
		if (h == HandbookRegistry.EXTRACTS)
			return 3;
		if (h == HandbookRegistry.FLAKES)
			return 2;
		if (h == HandbookRegistry.COMPACTS)
			return 4;
		if (h == HandbookRegistry.GLASS)
			return 2;
		if (h == HandbookRegistry.NETHERDUST)
			return 2;
		if (h == HandbookRegistry.YEAST)
			return 5;
		if (h == HandbookRegistry.ETHANOL)
			return 2;
		if (h == HandbookRegistry.SILVERINGOT)
			return 2;
		if (h == HandbookRegistry.BUCKETS) {
			if (System.nanoTime()-time > SECOND*2) {
				i++;
				time = System.nanoTime();
				if (i > 1)
					i = 0;
			}
			return (byte)(7-i);
		}

		return 0;
	}

	public void drawRecipes() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(icons1);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		HandbookAuxData.drawPage(fontRenderer, screen, page, subpage, posX, posY);
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		if (System.nanoTime()-buttontime > SECOND/20) {
			buttoni++;
			buttontime = System.nanoTime();
			buttontimer = 0;
		}
		//drawDefaultBackground();
		String var4;

		bcg = this.getGuiLayout();

		switch(bcg) {
		case 0:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookgui.png";
			break;
		case 1:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguib.png";
			break;
		case 2:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguic.png";
			break;
		case 3:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguid.png";
			break;
		case 4:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguie.png";
			break;
		case 5:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguif.png";
			break;
		case 6:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguig.png";
			break;
		case 7:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguih.png";
			break;
		case 8:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguij.png";
			break;
		default:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguib.png"; //default to plain gui
			break;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		int xo = 0;
		int yo = 0;
		fontRenderer.drawString(HandbookRegistry.getEntry(screen, page).getTitle(), posX+xo+6, posY+yo+6, 0x000000);
		if (subpage == 0) {
			this.drawRecipes();
			fontRenderer.drawSplitString(String.format("%s", RotaryDescriptions.data[screen*8+page][0]), posX+descX, posY+descY, 242, 0xffffff);
			//fontRenderer.drawSplitString(String.format("%s", HandbookText.machineInfo[screen*8+page]), posX+descX, posY+descY, 242, 0xffffff);
		}
		else
			fontRenderer.drawSplitString(String.format("%s", RotaryDescriptions.data[screen*8+page][1]), posX+descX, posY+descY, 242, 0xffffff);
		//fontRenderer.drawSplitString(String.format("%s", HandbookText.machineNotes[screen*8+page]), posX+descX, posY+descY, 242, 0xffffff);


		if (screen == INFOSTART && page == 0) {
			int xc = posX+xSize/2; int yc = posY+43; int r = 35;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, xc+r, yc, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+r-0.459*r), (int)(yc-0.841*r), 0);/*
    		for (float i = 0; i < 1; i += 0.1)
    			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(i)*r), (int)(yc-Math.sin(i)*r), 0x000000);*/
			String s = "One radian";
			fontRenderer.drawString(s, xc+r+10, yc-4, 0x000000);
		}

		if (screen == INFOSTART && page == 1) {
			int xc = posX+xSize/8; int yc = posY+45; int r = 5;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, xc+45, yc, 0x0000ff);
			ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+45, yc+20, 0xff0000);
			ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+50, yc+5, 0xff0000);
			ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+40, yc+5, 0xff0000);
			fontRenderer.drawString("Distance", xc+4, yc-10, 0x0000ff);
			fontRenderer.drawString("Force", xc+30, yc+20, 0xff0000);

			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc-1.4*r), xc-r, yc-r*2-2, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc-1.4*r), xc-2*r-2, yc, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc+1.4*r), xc-2*r-2, yc, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc+1.4*r), xc-r, yc+r*2+2, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-r, yc+r*2+2, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2+7, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2-3, 0x8800ff);
			fontRenderer.drawString("Torque", xc-24, yc+18, 0x8800ff);

			r = 35; xc = posX+xSize/2+r+r/2; yc = posY+43;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			double a = 57.3*System.nanoTime()/1000000000%360;
			double b = 3*57.3*System.nanoTime()/1000000000%360;
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(a))*r), (int)(yc+Math.sin(Math.toRadians(a))*r), 0xff0000);
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(b))*r), (int)(yc+Math.sin(Math.toRadians(b))*r), 0x0000ff);

			fontRenderer.drawString("1 rad/s", xc+r-4, yc+18, 0xff0000);
			fontRenderer.drawString("3 rad/s", xc+r-4, yc+18+10, 0x0000ff);
		}


		super.drawScreen(x, y, f);
	}
}
