/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.TileEntityFan;
import Reika.RotaryCraft.TileEntities.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.TileEntityGearBevel;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPump;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;

public class ItemDebug extends ItemRotaryTool {

	public ItemDebug(int ID) {
		super(ID, 112);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int s, float par8, float par9, float par10) {
		ReikaChatHelper.clearChat();
		if (!player.isSneaking()) {
			ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te instanceof RotaryCraftTileEntity)
				ReikaChatHelper.write("Tile Entity Direction Data: "+(((RotaryCraftTileEntity)te).getBlockMetadata()+1)+" of "+((RotaryCraftTileEntity)te).getMachine().getNumberDirections());
			ReikaChatHelper.write("Additional Data (Meaning differs per machine):");
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.BEVELGEARS) {
			TileEntityGearBevel tile = (TileEntityGearBevel)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.direction));
			}
		}
		if (m == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace tile = (TileEntityBlastFurnace)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("Temperature: %dC", tile.temperature));
				if (player.isSneaking()) {
					tile.temperature = tile.MAXTEMP;
				}
			}
		}
		if (m == MachineRegistry.HOSE) {
			TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.lubricant));
			}
		}
		if (world.getBlockId(x, y, z) == Block.mobSpawner.blockID) {
			TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				MobSpawnerBaseLogic lgc = tile.func_98049_a();
				lgc.spawnDelay = 0;
			}
		}
		if (m == MachineRegistry.PIPE) {
			TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d  %d", tile.liquidID, tile.liquidLevel, tile.fluidPressure));
			}
		}
		if (m == MachineRegistry.PUMP) {
			TileEntityPump tile = (TileEntityPump)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.liquidID, tile.liquidLevel));
				if (player.isSneaking() && tile.liquidID != -1) {
					ReikaChatHelper.write("Filled to capacity.");
					tile.liquidLevel = tile.CAPACITY;
				}
			}
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.liquidID, tile.liquidLevel));
			}
		}
		if (m == MachineRegistry.EXTRACTOR) {
			TileEntityExtractor tile = (TileEntityExtractor)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.waterLevel));
			}
		}
		if (m == MachineRegistry.SPRINKLER) {
			TileEntitySprinkler tile = (TileEntitySprinkler)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.waterLevel, tile.waterPressure));
			}
		}
		if (m == MachineRegistry.OBSIDIAN) {
			TileEntityObsidianMaker tile = (TileEntityObsidianMaker)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d  %d", tile.waterLevel, tile.lavaLevel, tile.temperature));
			}
			if (player.isSneaking()) {
				tile.lavaLevel = tile.CAPACITY;
				tile.waterLevel = tile.CAPACITY;
				ReikaChatHelper.write("Filled to capacity.");
			}
		}
		if (m == MachineRegistry.TNTCANNON) {
			TileEntityTNTCannon tile = (TileEntityTNTCannon)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				if (player.isSneaking()) {
					if (tile.isCreative) {
						tile.isCreative = false;
						ReikaChatHelper.write("Set to default mode.");
					}
					else {
						tile.isCreative = true;
						ReikaChatHelper.write("Set to infinite-TNT mode.");
					}
				}
			}
		}
		if (m == MachineRegistry.PULSEJET) {
			TileEntityPulseFurnace tile = (TileEntityPulseFurnace)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.waterLevel, tile.temperature));
				if (player.isSneaking()) {
					tile.fuelLevel = tile.MAXFUEL;
					tile.waterLevel = tile.CAPACITY;
					ReikaChatHelper.write("Filled to capacity.");
				}
			}
		}
		if (m == MachineRegistry.FRACTIONATOR) {
			TileEntityFractionator tile = (TileEntityFractionator)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d", tile.fuel));
			}
		}
		if (m == MachineRegistry.FAN) {
			TileEntityFan tile = (TileEntityFan)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d %d %d", tile.xstep, tile.ystep, tile.zstep));
			}
		}
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine tile = (TileEntityEngine)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d  %d", tile.waterLevel, tile.temperature));
			}
			if (player.isSneaking()) {
				tile.jetfuels = tile.FUELCAP;
				tile.ethanols = tile.FUELCAP;
				tile.additives = tile.FUELCAP;
				tile.waterLevel = tile.CAPACITY;
				ReikaChatHelper.write("Filled to capacity.");
			}
		}
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft tile = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				ReikaChatHelper.write(String.format("%d %d %d %d", tile.readomega[0], tile.readomega[1], tile.readtorque[0], tile.readtorque[1]));
			}
		}
		if (m == MachineRegistry.GEARBOX) {
			TileEntityGearbox tile = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
			if (player.isSneaking()) {
				tile.damage = 0;
				tile.lubricant = tile.MAXLUBE;
				ReikaChatHelper.write("Filled to capacity.");
			}
		}

		return true;
	}
}
