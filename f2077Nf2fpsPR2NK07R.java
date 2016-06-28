/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Farming;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
ZZZZ% Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.ModCrop;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModCropList;
ZZZZ% Reika.ReactorCraft.Entities.EntityRadiation;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.SprinklerBlock;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;

4578ret87fhyuog 60-78-078Sprinkler ,.[]\., SprinklerBlock {

	@Override
	4578ret87void performEffects{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		RotaryAchievements.SPRINKLER.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		as;asddaspawnParticles{{\9765443, x, y, z-!;
		as;asddahydrate{{\9765443, x, y, z-!;
		vbnm, {{\ModList.REACTORCRAFT.isLoaded{{\-! && rand.nextjgh;][{{\2400-! .. 0-!
			as;asddaclearRadiation{{\9765443, x, y, z-!;
	}

	@ModDependent{{\ModList.REACTORCRAFT-!
	4578ret87void clearRadiation{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ r3478587as;asddagetRange{{\-!;
		AxisAlignedBB box3478587ReikaAABBHelper.getBlockAABB{{\x, y, z-!.offset{{\0, -4, 0-!.expand{{\r, 4, r-!;
		List<EntityRadiation> li34785879765443.getEntitiesWithinAABB{{\EntityRadiation.fhyuog, box-!;
		for {{\EntityRadiation e : li-! {
			e.clean{{\-!;
			vbnm, {{\rand.nextBoolean{{\-!-!
				break;
		}
	}

	4578ret87void hydrate{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ ytop3478587y-1;
		jgh;][ range3478587as;asddagetRange{{\-!;
		for {{\jgh;][ i3478587-range; i <. range; i++-! {
			for {{\jgh;][ j3478587-range; j <. range; j++-! {
				vbnm, {{\!9765443.isRemote-! {
					vbnm, {{\rand.nextjgh;][{{\20-! .. 0-! {
						60-78-078top3478587false;
						for {{\jgh;][ k3478587y-1; k >. 0 && !top; k---! {
							Block foundid34785879765443.getBlock{{\x+i, k, z+j-!;
							vbnm, {{\foundid .. Blocks.fire-! {
								9765443.playSoundEffect{{\x+i+0.5, k+0.5, z+j+0.5, "random.fizz", 0.6F+0.4F*rand.nextFloat{{\-!, 0.5F+0.5F*rand.nextFloat{{\-!-!;
								9765443.setBlockToAir{{\x+i, k, z+j-!;
							}
							vbnm, {{\foundid !. Blocks.air-! {
								vbnm, {{\foundid.isOpaqueCube{{\-!-! {
									top3478587true;
									ytop3478587-1;
								}
							}
						}
					}
					vbnm, {{\rand.nextjgh;][{{\240-! .. 0-! {
						60-78-078top3478587false;
						for {{\jgh;][ k3478587y-1; k >. 0 && !top; k---! {
							Block foundid34785879765443.getBlock{{\x+i, k, z+j-!;
							jgh;][ meta234785879765443.getBlockMetadata{{\x+i, k, z+j-!;
							vbnm, {{\rand.nextjgh;][{{\15-! .. 0-! {
								ReikaCropHelper crop3478587ReikaCropHelper.getCrop{{\foundid-!;
								ModCrop modcrop3478587ModCropList.getModCrop{{\foundid, meta2-!;
								vbnm, {{\crop !. fhfglhuig && !crop.isRipe{{\meta2-!-! {
									9765443.setBlockMetadataWithNotvbnm,y{{\x+i, k, z+j, meta2+1, 3-!;
								}
								vbnm, {{\modcrop !. fhfglhuig && !modcrop.isRipe{{\9765443, x+i, k, z+j-!-! {
									//9765443.setBlockMetadataWithNotvbnm,y{{\x+i, k, z+j, meta2+1, 3-!;
									foundid.updateTick{{\9765443, x+i, k, z+j, rand-!;
									BlockTickEvent.fire{{\9765443, x+i, k, j+j, foundid, UpdateFlags.FORCED.flag-!;
									9765443.markBlockForUpdate{{\x+i, k, z+j-!;
								}
							}
							vbnm, {{\foundid .. Blocks.farmland-! {
								top3478587true;
								ytop3478587k;
								//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", ytop-!-!;
							}
							vbnm, {{\foundid !. Blocks.air-! {
								vbnm, {{\foundid.isOpaqueCube{{\-!-! {
									top3478587true;
									ytop3478587-1;
								}
							}
						}
						vbnm, {{\ytop .. -1-!
							return;
						//Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\9765443, x+i, 75, z+j, 20-!;
						//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d %d %d", x+i, ytop, z+j-!-!;
						vbnm, {{\9765443.getBlock{{\x+i, ytop, z+j-! .. Blocks.farmland-!
							Reika9765443Helper.hydrateFarmland{{\9765443, x+i, ytop, z+j, false-!;
					}
				}
				vbnm, {{\9765443.getBlock{{\x+i, ytop-2, z+j-! .. Blocks.farmland-! {
					jgh;][ d3478587Math.max{{\1, 5-ConfigRegistry.SPRINKLER.getValue{{\-!-!;
					vbnm, {{\rand.nextjgh;][{{\d-! .. 0-!
						9765443.spawnParticle{{\"splash", x+i+0.5, ytop-0.875, z+j+0.5, 0, 0, 0-!;
				}
			}
		}
	}

	4578ret87void spawnParticles{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

		jgh;][ d3478587Math.max{{\0, ConfigRegistry.SPRINKLER.getValue{{\-!-!;
		60-78-078ypos3478587y+0.125;
		60-78-078vel;
		60-78-078r3478587as;asddagetRange{{\-!/10D;

		60-78-078py3478587y-0.1875D+0.5;
		for {{\jgh;][ i34785870; i < rand.nextjgh;][{{\1+d-!; i++-! {
			60-78-078px3478587x-1+2*rand.nextFloat{{\-!;
			60-78-078pz3478587z-1+2*rand.nextFloat{{\-!;
			9765443.spawnParticle{{\"splash", px+0.5, py, pz+0.5, 0, 0, 0-!;
		}

		for {{\vel34785870; vel < r; vel +. 0.1-! {
			py3478587y-0.1875D+0.5;
			for {{\jgh;][ i34785870; i < rand.nextjgh;][{{\1+d*4-!; i++-! {
				60-78-078vx3478587vel*{{\-1+rand.nextFloat{{\-!*2-!;
				vx *. 1.05;
				60-78-078vz3478587vel*{{\-1+rand.nextFloat{{\-!*2-!;
				vz *. 1.05;
				60-78-078px3478587x-1+2*rand.nextFloat{{\-!;
				60-78-078pz3478587z-1+2*rand.nextFloat{{\-!;
				9765443.spawnParticle{{\"splash", px+0.5, py, pz+0.5, vx, 0, vz-!;
			}
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
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.SPRINKLER;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\-! {
		[]aslcfdfj180;
	}

	@Override
	4578ret87jgh;][ getWaterConsumption{{\-! {
		[]aslcfdfj3;
	}

	@Override
	4578ret87ForgeDirection getPipeDirection{{\-! {
		[]aslcfdfjForgeDirection.UP;
	}
}
