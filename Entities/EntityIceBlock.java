/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityIceBlock extends Entity {

	public double xWidth;
	public double yWidth;
	public double zWidth;

	private EntityLivingBase target;

	public EntityIceBlock(World world) {
		super(world);
	}

	public EntityIceBlock(World world, EntityLivingBase t) {
		super(world);
		if (t == null) {
			this.setDead();
			return;
		}
		target = t;
		posX = target.posX;
		posY = target.posY;
		posZ = target.posZ;
		xWidth = target.width+0.15;
		zWidth = target.width+0.15;
		yWidth = target.height+0.375;
		//rotationPitch = target.rotationPitch;
		//rotationYaw = -target.rotationYawHead;
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (target == null)
			return;
		if (target.isDead) {
			this.setDead();
			return;
		}
		posX = target.posX;
		posY = target.posY;
		posZ = target.posZ;
		xWidth = target.width+0.15;
		zWidth = target.width+0.15;
		yWidth = target.height+0.375;
		height = (float) yWidth;
		width = (float) xWidth;
		//rotationPitch = target.rotationPitch;
		//rotationYaw = -target.rotationYawHead;
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound NBT) {
		xWidth = NBT.getDouble("xw");
		yWidth = NBT.getDouble("yw");
		zWidth = NBT.getDouble("zw");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound NBT) {
		NBT.setDouble("xw", xWidth);
		NBT.setDouble("yw", yWidth);
		NBT.setDouble("zw", zWidth);
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return AxisAlignedBB.getAABBPool().getAABB(posX, posY, posZ, posX+xWidth, posY+yWidth, posZ+zWidth);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !isDead;
	}

	@Override
	public boolean canBePushed()
	{
		return true;
	}

}
