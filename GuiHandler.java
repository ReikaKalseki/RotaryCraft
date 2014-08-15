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

import Reika.DragonAPI.Base.ContainerBasicStorage;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Interfaces.InertIInv;
import Reika.RotaryCraft.Base.GuiBasicRange;
import Reika.RotaryCraft.Base.GuiBasicStorage;
import Reika.RotaryCraft.Base.GuiOneSlotInv;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.RemoteControlMachine;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;
import Reika.RotaryCraft.Containers.ContainerAerosolizer;
import Reika.RotaryCraft.Containers.ContainerBigFurnace;
import Reika.RotaryCraft.Containers.ContainerBlastFurnace;
import Reika.RotaryCraft.Containers.ContainerBlower;
import Reika.RotaryCraft.Containers.ContainerCVT;
import Reika.RotaryCraft.Containers.ContainerCannon;
import Reika.RotaryCraft.Containers.ContainerCentrifuge;
import Reika.RotaryCraft.Containers.ContainerCompactor;
import Reika.RotaryCraft.Containers.ContainerComposter;
import Reika.RotaryCraft.Containers.ContainerCrystallizer;
import Reika.RotaryCraft.Containers.ContainerDefoliator;
import Reika.RotaryCraft.Containers.ContainerEthanol;
import Reika.RotaryCraft.Containers.ContainerExtractor;
import Reika.RotaryCraft.Containers.ContainerFermenter;
import Reika.RotaryCraft.Containers.ContainerFillingStation;
import Reika.RotaryCraft.Containers.ContainerFractionator;
import Reika.RotaryCraft.Containers.ContainerFridge;
import Reika.RotaryCraft.Containers.ContainerGearbox;
import Reika.RotaryCraft.Containers.ContainerGrinder;
import Reika.RotaryCraft.Containers.ContainerHandCraft;
import Reika.RotaryCraft.Containers.ContainerHeater;
import Reika.RotaryCraft.Containers.ContainerItemCannon;
import Reika.RotaryCraft.Containers.ContainerJet;
import Reika.RotaryCraft.Containers.ContainerLandmine;
import Reika.RotaryCraft.Containers.ContainerObsidian;
import Reika.RotaryCraft.Containers.ContainerPerformance;
import Reika.RotaryCraft.Containers.ContainerPowerBus;
import Reika.RotaryCraft.Containers.ContainerProjector;
import Reika.RotaryCraft.Containers.ContainerPulseFurnace;
import Reika.RotaryCraft.Containers.ContainerPurifier;
import Reika.RotaryCraft.Containers.ContainerRemoteControl;
import Reika.RotaryCraft.Containers.ContainerReservoir;
import Reika.RotaryCraft.Containers.ContainerScaleChest;
import Reika.RotaryCraft.Containers.ContainerScreen;
import Reika.RotaryCraft.Containers.ContainerSorter;
import Reika.RotaryCraft.Containers.ContainerSteam;
import Reika.RotaryCraft.Containers.ContainerTerraformer;
import Reika.RotaryCraft.Containers.ContainerVacuum;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.Containers.ContainerWorldEdit;
import Reika.RotaryCraft.GUIs.GuiHandCraft;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.GUIs.GuiHandbookPage;
import Reika.RotaryCraft.GUIs.GuiSafePlayerList;
import Reika.RotaryCraft.GUIs.GuiSlide;
import Reika.RotaryCraft.GUIs.GuiWorldEdit;
import Reika.RotaryCraft.GUIs.Machine.GuiBevel;
import Reika.RotaryCraft.GUIs.Machine.GuiBlower;
import Reika.RotaryCraft.GUIs.Machine.GuiBorer;
import Reika.RotaryCraft.GUIs.Machine.GuiCoil;
import Reika.RotaryCraft.GUIs.Machine.GuiGPR;
import Reika.RotaryCraft.GUIs.Machine.GuiGearbox;
import Reika.RotaryCraft.GUIs.Machine.GuiJet;
import Reika.RotaryCraft.GUIs.Machine.GuiMobRadar;
import Reika.RotaryCraft.GUIs.Machine.GuiMultiClutch;
import Reika.RotaryCraft.GUIs.Machine.GuiMusic;
import Reika.RotaryCraft.GUIs.Machine.GuiPlayerDetector;
import Reika.RotaryCraft.GUIs.Machine.GuiRemoteControl;
import Reika.RotaryCraft.GUIs.Machine.GuiReservoir;
import Reika.RotaryCraft.GUIs.Machine.GuiSonic;
import Reika.RotaryCraft.GUIs.Machine.GuiSorter;
import Reika.RotaryCraft.GUIs.Machine.GuiSpawnerController;
import Reika.RotaryCraft.GUIs.Machine.GuiSplitter;
import Reika.RotaryCraft.GUIs.Machine.GuiSteam;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiAerosolizer;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiBigFurnace;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiBlastFurnace;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCCTVScreen;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCVT;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCannon;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCentrifuge;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCompactor;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiComposter;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCrystallizer;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiDefoliator;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiEthanol;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiExtractor;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFermenter;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFillingStation;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFractionator;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFridge;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiGrinder;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiHeater;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiItemCannon;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiLandmine;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiObsidian;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiParticle;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPerformance;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPowerBus;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiProjector;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPulseFurnace;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPurifier;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiScaleChest;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiSpyCam;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiTerraformer;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiVacuum;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiWinder;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiWorktable;
import Reika.RotaryCraft.ModInterface.ContainerEnergyToPower;
import Reika.RotaryCraft.ModInterface.GuiEnergyToPower;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityBlower;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySorting;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFillingStation;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityParticleEmitter;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityBigFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCentrifuge;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCrystallizer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityRefrigerator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntitySpyCam;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLandmine;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.World.TileEntityDefoliator;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final GuiHandler instance = new GuiHandler();
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id >= 24000) {
			return new ContainerScaleChest(player, (TileEntityScaleableChest)world.getTileEntity(x, y, z), id-24000);
		}
		GuiRegistry gr = GuiRegistry.getEntry(id);
		ItemStack is = player.getCurrentEquippedItem();
		if (!gr.hasContainer())
			return null;
		if (gr == GuiRegistry.HANDCRAFT)
			return new ContainerHandCraft(player, world);
		if (gr == GuiRegistry.WORLDEDIT)
			return new ContainerWorldEdit(player, world);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof RotaryCraftTileEntity))
			return null;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)tile;
		if (te instanceof TileEntityPulseFurnace) {
			return new ContainerPulseFurnace(player, (TileEntityPulseFurnace) te);
		}
		if (te instanceof TileEntityReservoir) {
			return new ContainerReservoir(player, (TileEntityReservoir) te);
		}
		if (te instanceof TileEntityAerosolizer) {
			return new ContainerAerosolizer(player, (TileEntityAerosolizer) te);
		}
		if (te instanceof TileEntityCompactor) {
			return new ContainerCompactor(player, (TileEntityCompactor) te);
		}
		if (te instanceof TileEntityEngine) {
			switch(((TileEntityEngine) te).getEngineType()) {
			case STEAM:
				return new ContainerSteam(player, (TileEntityEngine) te);
			case GAS:
				return new ContainerEthanol(player, (TileEntityEngine) te);
			case AC:
				return new OneSlotContainer(player, te);
			case SPORT:
				return new ContainerPerformance(player, (TileEntityPerformanceEngine) te);
			case MICRO:
			case JET:
				return new ContainerJet(player, (TileEntityEngine)te);
			default:
				return null;
			}
		}
		if (te instanceof TileEntityExtractor) {
			return new ContainerExtractor(player, (TileEntityExtractor) te);
		}
		if (te instanceof TileEntityFermenter) {
			return new ContainerFermenter(player, (TileEntityFermenter) te);
		}
		if (te instanceof TileEntityGearbox) {
			return new ContainerGearbox(player, (TileEntityGearbox) te);
		}
		if (te instanceof TileEntityGrinder) {
			return new ContainerGrinder(player, (TileEntityGrinder) te);
		}
		if (te instanceof TileEntityFractionator) {
			return new ContainerFractionator(player, (TileEntityFractionator) te);
		}
		if (te instanceof TileEntityVacuum) {
			return new ContainerVacuum(player, (TileEntityVacuum) te);
		}
		if (te instanceof TileEntityHeater) {
			return new ContainerHeater(player, te);
		}
		if (te instanceof TileEntityObsidianMaker) {
			return new ContainerObsidian(player, (TileEntityObsidianMaker) te);
		}
		if (te instanceof TileEntityAdvancedGear) {
			switch (((TileEntityAdvancedGear) te).getGearType()) {
			case COIL:
				return new CoreContainer(player, te);
			case CVT:
				return new ContainerCVT(player, (TileEntityAdvancedGear) te);
			default:
				return null;
			}
		}
		if (te instanceof TileEntityLaunchCannon) {
			return new ContainerCannon(player, (TileEntityLaunchCannon) te);
		}
		if (te instanceof TileEntityBlastFurnace) {
			return new ContainerBlastFurnace(player, (TileEntityBlastFurnace) te);
		}
		if (te instanceof TileEntityScaleableChest) {
			return new ContainerScaleChest(player, (TileEntityScaleableChest) te, 0);
		}
		if (te instanceof TileEntityProjector) {
			return new ContainerProjector(player, (TileEntityProjector) te);
		}
		if (te instanceof TileEntityScreen) {
			return new ContainerScreen(player, (TileEntityScreen)te);
		}
		if (te instanceof RemoteControlMachine) {
			return new ContainerRemoteControl(player, (RemoteControlMachine)te);
		}
		if (te instanceof TileEntityPurifier) {
			return new ContainerPurifier(player, (TileEntityPurifier)te);
		}
		if (te instanceof TileEntityItemCannon) {
			return new ContainerItemCannon(player, (TileEntityItemCannon)te);
		}
		if (te instanceof TileEntityWorktable) {
			return new ContainerWorktable(player, (TileEntityWorktable)te, world);
		}
		if (te instanceof TileEntityLandmine) {
			return new ContainerLandmine(player, (TileEntityLandmine)te);
		}
		if (te instanceof TileEntityTerraformer) {
			return new ContainerTerraformer(player, (TileEntityTerraformer)te);
		}
		if (te instanceof EnergyToPowerBase) {
			return new ContainerEnergyToPower(player, (EnergyToPowerBase)te);
		}
		if (te instanceof TileEntityFillingStation) {
			return new ContainerFillingStation(player, (TileEntityFillingStation) te);
		}
		if (te instanceof TileEntitySorting) {
			return new ContainerSorter(player, (TileEntitySorting) te);
		}
		if (te instanceof TileEntityBigFurnace) {
			return new ContainerBigFurnace(player, (TileEntityBigFurnace) te);
		}
		if (te instanceof TileEntityCrystallizer) {
			return new ContainerCrystallizer(player, (TileEntityCrystallizer) te);
		}
		if (te instanceof TileEntityPowerBus) {
			return new ContainerPowerBus(player, (TileEntityPowerBus) te);
		}
		if (te instanceof TileEntityParticleEmitter) {
			return new OneSlotContainer(player, te, 28);
		}
		if (te instanceof TileEntityBlower) {
			return new ContainerBlower(player, (TileEntityBlower) te);
		}
		if (te instanceof TileEntityDefoliator) {
			return new ContainerDefoliator(player, (TileEntityDefoliator) te);
		}
		if (te instanceof TileEntityRefrigerator) {
			return new ContainerFridge(player, (TileEntityRefrigerator)te);
		}
		if (te instanceof TileEntityComposter) {
			return new ContainerComposter(player, (TileEntityComposter)te);
		}
		if (te instanceof TileEntityCentrifuge) {
			return new ContainerCentrifuge(player, (TileEntityCentrifuge)te);
		}

		if (te instanceof OneSlotMachine)
			return new OneSlotContainer(player, te);
		if (te instanceof GuiController)
			return new CoreContainer(player, te);
		if (te instanceof IInventory && !(te instanceof InertIInv))
			return new ContainerBasicStorage(player, te);
		return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id >= 24000) {
			return new GuiScaleChest(player, (TileEntityScaleableChest)world.getTileEntity(x, y, z), id-24000);
		}
		GuiRegistry gr = GuiRegistry.getEntry(id);
		TileEntity te = world.getTileEntity(x, y, z);

		if (gr == GuiRegistry.HANDCRAFT)
			return new GuiHandCraft(player, world);
		if (gr == GuiRegistry.HANDBOOK)
			return new GuiHandbook(player, world, 0, 0);
		//return new GuiGuide();
		if (gr == GuiRegistry.LOADEDHANDBOOK) {
			MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
			return new GuiHandbook(player, world, HandbookRegistry.getScreen(m, te), HandbookRegistry.getPage(m, te));
		}
		if (gr == GuiRegistry.HANDBOOKPAGE) {
			MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
			return new GuiHandbookPage(player, world, HandbookRegistry.getScreen(m, te), HandbookRegistry.getPage(m, te));
		}
		if (gr == GuiRegistry.WORLDEDIT)
			return new GuiWorldEdit(player, world);
		if (gr == GuiRegistry.SAFEPLAYERS)
			return new GuiSafePlayerList(player, (TileEntityAimedCannon)te);
		if (gr == GuiRegistry.SPYCAM)
			return new GuiSpyCam(player, (TileEntitySpyCam)te);
		if (gr == GuiRegistry.SLIDE)
			return new GuiSlide(player.getCurrentEquippedItem());
		if (te instanceof TileEntityPulseFurnace) {
			return new GuiPulseFurnace(player, (TileEntityPulseFurnace) te);
		}
		if (te instanceof TileEntityReservoir) {
			return new GuiReservoir(player, (TileEntityReservoir) te);
		}
		if (te instanceof TileEntityAerosolizer) {
			return new GuiAerosolizer(player, (TileEntityAerosolizer) te);
		}
		if (te instanceof TileEntityBorer) {
			return new GuiBorer(player, (TileEntityBorer) te);
		}
		if (te instanceof TileEntityCompactor) {
			return new GuiCompactor(player, (TileEntityCompactor) te);
		}
		if (te instanceof TileEntityEngine) {
			switch(((TileEntityEngine) te).getEngineType()) {
			case STEAM:
				return new GuiSteam(player, (TileEntityEngine) te);
			case GAS:
				return new GuiEthanol(player, (TileEntityEngine) te);
			case AC:
				return new GuiOneSlotInv(player, new OneSlotContainer(player, te), (RotaryCraftTileEntity)te);
			case SPORT:
				return new GuiPerformance(player, (TileEntityPerformanceEngine) te);
			case MICRO:
			case JET:
				return new GuiJet(player, (TileEntityEngine) te);
			default:
				return null;
			}
		}
		if (te instanceof TileEntityExtractor) {
			return new GuiExtractor(player, (TileEntityExtractor) te);
		}
		if (te instanceof TileEntityFermenter) {
			return new GuiFermenter(player, (TileEntityFermenter) te);
		}
		if (te instanceof TileEntityGearbox) {
			return new GuiGearbox(player, (TileEntityGearbox) te);
		}
		if (te instanceof TileEntityBevelGear) {
			return new GuiBevel(player, (TileEntityBevelGear) te);
		}
		if (te instanceof TileEntityGrinder) {
			return new GuiGrinder(player, (TileEntityGrinder) te);
		}
		if (te instanceof TileEntityFractionator) {
			return new GuiFractionator(player, (TileEntityFractionator) te);
		}
		if (te instanceof TileEntityVacuum) {
			return new GuiVacuum(player, (TileEntityVacuum) te);
		}
		if (te instanceof TileEntityGPR) {
			return new GuiGPR(player, (TileEntityGPR) te);
		}
		if (te instanceof TileEntitySplitter) {
			return new GuiSplitter(player, (TileEntitySplitter) te);
		}
		if (te instanceof TileEntityHeater) {
			return new GuiHeater(player, (TileEntityHeater) te, (TileEntityHeater) te);
		}
		if (te instanceof TileEntityObsidianMaker) {
			return new GuiObsidian(player, (TileEntityObsidianMaker) te);
		}
		if (te instanceof TileEntitySpawnerController) {
			return new GuiSpawnerController(player, (TileEntitySpawnerController) te);
		}
		if (te instanceof TileEntityPlayerDetector) {
			return new GuiPlayerDetector(player, (TileEntityPlayerDetector) te);
		}
		if (te instanceof TileEntityAdvancedGear) {
			switch (((TileEntityAdvancedGear) te).getGearType()) {
			case COIL:
				return new GuiCoil(player, (TileEntityAdvancedGear) te);
			case CVT:
				return new GuiCVT(player, (TileEntityAdvancedGear) te);
			default:
				return null;
			}
		}
		if (te instanceof TileEntityMobRadar) {
			return new GuiMobRadar(player, (TileEntityMobRadar) te);
		}
		if (te instanceof TileEntityLaunchCannon) {
			return new GuiCannon(player, (TileEntityLaunchCannon) te);
		}
		if (te instanceof TileEntityBlastFurnace) {
			return new GuiBlastFurnace(player, (TileEntityBlastFurnace) te);
		}
		if (te instanceof TileEntitySonicWeapon) {
			return new GuiSonic(player, (TileEntitySonicWeapon) te);
		}
		if (te instanceof TileEntityForceField) {
			return new GuiBasicRange(player, (TileEntityForceField) te);
		}
		if (te instanceof TileEntityContainment) {
			return new GuiBasicRange(player, (TileEntityContainment) te);
		}
		if (te instanceof TileEntityScaleableChest) {
			return new GuiScaleChest(player, (TileEntityScaleableChest) te, 0);
		}
		if (te instanceof TileEntityMusicBox) {
			return new GuiMusic(player, (TileEntityMusicBox)te);
		}
		if (te instanceof TileEntityWinder) {
			return new GuiWinder(player, (TileEntityWinder)te);
		}
		if (te instanceof TileEntityProjector) {
			return new GuiProjector(player, (TileEntityProjector)te);
		}
		if (te instanceof TileEntityScreen) {
			return new GuiCCTVScreen(player, (TileEntityScreen)te);
		}
		if (te instanceof RemoteControlMachine) {
			return new GuiRemoteControl(player, (RemoteControlMachine)te);
		}
		if (te instanceof TileEntityPurifier) {
			return new GuiPurifier(player, (TileEntityPurifier)te);
		}
		if (te instanceof TileEntityItemCannon) {
			return new GuiItemCannon(player, (TileEntityItemCannon)te);
		}
		if (te instanceof TileEntityWorktable) {
			return new GuiWorktable(player, (TileEntityWorktable)te, world);
		}
		if (te instanceof TileEntityLandmine) {
			return new GuiLandmine(player, (TileEntityLandmine)te);
		}
		if (te instanceof TileEntitySpyCam) {
			return new GuiSpyCam(player, (TileEntitySpyCam)te);
		}
		if (te instanceof TileEntityMultiClutch) {
			return new GuiMultiClutch(player, (TileEntityMultiClutch)te);
		}
		if (te instanceof TileEntityTerraformer) {
			return new GuiTerraformer(player, (TileEntityTerraformer)te);
		}
		if (te instanceof EnergyToPowerBase) {
			return new GuiEnergyToPower(player, (EnergyToPowerBase)te);
		}
		if (te instanceof TileEntityFillingStation) {
			return new GuiFillingStation(player, (TileEntityFillingStation)te);
		}
		if (te instanceof TileEntitySorting) {
			return new GuiSorter(player, (TileEntitySorting) te);
		}
		if (te instanceof TileEntityBigFurnace) {
			return new GuiBigFurnace(player, (TileEntityBigFurnace) te);
		}
		if (te instanceof TileEntityCrystallizer) {
			return new GuiCrystallizer(player, (TileEntityCrystallizer) te);
		}
		if (te instanceof TileEntityPowerBus) {
			return new GuiPowerBus(player, (TileEntityPowerBus) te);
		}
		if (te instanceof TileEntityParticleEmitter) {
			return new GuiParticle(player, (TileEntityParticleEmitter) te);
		}
		if (te instanceof TileEntityBlower) {
			return new GuiBlower(player, (TileEntityBlower) te);
		}
		if (te instanceof TileEntityDefoliator) {
			return new GuiDefoliator(player, (TileEntityDefoliator) te);
		}
		if (te instanceof TileEntityRefrigerator) {
			return new GuiFridge(player, (TileEntityRefrigerator)te);
		}
		if (te instanceof TileEntityComposter) {
			return new GuiComposter(player, (TileEntityComposter)te);
		}
		if (te instanceof TileEntityCentrifuge) {
			return new GuiCentrifuge(player, (TileEntityCentrifuge)te);
		}

		if (te instanceof OneSlotMachine) {
			return new GuiOneSlotInv(player, new OneSlotContainer(player, te), (RotaryCraftTileEntity)te);
		}
		if (te instanceof IInventory && !(te instanceof InertIInv))
			return new GuiBasicStorage(player, (RotaryCraftTileEntity)te);
		return null;
	}
}