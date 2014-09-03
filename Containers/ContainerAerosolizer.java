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
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;

public class ContainerAerosolizer extends CoreContainer
{
	private TileEntityAerosolizer aerosolizer;
	private int lastAerosolizerCookTime;
	private int lastAerosolizerBurnTime;
	private int lastAerosolizerItemBurnTime;

	public ContainerAerosolizer(EntityPlayer player, TileEntityAerosolizer par2TileEntityAerosolizer)
	{
		super(player, par2TileEntityAerosolizer);
		lastAerosolizerCookTime = 0;
		lastAerosolizerBurnTime = 0;
		lastAerosolizerItemBurnTime = 0;
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
