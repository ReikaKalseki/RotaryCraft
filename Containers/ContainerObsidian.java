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
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerObsidian extends CoreContainer
{
	private TileEntityObsidianMaker obsidian;
	private int lastObsidianCookTime;
	private int lastObsidianBurnTime;
	private int lastObsidianItemBurnTime;

	public ContainerObsidian(EntityPlayer player, TileEntityObsidianMaker par2TileEntityObsidian)
	{
		super(player, par2TileEntityObsidian);
		lastObsidianCookTime = 0;
		lastObsidianBurnTime = 0;
		lastObsidianItemBurnTime = 0;
		obsidian = par2TileEntityObsidian;

		int var3; int var4;
		for (var3 = 0; var3 < 3; ++var3)
		{
			for (var4 = 0; var4 < 3; ++var4)
			{
				this.addSlotToContainer(new Slot(par2TileEntityObsidian, var4 + var3 * 3, 62 + var4 * 18, 18 + var3 * 18));
			}
		}

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

			if (lastObsidianCookTime != obsidian.mixTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, obsidian.mixTime);
			}
			//icrafting.sendProgressBarUpdate(this, 1, obsidian.getWater());
			//icrafting.sendProgressBarUpdate(this, 2, obsidian.getLava());
		}

		lastObsidianCookTime = obsidian.mixTime;

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, obsidian, "lava");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, obsidian, "water");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 0: obsidian.mixTime = par2; break;
		//case 1: obsidian.setWater(par2); break;
		//case 2: obsidian.setLava(par2); break;
		}
	}
}