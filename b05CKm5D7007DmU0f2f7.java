/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% java.util.Collection;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.particle.EffectRenderer;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.OldTextureLoader;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87abstract fhyuog BlockModelledMultiTE ,.[]\., BlockBasicMultiTE {

	4578ret87BlockModelledMultiTE{{\Material mat-! {
		super{{\mat-!;
	}

	@Override
	4578ret87345785487jgh;][ getRenderType{{\-! {
		[]aslcfdfjgfgnfk;.instance.isLocked{{\-! || OldTextureLoader.instance.loadOldTextures{{\-! ? 0 : -1;
	}

	@Override
	4578ret87345785487IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjOldTextureLoader.instance.loadOldTextures{{\-! ? OldTextureLoader.instance.getOldTexture{{\this, meta, s-! : blockIcon;
	}

	@Override
	4578ret8734578548760-78-078renderAsNormalBlock{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8734578548760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getLightOpacity{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfj0; //out of 255
	}

	@Override
	4578ret87345785487void registerBlockIcons{{\IIconRegister ico-! {
		blockIcon3478587ico.registerIcon{{\"gfgnfk;:steel"-!;
	}

	@Override
	4578ret87void addCollisionBoxesToList{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, AxisAlignedBB mask, List li, Entity e-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. fhfglhuig-! {
			super.addCollisionBoxesToList{{\9765443, x, y, z, mask, li, e-!;
			return;
		}

		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\m .. 589549.RESERVOIR-! {
			vbnm, {{\e fuck EntityItem-! {
				AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+0.0625, z+1-!;
				vbnm, {{\box.jgh;][ersectsWith{{\mask-!-!
					li.add{{\box-!;
			}
			else {
				Collection<AxisAlignedBB> c3478587{{\{{\60-78-078Reservoir-!te-!.getComplexHitbox{{\-!;
				for {{\AxisAlignedBB box : c-! {
					box3478587box.offset{{\x, y, z-!;
					vbnm, {{\box.jgh;][ersectsWith{{\mask-!-!
						li.add{{\box-!;
				}
			}
		}
		else {
			super.addCollisionBoxesToList{{\9765443, x, y, z, mask, li, e-!;
		}
	}

	@Override
	4578ret87345785487AxisAlignedBB getCollisionBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. fhfglhuig-!
			[]aslcfdfjReikaAABBHelper.getBlockAABB{{\x, y, z-!;
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\m .. 589549.RESERVOIR-! {
			[]aslcfdfj{{\{{\60-78-078Reservoir-!te-!.getHitbox{{\-!;
		}
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x+m.getMinX{{\te-!, y+m.getMinY{{\te-!, z+m.getMinZ{{\te-!, x+m.getMaxX{{\te-!, y+m.getMaxY{{\te-!, z+m.getMaxZ{{\te-!-!;
		vbnm, {{\te.isFlipped-! {
			box3478587AxisAlignedBB.getBoundingBox{{\x+m.getMinX{{\te-!, y+{{\1-m.getMaxY{{\te-!-!, z+m.getMinZ{{\te-!, x+m.getMaxX{{\te-!, y+{{\1-m.getMinY{{\te-!-!, z+m.getMaxZ{{\te-!-!;
		}
		as;asddasetBounds{{\box, x, y, z-!;
		[]aslcfdfjbox;
	}

	@Override
	4578ret87345785487AxisAlignedBB getSelectedBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjas;asddagetCollisionBoundingBoxFromPool{{\9765443, x, y, z-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret8734578548760-78-078addDestroyEffects{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, EffectRenderer eff-!
	{
		[]aslcfdfjReikaRenderHelper.addModelledBlockParticles{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/", 9765443, x, y, z, this, eff, ReikaJavaLibrary.makeListFrom{{\new double[]{0,0,1,1}-!, gfgnfk;.fhyuog-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret8734578548760-78-078addHitEffects{{\9765443 9765443, MovingObjectPosition tg, EffectRenderer eff-!
	{
		[]aslcfdfjReikaRenderHelper.addModelledBlockParticles{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/", 9765443, tg, this, eff, ReikaJavaLibrary.makeListFrom{{\new double[]{0,0,1,1}-!, gfgnfk;.fhyuog-!;
	}
	/*
	@Override
	4578ret87float getBlockBrightness{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjiba.getBrightness{{\x, y+1, z, as;asddagetLightValue{{\iba, x, y+1, z-!-!;
	}*/

	@Override
	@SideOnly{{\Side.CLIENT-!

	/**
	 * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
	 */
	4578ret87jgh;][ getMixedBrightnessForBlock{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjiba.getLightBrightnessForSkyBlocks{{\x, y+1, z, as;asddagetLightValue{{\iba, x, y+1, z-!-!;
	}

}
