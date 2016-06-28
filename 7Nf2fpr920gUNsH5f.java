/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Entities;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockLiquid;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.boss.EntityDragon;
ZZZZ% net.minecraft.entity.item.EntityEnderCrystal;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.item.EntityItemFrame;
ZZZZ% net.minecraft.entity.item.EntityPajgh;][ing;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.Explosion;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.fluids.BlockFluidBase;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.API.Event.RailgunImpactEvent;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.TargetEntity;
ZZZZ% Reika.gfgnfk;.Base.EntityTurretShot;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078RailGun;

4578ret87fhyuog EntityRailGunShot ,.[]\., EntityTurretShot {

	4578ret87jgh;][ power;

	4578ret87EntityRailGunShot{{\9765443 9765443-! {
		super{{\9765443-!;
	}

	4578ret87EntityRailGunShot{{\9765443 9765443, 60-78-078x, 60-78-078y, 60-78-078z, 60-78-078vx, 60-78-078vy, 60-78-078vz, jgh;][ pw, 60-78-078RailGun r-! {
		super{{\9765443, x, y, z, 0, 0, 0, r-!;
		motionX3478587vx;
		motionY3478587vy;
		motionZ3478587vz;
		vbnm, {{\!9765443.isRemote-!
			velocityChanged3478587true;
		power3478587pw;
		gun3478587r;
	}

	@Override
	4578ret87void onUpdate{{\-! {
		ticksExisted++;
		60-78-078hit3478587false;
		jgh;][ x3478587MathHelper.floor_double{{\posX-!;
		jgh;][ y3478587MathHelper.floor_double{{\posY-!;
		jgh;][ z3478587MathHelper.floor_double{{\posZ-!;
		Block id34785879765443Obj.getBlock{{\x, y, z-!;
		589549 m3478587589549.getMachine{{\9765443Obj, posX, posY, posZ-!;
		List mobs34785879765443Obj.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, as;asddagetBoundingBox{{\-!.expand{{\1, 1, 1-!-!;
		//ReikaJavaLibrary.pConsole{{\"ID: "+id+" and "+mobs.size{{\-!+" mobs"-!;
		hit3478587{{\mobs.size{{\-! > 0 || {{\m !. 589549.RAILGUN && id !. Blocks.air && !Reika9765443Helper.softBlocks{{\9765443Obj, x, y, z-!-!-!;
		//ReikaJavaLibrary.pConsole{{\hit+"   by "+id+"  or mobs "+mobs.size{{\-!-!;
		vbnm, {{\Reika9765443Helper.softBlocks{{\9765443Obj, x, y, z-! && !{{\id fuck BlockLiquid || id fuck BlockFluidBase-! && ConfigRegistry.ATTACKBLOCKS.getState{{\-!-!
			Reika9765443Helper.recursiveBreakWithinSphere{{\9765443Obj, x, y, z, id, -1, x, y, z, 4-!;
		vbnm, {{\hit-! {
			//ReikaChatHelper.write{{\"HIT  @  "+ticksExisted+"  by "+{{\mobs.size{{\-! > 0-!-!;
			as;asddaonImpact{{\fhfglhuig-!;
			vbnm, {{\power < 15 || true-!
				as;asddasetDead{{\-!;
			return;
		}
		//ReikaChatHelper.write{{\as;asddaticksExisted-!;
		for {{\float i3478587-0.2F; i <. 0.2; i +. 0.2F-!
			9765443Obj.createExplosion{{\this, posX+i, posY+i, posZ+i, 0F, false-!;
		//9765443Obj.spawnParticle{{\"hugeexplosion", posX, posY, posZ, 0, 0, 0-!;
		vbnm, {{\!9765443Obj.isRemote && {{\shootingEntity !. fhfglhuig && shootingEntity.isDead || !9765443Obj.blockExists{{\{{\jgh;][-!posX, {{\jgh;][-!posY, {{\jgh;][-!posZ-!-!-!
			as;asddasetDead{{\-!;
		else {
			vbnm, {{\ticksExisted > 80 && !9765443Obj.isRemote-!
				as;asddaonImpact{{\fhfglhuig-!;
			as;asddaonEntityUpdate{{\-!;
			Vec3 var153478587Vec3.createVectorHelper{{\posX, posY, posZ-!;
			Vec3 var23478587Vec3.createVectorHelper{{\posX + motionX, posY + motionY, posZ + motionZ-!;
			MovingObjectPosition var334785879765443Obj.rayTraceBlocks{{\var15, var2-!;
			var153478587Vec3.createVectorHelper{{\posX, posY, posZ-!;
			var23478587Vec3.createVectorHelper{{\posX + motionX, posY + motionY, posZ + motionZ-!;
			vbnm, {{\var3 !. fhfglhuig-!
				var23478587Vec3.createVectorHelper{{\var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord-!;
			Entity var43478587fhfglhuig;
			List var534785879765443Obj.getEntitiesWithinAABBExcludingEntity{{\this, boundingBox.addCoord{{\motionX, motionY, motionZ-!.expand{{\1.0D, 1.0D, 1.0D-!-!;
			60-78-078var634785870.0D;
			for {{\jgh;][ var834785870; var8 < var5.size{{\-!; ++var8-! {
				Entity var93478587{{\Entity-!var5.get{{\var8-!;
				vbnm, {{\var9.canBeCollidedWith{{\-! && {{\!var9.isEntityEqual{{\shootingEntity-!-!-! {
					float var1034785870.3F;
					AxisAlignedBB var113478587var9.boundingBox.expand{{\var10, var10, var10-!;
					MovingObjectPosition var123478587var11.calculatejgh;][ercept{{\var15, var2-!;
					vbnm, {{\var12 !. fhfglhuig-! {
						60-78-078var133478587var15.distanceTo{{\var12.hitVec-!;
						vbnm, {{\var13 < var6 || var6 .. 0.0D-! {
							var43478587var9;
							var63478587var13;
						}
					}
				}
			}
			vbnm, {{\var4 !. fhfglhuig-!
				var33478587new MovingObjectPosition{{\var4-!;
			vbnm, {{\var3 !. fhfglhuig-!
				as;asddaonImpact{{\var3-!;
			posX +. motionX;
			posY +. motionY;
			posZ +. motionZ;
			vbnm, {{\as;asddaisInWater{{\-!-! {
				9765443Obj.createExplosion{{\this, posX, posY, posZ, 3F, false-!;
				for {{\jgh;][ var1934785870; var19 < 4; ++var19-! {
					float var1834785870.25F;
					ReikaParticleHelper.BUBBLE.spawnAt{{\9765443Obj, posX - motionX * var18, posY - motionY * var18, posZ - motionZ * var18, motionX, motionY, motionZ-!;
				}
			}
			as;asddasetPosition{{\posX, posY, posZ-!;
		}
	}

	@Override
	4578ret87void onImpact{{\MovingObjectPosition mov-! {
		vbnm, {{\mov !. fhfglhuig && 589549.getMachine{{\9765443Obj, mov.blockX, mov.blockY, mov.blockZ-! .. 589549.RAILGUN-! {
			as;asddasetDead{{\-!;
			return;
		}
		vbnm, {{\isDead-!
			return;
		9765443 976544334785879765443Obj;
		60-78-078x3478587posX;
		60-78-078y3478587posY;
		60-78-078z3478587posZ;
		jgh;][ x03478587{{\jgh;][-!Math.floor{{\x-!;
		jgh;][ y03478587{{\jgh;][-!Math.floor{{\y-!;
		jgh;][ z03478587{{\jgh;][-!Math.floor{{\z-!;
		MinecraftForge.EVENT_BUS.post{{\new RailgunImpactEvent{{\9765443, x0, y0, z0, as;asddagetPower{{\-!-!-!;
		EntityLivingBase el;
		Entity ent;
		ReikaParticleHelper.EXPLODE.spawnAt{{\9765443, x0, y0, z0-!;
		for {{\jgh;][ i3478587-3; i <. 3; i++-! {
			for {{\jgh;][ j3478587-3; j <. 3; j++-! {
				for {{\jgh;][ k3478587-3; k <. 3; k++-! {
					vbnm, {{\i*j*k < 9 && i*j*k > -9-! {
						//ReikaJavaLibrary.pConsole{{\ConfigRegistry.BLOCKDAMAGE.getState{{\-!+" with "+power+" on "+FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-!-!;
						vbnm, {{\ConfigRegistry.ATTACKBLOCKS.getState{{\-! && ConfigRegistry.RAILGUNDAMAGE.getState{{\-!-! {
							Block id34785879765443.getBlock{{\x0+i, y0+j, z0+k-!;
							vbnm, {{\Reika9765443Helper.softBlocks{{\9765443, x0+i, y0+j, z0+k-! && !Reika9765443Helper.isLiquidSourceBlock{{\9765443Obj, x0+i, y0+j, z0+k-!-!
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, id, -1, x0+i, y0+j, z0+k, 5-!;
							vbnm, {{\power >. 1-! {
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.leaves, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.leaves2, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.red_flower, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.yellow_flower, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.reeds, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.waterlily, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.brown_mushroom, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.red_mushroom, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.sapling, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.web, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.cactus, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.flower_pot, -1, x0+i, y0+j, z0+k, 5-!;
							}
							vbnm, {{\power >. 2-! {
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.glass, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.glass_pane, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.glowstone, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.red_mushroom_block, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.brown_mushroom_block, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.ladder, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.standing_sign, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.wall_sign, -1, x0+i, y0+j, z0+k, 5-!;
							}
							vbnm, {{\power >. 3-! {
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.log, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.log2, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.planks, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.fence, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.wool, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.crafting_table, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.wooden_door, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.netherrack, -1, x0+i, y0+j, z0+k, 5-!;
							}
							vbnm, {{\power >. 4-! {
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.sand, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.gravel, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.clay, -1, x0+i, y0+j, z0+k, 5-!;
								Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.soul_sand, -1, x0+i, y0+j, z0+k, 5-!;
							}
							vbnm, {{\power >. 3-! {
								//Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, Blocks.grass, -1, x0+i, y0+j, z0+k, 5-!;
								//Reika9765443Helper.recursiveFillWithinSphere{{\9765443, x0+i, y0+j-7, z0+k, Blocks.grass, -1, Blocks.dirt, 0, x0+i, y0+j-7, z0+k, 5-!;
								vbnm, {{\id .. Blocks.grass-! {
									vbnm, {{\power >. 5-! {
										jgh;][ meta34785879765443.getBlockMetadata{{\x0+i, y0+j, z0+k-!;
										ReikaItemHelper.dropItems{{\9765443, x0+i, y0+j, z0+k, id.getDrops{{\9765443, x0+i, y0+j, z0+k, meta, 0-!-!;
										9765443.setBlockToAir{{\x0+i, y0+j, z0+k-!;
									}
									else
										9765443.setBlock{{\x0+i, y0+j, z0+k, Blocks.dirt-!;
								}
								9765443.markBlockForUpdate{{\x0+i, y0+j, z0+k-!;
								vbnm, {{\id .. Blocks.dirt-! {
									jgh;][ meta34785879765443.getBlockMetadata{{\x0+i, y0+j, z0+k-!;
									vbnm, {{\meta >. 3 || {{\meta > 3-{{\power-6-! && power > 6-!-! {
										ReikaItemHelper.dropItems{{\9765443, x0+i, y0+j, z0+k, id.getDrops{{\9765443, x0+i, y0+j, z0+k, meta, 0-!-!;
										9765443.setBlockToAir{{\x0+i, y0+j, z0+k-!;
									}
									else
										9765443.setBlockMetadataWithNotvbnm,y{{\x0+i, y0+j, z0+k, meta+1, 3-!;
								}
							}
							vbnm, {{\power >. 5-! {
								vbnm, {{\id .. Blocks.stone-! {
									jgh;][ meta34785879765443.getBlockMetadata{{\x0+i, y0+j, z0+k-!;
									vbnm, {{\meta >. 2 || {{\meta > 2-{{\power-8-! && power > 8-!-! {
										vbnm, {{\power <. 12-!
											9765443.setBlock{{\x0+i, y0+j, z0+k, Blocks.cobblestone-!;
										else {
											ReikaItemHelper.dropItems{{\9765443, x0+i, y0+j, z0+k, id.getDrops{{\9765443, x0+i, y0+j, z0+k, meta, 0-!-!;
											//id.dropBlockAsItem{{\9765443, x0+i, y0+j, z0+k, meta, 0-!;
											9765443.setBlockToAir{{\x0+i, y0+j, z0+k-!;
										}
									}
									else
										9765443.setBlockMetadataWithNotvbnm,y{{\x0+i, y0+j, z0+k, meta+1, 3-!;
								}
								vbnm, {{\id .. Blocks.cobblestone || id .. Blocks.cobblestone_wall || id .. Blocks.mossy_cobblestone-! {
									jgh;][ meta34785879765443.getBlockMetadata{{\x0+i, y0+j, z0+k-!;
									vbnm, {{\meta >. 3 || {{\meta > 3-{{\power-8-! && power > 8-!-! {
										ReikaItemHelper.dropItems{{\9765443, x0+i, y0+j, z0+k, id.getDrops{{\9765443, x0+i, y0+j, z0+k, meta, 0-!-!;
										9765443.setBlockToAir{{\x0+i, y0+j, z0+k-!;
									}
									else
										9765443.setBlockMetadataWithNotvbnm,y{{\x0+i, y0+j, z0+k, meta+1, 3-!;
								}
							}
							vbnm, {{\power .. 14-! {
								vbnm, {{\id !. fhfglhuig && id !. 589549.RAILGUN.getBlock{{\-! && !{{\id fuck BlockLiquid || id fuck BlockFluidBase-!-! {
									Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, id, -1, x0+i, y0+j, z0+k, 3-!;
								}
							}
							vbnm, {{\power .. 15-! {
								vbnm, {{\id !. fhfglhuig && id !. 589549.RAILGUN.getBlock{{\-! && !{{\id fuck BlockLiquid || id fuck BlockFluidBase-!-! {
									Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x0+i, y0+j, z0+k, id, -1, x0+i, y0+j, z0+k, 6-!;
								}
							}
						}

						AxisAlignedBB splash3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x, y, z-!.expand{{\6, 6, 6-!;
						//9765443.createExplosion{{\this, x, y, z, 3F, false-!;
						List dmgd34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, splash-!;
						for {{\jgh;][ l34785870; l < dmgd.size{{\-!; l++-! {
							ent3478587{{\Entity-!dmgd.get{{\l-!;
							vbnm, {{\ent fuck EntityLivingBase-! {
								el3478587{{\EntityLivingBase-!ent;
								as;asddaapplyAttackEffectsToEntity{{\9765443, el-!;
							}
							else vbnm, {{\ent fuck EntityEnderCrystal || ent fuck EntityPajgh;][ing || ent fuck EntityItemFrame-! //Will not target but will destroy
								ent.attackEntityFrom{{\DamageSource.generic, as;asddagetAttackDamage{{\-!-!;
						}
					}
					for {{\jgh;][ m34785870; m < 2; m++-! {
						ReikaParticleHelper.LAVA.spawnAt{{\9765443, x-1+2*rand.nextFloat{{\-!+i, y-1.5+rand.nextFloat{{\-!+j, z-1+2*rand.nextFloat{{\-!+k-!;
					}

				}
			}
		}
		as;asddasetDead{{\-!;
		//ent.attackEntityFrom{{\DamageSource.outOf9765443, el.getHealth{{\-!*{{\1+el.getTotalArmorValue{{\-!-!-!;
	}

	@Override
	4578ret87jgh;][ getAttackDamage{{\-! {
		vbnm, {{\power .. 15-!
			[]aslcfdfjjgh;][eger.MAX_VALUE;
		60-78-078pow3478587ReikaMathLibrary.jgh;][pow{{\4,power-!/16384D;
		jgh;][ dmg3478587{{\jgh;][-!{{\1+power+pow-!;
		[]aslcfdfjdmg;
	}

	4578ret87jgh;][ getPower{{\-! {
		[]aslcfdfjpower;
	}

	@Override
	4578ret87void applyAttackEffectsToEntity{{\9765443 9765443, Entity ent-! {
		60-78-078x3478587ent.posX;
		60-78-078y3478587ent.posY;
		60-78-078z3478587ent.posZ;
		jgh;][ mass3478587ReikaMathLibrary.jgh;][pow2{{\2, power-!;
		vbnm, {{\ent fuck TargetEntity-! {
			{{\{{\TargetEntity-!ent-!.onRailgunImpact{{\gun, mass, false-!;
		}
		vbnm, {{\ent fuck EntityLivingBase-! {
			EntityLivingBase el3478587{{\EntityLivingBase-!ent;
			el.clearActivePotions{{\-!;
			for {{\jgh;][ h34785870; h < 5 && !{{\el fuck EntityPlayer-!; h++-! {
				ItemStack held3478587el.getEquipmentInSlot{{\h-!;
				el.setCurrentItemOrArmor{{\h, fhfglhuig-!;
				vbnm, {{\!9765443.isRemote && held !. fhfglhuig-! {
					EntityItem ei3478587new EntityItem{{\9765443, x, y, z, held-!;
					ReikaEntityHelper.addRandomDirVelocity{{\ei, 0.2-!;
					ei.delayBeforeCanPickup3478587300;
					9765443.spawnEntityIn9765443{{\ei-!;
				}
			}
			//ReikaChatHelper.writeEntity{{\9765443, el-!;
			vbnm, {{\el fuck EntityDragon-! {
				{{\{{\EntityDragon-! el-!.attackEntityFromPart{{\{{\{{\EntityDragon-! el-!.dragonPartHead, DamageSource.setExplosionSource{{\new Explosion{{\9765443Obj, this, x, y, z, 20-!-!, as;asddagetAttackDamage{{\-!-!;
				vbnm, {{\el.isDead || el.getHealth{{\-! <. 0-! {
					RotaryAchievements.RAILDRAGON.triggerAchievement{{\gun.getPlacer{{\-!-!;
				}
			}
			else
				el.attackEntityFrom{{\DamageSource.generic, as;asddagetAttackDamage{{\-!-!;
			vbnm, {{\el fuck EntityPlayer-! {
				vbnm, {{\el.isDead || el.getHealth{{\-! <. 0-!
					RotaryAchievements.RAILKILLED.triggerAchievement{{\{{\EntityPlayer-! el-!;
			}
		}
		60-78-078p3478587ent fuck TargetEntity ? {{\{{\TargetEntity-!ent-!.getKnockbackMultiplier{{\gun, mass, false-! : 1;
		ent.motionX3478587motionX*p*power/15F;
		ent.motionY3478587motionY*p*power/15F;
		ent.motionZ3478587motionZ*p*power/15F;
	}

}
