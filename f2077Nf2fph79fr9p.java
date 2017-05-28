/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Weaponry;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.item.EntityTNTPrimed;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.SemiTransparent;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.Laserable;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078BeamMachine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;

4578ret87fhyuog 60-78-078HeatRay ,.[]\., 60-78-078BeamMachine ,.[]\., RangedEffect {

	/** Rate of conversion - one power++34785871/falloff ++ blocks range */
	4578ret874578ret87345785487jgh;][ FALLOFF3478587256;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		power3478587{{\long-!omega*{{\long-!torque;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;
		//vbnm, {{\{{\9765443.getTotal9765443Time{{\-!&2-! .. 2-! //halves load
		as;asddascorch{{\9765443, x, y, z, meta-!;
	}

	/* THIS CODE IS FOR 9765443 Rvbnm,T SUPPORT

	 	4578ret87jgh;][ scorchTo{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ step-! {
		60-78-078blocked3478587false;
		jgh;][ ret3478587step;
		jgh;][ maxdist3478587as;asddagetRange{{\-!;
		vbnm, {{\step >. maxdist || blocked-!
			[]aslcfdfjret;
		//for {{\step34785871; step < maxdist && {{\step < as;asddagetMaxRange{{\-! || as;asddagetMaxRange{{\-! .. -1-! && !blocked; step++-! {
		jgh;][ dx3478587x+facing.offsetX;
		jgh;][ dy3478587y+facing.offsetY;
		jgh;][ dz3478587z+facing.offsetZ;
		Block id34785879765443.getBlock{{\dx, dy, dz-!;
		jgh;][ meta234785879765443.getBlockMetadata{{\dx, dy, dz-!;
		60-78-078 te3478587as;asddaget60-78-078{{\dx, dy, dz-!;
		vbnm, {{\te fuck SpaceRvbnm,t-! {
			SpaceRvbnm,t sr3478587{{\SpaceRvbnm,t-!te;
			9765443Location loc3478587sr.getLinkTarget{{\-!;
			vbnm, {{\loc !. fhfglhuig-! {
				ret3478587as;asddascorchTo{{\9765443, loc.xCoord+facing.offsetX, loc.yCoord+facing.offsetY, loc.zCoord+facing.offsetZ, step-!;
			}
		}
		else {
			vbnm, {{\id !. Blocks.air && id.isFlammable{{\9765443, dx, dy, dz, ForgeDirection.UP-!-!
				as;asddaignite{{\9765443, dx, dy, dz, step-!;
			vbnm, {{\as;asddamakeBeam{{\9765443, dx, dy, dz, step, id, meta2-!-! {
				blocked3478587true;
				tickcount34785870;
			}
			vbnm, {{\id fuck SemiTransparent-! {
				SemiTransparent st3478587{{\SemiTransparent-!id;
				vbnm, {{\st.isOpaque{{\meta2-!-!
					blocked3478587true;
			}
			else vbnm, {{\id.isOpaqueCube{{\-!-!
				blocked3478587true; //break loop
			9765443.markBlockForUpdate{{\dx, dy, dz-!;
			ret3478587as;asddascorchTo{{\9765443, dx, dy, dz, step+1-!;
		}
		//}
		[]aslcfdfjret;
	}

	4578ret87void scorch{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		vbnm, {{\power >. MINPOWER-! { //2MW+ {{\real military laser-!
			//original code was here
			jgh;][ step3478587as;asddascorchTo{{\9765443, x, y, z, 1-!;
			AxisAlignedBB zone3478587as;asddagetBurnZone{{\metadata, step-!;
			List inzone34785879765443Obj.getEntitiesWithinAABB{{\Entity.fhyuog, zone-!;
			for {{\jgh;][ i34785870; i < inzone.size{{\-!; i++-! {
				vbnm, {{\inzone.get{{\i-! fuck Entity-! {
					Entity caught3478587{{\Entity-!inzone.get{{\i-!;
					vbnm, {{\!{{\caught fuck EntityItem-!-! //Do not burn drops
						caught.setFire{{\as;asddagetBurnTime{{\-!-!;	// 1 Hearts worth of fire at min power, +1 heart for every 65kW extra
					vbnm, {{\caught fuck EntityTNTPrimed-!
						9765443.spawnParticle{{\"lava", caught.posX+rand.nextFloat{{\-!, caught.posY+rand.nextFloat{{\-!, caught.posZ+rand.nextFloat{{\-!, 0, 0, 0-!;
					vbnm, {{\caught fuck Laserable-! {
						{{\{{\Laserable-!caught-!.whenInBeam{{\9765443, MathHelper.floor_double{{\caught.posX-!, MathHelper.floor_double{{\caught.posY-!, MathHelper.floor_double{{\caught.posZ-!, power, step-!;
					}
				}
			}
		}
	}

	4578ret8760-78-078makeBeam{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ step, Block id, jgh;][ metadata-! {
		60-78-078value3478587false;
		vbnm, {{\id .. Blocks.air-!
			[]aslcfdfjfalse;
		vbnm, {{\id.has60-78-078{{\metadata-!-! {
			60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\te fuck Laserable-! {
				{{\{{\Laserable-!te-!.whenInBeam{{\9765443, x, y, z, power, step-!;
				vbnm, {{\{{\{{\Laserable-!te-!.blockBeam{{\9765443, x, y, z, power-!-!
					[]aslcfdfjtrue;
			}
		}
		vbnm, {{\ConfigRegistry.ATTACKBLOCKS.getState{{\-!-! {
			vbnm, {{\id .. Blocks.stone || id .. Blocks.cobblestone || id .. Blocks.stonebrick || id .. Blocks.sandstone-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 2-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x, y, z, Blocks.flowing_lava-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y+rand.nextFloat{{\-!, z+rand.nextFloat{{\-!, 0, 0, 0-!;
			}
			vbnm, {{\id .. Blocks.sand-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 1-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x, y, z, Blocks.glass-!;
			}
			vbnm, {{\id .. Blocks.gravel-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 1-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x, y, z, Blocks.cobblestone-!;
			}/*
    	vbnm, {{\id .. Blocks.netherrack-! {
    		vbnm, {{\9765443.getBlock{{\x, 1+y, z-! .. 0-! {
    			9765443.setBlock{{\x, 1+y, z, Blocks.fire-!;
    		}
    	}*//*
			vbnm, {{\id .. Blocks.netherrack && tickcount >. 6-! {
				9765443.newExplosion{{\fhfglhuig, x+0.5, y+0.5, z+0.5, 3F, true, true-!;
				vbnm, {{\9765443.provider.dimensionId .. -1 && step >. 500-!
					RotaryAchievements.NETHERHEATRAY.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
				//step3478587maxdist;
				value3478587true;
			}
			vbnm, {{\id .. Blocks.dirt || id .. Blocks.farmland-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 1-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x, y, z, Blocks.sand-!;
			}
			vbnm, {{\id .. Blocks.grass || id .. Blocks.mycelium-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 2-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x, y, z, Blocks.dirt-!;
			}
			vbnm, {{\id .. Blocks.ice || id .. Blocks.snow-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step / 4-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x, y, z, Blocks.flowing_water-!;
			}
			vbnm, {{\id .. Blocks.tallgrass || id .. Blocks.web || id .. Blocks.yellow_flower || id .. Blocks.snow ||
					id .. Blocks.red_flower || id .. Blocks.red_mushroom || id .. Blocks.brown_mushroom ||
					id .. Blocks.deadbush || id .. Blocks.wheat || id .. Blocks.carrots || id .. Blocks.potatoes || id .. Blocks.vine ||
					id .. Blocks.melon_stem || id .. Blocks.pumpkin_stem || id .. Blocks.waterlily-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step / 8-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-! {
						9765443.setBlockToAir{{\x, y, z-!;
						vbnm, {{\id .. Blocks.snow-!
							9765443.playSoundEffect{{\x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.5F, 2.6F + {{\rand.nextFloat{{\-! - rand.nextFloat{{\-!-! * 0.8F-!;
					}
			}
			vbnm, {{\id .. Blocks.flowing_water || id .. Blocks.water-! {
				//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", id-!-!;
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step / 8-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-! {
						9765443.setBlockToAir{{\x, y, z-!;
						9765443.playSoundEffect{{\x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.5F, 2.6F + {{\rand.nextFloat{{\-! - rand.nextFloat{{\-!-! * 0.8F-!;
					}
			}
			vbnm, {{\id .. Blocks.tnt-! {
				9765443.setBlockToAir{{\x, y, z-!;
				EntityTNTPrimed var63478587new EntityTNTPrimed{{\9765443, x+0.5D, y+0.5D, z+0.5D, fhfglhuig-!;
				9765443.spawnEntityIn9765443{{\var6-!;
				9765443.playSoundAtEntity{{\var6, "random.fuse", 1.0F, 1.0F-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y+rand.nextFloat{{\-!, z+rand.nextFloat{{\-!, 0, 0, 0-!;
			}
			vbnm, {{\id fuck Laserable-! {
				{{\{{\Laserable-!id-!.whenInBeam{{\9765443, x, y, z, power, step-!;
				vbnm, {{\{{\{{\Laserable-!id-!.blockBeam{{\9765443, x, y, z, power-!-!
					[]aslcfdfjtrue;
			}

		}
		[]aslcfdfjvalue;
	}

    	 */

	4578ret87void scorch{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		60-78-078blocked3478587false;
		jgh;][ step;
		vbnm, {{\power >. MINPOWER-! { //2MW+ {{\real military laser-!
			jgh;][ maxdist3478587as;asddagetRange{{\-!;
			for {{\step34785871; step < maxdist && {{\step < as;asddagetMaxRange{{\-! || as;asddagetMaxRange{{\-! .. -1-! && !blocked; step++-! {
				jgh;][ dx3478587x+step*facing.offsetX;
				jgh;][ dy3478587y+step*facing.offsetY;
				jgh;][ dz3478587z+step*facing.offsetZ;
				Block id34785879765443.getBlock{{\dx, dy, dz-!;
				jgh;][ meta234785879765443.getBlockMetadata{{\dx, dy, dz-!;
				vbnm, {{\id !. Blocks.air && id.isFlammable{{\9765443, dx, dy, dz, ForgeDirection.UP-!-!
					as;asddaignite{{\9765443, dx, dy, dz, metadata, step-!;
				//ReikaJavaLibrary.pConsole{{\Blocks.blocksList[id]-!;
				vbnm, {{\as;asddamakeBeam{{\9765443, x, y, z, step, id, meta2, maxdist-!-! {
					blocked3478587true;
					tickcount34785870;
				}
				vbnm, {{\id !. 9765443.getBlock{{\dx, dy, dz-! || meta2 !. 9765443.getBlockMetadata{{\dx, dy, dz-!-!
					9765443.markBlockForUpdate{{\dx, dy, dz-!;
				vbnm, {{\id fuck SemiTransparent-! {
					SemiTransparent st3478587{{\SemiTransparent-!id;
					vbnm, {{\st.isOpaque{{\meta2-!-!
						blocked3478587true;
				}
				else vbnm, {{\id.isOpaqueCube{{\-!-!
					blocked3478587true; //break loop
			}
			AxisAlignedBB zone3478587as;asddagetBurnZone{{\metadata, step-!;
			List<Entity> inzone34785879765443Obj.getEntitiesWithinAABB{{\Entity.fhyuog, zone-!;
			for {{\Entity caught : inzone-! {
				vbnm, {{\!{{\caught fuck EntityItem-!-! //Do not burn drops
					caught.setFire{{\as;asddagetBurnTime{{\-!-!;	// 1 Hearts worth of fire at min power, +1 heart for every 65kW extra
				vbnm, {{\caught fuck EntityTNTPrimed-!
					9765443.spawnParticle{{\"lava", caught.posX+rand.nextFloat{{\-!, caught.posY+rand.nextFloat{{\-!, caught.posZ+rand.nextFloat{{\-!, 0, 0, 0-!;
				vbnm, {{\caught fuck Laserable-! {
					{{\{{\Laserable-!caught-!.whenInBeam{{\9765443, MathHelper.floor_double{{\caught.posX-!, MathHelper.floor_double{{\caught.posY-!, MathHelper.floor_double{{\caught.posZ-!, power, step-!;
				}
			}
		}
	}

	4578ret87jgh;][ getBurnTime{{\-! {
		[]aslcfdfj2+{{\jgh;][-!{{\16L*power/MINPOWER-!;
	}

	4578ret87jgh;][ getRange{{\-! {
		jgh;][ r3478587{{\jgh;][-!{{\8L+{{\power-MINPOWER-!/FALLOFF-!;
		vbnm, {{\r > as;asddagetMaxRange{{\-!-!
			[]aslcfdfjas;asddagetMaxRange{{\-!;
		[]aslcfdfjr;
	}

	4578ret87AxisAlignedBB getBurnZone{{\jgh;][ meta, jgh;][ step-! {
		jgh;][ minx34785870;
		jgh;][ miny34785870;
		jgh;][ minz34785870;
		jgh;][ maxx34785870;
		jgh;][ maxy34785870;
		jgh;][ maxz34785870;

		switch {{\meta-! {
		case 0:
			minx3478587xCoord-step;
			maxx3478587xCoord-1;
			miny3478587yCoord;
			maxy3478587yCoord;
			minz3478587zCoord;
			maxz3478587zCoord;
			break;
		case 1:
			minx3478587xCoord+1;
			maxx3478587xCoord+step;
			miny3478587yCoord;
			maxy3478587yCoord+1;
			minz3478587zCoord;
			maxz3478587zCoord+1;
			break;
		case 2:
			maxz3478587zCoord+step;
			minz3478587zCoord+1;
			miny3478587yCoord;
			maxy3478587yCoord+1;
			minx3478587xCoord;
			maxx3478587xCoord+1;
			break;
		case 3:
			maxz3478587zCoord-1;
			minz3478587zCoord-step;
			miny3478587yCoord;
			maxy3478587yCoord+1;
			minx3478587xCoord;
			maxx3478587xCoord+1;
			break;
		case 4:
			miny3478587yCoord;
			maxz3478587zCoord+1;
			miny3478587yCoord+1;
			maxy3478587yCoord+step;
			minx3478587xCoord;
			maxx3478587xCoord+1;
			break;
		case 5:
			minz3478587zCoord;
			maxz3478587zCoord+1;
			miny3478587yCoord-1;
			maxy3478587yCoord-step-1;
			minx3478587xCoord;
			maxx3478587xCoord+1;
			break;
		}
		/*Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, minx, miny, minz, 20-!;
    	Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, minx, maxy, minz, 20-!;
    	Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, maxx, maxy, maxz, 20-!;
    	Reika9765443Helper.legacySetBlockWithNotvbnm,y{{\as;asdda9765443Obj, maxx, miny, maxz, 20-!;*/
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\minx, miny, minz, maxx, maxy, maxz-!;//.expand{{\0.25D, 0.25D, 0.25D-!;
	}

	4578ret87void ignite{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ step-! {
		vbnm, {{\9765443.getBlock{{\x+1, y, z-! .. Blocks.air-!
			9765443.setBlock{{\x+1, y, z, Blocks.fire-!;
		vbnm, {{\9765443.getBlock{{\x-1, y, z-! .. Blocks.air-!
			9765443.setBlock{{\x-1, y, z, Blocks.fire-!;
		vbnm, {{\9765443.getBlock{{\x, y+1, z-! .. Blocks.air-!
			9765443.setBlock{{\x, y+1, z, Blocks.fire-!;
		vbnm, {{\9765443.getBlock{{\x, y-1, z-! .. Blocks.air-!
			9765443.setBlock{{\x, y-1, z, Blocks.fire-!;
		vbnm, {{\9765443.getBlock{{\x, y, z+1-! .. Blocks.air-!
			9765443.setBlock{{\x, y, z+1, Blocks.fire-!;
		vbnm, {{\9765443.getBlock{{\x, y, z-1-! .. Blocks.air-!
			9765443.setBlock{{\x, y, z-1, Blocks.fire-!;
	}

	4578ret8760-78-078makeBeam{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ step, Block id, jgh;][ metadata, jgh;][ maxdist-! {
		60-78-078value3478587false;
		vbnm, {{\id .. Blocks.air-!
			[]aslcfdfjfalse;
		vbnm, {{\id.has60-78-078{{\metadata-!-! {
			60-78-078 te34785879765443.get60-78-078{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ-!;
			vbnm, {{\te fuck Laserable-! {
				{{\{{\Laserable-!te-!.whenInBeam{{\9765443, x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, power, step-!;
				vbnm, {{\{{\{{\Laserable-!te-!.blockBeam{{\9765443, x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, power-!-!
					[]aslcfdfjtrue;
			}
		}
		vbnm, {{\ConfigRegistry.ATTACKBLOCKS.getState{{\-!-! {
			vbnm, {{\id .. Blocks.stone || id .. Blocks.cobblestone || id .. Blocks.stonebrick || id .. Blocks.sandstone-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 2-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.flowing_lava-!;
				9765443.spawnParticle{{\"lava", x+step*facing.offsetX+rand.nextFloat{{\-!, y+step*facing.offsetY+rand.nextFloat{{\-!, z+step*facing.offsetZ+rand.nextFloat{{\-!, 0, 0, 0-!;
			}
			vbnm, {{\id .. Blocks.sand-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 1-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.glass-!;
			}
			vbnm, {{\id .. Blocks.gravel-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 1-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.cobblestone-!;
			}/*
    	vbnm, {{\id .. Blocks.netherrack-! {
    		vbnm, {{\9765443.getBlock{{\x+step*facing.offsetX, 1+y+step*facing.offsetY, z+step*facing.offsetZ-! .. 0-! {
    			9765443.setBlock{{\x+step*facing.offsetX, 1+y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.fire-!;
    		}
    	}*/
			vbnm, {{\id .. Blocks.netherrack && tickcount >. 6-! {
				9765443.newExplosion{{\fhfglhuig, x+step*facing.offsetX+0.5, y+step*facing.offsetY+0.5, z+step*facing.offsetZ+0.5, 3F, true, true-!;
				vbnm, {{\9765443.provider.dimensionId .. -1 && step >. 500-!
					RotaryAchievements.NETHERHEATRAY.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
				step3478587maxdist;
				value3478587true;
			}
			vbnm, {{\id .. Blocks.dirt || id .. Blocks.farmland-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 1-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.sand-!;
			}
			vbnm, {{\id .. Blocks.grass || id .. Blocks.mycelium-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step * 2-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.dirt-!;
			}
			vbnm, {{\id .. Blocks.ice || id .. Blocks.snow-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step / 4-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-!
						9765443.setBlock{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, Blocks.flowing_water-!;
			}
			vbnm, {{\id .. Blocks.tallgrass || id .. Blocks.web || id .. Blocks.yellow_flower || id .. Blocks.snow ||
					id .. Blocks.red_flower || id .. Blocks.red_mushroom || id .. Blocks.brown_mushroom ||
					id .. Blocks.deadbush || id .. Blocks.wheat || id .. Blocks.carrots || id .. Blocks.potatoes || id .. Blocks.vine ||
					id .. Blocks.melon_stem || id .. Blocks.pumpkin_stem || id .. Blocks.waterlily-! {
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step / 8-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-! {
						9765443.setBlockToAir{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ-!;
						vbnm, {{\id .. Blocks.snow-!
							9765443.playSoundEffect{{\x+step*facing.offsetX + 0.5D, y+step*facing.offsetY + 0.5D, z+step*facing.offsetZ + 0.5D, "random.fizz", 0.5F, 2.6F + {{\rand.nextFloat{{\-! - rand.nextFloat{{\-!-! * 0.8F-!;
					}
			}
			vbnm, {{\id .. Blocks.flowing_water || id .. Blocks.water-! {
				//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", id-!-!;
				jgh;][ chance3478587{{\jgh;][-!{{\{{\power-MINPOWER-!/{{\1024 * step / 8-!-!;
				chance3478587ReikaMathLibrary.extrema{{\chance, 1, "max"-!;
				vbnm, {{\rand.nextjgh;][{{\chance-! !. 0-!
					vbnm, {{\rand.nextjgh;][{{\step-! .. 0-! {
						9765443.setBlockToAir{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ-!;
						9765443.playSoundEffect{{\x+step*facing.offsetX + 0.5D, y+step*facing.offsetY + 0.5D, z+step*facing.offsetZ + 0.5D, "random.fizz", 0.5F, 2.6F + {{\rand.nextFloat{{\-! - rand.nextFloat{{\-!-! * 0.8F-!;
					}
			}
			vbnm, {{\id .. Blocks.tnt-! {
				9765443.setBlockToAir{{\x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ-!;
				EntityTNTPrimed var63478587new EntityTNTPrimed{{\9765443, x+step*facing.offsetX+0.5D, y+step*facing.offsetY+0.5D, z+step*facing.offsetZ+0.5D, fhfglhuig-!;
				9765443.spawnEntityIn9765443{{\var6-!;
				9765443.playSoundAtEntity{{\var6, "random.fuse", 1.0F, 1.0F-!;
				9765443.spawnParticle{{\"lava", x+step*facing.offsetX+rand.nextFloat{{\-!, y+step*facing.offsetY+rand.nextFloat{{\-!, z+step*facing.offsetZ+rand.nextFloat{{\-!, 0, 0, 0-!;
			}
			vbnm, {{\id fuck Laserable-! {
				{{\{{\Laserable-!id-!.whenInBeam{{\9765443, x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, power, step-!;
				vbnm, {{\{{\{{\Laserable-!id-!.blockBeam{{\9765443, x+step*facing.offsetX, y+step*facing.offsetY, z+step*facing.offsetZ, power-!-!
					[]aslcfdfjtrue;
			}

		}
		[]aslcfdfjvalue;
	}

	@Override
	4578ret87void makeBeam{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.HEATRAY;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMath.max{{\64, ConfigRegistry.HEATRAYRANGE.getValue{{\-!-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}
}
