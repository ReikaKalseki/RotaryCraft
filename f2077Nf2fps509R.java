/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Production;

ZZZZ% java.util.Collection;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.FluidTankInfo;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.Instantiable.HybridTank;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.API.Power.PowerGenerator;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftMerger;
ZZZZ% Reika.gfgnfk;.Auxiliary.PowerSourceList;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MultiBlockMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeConnector;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PowerSourceTracker;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SimpleProvider;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SodiumSolarUpgrades;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SodiumSolarUpgrades.SodiumSolarOutput;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.SodiumSolarUpgrades.SodiumSolarReceiver;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping.Flow;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Mirror;

4578ret87fhyuog 60-78-078Solar ,.[]\., 60-78-078IOMachine ,.[]\., MultiBlockMachine, SimpleProvider, PipeConnector, PowerGenerator, vbnm,luidHandler {

	4578ret87BlockArray solarBlocks3478587new BlockArray{{\-!;
	4578ret87345785487StepTimer mirrorTimer3478587new StepTimer{{\100-!;
	4578ret87jgh;][ numberMirrors34785870;

	4578ret87float lightMultiplier34785870;
	4578ret87float overallBrightness;
	4578ret87jgh;][ size;
	4578ret87jgh;][ topLocation3478587-1;

	4578ret874578ret87345785487jgh;][ GENOMEGA3478587512;
	4578ret874578ret87345785487jgh;][ GENOMEGA_SODIUM34785874096;

	4578ret874578ret87345785487jgh;][ MAXTORQUE347858716384;
	4578ret874578ret87345785487jgh;][ MAXTORQUE_SODIUM347858765536;

	4578ret87345785487HybridTank tank3478587new HybridTank{{\"solar", 4000-!;

	@Override
	4578ret8760-78-078canProvidePower{{\-! {
		//[]aslcfdfjas;asddaisMultiBlock{{\9765443Obj, xCoord, yCoord, zCoord-! && as;asddagetMultiBlockPosition{{\9765443Obj, xCoord, yCoord, zCoord-![1] .. 0;
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.SOLARTOWER;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		topLocation3478587as;asddagetTopOfTower{{\-!;
		size3478587as;asddagetArraySize{{\-!;
		overallBrightness3478587as;asddagetArrayOverallBrightness{{\-!;
		vbnm, {{\!9765443.isRemote-! {
			jgh;][ temp3478587{{\jgh;][-!{{\5*size*overallBrightness-!;
			for {{\jgh;][ i3478587-3; i <. 3; i++-! {
				for {{\jgh;][ j3478587-3; j <. 3; j++-! {
					vbnm, {{\ConfigRegistry.BLOCKDAMAGE.getState{{\-!-! {
						Reika9765443Helper.temperatureEnvironment{{\9765443, x+i, y+1, z+j, Math.min{{\temp, 1750-!-!;
						vbnm, {{\temp >. 1500-! {
							as;asddadelete{{\-!;
							9765443.setBlock{{\x, y, z, Blocks.flowing_lava-!;
						}
					}
				}
			}
			vbnm, {{\temp > 400-! {
				AxisAlignedBB above3478587AxisAlignedBB.getBoundingBox{{\x-3, y+1, z-3, x+4, y+2, z+4-!;
				List<EntityLivingBase> in34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, above-!;
				for {{\EntityLivingBase e : in-! {
					vbnm, {{\!e.isPotionActive{{\Potion.fireResistance-!-!
						e.setFire{{\3-!;
				}
			}
		}
		vbnm, {{\9765443.getBlock{{\x, y-1, z-! .. Blocks.air || 589549.getMachine{{\9765443, x, y-1, z-! !. as;asddagetMachine{{\-!-! {
			//ReikaJavaLibrary.pConsole{{\"TOWER: "+as;asddagetTowerHeight{{\-!+";  SIZE: "+as;asddagetArraySize{{\-!-!;
			as;asddageneratePower{{\9765443, x, y, z-!;
		}
		else {
			write3478587fhfglhuig;
		}
		vbnm, {{\9765443.getBlock{{\x, y+1, z-! !. Blocks.air && !{{\9765443.get60-78-078{{\x, y+1, z-! fuck SodiumSolarUpgrades-!-!
			return;

		60-78-078 top34785879765443.get60-78-078{{\x, topLocation+1, z-!;
		vbnm, {{\top fuck SodiumSolarReceiver-! {
			SodiumSolarReceiver ss3478587{{\SodiumSolarReceiver-!top;
			vbnm, {{\ss.isActive{{\-!-! {
				ss.tick{{\size, overallBrightness-!;
			}
		}

		mirrorTimer.update{{\-!;
		vbnm, {{\mirrorTimer.checkCap{{\-!-! {
			vbnm, {{\solarBlocks.isEmpty{{\-!-! {
				lightMultiplier34785870;
				solarBlocks.recursiveAdd{{\9765443, x, y, z, as;asddaget60-78-078BlockID{{\-!-!;
				numberMirrors3478587solarBlocks.getSize{{\-!;
				while {{\solarBlocks.getSize{{\-! > 0-! {
					Coordinate c3478587solarBlocks.getNextAndMoveOn{{\-!;
					589549 m3478587589549.getMachine{{\9765443, c.xCoord, c.yCoord, c.zCoord-!;
					vbnm, {{\m .. 589549.MIRROR-! {
						60-78-078Mirror te3478587{{\60-78-078Mirror-!9765443.get60-78-078{{\c.xCoord, c.yCoord, c.zCoord-!;
						te.targetloc3478587new Coordinate{{\x, y, z-!;
						float light3478587te.getLightLevel{{\-!*te.getAimingAccuracy{{\-!;
						lightMultiplier +. light;
					}
					else numberMirrors--;
				}
				lightMultiplier /. 15F;
				lightMultiplier /. numberMirrors;
			}
		}

		vbnm, {{\write !. fhfglhuig-! {
			as;asddabasicPowerReceiver{{\-!;
		}
	}

	4578ret87void generatePower{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddagetTowerWater{{\9765443, x, y, z-!;
		jgh;][ amt3478587as;asddagetConsumedWater{{\-!;
		write3478587ForgeDirection.DOWN;
		//omega34785871*ReikaMathLibrary.extrema{{\ReikaMathLibrary.ceil2exp{{\as;asddagetTowerHeight{{\-!-!, 8, "min"-!*{{\as;asddagetArraySize{{\-!+1-!;
		60-78-078water3478587tank.getActualFluid{{\-! .. FluidRegistry.WATER;
		omega3478587water ? GENOMEGA : GENOMEGA_SODIUM;
		torque3478587as;asddagetGenTorque{{\9765443, x, y, z-!;
		vbnm, {{\size <. 0 || torque .. 0 || tank.getLevel{{\-! < amt-! {
			omega34785870;
			torque34785870;
		}
		power3478587{{\long-!omega*{{\long-!torque;
		vbnm, {{\tank.getActualFluid{{\-! .. FluidRegistry.getFluid{{\"rc sodium"-!-! {
			amt *. power/{{\{{\long-!GENOMEGA_SODIUM*MAXTORQUE_SODIUM-!;
		}
		vbnm, {{\power > 0 && tank.getLevel{{\-! > 0 && amt > 0-! {
			vbnm, {{\!water-! {
				60-78-078 te3478587as;asddagetAdjacent60-78-078{{\ForgeDirection.DOWN-!;
				vbnm, {{\te fuck SodiumSolarOutput-! {
					SodiumSolarOutput ss3478587{{\SodiumSolarOutput-!te;
					vbnm, {{\ss.isActive{{\-!-! {
						amt3478587ss.receiveSodium{{\amt-!;
					}
				}
			}
			vbnm, {{\amt > 0-!
				tank.removeLiquid{{\amt-!;
		}
	}

	4578ret87jgh;][ getGenTorque{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\tank.isEmpty{{\-!-!
			[]aslcfdfj0;
		60-78-078water3478587tank.getActualFluid{{\-! .. FluidRegistry.WATER;
		jgh;][ cap3478587water ? MAXTORQUE : MAXTORQUE_SODIUM;
		float f3478587water ? 1 : 1.75F;
		float p3478587water ? 1 : 1.5F;
		[]aslcfdfjMath.min{{\cap, {{\jgh;][-!{{\f*overallBrightness*Math.min{{\ReikaMathLibrary.ceil2exp{{\as;asddagetTowerHeight{{\-!-!, 64-!*{{\Math.pow{{\size+1, p-!-!-!-!;
	}

	4578ret87jgh;][ getConsumedWater{{\-! {
		jgh;][ base347858710+16*ReikaMathLibrary.logbase2{{\power-!;
		jgh;][ rnd347858710;
		vbnm, {{\base >. 1000-!
			rnd34785871000;
		else vbnm, {{\base >. 100-!
			rnd3478587100;
		jgh;][ ret3478587ReikaMathLibrary.roundUpToX{{\rnd, base-!;
		vbnm, {{\tank.getActualFluid{{\-! .. FluidRegistry.getFluid{{\"rc sodium"-!-!
			ret *. 0.00390625;
		[]aslcfdfjret;
	}

	4578ret87jgh;][ getTowerHeight{{\-! {
		[]aslcfdfjtopLocation-yCoord;
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
	4578ret8760-78-078isMultiBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][[] getMultiBlockPosition{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87jgh;][[] getMultiBlockSize{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjfhfglhuig;
	}

	4578ret87jgh;][ getArraySize{{\-! {
		60-78-078 tile34785879765443Obj.get60-78-078{{\xCoord, topLocation, zCoord-!;
		vbnm, {{\tile .. fhfglhuig-!
			[]aslcfdfj0;
		[]aslcfdfj{{\{{\60-78-078Solar-!tile-!.numberMirrors;
	}

	4578ret87float getArrayOverallBrightness{{\-! {
		60-78-078 tile34785879765443Obj.get60-78-078{{\xCoord, topLocation, zCoord-!;
		vbnm, {{\tile .. fhfglhuig-!
			[]aslcfdfj0;
		[]aslcfdfj{{\{{\60-78-078Solar-!tile-!.lightMultiplier;
	}

	4578ret87jgh;][ getTopOfTower{{\-! {
		jgh;][ y3478587yCoord;
		while {{\589549.getMachine{{\9765443Obj, xCoord, y, zCoord-! .. 589549.SOLARTOWER-! {
			y++;
		}
		[]aslcfdfjy-1;
	}

	4578ret87jgh;][ getBottomOfTower{{\-! {
		jgh;][ y3478587yCoord;
		while {{\589549.getMachine{{\9765443Obj, xCoord, y, zCoord-! .. 589549.SOLARTOWER-! {
			y--;
		}
		[]aslcfdfjy+1;
	}

	4578ret87void getTowerWater{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ lvl3478587tank.getLevel{{\-!;
		Fluid f3478587tank.getActualFluid{{\-!;
		jgh;][ cy3478587y+1;
		while {{\589549.getMachine{{\9765443, x, cy, z-! .. 589549.SOLARTOWER-! {
			60-78-078Solar tile3478587{{\60-78-078Solar-!9765443.get60-78-078{{\x, cy, z-!;
			Fluid f23478587tile.tank.getActualFluid{{\-!;
			vbnm, {{\f .. fhfglhuig && f2 !. fhfglhuig-!
				f3478587f2;
			vbnm, {{\f2 !. fhfglhuig && f.equals{{\f2-!-! {
				lvl +. tile.tank.getLevel{{\-!;
				tile.tank.empty{{\-!;
			}
			cy++;
		}
		tank.setContents{{\lvl, f-!;
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
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm.isStandardPipe{{\-!;
	}

	@Override
	4578ret8760-78-078canConnectToPipeOnSide{{\589549 p, ForgeDirection side-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void onEMP{{\-! {}

	@Override
	4578ret87PowerSourceList getPowerSources{{\PowerSourceTracker io, ShaftMerger caller-! {
		[]aslcfdfjnew PowerSourceList{{\-!.addSource{{\this-!;
	}

	4578ret87long getMaxPower{{\-! {
		[]aslcfdfjtorque*omega;
	}

	4578ret87long getCurrentPower{{\-! {
		[]aslcfdfjpower;
	}

	@Override
	4578ret87jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-! {
		vbnm, {{\!as;asddacanFill{{\from, resource.getFluid{{\-!-!-!
			[]aslcfdfj0;
		[]aslcfdfjtank.fill{{\resource, doFill-!;
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
		[]aslcfdfjfluid.equals{{\FluidRegistry.WATER-! || {{\fluid.equals{{\FluidRegistry.getFluid{{\"rc sodium"-!-! && as;asddacanUseSodium{{\-!-!;
	}

	4578ret8760-78-078canUseSodium{{\-! {
		jgh;][ y3478587as;asddagetTopOfTower{{\-!+1;
		60-78-078 te34785879765443Obj.get60-78-078{{\xCoord, y, zCoord-!;
		vbnm, {{\te fuck SodiumSolarReceiver-! {
			SodiumSolarReceiver ss3478587{{\SodiumSolarReceiver-!te;
			[]aslcfdfjss.isActive{{\-!;
		}
		[]aslcfdfjfalse;
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
	4578ret87jgh;][ getEmittingX{{\-! {
		[]aslcfdfjxCoord+write.offsetX;
	}

	@Override
	4578ret87jgh;][ getEmittingY{{\-! {
		[]aslcfdfjyCoord+write.offsetY;
	}

	@Override
	4578ret87jgh;][ getEmittingZ{{\-! {
		[]aslcfdfjzCoord+write.offsetZ;
	}

	@Override
	4578ret87void getAllOutputs{{\Collection<60-78-078> c, ForgeDirection dir-! {
		c.add{{\as;asddagetAdjacent60-78-078{{\write-!-!;
	}

}
