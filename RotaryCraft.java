/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.net.URL;
import java.util.Collection;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.Aspect;
import Reika.ChromatiCraft.API.AcceleratorBlacklist;
import Reika.ChromatiCraft.API.AcceleratorBlacklist.BlacklistReason;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.CreativeTabSorter;
import Reika.DragonAPI.Auxiliary.Trackers.CommandableUpdateChecker;
import Reika.DragonAPI.Auxiliary.Trackers.CompatibilityTracker;
import Reika.DragonAPI.Auxiliary.Trackers.DonatorController;
import Reika.DragonAPI.Auxiliary.Trackers.IntegrityChecker;
import Reika.DragonAPI.Auxiliary.Trackers.PlayerFirstTimeTracker;
import Reika.DragonAPI.Auxiliary.Trackers.PlayerHandler;
import Reika.DragonAPI.Auxiliary.Trackers.PotionCollisionTracker;
import Reika.DragonAPI.Auxiliary.Trackers.SuggestedModsTracker;
import Reika.DragonAPI.Auxiliary.Trackers.VanillaIntegrityTracker;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Base.DragonAPIMod.LoadProfiler.LoadPhase;
import Reika.DragonAPI.Instantiable.CustomStringDamageSource;
import Reika.DragonAPI.Instantiable.EnhancedFluid;
import Reika.DragonAPI.Instantiable.IO.ModLogger;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.ReikaRegistryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.BannedItemReader;
import Reika.DragonAPI.ModInteract.MTInteractionManager;
import Reika.DragonAPI.ModInteract.ReikaEEHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaMystcraftHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaThaumHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.RouterHelper;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.FindMachinesCommand;
import Reika.RotaryCraft.Auxiliary.FreezePotion;
import Reika.RotaryCraft.Auxiliary.HandbookNotifications.HandbookConfigVerifier;
import Reika.RotaryCraft.Auxiliary.HandbookTracker;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.JetpackFuelOverlay;
import Reika.RotaryCraft.Auxiliary.LockNotification;
import Reika.RotaryCraft.Auxiliary.PotionDeafness;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.TabModOre;
import Reika.RotaryCraft.Auxiliary.TabRotaryCraft;
import Reika.RotaryCraft.Auxiliary.TabRotaryItems;
import Reika.RotaryCraft.Auxiliary.TabRotaryTools;
import Reika.RotaryCraft.Auxiliary.TabSpawner;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Items.ItemFuelTank;
import Reika.RotaryCraft.ModInterface.CanolaBee;
import Reika.RotaryCraft.ModInterface.MachineAspectMapper;
import Reika.RotaryCraft.ModInterface.OreForcer;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
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


@Mod( modid = "RotaryCraft", name="RotaryCraft", version="Gamma", certificateFingerprint = "@GET_FINGERPRINT@", dependencies="required-after:DragonAPI")

public class RotaryCraft extends DragonAPIMod {
	public static final String packetChannel = "RotaryCraftData";

	public static final CreativeTabs tabRotary = new TabRotaryCraft();
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

	public static final EnhancedFluid jetFuelFluid = (EnhancedFluid)new EnhancedFluid("jet fuel").setColor(0xFB5C90).setDensity(810).setViscosity(800);
	public static final EnhancedFluid lubeFluid = (EnhancedFluid)new EnhancedFluid("lubricant").setColor(0xE4E18E).setDensity(750).setViscosity(1200);
	public static final EnhancedFluid ethanolFluid = (EnhancedFluid)new EnhancedFluid("rc ethanol").setColor(0x5CC5B2).setDensity(789).setViscosity(950).setTemperature(340);
	public static final EnhancedFluid nitrogenFluid = (EnhancedFluid)new EnhancedFluid("liquid nitrogen").setColor(0xffffff).setDensity(808).setViscosity(158).setTemperature(77);
	public static final Fluid poisonFluid = new Fluid("poison"); //for defoliator

