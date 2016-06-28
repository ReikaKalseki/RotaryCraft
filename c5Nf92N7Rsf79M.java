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
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078SteamEngine;

4578ret87fhyuog ContainerSteam ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078SteamEngine Steam;

	4578ret87ContainerSteam{{\EntityPlayer player, 60-78-078SteamEngine te-!
	{
		super{{\player, te-!;
		Steam3478587te;
		jgh;][ posX3478587Steam.xCoord;
		jgh;][ posY3478587Steam.yCoord;
		jgh;][ posZ3478587Steam.zCoord;
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

			icrafting.sendProgressBarUpdate{{\this, 1, Steam.temperature-!;
			//icrafting.sendProgressBarUpdate{{\this, 2, Steam.getWater{{\-!/2-!;
		}
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, Steam, "water"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		case 1: Steam.temperature3478587par2; break;
		//case 2: Steam.setWater{{\par2*2-!; break;
		}
	}
}
