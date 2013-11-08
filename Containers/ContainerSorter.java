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
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GhostSlot;
import Reika.RotaryCraft.TileEntities.TileEntitySorting;

public class ContainerSorter extends CoreContainer {

	private TileEntitySorting tile;

	public ContainerSorter(EntityPlayer player, TileEntitySorting te) {
		super(player, te);
		tile = te;

		int l = TileEntitySorting.LENGTH;
		int dy = 22;
		int x = 8;
		int y = 18;
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < l; i++) {
				this.addSlotToContainer(new GhostSlot(i+k*l, x+i*18, y+dy*k));
			}
		}

		this.addPlayerInventoryWithOffset(player, 0, 14);
	}

	@Override
	public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer ep) {
		boolean inGUI = slot < TileEntitySorting.LENGTH*3 && slot >= 0;
		if (inGUI) {
			ItemStack held = ep.inventory.getItemStack();
			tile.setMapping(slot, held);
			return held;
		}
		else
			return super.slotClick(slot, par2, par3, ep);
	}

}
