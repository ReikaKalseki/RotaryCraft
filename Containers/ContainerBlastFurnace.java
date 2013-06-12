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
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;

public class ContainerBlastFurnace extends CoreContainer
{
	private TileEntityBlastFurnace blast;

	public ContainerBlastFurnace(EntityPlayer player, TileEntityBlastFurnace par2TileEntityBlastFurnace)
	{
		super(player, par2TileEntityBlastFurnace);
		blast = par2TileEntityBlastFurnace;
		int posX = blast.xCoord;
		int posY = blast.yCoord;
		int posZ = blast.zCoord;

		int id = 0;
		this.addSlotToContainer(new Slot(par2TileEntityBlastFurnace, id, 26, 35));
		id++;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(par2TileEntityBlastFurnace, id, 62+i*18, 17+j*18));
				id++;
			}
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityBlastFurnace, 10, 148, 35));
		this.addSlotToContainer(new Slot(par2TileEntityBlastFurnace, 11, 26, 54));
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityBlastFurnace, 12, 148, 17));
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityBlastFurnace, 13, 148, 53));

		this.addPlayerInventory(player);
	}

	@Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer ep) {
		ItemStack is = super.slotClick(par1, par2, par3, ep);
		if (ReikaItemHelper.matchStacks(ItemStacks.steelingot, is))
			ep.triggerAchievement(RotaryAchievements.MAKESTEEL.get());
		return is;
	}
}
