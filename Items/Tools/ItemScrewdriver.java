/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Screwdriverable;
import Reika.RotaryCraft.API.ShaftMachine;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;
import Reika.RotaryCraft.TileEntities.World.TileEntityFloodlight;

import java.util.HashMap;

import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import binnie.extratrees.api.IToolHammer;
import buildcraft.api.tools.IToolWrench;


public class ItemScrewdriver extends ItemRotaryTool implements IToolWrench, IScrewdriver, IToolHammer, powercrystals.minefactoryreloaded.api.IToolHammer
{
	public static HashMap<Block, Integer> maxdamage = new HashMap(); //Max damage values (or tileentity datas) for the block ids associated


	public ItemScrewdriver(int tex) {
		super(tex);
	}

	static {
		maxdamage.put(Blocks.piston, 5);
		maxdamage.put(Blocks.sticky_piston, 5);
		maxdamage.put(Blocks.dispenser, 5);
		maxdamage.put(Blocks.furnace, 3);
		maxdamage.put(Blocks.lit_furnace, 3);
		maxdamage.put(Blocks.oak_stairs, 7);
		maxdamage.put(Blocks.stone_stairs, 7);
		maxdamage.put(Blocks.brick_stairs, 7);
		maxdamage.put(Blocks.stone_brick_stairs, 7);
		maxdamage.put(Blocks.sandstone_stairs, 7);
		maxdamage.put(Blocks.spruce_stairs, 7);
		maxdamage.put(Blocks.birch_stairs, 7);
		maxdamage.put(Blocks.jungle_stairs, 7);
		maxdamage.put(Blocks.nether_brick_stairs, 7);
		maxdamage.put(Blocks.quartz_stairs, 7);
		maxdamage.put(Blocks.dropper, 5);
		maxdamage.put(Blocks.lit_pumpkin, 3);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float par8, float par9, float par10)
	{
		int damage = 0;
		TileEntity te = world.getTileEntity(x, y, z);
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
		if (te instanceof Screwdriverable) {
			Screwdriverable sc = (Screwdriverable)te;
			boolean flag = false;
			if (ep.isSneaking())
				flag = sc.onShiftRightClick(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[s]);
			else
				flag = sc.onRightClick(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[s]);
			if (flag)
				return true;
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
				if (ep.isSneaking()) {
					clicked.redstoneMode = !clicked.redstoneMode;
					ReikaChatHelper.writeString(clicked.redstoneMode ? "ECU is now redstone-operated." : "ECU is now manually controlled.");
				}
				else {
					clicked.increment();
					ReikaChatHelper.writeString(String.format("ECU set to %.2f%s speed.", 100D*clicked.getSpeedMultiplier(), "%%"));
				}
			}
			if (m == MachineRegistry.ADVANCEDGEARS) {
				TileEntityAdvancedGear clicked = (TileEntityAdvancedGear)te;
				if (ep.isSneaking()) {
					clicked.torquemode = !clicked.torquemode;
				}
				else {
					if (damage != 3 && damage != 7 && damage != 11 && damage != 15)
						clicked.setBlockMetadata(damage+1);
					else
						clicked.setBlockMetadata(damage-3);
				}
				return true;
			}/*
			if (m == MachineRegistry.HYDRAULIC) {
				TileEntityHydraulicPump clicked = (TileEntityHydraulicPump)te;
				if (damage != 5 && damage != 11)
					clicked.setBlockMetadata(damage+1);
				else
					clicked.setBlockMetadata(damage-5);
				return true;
			}*/
			if (m == MachineRegistry.SHAFT) {
				TileEntityShaft ts = (TileEntityShaft)te;
				MaterialRegistry type = ts.getShaftType();
				if (damage < 5)
					ts.setBlockMetadata(damage+1);
				if (damage == 5)
					ts.setBlockMetadata(0);
				if (damage > 5 && damage < 9)
					ts.setBlockMetadata(damage+1);
				if (damage == 9)
					ts.setBlockMetadata(6);
				TileEntityShaft ts1 = (TileEntityShaft)te;
				//ts1.type = type;
				return true;
			}
			if (m == MachineRegistry.FLOODLIGHT) {
				if (ep.isSneaking()) {
					TileEntityFloodlight clicked = (TileEntityFloodlight)te;
					if (clicked != null && clicked.getBlockMetadata() >= 4) {
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
			if (m == MachineRegistry.BELT || m == MachineRegistry.CHAIN) {
				TileEntityBeltHub clicked = (TileEntityBeltHub)te;
				if (ep.isSneaking()) {
					if (clicked != null) {
						clicked.isEmitting = !clicked.isEmitting;
					}
				}
				else {
					int newdmg = damage < 11 ? damage+1 : 0;
					clicked.setBlockMetadata(newdmg);
					clicked.reset();
					clicked.resetOther();
				}
				return true;
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
			world.markBlockForUpdate(x, y, z);
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
		}
		else {
			if (!world.isRemote) {
				Block id = world.getBlock(x, y, z);
				damage = world.getBlockMetadata(x, y, z);
				if (id == Blocks.end_portal_frame) {
					if (damage >= 4) {
						world.setBlockMetadataWithNotify(x, y, z, damage-4, 3);
						ReikaItemHelper.dropItem(world, x+0.5, y+1, z+0.5, new ItemStack(Items.ender_eye));
					}
					else {
						int newmeta = damage == 3 ? 0 : damage+1;
						world.setBlockMetadataWithNotify(x, y, z, newmeta, 3);
					}
					return true;
				}
				if (id instanceof BlockRedstoneDiode) {
					int newmeta = damage%4 == 3 ? damage-3 : damage+1;
					world.setBlockMetadataWithNotify(x, y, z, newmeta, 3);
					return true;
				}
				if ((id == Blocks.sticky_piston || id == Blocks.piston) && world.isBlockIndirectlyGettingPowered(x, y, z))
					return false;
				if (maxdamage.containsKey(id)) {
					if (damage < maxdamage.get(id)) {
						world.setBlockMetadataWithNotify(x, y, z, damage+1, 3);
					}
					else {
						world.setBlockMetadataWithNotify(x, y, z, 0, 3);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;//buildcraft
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		//buildcraft
	}

	@Override
	public final void damageScrewdriver(World world, EntityPlayer player) {
		//project red
	}

	@Override
	public boolean isActive(ItemStack is) {
		return true;//extratrees
	}

	@Override
	public void onHammerUsed(ItemStack is, EntityPlayer ep) {
		//extratrees
	}
}