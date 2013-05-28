/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;

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

        this.addPlayerInventory(player);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);

            icrafting.sendProgressBarUpdate(this, 1, Reservoir.liquidID);
            icrafting.sendProgressBarUpdate(this, 2, Reservoir.liquidLevel);
        }
    }

    @Override
	public void updateProgressBar(int par1, int par2)
    {
        switch(par1) {
	        case 1: Reservoir.liquidID = par2; break;
	        case 2: Reservoir.liquidLevel = par2; break;
        }
    }
}
