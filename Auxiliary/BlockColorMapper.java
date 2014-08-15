/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.BlockColorInterface;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockColorMapper {

	public static final BlockColorMapper instance = new BlockColorMapper();

	public static final int UNKNOWN_COLOR = 0xffD47EFF;
	public static final int AIR_COLOR = ReikaColorAPI.GStoHex(33);

	private final HashMap<Block, BlockColor> map = new HashMap();
	private final HashMap<Block, Block> mimics = new HashMap();

	private BlockColorMapper() {
		this.addBlockColor(Blocks.stone, ReikaColorAPI.RGBtoHex(126, 126, 126));
		this.addBlockColor(Blocks.grass, ReikaColorAPI.RGBtoHex(104, 167, 65));
		this.addBlockColor(Blocks.dirt, ReikaColorAPI.RGBtoHex(120, 85, 60));
		this.addBlockColor(Blocks.cobblestone, ReikaColorAPI.RGBtoHex(99, 99, 99));
		this.addBlockColor(Blocks.planks, ReikaColorAPI.RGBtoHex(178, 142, 90)); //-----------------------------------?
		this.addBlockColor(Blocks.sapling, ReikaColorAPI.RGBtoHex(0, 255, 0));
		this.addBlockColor(Blocks.bedrock, ReikaColorAPI.RGBtoHex(50, 50, 50));
		this.addBlockColor(Blocks.flowing_water, ReikaColorAPI.RGBtoHex(0, 0, 255));
		this.addBlockMimic(Blocks.water, Blocks.flowing_water);
		this.addBlockColor(Blocks.flowing_lava, ReikaColorAPI.RGBtoHex(255, 40, 0));
		this.addBlockMimic(Blocks.lava, Blocks.flowing_lava);
		this.addBlockColor(Blocks.sand, ReikaColorAPI.RGBtoHex(225, 219, 163));
		this.addBlockColor(Blocks.gravel, ReikaColorAPI.RGBtoHex(159, 137, 131));
		this.addBlockColor(Blocks.gold_ore, ReikaColorAPI.RGBtoHex(251, 237, 76));
		this.addBlockColor(Blocks.iron_ore, ReikaColorAPI.RGBtoHex(214, 173, 145));
		this.addBlockColor(Blocks.coal_ore, ReikaColorAPI.RGBtoHex(70, 70, 70));
		this.addBlockColor(Blocks.log, ReikaColorAPI.RGBtoHex(103, 83, 53));
		this.addBlockColor(Blocks.log2, ReikaColorAPI.RGBtoHex(103, 83, 53));
		this.addBlockColor(Blocks.leaves, ReikaColorAPI.RGBtoHex(87, 171, 65));
		this.addBlockColor(Blocks.leaves2, ReikaColorAPI.RGBtoHex(87, 171, 65));
		this.addBlockColor(Blocks.sponge, ReikaColorAPI.RGBtoHex(204, 204, 71));
		this.addBlockColor(Blocks.glass, ReikaColorAPI.RGBtoHex(190, 244, 254));
		this.addBlockColor(Blocks.lapis_ore, ReikaColorAPI.RGBtoHex(40, 98, 175));
		this.addBlockColor(Blocks.lapis_block, ReikaColorAPI.RGBtoHex(21, 52, 188));
		this.addBlockColor(Blocks.dispenser, ReikaColorAPI.RGBtoHex(119, 119, 119));
		this.addBlockColor(Blocks.sandstone, ReikaColorAPI.RGBtoHex(212, 205, 153));
		this.addBlockColor(Blocks.noteblock, ReikaColorAPI.RGBtoHex(147, 90, 64));
		this.addBlockColor(Blocks.bed, ReikaColorAPI.RGBtoHex(136, 27, 27));
		this.addBlockColor(Blocks.golden_rail, ReikaColorAPI.RGBtoHex(220, 182, 47));
		this.addBlockColor(Blocks.detector_rail, ReikaColorAPI.RGBtoHex(134, 0, 0));
		this.addBlockColor(Blocks.sticky_piston, ReikaColorAPI.RGBtoHex(122, 190, 111));
		this.addBlockColor(Blocks.web, ReikaColorAPI.RGBtoHex(220, 220, 220));
		this.addBlockColor(Blocks.tallgrass, ReikaColorAPI.RGBtoHex(104, 167, 65));
		this.addBlockColor(Blocks.deadbush, ReikaColorAPI.RGBtoHex(146, 99, 44));
		this.addBlockColor(Blocks.piston, ReikaColorAPI.RGBtoHex(178, 142, 90));
		this.addBlockColor(Blocks.piston_head, UNKNOWN_COLOR);

		this.addWool();

		this.addBlockColor(Blocks.piston_extension, UNKNOWN_COLOR);
		this.addBlockColor(Blocks.yellow_flower, ReikaColorAPI.RGBtoHex(255, 255, 0));

		this.addFlowers();

		this.addBlockColor(Blocks.brown_mushroom, ReikaColorAPI.RGBtoHex(202, 151, 119));
		this.addBlockColor(Blocks.red_mushroom, ReikaColorAPI.RGBtoHex(225, 24, 25));
		this.addBlockColor(Blocks.gold_block, ReikaColorAPI.RGBtoHex(255, 240, 69));
		this.addBlockColor(Blocks.iron_block, ReikaColorAPI.RGBtoHex(232, 232, 232));
		this.addBlockMimic(Blocks.double_stone_slab, Blocks.stone_slab);

		this.addSlabs();

		this.addBlockColor(Blocks.brick_block, ReikaColorAPI.RGBtoHex(175, 91, 72));
		this.addBlockColor(Blocks.tnt, ReikaColorAPI.RGBtoHex(216, 58, 19));
		this.addBlockColor(Blocks.bookshelf, ReikaColorAPI.RGBtoHex(186, 150, 98));
		this.addBlockColor(Blocks.mossy_cobblestone, ReikaColorAPI.RGBtoHex(69, 143, 69));
		this.addBlockColor(Blocks.obsidian, ReikaColorAPI.RGBtoHex(62, 51, 86));
		this.addBlockColor(Blocks.torch, ReikaColorAPI.RGBtoHex(255, 214, 0));
		this.addBlockColor(Blocks.fire, ReikaColorAPI.RGBtoHex(255, 170, 0));
		this.addBlockColor(Blocks.mob_spawner, ReikaColorAPI.RGBtoHex(39, 64, 81));
		this.addBlockMimic(Blocks.oak_stairs, Blocks.planks);
		this.addBlockMimic(Blocks.chest, Blocks.planks);
		this.addBlockColor(Blocks.redstone_wire, ReikaColorAPI.RGBtoHex(145, 0, 16));
		this.addBlockColor(Blocks.diamond_ore, ReikaColorAPI.RGBtoHex(93, 235, 244));
		this.addBlockColor(Blocks.diamond_block, ReikaColorAPI.RGBtoHex(104, 222, 217));
		this.addBlockMimic(Blocks.crafting_table, Blocks.planks);
		this.addBlockColor(Blocks.wheat, ReikaColorAPI.RGBtoHex(4, 189, 18));
		this.addBlockColor(Blocks.farmland, ReikaColorAPI.RGBtoHex(96, 55, 27));
		this.addBlockColor(Blocks.furnace, ReikaColorAPI.RGBtoHex(119, 119, 119));
		this.addBlockMimic(Blocks.lit_furnace, Blocks.furnace);
		this.addBlockMimic(Blocks.standing_sign, Blocks.planks);
		this.addBlockMimic(Blocks.wooden_door, Blocks.planks);
		this.addBlockColor(Blocks.ladder, ReikaColorAPI.RGBtoHex(170, 134, 82));
		this.addBlockColor(Blocks.rail, ReikaColorAPI.RGBtoHex(170, 134, 82));
		this.addBlockMimic(Blocks.stone_stairs, Blocks.cobblestone);
		this.addBlockMimic(Blocks.wall_sign, Blocks.planks);
		this.addBlockColor(Blocks.lever, ReikaColorAPI.RGBtoHex(123, 98, 64));
		this.addBlockMimic(Blocks.stone_pressure_plate, Blocks.stone);
		this.addBlockColor(Blocks.iron_door, ReikaColorAPI.RGBtoHex(222, 222, 222));
		this.addBlockMimic(Blocks.wooden_pressure_plate, Blocks.planks);
		this.addBlockColor(Blocks.redstone_ore, ReikaColorAPI.RGBtoHex(215, 0, 0));
		this.addBlockMimic(Blocks.lit_redstone_ore, Blocks.redstone_ore);
		this.addBlockMimic(Blocks.unlit_redstone_torch, Blocks.redstone_torch);
		this.addBlockColor(Blocks.redstone_torch, ReikaColorAPI.RGBtoHex(173, 0, 0));
		this.addBlockMimic(Blocks.stone_button, Blocks.stone);
		this.addBlockMimic(Blocks.snow_layer, Blocks.snow);
		this.addBlockColor(Blocks.ice, ReikaColorAPI.RGBtoHex(117, 166, 255));
		this.addBlockColor(Blocks.snow, ReikaColorAPI.RGBtoHex(255, 255, 255));
		this.addBlockColor(Blocks.cactus, ReikaColorAPI.RGBtoHex(24, 126, 37));
		this.addBlockColor(Blocks.clay, ReikaColorAPI.RGBtoHex(171, 175, 191));
		this.addBlockColor(Blocks.reeds, ReikaColorAPI.RGBtoHex(168, 217, 115));
		this.addBlockColor(Blocks.jukebox, ReikaColorAPI.RGBtoHex(147, 90, 64));
		this.addBlockMimic(Blocks.fence, Blocks.planks);
		this.addBlockColor(Blocks.pumpkin, ReikaColorAPI.RGBtoHex(226, 142, 34));
		this.addBlockColor(Blocks.netherrack, ReikaColorAPI.RGBtoHex(163, 66, 66));
		this.addBlockColor(Blocks.soul_sand, ReikaColorAPI.RGBtoHex(92, 74, 63));
		this.addBlockColor(Blocks.glowstone, ReikaColorAPI.RGBtoHex(248, 210, 154));
		this.addBlockColor(Blocks.portal, ReikaColorAPI.RGBtoHex(128, 0, 255));
		this.addBlockMimic(Blocks.lit_pumpkin, Blocks.pumpkin);
		this.addBlockColor(Blocks.cake, ReikaColorAPI.RGBtoHex(165, 83, 37));
		this.addBlockMimic(Blocks.unpowered_repeater, Blocks.powered_repeater);
		this.addBlockColor(Blocks.powered_repeater, ReikaColorAPI.RGBtoHex(145, 32, 48));

		this.addBlockMimic(Blocks.stained_glass, Blocks.wool); //dye glass
		this.addBlockMimic(Blocks.stained_glass_pane, Blocks.stained_glass); //dye glass

		this.addBlockColor(Blocks.trapdoor, ReikaColorAPI.RGBtoHex(141, 106, 55));
		this.addBlockColor(Blocks.monster_egg, ReikaColorAPI.RGBtoHex(156, 156, 156));
		this.addBlockColor(Blocks.stonebrick, ReikaColorAPI.RGBtoHex(135, 135, 135));
		this.addBlockColor(Blocks.brown_mushroom_block, ReikaColorAPI.RGBtoHex(148, 113, 90));
		this.addBlockColor(Blocks.red_mushroom_block, ReikaColorAPI.RGBtoHex(179, 34, 32));
		this.addBlockColor(Blocks.iron_bars, ReikaColorAPI.RGBtoHex(106, 104, 106));
		this.addBlockMimic(Blocks.glass_pane, Blocks.glass);
		this.addBlockColor(Blocks.melon_block, ReikaColorAPI.RGBtoHex(175, 173, 43));
		this.addBlockColor(Blocks.pumpkin_stem, ReikaColorAPI.RGBtoHex(192, 128, 140));
		this.addBlockMimic(Blocks.melon_stem, Blocks.pumpkin_stem);
		this.addBlockColor(Blocks.vine, ReikaColorAPI.RGBtoHex(26, 139, 40));
		this.addBlockMimic(Blocks.fence_gate, Blocks.fence);
		this.addBlockMimic(Blocks.brick_stairs, Blocks.brick_block);
		this.addBlockMimic(Blocks.stone_brick_stairs, Blocks.stonebrick);
		this.addBlockColor(Blocks.mycelium, ReikaColorAPI.RGBtoHex(97, 82, 104));
		this.addBlockColor(Blocks.waterlily, ReikaColorAPI.RGBtoHex(30, 53, 15));
		this.addBlockColor(Blocks.nether_brick, ReikaColorAPI.RGBtoHex(73, 39, 46));
		this.addBlockMimic(Blocks.nether_brick_fence, Blocks.nether_brick);
		this.addBlockMimic(Blocks.nether_brick_stairs, Blocks.nether_brick);
		this.addBlockColor(Blocks.nether_wart, ReikaColorAPI.RGBtoHex(159, 41, 45));
		this.addBlockColor(Blocks.enchanting_table, ReikaColorAPI.RGBtoHex(160, 46, 45));
		this.addBlockColor(Blocks.brewing_stand, ReikaColorAPI.RGBtoHex(196, 186, 81));
		this.addBlockColor(Blocks.cauldron, ReikaColorAPI.RGBtoHex(59, 59, 59));
		this.addBlockColor(Blocks.end_portal, ReikaColorAPI.RGBtoHex(0, 0, 0));
		this.addBlockColor(Blocks.end_portal_frame, ReikaColorAPI.RGBtoHex(67, 114, 102));
		this.addBlockColor(Blocks.end_stone, ReikaColorAPI.RGBtoHex(234, 247, 180));
		this.addBlockColor(Blocks.dragon_egg, ReikaColorAPI.RGBtoHex(48, 5, 54));
		this.addBlockColor(Blocks.redstone_lamp, ReikaColorAPI.RGBtoHex(222, 147, 71));
		this.addBlockMimic(Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
		this.addBlockMimic(Blocks.double_wooden_slab, Blocks.wooden_slab);
		this.addBlockMimic(Blocks.wooden_slab, Blocks.planks);
		this.addBlockColor(Blocks.cocoa, ReikaColorAPI.RGBtoHex(177, 98, 28));
		this.addBlockMimic(Blocks.sandstone_stairs, Blocks.sandstone);
		this.addBlockColor(Blocks.emerald_ore, ReikaColorAPI.RGBtoHex(23, 221, 98));
		this.addBlockColor(Blocks.ender_chest, ReikaColorAPI.RGBtoHex(43, 61, 63));
		this.addBlockMimic(Blocks.tripwire_hook, Blocks.planks);
		this.addBlockColor(Blocks.tripwire, ReikaColorAPI.RGBtoHex(33, 33, 33)); //render tripwires as air
		this.addBlockColor(Blocks.emerald_block, ReikaColorAPI.RGBtoHex(63, 213, 102));
		this.addBlockColor(Blocks.spruce_stairs, ReikaColorAPI.RGBtoHex(127, 94, 56));
		this.addBlockColor(Blocks.birch_stairs, ReikaColorAPI.RGBtoHex(213, 201, 139));
		this.addBlockColor(Blocks.jungle_stairs, ReikaColorAPI.RGBtoHex(182, 133, 99));
		this.addBlockColor(Blocks.command_block, ReikaColorAPI.RGBtoHex(199, 126, 79));
		this.addBlockColor(Blocks.beacon, ReikaColorAPI.RGBtoHex(44, 197, 87));
		this.addBlockColor(Blocks.cobblestone_wall, ReikaColorAPI.RGBtoHex(99, 99, 99));
		this.addBlockColor(Blocks.flower_pot, ReikaColorAPI.RGBtoHex(116, 63, 48));
		this.addBlockColor(Blocks.carrots, ReikaColorAPI.RGBtoHex(4, 189, 18));
		this.addBlockColor(Blocks.potatoes, ReikaColorAPI.RGBtoHex(4, 189, 18));
		this.addBlockMimic(Blocks.wooden_button, Blocks.planks);
		this.addBlockColor(Blocks.skull, ReikaColorAPI.RGBtoHex(90, 90, 90));
		this.addBlockColor(Blocks.anvil, ReikaColorAPI.RGBtoHex(67, 67, 67));
		this.addBlockMimic(Blocks.trapped_chest, Blocks.chest);
		this.addBlockMimic(Blocks.light_weighted_pressure_plate, Blocks.gold_block);
		this.addBlockMimic(Blocks.heavy_weighted_pressure_plate, Blocks.iron_block);
		this.addBlockMimic(Blocks.unpowered_comparator, Blocks.powered_repeater);
		this.addBlockMimic(Blocks.powered_comparator, Blocks.powered_repeater);
		this.addBlockColor(Blocks.daylight_detector, ReikaColorAPI.RGBtoHex(71, 61, 41));
		this.addBlockColor(Blocks.redstone_block, ReikaColorAPI.RGBtoHex(255, 100, 0));
		this.addBlockColor(Blocks.quartz_ore, ReikaColorAPI.RGBtoHex(203, 191, 177));
		this.addBlockColor(Blocks.hopper, ReikaColorAPI.RGBtoHex(75, 75, 75));
		this.addBlockColor(Blocks.quartz_block, ReikaColorAPI.RGBtoHex(236, 232, 226));
		this.addBlockMimic(Blocks.quartz_stairs, Blocks.quartz_block);
		this.addBlockColor(Blocks.activator_rail, ReikaColorAPI.RGBtoHex(183, 12, 12));
		this.addBlockMimic(Blocks.dropper, Blocks.dispenser);

		this.addBlockColor(Blocks.stained_hardened_clay, ReikaColorAPI.RGBtoHex(183, 12, 12));
		this.addBlockColor(Blocks.hay_block, ReikaColorAPI.RGBtoHex(255, 209, 94));

		/*
		this.addBlockMimic(Blocks.tintedThinGlass, Blocks.trappedChest); //tinted glass
		this.addBlockColor(Blocks.newLeaf, uhh);
		this.addBlockColor(Blocks.newLog, uhh); //meta values
		this.addBlockMimic(Blocks.carpet, Blocks.wool); //tinted glass
		 */

		this.addBlockColor(Blocks.hardened_clay, ReikaColorAPI.RGBtoHex(158, 100, 73));
		this.addBlockColor(Blocks.coal_block, ReikaColorAPI.RGBtoHex(21, 21, 21));

		/*
this.addBlockColor(Blocks.packedIce, ReikaColorAPI.RGBtoHex(165, 195, 247)); //meta values

		 */

		boolean renderore = ConfigRegistry.GPRORES.getState() || DragonAPICore.isReikasComputer();
		if (!renderore) {
			for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
				ReikaOreHelper ore = ReikaOreHelper.oreList[i];
				Block b = ore.getOreBlockInstance();
				this.addBlockMimic(b, ore.getOreGenBlock());
			}
		}

		this.addRotaryCraft();

		this.addFluids();

		this.addModOres();
		this.addModWood();
		this.addModCrops();

		this.loadModData();
	}

	private void loadModData() {
		Set<ItemStack> keys = BlockColorInterface.getMappedBlocks();
		for (ItemStack li : keys) {
			Block id = Block.getBlockFromItem(li.getItem());
			int meta = li.getItemDamage();
			int color = BlockColorInterface.getColor(id, meta);
			RotaryCraft.logger.log("Received mod request for block "+id+":"+meta+" to have color mapping "+color);
			this.addOrSetColorMapping(id, meta, color, false);
		}
	}

	private void addRotaryCraft() {
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			BlockRegistry r = BlockRegistry.blockList[i];
			this.addBlockColor(r.getBlockInstance(), ReikaColorAPI.RGBtoHex(200, 200, 200));
		}
		this.addBlockColor(BlockRegistry.MININGPIPE, ReikaColorAPI.RGBtoHex(80, 80, 80));
		this.addBlockColor(BlockRegistry.CANOLA, 0x00bb00);
		this.addBlockMimic(BlockRegistry.BEDROCKSLICE, Blocks.bedrock);
		this.addBlockColor(BlockRegistry.LIGHT, ReikaColorAPI.RGBtoHex(33, 33, 33));
		this.addBlockColor(BlockRegistry.BEAM, ReikaColorAPI.RGBtoHex(33, 33, 33));
		this.addBlockColor(BlockRegistry.BRIDGE, 0x00aaff);

		this.addBlockMimic(BlockRegistry.BLASTPANE, Blocks.obsidian);
		this.addBlockMimic(BlockRegistry.BLASTGLASS, Blocks.obsidian);

		BlockColor deco = new BlockColor();
		deco.addMetaColor(0, 210, 200, 220);
		deco.addMetaColor(1, 240, 240, 240);
		deco.addMetaColor(2, 15, 15, 15);
		map.put(BlockRegistry.DECO.getBlockInstance(), deco);
	}

	private void addFluids() {
		for (String s : FluidRegistry.getRegisteredFluids().keySet()) {
			Fluid f = FluidRegistry.getFluid(s);
			if (f != null && !f.equals(FluidRegistry.WATER) && !f.equals(FluidRegistry.LAVA)) {
				if (f.canBePlacedInWorld()) {
					Block b = f.getBlock();
					;
					int color = f.getColor();
					this.addBlockColor(b, color);
				}
			}
		}
	}

	private void addModCrops() {
		for (int i = 0; i < ModCropList.cropList.length; i++) {
			ModCropList crop = ModCropList.cropList[i];
			if (crop.exists()) {
				int minmeta = 0; //not best
				int maxmeta = crop.ripeMeta;
				int color = crop.cropColor;
				Block id = null;
				if (crop.isHandlered()) {
					color = 0x337A53;
				}
				else {
					id = crop.blockID;
				}
				if (id != null) {
					for (int k = minmeta; k <= maxmeta; k++) {
						this.addOrSetColorMapping(id, k, color, true);
					}
				}
			}
		}
	}

	private void addModOres() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			int color = ore.oreColor;
			ArrayList<ItemStack> li = ore.getAllOreBlocks();
			for (int k = 0; k < li.size(); k++) {
				ItemStack is = li.get(k);
				Block id = Block.getBlockFromItem(is.getItem());
				int meta = is.getItemDamage();
				this.addOrSetColorMapping(id, meta, color, true);
			}
		}
	}

	private void addModWood() {
		for (int i = 0; i < ModWoodList.woodList.length; i++) {
			ModWoodList wood = ModWoodList.woodList[i];
			if (wood.exists()) {
				int log = wood.logColor;
				int leaf = wood.leafColor;
				Block logID = Block.getBlockFromItem(wood.getItem().getItem());
				Block leafID = Block.getBlockFromItem(wood.getCorrespondingLeaf().getItem());
				Block saplingID = Block.getBlockFromItem(wood.getCorrespondingSapling().getItem());

				this.addOrSetColorMapping(saplingID, wood.getCorrespondingSapling().getItemDamage(), 0x3C9119, true);

				List<Integer> logMetas = wood.getLogMetadatas();
				for (int k = 0; k < logMetas.size(); k++) {
					int meta = logMetas.get(k);
					this.addOrSetColorMapping(logID, meta, log, true);
				}

				List<Integer> leafMetas = wood.getLeafMetadatas();
				for (int k = 0; k < leafMetas.size(); k++) {
					int meta = leafMetas.get(k);
					this.addOrSetColorMapping(leafID, meta, leaf, true);
				}
			}
		}
	}

	private void addOrSetColorMapping(Block b, int meta, int color, boolean allowOverwrite) {
		if (b == null)
			throw new IllegalArgumentException("Block ID "+b+" does not exist!");
		BlockColor c = map.get(b);
		if (c == null) {
			c = new BlockColor();
			map.put(b, c);
		}
		if (c.colorInts.containsKey(meta) && !allowOverwrite)
			throw new IllegalArgumentException("Cannot overwrite color mapping for "+b+":"+meta+"!");
		c.addMetaColor(meta, color);
	}

	private void addSlabs() {
		BlockColor slab = new BlockColor();
		int[] colors = new int[]{0xA3A3A3, 0xDCD3A0, 0xBC9862, 0x969696, 0xA55B47, 0x797979, 0x36181E, 0xE8E4DC};
		for (int i = 0; i < colors.length; i++) {
			slab.addMetaColor(i, colors[i]);
		}
		map.put(Blocks.stone_slab, slab);
	}

	private void addFlowers() {
		BlockColor rose = new BlockColor();
		int[] colors = new int[]{0xF7070F, 0x29AEFB, 0xBF75FB, 0xF2F29C, 0xD33A17, 0xE17124, 0xF3F3F3, 0xEABEEA, 0xD2C71E};
		for (int i = 0; i < colors.length; i++) {
			rose.addMetaColor(i, colors[i]);
		}
		map.put(Blocks.red_flower, rose);
		/*
		BlockColor flowers = new BlockColor(0);
		int[] colors2 = new int[]{0xE2A41F, 0x9F78A4, 0x58864C, 0x58864C, 0xBA050B, 0xDEA5F7};
		for (int i = 0; i < colors2.length; i++) {
			flowers.addMetaColor(i, colors2[i]);
		}
		map.put(Blocks.newFlowers, flowers);*/
	}

	private void addWool() {
		BlockColor c = new BlockColor();
		for (int i = 0; i < 16; i++) {
			ReikaDyeHelper color = ReikaDyeHelper.dyes[i];
			int meta = color.getWoolMeta();
			c.addMetaColor(meta, color.color);
		}
		map.put(Blocks.wool, c);
	}

	private void addDyeGlass() {
		BlockColor c = new BlockColor();
		for (int i = 0; i < 16; i++) {
			ReikaDyeHelper color = ReikaDyeHelper.dyes[i];
			int meta = color.getWoolMeta();
			c.addMetaColor(meta, color.color);
		}
		map.put(Blocks.stained_glass, c);
		map.put(Blocks.stained_glass_pane, c);
	}

	private void addBlockColor(Block b, int rgb) {
		map.put(b, new BlockColor(rgb));
	}

	private void addBlockColor(BlockRegistry b, int rgb) {
		this.addBlockColor(b.getBlockInstance(), rgb);
	}

	private void addBlockMimic(BlockRegistry mimic, Block target) {
		this.addBlockMimic(mimic.getBlockInstance(), target);
	}

	private void addBlockMimic(Block mimic, Block target) {
		if (mimic == null || target == null)
			throw new IllegalArgumentException("Null cannot mimic or be mimicked!");
		if (mimic == target)
			throw new IllegalArgumentException("A block cannot mimic itself!");
		mimics.put(mimic, target);
	}

	private static class BlockColor {
		private final HashMap<Integer, Integer> colorInts = new HashMap();
		private boolean metaTag = false;

		public BlockColor(int r, int g, int b) {
			colorInts.put(0, ReikaColorAPI.RGBtoHex(r, g, b));
		}

		public BlockColor(int rgb) {
			colorInts.put(0, rgb);
		}

		public BlockColor() {

		}

		public BlockColor addMetaColor(int meta, int r, int g, int b) {
			return this.addMetaColor(meta, ReikaColorAPI.RGBtoHex(r, g, b));
		}

		public BlockColor addMetaColor(int meta, int rgb) {
			metaTag = true;
			colorInts.put(meta, rgb);
			return this;
		}

		public int getColorInt(int meta) {
			if (metaTag) {
				if (colorInts.containsKey(meta)) {
					return colorInts.get(meta);
				}
				else {
					return BlockColorMapper.UNKNOWN_COLOR;
				}
			}
			else {
				return colorInts.get(0);
			}
		}

		public Color getJavaColor(int meta) {
			return new Color(this.getColorInt(meta));
		}
	}

	public int getColorForBlock(Block b, int meta) {
		if (b == Blocks.air)
			return AIR_COLOR;
		if (b == null)
			return UNKNOWN_COLOR;
		Block mimic = mimics.get(b);
		if (mimic != null)
			return this.getColorForBlock(mimic, meta);
		BlockColor c = map.get(b);
		return c != null ? c.getColorInt(meta) : UNKNOWN_COLOR;
	}/*

	public void addModBlockColor(int blockID, int metadata, int color) {
		this.addOrSetColorMapping(blockID, metadata, color, false);
	}*/

}