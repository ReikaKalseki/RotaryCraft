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

import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerTerraformer extends CoreContainer {

	private TileEntityTerraformer terra;

	public ContainerTerraformer(EntityPlayer player, TileEntityTerraformer te) {
		super(player, te);
		terra = te;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				this.addSlotToContainer(new Slot(te, j*9+i, 72+i*18, 18+j*18));
			}
		}

		int dx = 46+18;
		int dy = 37;

		for (int j = 0; j < 3; ++j)
		{
			for (int k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, dx+8 + k * 18, 103 + j * 18 + dy));
			}
		}

		for (int j = 0; j < 9; ++j)
		{
			this.addSlotToContainer(new Slot(player.inventory, j, dx+8 + j * 18, 161 + dy));
		}
	}

}