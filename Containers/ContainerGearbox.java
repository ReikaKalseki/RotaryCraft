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
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;

public class ContainerGearbox extends CoreContainer
{
    private TileEntityGearbox gearbox;

    public ContainerGearbox(EntityPlayer player, TileEntityGearbox par2TileEntityGearbox)
    {
    	super(player, par2TileEntityGearbox);
        gearbox = par2TileEntityGearbox;
        int posX = gearbox.xCoord;
        int posY = gearbox.yCoord;
        int posZ = gearbox.zCoord;

        this.addSlotToContainer(new Slot(par2TileEntityGearbox, 0, 35, 60));

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

            icrafting.sendProgressBarUpdate(this, 1, gearbox.lubricant);
            icrafting.sendProgressBarUpdate(this, 2, gearbox.damage);
            icrafting.sendProgressBarUpdate(this, 3, gearbox.omega);
            icrafting.sendProgressBarUpdate(this, 4, gearbox.torque);
        }
    }

    @Override
	public void updateProgressBar(int par1, int par2)
    {
        switch(par1) {
	        case 1: gearbox.lubricant = par2; break;
	        case 2: gearbox.damage = par2; break;
	        case 3: gearbox.omega = par2; break;
	        case 4: gearbox.torque = par2; break;
        }
    }
}
