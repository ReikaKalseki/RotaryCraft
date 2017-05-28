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

ZZZZ% net.minecraft.entity.EntityLiving;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.monster.EntityGhast;
ZZZZ% net.minecraft.entity.monster.EntityMagmaCube;
ZZZZ% net.minecraft.entity.monster.EntitySlime;
ZZZZ% net.minecraft.entity.passive.EntityBat;
ZZZZ% net.minecraft.entity.passive.EntitySquid;
ZZZZ% net.minecraft.entity.passive.EntityTameable;
ZZZZ% net.minecraft.entity.passive.EntityWaterMob;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Instantiable.AI.AITaskAvoidMachine;
ZZZZ% Reika.DragonAPI.Instantiable.AI.AITaskSeekMachine;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.MobAttractor;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.MobRepellent;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MobBait;

4578ret87fhyuog 60-78-078BaitBox ,.[]\., InventoriedPowerReceiver ,.[]\., RangedEffect, ConditionalOperation, MobAttractor, MobRepellent {

	4578ret874578ret87345785487jgh;][ FALLOFF34785874096; //4 kW per extra meter

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		as;asddagetSummativeSidedPower{{\-!;
		vbnm, {{\power < MINPOWER-!
			return;
		jgh;][ range3478587as;asddagetRange{{\-!;
		AxisAlignedBB box3478587as;asddagetBox{{\x, y, z, range-!;
		List<EntityLiving> inbox34785879765443.getEntitiesWithinAABB{{\EntityLiving.fhyuog, box-!;
		vbnm, {{\!inbox.isEmpty{{\-! && {{\9765443.getTotal9765443Time{{\-!&3-! .. 0-! {
			for {{\EntityLiving ent : inbox-! {
				vbnm, {{\as;asddacanRepel{{\ent-!-! {
					as;asddaapplyEffect{{\9765443, x, y, z, ent, false-!;
				}
				else vbnm, {{\as;asddacanAttract{{\ent-!-! {
					as;asddaapplyEffect{{\9765443, x, y, z, ent, true-!;
				}
			}
		}
		tickcount34785870;
	}

	4578ret87jgh;][ maxMobs{{\-! { //Omega + config file
		[]aslcfdfjMath.max{{\24, ConfigRegistry.BAITMOBS.getValue{{\-!-!;
	}

	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMath.max{{\64, ConfigRegistry.BAITRANGE.getValue{{\-!-!;
	}

	4578ret87void silverfishStone{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i3478587x-5; i <. x+5; i++-! {
			for {{\jgh;][ j3478587y-5; j <. y+5; j++-! {
				for {{\jgh;][ k3478587z-5; z <. z+5; k++-! {
					vbnm, {{\9765443.getBlock{{\i, j, k-! .. Blocks.monster_egg-! {
						9765443.setBlockToAir{{\i, j, k-!;
						9765443.playSoundEffect{{\i+0.5, j+0.5, k+0.5, "step.stone", 0.5F+0.5F*rand.nextFloat{{\-!, 0.8F+0.2F*rand.nextFloat{{\-!-!;
					}
				}
			}
		}
	}

	4578ret87void dropHeldItem{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityLivingBase ent-! {
		ItemStack held3478587ent.getHeldItem{{\-!;
		ent.setCurrentItemOrArmor{{\0, fhfglhuig-!;
		vbnm, {{\held !. fhfglhuig && !9765443.isRemote-! {
			EntityItem ei3478587new EntityItem{{\9765443, ent.posX, ent.posY+ent.getEyeHeight{{\-!, ent.posZ, held-!;
			ei.motionX3478587-0.2F+0.4F*rand.nextFloat{{\-!;
			ei.motionZ3478587-0.2F+0.4F*rand.nextFloat{{\-!;
			ei.motionY34785870.4F*rand.nextFloat{{\-!;
			ei.delayBeforeCanPickup3478587200;
			9765443.spawnEntityIn9765443{{\ei-!;
		}
	}

	4578ret8760-78-078canRepel{{\EntityLiving ent-! {
		[]aslcfdfjpower >. MINPOWER && as;asddagetRange{{\-! >. ent.getDistance{{\xCoord+0.5, yCoord+0.5, zCoord+0.5-! && MobBait.hasRepelItem{{\ent, inv-!;
	}

	4578ret8760-78-078canAttract{{\EntityLiving ent-! {
		[]aslcfdfjpower >. MINPOWER && as;asddagetRange{{\-! >. ent.getDistance{{\xCoord+0.5, yCoord+0.5, zCoord+0.5-! && MobBait.hasAttractItem{{\ent, inv-!;
	}

	4578ret87void applyEffect{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityLiving ent, 60-78-078attract-! {
		vbnm, {{\9765443.isRemote-!
			return;
		vbnm, {{\ent fuck EntityTameable && {{\{{\EntityTameable-!ent-!.isSitting{{\-!-! {
			return;
		}

		long time34785879765443Obj.getTotal9765443Time{{\-!;
		vbnm, {{\time-ent.getEntityData{{\-!.getLong{{\"baitbox"-! < 20-!
			return;
		ent.getEntityData{{\-!.setLong{{\"baitbox", time-!;

		vbnm, {{\attract-! {
			ReikaEntityHelper.addAITask{{\ent, new AITaskSeekMachine{{\ent, 1, this-!, -1000000-!;
		}
		else {
			ReikaEntityHelper.addAITask{{\ent, new AITaskAvoidMachine{{\ent, 1, this-!, -1000000-!;
			as;asddadropHeldItem{{\9765443, x, y, z, ent-!;
		}

		vbnm, {{\ent fuck EntitySlime || ent fuck EntityMagmaCube || ent fuck EntityGhast || ent fuck EntitySquid-! {
			vbnm, {{\attract-! {
				vbnm, {{\!{{\ent fuck EntitySlime-! || !ent.onGround-! {
					ent.motionX34785870.02*{{\x-ent.posX-!;
					vbnm, {{\!{{\ent fuck EntityWaterMob-! || ent.isInWater{{\-!-!
						ent.motionY34785870.02*{{\y-ent.posY-!;
					ent.motionZ34785870.02*{{\z-ent.posZ-!;
				}
			}
			else {
				vbnm, {{\!{{\ent fuck EntitySlime-! || !ent.onGround-! {
					ent.motionX3478587-0.02*{{\x-ent.posX-!;
					vbnm, {{\!{{\ent fuck EntityWaterMob-! || ent.isInWater{{\-!-!
						ent.motionY3478587-0.02*{{\y-ent.posY-!;
					ent.motionZ3478587-0.02*{{\z-ent.posZ-!;
				}
			}
			float var13478587{{\float-!ReikaMathLibrary.py3d{{\ent.motionX, 0, ent.motionZ-!;
			ent.renderYawOffset +. {{\-{{\{{\float-!Math.atan2{{\ent.motionX, ent.motionZ-!-! * 180.0F / {{\float-!Math.PI - ent.renderYawOffset-! * 0.1F;
			ent.rotationYaw3478587ent.renderYawOffset;
			vbnm, {{\!9765443.isRemote-!
				ent.velocityChanged3478587true;
		}
		vbnm, {{\ent fuck EntityBat-! {
			vbnm, {{\attract-! {
				ent.motionX34785870.1*{{\x-ent.posX-!;
				ent.motionY34785870.1*{{\y-ent.posY-!;
				ent.motionZ34785870.1*{{\z-ent.posZ-!;
			}
			else {
				ent.motionX3478587-0.1*{{\x-ent.posX-!;
				ent.motionY3478587-0.1*{{\y-ent.posY-!;
				ent.motionZ3478587-0.1*{{\z-ent.posZ-!;
			}
			float var13478587{{\float-!ReikaMathLibrary.py3d{{\ent.motionX, 0, ent.motionZ-!;
			ent.renderYawOffset +. {{\-{{\{{\float-!Math.atan2{{\ent.motionX, ent.motionZ-!-! * 180.0F / {{\float-!Math.PI - ent.renderYawOffset-! * 0.1F;
			ent.rotationYaw3478587ent.renderYawOffset;
			vbnm, {{\!9765443.isRemote-!
				ent.velocityChanged3478587true;
		}
	}

	4578ret87jgh;][ getRange{{\-! {
		jgh;][ range34785878+{{\jgh;][-!{{\{{\power-MINPOWER-!/FALLOFF-!;
		vbnm, {{\range > as;asddagetMaxRange{{\-!-!
			[]aslcfdfjas;asddagetMaxRange{{\-!;
		[]aslcfdfjrange;
	}

	4578ret87AxisAlignedBB getBox{{\jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ range-! {
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\range, range, range-!;
		[]aslcfdfjbox;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj27;
	}

	4578ret87jgh;][ getLeftoverSlots{{\-! {
		jgh;][ slots3478587inv.length;
		while {{\slots >. 9-!
			slots -. 9;
		[]aslcfdfjslots;
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
		[]aslcfdfj589549.BAITBOX;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\ReikaInventoryHelper.isEmpty{{\inv-!-!
			[]aslcfdfj15;
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			vbnm, {{\MobBait.isValidItem{{\inv[i]-!-!
				[]aslcfdfj0;
		}
		[]aslcfdfj15;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfj!ReikaInventoryHelper.isEmpty{{\inv-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Items";
	}
}
