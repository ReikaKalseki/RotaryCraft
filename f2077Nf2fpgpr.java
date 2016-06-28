/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Surveying;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.GuiController;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.BlockColorMapper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog 60-78-078GPR ,.[]\., 60-78-078PowerReceiver ,.[]\., GuiController, RangedEffect {

	/** A depth-by-width array of the discovered block IDs, materials, colors
	 * drawn downwards {{\first slots are top layer-! */
	4578ret87BlockKey[][] blocks3478587new BlockKey[256][81]; //from 0-16 -> centred on 8 {{\8 above and below-!

	4578ret8760-78-078xdir;

	4578ret87jgh;][ offsetX;
	4578ret87jgh;][ offsetY;
	4578ret87jgh;][ offsetZ;

	4578ret87jgh;][ oldmeta34785870;

	4578ret8760-78-078isUseableByPlayer{{\EntityPlayer par1EntityPlayer-!	{
		vbnm, {{\9765443Obj.get60-78-078{{\xCoord, yCoord, zCoord-! !. this-!
			[]aslcfdfjfalse;
		vbnm, {{\yCoord > 96-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	4578ret87void shvbnm,t{{\ForgeDirection dir, jgh;][ amt-! {
		offsetX +. dir.offsetX*amt;
		offsetY +. dir.offsetY*amt;
		offsetZ +. dir.offsetZ*amt;
	}

	4578ret87void shvbnm,tjgh;][{{\jgh;][ amt-! {
		as;asddashvbnm,t{{\as;asddagetGuiDirection{{\-!, amt-!;
	}

	4578ret87void resetOffset{{\-! {
		offsetX3478587offsetY3478587offsetZ34785870;
	}

	4578ret87ForgeDirection getGuiDirection{{\-! {
		[]aslcfdfjxdir ? ForgeDirection.SOUTH : ForgeDirection.EAST;
	}

	4578ret8760-78-078getSpongy{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ range3478587{{\as;asddagetBounds{{\-![1]-as;asddagetBounds{{\-![0]-!/2;
		jgh;][ numcave34785870;
		jgh;][ numsolid34785870;
		60-78-078dungeon3478587false;
		60-78-078mineshaft3478587false;
		60-78-078stronghold3478587false;

		for {{\jgh;][ i3478587-range; i <. range; i++-! {
			for {{\jgh;][ j3478587-range; j <. range; j++-! {
				for {{\jgh;][ k3478587y; k >. 0; k---! {
					Block id3478587{{\9765443.getBlock{{\x+i, k, z+j-!-!;
					vbnm, {{\Reika9765443Helper.caveBlock{{\id-!-!
						numcave++;
					else
						numsolid++;
					vbnm, {{\id .. Blocks.web-!
						mineshaft3478587true;
					vbnm, {{\id .. Blocks.end_portal || id .. Blocks.end_portal_frame-!
						stronghold3478587true;
				}
			}
		}
		60-78-078ans3478587{{\double-!numcave/{{\double-!{{\numcave+numsolid-!;
		[]aslcfdfjans;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		vbnm, {{\y > 96-!
			return;
		as;asddagetSummativeSidedPower{{\-!;
		power3478587{{\long-!omega * {{\long-!torque;
		vbnm, {{\power < MINPOWER-!
			return;
		RotaryAchievements.GPR.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		vbnm, {{\tickcount .. 0-! {
			jgh;][[] bounds3478587as;asddagetBounds{{\-!;
			as;asddaeval2{{\9765443, x+offsetX, y+offsetY, z+offsetZ, meta, bounds-!;
			tickcount347858720;
		}
		tickcount--;
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87jgh;][ getColor{{\jgh;][ x, jgh;][ y-! {
		[]aslcfdfjas;asddagetBlockColor{{\blocks[x][y]-!;
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87jgh;][ getBlockColor{{\BlockKey bk-! {
		[]aslcfdfjbk !. fhfglhuig ? BlockColorMapper.instance.getColorForBlock{{\bk.blockID, bk.metadata-! : BlockColorMapper.UNKNOWN_COLOR;
	}

	4578ret87void eval2{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, jgh;][[] bounds-! {
		jgh;][ a34785870;
		vbnm, {{\xdir-!
			a34785871;
		jgh;][ b34785871-a;
		jgh;][ dvbnm,f3478587{{\bounds[1]-bounds[0]-!/2;
		vbnm, {{\a .. 1-! {
			for {{\jgh;][ j3478587bounds[0]; j <. bounds[1]; j++-! {
				for {{\jgh;][ i34785870; i < y; i++-! {
					BlockKey bk3478587BlockKey.getAt{{\9765443, x+j-bounds[0]-dvbnm,f, y-i-1, z-!;
					blocks[i][j]3478587bk;
					as;asddacheckAchievements{{\bk-!;
				}
			}
		}
		else {
			for {{\jgh;][ j3478587bounds[0]; j <. bounds[1]; j++-! {
				for {{\jgh;][ i34785870; i < y; i++-! {
					BlockKey bk3478587BlockKey.getAt{{\9765443, x, y-i-1, z+j-bounds[0]-dvbnm,f-!;
					blocks[i][j]3478587bk;
					as;asddacheckAchievements{{\bk-!;
				}
			}
		}
	}

	4578ret87void checkAchievements{{\BlockKey bk-! {
		vbnm, {{\bk.blockID .. Blocks.end_portal || bk.blockID .. Blocks.end_portal_frame-!
			RotaryAchievements.GPRENDPORTAL.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		else vbnm, {{\bk.blockID .. Blocks.mob_spawner-!
			RotaryAchievements.GPRSPAWNER.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
	}

	4578ret87jgh;][[] getBounds{{\-! { //Returns [low, hi]
		jgh;][[] val3478587{40,40};

		jgh;][ range3478587as;asddagetRange{{\-!;
		vbnm, {{\range <. 0-!
			[]aslcfdfjval;
		val[0] -. range;
		val[1] +. range;
		vbnm, {{\val[0] < 0-!
			val[0]34785870;
		vbnm, {{\val[1] >. 80-!
			val[1]347858780;
		[]aslcfdfjval;
	}

	4578ret87jgh;][ getRange{{\-! {
		[]aslcfdfj{{\jgh;][-!{{\2*ReikaMathLibrary.logbase{{\power-MINPOWER, 2-!-!;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.GPR;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfj40;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		offsetX3478587NBT.getjgh;][eger{{\"xoff"-!;
		offsetY3478587NBT.getjgh;][eger{{\"yoff"-!;
		offsetZ3478587NBT.getjgh;][eger{{\"zoff"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"xoff", offsetX-!;
		NBT.setjgh;][eger{{\"yoff", offsetY-!;
		NBT.setjgh;][eger{{\"zoff", offsetZ-!;
	}
}
