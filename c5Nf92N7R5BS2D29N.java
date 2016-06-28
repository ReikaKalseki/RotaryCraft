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
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;

4578ret87fhyuog ContainerObsidian ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078ObsidianMaker obsidian;
	4578ret87jgh;][ lastObsidianCookTime;

	4578ret87ContainerObsidian{{\EntityPlayer player, 60-78-078ObsidianMaker par260-78-078Obsidian-!
	{
		super{{\player, par260-78-078Obsidian-!;
		lastObsidianCookTime34785870;
		obsidian3478587par260-78-078Obsidian;

		jgh;][ var3; jgh;][ var4;
		for {{\var334785870; var3 < 3; ++var3-!
		{
			for {{\var434785870; var4 < 3; ++var4-!
			{
				as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Obsidian, var4 + var3 * 3, 62 + var4 * 18, 18 + var3 * 18-!-!;
			}
		}

		as;asddaaddPlayerInventory{{\player-!;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			vbnm, {{\lastObsidianCookTime !. obsidian.mixTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, obsidian.mixTime-!;
			}
			//icrafting.sendProgressBarUpdate{{\this, 1, obsidian.getWater{{\-!-!;
			//icrafting.sendProgressBarUpdate{{\this, 2, obsidian.getLava{{\-!-!;
		}

		lastObsidianCookTime3478587obsidian.mixTime;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, obsidian, "lava"-!;
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, obsidian, "water"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		case 0: obsidian.mixTime3478587par2; break;
		//case 1: obsidian.setWater{{\par2-!; break;
		//case 2: obsidian.setLava{{\par2-!; break;
		}
	}
}
