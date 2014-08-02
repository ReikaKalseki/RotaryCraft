/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.net.URL;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.Aspect;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.CommandableUpdateChecker;
import Reika.DragonAPI.Auxiliary.CompatibilityTracker;
import Reika.DragonAPI.Auxiliary.DonatorController;
import Reika.DragonAPI.Auxiliary.IntegrityChecker;
import Reika.DragonAPI.Auxiliary.PlayerFirstTimeTracker;
import Reika.DragonAPI.Auxiliary.PotionCollisionTracker;
import Reika.DragonAPI.Auxiliary.SuggestedModsTracker;
import Reika.DragonAPI.Auxiliary.VanillaIntegrityTracker;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Extras.ItemSpawner;
import Reika.DragonAPI.Instantiable.CustomStringDamageSource;
import Reika.DragonAPI.Instantiable.EnhancedFluid;
import Reika.DragonAPI.Instantiable.IO.ModLogger;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.BannedItemReader;
import Reika.DragonAPI.ModInteract.ReikaMystcraftHelper;
import Reika.DragonAPI.ModInteract.ReikaThaumHelper;
import Reika.GeoStrata.API.AcceleratorBlacklist;
import Reika.GeoStrata.API.AcceleratorBlacklist.BlacklistReason;
import Reika.RotaryCraft.Auxiliary.FreezePotion;
import Reika.RotaryCraft.Auxiliary.HandbookTracker;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.JetpackFuelOverlay;
import Reika.RotaryCraft.Auxiliary.LockNotification;
import Reika.RotaryCraft.Auxiliary.PotionDeafness;
import Reika.RotaryCraft.Auxiliary.PotionGrowthHormone;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.TabModOre;
import Reika.RotaryCraft.Auxiliary.TabRotaryCraft;
import Reika.RotaryCraft.Auxiliary.TabRotaryItems;
import Reika.RotaryCraft.Auxiliary.TabRotaryTools;
import Reika.RotaryCraft.Auxiliary.TabSpawner;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Base.ItemMulti;
import Reika.RotaryCraft.Blocks.BlockBeam;
import Reika.RotaryCraft.Blocks.BlockBedrockSlice;
import Reika.RotaryCraft.Blocks.BlockBlastGlass;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Blocks.BlockDeco;
import Reika.RotaryCraft.Blocks.BlockDecoTank;
import Reika.RotaryCraft.Blocks.BlockLightBridge;
import Reika.RotaryCraft.Blocks.BlockLightblock;
import Reika.RotaryCraft.Blocks.BlockMiningPipe;
import Reika.RotaryCraft.Blocks.BlockObsidianGlass;
import Reika.RotaryCraft.Items.ItemFuelTank;
import Reika.RotaryCraft.Items.ItemModOre;
import Reika.RotaryCraft.Items.Placers.ItemAdvGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemEnginePlacer;
import Reika.RotaryCraft.Items.Placers.ItemFlywheelPlacer;
import Reika.RotaryCraft.Items.Placers.ItemGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemMachinePlacer;
import Reika.RotaryCraft.Items.Placers.ItemShaftPlacer;
import Reika.RotaryCraft.ModInterface.CanolaBee;
import Reika.RotaryCraft.ModInterface.MachineAspectMapper;
import Reika.RotaryCraft.ModInterface.OreForcer;
import Reika.RotaryCraft.ModInterface.Lua.LuaMethods;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ExtractorBonus;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MobBait;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.PlantMaterials;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.recipes.RecipeManagers;


@Mod( modid = "RotaryCraft", name="RotaryCraft", version="Gamma", certificateFingerprint = "@GET_FINGERPRINT@", dependencies="required-after:DragonAPI")
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "RotaryCraftData" }, packetHandler = ClientPackets.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "RotaryCraftData" }, packetHandler = ServerPackets.class))

public class RotaryCraft extends DragonAPIMod {
	public static final String packetChannel = "RotaryCraftData";

