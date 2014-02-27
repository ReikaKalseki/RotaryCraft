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
import net.minecraft.inventory.ICrafting;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.Auxiliary.SlotExtractor1;
import Reika.RotaryCraft.Auxiliary.SlotExtractor2;
import Reika.RotaryCraft.Auxiliary.SlotExtractor3;
import Reika.RotaryCraft.Auxiliary.SlotExtractor4;
import Reika.RotaryCraft.Auxiliary.SlotMachineOut;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;

public class ContainerExtractor extends CoreContainer
{
	private TileEntityExtractor extractor;
	private int[] lastExtractorCookTime;

	public ContainerExtractor(EntityPlayer player, TileEntityExtractor par2TileEntityExtractor)
	{
		super(player, par2TileEntityExtractor);
		lastExtractorCookTime = new int[4];
		extractor = par2TileEntityExtractor;
		this.addSlotToContainer(new SlotExtractor1(par2TileEntityExtractor, 0, 26, 13));
		this.addSlotToContainer(new SlotMachineOut(player, par2TileEntityExtractor, 4, 26, 55));
		this.addSlotToContainer(new SlotExtractor2(par2TileEntityExtractor, 1, 62, 13));
		this.addSlotToContainer(new SlotMachineOut(player, par2TileEntityExtractor, 5, 62, 55));
		this.addSlotToContainer(new SlotExtractor3(par2TileEntityExtractor, 2, 98, 13));
		this.addSlotToContainer(new SlotMachineOut(player, par2TileEntityExtractor, 6, 98, 55));
		this.addSlotToContainer(new SlotExtractor4(par2TileEntityExtractor, 3, 134, 13));
		this.addSlotToContainer(new SlotMachineOut(player, par2TileEntityExtractor, 7, 134, 55));
		this.addSlotToContainer(new SlotMachineOut(player, par2TileEntityExtractor, 8, 152, 55));

		this.addPlayerInventory(player);
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	public void detectAndSendChanges(int i)
	{
		super.detectAndSendChanges();

		for (int j = 0; j < crafters.size(); j++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (lastExtractorCookTime[i] != extractor.getCookTime(i))
			{
				icrafting.sendProgressBarUpdate(this, 0, extractor.getCookTime(i));
			}
		}

		lastExtractorCookTime[i] = extractor.getCookTime(i);
	}

	public void updateProgressBar(int par1, int par2, int i)
	{
		switch(par1) {
		case 0: extractor.setCookTime(i, par2); break;
		}
	}
}
