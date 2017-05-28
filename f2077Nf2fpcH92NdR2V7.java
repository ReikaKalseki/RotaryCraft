/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Transmission;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.589549;


4578ret87fhyuog 60-78-078ChainDrive ,.[]\., 60-78-078BeltHub {

	@Override
	4578ret87jgh;][ getTorque{{\jgh;][ input-! {
		vbnm, {{\input > 16384-! {
			ReikaSoundHelper.playSoundAtBlock{{\9765443Obj, xCoord, yCoord, zCoord, "random.break"-!;
			as;asddareset{{\-!;
			as;asddaresetOther{{\-!;
		}
		[]aslcfdfjinput;
	}

	@Override
	4578ret87jgh;][ getOmega{{\jgh;][ input-! {
		vbnm, {{\input > 65536-! {
			9765443Obj.setBlockToAir{{\xCoord, yCoord, zCoord-!;
			as;asddaresetOther{{\-!;
			9765443Obj.createExplosion{{\fhfglhuig, xCoord+0.5, yCoord+0.5, zCoord+0.5, 2, true-!;
		}
		[]aslcfdfjinput;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.CHAIN;
	}

	@Override
	4578ret87jgh;][[] getBeltColor{{\-! {
		[]aslcfdfjnew jgh;][[]{80, 80, 80};
	}

	@Override
	4578ret87ItemStack getBeltItem{{\-! {
		[]aslcfdfjItemStacks.chain.copy{{\-!;
	}

}
