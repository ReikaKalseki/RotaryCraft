/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityMultiCannon;


public class ContainerMultiCannon extends ContainerIOMachine {

	private final TileEntityMultiCannon tile;

	public ContainerMultiCannon(EntityPlayer player, TileEntityMultiCannon te) {
		super(player, te);
		tile = te;

		this.addSlot(TileEntityMultiCannon.CLIP_SLOT, 12, 13);

		int id = TileEntityMultiCannon.LOAD_SLOT;
		int i;
		int x = 46;
		int y = 56;
		for (i = 0; i <= 5; i++) {
			this.addSlot(id+i, x, y);
			if (i != 5)
				x += 17;
		}
		y += 17;
		for (int c = 0; c <= 6; c++) {
			this.addSlot(id+i, x, y);
			if (c != 6)
				x -= 17;
			i++;
		}
		y -= 17;
		this.addSlot(id+i, x, y);
		i++;
		y -= 17;
		for (int c = 0; c <= 7; c++) {
			this.addSlot(id+i, x, y);
			if (c != 7)
				x += 17;
			i++;
		}
		y += 17;
		this.addSlot(id+i, x, y);
		i++;
		y += 17;
		this.addSlot(id+i, x, y);
		i++;
		y += 17;
		for (int c = 0; c <= 8; c++) {
			this.addSlot(id+i, x, y);
			if (c != 8)
				x -= 17;
			i++;
		}
		for (int c = 1; c <= 3; c++) {
			y -= 17;
			this.addSlot(id+i, x, y);
			i++;
		}

		this.addPlayerInventoryWithOffset(ep, 0, 38);
	}

}
