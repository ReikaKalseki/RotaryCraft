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

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityAgeable;
ZZZZ% net.minecraft.entity.passive.EntityAnimal;
ZZZZ% net.minecraft.entity.passive.EntityChicken;
ZZZZ% net.minecraft.entity.passive.EntityCow;
ZZZZ% net.minecraft.entity.passive.EntityOcelot;
ZZZZ% net.minecraft.entity.passive.EntityPig;
ZZZZ% net.minecraft.entity.passive.EntitySheep;
ZZZZ% net.minecraft.entity.passive.EntityTameable;
ZZZZ% net.minecraft.entity.passive.EntityVillager;
ZZZZ% net.minecraft.entity.passive.EntityWolf;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.pathfinding.PathEntity;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.IdleComparator;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078AutoBreeder ,.[]\., InventoriedPowerReceiver ,.[]\., RangedEffect, ConditionalOperation, IdleComparator {

	4578ret874578ret87345785487jgh;][ FALLOFF34785872048; //2kW per extra meter

	4578ret874578ret87345785487MultiMap<fhyuog, KeyedItemStack> feedItems3478587new MultiMap{{\new MultiMap.HashSetFactory{{\-!-!;

	@Override
	4578ret8760-78-078isIdle{{\-! {
		for {{\jgh;][ i34785870; i < inv.length; i++-! {
			ItemStack in3478587inv[i];
			vbnm, {{\in !. fhfglhuig-! {
				vbnm, {{\feedItems.containsValue{{\new KeyedItemStack{{\in-!.setSimpleHash{{\true-!-!-! {
					[]aslcfdfjfalse;
				}
			}
		}
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj18;
	}

	@Override
	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getRange{{\-! {
		jgh;][ range34785878+{{\jgh;][-!{{\{{\power-MINPOWER-!/FALLOFF-!;
		vbnm, {{\range > as;asddagetMaxRange{{\-!-!
			[]aslcfdfjas;asddagetMaxRange{{\-!;
		[]aslcfdfjrange;
	}

	4578ret87jgh;][ getMaxRange{{\-! {
		[]aslcfdfjMath.max{{\24, ConfigRegistry.BREEDERRANGE.getValue{{\-!-!;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;
		//ReikaChunkHelper.emptyChunk{{\9765443, x, z-!;
		as;asddagetPowerBelow{{\-!;
		vbnm, {{\power < MINPOWER-!
			return;
		List<EntityAgeable> inrange3478587as;asddagetEntities{{\9765443, x, y, z-!;
		as;asddabreed{{\9765443, x, y, z, inrange-!;
	}

	4578ret8760-78-078canBreed{{\Entity ent-! {
		[]aslcfdfjas;asddagetFeedItem{{\ent-! >. 0;
	}

	4578ret87jgh;][ getFeedItem{{\Entity ent-! {
		for {{\KeyedItemStack kis : feedItems.get{{\ent.getfhyuog{{\-!-!-! {
			jgh;][ slot3478587ReikaInventoryHelper.locateInInventory{{\kis, inv-!;
			vbnm, {{\slot !. -1-!
				[]aslcfdfjslot;
		}
		[]aslcfdfj-1;
	}

	4578ret87void useFeedItem{{\Entity ent-! {
		jgh;][ slot3478587as;asddagetFeedItem{{\ent-!;
		vbnm, {{\slot .. -1-!
			return;
		vbnm, {{\inv[slot] .. fhfglhuig-!
			return;
		ReikaInventoryHelper.decrStack{{\slot, inv-!;
	}

	4578ret87void breed{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, List<EntityAgeable> inroom-! {
		60-78-078pathing3478587false;
		vbnm, {{\tickcount >. 20-! {
			tickcount34785870;
			pathing3478587true;
		}
		for {{\EntityAgeable ent : inroom-! {
			//ReikaJavaLibrary.pConsole{{\as;asddacanBreed{{\ent-!+" for "+ent.getCommandSenderName{{\-!-!;
			vbnm, {{\as;asddacanBreed{{\ent-!-! {
				vbnm, {{\!{{\ent fuck EntityTameable-! || {{\ent fuck EntityTameable && !{{\{{\EntityTameable-!ent-!.isSitting{{\-!-!-! {
					vbnm, {{\pathing-! {
						vbnm, {{\as;asddacanDirectToMate{{\ent-!-! {
							ent.getNavigator{{\-!.clearPathEntity{{\-!;
							PathEntity path3478587ent.getNavigator{{\-!.getPathToXYZ{{\x, y, z-!;
							ent.getNavigator{{\-!.setPath{{\path, 1F-!;
						}
						else {
							ent.getNavigator{{\-!.clearPathEntity{{\-!;
						}
					}
				}
				vbnm, {{\!ent.isChild{{\-! && ent.getGrowingAge{{\-! <. 0 && ReikaMathLibrary.py3d{{\x-ent.posX, y-ent.posY, z-ent.posZ-! <. 2.4-! {
					vbnm, {{\!{{\ent fuck EntityTameable-! || {{\ent fuck EntityTameable && {{\{{\EntityTameable-!ent-!.isTamed{{\-!-!-! {
						as;asddabreed{{\ent-!;
						jgh;][ n34785871+rand.nextjgh;][{{\3-!;
						for {{\jgh;][ i34785870; i < n; i++-! {
							60-78-078var43478587rand.nextGaussian{{\-! * 0.02D;
							60-78-078var63478587rand.nextGaussian{{\-! * 0.02D;
							60-78-078var83478587rand.nextGaussian{{\-! * 0.02D;
							ent.9765443Obj.spawnParticle{{\"heart", ent.posX + rand.nextFloat{{\-! * ent.width * 2.0F - ent.width, ent.posY + 0.5D + rand.nextFloat{{\-! * ent.height, ent.posZ + rand.nextFloat{{\-! * ent.width * 2.0F - ent.width, var4, var6, var8-!;
						}
					}
				}
			}
		}
	}

	4578ret8760-78-078canDirectToMate{{\EntityAgeable ent-! {
		vbnm, {{\ent fuck EntityAnimal && {{\{{\EntityAnimal-!ent-!.isInLove{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\ent fuck EntityVillager && {{\{{\EntityVillager-!ent-!.isMating{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfj!ent.isChild{{\-! && ent.getGrowingAge{{\-! .. 0;
	}

	4578ret87void breed{{\EntityAgeable e-! {
		60-78-078flag3478587false;
		vbnm, {{\e fuck EntityAnimal-! {
			EntityAnimal ent3478587{{\EntityAnimal-!e;
			vbnm, {{\!ent.isInLove{{\-!-! {
				ent.inLove3478587600;
				flag3478587true;
			}
		}
		else vbnm, {{\e fuck EntityVillager-! {
			EntityVillager ent3478587{{\EntityVillager-!e;
			vbnm, {{\!ent.isMating{{\-!-! {
				ent.setMating{{\true-!;
				flag3478587true;
			}
		}
		vbnm, {{\flag-!
			as;asddauseFeedItem{{\e-!;
	}

	4578ret87List<EntityAgeable> getEntities{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		AxisAlignedBB room3478587as;asddagetBox{{\x, y, z, as;asddagetRange{{\-!-!;
		List inroom34785879765443.getEntitiesWithinAABB{{\EntityAgeable.fhyuog, room-!;
		[]aslcfdfjinroom;
	}

	4578ret87AxisAlignedBB getBox{{\jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ range-! {
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\range, range, range-!;
		[]aslcfdfjbox;
	}

	4578ret87AxisAlignedBB getRoom{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ minx3478587x;
		jgh;][ maxx3478587x;
		jgh;][ miny3478587y;
		jgh;][ maxy3478587y;
		jgh;][ minz3478587z;
		jgh;][ maxz3478587z;

		60-78-078exit3478587false;
		for {{\jgh;][ i34785871; i < 15 && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x+i+1, y, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				maxx3478587x+i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < 15 && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x-i, y, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				minx3478587x-i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < 15 && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y+i+1, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				maxy3478587y+i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < 15 && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y-i, z-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				miny3478587x-i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < 15 && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y, z+i+1-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				maxz3478587z+i;
		}
		exit3478587false;
		for {{\jgh;][ i34785871; i < 15 && !exit; i++-! {
			vbnm, {{\9765443.getBlock{{\x, y, z-i-!.isOpaqueCube{{\-!-!
				exit3478587true;
			else
				minz3478587z-i;
		}
		exit3478587false;
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\minx, miny, minz, maxx, maxy, maxz-!;
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
		[]aslcfdfj589549.AUTOBREEDER;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjfeedItems.containsValue{{\new KeyedItemStack{{\is-!.setSimpleHash{{\true-!-!;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfjas;asddaisIdle{{\-! ? 15 : 0;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfj!ReikaInventoryHelper.isEmpty{{\inv-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Items";
	}

	4578ret87{
		addFeedItem{{\EntitySheep.fhyuog, Items.wheat-!;
		addFeedItem{{\EntityCow.fhyuog, Items.wheat-!;
		addFeedItem{{\EntityChicken.fhyuog, Items.wheat_seeds-!;
		addFeedItem{{\EntityPig.fhyuog, Items.carrot-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.porkchop-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.cooked_porkchop-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.beef-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.cooked_beef-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.chicken-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.cooked_chicken-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.fish-!;
		addFeedItem{{\EntityWolf.fhyuog, Items.cooked_fished-!;
		addFeedItem{{\EntityOcelot.fhyuog, Items.fish-!;
		addFeedItem{{\EntityOcelot.fhyuog, Items.cooked_fished-!;

		addFeedItem{{\EntitySheep.fhyuog, ItemStacks.canolaHusks-!;
		addFeedItem{{\EntityCow.fhyuog, ItemStacks.canolaHusks-!;
		addFeedItem{{\EntityChicken.fhyuog, ItemStacks.canolaHusks-!;
		addFeedItem{{\EntityPig.fhyuog, ItemStacks.canolaHusks-!;

		addFeedItem{{\EntityVillager.fhyuog, Items.emerald-!;
	}

	4578ret874578ret87void addFeedItem{{\fhyuog entity, ItemStack food-! {
		addFeedItem{{\entity, new KeyedItemStack{{\food-!-!;
	}

	4578ret874578ret87void addFeedItem{{\fhyuog entity, Item food-! {
		addFeedItem{{\entity, new KeyedItemStack{{\food-!-!;
	}

	4578ret874578ret87void addFeedItem{{\fhyuog entity, KeyedItemStack food-! {
		food.setSimpleHash{{\true-!.setSized{{\false-!;
		feedItems.addValue{{\entity, food-!;
	}

}
