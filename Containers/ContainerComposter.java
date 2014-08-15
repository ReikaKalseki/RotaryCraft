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
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerComposter extends CoreContainer
{
	private TileEntityComposter composter;
	private int lastComposterCookTime;

	public ContainerComposter(EntityPlayer player, TileEntityComposter par2TileEntityComposter)
	{
		super(player, par2TileEntityComposter);
		lastComposterCookTime = 0;
		composter = par2TileEntityComposter;
		this.addSlotToContainer(new Slot(par2TileEntityComposter, 0, 55, 26));
		this.addSlotToContainer(new Slot(par2TileEntityComposter, 1, 55, 44));
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityComposter, 2, 116, 35));

		this.addPlayerInventory(player);
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (lastComposterCookTime != composter.composterCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, composter.composterCookTime);
			}
		}

		lastComposterCookTime = composter.composterCookTime;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			composter.composterCookTime = par2;
		}
	}

	@Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer ep) {
		ItemStack is = super.slotClick(par1, par2, par3, ep);
		if (ReikaItemHelper.matchStacks(ItemRegistry.YEAST.getStackOf(), is))
			RotaryAchievements.MAKEYEAST.triggerAchievement(ep);
		return is;
	}
}