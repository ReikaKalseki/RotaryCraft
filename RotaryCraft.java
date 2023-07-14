/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import Reika.ChromatiCraft.API.AdjacencyUpgradeAPI.BlacklistReason;
import Reika.ChromatiCraft.API.ChromatiAPI;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.CreativeTabSorter;
import Reika.DragonAPI.Auxiliary.Trackers.CommandableUpdateChecker;
import Reika.DragonAPI.Auxiliary.Trackers.CompatibilityTracker;
import Reika.DragonAPI.Auxiliary.Trackers.ConfigMatcher;
import Reika.DragonAPI.Auxiliary.Trackers.DonatorController;
import Reika.DragonAPI.Auxiliary.Trackers.FurnaceFuelRegistry;
import Reika.DragonAPI.Auxiliary.Trackers.IDCollisionTracker;
import Reika.DragonAPI.Auxiliary.Trackers.IntegrityChecker;
import Reika.DragonAPI.Auxiliary.Trackers.ModLockController;
import Reika.DragonAPI.Auxiliary.Trackers.PackModificationTracker;
import Reika.DragonAPI.Auxiliary.Trackers.PlayerFirstTimeTracker;
import Reika.DragonAPI.Auxiliary.Trackers.PlayerHandler;
import Reika.DragonAPI.Auxiliary.Trackers.SuggestedModsTracker;
import Reika.DragonAPI.Auxiliary.Trackers.VanillaIntegrityTracker;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Base.DragonAPIMod.LoadProfiler.LoadPhase;
import Reika.DragonAPI.Instantiable.EnhancedFluid;
import Reika.DragonAPI.Instantiable.RayTracer;
import Reika.DragonAPI.Instantiable.SimpleCropHandler;
import Reika.DragonAPI.Instantiable.IO.ModLogger;
import Reika.DragonAPI.Libraries.ReikaDispenserHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.ReikaRegistryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.BannedItemReader;
import Reika.DragonAPI.ModInteract.ItemStackRepository;
import Reika.DragonAPI.ModInteract.MinetweakerHooks;
import Reika.DragonAPI.ModInteract.ReikaEEHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.MTInteractionManager;
import Reika.DragonAPI.ModInteract.DeepInteract.RouterHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.SensitiveFluidRegistry;
import Reika.DragonAPI.ModInteract.DeepInteract.SensitiveItemRegistry;
import Reika.DragonAPI.ModInteract.DeepInteract.TimeTorchHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.TinkerMaterialHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.TinkerMaterialHelper.CustomTinkerMaterial;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerBlockHandler.Pulses;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerToolHandler.ToolParts;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.RotaryCraft.Auxiliary.BlockColorMapper;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.FindMachinesCommand;
import Reika.RotaryCraft.Auxiliary.FreezePotion;
import Reika.RotaryCraft.Auxiliary.HandbookNotifications.HandbookConfigVerifier;
import Reika.RotaryCraft.Auxiliary.HandbookTracker;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.JetpackFuelOverlay;
import Reika.RotaryCraft.Auxiliary.LockNotification;
import Reika.RotaryCraft.Auxiliary.MachineDamage;
import Reika.RotaryCraft.Auxiliary.OldTextureLoader;
import Reika.RotaryCraft.Auxiliary.PotionDeafness;
import Reika.RotaryCraft.Auxiliary.RotaryASMHandler;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.RotaryIntegrationManager;
import Reika.RotaryCraft.Auxiliary.TabModOre;
import Reika.RotaryCraft.Auxiliary.TabRotaryCraft;
import Reika.RotaryCraft.Auxiliary.TabRotaryItems;
import Reika.RotaryCraft.Auxiliary.TabRotaryPower;
import Reika.RotaryCraft.Auxiliary.TabRotaryTools;
import Reika.RotaryCraft.Auxiliary.TabSpawner;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Items.Tools.ItemFuelTank;
import Reika.RotaryCraft.ModInterface.AgriCanola;
import Reika.RotaryCraft.ModInterface.CanolaBee;
import Reika.RotaryCraft.ModInterface.ChromaRC;
import Reika.RotaryCraft.ModInterface.OreForcer;
import Reika.RotaryCraft.ModInterface.RotaryAspectManager;
import Reika.RotaryCraft.ModInterface.Minetweaker.FrictionTweaker;
import Reika.RotaryCraft.ModInterface.Minetweaker.GrinderTweaker;
import Reika.RotaryCraft.ModInterface.Minetweaker.PulseJetTweaker;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;

