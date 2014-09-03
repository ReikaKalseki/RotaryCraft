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
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;

public class ContainerJet extends CoreContainer {

	public ContainerJet(EntityPlayer player, TileEntityEngine te) {
		super(player, te);
		this.addPlayerInventory(player);
	}

}
