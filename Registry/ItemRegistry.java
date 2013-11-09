/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.DragonAPI.Interfaces.RegistrationList;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Items.ItemCanolaSeed;
import Reika.RotaryCraft.Items.ItemCoil;
import Reika.RotaryCraft.Items.ItemEthanolMinecart;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Items.ItemHandBook;
import Reika.RotaryCraft.Items.ItemRailGunAmmo;
import Reika.RotaryCraft.Items.Tools.ItemBedrockArmor;
import Reika.RotaryCraft.Items.Tools.ItemBedrockAxe;
import Reika.RotaryCraft.Items.Tools.ItemBedrockPickaxe;
import Reika.RotaryCraft.Items.Tools.ItemBedrockShovel;
import Reika.RotaryCraft.Items.Tools.ItemCannonKey;
import Reika.RotaryCraft.Items.Tools.ItemDebug;
import Reika.RotaryCraft.Items.Tools.ItemFireballLauncher;
import Reika.RotaryCraft.Items.Tools.ItemGravelGun;
import Reika.RotaryCraft.Items.Tools.ItemHandheldCrafting;
import Reika.RotaryCraft.Items.Tools.ItemIOGoggles;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Items.Tools.ItemMeter;
import Reika.RotaryCraft.Items.Tools.ItemMotionTracker;
import Reika.RotaryCraft.Items.Tools.ItemNightVisionGoggles;
import Reika.RotaryCraft.Items.Tools.ItemNightVisionHelmet;
import Reika.RotaryCraft.Items.Tools.ItemScrewdriver;
import Reika.RotaryCraft.Items.Tools.ItemSteelArmor;
import Reika.RotaryCraft.Items.Tools.ItemSteelAxe;
import Reika.RotaryCraft.Items.Tools.ItemSteelPick;
import Reika.RotaryCraft.Items.Tools.ItemSteelShovel;
import Reika.RotaryCraft.Items.Tools.ItemStunGun;
import Reika.RotaryCraft.Items.Tools.ItemTarget;
import Reika.RotaryCraft.Items.Tools.ItemTileSelector;
import Reika.RotaryCraft.Items.Tools.ItemUltrasound;
import Reika.RotaryCraft.Items.Tools.ItemVacuum;
import Reika.RotaryCraft.Items.Tools.ItemWorldEdit;
import cpw.mods.fml.common.registry.GameRegistry;

public enum ItemRegistry implements RegistrationList, IDRegistry {

