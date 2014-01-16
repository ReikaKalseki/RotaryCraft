package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;

public class ContainerPowerBus extends CoreContainer {

	private final TileEntityPowerBus te;

	public ContainerPowerBus(EntityPlayer player, TileEntityPowerBus te) {
		super(player, te);

		this.te = te;

		this.addSlot(0, 66, 8);
		this.addSlot(1, 66, 123);

		this.addSlot(2, 8, 66);
		this.addSlot(3, 124, 66);

		this.addPlayerInventoryWithOffset(player, 0, 64);
	}

}