import appeng.api.config.PowerUnits;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.recipes.RecipeManagers;


@Mod( modid = "RotaryCraft", name="RotaryCraft", version = "v@MAJOR_VERSION@@MINOR_VERSION@", certificateFingerprint = "@GET_FINGERPRINT@", dependencies="required-after:DragonAPI")

public class RotaryCraft extends DragonAPIMod {
	public static final String packetChannel = "RotaryCraftData";

	public static final CreativeTabs tabRotary = new TabRotaryCraft();
	public static final CreativeTabs tabPower = new TabRotaryPower();
	public static final CreativeTabs tabRotaryItems = new TabRotaryItems();
	public static final CreativeTabs tabRotaryTools = new TabRotaryTools();
	public static final CreativeTabs tabModOres = new TabModOre();
	public static final CreativeTabs tabSpawner = new TabSpawner();

	private static final int[] dmgs = {
			ArmorMaterial.DIAMOND.getDamageReductionAmount(0),
			ArmorMaterial.DIAMOND.getDamageReductionAmount(1),
			ArmorMaterial.DIAMOND.getDamageReductionAmount(2),
			ArmorMaterial.DIAMOND.getDamageReductionAmount(3)
	};

	public static final ArmorMaterial NVHM = EnumHelper.addArmorMaterial("NVHelmet", ArmorMaterial.DIAMOND.getDurability(0), dmgs, ArmorMaterial.GOLD.getEnchantability());
	public static final ArmorMaterial NVGM = EnumHelper.addArmorMaterial("NVGoggles", 65536, new int[]{0, 0, 0, 0}, 0);
	public static final ArmorMaterial IOGM = EnumHelper.addArmorMaterial("IOGoggles", 65536, new int[]{0, 0, 0, 0}, 0);
	public static final ArmorMaterial JETPACK = EnumHelper.addArmorMaterial("Jetpack", 65536, new int[]{0, 0, 0, 0}, 0);
	public static final ArmorMaterial JUMP = EnumHelper.addArmorMaterial("Jump", 65536, new int[]{0, 0, 0, 0}, 0);

	public static final ArmorMaterial BEDROCK = EnumHelper.addArmorMaterial("Bedrock", Integer.MAX_VALUE, new int[]{6, 12, 10, 5}, 18);
	public static final ArmorMaterial HSLA = EnumHelper.addArmorMaterial("HSLA", 24, new int[]{3, 7, 5, 3}, ArmorMaterial.IRON.getEnchantability());

	public static final EnhancedFluid jetFuelFluid = (EnhancedFluid)new EnhancedFluid("rc jet fuel").setColor(0xFB5C90).setDensity(810).setViscosity(800);
	public static final EnhancedFluid lubeFluid = (EnhancedFluid)new EnhancedFluid("rc lubricant").setColor(0xE4E18E).setDensity(750).setViscosity(1200);
	public static final EnhancedFluid ethanolFluid = (EnhancedFluid)new EnhancedFluid("rc ethanol").setColor(0x5CC5B2).setDensity(789).setViscosity(950).setTemperature(340);
	public static final EnhancedFluid nitrogenFluid = (EnhancedFluid)new EnhancedFluid("rc liquid nitrogen").setColor(0xB37ECC).setDensity(808).setViscosity(158).setTemperature(77);
	public static final Fluid poisonFluid = new Fluid("poison"); //for defoliator
	public static final Fluid hslaFluid = new EnhancedFluid("molten hsla").setColor(0xF0B564).setTemperature(1873).setDensity(7000).setViscosity(6100); //for TiC

