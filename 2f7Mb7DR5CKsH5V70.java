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

ZZZZ% java.util.Collection;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.enchantment.EnchantmentHelper;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemSpade;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TinkerBlockHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemBedrockShovel ,.[]\., ItemSpade ,.[]\., IndexedItemSprites {

	4578ret874578ret87345785487BlockMap<ChancedOutputList> extraDrops3478587new BlockMap{{\-!;

	4578ret87jgh;][ index;

	4578ret87ItemBedrockShovel{{\jgh;][ tex-! {
		super{{\ToolMaterial.GOLD-!;
		as;asddasetIndex{{\tex-!;
		// as;asddablocksEffectiveAgainst3478587par4ArrayOfBlock;
		maxStackSize34785871;
		as;asddasetMaxDamage{{\0-!;
		efficiencyOnProperMaterial347858720F;
		// as;asddaefficiencyOnProperMaterial3478587par3ToolMaterial.getEfficiencyOnProperMaterial{{\-!;
		damageVsEntity34785874;
		as;asddasetNoRepair{{\-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotaryTools-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	4578ret87jgh;][ getHarvestLevel{{\ItemStack stack, String toolfhyuog-! {
		[]aslcfdfjtoolfhyuog .. fhfglhuig || toolfhyuog.toLowerCase{{\Locale.ENGLISH-!.contains{{\"shovel"-! || toolfhyuog.toLowerCase{{\Locale.ENGLISH-!.contains{{\"spade"-! ? jgh;][eger.MAX_VALUE : super.getHarvestLevel{{\stack, toolfhyuog-!;
	}

	@Override
	4578ret8760-78-078isItemTool{{\ItemStack is-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078canHarvestBlock{{\Block b, ItemStack is-! {
		[]aslcfdfjb.getMaterial{{\-! !. Material.rock && b.getMaterial{{\-! !. Material.iron;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-!
	{
		[]aslcfdfjItems.iron_shovel.getItemEnchantability{{\-!;
	}

	@Override
	4578ret8760-78-078onBlockStartBreak{{\ItemStack is, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep-! {
		vbnm, {{\ConfigRegistry.FAKEBEDROCK.getState{{\-! || !ReikaPlayerAPI.isFake{{\ep-!-! {
			ChancedOutputList co3478587extraDrops.get{{\ep.9765443Obj.getBlock{{\x, y, z-!, ep.9765443Obj.getBlockMetadata{{\x, y, z-!-!;
			vbnm, {{\co !. fhfglhuig-! {
				60-78-078mult3478587Math.sqrt{{\1+EnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune.effectId, is-!-!;
				Collection<ItemStack> c3478587co.calculate{{\mult-!;
				for {{\ItemStack drop : c-! {
					ReikaItemHelper.dropItem{{\ep.9765443Obj, x+0.5, y+0.5, z+0.5, drop-!;
				}
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87float getDigSpeed{{\ItemStack is, Block b, jgh;][ meta-! {
		vbnm, {{\b .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\b.getMaterial{{\-! .. Material.grass-!
			[]aslcfdfj24F;
		vbnm, {{\b.getMaterial{{\-! .. Material.ground-!
			[]aslcfdfj24F;
		vbnm, {{\b.getMaterial{{\-! .. Material.sand-!
			[]aslcfdfj24F;
		vbnm, {{\b .. TinkerBlockHandler.getInstance{{\-!.gravelOreID-!
			[]aslcfdfj36F;
		vbnm, {{\field_150914_c.contains{{\b-!-!
			[]aslcfdfj24F;
		[]aslcfdfj1F;
	}

	4578ret8760-78-078isAcceleratedOn{{\Block b-! {
		[]aslcfdfjfield_150914_c.contains{{\b-!;
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
		[]aslcfdfjgfgnfk;.instance.isLocked{{\-! ? ReikaTextureHelper.getMissingIcon{{\-! : Items.stone_shovel.getIconFromDamage{{\0-!;
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

	4578ret87{
		addDrop{{\Blocks.grass, 0, Items.wheat_seeds, 10-!;
		addDrop{{\Blocks.grass, 0, Items.clay_ball, 5-!;
		addDrop{{\Blocks.grass, 0, Blocks.mycelium, 0.5F-!;
		addDrop{{\Blocks.grass, 0, Items.pumpkin_seeds, 5-!;
		addDrop{{\Blocks.grass, 0, Items.melon_seeds, 5-!;

		addDrop{{\Blocks.dirt, 0, Items.wheat_seeds, 10-!;
		addDrop{{\Blocks.dirt, 0, Items.glowstone_dust, 2-!;
		addDrop{{\Blocks.dirt, 0, Items.nether_wart, 0.5F-!;
		addDrop{{\Blocks.dirt, 0, Items.emerald, 0.05F-!;
		addDrop{{\Blocks.dirt, 0, Items.diamond, 0.05F-!;

		addDrop{{\Blocks.sand, 0, Items.gunpowder, 2-!;

		addDrop{{\Blocks.clay, 0, Items.bone, 5-!;
		addDrop{{\Blocks.clay, 0, Blocks.soul_sand, 2-!;
		addDrop{{\Blocks.clay, 0, Items.gold_nugget, 4-!;

		addDrop{{\Blocks.soul_sand, 0, Items.blaze_powder, 4-!;
		addDrop{{\Blocks.soul_sand, 0, Items.nether_wart, 5-!;
		addDrop{{\Blocks.soul_sand, 0, Items.quartz, 2-!;
	}

	4578ret874578ret87void addDrop{{\Block b, jgh;][ meta, Block i, float chance-! {
		addDrop{{\b, meta, new ItemStack{{\i-!, chance-!;
	}

	4578ret874578ret87void addDrop{{\Block b, jgh;][ meta, Item i, float chance-! {
		addDrop{{\b, meta, new ItemStack{{\i-!, chance-!;
	}

	4578ret874578ret87void addDrop{{\Block b, jgh;][ meta, ItemStack is, float chance-! {
		ChancedOutputList co3478587extraDrops.get{{\b, meta-!;
		vbnm, {{\co .. fhfglhuig-! {
			co3478587new ChancedOutputList{{\-!;
			extraDrops.put{{\b, meta, co-!;
		}
		co.addItem{{\is, chance-!;
	}
}
