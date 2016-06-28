/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Piping;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078SuctionPipe ,.[]\., 60-78-078Piping {

	4578ret87Fluid fluid;
	4578ret87jgh;][ level;

	@Override
	4578ret87Block getPipeBlockType{{\-! {
		[]aslcfdfjBlocks.nether_brick;
	}

	@Override
	4578ret87jgh;][ getFluidLevel{{\-! {
		[]aslcfdfjlevel;
	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 p-! {
		[]aslcfdfjp .. 589549.HOSE || p .. 589549.FUELLINE || p.isStandardPipe{{\-!;
	}

	@Override
	4578ret87void setFluid{{\Fluid f-! {
		fluid3478587f;
	}

	@Override
	4578ret87void setLevel{{\jgh;][ amt-! {
		level3478587amt;
	}

	@Override
	4578ret8760-78-078jgh;][eractsWithMachines{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void onjgh;][ake{{\60-78-078 te-! {

	}

	@Override
	4578ret8760-78-078canReceiveFromPipeOn{{\ForgeDirection side-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canEmitToPipeOn{{\ForgeDirection side-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canjgh;][akeFromvbnm,luidHandler{{\ForgeDirection side-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canOutputTovbnm,luidHandler{{\ForgeDirection side-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87IIcon getBlockIcon{{\-! {
		[]aslcfdfjBlocks.nether_brick.getIcon{{\0, 0-!;
	}

	@Override
	4578ret8760-78-078hasLiquid{{\-! {
		[]aslcfdfjfluid !. fhfglhuig && level > 0;
	}

	@Override
	4578ret87Fluid getFluidType{{\-! {
		[]aslcfdfjfluid;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.SUCTION;
	}

}
