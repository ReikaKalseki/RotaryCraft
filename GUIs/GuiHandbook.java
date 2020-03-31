/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.PackModificationTracker;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.HandbookNotifications;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.Interfaces.HandbookEntry;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.recipe.GuiCraftingRecipe;

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

	public static final int PAGES_PER_SCREEN = 8;

	private static final int descX = 8;
	private static final int descY = 88;

	protected int screen = 0;
	protected int page = 0;
	protected int subpage = 0;
	protected int modifier = 0;
	private byte bcg;

	public static long time;
	private long buttontime;
	public static int i = 0;
	private int buttoni = 0;
	protected int buttontimer = 0;

	private static int staticwidth;
	private static int staticheight;

	private int guiTick;

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
		guiTick = 0;

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

		if (h.hasSubpages() || ((h instanceof HandbookRegistry) && ((HandbookRegistry)h).getBonusSubpages() > 0)) {
			buttonList.add(new GuiButton(13, j+xSize-27, k+40, 20, 20, ">"));
			buttonList.add(new GuiButton(14, j+xSize-27, k+60, 20, 20, "<"));
		}
		if (!this.isLimitedView())
			this.addTabButtons(j, k);
		this.onInitGui(j, k, h);
	}

	protected void onInitGui(int j, int k, HandbookEntry h) {

	}

	protected void addTabButtons(int j, int k) {
		HandbookRegistry.addRelevantButtons(j, k, screen, buttonList);
	}

	@Override
	public final boolean doesGuiPauseGame()
	{
		return true;
	}

	public int getMaxScreen() {
		return HandbookRegistry.RESOURCEDESC.getScreen()+HandbookRegistry.RESOURCEDESC.getNumberChildren()/PAGES_PER_SCREEN;
	}

	public int getMaxPage() {
		return HandbookRegistry.getEntriesForScreen(screen).size()-1;
	}

	public int getMaxSubpage() {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h == HandbookRegistry.TIERS)
			return HandbookAuxData.getPowerDataSize()-1;
		if (h == HandbookRegistry.COMPUTERCRAFT)
			return MachineRegistry.machineList.length/36+1;
		if (h == HandbookRegistry.ALERTS)
			return HandbookNotifications.instance.getNewAlerts().size()/3;
		if (h == HandbookRegistry.PACKMODS)
			return PackModificationTracker.instance.getModifications(RotaryCraft.instance).size()/3;
		return h.hasSubpages() ? 1+h.getBonusSubpages() : h.getBonusSubpages();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
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
			this.prevScreen();
			return;
		}
		if (button.id == 11) {
			this.nextScreen();
			return;
		}
		if (this.isOnTOC()) {
			screen = this.getNewScreenByTOCButton(button.id+screen*PAGES_PER_SCREEN);
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

	private void nextScreen() {
		if (screen < this.getMaxScreen()) {
			screen++;
			page = 0;
			subpage = 0;
		}
		renderq = 22.5F;
		this.initGui();
		//this.refreshScreen();
	}

	private void prevScreen() {
		if (screen > 0) {
			screen--;
			page = 0;
			subpage = 0;
		}
		renderq = 22.5F;
		this.initGui();
		//this.refreshScreen();
	}

	private void nextPage() {
		if (page < this.getMaxPage()) {
			page++;
			subpage = 0;
		}
		else {
			this.nextScreen();
			return;
		}
		renderq = 22.5F;
		this.initGui();
		//this.refreshScreen();
	}

	private void prevPage() {
		if (page > 0) {
			page--;
			subpage = 0;
		}
		else {
			this.prevScreen();
			page = this.getMaxPage();
			return;
		}
		renderq = 22.5F;
		this.initGui();
		//this.refreshScreen();
	}

	protected boolean isOnTOC() {
		return this.getEntry() == HandbookRegistry.TOC;
	}

	protected int getNewScreenByTOCButton(int id) {
		List<HandbookRegistry> li = HandbookRegistry.getCategoryTabs(true);
		if (id >= li.size()) {
			ReikaJavaLibrary.pConsole("Could not load screen for #"+id);
			return 0;
		}
		return li.get(id).getScreen();
	}

	public void refreshScreen() {
		int lastx = mx;
		int lasty = my;
		mc.thePlayer.closeScreen();
		Mouse.setCursorPosition(lastx, lasty);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		mx = Mouse.getX();
		my = Mouse.getY();
	}

	protected PageType getGuiLayout() {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (this.isOnTOC())
			return PageType.TOC;
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
		if (h == HandbookRegistry.ALERTS)
			return PageType.BLACKBOX;
		if (h == HandbookRegistry.PACKMODS)
			return PageType.BLACKBOX;
		if (subpage >= 1)
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
		if (h == HandbookRegistry.TUNGSTEN)
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
		//if (h == HandbookRegistry.BEDINGOT)
		//	return PageType.BLASTFURNACE;
		if (h == HandbookRegistry.ALLOYING)
			return PageType.BLASTFURNACE;
		if (h == HandbookRegistry.COKE)
			return PageType.BLASTFURNACE;
		if (h == HandbookRegistry.STRONGSPRING)
			return PageType.BLASTFURNACE;

		if (h.isMachine() || h.isEngine() || h.isTrans() || h.getParent() == HandbookRegistry.CONVERTERDESC) {
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
		GREYBOX("n"),
		BLACKBOX("o"),
		SOLID("p"),
		TOC("a");

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

	private final void drawGraphics(float ptick) {
		int posX = (width - xSize) / 2-2;
		int posY = (height - ySize) / 2-8;

		if (!this.isLimitedView()) {
			ReikaRenderHelper.disableLighting();
			int msx = ReikaGuiAPI.instance.getMouseRealX();
			int msy = ReikaGuiAPI.instance.getMouseRealY();
			String s = String.format("Page %d/%d", screen, this.getMaxScreen());
			//ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, s, posX+xSize+23, posY+5, 0xffffff);
			ReikaGuiAPI.instance.drawTooltipAt(fontRendererObj, s, posX+24+xSize+fontRendererObj.getStringWidth(s), posY+20);
			if (ReikaGuiAPI.instance.isMouseInBox(posX-18, posX+2, posY+0, posY+220)) {
				String sg = "";
				List<HandbookEntry> li = this.getAllTabsOnScreen();
				int idx = (msy-posY)/20;
				if (idx >= PAGES_PER_SCREEN) {
					int diff = idx-PAGES_PER_SCREEN;
					switch(diff) {
						case 0:
							sg = "Next";
							break;
						case 1:
							sg = "Back";
							break;
						case 2:
							sg = "Return";
							break;
					}
				}
				else if (idx < li.size()) {
					HandbookEntry h = li.get(idx);
					sg = h.getTitle();
				}
				if (!sg.isEmpty())
					ReikaGuiAPI.instance.drawTooltipAt(fontRendererObj, sg, msx+fontRendererObj.getStringWidth(sg)+30, msy);
			}
		}

		if (HandbookNotifications.instance.newAlerts() || PackModificationTracker.instance.modificationsExist(RotaryCraft.instance)) {
			ReikaTextureHelper.bindFinalTexture(DragonAPICore.class, "Resources/warning.png");
			GL11.glEnable(GL11.GL_BLEND);
			Tessellator v5 = Tessellator.instance;
			int x = posX+257;
			int y = posY+18;
			int alpha = (int)(155+100*Math.sin(Math.toRadians(System.currentTimeMillis()/8%360)));
			v5.startDrawingQuads();
			v5.setColorRGBA_I(0xffffff, alpha);
			v5.addVertexWithUV(x, y+24, 0, 0, 1);
			v5.addVertexWithUV(x+24, y+24, 0, 1, 1);
			v5.addVertexWithUV(x+24, y, 0, 1, 0);
			v5.addVertexWithUV(x, y, 0, 0, 0);
			v5.draw();
			GL11.glDisable(GL11.GL_BLEND);

			int i = Mouse.getX() * width / mc.displayWidth;
			int j = height - Mouse.getY() * height / mc.displayHeight - 1;
			int dx = i-posX;
			int dy = j-posY;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(261, 377, dx) && ReikaMathLibrary.isValueInsideBoundsIncl(22, 36, dy)) {
				if (HandbookNotifications.instance.newAlerts())
					ReikaGuiAPI.instance.drawTooltip(fontRendererObj, "Some config settings have been changed.");
				if (PackModificationTracker.instance.modificationsExist(RotaryCraft.instance))
					ReikaGuiAPI.instance.drawTooltip(fontRendererObj, "The modpack has made some changes to the mod.", 0, 10);
			}
		}

		this.drawAuxGraphics(posX, posY, ptick);
	}

	@Override
	protected final void mouseClicked(int x, int y, int a) {
		super.mouseClicked(x, y, a);
		int j = (width - xSize) / 2-2;
		int k = (height - ySize) / 2-8;
		if (a == 0) {
			int dx = x-j;
			int dy = y-k;
			if (HandbookNotifications.instance.newAlerts() || PackModificationTracker.instance.modificationsExist(RotaryCraft.instance)) {
				if (ReikaMathLibrary.isValueInsideBoundsIncl(261, 377, dx) && ReikaMathLibrary.isValueInsideBoundsIncl(22, 36, dy)) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					int screen = -1;
					int page = -1;

					if (HandbookNotifications.instance.newAlerts()) {
						screen = HandbookRegistry.ALERTS.getScreen();
						page = HandbookRegistry.ALERTS.getPage();
					}
					else if (PackModificationTracker.instance.modificationsExist(RotaryCraft.instance)) {
						screen = HandbookRegistry.PACKMODS.getScreen();
						page = HandbookRegistry.PACKMODS.getPage();
					}

					if (screen >= 0 && page >= 0) {
						this.screen = screen;
						this.page = page;
						this.initGui();
						HandbookNotifications.instance.clearAlert();
					}
				}
			}
		}
	}

	@Override
	public final void keyTyped(char c, int key) {
		super.keyTyped(c, key);

		if (ModList.NEI.isLoaded() && key == NEIClientConfig.getKeyBinding("gui.recipe")) {
			int x = ReikaGuiAPI.instance.getMouseRealX();
			int y = ReikaGuiAPI.instance.getMouseRealY();
			int j = (width - xSize) / 2-2;
			int k = (height - ySize) / 2-8;
			if (x >= j && y >= k && x < j+xSize && y < k+ySize) {
				ItemStack is = ReikaGuiAPI.instance.getItemRenderAt(x, y);
				if (is != null) {
					GuiCraftingRecipe.openRecipeGui("item", is);
				}
			}
		}

		if (key == Keyboard.KEY_LEFT) {
			this.prevPage();
		}
		else if (key == Keyboard.KEY_RIGHT) {
			this.nextPage();
		}
		else if (key == Keyboard.KEY_PRIOR) {
			this.prevScreen();
		}
		else if (key == Keyboard.KEY_NEXT) {
			this.nextScreen();
		}
	}

	protected void drawAuxGraphics(int posX, int posY, float ptick) {
		HandbookAuxData.drawGraphics((HandbookRegistry)this.getEntry(), posX, posY, xSize, ySize, fontRendererObj, ri, subpage);
	}

	public final int getGuiTick() {
		return guiTick;
	}

	@Override
	public final void drawScreen(int x, int y, float f)
	{
		if (System.nanoTime()-buttontime > SECOND/20) {
			buttoni++;
			buttontime = System.nanoTime();
			buttontimer = 0;
		}

		guiTick++;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.bindTexture();

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		int xo = 0;
		int yo = 0;
		HandbookEntry h = this.getEntry();
		boolean disable = h.isConfigDisabled();
		String s = h.getTitle()+(disable ? " (Disabled)" : "");
		fontRendererObj.drawString(s, posX+xo+6, posY+yo+6, disable ? 0xff0000 : 0x000000);
		int c = disable ? 0x777777 : 0xffffff;
		int px = posX+descX;
		if (this.isOnTOC()) {
			posY -= 44;
		}
		if (subpage == 0 || h.sameTextAllSubpages()) {
			fontRendererObj.drawSplitString(this.parseHandbookText(h.getData()), px, posY+descY, 242, c);
		}
		else {
			fontRendererObj.drawSplitString(this.parseHandbookText(h.getNotes(subpage)), px, posY+descY, 242, c);
		}
		if (disable) {
			fontRendererObj.drawSplitString("This machine has been disabled by your server admin or modpack creator.", px, posY+descY, 242, 0xffffff);
			fontRendererObj.drawSplitString("Contact them for further information or to request that they remove this restriction.", px, posY+descY+27, 242, 0xffffff);
			fontRendererObj.drawSplitString("If you are the server admin or pack creator, use the configuration files to change this setting.", px, posY+descY+54, 242, 0xffffff);
		}

		super.drawScreen(x, y, f);

		if (subpage == 0 && !disable)
			this.drawRecipes();

		if (!this.isLimitedView()) {
			this.drawTabIcons();
		}

		this.drawGraphics(f);

		if (subpage == 0)
			this.drawMachineRender(posX, posY);

		RenderHelper.disableStandardItemLighting();
	}

	private String parseHandbookText(String s) {
		return s;
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
			//variable = -1000F*(h.getOffset()+1);
			EngineType type = EngineType.engineList[h.getOffset()];
			te = type.getTEInstanceForRender();
			((TileEntityEngine)te).setType(type.getCraftedProduct());
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
		GL11.glPushMatrix();
		if (m.hasModel() && !m.isPipe()) {
			double dx = x;
			double dy = y+21;
			double dz = 0;
			GL11.glTranslated(dx, dy, dz);
			GL11.glScaled(sc, -sc, sc);
			GL11.glRotatef(renderq, 1, 0, 0);
			GL11.glRotatef(r, 0, 1, 0);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(te, -0.5, 0, -0.5, variable);
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
			rb.renderBlockAsItem(m.getBlock(), m.getBlockMetadata(), 1);
		}
		GL11.glPopMatrix();
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
			GL11.glEnable(GL11.GL_BLEND);
			this.doRenderMachine(x, y, h);
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glTranslated(0, 0, -32);
	}
}
