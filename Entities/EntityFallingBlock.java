/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFallingBlock extends EntityFallingSand {

	public EntityFallingBlock(World par1World) {
		super(par1World);
	}

	public EntityFallingBlock(World world, double x, double y, double z, int id, int meta) {
		super(world);
		blockID = id;
		metadata = meta;
        fallTime = 0;
        shouldDropItem = false;
        preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        this.setPosition(x, y, z);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
	}

    @Override
	public void onUpdate()
    {
        this.setDead();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
	protected void writeEntityToNBT(NBTTagCompound NBT)
    {
    	super.writeToNBT(NBT);
        NBT.setByte("Tile", (byte)blockID);
        NBT.setByte("Data", (byte)metadata);
        NBT.setByte("Time", (byte)fallTime);
        NBT.setBoolean("DropItem", shouldDropItem);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	protected void readEntityFromNBT(NBTTagCompound NBT)
    {
    	super.readFromNBT(NBT);
        blockID = NBT.getByte("Tile") & 255;
        metadata = NBT.getByte("Data") & 255;
        fallTime = NBT.getByte("Time") & 255;

        if (NBT.hasKey("DropItem"))
        {
            shouldDropItem = NBT.getBoolean("DropItem");
        }
    }

}