	public static final CustomStringDamageSource jetingest = new CustomStringDamageSource("was sucked into a jet engine");
	public static final CustomStringDamageSource hydrokinetic = new CustomStringDamageSource("was paddled to death");
	public static final CustomStringDamageSource shock = (CustomStringDamageSource)new CustomStringDamageSource("was electrified").setDamageBypassesArmor();

	static final Random rand = new Random();

	private boolean isLocked = false;

	public static final Block[] blocks = new Block[BlockRegistry.blockList.length];
	public static final Item[] items = new Item[ItemRegistry.itemList.length];

	public static Achievement[] achievements;
	public static Entity fallblock;

	public static FreezePotion freeze;
	public static PotionDeafness deafness;

	public static String currentVersion = "v@MAJOR_VERSION@@MINOR_VERSION@";

	@Instance("RotaryCraft")
	public static RotaryCraft instance = new RotaryCraft();

	public static final RotaryConfig config = new RotaryConfig(instance, ConfigRegistry.optionList, ExtraConfigIDs.idList, 0);

	public static ModLogger logger;

	//private String version;

	@SidedProxy(clientSide="Reika.RotaryCraft.ClientProxy", serverSide="Reika.RotaryCraft.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void invalidSignature(FMLFingerprintViolationEvent evt) {

	}

	public final boolean isLocked() {
		return isLocked;
	}

