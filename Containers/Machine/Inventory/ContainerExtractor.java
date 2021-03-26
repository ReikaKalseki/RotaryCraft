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

import Reika.DragonAPI.Instantiable.GUI.Slot.SlotXItems;
import Reika.RotaryCraft.Auxiliary.SlotExtractor1;
import Reika.RotaryCraft.Auxiliary.SlotExtractor2;
import Reika.RotaryCraft.Auxiliary.SlotExtractor3;
import Reika.RotaryCraft.Auxiliary.SlotExtractor4;
import Reika.RotaryCraft.Auxiliary.SlotMachineOut;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;

public class ContainerExtractor extends ContainerIOMachine
{
	private TileEntityExtractor extractor;
	private int[] lastExtractorCookTime;

	public ContainerExtractor(EntityPlayer player, TileEntityExtractor te)
	{
		super(player, te);
		lastExtractorCookTime = new int[4];
		extractor = te;
		this.addSlotToContainer(new SlotExtractor1(te, 0, 26, 13));
		this.addSlotToContainer(new SlotMachineOut(player, te, 4, 26, 55));
		this.addSlotToContainer(new SlotExtractor2(te, 1, 62, 13));
		this.addSlotToContainer(new SlotMachineOut(player, te, 5, 62, 55));
		this.addSlotToContainer(new SlotExtractor3(te, 2, 98, 13));
		this.addSlotToContainer(new SlotMachineOut(player, te, 6, 98, 55));
		this.addSlotToContainer(new SlotExtractor4(te, 3, 134, 13));
		this.addSlotToContainer(new SlotMachineOut(player, te, 7, 134, 55));
		this.addSlotToContainer(new SlotMachineOut(player, te, 8, 152, 55));

		if (ConfigRegistry.EXTRACTORMAINTAIN.getState()) {
			this.addSlotToContainer(new SlotXItems(te, 9, 26, 34, 1));
		}

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
