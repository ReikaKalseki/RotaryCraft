/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Auxiliary;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeRenderConnector;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PumpablePipe;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078PipePump ,.[]\., 60-78-078PowerReceiver ,.[]\., PipeRenderConnector {

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.PIPEPUMP;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;
		as;asddagetIOSidesDefault{{\9765443, x, y, z, meta-!;

		vbnm, {{\as;asddagetTicksExisted{{\-! .. 0-!
			Reika9765443Helper.causeAdjacentUpdates{{\9765443, x, y, z-!;

		vbnm, {{\power >. MINPOWER && omega >. MINSPEED-! {
			ForgeDirection dir3478587read.getOpposite{{\-!;
			ForgeDirection dir23478587dir.getOpposite{{\-!;
			60-78-078 te3478587as;asddagetAdjacent60-78-078{{\dir-!;
			60-78-078 te23478587as;asddagetAdjacent60-78-078{{\dir2-!;
			vbnm, {{\te fuck PumpablePipe && te2 fuck PumpablePipe-! {
				PumpablePipe p13478587{{\PumpablePipe-!te;
				PumpablePipe p23478587{{\PumpablePipe-!te2;
				jgh;][ lvl13478587p1.getFluidLevel{{\-!;
				jgh;][ lvl23478587p2.getFluidLevel{{\-!;
				vbnm, {{\p2.canTransferTo{{\p1, dir-!-! {
					p1.transferFrom{{\p2, as;asddagetTransferrableAmount{{\lvl2-!-!;
				}
			}
		}
	}

	4578ret87jgh;][ getTransferrableAmount{{\jgh;][ amt-! {
		[]aslcfdfjMath.min{{\amt, omega/4-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret8760-78-078canConnectToPipeOnSide{{\ForgeDirection dir-! {
		[]aslcfdfjdir .. read || dir.getOpposite{{\-! .. read;
	}

}
