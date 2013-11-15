/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.ShaftMachine;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;
import buildcraft.api.tools.IToolWrench;


public class ItemScrewdriver extends ItemRotaryTool implements IToolWrench
{
	public static byte[] maxdamage = new byte[4096]; //Max damage values (or tileentity datas) for the block ids associated


	public ItemScrewdriver(int ID, int tex) {
		super(ID, tex);
	}

	static {
		for (int i = 0; i < maxdamage.length; i++)
			maxdamage[i] = -1;

		maxdamage[Block.pistonBase.blockID] = 5;
		maxdamage[Block.pistonStickyBase.blockID] = 5;
		maxdamage[Block.dispenser.blockID] = 5;
		maxdamage[Block.furnaceIdle.blockID] = 3;
		maxdamage[Block.stairsWoodOak.blockID] = 7;
		maxdamage[Block.stairsCobblestone.blockID] = 7;
		maxdamage[Block.stairsBrick.blockID] = 7;
		maxdamage[Block.stairsStoneBrick.blockID] = 7;
		maxdamage[Block.stairsSandStone.blockID] = 7;
		maxdamage[Block.stairsWoodSpruce.blockID] = 7;
		maxdamage[Block.stairsWoodBirch.blockID] = 7;
		maxdamage[Block.stairsWoodJungle.blockID] = 7;
		maxdamage[Block.stairsNetherBrick.blockID] = 7;
		maxdamage[Block.stairsNetherQuartz.blockID] = 7;
		maxdamage[Block.dropper.blockID] = 3;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float par8, float par9, float par10)
	{
		int damage = 0;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof RotaryCraftTileEntity) {
			RotaryCraftTileEntity t = (RotaryCraftTileEntity)te;
			damage = t.getBlockMetadata();
		}
		if (te instanceof TileEntityIOMachine) {
			((TileEntityIOMachine)te).iotick = 512;
			world.markBlockForUpdate(x, y, z);
		}
		if (te instanceof ShaftMachine) {
			ShaftMachine sm = (ShaftMachine)te;
			sm.setIORenderAlpha(512);
			world.markBlockForUpdate(x, y, z);
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null) {
			if (m == MachineRegistry.ENGINE) {
				TileEntityEngine clicked = (TileEntityEngine)te;
				int dmg = damage;
				while (damage > 3)
					damage -= 4;
				if (damage == 3)
					clicked.setBlockMetadata(dmg-3);
				else
					clicked.setBlockMetadata(dmg+1);
				return true;
			}
			if (m == MachineRegistry.FLYWHEEL) {
				TileEntityFlywheel clicked = (TileEntityFlywheel)te;
				if (damage != 3 && damage != 7 && damage != 11 && damage != 15)
					clicked.setBlockMetadata(damage+1);
				else
					clicked.setBlockMetadata(damage-3);
				return true;
			}
			if (m == MachineRegistry.COOLINGFIN) {
				TileEntityCoolingFin clicked = (TileEntityCoolingFin)te;
				clicked.ticks = 512;
			}
			if (m == MachineRegistry.ECU) {
				TileEntityEngineController clicked = (TileEntityEngineController)te;
				clicked.increment();
				ReikaChatHelper.writeString(String.format("ECU set to %.2f%s speed.", 100D*clicked.getSpeedMultiplier(), "%%"));
			}
			if (m == MachineRegistry.ADVANCEDGEARS) {
				TileEntityAdvancedGear clicked = (TileEntityAdvancedGear)te;
				if (damage != 3 && damage != 7 && damage != 11)
					clicked.setBlockMetadata(damage+1);
				else
					clicked.setBlockMetadata(damage-3);
				return true;
			}
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft ts = (TileEntityShaft)te;
				MaterialRegistry type = ts.type;
				if (damage < 5)
					ts.setBlockMetadata(damage+1);
				if (damage == 5)
					ts.setBlockMetadata(0);
				if (damage > 5 && damage < 9)
					ts.setBlockMetadata(damage+1);
				if (damage == 9)
					ts.setBlockMetadata(6);
				TileEntityShaft ts1 = (TileEntityShaft)te;
				ts1.type = type;
				return true;
			}
			if (m == MachineRegistry.FLOODLIGHT) {
				if (ep.isSneaking()) {
					TileEntityFloodlight clicked = (TileEntityFloodlight)te;
					if (clicked != null) {
						if (clicked.beammode)
							clicked.beammode = false;
						else
							clicked.beammode = true;
						clicked.lightsOut(world, x, y, z);
						return true;
					}
				}
			}
			if (m.isCannon()) {
				if (ep.isSneaking()) {
					TileEntityAimedCannon clicked = (TileEntityAimedCannon)te;
					if (clicked != null) {
						if (clicked.targetPlayers)
							clicked.targetPlayers = false;
						else
							clicked.targetPlayers = true;
						return true;
					}
				}
			}
			if (m == MachineRegistry.TNTCANNON) {
				TileEntityTNTCannon clicked = (TileEntityTNTCannon)te;
				if (clicked != null) {
					if (clicked.targetMode)
						clicked.targetMode = false;
					else
						clicked.targetMode = true;
					return true;
				}
			}
			if (m == MachineRegistry.BUCKETFILLER) {
				TileEntityBucketFiller clicked = (TileEntityBucketFiller)te;
				if (clicked != null) {
					if (clicked.filling)
						clicked.filling = false;
					else
						clicked.filling = true;
					return true;
				}
			}
			if (m == MachineRegistry.BELT) {
				if (ep.isSneaking()) {
					TileEntityBeltHub clicked = (TileEntityBeltHub)te;
					if (clicked != null) {
						clicked.setEmitting(!clicked.isEmitting());
						return true;
					}
				}
			}
			if (m == MachineRegistry.GPR) {
				TileEntityGPR clicked = (TileEntityGPR)te;
				if (clicked != null) {
					if (clicked.xdir)
						clicked.xdir = false;
					else
						clicked.xdir = true;
					return true;
				}
			}
			if (m == MachineRegistry.CCTV) {
				TileEntityCCTV clicked = (TileEntityCCTV)te;
				if (ep.isSneaking()) {
					clicked.theta -= 5;

				}
				else {
					clicked.phi += 5;
				}
				return true;
			}
			if (m == MachineRegistry.GEARBOX) {
				if (ep.isSneaking()) {
					TileEntityGearbox clicked = (TileEntityGearbox)te;
					if (clicked.reduction)
						clicked.reduction = false;
					else
						clicked.reduction = true;

				}
				else {
					TileEntityGearbox clicked = (TileEntityGearbox)te;
					if (damage != 3 && damage != 7 && damage != 11 && damage != 15)
						clicked.setBlockMetadata(damage+1);
					else
						clicked.setBlockMetadata(damage-3);
					//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", world.getBlockMetadata(x, y, z)));
				}
				return true;
			}
			if (m == MachineRegistry.SPLITTER && (!ep.isSneaking())) {
				TileEntitySplitter clicked = (TileEntitySplitter)te;
				if (damage < 7 || (damage < 15 && damage > 7))
					clicked.setBlockMetadata(damage+1);
				if (damage == 7)
					clicked.setBlockMetadata(0);
				if (damage == 15)
					clicked.setBlockMetadata(8);
				return true;
			}
			if (m == MachineRegistry.SPLITTER && (ep.isSneaking())) {	// Toggle in/out
				TileEntitySplitter clicked = (TileEntitySplitter)te;
				if (damage < 8)
					clicked.setBlockMetadata(damage+8);
				else
					clicked.setBlockMetadata(damage-8);
				return true;
			}
			int max = m.getNumberDirections()-1;
			RotaryCraftTileEntity t = (RotaryCraftTileEntity)te;
			int meta = t.getBlockMetadata();
			if (meta < max)
				t.setBlockMetadata(meta+1);
			else
				t.setBlockMetadata(0);
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
		}
		else {
			int id = world.getBlockId(x, y, z);
			damage = world.getBlockMetadata(x, y, z);
			if (damage < maxdamage[id] && maxdamage[id] != -1)
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, damage+1);
			else if (maxdamage[id] != -1)
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 0);
		}
		return true;
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {

	}
}
