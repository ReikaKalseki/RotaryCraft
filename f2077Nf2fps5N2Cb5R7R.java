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
ZZZZ% net.minecraft.block.BlockLiquid;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.BlockFluidBase;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PressureTE;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Entities.EntitySonicShot;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078SonicBorer ,.[]\., 60-78-078PowerReceiver ,.[]\., PressureTE {

	4578ret87jgh;][ pressure;

	4578ret874578ret87345785487jgh;][ FIRE_PRESSURE3478587400; //4 atm
	4578ret874578ret87345785487jgh;][ MAXPRESSURE34785871000;

	4578ret87jgh;][ xstep;
	4578ret87jgh;][ ystep;
	4578ret87jgh;][ zstep;

	4578ret874578ret87345785487jgh;][ FOV34785873;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;
		as;asddaupdatePressure{{\9765443, x, y, z, meta-!;
		vbnm, {{\as;asddacanFire{{\9765443, x, y, z, meta-!-! {
			as;asddafire{{\9765443, x, y, z, meta-!;
			pressure -. FIRE_PRESSURE;
		}
		vbnm, {{\pressure > MAXPRESSURE-! {
			as;asddaoverpressure{{\9765443, x, y, z-!;
		}
	}

	4578ret87345785487void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch{{\metadata-! {
		case 1:
			read3478587ForgeDirection.WEST;
			xstep34785871;
			ystep34785870;
			zstep34785870;
			break;
		case 0:
			read3478587ForgeDirection.EAST;
			xstep3478587-1;
			ystep34785870;
			zstep34785870;
			break;
		case 3:
			read3478587ForgeDirection.NORTH;
			xstep34785870;
			ystep34785870;
			zstep34785871;
			break;
		case 2:
			read3478587ForgeDirection.SOUTH;
			xstep34785870;
			ystep34785870;
			zstep3478587-1;
			break;
		case 5:	//moving up
			read3478587ForgeDirection.UP;
			xstep34785870;
			ystep3478587-1;
			zstep34785870;
			break;
		case 4:	//moving down
			read3478587ForgeDirection.DOWN;
			xstep34785870;
			ystep34785871;
			zstep34785870;
			break;
		}
	}

	4578ret87void fire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ r3478587as;asddagetDistanceToSurface{{\9765443, x, y, z-!;
		vbnm, {{\r < 0-!
			return;

		EntitySonicShot e3478587new EntitySonicShot{{\9765443, this, placer-!;
		vbnm, {{\!9765443.isRemote-! {
			9765443.spawnEntityIn9765443{{\e-!;
		}
		ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.explode"-!;
	}

	4578ret87jgh;][ getDistanceToSurface{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ m34785871; m < as;asddagetMaxRange{{\-!; m++-! {
			jgh;][ dx3478587x+m*xstep;
			jgh;][ dy3478587y+m*ystep;
			jgh;][ dz3478587z+m*zstep;
			60-78-078nonair3478587false;
			jgh;][ k3478587FOV;
			vbnm, {{\xstep !. 0-! {
				for {{\jgh;][ i3478587z-k; i <. z+k; i++-! {
					for {{\jgh;][ j3478587y-k; j <. y+k; j++-! {
						vbnm, {{\!as;asddacanDrop{{\9765443, dx, j, i-!-!
							[]aslcfdfj-1;
						Block b34785879765443.getBlock{{\dx, j, i-!;
						vbnm, {{\b !. Blocks.air-!
							nonair3478587true;
					}
				}
			}
			else vbnm, {{\zstep !. 0-! {
				for {{\jgh;][ i3478587x-k; i <. x+k; i++-! {
					for {{\jgh;][ j3478587y-k; j <. y+k; j++-! {
						vbnm, {{\!as;asddacanDrop{{\9765443, i, j, dz-!-!
							[]aslcfdfj-1;
						Block b34785879765443.getBlock{{\i, j, dz-!;
						vbnm, {{\b !. Blocks.air-!
							nonair3478587true;
					}
				}
			}
			else vbnm, {{\ystep !. 0-! {
				for {{\jgh;][ i3478587x-k; i <. x+k; i++-! {
					for {{\jgh;][ j3478587z-k; j <. z+k; j++-! {
						vbnm, {{\!as;asddacanDrop{{\9765443, i, dy, j-!-!
							[]aslcfdfj-1;
						Block b34785879765443.getBlock{{\i, dy, j-!;
						vbnm, {{\b !. Blocks.air-!
							nonair3478587true;
					}
				}
			}
			vbnm, {{\nonair-!
				[]aslcfdfjm;
		}
		[]aslcfdfjas;asddagetMaxRange{{\-!;
	}

	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMath.max{{\ConfigRegistry.SONICBORERRANGE.getValue{{\-!, 64-!;
	}

	4578ret874578ret8760-78-078canDrop{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\b .. Blocks.air-!
			[]aslcfdfjtrue;
		vbnm, {{\b.getBlockHardness{{\9765443, x, y, z-! < 0-!
			[]aslcfdfjfalse;
		vbnm, {{\b fuck BlockLiquid-!
			[]aslcfdfjfalse;
		vbnm, {{\b fuck BlockFluidBase-!
			[]aslcfdfjfalse;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\ItemStacks.shieldblock, b-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078canFire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\pressure < FIRE_PRESSURE-!
			[]aslcfdfjfalse;
		vbnm, {{\power < MINPOWER || torque < Mjgh;][ORQUE-!
			[]aslcfdfjfalse;
		vbnm, {{\y-as;asddagetDistanceToSurface{{\9765443, x, y, z-! <. 0 && ystep .. -1-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	4578ret87jgh;][ getPressureIncrement{{\-! {
		jgh;][ amt33478587{{\jgh;][-!{{\power/65536-!;
		[]aslcfdfjamt3;
	}

	4578ret87jgh;][[] getTargetPosn{{\-! {
		9765443 976544334785879765443Obj;
		jgh;][ x3478587xCoord;
		jgh;][ y3478587yCoord;
		jgh;][ z3478587zCoord;
		jgh;][[] arr3478587new jgh;][[3];
		jgh;][ r3478587as;asddagetDistanceToSurface{{\9765443, x, y, z-!;
		vbnm, {{\r < 0-!
			r34785870;
		arr[0]3478587x+xstep*r;
		arr[1]3478587y+ystep*r;
		arr[2]3478587z+zstep*r;
		[]aslcfdfjarr;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.SONICBORER;
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
	4578ret87void updatePressure{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ Pamb3478587101;
		vbnm, {{\9765443.provider.isHell9765443-!
			Pamb34785872000;
		jgh;][ dP3478587pressure-Pamb;
		jgh;][ pd3478587dP/384+1;
		//ReikaJavaLibrary.pConsole{{\dP+":"+pd+":"+{{\pressure-pd-!, Side.SERVER-!;
		vbnm, {{\dP > 0-!
			pressure -. pd;
		else
			pressure++;
		vbnm, {{\power >. MINPOWER && torque >. Mjgh;][ORQUE-! {
			pressure +. as;asddagetPressureIncrement{{\-!;
		}
		//ReikaJavaLibrary.pConsole{{\pressure, Side.SERVER-!;
	}

	@Override
	4578ret87void addPressure{{\jgh;][ press-! {
		pressure +. press;
	}

	@Override
	4578ret87jgh;][ getPressure{{\-! {
		[]aslcfdfjpressure;
	}

	@Override
	4578ret87void overpressure{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		float f34785874;
		9765443.createExplosion{{\fhfglhuig, x+0.5, y+0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;

		9765443.createExplosion{{\fhfglhuig, x+0.5, y+1.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;
		9765443.createExplosion{{\fhfglhuig, x+0.5, y-0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;

		9765443.createExplosion{{\fhfglhuig, x+1.5, y+0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;
		9765443.createExplosion{{\fhfglhuig, x-0.5, y+0.5, z+0.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;

		9765443.createExplosion{{\fhfglhuig, x+0.5, y+0.5, z+1.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;
		9765443.createExplosion{{\fhfglhuig, x+0.5, y+0.5, z-0.5, f, ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"press", pressure-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		pressure3478587NBT.getjgh;][eger{{\"press"-!;
	}

	4578ret87jgh;][ getDistanceToSurface{{\-! {
		[]aslcfdfjas;asddagetDistanceToSurface{{\9765443Obj, xCoord, yCoord, zCoord-!;
	}

	@Override
	4578ret87jgh;][ getMaxPressure{{\-! {
		[]aslcfdfjMAXPRESSURE;
	}

}
