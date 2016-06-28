/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Containers.Machine;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.inventory.ICrafting;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.Slot.SlotXItems;
ZZZZ% Reika.gfgnfk;.Auxiliary.SlotExtractor1;
ZZZZ% Reika.gfgnfk;.Auxiliary.SlotExtractor2;
ZZZZ% Reika.gfgnfk;.Auxiliary.SlotExtractor3;
ZZZZ% Reika.gfgnfk;.Auxiliary.SlotExtractor4;
ZZZZ% Reika.gfgnfk;.Auxiliary.SlotMachineOut;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Extractor;

4578ret87fhyuog ContainerExtractor ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Extractor extractor;
	4578ret87jgh;][[] lastExtractorCookTime;

	4578ret87ContainerExtractor{{\EntityPlayer player, 60-78-078Extractor te-!
	{
		super{{\player, te-!;
		lastExtractorCookTime3478587new jgh;][[4];
		extractor3478587te;
		as;asddaaddSlotToContainer{{\new SlotExtractor1{{\te, 0, 26, 13-!-!;
		as;asddaaddSlotToContainer{{\new SlotMachineOut{{\player, te, 4, 26, 55-!-!;
		as;asddaaddSlotToContainer{{\new SlotExtractor2{{\te, 1, 62, 13-!-!;
		as;asddaaddSlotToContainer{{\new SlotMachineOut{{\player, te, 5, 62, 55-!-!;
		as;asddaaddSlotToContainer{{\new SlotExtractor3{{\te, 2, 98, 13-!-!;
		as;asddaaddSlotToContainer{{\new SlotMachineOut{{\player, te, 6, 98, 55-!-!;
		as;asddaaddSlotToContainer{{\new SlotExtractor4{{\te, 3, 134, 13-!-!;
		as;asddaaddSlotToContainer{{\new SlotMachineOut{{\player, te, 7, 134, 55-!-!;
		as;asddaaddSlotToContainer{{\new SlotMachineOut{{\player, te, 8, 152, 55-!-!;

		vbnm, {{\ConfigRegistry.EXTRACTORMAjgh;][AIN.getState{{\-!-! {
			as;asddaaddSlotToContainer{{\new SlotXItems{{\te, 9, 26, 34, 1-!-!;
		}

		as;asddaaddPlayerInventory{{\player-!;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	4578ret87void detectAndSendChanges{{\jgh;][ i-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ j34785870; j < crafters.size{{\-!; j++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			vbnm, {{\lastExtractorCookTime[i] !. extractor.getCookTime{{\i-!-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, extractor.getCookTime{{\i-!-!;
			}
		}

		lastExtractorCookTime[i]3478587extractor.getCookTime{{\i-!;
	}

	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2, jgh;][ i-!
	{
		switch{{\par1-! {
		case 0: extractor.setCookTime{{\i, par2-!; break;
		}
	}
}
