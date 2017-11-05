/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBundledBus extends GuiPowerOnlyMachine
{
	private IInventory upperBundledBusInventory;
	private TileEntityBundledBus med;

	public GuiBundledBus(EntityPlayer p5ep, TileEntityBundledBus te)
	{
		super(new ContainerBundledBus(p5ep, te), te);
		upperBundledBusInventory = p5ep.inventory;
		med = te;
		ep = p5ep;

		xSize = 176;
		ySize = 192;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
	}

	@Override
	public boolean labelInventory() {
		return true;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);

		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), xSize-58, (ySize - 97) + 4, 0xffffff);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		for (int i = 0; i < med.NSLOTS; i++) {
			int dx = 50+(i%4)*20;
			int dy = 19+(i/4)*20;
			api.drawItemStack(itemRender, fontRendererObj, med.getMapping(i), dx, dy);
		}
	}

	@Override
	public String getGuiTexture() {
		return "bundledbusgui";
	}
}
