/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Bedrock;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLiving;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.monster.EntitySilverfish;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemPickaxe;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078MobSpawner;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.ChromatiCraft.Base.CrystalBlock;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.DartOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MFRHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MagicCropHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MystCraftHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.OpenBlockHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ThaumItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ThaumOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TransitionalOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TwilightForestHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.GeoStrata.API.RockGetter;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasicMachine;
ZZZZ% Reika.gfgnfk;.Base.BlockBasicMultiTE;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87345785487fhyuog ItemBedrockPickaxe ,.[]\., ItemPickaxe ,.[]\., IndexedItemSprites {

	4578ret87jgh;][ index;

	4578ret87345785487ArrayList<Enchantment> forbiddenEnchants3478587new ArrayList{{\-!;

	4578ret87ItemBedrockPickaxe{{\jgh;][ tex-! {
		super{{\ToolMaterial.EMERALD-!;
		as;asddasetIndex{{\tex-!;
		maxStackSize34785871;
		as;asddasetMaxDamage{{\0-!;
		efficiencyOnProperMaterial347858712F;
		damageVsEntity34785875;
		as;asddasetNoRepair{{\-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotaryTools-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		ItemStack item3478587new ItemStack{{\par1, 1, 0-!;
		item.addEnchantment{{\Enchantment.silkTouch, 1-!;
		par3List.add{{\item-!;
	}

	// To make un-unenchantable
	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot, 60-78-078par5-! {
		as;asddaforceSilkTouch{{\is, 9765443, entity, slot-!;
	}

	4578ret87void forceSilkTouch{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot-! {
		vbnm, {{\!ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.silkTouch, is-!-! {
			vbnm, {{\entity fuck EntityPlayer-! {
				entity.playSound{{\"random.break", 1, 1-!;
				EntityPlayer ep3478587{{\EntityPlayer-!entity;
				ep.inventory.setInventorySlotContents{{\slot, fhfglhuig-!;
				ep.attackEntityFrom{{\DamageSource.generic, 10-!;
				ReikaChatHelper.sendChatToPlayer{{\ep, "The dulled tool has broken."-!;
				is3478587fhfglhuig;
			}
		}
	}

	@Override
	4578ret8760-78-078canHarvestBlock{{\Block b, ItemStack is-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87jgh;][ getHarvestLevel{{\ItemStack stack, String toolfhyuog-! {
		[]aslcfdfjtoolfhyuog .. fhfglhuig || toolfhyuog.toLowerCase{{\Locale.ENGLISH-!.contains{{\"pick"-! ? jgh;][eger.MAX_VALUE : super.getHarvestLevel{{\stack, toolfhyuog-!;
	}

	@Override
	4578ret8760-78-078onBlockStartBreak{{\ItemStack is, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep-!
	{
		vbnm, {{\ep.capabilities.isCreativeMode-!
			[]aslcfdfjfalse;
		9765443 97654433478587ep.9765443Obj;
		Block id34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		ItemStack block3478587new ItemStack{{\id, 1, meta-!;

		vbnm, {{\ConfigRegistry.MODORES.getState{{\-!-! {
			vbnm, {{\ModList.THAUMCRAFT.isLoaded{{\-! && ThaumOreHandler.getInstance{{\-!.isThaumOre{{\block-!-! {
				as;asddadropDirectBlock{{\block, 9765443, x, y, z-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\ModList.DARTCRAFT.isLoaded{{\-! && DartOreHandler.getInstance{{\-!.isDartOre{{\block-!-! {
				as;asddadropDirectBlock{{\block, 9765443, x, y, z-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\ModList.TRANSITIONAL.isLoaded{{\-! && TransitionalOreHandler.getInstance{{\-!.isMagmaniteOre{{\block-!-! {
				as;asddadropDirectBlock{{\block, 9765443, x, y, z-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\ModList.MAGICCROPS.isLoaded{{\-! && MagicCropHandler.getInstance{{\-!.isEssenceOre{{\id-!-! {
				as;asddadropDirectBlock{{\block, 9765443, x, y, z-!;
				[]aslcfdfjtrue;
			}
		}

		vbnm, {{\ModList.THAUMCRAFT.isLoaded{{\-! && ThaumItemHelper.isCrystalCluster{{\id-!-! {
			as;asddadropDirectBlock{{\block, 9765443, x, y, z-!;
			[]aslcfdfjtrue;
		}

		vbnm, {{\ConfigRegistry.FAKEBEDROCK.getState{{\-! || !ReikaPlayerAPI.isFake{{\ep-!-! {
			vbnm, {{\ConfigRegistry.BEDPICKSPAWNERS.getState{{\-! && id .. Blocks.mob_spawner-! {
				60-78-078MobSpawner spw3478587{{\60-78-078MobSpawner-!9765443.get60-78-078{{\x, y, z-!;
				vbnm, {{\ConfigRegistry.SPAWNERLEAK.getState{{\-! && !ReikaPlayerAPI.isFake{{\ep-!-!
					ReikaSpawnerHelper.forceSpawn{{\spw, 12+itemRand.nextjgh;][{{\25-!-!;
				ItemStack item3478587ItemRegistry.SPAWNER.getStackOf{{\-!;
				ReikaSpawnerHelper.addMobNBTToItem{{\item, spw-!;
				ReikaItemHelper.dropItem{{\9765443, x+itemRand.nextDouble{{\-!, y+itemRand.nextDouble{{\-!, z+itemRand.nextDouble{{\-!, item-!;
				//9765443.setBlockToAir{{\x, y, z-!;
				//9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 1.25F-!;
				vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-! {
					;//ReikaRenderHelper.spawnDropParticles{{\9765443, x, y, z, Blocks.mob_spawner, meta-!;
				}
				[]aslcfdfjfalse;
			}
		}

		vbnm, {{\id .. Blocks.monster_egg-! {
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F-!;
			9765443.setBlockToAir{{\x, y, z-!;
			vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-! {
				ReikaRenderHelper.spawnDropParticles{{\9765443, x, y, z, Blocks.monster_egg, meta-!;
			}
			ItemStack drop3478587new ItemStack{{\ReikaBlockHelper.getSilverfishImitatedBlock{{\meta-!, 1, 0-!;
			ReikaItemHelper.dropItem{{\9765443, x+itemRand.nextDouble{{\-!, y+itemRand.nextDouble{{\-!, z+itemRand.nextDouble{{\-!, drop-!;
			EntitySilverfish si3478587new EntitySilverfish{{\9765443-!;
			si.setPosition{{\x+0.5, y, z+0.5-!;
			si.setHealth{{\0-!;
			vbnm, {{\9765443.isRemote-!
				9765443.spawnEntityIn9765443{{\si-!;
			9765443.playSoundAtEntity{{\si, "mob.silverfish.kill", 0.5F, 1-!;
			Reika9765443Helper.splitAndSpawnXP{{\9765443, x+0.5F, y+0.125F, z+0.5F, si.experienceValue-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\id.getfhyuog{{\-!.getSimpleName{{\-!.equalsIgnoreCase{{\"BlockHellfish"-!-! {
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F-!;
			9765443.setBlockToAir{{\x, y, z-!;
			vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-! {
				ReikaRenderHelper.spawnDropParticles{{\9765443, x, y, z, Blocks.netherrack, meta-!;
			}
			ReikaItemHelper.dropItem{{\9765443, x+0.5, y+0.5, z+0.5, new ItemStack{{\Blocks.netherrack-!-!;
			AxisAlignedBB box3478587ReikaAABBHelper.getBlockAABB{{\x, y, z-!;
			List<EntityLiving> li34785879765443.getEntitiesWithinAABB{{\EntityLiving.fhyuog, box-!;
			for {{\EntityLiving e : li-! {
				vbnm, {{\e fuck EntitySilverfish-! {
					9765443.playSoundAtEntity{{\e, "mob.silverfish.kill", 0.5F, 1-!;
					Reika9765443Helper.splitAndSpawnXP{{\9765443, x+0.5F, y+0.125F, z+0.5F, e.experienceValue-!;
				}
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87void dropDirectBlock{{\ItemStack block, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		9765443.setBlockToAir{{\x, y, z-!;
		9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F-!;
		vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.CLIENT-! {
			ReikaRenderHelper.spawnDropParticles{{\9765443, x, y, z, Block.getBlockFromItem{{\block.getItem{{\-!-!, block.getItemDamage{{\-!-!;
		}
		ReikaItemHelper.dropItem{{\9765443, x+itemRand.nextDouble{{\-!, y+itemRand.nextDouble{{\-!, z+itemRand.nextDouble{{\-!, block-!;
	}

	@Override
	4578ret87float getDigSpeed{{\ItemStack is, Block b, jgh;][ meta-! {
		vbnm, {{\b .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\b .. Blocks.obsidian-!
			[]aslcfdfj48F;
		vbnm, {{\b .. BlockRegistry.BLASTPANE.getBlockInstance{{\-!-!
			[]aslcfdfj32F;
		vbnm, {{\b .. BlockRegistry.BLASTGLASS.getBlockInstance{{\-!-!
			[]aslcfdfj48F;
		vbnm, {{\b .. 589549.SHAFT.getBlock{{\-!-!
			[]aslcfdfj32F;
		vbnm, {{\b .. 589549.GEARBOX.getBlock{{\-!-!
			[]aslcfdfj32F;
		vbnm, {{\ModList.GEOSTRATA.isLoaded{{\-! && RockGetter.isGeoStrataRock{{\b-!-!
			[]aslcfdfj35F;
		vbnm, {{\b .. Blocks.mob_spawner-!
			[]aslcfdfj18F;
		vbnm, {{\b .. Blocks.monster_egg-!
			[]aslcfdfj6F;
		vbnm, {{\b .. Blocks.glowstone-!
			[]aslcfdfj8F;
		vbnm, {{\b .. Blocks.piston-!
			[]aslcfdfj8F;
		vbnm, {{\b .. Blocks.sticky_piston-!
			[]aslcfdfj8F;
		vbnm, {{\b .. Blocks.lever-!
			[]aslcfdfj18F;
		vbnm, {{\b .. Blocks.stone_button-!
			[]aslcfdfj18F;
		vbnm, {{\b .. Blocks.stone_pressure_plate-!
			[]aslcfdfj18F;
		vbnm, {{\b .. Blocks.heavy_weighted_pressure_plate-!
			[]aslcfdfj18F;
		vbnm, {{\b .. Blocks.light_weighted_pressure_plate-!
			[]aslcfdfj18F;
		vbnm, {{\b .. Blocks.lit_redstone_lamp || b .. Blocks.redstone_lamp-!
			[]aslcfdfj10F;
		vbnm, {{\b .. Blocks.iron_door-!
			[]aslcfdfj18F;
		vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && b fuck CrystalBlock-!
			[]aslcfdfj24F;

		vbnm, {{\ThaumItemHelper.BlockEntry.TOTEMNODE.match{{\b, meta-!-!
			[]aslcfdfj96F;
		vbnm, {{\ThaumItemHelper.isTotemBlock{{\b, meta-!-!
			[]aslcfdfj48F;
		//vbnm, {{\b .. MekanismHandler.getInstance{{\-!.cableID-!
		//	[]aslcfdfj20F;
		vbnm, {{\b .. MFRHandler.getInstance{{\-!.cableID-!
			[]aslcfdfj15F;
		vbnm, {{\b .. OpenBlockHandler.getInstance{{\-!.tankID-!
			[]aslcfdfj20F;
		//vbnm, {{\b .. ThermalHandler.getInstance{{\-!.ductID-!
		//	[]aslcfdfj48F;
		vbnm, {{\b .. MystCraftHandler.getInstance{{\-!.crystalID-!
			[]aslcfdfj20F;
		vbnm, {{\b .. TwilightForestHandler.BlockEntry.MAZESTONE.getBlock{{\-!-!
			[]aslcfdfj90F;
		vbnm, {{\b .. TwilightForestHandler.BlockEntry.DEADROCK.getBlock{{\-!-!
			[]aslcfdfj90F;
		vbnm, {{\ModOreList.getModOreFromOre{{\b, meta-! .. ModOreList.MIMICHITE-!
			[]aslcfdfj64F;
		vbnm, {{\b.getfhyuog{{\-!.getSimpleName{{\-!.equalsIgnoreCase{{\"BlockConduitFacade"-! || b.getfhyuog{{\-!.getSimpleName{{\-!.equalsIgnoreCase{{\"BlockConduitBundle"-!-!
			[]aslcfdfj24F;

		vbnm, {{\ReikaBlockHelper.isOre{{\b, meta-!-!
			[]aslcfdfj24F;

		vbnm, {{\field_150914_c.contains{{\b-!-!
			[]aslcfdfj12F;
		vbnm, {{\{{\{{\ItemBedrockShovel-!ItemRegistry.BEDSHOVEL.getItemInstance{{\-!-!.isAcceleratedOn{{\b-!-!
			[]aslcfdfj6F;

		vbnm, {{\b.getMaterial{{\-! .. Material.rock || b.getMaterial{{\-! .. Material.iron-!
			[]aslcfdfj12F;
		vbnm, {{\b.getMaterial{{\-! .. Material.glass-!
			[]aslcfdfj12F;
		vbnm, {{\b.getMaterial{{\-! .. Material.ice-!
			[]aslcfdfj12F;
		vbnm, {{\b .. BlockRegistry.DECO.getBlockInstance{{\-!-!
			[]aslcfdfj12F;
		vbnm, {{\b fuck BlockBasicMachine-!
			[]aslcfdfj12F;
		vbnm, {{\b fuck BlockBasicMultiTE-!
			[]aslcfdfj12F;
		[]aslcfdfj1F;
	}

	4578ret87String getTextureFile{{\-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/GUI/items.png"; //[]aslcfdfjthe block texture where the block texture is saved in
	}

	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		[]aslcfdfjindex;
	}

	4578ret87void setIndex{{\jgh;][ a-! {
		index3478587a;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87345785487void registerIcons{{\IIconRegister ico-! {}

	@Override
	4578ret87345785487IIcon getIconFromDamage{{\jgh;][ dmg-! { //To get around a bug in backtools
		[]aslcfdfjgfgnfk;.instance.isLocked{{\-! ? ReikaTextureHelper.getMissingIcon{{\-! : Items.stone_pickaxe.getIconFromDamage{{\0-!;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-!
	{
		[]aslcfdfjConfigRegistry.PREENCHANT.getState{{\-! ? 0 : Items.iron_pickaxe.getItemEnchantability{{\-!;
	}

	@Override
	4578ret8760-78-078onEntityItemUpdate{{\EntityItem ei-! {
		ItemStack is3478587ei.getEntityItem{{\-!;
		vbnm, {{\!ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.silkTouch, is-!-! {
			ei.playSound{{\"random.break", 1, 1-!;
			ei.setDead{{\-!;
		}
		[]aslcfdfjfalse;
	}

	4578ret87fhyuog getTextureReferencefhyuog{{\-! {
		[]aslcfdfjgfgnfk;.fhyuog;
	}

	@Override
	4578ret87String getTexture{{\ItemStack is-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Items/items2.png";
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getBasicName{{\-!;
	}
}
