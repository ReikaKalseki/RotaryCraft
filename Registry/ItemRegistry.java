/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Extras.ItemSpawner;
import Reika.DragonAPI.Interfaces.ItemEnum;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.ItemMulti;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Items.ItemCanolaSeed;
import Reika.RotaryCraft.Items.ItemCoil;
import Reika.RotaryCraft.Items.ItemDisk;
import Reika.RotaryCraft.Items.ItemEngineUpgrade;
import Reika.RotaryCraft.Items.ItemEthanolMinecart;
import Reika.RotaryCraft.Items.ItemFuelTank;
import Reika.RotaryCraft.Items.ItemHandBook;
import Reika.RotaryCraft.Items.ItemModOre;
import Reika.RotaryCraft.Items.ItemRailGunAmmo;
import Reika.RotaryCraft.Items.ItemSlide;
import Reika.RotaryCraft.Items.Placers.ItemAdvGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemEnginePlacer;
import Reika.RotaryCraft.Items.Placers.ItemFlywheelPlacer;
import Reika.RotaryCraft.Items.Placers.ItemGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemMachinePlacer;
import Reika.RotaryCraft.Items.Placers.ItemShaftPlacer;
import Reika.RotaryCraft.Items.Tools.ItemCannonKey;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Items.Tools.ItemDebug;
import Reika.RotaryCraft.Items.Tools.ItemFuelLubeBucket;
import Reika.RotaryCraft.Items.Tools.ItemHandheldCrafting;
import Reika.RotaryCraft.Items.Tools.ItemIOGoggles;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Items.Tools.ItemMeter;
import Reika.RotaryCraft.Items.Tools.ItemNightVisionHelmet;
import Reika.RotaryCraft.Items.Tools.ItemScrewdriver;
import Reika.RotaryCraft.Items.Tools.ItemTarget;
import Reika.RotaryCraft.Items.Tools.ItemTileSelector;
import Reika.RotaryCraft.Items.Tools.ItemWorldEdit;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedReveal;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockAxe;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockGrafter;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockHoe;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockPickaxe;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockSaw;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockShears;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockShovel;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockSickle;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockSword;
import Reika.RotaryCraft.Items.Tools.Charged.ItemChargedGrafter;
import Reika.RotaryCraft.Items.Tools.Charged.ItemFireballLauncher;
import Reika.RotaryCraft.Items.Tools.Charged.ItemFlamethrower;
import Reika.RotaryCraft.Items.Tools.Charged.ItemGravelGun;
import Reika.RotaryCraft.Items.Tools.Charged.ItemMotionTracker;
import Reika.RotaryCraft.Items.Tools.Charged.ItemNightVisionGoggles;
import Reika.RotaryCraft.Items.Tools.Charged.ItemPump;
import Reika.RotaryCraft.Items.Tools.Charged.ItemSpringBoots;
import Reika.RotaryCraft.Items.Tools.Charged.ItemStunGun;
import Reika.RotaryCraft.Items.Tools.Charged.ItemUltrasound;
import Reika.RotaryCraft.Items.Tools.Charged.ItemVacuum;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelArmor;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelAxe;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelHoe;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelPick;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelShears;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelShovel;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelSickle;
import Reika.RotaryCraft.Items.Tools.Steel.ItemSteelSword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public enum ItemRegistry implements ItemEnum {

	SCREWDRIVER(0, 1, false, 		"item.screwdriver", 		ItemScrewdriver.class),
	METER(16, 1, false, 			"item.meter", 				ItemMeter.class),
	DEBUG(112, 1, false, 			"item.debug", 				ItemDebug.class),
	WORLDEDIT(114, 115, 1, true, 	"item.worldedit", 			ItemWorldEdit.class),
	HANDBOOK(208, 1, false, 		"item.handbook", 			ItemHandBook.class),
	YEAST(32, 1, false, 			"item.yeast", 				ItemBasic.class),
	ETHANOL(64, 1, false, 			"item.ethanol", 			ItemBasic.class),
	CANOLA(80, 1, true, 			"item.canola", 				ItemCanolaSeed.class),
	SPRING(96, 1, true, 			"item.spring", 				ItemCoil.class),
	ULTRASOUND(128, 1, true, 		"item.ultrasound", 			ItemUltrasound.class),
	MOTION(144, 1, true, 			"item.motion", 				ItemMotionTracker.class),
	VACUUM(160, 1, true, 			"item.vacuum", 				ItemVacuum.class),
	STUNGUN(192, 1, true, 			"item.stungun", 			ItemStunGun.class),
	GRAVELGUN(176, 1, true, 		"item.gravelgun", 			ItemGravelGun.class),
	FIREBALL(224, 232, 1, true, 	"item.firelauncher",		ItemFireballLauncher.class),
	BEDPICK(101, 1, false, 			"item.bedpick", 			ItemBedrockPickaxe.class),
	BEDAXE(100, 1, false, 			"item.bedaxe", 				ItemBedrockAxe.class),
	BEDSHOVEL(102, 1, false, 		"item.bedshovel", 			ItemBedrockShovel.class),
	NVG(97, 1, true, 				"item.nvg", 				ItemNightVisionGoggles.class),
	NVH(48, 1, true, 				"item.nvh", 				ItemNightVisionHelmet.class),
	HANDCRAFT(33, 1, false, 		"item.handcraft", 			ItemHandheldCrafting.class),
	RAILGUN(113, 1, true, 			"item.railgun", 			ItemRailGunAmmo.class),
	BUCKET(104, 106, 1, true, 		"item.rcbucket", 			ItemFuelLubeBucket.class),
	TARGET(98, 1, false, 			"item.target", 				ItemTarget.class),
	IOGOGGLES(1, 1, false,			"item.iogoggles", 			ItemIOGoggles.class),
	SLIDE(2, 1, true, 				"item.slide", 				ItemSlide.class),
	KEY(4, 1, false,				"item.key",					ItemCannonKey.class),
	SHELL(5, 1, false,				"item.shell",				ItemBasic.class),
	MINECART(6, 1, false,			"item.ethacart",			ItemEthanolMinecart.class),
	BEDHELM(7, 1, false,			"item.bedhelm",				ItemBedrockArmor.class),
	BEDCHEST(9, 1, false,			"item.bedchest",			ItemBedrockArmor.class),
	BEDLEGS(10, 1, false,			"item.bedlegs",				ItemBedrockArmor.class),
	BEDBOOTS(8, 1, false,			"item.bedboots",			ItemBedrockArmor.class),
	TILESELECTOR(11, 1, false,		"item.tileselector",		ItemTileSelector.class),
	BEDPACK(12, 1, false,			"item.jetchest",			ItemJetPack.class),
	STEELPICK(13, 1, false,			"item.steelpick",			ItemSteelPick.class),
	STEELAXE(14, 1, false,			"item.steelaxe",			ItemSteelAxe.class),
	STEELSHOVEL(15, 1, false,		"item.steelshovel",			ItemSteelShovel.class),
	STEELHELMET(17, 1, false,		"item.steelhelmet",			ItemSteelArmor.class),
	STEELCHEST(18, 1, false,		"item.steelchest",			ItemSteelArmor.class),
	STEELLEGS(19, 1, false,			"item.steellegs",			ItemSteelArmor.class),
	STEELBOOTS(20, 1, false,		"item.steelboots",			ItemSteelArmor.class),
	STRONGCOIL(99, 1, true,			"item.strongcoil",			ItemCoil.class),
	JETPACK(28, 1, false,			"item.ethanoljetpack",		ItemJetPack.class),
	PUMP(29, 1, true,				"item.handpump",			ItemPump.class),
	JUMP(30, 1, true,				"item.jumpboots",			ItemSpringBoots.class),
	BEDJUMP(31, 1, false,			"item.bedrockjump",			ItemSpringBoots.class),
	FUEL(27, 1, false,				"item.fueltank",			ItemFuelTank.class),
	BEDHOE(21, 1, false,			"item.bedrockhoe",			ItemBedrockHoe.class),
	STEELHOE(22, 1, false,			"item.steelhoe",			ItemSteelHoe.class),
	BEDSWORD(23, 1, false,			"item.bedrocksword",		ItemBedrockSword.class),
	STEELSWORD(24, 1, false,		"item.steelsword",			ItemSteelSword.class),
	BEDSHEARS(25, 1, false,			"item.bedrockshears",		ItemBedrockShears.class),
	STEELSHEARS(26, 1, false,		"item.steelshears",			ItemSteelShears.class),
	FLAMETHROWER(28, 1, false,		"item.flamethrower",		ItemFlamethrower.class),
	DISK(3, 1, false,				"item.musicboxdisc",		ItemDisk.class),
	UPGRADE(240, 1, true,			"item.engineupgrade",		ItemEngineUpgrade.class),
	CRAFTPATTERN(34, 1, false,		"item.craftpattern",		ItemCraftPattern.class),
	BEDSICKLE(36, 1, false,			"item.bedsickle",			ItemBedrockSickle.class),
	STEELSICKLE(35, 1, false,		"item.steelsickle",			ItemSteelSickle.class),
	GRAFTER(37, 1, true,			"item.chargedgrafter",		ItemChargedGrafter.class, ModList.FORESTRY),
	BEDGRAFTER(38, 1, false,		"item.bedgrafter",			ItemBedrockGrafter.class, ModList.FORESTRY),
	BEDSAW(39, 1, false,			"item.bedsaw",				ItemBedrockSaw.class, ModList.MULTIPART),
	BEDREVEAL(40, 1, false,			"item.bedreveal",			ItemBedReveal.class, ModList.THAUMCRAFT),
	MACHINE(0, true,				"item.placer",				ItemMachinePlacer.class),
	ENGINE(0, true,					"item.engplacer",			ItemEnginePlacer.class),
	SHAFT(0, true,					"item.shaftplacer",			ItemShaftPlacer.class),
	GEARBOX(0, true,				"item.gearplacer",			ItemGearPlacer.class),
	FLYWHEEL(0, true,				"item.flyplacer",			ItemFlywheelPlacer.class),
	ADVGEAR(0, true,				"item.advplacer",			ItemAdvGearPlacer.class),
	SHAFTCRAFT(0, true,				"item.shaftcraft",			ItemMulti.class),
	ENGINECRAFT(1, true,			"item.enginecraft",			ItemMulti.class),
	MISCCRAFT(2, true,				"item.misccraft",			ItemMulti.class),
	BORECRAFT(3, true,				"item.borecraft",			ItemMulti.class),
	EXTRACTS(4, true,				"item.extracts",			ItemMulti.class),
	COMPACTS(6, true,				"item.compacts",			ItemMulti.class),
	POWDERS(8, true,				"item.powder",				ItemMulti.class),
	GEARUNITS(23, true,				"item.gearunits",			ItemMulti.class),
	MODINTERFACE(14, true,			"item.modinterface",		ItemMulti.class),
	MODEXTRACTS(-1, true,			"item.modextracts",			ItemModOre.class),
	MODINGOTS(-1, true,				"item.modingots",			ItemModOre.class),
	SPAWNER(0, false,				"item.spawner",				ItemSpawner.class);

	private final int index;
	private final int imageSheet;
	private final boolean hasSubtypes;
	private final String name;
	private final Class itemClass;
	private final ModList condition;

	private int maxindex;

	private ItemRegistry(int tex, boolean sub, String n, Class <?extends Item> iCl) {
		this(tex, 0, sub, n, iCl, null);
	}

	private ItemRegistry(int tex, int sheet, boolean sub, String n, Class <?extends Item> iCl) {
		this(tex, sheet, sub, n, iCl, null);
	}

	private ItemRegistry(int tex, int sheet, boolean sub, String n, Class <?extends Item> iCl, ModList api) {
		index = tex;
		hasSubtypes = sub;
		name = n;
		itemClass = iCl;
		condition = api;
		imageSheet = sheet;
	}

	private ItemRegistry(int lotex, int hitex, int sheet, boolean sub, String n, Class <?extends Item> iCl) {
		if (lotex > hitex)
			throw new RegistrationException(RotaryCraft.instance, "Invalid item sprite registration for "+n+"! Backwards texture bounds?");
		index = lotex;
		maxindex = lotex;
		hasSubtypes = sub;
		name = n;
		itemClass = iCl;
		condition = null;
		imageSheet = sheet;
	}

	public static final ItemRegistry[] itemList = values();

	@Override
	public Class[] getConstructorParamTypes() {
		if (this.isArmor()) {
			if (this.isBedrockArmor() || this.isSteelArmor())
				return new Class[]{int.class, int.class, int.class}; // Armor render, Sprite index, armor type
			else if (this.isJetpack() || this.isJumpBoots())
				return new Class[]{ArmorMaterial.class, int.class, int.class}; // Material, Sprite index, Armor render
			else
				return new Class[]{int.class, int.class}; // Sprite index, Armor render
		}
		if (this.isPlacer()) {
			return new Class[0];
		}
		if (this.isModOre()) {
			return new Class[0];
		}
		if (this == SPAWNER) {
			return new Class[0];
		}
		return new Class[]{int.class}; // Sprite index
	}

	public boolean isCrafting() {
		return ItemMulti.class.isAssignableFrom(itemClass);
	}

	public boolean isPlacer() {
		return ItemBlockPlacer.class.isAssignableFrom(itemClass);
	}

	public boolean isModOre() {
		return ItemModOre.class.isAssignableFrom(itemClass);
	}

	public boolean isBedrockArmor() {
		if (this == BEDHELM)
			return true;
		if (this == BEDCHEST)
			return true;
		if (this == BEDLEGS)
			return true;
		if (this == BEDBOOTS)
			return true;
		return false;
	}

	public boolean isBedrockTypeArmor() {
		if (this == BEDJUMP)
			return true;
		if (this == BEDPACK)
			return true;
		if (this == BEDREVEAL)
			return true;
		return this.isBedrockArmor();
	}

	@Override
	public Object[] getConstructorParams() {
		if (this.isArmor()) {
			if (this.isBedrockArmor() || this.isSteelArmor())
				return new Object[]{this.getTextureIndex(), this.getArmorRender(), this.getArmorType()};
			else if (this.isJetpack() || this.isJumpBoots())
				return new Object[]{this.getArmorMaterial(), this.getTextureIndex(), this.getArmorRender()};
			else
				return new Object[]{this.getTextureIndex(), this.getArmorRender()};
		}
		if (this.isPlacer()) {
			return new Object[0];
		}
		if (this.isModOre()) {
			return new Object[0];
		}
		if (this == SPAWNER) {
			return new Object[0];
		}
		return new Object[]{this.getTextureIndex()};
	}

	private ArmorMaterial getArmorMaterial() {
		if (this.isBedrockTypeArmor())
			return RotaryCraft.BEDROCK;
		if (this == JETPACK)
			return RotaryCraft.JETPACK;
		if (this == JUMP)
			return RotaryCraft.JUMP;
		return null;
	}

	public boolean isJetpack() {
		if (this == JETPACK || this == BEDPACK)
			return true;
		return false;
	}

	private boolean isJumpBoots() {
		if (this == JUMP || this == BEDJUMP)
			return true;
		return false;
	}

	public int getArmorType() {
		switch(this) {
		case BEDBOOTS:
		case STEELBOOTS:
		case JUMP:
		case BEDJUMP:
			return 3;
		case BEDCHEST:
		case STEELCHEST:
		case JETPACK:
		case BEDPACK:
			return 1;
		case BEDHELM:
		case STEELHELMET:
		case BEDREVEAL:
			return 0;
		case BEDLEGS:
		case STEELLEGS:
			return 2;
		default:
			return 0;
		}
	}

	public int getTextureIndex() {
		return index;
	}

	public static boolean isRegistered(ItemStack is) {
		return isRegistered(is.getItem());
	}

	public static boolean isRegistered(Item id) {
		for (int i = 0; i < itemList.length; i++) {
			if (itemList[i].getItemInstance() == id)
				return true;
		}
		return false;
	}

	public static ItemRegistry getEntryByID(Item id) {
		for (int i = 0; i < itemList.length; i++) {
			if (itemList[i].getItemInstance() == id)
				return itemList[i];
		}
		//throw new RegistrationException(RotaryCraft.instance, "Item ID "+id+" was called to the item registry but does not exist there!");
		return null;
	}

	public static ItemRegistry getEntry(ItemStack is) {
		if (is == null)
			return null;
		return getEntryByID(is.getItem());
	}

	public boolean matchItem(ItemStack is) {
		return is != null && this.getItemInstance() == is.getItem();
	}

	public String getName(int dmg) {
		if (this.hasMultiValuedName())
			return this.getMultiValuedName(dmg);
		return name;
	}

	public String getBasicName() {
		return StatCollector.translateToLocal(name);
	}

	public String getMultiValuedName(int dmg) {
		if (this.isCharged())
			return this.getBasicName()+" ("+String.format("%d", dmg)+" kJ)";
		switch(this) {
		case SLIDE:
			return this.getBasicName()+" ("+dmg+")";
		case SPRING:
		case STRONGCOIL:
			return this.getBasicName()+" ("+String.format("%d", dmg)+" kJ)";
		case BUCKET:
			return RotaryNames.getBucketName(dmg);
		case RAILGUN:
			return this.getBasicName()+" ("+String.format("%d", (int)ReikaMathLibrary.intpow(2, dmg))+" kg)";
		case UPGRADE:
			return ItemEngineUpgrade.Upgrades.values()[dmg].desc;
		case MODEXTRACTS:
			return RotaryNames.getModExtractName(dmg);
		case MODINGOTS:
			return RotaryNames.getModIngotName(dmg);
		case SHAFTCRAFT:
			return StatCollector.translateToLocal(RotaryNames.shaftPartNames[dmg]);
		case MISCCRAFT:
			return StatCollector.translateToLocal(RotaryNames.miscPartNames[dmg]);
		case BORECRAFT:
			return StatCollector.translateToLocal(RotaryNames.borerPartNames[dmg]);
		case ENGINECRAFT:
			return StatCollector.translateToLocal(RotaryNames.enginePartNames[dmg]);
		case EXTRACTS:
			return StatCollector.translateToLocal(RotaryNames.extractNames[dmg]);
		case COMPACTS:
			return StatCollector.translateToLocal(RotaryNames.compactNames[dmg]);
		case POWDERS:
			return StatCollector.translateToLocal(RotaryNames.powderNames[dmg]);
		case MODINTERFACE:
			return StatCollector.translateToLocal(RotaryNames.interfaceNames[dmg]);
		case GEARUNITS:
			return StatCollector.translateToLocal(RotaryNames.gearUnitNames[dmg]);
		case SHAFT:
			return RotaryNames.getShaftName(dmg);
		case ENGINE:
			return RotaryNames.getEngineName(dmg);
		case GEARBOX:
			return RotaryNames.getGearboxName(dmg);
		case FLYWHEEL:
			return RotaryNames.getFlywheelName(dmg);
		case ADVGEAR:
			return RotaryNames.getAdvGearName(dmg);
		case MACHINE:
			return MachineRegistry.machineList[dmg].getName();
		default:
			break;
		}
		throw new RuntimeException("Item "+name+" was called for a multi-name, but it was not registered!");
	}

	public int getArmorRender() {
		if (!this.isArmor())
			throw new RegistrationException(RotaryCraft.instance, "Item "+name+" is not an armor yet was called for its render!");
		if (this == IOGOGGLES)
			return RotaryCraft.proxy.IOGoggles;
		if (this == NVG)
			return RotaryCraft.proxy.NVGoggles;
		if (this == NVH)
			return RotaryCraft.proxy.NVHelmet;
		if (this.isBedrockArmor())
			return RotaryCraft.proxy.armor;
		if (this.isSteelArmor())
			return RotaryCraft.proxy.armor;
		if (this.isJetpack())
			return RotaryCraft.proxy.armor;
		if (this.isJumpBoots())
			return RotaryCraft.proxy.armor;
		if (this == BEDREVEAL)
			return RotaryCraft.proxy.armor;
		throw new RegistrationException(RotaryCraft.instance, "Item "+name+" is an armor yet has no specified render!");
	}

	private boolean isSteelArmor() {
		if (this == STEELHELMET)
			return true;
		if (this == STEELCHEST)
			return true;
		if (this == STEELLEGS)
			return true;
		if (this == STEELBOOTS)
			return true;
		return false;
	}

	public boolean isSteelTool() {
		return this == STEELPICK || this == STEELAXE || this == STEELSHOVEL || this == STEELSWORD || this == STEELSHEARS || this == STEELHOE;
	}

	public String getUnlocalizedName() {
		return ReikaStringParser.stripSpaces(name).toLowerCase();
	}

	public Item getItemInstance() {
		return RotaryCraft.items[this.ordinal()];
	}

	public boolean hasMultiValuedName() {
		if (this.isCharged())
			return true;
		if (this == UPGRADE)
			return true;
		if (this == BUCKET)
			return true;
		if (this.isTool() || this.isArmor())
			return false;
		if (this == CANOLA)
			return false;
		return this.getNumberMetadatas() > 1;
	}

	public boolean isTool() {
		if (this.isBedrockTool())
			return true;
		if (this.isSteelTool())
			return true;
		return ItemRotaryTool.class.isAssignableFrom(itemClass);
	}

	public boolean isCharged() {
		if (this == BEDJUMP)
			return false;
		if (this == NVH)
			return false;
		return ItemChargedTool.class.isAssignableFrom(itemClass) || ItemChargedArmor.class.isAssignableFrom(itemClass);
	}

	public boolean isBedrockTool() {
		return this == BEDPICK || this == BEDAXE || this == BEDSHOVEL || this == BEDSWORD || this == BEDSHEARS || this == BEDHOE || this == BEDSICKLE;
	}

	public boolean isCreativeOnly() {
		if (this == DEBUG)
			return true;
		if (this == WORLDEDIT)
			return true;
		return false;
	}

	public int getTextureSheet() {
		return imageSheet;
	}

	public boolean isMultiSheet() {
		return this.getTextureIndex() < 0;
	}

	public int getNumberMetadatas() {
		if (!hasSubtypes)
			return 1;
		if (this.isCharged())
			return SPRING.getNumberMetadatas();
		if (this.isBedrockTool())
			return 1;
		if (this.isBedrockArmor())
			return 1;
		if (this.isSteelArmor())
			return 600;
		if (this.isSteelTool())
			return 600;
		switch(this) {
		case WORLDEDIT:
		case CANOLA:
			return 2;
		case NVH:
			return Items.diamond_helmet.getMaxDamage();
		case SPRING:
		case STRONGCOIL:
			return 32000;
		case SLIDE:
			return 25;
		case RAILGUN:
			return 16;
		case BUCKET:
			return 4;
		case UPGRADE:
			return ItemEngineUpgrade.Upgrades.values().length;
		case MODEXTRACTS:
			return 4*ReikaJavaLibrary.getEnumEntriesWithoutInitializing(ModOreList.class).size();
		case MODINGOTS:
			return ReikaJavaLibrary.getEnumEntriesWithoutInitializing(ModOreList.class).size();
		case SHAFTCRAFT:
			return RotaryNames.shaftPartNames.length;
		case MISCCRAFT:
			return RotaryNames.miscPartNames.length;
		case BORECRAFT:
			return RotaryNames.borerPartNames.length;
		case ENGINECRAFT:
			return RotaryNames.enginePartNames.length;
		case EXTRACTS:
			return RotaryNames.extractNames.length;
		case COMPACTS:
			return RotaryNames.compactNames.length;
		case POWDERS:
			return RotaryNames.powderNames.length;
		case MODINTERFACE:
			return RotaryNames.interfaceNames.length;
		case GEARUNITS:
			return RotaryNames.gearUnitNames.length;
		case SHAFT:
			return RotaryNames.getNumberShaftTypes();
		case ENGINE:
			return RotaryNames.getNumberEngineTypes();
		case GEARBOX:
			return RotaryNames.getNumberGearTypes();
		case FLYWHEEL:
			return RotaryNames.getNumberFlywheelTypes();
		case ADVGEAR:
			return RotaryNames.getNumberAdvGearTypes();
		case MACHINE:
			return ReikaJavaLibrary.getEnumEntriesWithoutInitializing(MachineRegistry.class).size();
		default:
			throw new RegistrationException(RotaryCraft.instance, "Item "+name+" has subtypes but the number was not specified!");
		}
	}

	public boolean isArmor() {
		switch(this) {
		case IOGOGGLES:
		case NVG:
		case NVH:
		case BEDHELM:
		case BEDCHEST:
		case BEDLEGS:
		case BEDBOOTS:
		case BEDPACK:
		case JETPACK:
		case STEELHELMET:
		case STEELCHEST:
		case STEELLEGS:
		case STEELBOOTS:
		case JUMP:
		case BEDJUMP:
		case BEDREVEAL:
			return true;
		default:
			return false;
		}
	}

	public ItemStack getCraftedProduct(int amt) {
		return new ItemStack(this.getItemInstance(), amt, 0);
	}

	public ItemStack getCraftedMetadataProduct(int amt, int meta) {
		return new ItemStack(this.getItemInstance(), amt, meta);
	}

	public ItemStack getStackOf() {
		return this.getCraftedProduct(1);
	}

	public ItemStack getStackOfMetadata(int meta) {
		return this.getCraftedMetadataProduct(1, meta);
	}

	public boolean overridesRightClick(ItemStack is) {
		switch(this) {
		case DEBUG:
		case METER:
		case SCREWDRIVER:
		case KEY:
		case TILESELECTOR:
		case UPGRADE:
			return true;
		case PUMP:
			return is.stackTagCompound != null;
		default:
			return false;
		}
	}

	@Override
	public Class getObjectClass() {
		return itemClass;
	}

	public boolean isDummiedOut() {
		if (this.hasPrerequisite() && !this.getPrerequisite().isLoaded())
			return true;
		return itemClass == null;
	}

	private boolean hasPrerequisite() {
		return condition != null;
	}

	private ModList getPrerequisite() {
		return condition;
	}

	public void addRecipe(Object... params) {
		if (!this.isDummiedOut()) {
			GameRegistry.addRecipe(this.getStackOf(), params);
			WorktableRecipes.getInstance().addRecipe(this.getStackOf(), params);
		}
	}

	public void addSizedRecipe(int num, Object... params) {
		if (!this.isDummiedOut()) {
			GameRegistry.addRecipe(this.getCraftedProduct(num), params);
			WorktableRecipes.getInstance().addRecipe(this.getCraftedProduct(num), params);
		}
	}

	public void addMetaRecipe(int meta, Object... params) {
		if (!this.isDummiedOut()) {
			GameRegistry.addRecipe(this.getStackOfMetadata(meta), params);
			WorktableRecipes.getInstance().addRecipe(this.getStackOfMetadata(meta), params);
		}
	}

	public void addSizedMetaRecipe(int meta, int num, Object... params) {
		if (!this.isDummiedOut()) {
			GameRegistry.addRecipe(this.getCraftedMetadataProduct(num, meta), params);
			WorktableRecipes.getInstance().addRecipe(this.getCraftedMetadataProduct(num, meta), params);
		}
	}

	public void addBlastRecipe(int temperature, int speed, Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getStackOf();
			RecipesBlastFurnace.getRecipes().add3x3Crafting(is, temperature, speed, 0, params);
		}
	}

	public void addMetaBlastRecipe(int temperature, int speed, int meta, Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getStackOfMetadata(meta);
			RecipesBlastFurnace.getRecipes().add3x3Crafting(is, temperature, speed, 0, params);
		}
	}

	public void addEnchantedBlastRecipe(int temperature, int speed, Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getEnchantedStack();
			RecipesBlastFurnace.getRecipes().add3x3Crafting(is, temperature, speed, 0, params);
		}
	}

	public void addEnchantedRecipe(Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getEnchantedStack();
			GameRegistry.addRecipe(is, params);
			WorktableRecipes.getInstance().addRecipe(is, params);
		}
	}

	public void addShapelessEnchantedRecipe(Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getEnchantedStack();
			GameRegistry.addShapelessRecipe(is, params);
			WorktableRecipes.getInstance().addShapelessRecipe(is, params);
		}
	}

	public ItemStack getEnchantedStack() {
		ItemStack is = this.getStackOf();
		if (is == null)
			return is;
		switch(this) {
		case BEDBOOTS:
		case BEDCHEST:
		case BEDHELM:
		case BEDLEGS:
			ReikaEnchantmentHelper.applyEnchantments(is, ((ItemBedrockArmor)is.getItem()).getDefaultEnchantments());
			break;
		case BEDPACK:
			ReikaEnchantmentHelper.applyEnchantments(is, ((ItemBedrockArmor)BEDCHEST.getItemInstance()).getDefaultEnchantments());
			break;
		case BEDPICK:
			is.addEnchantment(Enchantment.silkTouch, 1);
			break;
		case BEDJUMP:
			ReikaEnchantmentHelper.applyEnchantments(is, ((ItemBedrockArmor)BEDBOOTS.getItemInstance()).getDefaultEnchantments());
			break;
		case BEDREVEAL:
			ReikaEnchantmentHelper.applyEnchantments(is, ((ItemBedrockArmor)BEDHELM.getItemInstance()).getDefaultEnchantments());
			break;
		case BEDSWORD:
			is.addEnchantment(Enchantment.sharpness, 5);
			is.addEnchantment(Enchantment.looting, 5);
			break;
		case BEDSICKLE:
			is.addEnchantment(Enchantment.fortune, 5);
		default:
			break;
		}
		return is;
	}

	public void addShapelessRecipe(Object... params) {
		if (!this.isDummiedOut()) {
			GameRegistry.addShapelessRecipe(this.getStackOf(), params);
			WorktableRecipes.getInstance().addShapelessRecipe(this.getStackOf(), params);
		}
	}

	public void addRecipe(IRecipe ir) {
		if (!this.isDummiedOut()) {
			GameRegistry.addRecipe(ir);
			WorktableRecipes.addRecipe(ir);
		}
	}

	public void addOreRecipe(Object... in) {
		if (!this.isDummiedOut()) {
			ItemStack out = this.getStackOf();
			boolean added = ReikaRecipeHelper.addOreRecipe(out, in);
			if (added)
				WorktableRecipes.addRecipe(new ShapedOreRecipe(out, in));
		}
	}

	public boolean isAvailableInCreativeInventory() {
		if (RotaryCraft.instance.isLocked())
			return false;
		if (this.isDummiedOut())
			return false;
		return true;
	}

	@Override
	public boolean overwritingItem() {
		return false;
	}

	public boolean isContinuousCreativeMetadatas() {
		if (this.isTool())
			return false;
		if (this.isArmor())
			return false;
		switch(this) {
		case SPRING:
		case STRONGCOIL:
			return false;
		default:
			return true;
		}
	}
}