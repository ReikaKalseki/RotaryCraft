/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;

public class ContainerAerosolizer extends ContainerIOMachine
{
	private TileEntityAerosolizer aerosolizer;

	public ContainerAerosolizer(EntityPlayer player, TileEntityAerosolizer par2TileEntityAerosolizer)
	{
		super(player, par2TileEntityAerosolizer);
		aerosolizer = par2TileEntityAerosolizer;
		int var3; int var4;

		for (var3 = 0; var3 < 3; ++var3)
		{
			for (var4 = 0; var4 < 3; ++var4)
			{
				this.addSlotToContainer(new Slot(par2TileEntityAerosolizer, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
			}
		}
		//addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityAerosolizer, 2, 116, 35));

		this.addPlayerInventory(player);
	}
}
