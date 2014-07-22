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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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

public class BlockColorMapper {

	public static final BlockColorMapper instance = new BlockColorMapper();

	public static final int UNKNOWN_COLOR = 0xffD47EFF;
	public static final int AIR_COLOR = ReikaColorAPI.GStoHex(33);

	private final HashMap<Block, BlockColor> map = new HashMap();
	private final HashMap<Block, Block> mimics = new HashMap();

	private BlockColorMapper() {
		this.addBlockColor(Block.stone, ReikaColorAPI.RGBtoHex(126, 126, 126));
		this.addBlockColor(Block.grass, ReikaColorAPI.RGBtoHex(104, 167, 65));
		this.addBlockColor(Block.dirt, ReikaColorAPI.RGBtoHex(120, 85, 60));
		this.addBlockColor(Block.cobblestone, ReikaColorAPI.RGBtoHex(99, 99, 99));
		this.addBlockColor(Block.planks, ReikaColorAPI.RGBtoHex(178, 142, 90)); //-----------------------------------?
		this.addBlockColor(Block.sapling, ReikaColorAPI.RGBtoHex(0, 255, 0));
		this.addBlockColor(Block.bedrock, ReikaColorAPI.RGBtoHex(50, 50, 50));
		this.addBlockColor(Block.waterMoving, ReikaColorAPI.RGBtoHex(0, 0, 255));
		this.addBlockMimic(Block.waterStill, Block.waterMoving);
		this.addBlockColor(Block.lavaMoving, ReikaColorAPI.RGBtoHex(255, 40, 0));
		this.addBlockMimic(Block.lavaStill, Block.lavaMoving);
		this.addBlockColor(Block.sand, ReikaColorAPI.RGBtoHex(225, 219, 163));
		this.addBlockColor(Block.gravel, ReikaColorAPI.RGBtoHex(159, 137, 131));
		this.addBlockColor(Block.oreGold, ReikaColorAPI.RGBtoHex(251, 237, 76));
		this.addBlockColor(Block.oreIron, ReikaColorAPI.RGBtoHex(214, 173, 145));
		this.addBlockColor(Block.oreCoal, ReikaColorAPI.RGBtoHex(70, 70, 70));
		this.addBlockColor(Block.wood, ReikaColorAPI.RGBtoHex(103, 83, 53));
		this.addBlockColor(Block.leaves, ReikaColorAPI.RGBtoHex(87, 171, 65));
		this.addBlockColor(Block.sponge, ReikaColorAPI.RGBtoHex(204, 204, 71));
		this.addBlockColor(Block.glass, ReikaColorAPI.RGBtoHex(190, 244, 254));
		this.addBlockColor(Block.oreLapis, ReikaColorAPI.RGBtoHex(40, 98, 175));
		this.addBlockColor(Block.blockLapis, ReikaColorAPI.RGBtoHex(21, 52, 188));
		this.addBlockColor(Block.dispenser, ReikaColorAPI.RGBtoHex(119, 119, 119));
		this.addBlockColor(Block.sandStone, ReikaColorAPI.RGBtoHex(212, 205, 153));
		this.addBlockColor(Block.music, ReikaColorAPI.RGBtoHex(147, 90, 64));
		this.addBlockColor(Block.bed, ReikaColorAPI.RGBtoHex(136, 27, 27));
		this.addBlockColor(Block.railPowered, ReikaColorAPI.RGBtoHex(220, 182, 47));
		this.addBlockColor(Block.railDetector, ReikaColorAPI.RGBtoHex(134, 0, 0));
		this.addBlockColor(Block.pistonStickyBase, ReikaColorAPI.RGBtoHex(122, 190, 111));
		this.addBlockColor(Block.web, ReikaColorAPI.RGBtoHex(220, 220, 220));
		this.addBlockColor(Block.tallGrass, ReikaColorAPI.RGBtoHex(104, 167, 65));
		this.addBlockColor(Block.deadBush, ReikaColorAPI.RGBtoHex(146, 99, 44));
		this.addBlockColor(Block.pistonBase, ReikaColorAPI.RGBtoHex(178, 142, 90));
		this.addBlockColor(Block.pistonExtension, UNKNOWN_COLOR);

		this.addWool();

		this.addBlockColor(Block.pistonMoving, UNKNOWN_COLOR);
		this.addBlockColor(Block.plantYellow, ReikaColorAPI.RGBtoHex(255, 255, 0));

		this.addFlowers();

		this.addBlockColor(Block.mushroomBrown, ReikaColorAPI.RGBtoHex(202, 151, 119));
		this.addBlockColor(Block.mushroomRed, ReikaColorAPI.RGBtoHex(225, 24, 25));
		this.addBlockColor(Block.blockGold, ReikaColorAPI.RGBtoHex(255, 240, 69));
		this.addBlockColor(Block.blockIron, ReikaColorAPI.RGBtoHex(232, 232, 232));
		this.addBlockMimic(Block.stoneDoubleSlab, Block.stoneSingleSlab);

		this.addSlabs();

		this.addBlockColor(Block.brick, ReikaColorAPI.RGBtoHex(175, 91, 72));
		this.addBlockColor(Block.tnt, ReikaColorAPI.RGBtoHex(216, 58, 19));
		this.addBlockColor(Block.bookShelf, ReikaColorAPI.RGBtoHex(186, 150, 98));
		this.addBlockColor(Block.cobblestoneMossy, ReikaColorAPI.RGBtoHex(69, 143, 69));
		this.addBlockColor(Block.obsidian, ReikaColorAPI.RGBtoHex(62, 51, 86));
		this.addBlockColor(Block.torchWood, ReikaColorAPI.RGBtoHex(255, 214, 0));
		this.addBlockColor(Block.fire, ReikaColorAPI.RGBtoHex(255, 170, 0));
		this.addBlockColor(Block.mobSpawner, ReikaColorAPI.RGBtoHex(39, 64, 81));
		this.addBlockMimic(Block.stairsWoodOak, Block.planks);
		this.addBlockMimic(Block.chest, Block.planks);
		this.addBlockColor(Block.redstoneWire, ReikaColorAPI.RGBtoHex(145, 0, 16));
		this.addBlockColor(Block.oreDiamond, ReikaColorAPI.RGBtoHex(93, 235, 244));
		this.addBlockColor(Block.blockDiamond, ReikaColorAPI.RGBtoHex(104, 222, 217));
		this.addBlockMimic(Block.workbench, Block.planks);
		this.addBlockColor(Block.crops, ReikaColorAPI.RGBtoHex(4, 189, 18));
		this.addBlockColor(Block.tilledField, ReikaColorAPI.RGBtoHex(96, 55, 27));
		this.addBlockColor(Block.furnaceIdle, ReikaColorAPI.RGBtoHex(119, 119, 119));
		this.addBlockMimic(Block.furnaceBurning, Block.furnaceIdle);
		this.addBlockMimic(Block.signPost, Block.planks);
		this.addBlockMimic(Block.doorWood, Block.planks);
		this.addBlockColor(Block.ladder, ReikaColorAPI.RGBtoHex(170, 134, 82));
		this.addBlockColor(Block.rail, ReikaColorAPI.RGBtoHex(170, 134, 82));
		this.addBlockMimic(Block.stairsCobblestone, Block.cobblestone);
		this.addBlockMimic(Block.signWall, Block.planks);
		this.addBlockColor(Block.lever, ReikaColorAPI.RGBtoHex(123, 98, 64));
		this.addBlockMimic(Block.pressurePlateStone, Block.stone);
		this.addBlockColor(Block.doorIron, ReikaColorAPI.RGBtoHex(222, 222, 222));
		this.addBlockMimic(Block.pressurePlatePlanks, Block.planks);
		this.addBlockColor(Block.oreRedstone, ReikaColorAPI.RGBtoHex(215, 0, 0));
		this.addBlockMimic(Block.oreRedstoneGlowing, Block.oreRedstone);
		this.addBlockMimic(Block.torchRedstoneIdle, Block.torchRedstoneActive);
		this.addBlockColor(Block.torchRedstoneActive, ReikaColorAPI.RGBtoHex(173, 0, 0));
		this.addBlockMimic(Block.stoneButton, Block.stone);
		this.addBlockMimic(Block.snow, Block.blockSnow);
		this.addBlockColor(Block.ice, ReikaColorAPI.RGBtoHex(117, 166, 255));
		this.addBlockColor(Block.blockSnow, ReikaColorAPI.RGBtoHex(255, 255, 255));
		this.addBlockColor(Block.cactus, ReikaColorAPI.RGBtoHex(24, 126, 37));
		this.addBlockColor(Block.blockClay, ReikaColorAPI.RGBtoHex(171, 175, 191));
		this.addBlockColor(Block.reed, ReikaColorAPI.RGBtoHex(168, 217, 115));
		this.addBlockColor(Block.jukebox, ReikaColorAPI.RGBtoHex(147, 90, 64));
		this.addBlockMimic(Block.fence, Block.planks);
		this.addBlockColor(Block.pumpkin, ReikaColorAPI.RGBtoHex(226, 142, 34));
		this.addBlockColor(Block.netherrack, ReikaColorAPI.RGBtoHex(163, 66, 66));
		this.addBlockColor(Block.slowSand, ReikaColorAPI.RGBtoHex(92, 74, 63));
		this.addBlockColor(Block.glowStone, ReikaColorAPI.RGBtoHex(248, 210, 154));
		this.addBlockColor(Block.portal, ReikaColorAPI.RGBtoHex(128, 0, 255));
		this.addBlockMimic(Block.pumpkinLantern, Block.pumpkin);
		this.addBlockColor(Block.cake, ReikaColorAPI.RGBtoHex(165, 83, 37));
		this.addBlockMimic(Block.redstoneRepeaterIdle, Block.redstoneRepeaterActive);
		this.addBlockColor(Block.redstoneRepeaterActive, ReikaColorAPI.RGBtoHex(145, 32, 48));
		this.addBlockMimic(Block.lockedChest, Block.cloth); //dye glass
		this.addBlockColor(Block.trapdoor, ReikaColorAPI.RGBtoHex(141, 106, 55));
		this.addBlockColor(Block.silverfish, ReikaColorAPI.RGBtoHex(156, 156, 156));
		this.addBlockColor(Block.stoneBrick, ReikaColorAPI.RGBtoHex(135, 135, 135));
		this.addBlockColor(Block.mushroomCapBrown, ReikaColorAPI.RGBtoHex(148, 113, 90));
		this.addBlockColor(Block.mushroomCapRed, ReikaColorAPI.RGBtoHex(179, 34, 32));
		this.addBlockColor(Block.fenceIron, ReikaColorAPI.RGBtoHex(106, 104, 106));
		this.addBlockMimic(Block.thinGlass, Block.glass);
		this.addBlockColor(Block.melon, ReikaColorAPI.RGBtoHex(175, 173, 43));
		this.addBlockColor(Block.pumpkinStem, ReikaColorAPI.RGBtoHex(192, 128, 140));
		this.addBlockMimic(Block.melonStem, Block.pumpkinStem);
		this.addBlockColor(Block.vine, ReikaColorAPI.RGBtoHex(26, 139, 40));
		this.addBlockMimic(Block.fenceGate, Block.fence);
		this.addBlockMimic(Block.stairsBrick, Block.brick);
		this.addBlockMimic(Block.stairsStoneBrick, Block.stoneBrick);
		this.addBlockColor(Block.mycelium, ReikaColorAPI.RGBtoHex(97, 82, 104));
		this.addBlockColor(Block.waterlily, ReikaColorAPI.RGBtoHex(30, 53, 15));
		this.addBlockColor(Block.netherBrick, ReikaColorAPI.RGBtoHex(73, 39, 46));
		this.addBlockMimic(Block.netherFence, Block.netherBrick);
		this.addBlockMimic(Block.stairsNetherBrick, Block.netherBrick);
		this.addBlockColor(Block.netherStalk, ReikaColorAPI.RGBtoHex(159, 41, 45));
		this.addBlockColor(Block.enchantmentTable, ReikaColorAPI.RGBtoHex(160, 46, 45));
		this.addBlockColor(Block.brewingStand, ReikaColorAPI.RGBtoHex(196, 186, 81));
		this.addBlockColor(Block.cauldron, ReikaColorAPI.RGBtoHex(59, 59, 59));
		this.addBlockColor(Block.endPortal, ReikaColorAPI.RGBtoHex(0, 0, 0));
		this.addBlockColor(Block.endPortalFrame, ReikaColorAPI.RGBtoHex(67, 114, 102));
		this.addBlockColor(Block.whiteStone, ReikaColorAPI.RGBtoHex(234, 247, 180));
		this.addBlockColor(Block.dragonEgg, ReikaColorAPI.RGBtoHex(48, 5, 54));
		this.addBlockColor(Block.redstoneLampIdle, ReikaColorAPI.RGBtoHex(222, 147, 71));
		this.addBlockMimic(Block.redstoneLampActive, Block.redstoneLampIdle);
		this.addBlockMimic(Block.woodDoubleSlab, Block.woodSingleSlab);
		this.addBlockMimic(Block.woodSingleSlab, Block.planks);
		this.addBlockColor(Block.cocoaPlant, ReikaColorAPI.RGBtoHex(177, 98, 28));
		this.addBlockMimic(Block.stairsSandStone, Block.sandStone);
		this.addBlockColor(Block.oreEmerald, ReikaColorAPI.RGBtoHex(23, 221, 98));
		this.addBlockColor(Block.enderChest, ReikaColorAPI.RGBtoHex(43, 61, 63));
		this.addBlockMimic(Block.tripWireSource, Block.planks);
		this.addBlockColor(Block.tripWire, ReikaColorAPI.RGBtoHex(33, 33, 33)); //render tripwires as air
		this.addBlockColor(Block.blockEmerald, ReikaColorAPI.RGBtoHex(63, 213, 102));
		this.addBlockColor(Block.stairsWoodSpruce, ReikaColorAPI.RGBtoHex(127, 94, 56));
		this.addBlockColor(Block.stairsWoodBirch, ReikaColorAPI.RGBtoHex(213, 201, 139));
		this.addBlockColor(Block.stairsWoodJungle, ReikaColorAPI.RGBtoHex(182, 133, 99));
		this.addBlockColor(Block.commandBlock, ReikaColorAPI.RGBtoHex(199, 126, 79));
		this.addBlockColor(Block.beacon, ReikaColorAPI.RGBtoHex(44, 197, 87));
		this.addBlockColor(Block.cobblestoneWall, ReikaColorAPI.RGBtoHex(99, 99, 99));
		this.addBlockColor(Block.flowerPot, ReikaColorAPI.RGBtoHex(116, 63, 48));
		this.addBlockColor(Block.carrot, ReikaColorAPI.RGBtoHex(4, 189, 18));
		this.addBlockColor(Block.potato, ReikaColorAPI.RGBtoHex(4, 189, 18));
		this.addBlockMimic(Block.woodenButton, Block.planks);
		this.addBlockColor(Block.skull, ReikaColorAPI.RGBtoHex(90, 90, 90));
		this.addBlockColor(Block.anvil, ReikaColorAPI.RGBtoHex(67, 67, 67));
		this.addBlockMimic(Block.chestTrapped, Block.chest);
		this.addBlockMimic(Block.pressurePlateGold, Block.blockGold);
		this.addBlockMimic(Block.pressurePlateIron, Block.blockIron);
		this.addBlockMimic(Block.redstoneComparatorIdle, Block.redstoneRepeaterActive);
		this.addBlockMimic(Block.redstoneComparatorActive, Block.redstoneRepeaterActive);
		this.addBlockColor(Block.daylightSensor, ReikaColorAPI.RGBtoHex(71, 61, 41));
		this.addBlockColor(Block.blockRedstone, ReikaColorAPI.RGBtoHex(255, 100, 0));
		this.addBlockColor(Block.oreNetherQuartz, ReikaColorAPI.RGBtoHex(203, 191, 177));
		this.addBlockColor(Block.hopperBlock, ReikaColorAPI.RGBtoHex(75, 75, 75));
		this.addBlockColor(Block.blockNetherQuartz, ReikaColorAPI.RGBtoHex(236, 232, 226));
		this.addBlockMimic(Block.stairsNetherQuartz, Block.blockNetherQuartz);
		this.addBlockColor(Block.railActivator, ReikaColorAPI.RGBtoHex(183, 12, 12));
		this.addBlockMimic(Block.dropper, Block.dispenser);

		this.addBlockColor(Block.stainedClay, ReikaColorAPI.RGBtoHex(183, 12, 12));
		this.addBlockColor(Block.hay, ReikaColorAPI.RGBtoHex(255, 209, 94));

		/*
		this.addBlockMimic(Block.tintedThinGlass, Block.trappedChest); //tinted glass
		this.addBlockColor(Block.newLeaf, uhh);
		this.addBlockColor(Block.newLog, uhh); //meta values
		this.addBlockMimic(Block.carpet, Block.cloth); //tinted glass
		 */

		this.addBlockColor(Block.hardenedClay, ReikaColorAPI.RGBtoHex(158, 100, 73));
		this.addBlockColor(Block.coalBlock, ReikaColorAPI.RGBtoHex(21, 21, 21));

		/*
this.addBlockColor(Block.packedIce, ReikaColorAPI.RGBtoHex(165, 195, 247)); //meta values

		 */

		boolean renderore = ConfigRegistry.GPRORES.getState() || DragonAPICore.isReikasComputer();
		if (!renderore) {
			for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
				ReikaOreHelper ore = ReikaOreHelper.oreList[i];
				Block b = Block.blocksList[ore.getOreBlock().itemID];
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
		Set<List<Integer>> keys = BlockColorInterface.getMappedBlocks();
		for (List<Integer> li : keys) {
			int id = li.get(0);
			int meta = li.get(1);
			int color = BlockColorInterface.getColor(id, meta);
			RotaryCraft.logger.log("Received mod request for block "+id+":"+meta+" to have color mapping "+color);
			this.addOrSetColorMapping(id, meta, color, false);
		}
	}

	private void addRotaryCraft() {
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			BlockRegistry r = BlockRegistry.blockList[i];
			this.addBlockColor(r.getBlockVariable(), ReikaColorAPI.RGBtoHex(200, 200, 200));
		}
		this.addBlockColor(RotaryCraft.miningpipe, ReikaColorAPI.RGBtoHex(80, 80, 80));
		this.addBlockColor(RotaryCraft.canola, 0x00bb00);
		this.addBlockMimic(RotaryCraft.bedrockslice, Block.bedrock);
		this.addBlockColor(RotaryCraft.lightblock, ReikaColorAPI.RGBtoHex(33, 33, 33));
		this.addBlockColor(RotaryCraft.beamblock, ReikaColorAPI.RGBtoHex(33, 33, 33));
		this.addBlockColor(RotaryCraft.lightbridge, 0x00aaff);

		this.addBlockMimic(RotaryCraft.blastpane, Block.obsidian);
		this.addBlockMimic(RotaryCraft.blastglass, Block.obsidian);

		BlockColor deco = new BlockColor();
		deco.addMetaColor(0, 210, 200, 220);
		deco.addMetaColor(1, 240, 240, 240);
		deco.addMetaColor(2, 15, 15, 15);
		map.put(RotaryCraft.decoblock, deco);
	}

