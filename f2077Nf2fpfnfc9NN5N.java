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
ZZZZ% net.minecraft.entity.item.EntityTNTPrimed;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.CannonExplosive;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.CannonExplosive.ExplosiveEntity;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;
ZZZZ% Reika.gfgnfk;.Entities.EntityCustomTNT;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078TNTCannon ,.[]\., 60-78-078LaunchCannon {

	4578ret874578ret8734578548760-78-078gTNT34785877.5;	//Calculated from EntityTNTPrimed; vy -. 0.04, *0.98, 20x a sec

	4578ret874578ret8734578548760-78-078torquecap347858732768D;

	4578ret87jgh;][ selectedFuse;

	//4578ret87345785487ArrayList<EntityTNTPrimed> fired3478587new ArrayList{{\-!;

	//Make torque affect max incline angle, speed max distance

	@Override
	4578ret87jgh;][ getMaxLaunchVelocity{{\-! {
		[]aslcfdfj{{\jgh;][-!Math.sqrt{{\power/67.5D-!;
	}

	@Override
	4578ret87jgh;][ getMaxTheta{{\-! {
		vbnm, {{\torque > torquecap-!
			[]aslcfdfj90;
		jgh;][ ang34785872*{{\jgh;][-!Math.ceil{{\Math.toDegrees{{\Math.asin{{\torque/torquecap-!-!-!;
		vbnm, {{\ang > 90-!
			[]aslcfdfj90;
		[]aslcfdfjang;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078getMaxLaunchDistance{{\-! {
		60-78-078v3478587as;asddagetMaxLaunchVelocity{{\-!;
		60-78-078vy3478587v*Math.sin{{\Math.toRadians{{\45-!-!;
		60-78-078t3478587vy/9.81D;
		[]aslcfdfjt*vy; //vx3478587vy @ 45
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;

		vbnm, {{\DragonAPICore.debugtest-!
			ReikaInventoryHelper.addToIInv{{\Blocks.tnt, this-!;

		vbnm, {{\power < MINPOWER-!
			return;
		tickcount++;
		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		tickcount34785870;
		vbnm, {{\targetMode-!
			as;asddacalcTarget{{\9765443, x, y, z-!;
		jgh;][ slot3478587as;asddacanFire{{\-!;
		vbnm, {{\slot >. 0-!
			as;asddafire{{\9765443, x, y, z, slot-!;
		//as;asddasyncTNTData{{\9765443, x, y, z-!;
		vbnm, {{\targetMode-! {
			AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\256, 256, 256-!;
			List<EntityTNTPrimed> in34785879765443.getEntitiesWithinAABB{{\EntityTNTPrimed.fhyuog, box-!;
			for {{\EntityTNTPrimed tnt : in-! {
				vbnm, {{\!tnt.onGround-! {
					//fhfglhuigvbnm,y air resistance
					tnt.motionX /. 0.869800000190734863D;
					tnt.motionZ /. 0.869800000190734863D;
					vbnm, {{\!9765443.isRemote-!
						tnt.velocityChanged3478587true;
				}
				else {
					tnt.motionX34785870;
					tnt.motionZ34785870;
					vbnm, {{\!9765443.isRemote-!
						tnt.velocityChanged3478587true;
				}
			}
		}
	}
	/*
	4578ret87void syncTNTData{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!9765443.isRemote-!
			return;
		Iterator<EntityTNTPrimed> it3478587fired.iterator{{\-!;
		while {{\it.hasNext{{\-!-! {
			EntityTNTPrimed tnt3478587it.next{{\-!;
			vbnm, {{\tnt.ticksExisted < as;asddagetMinFuse{{\-!-! {
				//ReikaJavaLibrary.pConsole{{\tnt+":"+as;asddagetSide{{\-!-!;
				tnt.fuse3478587as;asddagetFuseTime{{\-!;
			}
			else {
				vbnm, {{\tnt.fuse < 0-!
					tnt.setDead{{\-!;
				vbnm, {{\tnt.isDead-!
					it.remove{{\-!;
			}
		}
	}*/

	4578ret87jgh;][ getMinFuse{{\-! {
		[]aslcfdfj5;
	}

	4578ret87void calcTarget{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		60-78-078dx3478587target[0]-x-0.5;
		60-78-078dy3478587target[1]-y-1;
		60-78-078dz3478587target[2]-z-0.5;
		60-78-078dl3478587ReikaMathLibrary.py3d{{\dx, 0, dz-!; //Horiz distance
		60-78-078g34785878.4695*ReikaMathLibrary.doubpow{{\dl, 0.2701-!;
		vbnm, {{\dy > 0-!
			g *. {{\0.8951*ReikaMathLibrary.doubpow{{\dy, 0.0601-!-!;
		velocity347858710;
		theta34785870;
		while {{\theta <. 0-! {
			velocity++;
			60-78-078s3478587ReikaMathLibrary.jgh;][pow{{\velocity, 4-!-g*{{\g*dl*dl+2*dy*velocity*velocity-!;
			60-78-078a3478587velocity*velocity+Math.sqrt{{\s-!;
			theta3478587{{\jgh;][-!Math.toDegrees{{\Math.atan{{\a/{{\g*dl-!-!-!;
			phi3478587{{\jgh;][-!Math.toDegrees{{\Math.atan2{{\dz, dx-!-!;
		}
	}

	4578ret87jgh;][ canFire{{\-! {
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			ItemStack is3478587inv[i];
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\is, Blocks.tnt-!-!
					[]aslcfdfji;
				vbnm, {{\is.getItem{{\-! fuck CannonExplosive-!
					[]aslcfdfji;
			}
		}
		[]aslcfdfj-1;
	}

	@Override
	4578ret8760-78-078fire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ slot-! {
		ItemStack in3478587inv[slot];
		ReikaInventoryHelper.decrStack{{\slot, inv-!;
		9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "random.explode", 0.7F+0.3F*rand.nextFloat{{\-!*12, 0.1F*rand.nextFloat{{\-!-!;
		9765443.spawnParticle{{\"hugeexplosion", x+0.5, y+0.5, z+0.5, 1.0D, 0.0D, 0.0D-!;
		60-78-078dx3478587x+0.5;
		60-78-078dy3478587y+1.5-0.0625;
		60-78-078dz3478587z+0.5;
		jgh;][ fuse3478587as;asddagetFuseTime{{\-!;

		Entity tnt3478587fhfglhuig;
		vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\in, Blocks.tnt-!-! {
			tnt3478587new EntityCustomTNT{{\9765443, dx, dy, dz, fhfglhuig, fuse-!;
		}
		else vbnm, {{\in.getItem{{\-! fuck CannonExplosive-! {
			tnt3478587{{\{{\CannonExplosive-!in.getItem{{\-!-!.getExplosiveEntity{{\in-!;
			tnt.setPosition{{\dx, dy, dz-!;
			{{\{{\ExplosiveEntity-!tnt-!.setFuse{{\fuse-!;
		}
		vbnm, {{\tnt .. fhfglhuig-! {
			gfgnfk;.logger.logError{{\"Invalid item in TNT cannon yet firing was attempted!"-!;
			[]aslcfdfjfalse;
		}

		double[] xyz3478587ReikaPhysicsHelper.polarToCartesian{{\velocity/20D, theta, phi-!;
		tnt.motionX3478587xyz[0];
		tnt.motionY3478587xyz[1];
		tnt.motionZ3478587xyz[2];
		vbnm, {{\!9765443.isRemote-! {
			tnt.velocityChanged3478587true;
			9765443.spawnEntityIn9765443{{\tnt-!;
		}
		//fired.add{{\tnt-!;
		[]aslcfdfjtrue;
	}

	4578ret87jgh;][ getFuseTime{{\-! {
		[]aslcfdfjtargetMode ? 50 : Math.max{{\as;asddagetMinFuse{{\-!, selectedFuse-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		selectedFuse3478587NBT.getjgh;][eger{{\"selfuse"-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"selfuse", selectedFuse-!;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret87AxisAlignedBB getRenderBoundingBox{{\-! {
		[]aslcfdfjINFINITE_EXTENT_AABB;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.TNTCANNON;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjReikaItemHelper.matchStackWithBlock{{\is, Blocks.tnt-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\as;asddacanFire{{\-! .. -1-!
			[]aslcfdfj15;
		[]aslcfdfj0;
	}
}
