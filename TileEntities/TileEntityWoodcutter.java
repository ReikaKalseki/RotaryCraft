/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.ReikaTreeHelper;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelWoodcutter;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWoodcutter extends TileEntityPowerReceiver implements EnchantableMachine {

	private HashMap<Enchantment,Integer> enchantments = new HashMap<Enchantment,Integer>();

	public int editx;
	public int edity;
	public int editz;
	public double dropx;
	public double dropz;

	/** For the 3x3 area of effect */
	public boolean varyx;
	public boolean varyz;
	public int stepx;
	public int stepz;

	private BlockArray tree = new BlockArray();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;

		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (power < MINPOWER || torque < MINTORQUE) {
			return;
		}

		if (world.isRemote)
			return;

		if (tree.isEmpty() && this.hasWood()) {
			ModWoodList wood = ModWoodList.getModWood(world.getBlockId(editx, edity, editz), world.getBlockMetadata(editx, edity, editz));
			ReikaTreeHelper vanilla = ReikaTreeHelper.getTree(world.getBlockId(editx, edity, editz), world.getBlockMetadata(editx, edity, editz));
			if (wood == ModWoodList.SEQUOIA) {
				tree.addSequoia(world, editx, edity, editz, RotaryCraft.logger.shouldDebug());
			}
			else if (wood != null) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						//tree.addGenerousTree(world, editx+i, edity, editz+j, 16);
						tree.addModTree(world, editx+i, edity, editz+j, wood);
					}
				}
			}
			else if (vanilla != null) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						//tree.addGenerousTree(world, editx+i, edity, editz+j, 16);
						tree.addTree(world, editx+i, edity, editz+j, vanilla);
					}
				}
			}
		}

		int id = world.getBlockId(x, y+1, z);
		if (id != 0) {
			Block b = Block.blocksList[id];
			ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, b.getBlockDropped(world, x, y+1, z, world.getBlockMetadata(x, y+1, z), this.getEnchantment(Enchantment.fortune)));
			world.setBlock(x, y+1, z, 0);
		}

		RotaryCraft.logger.debug(tree);

		if (tree.isEmpty())
			return;

		if (!this.operationComplete((int)(tickcount*ReikaEnchantmentHelper.getEfficiencyMultiplier(this.getEnchantment(Enchantment.efficiency))), 0) && ConfigRegistry.INSTACUT.getState())
			return;
		tickcount = 0;

		int[] xyz = tree.getNextAndMoveOn();
		int drop = world.getBlockId(xyz[0], xyz[1], xyz[2]);
		if (drop != 0) {
			int dropmeta = world.getBlockMetadata(xyz[0], xyz[1], xyz[2]);
			Material mat = world.getBlockMaterial(xyz[0], xyz[1], xyz[2]);
			if (ConfigRegistry.INSTACUT.getState()) {
				world.setBlock(xyz[0], xyz[1], xyz[2], 0);

				//ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlock.getBlockDropped(world, xyz[0], xyz[1], xyz[2], dropmeta, 0));
				this.dropBlocks(world, xyz[0], xyz[1], xyz[2]);

				//if (xyz[1] == edity) {
				if (ReikaPlantHelper.SAPLING.canPlantAt(world, xyz[0], xyz[1], xyz[2])) {
					ItemStack plant = this.getSapling(drop, dropmeta);
					if (plant != null)
						world.setBlock(xyz[0], xyz[1], xyz[2], plant.itemID, plant.getItemDamage(), 3);
				}
				//}
			}
			else {
				boolean fall = BlockSand.canFallBelow(world, xyz[0], xyz[1]-1, xyz[2]);
				if (fall) {
					EntityFallingSand e = new EntityFallingSand(world, xyz[0]+0.5, xyz[1]+0.65, xyz[2]+0.5, drop, dropmeta);
					e.fallTime = -2000;
					e.shouldDropItem = false;
					if (!world.isRemote) {
						world.spawnEntityInWorld(e);
					}
					world.setBlock(xyz[0], xyz[1], xyz[2], 0);
				}
				else {
					world.setBlock(xyz[0], xyz[1], xyz[2], 0);

					//ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlock.getBlockDropped(world, xyz[0], xyz[1], xyz[2], dropmeta, 0));
					this.dropBlocks(world, xyz[0], xyz[1], xyz[2]);

					if (mat == Material.leaves)
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+par5Random.nextFloat(), 1F);
					else
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+par5Random.nextFloat(), 1F);

					if (xyz[1] == edity) {
						if (ReikaPlantHelper.SAPLING.canPlantAt(world, xyz[0], xyz[1], xyz[2])) {
							ItemStack plant = this.getSapling(drop, dropmeta);
							if (plant != null)
								world.setBlock(xyz[0], xyz[1], xyz[2], plant.itemID, plant.getItemDamage(), 3);
						}
					}
				}
			}
		}
	}

	private void dropBlocks(World world, int x, int y, int z) {
		int drop = world.getBlockId(x, y, z);
		int dropmeta = world.getBlockMetadata(x, y, z);
		Block dropBlock = Block.blocksList[drop];
		List<ItemStack> drops = dropBlock.getBlockDropped(world, x, y, z, dropmeta, this.getEnchantment(Enchantment.fortune));
		for (int i = 0; i < drops.size(); i++) {
			ItemStack todrop = drops.get(i);
			if (!this.chestCheck(todrop))
				ReikaItemHelper.dropItem(world, dropx, y-0.25, dropz, todrop);
		}
	}

	private boolean chestCheck(ItemStack is) {
		TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		if (te instanceof IInventory) {
			IInventory ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		return false;
	}

	public ItemStack getSapling(int id, int meta) {
		if (id == Block.wood.blockID) {
			return new ItemStack(Block.sapling.blockID, 1, meta%4);
		}
		ModWoodList wood = ModWoodList.getModWood(id, meta);
		if (wood == null)
			return null;
		else
			return wood.getCorrespondingSapling();
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = x+1;
			readz = z;
			ready = y;
			editx = x-1;
			edity = y;
			editz = z;
			dropx = x+1+0.125;
			dropz = z+0.5;
			stepx = 1;
			stepz = 0;
			varyx = false;
			varyz = true;
			break;
		case 1:
			readx = x-1;
			readz = z;
			ready = y;
			editx = x+1;
			edity = y;
			editz = z;
			dropx = x-0.125;
			dropz = z+0.5;
			stepx = -1;
			stepz = 0;
			varyx = false;
			varyz = true;
			break;
		case 2:
			readz = z+1;
			readx = x;
			ready = y;
			editx = x;
			edity = y;
			editz = z-1;
			dropx = x+0.5;
			dropz = z+1+0.125;
			stepx = 0;
			stepz = 1;
			varyx = true;
			varyz = false;
			break;
		case 3:
			readz = z-1;
			readx = x;
			ready = y;
			editx = x;
			edity = y;
			editz = z+1;
			dropx = x+0.5;
			dropz = z-0.125;
			stepx = 0;
			stepz = -1;
			varyx = true;
			varyz = false;
			break;
		}
		dropx = x+0.5; dropz = z+0.5;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelWoodcutter();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.WOODCUTTER.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasWood())
			return 15;
		return 0;
	}

	private boolean hasWood() {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int id = worldObj.getBlockId(editx+i, edity, editz+j);
				int meta = worldObj.getBlockMetadata(editx+i, edity, editz+j);
				if (id == Block.wood.blockID)
					return true;
				ModWoodList wood = ModWoodList.getModWood(id, meta);
				RotaryCraft.logger.debug("Retrieved wood "+wood+" from "+id+":"+meta);
				if (wood != null)
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean applyEnchants(ItemStack is) {
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.fortune, is)) {
			enchantments.put(Enchantment.fortune, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is));
			return true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.efficiency, is))	 {
			enchantments.put(Enchantment.efficiency, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency, is));
			return true;
		}
		return false;
	}

	@Override
	public HashMap<Enchantment,Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantments().containsKey(e);
	}

	@Override
	public boolean hasEnchantments() {
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				if (this.getEnchantment(Enchantment.enchantmentsList[i]) > 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public int getEnchantment(Enchantment e) {
		if (!this.hasEnchantment(e))
			return 0;
		else
			return this.getEnchantments().get(e);
	}
}
