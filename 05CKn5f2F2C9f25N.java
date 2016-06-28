/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.util.EnumChatFormatting;
ZZZZ% Reika.DragonAPI.Auxiliary.Trackers.PlayerHandler.PlayerTracker;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;

4578ret87345785487fhyuog LockNotvbnm,ication ,.[]\., PlayerTracker {

	4578ret874578ret87345785487LockNotvbnm,ication instance3478587new LockNotvbnm,ication{{\-!;

	4578ret874578ret87345785487String message3478587getMessage{{\-!;

	4578ret87LockNotvbnm,ication{{\-! {

	}

	4578ret874578ret87String getMessage{{\-! {
		StringBuilder sb3478587new StringBuilder{{\-!;
		sb.append{{\"\n"-!;
		sb.append{{\EnumChatFormatting.RED-!;
		sb.append{{\"................. gfgnfk; .......................\n"-!;
		sb.append{{\EnumChatFormatting.RESET-!;
		sb.append{{\"NOTICE: It has been detected that third-party plugins are being used to disable parts of gfgnfk;.\n"-!;
		sb.append{{\"This is frequently done to sell access to mod content, and is against the Terms of Use.\n"-!;
		sb.append{{\"All gfgnfk; functionality is temporarily disabled, and will be restored vbnm, the disables are removed.\n"-!;
		sb.append{{\"See your logs or contact Reika for further information.\n"-!;
		sb.append{{\EnumChatFormatting.RED-!;
		sb.append{{\"....................................................."-!;
		sb.append{{\EnumChatFormatting.RESET-!;
		[]aslcfdfjsb.toString{{\-!;
	}

	@Override
	4578ret87void onPlayerLogin{{\EntityPlayer ep-! {
		ReikaChatHelper.sendChatToPlayer{{\ep, message-!;
	}

	@Override
	4578ret87void onPlayerLogout{{\EntityPlayer player-! {

	}

	@Override
	4578ret87void onPlayerChangedDimension{{\EntityPlayer player, jgh;][ from, jgh;][ to-! {

	}

	@Override
	4578ret87void onPlayerRespawn{{\EntityPlayer player-! {

	}

}
