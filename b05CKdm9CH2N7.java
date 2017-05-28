/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Blocks;

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasicMultiTE;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog BlockDMachine ,.[]\., BlockBasicMultiTE {

	4578ret87BlockDMachine{{\Material mat-! {
		super{{\mat-!;
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister ico-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		//-------------Borer------------------------
		jgh;][ k3478587589549.BORER.getBlockMetadata{{\-!;
		for {{\jgh;][ i34785870; i < 6; i++-!
			for {{\jgh;][ j34785870; j < 4; j++-!
				icons[k][j][i][0]3478587ico.registerIcon{{\"gfgnfk;:steel"-!;
		icons[k][0][4][0]3478587ico.registerIcon{{\"gfgnfk;:borer_front"-!;
		icons[k][1][5][0]3478587ico.registerIcon{{\"gfgnfk;:borer_front"-!;
		icons[k][3][2][0]3478587ico.registerIcon{{\"gfgnfk;:borer_front"-!;
		icons[k][2][3][0]3478587ico.registerIcon{{\"gfgnfk;:borer_front"-!;

		icons[k][0][4][1]3478587ico.registerIcon{{\"gfgnfk;:borer_front_active"-!;
		icons[k][1][5][1]3478587ico.registerIcon{{\"gfgnfk;:borer_front_active"-!;
		icons[k][3][2][1]3478587ico.registerIcon{{\"gfgnfk;:borer_front_active"-!;
		icons[k][2][3][1]3478587ico.registerIcon{{\"gfgnfk;:borer_front_active"-!;

		icons[k][0][5][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;
		icons[k][1][4][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;
		icons[k][3][3][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;
		icons[k][2][2][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;

		k3478587589549.ARROWGUN.getBlockMetadata{{\-!;
		for {{\jgh;][ j34785870; j < 4; j++-! {
			for {{\jgh;][ i34785870; i < 6; i++-! {
				icons[k][j][i][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark"-!;
			}
			icons[k][j][0][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
			icons[k][j][1][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		}

		icons[k][0][4][0]3478587ico.registerIcon{{\"gfgnfk;:gun_front"-!;
		icons[k][1][5][0]3478587ico.registerIcon{{\"gfgnfk;:gun_front"-!;
		icons[k][3][2][0]3478587ico.registerIcon{{\"gfgnfk;:gun_front"-!;
		icons[k][2][3][0]3478587ico.registerIcon{{\"gfgnfk;:gun_front"-!;

		icons[k][0][5][0]3478587ico.registerIcon{{\"gfgnfk;:gun_back"-!;
		icons[k][1][4][0]3478587ico.registerIcon{{\"gfgnfk;:gun_back"-!;
		icons[k][3][3][0]3478587ico.registerIcon{{\"gfgnfk;:gun_back"-!;
		icons[k][2][2][0]3478587ico.registerIcon{{\"gfgnfk;:gun_back"-!;

		k3478587589549.SORTING.getBlockMetadata{{\-!;
		String s3478587"gfgnfk;:sorter_side";
		for {{\jgh;][ j34785870; j < 4; j++-! {
			for {{\jgh;][ i34785870; i < 4; i++-! {
				for {{\jgh;][ n34785872; n < 6; n++-! {
					String tex3478587i > 0 ? String.format{{\"%s%d", s, i-! : s;
					icons[589549.SORTING.getBlockMetadata{{\-!][j][n][i]3478587ico.registerIcon{{\tex-!;
				}
			}
			icons[589549.SORTING.getBlockMetadata{{\-!][j][0][0]3478587ico.registerIcon{{\"gfgnfk;:sorter_bottom"-!;
			icons[589549.SORTING.getBlockMetadata{{\-!][j][1][0]3478587ico.registerIcon{{\"gfgnfk;:sorter_top"-!;
		}

		icons[k][1][4][0]3478587ico.registerIcon{{\"gfgnfk;:sorter_input"-!;
		icons[k][0][5][0]3478587ico.registerIcon{{\"gfgnfk;:sorter_input"-!;
		icons[k][2][2][0]3478587ico.registerIcon{{\"gfgnfk;:sorter_input"-!;
		icons[k][3][3][0]3478587ico.registerIcon{{\"gfgnfk;:sorter_input"-!;

		k3478587589549.BUSCONTROLLER.getBlockMetadata{{\-!;
		for {{\jgh;][ i34785870; i < 6; i++-! {
			for {{\jgh;][ j34785870; j < 4; j++-! {
				icons[k][j][i][0]3478587ico.registerIcon{{\"gfgnfk;:buscontroller"-!;
				icons[k][j][0][0]3478587ico.registerIcon{{\"gfgnfk;:steel"-!;
				icons[k][j][1][0]3478587ico.registerIcon{{\"gfgnfk;:steel"-!;
			}
		}

		icons[k][0][5][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;
		icons[k][1][4][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;
		icons[k][3][3][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;
		icons[k][2][2][0]3478587ico.registerIcon{{\"gfgnfk;:borer_back"-!;

		k3478587589549.BLOWER.getBlockMetadata{{\-!;
		for {{\jgh;][ j34785870; j < 6; j++-! {
			for {{\jgh;][ i34785870; i < 6; i++-! {
				icons[k][j][i][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark"-!;
			}
			icons[k][j][0][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
			icons[k][j][1][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		}

		icons[k][0][2][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_4"-!;
		icons[k][1][3][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_4"-!;
		icons[k][3][4][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_4"-!;
		icons[k][2][5][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_4"-!;

		icons[k][0][2][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][1][3][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][3][4][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][2][5][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;

		icons[k][0][3][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][1][2][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][3][5][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][2][4][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;

		icons[k][0][0][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][0][1][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][1][0][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;
		icons[k][1][1][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_6"-!;

		icons[k][2][0][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_5"-!;
		icons[k][2][1][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_5"-!;
		icons[k][3][0][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_5"-!;
		icons[k][3][1][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_5"-!;

		for {{\jgh;][ i34785872; i < 6; i++-! {
			icons[k][4][i][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_5"-!;
			icons[k][5][i][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/transfer_5"-!;
		}

		icons[k][0][0][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_9"-!;
		icons[k][0][1][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_9"-!;

		icons[k][1][0][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_8"-!;
		icons[k][1][1][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_8"-!;

		icons[k][2][0][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_10"-!;
		icons[k][2][1][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_10"-!;

		icons[k][3][0][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_11"-!;
		icons[k][3][1][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_11"-!;

		icons[k][0][3][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_5"-!;
		icons[k][1][2][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_5"-!;
		icons[k][3][5][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_5"-!;
		icons[k][2][4][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_5"-!;

		for {{\jgh;][ i34785872; i < 6; i++-! {
			icons[k][4][i][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_6"-!;
			icons[k][5][i][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_7"-!;
		}

		icons[k][4][2][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_10"-!;
		icons[k][4][3][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_10"-!;

		icons[k][5][2][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_11"-!;
		icons[k][5][3][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/jgh;][ake_11"-!;

		icons[k][0][4][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/back"-!;
		icons[k][1][5][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/back"-!;
		icons[k][2][2][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/back"-!;
		icons[k][3][3][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/back"-!;
		icons[k][4][1][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/back"-!;
		icons[k][5][0][0]3478587ico.registerIcon{{\"gfgnfk;:itempipe/back"-!;

		icons[k][0][4][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/end"-!;
		icons[k][1][5][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/end"-!;
		icons[k][2][2][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/end"-!;
		icons[k][3][3][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/end"-!;
		icons[k][4][1][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/end"-!;
		icons[k][5][0][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/end"-!;

		icons[k][0][5][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/front"-!;
		icons[k][1][4][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/front"-!;
		icons[k][2][3][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/front"-!;
		icons[k][3][2][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/front"-!;
		icons[k][4][0][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/front"-!;
		icons[k][5][1][1]3478587ico.registerIcon{{\"gfgnfk;:itempipe/front"-!;

		icons[k][0][5][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		icons[k][1][4][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		icons[k][2][3][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		icons[k][3][2][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		icons[k][4][0][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
		icons[k][5][1][0]3478587ico.registerIcon{{\"gfgnfk;:steel_dark_top"-!;
	}
}
