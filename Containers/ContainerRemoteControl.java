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
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.Base.TileEntity.RemoteControlMachine;

public class ContainerRemoteControl extends CoreContainer {

	public ContainerRemoteControl(EntityPlayer player, RemoteControlMachine te) {
		super(player, te);

		this.addSlotToContainer(new Slot(te, 0, 80, 17));

		this.addSlotToContainer(new Slot(te, 1, 62, 53));
		this.addSlotToContainer(new Slot(te, 2, 80, 53));
		this.addSlotToContainer(new Slot(te, 3, 98, 53));

		this.addPlayerInventory(player);
	}

}
