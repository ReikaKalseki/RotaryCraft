/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityBigFurnace;

public class ContainerBigFurnace extends CoreContainer
{
	private TileEntityBigFurnace te;

	public ContainerBigFurnace(EntityPlayer player, TileEntityBigFurnace par2TileEntityBigFurnace)
	{
		super(player, par2TileEntityBigFurnace);
		te = par2TileEntityBigFurnace;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;

		int dx = 18;
		int dy = 21;
		for (int i = 0; i < te.getNumberInputSlots(); i++) {
			int row = i%9;
			int col = i/9;
			Slot slot = new Slot(te, i, 8+row*dx, 18+col*dy);
			this.addSlotToContainer(slot);
		}

		for (int i = 0; i < te.getNumberInputSlots(); i++) {
			int row = i%9;
			int col = i/9;
			Slot slot = new SlotFurnace(player, te, i+te.getNumberInputSlots(), 8+row*dx, 72+col*dy);
			this.addSlotToContainer(slot);
		}


		this.addPlayerInventoryWithOffset(player, 0, 41);
	}
}
