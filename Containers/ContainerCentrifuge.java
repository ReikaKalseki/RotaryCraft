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
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCentrifuge;

public class ContainerCentrifuge extends CoreContainer
{
	private TileEntityCentrifuge te;

	public ContainerCentrifuge(EntityPlayer player, TileEntityCentrifuge Centrifuge)
	{
		super(player, Centrifuge);
		te = Centrifuge;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;

		this.addSlot(0, 26, 38);

		int dx = 85;
		int dy = 20;
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				int id = 1+i*3+k;
				int x = dx+k*18;
				int y = dy+i*18;
				this.addSlot(id, x, y);
			}
		}

		this.addPlayerInventory(player);
	}
}