	public static final CreativeTabs tabRotary = new TabRotaryCraft(CreativeTabs.getNextID(), "RotaryCraft");
	public static final CreativeTabs tabRotaryItems = new TabRotaryItems(CreativeTabs.getNextID(), "RotaryItems");
	public static final CreativeTabs tabRotaryTools = new TabRotaryTools(CreativeTabs.getNextID(), "RotaryTools");
	public static final CreativeTabs tabModOres = new TabModOre(CreativeTabs.getNextID(), "RotaryModOres");
	public static final CreativeTabs tabSpawner = new TabSpawner(CreativeTabs.getNextID(), "Spawners");

	private static final int[] dmgs = {
		EnumArmorMaterial.DIAMOND.getDamageReductionAmount(0),
		EnumArmorMaterial.DIAMOND.getDamageReductionAmount(1),
		EnumArmorMaterial.DIAMOND.getDamageReductionAmount(2),
		EnumArmorMaterial.DIAMOND.getDamageReductionAmount(3)
	};

	public static final EnumArmorMaterial NVHM = EnumHelper.addArmorMaterial("NVHelmet", EnumArmorMaterial.DIAMOND.getDurability(0), dmgs, EnumArmorMaterial.GOLD.getEnchantability());
	public static final EnumArmorMaterial NVGM = EnumHelper.addArmorMaterial("NVGoggles", 65536, new int[]{0, 0, 0, 0}, 0);
	public static final EnumArmorMaterial IOGM = EnumHelper.addArmorMaterial("IOGoggles", 65536, new int[]{0, 0, 0, 0}, 0);
	public static final EnumArmorMaterial JETPACK = EnumHelper.addArmorMaterial("Jetpack", 65536, new int[]{0, 0, 0, 0}, 0);
	public static final EnumArmorMaterial JUMP = EnumHelper.addArmorMaterial("Jump", 65536, new int[]{0, 0, 0, 0}, 0);

	public static final EnumArmorMaterial BEDROCK = EnumHelper.addArmorMaterial("Bedrock", Integer.MAX_VALUE, new int[]{6, 12, 10, 5}, 18);
	public static final EnumArmorMaterial HSLA = EnumHelper.addArmorMaterial("HSLA", 24, new int[]{3, 7, 5, 3}, EnumArmorMaterial.IRON.getEnchantability());

	public static Block decoblock;
	public static Block blastpane;
	public static Block blastglass;

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

	public static Item shaftcraft;
	public static Item enginecraft;
	public static Item misccraft;
	public static Item borecraft;
	public static Item extracts;
	public static Item compacts;
	public static Item engineitems;
	public static Item powders;
	public static Item shaftitems;
	public static Item gbxitems;
	public static Item gearunits;
	public static Item machineplacer;
	public static Item flywheelitems;
	public static Item advgearitems;
	public static Item modextracts;
	public static Item modingots;
	public static Item spawner;
	public static Item modinterface;

	public static Block canola;
	public static Block miningpipe;
	public static Block bedrockslice;
	public static Block lightblock;
	public static Block beamblock;
	public static Block lightbridge;
	public static Block decoTank;

	public static final Block[] machineBlocks = new Block[BlockRegistry.blockList.length];
	public static final Item[] basicItems = new Item[ItemRegistry.itemList.length];

	public static Achievement[] achievements;
	public static Entity fallblock;

	public static FreezePotion freeze;
	public static PotionGrowthHormone growth;
	public static PotionDeafness deafness;

	@Instance("RotaryCraft")
	public static RotaryCraft instance = new RotaryCraft();

	public static final RotaryConfig config = new RotaryConfig(instance, ConfigRegistry.optionList, BlockRegistry.blockList, ItemRegistry.itemList, ExtraConfigIDs.idList, 0);

	public static ModLogger logger;

	//private String version;

	@SidedProxy(clientSide="Reika.RotaryCraft.ClientProxy", serverSide="Reika.RotaryCraft.CommonProxy")
	public static CommonProxy proxy;

	public final boolean isLocked() {
		return isLocked;
	}

