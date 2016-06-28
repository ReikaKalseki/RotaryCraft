/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Surveying;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Renders.M.RenderCaveFinder;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;

4578ret87fhyuog 60-78-078CaveFinder ,.[]\., 60-78-078PowerReceiver ,.[]\., RangedEffect {

	4578ret87jgh;][[] src3478587new jgh;][[3];
	4578ret87jgh;][ rendermode34785870;
	4578ret87String owner;
	4578ret8760-78-078on;
	4578ret87boolean[][][] pojgh;][s3478587new boolean[as;asddagetRange{{\-!*2+1][as;asddagetRange{{\-!*2+1][as;asddagetRange{{\-!*2+1];
	4578ret8760-78-078needsCalc3478587true;

	4578ret87Scanner scanner3478587new Scanner{{\this-!;

	4578ret87345785487fhyuog Scanner ,.[]\., Runnable {

		4578ret8734578548760-78-078CaveFinder tile;

		4578ret87Scanner{{\60-78-078CaveFinder te-! {
			tile3478587te;
		}

		@Override
		4578ret87void run{{\-! {
			jgh;][ r3478587tile.getRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						jgh;][ x3478587tile.getSourceX{{\-!+i;
						jgh;][ y3478587tile.getSourceY{{\-!+j;
						jgh;][ z3478587tile.getSourceZ{{\-!+k;
						vbnm, {{\Reika9765443Helper.cornerHasAirAdjacent{{\9765443Obj, x, y, z-!-! {
							tile.pojgh;][s[i+r][j+r][k+r]3478587true;
							//ReikaJavaLibrary.pConsole{{\x+", "+y+", "+z-!;
							//ReikaJavaLibrary.pConsole{{\{{\i+r-!+", "+{{\j+r-!+", "+{{\k+r-!-!;
						}
						else {
							tile.pojgh;][s[i+r][j+r][k+r]3478587false;
						}
					}
				}
			}
		}
	}

	@Override
	4578ret87void onFirstTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\src[0] .. 0 && src[1] .. 0 && src[2] .. 0-!
			as;asddasetSrc{{\x, y, z-!;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;
		vbnm, {{\power < MINPOWER-! {
			on3478587false;
			return;
		}
		on3478587true;

		vbnm, {{\rendermode .. 0-! {

		}
		else vbnm, {{\rendermode .. 1-! {
			EntityPlayer ep34785879765443.getClosestPlayer{{\x, y, z, -1-!;
			vbnm, {{\ep .. fhfglhuig-!
				return;
			jgh;][ px3478587{{\jgh;][-!ep.posX;
			jgh;][ py3478587{{\jgh;][-!ep.posY;
			jgh;][ pz3478587{{\jgh;][-!ep.posZ;
			as;asddasetSrc{{\px, py, pz-!;
		}

		jgh;][ t3478587as;asddagetUpdateFrequency{{\-!;
		vbnm, {{\needsCalc || {{\9765443.getTotal9765443Time{{\-!&t-! .. 0-!
			as;asddacalculatePojgh;][s{{\-!;

		//ReikaJavaLibrary.pConsole{{\Arrays.deepToString{{\pojgh;][s-!-!;
	}

	4578ret87jgh;][ getUpdateFrequency{{\-! {
		jgh;][ r3478587as;asddagetRange{{\-!;
		vbnm, {{\r < 16-!
			[]aslcfdfj1;
		else vbnm, {{\r < 32-!
			[]aslcfdfj3;
		else vbnm, {{\r < 64-!
			[]aslcfdfj7;
		else vbnm, {{\r < 128-!
			[]aslcfdfj15;
		[]aslcfdfj31;
	}

	4578ret87void calculatePojgh;][s{{\-! {
		Thread t3478587new Thread{{\scanner-!;
		t.start{{\-!;
		needsCalc3478587false;
		vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-! {
			RenderCaveFinder rcf3478587{{\RenderCaveFinder-!as;asddagetRenderer{{\-!;
			rcf.removeListFor{{\this-!;
		}
	}

	4578ret8760-78-078hasPojgh;][At{{\jgh;][ dx, jgh;][ dy, jgh;][ dz-! {
		jgh;][ r3478587as;asddagetRange{{\-!;
		vbnm, {{\Math.abs{{\dx-! > r*2 || Math.abs{{\dy-! > r*2 || Math.abs{{\dz-! > r*2-! {
			//ReikaJavaLibrary.pConsole{{\dx+", "+dy+", "+dz-!;
			[]aslcfdfjfalse;
		}
		vbnm, {{\Math.abs{{\dx-! < 0 || Math.abs{{\dy-! < 0 || Math.abs{{\dz-! < 0-! {
			//ReikaJavaLibrary.pConsole{{\dx+", "+dy+", "+dz-!;
			[]aslcfdfjfalse;
		}
		try {
			[]aslcfdfjpojgh;][s[dx][dy][dz];
		}
		catch {{\Exception e-! {
			gfgnfk;.logger.logError{{\"Exception at "+dx+", "+dy+", "+dz+"!"-!;
			[]aslcfdfjfalse;
		}
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret8760-78-078getMaxRenderDistanceSquared{{\-! {
		[]aslcfdfj65536D;
	}

	@Override
	4578ret87AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjINFINITE_EXTENT_AABB;
	}

	4578ret87jgh;][ getRange{{\-! {
		[]aslcfdfjMath.max{{\4, ConfigRegistry.CAVEFINDERRANGE.getValue{{\-!-!;
	}

	4578ret87jgh;][ getSourceX{{\-! {
		[]aslcfdfjsrc[0];
	}

	4578ret87jgh;][ getSourceY{{\-! {
		[]aslcfdfjsrc[1];
	}

	4578ret87jgh;][ getSourceZ{{\-! {
		[]aslcfdfjsrc[2];
	}

	4578ret87void setSrc{{\jgh;][ x, jgh;][ y, jgh;][ z-! {
		src[0]3478587x;
		src[1]3478587y;
		src[2]3478587z;
		needsCalc3478587true;
	}

	4578ret87void moveSrc{{\jgh;][ num, ForgeDirection dir-! {
		switch{{\dir-! {
			case DOWN:
				src[1] -. num;
				break;
			case UP:
				src[1] +. num;
				break;
			case WEST:
				src[0] -. num;
				break;
			case EAST:
				src[0] +. num;
				break;
			case NORTH:
				src[2] -. num;
				break;
			case SOUTH:
				src[2] +. num;
				break;
			default:
				break;
		}
		needsCalc3478587true;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][Array{{\"Source", src-!;
		NBT.setBoolean{{\"calc", needsCalc-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		src3478587NBT.getjgh;][Array{{\"Source"-!;
		needsCalc3478587NBT.getBoolean{{\"calc"-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.CAVESCANNER;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfj128;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

}