	private final boolean checkForLock() {
		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry r = ItemRegistry.itemList[i];
			if (!r.isDummiedOut()) {
				Item id = r.getItemInstance();
				if (BannedItemReader.instance.containsID(id)) {
					ReikaJavaLibrary.pConsole("ROTARYCRAFT: Found Banned RotaryCraft Item Id "+id);
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
		MinecraftForge.EVENT_BUS.register(RotaryEventManager.instance);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			MinecraftForge.EVENT_BUS.register(JetpackFuelOverlay.instance);

		config.loadSubfolderedConfigFile(evt);
		config.initProps(evt);
		proxy.registerSounds();

		isLocked = this.checkForLock();
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

		logger = new ModLogger(instance, ConfigRegistry.ALARM.getState());

		this.setupClassFiles();

		if (!this.isLocked()) {
			if (ConfigRegistry.ACHIEVEMENTS.getState()) {
				achievements = new Achievement[RotaryAchievements.list.length];
				RotaryAchievements.registerAchievements();
			}
		}

		int id = ExtraConfigIDs.FREEZEID.getValue();
		PotionCollisionTracker.instance.addPotionID(instance, id, FreezePotion.class);
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

		this.basicSetup(evt);
		this.finishTiming();
	}

	@Override
	@EventHandler
	public void load(FMLInitializationEvent event) {
		this.startTiming(LoadPhase.LOAD);

		if (this.isLocked())
			PlayerHandler.instance.registerTracker(LockNotification.instance);

		if (!this.isLocked()) {
			proxy.addArmorRenders();
			proxy.registerRenderers();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		RotaryRegistration.addTileEntities();
		RotaryRegistration.addEntities();

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			RotaryDescriptions.loadData();
		//DemoMusic.addTracks();

		if (!this.isLocked()) {
			RotaryRecipes.addRecipes();
		}
		RotaryChests.addToChests();

		float iron = ConfigRegistry.EXTRAIRON.getFloat();
		if (iron > 1) {
			GameRegistry.registerWorldGenerator(new ExtraIronGenerator(iron), 3000);
			logger.log(String.format("Extra iron ore gen enabled, with a scaling factor of %.1fx.", iron));
		}

		MinecraftForge.addGrassSeed(ItemRegistry.CANOLA.getStackOf(), 2);

		//MinecraftForge.setToolClass(ItemRegistry.STEELAXE.getItemInstance(), "axe", 2);
		//MinecraftForge.setToolClass(ItemRegistry.STEELPICK.getItemInstance(), "pickaxe", 2);
		//MinecraftForge.setToolClass(ItemRegistry.STEELSHOVEL.getItemInstance(), "shovel", 2);

		FMLInterModComms.sendMessage(ModList.THAUMCRAFT.modLabel, "harvestStandardCrop", BlockRegistry.CANOLA.getStackOfMetadata(9));
		for (int i = 0; i < RotaryNames.blockNames.length; i++) {
			ItemStack is = BlockRegistry.DECO.getStackOfMetadata(i);
			FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", is);
		}
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", BlockRegistry.BLASTGLASS.getStackOf());

		DonatorController.instance.addDonation(instance, "sys64738", 25.00F);
		DonatorController.instance.addDonation(instance, "Zerotheliger", "bc7dc757-b92a-457e-98b9-351ce7a317a3", 50.00F);
		DonatorController.instance.addDonation(instance, "EverRunes", 75.00F);
		DonatorController.instance.addDonation(instance, "AnotherDeadBard", 25.00F);
		DonatorController.instance.addDonation(instance, "hyper1on", 25.00F);
		DonatorController.instance.addDonation(instance, "MarkyRedstone", 35.00F);
		DonatorController.instance.addDonation(instance, "Darkholme", 10.00F);
		DonatorController.instance.addDonation(instance, "william kirk", 10.00F);
		DonatorController.instance.addDonation(instance, "Tyler Rudie", 10.00F);
		DonatorController.instance.addDonation(instance, "Mortvana", 7.50F);
		DonatorController.instance.addDonation(instance, "goreacraft", 10.00F);
		DonatorController.instance.addDonation(instance, "Scooterdanny", 30.00F);
		DonatorController.instance.addDonation(instance, "Sibmer", 50.00F);
		DonatorController.instance.addDonation(instance, "Hezmana", 10.00F);
		DonatorController.instance.addDonation(instance, "Josh Ricker", 20.00F);
		DonatorController.instance.addDonation(instance, "Karapol", 25.00F);
		DonatorController.instance.addDonation(instance, "RiComikka", 15.00F);
		DonatorController.instance.addDonation(instance, "Spork", 10.00F);
		DonatorController.instance.addDonation(instance, "Demosthenex", "2249847d-1992-45e8-8c10-8d17f467ca96", 50.00F);
		DonatorController.instance.addDonation(instance, "Lavious", 15.00F);
		DonatorController.instance.addDonation(instance, "Paul17041993", 20.00F);
		DonatorController.instance.addDonation(instance, "Mattabase", 40.00F);
		DonatorController.instance.addDonation(instance, "Celestial Phoenix", 100.00F);
		DonatorController.instance.addDonation(instance, "SemicolonDash", 50.00F);
		DonatorController.instance.addDonation(instance, "Choco218", 50.00F);
		DonatorController.instance.addDonation(instance, "Dragonsummoner", 5.00F);
		DonatorController.instance.addDonation(instance, "StoneRhino", "a94d96b9-23c9-4458-b394-fcc63db67584", 100.00F);
		DonatorController.instance.addDonation(instance, "Jason Saffle", "2b9a2791-3465-4332-8013-4015dc9cc120", 20.00F);

		ReikaMystcraftHelper.disableFluidPage("jet fuel");
		ReikaMystcraftHelper.disableFluidPage("rc ethanol");
		ReikaMystcraftHelper.disableFluidPage("lubricant");
		ReikaMystcraftHelper.disableFluidPage("liquid nitrogen");

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
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.BCENERGY, "Access to MJ power conversion and alternate lubricant production");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.THERMALEXPANSION, "Access to RF power conversion and some interface recipes");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.FORESTRY, "Access to Canola bees to speed canola growth and produce some lubricant");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.RAILCRAFT, "Access to steam power generation and consumption");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.TWILIGHT, "Special integration with TF mobs and structures");

		if (MTInteractionManager.isMTLoaded()) {
			MTInteractionManager.instance.blacklistNewRecipesFor(BlockRegistry.BLASTGLASS.getBlockInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(BlockRegistry.BLASTPANE.getBlockInstance());

			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.ETHANOL.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.CANOLA.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.EXTRACTS.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.MODEXTRACTS.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.CUSTOMEXTRACT.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.ENGINE.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.SHAFT.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.FLYWHEEL.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.GEARBOX.getItemInstance());
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.MACHINE.getItemInstance());
			for (int i = 0; i < ItemRegistry.itemList.length; i++) {
				ItemRegistry ir = ItemRegistry.itemList[i];
				if (!ir.isDummiedOut()) {
					if (ir.isBedrockArmor() || ir.isBedrockTypeArmor() || ir.isBedrockTool())
						MTInteractionManager.instance.blacklistNewRecipesFor(ir.getItemInstance());
				}
			}
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.sludge);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.springingot);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.bedingotblock);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.steelblock);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.steelingot);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.netherrackdust);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.tar);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.redgoldingot);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.tungsteningot);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.bedrockdust);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemStacks.bedingot);
			MTInteractionManager.instance.blacklistNewRecipesFor(ItemRegistry.UPGRADE.getItemInstance());

			;//MinetweakerHooks.instance.registerClass(GrinderTweaker.class);
		}

		this.finishTiming();
	}

	@Override
	@EventHandler
	public void postload(FMLPostInitializationEvent evt) {
		this.startTiming(LoadPhase.POSTLOAD);

		OreForcer.instance.forceCompatibility();

		CustomExtractLoader.instance.loadFile();
		ExtractorModOres.addCustomSmelting();

		//RotaryRecipes.addModInterface();
		proxy.initClasses();

		proxy.loadDonatorRender();

		TileEntityReservoir.initCreativeFluids();
		TileEntityFluidCompressor.initCreativeFluids();
		ItemFuelTank.initCreativeFluids();

		if (!this.isLocked())
			RecipesGrinder.getRecipes().addOreRecipes();

		if (!this.isLocked())
			if (ModList.FORESTRY.isLoaded()) {
				try {
					CanolaBee bee = new CanolaBee();
					bee.register();
					bee.addBreeding("Meadows", "Cultivated", 20);
					RecipeManagers.centrifugeManager.addRecipe(30, ItemStacks.slipperyComb, ItemStacks.slipperyPropolis);
					FluidStack fs = new FluidStack(FluidRegistry.getFluid("lubricant"), 20); //was 150
					RecipeManagers.squeezerManager.addRecipe(30, new ItemStack[]{ItemStacks.slipperyPropolis}, fs);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		if (ModList.CHROMATICRAFT.isLoaded()) {
			for (int i = 0; i < MachineRegistry.machineList.length; i++) {
				MachineRegistry m = MachineRegistry.machineList.get(i);
				if (!m.allowsAcceleration())
					AcceleratorBlacklist.addBlacklist(m.getTEClass(), m.getName(), BlacklistReason.EXPLOIT);
			}
			for (int i = 0; i < EngineType.engineList.length; i++) {
				EngineType type = EngineType.engineList[i];
				AcceleratorBlacklist.addBlacklist(type.engineClass, type.name(), BlacklistReason.EXPLOIT);
			}
		}

		if (!this.isLocked())
			if (ModList.THAUMCRAFT.isLoaded()) {
				RotaryCraft.logger.log("Adding ThaumCraft aspects.");
				ReikaThaumHelper.addAspects(ItemRegistry.CANOLA.getStackOf(), Aspect.EXCHANGE, 2, Aspect.CROP, 1, Aspect.MECHANISM, 1);
				ReikaThaumHelper.addAspects(ItemRegistry.YEAST.getStackOf(), Aspect.EXCHANGE, 4);
				ReikaThaumHelper.clearAspects(ItemRegistry.HANDBOOK.getStackOf());

				ReikaThaumHelper.addAspects(ItemRegistry.BEDAXE.getStackOf(), Aspect.TOOL, 96);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDPICK.getStackOf(), Aspect.TOOL, 96);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDHOE.getStackOf(), Aspect.TOOL, 80);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDSWORD.getStackOf(), Aspect.TOOL, 80);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDSHEARS.getStackOf(), Aspect.TOOL, 80);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDSHOVEL.getStackOf(), Aspect.TOOL, 72);

				ReikaThaumHelper.addAspects(ItemRegistry.BEDLEGS.getStackOf(), Aspect.ARMOR, 140);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDHELM.getStackOf(), Aspect.ARMOR, 100);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDBOOTS.getStackOf(), Aspect.ARMOR, 80);
				ReikaThaumHelper.addAspects(ItemRegistry.BEDCHEST.getStackOf(), Aspect.ARMOR, 160);

				ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(0), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.MOTION, 2, Aspect.MECHANISM, 2);
				ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(1), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.FIRE, 3, Aspect.ENERGY, 12);
				ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(2), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.ENERGY, 7, Aspect.PLANT, 3);

				ReikaThaumHelper.addAspects(ItemRegistry.SHELL.getStackOf(), Aspect.FIRE, 8, Aspect.WEAPON, 8);

				ReikaThaumHelper.addAspects(ItemStacks.steelingot, Aspect.METAL, 10, Aspect.MECHANISM, 6);
				ReikaThaumHelper.addAspects(ItemStacks.netherrackdust, Aspect.FIRE, 4);
				ReikaThaumHelper.addAspects(ItemStacks.sludge, Aspect.ENERGY, 1);
				ReikaThaumHelper.addAspects(ItemStacks.sawdust, Aspect.TREE, 1);
				ReikaThaumHelper.addAspects(ItemStacks.anthracite, Aspect.FIRE, 4, Aspect.ENERGY, 4);
				ReikaThaumHelper.addAspects(ItemStacks.coke, Aspect.FIRE, 2, Aspect.MECHANISM, 2);

				MachineAspectMapper.instance.register();
			}

		if (!this.isLocked())
			RotaryRecipes.addPostLoadRecipes();

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
		if (!this.isLocked()) {
			if (!ReikaRecipeHelper.isCraftable(MachineRegistry.BLASTFURNACE.getCraftedProduct())) {
				Collection<ItemStack> c = RotaryRecipes.getBlastFurnaceGatingMaterials();
				for (ItemStack is : c)
					GameRegistry.addRecipe(MachineRegistry.BLASTFURNACE.getCraftedProduct(), "StS", "trt", "StS", 'r', Items.redstone, 'S', ReikaItemHelper.stoneBricks, is);
			}
			if (!ReikaRecipeHelper.isCraftable(MachineRegistry.WORKTABLE.getCraftedProduct())) {
				GameRegistry.addRecipe(MachineRegistry.WORKTABLE.getCraftedProduct(), " C ", "SBS", "srs", 'r', Items.redstone, 'S', ItemStacks.steelingot, 'B', Blocks.brick_block, 'C', Blocks.crafting_table, 's', ReikaItemHelper.stoneSlab);
			}
		}
	}

	private static void setupClassFiles() {
		ReikaRegistryHelper.instantiateAndRegisterItems(RotaryCraft.instance, ItemRegistry.itemList, RotaryCraft.items);
		ItemRegistry.SPAWNER.getItemInstance().setCreativeTab(tabSpawner);
		ReikaRegistryHelper.instantiateAndRegisterBlocks(RotaryCraft.instance, BlockRegistry.blockList, RotaryCraft.blocks);

		RotaryRegistration.setupLiquids();

		BlockRegistry.loadMappings();
		ItemRegistry.loadMappings();
	}

	private static void registerBlock(Block b) {
		GameRegistry.registerBlock(b, b.getUnlocalizedName());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void textureHook(TextureStitchEvent.Pre event) {
		if (!this.isLocked())
			RotaryRegistration.setupLiquidIcons(event);
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
}