	private final boolean checkForLock() {
		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry r = ItemRegistry.itemList[i];
			if (!r.isDummiedOut()) {
				int id = r.getShiftedID();
				if (BannedItemReader.instance.containsID(id))
					return true;
			}
		}
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			BlockRegistry r = BlockRegistry.blockList[i];
			if (!r.isDummiedOut()) {
				int id = r.getBlockID();
				if (BannedItemReader.instance.containsID(id))
					return true;
			}
		}
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
		}
		return false;
	}

	@Override
	@EventHandler
	public void preload(FMLPreInitializationEvent evt) {
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

		id = ExtraConfigIDs.GROWTHID.getValue();
		PotionCollisionTracker.instance.addPotionID(instance, id, PotionGrowthHormone.class);
		growth = (PotionGrowthHormone)new PotionGrowthHormone(id).setPotionName("Growth Hormone");

		//id = ExtraConfigIDs.DEAFID.getValue();
		//PotionCollisionTracker.instance.addPotionID(instance, id, PotionDeafness.class);
		//deafness = (PotionDeafness)new PotionDeafness(id).setPotionName("Deafness");

		//version = evt.getModMetadata().version;

		CompatibilityTracker.instance.registerIncompatibility(ModList.ROTARYCRAFT, ModList.OPTIFINE, CompatibilityTracker.Severity.GLITCH, "Optifine is known to break some rendering and cause framerate drops.");
		CompatibilityTracker.instance.registerIncompatibility(ModList.ROTARYCRAFT, ModList.GREGTECH, CompatibilityTracker.Severity.GLITCH, "The GT unifier registers HSLA steel as standard OreDict steel. This breaks the techtrees of mods like RailCraft and TConstruct.");

		this.basicSetup(evt);
	}

	@Override
	@EventHandler
	public void load(FMLInitializationEvent event) {
		if (this.isLocked())
			GameRegistry.registerPlayerTracker(LockNotification.instance);
		if (!this.isLocked()) {
			proxy.addArmorRenders();
			proxy.registerRenderers();
		}
		RotaryRegistration.addBlocks();
		if (!this.isLocked())
			RotaryNames.addNames();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
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
			GameRegistry.registerWorldGenerator(new ExtraIronGenerator(iron));
			logger.log(String.format("Extra iron ore gen enabled, with a scaling factor of %.1fx.", iron));
		}

		MinecraftForge.setBlockHarvestLevel(blastpane, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(blastglass, "pickaxe", 3);
		MinecraftForge.addGrassSeed(ItemRegistry.CANOLA.getStackOf(), 2);

		MinecraftForge.setToolClass(ItemRegistry.STEELAXE.getItemInstance(), "axe", 2);
		MinecraftForge.setToolClass(ItemRegistry.STEELPICK.getItemInstance(), "pickaxe", 2);
		MinecraftForge.setToolClass(ItemRegistry.STEELSHOVEL.getItemInstance(), "shovel", 2);

		FMLInterModComms.sendMessage(ModList.THAUMCRAFT.modLabel, "harvestStandardCrop", new ItemStack(RotaryCraft.canola, 1, 9));
		for (int i = 0; i < RotaryNames.blockNames.length; i++) {
			ItemStack is = new ItemStack(decoblock, 1, i);
			FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", is);
		}
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(blastglass));

		DonatorController.instance.addDonation(instance, "sys64738", 25.00F);
		DonatorController.instance.addDonation(instance, "Zerotheliger", 50.00F);
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
		DonatorController.instance.addDonation(instance, "Demosthenex", 50.00F);

		ReikaMystcraftHelper.disableFluidPage("jet fuel");
		ReikaMystcraftHelper.disableFluidPage("rc ethanol");
		ReikaMystcraftHelper.disableFluidPage("lubricant");
		ReikaMystcraftHelper.disableFluidPage("liquid nitrogen");

		if (!this.isLocked())
			IntegrityChecker.instance.addMod(instance, BlockRegistry.blockList, ItemRegistry.itemList);

		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.blockRedstone);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.blockLapis);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.planks);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.stone);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.netherBrick);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.blockEmerald);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.obsidian);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.sandStone);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.blockNetherQuartz);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.cloth);
		VanillaIntegrityTracker.instance.addWatchedBlock(instance, Block.glass);

		if (ConfigRegistry.HANDBOOK.getState())
			PlayerFirstTimeTracker.addTracker(new HandbookTracker());

		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.REACTORCRAFT, "Endgame power generation of multiple gigawatts");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.ELECTRICRAFT, "Easier and lower-CPU-load power transmission and distribution");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.BCENERGY, "Access to MJ power conversion and alternate lubricant production");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.THERMALEXPANSION, "Access to RF power conversion and some interface recipes");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.FORESTRY, "Access to Canola bees to speed canola growth and produce some lubricant");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.RAILCRAFT, "Access to steam power generation and consumption");
		SuggestedModsTracker.instance.addSuggestedMod(instance, ModList.TWILIGHT, "Special integration with TF mobs and structures");
	}

	@Override
	@EventHandler
	public void postload(FMLPostInitializationEvent evt) {
		OreForcer.instance.forceCompatibility();

		//RotaryRecipes.addModInterface();

		ReikaJavaLibrary.initClass(DifficultyEffects.class);
		ReikaJavaLibrary.initClass(ExtractorBonus.class);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			ReikaJavaLibrary.initClass(HandbookRegistry.class);
		ReikaJavaLibrary.initClass(MobBait.class);
		ReikaJavaLibrary.initClass(PlantMaterials.class);
		ReikaJavaLibrary.initClass(EngineType.class);
		ReikaJavaLibrary.initClass(SoundRegistry.class);
		ReikaJavaLibrary.initClass(PacketRegistry.class);
		ReikaJavaLibrary.initClass(PowerReceivers.class);
		ReikaJavaLibrary.initClass(LuaMethods.class);

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

		if (ModList.GEOSTRATA.isLoaded()) {
			for (int i = 0; i < MachineRegistry.machineList.length; i++) {
				MachineRegistry m = MachineRegistry.machineList[i];
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
	}

	@EventHandler
	public void overrideRecipes(FMLServerStartedEvent evt) {
		if (!this.isLocked()) {
			if (!ReikaRecipeHelper.isCraftable(MachineRegistry.BLASTFURNACE.getCraftedProduct())) {
				GameRegistry.addRecipe(MachineRegistry.BLASTFURNACE.getCraftedProduct(), "SSS", "SrS", "SSS", 'r', Item.redstone, 'S', ReikaItemHelper.stoneBricks);
			}
			if (!ReikaRecipeHelper.isCraftable(MachineRegistry.WORKTABLE.getCraftedProduct())) {
				GameRegistry.addRecipe(MachineRegistry.WORKTABLE.getCraftedProduct(), " C ", "SBS", "srs", 'r', Item.redstone, 'S', ItemStacks.steelingot, 'B', Block.brick, 'C', Block.workbench, 's', ReikaItemHelper.stoneSlab);
			}
		}
	}

	private static void setupClassFiles() {
		RotaryRegistration.instantiateItems();

		shaftcraft = new ItemMulti(ExtraConfigIDs.SHAFTCRAFT.getValue(), 0).setUnlocalizedName("shaftcraft");
		enginecraft = new ItemMulti(ExtraConfigIDs.ENGINECRAFT.getValue(), 1).setUnlocalizedName("enginecraft");
		misccraft = new ItemMulti(ExtraConfigIDs.MISCCRAFT.getValue(), 2).setUnlocalizedName("misccraft");
		borecraft = new ItemMulti(ExtraConfigIDs.BORECRAFT.getValue(), 3).setUnlocalizedName("borecraft");
		extracts = new ItemMulti(ExtraConfigIDs.EXTRACTS.getValue(), 4).setUnlocalizedName("extracts");
		compacts = new ItemMulti(ExtraConfigIDs.COMPACTS.getValue(), 6).setUnlocalizedName("compacts");
		engineitems = new ItemEnginePlacer(ExtraConfigIDs.ENGINEITEMS.getValue()).setUnlocalizedName("engines");
		powders = new ItemMulti(ExtraConfigIDs.POWDERS.getValue(), 8).setUnlocalizedName("powder");
		modinterface = new ItemMulti(ExtraConfigIDs.INTERFACE.getValue(), 14).setUnlocalizedName("modinterface");

		shaftitems = new ItemShaftPlacer(ExtraConfigIDs.SHAFTITEMS.getValue()).setUnlocalizedName("shafts");
		gbxitems = new ItemGearPlacer(ExtraConfigIDs.GEARBOXITEMS.getValue()).setUnlocalizedName("gbxs");
		gearunits = new ItemMulti(ExtraConfigIDs.GEARUNITS.getValue(), 23).setUnlocalizedName("gearunits");
		machineplacer = new ItemMachinePlacer(ExtraConfigIDs.MACHINEPLACER.getValue()).setUnlocalizedName("machineplacer");
		advgearitems = new ItemAdvGearPlacer(ExtraConfigIDs.ADVGEARITEMS.getValue()).setUnlocalizedName("advgearitem");
		flywheelitems = new ItemFlywheelPlacer(ExtraConfigIDs.FLYWHEELITEMS.getValue()).setUnlocalizedName("flywheelitem");
		//hydraulicitems = new ItemHydraulicPlacer(ExtraConfigIDs.HYDRAULICITEMS.getValue()).setUnlocalizedName("hydraulicitem");

		modextracts = new ItemModOre(ExtraConfigIDs.MODEXTRACTS.getValue()).setUnlocalizedName("modextracts");
		modingots = new ItemModOre(ExtraConfigIDs.MODINGOTS.getValue()).setUnlocalizedName("modingots");

		decoblock = new BlockDeco(ExtraConfigIDs.DECOBLOCKS.getValue()).setUnlocalizedName("decoblock");
		blastpane = new BlockBlastGlass(ExtraConfigIDs.BLASTPANE.getValue()).setUnlocalizedName("BlastGlassPane");
		blastglass = new BlockObsidianGlass(ExtraConfigIDs.BLASTGLASS.getValue()).setUnlocalizedName("BlastGlass");
		canola = new BlockCanola(ExtraConfigIDs.CANOLA.getValue()).setUnlocalizedName("Canola");

		spawner = new ItemSpawner(ExtraConfigIDs.SPAWNERS.getValue()).setUnlocalizedName("spawner").setCreativeTab(instance.isLocked() ? null : tabSpawner);

		RotaryRegistration.instantiateMachines();

		miningpipe = new BlockMiningPipe(ExtraConfigIDs.MININGPIPE.getValue()).setUnlocalizedName("MiningPipe");
		//gravlog = new BlockGravLog(ExtraConfigIDs.GRAVLOG.getValue()).setUnlocalizedName("GravLog");
		//gravleaves = new BlockGravLeaves(ExtraConfigIDs.GRAVLEAVES.getValue()).setUnlocalizedName("GravLeaves");
		lightblock = new BlockLightblock(ExtraConfigIDs.LIGHTBLOCK.getValue()).setUnlocalizedName("LightBlock");
		beamblock = new BlockBeam(ExtraConfigIDs.BEAMBLOCK.getValue()).setUnlocalizedName("BeamBlock");
		lightbridge = new BlockLightBridge(ExtraConfigIDs.BRIDGEBLOCK.getValue()).setUnlocalizedName("Bridge");
		bedrockslice = new BlockBedrockSlice(ExtraConfigIDs.BEDROCKSLICE.getValue()).setUnlocalizedName("BedrockSlice");
		decoTank = new BlockDecoTank(ExtraConfigIDs.DECOTANK.getValue()).setUnlocalizedName("DecoTank");

		RotaryRegistration.setupLiquids();
	}

	@ForgeSubscribe
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
