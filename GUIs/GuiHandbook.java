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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Instantiable.ItemReq;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.MobBait;
import Reika.RotaryCraft.TileEntities.TileEntityTerraformer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;

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

	protected int screen = 0;
	protected int page = 0;
	protected int subpage = 0;
	private byte bcg;
	private int tickcount;

	public static long time;
	private long buttontime;
	public static int i = 0;
	private int buttoni = 0;
	protected int buttontimer = 0;

	private static int staticwidth;
	private static int staticheight;

	private float renderq = 22.5F;

	public GuiHandbook(EntityPlayer p5ep, World world, int s, int p)
	{
		//super();
		player = p5ep;
		worldObj = world;
		staticwidth = xSize;
		staticheight = ySize;

		screen = s;
		page = p;

		if (ConfigRegistry.DYNAMICHANDBOOK.getState())
			RotaryDescriptions.reload();
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2 - 8;

		String file = HandbookRegistry.TOC.getTabImageFile();
		buttonList.add(new ImagedGuiButton(10, j-20, 17+k+163, 20, 20, "-", 220, 0, 0, false, file, RotaryCraft.class)); //Prev Page
		buttonList.add(new ImagedGuiButton(11, j-20, 17+k+143, 20, 20, "+", 220, 20, 0, false, file, RotaryCraft.class));	//Next page
		buttonList.add(new ImagedGuiButton(15, j-20, 17+k+183, 20, 20, "<<", 220, 20, 0, false, file, RotaryCraft.class));	//Next page
		buttonList.add(new GuiButton(12, j+xSize-27, k+6, 20, 20, "X"));	//Close gui button

		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);

		if (h.hasSubpages()) {
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

	public int getMaxPage() {
		return HandbookRegistry.RESOURCEDESC.getScreen()+HandbookRegistry.RESOURCEDESC.getNumberChildren()/8;
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
			renderq = 22.5F;
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
			renderq = 22.5F;
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (button.id == 11) {
			if (screen < this.getMaxPage()) {
				screen++;
				page = 0;
				subpage = 0;
			}
			else {
				screen = 0;
				page = 0;
				subpage = 0;
			}
			renderq = 22.5F;
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (screen == HandbookRegistry.TOC.getScreen()) {
			switch(button.id) {
			case 0:
				screen = HandbookRegistry.TERMS.getScreen();
				break;
			case 1:
				screen = HandbookRegistry.MISCDESC.getScreen();
				break;
			case 2:
				screen = HandbookRegistry.ENGINEDESC.getScreen();
				break;
			case 3:
				screen = HandbookRegistry.TRANSDESC.getScreen();
				break;
			case 4:
				screen = HandbookRegistry.PRODMACHINEDESC.getScreen();
				break;
			case 5:
				screen = HandbookRegistry.TOOLDESC.getScreen();
				break;
			case 6:
				screen = HandbookRegistry.RESOURCEDESC.getScreen();
				break;
			}
			this.initGui();
			page = 0;
			subpage = 0;
			renderq = 22.5F;
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
		page = button.id;
		subpage = 0;
		renderq = 22.5F;
		this.initGui();
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
		if (h == HandbookRegistry.BAITBOX && subpage == 1)
			return 9;
		if (h == HandbookRegistry.TERRA && subpage == 1)
			return 10;
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
			if ((System.nanoTime()/SECOND)%2 == 0)
				return 6;
			else
				return 7;
		}

		if (h.isMachine() || h.isEngine() || h.isTrans()) {
			return 11;
		}

		return 0;
	}

	private void drawRecipes() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		try {
			HandbookAuxData.drawPage(fontRenderer, screen, page, subpage, posX, posY);
		}
		catch (Exception e) {
			ReikaChatHelper.write(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void drawTabIcons() {
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		List<HandbookRegistry> li = HandbookRegistry.getEntriesForScreen(screen);
		for (int i = 0; i < li.size(); i++) {
			HandbookRegistry h = li.get(i);
			ReikaGuiAPI.instance.drawItemStack(new RenderItem(), fontRenderer, h.getTabIcon(), posX-17, posY-6+i*20);
		}
	}

	private void drawGraphics() {
		int posX = (width - xSize) / 2-2;
		int posY = (height - ySize) / 2-8;

		if (!(this instanceof GuiHandbookPage))
			ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, String.format("Page %d", screen), posX+xSize-45, posY+12, 0);

		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);

		if (h == HandbookRegistry.TERMS) {
			int xc = posX+xSize/2; int yc = posY+43; int r = 35;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, xc+r, yc, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+r-0.459*r), (int)(yc-0.841*r), 0);/*
    		for (float i = 0; i < 1; i += 0.1)
    			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(i)*r), (int)(yc-Math.sin(i)*r), 0x000000);*/
			String s = "One radian";
			fontRenderer.drawString(s, xc+r+10, yc-4, 0x000000);
		}

		if (h == HandbookRegistry.PHYSICS) {
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
		if (h == HandbookRegistry.BAITBOX && subpage == 1) {
			RenderItem ri = new RenderItem();
			int k = (int)((System.nanoTime()/2000000000)%MobBait.baitList.length);
			MobBait b = MobBait.baitList[k];
			int u = b.getMobIconU();
			int v = b.getMobIconV();
			ItemStack is1 = b.getAttractorItemStack();
			ItemStack is2 = b.getRepellentItemStack();
			ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, is1, posX+162, posY+27);
			ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, is2, posX+162, posY+27+18);
			String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
			int UNIT = 4;
			this.drawTexturedModalRect(posX+88-UNIT/2, posY+41-UNIT/2, u, v, UNIT*2, UNIT*2);
			fontRenderer.drawString("Attractor", posX+110, posY+30, 0);
			fontRenderer.drawString("Repellent", posX+110, posY+48, 0);
			//RenderManager.instance.renderEntityWithPosYaw(new EntityCreeper(Minecraft.getMinecraft().theWorld), 120, 60, 0, 0, 0);
		}
		if (h == HandbookRegistry.TERRA && subpage == 1) {
			RenderItem ri = new RenderItem();
			ArrayList<Object[]> transforms = TileEntityTerraformer.getTransformList();
			int time = 2000000000;
			int k = (int)((System.nanoTime()/time)%transforms.size());
			String tex = "/Reika/RotaryCraft/Textures/GUI/biomes.png";
			ReikaTextureHelper.bindTexture(RotaryCraft.class, tex);
			Object[] data = transforms.get(k);
			BiomeGenBase from = (BiomeGenBase)data[0];
			BiomeGenBase from_ = from;
			from = ReikaBiomeHelper.getParentBiomeType(from);
			BiomeGenBase to = (BiomeGenBase)data[1];
			this.drawTexturedModalRect(posX+16, posY+22, 32*(from.biomeID%8), 32*(from.biomeID/8), 32, 32);
			this.drawTexturedModalRect(posX+80, posY+22, 32*(to.biomeID%8), 32*(to.biomeID/8), 32, 32);
			String name = ReikaStringParser.splitCamelCase(from_.biomeName);
			String[] words = name.split(" ");
			for (int i = 0; i < words.length; i++) {
				ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, words[i], posX+33, posY+57+i*fontRenderer.FONT_HEIGHT, 0);
			}
			String name2 = ReikaStringParser.splitCamelCase(to.biomeName);
			String[] words2 = name2.split(" ");
			for (int i = 0; i < words2.length; i++) {
				ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, words2[i], posX+97, posY+57+i*fontRenderer.FONT_HEIGHT, 0);
			}
			fontRenderer.drawString(String.format("%.3f kW", (Integer)data[2]/1000D), posX+116, posY+22, 0);
			FluidStack liq = (FluidStack)data[3];
			if (liq != null) {
				GL11.glColor4f(1, 1, 1, 1);
				ReikaLiquidRenderer.bindFluidTexture(liq.getFluid());
				Icon ico = liq.getFluid().getIcon();
				this.drawTexturedModelRectFromIcon(posX+116, posY+38, ico, 16, 16);
				this.drawTexturedModelRectFromIcon(posX+116+16, posY+38, ico, 16, 16);
				//ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, liq.asItemStack(), posX+116, posY+38);
				//ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, liq.asItemStack(), posX+116+16, posY+38);
				ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, String.format("%d", liq.amount), posX+116+16, posY+38+5, 0);
			}
			List<ItemReq> li = (List<ItemReq>)data[4];
			for (int i = 0; i < li.size(); i++) {
				ItemStack is = li.get(i).asItemStack();
				ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, is, posX+190, posY+8+i*18);
			}
		}
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
		case 9:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguik.png";
			break;
		case 10:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguil.png";
			break;
		case 11:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguim.png";
			break;
		default:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguib.png"; //default to plain gui
			break;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		int xo = 0;
		int yo = 0;
		fontRenderer.drawString(HandbookRegistry.getEntry(screen, page).getTitle(), posX+xo+6, posY+yo+6, 0x000000);
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (subpage == 0) {
			fontRenderer.drawSplitString(String.format("%s", h.getData()), posX+descX, posY+descY, 242, 0xffffff);
		}
		else {
			fontRenderer.drawSplitString(String.format("%s", h.getNotes()), posX+descX, posY+descY, 242, 0xffffff);
		}

		this.drawGraphics();

		super.drawScreen(x, y, f);

		if (subpage == 0)
			this.drawRecipes();

		if (!(this instanceof GuiHandbookPage)) {
			this.drawTabIcons();
		}

		if (subpage == 0)
			this.drawMachineRender(posX, posY);
	}

	private void drawMachineRender(int posX, int posY) {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		double x = posX+167;
		double y = posY+44;
		//float q = 12.5F + fscale*(float)Math.sin(System.nanoTime()/1000000000D); //wobble
		//ReikaJavaLibrary.pConsole(y-ReikaGuiAPI.instance.getMouseScreenY(height));
		int range = 64;
		boolean rotate = ReikaGuiAPI.instance.isMouseInBox((int)x-range/2, (int)x+range/2, (int)y-range, (int)y+range);
		if (Mouse.isButtonDown(0) && rotate) {
			int mvy = Mouse.getDY();
			if (mvy < 0 && renderq < 45) {
				renderq++;
			}
			if (mvy > 0 && renderq > -45) {
				renderq--;
			}
		}
		y -= 8*Math.sin(Math.abs(Math.toRadians(renderq)));
		float p8 = 0;
		MaterialRegistry[] mats = MaterialRegistry.values();
		int mat = (int)((System.nanoTime()/SECOND)%mats.length);

		if (h.isMachine() || h.isEngine() || h.isTrans()) {
			MachineRegistry m = h.getMachine();
			if (m != null) {
				int r = (int)(System.nanoTime()/20000000)%360;
				TileEntity te = m.createTEInstanceForRender();
				if (h.isEngine() && h != HandbookRegistry.SOLAR) {
					((TileEntityEngine)te).type = EnumEngineType.engineList[h.getOffset()];
					p8 = -1000F*(h.getOffset()+1);
				}
				if (h == HandbookRegistry.SHAFT) {
					p8 = -1000F*(mat+1);
				}
				if (h == HandbookRegistry.FLYWHEEL) {
					int tick = (int)((System.nanoTime()/SECOND)%RotaryNames.getNumberFlywheelTypes());
					p8 = 500-1000F*(tick+1);
				}
				if (h == HandbookRegistry.GEARBOX) {
					p8 = -1000F*(mat+1);
				}
				if (h == HandbookRegistry.WORM) {
					p8 = -1000F;
				}
				if (h == HandbookRegistry.CVT) {
					p8 = -2000F;
				}
				if (h == HandbookRegistry.COIL) {
					p8 = -3000F;
				}
				double sc = 48;
				if (m.hasModel()) {
					double dx = -x;
					double dy = -y-21;
					double dz = 0;
					GL11.glTranslated(-dx, -dy, -dz);
					GL11.glScaled(sc, -sc, sc);
					GL11.glRotatef(renderq, 1, 0, 0);
					GL11.glRotatef(r, 0, 1, 0);
					TileEntityRenderer.instance.renderTileEntityAt(te, -0.5, 0, -0.5, p8);
					GL11.glRotatef(-r, 0, 1, 0);
					GL11.glRotatef(-renderq, 1, 0, 0);
					GL11.glTranslated(-dx, -dy, -dz);
					GL11.glScaled(1D/sc, -1D/sc, 1D/sc);
				}
				else {
					double dx = x;
					double dy = y;
					double dz = 0;
					GL11.glTranslated(dx, dy, dz);
					GL11.glScaled(sc, -sc, sc);
					GL11.glRotatef(renderq, 1, 0, 0);
					GL11.glRotatef(r, 0, 1, 0);
					RenderBlocks rb = new RenderBlocks();
					ReikaTextureHelper.bindTerrainTexture();
					rb.renderBlockAsItem(m.getBlockVariable(), m.getMachineMetadata(), 1);
					GL11.glRotatef(-r, 0, 1, 0);
					GL11.glRotatef(-renderq, 1, 0, 0);
					GL11.glScaled(1D/sc, -1D/sc, 1D/sc);
					GL11.glTranslated(-dx, -dy, -dz);
				}
			}
		}
	}
}
