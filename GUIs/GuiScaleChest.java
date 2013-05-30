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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerScaleChest;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;

@SideOnly(Side.CLIENT)
public class GuiScaleChest extends GuiPowerOnlyMachine
{
	private IInventory upperScaleChestInventory;
	private TileEntityScaleableChest tile;
	private int numrows;
	private int page;
	private EntityPlayer player;
	int x;
	int y;

	private int invsize = 0;

	public GuiScaleChest(EntityPlayer player, IInventory par2IInventory, TileEntityScaleableChest te)
	{
		super(new ContainerScaleChest(player, te), te);
		upperScaleChestInventory = player.inventory;
		allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		invsize = par2IInventory.getSizeInventory();
		tile = te;
		ySize = var4 + 18*tile.MAXROWS;//ReikaMathLibrary.extrema(numrows, tile.MAXROWS, "min")*18;
		this.player = player;
		this.setValues();
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		buttonList.add(new GuiButton(0, -7+j-20, k+16, 20, 20, "+"));
		buttonList.add(new GuiButton(1, -7+j-20, k+36, 20, 20, "-"));
	}

	private void setValues() {
		int oldpage = page;
		numrows = (int)Math.ceil(invsize/9D);
		if (numrows > tile.MAXROWS) {
			numrows = tile.MAXROWS;
		}
		page = tile.page;
		if (page == tile.getMaxPage()) {
			numrows = (int)Math.ceil((invsize-(page*9*tile.MAXROWS))/9D);
		}
		if (page != oldpage) {

		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		int oldpage = page;
		if (button.id == 0 && page < tile.getMaxPage())
			page++;
		if (button.id == 1 && page > 0)
			page--;
		if (page == oldpage)
			return;
		ReikaPacketHelper.sendPacket(RotaryCraft.packetChannel, 18, tile, player, page);
		//player.closeScreen();
		//this.refresh();
		//this.setValues();
	}

	private void refresh() {
		int lastx = x;
		int lasty = y;
		mc.thePlayer.closeScreen();
		player.openGui(RotaryCraft.instance, 9, tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		Mouse.setCursorPosition(lastx, lasty);
		invsize = tile.getSizeInventory();
		numrows = (int)Math.ceil(invsize/9D);
		if (numrows > tile.MAXROWS) {
			numrows = tile.MAXROWS;
		}
		if (page == tile.getMaxPage()) {
			numrows = (int)Math.ceil((invsize-(page*9*tile.MAXROWS))/9D);
		}
		short var3 = 222;
		int var4 = var3 - 108;
		//ySize = var4 + ReikaMathLibrary.extrema(numrows, tile.MAXROWS, "min")*18;
		this.initGui();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		this.setValues();
		super.drawGuiContainerForegroundLayer(a, b);
		int var3 = 0;
		int pageinv = invsize-page*9*tile.MAXROWS;
		int pagerows = numrows;
		if (pagerows > tile.MAXROWS)
			pagerows = tile.MAXROWS;
		int var4 = 7+pageinv*18-(numrows-1)*18*9;
		int var5 = -1+pagerows*18;
		int diff = pagerows*9-pageinv;
		if (page < tile.getMaxPage())
			diff = 0;
		int color1 = 0xffeeeeee;
		int color2 = 0xff939393;
		ReikaGuiAPI.drawRect(var4, var5, var4+18*diff, var5+18, color1);
		if (pagerows < tile.MAXROWS) {
			var4 = 7;
			var5 += 18;
			diff = tile.MAXROWS-pagerows;
			ReikaGuiAPI.drawRect(var4, var5, var4+18*9, var5+18*diff, color1);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		if (tile.power < tile.MINPOWER) {
			return;
		}
		String var4 = this.getGuiTexture();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, tile.MAXROWS*18 + 17);
		this.drawTexturedModalRect(var5, var6 + tile.MAXROWS*18 + 17, 0, 126, xSize, 96);

		this.drawPowerTab(var5, var6);
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/basicstorage.png";
	}
}
