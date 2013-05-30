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

import net.minecraft.entity.player.EntityPlayer;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;

public class GuiGPR extends GuiPowerOnlyMachine
{

	private TileEntityGPR gpr;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private EntityPlayer player;
	int x;
	int y;
	public static final int UNIT = 2;

	public GuiGPR(EntityPlayer player, TileEntityGPR GPR)
	{
		super(new CoreContainer(player, GPR), GPR);
		gpr = GPR;
		ySize = 240;
		this.player = player;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2+1;

		this.drawRadar(j, k);
	}

	private void drawRadar(int a, int b) {
		int width = gpr.getBounds()[1]-gpr.getBounds()[0];
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", width));
		for (int j = gpr.getBounds()[0]; j <= gpr.getBounds()[1]; j++) {
			for (int i = 0; i < gpr.yCoord; i++) {
				this.drawRect(a+7+UNIT*j, b+16+UNIT*i, a+7+UNIT+UNIT*j, b+16+UNIT*i+UNIT, gpr.colors[i][j]);
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return "gprgui";
	}
}