	SCREWDRIVER(0, false, 		"item.screwdriver", 		ItemScrewdriver.class),
	METER(16, false, 			"item.meter", 				ItemMeter.class),
	DEBUG(112, false, 			"item.debug", 				ItemDebug.class),
	WORLDEDIT(114, 115, true, 	"item.worldedit", 			ItemWorldEdit.class),
	HANDBOOK(208, false, 		"item.handbook", 			ItemHandBook.class),
	YEAST(32, false, 			"item.yeast", 				ItemBasic.class),
	ETHANOL(64, false, 			"item.ethanol", 			ItemBasic.class),
	CANOLA(80, true, 			"item.canola", 				ItemCanolaSeed.class),
	SPRING(96, true, 			"#item.spring", 			ItemCoil.class),
	ULTRASOUND(128, true, 		"item.ultrasound", 			ItemUltrasound.class),
	MOTION(144, true, 			"item.motion", 				ItemMotionTracker.class),
	VACUUM(160, true, 			"item.vacuum", 				ItemVacuum.class),
	STUNGUN(192, true, 			"item.stungun", 			ItemStunGun.class),
	GRAVELGUN(176, true, 		"item.gravelgun", 			ItemGravelGun.class),
	FIREBALL(224, 232, true, 	"item.fireball", 			ItemFireballLauncher.class),
	BEDPICK(101, false, 		"item.bedpick", 			ItemBedrockPickaxe.class),
	BEDAXE(100, false, 			"item.bedaxe", 				ItemBedrockAxe.class),
	BEDSHOVEL(102, false, 		"item.bedshovel", 			ItemBedrockShovel.class),
	NVG(97, true, 				"item.nvg", 				ItemNightVisionGoggles.class),
	NVH(48, true, 				"item.nvh", 				ItemNightVisionHelmet.class),
	HANDCRAFT(33, false, 		"item.handcraft", 			ItemHandheldCrafting.class),
	RAILGUN(113, true, 			"#item.railgun", 			ItemRailGunAmmo.class),
	BUCKET(104, 106, true, 		"#item.bucket", 			ItemFuelLubeBucket.class),
	TARGET(98, false, 			"item.target", 				ItemTarget.class),
	IOGOGGLES(1, true, 			"item.iogoggles", 			ItemIOGoggles.class),
	SLIDE(2, true, 				"#item.slide", 				ItemBasic.class),
	KEY(4, false,				"item.key",					ItemCannonKey.class),
	SHELL(5, false,				"item.shell",				ItemBasic.class),
	MINECART(6, false,			"item.ethacart",			ItemEthanolMinecart.class),
	BEDHELM(7, false,			"item.bedhelm",				ItemBedrockArmor.class),
	BEDCHEST(9, false,			"item.bedchest",			ItemBedrockArmor.class),
	BEDLEGS(10, false,			"item.bedlegs",				ItemBedrockArmor.class),
	BEDBOOTS(8, false,			"item.bedboots",			ItemBedrockArmor.class),
	TILESELECTOR(11, false,		"item.tileselector",		ItemTileSelector.class),
	BEDPACK(12, false,			"item.jetchest",			ItemJetPack.class),
	STEELPICK(13, true,			"item.steelpick",			ItemSteelPick.class),
	STEELAXE(14, true,			"item.steelaxe",			ItemSteelAxe.class),
	STEELSHOVEL(15, true,		"item.steelshovel",			ItemSteelShovel.class),
	STEELHELMET(17, false,		"item.steelhelmet",			ItemSteelArmor.class),
	STEELCHEST(18, false,		"item.steelchest",			ItemSteelArmor.class),
	STEELLEGS(19, false,		"item.steellegs",			ItemSteelArmor.class),
	STEELBOOTS(20, false,		"item.steelboots",			ItemSteelArmor.class),
	STRONGCOIL(99, true,		"#item.strongcoil",			ItemCoil.class),
	JETPACK(28, false,			"item.ethanoljetpack",		ItemJetPack.class);

	private int index;
	private boolean hasSubtypes;
	private String name;
	private Class itemClass;
	private int texturesheet;
	private ModList condition;

	private int maxindex;

	private ItemRegistry(int tex, boolean sub, String n, Class <?extends Item> iCl) {
		this(tex, sub, n, iCl, null);
	}

	private ItemRegistry(int tex, boolean sub, String n, Class <?extends Item> iCl, ModList api) {
		texturesheet = 1;
		if (tex < 0) {
			tex = -tex;
			texturesheet = 0;
		}
		if (tex > 255) {
			texturesheet = tex/256;
			tex -= texturesheet*256;
		}
		index = tex;
		hasSubtypes = sub;
		name = n;
		itemClass = iCl;
		condition = api;
	}

	private ItemRegistry(int lotex, int hitex, boolean sub, String n, Class <?extends Item> iCl) {
		if (lotex > hitex)
			throw new RegistrationException(RotaryCraft.instance, "Invalid item sprite registration for "+n+"! Backwards texture bounds?");
		texturesheet = 1;
		if (lotex < 0) {
			lotex = -lotex;
			hitex = -hitex;
			texturesheet = 0;
		}
		if (lotex > 255) {
			texturesheet = lotex/256;
			lotex -= texturesheet*256;
			hitex -= texturesheet*256;
		}
		index = lotex;
		maxindex = lotex;
		hasSubtypes = sub;
		name = n;
		itemClass = iCl;
	}

	public static final ItemRegistry[] itemList = ItemRegistry.values();


