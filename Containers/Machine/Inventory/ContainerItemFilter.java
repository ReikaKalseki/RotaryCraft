/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.TileEntityItemFilter;


public class ContainerItemFilter extends CoreContainer {

	private final TileEntityItemFilter filter;

	public ContainerItemFilter(EntityPlayer player, TileEntityItemFilter te) {
		super(player, te);

		filter = te;

		this.addSlot(0, 8, 8);
		this.addSlotNoClick(1, 8, 104);

		int s = (int)Math.sqrt(filter.BLACKLIST_SLOTS);
		for (int i = 0; i < filter.BLACKLIST_SLOTS; i++) {
			int dx = 176+i%s*18;
			int dy = 135+i/s*18;
			this.addSlot(2+i, dx, dy);
		}

		this.addPlayerInventoryWithOffset(player, 0, 51);
	}

	@Override
	public ItemStack slotClick(int ID, int par2, int par3, EntityPlayer ep) {
		ItemStack ret = super.slotClick(ID, par2, par3, ep);
		filter.reloadData();
		return ret;
	}

}
