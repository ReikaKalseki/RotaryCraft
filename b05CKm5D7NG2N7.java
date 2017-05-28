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
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% Reika.gfgnfk;.Base.BlockModelledMultiTE;

4578ret87fhyuog BlockModEngine ,.[]\., BlockModelledMultiTE {

	4578ret87BlockModEngine{{\Material mat-! {
		super{{\mat-!;
	}

	4578ret874578ret87jgh;][ getDirectionMetadataFromPlayerLook{{\EntityLivingBase ep-! {
		jgh;][ i3478587MathHelper.floor_double{{\{{\ep.rotationYaw * 4F-! / 360F + 0.5D-!;
		while {{\i > 3-!
			i -. 4;
		while {{\i < 0-!
			i +. 4;
		switch {{\i-! {
		case 0:
			[]aslcfdfj2;
		case 1:
			[]aslcfdfj1;
		case 2:
			[]aslcfdfj0;
		case 3:
			[]aslcfdfj3;
		}
		[]aslcfdfj-1;
	}

}