	public static final MachineDamage jetingest = new MachineDamage("was sucked into a jet engine").setArmorBlocking(1F, 90, 1, 2, 3, 4);
	public static final MachineDamage hydrokinetic = new MachineDamage("was paddled to death").setArmorBlocking(0.5F, 2, 4);
	public static final MachineDamage shock = (MachineDamage)new MachineDamage("was electrified").setDamageBypassesArmor();
	public static final MachineDamage grind = new MachineDamage("was ground to a pulp").setArmorBlocking(0.5F, 6, 1);
	public static final MachineDamage freezeDamage = new MachineDamage("froze");
	public static final MachineDamage heatDamage = (MachineDamage)new MachineDamage("burned up").setFireDamage();

	static final Random rand = new Random();

	public static final Block[] blocks = new Block[BlockRegistry.blockList.length];
	public static final Item[] items = new Item[ItemRegistry.itemList.length];

	public static Achievement[] achievements;

	public static FreezePotion freeze;
	public static PotionDeafness deafness;

	public static IIcon hydratorOverlay;
	public static IIcon woodLattice;

	public static String currentVersion = "v@MAJOR_VERSION@@MINOR_VERSION@";

	@Instance("RotaryCraft")
	public static RotaryCraft instance = new RotaryCraft();

	public static final RotaryConfig config = new RotaryConfig(instance, ConfigRegistry.optionList, ExtraConfigIDs.idList);

	public static ModLogger logger;

	//private String version;

	@SidedProxy(clientSide="Reika.RotaryCraft.ClientProxy", serverSide="Reika.RotaryCraft.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void invalidSignature(FMLFingerprintViolationEvent evt) {

	}

	public final boolean isLocked() {
		return !ModLockController.instance.verify(this);
	}

