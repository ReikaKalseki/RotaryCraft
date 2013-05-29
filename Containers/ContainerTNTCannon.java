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
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;

public class ContainerTNTCannon extends CoreContainer
{
    private TileEntityTNTCannon cannon;

    public ContainerTNTCannon(EntityPlayer player, TileEntityTNTCannon par2TileEntityTNTCannon)
    {
    	super(player, par2TileEntityTNTCannon);
        cannon = par2TileEntityTNTCannon;
        int posX = cannon.xCoord;
        int posY = cannon.yCoord;
        int posZ = cannon.zCoord;
        int dy = 114;
        if (cannon.targetMode)
        	dy = 48;
        int i = 0;
        //for (int i = 0; i < 2; i++)
        	for (int j = 0; j < 9; j++)
        		this.addSlotToContainer(new Slot(par2TileEntityTNTCannon, j+9*i, 8+j*18, dy+i*18+18));
        dy += 40;
        for (i = 0; i < 3; i++)
            for (int k = 0; k < 9; k++)
                this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, dy + i * 18));
        dy += 58;
        for (int j = 0; j < 9; j++)
            this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, dy));
    }
}
