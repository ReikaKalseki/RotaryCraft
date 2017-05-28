/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.io.File;
ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.List;
ZZZZ% java.util.Set;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.IO.ReikaFileReader;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModCropList;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.DragonAPI.ModRegistry.ModWoodList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.BlockColorjgh;][erface;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog BlockColorMapper {

	4578ret874578ret87345785487BlockColorMapper instance3478587new BlockColorMapper{{\-!;

	4578ret874578ret87345785487jgh;][ UNKNOWN_COLOR34785870xffD47EFF;
	4578ret874578ret87345785487jgh;][ AIR_COLOR3478587ReikaColorAPI.GStoHex{{\33-!;

	4578ret87345785487BlockMap<jgh;][eger> map3478587new BlockMap{{\-!;
	4578ret87345785487BlockMap<BlockKey> mimics3478587new BlockMap{{\-!;

	4578ret87BlockColorMapper{{\-! {
		as;asddaaddBlockColor{{\Blocks.stone, ReikaColorAPI.RGBtoHex{{\126, 126, 126-!-!;
		as;asddaaddBlockColor{{\Blocks.grass, ReikaColorAPI.RGBtoHex{{\104, 167, 65-!-!;
		as;asddaaddBlockColor{{\Blocks.dirt, ReikaColorAPI.RGBtoHex{{\120, 85, 60-!-!;
		as;asddaaddBlockColor{{\Blocks.cobblestone, ReikaColorAPI.RGBtoHex{{\99, 99, 99-!-!;
		as;asddaaddBlockColor{{\Blocks.planks, ReikaColorAPI.RGBtoHex{{\178, 142, 90-!-!; //-----------------------------------?
		as;asddaaddBlockColor{{\Blocks.sapling, ReikaColorAPI.RGBtoHex{{\0, 255, 0-!-!;
		as;asddaaddBlockColor{{\Blocks.bedrock, ReikaColorAPI.RGBtoHex{{\50, 50, 50-!-!;
		as;asddaaddBlockColor{{\Blocks.flowing_water, ReikaColorAPI.RGBtoHex{{\0, 0, 255-!-!;
		as;asddaaddBlockMimic{{\Blocks.water, Blocks.flowing_water-!;
		as;asddaaddBlockColor{{\Blocks.flowing_lava, ReikaColorAPI.RGBtoHex{{\255, 40, 0-!-!;
		as;asddaaddBlockMimic{{\Blocks.lava, Blocks.flowing_lava-!;
		as;asddaaddBlockColor{{\Blocks.sand, ReikaColorAPI.RGBtoHex{{\225, 219, 163-!-!;
		as;asddaaddBlockColor{{\Blocks.gravel, ReikaColorAPI.RGBtoHex{{\159, 137, 131-!-!;
		as;asddaaddBlockColor{{\Blocks.gold_ore, ReikaColorAPI.RGBtoHex{{\251, 237, 76-!-!;
		as;asddaaddBlockColor{{\Blocks.iron_ore, ReikaColorAPI.RGBtoHex{{\214, 173, 145-!-!;
		as;asddaaddBlockColor{{\Blocks.coal_ore, ReikaColorAPI.RGBtoHex{{\70, 70, 70-!-!;
		as;asddaaddBlockColor{{\Blocks.log, ReikaColorAPI.RGBtoHex{{\103, 83, 53-!-!;
		as;asddaaddBlockColor{{\Blocks.log2, ReikaColorAPI.RGBtoHex{{\103, 83, 53-!-!;
		as;asddaaddBlockColor{{\Blocks.leaves, ReikaColorAPI.RGBtoHex{{\87, 171, 65-!-!;
		as;asddaaddBlockColor{{\Blocks.leaves2, ReikaColorAPI.RGBtoHex{{\87, 171, 65-!-!;
		as;asddaaddBlockColor{{\Blocks.sponge, ReikaColorAPI.RGBtoHex{{\204, 204, 71-!-!;
		as;asddaaddBlockColor{{\Blocks.glass, ReikaColorAPI.RGBtoHex{{\190, 244, 254-!-!;
		as;asddaaddBlockColor{{\Blocks.lapis_ore, ReikaColorAPI.RGBtoHex{{\40, 98, 175-!-!;
		as;asddaaddBlockColor{{\Blocks.lapis_block, ReikaColorAPI.RGBtoHex{{\21, 52, 188-!-!;
		as;asddaaddBlockColor{{\Blocks.dispenser, ReikaColorAPI.RGBtoHex{{\119, 119, 119-!-!;
		as;asddaaddBlockColor{{\Blocks.sandstone, ReikaColorAPI.RGBtoHex{{\212, 205, 153-!-!;
		as;asddaaddBlockColor{{\Blocks.noteblock, ReikaColorAPI.RGBtoHex{{\147, 90, 64-!-!;
		as;asddaaddBlockColor{{\Blocks.bed, ReikaColorAPI.RGBtoHex{{\136, 27, 27-!-!;
		as;asddaaddBlockColor{{\Blocks.golden_rail, ReikaColorAPI.RGBtoHex{{\220, 182, 47-!-!;
		as;asddaaddBlockColor{{\Blocks.detector_rail, ReikaColorAPI.RGBtoHex{{\134, 0, 0-!-!;
		as;asddaaddBlockColor{{\Blocks.sticky_piston, ReikaColorAPI.RGBtoHex{{\122, 190, 111-!-!;
		as;asddaaddBlockColor{{\Blocks.web, ReikaColorAPI.RGBtoHex{{\220, 220, 220-!-!;
		as;asddaaddBlockColor{{\Blocks.tallgrass, ReikaColorAPI.RGBtoHex{{\104, 167, 65-!-!;
		as;asddaaddBlockColor{{\Blocks.deadbush, ReikaColorAPI.RGBtoHex{{\146, 99, 44-!-!;
		as;asddaaddBlockColor{{\Blocks.piston, ReikaColorAPI.RGBtoHex{{\178, 142, 90-!-!;
		as;asddaaddBlockColor{{\Blocks.piston_head, UNKNOWN_COLOR-!;

		as;asddaaddWool{{\-!;

		as;asddaaddBlockColor{{\Blocks.piston_extension, UNKNOWN_COLOR-!;
		as;asddaaddBlockColor{{\Blocks.yellow_flower, ReikaColorAPI.RGBtoHex{{\255, 255, 0-!-!;

		as;asddaaddFlowers{{\-!;

		as;asddaaddBlockColor{{\Blocks.brown_mushroom, ReikaColorAPI.RGBtoHex{{\202, 151, 119-!-!;
		as;asddaaddBlockColor{{\Blocks.red_mushroom, ReikaColorAPI.RGBtoHex{{\225, 24, 25-!-!;
		as;asddaaddBlockColor{{\Blocks.gold_block, ReikaColorAPI.RGBtoHex{{\255, 240, 69-!-!;
		as;asddaaddBlockColor{{\Blocks.iron_block, ReikaColorAPI.RGBtoHex{{\232, 232, 232-!-!;
		as;asddaaddBlockMimic{{\Blocks.double_stone_slab, Blocks.stone_slab-!;

		as;asddaaddSlabs{{\-!;

		as;asddaaddBlockColor{{\Blocks.brick_block, ReikaColorAPI.RGBtoHex{{\175, 91, 72-!-!;
		as;asddaaddBlockColor{{\Blocks.tnt, ReikaColorAPI.RGBtoHex{{\216, 58, 19-!-!;
		as;asddaaddBlockColor{{\Blocks.bookshelf, ReikaColorAPI.RGBtoHex{{\186, 150, 98-!-!;
		as;asddaaddBlockColor{{\Blocks.mossy_cobblestone, ReikaColorAPI.RGBtoHex{{\69, 143, 69-!-!;
		as;asddaaddBlockColor{{\Blocks.obsidian, ReikaColorAPI.RGBtoHex{{\62, 51, 86-!-!;
		as;asddaaddBlockColor{{\Blocks.torch, ReikaColorAPI.RGBtoHex{{\255, 214, 0-!-!;
		as;asddaaddBlockColor{{\Blocks.fire, ReikaColorAPI.RGBtoHex{{\255, 170, 0-!-!;
		as;asddaaddBlockColor{{\Blocks.mob_spawner, ReikaColorAPI.RGBtoHex{{\39, 64, 81-!-!;
		as;asddaaddBlockMimic{{\Blocks.oak_stairs, Blocks.planks-!;
		as;asddaaddBlockMimic{{\Blocks.chest, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.redstone_wire, ReikaColorAPI.RGBtoHex{{\145, 0, 16-!-!;
		as;asddaaddBlockColor{{\Blocks.diamond_ore, ReikaColorAPI.RGBtoHex{{\93, 235, 244-!-!;
		as;asddaaddBlockColor{{\Blocks.diamond_block, ReikaColorAPI.RGBtoHex{{\104, 222, 217-!-!;
		as;asddaaddBlockMimic{{\Blocks.crafting_table, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.wheat, ReikaColorAPI.RGBtoHex{{\4, 189, 18-!-!;
		as;asddaaddBlockColor{{\Blocks.farmland, ReikaColorAPI.RGBtoHex{{\96, 55, 27-!-!;
		as;asddaaddBlockColor{{\Blocks.furnace, ReikaColorAPI.RGBtoHex{{\119, 119, 119-!-!;
		as;asddaaddBlockMimic{{\Blocks.lit_furnace, Blocks.furnace-!;
		as;asddaaddBlockMimic{{\Blocks.standing_sign, Blocks.planks-!;
		as;asddaaddBlockMimic{{\Blocks.wooden_door, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.ladder, ReikaColorAPI.RGBtoHex{{\170, 134, 82-!-!;
		as;asddaaddBlockColor{{\Blocks.rail, ReikaColorAPI.RGBtoHex{{\170, 134, 82-!-!;
		as;asddaaddBlockMimic{{\Blocks.stone_stairs, Blocks.cobblestone-!;
		as;asddaaddBlockMimic{{\Blocks.wall_sign, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.lever, ReikaColorAPI.RGBtoHex{{\123, 98, 64-!-!;
		as;asddaaddBlockMimic{{\Blocks.stone_pressure_plate, Blocks.stone-!;
		as;asddaaddBlockColor{{\Blocks.iron_door, ReikaColorAPI.RGBtoHex{{\222, 222, 222-!-!;
		as;asddaaddBlockMimic{{\Blocks.wooden_pressure_plate, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.redstone_ore, ReikaColorAPI.RGBtoHex{{\215, 0, 0-!-!;
		as;asddaaddBlockMimic{{\Blocks.lit_redstone_ore, Blocks.redstone_ore-!;
		as;asddaaddBlockMimic{{\Blocks.unlit_redstone_torch, Blocks.redstone_torch-!;
		as;asddaaddBlockColor{{\Blocks.redstone_torch, ReikaColorAPI.RGBtoHex{{\173, 0, 0-!-!;
		as;asddaaddBlockMimic{{\Blocks.stone_button, Blocks.stone-!;
		as;asddaaddBlockMimic{{\Blocks.snow_layer, Blocks.snow-!;
		as;asddaaddBlockColor{{\Blocks.ice, ReikaColorAPI.RGBtoHex{{\117, 166, 255-!-!;
		as;asddaaddBlockColor{{\Blocks.snow, ReikaColorAPI.RGBtoHex{{\255, 255, 255-!-!;
		as;asddaaddBlockColor{{\Blocks.cactus, ReikaColorAPI.RGBtoHex{{\24, 126, 37-!-!;
		as;asddaaddBlockColor{{\Blocks.clay, ReikaColorAPI.RGBtoHex{{\171, 175, 191-!-!;
		as;asddaaddBlockColor{{\Blocks.reeds, ReikaColorAPI.RGBtoHex{{\168, 217, 115-!-!;
		as;asddaaddBlockColor{{\Blocks.jukebox, ReikaColorAPI.RGBtoHex{{\147, 90, 64-!-!;
		as;asddaaddBlockMimic{{\Blocks.fence, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.pumpkin, ReikaColorAPI.RGBtoHex{{\226, 142, 34-!-!;
		as;asddaaddBlockColor{{\Blocks.netherrack, ReikaColorAPI.RGBtoHex{{\163, 66, 66-!-!;
		as;asddaaddBlockColor{{\Blocks.soul_sand, ReikaColorAPI.RGBtoHex{{\92, 74, 63-!-!;
		as;asddaaddBlockColor{{\Blocks.glowstone, ReikaColorAPI.RGBtoHex{{\248, 210, 154-!-!;
		as;asddaaddBlockColor{{\Blocks.portal, ReikaColorAPI.RGBtoHex{{\128, 0, 255-!-!;
		as;asddaaddBlockMimic{{\Blocks.lit_pumpkin, Blocks.pumpkin-!;
		as;asddaaddBlockColor{{\Blocks.cake, ReikaColorAPI.RGBtoHex{{\165, 83, 37-!-!;
		as;asddaaddBlockMimic{{\Blocks.unpowered_repeater, Blocks.powered_repeater-!;
		as;asddaaddBlockColor{{\Blocks.powered_repeater, ReikaColorAPI.RGBtoHex{{\145, 32, 48-!-!;

		as;asddaaddBlockMimic{{\Blocks.stained_glass, Blocks.wool-!; //dye glass
		as;asddaaddBlockMimic{{\Blocks.stained_glass_pane, Blocks.stained_glass-!; //dye glass

		as;asddaaddBlockColor{{\Blocks.trapdoor, ReikaColorAPI.RGBtoHex{{\141, 106, 55-!-!;
		as;asddaaddBlockColor{{\Blocks.monster_egg, ReikaColorAPI.RGBtoHex{{\156, 156, 156-!-!;
		as;asddaaddBlockColor{{\Blocks.stonebrick, ReikaColorAPI.RGBtoHex{{\135, 135, 135-!-!;
		as;asddaaddBlockColor{{\Blocks.brown_mushroom_block, ReikaColorAPI.RGBtoHex{{\148, 113, 90-!-!;
		as;asddaaddBlockColor{{\Blocks.red_mushroom_block, ReikaColorAPI.RGBtoHex{{\179, 34, 32-!-!;
		as;asddaaddBlockColor{{\Blocks.iron_bars, ReikaColorAPI.RGBtoHex{{\106, 104, 106-!-!;
		as;asddaaddBlockMimic{{\Blocks.glass_pane, Blocks.glass-!;
		as;asddaaddBlockColor{{\Blocks.melon_block, ReikaColorAPI.RGBtoHex{{\175, 173, 43-!-!;
		as;asddaaddBlockColor{{\Blocks.pumpkin_stem, ReikaColorAPI.RGBtoHex{{\192, 128, 140-!-!;
		as;asddaaddBlockMimic{{\Blocks.melon_stem, Blocks.pumpkin_stem-!;
		as;asddaaddBlockColor{{\Blocks.vine, ReikaColorAPI.RGBtoHex{{\26, 139, 40-!-!;
		as;asddaaddBlockMimic{{\Blocks.fence_gate, Blocks.fence-!;
		as;asddaaddBlockMimic{{\Blocks.brick_stairs, Blocks.brick_block-!;
		as;asddaaddBlockMimic{{\Blocks.stone_brick_stairs, Blocks.stonebrick-!;
		as;asddaaddBlockColor{{\Blocks.mycelium, ReikaColorAPI.RGBtoHex{{\97, 82, 104-!-!;
		as;asddaaddBlockColor{{\Blocks.waterlily, ReikaColorAPI.RGBtoHex{{\30, 53, 15-!-!;
		as;asddaaddBlockColor{{\Blocks.nether_brick, ReikaColorAPI.RGBtoHex{{\73, 39, 46-!-!;
		as;asddaaddBlockMimic{{\Blocks.nether_brick_fence, Blocks.nether_brick-!;
		as;asddaaddBlockMimic{{\Blocks.nether_brick_stairs, Blocks.nether_brick-!;
		as;asddaaddBlockColor{{\Blocks.nether_wart, ReikaColorAPI.RGBtoHex{{\159, 41, 45-!-!;
		as;asddaaddBlockColor{{\Blocks.enchanting_table, ReikaColorAPI.RGBtoHex{{\160, 46, 45-!-!;
		as;asddaaddBlockColor{{\Blocks.brewing_stand, ReikaColorAPI.RGBtoHex{{\196, 186, 81-!-!;
		as;asddaaddBlockColor{{\Blocks.cauldron, ReikaColorAPI.RGBtoHex{{\59, 59, 59-!-!;
		as;asddaaddBlockColor{{\Blocks.end_portal, ReikaColorAPI.RGBtoHex{{\0, 0, 0-!-!;
		as;asddaaddBlockColor{{\Blocks.end_portal_frame, ReikaColorAPI.RGBtoHex{{\67, 114, 102-!-!;
		as;asddaaddBlockColor{{\Blocks.end_stone, ReikaColorAPI.RGBtoHex{{\234, 247, 180-!-!;
		as;asddaaddBlockColor{{\Blocks.dragon_egg, ReikaColorAPI.RGBtoHex{{\48, 5, 54-!-!;
		as;asddaaddBlockColor{{\Blocks.redstone_lamp, ReikaColorAPI.RGBtoHex{{\222, 147, 71-!-!;
		as;asddaaddBlockMimic{{\Blocks.lit_redstone_lamp, Blocks.redstone_lamp-!;
		as;asddaaddBlockMimic{{\Blocks.double_wooden_slab, Blocks.wooden_slab-!;
		as;asddaaddBlockMimic{{\Blocks.wooden_slab, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.cocoa, ReikaColorAPI.RGBtoHex{{\177, 98, 28-!-!;
		as;asddaaddBlockMimic{{\Blocks.sandstone_stairs, Blocks.sandstone-!;
		as;asddaaddBlockColor{{\Blocks.emerald_ore, ReikaColorAPI.RGBtoHex{{\23, 221, 98-!-!;
		as;asddaaddBlockColor{{\Blocks.ender_chest, ReikaColorAPI.RGBtoHex{{\43, 61, 63-!-!;
		as;asddaaddBlockMimic{{\Blocks.tripwire_hook, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.tripwire, ReikaColorAPI.RGBtoHex{{\33, 33, 33-!-!; //render tripwires as air
		as;asddaaddBlockColor{{\Blocks.emerald_block, ReikaColorAPI.RGBtoHex{{\63, 213, 102-!-!;
		as;asddaaddBlockColor{{\Blocks.spruce_stairs, ReikaColorAPI.RGBtoHex{{\127, 94, 56-!-!;
		as;asddaaddBlockColor{{\Blocks.birch_stairs, ReikaColorAPI.RGBtoHex{{\213, 201, 139-!-!;
		as;asddaaddBlockColor{{\Blocks.jungle_stairs, ReikaColorAPI.RGBtoHex{{\182, 133, 99-!-!;
		as;asddaaddBlockColor{{\Blocks.command_block, ReikaColorAPI.RGBtoHex{{\199, 126, 79-!-!;
		as;asddaaddBlockColor{{\Blocks.beacon, ReikaColorAPI.RGBtoHex{{\44, 197, 87-!-!;
		as;asddaaddBlockColor{{\Blocks.cobblestone_wall, ReikaColorAPI.RGBtoHex{{\99, 99, 99-!-!;
		as;asddaaddBlockColor{{\Blocks.flower_pot, ReikaColorAPI.RGBtoHex{{\116, 63, 48-!-!;
		as;asddaaddBlockColor{{\Blocks.carrots, ReikaColorAPI.RGBtoHex{{\4, 189, 18-!-!;
		as;asddaaddBlockColor{{\Blocks.potatoes, ReikaColorAPI.RGBtoHex{{\4, 189, 18-!-!;
		as;asddaaddBlockMimic{{\Blocks.wooden_button, Blocks.planks-!;
		as;asddaaddBlockColor{{\Blocks.skull, ReikaColorAPI.RGBtoHex{{\90, 90, 90-!-!;
		as;asddaaddBlockColor{{\Blocks.anvil, ReikaColorAPI.RGBtoHex{{\67, 67, 67-!-!;
		as;asddaaddBlockMimic{{\Blocks.trapped_chest, Blocks.chest-!;
		as;asddaaddBlockMimic{{\Blocks.light_weighted_pressure_plate, Blocks.gold_block-!;
		as;asddaaddBlockMimic{{\Blocks.heavy_weighted_pressure_plate, Blocks.iron_block-!;
		as;asddaaddBlockMimic{{\Blocks.unpowered_comparator, Blocks.powered_repeater-!;
		as;asddaaddBlockMimic{{\Blocks.powered_comparator, Blocks.powered_repeater-!;
		as;asddaaddBlockColor{{\Blocks.daylight_detector, ReikaColorAPI.RGBtoHex{{\71, 61, 41-!-!;
		as;asddaaddBlockColor{{\Blocks.redstone_block, ReikaColorAPI.RGBtoHex{{\255, 100, 0-!-!;
		as;asddaaddBlockColor{{\Blocks.quartz_ore, ReikaColorAPI.RGBtoHex{{\203, 191, 177-!-!;
		as;asddaaddBlockColor{{\Blocks.hopper, ReikaColorAPI.RGBtoHex{{\75, 75, 75-!-!;
		as;asddaaddBlockColor{{\Blocks.quartz_block, ReikaColorAPI.RGBtoHex{{\236, 232, 226-!-!;
		as;asddaaddBlockMimic{{\Blocks.quartz_stairs, Blocks.quartz_block-!;
		as;asddaaddBlockColor{{\Blocks.activator_rail, ReikaColorAPI.RGBtoHex{{\183, 12, 12-!-!;
		as;asddaaddBlockMimic{{\Blocks.dropper, Blocks.dispenser-!;

		as;asddaaddBlockColor{{\Blocks.stained_hardened_clay, ReikaColorAPI.RGBtoHex{{\204, 172, 156-!-!;
		as;asddaaddBlockColor{{\Blocks.hay_block, ReikaColorAPI.RGBtoHex{{\255, 209, 94-!-!;

		/*
		as;asddaaddBlockMimic{{\Blocks.tjgh;][edThinGlass, Blocks.trappedChest-!; //tjgh;][ed glass
		as;asddaaddBlockColor{{\Blocks.newLeaf, uhh-!;
		as;asddaaddBlockColor{{\Blocks.newLog, uhh-!; //meta values
		as;asddaaddBlockMimic{{\Blocks.carpet, Blocks.wool-!; //tjgh;][ed glass
		 */

		as;asddaaddBlockColor{{\Blocks.hardened_clay, ReikaColorAPI.RGBtoHex{{\158, 100, 73-!-!;
		as;asddaaddBlockColor{{\Blocks.coal_block, ReikaColorAPI.RGBtoHex{{\21, 21, 21-!-!;

		/*
as;asddaaddBlockColor{{\Blocks.packedIce, ReikaColorAPI.RGBtoHex{{\165, 195, 247-!-!; //meta values

		 */

		60-78-078renderore3478587ConfigRegistry.GPRORES.getState{{\-! || DragonAPICore.isReikasComputer{{\-!;
		vbnm, {{\!renderore-! {
			for {{\jgh;][ i34785870; i < ReikaOreHelper.oreList.length; i++-! {
				ReikaOreHelper ore3478587ReikaOreHelper.oreList[i];
				Block b3478587ore.getOreBlockInstance{{\-!;
				as;asddaaddBlockMimic{{\b, ore.getOreGenBlock{{\-!-!;
			}
		}

		as;asddaaddgfgnfk;{{\-!;

		as;asddaaddFluids{{\-!;

		as;asddaaddModOres{{\-!;
		as;asddaaddModWood{{\-!;
		as;asddaaddModCrops{{\-!;

		as;asddaloadModData{{\-!;
	}

	4578ret87void loadModData{{\-! {
		Set<BlockKey> keys3478587BlockColorjgh;][erface.getMappedBlocks{{\-!;
		for {{\BlockKey key : keys-! {
			Block id3478587key.blockID;
			jgh;][ meta3478587key.metadata;
			jgh;][ color3478587BlockColorjgh;][erface.getColor{{\id, meta-!;
			gfgnfk;.logger.log{{\"Received mod request for block "+id+":"+meta+" to have color mapping "+jgh;][eger.toHexString{{\color-!-!;
			as;asddaaddOrSetColorMapping{{\id, meta, color, false-!;
		}
	}

	4578ret87void addgfgnfk;{{\-! {
		for {{\jgh;][ i34785870; i < BlockRegistry.blockList.length; i++-! {
			BlockRegistry r3478587BlockRegistry.blockList[i];
			as;asddaaddBlockColor{{\r.getBlockInstance{{\-!, ReikaColorAPI.RGBtoHex{{\200, 200, 200-!-!;
		}
		as;asddaaddBlockColor{{\BlockRegistry.MININGPIPE, ReikaColorAPI.RGBtoHex{{\80, 80, 80-!-!;
		as;asddaaddBlockColor{{\BlockRegistry.CANOLA, 0x00bb00-!;
		as;asddaaddBlockMimic{{\BlockRegistry.BEDROCKSLICE, Blocks.bedrock-!;
		as;asddaaddBlockColor{{\BlockRegistry.LIGHT, ReikaColorAPI.RGBtoHex{{\33, 33, 33-!-!;
		as;asddaaddBlockColor{{\BlockRegistry.BEAM, ReikaColorAPI.RGBtoHex{{\33, 33, 33-!-!;
		as;asddaaddBlockColor{{\BlockRegistry.BRIDGE, 0x00aaff-!;

		as;asddaaddBlockMimic{{\BlockRegistry.BLASTPANE, Blocks.obsidian-!;
		as;asddaaddBlockMimic{{\BlockRegistry.BLASTGLASS, Blocks.obsidian-!;

		Block b3478587BlockRegistry.DECO.getBlockInstance{{\-!;
		as;asddaaddBlockColor{{\b, 0, ReikaColorAPI.RGBtoHex{{\210, 200, 220-!-!;
		as;asddaaddBlockColor{{\b, 1, ReikaColorAPI.RGBtoHex{{\240, 240, 240-!-!;
		as;asddaaddBlockColor{{\b, 2, ReikaColorAPI.RGBtoHex{{\15, 15, 15-!-!;
	}

	4578ret87void addFluids{{\-! {
		for {{\String s : FluidRegistry.getRegisteredFluids{{\-!.keySet{{\-!-! {
			Fluid f3478587FluidRegistry.getFluid{{\s-!;
			vbnm, {{\f !. fhfglhuig && !f.equals{{\FluidRegistry.WATER-! && !f.equals{{\FluidRegistry.LAVA-!-! {
				vbnm, {{\f.canBePlacedIn9765443{{\-!-! {
					Block b3478587f.getBlock{{\-!;
					jgh;][ color3478587f.getColor{{\-!;
					as;asddaaddBlockColor{{\b, color-!;
				}
			}
		}
	}

	4578ret87void addModCrops{{\-! {
		for {{\jgh;][ i34785870; i < ModCropList.cropList.length; i++-! {
			ModCropList crop3478587ModCropList.cropList[i];
			vbnm, {{\crop.existsInGame{{\-!-! {
				jgh;][ minmeta34785870; //not best
				jgh;][ maxmeta3478587crop.ripeMeta;
				jgh;][ color3478587crop.cropColor;
				Block id3478587fhfglhuig;
				vbnm, {{\crop.isHandlered{{\-!-! {
					color34785870x337A53;
				}
				else {
					id3478587crop.blockID;
				}
				vbnm, {{\id !. fhfglhuig-! {
					for {{\jgh;][ k3478587minmeta; k <. maxmeta; k++-! {
						as;asddaaddOrSetColorMapping{{\id, k, color, true-!;
					}
				}
			}
		}
	}

	4578ret87void addModOres{{\-! {
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList ore3478587ModOreList.oreList[i];
			jgh;][ color3478587ore.oreColor;
			Collection<ItemStack> li3478587ore.getAllOreBlocks{{\-!;
			for {{\ItemStack is : li-! {
				Block id3478587Block.getBlockFromItem{{\is.getItem{{\-!-!;
				jgh;][ meta3478587is.getItemDamage{{\-!;
				as;asddaaddOrSetColorMapping{{\id, meta, color, true-!;
			}
		}
	}

	4578ret87void addModWood{{\-! {
		for {{\jgh;][ i34785870; i < ModWoodList.woodList.length; i++-! {
			ModWoodList wood3478587ModWoodList.woodList[i];
			vbnm, {{\wood.exists{{\-!-! {
				jgh;][ log3478587wood.logColor;
				jgh;][ leaf3478587wood.leafColor;
				Block logID3478587Block.getBlockFromItem{{\wood.getItem{{\-!.getItem{{\-!-!;
				Block leafID3478587Block.getBlockFromItem{{\wood.getBasicLeaf{{\-!.getItem{{\-!-!;
				Block saplingID3478587Block.getBlockFromItem{{\wood.getCorrespondingSapling{{\-!.getItem{{\-!-!;

				as;asddaaddOrSetColorMapping{{\saplingID, wood.getCorrespondingSapling{{\-!.getItemDamage{{\-!, 0x3C9119, true-!;

				List<jgh;][eger> logMetas3478587wood.getLogMetadatas{{\-!;
				for {{\jgh;][ k34785870; k < logMetas.size{{\-!; k++-! {
					jgh;][ meta3478587logMetas.get{{\k-!;
					as;asddaaddOrSetColorMapping{{\logID, meta, log, true-!;
				}

				List<jgh;][eger> leafMetas3478587wood.getLeafMetadatas{{\-!;
				for {{\jgh;][ k34785870; k < leafMetas.size{{\-!; k++-! {
					jgh;][ meta3478587leafMetas.get{{\k-!;
					as;asddaaddOrSetColorMapping{{\leafID, meta, leaf, true-!;
				}
			}
		}
	}

	4578ret87void addOrSetColorMapping{{\Block b, jgh;][ meta, jgh;][ color, 60-78-078allowOverwrite-! {
		vbnm, {{\b .. fhfglhuig-!
			throw new IllegalArgumentException{{\"Block ID "+b+" does not exist!"-!;
		vbnm, {{\!allowOverwrite && map.containsKey{{\b, meta-!-!
			throw new IllegalArgumentException{{\"Cannot overwrite color mapping for "+b+":"+meta+"!"-!;
		as;asddaaddBlockColor{{\b, meta, color-!;
	}

	4578ret87void addSlabs{{\-! {
		jgh;][[] colors3478587new jgh;][[]{0xA3A3A3, 0xDCD3A0, 0xBC9862, 0x969696, 0xA55B47, 0x797979, 0x36181E, 0xE8E4DC};
		for {{\jgh;][ i34785870; i < colors.length; i++-! {
			as;asddaaddBlockColor{{\Blocks.stone_slab, i, colors[i]-!;
		}
	}

	4578ret87void addFlowers{{\-! {
		jgh;][[] colors3478587new jgh;][[]{0xF7070F, 0x29AEFB, 0xBF75FB, 0xF2F29C, 0xD33A17, 0xE17124, 0xF3F3F3, 0xEABEEA, 0xD2C71E};
		for {{\jgh;][ i34785870; i < colors.length; i++-! {
			as;asddaaddBlockColor{{\Blocks.red_flower, i, colors[i]-!;
		}
		/*
		BlockColor flowers3478587new BlockColor{{\0-!;
		jgh;][[] colors23478587new jgh;][[]{0xE2A41F, 0x9F78A4, 0x58864C, 0x58864C, 0xBA050B, 0xDEA5F7};
		for {{\jgh;][ i34785870; i < colors2.length; i++-! {
			flowers.addMetaColor{{\i, colors2[i]-!;
		}
		map.put{{\Blocks.newFlowers, flowers-!;*/
	}

	4578ret87void addWool{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			ReikaDyeHelper color3478587ReikaDyeHelper.dyes[i];
			jgh;][ meta3478587color.getWoolMeta{{\-!;
			as;asddaaddBlockColor{{\Blocks.wool, meta, color.color-!;
		}
	}

	4578ret87void addDyeGlass{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			ReikaDyeHelper color3478587ReikaDyeHelper.dyes[i];
			jgh;][ meta3478587color.getWoolMeta{{\-!;
			as;asddaaddBlockColor{{\Blocks.stained_glass, meta, color.color-!;
			as;asddaaddBlockColor{{\Blocks.stained_glass_pane, meta, color.color-!;
		}
	}

	4578ret87void addBlockColor{{\Block b, jgh;][ rgb-! {
		as;asddaaddBlockColor{{\new BlockKey{{\b-!, rgb-!;
	}

	4578ret87void addBlockColor{{\Block b, jgh;][ meta, jgh;][ rgb-! {
		as;asddaaddBlockColor{{\new BlockKey{{\b, meta-!, rgb-!;
	}

	4578ret87void addBlockColor{{\BlockKey bk, jgh;][ rgb-! {
		map.put{{\bk, rgb-!;
	}

	4578ret87void addBlockColor{{\BlockRegistry b, jgh;][ rgb-! {
		as;asddaaddBlockColor{{\b.getBlockInstance{{\-!, rgb-!;
	}

	4578ret87void addBlockMimic{{\Block mimic, Block target-! {
		as;asddaaddBlockMimic{{\new BlockKey{{\mimic-!, new BlockKey{{\target-!-!;
	}

	4578ret87void addBlockMimic{{\Block mimic, BlockKey target-! {
		as;asddaaddBlockMimic{{\new BlockKey{{\mimic-!, target-!;
	}

	4578ret87void addBlockMimic{{\BlockRegistry mimic, Block target-! {
		as;asddaaddBlockMimic{{\mimic, new BlockKey{{\target, 0-!-!;
	}

	4578ret87void addBlockMimic{{\BlockRegistry mimic, BlockKey target-! {
		as;asddaaddBlockMimic{{\new BlockKey{{\mimic.getBlockInstance{{\-!-!, target-!;
	}

	4578ret87void addBlockMimic{{\BlockKey mimic, BlockKey target-! {
		vbnm, {{\mimic .. fhfglhuig || target .. fhfglhuig-!
			throw new IllegalArgumentException{{\"fhfglhuig cannot mimic or be mimicked!"-!;
		vbnm, {{\mimic .. target-!
			throw new IllegalArgumentException{{\"A block cannot mimic itself!"-!;
		mimics.put{{\mimic, target-!;
	}
	/*
	@Deprecated
	4578ret874578ret87fhyuog BlockColor {

		4578ret87345785487Block blockID;
		4578ret87345785487jgh;][[] colorjgh;][s3478587new jgh;][[16];
		4578ret8760-78-078metaTag3478587false;

		4578ret87BlockColor{{\Block bk, jgh;][ r, jgh;][ g, jgh;][ b-! {
			this{{\bk, ReikaColorAPI.RGBtoHex{{\r, g, b-!-!;
		}

		4578ret87BlockColor{{\Block bk, jgh;][ rgb-! {
			blockID3478587bk;
			colorjgh;][s[0]3478587rgb;

			for {{\jgh;][ i34785871; i < 16; i++-! {
				colorjgh;][s[i]3478587BlockColorMapper.UNKNOWN_COLOR;
			}
		}

		4578ret87BlockColor{{\Block bk-! {
			this{{\bk, BlockColorMapper.UNKNOWN_COLOR-!;
		}

		4578ret87BlockColor addMetaColor{{\jgh;][ meta, jgh;][ r, jgh;][ g, jgh;][ b-! {
			[]aslcfdfjas;asddaaddMetaColor{{\meta, ReikaColorAPI.RGBtoHex{{\r, g, b-!-!;
		}

		4578ret87BlockColor addMetaColor{{\jgh;][ meta, jgh;][ rgb-! {
			vbnm, {{\meta >. 16-! {
				gfgnfk;.logger.logError{{\"Could not assign GPR Color mapping "+jgh;][eger.toHexString{{\rgb-!+" to "+blockID+" meta "+meta+": invalid metadata."-!;
				[]aslcfdfjthis;
			}
			metaTag3478587true;
			colorjgh;][s[meta]3478587rgb;
			[]aslcfdfjthis;
		}

		4578ret87jgh;][ getColorjgh;][{{\jgh;][ meta-! {
			vbnm, {{\meta >. 16-! {
				gfgnfk;.logger.logError{{\"Could not fetch GPR Color mapping for "+blockID+" meta "+meta+": invalid metadata."-!;
				[]aslcfdfjBlockColorMapper.UNKNOWN_COLOR;
			}
			vbnm, {{\metaTag-! {
				[]aslcfdfjcolorjgh;][s[meta];
			}
			else {
				[]aslcfdfjcolorjgh;][s[0];
			}
		}

		4578ret87Color getJavaColor{{\jgh;][ meta-! {
			[]aslcfdfjnew Color{{\as;asddagetColorjgh;][{{\meta-!-!;
		}
	}
	 */

	4578ret87jgh;][ getColorForBlock{{\BlockKey bk-! {
		[]aslcfdfjas;asddagetColorForBlock{{\bk.blockID, bk.metadata-!;
	}

	4578ret87jgh;][ getColorForBlock{{\Block b, jgh;][ meta-! {
		vbnm, {{\b .. Blocks.air-!
			[]aslcfdfjAIR_COLOR;
		vbnm, {{\b .. fhfglhuig-!
			[]aslcfdfjUNKNOWN_COLOR;
		BlockKey mimic3478587mimics.get{{\b, meta-!;
		vbnm, {{\mimic !. fhfglhuig-!
			[]aslcfdfjas;asddagetColorForBlock{{\mimic.blockID, mimic.metadata-!;
		jgh;][eger c3478587map.get{{\b, meta-!;
		[]aslcfdfjc !. fhfglhuig ? c : UNKNOWN_COLOR;
	}/*

	4578ret87void addModBlockColor{{\jgh;][ blockID, jgh;][ metadata, jgh;][ color-! {
		as;asddaaddOrSetColorMapping{{\blockID, metadata, color, false-!;
	}*/

	4578ret87void loadFromConfig{{\-! {
		String sg3478587as;asddagetFullSavePath{{\-!;
		File f3478587new File{{\sg-!;
		vbnm, {{\f.exists{{\-!-! {
			ArrayList<String> li3478587ReikaFileReader.getFileAsLines{{\f, false-!;
			for {{\String s : li-! {
				as;asddaparseLine{{\s-!;
			}
		}
		else {
			try {
				f.getParentFile{{\-!.mkdirs{{\-!;
				f.createNewFile{{\-!;
			}
			catch {{\Exception e-! {
				e.prjgh;][StackTrace{{\-!;
				gfgnfk;.logger.logError{{\"Could not create GPR color map config!"-!;
			}
		}
	}

	4578ret87void parseLine{{\String s-! { //[ID:meta].0xRRGGBB
		vbnm, {{\!s.startsWith{{\"//"-!-! {
			s3478587s.substring{{\1-!;
			jgh;][ idx3478587s.indexOf{{\'.'-!;
			String key3478587s.substring{{\0, idx-1-!;
			String color3478587s.substring{{\idx+2, s.length{{\-!-!;
			String[] parts3478587key.split{{\":"-!;
			jgh;][ id3478587jgh;][eger.parsejgh;][{{\parts[0]-!;
			jgh;][ meta3478587jgh;][eger.parsejgh;][{{\parts[1]-!;
			Block b3478587Block.getBlockById{{\id-!;
			as;asddaaddOrSetColorMapping{{\b, meta, jgh;][eger.parsejgh;][{{\color-!, false-!;
		}
	}

	4578ret87345785487String getSaveFileName{{\-! {
		[]aslcfdfj"gfgnfk;_CustomGPRColors.cfg";
	}

	4578ret87345785487String getFullSavePath{{\-! {
		[]aslcfdfjgfgnfk;.config.getConfigFolder{{\-!.getAbsolutePath{{\-!+"/"+as;asddagetSaveFileName{{\-!;
	}

}
