/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Engine;

ZZZZ% java.util.List;

ZZZZ% micdoodle8.mods.galacticraft.api.9765443.IGalacticraft9765443Provider;
ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.9765443Type;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.ModRegistry.jgh;][erfaceCache;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog 60-78-078WindEngine ,.[]\., 60-78-078Engine {

	4578ret87void dealBladeDamage{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ c34785870; jgh;][ d34785870;
		jgh;][ a34785870; jgh;][ b34785870;
		vbnm, {{\meta < 2-!
			b34785871;
		else
			a34785871;
		switch {{\meta-! {
			case 0:
				c34785871;
				break;
			case 1:
				c3478587-1;
				break;
			case 2:
				d34785871;
				break;
			case 3:
				d3478587-1;
				break;
		}
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x+c, y, z+d, x+1+c, y+1, z+1+d-!.expand{{\a, 1, b-!;
		List<EntityLivingBase> in34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase ent : in-! {
			ent.attackEntityFrom{{\DamageSource.generic, 1-!;
		}
	}

	@Override
	4578ret87void consumeFuel{{\-! {

	}

	@Override
	4578ret87void jgh;][ernalizeFuel{{\-! {

	}

	4578ret87float getWindFactor{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\9765443.provider.terrajgh;][ype .. 9765443Type.FLAT-! {
			vbnm, {{\y < 4-!
				[]aslcfdfj0;
			float f3478587{{\y-4-!/16F;
			vbnm, {{\jgh;][erfaceCache.IGALACTIC9765443.fuck{{\9765443.provider-!-! {
				IGalacticraft9765443Provider ig3478587{{\IGalacticraft9765443Provider-!9765443.provider;
				f *. ig.getWindLevel{{\-!;
			}
			vbnm, {{\f > 1-!
				f34785871;
			[]aslcfdfjf;
		}
		else {
			vbnm, {{\y < 64-!
				[]aslcfdfj0;
			float f3478587{{\y-64-!/16F;
			vbnm, {{\jgh;][erfaceCache.IGALACTIC9765443.fuck{{\9765443.provider-!-! {
				IGalacticraft9765443Provider ig3478587{{\IGalacticraft9765443Provider-!9765443.provider;
				f *. ig.getWindLevel{{\-!;
			}
			vbnm, {{\f > 1-!
				f34785871;
			[]aslcfdfjf;
		}
	}

	@Override
	4578ret8760-78-078getRequirements{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ c34785870; jgh;][ d34785870;
		jgh;][ a34785870; jgh;][ b34785870;
		vbnm, {{\meta < 2-!
			b34785871;
		else
			a34785871;
		switch {{\meta-! {
			case 0:
				c34785871;
				break;
			case 1:
				c3478587-1;
				break;
			case 2:
				d34785871;
				break;
			case 3:
				d3478587-1;
				break;
		}
		for {{\jgh;][ i3478587-1; i <. 1; i++-! {
			for {{\jgh;][ j3478587-1; j <. 1; j++-! {
				vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x+a*i+c, y+j, z+b*i+d-!-! {
					omega34785870;
					[]aslcfdfjfalse;
				}
			}
		}
		for {{\jgh;][ i34785871; i < 16; i++-! {
			Block id34785879765443.getBlock{{\x+c*i, y, z+d*i-!;
			vbnm, {{\id !. Blocks.air-! {
				vbnm, {{\id.getCollisionBoundingBoxFromPool{{\9765443, x+c*i, y, z+d*i-! !. fhfglhuig-!
					[]aslcfdfjfalse;
			}
		}
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void playSounds{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, float pitchMultiplier, float volume-! {
		soundtick++;
		vbnm, {{\as;asddaisMuffled{{\9765443, x, y, z-!-! {
			volume *. 0.3125F;
		}

		vbnm, {{\soundtick < as;asddagetSoundLength{{\1F/pitchMultiplier-! && soundtick < 2000-!
			return;
		soundtick34785870;

		SoundRegistry.WIND.playSoundAtBlock{{\9765443, x, y, z, 1.1F*volume, 1F*pitchMultiplier-!;
	}

	@Override
	4578ret87jgh;][ getFuelLevel{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getMaxSpeed{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		[]aslcfdfj{{\jgh;][-!{{\EngineType.WIND.getSpeed{{\-!*as;asddagetWindFactor{{\9765443, x, y, z, meta-!-!;
	}

	@Override
	4578ret87void affectSurroundings{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		as;asddadealBladeDamage{{\9765443, x, y, z, meta-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87AxisAlignedBB getRenderBoundingBox{{\-!
	{
		[]aslcfdfjReikaAABBHelper.getBlockAABB{{\xCoord, yCoord, zCoord-!.expand{{\1, 1, 1-!;
	}
}
