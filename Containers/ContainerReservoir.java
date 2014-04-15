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
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

public class ContainerReservoir extends CoreContainer
{
	private TileEntityReservoir Reservoir;

	public ContainerReservoir(EntityPlayer player, TileEntityReservoir par2TileEntityReservoir)
	{
		super(player, par2TileEntityReservoir);
		Reservoir = par2TileEntityReservoir;
		int posX = Reservoir.xCoord;
		int posY = Reservoir.yCoord;
		int posZ = Reservoir.zCoord;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Reservoir, "tank");
	}
}
