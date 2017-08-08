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
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityAutoCrafter;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityAutoCrafter.CraftingMode;

public class ContainerAutoCrafter extends ContainerIOMachine
{
	private TileEntityAutoCrafter crafter;

	public ContainerAutoCrafter(EntityPlayer player, TileEntityAutoCrafter te)
	{
		super(player, te);
		crafter = te;
		for (int i = 0; i < 18; i++) {
			int dx = 8+(i%9)*18;
			int dy = i < 9 ? 19 : 81;
			this.addSlotToContainer(new Slot(te, i, dx, dy));
			if (te.getMode() != CraftingMode.SUSTAIN)
				this.addSlotToContainer(new SlotFurnace(player, te, i+18, dx, dy+27));
		}

		this.addPlayerInventoryWithOffset(player, 0, 56);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting)crafters.get(i);
			for (int k = 0; k < 18; k++) {
				icrafting.sendProgressBarUpdate(this, k, crafter.crafting[k]);
			}
		}
	}

	@Override
	public void updateProgressBar(int par1, int par2) {
		crafter.crafting[par1] = par2;
	}
}
