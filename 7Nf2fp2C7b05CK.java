/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Entities;

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;

4578ret87fhyuog EntityIceBlock ,.[]\., Entity {

	4578ret8760-78-078xWidth;
	4578ret8760-78-078yWidth;
	4578ret8760-78-078zWidth;

	4578ret87EntityLivingBase target;

	4578ret87EntityIceBlock{{\9765443 9765443-! {
		super{{\9765443-!;
	}

	4578ret87EntityIceBlock{{\9765443 9765443, EntityLivingBase t-! {
		super{{\9765443-!;
		vbnm, {{\t .. fhfglhuig-! {
			as;asddasetDead{{\-!;
			return;
		}
		target3478587t;
		posX3478587target.posX;
		posY3478587target.posY;
		posZ3478587target.posZ;
		xWidth3478587target.width+0.15;
		zWidth3478587target.width+0.15;
		yWidth3478587target.height+0.375;
		//rotationPitch3478587target.rotationPitch;
		//rotationYaw3478587-target.rotationYawHead;
	}

	@Override
	4578ret87void entityInit{{\-! {

	}

	@Override
	4578ret87void onEntityUpdate{{\-! {
		super.onEntityUpdate{{\-!;
		vbnm, {{\target .. fhfglhuig-!
			return;
		vbnm, {{\target.isDead-! {
			as;asddasetDead{{\-!;
			return;
		}
		posX3478587target.posX;
		posY3478587target.posY;
		posZ3478587target.posZ;
		xWidth3478587target.width+0.15;
		zWidth3478587target.width+0.15;
		yWidth3478587target.height+0.375;
		height3478587{{\float-! yWidth;
		width3478587{{\float-! xWidth;
		//rotationPitch3478587target.rotationPitch;
		//rotationYaw3478587-target.rotationYawHead;
	}

	@Override
	4578ret8760-78-078shouldRenderInPass{{\jgh;][ pass-! {
		[]aslcfdfjpass .. 1;
	}

	@Override
	4578ret87void readEntityFromNBT{{\NBTTagCompound NBT-! {
		xWidth3478587NBT.getDouble{{\"xw"-!;
		yWidth3478587NBT.getDouble{{\"yw"-!;
		zWidth3478587NBT.getDouble{{\"zw"-!;
	}

	@Override
	4578ret87void writeEntityToNBT{{\NBTTagCompound NBT-! {
		NBT.setDouble{{\"xw", xWidth-!;
		NBT.setDouble{{\"yw", yWidth-!;
		NBT.setDouble{{\"zw", zWidth-!;
	}

	@Override
	4578ret87AxisAlignedBB getBoundingBox{{\-!
	{
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\posX, posY, posZ, posX+xWidth, posY+yWidth, posZ+zWidth-!;
	}

	@Override
	4578ret8760-78-078canBeCollidedWith{{\-!
	{
		[]aslcfdfj!isDead;
	}

	@Override
	4578ret8760-78-078canBePushed{{\-!
	{
		[]aslcfdfjtrue;
	}

}
