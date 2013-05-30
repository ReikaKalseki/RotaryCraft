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

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;

public class GuiReservoir extends GuiNonPoweredMachine
{
	boolean water = false;

	private TileEntityReservoir Reservoir;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;
	private EntityPlayer player;
	int x;
	int y;

	public GuiReservoir(EntityPlayer player, TileEntityReservoir tile)
	{
		super(new ContainerReservoir(player, tile), tile);
		Reservoir = tile;
		xSize = 176;
		ySize = 166;
		this.player = player;
		water = (tile.liquidID == 8 || tile.liquidID == 9);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i2 = Reservoir.getLiquidScaled(44);
		if (i2 > 44)
			i2 = 44;
		int i3 = 0;
		if (i2 != 0)
			i3 = 1;
		int i4 = 0;
		if (!water)
			i4 = 8;
		this.drawTexturedModalRect(j +xSize/2-4, ySize/2+k-13-i2, 176+i4, 0, 8, i2);
	}

	@Override
	public String getGuiTexture() {
		return "reservoirgui";
	}

}