	public Class[] getConstructorParamTypes() {
		if (this.isArmor()) {
			if (this.isBedrockArmor() || this.isSteelArmor())
				return new Class[]{int.class, int.class, int.class, int.class}; // ID, Armor render, Sprite index, armor type
			if (this.isJetpack())
				return new Class[]{int.class, EnumArmorMaterial.class, int.class, int.class}; // ID, Armor render, Sprite index
			return new Class[]{int.class, int.class, int.class}; // ID, Armor render, Sprite index
		}

		return new Class[]{int.class, int.class}; // ID, Sprite index
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

	public Object[] getConstructorParams() {
		if (this.isArmor()) {
			if (this.isBedrockArmor() || this.isSteelArmor())
				return new Object[]{RotaryCraft.config.getItemID(this.ordinal()), this.getTextureIndex(), this.getArmorRender(), this.getArmorType()};
			else if (this.isJetpack())
				return new Object[]{RotaryCraft.config.getItemID(this.ordinal()), this.getArmorMaterial(), this.getTextureIndex(), this.getArmorRender()};
			else
				return new Object[]{RotaryCraft.config.getItemID(this.ordinal()), this.getTextureIndex(), this.getArmorRender()};
		}
		else
			return new Object[]{RotaryCraft.config.getItemID(this.ordinal()), this.getTextureIndex()};
	}

	private EnumArmorMaterial getArmorMaterial() {
		if (this == BEDPACK)
			return RotaryCraft.BEDROCK;
		if (this == JETPACK)
			return RotaryCraft.JETPACK;
		return null;
	}

	private boolean isJetpack() {
		if (this == JETPACK || this == BEDPACK)
			return true;
		return false;
	}

	public int getArmorType() {
		switch(this) {
		case BEDBOOTS:
		case STEELBOOTS:
			return 3;
		case BEDCHEST:
		case STEELCHEST:
		case JETPACK:
		case BEDPACK:
			return 1;
		case BEDHELM:
		case STEELHELMET:
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
		return isRegistered(is.itemID);
	}

	public static boolean isRegistered(int id) {
		for (int i = 0; i < itemList.length; i++) {
			if (itemList[i].getShiftedID() == id)
				return true;
		}
		return false;
	}

	public static ItemRegistry getEntryByID(int id) {
		for (int i = 0; i < itemList.length; i++) {
			if (itemList[i].getShiftedID() == id)
				return itemList[i];
		}
		//throw new RegistrationException(RotaryCraft.instance, "Item ID "+id+" was called to the item registry but does not exist there!");
		return null;
	}

	public static ItemRegistry getEntry(ItemStack is) {
		if (is == null)
			return null;
		return getEntryByID(is.itemID);
	}

	public String getName(int dmg) {
		if (this.hasMultiValuedName())
			return this.getMultiValuedName(dmg);
		return name;
	}

	public String getBasicName() {
		String sg = name;
		if (name.startsWith("#"))
			sg = name.substring(1);
		return StatCollector.translateToLocal(sg);
	}

	public String getMultiValuedName(int dmg) {
		if (!this.hasMultiValuedName())
			throw new RuntimeException("Item "+name+" was called for a multi-name, yet does not have one!");
		if (this == SPRING)
			return this.getBasicName()+" ("+String.format("%d", dmg)+" kJ)";
		if (this == STRONGCOIL)
			return this.getBasicName()+" ("+String.format("%d", dmg)+" kJ)";
		if (this == BUCKET)
			return RotaryNames.getBucketName(dmg);
		if (this == RAILGUN)
			return this.getBasicName()+" ("+String.format("%d", (int)ReikaMathLibrary.intpow(2, dmg))+" kg)";
		if (this == SLIDE)
			return this.getBasicName()+" "+String.format("%d", dmg);
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

	public String getUnlocalizedName() {
		return ReikaStringParser.stripSpaces(name).toLowerCase();
	}

	public int getID() {
		return RotaryCraft.config.getItemID(this.ordinal());
	}

	public int getShiftedID() {
		return RotaryCraft.config.getItemID(this.ordinal())+256;
	}

	public Item getItemInstance() {
		return RotaryCraft.basicItems[this.ordinal()];
	}

	public boolean hasMultiValuedName() {
		return name.startsWith("#");
	}

	public boolean isTool() {
		if (this.isBedrockTool())
			return true;
		return ItemRotaryTool.class.isAssignableFrom(itemClass);
	}

	public boolean isCharged() {
		return ItemChargedTool.class.isAssignableFrom(itemClass) || this == NVG;
	}

	public boolean isBedrockTool() {
		return this == BEDPICK || this == BEDAXE || this == BEDSHOVEL;
	}

	public boolean isCreativeOnly() {
		if (this == DEBUG)
			return true;
		if (this == WORLDEDIT)
			return true;
		return false;
	}

	public int getTextureSheet() {
		return texturesheet;
	}

	public int getNumberMetadatas() {
		if (!hasSubtypes)
			return 1;
		switch(this) {
		case SPRING:
		case STRONGCOIL:
			return 65536;
		case SLIDE:
			return 24;
		case RAILGUN:
			return 16;
		case BUCKET:
			return 3;
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
			return true;
		default:
			return false;
		}
	}

	public ItemStack getCraftedProduct(int amt) {
		if (this.hasPrerequisite() && !this.getPrerequisite().isLoaded())
			return null;
		return new ItemStack(this.getShiftedID(), amt, 0);
	}

	public ItemStack getCraftedMetadataProduct(int amt, int meta) {
		return new ItemStack(this.getShiftedID(), amt, meta);
	}

	public ItemStack getStackOf() {
		return this.getCraftedProduct(1);
	}

	public ItemStack getStackOfMetadata(int meta) {
		return this.getCraftedMetadataProduct(1, meta);
	}

	public boolean overridesRightClick() {
		switch(this) {
		case DEBUG:
		case METER:
		case SCREWDRIVER:
		case KEY:
		case TILESELECTOR:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Class getObjectClass() {
		return itemClass;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		return null;
	}

	@Override
	public boolean hasItemBlock() {
		return false;
	}

	@Override
	public String getConfigName() {
		return this.getBasicName();
	}

	@Override
	public int getDefaultID() {
		return 30500+this.ordinal();
	}

	@Override
	public boolean isBlock() {
		return false;
	}

	@Override
	public boolean isItem() {
		return true;
	}

	@Override
	public String getCategory() {
		if (this.isTool())
			return "Tool Item IDs";
		return "Item IDs";
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
		if (!this.isDummiedOut())
			GameRegistry.addRecipe(this.getStackOf(), params);
	}

	public void addSizedRecipe(int num, Object... params) {
		if (!this.isDummiedOut())
			GameRegistry.addRecipe(this.getCraftedProduct(num), params);
	}

	public void addMetaRecipe(int meta, Object... params) {
		if (!this.isDummiedOut())
			GameRegistry.addRecipe(this.getStackOfMetadata(meta), params);
	}

	public void addSizedMetaRecipe(int meta, int num, Object... params) {
		if (!this.isDummiedOut())
			GameRegistry.addRecipe(this.getCraftedMetadataProduct(num, meta), params);
	}

	public void addEnchantedRecipe(Enchantment e, int lvl, Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getStackOf();
			is.addEnchantment(e, lvl);
			GameRegistry.addRecipe(is, params);
		}
	}

	public void addShapelessEnchantedRecipe(Enchantment e, int lvl, Object... params) {
		if (!this.isDummiedOut()) {
			ItemStack is = this.getStackOf();
			is.addEnchantment(e, lvl);
			GameRegistry.addShapelessRecipe(is, params);
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
			is.addEnchantment(((ItemBedrockArmor)is.getItem()).getDefaultEnchantment(), 4);
			return is;
		case BEDPACK:
			is.addEnchantment(((ItemBedrockArmor)BEDCHEST.getItemInstance()).getDefaultEnchantment(), 4);
		case BEDPICK:
			is.addEnchantment(Enchantment.silkTouch, 1);
			return is;
		default:
			return is;
		}
	}

	public void addShapelessRecipe(Object... params) {
		if (!this.isDummiedOut())
			GameRegistry.addShapelessRecipe(this.getStackOf(), params);
	}

	public void addRecipe(IRecipe ir) {
		if (!this.isDummiedOut())
			GameRegistry.addRecipe(ir);
	}

	public void addWorktableRecipe(Object... params) {

	}

	public boolean isAvailableInCreativeInventory() {
		if (this.isDummiedOut())
			return false;
		return true;
	}

	@Override
	public boolean overwritingItem() {
		return false;
	}
}
