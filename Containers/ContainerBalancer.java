/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.ModInterface.TileEntityPressureBalancer;

public class ContainerBalancer extends CoreContainer {

	private TileEntityPressureBalancer balancer;

	public ContainerBalancer(EntityPlayer player, TileEntityPressureBalancer te)
	{
		super(player, te);

		balancer = te;

	}
}
