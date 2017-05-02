/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiMachine extends GuiContainer {

	protected RotaryCraftTileEntity tile;
	protected TileEntityPowerReceiver recv;
	protected EntityPlayer ep;
	protected static final ReikaGuiAPI api = ReikaGuiAPI.instance;
	protected long lastClick = -1;

	public GuiMachine(Container par1Container, RotaryCraftTileEntity te) {
		super(par1Container);
		tile = te;
		if (te instanceof TileEntityPowerReceiver)
			recv = (TileEntityPowerReceiver)te;
	}

	protected abstract String getGuiTexture();

	public final int getXSize() {
		return xSize;
	}

	public final int getYSize() {
		return ySize;
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	public int getGuiTop() {
		return guiTop;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		String file = "/Reika/RotaryCraft/Textures/GUI/buttons.png";
		buttonList.add(new ImagedGuiButton(24000, j-17, k+4, 18, ySize-12, 72, 0, file, "Info", 0xffffff, false, RotaryCraft.class));
		buttonList.add(new ImagedGuiButton(24001, j-17, k+ySize-8, 18, 4, 72, 252, file, "Info", 0xffffff, false, RotaryCraft.class));
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		if (b.id == 24000 || b.id == 24001) {
			ep.closeScreen();
			if (ReikaInventoryHelper.checkForItem(ItemRegistry.HANDBOOK.getItemInstance(), ep.inventory.mainInventory))
				ep.openGui(RotaryCraft.instance, GuiRegistry.LOADEDHANDBOOK.ordinal(), tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
			else
				ep.openGui(RotaryCraft.instance, GuiRegistry.HANDBOOKPAGE.ordinal(), tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		}
	}

	protected final boolean isClickTooSoon() {
		boolean flag = System.currentTimeMillis()-lastClick < 250;
		if (!flag)
			lastClick = System.currentTimeMillis();
		return flag;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		ReikaTextureHelper.bindFontTexture();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		if (tile instanceof TileEntityProjector)
			fontRendererObj.drawString(tile.getMultiValuedName(), 6, 6, 4210752);
		else if (tile instanceof TileEntityScaleableChest)
			fontRendererObj.drawString(tile.getMultiValuedName(), 8, 6, 4210752);
		else
			ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRendererObj, tile.getMultiValuedName(), xSize/2, 5, 4210752);

		if (tile instanceof IInventory && this.labelInventory()) {
			int dx = this.inventoryLabelLeft() ? 8 : xSize-58;
			fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), dx, (ySize - 96) + 3, 4210752);
		}

		this.drawHelpTab(j, k);
	}

	protected boolean inventoryLabelLeft() {
		return false;
	}

	public boolean labelInventory() {
		return this.getGuiTexture() != "targetgui";
	}

	@Override
	public final boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		//x = Mouse.getX();
		//y = Mouse.getY();
	}

	public final void refreshScreen() {
		//int lastx = x;
		//int lasty = y;
		//mc.thePlayer.closeScreen();
		//ModLoader.openGUI(player, new GuiReservoir(player, Reservoir));
		//Mouse.setCursorPosition(lastx, lasty);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		String i = "/Reika/RotaryCraft/Textures/GUI/"+this.getGuiTexture()+".png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, i);
		this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		if (tile instanceof TileEntityPowerReceiver)
			this.drawPowerTab(j, k);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, i);
		if (ep == null && !(this instanceof GuiOneSlotInv))
			RotaryCraft.logger.logError("Gui for "+tile.getMultiValuedName()+" did not set player entity!");
		//RenderHelper.enableGUIStandardItemLighting();
		//RenderHelper.disableStandardItemLighting();
	}

	protected abstract void drawPowerTab(int j, int k);

	public void drawHelpTab(int j, int k) {
		fontRendererObj.drawString("?", -10, ySize/2-4, 0xffffff);
	}

	@Override
	protected final void func_146977_a(Slot slot) {
		if (this.renderSlot(slot)) {
			super.func_146977_a(slot);
		}
		if (Keyboard.isKeyDown(DragonOptions.DEBUGKEY.getValue()) && DragonOptions.TABNBT.getState()) {
			ReikaTextureHelper.bindFontTexture();
			fontRendererObj.drawString(String.format("%d", slot.getSlotIndex()), slot.xDisplayPosition+1, slot.yDisplayPosition+1, 0x888888);
		}
	}

	protected boolean renderSlot(Slot slot) {
		return true;
	}
}