	private final boolean checkForLock() {
		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry r = ItemRegistry.itemList[i];
			if (!r.isDummiedOut()) {
				Item id = r.getItemInstance();
				if (BannedItemReader.instance.containsID(id)) {
					return true;
				}
			}
		}
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			BlockRegistry r = BlockRegistry.blockList[i];
			if (!r.isDummiedOut()) {
				Block id = r.getBlockInstance();
				if (BannedItemReader.instance.containsID(id)) {
					return true;
				}
			}
		}/*
		for (int i = 0; i < ExtraConfigIDs.idList.length; i++) {
			ExtraConfigIDs entry = ExtraConfigIDs.idList[i];
			if (entry.isBlock()) {
				int id = entry.getValue();
				if (BannedItemReader.instance.containsID(id))
					return true;
			}
			else if (entry.isItem()) {
				int id = entry.getShiftedValue();
				if (BannedItemReader.instance.containsID(id))
					return true;
			}
		}*/
		return false;
	}

	@Override
	@EventHandler
	public void preload(FMLPreInitializationEvent evt) {
		this.startTiming(LoadPhase.PRELOAD);
		this.verifyInstallation();
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			MinecraftForge.EVENT_BUS.register(JetpackFuelOverlay.instance);

		config.loadSubfolderedConfigFile(evt);
		config.initProps(evt);
		proxy.registerSounds();
		ModLockController.instance.registerMod(this);

		logger = new ModLogger(instance, ConfigRegistry.ALARM.getState());
		if (DragonOptions.FILELOG.getState())
			logger.setOutput("**_Loading_Log.log");

		this.setupClassFiles();

		MinecraftForge.EVENT_BUS.register(RotaryEventManager.instance);
		FMLCommonHandler.instance().bus().register(RotaryEventManager.instance);

		int id = ExtraConfigIDs.FREEZEID.getValue();
		IDCollisionTracker.instance.addPotionID(instance, id, FreezePotion.class);
		freeze = (FreezePotion)new FreezePotion(id).setPotionName("Frozen Solid");

		ReikaPacketHelper.registerPacketHandler(instance, packetChannel, new PacketHandlerCore());

		//id = ExtraConfigIDs.DEAFID.getValue();
		//PotionCollisionTracker.instance.addPotionID(instance, id, PotionDeafness.class);
		//deafness = (PotionDeafness)new PotionDeafness(id).setPotionName("Deafness");

		//version = evt.getModMetadata().version;

		CreativeTabSorter.instance.registerCreativeTabAfter(tabRotaryItems, tabRotary);
		CreativeTabSorter.instance.registerCreativeTabAfter(tabRotaryTools, tabRotary);
		CreativeTabSorter.instance.registerCreativeTabAfter(tabModOres, tabRotary);
		CreativeTabSorter.instance.registerCreativeTabAfter(tabSpawner, tabRotary);

		//CompatibilityTracker.instance.registerIncompatibility(ModList.ROTARYCRAFT, ModList.OPTIFINE, CompatibilityTracker.Severity.GLITCH, "Optifine is known to break some rendering and cause framerate drops.");
		CompatibilityTracker.instance.registerIncompatibility(ModList.ROTARYCRAFT, ModList.GREGTECH, CompatibilityTracker.Severity.GLITCH, "The GT unifier registers HSLA steel as standard OreDict steel. This breaks the techtrees of mods like RailCraft and TConstruct.");

		FMLInterModComms.sendMessage("zzzzzcustomconfigs", "blacklist-mod-as-output", this.getModContainer().getModId());

		ConfigMatcher.instance.addConfigList(this, ConfigRegistry.optionList);
		ConfigMatcher.instance.addConfigList(this, ExtraConfigIDs.idList);

		RayTracer.addVisuallyTransparentBlock(BlockRegistry.BLASTGLASS.getBlockInstance());
		RayTracer.addVisuallyTransparentBlock(BlockRegistry.BLASTPANE.getBlockInstance());

		this.basicSetup(evt);
		this.finishTiming();
	}

	@Override
	@EventHandler
	public void load(FMLInitializationEvent event) {
		this.startTiming(LoadPhase.LOAD);

		if (this.checkForLock()) {
			ModLockController.instance.unverify(this);
		}
		if (this.isLocked()) {
			ReikaJavaLibrary.pConsole("");
			ReikaJavaLibrary.pConsole("\t========================================= ROTARYCRAFT ===============================================");
			ReikaJavaLibrary.pConsole("\tNOTICE: It has been detected that third-party plugins are being used to disable parts of RotaryCraft.");
			ReikaJavaLibrary.pConsole("\tBecause this is frequently done to sell access to mod content, which is against the Terms of Use");
			ReikaJavaLibrary.pConsole("\tof both Mojang and the mod, the mod has been functionally disabled. No damage will occur to worlds,");
			ReikaJavaLibrary.pConsole("\tand all machines (including contents) and items already placed or in inventories will remain so,");
			ReikaJavaLibrary.pConsole("\tbut its machines will not function, recipes will not load, and no renders or textures will be present.");
			ReikaJavaLibrary.pConsole("\tAll other mods in your installation will remain fully functional.");
			ReikaJavaLibrary.pConsole("\tTo regain functionality, unban the RotaryCraft content, and then reload the game. All functionality");
			ReikaJavaLibrary.pConsole("\twill be restored. You may contact Reika for further information on his forum thread.");
			ReikaJavaLibrary.pConsole("\t=====================================================================================================");
			ReikaJavaLibrary.pConsole("");
		}

		if (!this.isLocked()) {
			if (ConfigRegistry.ACHIEVEMENTS.getState()) {
				achievements = new Achievement[RotaryAchievements.list.length];
				RotaryAchievements.registerAchievements();
			}
		}

		if (this.isLocked())
			PlayerHandler.instance.registerTracker(LockNotification.instance);

		if (!this.isLocked()) {
			proxy.addArmorRenders();
			proxy.registerRenderers();
		}

		PackModificationTracker.instance.addMod(this, config);

		ItemStackRepository.instance.registerClass(this, ItemStacks.class);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		RotaryRegistration.addTileEntities();
		RotaryRegistration.addEntities();

		ReikaDispenserHelper.addDispenserAction(ItemStacks.compost, ReikaDispenserHelper.bonemealEffect);

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			RotaryDescriptions.loadData();
		}
		//DemoMusic.addTracks();

		RotaryRegistration.loadOreDictionary();
		RotaryRegistration.auxRegistration();
		RotaryRecipes.loadMachineRecipeHandlers();
		if (!this.isLocked()) {
			RotaryRecipes.addRecipes();
		}
		RotaryChests.addToChests();

		float iron = ConfigRegistry.EXTRAIRON.getFloat();
		if (iron > 1) {
			GameRegistry.registerWorldGenerator(new ExtraIronGenerator(iron), 3000);
			logger.log(String.format("Extra iron ore gen enabled, with a scaling factor of %.1fx.", iron));
		}

		MinecraftForge.addGrassSeed(ItemStacks.canolaSeeds, 2);

		//MinecraftForge.setToolClass(ItemRegistry.STEELAXE.getItemInstance(), "axe", 2);
		//MinecraftForge.setToolClass(ItemRegistry.STEELPICK.getItemInstance(), "pickaxe", 2);
		//MinecraftForge.setToolClass(ItemRegistry.STEELSHOVEL.getItemInstance(), "shovel", 2);

		FMLInterModComms.sendMessage(ModList.THAUMCRAFT.modLabel, "harvestStandardCrop", BlockRegistry.CANOLA.getStackOfMetadata(9));
		for (int i = 0; i < RotaryNames.blockNames.length; i++) {
			ItemStack is = BlockRegistry.DECO.getStackOfMetadata(i);
			FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", is);
		}
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", BlockRegistry.BLASTGLASS.getStackOf());

		FMLInterModComms.sendMessage("Randomod", "blacklist", this.getModContainer().getModId());

		DonatorController.instance.registerMod(this, DonatorController.reikaURL);

		if (!this.isLocked())
			IntegrityChecker.instance.addMod(instance, BlockRegistry.blockList, ItemRegistry.itemList);

		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.redstone_block);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.lapis_block);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.planks);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.stone);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.nether_brick);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.emerald_block);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.obsidian);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.sandstone);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.quartz_block);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.wool);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Blocks.glass);

		if (ConfigRegistry.HANDBOOK.getState())
			PlayerFirstTimeTracker.addTracker(new HandbookTracker());
		PlayerHandler.instance.registerTracker(HandbookConfigVerifier.instance);

		//ReikaEEHelper.blacklistRegistry(BlockRegistry.blockList);
		//ReikaEEHelper.blacklistRegistry(ItemRegistry.itemList);

		ReikaEEHelper.blacklistItemStack(ItemStacks.steelingot);
		ReikaEEHelper.blacklistItemStack(ItemStacks.bedingot);
		ReikaEEHelper.blacklistItemStack(ItemStacks.springingot);
		ReikaEEHelper.blacklistItemStack(ItemStacks.redgoldingot);
		ReikaEEHelper.blacklistEntry(ItemRegistry.ETHANOL);
		ReikaEEHelper.blacklistEntry(BlockRegistry.BLASTGLASS);

		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.REACTORCRAFT, "Endgame power generation of multiple gigawatts");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.ELECTRICRAFT, "Easier and lower-CPU-load power transmission and distribution");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.BCENERGY, "Access to alternate lubricant production");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.THERMALEXPANSION, "Access to some interface recipes");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.FORESTRY, "Access to Canola bees to speed canola growth and produce some lubricant");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.RAILCRAFT, "Access to steam power generation and consumption");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.TWILIGHT, "Special integration with TF mobs and structures");

		SensitiveItemRegistry.instance.registerItem(this, BlockRegistry.BLASTGLASS.getBlockInstance(), false);
		SensitiveItemRegistry.instance.registerItem(this, BlockRegistry.BLASTPANE.getBlockInstance(), true);

		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.YEAST.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.ETHANOL.getItemInstance(), true);
		//SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.CANOLA.getItemInstance(), true);
		//SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.EXTRACTS.getItemInstance(), true);
		//SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.MODEXTRACTS.getItemInstance(), true);
		//SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.CUSTOMEXTRACT.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.ENGINE.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.SHAFT.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.FLYWHEEL.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.GEARBOX.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.MACHINE.getItemInstance(), true);
		/*
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.SHAFTCRAFT.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.BORECRAFT.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.ENGINECRAFT.getItemInstance(), true);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.MISCCRAFT.getItemInstance(), true);
		 */
		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry ir = ItemRegistry.itemList[i];
			if (!ir.isDummiedOut()) {
				if (ir.isBedrockArmor() || ir.isBedrockTypeArmor() || ir.isBedrockTool())
					SensitiveItemRegistry.instance.registerItem(this, ir.getItemInstance(), true);
			}
		}
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.sludge, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.cleansludge, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.springingot, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.bedingotblock, true);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.steelblock, true);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.steelingot, true);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.netherrackdust, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.tar, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.redgoldingot, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.tungsteningot, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.bedrockdust, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.bedingot, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.silumin, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemRegistry.UPGRADE.getItemInstance(), true);

		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.aluminumcylinder, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.bedrockdrill, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.highcombustor, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.compoundturb, false);
		SensitiveItemRegistry.instance.registerItem(this, ItemStacks.compoundcompress, false);

		if (MTInteractionManager.isMTLoaded()) {
			MTInteractionManager.instance.blacklistRecipeRemovalFor(MachineRegistry.BLASTFURNACE.getCraftedProduct());
			MTInteractionManager.instance.blacklistRecipeRemovalFor(MachineRegistry.WORKTABLE.getCraftedProduct());

			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.steelingot);
			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.redgoldingot);
			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.tungsteningot);
			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.bedrockdust);
			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.bedingot);
			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.springingot);
			MTInteractionManager.instance.blacklistOreDictTagsFor(ItemStacks.silumin);
		}

		SensitiveFluidRegistry.instance.registerFluid("rc jet fuel");
		SensitiveFluidRegistry.instance.registerFluid("rc ethanol");
		SensitiveFluidRegistry.instance.registerFluid("rc lubricant");
		SensitiveFluidRegistry.instance.registerFluid("rc liquid nitrogen");
		SensitiveFluidRegistry.instance.registerFluid("molten hsla");

		MinetweakerHooks.instance.registerClass(GrinderTweaker.class);
		MinetweakerHooks.instance.registerClass(PulseJetTweaker.class);
		MinetweakerHooks.instance.registerClass(FrictionTweaker.class);

		FurnaceFuelRegistry.instance.registerItemSimple(ItemRegistry.ETHANOL.getStackOf(), 5);
		FurnaceFuelRegistry.instance.registerItemSimple(ItemStacks.coke, 12);
		FurnaceFuelRegistry.instance.registerItemSimple(ItemStacks.anthracite, 24);
		FurnaceFuelRegistry.instance.registerItemSimple(ItemStacks.cokeblock, 12*FurnaceFuelRegistry.instance.getBlockOverItemFactor());
		FurnaceFuelRegistry.instance.registerItemSimple(ItemStacks.anthrablock, 24*FurnaceFuelRegistry.instance.getBlockOverItemFactor());
		FurnaceFuelRegistry.instance.registerItemSimple(ItemStacks.sawdust, 0.25F);

		if (ModList.AGRICRAFT.isLoaded()) {
			AgriCanola.register();
		}

		if (ModList.APPENG.isLoaded()) {
			this.fixAEPowerRatio();
		}

		this.finishTiming();
	}

	@ModDependent(ModList.APPENG)
	private void fixAEPowerRatio() {
		double f = 1/11526D;
		if (ReikaMathLibrary.isValueInsideBoundsIncl(f-0.005, f+0.005, PowerUnits.WA.conversionRatio)) {
			PowerUnits.WA.conversionRatio = 1/1040D;
		}
	}

	@Override
	@EventHandler
	public void postload(FMLPostInitializationEvent evt) {
		this.startTiming(LoadPhase.POSTLOAD);

		OreForcer.instance.forceCompatibility();

		CustomExtractLoader.instance.loadFile();
		ExtractorModOres.addCustomSmelting();
		TileEntityGrinder.loadSeeds();

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaJavaLibrary.initClass(BlockColorMapper.class, true);
			BlockColorMapper.instance.loadFromConfig();
		}

		ModCropList.addCustomCropType(new SimpleCropHandler(ModList.ROTARYCRAFT, 0x00cc00, "CANOLA", BlockRegistry.CANOLA.getBlockInstance(), 9, ItemRegistry.CANOLA.getStackOf()));

		//RotaryRecipes.addModInterface();
		proxy.initClasses();

		proxy.loadDonatorRender();

		TileEntityReservoir.initCreativeFluids();
		TileEntityFluidCompressor.initCreativeFluids();
		ItemFuelTank.initCreativeFluids();

		RotaryIntegrationManager.verifyClassIntegrity();

		if (!this.isLocked()) {
			if (ModList.FORESTRY.isLoaded()) {
				try {
					CanolaBee bee = new CanolaBee();
					bee.register();
					bee.addBreeding("Meadows", "Cultivated", 20);
					HashMap<ItemStack, Float> map = new HashMap();
					map.put(ItemStacks.slipperyPropolis, 0.80F);
					RecipeManagers.centrifugeManager.addRecipe(30, ItemStacks.slipperyComb, map);
					FluidStack fs = new FluidStack(FluidRegistry.getFluid("rc lubricant"), 20); //was 150
					RecipeManagers.squeezerManager.addRecipe(30, new ItemStack[]{ItemStacks.slipperyPropolis}, fs);
				}
				catch (IncompatibleClassChangeError e) {
					e.printStackTrace();
					logger.logError("Could not add Forestry integration. Check your versions; if you are up-to-date with both mods, notify Reika.");
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.logError("Could not add Forestry integration. Check your versions; if you are up-to-date with both mods, notify Reika.");
				}
				catch (LinkageError e) {
					e.printStackTrace();
					logger.logError("Could not add Forestry integration. Check your versions; if you are up-to-date with both mods, notify Reika.");
				}

				//MobBait.addBait("BUTTERFLY", ReikaBeeHelper.getButterflyClass(), -1, ForestryHandler.ItemEntry.POLLEN.getItem(), Items.gunpowder);
			}

			if (ModList.TINKERER.isLoaded() && (Pulses.TOOLS.isLoaded() || Pulses.WEAPONS.isLoaded())) {
				int id = ExtraConfigIDs.BEDROCKID.getValue();
				CustomTinkerMaterial mat = TinkerMaterialHelper.instance.createMaterial(id, this, "Bedrock");
				mat.durability = 1000000;
				mat.damageBoost = 5;
				mat.harvestLevel = 800;
				mat.miningSpeed = 1800;
				mat.handleModifier = 3F;
				mat.setUnbreakable();
				mat.chatColor = EnumChatFormatting.BLACK.toString();
				mat.renderColor = 0x383838;

				mat.disableToolPart(ToolParts.PICK).disableToolPart(ToolParts.AXEHEAD).disableToolPart(ToolParts.LUMBER);
				mat.disableToolPart(ToolParts.SHOVEL).disableToolPart(ToolParts.SWORD).disableToolPart(ToolParts.SCYTHE);

				mat.setDisallowCheatedParts();

				mat.register(true).registerTexture("tinkertools/bedrock/bedrock", false);
				//mat.registerPatternBuilder(ItemStacks.bedingot);
				mat.registerWeapons(ItemStacks.bedingot, 10, 0.5F, 5F, 4F, 15F, 0);


				id = ExtraConfigIDs.HSLAID.getValue();
				mat = TinkerMaterialHelper.instance.createMaterial(id, this, "HSLA");
				mat.durability = 600;
				mat.damageBoost = 3;
				mat.harvestLevel = 2;
				mat.miningSpeed = 1200;
				mat.handleModifier = 1.2F;
				mat.chatColor = EnumChatFormatting.LIGHT_PURPLE.toString();
				mat.renderColor = 0xCACBF2;

				//mat.disableToolPart(ToolParts.PICK).disableToolPart(ToolParts.AXEHEAD).disableToolPart(ToolParts.LUMBER);
				//mat.disableToolPart(ToolParts.SHOVEL).disableToolPart(ToolParts.SWORD).disableToolPart(ToolParts.SCYTHE);

				mat.setDisallowCheatedParts();

				mat.register(true).registerTexture("tinkertools/hsla/hsla", false);
				mat.registerRepairMaterial(ItemStacks.steelingot);
				//mat.registerPatternBuilder(ItemStacks.steelingot);
				mat.registerWeapons(ItemStacks.steelblock, 8, 0.75F, 2F, 2.5F, 8F, 0.015F);
				mat.registerSmelteryCasting(ItemStacks.steelingot, hslaFluid, 750, ItemStacks.steelblock);
			}

			for (int i = 0; i < MachineRegistry.machineList.length; i++) {
				MachineRegistry m = MachineRegistry.machineList.get(i);
				if (!m.allowsAcceleration()) {
					if (ModList.CHROMATICRAFT.isLoaded())
						ChromaRC.blacklistTileAccelerator(m);
					TimeTorchHelper.blacklistTileEntity(m.getTEClass());
				}
			}
			for (int i = 0; i < EngineType.engineList.length; i++) {
				EngineType type = EngineType.engineList[i];
				if (ModList.CHROMATICRAFT.isLoaded()) {
					ChromatiAPI.getAPI().adjacency().addAcceleratorBlacklist(type.engineClass, type.name(), type.getCraftedProduct(), BlacklistReason.EXPLOIT);
				}
				TimeTorchHelper.blacklistTileEntity(type.engineClass);
			}

			if (ModList.THAUMCRAFT.isLoaded()) {
				RotaryAspectManager.addThaumAspects();
			}

			if (ModList.CHROMATICRAFT.isLoaded()) {
				ChromaRC.addHealingCoreEffects();
				ChromaRC.addAuraPouchItems();
				ChromaRC.addAcceleratorGroupedBlacklistItems();
			}

			RotaryRecipes.addPostLoadRecipes();
		}

		if (ModList.ROUTER.isLoaded()) {
			RouterHelper.blacklistTileEntity(TileEntityExtractor.class, "Extractor", "BlockMIMachine:10"); //Extractor
		}

		this.finishTiming();
	}

	@EventHandler
	public void registerCommands(FMLServerStartingEvent evt) {
		evt.registerServerCommand(new FindMachinesCommand());
	}

	@EventHandler
	public void overrideRecipes(FMLServerStartedEvent evt) {
		this.reinitRecipes();
	}

	public void reinitRecipes() {
		if (!this.isLocked()) {
			if (!ReikaRecipeHelper.isCraftable(MachineRegistry.BLASTFURNACE.getCraftedProduct())) {
				GameRegistry.addRecipe(new ShapedOreRecipe(MachineRegistry.BLASTFURNACE.getCraftedProduct(), RotaryRecipes.getBlastFurnaceIngredients()));
			}
			if (!ReikaRecipeHelper.isCraftable(MachineRegistry.WORKTABLE.getCraftedProduct())) {
				GameRegistry.addRecipe(new ShapedOreRecipe(MachineRegistry.WORKTABLE.getCraftedProduct(), RotaryRecipes.getWorktableIngredients()));
			}
		}
	}

	private static void setupClassFiles() {
		ReikaRegistryHelper.instantiateAndRegisterBlocks(RotaryCraft.instance, BlockRegistry.blockList, RotaryCraft.blocks);
		ReikaRegistryHelper.instantiateAndRegisterItems(RotaryCraft.instance, ItemRegistry.itemList, RotaryCraft.items);
		ItemRegistry.SPAWNER.getItemInstance().setCreativeTab(tabSpawner);

		BlockRegistry.loadMappings();
		ItemRegistry.loadMappings();

		RotaryRegistration.setupLiquids();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void textureHook(TextureStitchEvent.Pre event) {
		if (!this.isLocked())
			RotaryRegistration.setupLiquidIcons(event);
		if (OldTextureLoader.instance.loadOldTextures())
			OldTextureLoader.instance.reloadOldTextures(event.map);
		if (event.map.getTextureType() == 0) {
			hydratorOverlay = event.map.registerIcon("rotarycraft:wateroverlay");
			woodLattice = event.map.registerIcon("rotarycraft:woodlattice");
		}
	}

	@Override
	public String getDisplayName() {
		return "RotaryCraft";
	}

	@Override
	public String getModAuthorName() {
		return "Reika";
	}

	@Override
	public URL getDocumentationSite() {
		return DragonAPICore.getReikaForumPage();
	}

	@Override
	public URL getBugSite() {
		return DragonAPICore.getReikaGithubPage();
	}

	@Override
	public String getWiki() {
		return "http://rotarycraft.wikia.com/wiki/RotaryCraft_Wiki";
	}

	@Override
	public ModLogger getModLogger() {
		return logger;
	}

	@Override
	public String getUpdateCheckURL() {
		return CommandableUpdateChecker.reikaURL;
	}

	@Override
	public File getConfigFolder() {
		return config.getConfigFolder();
	}

	@Override
	protected Class<? extends IClassTransformer> getASMClass() {
		return RotaryASMHandler.ASMExecutor.class;
	}
}
