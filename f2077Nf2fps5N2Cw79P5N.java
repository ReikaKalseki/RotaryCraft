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

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityCreature;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.monster.EntityMob;
ZZZZ% net.minecraft.entity.monster.EntitySilverfish;
ZZZZ% net.minecraft.entity.passive.EntityAnimal;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.pathfinding.PathEntity;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.GuiController;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078SonicWeapon ,.[]\., 60-78-078PowerReceiver ,.[]\., GuiController, RangedEffect {

	4578ret87long setpitch;
	4578ret87long setvolume;

	4578ret874578ret87345785487long MAXBROWNNOTE347858764; //Triggers food poisoning
	4578ret874578ret87345785487long BATKILL347858780000; //Well within their echolocation pitch range
	4578ret874578ret87345785487long OMINOUS347858716; //That almost-infrasonic uneasy-feeling-generating tone; triggers blindness effect
	4578ret874578ret87345785487long DOGWHISTLE347858740000; //dog whistle
	4578ret874578ret87345785487long LRAD34785872400; //Pitch for most crowd-control audio weapons

	4578ret874578ret87345785487long LETHALVOLUME3478587100000000; //10^8 W/m {{\~38psi-!.200dB for 1-hit kill
	4578ret874578ret87345785487long BRICKDESTROY34785871000000; //.. 20kPa overpressure -> "Brick walls destroyed"
	4578ret874578ret87345785487long LRADVOLUME34785871260;
	4578ret874578ret87345785487long SHATTERGLASS3478587118680; //1psi overpressure
	4578ret874578ret87345785487long BREAKWOOD3478587475410; //2 psi overpressure
	4578ret874578ret87345785487long LUNGDAMAGE34785872971000; //5 psi causes pulmonary damage -> cause drowning effect
	4578ret874578ret87345785487long BRAINDAMAGE34785873906200; //125kPa causes TBIs
	4578ret874578ret87345785487long EYEDAMAGE34785871807500; //Causes blindness
	4578ret874578ret87345785487long SILVERFISHKILL3478587400000;

	4578ret874578ret87345785487long REFERENCE34785871000000000000L; // 10^-12 W/m^2 reference

	4578ret874578ret87345785487jgh;][ fudge3478587100;

	4578ret874578ret87345785487jgh;][ FALLOFF347858716384;

	4578ret874578ret87345785487long jgh;][ENSITYPERTORQUE3478587262144L*65536L*256L*8L;
	4578ret874578ret87345785487jgh;][ HZPEROMEGA34785878192;

	4578ret874578ret8734578548760-78-078ENABLEFREQ3478587false;
	4578ret874578ret8734578548760-78-078DECIBELMODE3478587true;

	4578ret87jgh;][ brownrange;
	4578ret87jgh;][ batrange;
	4578ret87jgh;][ ominousrange;
	4578ret87jgh;][ dogrange;
	4578ret87jgh;][ lradrange;

	4578ret87jgh;][ killrange;
	4578ret87jgh;][ brickrange;
	4578ret87jgh;][ glassrange;
	4578ret87jgh;][ woodrange;
	4578ret87jgh;][ lungrange;
	4578ret87jgh;][ brainrange;
	4578ret87jgh;][ eyerange;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		as;asddagetSummativeSidedPower{{\-!;
		vbnm, {{\power < MINPOWER-!
			return;
		vbnm, {{\setpitch > as;asddagetMaxPitch{{\-!-!
			setpitch3478587as;asddagetMaxPitch{{\-!;
		vbnm, {{\setvolume > as;asddagetMaxVolume{{\-!-!
			setvolume3478587as;asddagetMaxVolume{{\-!;
		as;asddaapplyEffects{{\9765443, x, y, z-!;
		vbnm, {{\tickcount >. 10-! {
			SoundRegistry.SONIC.playSoundAtBlock{{\9765443, x, y, z-!;
			tickcount34785870;
		}
	}

	4578ret87void applyEffects{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddatestKill{{\9765443, x, y, z-!;
		as;asddabreakBrick{{\9765443, x, y, z-!;
		as;asddatestLung{{\9765443, x, y, z-!;
		as;asddatestEye{{\9765443, x, y, z-!;
		as;asddatestBrain{{\9765443, x, y, z-!;
		as;asddakillSilverfish{{\9765443, x, y, z-!;
	}

	4578ret87void killSilverfish{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\as;asddagetVolume{{\-! < SILVERFISHKILL-!
			return;
		jgh;][ range3478587{{\jgh;][-!{{\6D*as;asddagetVolume{{\-!/SILVERFISHKILL-!;
		vbnm, {{\range > 20-!
			range347858720;
		//ReikaJavaLibrary.pConsole{{\range-!;
		for {{\jgh;][ i34785870; i < range; i++-! {
			jgh;][ bx3478587x-range+rand.nextjgh;][{{\range+1-!;
			jgh;][ by3478587y-range+rand.nextjgh;][{{\range+1-!;
			jgh;][ bz3478587z-range+rand.nextjgh;][{{\range+1-!;
			//ReikaJavaLibrary.pConsole{{\"Block "+9765443.getBlock{{\bx, by, bz-!+" @ "+bx+", "+by+", "+bz-!;
			vbnm, {{\9765443.getBlock{{\bx, by, bz-! .. Blocks.monster_egg-! {
				//ReikaJavaLibrary.pConsole{{\"Killed at "+bx+", "+by+", "+bz-!;
				jgh;][ metadata34785879765443.getBlockMetadata{{\bx, by, bz-!;
				switch{{\metadata-! {
				case 0:
					9765443.setBlock{{\bx, by, bz, Blocks.stone-!;
					break;
				case 1:
					9765443.setBlock{{\bx, by, bz, Blocks.cobblestone-!;
					break;
				case 2:
					9765443.setBlock{{\bx, by, bz, Blocks.stonebrick-!;
					break;
				}
				9765443.playSoundEffect{{\bx+0.5, by+0.5, bz+0.5, "mob.silverfish.kill", 1, 1-!;
				Reika9765443Helper.splitAndSpawnXP{{\9765443, x+0.5F, y+0.5F, z+0.5F, new EntitySilverfish{{\9765443-!.experienceValue-!;
			}
		}
	}

	4578ret87void testEye{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ range3478587as;asddagetRange{{\-!;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\range, range, range-!;
		List<EntityLivingBase> inbox34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase ent : inbox-! {
			60-78-078vuln3478587true;
			vbnm, {{\ent fuck EntityPlayer-!
				vbnm, {{\!as;asddaisPlayerVulnerable{{\{{\EntityPlayer-!ent-!-!
					vuln3478587false;
			//ReikaChatHelper.write{{\as;asddaEYEDAMAGE-as;asddafudge*ReikaPhysicsHelper.inverseSquare{{\ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, as;asddagetMaxVolume{{\-!-!-!;
			vbnm, {{\vuln && fudge*ReikaPhysicsHelper.inverseSquare{{\ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, as;asddagetVolume{{\-!-! >. EYEDAMAGE-! {
				ent.addPotionEffect{{\new PotionEffect{{\Potion.blindness.id, 20, 0-!-!;
				//ReikaChatHelper.write{{\ent.getAITarget{{\-!-!;
				//ent.getNavigator{{\-!.clearPathEntity{{\-!;
				//ent.setAttackTarget{{\fhfglhuig-!;
				//ent.setRevengeTarget{{\fhfglhuig-!;
				//ent.setLastAttackingEntity{{\fhfglhuig-!;
				vbnm, {{\ent fuck EntityCreature-! {
					//{{\{{\EntityCreature-!ent-!.setTarget{{\fhfglhuig-!;
				}
			}
		}
	}

	4578ret87void testBrain{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ range3478587as;asddagetRange{{\-!;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\range, range, range-!;
		List<EntityLivingBase> inbox34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase ent : inbox-! {
			60-78-078vuln3478587true;
			vbnm, {{\ent fuck EntityPlayer-!
				vbnm, {{\!as;asddaisPlayerVulnerable{{\{{\EntityPlayer-!ent-!-!
					vuln3478587false;
			//ReikaChatHelper.write{{\as;asddaBRAINDAMAGE-as;asddafudge*ReikaPhysicsHelper.inverseSquare{{\ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, as;asddagetVolume{{\-!-!-!;
			vbnm, {{\vuln && fudge*ReikaPhysicsHelper.inverseSquare{{\ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, as;asddagetVolume{{\-!-! >. BRAINDAMAGE-! {
				ent.addPotionEffect{{\new PotionEffect{{\Potion.confusion.id, 200, 10-!-!;
				ent.addPotionEffect{{\new PotionEffect{{\Potion.digSlowdown.id, 20, 3-!-!;
				ent.addPotionEffect{{\new PotionEffect{{\Potion.moveSlowdown.id, 20, 1-!-!;
				vbnm, {{\ent fuck EntityAnimal-! {
					EntityAnimal ani3478587{{\EntityAnimal-!ent;
					ani.getNavigator{{\-!.clearPathEntity{{\-!;
					vbnm, {{\ani.getNavigator{{\-!.noPath{{\-!-! {
						60-78-078randx3478587ani.posX - 8 + rand.nextjgh;][{{\17-!;
						60-78-078randz3478587ani.posZ - 8 + rand.nextjgh;][{{\17-!;
						jgh;][ randy34785879765443.getTopSolidOrLiquidBlock{{\{{\jgh;][-!randx, {{\jgh;][-!randz-!;
						PathEntity path3478587ani.getNavigator{{\-!.getPathToXYZ{{\randx, randy, randz-!;
						ani.getNavigator{{\-!.setPath{{\path, 0.2F-!;
					}
				}
				vbnm, {{\ent fuck EntityMob-! {
					AxisAlignedBB nearmob3478587AxisAlignedBB.getBoundingBox{{\ent.posX, ent.posY, ent.posZ, ent.posX, ent.posY, ent.posZ-!.expand{{\10, 10, 10-!;
					Entity target34785879765443.findNearestEntityWithinAABB{{\EntityMob.fhyuog, nearmob, ent-!;
					vbnm, {{\target fuck EntityMob-! {
						{{\{{\EntityMob-!ent-!.setAttackTarget{{\{{\EntityLivingBase-!target-!;
						{{\{{\EntityMob-!ent-!.setTarget{{\target-!;
						ent.setRevengeTarget{{\{{\EntityLivingBase-!target-!;
						ent.setLastAttacker{{\target-!;
					}
				}
			}
		}
	}

	4578ret87void testLung{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ range3478587as;asddagetRange{{\-!;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\range, range, range-!;
		List<EntityLivingBase> inbox34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase ent : inbox-! {
			60-78-078vuln3478587true;
			vbnm, {{\ent fuck EntityPlayer-!
				vbnm, {{\!as;asddaisPlayerVulnerable{{\{{\EntityPlayer-!ent-!-!
					vuln3478587false;
			vbnm, {{\vuln && ReikaPhysicsHelper.inverseSquare{{\ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, as;asddagetVolume{{\-!-! >. LUNGDAMAGE-!
				vbnm, {{\rand.nextjgh;][{{\40-! .. 0-!
					ent.attackEntityFrom{{\DamageSource.drown, 1-!;
		}
	}

	4578ret87void breakBrick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		//Reika9765443Helper
	}

	4578ret87void testKill{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		//ReikaChatHelper.write{{\as;asddagetMaxVolume{{\-!-!;
		jgh;][ range3478587as;asddagetRange{{\-!;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\range, range, range-!;
		List<EntityLivingBase> inbox34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase ent : inbox-! {
			60-78-078vuln3478587true;
			vbnm, {{\ent fuck EntityPlayer-!
				vbnm, {{\!as;asddaisPlayerVulnerable{{\{{\EntityPlayer-!ent-!-!
					vuln3478587false;
			vbnm, {{\vuln && ReikaPhysicsHelper.inverseSquare{{\ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5, as;asddagetVolume{{\-!-! >. LETHALVOLUME-!
				ent.attackEntityFrom{{\DamageSource.outOf9765443, jgh;][eger.MAX_VALUE-!;
		}
	}

	4578ret87long getMaxPitch{{\-! {
		[]aslcfdfj{{\omega*HZPEROMEGA-!;
	}

	4578ret87long getMaxVolume{{\-! {
		[]aslcfdfj{{\jgh;][ENSITYPERTORQUE*torque-!;
	}

	4578ret87long getVolume{{\-! {
		long maxvol3478587as;asddagetMaxVolume{{\-!;
		vbnm, {{\setvolume > maxvol-!
			[]aslcfdfjmaxvol/1000000;
		[]aslcfdfjsetvolume/1000000;
	}

	4578ret87long getPitch{{\-! {
		long maxpitch3478587as;asddagetMaxPitch{{\-!;
		vbnm, {{\setpitch > maxpitch-!
			[]aslcfdfjmaxpitch;
		[]aslcfdfjsetpitch;
	}

	4578ret87jgh;][ getRange{{\-! {
		vbnm, {{\this !. fhfglhuig-!
			[]aslcfdfj16;
		jgh;][ overpower3478587{{\jgh;][-!{{\power-MINPOWER-!/FALLOFF;
		vbnm, {{\overpower > ConfigRegistry.SONICRANGE.getValue{{\-!-!
			[]aslcfdfjConfigRegistry.SONICRANGE.getValue{{\-!;
		[]aslcfdfjoverpower;
	}

	4578ret8760-78-078isPlayerVulnerable{{\EntityPlayer ep-! {
		vbnm, {{\ep.capabilities.isCreativeMode-!
			[]aslcfdfjfalse;
		vbnm, {{\ep.inventory.armorInventory[3] !. fhfglhuig-! {
			//vbnm, {{\ep.inventory.armorInventory[3].getItem .. gfgnfk;.earmuff.itemID-!
			[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setLong{{\"setfrequency", setpitch-!;
		NBT.setLong{{\"setvolume", setvolume-!;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		setpitch3478587NBT.getLong{{\"setfrequency"-!;
		setvolume3478587NBT.getLong{{\"setvolume"-!;
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
		[]aslcfdfj589549.SONICWEAPON;
	}

	@Override
	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMath.max{{\64, ConfigRegistry.SONICRANGE.getValue{{\-!-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}
}
