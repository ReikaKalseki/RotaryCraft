/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import Reika.RotaryCraft.Auxiliary.EnumEngineType;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.TileEntityBedrockBreaker;
import Reika.RotaryCraft.TileEntities.TileEntityBridgeEmitter;
import Reika.RotaryCraft.TileEntities.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.TileEntityClutch;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.TileEntityFan;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.TileEntityFreezeGun;
import Reika.RotaryCraft.TileEntities.TileEntityGearBevel;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityLaserGun;
import Reika.RotaryCraft.TileEntities.TileEntityMagnetizer;
import Reika.RotaryCraft.TileEntities.TileEntityMobHarvester;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.TileEntityMonitor;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPileDriver;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPump;
import Reika.RotaryCraft.TileEntities.TileEntityRailGun;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.TileEntitySmokeDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWeatherController;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.TileEntityWoodcutter;

public class ItemMachineRenderer implements IItemRenderer {

	private int Renderid;
	private int metadata;

	public ItemMachineRenderer() {

	}

	public ItemMachineRenderer(int id) {
		Renderid = id;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		//return MachineRegistry.machineList[item.getItemDamage()].hasModel();
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		//return MachineRegistry.machineList[item.getItemDamage()].hasModel();
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (Renderid == -1) {
			RenderBlocks rb = new RenderBlocks();
			//ModLoader.getMinecraftInstance().renderEngine.bindTexture("/terrain.png");
			rb.renderBlockAsItem(Block.lockedChest, 0, 1);
			return;
		}
		float a = 0; float b = 0;
		if (item.itemID == RotaryCraft.engineitems.itemID) {
			TileEntityEngine eng = new TileEntityEngine();
			eng.type = EnumEngineType.DC;
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(eng, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.gbxitems.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(new TileEntityGearbox(), a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.advgearitems.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(new TileEntityAdvancedGear(), a, -0.1D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.flywheelitems.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F; b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFlywheel(), a, 0.0D, b, 500-1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.shaftitems.itemID) {
			if (type == type.ENTITY) {
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				a = -0.5F; b = -0.5F;
			}
			if (item.getItemDamage() == RotaryNames.shaftItemNames.length-1)
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityShaft(), a, 0.0D, b, -10000F);
			else
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityShaft(), a, 0.0D, b, -1000F*(item.getItemDamage()+1));
		}
		else if (item.itemID == RotaryCraft.machineplacer.itemID) {
			if (type == type.ENTITY) {
				a = -0.5F;
				b = -0.5F;
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			}
			switch (MachineRegistry.machineList[item.getItemDamage()]) {
			case ENGINE:
				TileEntityEngine eng = new TileEntityEngine();
				eng.type = EnumEngineType.DC;
				TileEntityRenderer.instance.renderTileEntityAt(eng, a, 0.0D, b, -1000F*(item.getItemDamage()+1));
				break;
			case AEROSOLIZER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityAerosolizer(), a, 0.0D, b, 0.0F);
				break;
			case BEVELGEARS:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityGearBevel(), a, -0.1D, b, 0.0F);
				break;
			case LIGHTBRIDGE:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityBridgeEmitter(), a, 0.0D, b, 0.0F);
				break;
			case CLUTCH:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityClutch(), a, 0.0D, b, 0.0F);
				break;
			case COMPACTOR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityCompactor(), a, -0.1D, b, 0.0F);
				break;
			case EXTRACTOR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityExtractor(), a, 0.0D, b, 0.0F);
				break;
			case FAN:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFan(), a, -0.1D, b, 0.0F);
				break;
			case FLYWHEEL:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFlywheel(), a, 0.0D, b, -250F*(item.getItemDamage()+1));
				break;
			case GEARBOX:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityGearbox(), a, 0.0D, b, -1000F*(item.getItemDamage()+1));
				break;
			case HEATRAY:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityHeatRay(), a, 0.0D, b, 0.0F);
				break;
			case FLOODLIGHT:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFloodlight(), a, 0.0D, b, 0.0F);
				break;
			case PULSEJET:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityPulseFurnace(), a, 0.0D, b, 0.0F);
				break;
			case PUMP:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityPump(), a, 0.0D, b, 0.0F);
				break;
			case RESERVOIR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityReservoir(), a, -0.1D, b, 0.0F);
				break;
			case SHAFT:
				if (item.getItemDamage() == 6)
					TileEntityRenderer.instance.renderTileEntityAt(new TileEntityShaft(), a, 0.0D, b, -10000F);
				else
					TileEntityRenderer.instance.renderTileEntityAt(new TileEntityShaft(), a, 0.0D, b, -1000F*(item.getItemDamage()+1));
				break;
			case FRACTIONATOR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFractionator(), a, 0.0D, b, 0.0F);
				break;
			case VACUUM:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityVacuum(), a, -0.1D, b, 0.0F);
				break;
			case PILEDRIVER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityPileDriver(), a, -0.1D, b, 0.0F);
				break;
			case SPLITTER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntitySplitter(), a, -0.1D, b, 0.0F);
				break;
			case SPRINKLER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntitySprinkler(), a, -0.1D, b, 0.0F);
				break;
			case GRINDER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityGrinder(), a, -0.1D, b, 0.0F);
				break;
			case WOODCUTTER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityWoodcutter(), a, -0.1D, b, 0.0F);
				break;
			case OBSIDIAN:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityObsidianMaker(), a, -0.1D, b, 0.0F);
				break;
			case SPAWNERCONTROLLER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntitySpawnerController(), a, -0.1D, b, 0.0F);
				break;
			case PLAYERDETECTOR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityPlayerDetector(), a, -0.1D, b, 0.0F);
				break;
			case DYNAMOMETER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityMonitor(), a, -0.1D, b, 0.0F);
				break;
			case HEATER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityHeater(), a, -0.1D, b, 0.0F);
				break;
			case AUTOBREEDER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityAutoBreeder(), a, -0.1D, b, 0.0F);
				break;
			case BAITBOX:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityBaitBox(), a, -0.1D, b, 0.0F);
				break;
			case BEDROCKBREAKER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityBedrockBreaker(), a, -0.1D, b, 0.0F);
				break;
			case ADVANCEDGEARS:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityAdvancedGear(), a, -0.1D, b, -1000F*(item.getItemDamage()+1));
				break;
			case TNTCANNON:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityTNTCannon(), a, -0.1D, b, 0.0F);
				break;
			case WINDER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityWinder(), a, -0.1D, b, 0.0F);
				break;
			case SMOKEDETECTOR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntitySmokeDetector(), a, -0.1D, b, 0.0F);
				break;
			case SONICWEAPON:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntitySonicWeapon(), a, -0.1D, b, 0.0F);
				break;
			case FORCEFIELD:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityForceField(), a, -0.1D, b, 0.0F);
				break;
			case MOBHARVESTER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityMobHarvester(), a, -0.1D, b, 0.0F);
				break;
			case RAILGUN:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityRailGun(), a, -0.1D, b, 0.0F);
				break;
			case MOBRADAR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityMobRadar(), a, -0.1D, b, 0.0F);
				break;
			case PROJECTOR:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityProjector(), a, -0.1D, b, 0.0F);
				break;
			case WEATHERCONTROLLER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityWeatherController(), a, -0.1D, b, 0.0F);
				break;
			case SCALECHEST:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityScaleableChest(), a, -0.1D, b, 0.0F);
				break;
			case CAVESCANNER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityCaveFinder(), a, -0.1D, b, 0.0F);
				break;
			case CCTV:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityCCTV(), a, -0.1D, b, 0.0F);
				break;
			case MAGNETIZER:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityMagnetizer(), a, -0.1D, b, 0.0F);
				break;
			case CONTAINMENT:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityContainment(), a, -0.1D, b, 0.0F);
				break;
			case FREEZEGUN:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFreezeGun(), a, -0.1D, b, 0.0F);
				break;
			case SCREEN:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityScreen(), a, -0.1D, b, 0.0F);
				break;
			case LASERGUN:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityLaserGun(), a, -0.1D, b, 0.0F);
				break;
			case ITEMCANNON:
				TileEntityRenderer.instance.renderTileEntityAt(new TileEntityItemCannon(), a, -0.1D, b, 0.0F);
				break;
			default:
				RenderBlocks rb = new RenderBlocks();
				Minecraft.getMinecraft().renderEngine.bindTexture("/terrain.png");
				rb.renderBlockAsItem(MachineRegistry.machineList[item.getItemDamage()].getBlockVariable(), MachineRegistry.machineList[item.getItemDamage()].getMachineMetadata(), 1);
				break;
			}
		}
	}
}