	private void addFluids() {
		for (String s : FluidRegistry.getRegisteredFluids().keySet()) {
			Fluid f = FluidRegistry.getFluid(s);
			if (f != null && !f.equals(FluidRegistry.WATER) && !f.equals(FluidRegistry.LAVA)) {
				if (f.canBePlacedInWorld()) {
					int id = f.getBlockID();
					Block b = Block.blocksList[id];
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
				int id = -1;
				if (crop.isHandlered()) {
					color = 0x337A53;
				}
				else {
					id = crop.blockID;
				}
				if (id != -1) {
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
				int id = is.itemID;
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
				int logID = wood.getItem().itemID;
				int leafID = wood.getCorrespondingLeaf().itemID;
				int saplingID = wood.getCorrespondingSapling().itemID;

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

	private void addOrSetColorMapping(int id, int meta, int color, boolean allowOverwrite) {
		Block b = Block.blocksList[id];
		if (b == null)
			throw new IllegalArgumentException("Block ID "+id+" does not exist!");
		BlockColor c = map.get(b);
		if (c == null) {
			c = new BlockColor();
			map.put(b, c);
		}
		if (c.colorInts.containsKey(meta) && !allowOverwrite)
			throw new IllegalArgumentException("Cannot overwrite color mapping for "+id+":"+meta+"!");
		c.addMetaColor(meta, color);
	}

	private void addSlabs() {
		BlockColor slab = new BlockColor();
		int[] colors = new int[]{0xA3A3A3, 0xDCD3A0, 0xBC9862, 0x969696, 0xA55B47, 0x797979, 0x36181E, 0xE8E4DC};
		for (int i = 0; i < colors.length; i++) {
			slab.addMetaColor(i, colors[i]);
		}
		map.put(Block.stoneSingleSlab, slab);
	}

	private void addFlowers() {
		BlockColor rose = new BlockColor();
		int[] colors = new int[]{0xF7070F, 0x29AEFB, 0xBF75FB, 0xF2F29C, 0xD33A17, 0xE17124, 0xF3F3F3, 0xEABEEA, 0xD2C71E};
		for (int i = 0; i < colors.length; i++) {
			rose.addMetaColor(i, colors[i]);
		}
		map.put(Block.plantRed, rose);
		/*
		BlockColor flowers = new BlockColor(0);
		int[] colors2 = new int[]{0xE2A41F, 0x9F78A4, 0x58864C, 0x58864C, 0xBA050B, 0xDEA5F7};
		for (int i = 0; i < colors2.length; i++) {
			flowers.addMetaColor(i, colors2[i]);
		}
		map.put(Block.newFlowers, flowers);*/
	}

	private void addWool() {
		BlockColor c = new BlockColor();
		for (int i = 0; i < 16; i++) {
			ReikaDyeHelper color = ReikaDyeHelper.dyes[i];
			int meta = color.getWoolMeta();
			c.addMetaColor(meta, color.color);
		}
		map.put(Block.cloth, c);
	}

	private void addBlockColor(Block b, int rgb) {
		map.put(b, new BlockColor(rgb));
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

	public int getColorForBlock(int id, int meta) {
		if (id == 0)
			return AIR_COLOR;
		Block b = Block.blocksList[id];
		if (b == null)
			return UNKNOWN_COLOR;
		Block mimic = mimics.get(b);
		if (mimic != null)
			return this.getColorForBlock(mimic.blockID, meta);
		BlockColor c = map.get(b);
		return c != null ? c.getColorInt(meta) : UNKNOWN_COLOR;
	}/*

	public void addModBlockColor(int blockID, int metadata, int color) {
		this.addOrSetColorMapping(blockID, metadata, color, false);
	}*/

}
