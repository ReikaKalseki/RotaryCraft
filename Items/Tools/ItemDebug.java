/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityHydroEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFan;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHose;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public class ItemDebug extends ItemRotaryTool {

	public ItemDebug(int tex) {
		super(tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		if (super.onItemUse(is, ep, world, x, y, z, s, a, b, c))
			return true;
		//ReikaChatHelper.clearChat();
		if (!ep.isSneaking()) {
			ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof RotaryCraftTileEntity)
				ReikaChatHelper.write("Tile Entity Direction Data: "+(((RotaryCraftTileEntity)te).getBlockMetadata()+1)+" of "+((RotaryCraftTileEntity)te).getTile().getNumberDirections());
			else if (te instanceof TileEntityBase)
				ReikaChatHelper.write("Tile Entity Direction Data: "+(((TileEntityBase)te).getBlockMetadata()));
			ReikaChatHelper.write("Additional Data (Meaning differs per machine):");
		}
		TileEntity te = world.getTileEntity(x, y, z);
		if (ep.isSneaking() && te instanceof TileEntitySpringPowered) {
			TileEntitySpringPowered sp = (TileEntitySpringPowered)te;
			sp.isCreativeMode = !sp.isCreativeMode;
			return true;
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.BEVELGEARS) {
			TileEntityBevelGear tile = (TileEntityBevelGear)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.direction));
			}
		}
		if (m == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace tile = (TileEntityBlastFurnace)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("Temperature: %dC", tile.getTemperature()));
				if (ep.isSneaking()) {
					tile.addTemperature(tile.MAXTEMP-tile.getTemperature());
				}
			}
		}
		if (m == MachineRegistry.BELT) {
			TileEntityBeltHub tile = (TileEntityBeltHub)te;
			if (tile != null) {
				ReikaChatHelper.write(tile.getDistanceToTarget()+" @ "+tile.getBeltDirection());
			}
		}
		if (m == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.getFluidLevel()));
			}
		}
		if (world.getBlock(x, y, z) == Blocks.mob_spawner) {
			TileEntityMobSpawner tile = (TileEntityMobSpawner)te;
			if (tile != null) {
				MobSpawnerBaseLogic lgc = tile.func_145881_a();
				lgc.spawnDelay = 0;
			}
		}
		if (m != null && m.isStandardPipe()) {
			TileEntityPipe tile = (TileEntityPipe)te;
			if (tile != null) {
				if (tile.getFluidType() != null)
					ReikaChatHelper.write(String.format("%s  %d  %d", tile.getFluidType().getLocalizedName(), tile.getFluidLevel(), tile.getPressure()));
				else
					ReikaChatHelper.write("Pipe is empty.");
			}
		}
		if (m == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%s  %d", tile.getLevel() <= 0 ? 0 : tile.getLiquid().getLocalizedName(), tile.getLevel()));
			}
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)te;
			if (ep.isSneaking())
				tile.isCreative = !tile.isCreative;
			else
				if (tile != null && !tile.isEmpty()) {
					ReikaChatHelper.write(String.format("%s  %d", tile.getFluid().getLocalizedName(), tile.getLevel()));
				}
		}
		if (m == MachineRegistry.EXTRACTOR) {
			TileEntityExtractor tile = (TileEntityExtractor)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.getLevel()));
			}
		}
		if (m == MachineRegistry.SPRINKLER) {
			TileEntitySprinkler tile = (TileEntitySprinkler)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.getWater(), tile.getPressure()));
			}
		}
		if (m == MachineRegistry.OBSIDIAN) {
			TileEntityObsidianMaker tile = (TileEntityObsidianMaker)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d  %d", tile.getWater(), tile.getLava(), tile.temperature));
			}
			if (ep.isSneaking()) {
				tile.setLava(tile.CAPACITY);
				tile.setWater(tile.CAPACITY);
				ReikaChatHelper.write("Filled to capacity.");
			}
		}
		if (m == MachineRegistry.PULSEJET) {
			TileEntityPulseFurnace tile = (TileEntityPulseFurnace)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d  %d", tile.getWater(), tile.temperature, tile.getFuel()));
				if (ep.isSneaking()) {
					tile.addFuel(tile.MAXFUEL);
					tile.addWater(tile.CAPACITY);
					ReikaChatHelper.write("Filled to capacity.");
				}
			}
		}
		if (m == MachineRegistry.FRACTIONATOR) {
			TileEntityFractionator tile = (TileEntityFractionator)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.getFuelLevel()));
			}
		}
		if (m == MachineRegistry.FAN) {
			TileEntityFan tile = (TileEntityFan)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%s", tile.getFacing().toString()));
			}
		}
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine tile = (TileEntityEngine)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.getWater(), tile.temperature));
			}
			if (ep.isSneaking()) {
				tile.addFuel(tile.FUELCAP);
				if (tile instanceof TileEntityPerformanceEngine)
					((TileEntityPerformanceEngine)tile).additives = tile.FUELCAP/1000;
				if (tile instanceof TileEntityHydroEngine)
					((TileEntityHydroEngine)tile).addLubricant(50000);
				tile.addWater(tile.CAPACITY);
				ReikaChatHelper.write("Filled to capacity.");
				tile.omega = tile.getEngineType().getSpeed();
			}
		}
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft tile = (TileEntityShaft)te;
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d %d %d %d", tile.readomega[0], tile.readomega[1], tile.readtorque[0], tile.readtorque[1]));
			}
		}
		if (m == MachineRegistry.GEARBOX) {
			TileEntityGearbox tile = (TileEntityGearbox)te;
			if (ep.isSneaking()) {
				tile.repair(Integer.MAX_VALUE);
				tile.fillWithLubricant();
				ReikaChatHelper.write("Filled to capacity.");
			}
		}

		return true;
	}
}
