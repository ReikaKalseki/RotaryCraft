/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ChargingRecipe;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class RotaryRecipes {
	public static void addRecipes() {
		addOreDictRecipes();
		addMachines();
		addCraftItems();
		addItemBlocks();
		addToolItems();
		addMisc();
		addCharging();
		addFurnace();
	}

	private static void addOreDictRecipes() {
		OreDictionary.initVanillaEntries();
		ArrayList<ItemStack> woods = OreDictionary.getOres("plankWood");
		woods.addAll(OreDictionary.getOres("woodPlank"));
		for (int i = 0; i < woods.size(); i++) {
			ItemStack wood = woods.get(i);

			GameRegistry.addRecipe(MachineRegistry.EXTRACTOR.getCraftedProduct(), new Object[]{
				"SWS", "siD", "PIN", 'D', ItemStacks.drill, 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.shaftitem, 's', Block.stone, 'i', ItemStacks.impeller, 'N', Block.netherrack, 'W', wood});

			GameRegistry.addRecipe(ItemStacks.woodgear, new Object[]{
					" W ", "WWW", " W ", 'W', wood});
			GameRegistry.addRecipe(ItemStacks.flywheelcore, new Object[]{
					"WWW", "WGW", "WWW", 'W', wood, 'G', ItemStacks.steelgear});

			GameRegistry.addRecipe(ItemRegistry.SCREWDRIVER.getCraftedProduct(1), new Object[]{
				"I  ", " S ", "  W", 'S', Item.stick, 'I', Item.ingotIron, 'W', wood});
			GameRegistry.addRecipe(ItemRegistry.SCREWDRIVER.getCraftedProduct(1), new Object[]{
				"I  ", " S ", "  W", 'S', Item.stick, 'I', ItemStacks.steelingot, 'W', wood});
			GameRegistry.addRecipe(ItemRegistry.METER.getCraftedProduct(1), new Object[]{
				"WWW", "WEW", " S ", 'S', Item.stick, 'E', Item.enderPearl, 'I', Item.ingotIron, 'W', wood});

			GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 1), new Object[]{
				"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(0), 'p', wood});
			GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 2), new Object[]{
				"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(1), 'p', wood});
			GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 3), new Object[]{
				"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(2), 'p', wood});

			GameRegistry.addRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(0), new Object[]{ //Wood shaft unit
				"   ", "BSB", "BBB", 'B', wood, 'S', Item.stick});
			GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.hose, 16), new Object[]{ //Hose
				"W W", "W W", "W W", 'W', wood});

			ItemStack gear;
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(0));
			GameRegistry.addRecipe(gear, new Object[]{
					"MGM", "MMM", 'M', wood, 'G', ItemStacks.wood2x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(5));
			GameRegistry.addRecipe(gear, new Object[]{
					"MGM", "MMM", 'M', wood, 'G', ItemStacks.wood4x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(10));
			GameRegistry.addRecipe(gear, new Object[]{
					"MGM", "MMM", 'M', wood, 'G', ItemStacks.wood8x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(15));
			GameRegistry.addRecipe(gear, new Object[]{
					"MGM", "MMM", 'M', wood, 'G', ItemStacks.wood16x});
		}
	}

	private static void addMachines() {
		GameRegistry.addRecipe(MachineRegistry.COMPACTOR.getCraftedProduct(), new Object[]{
			"SPS", "PGP", "#P#", '#', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'P', ItemStacks.presshead, 'G', ItemStacks.gearunit16});
		GameRegistry.addRecipe(MachineRegistry.FAN.getCraftedProduct(), new Object[]{
			"SSS", "SIS", "#G#", '#', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'G', ItemStacks.gearunit});

		GameRegistry.addRecipe(MachineRegistry.AEROSOLIZER.getCraftedProduct(), new Object[]{
			"BRB", "RIR", "BRB", 'B', ItemStacks.basepanel, 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'I', ItemStacks.impeller});

		GameRegistry.addRecipe(MachineRegistry.HEATRAY.getCraftedProduct(), new Object[]{
			"OOO", "BLb", "#P#", '#', ItemStacks.basepanel, 'B', ItemStacks.bulb, 'b', ItemStacks.barrel, 'P', ItemStacks.power, 'L', ItemStacks.lens, 'O', Block.obsidian});

		GameRegistry.addRecipe(MachineRegistry.FLOODLIGHT.getCraftedProduct(), new Object[]{
			"ISO", "Ggd", "I#O", '#', ItemStacks.basepanel, 'I', Item.ingotIron, 'd', Item.ingotGold, 'S', ItemStacks.steelingot, 'G', Block.glass, 'g', Block.glowStone, 'O', Block.obsidian});

		GameRegistry.addRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(RotaryNames.shaftItemNames.length-1), new Object[]{
			" S ", "SSS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem}); //Shaft cross

		GameRegistry.addRecipe(MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(0), new Object[]{
			"SW ", " GS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'W', ItemStacks.wormgear, 'G', ItemStacks.steelgear}); //Worm gear
		GameRegistry.addRecipe(MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(1), new Object[]{
			"BSB", "BSB", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'B', ItemStacks.bearing}); //CVT
		GameRegistry.addRecipe(MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(2), new Object[]{
			"   ", "BCS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'B', ItemStacks.brake, 'C', ItemStacks.tenscoil}); //Coil

		GameRegistry.addRecipe(MachineRegistry.BLASTFURNACE.getCraftedProduct(), new Object[]{
			"SSS", "SrS", "SSS", 'r', Item.redstone, 'S', Block.stoneBrick});

		GameRegistry.addRecipe(MachineRegistry.BEVELGEARS.getCraftedProduct(), new Object[]{
			"ISB", "SGB", "BBB", 'B', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.gearunit});

		GameRegistry.addRecipe(MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(0), new Object[]{
			"W", "M", 'W', ItemStacks.flywheelcore, 'M', ItemStacks.mount});
		GameRegistry.addRecipe(MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(4), new Object[]{
			"W", "M", 'W', ItemStacks.flywheelcore2, 'M', ItemStacks.mount});
		GameRegistry.addRecipe(MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(8), new Object[]{
			"W", "M", 'W', ItemStacks.flywheelcore3, 'M', ItemStacks.mount});
		GameRegistry.addRecipe(MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(12), new Object[]{
			"W", "M", 'W', ItemStacks.flywheelcore4, 'M', ItemStacks.mount});

		GameRegistry.addRecipe(MachineRegistry.SPLITTER.getCraftedProduct(), new Object[]{
			"ISP", "SGP", "ISP", 'P', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.gearunit});

		GameRegistry.addRecipe(MachineRegistry.CLUTCH.getCraftedProduct(), new Object[]{
			"S", "M", "R", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'R', Item.redstone});
		GameRegistry.addRecipe(MachineRegistry.CLUTCH.getCraftedProduct(), new Object[]{
			"S", "R", 'S', new ItemStack(RotaryCraft.shaftitems, 1, 2), 'R', Item.redstone});

		GameRegistry.addRecipe(MachineRegistry.DYNAMOMETER.getCraftedProduct(), new Object[]{
			" S ", " E ", " Ms", 's', ItemStacks.screen, 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'E', Item.enderPearl});

		GameRegistry.addRecipe(MachineRegistry.BEDROCKBREAKER.getCraftedProduct(), new Object[]{
			"BDO", "BSO", "BDO", 'S', ItemStacks.steelingot, 'D', Item.diamond, 'O', Block.obsidian, 'B', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.FERMENTER.getCraftedProduct(), new Object[]{
			"BPB", "PIP", "BPB", 'B', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.GRINDER.getCraftedProduct(), new Object[]{
			"B B", "SGS", "PPP", 'B', ItemStacks.steelingot, 'G', ItemStacks.gearunit, 'P', ItemStacks.basepanel, 'S', ItemStacks.saw});

		GameRegistry.addRecipe(MachineRegistry.RESERVOIR.getCraftedProduct(), new Object[]{
			"B B", "B B", "BBB", 'B', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.FIREWORK.getCraftedProduct(), new Object[]{
			"BEB", "BDB", "BRB", 'B', ItemStacks.basepanel, 'R', Item.redstone, 'E', Item.eyeOfEnder, 'D', Block.dispenser});

		GameRegistry.addRecipe(MachineRegistry.AUTOBREEDER.getCraftedProduct(), new Object[]{
			"B B", "BBB", 'B', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.FRACTIONATOR.getCraftedProduct(), new Object[]{
			"GFG", "GIG", "GPG", 'P', ItemStacks.basepanel, 'I', ItemStacks.mixer, 'G', Item.ingotGold, 'F', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 2)});

		GameRegistry.addRecipe(MachineRegistry.BAITBOX.getCraftedProduct(), new Object[]{
			"BBB", "BAB", "BBB", 'B', Block.fenceIron, 'A', MachineRegistry.AUTOBREEDER.getCraftedProduct()});

		GameRegistry.addRecipe(MachineRegistry.WINDER.getCraftedProduct(), new Object[]{
			" ss", " hg", "ppp", 'h', ItemStacks.shaftitem, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.SMOKEDETECTOR.getCraftedProduct(), new Object[]{
			" S ", "RRR", " N ", 'S', Block.stoneSingleSlab, 'R', Item.redstone, 'N', Block.music});

		GameRegistry.addRecipe(MachineRegistry.ECU.getCraftedProduct(), new Object[]{
			"IPI", "ICI", "IRI", 'I', ItemStacks.steelingot, 'P', ItemStacks.power, 'C', ItemStacks.pcb, 'R', Item.redstone});

		GameRegistry.addRecipe(MachineRegistry.WOODCUTTER.getCraftedProduct(), new Object[]{
			"IS ", "PGS", "PPI", 'I', ItemStacks.steelingot, 'S', ItemStacks.saw, 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit});

		GameRegistry.addRecipe(MachineRegistry.VACUUM.getCraftedProduct(), new Object[]{
			"SIS", "ICI", "SIS", 'C', Block.chest, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller});

		GameRegistry.addRecipe(MachineRegistry.BORER.getCraftedProduct(), new Object[]{
			"SSS", "DGC", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', ItemStacks.drill, 'G', ItemStacks.gearunit, 'C', ItemStacks.pcb});

		GameRegistry.addRecipe(MachineRegistry.SPRINKLER.getCraftedProduct(), new Object[]{
			" s ", " p ", " i ", 's', ItemStacks.steelingot, 'p', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1), 'i', ItemStacks.impeller});

		GameRegistry.addRecipe(MachineRegistry.SPAWNERCONTROLLER.getCraftedProduct(), new Object[]{
			"PCP", "OGO", "g g", 'O', Block.obsidian, 'P', ItemStacks.basepanel, 'G', Item.ingotGold, 'g', Block.glowStone, 'C', ItemStacks.pcb});

		GameRegistry.addRecipe(MachineRegistry.PLAYERDETECTOR.getCraftedProduct(), new Object[]{
			"LRL", "OGO", "OPO", 'L', ReikaItemHelper.lapisDye, 'R', ItemStacks.radar, 'O', Block.obsidian, 'P', ItemStacks.basepanel, 'G', Item.ingotGold});

		GameRegistry.addRecipe(MachineRegistry.OBSIDIAN.getCraftedProduct(), new Object[]{
			"SpS", "PMP", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'p', Block.thinGlass, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});

		GameRegistry.addRecipe(MachineRegistry.HEATER.getCraftedProduct(), new Object[]{
			"sBs", "prp", "scs", 's', ItemStacks.steelingot, 'B', Block.fenceIron, 'p', ItemStacks.basepanel, 'c', ItemStacks.combustor});

		GameRegistry.addRecipe(MachineRegistry.GPR.getCraftedProduct(), new Object[]{
			"SsS", "PCP", "SRS", 'S', ItemStacks.steelingot, 's', ItemStacks.screen, 'P', ItemStacks.basepanel, 'R', ItemStacks.radar, 'C', ItemStacks.pcb});

		GameRegistry.addRecipe(MachineRegistry.PULSEJET.getCraftedProduct(), new Object[]{
			"OCD", "PcO", "BBB", 'B', ItemStacks.basepanel, 'O', Block.obsidian, 'C', ItemStacks.compressor, 'D', ItemStacks.diffuser, 'c', ItemStacks.combustor, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});

		GameRegistry.addRecipe(MachineRegistry.LIGHTBRIDGE.getCraftedProduct(), new Object[]{
			"GgG", "BgS", "BBD", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', Item.diamond, 'G', Item.ingotGold, 'g', Block.glass});

		GameRegistry.addRecipe(MachineRegistry.PILEDRIVER.getCraftedProduct(), new Object[]{
			"PGP", "gFg", "PDP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit8, 'g', ItemStacks.shaftitem, 'F', ItemStacks.flywheelcore3, 'D', ItemStacks.drill});

		GameRegistry.addRecipe(MachineRegistry.PUMP.getCraftedProduct(), new Object[]{
			"SGS", "pIp", "PpP", 'P', ItemStacks.basepanel, 'p', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1), 'I', ItemStacks.impeller, 'G', Block.thinGlass, 'S', ItemStacks.steelingot});

		//GameRegistry.addRecipe(MachineRegistry.RAILGUN.getCraftedProduct(), new Object[]{
		//	"LLS", "LLG", "SGB", 'L', ItemStacks.lim, 'S', ItemStacks.steelingot, 'G', ItemStacks.gearunit, 'B', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.MOBRADAR.getCraftedProduct(), new Object[]{
			" rs", " g ", "pcp", 'r', ItemStacks.radar, 's', ItemStacks.screen, 'c', ItemStacks.pcb, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.TNTCANNON.getCraftedProduct(), new Object[]{
			"sgc", "pcp", "pCr", 'g', Block.blockRedstone, 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Block.chest, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.SONICWEAPON.getCraftedProduct(), new Object[]{
			"psp", "sts", "psp", 't', ItemStacks.turbine, 's', ItemStacks.sonar, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.FORCEFIELD.getCraftedProduct(), new Object[]{
			"lnl", "ddd", "sgs", 'd', Item.diamond, 's', ItemStacks.basepanel, 'n', Item.netherStar, 'g', Item.ingotGold, 'l', ReikaItemHelper.lapisDye});

		GameRegistry.addRecipe(MachineRegistry.MUSICBOX.getCraftedProduct(), new Object[]{
			"sns", "ncn", "sns", 'n', Block.music, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb});

		GameRegistry.addRecipe(MachineRegistry.WEATHERCONTROLLER.getCraftedProduct(), new Object[]{
			"s s", "sls", "pcp", 'l', Block.daylightSensor, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.MOBHARVESTER.getCraftedProduct(), new Object[]{
			"   ", "shs", "sps", 'h', MachineRegistry.HEATER.getCraftedProduct(), 'p', Item.enderPearl, 's', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.PROJECTOR.getCraftedProduct(), new Object[]{
			"sss", "gcl", "ppp", 'c', ItemStacks.pcb, 's', ItemStacks.steelingot, 'g', Block.glass, 'l', Block.glowStone, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.REFRESHER.getCraftedProduct(), new Object[]{
			"ses", "epe", "ses", 'p', Item.enderPearl, 's', ItemStacks.steelingot, 'e', Block.whiteStone});

		GameRegistry.addRecipe(MachineRegistry.CAVESCANNER.getCraftedProduct(), new Object[]{
			"sps", "pcp", "sns", 'n', ItemStacks.sonar, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.SCALECHEST.getCraftedProduct(), new Object[]{
			"sss", "scs", "sss", 'c', Block.chest, 's', ItemStacks.steelingot});

		GameRegistry.addRecipe(MachineRegistry.SPILLER.getCraftedProduct(), new Object[]{
			" p ", "s s", "   ", 'p', ItemStacks.pipe, 's', ItemStacks.steelingot});

		GameRegistry.addRecipe(MachineRegistry.SMOKEDETECTOR.getCraftedProduct(), new Object[]{
			" p ", " s ", "   ", 's', Block.stone, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.IGNITER.getCraftedProduct(), new Object[]{
			"OGO", "GCG", "OGO", 'O', Block.obsidian, 'G', Item.ingotGold, 'C', ItemStacks.combustor});

		GameRegistry.addRecipe(MachineRegistry.CONTAINMENT.getCraftedProduct(), new Object[]{
			"lnl", "ddd", "sgs", 'd', Item.diamond, 's', ItemStacks.basepanel, 'n', Item.netherStar, 'g', Item.ingotGold, 'l', ReikaItemHelper.purpleDye});

		GameRegistry.addRecipe(MachineRegistry.MAGNETIZER.getCraftedProduct(), new Object[]{
			"p p", "gmg", "prp", 'r', Item.redstone, 'p', ItemStacks.basepanel, 'm', ItemStacks.mount, 'g', ItemStacks.goldcoil});

		GameRegistry.addRecipe(MachineRegistry.FREEZEGUN.getCraftedProduct(), new Object[]{
			" ss", "iig", "sgp", 'i', Block.ice, 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit});

		GameRegistry.addRecipe(MachineRegistry.SCREEN.getCraftedProduct(), new Object[]{
			"sss", "mcs", "ppp", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'm', ItemStacks.screen, 'c', ItemStacks.pcb});

		//GameRegistry.addRecipe(MachineRegistry.CCTV.getCraftedProduct(), new Object[]{
		//	" g ", "brs", " p ", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'b', Block.thinGlass, 'r', Item.redstone, 'g', Item.ingotGold});

		GameRegistry.addRecipe(MachineRegistry.PURIFIER.getCraftedProduct(), new Object[]{
			"sbs", "prp", "sps", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'r', Item.redstone, 'b', Block.fenceIron});

		//GameRegistry.addRecipe(MachineRegistry.MIRROR.getCraftedProduct(), new Object[]{
		//	"GGG", "III", "pcp", 'p', ItemStacks.basepanel, 'c', ItemStacks.pcb, 'I', Item.ingotIron, 'G', Block.glass});
		GameRegistry.addRecipe(MachineRegistry.MIRROR.getCraftedProduct(), new Object[]{
			"   ", " m ", "pcp", 'p', ItemStacks.basepanel, 'c', ItemStacks.pcb, 'm', ItemStacks.mirror});

		GameRegistry.addRecipe(MachineRegistry.SOLARTOWER.getCraftedProduct(), new Object[]{
			"pPp", "iPi", "pPp", 'p', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'i', ReikaItemHelper.inksac});

		GameRegistry.addRecipe(MachineRegistry.RAILGUN.getCraftedProduct(), new Object[]{
			" H ", " A ", " B ", 'B', ItemStacks.railbase, 'A', ItemStacks.railaim, 'H', ItemStacks.railhead});

		GameRegistry.addRecipe(MachineRegistry.LASERGUN.getCraftedProduct(), new Object[]{
			"CLB", "APG", " b ", 'b', ItemStacks.railbase, 'C', ItemStacks.bulb, 'L', ItemStacks.lens, 'P', ItemStacks.power, 'B', ItemStacks.barrel, 'A', ItemStacks.railaim, 'G', ItemStacks.gearunit});

		GameRegistry.addRecipe(MachineRegistry.ITEMCANNON.getCraftedProduct(), new Object[]{
			"s c", "pcp", "pCr", 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.gearunit, 'r', Block.chest, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.BLOCKCANNON.getCraftedProduct(), new Object[]{
			"s c", "pcp", "pCr", 'C', ItemStacks.compressor,  'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Block.chest, 'p', ItemStacks.basepanel});

		GameRegistry.addRecipe(MachineRegistry.FRICTION.getCraftedProduct(), new Object[]{
			"S  ", "Sss", "SPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem});

		GameRegistry.addRecipe(MachineRegistry.LANDMINE.getCraftedProduct(), new Object[]{
			" P ", "RGR", "SIS", 'P', Block.pressurePlateStone, 'S', ItemStacks.steelingot, 'I', ItemStacks.igniter, 'R', Item.redstone, 'G', Item.ingotGold});

		GameRegistry.addRecipe(MachineRegistry.BUCKETFILLER.getCraftedProduct(), new Object[]{
			"SPS", "PCP", "SPS", 'P', ItemStacks.pipe, 'S', ItemStacks.steelingot, 'C', Block.chest});

		GameRegistry.addRecipe(MachineRegistry.SPYCAM.getCraftedProduct(), new Object[]{
			"SCS", "PRP", "SGS", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Block.thinGlass, 'R', Item.redstone});
	}

	private static void addCraftItems() {
		GameRegistry.addRecipe(ItemStacks.impeller, new Object[]{
				" S ", "SGS", " S ", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.compressor, new Object[]{
				"SSS", "SGS", "SSS", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.turbine, new Object[]{
				"SSS", "SGS", "SSS", 'S', ItemStacks.compressor, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.diffuser, new Object[]{
				" SS", "S  ", " SS", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.combustor, new Object[]{
				"SSS", "SRS", "SGS", 'S', ItemStacks.steelingot, 'G', ItemStacks.igniter, 'R', Item.redstone});
		GameRegistry.addRecipe(ItemStacks.radiator, new Object[]{
				"GGG", "PPP", "SSS", 'I', Item.ingotGold, 'S', ItemStacks.steelingot, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});
		GameRegistry.addRecipe(ItemStacks.condenser, new Object[]{
				"SPS", "PSP", "SPS", 'S', ItemStacks.steelingot, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});
		GameRegistry.addRecipe(ItemStacks.goldcoil, new Object[]{
				"GGG", "GSG", "GGG", 'S', ItemStacks.steelingot, 'G', Item.ingotGold});
		GameRegistry.addRecipe(ItemStacks.cylinder, new Object[]{
				"SSS", "S S", "SSS", 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.shaftcore, new Object[]{
				"  s", " S ", "s  ", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem});
		GameRegistry.addRecipe(ItemStacks.igniter, new Object[]{
				"G G", "SRS", "SSS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', Item.ingotGold});
		GameRegistry.addRecipe(ItemStacks.waterplate, new Object[]{
				"PPP", "PPP", "PPP", 'P', ItemStacks.basepanel});
		GameRegistry.addRecipe(ItemStacks.prop, new Object[]{
				" S ", " I ", " P ", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem, 'I', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.hub, new Object[]{
				"  B", " C ", "G  ", 'G', ItemStacks.steelgear, 'B', ItemStacks.bearing, 'C', ItemStacks.shaftcore});
		GameRegistry.addRecipe(ItemStacks.mirror, new Object[]{
				"   ", "GGG", "III", 'G', Block.glass, 'I', Item.ingotIron});
		GameRegistry.addRecipe(ItemStacks.railhead, new Object[]{
				"LLL", "LGL", "LLL", 'G', ItemStacks.power, 'L', ItemStacks.lim});
		GameRegistry.addRecipe(ItemStacks.railbase, new Object[]{
				"   ", " S ", "PGP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit, 'S', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.railaim, new Object[]{
				"sds", "CRC", "sgs", 'R', ItemStacks.radar, 'C', ItemStacks.pcb, 's', ItemStacks.steelingot, 'd', Item.diamond, 'g', ItemStacks.generator});

		GameRegistry.addRecipe(ItemStacks.bedingot, new Object[]{
				" B ", "BSB", " B ", 'S', ItemStacks.steelingot, 'B', ItemStacks.bedrockdust});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.basepanel, 3), new Object[]{
			"SSS", 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.mount, new Object[]{
				"S S","SBS", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.drill, new Object[]{
				"SSS", "SSS", " S ", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.presshead, new Object[]{
				"SOD", "ODB", "DBB", 'S', ItemStacks.steelingot, 'D', Item.diamond, 'O', Block.obsidian, 'B', ItemStacks.bedrockdust});
		GameRegistry.addRecipe(ItemStacks.screen, new Object[]{
				"SGS", "SCS", 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Block.thinGlass});
		GameRegistry.addRecipe(ItemStacks.mixer, new Object[]{
				" S ", "SIS", " S ", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller});
		GameRegistry.addRecipe(ItemStacks.saw, new Object[]{
				"S S", " C ", "S S", 'S', ItemStacks.steelingot, 'C', ItemStacks.compressor});
		GameRegistry.addRecipe(ItemStacks.pcb, new Object[]{
				"PGP", "RER", "GPG", 'P', ItemStacks.basepanel, 'G', Item.ingotGold, 'R', Item.redstone, 'E', Item.enderPearl});
		GameRegistry.addRecipe(ItemStacks.sonar, new Object[]{
				" S ", "SNS", "RCR", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'N', Block.music, 'C', ItemStacks.pcb});
		GameRegistry.addRecipe(ItemStacks.radar, new Object[]{
				"SSS", " G ", "RMR", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', Item.ingotGold, 'M', new ItemStack(RotaryCraft.engineitems.itemID, 1, 0)});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.belt, 2), new Object[]{
			"LLL", "LSL", "LLL", 'L', Item.leather, 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.bearing, new Object[]{
				"LLL", "LSL", "LLL", 'L', ItemStacks.bearingitem, 'S', ItemStacks.steelingot});
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bearingitem, 4), ItemStacks.steelingot);

		GameRegistry.addRecipe(ItemStacks.brake, new Object[]{
				" g ", "SBS", " G ", 'g', ItemStacks.gearunit, 'G', ItemStacks.steelgear, 'S', ItemStacks.shaftitem, 'B', ItemStacks.bearing});
		GameRegistry.addRecipe(ItemStacks.tenscoil, new Object[]{
				"WWW", "WSW", "WWW", 'W', ItemRegistry.SPRING.getStackOf(), 'S', ItemStacks.shaftitem});

		GameRegistry.addRecipe(ItemStacks.lens, new Object[]{
				" D ", "DGD", " D ", 'D', Item.diamond, 'G', Block.glass});
		GameRegistry.addRecipe(ItemStacks.power, new Object[]{
				"RER", "GGG", "SSS", 'R', Item.redstone, 'G', Item.ingotGold, 'E', Item.eyeOfEnder, 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.barrel, new Object[]{
				"OOO", "ggG", "OOO", 'O', Block.obsidian, 'G', Block.glass, 'g', Block.glowStone});
		GameRegistry.addRecipe(ItemStacks.bulb, new Object[]{
				"GGG", "BDB", "BRB", 'D', Item.netherStar, 'G', Block.glowStone, 'R', Item.redstone, 'B', Item.blazeRod});


		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelgear, 3), new Object[]{
			" B ", "BBB", " B ", 'B', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.gearunit, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.gearunit4, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit});
		GameRegistry.addRecipe(ItemStacks.gearunit8, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit4});
		GameRegistry.addRecipe(ItemStacks.gearunit16, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit8});

		GameRegistry.addRecipe(ItemStacks.wood2x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.woodgear});
		GameRegistry.addRecipe(ItemStacks.wood4x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.wood2x});
		GameRegistry.addRecipe(ItemStacks.wood8x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.wood4x});
		GameRegistry.addRecipe(ItemStacks.wood16x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.wood8x});

		GameRegistry.addRecipe(ItemStacks.stone2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stonegear});
		GameRegistry.addRecipe(ItemStacks.stone4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone2x});
		GameRegistry.addRecipe(ItemStacks.stone8x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone4x});
		GameRegistry.addRecipe(ItemStacks.stone16x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone8x});

		GameRegistry.addRecipe(ItemStacks.diamond2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamondgear});
		GameRegistry.addRecipe(ItemStacks.diamond4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond2x});
		GameRegistry.addRecipe(ItemStacks.diamond8x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond4x});
		GameRegistry.addRecipe(ItemStacks.diamond16x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond8x});

		GameRegistry.addRecipe(ItemStacks.bedrock2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrockgear});
		GameRegistry.addRecipe(ItemStacks.bedrock4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock2x});
		GameRegistry.addRecipe(ItemStacks.bedrock8x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock4x});
		GameRegistry.addRecipe(ItemStacks.bedrock16x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock8x});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.stonerod, 2), new Object[]{
			"B  ", " B ", "  B", 'B', Block.stone});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.shaftitem, 3), new Object[]{
			"B  ", " B ", "  B", 'B', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.diamondshaft, 8), new Object[]{
			"B  ", " B ", "  B", 'B', Item.diamond});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockshaft, 4), new Object[]{
			" D ", "DSD", " D ", 'D', ItemStacks.bedrockdust, 'S', ItemStacks.shaftitem});

		GameRegistry.addRecipe(ItemStacks.wormgear, new Object[]{
				"S  ", " G ", "  S", 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(new ItemStack(ItemStacks.stonegear.itemID, 2, ItemStacks.stonegear.getItemDamage()), new Object[]{
			" W ", "WWW", " W ", 'W', Block.stone});
		GameRegistry.addRecipe(new ItemStack(ItemStacks.diamondgear.itemID, 8, ItemStacks.diamondgear.getItemDamage()), new Object[]{
			" W ", "WWW", " W ", 'W', Item.diamond});
		GameRegistry.addRecipe(new ItemStack(ItemStacks.bedrockgear.itemID, 8, ItemStacks.bedrockgear.getItemDamage()), new Object[]{
			"bWb", "WWW", "bWb", 'b', ItemStacks.bedrockdust, 'W', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.flywheelcore2, new Object[]{
				"WWW", "WGW", "WWW", 'W', Block.stone, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore3, new Object[]{
				"WWW", "WGW", "WWW", 'W', Item.ingotIron, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore4, new Object[]{
				"WWW", "WGW", "WWW", 'W', Item.ingotGold, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(ItemStacks.lim, new Object[]{
				"   ", "WRW", "NNN", 'W', ItemStacks.goldcoil, 'N', ItemStacks.steelingot, 'R', Item.redstone});
	}

	private static void addToolItems() {
		GameRegistry.addRecipe(ItemRegistry.SPRING.getCraftedProduct(4), new Object[]{
			" S ", "S S", " S ", 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemRegistry.TARGET.getCraftedProduct(1), new Object[]{
			" E ", "SRS", "SLS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'E', Item.enderPearl, 'L', ReikaItemHelper.lapisDye});

		GameRegistry.addRecipe(ItemRegistry.HANDBOOK.getCraftedProduct(1), new Object[]{
			"RSR", "PPP", "PPP", 'R', Item.redstone, 'S', Item.ingotIron, 'P', Item.paper});

		ItemStack pick = ItemRegistry.BEDPICK.getCraftedProduct(1);
		pick.addEnchantment(Enchantment.silkTouch, 1);
		GameRegistry.addRecipe(ItemRegistry.BEDAXE.getCraftedProduct(1), new Object[]{
			"BB ", "BS ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot});
		GameRegistry.addRecipe(pick, new Object[]{
				"BBB", " S ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.BEDSHOVEL.getCraftedProduct(1), new Object[]{
			" B ", " S ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot});

		GameRegistry.addShapelessRecipe(ItemRegistry.NVH.getCraftedProduct(1), Item.helmetDiamond, ItemRegistry.NVG.getStackOf());

		GameRegistry.addRecipe(ItemRegistry.ULTRASOUND.getCraftedProduct(1), new Object[]{
			" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar});
		GameRegistry.addRecipe(ItemRegistry.MOTION.getCraftedProduct(1), new Object[]{
			" nr", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar, 'r', ItemStacks.radar});
		GameRegistry.addRecipe(ItemRegistry.VACUUM.getCraftedProduct(1), new Object[]{
			" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.impeller, 'n', ItemStacks.diffuser});
		GameRegistry.addRecipe(ItemRegistry.STUNGUN.getCraftedProduct(1), new Object[]{
			" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.sonar, 'n', ItemStacks.diffuser});
		GameRegistry.addRecipe(ItemRegistry.GRAVELGUN.getCraftedProduct(1), new Object[]{
			" d ", "gcg", "sss", 's', ItemStacks.steelingot, 'c', Block.chest, 'd', Block.dispenser, 'g', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemRegistry.FIREBALL.getCraftedProduct(1), new Object[]{
			"b b", "scs", "srs", 's', ItemStacks.steelingot, 'c', ItemStacks.combustor, 'r', Item.redstone, 'b', Item.blazeRod});
		GameRegistry.addRecipe(ItemRegistry.HANDCRAFT.getCraftedProduct(1), new Object[]{
			" g ", "scs", " g ", 's', ItemStacks.steelingot, 'g', Item.ingotGold, 'c', Block.workbench});
		GameRegistry.addRecipe(ItemRegistry.NVG.getCraftedProduct(1), new Object[]{
			"   ", "scs", "ese", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'e', Item.eyeOfEnder});

		GameRegistry.addRecipe(ItemRegistry.IOGOGGLES.getCraftedProduct(1), new Object[]{
			"   ", "scs", "ese", 's', ItemStacks.steelingot, 'c', Item.enderPearl, 'e', Item.redstone});

	}

	private static void addMisc() {
		if (ConfigRegistry.CRAFTABLEBEDROCK.getState())
			GameRegistry.addRecipe(new ItemStack(Block.bedrock.blockID, 1, 0), new Object[]{
				"DDD", "DDD", "DDD", 'D', ItemStacks.bedrockdust});

		GameRegistry.addRecipe(ItemRegistry.CANOLA.getCraftedMetadataProduct(1, 1), new Object[]{
			"DDD", "DDD", "DDD", 'D', ItemRegistry.CANOLA.getStackOf()});

		GameRegistry.addShapelessRecipe(ItemRegistry.CANOLA.getCraftedProduct(9),
				ItemRegistry.CANOLA.getStackOfMetadata(1));

		GameRegistry.addRecipe(new ItemStack(Block.planks.blockID, 1, 0), new Object[]{
			"WW", "WW", 'W', ItemStacks.sawdust});
		GameRegistry.addRecipe(new ItemStack(Item.paper.itemID, 8, 0), new Object[]{
			" W ", "SSS", "RRR", 'R', Block.stone, 'S', ItemStacks.sawdust, 'W', Item.bucketWater});

		GameRegistry.addRecipe(new ItemStack(RotaryCraft.blastglass, 16, 0), new Object[]{
			"OOO", "OOO", 'O', RotaryCraft.obsidianglass});

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 15), ItemStacks.steelgear);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 48), ItemStacks.gearunit);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 114), ItemStacks.gearunit4);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 246), ItemStacks.gearunit8);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 510), ItemStacks.gearunit16);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.shaftitem);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.basepanel);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 45), ItemStacks.mount);

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), Block.rail, Block.rail, Block.rail, Block.rail, Block.rail, Block.rail, Block.rail, Block.rail);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), Block.fenceIron, Block.fenceIron, Block.fenceIron, Block.fenceIron, Block.fenceIron, Block.fenceIron, Block.fenceIron, Block.fenceIron);

		GameRegistry.addRecipe(ItemStacks.steelblock, new Object[]{
				"BBB", "BBB", "BBB", 'B', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.anthrablock, new Object[]{
				"BBB", "BBB", "BBB", 'B', ItemStacks.anthracite});

		GameRegistry.addRecipe(ItemStacks.lonsblock, new Object[]{
				"BBB", "BBB", "BBB", 'B', ItemStacks.lonsda});

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 9), ItemStacks.steelblock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.anthracite, 9), ItemStacks.anthrablock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.lonsda, 9), ItemStacks.lonsblock);

		GameRegistry.addShapelessRecipe(ItemStacks.salt, Item.bucketWater);
		GameRegistry.addShapelessRecipe(ItemStacks.silveriodide, ItemStacks.salt, ItemStacks.silveringot);

		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedProduct(3), new Object[]{
			"ss ", "s  ", "   ", 's', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 4), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(3), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 5), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(4), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 6), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(5), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 7), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(6), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 8), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(7), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 9), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(8), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 10), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(9), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 11), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(10), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 12), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(11), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 13), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(12), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 14), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(13), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 15), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(14), 'p', ItemStacks.bedingot});

		addSlideRecipes();
	}

	private static void addSlideRecipes() {
		GameRegistry.addRecipe(ItemRegistry.SLIDE.getCraftedProduct(0), new Object[]{ //Wood shaft unit
			"PPP", "PGP", "PPP", 'G', Block.thinGlass, 'P', Item.paper});

		Random r = new Random();
		HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		boolean[] hasMapping = new boolean[16];
		for (int i = 0; i < 16; i++) {
			int randVal = r.nextInt(16);
			while (hasMapping[randVal]) {
				randVal = r.nextInt(16);
			}
			colors.put(i, randVal);
			hasMapping[randVal] = true;
			//ReikaJavaLibrary.pConsole("Color "+i+" registered to value "+randVal);
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				int color = colors.get(i);
				while (color+j >= 24) {
					color -= 24;
				}
				if (color+j < 0)
					throw new RuntimeException("Color mapping < 0 at color "+color+" and dye color "+i+" for slide "+j);
				GameRegistry.addShapelessRecipe(ItemRegistry.SLIDE.getCraftedMetadataProduct(1, color+j), new ItemStack(Item.dyePowder.itemID, 1, i), ItemRegistry.SLIDE.getStackOfMetadata(j));
				//ReikaJavaLibrary.pConsole("Registering recipe with slide "+j+" and color "+i+" to result slide "+(color+j));
			}
		}
	}

	private static void addItemBlocks() {
		GameRegistry.addRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(1), new Object[]{ //Stone shaft unit
			"   ", "sSs", "sss", 's', ReikaItemHelper.stoneSlab, 'S', ItemStacks.stonerod});
		GameRegistry.addRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(2), new Object[]{ //Steel shaft unit
			"S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem});
		GameRegistry.addRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(3), new Object[]{ //Diamond shaft unit
			"S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.diamondshaft});
		GameRegistry.addRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(4), new Object[]{ //Bedrock shaft unit
			"S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.bedrockshaft});

		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.DC.ordinal()), new Object[]{
			"SSS", "SRs", "PRP", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.WIND.ordinal()), new Object[]{
			"SSS", "SHS", "SSS", 'S', ItemStacks.prop, 'H', ItemStacks.hub});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.STEAM.ordinal()), new Object[]{
			"SSS", "CIs", "PGP", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', Item.ingotGold, 'C', ItemStacks.condenser});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.GAS.ordinal()), new Object[]{
			"CgC", "SGs", "PIP", 'g', Item.ingotGold, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.gearunit, 'C', ItemStacks.cylinder});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.AC.ordinal()), new Object[]{
			"SSS", "SGs", "PRP", 'S', Item.ingotGold, 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.goldcoil});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.SPORT.ordinal()), new Object[]{
			"CrC", "SGs", "PIP", 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'r', ItemStacks.radiator, 'G', ItemStacks.gearunit});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.HYDRO.ordinal()), new Object[]{
			"PPP", "PGP", "PPP", 'P', ItemStacks.waterplate, 'G', ItemStacks.shaftcore});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.MICRO.ordinal()), new Object[]{
			"CSS", "cTs", "PPP", 'S', ItemStacks.steelingot, 'C', ItemStacks.compressor, 'c', ItemStacks.combustor, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem});
		GameRegistry.addRecipe(MachineRegistry.ENGINE.getCraftedMetadataProduct(EnumEngineType.JET.ordinal()), new Object[]{
			"DCS", "ScS", "PTs", 'S', ItemStacks.steelingot, 'D', ItemStacks.diffuser, 'C', ItemStacks.compressor, 'c', ItemStacks.combustor, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.pipe, 16), new Object[]{ //Pipe
			"S S", "S S", "S S", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.fuelline, 16), new Object[]{ //Fuel Line
			"O O", "O O", "O O", 'O', Block.obsidian});

		ItemStack gear;

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(1));
		GameRegistry.addRecipe(gear, new Object[]{
				"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(6));
		GameRegistry.addRecipe(gear, new Object[]{
				"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(11));
		GameRegistry.addRecipe(gear, new Object[]{
				"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(16));
		GameRegistry.addRecipe(gear, new Object[]{
				"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone16x});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(2));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(7));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit4});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(12));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit8});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(17));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit16});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(3));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(8));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(13));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(18));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond16x});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(4));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(9));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(14));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(19));
		GameRegistry.addRecipe(gear, new Object[]{
				"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock16x});
	}

	private static void addCharging() {
		GameRegistry.registerCraftingHandler(new ItemChargingRecipeHandler());
		//GameRegistry.addShapelessRecipe(ItemRegistry.MOTION.getStackOf(), RotaryCraft.wind, RotaryCraft.motiontracker);
		//CraftingManager.getInstance().addShapelessRecipe(ItemRegistry..getStackOf()motiontracker), RotaryCraft.wind, RotaryCraft.motiontracker);
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.MOTION.getStackOf()));
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.FIREBALL.getStackOf()));
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.VACUUM.getStackOf()));
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.STUNGUN.getStackOf()));
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.ULTRASOUND.getStackOf()));
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.GRAVELGUN.getStackOf()));
		CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry.NVG.getStackOf()));
		//CraftingManager.getInstance().getRecipeList().add(new ChargingRecipe(ItemRegistry..getStackOf()nvh));
	}

	private static void addFurnace() {
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.powders.itemID, 2, ItemRegistry.ETHANOL.getStackOf(), 0.5F);

		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 24, new ItemStack(Item.coal.itemID, 1, 0), 0.1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 25, new ItemStack(Item.ingotIron.itemID, 1, 0), 0.7F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 26, new ItemStack(Item.ingotGold.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 27, new ItemStack(Item.redstone.itemID, 4, 0), 0.5F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 28, new ItemStack(Item.dyePowder.itemID, 4, 6), 0.6F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 29, new ItemStack(Item.diamond.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 30, new ItemStack(Item.emerald.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 31, new ItemStack(Item.netherQuartz.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 32, new ItemStack(ItemStacks.silveringot.itemID, 1, ItemStacks.silveringot.getItemDamage()), 1F);
	}

	private static ItemStack addDamageNBT(ItemStack is) {
		is.setTagCompound(new NBTTagCompound());
		is.stackTagCompound.setInteger("damage", 0);
		return is;
	}
}
