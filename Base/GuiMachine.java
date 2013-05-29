/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;

@SideOnly(Side.CLIENT)
public abstract class GuiMachine extends GuiContainer {

	protected RotaryCraftTileEntity tile;
	protected TileEntityPowerReceiver recv;
	protected EntityPlayer ep;

	public GuiMachine(Container par1Container, RotaryCraftTileEntity te) {
		super(par1Container);
		tile = te;
		if (te instanceof TileEntityPowerReceiver)
			recv = (TileEntityPowerReceiver)te;
	}

	@Override
	protected abstract void drawGuiContainerBackgroundLayer(float par1, int par2, int par3);

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 3, 4210752);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, tile.getMultiValuedName(), xSize/2, 4, 4210752);
	}

	protected abstract void drawPowerTab(int j, int k);

	public void drawHelpTab(int j, int k) {

	}

	public void callHelpAction(int j, int k) {

	}
}
