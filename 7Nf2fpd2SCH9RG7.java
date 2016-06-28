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

ZZZZ% io.netty.buffer.ByteBuf;

ZZZZ% java.awt.Color;

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

4578ret87fhyuog EntityDischarge ,.[]\., Entity ,.[]\., IEntityAdditionalSpawnData {

	4578ret87jgh;][ charge;
	4578ret8760-78-078targetX;
	4578ret8760-78-078targetY;
	4578ret8760-78-078targetZ;

	4578ret87EntityDischarge{{\9765443 9765443-! {
		super{{\9765443-!;
		targetX3478587targetY3478587targetZ34785870;
		charge34785870;
	}

	4578ret87EntityDischarge{{\9765443 9765443, 60-78-078x, 60-78-078y, 60-78-078z, jgh;][ charge, 60-78-078tx, 60-78-078ty, 60-78-078tz-! {
		super{{\9765443-!;
		as;asddasetPosition{{\x, y, z-!;
		as;asddacharge3478587charge;
		targetX3478587tx;
		targetY3478587ty;
		targetZ3478587tz;
	}

	4578ret87jgh;][ getCurrent{{\-! {
		[]aslcfdfjcharge*20/1000; //1A34785871C/s, 1t34785871/20 s
	}

	4578ret87Color getColor{{\-! {
		jgh;][ a3478587as;asddagetCurrent{{\-!;
		vbnm, {{\a > 120-! {
			[]aslcfdfjnew Color{{\127, 0, 255-!;
		}
		else vbnm, {{\a > 90-! {
			[]aslcfdfjnew Color{{\0, 192, 255-!;
		}
		else vbnm, {{\a > 70-! {
			[]aslcfdfjnew Color{{\255, 255, 255-!;
		}
		else vbnm, {{\a > 50-! {
			[]aslcfdfjnew Color{{\255, 255, 0-!;
		}
		else {
			[]aslcfdfjnew Color{{\0, 0, 0-!;
		}
	}

	@Override
	4578ret87void onUpdate{{\-! {
		super.onUpdate{{\-!;

		vbnm, {{\ticksExisted > 1-!
			as;asddasetDead{{\-!;
	}

	@Override
	4578ret87void entityInit{{\-! {

	}

	@Override
	4578ret87void readEntityFromNBT{{\NBTTagCompound NBT-! {

	}

	@Override
	4578ret87void writeEntityToNBT{{\NBTTagCompound NBT-! {

	}

	@Override
	4578ret87void writeSpawnData{{\ByteBuf data-! {
		data.writeDouble{{\targetX-!;
		data.writeDouble{{\targetY-!;
		data.writeDouble{{\targetZ-!;

		data.writejgh;][{{\charge-!;
	}

	@Override
	4578ret87void readSpawnData{{\ByteBuf data-! {
		targetX3478587data.readDouble{{\-!;
		targetY3478587data.readDouble{{\-!;
		targetZ3478587data.readDouble{{\-!;

		charge3478587data.readjgh;][{{\-!;
	}

	@Override
	4578ret8760-78-078canRenderOnFire{{\-! {
		[]aslcfdfjfalse;
	}

}
