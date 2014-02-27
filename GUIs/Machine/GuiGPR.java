/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;

public class GuiGPR extends GuiPowerOnlyMachine
{

	private TileEntityGPR gpr;

	int x;
	int y;
	public static final int UNIT = 2;

	public GuiGPR(EntityPlayer p5ep, TileEntityGPR GPR) {
		super(new CoreContainer(p5ep, GPR), GPR);
		gpr = GPR;
		ySize = 215;
		ep = p5ep;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2+1;

		this.drawRadar(j, k);
	}

	private void drawRadar(int a, int b) {
		for (int j = gpr.getBounds()[0]; j <= gpr.getBounds()[1]; j++) {
			for (int i = 0; i < gpr.yCoord; i++) {
				int color = 0xff000000 | gpr.colors[i][j];
				this.drawRect(a+7+UNIT*j, b+16+UNIT*i, a+7+UNIT+UNIT*j, b+16+UNIT*i+UNIT, color);
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "gprgui";
	}
}
