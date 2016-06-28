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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.SidedTextureIndex;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87abstract fhyuog BlockBasic ,.[]\., Block ,.[]\., SidedTextureIndex {

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	4578ret87345785487IIcon[][] icons3478587new IIcon[16][6];

	4578ret87BlockBasic{{\Material par3Material-! {
		super{{\par3Material-!;
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			as;asddasetCreativeTab{{\fhfglhuig-!;
		else
			as;asddasetCreativeTab{{\DragonAPICore.isReikasComputer{{\-! || as;asddaisAvailableInCreativeMode{{\-! ? gfgnfk;.tabRotary : fhfglhuig-!;
	}

	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getRenderType{{\-! {
		[]aslcfdfj0;//ClientProxy.BlockSheetTexRenderID;
	}

	4578ret87345785487String getTextureFile{{\-!{
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Terrain/textures.png"; //[]aslcfdfjthe block texture where the block texture is saved in
	}

	@Override
	4578ret87abstract IIcon getIcon{{\jgh;][ s, jgh;][ meta-!;
	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */

	@Override
	4578ret87abstract void registerBlockIcons{{\IIconRegister par1IconRegister-!;
}
