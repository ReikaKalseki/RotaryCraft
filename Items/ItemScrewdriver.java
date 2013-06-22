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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;


public class ItemScrewdriver extends ItemRotaryTool
{
	public static byte[] maxdamage = new byte[4096]; //Max damage values (or tileentity datas) for the block ids associated


	public ItemScrewdriver(int ID, int tex) {
		super(ID, tex);
	}

	public static void setmaxdmgs() {
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
		this.setmaxdmgs();
		int damage = 0;
		TileEntity teb = world.getBlockTileEntity(x, y, z);
		if (teb instanceof RotaryCraftTileEntity) {
			RotaryCraftTileEntity t = (RotaryCraftTileEntity)teb;
			damage = t.getBlockMetadata();
		}
		if (teb instanceof TileEntityIOMachine) {
			((TileEntityIOMachine)teb).iotick = 512;
			world.markBlockForUpdate(x, y, z);
		}
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null) {
			if (m == MachineRegistry.ENGINE) {
				TileEntityEngine clicked = (TileEntityEngine)world.getBlockTileEntity(x, y, z);
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
				TileEntityFlywheel clicked = (TileEntityFlywheel)world.getBlockTileEntity(x, y, z);
				if (damage != 3 && damage != 7 && damage != 11 && damage != 15)
					clicked.setBlockMetadata(damage+1);
				else
					clicked.setBlockMetadata(damage-3);
				return true;
			}
			if (m == MachineRegistry.ADVANCEDGEARS) {
				TileEntityAdvancedGear clicked = (TileEntityAdvancedGear)world.getBlockTileEntity(x, y, z);
				if (damage != 3 && damage != 7 && damage != 11)
					clicked.setBlockMetadata(damage+1);
				else
					clicked.setBlockMetadata(damage-3);
				return true;
			}
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft ts = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
				MaterialRegistry type = ts.type;
				if (damage < 5)
					ts.setBlockMetadata(damage+1);
				if (damage == 5)
					ts.setBlockMetadata(0);
				if (damage > 5 && damage < 9)
					ts.setBlockMetadata(damage+1);
				if (damage == 9)
					ts.setBlockMetadata(6);
				TileEntityShaft ts1 = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
				ts1.type = type;
				return true;
			}
			if (m == MachineRegistry.FLOODLIGHT) {
				if (ep.isSneaking()) {
					TileEntityFloodlight clicked = (TileEntityFloodlight)world.getBlockTileEntity(x, y, z);
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
					TileEntityAimedCannon clicked = (TileEntityAimedCannon)world.getBlockTileEntity(x, y, z);
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
				TileEntityTNTCannon clicked = (TileEntityTNTCannon)world.getBlockTileEntity(x, y, z);
				if (clicked != null) {
					if (clicked.targetMode)
						clicked.targetMode = false;
					else
						clicked.targetMode = true;
					return true;
				}
			}
			if (m == MachineRegistry.BUCKETFILLER) {
				TileEntityBucketFiller clicked = (TileEntityBucketFiller)world.getBlockTileEntity(x, y, z);
				if (clicked != null) {
					if (clicked.filling)
						clicked.filling = false;
					else
						clicked.filling = true;
					return true;
				}
			}
			if (m == MachineRegistry.GPR) {
				TileEntityGPR clicked = (TileEntityGPR)world.getBlockTileEntity(x, y, z);
				if (clicked != null) {
					if (clicked.xdir)
						clicked.xdir = false;
					else
						clicked.xdir = true;
					return true;
				}
			}
			if (m == MachineRegistry.CCTV) {
				TileEntityCCTV clicked = (TileEntityCCTV)world.getBlockTileEntity(x, y, z);
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
					TileEntityGearbox clicked = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
					if (clicked.reduction)
						clicked.reduction = false;
					else
						clicked.reduction = true;

				}
				else {
					TileEntityGearbox clicked = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
					if (damage != 3 && damage != 7 && damage != 11 && damage != 15)
						clicked.setBlockMetadata(damage+1);
					else
						clicked.setBlockMetadata(damage-3);
					//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", world.getBlockMetadata(x, y, z)));
				}
				return true;
			}
			if (m == MachineRegistry.SPLITTER && (!ep.isSneaking())) {
				TileEntitySplitter clicked = (TileEntitySplitter)teb;
				if (damage < 7 || (damage < 15 && damage > 7))
					clicked.setBlockMetadata(damage+1);
				if (damage == 7)
					clicked.setBlockMetadata(0);
				if (damage == 15)
					clicked.setBlockMetadata(8);
				return true;
			}
			if (m == MachineRegistry.SPLITTER && (ep.isSneaking())) {	// Toggle in/out
				TileEntitySplitter clicked = (TileEntitySplitter)teb;
				if (damage < 8)
					clicked.setBlockMetadata(damage+8);
				else
					clicked.setBlockMetadata(damage-8);
				return true;
			}
			int max = m.getNumberDirections()-1;
			if (m.hasModel()) {
				RotaryCraftTileEntity t = (RotaryCraftTileEntity)teb;
				int meta = t.getBlockMetadata();
				if (meta < max)
					t.setBlockMetadata(meta+1);
				else
					t.setBlockMetadata(0);
			}
			else {
				int meta = world.getBlockMetadata(x, y, z);
				if (meta-m.getMachineMetadata() < max)
					ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, meta+1);
				else
					ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, m.getMachineMetadata());
			}
		}
		else {
			int id = world.getBlockId(x, y, z);
			if (damage < maxdamage[id] && maxdamage[id] != -1)
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, damage+1);
			else if (maxdamage[id] != -1)
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 0);
		}
		return true;
	}

	public int setBevel(int dir) {
		if (dir < 4)
			return dir;
		if (dir >= 8 && dir <= 15)
			return dir-4;
		switch (dir) {
		case 4:
			return 3;
		case 5:
			return 0;
		case 6:
			return 1;
		case 7:
			return 2;
		case 16:
			return 8;
		case 17:
			return 9;
		case 18:
			return 10;
		case 19:
			return 11;
		case 20:
			return 4;
		case 21:
			return 5;
		case 22:
			return 6;
		case 23:
			return 7;
		}
		return 0;
	}
}
