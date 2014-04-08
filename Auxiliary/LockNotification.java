/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import cpw.mods.fml.common.IPlayerTracker;

public final class LockNotification implements IPlayerTracker {

	public static final LockNotification instance = new LockNotification();

	private static final String message = getMessage();

	private LockNotification() {

	}

	private static String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(EnumChatFormatting.RED);
		sb.append("================= ROTARYCRAFT =======================\n");
		sb.append(EnumChatFormatting.RESET);
		sb.append("NOTICE: It has been detected that third-party plugins are being used to disable parts of RotaryCraft.\n");
		sb.append("This is frequently done to sell access to mod content, and is against the Terms of Use.\n");
		sb.append("All RotaryCraft functionality is temporarily disabled, and will be restored if the disables are removed.\n");
		sb.append("See your logs or contact Reika for further information.\n");
		sb.append(EnumChatFormatting.RED);
		sb.append("=====================================================");
		sb.append(EnumChatFormatting.RESET);
		return sb.toString();
	}

	@Override
	public void onPlayerLogin(EntityPlayer ep) {
		ReikaChatHelper.sendChatToPlayer(ep, message);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {

	}

}
