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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.ContainerFillingStation;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFillingStation;

public class GuiFillingStation extends GuiPowerOnlyMachine
{

	private TileEntityFillingStation FillingStation;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;

	public GuiFillingStation(EntityPlayer p5ep, TileEntityFillingStation te)
	{
		super(new ContainerFillingStation(p5ep, te), te);
		FillingStation = te;
		xSize = 176;
		ySize = 187;
		ep = p5ep;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		super.drawGuiContainerForegroundLayer(a, b);

		if (api.isMouseInBox(j+81, j+94, k+20, k+87)) {
			int mx = api.getMouseRealX();
			int my = api.getMouseRealY();
			api.drawTooltipAt(fontRenderer, String.format("%d/%d mB", FillingStation.getLevel(), FillingStation.CAPACITY), mx-j, my-k);
		}

		if (!FillingStation.isEmpty()) {
			int i2 = FillingStation.getLiquidScaled(66);
			int x = 82;
			int y = 87-i2;
			Icon ico = FillingStation.getFluid().getStillIcon();
			ReikaLiquidRenderer.bindFluidTexture(FillingStation.getFluid());
			this.drawTexturedModelRectFromIcon(x, y, ico, 12, i2);
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
	public String getGuiTexture() {
		return "fillingstationgui";
	}

}
