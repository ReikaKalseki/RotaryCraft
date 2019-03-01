/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

public class GuiReservoir extends GuiNonPoweredMachine
{

	private TileEntityReservoir reservoir;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;

	public GuiReservoir(EntityPlayer p5ep, TileEntityReservoir te)
	{
		super(new ContainerReservoir(p5ep, te), te);
		reservoir = te;
		xSize = 176;
		ySize = 96;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (api.isMouseInBox(j+83, j+92, k+25, k+70)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRendererObj, String.format("%d/%d", reservoir.getLevel(), reservoir.CAPACITY), mx-j, my-k);
		}

		if (!reservoir.isEmpty()) {
			int i2 = reservoir.getLiquidScaled(44);
			int x = xSize/2-4;
			int y = ySize/2-13-i2+35;
			IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(reservoir.getFluid());
			ReikaLiquidRenderer.bindFluidTexture(reservoir.getFluid());
			this.drawTexturedModelRectFromIcon(x, y, ico, 8, i2);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
	}

	@Override
	protected String getGuiTexture() {
		return "reservoirgui";
	}

}
