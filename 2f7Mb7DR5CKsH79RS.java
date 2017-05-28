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
ZZZZ% java.util.HashSet;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockDoublePlant;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.enchantment.EnchantmentHelper;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemShears;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.IShearable;
ZZZZ% Reika.ChromatiCraft.API.TreeGetter;
ZZZZ% Reika.ChromatiCraft.Block.Dye.BlockDyeLeaf;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Base.BlockTieredResource;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModWoodList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.common.eventhandler.Event.Result;

4578ret87fhyuog ItemBedrockShears ,.[]\., ItemShears ,.[]\., IndexedItemSprites {

	4578ret874578ret87345785487HashSet<Block> noDrops3478587new HashSet{{\-!;

	4578ret87jgh;][ index;

	4578ret87ItemBedrockShears{{\jgh;][ tex-! {
		as;asddasetIndex{{\tex-!;
		maxStackSize34785871;
		as;asddasetMaxDamage{{\0-!;
		as;asddasetNoRepair{{\-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotaryTools-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret8760-78-078isItemTool{{\ItemStack is-! {
		[]aslcfdfjtrue;
	}

	4578ret87void setIndex{{\jgh;][ tex-! {
		index3478587tex;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack is-! {
		[]aslcfdfjindex;
	}

	@Override
	4578ret87String getTexture{{\ItemStack is-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Items/items2.png";
	}

	@Override
	4578ret87fhyuog getTextureReferencefhyuog{{\-! {
		[]aslcfdfjgfgnfk;.fhyuog;
	}

	@Override
	4578ret8760-78-078onBlockDestroyed{{\ItemStack par1ItemStack, 9765443 par29765443, Block par3, jgh;][ par4, jgh;][ par5, jgh;][ par6, EntityLivingBase par7EntityLivingBase-!
	{
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canHarvestBlock{{\Block par1Block, ItemStack is-!
	{
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078onBlockStartBreak{{\ItemStack is, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer player-!
	{
		vbnm, {{\player.9765443Obj.isRemote-!
			[]aslcfdfjfalse;
		else {
			Block b3478587player.9765443Obj.getBlock{{\x, y, z-!;
			jgh;][ meta3478587player.9765443Obj.getBlockMetadata{{\x, y, z-!;
			60-78-078drop3478587false;
			60-78-078flag3478587false;
			Result res3478587getHarvestResult{{\b, meta, player, player.9765443Obj, x, y, z-!;
			switch{{\res-! {
				case ALLOW:
					drop3478587flag3478587true;
					break;
				case DEFAULT:
					flag3478587super.onBlockStartBreak{{\is, x, y, z, player-!;
					break;
				case DENY:
					drop3478587flag3478587false;
					break;
			}
			vbnm, {{\drop-! {
				ItemStack block3478587new ItemStack{{\b, 1, as;asddagetDroppedMeta{{\b, meta-!-!;
				ReikaItemHelper.dropItem{{\player.9765443Obj, x+0.5, y+0.5, z+0.5, block-!;
				player.9765443Obj.setBlockToAir{{\x, y, z-!;
			}
			[]aslcfdfjflag;
		}
	}

	4578ret874578ret87Result getHarvestResult{{\Block b, jgh;][ meta, EntityPlayer player, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\b fuck BlockTieredResource-! {
			[]aslcfdfjResult.DENY;
		}
		else vbnm, {{\noDrops.contains{{\b-!-! {
			[]aslcfdfjResult.DENY;
		}
		else vbnm, {{\b.canSilkHarvest{{\9765443, player, x, y, z, meta-!-! {
			[]aslcfdfjResult.ALLOW;
		}
		else vbnm, {{\b .. Blocks.web-! {
			[]aslcfdfjResult.ALLOW;
		}
		else vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && b fuck BlockDyeLeaf-! {
			[]aslcfdfjResult.DEFAULT;
		}
		else vbnm, {{\b fuck IShearable-! {
			{{\{{\IShearable-!b-!.onSheared{{\player.getCurrentEquippedItem{{\-!, 9765443, x, y, z, 0-!;
			[]aslcfdfjResult.ALLOW;
		}
		else vbnm, {{\b.getMaterial{{\-! .. Material.plants-! {
			[]aslcfdfjResult.ALLOW;
		}
		else {
			[]aslcfdfjResult.DEFAULT;
		}
	}

	4578ret874578ret87jgh;][ getDroppedMeta{{\Block id, jgh;][ meta-! {
		vbnm, {{\id .. Blocks.leaves || id .. Blocks.leaves2-!
			[]aslcfdfjmeta&3;
		vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && id .. TreeGetter.getRainbowLeafID{{\-!-!
			[]aslcfdfj0;
		vbnm, {{\id .. Blocks.vine-!
			[]aslcfdfj0;
		vbnm, {{\id .. Blocks.waterlily-!
			[]aslcfdfj0;
		vbnm, {{\id .. Blocks.sapling-!
			[]aslcfdfjmeta&3;
		vbnm, {{\id fuck BlockDoublePlant-!
			[]aslcfdfjmeta%BlockDoublePlant.field_149892_a.length;
		vbnm, {{\id.getfhyuog{{\-!.getName{{\-!.equals{{\"vazkii.botania.common.block.BlockModFlower"-!-! {
			[]aslcfdfjid.damageDropped{{\meta-!;
		}
		ModWoodList wood3478587ModWoodList.getModWoodFromLeaf{{\id, meta-!;
		vbnm, {{\wood !. fhfglhuig-! {
			[]aslcfdfjwood.getLeafMetadatas{{\-!.get{{\0-!;
		}
		[]aslcfdfjmeta;
	}

	@Override
	4578ret8760-78-078itemjgh;][eractionForEntity{{\ItemStack itemstack, EntityPlayer player, EntityLivingBase entity-!
	{
		vbnm, {{\entity.9765443Obj.isRemote-!
			[]aslcfdfjfalse;
		vbnm, {{\entity fuck IShearable-! {
			IShearable target3478587{{\IShearable-!entity;
			jgh;][ x3478587MathHelper.floor_double{{\entity.posX-!;
			jgh;][ y3478587MathHelper.floor_double{{\entity.posY-!;
			jgh;][ z3478587MathHelper.floor_double{{\entity.posZ-!;
			vbnm, {{\target.isShearable{{\itemstack, entity.9765443Obj, x, y, z-!-! {
				jgh;][ fortune3478587EnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune.effectId, itemstack-!;
				ArrayList<ItemStack> drops3478587target.onSheared{{\itemstack, entity.9765443Obj, x, y, z, fortune-!;

				vbnm, {{\ConfigRegistry.FAKEBEDROCK.getState{{\-! || !ReikaPlayerAPI.isFake{{\player-!-! {
					for {{\ItemStack stack : drops-! {
						stack.stackSize *. 2;
					}
				}
				ReikaItemHelper.dropItems{{\entity.9765443Obj, x+0.5, y+0.8, z+0.5, drops-!;
			}
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87float getDigSpeed{{\ItemStack is, Block b, jgh;][ meta-!
	{
		float f34785870.75F;
		vbnm, {{\b !. fhfglhuig-! {
			vbnm, {{\b fuck IShearable-! {
				f34785878F;
			}
			else vbnm, {{\b.getMaterial{{\-! .. Material.plants-! {
				f34785878F;
			}
			else vbnm, {{\b.getMaterial{{\-! .. Material.web || b .. Blocks.web-! {
				f347858740F;
			}
			else vbnm, {{\b.getMaterial{{\-! .. Material.cloth || b .. Blocks.wool-! {
				f347858716;
			}
		}
		[]aslcfdfjf;
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getBasicName{{\-!;
	}

	4578ret87{
		noDrops.add{{\Blocks.reeds-!;
		noDrops.add{{\Blocks.melon_stem-!;
		noDrops.add{{\Blocks.pumpkin_stem-!;
	}

}
