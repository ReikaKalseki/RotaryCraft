/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerScaleChest;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

	public GuiScaleChest(EntityPlayer p5ep, TileEntityScaleableChest te, int page)
	{
		super(new ContainerScaleChest(p5ep, te, page), te);
		upperScaleChestInventory = p5ep.inventory;
		allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		invsize = te.getNumberSlots();
		scale = te;
		ySize = var4 + 18*scale.MAXROWS;//ReikaMathLibrary.extrema(numrows, scale.MAXROWS, "min")*18;
		ep = p5ep;
		this.setValues();
		this.page = page;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2+8;
		int k = (height - ySize) / 2 - 12;
		buttonList.add(new GuiButton(0, j+xSize-9, k+45, 40, 20, "Next"));
		buttonList.add(new GuiButton(1, j+xSize-9, k+65, 40, 20, "Back"));
	}

	private void setValues() {
		int oldpage = page;
		numrows = (int)Math.ceil(invsize/9D);
		if (numrows > scale.MAXROWS) {
			numrows = scale.MAXROWS;
		}
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
		if (button.id == 0 && page < scale.getMaxPage()-1)
			page++;
		if (button.id == 1 && page > 0)
			page--;
		if (page == oldpage)
			return;
		ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.CHEST.getMinValue(), scale, page);
		//ep.closeScreen();
		//this.refresh();
		//this.setValues();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		this.setValues();
		super.drawGuiContainerForegroundLayer(a, b);
		fontRendererObj.drawString("Page "+String.valueOf(page), xSize-48, 6, 4210752);
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
		int color3 = 0xffc6c6c6;

		ReikaGuiAPI.drawRect(var4, var5, var4+18*diff, var5+18, color3);
		if (pagerows < scale.MAXROWS) {
			var4 = 7;
			var5 += 18;
			diff = scale.MAXROWS-pagerows;
			ReikaGuiAPI.drawRect(var4, var5, var4+18*9, var5+18*diff, color3);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		if (scale.power < scale.MINPOWER) {
			return;
		}
		String var4 = this.getGuiTexture();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
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