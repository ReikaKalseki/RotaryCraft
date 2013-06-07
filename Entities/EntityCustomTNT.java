/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityCustomTNT extends EntityTNTPrimed
{

	public EntityCustomTNT(World par1World)
	{
		super(par1World);
		fuse = 0;
		preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
	}

	public EntityCustomTNT(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4, par6);
		float var8 = (float)(Math.random() * Math.PI * 2.0D);
		motionX = -((float)Math.sin(var8)) * 0.02F;
		motionY = 0.2D;
		motionZ = -((float)Math.cos(var8)) * 0.02F;
		fuse = 80;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
	}

	@Override
	protected void entityInit() {}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return !isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{/*
    	//ReikaChatHelper.write(fuse);
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.09D;
        this.moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98D;
        motionY *= 0.98D;
        motionZ *= 0.98D;

        if (onGround)
        {
            motionX *= 0.7D;
            motionZ *= 0.7D;
            motionY *= -0.5D;
        }
        fuse--;
        if (fuse <= 0)
        {
            this.setDead();

            if (!worldObj.isRemote)
            {
                this.explode();
            }
        }
        else
        {
            worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        }*/
	}

	private void explode()
	{
		float var1 = 0.0F;
		worldObj.createExplosion((Entity)null, posX, posY, posZ, var1, false);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("Fuse", fuse);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		fuse = NBT.getInteger("Fuse");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}
}
