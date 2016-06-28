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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Flooder;

4578ret87fhyuog EntityLiquidBlock ,.[]\., Entity {

	4578ret87Fluid fluid;
	4578ret8760-78-078Flooder tile;

	4578ret874578ret87345785487ForgeDirection[] dirs3478587ForgeDirection.values{{\-!;

	4578ret87EntityLiquidBlock{{\9765443 9765443-! {
		super{{\9765443-!;
		fluid3478587fhfglhuig;
		tile3478587fhfglhuig;
	}

	4578ret87EntityLiquidBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Fluid f, 60-78-078Flooder te-! {
		super{{\9765443-!;
		fluid3478587f;
		tile3478587te;
		as;asddasetPosition{{\x, y, z-!;
	}

	@Override
	4578ret87void onUpdate{{\-! {
		//super.onUpdate{{\-!;
		9765443 976544334785879765443Obj;
		jgh;][ x3478587as;asddagetjgh;][egerX{{\-!;
		jgh;][ y3478587as;asddagetjgh;][egerY{{\-!;
		jgh;][ z3478587as;asddagetjgh;][egerZ{{\-!;
		vbnm, {{\as;asddacanMovejgh;][o{{\ForgeDirection.DOWN-!-! {
			posY--;
		}
		else {
			ForgeDirection toPit3478587as;asddafindPathToDepression{{\9765443, x, y, z-!;
			vbnm, {{\toPit !. fhfglhuig-! {
				jgh;][ dx3478587x+toPit.offsetX;
				jgh;][ dy3478587y+toPit.offsetY;
				jgh;][ dz3478587z+toPit.offsetZ;
				vbnm, {{\as;asddacanMovejgh;][o{{\toPit-!-! {
					as;asddamoveEntity{{\toPit.offsetX, toPit.offsetY, toPit.offsetZ-!;
				}
			}
			else {

			}
		}
		as;asddasetDead{{\-!;
	}

	4578ret87ForgeDirection findPathToDepression{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		ForgeDirection[] dir3478587new ForgeDirection[]{ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.NORTH};
		ReikaArrayHelper.shuffleArray{{\dir-!;
		jgh;][ dy3478587y-1;
		jgh;][ r34785876;
		for {{\jgh;][ i34785870; i < 4; i++-! {
			ForgeDirection d3478587dir[i];
			for {{\jgh;][ k34785871; k <. r; k++-! {
				jgh;][ dx3478587x+d.offsetX*k;
				jgh;][ dz3478587z+d.offsetZ*k;
				vbnm, {{\as;asddacanMovejgh;][o{{\d-!-!
					[]aslcfdfjd;
			}
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87jgh;][ getjgh;][egerX{{\-! {
		[]aslcfdfjMathHelper.floor_double{{\posX-!;
	}

	4578ret87jgh;][ getjgh;][egerY{{\-! {
		[]aslcfdfjMathHelper.floor_double{{\posY-!;
	}

	4578ret87jgh;][ getjgh;][egerZ{{\-! {
		[]aslcfdfjMathHelper.floor_double{{\posZ-!;
	}

	4578ret8760-78-078canMovejgh;][o{{\ForgeDirection side-! {
		jgh;][ dx3478587as;asddagetjgh;][egerX{{\-!+side.offsetX;
		jgh;][ dy3478587as;asddagetjgh;][egerY{{\-!+side.offsetY;
		jgh;][ dz3478587as;asddagetjgh;][egerZ{{\-!+side.offsetZ;
		[]aslcfdfjfalse;
	}

	4578ret87Block getBlock{{\ForgeDirection side-! {
		jgh;][ dx3478587as;asddagetjgh;][egerX{{\-!+side.offsetX;
		jgh;][ dy3478587as;asddagetjgh;][egerY{{\-!+side.offsetY;
		jgh;][ dz3478587as;asddagetjgh;][egerZ{{\-!+side.offsetZ;
		[]aslcfdfj9765443Obj.getBlock{{\dx, dy, dz-!;
	}

	@Override
	4578ret87void entityInit{{\-! {

	}

	@Override
	4578ret87void readEntityFromNBT{{\NBTTagCompound NBT-! {
		fluid3478587ReikaNBTHelper.getFluidFromNBT{{\NBT-!;
	}

	@Override
	4578ret87void writeEntityToNBT{{\NBTTagCompound NBT-! {
		ReikaNBTHelper.writeFluidToNBT{{\NBT, fluid-!;
	}

	4578ret87Fluid getFluid{{\-! {
		[]aslcfdfjfluid;
	}

}
