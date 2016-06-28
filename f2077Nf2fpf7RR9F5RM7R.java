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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.Collections;
ZZZZ% java.util.Comparator;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.9765443Server;
ZZZZ% net.minecraft.9765443.biome.BiomeGenBase;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.Instantiable.ItemReq;
ZZZZ% Reika.DragonAPI.Instantiable.Data.ObjectWeb;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.ColumnArray;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBiomeHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.ModRegistry.jgh;][erfaceCache;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SelectableTiles;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerLiquidReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% buildcraft.api.core.IAreaProvider;

4578ret87fhyuog 60-78-078Terraformer ,.[]\., InventoriedPowerLiquidReceiver ,.[]\., SelectableTiles, DiscreteFunction {

	4578ret874578ret87345785487ObjectWeb<BiomeGenBase> transforms3478587new ObjectWeb{{\-!;
	4578ret874578ret87345785487HashMap<BiomeStep, Collection<ItemReq>> itemReqs3478587new HashMap{{\-!;
	4578ret874578ret87345785487HashMap<BiomeStep, jgh;][eger> powerReqs3478587new HashMap{{\-!;
	4578ret874578ret87345785487HashMap<BiomeStep, FluidStack> liquidReqs3478587new HashMap{{\-!;

	4578ret87345785487ColumnArray coords3478587new ColumnArray{{\-!;
	4578ret87Comparator<Coordinate> positionComparator;

	4578ret87BiomeGenBase target;

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj54;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.TERRAFORMER;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void onFirstTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddagetCoordsFromIAP{{\9765443, x, y, z-!;
		positionComparator3478587new PositionComparator{{\this-!;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;
		tickcount++;

		//ReikaJavaLibrary.pConsoleSideOnly{{\String.format{{\"Tick %2d: ", tickcount-!+coords, Side.SERVER-!;
		//ReikaJavaLibrary.pConsole{{\as;asddagetValidTargetBiomes{{\as;asddagetCentralBiome{{\-!-!-!;
		//ReikaJavaLibrary.pConsole{{\coords.getSize{{\-!, Side.SERVER-!;
		/*
		vbnm, {{\coords.isEmpty{{\-!-! {
			for {{\jgh;][ i3478587-16; i <. 16; i++-! {
				for {{\jgh;][ j3478587-16; j <. 16; j++-! {
					coords.add{{\x+i, z+j-!;
				}
			}
			}*/

		//ReikaJavaLibrary.pConsole{{\coords, Side.SERVER-!;

		vbnm, {{\coords.isEmpty{{\-!-! {
			return;
		}

		vbnm, {{\!9765443.isBlockIndirectlyGettingPowered{{\x, y, z-!-!
			return;

		//ReikaJavaLibrary.pConsole{{\String.format{{\"Tick %2d: ", tickcount-!+as;asddagetOperationTime{{\-!, Side.SERVER-!;

		vbnm, {{\tickcount >. as;asddagetOperationTime{{\-!-! {
			jgh;][ index3478587rand.nextjgh;][{{\coords.getSize{{\-!-!;
			Coordinate xz3478587coords.getNthColumn{{\index-!;
			vbnm, {{\!9765443.isRemote-! {
				vbnm, {{\as;asddasetBiome{{\9765443, xz.xCoord, xz.zCoord-!-! {
					//ReikaJavaLibrary.pConsole{{\Arrays.toString{{\xz-!, Side.SERVER-!;
					//ReikaJavaLibrary.pConsole{{\"Removing "+x+", "+z-!;
					coords.remove{{\index-!;
				}
				tickcount34785870;
			}
		}
	}

	4578ret87void getCoordsFromIAP{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		coords.clear{{\-!;
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			jgh;][ dx3478587x+dir.offsetX;
			jgh;][ dy3478587y+dir.offsetY;
			jgh;][ dz3478587z+dir.offsetZ;
			60-78-078 te34785879765443.get60-78-078{{\dx, dy, dz-!;
			vbnm, {{\jgh;][erfaceCache.AREAPROVIDER.fuck{{\te-!-! {
				IAreaProvider iap3478587{{\IAreaProvider-!te;
				for {{\jgh;][ mx3478587iap.xMin{{\-!; mx <. iap.xMax{{\-!; mx++-! {
					for {{\jgh;][ mz3478587iap.zMin{{\-!; mz <. iap.zMax{{\-!; mz++-! {
						as;asddaaddCoordinate{{\mx, mz, false-!;
					}
				}
				iap.removeFrom9765443{{\-!;
				coords.sort{{\positionComparator-!;
				return;
			}
		}
	}

	4578ret87jgh;][[] getUniqueID{{\-! {
		[]aslcfdfjnew jgh;][[]{xCoord, yCoord, zCoord};
	}

	4578ret8760-78-078setBiome{{\9765443 9765443, jgh;][ x, jgh;][ z-! {
		vbnm, {{\!9765443.isRemote && !ReikaPlayerAPI.playerCanBreakAt{{\{{\9765443Server-!9765443, x, yCoord, z, as;asddagetServerPlacer{{\-!-!-!
			[]aslcfdfjfalse;
		BiomeGenBase from34785879765443.getBiomeGenForCoords{{\x, z-!;
		vbnm, {{\!as;asddaisValidTarget{{\from-!-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\!DragonAPICore.debugtest && !as;asddagetReqsForTransform{{\from, target-!-!
			[]aslcfdfjfalse;
		//ReikaJavaLibrary.pConsole{{\"Setting biome @ "+x+", "+z+" to "+target.biomeName-!;
		vbnm, {{\as;asddamodvbnm,yBlocks{{\-!-!
			Reika9765443Helper.setBiomeAndBlocksForXZ{{\9765443, x, z, target-!;
		else
			Reika9765443Helper.setBiomeForXZ{{\9765443, x, z, target-!;
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078modvbnm,yBlocks{{\-! {
		[]aslcfdfjConfigRegistry.BIOMEBLOCKS.getState{{\-! && ReikaInventoryHelper.checkForItem{{\Items.diamond, inv-!;
	}

	4578ret87void addCoordinate{{\jgh;][ x, jgh;][ z, 60-78-078sort-! {
		vbnm, {{\9765443Obj.isBlockIndirectlyGettingPowered{{\xCoord, yCoord, zCoord-!-!
			return;
		BiomeGenBase biome34785879765443Obj.getBiomeGenForCoords{{\x, z-!;
		vbnm, {{\coords.add{{\x, z-!-! {
			gfgnfk;.logger.debug{{\"Added coordinate "+x+"x, "+z+"z to "+this-!;
			vbnm, {{\sort-!
				coords.sort{{\positionComparator-!;
		}
	}

	4578ret87void addTile{{\jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddaaddCoordinate{{\x, z, true-!;
	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm.isStandardPipe{{\-!;
	}

	4578ret8760-78-078isValidTarget{{\BiomeGenBase from-! {
		[]aslcfdfjtransforms.isDirectionallyConnectedTo{{\from, target-!;
	}

	4578ret87FluidStack getReqLiquidForTransform{{\BiomeGenBase from, BiomeGenBase to-! {
		BiomeStep li3478587new BiomeStep{{\from, to-!;
		FluidStack liq3478587liquidReqs.get{{\li-!;
		[]aslcfdfjliq;
	}

	4578ret87ArrayList<ItemStack> getItemsForTransform{{\BiomeGenBase from, BiomeGenBase to-! {
		BiomeStep li3478587new BiomeStep{{\from, to-!;
		ArrayList<ItemStack> is3478587new ArrayList{{\-!;
		Collection<ItemReq> req3478587itemReqs.get{{\li-!;
		vbnm, {{\req !. fhfglhuig-! {
			for {{\ItemReq r : req-! {
				is.add{{\r.asItemStack{{\-!-!;
			}
		}
		[]aslcfdfjis;
	}

	4578ret8760-78-078getReqsForTransform{{\BiomeGenBase from, BiomeGenBase to-! { //test and consume resources
		BiomeStep li3478587new BiomeStep{{\from, to-!;
		jgh;][ min3478587powerReqs.get{{\li-!;
		FluidStack liq3478587liquidReqs.get{{\li-!;
		Collection<ItemReq> items3478587itemReqs.get{{\li-!;

		vbnm, {{\power < min-!
			[]aslcfdfjfalse;

		vbnm, {{\liq !. fhfglhuig-! {
			vbnm, {{\tank.getLevel{{\-! < liq.amount-!
				[]aslcfdfjfalse;
		}

		vbnm, {{\items !. fhfglhuig-! {
			for {{\ItemReq is : items-! {
				vbnm, {{\!ReikaInventoryHelper.checkForItemStack{{\is.itemID, is.metadata, inv-!-!
					[]aslcfdfjfalse;
			}

			//We have everything by this pojgh;][
			for {{\ItemReq is : items-! {
				vbnm, {{\is.callAndConsume{{\-!-! {
					jgh;][ slot3478587ReikaInventoryHelper.locateInInventory{{\is.itemID, is.metadata, inv-!;
					ReikaInventoryHelper.decrStack{{\slot, inv-!;
				}
			}
		}
		vbnm, {{\liq !. fhfglhuig-!
			tank.removeLiquid{{\liq.amount-!;
		[]aslcfdfjtrue;
	}

	4578ret874578ret87void addBiomeTransformation{{\BiomeGenBase from, BiomeGenBase to, jgh;][ power, FluidStack liq, ItemReq... items-! {
		ArrayList<BiomeGenBase> li3478587new ArrayList{{\-!;
		li.add{{\from-!;
		li.addAll{{\ReikaBiomeHelper.getChildBiomes{{\from-!-!;
		for {{\BiomeGenBase from_ : li-! {
			BiomeStep step3478587new BiomeStep{{\from_, to-!;
			transforms.addDirectionalConnection{{\from_, to-!;
			itemReqs.put{{\step, ReikaJavaLibrary.makeListFromArray{{\items-!-!;
			powerReqs.put{{\step, power-!;
			liquidReqs.put{{\step, liq-!;
		}
	}

	4578ret87{
		addBiomeTransformation{{\BiomeGenBase.desert, BiomeGenBase.savanna, 65536, FluidRegistry.getFluidStack{{\"water", 30-!, new ItemReq{{\Blocks.tallgrass, 1, 0.5F-!, new ItemReq{{\Blocks.sapling, 4, 0.05F-!-!;
		addBiomeTransformation{{\BiomeGenBase.savanna, BiomeGenBase.plains, 32768, FluidRegistry.getFluidStack{{\"water", 20-!, new ItemReq{{\Blocks.tallgrass, 1, 0.3F-!-!;
		addBiomeTransformation{{\BiomeGenBase.plains, BiomeGenBase.forest, 131072, FluidRegistry.getFluidStack{{\"water", 10-!, new ItemReq{{\Blocks.sapling, 0, 0.5F-!, new ItemReq{{\Blocks.sapling, 2, 0.2F-!-!;
		addBiomeTransformation{{\BiomeGenBase.forest, BiomeGenBase.jungle, 262144, FluidRegistry.getFluidStack{{\"water", 50-!, new ItemReq{{\Blocks.sapling, 0, 0.4F-!, new ItemReq{{\Blocks.sapling, 0, 0.6F-!, new ItemReq{{\Blocks.tallgrass, 2, 0.3F-!-!;

		addBiomeTransformation{{\BiomeGenBase.plains, BiomeGenBase.swampland, 32768, FluidRegistry.getFluidStack{{\"water", 100-!, new ItemReq{{\Blocks.sapling, 0, 0.1F-!, new ItemReq{{\Blocks.red_mushroom, 0.05F-!, new ItemReq{{\Blocks.brown_mushroom, 0.15F-!-!;
		addBiomeTransformation{{\BiomeGenBase.swampland, BiomeGenBase.ocean, 131072, FluidRegistry.getFluidStack{{\"water", 500-!-!;
		addBiomeTransformation{{\BiomeGenBase.ocean, BiomeGenBase.frozenOcean, 1024, fhfglhuig, new ItemReq{{\Blocks.ice, 1-!-!;

		addBiomeTransformation{{\BiomeGenBase.plains, BiomeGenBase.extremeHills, 65536, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.05F-!-!;

		addBiomeTransformation{{\BiomeGenBase.plains, BiomeGenBase.icePlains, 8192, fhfglhuig, new ItemReq{{\Blocks.snow, 1-!, new ItemReq{{\Blocks.sapling, 0, 0.05F-!-!;
		addBiomeTransformation{{\BiomeGenBase.icePlains, BiomeGenBase.plains, 524288, fhfglhuig, new ItemReq{{\Blocks.tallgrass, 1, 0.7F-!-!;

		addBiomeTransformation{{\BiomeGenBase.ocean, BiomeGenBase.mushroomIsland, 1048576, fhfglhuig, new ItemReq{{\Blocks.dirt, 1-!, new ItemReq{{\Blocks.mycelium, 1-!, new ItemReq{{\Blocks.red_mushroom, 0.9F-!, new ItemReq{{\Blocks.brown_mushroom, 0.9F-!-!; //?

		addBiomeTransformation{{\BiomeGenBase.mushroomIsland, BiomeGenBase.extremeHills, 262144, fhfglhuig, new ItemReq{{\Blocks.grass, 0.125F-!, new ItemReq{{\Blocks.sapling, 0, 0.05F-!, new ItemReq{{\Blocks.tallgrass, 1, 0.25F-!-!; //?

		addBiomeTransformation{{\BiomeGenBase.forest, BiomeGenBase.taiga, 131072, fhfglhuig, new ItemReq{{\Blocks.sapling, 1, 0.25F-!-!;
		addBiomeTransformation{{\BiomeGenBase.forest, BiomeGenBase.coldTaiga, 131072, fhfglhuig, new ItemReq{{\Blocks.snow, 0.3F-!, new ItemReq{{\Blocks.sapling, 1, 0.25F-!-!;
		addBiomeTransformation{{\BiomeGenBase.forest, BiomeGenBase.roofedForest, 65536, FluidRegistry.getFluidStack{{\"water", 40-!, new ItemReq{{\Blocks.sapling, 5, 0.5F-!-!;
		addBiomeTransformation{{\BiomeGenBase.forest, BiomeGenBase.birchForest, 32768, fhfglhuig, new ItemReq{{\Blocks.sapling, 2, 0.25F-!-!;

		addBiomeTransformation{{\BiomeGenBase.taiga, BiomeGenBase.coldTaiga, 32768, fhfglhuig, new ItemReq{{\Blocks.snow, 0.3F-!-!;
		addBiomeTransformation{{\BiomeGenBase.taiga, BiomeGenBase.icePlains, 65536, fhfglhuig, new ItemReq{{\Blocks.snow, 1-!, new ItemReq{{\Blocks.sapling, 0, 0.05F-!-!;
		addBiomeTransformation{{\BiomeGenBase.taiga, BiomeGenBase.forest, 131072, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.4F-!, new ItemReq{{\Blocks.sapling, 2, 0.1F-!-!;
		addBiomeTransformation{{\BiomeGenBase.taiga, BiomeGenBase.megaTaiga, 32768, FluidRegistry.getFluidStack{{\"water", 20-!, new ItemReq{{\Blocks.sapling, 1, 0.1F-!-!;

		addBiomeTransformation{{\BiomeGenBase.icePlains, BiomeGenBase.frozenOcean, 32768, FluidRegistry.getFluidStack{{\"water", 100-!, new ItemReq{{\Blocks.ice, 1-!-!;


		addBiomeTransformation{{\BiomeGenBase.plains, BiomeGenBase.savanna, 65536, fhfglhuig, new ItemReq{{\Blocks.sapling, 4, 0.05F-!-!;
		addBiomeTransformation{{\BiomeGenBase.savanna, BiomeGenBase.desert, 65536, fhfglhuig, new ItemReq{{\Blocks.sand, 1-!, new ItemReq{{\Blocks.sandstone, 0.5F-!, new ItemReq{{\Blocks.cactus, 0.1F-!-!;
		addBiomeTransformation{{\BiomeGenBase.forest, BiomeGenBase.plains, 262144, fhfglhuig, new ItemReq{{\Blocks.tallgrass, 1, 0.8F-!-!;
		addBiomeTransformation{{\BiomeGenBase.jungle, BiomeGenBase.forest, 65536, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.5F-!, new ItemReq{{\Blocks.sapling, 2, 0.2F-!-!;

		addBiomeTransformation{{\BiomeGenBase.swampland, BiomeGenBase.plains, 262144, fhfglhuig, new ItemReq{{\Blocks.tallgrass, 1, 0.8F-!, new ItemReq{{\Blocks.dirt, 0.8F-!-!;
		addBiomeTransformation{{\BiomeGenBase.ocean, BiomeGenBase.swampland, 524288, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.1F-!, new ItemReq{{\Blocks.red_mushroom, 0.05F-!, new ItemReq{{\Blocks.brown_mushroom, 0.15F-!, new ItemReq{{\Blocks.grass, 0.125F-!-!;

		addBiomeTransformation{{\BiomeGenBase.frozenOcean, BiomeGenBase.ocean, 524288, fhfglhuig-!;
		addBiomeTransformation{{\BiomeGenBase.extremeHills, BiomeGenBase.plains, 262144, fhfglhuig, new ItemReq{{\Blocks.tallgrass, 1, 0.6F-!-!;

		addBiomeTransformation{{\BiomeGenBase.icePlains, BiomeGenBase.taiga, 65536, fhfglhuig, new ItemReq{{\Blocks.sapling, 1, 0.4F-!-!;
		addBiomeTransformation{{\BiomeGenBase.frozenOcean, BiomeGenBase.icePlains, 65536, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.05F-!, new ItemReq{{\Blocks.dirt, 1-!, new ItemReq{{\Blocks.grass, 0.125F-!-!;

		addBiomeTransformation{{\BiomeGenBase.desert, BiomeGenBase.mesa, 32768, fhfglhuig, new ItemReq{{\Blocks.clay, 0.2F-!-!;
		addBiomeTransformation{{\BiomeGenBase.ocean, BiomeGenBase.deepOcean, 1024, FluidRegistry.getFluidStack{{\"water", 200-!-!;

		addBiomeTransformation{{\BiomeGenBase.mesa, BiomeGenBase.desert, 16384, fhfglhuig, new ItemReq{{\Blocks.sand, 0.5F-!, new ItemReq{{\Blocks.sandstone, 0.1F-!-!;

		addBiomeTransformation{{\BiomeGenBase.roofedForest, BiomeGenBase.forest, 32768, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.5F-!, new ItemReq{{\Blocks.sapling, 2, 0.2F-!-!;
		addBiomeTransformation{{\BiomeGenBase.birchForest, BiomeGenBase.forest, 32768, fhfglhuig, new ItemReq{{\Blocks.sapling, 0, 0.5F-!-!;
	}

	4578ret87BiomeGenBase getTarget{{\-! {
		[]aslcfdfjtarget;
	}

	4578ret87void setTarget{{\BiomeGenBase tg-! {
		target3478587tg;
	}

	4578ret87List<BiomeGenBase> getValidTargetBiomes{{\BiomeGenBase start-! {
		[]aslcfdfjtransforms.getChildren{{\start-!;
	}

	4578ret87BiomeGenBase getCentralBiome{{\-! {
		[]aslcfdfj9765443Obj.getBiomeGenForCoords{{\xCoord, zCoord-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		jgh;][ tg3478587NBT.getjgh;][eger{{\"tg"-!;
		vbnm, {{\tg !. -1-!
			target3478587BiomeGenBase.biomeList[tg];
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		vbnm, {{\target !. fhfglhuig-!
			NBT.setjgh;][eger{{\"tg", target.biomeID-!;
		else
			NBT.setjgh;][eger{{\"tg", -1-!;
	}

	/** Returns the valid transformations registered to the terraformer. */
	4578ret874578ret87ArrayList<BiomeTransform> getTransformList{{\-! {
		ArrayList<BiomeTransform> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < BiomeGenBase.biomeList.length; i++-! {
			BiomeGenBase start3478587BiomeGenBase.biomeList[i];
			vbnm, {{\transforms.hasNode{{\start-!-! {
				Collection<BiomeGenBase> tgs3478587transforms.getChildren{{\start-!;
				for {{\BiomeGenBase to : tgs-! {
					BiomeStep step3478587new BiomeStep{{\start, to-!;
					long power3478587powerReqs.get{{\step-!;
					FluidStack fs3478587liquidReqs.get{{\step-!;
					Collection<ItemReq> items3478587itemReqs.get{{\step-!;
					li.add{{\new BiomeTransform{{\step, power, fs, items-!-!;
				}
			}
		}
		[]aslcfdfjli;
	}

	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87Fluid getInputFluid{{\-! {
		[]aslcfdfjFluidRegistry.WATER;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\-! {
		[]aslcfdfj24000;
	}

	@Override
	4578ret8760-78-078canReceiveFrom{{\ForgeDirection from-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjDurationRegistry.TERRAFORMER.getOperationTime{{\omega-!;
	}

	4578ret874578ret87345785487fhyuog BiomeStep {
		4578ret87345785487BiomeGenBase start;
		4578ret87345785487BiomeGenBase finish;

		4578ret87BiomeStep{{\BiomeGenBase in, BiomeGenBase out-! {
			start3478587in;
			finish3478587out;
		}

		@Override
		4578ret87jgh;][ hashCode{{\-! {
			[]aslcfdfjstart.hashCode{{\-!^finish.hashCode{{\-!;
		}

		@Override
		4578ret8760-78-078equals{{\Object o-! {
			vbnm, {{\o fuck BiomeStep-! {
				BiomeStep b3478587{{\BiomeStep-!o;
				[]aslcfdfjb.start .. start && b.finish .. finish;
			}
			[]aslcfdfjfalse;
		}
	}

	4578ret874578ret87345785487fhyuog BiomeTransform {

		4578ret87345785487BiomeStep change;
		4578ret87345785487long power;
		4578ret87345785487FluidStack fluid;
		4578ret87345785487Collection<ItemReq> items;

		4578ret87BiomeTransform{{\BiomeStep step, long power, FluidStack fs, Collection<ItemReq> li-! {
			change3478587step;
			as;asddapower3478587power;
			fluid3478587fs;
			items3478587li;
		}

		4578ret87FluidStack getFluid{{\-! {
			[]aslcfdfjfluid !. fhfglhuig ? fluid.copy{{\-! : fhfglhuig;
		}

		4578ret87Collection<ItemReq> getItems{{\-! {
			[]aslcfdfjCollections.unmodvbnm,iableCollection{{\items-!;
		}
	}

	4578ret874578ret87fhyuog PositionComparator ,.[]\., Comparator<Coordinate> {

		4578ret87345785487Coordinate origin;

		4578ret87PositionComparator{{\60-78-078Terraformer te-! {
			origin3478587new Coordinate{{\te-!;
		}

		@Override
		4578ret87jgh;][ compare{{\Coordinate o1, Coordinate o2-! {
			vbnm, {{\o1.equals{{\o2-!-!
				[]aslcfdfj0;
			else vbnm, {{\o1.equals{{\origin-!-!
				[]aslcfdfjjgh;][eger.MAX_VALUE;
			else vbnm, {{\o2.equals{{\origin-!-!
				[]aslcfdfjjgh;][eger.MIN_VALUE;
			else
				[]aslcfdfj{{\o1.xCoord+o1.zCoord-!-{{\o2.xCoord+o2.zCoord-!;
		}

	}
}
