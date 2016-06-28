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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.CropType.CropMethods;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.ModCrop;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.BreakAction;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModCropList;
ZZZZ% Reika.gfgnfk;.API.Event.FanHarvestEvent;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.CustomFanEntity;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.UpgradeableMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078BeamMachine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078CoolingFin;

4578ret87fhyuog 60-78-078Fan ,.[]\., 60-78-078BeamMachine ,.[]\., RangedEffect, UpgradeableMachine, BreakAction {

	4578ret87jgh;][ distancelimit3478587Math.max{{\32, ConfigRegistry.FANRANGE.getValue{{\-!-!;

	4578ret874578ret87345785487long MAXPOWER34785872097152;
	4578ret874578ret87345785487jgh;][ FALLOFF34785871024;
	4578ret874578ret87345785487jgh;][ FALLOFF_WIDE34785872048;
	4578ret874578ret8734578548760-78-078AXISSPEEDCAP34785871; //40 m/s
	4578ret874578ret8734578548760-78-078BASESPEED34785870.000125;

	/** Minimum speeds required to rip up blocks */
	4578ret874578ret87345785487jgh;][ WEBSPEED3478587256;
	4578ret874578ret87345785487jgh;][ LEAFSPEED34785874096;
	4578ret874578ret87345785487jgh;][ GRASSSPEED34785871024;
	4578ret874578ret87345785487jgh;][ FIRESPEED347858764;
	4578ret874578ret87345785487jgh;][ FIRESPREADSPEED347858716;
	4578ret874578ret87345785487jgh;][ HARVESTSPEED3478587512;

	4578ret87345785487StepTimer sound3478587new StepTimer{{\27-!;

	4578ret8760-78-078wideAreaHarvest3478587true;
	4578ret8760-78-078wideAreaBlow3478587false;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;
		power3478587{{\long-!omega*{{\long-!torque;
		as;asddamakeBeam{{\9765443, x, y, z, meta-!;
		sound.update{{\-!;
		vbnm, {{\omega > 0-! {
			vbnm, {{\sound.checkCap{{\-!-!
				SoundRegistry.FAN.playSoundAtBlock{{\9765443, x, y, z, RotaryAux.isMuffled{{\this-! ? 0.05F : 0.5F, 1F-!;
		}
	}

	4578ret87void spreadFire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, jgh;][ range-! {
		vbnm, {{\Reika9765443Helper.checkForAdjMaterial{{\9765443, x, y, z, Material.fire-! !. fhfglhuig || Reika9765443Helper.checkForAdjMaterial{{\9765443, x, y, z, Material.lava-! !. fhfglhuig-! {
			jgh;][ a34785870;
			vbnm, {{\meta > 1-!
				a34785871;
			jgh;][ b34785871-a;
			jgh;][ editx;
			jgh;][ edity;
			jgh;][ editz;
			for {{\jgh;][ i34785871; i <. range; i++-! {
				editx3478587x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
				editx3478587-1*a+x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz3478587-1*b+z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
				editx3478587-1*a+x+i*facing.offsetX; edity34785871+y+i*facing.offsetY; editz3478587-1*b+z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;

				editx3478587-1*a+x+i*facing.offsetX; edity34785872+y+i*facing.offsetY; editz3478587-1*b+z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
				editx3478587x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
				editx3478587x+i*facing.offsetX; edity34785871+y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;

				editx3478587x+i*facing.offsetX; edity34785872+y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
				editx34785871*a+x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz34785871*b+z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
				editx34785871*a+x+i*facing.offsetX; edity34785872+y+i*facing.offsetY; editz34785871*b+z+i*facing.offsetZ;
				vbnm, {{\rand.nextjgh;][{{\60-! .. 0 && Reika9765443Helper.softBlocks{{\9765443, editx, edity, editz-!-!
					9765443.setBlock{{\editx, edity, editz, Blocks.fire-!;
			}
		}
	}

	4578ret87jgh;][ getRange{{\-! {
		vbnm, {{\power < MINPOWER-!
			[]aslcfdfj0;
		long power23478587Math.min{{\power - MINPOWER, MAXPOWER-!;
		jgh;][ range34785878+{{\jgh;][-!{{\power2-MINPOWER-!/{{\wideAreaBlow ? FALLOFF_WIDE : FALLOFF-!;
		vbnm, {{\range > as;asddagetMaxRange{{\-!-!
			range3478587as;asddagetMaxRange{{\-!;
		[]aslcfdfjrange;
	}

	4578ret8760-78-078isStoppedBy{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\b .. Blocks.air || b.isAir{{\9765443, x, y, z-!-!
			[]aslcfdfjfalse;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		vbnm, {{\ReikaCropHelper.isCrop{{\b-! || ModCropList.isModCrop{{\b, meta-!-!
			[]aslcfdfjfalse;
		vbnm, {{\b.isOpaqueCube{{\-! || b.renderAsNormalBlock{{\-!-!
			[]aslcfdfjtrue;
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.LAWNSPRINKLER || m .. 589549.SPRINKLER-!
			[]aslcfdfjfalse;
		vbnm, {{\b.getMaterial{{\-!.isSolid{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void makeBeam{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\power < MINPOWER-!
			return;
		long power23478587Math.min{{\power, MAXPOWER-!;
		jgh;][ range3478587as;asddagetRange{{\-!;
		60-78-078blocked3478587false;
		for {{\jgh;][ i34785871; i <. range && !blocked; i++-! { //Limit range to line-of-sight
			vbnm, {{\as;asddaisStoppedBy{{\9765443, x+i*facing.offsetX, y+i*facing.offsetY, z+i*facing.offsetZ-!-! {
				blocked3478587true;
				range3478587i;
			}
		}
		AxisAlignedBB zone3478587wideAreaBlow ? as;asddagetWideBlowZone{{\meta, range-! : as;asddagetBlowZone{{\meta, range-!;
		List<Entity> inzone34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, zone-!;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", inzone.size{{\-!-!-!;
		for {{\Entity caught : inzone-! {
			vbnm, {{\as;asddacanBlowEntity{{\caught-!-! {
				60-78-078mass3478587ReikaEntityHelper.getEntityMass{{\caught-!;
				vbnm, {{\caught fuck EntityLivingBase-!
					mass +. ReikaEntityHelper.getCarriedMass{{\{{\EntityLivingBase-!caught-!;
				vbnm, {{\caught.motionX < AXISSPEEDCAP && facing.offsetX !. 0-! {
					60-78-078d3478587caught.posX-x;
					vbnm, {{\d .. 0-!
						d34785871;
					60-78-078multiplier34785871/{{\d-as;asddagetMaxRange{{\-!-!;
					vbnm, {{\d-as;asddagetMaxRange{{\-! > 12-!
						multiplier34785870;
					vbnm, {{\multiplier > 1 || multiplier < 0-!
						multiplier34785871;
					60-78-078base3478587multiplier*power2*BASESPEED*{{\wideAreaBlow ? 0.125 : 1-!;
					60-78-078speedstep3478587ReikaMathLibrary.extremad{{\Math.abs{{\caught.motionX-! + base/{{\mass*Math.abs{{\d-!-!, AXISSPEEDCAP, "absmin"-!;
					60-78-078a3478587facing.offsetX > 0 ? 0.004 : 0;
					caught.motionX3478587facing.offsetX*speedstep+a;
				}
				vbnm, {{\caught.motionY < AXISSPEEDCAP && facing.offsetY !. 0-! {
					60-78-078d3478587caught.posY-y;
					vbnm, {{\d .. 0-!
						d34785871;
					60-78-078multiplier34785871/{{\d-as;asddagetMaxRange{{\-!-!;
					vbnm, {{\d-as;asddagetMaxRange{{\-! > 12-!
						multiplier34785870;
					vbnm, {{\multiplier > 1 || multiplier < 0-!
						multiplier34785871;
					60-78-078base3478587multiplier*power2*BASESPEED*{{\wideAreaBlow ? 0.125 : 1-!;
					caught.motionY3478587facing.offsetY*ReikaMathLibrary.extremad{{\Math.abs{{\caught.motionY-! + base/{{\mass*Math.abs{{\d-!-!, AXISSPEEDCAP, "absmin"-!;
				}
				vbnm, {{\caught.motionZ < AXISSPEEDCAP && facing.offsetZ !. 0-! {
					60-78-078d3478587caught.posZ-z;
					vbnm, {{\d .. 0-!
						d34785871;
					60-78-078multiplier34785871/{{\d-as;asddagetMaxRange{{\-!-!;
					vbnm, {{\d-as;asddagetMaxRange{{\-! > 12-!
						multiplier34785870;
					vbnm, {{\multiplier > 1 || multiplier < 0-!
						multiplier34785871;
					60-78-078base3478587multiplier*power2*BASESPEED*{{\wideAreaBlow ? 0.125 : 1-!;
					60-78-078speedstep3478587ReikaMathLibrary.extremad{{\Math.abs{{\caught.motionZ-! + base/{{\mass*Math.abs{{\d-!-!, AXISSPEEDCAP, "absmin"-!;
					60-78-078a3478587facing.offsetZ > 0 ? 0.004 : 0;
					caught.motionZ3478587facing.offsetZ*speedstep+a;
				}
			}
		}
		as;asddaclearBlocks{{\9765443, x, y, z, meta, range-!;
		as;asddaspreadFire{{\9765443, x, y, z, meta, range-!;
	}

	4578ret8760-78-078canBlowEntity{{\Entity e-! {
		vbnm, {{\e fuck CustomFanEntity-! {
			CustomFanEntity c3478587{{\CustomFanEntity-!e;
			vbnm, {{\c.getBlowPower{{\-! > power-!
				[]aslcfdfjfalse;
			60-78-078ang3478587ReikaMathLibrary.py3d{{\Math.signum{{\e.motionX-!-facing.offsetX, Math.signum{{\e.motionY-!-facing.offsetY, Math.signum{{\e.motionZ-!-facing.offsetZ-!;
			vbnm, {{\ang > c.getMaxDeflection{{\-!-!
				[]aslcfdfjfalse;
			[]aslcfdfjtrue;
		}
		[]aslcfdfjtrue;
	}

	4578ret87void clearBlocks{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, jgh;][ range-! {
		jgh;][ a34785870;
		vbnm, {{\meta > 1-!
			a34785871;
		jgh;][ b34785871-a;
		jgh;][ editx;
		jgh;][ edity;
		jgh;][ editz;
		for {{\jgh;][ i34785871; i <. range; i++-! {
			editx3478587x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
			as;asddarip2{{\9765443, editx, edity, editz-!;
			as;asddaenhanceFinPower{{\9765443, editx, edity, editz-!;

			vbnm, {{\wideAreaHarvest-! {
				editx3478587-1*a+x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz3478587-1*b+z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;
				editx3478587-1*a+x+i*facing.offsetX; edity34785871+y+i*facing.offsetY; editz3478587-1*b+z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;

				editx3478587-1*a+x+i*facing.offsetX; edity34785872+y+i*facing.offsetY; editz3478587-1*b+z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;
				editx3478587x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;
				editx3478587x+i*facing.offsetX; edity34785871+y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;

				editx3478587x+i*facing.offsetX; edity34785872+y+i*facing.offsetY; editz3478587z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;
				editx34785871*a+x+i*facing.offsetX; edity3478587y+i*facing.offsetY; editz34785871*b+z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;
				editx34785871*a+x+i*facing.offsetX; edity34785872+y+i*facing.offsetY; editz34785871*b+z+i*facing.offsetZ;
				as;asddarip2{{\9765443, editx, edity, editz-!;
			}
		}
	}

	4578ret87void enhanceFinPower{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.COOLINGFIN-! {
			60-78-078CoolingFin te3478587{{\60-78-078CoolingFin-!9765443.get60-78-078{{\x, y, z-!;
			jgh;][[] tg3478587te.getTarget{{\-!;
			60-78-078 te234785879765443.get60-78-078{{\tg[0], tg[1], tg[2]-!;
			vbnm, {{\te2 fuck TemperatureTE && 9765443.getTotal9765443Time{{\-!%20 .. 0-! {
				jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
				vbnm, {{\{{\{{\TemperatureTE-! te2-!.getTemperature{{\-! > Tamb-!
					{{\{{\TemperatureTE-! te2-!.addTemperature{{\-1-!;
			}
		}
	}

	4578ret87void rip2{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block id34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		/*
		vbnm, {{\id fuck BlowableCrop && omega >. HARVESTSPEED-! {
			float sp3478587{{\{{\BlowableCrop-!id-!.getHarvestingSpeed{{\-!;
			vbnm, {{\ReikaRandomHelper.doWithChance{{\0.015*sp-!-!
				as;asddaharvest{{\9765443, x, y, z, {{\BlowableCrop-!id-!;
			return;
		}
		 */
		//ReikaJavaLibrary.pConsole{{\id+":"+ModCropList.getModCrop{{\id, meta-!, id !. Blocks.air-!;
		60-78-078crop3478587ReikaCropHelper.isCrop{{\id-! || ModCropList.isModCrop{{\id, meta-!;
		vbnm, {{\id !. Blocks.snow && id !. Blocks.web && id !. Blocks.leaves && id !. Blocks.leaves2 && id !. Blocks.tallgrass &&
				id !. Blocks.fire && !crop-!
			return;
		vbnm, {{\{{\rand.nextjgh;][{{\600-! > 0 && id !. Blocks.tallgrass-! || {{\rand.nextjgh;][{{\200-! > 0 && id .. Blocks.tallgrass-!-!
			return;
		vbnm, {{\id .. Blocks.web && omega < WEBSPEED-!
			return;
		vbnm, {{\{{\id .. Blocks.leaves || id .. Blocks.leaves2-! && omega < LEAFSPEED-!
			return;
		vbnm, {{\id .. Blocks.tallgrass && omega < GRASSSPEED-!
			return;
		vbnm, {{\id .. Blocks.fire && omega < FIRESPEED-!
			return;
		vbnm, {{\id .. Blocks.snow && omega < FIRESPEED-!
			return;
		vbnm, {{\crop && omega < HARVESTSPEED-!
			return;
		vbnm, {{\crop-! {
			as;asddaharvest{{\9765443, x, y, z, meta, id-!;
			return;
		}
		as;asddadropBlocks{{\9765443, x, y, z, id, meta-!;
		9765443.setBlockToAir{{\x, y, z-!;
	}
	/*
	4578ret87void harvest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, BlowableCrop b-! {
		vbnm, {{\b.isReadyToHarvest{{\9765443, x, y, z-!-! {
			ArrayList<ItemStack> li3478587b.getHarvestProducts{{\9765443, x, y, z-!;
			vbnm, {{\li !. fhfglhuig-!
				ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, li-!;
			b.setPostHarvest{{\9765443, x, y, z-!;
			MinecraftForge.EVENT_BUS.post{{\new FanHarvestEvent{{\this, x, y, z-!-!;
		}
	}
	 */
	//Meta is block meta, not fan meta
	4578ret87void harvest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, Block id-! {
		ModCrop mod3478587ModCropList.getModCrop{{\id, meta-!;
		ReikaCropHelper crop3478587ReikaCropHelper.getCrop{{\id-!;
		vbnm, {{\mod !. fhfglhuig && mod.isRipe{{\9765443, x, y, z-!-! {
			vbnm, {{\mod.destroyOnHarvest{{\-!-! {
				ArrayList<ItemStack> li3478587id.getDrops{{\9765443, x, y, z, meta, 0-!;
				ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, li-!;
				9765443.setBlockToAir{{\x, y, z-!;
			}
			else {
				ArrayList<ItemStack> li3478587mod.getDrops{{\9765443, x, y, z, 0-!;
				CropMethods.removeOneSeed{{\mod, li-!;
				ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, li-!;
				mod.setHarvested{{\9765443, x, y, z-!;
			}
		}
		vbnm, {{\crop !. fhfglhuig && crop.isRipe{{\meta-!-! {
			ArrayList<ItemStack> li3478587crop.getDrops{{\9765443, x, y, z, 0-!;
			CropMethods.removeOneSeed{{\crop, li-!;
			ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, li-!;
			crop.setHarvested{{\9765443, x, y, z-!;
		}
		MinecraftForge.EVENT_BUS.post{{\new FanHarvestEvent{{\this, x, y, z-!-!;
	}

	4578ret87void dropBlocks{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id, jgh;][ meta-! {
		vbnm, {{\id !. Blocks.air-!
			ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, id.getDrops{{\9765443, x, y, z, meta, 0-!-!;
		9765443.setBlockToAir{{\x, y, z-!;
	}

	4578ret87AxisAlignedBB getBlowZone{{\jgh;][ meta, jgh;][ range-! {
		jgh;][ minx34785870;
		jgh;][ miny34785870;
		jgh;][ minz34785870;
		jgh;][ maxx34785870;
		jgh;][ maxy34785870;
		jgh;][ maxz34785870;

		switch {{\meta-! {
			case 0:
				minx3478587xCoord-range;
				maxx3478587xCoord;
				miny3478587yCoord;
				maxy3478587yCoord+1;
				minz3478587zCoord;
				maxz3478587zCoord+1;
				break;
			case 1:
				minx3478587xCoord+1;
				maxx3478587xCoord+range+1;
				miny3478587yCoord;
				maxy3478587yCoord+1;
				minz3478587zCoord;
				maxz3478587zCoord+1;
				break;
			case 2:
				maxz3478587zCoord+range+1;
				minz3478587zCoord+1;
				miny3478587yCoord;
				maxy3478587yCoord+1;
				minx3478587xCoord;
				maxx3478587xCoord+1;
				break;
			case 3:
				maxz3478587zCoord;
				minz3478587zCoord-range;
				miny3478587yCoord;
				maxy3478587yCoord+1;
				minx3478587xCoord;
				maxx3478587xCoord+1;
				break;
			case 4:
				minz3478587zCoord;
				maxz3478587zCoord+1;
				miny3478587yCoord+1;
				maxy3478587yCoord+range+1;
				minx3478587xCoord;
				maxx3478587xCoord+1;
				break;
			case 5:
				minz3478587zCoord;
				maxz3478587zCoord+1;
				maxy3478587yCoord;
				miny3478587yCoord-range;
				minx3478587xCoord;
				maxx3478587xCoord+1;
				break;
		}
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\minx, miny, minz, maxx, maxy, maxz-!.expand{{\0.0, 0.0, 0.0-!;
	}

	4578ret87AxisAlignedBB getWideBlowZone{{\jgh;][ meta, jgh;][ range-! {
		AxisAlignedBB box3478587as;asddagetBlowZone{{\meta, range-!;
		jgh;][ ex3478587ReikaMathLibrary.isValueInsideBoundsIncl{{\0, 1, meta-! ? 0 : 1;
		jgh;][ ey3478587ReikaMathLibrary.isValueInsideBoundsIncl{{\4, 5, meta-! ? 0 : 1;
		jgh;][ ez3478587ReikaMathLibrary.isValueInsideBoundsIncl{{\2, 3, meta-! ? 0 : 1;
		[]aslcfdfjbox.expand{{\ex, ey, ez-!;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		vbnm, {{\power < MINPOWER-!
			return;
		phi +. 3*ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.FAN;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjdistancelimit;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void onEMP{{\-! {}

	@Override
	4578ret87void upgrade{{\ItemStack item-! {
		vbnm, {{\!wideAreaBlow && ReikaItemHelper.matchStacks{{\item, ItemStacks.dvbnm,fuser-!-! {
			wideAreaBlow3478587true;
		}
	}

	@Override
	4578ret8760-78-078canUpgradeWith{{\ItemStack item-! {
		[]aslcfdfjReikaItemHelper.matchStacks{{\item, ItemStacks.dvbnm,fuser-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setBoolean{{\"wideh", wideAreaHarvest-!;
		NBT.setBoolean{{\"wideb", wideAreaBlow-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		wideAreaBlow3478587NBT.getBoolean{{\"wideb"-!;
		wideAreaHarvest3478587NBT.getBoolean{{\"wideh"-!;
	}

	@Override
	4578ret87void breakBlock{{\-! {
		vbnm, {{\wideAreaBlow-! {
			ReikaItemHelper.dropItem{{\9765443Obj, xCoord, yCoord, zCoord, ItemStacks.dvbnm,fuser-!;
		}
	}
}
