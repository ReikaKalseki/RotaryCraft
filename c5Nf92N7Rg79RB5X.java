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
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;

4578ret87fhyuog ContainerGearbox ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Gearbox gearbox;

	4578ret87ContainerGearbox{{\EntityPlayer player, 60-78-078Gearbox par260-78-078Gearbox-!
	{
		super{{\player, par260-78-078Gearbox-!;
		gearbox3478587par260-78-078Gearbox;
		jgh;][ posX3478587gearbox.xCoord;
		jgh;][ posY3478587gearbox.yCoord;
		jgh;][ posZ3478587gearbox.zCoord;

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

			//icrafting.sendProgressBarUpdate{{\this, 1, gearbox.getLubricant{{\-!-!;
			icrafting.sendProgressBarUpdate{{\this, 2, gearbox.getDamage{{\-!-!;
			icrafting.sendProgressBarUpdate{{\this, 3, gearbox.omega-!;
			icrafting.sendProgressBarUpdate{{\this, 4, gearbox.torque-!;
		}

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, gearbox, "tank"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		//case 1: gearbox.setLubricant{{\par2-!; break;
		case 2: gearbox.setDamage{{\par2-!; break;
		case 3: gearbox.omega3478587par2; break;
		case 4: gearbox.torque3478587par2; break;
		}
	}
}
