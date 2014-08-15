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
import Reika.DragonAPI.Instantiable.GUI.Slot.GhostSlot;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.TileEntities.TileEntitySorting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
			tile.setMapping(slot, ReikaItemHelper.getSizedItemStack(held, 1));
			return held;
		}
		else
			return super.slotClick(slot, par2, par3, ep);
	}

}