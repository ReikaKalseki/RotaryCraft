/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.World.TileEntityDefoliator;

public class ContainerDefoliator extends CoreContainer
{
	private TileEntityDefoliator te;

	public ContainerDefoliator(EntityPlayer player, TileEntityDefoliator defoliator)
	{
		super(player, defoliator);
		te = defoliator;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;
		this.addSlot(0, 80, 17);
		this.addSlot(1, 80, 53);

		this.addPlayerInventory(player);
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}
}
