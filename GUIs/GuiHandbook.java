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

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.Interfaces.HandbookEntry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

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

	protected float renderq = 22.5F;

	protected static final RenderBlocks rb = new RenderBlocks();

	protected static final RenderItem ri = new RenderItem();

	public GuiHandbook(EntityPlayer p5ep, World world, int s, int p)
	{
		//super();
		player = p5ep;
		worldObj = world;
		staticwidth = xSize;
		staticheight = ySize;

		screen = s;
		page = p;

		if (ConfigRegistry.DYNAMICHANDBOOK.getState() || (DragonAPICore.isReikasComputer() && ReikaObfuscationHelper.isDeObfEnvironment()))
			this.reloadXMLData();
	}

	protected void reloadXMLData() {
		RotaryDescriptions.reload();
	}

	@Override
	public final void initGui() {
		super.initGui();
		buttonList.clear();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2 - 8;

		String file = HandbookRegistry.TOC.getTabImageFile();
		if (!this.isLimitedView()) {
			buttonList.add(new ImagedGuiButton(10, j-20, 17+k+163, 20, 20, 220, 0, "-", 0, false, file, RotaryCraft.class)); //Prev Page
			buttonList.add(new ImagedGuiButton(11, j-20, 17+k+143, 20, 20, 220, 20, "+", 0, false, file, RotaryCraft.class));	//Next page
			buttonList.add(new ImagedGuiButton(15, j-20, 17+k+183, 20, 20, 220, 20, "<<", 0, false, file, RotaryCraft.class));	//First page
		}
		buttonList.add(new GuiButton(12, j+xSize-27, k+6, 20, 20, "X"));	//Close gui button

		HandbookEntry h = this.getEntry();

		if (h.hasSubpages()) {
			buttonList.add(new GuiButton(13, j+xSize-27, k+40, 20, 20, ">"));
			buttonList.add(new GuiButton(14, j+xSize-27, k+60, 20, 20, "<"));
		}
		if (!this.isLimitedView())
			this.addTabButtons(j, k);
	}


	protected void addTabButtons(int j, int k) {
		HandbookRegistry.addRelevantButtons(j, k, screen, buttonList);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	@Override
	public final boolean doesGuiPauseGame()
	{
		return true;
	}

	public int getMaxPage() {
		return HandbookRegistry.RESOURCEDESC.getScreen()+HandbookRegistry.RESOURCEDESC.getNumberChildren()/8;
	}

	public int getMaxSubpage() {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h == HandbookRegistry.TIERS)
			return HandbookAuxData.getPowerDataSize()-1;
		if (h == HandbookRegistry.COMPUTERCRAFT)
			return MachineRegistry.machineList.length/36+1;
		return 1;
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
		if (this.isOnTOC()) {
			screen = this.getNewScreenByTOCButton(button.id+screen*8);
			this.initGui();
			page = 0;
			subpage = 0;
			renderq = 22.5F;
			return;
		}
		if (button.id == 13) {
			if (subpage < this.getMaxSubpage())
				subpage++;
			this.initGui();
			return;
		}
		if (button.id == 14) {
			if (subpage > 0)
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

	protected boolean isOnTOC() {
		return this.getEntry() == HandbookRegistry.TOC;
	}

	protected int getNewScreenByTOCButton(int id) {
		switch(id) {
		case 0:
			return HandbookRegistry.TERMS.getScreen();
		case 1:
			return HandbookRegistry.MISCDESC.getScreen();
		case 2:
			return HandbookRegistry.ENGINEDESC.getScreen();
		case 3:
			return HandbookRegistry.TRANSDESC.getScreen();
		case 4:
			return HandbookRegistry.PRODMACHINEDESC.getScreen();
		case 5:
			return HandbookRegistry.PROCMACHINEDESC.getScreen();
		case 6:
			return HandbookRegistry.FARMMACHINEDESC.getScreen();
		case 7:
			return HandbookRegistry.ACCMACHINEDESC.getScreen();
		case 8:
			return HandbookRegistry.WEPMACHINEDESC.getScreen();
		case 9:
			return HandbookRegistry.SURVMACHINEDESC.getScreen();
		case 10:
			return HandbookRegistry.COSMACHINEDESC.getScreen();
		case 11:
			return HandbookRegistry.UTILMACHINEDESC.getScreen();
		case 12:
			return HandbookRegistry.TOOLDESC.getScreen();
		case 13:
			return HandbookRegistry.RESOURCEDESC.getScreen();
		}
		return 0;
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
	protected PageType getGuiLayout() {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h.isPlainGui())
			return PageType.PLAIN;
		if (h == HandbookRegistry.BAITBOX && subpage == 1)
			return PageType.ANIMALBAIT;
		if (h == HandbookRegistry.TERRA && subpage == 1)
			return PageType.TERRAFORMER;
		if (h == HandbookRegistry.TIERS)
			return PageType.GREYBOX;
		if (h == HandbookRegistry.TIMING)
			return PageType.GREYBOX;
		if (subpage == 1)
			return PageType.PLAIN;
		if (h == HandbookRegistry.STEELINGOT)
			return PageType.BLASTFURNACE;
		if (h == HandbookRegistry.EXTRACTS)
			return PageType.EXTRACTOR;
		if (h == HandbookRegistry.FLAKES)
			return PageType.SMELTING;
		if (h == HandbookRegistry.COMPACTS)
			return PageType.COMPACTOR;
		if (h == HandbookRegistry.GLASS)
			return PageType.SMELTING;
		if (h == HandbookRegistry.NETHERDUST)
			return PageType.SMELTING;
		if (h == HandbookRegistry.YEAST)
			return PageType.FERMENTER;
		if (h == HandbookRegistry.ETHANOL)
			return PageType.SMELTING;
		if (h == HandbookRegistry.SILVERINGOT)
			return PageType.SMELTING;
		if (h == HandbookRegistry.BUCKETS) {
			if ((System.nanoTime()/SECOND)%2 == 0)
				return PageType.FRACTIONATOR;
			else
				return PageType.GRINDER;
		}
		if (h == HandbookRegistry.BEDTOOLS || h == HandbookRegistry.BEDARMOR)
			return PageType.BLASTFURNACE;
		if (h == HandbookRegistry.BEDINGOT)
			return PageType.BLASTFURNACE;
		if (h == HandbookRegistry.STRONGSPRING)
			return PageType.BLASTFURNACE;

		if (h.isMachine() || h.isEngine() || h.isTrans()) {
			return PageType.MACHINERENDER;
		}

		return PageType.CRAFTING;
	}

	protected static enum PageType {
		PLAIN("b"),
		CRAFTING(""),
		SMELTING("c"),
		EXTRACTOR("d"),
		COMPACTOR("e"),
		FERMENTER("f"),
		FRACTIONATOR("g"),
		GRINDER("h"),
		BLASTFURNACE("j"),
		ANIMALBAIT("k"),
		TERRAFORMER("l"),
		MACHINERENDER("m"),
		GREYBOX("n");

		private final String endString;

		private PageType(String s) {
			endString = s;
		}

		public String getFileName() {
			return "handbookgui"+endString;
		}
	}

	public final String getBackgroundTexture() {
		PageType type = this.getGuiLayout();
		String var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/"+type.getFileName()+".png";
		return var4;
	}

	private final void drawRecipes() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		try {
			this.drawAuxData(posX, posY);
		}
		catch (Exception e) {
			ReikaChatHelper.write(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	protected void drawAuxData(int posX, int posY) {
		HandbookAuxData.drawPage(fontRendererObj, ri, screen, page, subpage, posX, posY);
	}

	private final void drawTabIcons() {
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		List<HandbookEntry> li = this.getAllTabsOnScreen();
		for (int i = 0; i < li.size(); i++) {
			HandbookEntry h = li.get(i);
			ReikaGuiAPI.instance.drawItemStack(ri, fontRendererObj, h.getTabIcon(), posX-17, posY-6+i*20);
		}
	}

	public List<HandbookEntry> getAllTabsOnScreen() {
		List<HandbookRegistry> li = HandbookRegistry.getEntriesForScreen(screen);
		return new ArrayList(li);
	}

	private final void drawGraphics() {
		int posX = (width - xSize) / 2-2;
		int posY = (height - ySize) / 2-8;

		if (!this.isLimitedView()) {
			ReikaRenderHelper.disableLighting();
			String s = String.format("Page %d/%d", screen, this.getMaxPage());
			//ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, s, posX+xSize+23, posY+5, 0xffffff);
			ReikaGuiAPI.instance.drawTooltipAt(fontRendererObj, s, posX+12+xSize+fontRendererObj.getStringWidth(s), posY+20);
		}

		this.drawAuxGraphics(posX, posY);
	}

	protected void drawAuxGraphics(int posX, int posY) {
		HandbookAuxData.drawGraphics((HandbookRegistry)this.getEntry(), posX, posY, xSize, ySize, fontRendererObj, ri, subpage);
	}

	@Override
	public final void drawScreen(int x, int y, float f)
	{
		if (System.nanoTime()-buttontime > SECOND/20) {
			buttoni++;
			buttontime = System.nanoTime();
			buttontimer = 0;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.bindTexture();

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		int xo = 0;
		int yo = 0;
		HandbookEntry h = this.getEntry();
		fontRendererObj.drawString(h.getTitle(), posX+xo+6, posY+yo+6, 0x000000);
		if (subpage == 0 || h.sameTextAllSubpages()) {
			fontRendererObj.drawSplitString(String.format("%s", h.getData()), posX+descX, posY+descY, 242, 0xffffff);
		}
		else {
			fontRendererObj.drawSplitString(String.format("%s", h.getNotes()), posX+descX, posY+descY, 242, 0xffffff);
		}

		super.drawScreen(x, y, f);

		if (subpage == 0)
			this.drawRecipes();

		if (!this.isLimitedView()) {
			this.drawTabIcons();
		}

		this.drawGraphics();

		if (subpage == 0)
			this.drawMachineRender(posX, posY);

		RenderHelper.disableStandardItemLighting();
	}

	protected HandbookEntry getEntry() {
		return HandbookRegistry.getEntry(screen, page);
	}

	protected void bindTexture() {
		String var4 = this.getBackgroundTexture();
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
	}

	public boolean isLimitedView() {
		return false;
	}

	protected void doRenderMachine(double x, double y, HandbookEntry he) {
		HandbookRegistry h = (HandbookRegistry)he;
		MachineRegistry m = h.getMachine();
		if (m == null)
			return;
		MaterialRegistry[] mats = MaterialRegistry.values();
		TileEntity te = m.createTEInstanceForRender(0);
		int timeStep = (int)((System.nanoTime()/SECOND)%mats.length);
		int r = (int)(System.nanoTime()/20000000)%360;
		float variable = 0;
		if (h.isEngine() && h != HandbookRegistry.SOLAR) {
			//((TileEntityEngine)te).type = EngineType.engineList[h.getOffset()];
			variable = -1000F*(h.getOffset()+1);
		}
		if (h == HandbookRegistry.SHAFT) {
			variable = -1000F*(timeStep+1);
		}
		if (h == HandbookRegistry.FLYWHEEL) {
			int tick = (int)((System.nanoTime()/SECOND)%RotaryNames.getNumberFlywheelTypes());
			variable = 500-1000F*(tick+1);
		}
		if (h == HandbookRegistry.GEARBOX) {
			variable = -1000F*(timeStep+1);
		}
		if (h == HandbookRegistry.WORM) {
			variable = -1000F;
		}
		if (h == HandbookRegistry.CVT) {
			variable = -2000F;
		}
		if (h == HandbookRegistry.COIL) {
			int tick = (int)((System.nanoTime()/SECOND)%2);
			if (tick == 1)
				((TileEntityAdvancedGear)te).setBedrock(true);
			variable = -3000F;
		}
		double sc = 48;
		if (m.hasModel() && !m.isPipe()) {
			double dx = x;
			double dy = y+21;
			double dz = 0;
			GL11.glTranslated(dx, dy, dz);
			GL11.glScaled(sc, -sc, sc);
			GL11.glRotatef(renderq, 1, 0, 0);
			GL11.glRotatef(r, 0, 1, 0);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(te, -0.5, 0, -0.5, variable);
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
			ReikaTextureHelper.bindTerrainTexture();
			rb.renderBlockAsItem(m.getBlock(), m.getMachineMetadata(), 1);
			GL11.glRotatef(-r, 0, 1, 0);
			GL11.glRotatef(-renderq, 1, 0, 0);
			GL11.glScaled(1D/sc, -1D/sc, 1D/sc);
			GL11.glTranslated(-dx, -dy, -dz);
		}
	}

	private void drawMachineRender(int posX, int posY) {
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glTranslated(0, 0, 32);
		HandbookEntry h = this.getEntry();
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

		if (h.hasMachineRender()) {
			this.doRenderMachine(x, y, h);
		}
		GL11.glTranslated(0, 0, -32);
	}
}