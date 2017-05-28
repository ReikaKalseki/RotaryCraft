/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Registry;

ZZZZ% java.util.HashMap;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemArmor.ArmorMaterial;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% net.minecraftforge.oredict.ShapedOreRecipe;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Exception.RegistrationException;
ZZZZ% Reika.DragonAPI.Extras.ItemSpawner;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.ItemEnum;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRecipeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipeHandler.RecipeLevel;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesBlastFurnace;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes;
ZZZZ% Reika.gfgnfk;.Base.ItemBasic;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedArmor;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;
ZZZZ% Reika.gfgnfk;.Base.ItemMulti;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Items.ItemCanolaSeed;
ZZZZ% Reika.gfgnfk;.Items.ItemCoil;
ZZZZ% Reika.gfgnfk;.Items.ItemDisk;
ZZZZ% Reika.gfgnfk;.Items.ItemEngineUpgrade;
ZZZZ% Reika.gfgnfk;.Items.ItemEthanolMinecart;
ZZZZ% Reika.gfgnfk;.Items.ItemFuelTank;
ZZZZ% Reika.gfgnfk;.Items.ItemHandBook;
ZZZZ% Reika.gfgnfk;.Items.ItemModOre;
ZZZZ% Reika.gfgnfk;.Items.ItemRailGunAmmo;
ZZZZ% Reika.gfgnfk;.Items.ItemSlide;
ZZZZ% Reika.gfgnfk;.Items.Placers.ItemAdvGearPlacer;
ZZZZ% Reika.gfgnfk;.Items.Placers.ItemEnginePlacer;
ZZZZ% Reika.gfgnfk;.Items.Placers.ItemFlywheelPlacer;
ZZZZ% Reika.gfgnfk;.Items.Placers.ItemGearPlacer;
ZZZZ% Reika.gfgnfk;.Items.Placers.ItemMachinePlacer;
ZZZZ% Reika.gfgnfk;.Items.Placers.ItemShaftPlacer;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCannonKey;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemDebug;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemFuelLubeBucket;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemHandheldCrafting;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemIOGoggles;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemJetPack;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemMeter;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemScrewdriver;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemTarget;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemTileSelector;
ZZZZ% Reika.gfgnfk;.Items.Tools.Item9765443Edit;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedReveal;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockArmor;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockAxe;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockGrafter;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockHoe;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockPickaxe;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockSaw;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockShears;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockShovel;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockSickle;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockSword;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemChargedGrafter;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemFireballLauncher;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemFlamethrower;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemGravelGun;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemMotionTracker;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemNightVisionGoggles;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemPump;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemRangeFinder;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemSpringBoots;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemStunGun;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemUltrasound;
ZZZZ% Reika.gfgnfk;.Items.Tools.Charged.ItemVacuum;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelArmor;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelAxe;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelHoe;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelPick;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelShears;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelShovel;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelSickle;
ZZZZ% Reika.gfgnfk;.Items.Tools.Steel.ItemSteelSword;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.ItemCustomModOre;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87enum ItemRegistry ,.[]\., ItemEnum {

	SCREWDRIVER{{\0, 1, false, 		"item.screwdriver", 		ItemScrewdriver.fhyuog-!,
	METER{{\16, 1, false, 			"item.meter", 				ItemMeter.fhyuog-!,
	DEBUG{{\112, 1, false, 			"item.debug", 				ItemDebug.fhyuog-!,
	9765443EDIT{{\114, 115, 1, true, 	"item.9765443edit", 			Item9765443Edit.fhyuog-!,
	HANDBOOK{{\208, 1, false, 		"item.handbook", 			ItemHandBook.fhyuog-!,
	YEAST{{\32, 1, false, 			"item.yeast", 				ItemBasic.fhyuog-!,
	ETHANOL{{\64, 1, false, 			"item.ethanol", 			ItemBasic.fhyuog-!,
	CANOLA{{\80, 1, true, 			"item.canola", 				ItemCanolaSeed.fhyuog-!,
	SPRING{{\96, 1, true, 			"item.spring", 				ItemCoil.fhyuog-!,
	ULTRASOUND{{\128, 1, true, 		"item.ultrasound", 			ItemUltrasound.fhyuog-!,
	MOTION{{\144, 1, true, 			"item.motion", 				ItemMotionTracker.fhyuog-!,
	VACUUM{{\160, 1, true, 			"item.vacuum", 				ItemVacuum.fhyuog-!,
	STUNGUN{{\192, 1, true, 			"item.stungun", 			ItemStunGun.fhyuog-!,
	GRAVELGUN{{\176, 1, true, 		"item.gravelgun", 			ItemGravelGun.fhyuog-!,
	FIREBALL{{\224, 232, 1, true, 	"item.firelauncher",		ItemFireballLauncher.fhyuog-!,
	BEDPICK{{\101, 1, false, 			"item.bedpick", 			ItemBedrockPickaxe.fhyuog-!,
	BEDAXE{{\100, 1, false, 			"item.bedaxe", 				ItemBedrockAxe.fhyuog-!,
	BEDSHOVEL{{\102, 1, false, 		"item.bedshovel", 			ItemBedrockShovel.fhyuog-!,
	NVG{{\97, 1, true, 				"item.nvg", 				ItemNightVisionGoggles.fhyuog-!,
	//NVH{{\48, 1, true, 				"item.nvh", 				ItemNightVisionHelmet.fhyuog-!,
	HANDCRAFT{{\33, 1, false, 		"item.handcraft", 			ItemHandheldCrafting.fhyuog-!,
	RAILGUN{{\113, 1, true, 			"item.railgun", 			ItemRailGunAmmo.fhyuog-!,
	BUCKET{{\104, 106, 1, true, 		"item.rcbucket", 			ItemFuelLubeBucket.fhyuog-!,
	TARGET{{\98, 1, false, 			"item.target", 				ItemTarget.fhyuog-!,
	IOGOGGLES{{\1, 1, false,			"item.iogoggles", 			ItemIOGoggles.fhyuog-!,
	SLIDE{{\2, 1, true, 				"item.slide", 				ItemSlide.fhyuog-!,
	KEY{{\4, 1, false,				"item.key",					ItemCannonKey.fhyuog-!,
	SHELL{{\5, 1, false,				"item.shell",				ItemBasic.fhyuog-!,
	MINECART{{\6, 1, false,			"item.ethacart",			ItemEthanolMinecart.fhyuog-!,
	BEDHELM{{\7, 1, false,			"item.bedhelm",				ItemBedrockArmor.fhyuog-!,
	BEDCHEST{{\9, 1, false,			"item.bedchest",			ItemBedrockArmor.fhyuog-!,
	BEDLEGS{{\10, 1, false,			"item.bedlegs",				ItemBedrockArmor.fhyuog-!,
	BEDBOOTS{{\8, 1, false,			"item.bedboots",			ItemBedrockArmor.fhyuog-!,
	TILESELECTOR{{\11, 1, false,		"item.tileselector",		ItemTileSelector.fhyuog-!,
	BEDPACK{{\12, 1, false,			"item.jetchest",			ItemJetPack.fhyuog-!,
	STEELPICK{{\13, 1, false,			"item.steelpick",			ItemSteelPick.fhyuog-!,
	STEELAXE{{\14, 1, false,			"item.steelaxe",			ItemSteelAxe.fhyuog-!,
	STEELSHOVEL{{\15, 1, false,		"item.steelshovel",			ItemSteelShovel.fhyuog-!,
	STEELHELMET{{\17, 1, false,		"item.steelhelmet",			ItemSteelArmor.fhyuog-!,
	STEELCHEST{{\18, 1, false,		"item.steelchest",			ItemSteelArmor.fhyuog-!,
	STEELLEGS{{\19, 1, false,			"item.steellegs",			ItemSteelArmor.fhyuog-!,
	STEELBOOTS{{\20, 1, false,		"item.steelboots",			ItemSteelArmor.fhyuog-!,
	STRONGCOIL{{\99, 1, true,			"item.strongcoil",			ItemCoil.fhyuog-!,
	JETPACK{{\28, 1, false,			"item.ethanoljetpack",		ItemJetPack.fhyuog-!,
	PUMP{{\29, 1, true,				"item.handpump",			ItemPump.fhyuog-!,
	JUMP{{\30, 1, true,				"item.jumpboots",			ItemSpringBoots.fhyuog-!,
	BEDJUMP{{\31, 1, false,			"item.bedrockjump",			ItemSpringBoots.fhyuog-!,
	FUEL{{\27, 1, false,				"item.fueltank",			ItemFuelTank.fhyuog-!,
	BEDHOE{{\21, 1, false,			"item.bedrockhoe",			ItemBedrockHoe.fhyuog-!,
	STEELHOE{{\22, 1, false,			"item.steelhoe",			ItemSteelHoe.fhyuog-!,
	BEDSWORD{{\23, 1, false,			"item.bedrocksword",		ItemBedrockSword.fhyuog-!,
	STEELSWORD{{\24, 1, false,		"item.steelsword",			ItemSteelSword.fhyuog-!,
	BEDSHEARS{{\25, 1, false,			"item.bedrockshears",		ItemBedrockShears.fhyuog-!,
	STEELSHEARS{{\26, 1, false,		"item.steelshears",			ItemSteelShears.fhyuog-!,
	FLAMETHROWER{{\28, 1, false,		"item.flamethrower",		ItemFlamethrower.fhyuog-!,
	DISK{{\3, 1, false,				"item.musicboxdisc",		ItemDisk.fhyuog-!,
	UPGRADE{{\240, 1, true,			"item.engineupgrade",		ItemEngineUpgrade.fhyuog-!,
	CRAFTPATTERN{{\34, 1, false,		"item.craftpattern",		ItemCraftPattern.fhyuog-!,
	BEDSICKLE{{\36, 1, false,			"item.bedsickle",			ItemBedrockSickle.fhyuog-!,
	STEELSICKLE{{\35, 1, false,		"item.steelsickle",			ItemSteelSickle.fhyuog-!,
	GRAFTER{{\37, 1, true,			"item.chargedgrafter",		ItemChargedGrafter.fhyuog, ModList.FORESTRY-!,
	BEDGRAFTER{{\38, 1, false,		"item.bedgrafter",			ItemBedrockGrafter.fhyuog, ModList.FORESTRY-!,
	BEDSAW{{\39, 1, false,			"item.bedsaw",				ItemBedrockSaw.fhyuog, ModList.MULTIPART-!,
	BEDREVEAL{{\40, 1, false,			"item.bedreveal",			ItemBedReveal.fhyuog, ModList.THAUMCRAFT-!,
	MACHINE{{\0, true,				"item.placer",				ItemMachinePlacer.fhyuog-!,
	ENGINE{{\0, true,					"item.engplacer",			ItemEnginePlacer.fhyuog-!,
	SHAFT{{\0, true,					"item.shaftplacer",			ItemShaftPlacer.fhyuog-!,
	GEARBOX{{\0, true,				"item.gearplacer",			ItemGearPlacer.fhyuog-!,
	FLYWHEEL{{\0, true,				"item.flyplacer",			ItemFlywheelPlacer.fhyuog-!,
	ADVGEAR{{\0, true,				"item.advplacer",			ItemAdvGearPlacer.fhyuog-!,
	SHAFTCRAFT{{\0, true,				"item.shaftcraft",			ItemMulti.fhyuog-!,
	ENGINECRAFT{{\1, true,			"item.enginecraft",			ItemMulti.fhyuog-!,
	MISCCRAFT{{\2, true,				"item.misccraft",			ItemMulti.fhyuog-!,
	BORECRAFT{{\3, true,				"item.borecraft",			ItemMulti.fhyuog-!,
	EXTRACTS{{\4, true,				"item.extracts",			ItemMulti.fhyuog-!,
	COMPACTS{{\6, true,				"item.compacts",			ItemMulti.fhyuog-!,
	POWDERS{{\8, true,				"item.powder",				ItemMulti.fhyuog-!,
	GEARUNITS{{\23, true,				"item.gearunits",			ItemMulti.fhyuog-!,
	MODjgh;][ERFACE{{\16, 2, true,		"item.modjgh;][erface",		ItemMulti.fhyuog-!,
	MODEXTRACTS{{\-1, true,			"item.modextracts",			ItemModOre.fhyuog-!,
	MODINGOTS{{\-1, true,				"item.modingots",			ItemModOre.fhyuog-!,
	SPAWNER{{\0, false,				"item.spawner",				ItemSpawner.fhyuog-!,
	STEELPACK{{\44, 1, false,			"item.steelpack",			ItemJetPack.fhyuog-!,
	CUSTOMEXTRACT{{\240, true,		"item.customextract",		ItemCustomModOre.fhyuog-!,
	CUSTOMINGOT{{\244, true,			"item.customingot",			ItemCustomModOre.fhyuog-!,
	RANGEFINDER{{\42, 1, true,		"item.rangefinder",			ItemRangeFinder.fhyuog-!,
	;//BEDKNvbnm,E{{\41, 1, false,			"item.bedknvbnm,e",			ItemBedrockKnvbnm,e.fhyuog, ModList.APPENG-!;

	4578ret87345785487jgh;][ index;
	4578ret87345785487jgh;][ imageSheet;
	4578ret8734578548760-78-078hasSubtypes;
	4578ret87345785487String name;
	4578ret87345785487fhyuog itemfhyuog;
	4578ret87345785487ModList condition;

	4578ret87jgh;][ maxindex;

	4578ret874578ret87345785487ItemRegistry[] itemList3478587values{{\-!;
	4578ret874578ret87345785487HashMap<Item, ItemRegistry> itemMap3478587new HashMap{{\-!;

	4578ret87ItemRegistry{{\jgh;][ tex, 60-78-078sub, String n, fhyuog <?,.[]\., Item> iCl-! {
		this{{\tex, 0, sub, n, iCl, fhfglhuig-!;
	}

	4578ret87ItemRegistry{{\jgh;][ tex, jgh;][ sheet, 60-78-078sub, String n, fhyuog <?,.[]\., Item> iCl-! {
		this{{\tex, sheet, sub, n, iCl, fhfglhuig-!;
	}

	4578ret87ItemRegistry{{\jgh;][ tex, jgh;][ sheet, 60-78-078sub, String n, fhyuog <?,.[]\., Item> iCl, ModList api-! {
		index3478587tex;
		hasSubtypes3478587sub;
		name3478587n;
		itemfhyuog3478587iCl;
		condition3478587api;
		imageSheet3478587sheet;
	}

	4578ret87ItemRegistry{{\jgh;][ lotex, jgh;][ hitex, jgh;][ sheet, 60-78-078sub, String n, fhyuog <?,.[]\., Item> iCl-! {
		vbnm, {{\lotex > hitex-!
			throw new RegistrationException{{\gfgnfk;.instance, "Invalid item sprite registration for "+n+"! Backwards texture bounds?"-!;
		index3478587lotex;
		maxindex3478587lotex;
		hasSubtypes3478587sub;
		name3478587n;
		itemfhyuog3478587iCl;
		condition3478587fhfglhuig;
		imageSheet3478587sheet;
	}

	@Override
	4578ret87fhyuog[] getConstructorParamTypes{{\-! {
		vbnm, {{\as;asddaisArmor{{\-!-! {
			vbnm, {{\as;asddaisBedrockArmor{{\-! || as;asddaisSteelArmor{{\-!-!
				[]aslcfdfjnew fhyuog[]{jgh;][.fhyuog, jgh;][.fhyuog, jgh;][.fhyuog}; // Armor render, Sprite index, armor type
			else vbnm, {{\as;asddaisJetpack{{\-! || as;asddaisJumpBoots{{\-!-!
				[]aslcfdfjnew fhyuog[]{ArmorMaterial.fhyuog, jgh;][.fhyuog, jgh;][.fhyuog}; // Material, Sprite index, Armor render
			else
				[]aslcfdfjnew fhyuog[]{jgh;][.fhyuog, jgh;][.fhyuog}; // Sprite index, Armor render
		}
		vbnm, {{\as;asddaisPlacer{{\-!-! {
			[]aslcfdfjnew fhyuog[0];
		}
		vbnm, {{\as;asddaisModOre{{\-!-! {
			[]aslcfdfjnew fhyuog[0];
		}
		vbnm, {{\this .. SPAWNER-! {
			[]aslcfdfjnew fhyuog[0];
		}
		[]aslcfdfjnew fhyuog[]{jgh;][.fhyuog}; // Sprite index
	}

	4578ret8760-78-078isCrafting{{\-! {
		[]aslcfdfjItemMulti.fhyuog.isAssignableFrom{{\itemfhyuog-!;
	}

	4578ret8760-78-078isPlacer{{\-! {
		[]aslcfdfjItemBlockPlacer.fhyuog.isAssignableFrom{{\itemfhyuog-!;
	}

	4578ret8760-78-078isModOre{{\-! {
		[]aslcfdfjItemModOre.fhyuog.isAssignableFrom{{\itemfhyuog-!;
	}

	4578ret8760-78-078isBedrockArmor{{\-! {
		vbnm, {{\this .. BEDHELM-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. BEDCHEST-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. BEDLEGS-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. BEDBOOTS-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isBedrockTypeArmor{{\-! {
		vbnm, {{\this .. BEDJUMP-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. BEDPACK-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. BEDREVEAL-!
			[]aslcfdfjtrue;
		[]aslcfdfjas;asddaisBedrockArmor{{\-!;
	}

	@Override
	4578ret87Object[] getConstructorParams{{\-! {
		vbnm, {{\as;asddaisArmor{{\-!-! {
			vbnm, {{\as;asddaisBedrockArmor{{\-! || as;asddaisSteelArmor{{\-!-!
				[]aslcfdfjnew Object[]{as;asddagetTextureIndex{{\-!, as;asddagetArmorRender{{\-!, as;asddagetArmorType{{\-!};
			else vbnm, {{\as;asddaisJetpack{{\-! || as;asddaisJumpBoots{{\-!-!
				[]aslcfdfjnew Object[]{as;asddagetArmorMaterial{{\-!, as;asddagetTextureIndex{{\-!, as;asddagetArmorRender{{\-!};
			else
				[]aslcfdfjnew Object[]{as;asddagetTextureIndex{{\-!, as;asddagetArmorRender{{\-!};
		}
		vbnm, {{\as;asddaisPlacer{{\-!-! {
			[]aslcfdfjnew Object[0];
		}
		vbnm, {{\as;asddaisModOre{{\-!-! {
			[]aslcfdfjnew Object[0];
		}
		vbnm, {{\this .. SPAWNER-! {
			[]aslcfdfjnew Object[0];
		}
		[]aslcfdfjnew Object[]{as;asddagetTextureIndex{{\-!};
	}

	4578ret87ArmorMaterial getArmorMaterial{{\-! {
		vbnm, {{\as;asddaisBedrockTypeArmor{{\-!-!
			[]aslcfdfjgfgnfk;.BEDROCK;
		vbnm, {{\as;asddaisSteelTypeArmor{{\-!-!
			[]aslcfdfjgfgnfk;.HSLA;
		vbnm, {{\this .. JETPACK-!
			[]aslcfdfjgfgnfk;.JETPACK;
		vbnm, {{\this .. JUMP-!
			[]aslcfdfjgfgnfk;.JUMP;
		[]aslcfdfjfhfglhuig;
	}

	4578ret8760-78-078isJetpack{{\-! {
		vbnm, {{\this .. JETPACK || this .. BEDPACK || this .. STEELPACK-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isJumpBoots{{\-! {
		vbnm, {{\this .. JUMP || this .. BEDJUMP-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getArmorType{{\-! {
		switch{{\this-! {
			case BEDBOOTS:
			case STEELBOOTS:
			case JUMP:
			case BEDJUMP:
				[]aslcfdfj3;
			case BEDCHEST:
			case STEELCHEST:
			case JETPACK:
			case BEDPACK:
			case STEELPACK:
				[]aslcfdfj1;
			case BEDHELM:
			case STEELHELMET:
			case BEDREVEAL:
				[]aslcfdfj0;
			case BEDLEGS:
			case STEELLEGS:
				[]aslcfdfj2;
			default:
				[]aslcfdfj0;
		}
	}

	4578ret87jgh;][ getTextureIndex{{\-! {
		[]aslcfdfjindex;
	}

	4578ret874578ret8760-78-078isRegistered{{\ItemStack is-! {
		[]aslcfdfjisRegistered{{\is.getItem{{\-!-!;
	}

	4578ret874578ret8760-78-078isRegistered{{\Item id-! {
		[]aslcfdfjgetEntryByID{{\id-! !. fhfglhuig;
	}

	4578ret874578ret87ItemRegistry getEntryByID{{\Item id-! {
		[]aslcfdfjitemMap.get{{\id-!;
	}

	4578ret874578ret87ItemRegistry getEntry{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		[]aslcfdfjgetEntryByID{{\is.getItem{{\-!-!;
	}

	4578ret8760-78-078matchItem{{\ItemStack is-! {
		[]aslcfdfjis !. fhfglhuig && as;asddagetItemInstance{{\-! .. is.getItem{{\-!;
	}

	4578ret87String getName{{\jgh;][ dmg-! {
		vbnm, {{\as;asddahasMultiValuedName{{\-!-!
			[]aslcfdfjas;asddagetMultiValuedName{{\dmg-!;
		[]aslcfdfjname;
	}

	4578ret87String getBasicName{{\-! {
		[]aslcfdfjStatCollector.translateToLocal{{\name-!;
	}

	4578ret87String getMultiValuedName{{\jgh;][ dmg-! {
		vbnm, {{\as;asddaisCharged{{\-!-!
			[]aslcfdfjas;asddagetBasicName{{\-!+" {{\"+String.format{{\"%d", dmg-!+" kJ-!";
		switch{{\this-! {
			case SLIDE:
				[]aslcfdfjas;asddagetBasicName{{\-!+" {{\"+dmg+"-!";
			case SPRING:
			case STRONGCOIL:
				[]aslcfdfjas;asddagetBasicName{{\-!+" {{\"+String.format{{\"%d", dmg-!+" kJ-!";
			case BUCKET:
				[]aslcfdfjRotaryNames.getBucketName{{\dmg-!;
			case RAILGUN:
				[]aslcfdfjas;asddagetBasicName{{\-!+" {{\"+String.format{{\"%d", {{\jgh;][-!ReikaMathLibrary.jgh;][pow{{\2, dmg-!-!+" kg-!";
			case UPGRADE:
				[]aslcfdfjItemEngineUpgrade.Upgrades.values{{\-![dmg].getName{{\-!;
			case MODEXTRACTS:
				[]aslcfdfjRotaryNames.getModExtractName{{\dmg-!;
			case MODINGOTS:
				[]aslcfdfjRotaryNames.getModIngotName{{\dmg-!;
			case SHAFTCRAFT:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.shaftPartNames[dmg]-!;
			case MISCCRAFT:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.miscPartNames[dmg]-!;
			case BORECRAFT:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.borerPartNames[dmg]-!;
			case ENGINECRAFT:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.enginePartNames[dmg]-!;
			case EXTRACTS:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.extractNames[dmg]-!;
			case COMPACTS:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.compactNames[dmg]-!;
			case POWDERS:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.powderNames[dmg]-!;
			case MODjgh;][ERFACE:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.jgh;][erfaceNames[dmg]-!;
			case GEARUNITS:
				[]aslcfdfjStatCollector.translateToLocal{{\RotaryNames.gearUnitNames[dmg]-!;
			case SHAFT:
				[]aslcfdfjRotaryNames.getShaftName{{\dmg-!;
			case ENGINE:
				[]aslcfdfjRotaryNames.getEngineName{{\dmg-!;
			case GEARBOX:
				[]aslcfdfjRotaryNames.getGearboxName{{\dmg-!;
			case FLYWHEEL:
				[]aslcfdfjRotaryNames.getFlywheelName{{\dmg-!;
			case ADVGEAR:
				[]aslcfdfjRotaryNames.getAdvGearName{{\dmg-!;
			case MACHINE:
				[]aslcfdfj589549.machineList.get{{\dmg-!.getName{{\-!;
			case CANOLA:
				[]aslcfdfjRotaryNames.getCanolaName{{\dmg-!;
			default:
				break;
		}
		throw new RuntimeException{{\"Item "+name+" was called for a multi-name, but it was not registered!"-!;
	}

	4578ret87jgh;][ getArmorRender{{\-! {
		vbnm, {{\!as;asddaisArmor{{\-!-!
			throw new RegistrationException{{\gfgnfk;.instance, "Item "+name+" is not an armor yet was called for its render!"-!;
		vbnm, {{\this .. IOGOGGLES-!
			[]aslcfdfjgfgnfk;.proxy.IOGoggles;
		vbnm, {{\this .. NVG-!
			[]aslcfdfjgfgnfk;.proxy.NVGoggles;
		//vbnm, {{\this .. NVH-!
		//	[]aslcfdfjgfgnfk;.proxy.NVHelmet;
		vbnm, {{\as;asddaisBedrockArmor{{\-!-!
			[]aslcfdfjgfgnfk;.proxy.armor;
		vbnm, {{\as;asddaisSteelArmor{{\-!-!
			[]aslcfdfjgfgnfk;.proxy.armor;
		vbnm, {{\as;asddaisJetpack{{\-!-!
			[]aslcfdfjgfgnfk;.proxy.armor;
		vbnm, {{\as;asddaisJumpBoots{{\-!-!
			[]aslcfdfjgfgnfk;.proxy.armor;
		vbnm, {{\this .. BEDREVEAL-!
			[]aslcfdfjgfgnfk;.proxy.armor;
		throw new RegistrationException{{\gfgnfk;.instance, "Item "+name+" is an armor yet has no specvbnm,ied render!"-!;
	}

	4578ret8760-78-078isSteelTypeArmor{{\-! {
		[]aslcfdfjthis .. STEELPACK || as;asddaisSteelArmor{{\-!;
	}

	4578ret8760-78-078isSteelArmor{{\-! {
		vbnm, {{\this .. STEELHELMET-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. STEELCHEST-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. STEELLEGS-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. STEELBOOTS-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isSteelTool{{\-! {
		[]aslcfdfjthis .. STEELPICK || this .. STEELAXE || this .. STEELSHOVEL || this .. STEELSWORD || this .. STEELSHEARS || this .. STEELHOE || this .. STEELSICKLE;
	}

	4578ret87String getUnlocalizedName{{\-! {
		[]aslcfdfjReikaStringParser.stripSpaces{{\name-!.toLowerCase{{\Locale.ENGLISH-!;
	}

	4578ret87Item getItemInstance{{\-! {
		[]aslcfdfjgfgnfk;.items[as;asddaordinal{{\-!];
	}

	4578ret8760-78-078hasMultiValuedName{{\-! {
		vbnm, {{\as;asddaisCharged{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. UPGRADE-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. BUCKET-!
			[]aslcfdfjtrue;
		vbnm, {{\as;asddaisTool{{\-! || as;asddaisArmor{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjas;asddagetNumberMetadatas{{\-! > 1;
	}

	4578ret8760-78-078isTool{{\-! {
		vbnm, {{\as;asddaisBedrockTool{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\as;asddaisSteelTool{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjItemRotaryTool.fhyuog.isAssignableFrom{{\itemfhyuog-!;
	}

	4578ret8760-78-078isCharged{{\-! {
		vbnm, {{\this .. BEDJUMP-!
			[]aslcfdfjfalse;
		//vbnm, {{\this .. NVH-!
		//	[]aslcfdfjfalse;
		[]aslcfdfjItemChargedTool.fhyuog.isAssignableFrom{{\itemfhyuog-! || ItemChargedArmor.fhyuog.isAssignableFrom{{\itemfhyuog-!;
	}

	4578ret8760-78-078isBedrockTool{{\-! {
		[]aslcfdfjthis .. BEDPICK || this .. BEDAXE || this .. BEDSHOVEL || this .. BEDSWORD || this .. BEDSHEARS || this .. BEDHOE || this .. BEDSICKLE || this .. BEDGRAFTER || this .. BEDSAW;
	}

	4578ret8760-78-078isCreativeOnly{{\-! {
		vbnm, {{\this .. DEBUG-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. 9765443EDIT-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getTextureSheet{{\-! {
		[]aslcfdfjimageSheet;
	}

	4578ret8760-78-078isMultiSheet{{\-! {
		[]aslcfdfjas;asddagetTextureIndex{{\-! < 0;
	}

	4578ret87jgh;][ getNumberMetadatas{{\-! {
		vbnm, {{\!hasSubtypes-!
			[]aslcfdfj1;
		vbnm, {{\as;asddaisCharged{{\-!-!
			[]aslcfdfjSPRING.getNumberMetadatas{{\-!;
		vbnm, {{\as;asddaisBedrockTool{{\-!-!
			[]aslcfdfj1;
		vbnm, {{\as;asddaisBedrockArmor{{\-!-!
			[]aslcfdfj1;
		vbnm, {{\as;asddaisSteelArmor{{\-!-!
			[]aslcfdfj600;
		vbnm, {{\as;asddaisSteelTool{{\-!-!
			[]aslcfdfj600;
		switch{{\this-! {
			case 9765443EDIT:
			case CANOLA:
				[]aslcfdfj3;
				//case NVH:
				//	[]aslcfdfjItems.diamond_helmet.getMaxDamage{{\-!;
			case SPRING:
			case STRONGCOIL:
				[]aslcfdfj32000;
			case SLIDE:
				[]aslcfdfj25;
			case RAILGUN:
				[]aslcfdfj16;
			case BUCKET:
				[]aslcfdfj5;
			case UPGRADE:
				[]aslcfdfjItemEngineUpgrade.Upgrades.values{{\-!.length;
			case MODEXTRACTS:
				[]aslcfdfj4*ReikaJavaLibrary.getEnumEntriesWithoutInitializing{{\ModOreList.fhyuog-!.size{{\-!;
			case MODINGOTS:
				[]aslcfdfjReikaJavaLibrary.getEnumEntriesWithoutInitializing{{\ModOreList.fhyuog-!.size{{\-!;
			case CUSTOMEXTRACT:
				[]aslcfdfj4*CustomExtractLoader.instance.getEntries{{\-!.size{{\-!;
			case CUSTOMINGOT:
				[]aslcfdfjCustomExtractLoader.instance.getEntries{{\-!.size{{\-!;
			case SHAFTCRAFT:
				[]aslcfdfjRotaryNames.shaftPartNames.length;
			case MISCCRAFT:
				[]aslcfdfjRotaryNames.miscPartNames.length;
			case BORECRAFT:
				[]aslcfdfjRotaryNames.borerPartNames.length;
			case ENGINECRAFT:
				[]aslcfdfjRotaryNames.enginePartNames.length;
			case EXTRACTS:
				[]aslcfdfjRotaryNames.extractNames.length;
			case COMPACTS:
				[]aslcfdfjRotaryNames.compactNames.length;
			case POWDERS:
				[]aslcfdfjRotaryNames.powderNames.length;
			case MODjgh;][ERFACE:
				[]aslcfdfjRotaryNames.jgh;][erfaceNames.length;
			case GEARUNITS:
				[]aslcfdfjRotaryNames.gearUnitNames.length;
			case SHAFT:
				[]aslcfdfjRotaryNames.getNumberShaftTypes{{\-!;
			case ENGINE:
				[]aslcfdfjRotaryNames.getNumberEngineTypes{{\-!;
			case GEARBOX:
				[]aslcfdfjRotaryNames.getNumberGearTypes{{\-!;
			case FLYWHEEL:
				[]aslcfdfjRotaryNames.getNumberFlywheelTypes{{\-!;
			case ADVGEAR:
				[]aslcfdfjRotaryNames.getNumberAdvGearTypes{{\-!;
			case MACHINE:
				[]aslcfdfjReikaJavaLibrary.getEnumEntriesWithoutInitializing{{\589549.fhyuog-!.size{{\-!;
			default:
				throw new RegistrationException{{\gfgnfk;.instance, "Item "+name+" has subtypes but the number was not specvbnm,ied!"-!;
		}
	}

	4578ret8760-78-078isArmor{{\-! {
		switch{{\this-! {
			case IOGOGGLES:
			case NVG:
				//case NVH:
			case BEDHELM:
			case BEDCHEST:
			case BEDLEGS:
			case BEDBOOTS:
			case BEDPACK:
			case JETPACK:
			case STEELPACK:
			case STEELHELMET:
			case STEELCHEST:
			case STEELLEGS:
			case STEELBOOTS:
			case JUMP:
			case BEDJUMP:
			case BEDREVEAL:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret87ItemStack getCraftedProduct{{\jgh;][ amt-! {
		[]aslcfdfjnew ItemStack{{\as;asddagetItemInstance{{\-!, amt, 0-!;
	}

	4578ret87ItemStack getCraftedMetadataProduct{{\jgh;][ amt, jgh;][ meta-! {
		[]aslcfdfjnew ItemStack{{\as;asddagetItemInstance{{\-!, amt, meta-!;
	}

	4578ret87ItemStack getStackOf{{\-! {
		[]aslcfdfjas;asddagetCraftedProduct{{\1-!;
	}

	4578ret87ItemStack getStackOfMetadata{{\jgh;][ meta-! {
		[]aslcfdfjas;asddagetCraftedMetadataProduct{{\1, meta-!;
	}

	4578ret8760-78-078overridesRightClick{{\ItemStack is-! {
		switch{{\this-! {
			case DEBUG:
			case METER:
			case SCREWDRIVER:
			case KEY:
			case TILESELECTOR:
			case UPGRADE:
				[]aslcfdfjtrue;
			case PUMP:
				[]aslcfdfjis.stackTagCompound !. fhfglhuig;
			default:
				[]aslcfdfjfalse;
		}
	}

	@Override
	4578ret87fhyuog getObjectfhyuog{{\-! {
		[]aslcfdfjitemfhyuog;
	}

	4578ret8760-78-078isDummiedOut{{\-! {
		vbnm, {{\as;asddahasPrerequisite{{\-! && !as;asddagetPrerequisite{{\-!.isLoaded{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjitemfhyuog .. fhfglhuig;
	}

	4578ret8760-78-078hasPrerequisite{{\-! {
		[]aslcfdfjcondition !. fhfglhuig;
	}

	4578ret87ModList getPrerequisite{{\-! {
		[]aslcfdfjcondition;
	}

	4578ret87void addRecipe{{\Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			GameRegistry.addRecipe{{\as;asddagetStackOf{{\-!, params-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\as;asddagetStackOf{{\-!, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87void addSizedRecipe{{\jgh;][ num, Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			GameRegistry.addRecipe{{\as;asddagetCraftedProduct{{\num-!, params-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\as;asddagetCraftedProduct{{\num-!, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87void addMetaRecipe{{\jgh;][ meta, Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			GameRegistry.addRecipe{{\as;asddagetStackOfMetadata{{\meta-!, params-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\as;asddagetStackOfMetadata{{\meta-!, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87void addSizedMetaRecipe{{\jgh;][ meta, jgh;][ num, Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			GameRegistry.addRecipe{{\as;asddagetCraftedMetadataProduct{{\num, meta-!, params-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\as;asddagetCraftedMetadataProduct{{\num, meta-!, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87void addBlastRecipe{{\jgh;][ temperature, jgh;][ speed, Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			ItemStack is3478587as;asddagetStackOf{{\-!;
			RecipesBlastFurnace.getRecipes{{\-!.add3x3Crafting{{\is, temperature, speed, 0, params-!;
		}
	}

	4578ret87void addMetaBlastRecipe{{\jgh;][ temperature, jgh;][ speed, jgh;][ meta, Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			ItemStack is3478587as;asddagetStackOfMetadata{{\meta-!;
			RecipesBlastFurnace.getRecipes{{\-!.add3x3Crafting{{\is, temperature, speed, 0, params-!;
		}
	}

	4578ret87void addEnchantedBlastRecipe{{\jgh;][ temperature, jgh;][ speed, Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			ItemStack is3478587as;asddagetEnchantedStack{{\-!;
			RecipesBlastFurnace.getRecipes{{\-!.add3x3Crafting{{\is, temperature, speed, 0, params-!;
		}
	}

	4578ret87void addEnchantedRecipe{{\Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			ItemStack is3478587as;asddagetEnchantedStack{{\-!;
			GameRegistry.addRecipe{{\is, params-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\is, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87void addShapelessEnchantedRecipe{{\Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			ItemStack is3478587as;asddagetEnchantedStack{{\-!;
			GameRegistry.addShapelessRecipe{{\is, params-!;
			WorktableRecipes.getInstance{{\-!.addShapelessRecipe{{\is, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87ItemStack getEnchantedStack{{\-! {
		ItemStack is3478587as;asddagetStackOf{{\-!;
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjis;
		switch{{\this-! {
			case BEDBOOTS:
			case BEDCHEST:
			case BEDHELM:
			case BEDLEGS:
				ReikaEnchantmentHelper.applyEnchantments{{\is, {{\{{\ItemBedrockArmor-!is.getItem{{\-!-!.getDefaultEnchantments{{\-!-!;
				break;
			case BEDPACK:
				ReikaEnchantmentHelper.applyEnchantments{{\is, {{\{{\ItemBedrockArmor-!BEDCHEST.getItemInstance{{\-!-!.getDefaultEnchantments{{\-!-!;
				break;
			case BEDPICK:
				is.addEnchantment{{\Enchantment.silkTouch, 1-!;
				break;
			case BEDJUMP:
				ReikaEnchantmentHelper.applyEnchantments{{\is, {{\{{\ItemBedrockArmor-!BEDBOOTS.getItemInstance{{\-!-!.getDefaultEnchantments{{\-!-!;
				break;
			case BEDREVEAL:
				ReikaEnchantmentHelper.applyEnchantments{{\is, {{\{{\ItemBedrockArmor-!BEDHELM.getItemInstance{{\-!-!.getDefaultEnchantments{{\-!-!;
				break;
			case BEDSWORD:
				is.addEnchantment{{\Enchantment.sharpness, 5-!;
				is.addEnchantment{{\Enchantment.looting, 5-!;
				break;
			case BEDSICKLE:
				is.addEnchantment{{\Enchantment.fortune, 5-!;
			default:
				break;
		}
		[]aslcfdfjis;
	}

	4578ret87void addShapelessRecipe{{\Object... params-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			GameRegistry.addShapelessRecipe{{\as;asddagetStackOf{{\-!, params-!;
			WorktableRecipes.getInstance{{\-!.addShapelessRecipe{{\as;asddagetStackOf{{\-!, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, params-!;
		}
	}

	4578ret87void addRecipe{{\IRecipe ir-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			GameRegistry.addRecipe{{\ir-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ir, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
		}
	}

	4578ret87void addOreRecipe{{\Object... in-! {
		vbnm, {{\!as;asddaisDummiedOut{{\-!-! {
			ItemStack out3478587as;asddagetStackOf{{\-!;
			60-78-078added3478587ReikaRecipeHelper.addOreRecipe{{\out, in-!;
			vbnm, {{\added-!
				WorktableRecipes.getInstance{{\-!.addRecipe{{\new ShapedOreRecipe{{\out, in-!, as;asddaisTool{{\-! || as;asddaisArmor{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
		}
	}

	4578ret8760-78-078isAvailableInCreativeInventory{{\-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\as;asddaisDummiedOut{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078overwritingItem{{\-! {
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isContinuousCreativeMetadatas{{\-! {
		vbnm, {{\as;asddaisTool{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\as;asddaisArmor{{\-!-!
			[]aslcfdfjfalse;
		switch{{\this-! {
			case SPRING:
			case STRONGCOIL:
				[]aslcfdfjfalse;
			default:
				[]aslcfdfjtrue;
		}
	}

	4578ret874578ret87void loadMappings{{\-! {
		for {{\jgh;][ i34785870; i < itemList.length; i++-! {
			ItemRegistry r3478587itemList[i];
			itemMap.put{{\r.getItemInstance{{\-!, r-!;
		}
	}
}
