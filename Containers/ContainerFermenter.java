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
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerFermenter extends CoreContainer
{
	private TileEntityFermenter fermenter;
	private int lastFermenterCookTime;
	private int lastFermenterBurnTime;
	private int lastFermenterItemBurnTime;

	public ContainerFermenter(EntityPlayer player, TileEntityFermenter par2TileEntityFermenter)
	{
		super(player, par2TileEntityFermenter);
		lastFermenterCookTime = 0;
		lastFermenterBurnTime = 0;
		lastFermenterItemBurnTime = 0;
		fermenter = par2TileEntityFermenter;
		this.addSlotToContainer(new Slot(par2TileEntityFermenter, 0, 55, 17));
		//this.addSlotToContainer(new Slot(par2TileEntityFermenter, 1, 55, 35));
		//if (tile.worldObj.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord))
		//this.addSlotToContainer(new Slot(par2TileEntityFermenter, 1, 55, 35));
		//else
		this.addSlotToContainer(new Slot(par2TileEntityFermenter, 1, 55, 53));
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityFermenter, 2, 116, 35));

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

			if (lastFermenterCookTime != fermenter.fermenterCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, fermenter.fermenterCookTime);
				icrafting.sendProgressBarUpdate(this, 1, fermenter.getLevel());
			}
		}

		lastFermenterCookTime = fermenter.fermenterCookTime;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			fermenter.fermenterCookTime = par2;
		}
		if (par1 == 1)
		{
			fermenter.setLiquid(par2);
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