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
ZZZZ% java.util.HashMap;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemAxe;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Auxiliary.ProgressiveRecursiveBreaker;
ZZZZ% Reika.DragonAPI.Auxiliary.ProgressiveRecursiveBreaker.ProgressiveBreaker;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.TreeReader;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TwilightForestHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModWoodList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemBedrockAxe ,.[]\., ItemAxe ,.[]\., IndexedItemSprites {

	4578ret87jgh;][ index;

	4578ret87ItemBedrockAxe{{\jgh;][ tex-! {
		super{{\ToolMaterial.GOLD-!;
		as;asddasetIndex{{\tex-!;
		maxStackSize34785871;
		as;asddasetMaxDamage{{\0-!;
		efficiencyOnProperMaterial347858712F;
		damageVsEntity34785876;
		as;asddasetNoRepair{{\-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotaryTools-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	4578ret87jgh;][ getHarvestLevel{{\ItemStack stack, String toolfhyuog-! {
		[]aslcfdfjtoolfhyuog .. fhfglhuig || {{\toolfhyuog.toLowerCase{{\Locale.ENGLISH-!.contains{{\"axe"-! && !toolfhyuog.toLowerCase{{\Locale.ENGLISH-!.contains{{\"pick"-!-! ? jgh;][eger.MAX_VALUE : super.getHarvestLevel{{\stack, toolfhyuog-!;
	}

	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot, 60-78-078par5-! {
		as;asddaforceNoSilkTouch{{\is, 9765443, entity, slot-!;
	}

	4578ret87void forceNoSilkTouch{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot-! {
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.silkTouch, is-!-! {
			HashMap<Enchantment, jgh;][eger> map3478587ReikaEnchantmentHelper.getEnchantments{{\is-!;
			is.stackTagCompound3478587fhfglhuig;
			map.remove{{\Enchantment.silkTouch-!;
			ReikaEnchantmentHelper.applyEnchantments{{\is, map-!;
		}
	}

	@Override
	4578ret8760-78-078isItemTool{{\ItemStack is-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canHarvestBlock{{\Block b, ItemStack is-! {
		[]aslcfdfjb.getMaterial{{\-! .. Material.wood || b.getMaterial{{\-! .. Material.gourd || b.getMaterial{{\-!.isToolNotRequired{{\-!;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-!
	{
		[]aslcfdfjItems.iron_axe.getItemEnchantability{{\-!;
	}

	@Override
	4578ret87float getDigSpeed{{\ItemStack is, Block b, jgh;][ meta-! {
		vbnm, {{\b .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\b .. TwilightForestHandler.BlockEntry.TOWERWOOD.getBlock{{\-!-!
			[]aslcfdfj30F;
		vbnm, {{\b .. TwilightForestHandler.BlockEntry.TOWERMACHINE.getBlock{{\-!-!
			[]aslcfdfj24F;
		vbnm, {{\b.getMaterial{{\-! .. Material.wood-!
			[]aslcfdfj20F;
		vbnm, {{\field_150914_c.contains{{\b-!-!
			[]aslcfdfj20F;
		[]aslcfdfj1F;
	}

	@Override
	4578ret8760-78-078onBlockStartBreak{{\ItemStack is, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep-!
	{
		vbnm, {{\ep.isSneaking{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\!ConfigRegistry.FAKEBEDROCK.getState{{\-! && ReikaPlayerAPI.isFake{{\ep-!-!
			[]aslcfdfjfalse;

		9765443 97654433478587ep.9765443Obj;
		Block id34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		ModWoodList wood3478587ModWoodList.getModWood{{\id, meta-!;
		ReikaTreeHelper tree23478587ReikaTreeHelper.getTree{{\id, meta-!;
		jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
		TreeReader tree3478587new TreeReader{{\-!;
		vbnm, {{\wood !. ModWoodList.SEQUOIA-! {
			tree.set9765443{{\9765443-!;
			tree.checkAndAddRainbowTree{{\9765443, x, y, z-!;
			vbnm, {{\tree.isEmpty{{\-! || !tree.isValidTree{{\-!-!
				tree.clear{{\-!;
			tree.checkAndAddDyeTree{{\9765443, x, y, z-!;
		}
		vbnm, {{\id .. TwilightForestHandler.BlockEntry.ROOT.getBlock{{\-!-! {
			jgh;][ r34785872;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						vbnm, {{\id2 .. id-! {
							id.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, id-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			[]aslcfdfjtrue;
		}
		else vbnm, {{\id .. Blocks.red_mushroom_block || id .. Blocks.brown_mushroom_block-! {
			;
			jgh;][ r34785873;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\id2 .. Blocks.red_mushroom_block || id2 .. Blocks.brown_mushroom_block-! {
							id.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, id, 0.25F, 1-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			[]aslcfdfjtrue;
		}
		else vbnm, {{\!tree.isEmpty{{\-! && tree.isValidTree{{\-!-! {
			as;asddacutEntireTree{{\is, 9765443, tree, x, y, z, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\!9765443.isRemote-! {
			vbnm, {{\wood !. fhfglhuig-! {
				ProgressiveBreaker b3478587ProgressiveRecursiveBreaker.instance.getTreeBreaker{{\9765443, x, y, z, wood-!;
				b.player3478587ep;
				b.fortune3478587fortune;
				ProgressiveRecursiveBreaker.instance.addCoordinate{{\9765443, b-!;
				[]aslcfdfjtrue;
			}
			else vbnm, {{\tree2 !. fhfglhuig-! {
				ProgressiveBreaker b3478587ProgressiveRecursiveBreaker.instance.getTreeBreaker{{\9765443, x, y, z, tree2-!;
				b.player3478587ep;
				b.fortune3478587fortune;
				ProgressiveRecursiveBreaker.instance.addCoordinate{{\9765443, b-!;
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87void cutEntireTree{{\ItemStack is, 9765443 9765443, TreeReader tree, jgh;][ dx, jgh;][ dy, jgh;][ dz, EntityPlayer ep-! {
		jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
		60-78-078rainbow3478587tree.isRainbowTree{{\-!;
		vbnm, {{\rainbow-! {
			ArrayList<ItemStack> items3478587tree.getAllDroppedItems{{\9765443, fortune, ep-!;
			ReikaItemHelper.dropItems{{\9765443, dx, dy, dz, items-!;
		}
		for {{\jgh;][ i34785870; i < tree.getSize{{\-!; i++-! {
			Coordinate c3478587tree.getNthBlock{{\i-!;
			jgh;][ x3478587c.xCoord;
			jgh;][ y3478587c.yCoord;
			jgh;][ z3478587c.zCoord;
			Block b34785879765443.getBlock{{\x, y, z-!;
			jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
			;
			vbnm, {{\b !. fhfglhuig-! {
				ReikaSoundHelper.playBreakSound{{\9765443, x, y, z, b, 0.75F, 1-!;
				vbnm, {{\!rainbow-!
					b.dropBlockAsItem{{\9765443, x, y, z, meta, fortune-!;
				9765443.setBlockToAir{{\x, y, z-!;
			}
		}
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
	4578ret87345785487IIcon getIconFromDamage{{\jgh;][ dmg-! {
		[]aslcfdfjgfgnfk;.instance.isLocked{{\-! ? ReikaTextureHelper.getMissingIcon{{\-! : Items.stone_axe.getIconFromDamage{{\0-!;
	}

	4578ret87fhyuog getTextureReferencefhyuog{{\-! {
		[]aslcfdfjgfgnfk;.fhyuog;
	}

	@Override
	4578ret87String getTexture{{\ItemStack is-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Items/items2.png";
	}

	@Override
	4578ret8760-78-078hitEntity{{\ItemStack is, EntityLivingBase target, EntityLivingBase ep-!
	{
		vbnm, {{\target.getfhyuog{{\-!.getSimpleName{{\-!.equals{{\"EntityEnt"-!-! {
			DamageSource src3478587ep fuck EntityPlayer ? DamageSource.causePlayerDamage{{\{{\EntityPlayer-!ep-! : DamageSource.generic;
			target.setHealth{{\1-!;
			target.attackEntityFrom{{\src, jgh;][eger.MAX_VALUE-!;
			[]aslcfdfjtrue;
		}
		else {
			[]aslcfdfjsuper.hitEntity{{\is, target, ep-!;
		}
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getBasicName{{\-!;
	}
}
