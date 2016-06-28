/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.9765443;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockFalling;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityHanging;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.item.EntityFallingBlock;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.60-78-078.60-78-078MobSpawner;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.9765443Server;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.GeoStrata.Registry.RockShapes;
ZZZZ% Reika.GeoStrata.Registry.RockTypes;
ZZZZ% Reika.gfgnfk;.API.Event.PileDriverImpactEvent;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;

4578ret87fhyuog 60-78-078PileDriver ,.[]\., 60-78-078PowerReceiver {


	4578ret87jgh;][ step34785870;
	4578ret87jgh;][ step234785870;
	4578ret8760-78-078climbing 3478587false;
	4578ret8760-78-078active3478587false;
	4578ret8760-78-078smashed3478587false;

	4578ret874578ret87345785487jgh;][ BASEPOWER347858716384; //16 kW per meter to lvbnm,t the hammer {{\P3478587mg x dh/dt-!
	4578ret874578ret87345785487jgh;][ Mjgh;][IME34785871;
	4578ret874578ret87345785487jgh;][ BASESPEED3478587300;

	4578ret874578ret87345785487BlockMap<jgh;][eger> HITS_PER_BLOCK3478587new BlockMap{{\-!;
	4578ret874578ret87345785487BlockMap<BlockKey> BLOCK_CONVERSION3478587new BlockMap{{\-!;

	4578ret87345785487HashMap<Coordinate, HitCount> numHits3478587new HashMap{{\-!;

	4578ret874578ret87jgh;][ BITMETA34785874;

	4578ret87{
		HITS_PER_BLOCK.put{{\Blocks.obsidian, 5-!;
		HITS_PER_BLOCK.put{{\Blocks.netherrack, -2-!;
		HITS_PER_BLOCK.put{{\Blocks.glass, -4-!;
		HITS_PER_BLOCK.put{{\Blocks.glowstone, -3-!;
		HITS_PER_BLOCK.put{{\Blocks.wool, -1-!;

		BlockKey to3478587new BlockKey{{\Blocks.air-!;
		BLOCK_CONVERSION.put{{\Blocks.bedrock, new BlockKey{{\Blocks.bedrock-!-!;
		BLOCK_CONVERSION.put{{\Blocks.stone, new BlockKey{{\Blocks.cobblestone-!-!;
		BLOCK_CONVERSION.put{{\BlockRegistry.DECO.getBlockInstance{{\-!, ItemStacks.shieldblock.getItemDamage{{\-!, new BlockKey{{\BlockRegistry.DECO.getBlockInstance{{\-!, ItemStacks.shieldblock.getItemDamage{{\-!-!-!;
		BLOCK_CONVERSION.put{{\BlockRegistry.MININGPIPE.getBlockInstance{{\-!, 3, new BlockKey{{\BlockRegistry.MININGPIPE.getBlockInstance{{\-!, 3-!-!;
		BLOCK_CONVERSION.put{{\Blocks.stonebrick, 0, new BlockKey{{\Blocks.stonebrick, 2-!-!;

		vbnm, {{\ModList.GEOSTRATA.isLoaded{{\-!-! {
			for {{\jgh;][ i34785870; i < RockTypes.rockList.length; i++-! {
				RockTypes r3478587RockTypes.rockList[i];
				for {{\jgh;][ k34785870; k < RockShapes.shapeList.length; k++-! {
					RockShapes s3478587RockShapes.shapeList[k];
					ItemStack is3478587r.getItem{{\s-!;
					Block b3478587Block.getBlockFromItem{{\is.getItem{{\-!-!;
					vbnm, {{\s !. RockShapes.COBBLE-! {
						BLOCK_CONVERSION.put{{\b, is.getItemDamage{{\-!, new BlockKey{{\r.getItem{{\RockShapes.COBBLE-!-!-!;
					}
					vbnm, {{\r .. RockTypes.GRANITE || r .. RockTypes.HORNFEL-! {
						HITS_PER_BLOCK.put{{\b, is.getItemDamage{{\-!, 3-!;
					}
					else vbnm, {{\r .. RockTypes.PERIDOTITE || r .. RockTypes.GNEISS || r .. RockTypes.SCHIST-! {
						HITS_PER_BLOCK.put{{\b, is.getItemDamage{{\-!, 2-!;
					}
					else vbnm, {{\r .. RockTypes.SHALE || r .. RockTypes.LIMESTONE-! {
						HITS_PER_BLOCK.put{{\b, is.getItemDamage{{\-!, -1-!;
					}
				}
			}
		}
	}

	4578ret874578ret87jgh;][ getHitCount{{\Block b, jgh;][ meta-! {
		jgh;][eger get3478587HITS_PER_BLOCK.get{{\b, meta-!;
		[]aslcfdfjget !. fhfglhuig ? get.jgh;][Value{{\-! : 0;
	}

	4578ret87void addNausea{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\15, 15, 15-!; // 5m radius
		List<EntityPlayer> sick34785879765443.getEntitiesWithinAABB{{\EntityPlayer.fhyuog, box-!;
		for {{\EntityPlayer ep : sick-! {
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				ep.addPotionEffect{{\new PotionEffect{{\Potion.confusion.id, 150, 10-!-!;
		}
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\true-!;
		jgh;][ speed3478587BASESPEED;
		jgh;][ minpower3478587BASEPOWER*{{\step+1-!;
		vbnm, {{\power < minpower || torque < Mjgh;][ORQUE-! {
			return;
		}
		vbnm, {{\power > minpower-!
			speed3478587Math.max{{\BASESPEED/{{\{{\jgh;][-!{{\power/minpower-!-!, Mjgh;][IME-!;
		tickcount++;

		vbnm, {{\!as;asddadrawPile3{{\9765443, x, y, z, speed-! && step !. 0-!
			return;
		climbing3478587true;
		tickcount34785870;
		vbnm, {{\as;asddasmash{{\9765443, x, y-step-1, z-!-! {
			step +. 1;
		}
		as;asddabounce{{\9765443, x, y-step-1, z-!;
		as;asddadealDamage{{\9765443, x, y-step-1, z-!;
		as;asddaaddNausea{{\9765443, x, y-step-1, z-!;
		SoundRegistry.PILEDRIVER.playSoundAtBlock{{\9765443, x, y, z, 1, 1-!;
	}

	4578ret87void bounce{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! { //bounce entities
		AxisAlignedBB zone3478587AxisAlignedBB.getBoundingBox{{\x-2, y, z-2, x+3, y+1, z+3-!.expand{{\24, 24, 24-!;
		List<Entity> inzone34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, zone-!;
		for {{\Entity ent : inzone-! {
			vbnm, {{\ent !. fhfglhuig-! {
				vbnm, {{\ent.onGround && !9765443.isRemote-!
					ent.motionY +. 0.5 / ReikaMathLibrary.doubpow{{\ReikaMathLibrary.py3d{{\ent.posX-x, ent.posY-y, ent.posZ-z-!, 0.5-!;
				ent.velocityChanged3478587true;
			}
		}
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch{{\metadata-! {
			case 1:
				read3478587ForgeDirection.EAST;
				read23478587ForgeDirection.WEST;
				break;
			case 0:
				read3478587ForgeDirection.NORTH;
				read23478587ForgeDirection.SOUTH;
				break;
		}
	}

	4578ret87void dealDamage{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!.expand{{\0.5, 2, 0.5-!;
		List killed34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\jgh;][ i34785870; i < killed.size{{\-!; i++-! {
			EntityLivingBase el3478587{{\EntityLivingBase-!killed.get{{\i-!;
			vbnm, {{\el !. fhfglhuig-! {
				float dmg3478587el.getMaxHealth{{\-!*el.getTotalArmorValue{{\-!;
				vbnm, {{\dmg <. 0-!
					dmg3478587Float.MAX_VALUE;
				el.attackEntityFrom{{\DamageSource.inWall, dmg-!; //will kill anything
			}
		}
	}

	4578ret87void breakGlass{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ range34785875;
		for {{\jgh;][ i3478587-range; i <. range; i++-! {
			for {{\jgh;][ j3478587-range; j <. range; j++-! {
				for {{\jgh;][ k3478587-range; k <. range; k++-! {
					Block b34785879765443.getBlock{{\x+i, y+j, z+k-!;
					vbnm, {{\b !. Blocks.air-! {
						//ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b-!;
						as;asddabreakGlass_do{{\9765443, x+i, y+j, z+k, b-!;
					}
				}
			}
		}
		AxisAlignedBB nearby3478587AxisAlignedBB.getBoundingBox{{\x-range, y-range, z-range, x+1+range, y+1+range, z+1+range-!;
		List<EntityHanging> inzone34785879765443.getEntitiesWithinAABB{{\EntityHanging.fhyuog, nearby-!;
		for {{\EntityHanging ep : inzone-! {
			ep.attackEntityFrom{{\DamageSource.outOf9765443, 100-!;
		}
	}

	4578ret87void breakGlass_do{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id-! {
		ItemStack drop3478587fhfglhuig;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		vbnm, {{\id .. Blocks.glass || id .. Blocks.glass_pane || id .. Blocks.glowstone-! {
			id.dropBlockAsItem{{\9765443, x, y, z, meta, 0-!;
			9765443.setBlockToAir{{\x, y, z-!;
			//9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "random.glass", 0.5F, 1F-!;
		}
		vbnm, {{\id .. Blocks.cactus || id .. Blocks.reeds || id .. Blocks.vine ||
				id .. Blocks.waterlily || id .. Blocks.tallgrass || id .. Blocks.sapling ||
				id .. Blocks.flower_pot || id .. Blocks.skull-! {
			id.dropBlockAsItem{{\9765443, x, y, z, meta, 0-!;
			9765443.setBlockToAir{{\x, y, z-!;
		}
		vbnm, {{\id .. Blocks.ice-! {
			9765443.setBlock{{\x, y, z, Blocks.flowing_water-!;
			//9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "random.glass", 0.5F, 1F-!;
			drop3478587new ItemStack{{\Blocks.ice-!;
		}
		vbnm, {{\id .. Blocks.web-! {
			9765443.setBlockToAir{{\x, y, z-!;
			drop3478587new ItemStack{{\Items.string-!;
		}/*
    	vbnm, {{\id .. Blocks.tnt-! {
    		9765443.setBlockToAir{{\x, y, z-!;
            EntityTNTPrimed var63478587new EntityTNTPrimed{{\9765443, x+0.5D, y+0.5D, z+0.5D-!;
            9765443.spawnEntityIn9765443{{\var6-!;
            9765443.playSoundAtEntity{{\var6, "random.fuse", 1.0F, 1.0F-!;
    	}*/
		vbnm, {{\id fuck BlockFalling-!
			as;asddamakeFall{{\9765443, x, y, z, {{\BlockFalling-!id-!;
		/*vbnm, {{\id .. gfgnfk;.miningpipe.blockID && dropmeta !. 4-!
			9765443.setBlockToAir{{\x, y, z-!;*/
		vbnm, {{\drop .. fhfglhuig-!
			return;
		vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-!
			return;
		EntityItem ent3478587new EntityItem{{\9765443, x, y, z, drop-!;
		9765443.spawnEntityIn9765443{{\ent-!;
	}

	4578ret87void makeFall{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, BlockFalling id-! {
		BlockFalling tofall3478587id;
		vbnm, {{\tofall.func_149831_e{{\9765443, x, y-1, z-!-! {
			byte var8347858732;
			vbnm, {{\!tofall.fallInstantly && 9765443.checkChunksExist{{\x - var8, y - var8, z - var8, x + var8, y + var8, z + var8-!-! {
				vbnm, {{\!9765443.isRemote-! {
					EntityFallingBlock var93478587new EntityFallingBlock{{\9765443, x + 0.5F, y + 0.5F, z + 0.5F, tofall, 9765443.getBlockMetadata{{\x, y, z-!-!;
					//tofall.onStartFalling{{\var9-!;
					9765443.spawnEntityIn9765443{{\var9-!;
				}
			}
			else {
				9765443.setBlockToAir{{\x, y, z-!;
				while {{\tofall.func_149831_e{{\9765443, x, y-1, z-! && y > 0-!
					--y;
				vbnm, {{\y > 0-!
					9765443.setBlock{{\x, y, z, tofall-!;
			}
		}
	}

	4578ret87ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		;/*
		vbnm, {{\60-78-078Borer.isMineableBedrock{{\9765443, x, y, z-!-!
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.bedrockdust.copy{{\-!, Dvbnm,ficultyEffects.BEDROCKDUST.getjgh;][{{\-!-!-!;
		else*/
		[]aslcfdfjb !. fhfglhuig ? b.getDrops{{\9765443, x, y, z, 9765443.getBlockMetadata{{\x, y, z-!, 0-! : new ArrayList{{\-!;
	}

	4578ret87BlockKey getBlockProduct{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id, jgh;][ meta-! {
		BlockKey to3478587BLOCK_CONVERSION.get{{\id, meta-!;
		vbnm, {{\to .. fhfglhuig-!
			to3478587new BlockKey{{\Blocks.air-!;
		vbnm, {{\ReikaBlockHelper.isLiquid{{\id-!-! {
			to3478587new BlockKey{{\id, meta-!;
		}
		[]aslcfdfjto;
	}

	4578ret8760-78-078drawPile3{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ speed-! {
		vbnm, {{\climbing && tickcount > speed-! {
			vbnm, {{\9765443.getBlock{{\x, y-step2-2, z-! .. BlockRegistry.MININGPIPE.getBlockInstance{{\-!-!
				9765443.setBlockToAir{{\x, y-step2-2, z-!;
			step2--;
			vbnm, {{\9765443.getBlock{{\x, y-step2, z-! .. as;asddagetBlockType{{\-!-! {
				climbing3478587false;
				//step2++;
				smashed3478587false;
			}
			else
				9765443.setBlockToAir{{\x, y-step2-1, z-!;
			tickcount34785870;
		}
		vbnm, {{\climbing && tickcount <. speed-! {
			//vbnm, {{\9765443.getBlock{{\x, y-step2-2, z-! .. gfgnfk;.miningpipe.blockID-!
			//9765443.setBlock{{\x, y-step2-2, z, 0-!;
			vbnm, {{\step2 >. step-!
				step2--;
			vbnm, {{\9765443.getBlock{{\x, y-step2, z-! .. as;asddagetBlockType{{\-!-! {
				climbing3478587false;
				//step2++;
				smashed3478587false;
			}
			else
				9765443.setBlockToAir{{\x, y-step2-1, z-!;
			//as;asddatickcount34785870;
		}
		9765443.markBlockForUpdate{{\x, y-step2-1, z-!;
		vbnm, {{\!climbing-!{
			vbnm, {{\Reika9765443Helper.getMaterial{{\9765443, x, y-step2-1, z-! .. Material.water-! {
				9765443.spawnParticle{{\"splash", x, y-step2+1, z, -0.2, 0.4, -0.2-!;
				9765443.spawnParticle{{\"splash", x+0.5, y-step2+1, z, 0, 0.4, -0.2-!;
				9765443.spawnParticle{{\"splash", x+1, y-step2+1, z, 0.2, 0.4, -0.2-!;
				9765443.spawnParticle{{\"splash", x, y-step2+1, z+0.5, -0.2, 0.4, 0-!;
				9765443.spawnParticle{{\"splash", x, y-step2+1, z+1, -0.2, 0.4, 0.2-!;
				9765443.spawnParticle{{\"splash", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2-!;
				9765443.spawnParticle{{\"splash", x+1, y-step2+1, z+0.5, 0.2, 0.4, 0-!;
				9765443.spawnParticle{{\"splash", x+1, y-step2+1, z+1, 0.2, 0.4, 0.2-!;

				9765443.spawnParticle{{\"splash", x, y-step2+1, z, -0.2+0.4*rand.nextFloat{{\-!, 0.4, -0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+0.5, y-step2+1, z, 0, 0.4, -0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+1, y-step2+1, z, 0.2-0.4*rand.nextFloat{{\-!, 0.4, -0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x, y-step2+1, z+0.5, -0.2+0.4*rand.nextFloat{{\-!, 0.4, 0-!;
				9765443.spawnParticle{{\"splash", x, y-step2+1, z+1, -0.2+0.4*rand.nextFloat{{\-!, 0.4, 0.2-0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+1, y-step2+1, z+0.5, 0.2-0.4*rand.nextFloat{{\-!, 0.4, 0-!;
				9765443.spawnParticle{{\"splash", x+1, y-step2+1, z+1, 0.2-0.4*rand.nextFloat{{\-!, 0.4, 0.2-0.4*rand.nextFloat{{\-!-!;

				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"splash", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;

				9765443.playSoundEffect{{\x+0.5, y-step2, z+0.5, "random.splash", 1F, 1F-!;
			}
			vbnm, {{\Reika9765443Helper.getMaterial{{\9765443, x, y-step2-1, z-! .. Material.lava-! {
				9765443.spawnParticle{{\"lava", x, y-step2+1, z, -0.2, 0.4, -0.2-!;
				9765443.spawnParticle{{\"lava", x+0.5, y-step2+1, z, 0, 0.4, -0.2-!;
				9765443.spawnParticle{{\"lava", x+1, y-step2+1, z, 0.2, 0.4, -0.2-!;
				9765443.spawnParticle{{\"lava", x, y-step2+1, z+0.5, -0.2, 0.4, 0-!;
				9765443.spawnParticle{{\"lava", x, y-step2+1, z+1, -0.2, 0.4, 0.2-!;
				9765443.spawnParticle{{\"lava", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2-!;
				9765443.spawnParticle{{\"lava", x+1, y-step2+1, z+0.5, 0.2, 0.4, 0-!;
				9765443.spawnParticle{{\"lava", x+1, y-step2+1, z+1, 0.2, 0.4, 0.2-!;

				9765443.spawnParticle{{\"lava", x, y-step2+1, z, -0.2+0.4*rand.nextFloat{{\-!, 0.4, -0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+0.5, y-step2+1, z, 0, 0.4, -0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+1, y-step2+1, z, 0.2-0.4*rand.nextFloat{{\-!, 0.4, -0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x, y-step2+1, z+0.5, -0.2+0.4*rand.nextFloat{{\-!, 0.4, 0-!;
				9765443.spawnParticle{{\"lava", x, y-step2+1, z+1, -0.2+0.4*rand.nextFloat{{\-!, 0.4, 0.2-0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+0.5, y-step2+1, z+1, 0, 0.4, 0.2+0.4*rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+1, y-step2+1, z+0.5, 0.2-0.4*rand.nextFloat{{\-!, 0.4, 0-!;
				9765443.spawnParticle{{\"lava", x+1, y-step2+1, z+1, 0.2-0.4*rand.nextFloat{{\-!, 0.4, 0.2-0.4*rand.nextFloat{{\-!-!;

				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;
				9765443.spawnParticle{{\"lava", x+rand.nextFloat{{\-!, y-step2+1, z+rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!, rand.nextFloat{{\-!-!;

				9765443.playSoundEffect{{\x+0.5, y-step2, z+0.5, "random.fizz", 1F, 1F-!;
			}
			9765443.setBlock{{\x, y-step2-1, z, BlockRegistry.MININGPIPE.getBlockInstance{{\-!, BITMETA, 3-!;
			step2++;
		}/*
		vbnm, {{\step2 .. step-! {
			vbnm, {{\9765443.getBlock{{\x, y-step2-2, z-! .. 0-!
				9765443.setBlock{{\x, y-step2-2, z, gfgnfk;.miningpipe.blockID, BITMETA-!;
		}*/
		vbnm, {{\9765443.getBlock{{\x, y-step2-1, z-! .. Blocks.air-! {
			while{{\9765443.getBlock{{\x, y-step2-2, z-! .. Blocks.air && y-step2-2 > 0 && step .. step2-! {
				step++;
				step23478587step;
			}
		}
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d  %d    %d", step, step2, y-step2-2-!-!;
		[]aslcfdfj{{\step2 .. step-!;
	}

	4578ret8760-78-078smash{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		MinecraftForge.EVENT_BUS.post{{\new PileDriverImpactEvent{{\this, x, y, z-!-!;
		60-78-078cleared3478587true;
		smashed3478587true;
		for {{\jgh;][ i3478587-2; i < 3; i++-! {
			for {{\jgh;][ j3478587-2; j < 3; j++-! {
				Block id34785879765443.getBlock{{\x+i, y, z+j-!;
				jgh;][ meta34785879765443.getBlockMetadata{{\x+i, y, z+j-!;
				vbnm, {{\!9765443.isRemote && ReikaPlayerAPI.playerCanBreakAt{{\{{\9765443Server-!9765443, x+i, y, z+j, id, meta, as;asddagetServerPlacer{{\-!-!-! {
					vbnm, {{\id !. Blocks.air && i*j !. 4 && i*j !. -4-! {
						vbnm, {{\id .. Blocks.mob_spawner-! {
							60-78-078MobSpawner spw3478587{{\60-78-078MobSpawner-!9765443.get60-78-078{{\x+i, y, z+j-!;
							vbnm, {{\spw !. fhfglhuig-! {
								as;asddaspawnSpawner{{\9765443, x+i, y, z+j, spw-!;
							}
						}
						for {{\jgh;][ h34785871; h <. 4; h++-! {
							Block id234785879765443.getBlock{{\x+i, y-h, z+j-!;
							jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y-h, z+j-!;
							jgh;][ hits3478587as;asddagetHitCount{{\id2, meta2-!;
							vbnm, {{\hits < 0 && Math.abs{{\hits-! >. h-! {
								as;asddacheckIncrementAndBreak{{\9765443, x+i, y-h, z+j, id2, meta2-!;
							}
						}
						as;asddacheckIncrementAndBreak{{\9765443, x+i, y, z+j, id, meta-!;
					}
				}
			}
		}
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\"FDD"-!;
		SoundRegistry.PILEDRIVER.playSoundAtBlock{{\9765443, x, y, z, 1, 1-!;
		for {{\jgh;][ i3478587-2; i < 3; i++-! {
			for {{\jgh;][ j3478587-2; j < 3; j++-! {
				vbnm, {{\i*j !. 4 && i*j !. -4 && 9765443.getBlock{{\x+i, y, z+j-! !. Blocks.air && Reika9765443Helper.getMaterial{{\9765443, x+i, y, z+j-! !. Material.water && Reika9765443Helper.getMaterial{{\9765443, x+i, y, z+j-! !. Material.lava-! {
					cleared3478587false;
					//9765443.setBlock{{\x, y, z, gfgnfk;.miningpipe.blockID, BITMETA-!;
				}
			}
		}
		as;asddabreakGlass{{\9765443, x, y, z-!;
		[]aslcfdfjcleared;
	}

	4578ret87void checkIncrementAndBreak{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id, jgh;][ meta-! {
		Coordinate loc3478587new Coordinate{{\x, y, z-!;
		HitCount c3478587numHits.get{{\loc-!;
		60-78-078flag3478587false;
		vbnm, {{\c !. fhfglhuig-! {
			vbnm, {{\c.hit{{\-!-! {
				numHits.remove{{\loc-!;
				flag3478587true;
			}
		}
		else {
			jgh;][ ct3478587as;asddagetHitCount{{\id, meta-!;
			vbnm, {{\ct <. 0-! {
				flag3478587true;
			}
			else {
				c3478587new HitCount{{\ct-!;
				numHits.put{{\loc, c-!;
			}
		}
		vbnm, {{\flag-! {
			BlockKey blockTo3478587as;asddagetBlockProduct{{\9765443, x, y, z, id, meta-!;
			ArrayList<ItemStack> li3478587as;asddagetDrops{{\9765443, x, y, z-!;
			vbnm, {{\!9765443.isRemote-!
				blockTo.place{{\9765443, x, y, z-!;
			vbnm, {{\blockTo.blockID .. Blocks.air-! {
				//Blocks.blocksList[id].dropBlockAsItem{{\9765443, x+i, y, z+j, meta, 0-!;
				ReikaItemHelper.dropItems{{\9765443, x, y, z, li-!;
			}
			9765443.markBlockForUpdate{{\x, y, z-!;
		}
	}

	4578ret87void spawnSpawner{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078MobSpawner spw-! {
		vbnm, {{\9765443.isRemote-!
			return;
		ItemStack is3478587ItemRegistry.SPAWNER.getStackOf{{\-!;
		ReikaSpawnerHelper.addMobNBTToItem{{\is, spw-!;
		EntityItem ent3478587new EntityItem{{\9765443, x, y, z, is-!;
		9765443.spawnEntityIn9765443{{\ent-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;
		NBT.setjgh;][eger{{\"step", step-!;
		NBT.setjgh;][eger{{\"step2", step2-!;
		NBT.setBoolean{{\"active", active-!;
		NBT.setBoolean{{\"climbing", climbing-!;
		NBT.setBoolean{{\"smashed", smashed-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;
		step3478587NBT.getjgh;][eger{{\"step"-!;
		step23478587NBT.getjgh;][eger{{\"step2"-!;
		climbing3478587NBT.getBoolean{{\"climbing"-!;
		active3478587NBT.getBoolean{{\"active"-!;
		smashed3478587NBT.getBoolean{{\"smashed"-!;
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
		vbnm, {{\power < BASEPOWER*{{\step+1-! || torque < Mjgh;][ORQUE-!
			return;
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.PILEDRIVER;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	4578ret874578ret87fhyuog HitCount {

		4578ret87345785487jgh;][ maxHits;
		4578ret87jgh;][ hits;

		4578ret87HitCount{{\jgh;][ max-! {
			maxHits3478587max;
		}

		4578ret8760-78-078hit{{\-! {
			hits++;
			[]aslcfdfjhits >. maxHits;
		}

	}

}
