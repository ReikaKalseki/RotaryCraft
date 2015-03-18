/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerRockMelter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;

public class GuiRockMelter extends GuiPowerOnlyMachine {

	private final TileEntityLavaMaker tile;

	public GuiRockMelter(EntityPlayer ep, TileEntityLavaMaker te) {
		super(new ContainerRockMelter(ep, te), te);
		tile = te;
		this.ep = ep;
	}

	@Override
	protected String getGuiTexture() {
		return "lavamakergui";
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b) {
		super.drawGuiContainerForegroundLayer(a, b);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int minx = j+133;
		int maxx = minx+18;
		int maxy = k+75;
		int miny = maxy-66;
		if (!tile.isEmpty()) {
			if (api.isMouseInBox(minx, maxx, miny, maxy)) {
				String sg = String.format("%s: %d/%d mB", tile.getContainedFluid().getLocalizedName(), tile.getLevel(), tile.getCapacity());
				api.drawTooltip(fontRendererObj, sg);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		if (!tile.isEmpty()) {
			int amt = tile.getLevel();
			int cap = tile.getCapacity();
			int frac = amt*64/cap;
			int j = (width - xSize) / 2;
			int k = (height - ySize) / 2;
			GL11.glColor4f(1, 1, 1, 1);
			Fluid f = tile.getContainedFluid();
			IIcon ico = f.getIcon();
			ReikaLiquidRenderer.bindFluidTexture(f);
			int x = j+134;
			int y = k+73-frac+1;
			this.drawTexturedModelRectFromIcon(x, y, ico, 16, frac);
		}

	}

	@Override
	protected boolean inventoryLabelLeft() {
		return true;
	}

}
