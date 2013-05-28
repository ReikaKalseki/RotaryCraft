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
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public class ContainerPerformance extends CoreContainer
{
    private TileEntityEngine Engine;

    public ContainerPerformance(EntityPlayer player, TileEntityEngine par2TileEntityEngine)
    {
    	super(player, par2TileEntityEngine);
        Engine = par2TileEntityEngine;
        int posX = Engine.xCoord;
        int posY = Engine.yCoord;
        int posZ = Engine.zCoord;

        this.addSlotToContainer(new Slot(par2TileEntityEngine, 0, 58, 36));
        this.addSlotToContainer(new Slot(par2TileEntityEngine, 1, 103, 36));

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

            icrafting.sendProgressBarUpdate(this, 1, Engine.temperature);
            icrafting.sendProgressBarUpdate(this, 2, Engine.waterLevel);
            icrafting.sendProgressBarUpdate(this, 3, Engine.ethanols);
            icrafting.sendProgressBarUpdate(this, 4, Engine.additives);
        }
    }

    @Override
	public void updateProgressBar(int par1, int par2)
    {
        switch(par1) {
	        case 1: Engine.temperature = par2; break;
	        case 2: Engine.waterLevel = par2; break;
	        case 3: Engine.ethanols = par2; break;
	        case 4: Engine.additives = par2; break;
        }
    }
}
