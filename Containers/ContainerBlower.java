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
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Instantiable.GUI.Slot.GhostSlot;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.TileEntities.TileEntityBlower;

public class ContainerBlower extends CoreContainer {

	private TileEntityBlower tile;

	public ContainerBlower(EntityPlayer player, TileEntityBlower te) {
		super(player, te);
		tile = te;

		int dy = 18;
		int x = 8;
		int y = 21;
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < 9; i++) {
				this.addSlotToContainer(new GhostSlot(i+k*9, x+i*18, y+dy*k));
			}
		}

		this.addPlayerInventoryWithOffset(player, 0, 26);
	}

	@Override
	public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer ep) {
		boolean inGUI = slot < tile.matchingItems.length && slot >= 0;
		if (inGUI) {
			ItemStack held = ep.inventory.getItemStack();
			tile.matchingItems[slot] = ReikaItemHelper.getSizedItemStack(held, 1);
			return held;
		}
		else
			return super.slotClick(slot, par2, par3, ep);
	}

}
