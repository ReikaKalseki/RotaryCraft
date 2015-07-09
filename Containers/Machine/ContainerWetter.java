package Reika.RotaryCraft.Containers.Machine;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityWetter;

public class ContainerWetter extends OneSlotContainer {

	public ContainerWetter(EntityPlayer player, TileEntityWetter te) {
		super(player, te);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, tile, "tank");
	}

}
