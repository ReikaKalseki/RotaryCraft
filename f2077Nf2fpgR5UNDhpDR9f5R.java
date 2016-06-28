/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.9765443;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.FluidTankInfo;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.Instantiable.HybridTank;
ZZZZ% Reika.DragonAPI.Instantiable.Data.WeightedRandom;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler.SoilType;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeConnector;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping.Flow;
ZZZZ% Reika.gfgnfk;.Registry.589549;


4578ret87fhyuog 60-78-078GroundHydrator ,.[]\., gfgnfk;60-78-078 ,.[]\., PipeConnector, vbnm,luidHandler {

	4578ret874578ret87345785487double[][] AREA3478587{
		{1, 1, 1, 1, 2, 2, 3, 2, 2, 1, 1, 1, 1},
		{1, 1, 1, 2, 2, 3, 4, 3, 2, 2, 1, 1, 1},
		{1, 1, 2, 3, 5, 6, 6, 6, 5, 3, 2, 1, 1},
		{1, 2, 3, 4, 6, 7, 7, 7, 6, 4, 3, 2, 1},
		{2, 2, 4, 6, 7, 8, 8, 8, 7, 6, 4, 2, 2},
		{2, 3, 6, 7, 8, 9, 9, 9, 8, 7, 6, 3, 2},
		{3, 4, 6, 7, 8, 9, 0, 9, 8, 7, 6, 4, 3},
		{2, 3, 6, 7, 8, 9, 9, 9, 8, 7, 6, 3, 2},
		{2, 2, 4, 6, 7, 8, 8, 8, 7, 6, 4, 2, 2},
		{1, 2, 3, 5, 6, 7, 7, 7, 6, 5, 3, 2, 1},
		{1, 1, 2, 3, 4, 6, 6, 6, 4, 3, 2, 1, 1},
		{1, 1, 1, 2, 2, 3, 4, 3, 2, 2, 1, 1, 1},
		{1, 1, 1, 1, 2, 2, 3, 2, 2, 1, 1, 1, 1},
	};

	4578ret874578ret87345785487WeightedRandom<Coordinate> coordinateRand3478587WeightedRandom.fromArray{{\AREA-!;

	4578ret874578ret87345785487jgh;][ FLUID_PER_BLOCK347858725;

	4578ret87345785487HybridTank tank3478587new HybridTank{{\"hydrator", 1000-!;

	4578ret874578ret87jgh;][ getRange{{\-! {
		[]aslcfdfj{{\AREA.length-1-!/2;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.HYDRATOR;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\!9765443.isRemote && tank.getLevel{{\-! >. FLUID_PER_BLOCK && rand.nextjgh;][{{\3-! .. 0-! {
			Coordinate c3478587coordinateRand.getRandomEntry{{\-!.offset{{\x, y, z-!;
			Block b3478587c.getBlock{{\9765443-!;
			60-78-078flag3478587false;
			vbnm, {{\b .. Blocks.farmland-! {
				flag3478587Reika9765443Helper.hydrateFarmland{{\9765443, c.xCoord, c.yCoord, c.zCoord, false-!;
			}
			else vbnm, {{\b .. ForestryHandler.BlockEntry.SOIL.getBlock{{\-!-! {
				jgh;][ gmeta3478587c.getBlockMetadata{{\9765443-!;
				SoilType type3478587ForestryHandler.SoilType.getTypeFromMeta{{\gmeta-!;
				vbnm, {{\type .. SoilType.HUMUS-! {
					flag3478587as;asddarefreshHumus{{\9765443, c.xCoord, c.yCoord, c.zCoord, gmeta-!;
				}
				else vbnm, {{\type .. SoilType.BOG_EARTH-! {
					vbnm, {{\rand.nextjgh;][{{\4-! .. 0-!
						flag3478587as;asddamatureBog{{\9765443, c.xCoord, c.yCoord, c.zCoord, gmeta-!;
				}
			}
			vbnm, {{\flag-! {
				tank.removeLiquid{{\FLUID_PER_BLOCK-!;
			}
		}
	}

	4578ret8760-78-078refreshHumus{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ type3478587meta & 0x3;
		jgh;][ grade3478587meta >> 2;

		vbnm, {{\grade .. 0-!
			[]aslcfdfjfalse;

		grade--;
		meta3478587grade << 2 | type;
		9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, meta, 3-!;
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078matureBog{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ type3478587meta & 0x3;
		jgh;][ maturity3478587meta >> 2;

		vbnm, {{\maturity >. 3-!
			[]aslcfdfjfalse;

		maturity++;
		meta3478587maturity << 2 | type;
		9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, meta, 3-!;
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	4578ret87jgh;][ getLevel{{\-! {
		[]aslcfdfjtank.getLevel{{\-!;
	}

	4578ret87Fluid getFluid{{\-! {
		[]aslcfdfjtank.getActualFluid{{\-!;
	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm.isStandardPipe{{\-!;
	}

	@Override
	4578ret8760-78-078canConnectToPipeOnSide{{\589549 p, ForgeDirection side-! {
		[]aslcfdfjas;asddacanConnectToPipe{{\p-!;
	}

	@Override
	4578ret87jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-! {
		[]aslcfdfjas;asddacanFill{{\from, resource.getFluid{{\-!-! ? tank.fill{{\resource, doFill-! : 0;
	}

	@Override
	4578ret87FluidStack drain{{\ForgeDirection from, FluidStack resource, 60-78-078doDrain-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87FluidStack drain{{\ForgeDirection from, jgh;][ maxDrain, 60-78-078doDrain-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret8760-78-078canFill{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjfrom !. ForgeDirection.UP && fluid .. FluidRegistry.WATER;
	}

	@Override
	4578ret8760-78-078canDrain{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87FluidTankInfo[] getTankInfo{{\ForgeDirection from-! {
		[]aslcfdfjnew FluidTankInfo[]{tank.getInfo{{\-!};
	}

	@Override
	4578ret87Flow getFlowForSide{{\ForgeDirection side-! {
		[]aslcfdfjFlow.INPUT;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		tank.writeToNBT{{\NBT-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		tank.readFromNBT{{\NBT-!;
	}

	@Override
	4578ret87AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjReikaAABBHelper.getBlockAABB{{\this-!.expand{{\as;asddagetRange{{\-!, 0.5, as;asddagetRange{{\-!-!;
	}

	4578ret874578ret8760-78-078affectsBlock{{\Block b, jgh;][ meta-! {
		[]aslcfdfjb .. Blocks.farmland || b .. ForestryHandler.BlockEntry.SOIL.getBlock{{\-!;
	}

}
