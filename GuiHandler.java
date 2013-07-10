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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.ContainerBasicStorage;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Base.OneSlotContainer;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Instantiable.GuiStringBuilder;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.RotaryCraft.Base.GuiBasicRange;
import Reika.RotaryCraft.Base.GuiBasicStorage;
import Reika.RotaryCraft.Base.GuiOneSlotInv;
import Reika.RotaryCraft.Base.RemoteControlMachine;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntityLaunchCannon;
import Reika.RotaryCraft.Containers.ContainerAerosolizer;
import Reika.RotaryCraft.Containers.ContainerBlastFurnace;
import Reika.RotaryCraft.Containers.ContainerCVT;
import Reika.RotaryCraft.Containers.ContainerCannon;
import Reika.RotaryCraft.Containers.ContainerCompactor;
import Reika.RotaryCraft.Containers.ContainerEthanol;
import Reika.RotaryCraft.Containers.ContainerExtractor;
import Reika.RotaryCraft.Containers.ContainerFermenter;
import Reika.RotaryCraft.Containers.ContainerFractionator;
import Reika.RotaryCraft.Containers.ContainerGearbox;
import Reika.RotaryCraft.Containers.ContainerGrinder;
import Reika.RotaryCraft.Containers.ContainerHandCraft;
import Reika.RotaryCraft.Containers.ContainerHeater;
import Reika.RotaryCraft.Containers.ContainerItemCannon;
import Reika.RotaryCraft.Containers.ContainerJet;
import Reika.RotaryCraft.Containers.ContainerLandmine;
import Reika.RotaryCraft.Containers.ContainerObsidian;
import Reika.RotaryCraft.Containers.ContainerPerformance;
import Reika.RotaryCraft.Containers.ContainerProjector;
import Reika.RotaryCraft.Containers.ContainerPulseFurnace;
import Reika.RotaryCraft.Containers.ContainerPurifier;
import Reika.RotaryCraft.Containers.ContainerRemoteControl;
import Reika.RotaryCraft.Containers.ContainerReservoir;
import Reika.RotaryCraft.Containers.ContainerScaleChest;
import Reika.RotaryCraft.Containers.ContainerScreen;
import Reika.RotaryCraft.Containers.ContainerSteam;
import Reika.RotaryCraft.Containers.ContainerVacuum;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.Containers.ContainerWorldEdit;
import Reika.RotaryCraft.GUIs.GuiAerosolizer;
import Reika.RotaryCraft.GUIs.GuiBevel;
import Reika.RotaryCraft.GUIs.GuiBlastFurnace;
import Reika.RotaryCraft.GUIs.GuiBorer;
import Reika.RotaryCraft.GUIs.GuiCCTVScreen;
import Reika.RotaryCraft.GUIs.GuiCVT;
import Reika.RotaryCraft.GUIs.GuiCannon;
import Reika.RotaryCraft.GUIs.GuiCoil;
import Reika.RotaryCraft.GUIs.GuiCompactor;
import Reika.RotaryCraft.GUIs.GuiEthanol;
import Reika.RotaryCraft.GUIs.GuiExtractor;
import Reika.RotaryCraft.GUIs.GuiFermenter;
import Reika.RotaryCraft.GUIs.GuiFractionator;
import Reika.RotaryCraft.GUIs.GuiGPR;
import Reika.RotaryCraft.GUIs.GuiGearbox;
import Reika.RotaryCraft.GUIs.GuiGrinder;
import Reika.RotaryCraft.GUIs.GuiHandCraft;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.GUIs.GuiHandbookPage;
import Reika.RotaryCraft.GUIs.GuiHeater;
import Reika.RotaryCraft.GUIs.GuiItemCannon;
import Reika.RotaryCraft.GUIs.GuiJet;
import Reika.RotaryCraft.GUIs.GuiLandmine;
import Reika.RotaryCraft.GUIs.GuiMobRadar;
import Reika.RotaryCraft.GUIs.GuiMusic;
import Reika.RotaryCraft.GUIs.GuiObsidian;
import Reika.RotaryCraft.GUIs.GuiPerformance;
import Reika.RotaryCraft.GUIs.GuiPlayerDetector;
import Reika.RotaryCraft.GUIs.GuiProjector;
import Reika.RotaryCraft.GUIs.GuiPulseFurnace;
import Reika.RotaryCraft.GUIs.GuiPurifier;
import Reika.RotaryCraft.GUIs.GuiRemoteControl;
import Reika.RotaryCraft.GUIs.GuiReservoir;
import Reika.RotaryCraft.GUIs.GuiSafePlayerList;
import Reika.RotaryCraft.GUIs.GuiScaleChest;
import Reika.RotaryCraft.GUIs.GuiSonic;
import Reika.RotaryCraft.GUIs.GuiSpawnerController;
import Reika.RotaryCraft.GUIs.GuiSplitter;
import Reika.RotaryCraft.GUIs.GuiSpyCam;
import Reika.RotaryCraft.GUIs.GuiSteam;
import Reika.RotaryCraft.GUIs.GuiVacuum;
import Reika.RotaryCraft.GUIs.GuiWinder;
import Reika.RotaryCraft.GUIs.GuiWorktable;
import Reika.RotaryCraft.GUIs.GuiWorldEdit;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityDisplay;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.TileEntityGearBevel;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityLandmine;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.TileEntitySpyCam;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.TileEntityWorktable;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final GuiHandler instance = new GuiHandler();
	//returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiRegistry gr = GuiRegistry.getEntry(id);
		if (!gr.hasContainer())
			return null;
		if (gr == GuiRegistry.HANDCRAFT)
			return new ContainerHandCraft(player, world);
		if (gr == GuiRegistry.WORLDEDIT)
			return new ContainerWorldEdit(player, world);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
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
			switch(((TileEntityEngine) te).type) {
			case STEAM:
				return new ContainerSteam(player, (TileEntityEngine) te);
			case GAS:
				return new ContainerEthanol(player, (TileEntityEngine) te);
			case AC:
				return new OneSlotContainer(player, te);
			case SPORT:
				return new ContainerPerformance(player, (TileEntityEngine) te);
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
			if (((TileEntityAdvancedGear) te).coil) {
				return new CoreContainer(player, te);
			}
			else
				return new ContainerCVT(player, (TileEntityAdvancedGear) te);
		}
		if (te instanceof TileEntityLaunchCannon) {
			return new ContainerCannon(player, (TileEntityLaunchCannon) te);
		}
		if (te instanceof TileEntityBlastFurnace) {
			return new ContainerBlastFurnace(player, (TileEntityBlastFurnace) te);
		}
		if (te instanceof TileEntityScaleableChest) {
			return new ContainerScaleChest(player, (TileEntityScaleableChest) te);
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
			return new ContainerLandmine(player, te);
		}

		if (te instanceof OneSlotMachine)
			return new OneSlotContainer(player, te);
		if (te instanceof GuiController)
			return new CoreContainer(player, te);
		if (te instanceof IInventory)
			return new ContainerBasicStorage(player, te);
		return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiRegistry gr = GuiRegistry.getEntry(id);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (gr == GuiRegistry.HANDCRAFT)
			return new GuiHandCraft(player, world);
		if (gr == GuiRegistry.HANDBOOK)
			return new GuiHandbook(player, world, 0, 0);
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
			switch(((TileEntityEngine) te).type) {
			case STEAM:
				return new GuiSteam(player, (TileEntityEngine) te);
			case GAS:
				return new GuiEthanol(player, (TileEntityEngine) te);
			case AC:
				return new GuiOneSlotInv(player, new OneSlotContainer(player, te), (RotaryCraftTileEntity)te);
			case SPORT:
				return new GuiPerformance(player, (TileEntityEngine) te);
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
		if (te instanceof TileEntityGearBevel) {
			return new GuiBevel(player, (TileEntityGearBevel) te);
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
			if (((TileEntityAdvancedGear) te).coil) {
				return new GuiCoil(player, (TileEntityAdvancedGear) te);
			}
			else
				return new GuiCVT(player, (TileEntityAdvancedGear) te);
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
			return new GuiScaleChest(player, (TileEntityScaleableChest) te, (TileEntityScaleableChest) te);
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
		if (te instanceof TileEntityDisplay) {
			return new GuiStringBuilder(player, te.worldObj, te.xCoord, te.yCoord, te.zCoord, RotaryCraft.packetChannel, PacketRegistry.DISPLAY.getMinValue());
		}

		if (te instanceof OneSlotMachine) {
			return new GuiOneSlotInv(player, new OneSlotContainer(player, te), (RotaryCraftTileEntity)te);
		}
		if (te instanceof IInventory)
			return new GuiBasicStorage(player, (RotaryCraftTileEntity)te);
		return null;
	}
}
