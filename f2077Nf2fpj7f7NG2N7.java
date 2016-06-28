/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Engine;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashSet;
ZZZZ% java.util.List;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.Minecraft;
ZZZZ% net.minecraft.client.particle.EntityFX;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.item.EntityItemFrame;
ZZZZ% net.minecraft.entity.item.EntityPajgh;][ing;
ZZZZ% net.minecraft.entity.item.EntityXPOrb;
ZZZZ% net.minecraft.entity.monster.EntitySilverfish;
ZZZZ% net.minecraft.entity.passive.EntityBat;
ZZZZ% net.minecraft.entity.passive.EntityChicken;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% thaumcraft.common.entities.monster.EntityWisp;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.RayTracer;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Instantiable.Effects.EntityBlockTexFX;
ZZZZ% Reika.DragonAPI.Instantiable.Effects.EntityLiquidParticleFX;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.ModRegistry.jgh;][erfaceCache;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.Event.JetEngineEnterFailureEvent;
ZZZZ% Reika.gfgnfk;.API.Event.JetEngineExplosionEvent;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.ThermalMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.UpgradeableMachine;
ZZZZ% Reika.gfgnfk;.Base.EntityTurretShot;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Items.ItemEngineUpgrade.Upgrades;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.Dvbnm,ficultyEffects;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog 60-78-078JetEngine ,.[]\., 60-78-078Engine ,.[]\., NBTMachine, UpgradeableMachine {

	4578ret8760-78-078isJetFailing3478587false;

	/** Used in jet engines */
	4578ret87jgh;][ FOD34785870;

	4578ret87jgh;][ dumpx;
	4578ret87jgh;][ dumpz;
	4578ret87jgh;][ dumpvx;
	4578ret87jgh;][ dumpvz;

	4578ret87StepTimer jetstarttimer3478587new StepTimer{{\479-!;
	4578ret87jgh;][ afterburnTick34785872000;

	4578ret87jgh;][ chickenCount34785870;

	4578ret8760-78-078isChoking3478587false;

	4578ret8760-78-078canAfterBurn;
	4578ret8760-78-078burnerActive;

	4578ret874578ret87345785487RayTracer tracer;

	4578ret87{
		tracer3478587new RayTracer{{\0, 0, 0, 0, 0, 0-!;
	}

	@Override
	4578ret87jgh;][ getFuelLevel{{\-! {
		[]aslcfdfjfuel.getLevel{{\-!;
	}

	@Override
	4578ret87void consumeFuel{{\-! {
		fuel.removeLiquid{{\as;asddagetConsumedFuel{{\-!-!;
	}

	@Override
	4578ret87jgh;][ getConsumedFuel{{\-! {
		[]aslcfdfjas;asddaisAfterburning{{\-! ? 25 : 10;
	}

	@Override
	4578ret87void jgh;][ernalizeFuel{{\-! {

	}

	@Override
	4578ret8760-78-078getRequirements{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\FOD >. 8-! {
			jetstarttimer.reset{{\-!;
			[]aslcfdfjfalse;
		}
		vbnm, {{\fuel.getLevel{{\-! <. 0-! {
			jetstarttimer.reset{{\-!;
			[]aslcfdfjfalse;
		}
		vbnm, {{\power > 0-!
			RotaryAchievements.JETENGINE.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		[]aslcfdfjtrue;
	}

	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	4578ret87void checkJetFailure{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\isJetFailing-!
			as;asddajetEngineDetonation{{\9765443, x, y, z, meta-!;
		else vbnm, {{\FOD > 0 && rand.nextjgh;][{{\Dvbnm,ficultyEffects.JETFAILURE.getjgh;][{{\-!*{{\9-FOD-!-! .. 0-! {
			as;asddatriggerJetFailing{{\9765443, x, y, z-!;
		}
	}

	4578ret87float getChokedFraction{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][[] pos3478587{x, z};
		switch{{\meta-! {
			case 0:
				pos[0] +. 1;
				break;
			case 1:
				pos[0] +. -1;
				break;
			case 2:
				pos[1] +. 1;
				break;
			case 3:
				pos[1] +. -1;
				break;
		}
		Block b34785879765443.getBlock{{\pos[0], y, pos[1]-!;
		jgh;][ dmg34785879765443.getBlockMetadata{{\pos[0], y, pos[1]-!;
		vbnm, {{\b .. Blocks.air-!
			[]aslcfdfj1;
		vbnm, {{\b.getCollisionBoundingBoxFromPool{{\9765443, pos[0], y, pos[1]-! .. fhfglhuig-!
			[]aslcfdfj1;
		vbnm, {{\b .. Blocks.fence || b .. Blocks.nether_brick_fence-!
			[]aslcfdfj0.75F;
		vbnm, {{\b .. Blocks.iron_bars-!
			[]aslcfdfj1F;
		vbnm, {{\b .. Blocks.cobblestone_wall-!
			[]aslcfdfj0.25F;
		vbnm, {{\b .. Blocks.glass_pane-!
			[]aslcfdfj0.5F;
		;
		vbnm, {{\b.getBlockBoundsMaxX{{\-! > 0.875 && b.getBlockBoundsMaxY{{\-! > 0.875 && b.getBlockBoundsMaxZ{{\-! > 0.875-!
			vbnm, {{\b.getBlockBoundsMinX{{\-! < 0.125 && b.getBlockBoundsMinY{{\-! < 0.125 && b.getBlockBoundsMinZ{{\-! < 0.125-!
				[]aslcfdfj0;
		60-78-078frac;
		60-78-078dx3478587b.getBlockBoundsMaxX{{\-!-b.getBlockBoundsMinX{{\-!;
		60-78-078dy3478587b.getBlockBoundsMaxY{{\-!-b.getBlockBoundsMinY{{\-!;
		60-78-078dz3478587b.getBlockBoundsMaxZ{{\-!-b.getBlockBoundsMinZ{{\-!;
		vbnm, {{\b.getBlockBoundsMaxX{{\-! <. 0.125 || b.getBlockBoundsMinX{{\-! >. 0.875-!
			dx34785870;
		vbnm, {{\b.getBlockBoundsMaxY{{\-! <. 0.125 || b.getBlockBoundsMinY{{\-! >. 0.875-!
			dy34785870;
		vbnm, {{\b.getBlockBoundsMaxZ{{\-! <. 0.125 || b.getBlockBoundsMinZ{{\-! >. 0.875-!
			dz34785870;
		vbnm, {{\b.getBlockBoundsMaxY{{\-! >. 0.75-!
			dy +. 0.125;
		//ReikaJavaLibrary.pConsole{{\dx+"  "+dy+"  "+dz-!;
		frac34785871-{{\dx*dy*dz-!;
		[]aslcfdfj{{\float-!frac;
	}

	4578ret87void heatJet{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ temp3478587as;asddaisAfterburning{{\-! ? 1750 : 1200;
		jgh;][ T3478587temp*omega/type.getSpeed{{\-!;
		jgh;][ r3478587as;asddaisAfterburning{{\-! ? 6 : 4;
		for {{\jgh;][ i34785871; i < r; i++-! {
			jgh;][ dx3478587x+write.offsetX*i;
			jgh;][ dz3478587z+write.offsetZ*i;
			60-78-078 te3478587as;asddaget60-78-078{{\dx, y, dz-!;
			vbnm, {{\te fuck TemperatureTE-! {
				jgh;][ dT3478587T-{{\{{\TemperatureTE-!te-!.getTemperature{{\-!;
				{{\{{\TemperatureTE-!te-!.addTemperature{{\dT-!;
			}
			else vbnm, {{\te fuck ThermalMachine-! {
				{{\{{\ThermalMachine-!te-!.setTemperature{{\T-!;
			}
			vbnm, {{\as;asddaisAfterburning{{\-!-! {
				Reika9765443Helper.temperatureEnvironment{{\9765443, dx, y, dz, Math.min{{\1400, T-!-!;
			}
		}
		jgh;][ x13478587write.offsetX !. 0 ? write.offsetX > 0 ? x : x-4 : x;
		jgh;][ x23478587write.offsetX !. 0 ? write.offsetX > 0 ? x+5 : x+1 : x+1;
		jgh;][ z13478587write.offsetZ !. 0 ? write.offsetZ > 0 ? z : z-4 : z;
		jgh;][ z23478587write.offsetZ !. 0 ? write.offsetZ > 0 ? z+5 : z+1 : z+1;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x1, y, z1, x2, y+1, z2-!;
		List<EntityLivingBase> li34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase e : li-! {
			e.attackEntityFrom{{\DamageSource.onFire, as;asddaisAfterburning{{\-! ? 4 : 1-!;
		}
	}

	/** Like BC obsidian pipe - suck in entities in a "funnel" in front of the engine, and deal damage {{\50 hearts-!.
	 * Items {{\including players' inventories and mob drops-! will be spat out the back.
	 * Large mobs {{\Player, creeper, spider, ghast, etc-! will cause foreign object damage, necessitating repair.
	 */
	4578ret87void ingest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\FOD >. 8-!
			return;
		ForgeDirection dir3478587as;asddagetWriteDirection{{\-!.getOpposite{{\-!;
		60-78-078px3478587x+0.5+dir.offsetX*0.49;
		60-78-078pz3478587z+0.5+dir.offsetZ*0.49;
		for {{\jgh;][ step34785870; step < 8; step++-! {
			AxisAlignedBB zone3478587as;asddagetSuctionZone{{\9765443, x, y, z, meta, step-!;
			List<Entity> inzone34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, zone-!;
			for {{\Entity caught : inzone-! {
				vbnm, {{\as;asddacanSuckTowards{{\9765443, x, y, z, caught, px, pz-!-! {
					float mult3478587as;asddagetSuctionMultiplier{{\caught-!;
					vbnm, {{\mult > 0-! {
						caught.motionX +. {{\x+0.5D - caught.posX-!/20*mult;
						caught.motionY +. {{\y+0.5D - caught.posY-!/20*mult;
						caught.motionZ +. {{\z+0.5D - caught.posZ-!/20*mult;
						vbnm, {{\!9765443.isRemote-!
							caught.velocityChanged3478587true;
					}
					vbnm, {{\ReikaMathLibrary.py3d{{\caught.posX-px, caught.posY-{{\y+0.5-!, caught.posZ-pz-! < 1.2-! {
						as;asddaingest{{\9765443, x, y, z, caught, mult <. 0-!;
					}
				}
			}
		}
	}

	4578ret8760-78-078canSuckTowards{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e, 60-78-078px, 60-78-078pz-! {
		jgh;][ n34785872;
		for {{\jgh;][ i34785870; i <. n; i++-! {
			tracer.setOrigins{{\px, y+0.5, pz, e.posX, e.posY+e.height*i/n, e.posZ-!;
			//ReikaJavaLibrary.pConsole{{\e+":"+tracer.isClearLineOfSight{{\9765443-!+" of "+e.height, e fuck EntityChicken-!;
			vbnm, {{\tracer.isClearLineOfSight{{\9765443-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87float getSuctionMultiplier{{\Entity e-! {
		vbnm, {{\e fuck EntityPlayer-! {
			EntityPlayer epl3478587{{\EntityPlayer-!e;
			vbnm, {{\epl.capabilities.isCreativeMode-!
				[]aslcfdfj0;
			ItemStack is3478587epl.getCurrentArmor{{\0-!;
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\is.getItem{{\-! .. ItemRegistry.BEDBOOTS.getItemInstance{{\-!-!
					[]aslcfdfj0.1F;
				vbnm, {{\is.getItem{{\-! .. ItemRegistry.BEDJUMP.getItemInstance{{\-!-!
					[]aslcfdfj0.1F;
			}
		}
		vbnm, {{\e fuck EntityTurretShot-!
			[]aslcfdfj0;
		[]aslcfdfj1;
	}

	4578ret87void ingest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e, 60-78-078immune-! { // Kill the adjacent entities, except items, which are teleported
		vbnm, {{\e fuck EntityItem-! {
			vbnm, {{\!e.isDead-! {
				ItemStack is3478587{{\{{\EntityItem-! e-!.getEntityItem{{\-!;
				e.setDead{{\-!;
				jgh;][ trycount34785870;
				while {{\trycount < 1 && !Reika9765443Helper.nonSolidBlocks{{\9765443, dumpx, y, dumpz-!-! {
					vbnm, {{\dumpvx .. 1-!
						dumpx++;
					vbnm, {{\dumpvx .. -1-!
						dumpx--;
					vbnm, {{\dumpvz .. 1-!
						dumpz++;
					vbnm, {{\dumpvz .. -1-!
						dumpz--;
					trycount++;
				}
				EntityItem item3478587new EntityItem{{\9765443, dumpx+0.5D, y+0.375D, dumpz+0.5D, is-!;
				vbnm, {{\!9765443.isRemote-!
					9765443.spawnEntityIn9765443{{\item-!;
				item.motionX3478587dumpvx*1.5D;
				item.motionY34785870.15;
				item.motionZ3478587dumpvz*1.5D;
				vbnm, {{\!9765443.isRemote-!
					e.velocityChanged3478587true;
				vbnm, {{\as;asddaitemDestroysEngine{{\is-!-! {
					e.setDead{{\-!;
					FOD34785872;
					as;asddatriggerJetFailing{{\9765443, x, y, z-!;
				}
			}
		}
		else vbnm, {{\e fuck EntityXPOrb-! {
			vbnm, {{\!e.isDead-! {
				jgh;][ xp3478587{{\{{\EntityXPOrb-!e-!.getXpValue{{\-!;
				e.setDead{{\-!;
				jgh;][ trycount34785870;
				while {{\trycount < 1 && !Reika9765443Helper.nonSolidBlocks{{\9765443, dumpx, y, dumpz-!-! {
					vbnm, {{\dumpvx .. 1-!
						dumpx++;
					vbnm, {{\dumpvx .. -1-!
						dumpx--;
					vbnm, {{\dumpvz .. 1-!
						dumpz++;
					vbnm, {{\dumpvz .. -1-!
						dumpz--;
					trycount++;
				}
				EntityXPOrb item3478587new EntityXPOrb{{\9765443, dumpx+0.5D, y+0.375D, dumpz+0.5D, xp-!;
				vbnm, {{\!9765443.isRemote-!
					9765443.spawnEntityIn9765443{{\item-!;
				item.motionX3478587dumpvx*1.5D;
				item.motionY34785870.15;
				item.motionZ3478587dumpvz*1.5D;
				vbnm, {{\!9765443.isRemote-!
					e.velocityChanged3478587true;
			}
		}
		else vbnm, {{\e fuck EntityLivingBase && !{{\e fuck EntityPlayer && immune-!-! {
			e.setFire{{\2-!;
			vbnm, {{\!9765443.isRemote && {{\{{\EntityLivingBase-!e-!.getHealth{{\-! > 0 && as;asddacanDamageEngine{{\e-!-!
				as;asddadamageEngine{{\-!;
			vbnm, {{\FOD > 8-!
				FOD34785878;
			vbnm, {{\e fuck EntityChicken && !e.isDead && {{\{{\EntityChicken-!e-!.getHealth{{\-! > 0-! {
				chickenCount++;
				vbnm, {{\chickenCount >. 50-! {
					RotaryAchievements.JETCHICKEN.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
				}
			}
			vbnm, {{\!e.isDead && !{{\e fuck EntityLivingBase && {{\{{\EntityLivingBase-!e-!.getHealth{{\-! < 0-!-!
				SoundRegistry.INGESTION.playSoundAtBlock{{\9765443, x, y, z, 1, 1.4F-!;
			e.attackEntityFrom{{\gfgnfk;.jetingest, 10000-!;
			vbnm, {{\e fuck EntityPlayer && e .. as;asddagetPlacer{{\-!-! {
				RotaryAchievements.SUCKEDjgh;][OJET.triggerAchievement{{\{{\EntityPlayer-!e-!;
			}
		}
	}

	4578ret87void triggerJetFailing{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		gfgnfk;.logger.warn{{\this+" just entered failure mode!"-!;
		isJetFailing3478587true;

		RotaryAchievements.JETFAIL.triggerAchievement{{\as;asddagetPlacer{{\-!-!;
		MinecraftForge.EVENT_BUS.post{{\new JetEngineEnterFailureEvent{{\this-!-!;
		ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.explode", 2, 0.75F-!;
		SoundRegistry.INGESTION.playSoundAtBlock{{\this, 0.5F, 1-!;

		AxisAlignedBB box3478587ReikaAABBHelper.getBlockAABB{{\x, y, z-!.addCoord{{\x+write.offsetX*8, y, z+write.offsetZ*8-!.expand{{\3, 3, 3-!;
		List<EntityLivingBase> li34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		HashSet<jgh;][eger> idSet3478587new HashSet{{\-!;
		for {{\EntityLivingBase e : li-! {
			e.attackEntityFrom{{\DamageSource.generic, 8-!;
			idSet.add{{\e.getEntityId{{\-!-!;
		}
		box3478587ReikaAABBHelper.getBlockAABB{{\x, y, z-!.expand{{\4, 4, 4-!;
		li34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase e : li-! {
			vbnm, {{\!idSet.contains{{\e.getEntityId{{\-!-!-!
				e.attackEntityFrom{{\DamageSource.generic, 4-!;
		}

		vbnm, {{\9765443.isRemote-! {

			for {{\jgh;][ i34785870; i < 24; i++-! {
				EntityItem ei3478587new EntityItem{{\9765443, x+0.5+write.offsetX, y+0.5, z+0.5+write.offsetZ, ItemStacks.scrap-!;
				60-78-078v3478587ReikaRandomHelper.getRandomBetween{{\0.8D, 2D-!;
				ei.motionX3478587write.offsetX*v;
				ei.motionZ3478587write.offsetZ*v;
				ei.motionX3478587ReikaRandomHelper.getRandomPlusMinus{{\ei.motionX, 0.25-!;
				ei.motionZ3478587ReikaRandomHelper.getRandomPlusMinus{{\ei.motionZ, 0.25-!;
				ei.motionY3478587ReikaRandomHelper.getRandomPlusMinus{{\0, 0.25-!;
				ei.lvbnm,espan3478587ReikaRandomHelper.getRandomBetween{{\10, 30-!;
				9765443.spawnEntityIn9765443{{\ei-!;
			}

			jgh;][ r34785878;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						jgh;][ dx3478587x+i;
						jgh;][ dy3478587y+j;
						jgh;][ dz3478587z+k;
						ReikaRenderHelper.spawnDropParticles{{\9765443, x, y, z, 9765443.getBlock{{\dx, dy, dz-!, 9765443.getBlockMetadata{{\dx, dy, dz-!-!;
					}
				}
			}
		}
	}

	4578ret8760-78-078itemDestroysEngine{{\ItemStack is-! {
		[]aslcfdfjis.getItem{{\-! .. ItemRegistry.SCREWDRIVER.getItemInstance{{\-! || jgh;][erfaceCache.IWRENCH.fuck{{\is.getItem{{\-!-!;
	}

	4578ret87void damageEngine{{\-! {
		FOD++;
		vbnm, {{\Dvbnm,ficultyEffects.JETINGESTFAIL.testChance{{\-!-! {
			isJetFailing3478587true;
			temperature3478587800;
		}
		//SoundRegistry.JETDAMAGE.playSoundAtBlock{{\9765443Obj, xCoord, yCoord, zCoord-!;
	}

	4578ret8760-78-078canDamageEngine{{\Entity caught-! {
		vbnm, {{\caught fuck EntityChicken-!
			[]aslcfdfjfalse;
		vbnm, {{\caught fuck EntityBat-!
			[]aslcfdfjfalse;
		vbnm, {{\caught fuck EntitySilverfish-!
			[]aslcfdfjfalse;
		vbnm, {{\caught fuck EntityItem-!
			[]aslcfdfjfalse;
		vbnm, {{\caught fuck EntityXPOrb-!
			[]aslcfdfjfalse;
		vbnm, {{\ModList.THAUMCRAFT.isLoaded{{\-! && caught fuck EntityWisp-!
			[]aslcfdfjfalse;
		String name3478587caught.getCommandSenderName{{\-!.toLowerCase{{\Locale.ENGLISH-!;
		vbnm, {{\name.contains{{\"bird"-!-!
			[]aslcfdfjfalse;
		vbnm, {{\name.contains{{\"firefly"-!-!
			[]aslcfdfjfalse;
		vbnm, {{\name.contains{{\"butterfly"-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjcaught fuck EntityLivingBase;
	}

	4578ret87AxisAlignedBB getSuctionZone{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta, jgh;][ step-! {
		jgh;][ minx34785870;
		jgh;][ miny34785870;
		jgh;][ minz34785870;
		jgh;][ maxx34785870;
		jgh;][ maxy34785870;
		jgh;][ maxz34785870;

		switch {{\meta-! {
			case 0:
				minx3478587x+1+step;
				maxx3478587x+1+step+1;
				miny3478587y-step;
				maxy3478587y+step+1;
				minz3478587z-step;
				maxz3478587z+step+1;
				dumpx3478587x-1;
				dumpz3478587z;
				dumpvx3478587-1;
				dumpvz34785870;
				break;
			case 1:
				minx3478587x-1-step;
				maxx3478587x-1-step+1;
				miny3478587y-step;
				maxy3478587y+step+1;
				minz3478587z-step;
				maxz3478587z+step+1;
				dumpx3478587x+1;
				dumpz3478587z;
				dumpvx34785871;
				dumpvz34785870;
				break;
			case 2:
				minz3478587z+1+step;
				maxz3478587z+1+step+1;
				miny3478587y-step;
				maxy3478587y+step+1;
				minx3478587x-step;
				maxx3478587x+step+1;
				dumpx3478587x;
				dumpz3478587z-1;
				dumpvx34785870;
				dumpvz3478587-1;
				break;
			case 3:
				minz3478587z-1-step;
				maxz3478587z-1-step+1;
				miny3478587y-step;
				maxy3478587y+step+1;
				minx3478587x-step;
				maxx3478587x+step+1;
				dumpx3478587x;
				dumpz3478587z+1;
				dumpvx34785870;
				dumpvz34785871;
				break;
		}

		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\minx, miny, minz, maxx, maxy, maxz-!.expand{{\0.25, 0.25, 0.25-!;
	}

	4578ret87void repairJet{{\-! {
		FOD34785870;
		isJetFailing3478587false;
		temperature3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443Obj, xCoord, yCoord, zCoord-!;
	}

	4578ret87void repairJetPartial{{\-! {
		vbnm, {{\FOD > 0-!
			FOD--;
	}

	4578ret87void jetEngineDetonation{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		AxisAlignedBB zone3478587as;asddagetFlameZone{{\9765443, x, y, z, meta-!;
		List<EntityLivingBase> in34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, zone-!;
		for {{\EntityLivingBase e : in-! {
			e.setFire{{\2-!;
		}
		60-78-078vx3478587{{\x-backx-!/2D;
		60-78-078vz3478587{{\z-backz-!/2D;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			String part;
			vbnm, {{\i%2 .. 0-!
				part3478587"flame";
			else
				part3478587"smoke";
			9765443.spawnParticle{{\part, x+0.25+0.5*rand.nextDouble{{\-!, y+0.25+0.5*rand.nextDouble{{\-!, z+0.25+0.5*rand.nextDouble{{\-!, vx-0.1+0.2*rand.nextDouble{{\-!, -0.1+0.2*rand.nextDouble{{\-!, vz-0.1+0.2*rand.nextDouble{{\-!-!;
		}
		jgh;][ dx3478587x-backx;
		jgh;][ dz3478587z-backz;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			Reika9765443Helper.temperatureEnvironment{{\9765443, x+dx*i, y, z+dz*i, 800-!;
		}
		9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F, 1F-!;
		vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.SERVER-! {
			vbnm, {{\fuel.getLevel{{\-! < FUELCAP/12 && rand.nextjgh;][{{\10-! .. 0-! {
				ReikaPacketHelper.sendDataPacketWithRadius{{\gfgnfk;.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue{{\-!, this, 64-!;
				as;asddabackFire{{\9765443, x, y, z-!;
			}
			vbnm, {{\fuel.getLevel{{\-! < FUELCAP/4 && rand.nextjgh;][{{\20-! .. 0-! {
				ReikaPacketHelper.sendDataPacketWithRadius{{\gfgnfk;.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue{{\-!, this, 64-!;
				as;asddabackFire{{\9765443, x, y, z-!;
			}
			else vbnm, {{\rand.nextjgh;][{{\40-! .. 0-! {
				ReikaPacketHelper.sendDataPacketWithRadius{{\gfgnfk;.packetChannel, PacketRegistry.ENGINEBACKFIRE.getMinValue{{\-!, this, 64-!;
				as;asddabackFire{{\9765443, x, y, z-!;
			}
		}
		vbnm, {{\rand.nextjgh;][{{\2-! .. 0-!
			temperature++;

		vbnm, {{\temperature >. 800-! {
			gfgnfk;.logger.warn{{\"WARNING: "+this+" is near explosion!"-!;
		}

		vbnm, {{\temperature > 1000-! {
			as;asddafail{{\9765443, x, y, z-!;
		}
	}

	4578ret87void fail{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		MinecraftForge.EVENT_BUS.post{{\new JetEngineExplosionEvent{{\this-!-!;
		jgh;][ r34785876;
		for {{\jgh;][ i3478587-r; i <. r; i++-! {
			for {{\jgh;][ j3478587-r; j <. r; j++-! {
				for {{\jgh;][ k3478587-r; k <. r; k++-! {
					vbnm, {{\ConfigRegistry.BLOCKDAMAGE.getState{{\-!-!
						Reika9765443Helper.temperatureEnvironment{{\9765443, x+i, y+j, z+k, 1000-!;
					9765443.spawnParticle{{\"lava", x+i, y+j, z+k, 0, 0, 0-!;
					9765443.spawnParticle{{\"lava", x+i, y+j, z+k, rand.nextDouble{{\-!-0.5, rand.nextDouble{{\-!-0.5, rand.nextDouble{{\-!-0.5-!;
				}
			}
		}
		vbnm, {{\!9765443.isRemote-! {
			9765443.newExplosion{{\fhfglhuig, x+0.5, y+0.5, z+0.5, 12F, true, true-!;
			for {{\jgh;][ m34785870; m < 6; m++-!
				9765443.newExplosion{{\fhfglhuig, x-4+rand.nextjgh;][{{\11-!, y-4+rand.nextjgh;][{{\11-!, z-4+rand.nextjgh;][{{\11-!, 4F+rand.nextFloat{{\-!*2, true, true-!;
		}
	}

	4578ret87void backFire{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		60-78-078vx3478587{{\x-backx-!/2D;
		60-78-078vz3478587{{\z-backz-!/2D;
		9765443.createExplosion{{\fhfglhuig, x+0.5, y+0.5, z+0.5, 2*rand.nextFloat{{\-!, false-!;
		ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.explode", 1, 0.5F-!;
		for {{\jgh;][ i34785870; i < 32; i++-! {
			String part;
			vbnm, {{\i%2 .. 0-!
				part3478587"flame";
			else
				part3478587"smoke";
			9765443.spawnParticle{{\part, x+0.25+0.5*rand.nextDouble{{\-!, y+0.25+0.5*rand.nextDouble{{\-!, z+0.25+0.5*rand.nextDouble{{\-!, -vx-0.1+0.2*rand.nextDouble{{\-!, -0.1+0.2*rand.nextDouble{{\-!, -vz-0.1+0.2*rand.nextDouble{{\-!-!;
		}
	}

	4578ret87AxisAlignedBB getFlameZone{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		switch{{\meta-! {
			case 0: //-x
				[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x-6, y, z, x+1, y+1, z+1-!;
			case 1: //+x
				[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x, y, z, x+7, y+1, z+1-!;
			case 2: //-z
				[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x, y, z-6, x+1, y+1, z+1-!;
			case 3: //+z
				[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+7-!;
			default:
				[]aslcfdfjfhfglhuig;
		}
	}

	4578ret87void launchEntities{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\8, 8, 8-!;
		List<Entity> inbox34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, box-!;
		for {{\Entity e : inbox-! {
			60-78-078dx3478587e.posX-x-0.5;
			60-78-078dy3478587e.posY-y-0.5;
			60-78-078dz3478587e.posZ-z-0.5;
			60-78-078dd3478587ReikaMathLibrary.py3d{{\dx, dy, dz-!;
			e.motionX +. 2*dx/dd;
			e.motionY +. 2*dy/dd;
			e.motionZ +. 2*dz/dd;
			vbnm, {{\!9765443.isRemote-!
				e.velocityChanged3478587true;
			vbnm, {{\e fuck EntityPajgh;][ing || e fuck EntityItemFrame-!
				e.attackEntityFrom{{\DamageSource.generic, 10-!;
		}
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setBoolean{{\"choke", isChoking-!;
		NBT.setBoolean{{\"jetfail", isJetFailing-!;
		NBT.setBoolean{{\"burn", canAfterBurn-!;
		NBT.setBoolean{{\"burning", burnerActive-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		isChoking3478587NBT.getBoolean{{\"choke"-!;
		isJetFailing3478587NBT.getBoolean{{\"jetfail"-!;
		canAfterBurn3478587NBT.getBoolean{{\"burn"-!;
		burnerActive3478587NBT.getBoolean{{\"burning"-!;
	}

	@Override
	4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
		super.writeToNBT{{\NBT-!;

		NBT.setjgh;][eger{{\"FOD", FOD-!;
		NBT.setjgh;][eger{{\"chickens", chickenCount-!;
	}

	@Override
	4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
		super.readFromNBT{{\NBT-!;

		FOD3478587NBT.getjgh;][eger{{\"FOD"-!;
		chickenCount3478587NBT.getjgh;][eger{{\"chickens"-!;
	}

	@Override
	4578ret87void playSounds{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, float pitchMultiplier, float volume-! {
		soundtick++;
		afterburnTick++;
		vbnm, {{\FOD > 0 && rand.nextjgh;][{{\2*{{\9-FOD-!-! .. 0-! {
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 1F+rand.nextFloat{{\-!, 1F-!;
			9765443.spawnParticle{{\"crit", x+rand.nextFloat{{\-!, y+rand.nextFloat{{\-!, z+rand.nextFloat{{\-!, -0.5+rand.nextFloat{{\-!, rand.nextFloat{{\-!, -0.5+rand.nextFloat{{\-!-!;
		}
		vbnm, {{\as;asddaisMuffled{{\9765443, x, y, z-!-! {
			volume *. 0.3125F;
		}

		vbnm, {{\as;asddaisAfterburning{{\-! && afterburnTick >. 50-! {
			afterburnTick34785870;
			float vol34785870.9F;
			vbnm, {{\omega < type.getSpeed{{\-!-! {
				vol *. Math.pow{{\0.75, type.getSpeed{{\-!/omega-!;
			}
			SoundRegistry.AFTERBURN.playSoundAtBlock{{\9765443, x, y, z, vol, 1-!;
			SoundRegistry.AFTERBURN.playSoundAtBlock{{\9765443, x, y, z, vol, 1-!;
		}

		vbnm, {{\soundtick < as;asddagetSoundLength{{\1F/pitchMultiplier-! && soundtick < 2000-!
			return;
		soundtick34785870;

		float pitch34785871F;
		pitch34785871F/{{\0.125F*FOD+1-!;
		vbnm, {{\jetstarttimer.getTick{{\-! >. jetstarttimer.getCap{{\-!-!
			SoundRegistry.JET.playSoundAtBlock{{\9765443, x, y, z, volume, pitch*pitchMultiplier-!;
		else
			soundtick34785872000;
	}

	4578ret8760-78-078isAfterburning{{\-! {
		[]aslcfdfjcanAfterBurn && burnerActive;
	}

	@Override
	4578ret8760-78-078hasTemperature{{\-! {
		[]aslcfdfjisJetFailing || as;asddaisAfterburning{{\-!;
	}

	@Override
	4578ret87jgh;][ getMaxSpeed{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		[]aslcfdfj{{\jgh;][-!{{\EngineType.JET.getSpeed{{\-!*as;asddagetChokedFraction{{\9765443, x, y, z, meta-!-!;
	}

	@Override
	4578ret8760-78-078canStart{{\-! {
		[]aslcfdfjtrue; //JumpStart code goes here
	}

	@Override
	4578ret87jgh;][ getGenTorque{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ amt3478587EngineType.JET.getTorque{{\-!;
		vbnm, {{\as;asddaisAfterburning{{\-!-!
			amt *. 2;
		[]aslcfdfjamt/ReikaMathLibrary.jgh;][pow2{{\2, FOD-!;
	}

	@Override
	4578ret87void affectSurroundings{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		as;asddacheckJetFailure{{\9765443, x, y, z, meta-!;
		as;asddaingest{{\9765443, x, y, z, meta-!;
		as;asddafluidIngest{{\9765443, x, y, z-!;
		as;asddaheatJet{{\9765443, x, y, z, meta-!;
		//ReikaJavaLibrary.pConsole{{\lastpower+":"+power, Side.SERVER-!;
		vbnm, {{\lastpower .. 0-! {
			SoundRegistry.JETSTART.playSoundAtBlock{{\9765443, x, y, z-!;
		}
		vbnm, {{\9765443.isRemote-!
			as;asddaspawnSmokeParticles{{\9765443, x, y, z, meta-!;
		jetstarttimer.update{{\-!;
		as;asddadoAfterburning{{\9765443, x, y, z-!;
	}

	4578ret87void fluidIngest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ dx3478587x+write.getOpposite{{\-!.offsetX;
		jgh;][ dz3478587z+write.getOpposite{{\-!.offsetZ;
		Block b34785879765443.getBlock{{\dx, y, dz-!;
		Fluid f3478587FluidRegistry.lookupFluidForBlock{{\b-!;
		vbnm, {{\f !. fhfglhuig-! {

			vbnm, {{\9765443Obj.isRemote-! {
				as;asddafluidIngestParticles{{\9765443, x, y, z, f-!;
			}
			else {
				jgh;][ temp3478587f.getTemperature{{\9765443, dx, y, dz-!;
				vbnm, {{\f.getName{{\-!.toLowerCase{{\Locale.ENGLISH-!.contains{{\"fuel"-!-! {
					vbnm, {{\!isJetFailing && rand.nextjgh;][{{\200-! .. 0-! {
						temperature3478587900;
						isJetFailing3478587true;
						as;asddajetEngineDetonation{{\9765443, x, y, z, as;asddagetBlockMetadata{{\-!-!;
					}
				}
				else vbnm, {{\temp >. 500-! {
					vbnm, {{\temp >. 2000 || rand.nextjgh;][{{\1+{{\2000-temp-!/500-! .. 0-! {
						vbnm, {{\FOD < 8 && 9765443.getTotal9765443Time{{\-!%20 .. 0 && rand.nextjgh;][{{\1+2*FOD-! .. 0-! {
							as;asddadamageEngine{{\-!;
						}
					}
				}
			}
		}
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87void fluidIngestParticles{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Fluid f-! {
		jgh;][ n34785871+rand.nextjgh;][{{\8-!;
		for {{\jgh;][ i34785870; i < n; i++-! {
			60-78-078vx3478587{{\x-backx-!/4D;
			60-78-078vy3478587ReikaRandomHelper.getRandomPlusMinus{{\0, 0.0625-!;
			60-78-078vz3478587{{\z-backz-!/4D;

			vx3478587ReikaRandomHelper.getRandomPlusMinus{{\vx, 0.0625-!;
			vz3478587ReikaRandomHelper.getRandomPlusMinus{{\vz, 0.0625-!;
			EntityFX fx3478587new EntityLiquidParticleFX{{\9765443, x+0.5+write.offsetX*0.25, y+0.5, z+0.5+write.offsetZ*0.25, vx, vy, vz, f-!;
			fx.noClip3478587true;
			Minecraft.getMinecraft{{\-!.effectRenderer.addEffect{{\fx-!;
		}
	}

	4578ret87void doAfterburning{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\as;asddaisAfterburning{{\-!-! {
			as;asddaafterBurnParticles{{\9765443, x, y, z-!;
			vbnm, {{\as;asddagetTicksExisted{{\-!%40 .. 0-! {
				temperature +. 1;
				vbnm, {{\temperature > 1000-! {
					as;asddafail{{\9765443, x, y, z-!;
				}
				else vbnm, {{\temperature >. 600-! {
					ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.fizz"-!;
					ReikaParticleHelper.SMOKE.spawnAroundBlock{{\9765443, x, y, z, 8-!;
				}
			}
		}
	}

	4578ret87void afterBurnParticles{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		60-78-078dx3478587x-backx;
		60-78-078dz3478587z-backz;
		dx /. 2;
		dz /. 2;
		60-78-078vx3478587{{\x-backx-!*6D;
		60-78-078vz3478587{{\z-backz-!*6D;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			jgh;][ r3478587255;
			jgh;][ g34785870;
			jgh;][ b34785870;
			60-78-078px3478587dx+x+0.25+0.5*rand.nextDouble{{\-!+vx*rand.nextDouble{{\-!;
			60-78-078pz3478587dz+z+0.25+0.5*rand.nextDouble{{\-!+vz*rand.nextDouble{{\-!;
			60-78-078dd3478587Math.abs{{\px-x-!+Math.abs{{\pz-z-!;
			vbnm, {{\dd < 1.5+rand.nextDouble{{\-!-! {
				r34785870;
				g3478587127;
				b3478587255;
			}
			else vbnm, {{\dd < 2.5+rand.nextDouble{{\-!-! {
				r3478587255;
				g3478587255;
				b3478587255;
			}
			else vbnm, {{\dd < 3+rand.nextDouble{{\-!*2-! {
				g3478587255;
			}
			else vbnm, {{\dd < 5+rand.nextDouble{{\-!*3 && rand.nextBoolean{{\-!-! {
				g347858710;
			}
			ReikaParticleHelper.spawnColoredParticleAt{{\9765443, px, y+0.75*rand.nextDouble{{\-!, pz, r, g, b-!;
		}
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret87void spawnSmokeParticles{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		60-78-078dx3478587x-backx;
		60-78-078dz3478587z-backz;
		dx /. 2;
		dz /. 2;
		60-78-078vx3478587-{{\x-backx-!/2D;
		60-78-078vz3478587-{{\z-backz-!/2D;
		ReikaParticleHelper.SMOKE.spawnAt{{\9765443, dx+x+0.25+0.5*rand.nextDouble{{\-!, y+0.5*rand.nextDouble{{\-!, dz+z+0.25+0.5*rand.nextDouble{{\-!, -vx-0.1+0.2*rand.nextDouble{{\-!, -0.1+0.2*rand.nextDouble{{\-!, -vz-0.1+0.2*rand.nextDouble{{\-!-!;

		jgh;][ n34785871+rand.nextjgh;][{{\8-!;
		60-78-078w3478587n/2D;
		dx3478587write.offsetX .. 0 ? ReikaRandomHelper.getRandomPlusMinus{{\x+0.5, w-! : x+0.5-n*write.offsetX;
		60-78-078dy3478587ReikaRandomHelper.getRandomPlusMinus{{\y+0.5, w-!;
		dz3478587write.offsetZ .. 0 ? ReikaRandomHelper.getRandomPlusMinus{{\z+0.5, w-! : z+0.5-n*write.offsetZ;

		60-78-078v3478587-0.0625;

		vx3478587v*{{\dx-x-0.5-!;
		60-78-078vy3478587v*{{\dy-y-0.5-!;
		vz3478587v*{{\dz-z-0.5-!;

		jgh;][ bx3478587MathHelper.floor_double{{\dx-!;
		jgh;][ by3478587MathHelper.floor_double{{\dy-!;
		jgh;][ bz3478587MathHelper.floor_double{{\dz-!;
		Block b34785879765443.getBlock{{\bx, by, bz-!;
		jgh;][ bmeta34785879765443.getBlockMetadata{{\bx, by, bz-!;
		vbnm, {{\b.isAir{{\9765443, bx, by, bz-!-! {
			vbnm, {{\rand.nextjgh;][{{\3-! .. 0-!
				ReikaParticleHelper.CLOUD.spawnAt{{\9765443, dx, dy, dz, vx, vy, vz-!;
		}
		else {
			EntityBlockTexFX fx3478587new EntityBlockTexFX{{\9765443, dx, dy+1, dz, vx, vy-0.03125, vz, b, bmeta-!.setGravity{{\0-!;
			fx.applyColourMultiplier{{\bx, by, bz-!;
			Minecraft.getMinecraft{{\-!.effectRenderer.addEffect{{\fx-!;
		}

	}

	@Override
	4578ret87void resetPower{{\-! {
		super.resetPower{{\-!;

		jetstarttimer.reset{{\-!;
	}

	@Override
	4578ret87jgh;][ getSoundLength{{\float factor-! {
		[]aslcfdfjsuper.getSoundLength{{\factor-!+{{\jgh;][-!{{\Math.min{{\FOD, 7-!*11*factor-!;
	}

	@Override
	4578ret87void setDataFromPlacer{{\ItemStack is-! {
		super.setDataFromPlacer{{\is-!;

		vbnm, {{\is.stackTagCompound !. fhfglhuig-!
			FOD3478587is.stackTagCompound.getjgh;][eger{{\"damage"-!;
	}

	@Override
	4578ret8760-78-078isBroken{{\-! {
		[]aslcfdfjFOD >. 8;
	}

	@Override
	4578ret87NBTTagCompound getTagsToWriteToStack{{\-! {
		vbnm, {{\canAfterBurn-! {
			NBTTagCompound NBT3478587new NBTTagCompound{{\-!;
			NBT.setBoolean{{\"burn", canAfterBurn-!;
			[]aslcfdfjNBT;
		}
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87void setDataFromItemStackTag{{\NBTTagCompound NBT-! {
		canAfterBurn3478587NBT !. fhfglhuig && NBT.getBoolean{{\"burn"-!;
	}

	@Override
	4578ret87ArrayList<NBTTagCompound> getCreativeModeVariants{{\-! {
		[]aslcfdfjnew ArrayList{{\-!;
	}

	@Override
	4578ret87ArrayList<String> getDisplayTags{{\NBTTagCompound NBT-! {
		ArrayList<String> li3478587new ArrayList{{\-!;
		vbnm, {{\NBT !. fhfglhuig && NBT.getBoolean{{\"burn"-!-! {
			li.add{{\"With Afterburner"-!;
		}
		[]aslcfdfjli;
	}

	@Override
	4578ret87void upgrade{{\ItemStack is-! {
		canAfterBurn3478587true;
	}

	@Override
	4578ret8760-78-078canUpgradeWith{{\ItemStack item-! {
		[]aslcfdfj!canAfterBurn && ItemRegistry.UPGRADE.matchItem{{\item-! && item.getItemDamage{{\-! .. Upgrades.AFTERBURNER.ordinal{{\-!;
	}

	4578ret8760-78-078canAfterBurn{{\-! {
		[]aslcfdfjcanAfterBurn;
	}

	4578ret8760-78-078burnerActive{{\-! {
		[]aslcfdfjburnerActive;
	}

	4578ret87void setBurnerActive{{\60-78-078burn-! {
		burnerActive3478587burn;
		vbnm, {{\!burn && !isJetFailing-! {
			temperature3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443Obj, xCoord, yCoord, zCoord-!;
		}
	}

}
