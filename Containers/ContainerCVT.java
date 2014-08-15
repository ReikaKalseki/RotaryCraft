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

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCVT extends CoreContainer
{
	private TileEntityAdvancedGear CVT;

	public ContainerCVT(EntityPlayer player, TileEntityAdvancedGear par2TileEntityAdvancedGear)
	{
		super(player, par2TileEntityAdvancedGear);
		CVT = par2TileEntityAdvancedGear;
		int posX = CVT.xCoord;
		int posY = CVT.yCoord;
		int posZ = CVT.zCoord;

		int x = 8;
		int y = 11;
		int k = 0;
		int a = 0;
		int b = 0;
		for (int i = 0; i < ReikaMathLibrary.logbase(32, 2); i++) {
			for (int j = 0; j < ReikaMathLibrary.intpow(2, i); j++) {
				if (k > 22) {
					a = -144;
					b = 18;
				}
				this.addSlotToContainer(new Slot(CVT, k, x+j*18+a, y+i*26+b));
				k++;
			}
		}
		int dx = 31;
		int dy = 77;
		for (int i = 0; i < 3; i++)
		{
			for (k = 0; k < 9; k++)
			{
				this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18+dx, 84 + i * 18+dy));
			}
		}
		dy -= 4;
		for (int j = 0; j < 9; j++)
		{
			this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18+dx, 142+dy));
		}
	}
}