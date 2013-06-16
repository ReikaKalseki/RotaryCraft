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
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;

@SideOnly(Side.CLIENT)
public class GuiScaleChest extends GuiPowerOnlyMachine
{
	private IInventory upperScaleChestInventory;
	private TileEntityScaleableChest scale;
	private int numrows;
	private int page;

	int x;
	int y;

	private int invsize = 0;

	public GuiScaleChest(EntityPlayer p5ep, IInventory par2IInventory, TileEntityScaleableChest te)
	{
		super(new ContainerScaleChest(p5ep, te), te);
		upperScaleChestInventory = p5ep.inventory;
		allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		invsize = par2IInventory.getSizeInventory();
		scale = te;
		ySize = var4 + 18*scale.MAXROWS;//ReikaMathLibrary.extrema(numrows, scale.MAXROWS, "min")*18;
		ep = p5ep;
		this.setValues();
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		buttonList.add(new GuiButton(0, -7+j-20, k+16, 20, 20, "+"));
		buttonList.add(new GuiButton(1, -7+j-20, k+36, 20, 20, "-"));
	}

	private void setValues() {
		int oldpage = page;
		numrows = (int)Math.ceil(invsize/9D);
		if (numrows > scale.MAXROWS) {
			numrows = scale.MAXROWS;
		}
		page = scale.page;
		if (page == scale.getMaxPage()) {
			numrows = (int)Math.ceil((invsize-(page*9*scale.MAXROWS))/9D);
		}
		if (page != oldpage) {

		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		int oldpage = page;
		if (button.id == 0 && page < scale.getMaxPage())
			page++;
		if (button.id == 1 && page > 0)
			page--;
		if (page == oldpage)
			return;
		ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CHEST.getMinValue(), scale, ep, page);
		//player.closeScreen();
		//this.refresh();
		//this.setValues();
	}

	private void refresh() {
		int lastx = x;
		int lasty = y;
		mc.thePlayer.closeScreen();
		ep.openGui(RotaryCraft.instance, 9, scale.worldObj, scale.xCoord, scale.yCoord, scale.zCoord);
		Mouse.setCursorPosition(lastx, lasty);
		invsize = scale.getSizeInventory();
		numrows = (int)Math.ceil(invsize/9D);
		if (numrows > scale.MAXROWS) {
			numrows = scale.MAXROWS;
		}
		if (page == scale.getMaxPage()) {
			numrows = (int)Math.ceil((invsize-(page*9*scale.MAXROWS))/9D);
		}
		short var3 = 222;
		int var4 = var3 - 108;
		//ySize = var4 + ReikaMathLibrary.extrema(numrows, scale.MAXROWS, "min")*18;
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
		fontRenderer.drawString("Page "+String.valueOf(scale.page), xSize-48, 6, 4210752);
		int var3 = 0;
		int pageinv = invsize-page*9*scale.MAXROWS;
		int pagerows = numrows;
		if (pagerows > scale.MAXROWS)
			pagerows = scale.MAXROWS;
		int var4 = 7+pageinv*18-(numrows-1)*18*9;
		int var5 = -1+pagerows*18;
		int diff = pagerows*9-pageinv;
		if (page < scale.getMaxPage())
			diff = 0;
		int color1 = 0xffeeeeee;
		int color2 = 0xff939393;
		int color3 = 0xffcacaca;
		ReikaGuiAPI.drawRect(var4, var5, var4+18*diff, var5+18, color3);
		if (pagerows < scale.MAXROWS) {
			var4 = 7;
			var5 += 18;
			diff = scale.MAXROWS-pagerows;
			ReikaGuiAPI.drawRect(var4, var5, var4+18*9, var5+18*diff, color3);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		if (scale.power < scale.MINPOWER) {
			return;
		}
		String var4 = this.getGuiTexture();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, scale.MAXROWS*18 + 17);
		this.drawTexturedModalRect(var5, var6 + scale.MAXROWS*18 + 17, 0, 126, xSize, 96);

		this.drawPowerTab(var5, var6);
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/basicstorage.png";
	}
}
