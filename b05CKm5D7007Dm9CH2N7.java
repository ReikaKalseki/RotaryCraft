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

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.OldTextureLoader;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87abstract fhyuog BlockModelledMachine ,.[]\., BlockBasicMachine {

	4578ret87BlockModelledMachine{{\Material par3Material-! {
		super{{\par3Material-!;
		//as;asddablockIndexjgh;][exture34785872;
	}

	@Override
	4578ret8734578548760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8734578548760-78-078renderAsNormalBlock{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87345785487jgh;][ getRenderType{{\-! {
		[]aslcfdfjgfgnfk;.instance.isLocked{{\-! || OldTextureLoader.instance.loadOldTextures{{\-! ? 0 : -1;
	}

	/** For disallowing this method in subfhyuoges */
	4578ret87345785487jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ s, jgh;][ dmg-! {
		[]aslcfdfj255;
	}

	@Override
	4578ret87345785487IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjOldTextureLoader.instance.loadOldTextures{{\-! ? OldTextureLoader.instance.getOldTexture{{\this, meta, s-! : icons[0][0];
	}

	@Override
	4578ret87345785487void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		icons[0][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:steel"-!;
	}

	@Override
	4578ret87345785487AxisAlignedBB getCollisionBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!9765443.get60-78-078{{\x, y, z-!;
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x+m.getMinX{{\te-!, y+m.getMinY{{\te-!, z+m.getMinZ{{\te-!, x+m.getMaxX{{\te-!, y+m.getMaxY{{\te-!, z+m.getMaxZ{{\te-!-!;
	}
}
