/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import Reika.ChromatiCraft.API.TreeGetter;
import Reika.DragonAPI.Instantiable.Data.TreeReader;
import Reika.DragonAPI.Interfaces.InertIInv;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DamagingContact;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWoodcutter extends InventoriedPowerReceiver implements EnchantableMachine, InertIInv, DiscreteFunction,
ConditionalOperation, DamagingContact {

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

	private TreeReader tree = new TreeReader();
	private TreeReader treeCopy = new TreeReader();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;

		tree.setWorld(world);
		treeCopy.setWorld(world);

		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (power < MINPOWER || torque < MINTORQUE) {
			return;
		}

		if (world.isRemote)
			return;

		if (tree.isEmpty() && this.hasWood()) {
			tree.reset();
			ModWoodList wood = ModWoodList.getModWood(world.getBlock(editx, edity, editz), world.getBlockMetadata(editx, edity, editz));
			ReikaTreeHelper vanilla = ReikaTreeHelper.getTree(world.getBlock(editx, edity, editz), world.getBlockMetadata(editx, edity, editz));

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					tree.checkAndAddDyeTree(world, editx+i, edity, editz+j);
					if (tree.isEmpty() || !tree.isValidTree())
						tree.clear();
				}
			}
			if (tree.isEmpty()) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						tree.checkAndAddRainbowTree(world, editx+i, edity, editz+j);
						if (tree.isEmpty() || !tree.isValidTree())
							tree.clear();
					}
				}
			}

			if (tree.isEmpty()) {

				if (wood == ModWoodList.SEQUOIA) {
					for (int i = -32; i < 255; i += 16)
						tree.addSequoia(world, editx, edity+i, editz, RotaryCraft.logger.shouldDebug());
				}
				else if (wood == ModWoodList.DARKWOOD) {
					tree.addDarkForest(world, editx, edity, editz, editx-8, editx+8, editz-8, editz+8, RotaryCraft.logger.shouldDebug());
				}
				else if (wood == ModWoodList.IRONWOOD) {
					for (int i = -2; i < 128; i += 16)
						tree.addIronwood(world, editx, edity+i, editz, RotaryCraft.logger.shouldDebug());
				}
				else if (wood != null) {
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							//tree.addGenerousTree(world, editx+i, edity, editz+j, 16);
							tree.setModTree(wood);
							tree.addModTree(world, editx+i, edity, editz+j);
						}
						//ReikaJavaLibrary.pConsole(tree, Side.SERVER);
					}
				}
				else if (vanilla != null) {
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							//tree.addGenerousTree(world, editx+i, edity, editz+j, 16);
							tree.setTree(vanilla);
							tree.addTree(world, editx+i, edity, editz+j);
						}
					}
				}
			}

			this.checkAndMatchInventory();

			tree.reverseBlockOrder();
			treeCopy = tree.copy();
		}

		Block b = world.getBlock(x, y+1, z);
		if (b != Blocks.air) {
			if (b.getMaterial() == Material.wood || b.getMaterial() == Material.leaves) {
				ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, b.getDrops(world, x, y+1, z, world.getBlockMetadata(x, y+1, z), this.getEnchantment(Enchantment.fortune)));
				world.setBlockToAir(x, y+1, z);
			}
		}

		RotaryCraft.logger.debug(tree);

		if (tree.isEmpty())
			return;

		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;

		if (!tree.isValidTree()) {
			tree.clear();
			return;
		}

		int[] xyz = tree.getNextAndMoveOn();
		Block drop = world.getBlock(xyz[0], xyz[1], xyz[2]);
		int dropmeta = world.getBlockMetadata(xyz[0], xyz[1], xyz[2]);

		if (drop != Blocks.air) {
			Material mat = ReikaWorldHelper.getMaterial(world, xyz[0], xyz[1], xyz[2]);
			if (ConfigRegistry.INSTACUT.getState()) {
				//ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlocks.getDrops(world, xyz[0], xyz[1], xyz[2], dropmeta, 0));
				this.dropBlocks(world, xyz[0], xyz[1], xyz[2]);
				world.setBlockToAir(xyz[0], xyz[1], xyz[2]);
				if (mat == Material.leaves)
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+rand.nextFloat(), 1F);
				else
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+rand.nextFloat(), 1F);

				if (xyz[1] == edity) {
					Block idbelow = world.getBlock(xyz[0], xyz[1]-1, xyz[2]);
					Block root = TwilightForestHandler.getInstance().rootID;
					if (ReikaPlantHelper.SAPLING.canPlantAt(world, xyz[0], xyz[1], xyz[2])) {
						ItemStack plant = this.getPlantedSapling();
						if (plant != null) {
							if (inv[0] != null)
								ReikaInventoryHelper.decrStack(0, inv);
							ReikaWorldHelper.setBlock(world, xyz[0], xyz[1], xyz[2], plant);
						}
					}
					else if (tree.getModTree() == ModWoodList.TIMEWOOD && (idbelow == root || idbelow == Blocks.air)) {
						ItemStack plant = this.getPlantedSapling();
						if (plant != null) {
							if (inv[0] != null)
								ReikaInventoryHelper.decrStack(0, inv);
							world.setBlock(xyz[0], xyz[1]-1, xyz[2], Blocks.dirt);
							ReikaWorldHelper.setBlock(world, xyz[0], xyz[1], xyz[2], plant);
						}
					}
				}
			}
			else {
				boolean fall = BlockSand.func_149831_e(world, xyz[0], xyz[1]-1, xyz[2]);
				if (fall) {
					EntityFallingBlock e = new EntityFallingBlock(world, xyz[0]+0.5, xyz[1]+0.65, xyz[2]+0.5, drop, dropmeta);
					e.field_145812_b = -2000;
					e.field_145813_c = false;
					if (!world.isRemote) {
						world.spawnEntityInWorld(e);
					}
					world.setBlockToAir(xyz[0], xyz[1], xyz[2]);
				}
				else {

					//ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlocks.getDrops(world, xyz[0], xyz[1], xyz[2], dropmeta, 0));
					this.dropBlocks(world, xyz[0], xyz[1], xyz[2]);
					world.setBlockToAir(xyz[0], xyz[1], xyz[2]);
					ReikaSoundHelper.playBreakSound(world, xyz[0], xyz[1], xyz[2], Blocks.log);

					if (mat == Material.leaves)
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+rand.nextFloat(), 1F);
					else
						world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+rand.nextFloat(), 1F);

					if (xyz[1] == edity) {
						Block idbelow = world.getBlock(xyz[0], xyz[1]-1, xyz[2]);
						Block root = TwilightForestHandler.getInstance().rootID;
						if (ReikaPlantHelper.SAPLING.canPlantAt(world, xyz[0], xyz[1], xyz[2])) {
							ItemStack plant = this.getPlantedSapling();
							if (plant != null) {
								if (inv[0] != null)
									ReikaInventoryHelper.decrStack(0, inv);
								ReikaWorldHelper.setBlock(world, xyz[0], xyz[1], xyz[2], plant);
							}
						}
						else if (tree.getModTree() == ModWoodList.TIMEWOOD && (idbelow == root || idbelow == Blocks.air)) {
							ItemStack plant = this.getPlantedSapling();
							if (plant != null) {
								if (inv[0] != null)
									ReikaInventoryHelper.decrStack(0, inv);
								world.setBlock(xyz[0], xyz[1]-1, xyz[2], Blocks.dirt);
								ReikaWorldHelper.setBlock(world, xyz[0], xyz[1], xyz[2], plant);
							}
						}
					}
				}
			}
		}
	}

	private void checkAndMatchInventory() {
		ItemStack sapling = null;
		if (tree.isDyeTree()) {
			sapling = new ItemStack(TreeGetter.getSaplingID(), 1, tree.getDyeTreeMeta());
		}
		else if (tree.getModTree() != null) {
			sapling = tree.getModTree().getCorrespondingSapling();
		}
		else if (tree.getVanillaTree() != null) {
			sapling = tree.getVanillaTree().getSapling();
		}
		if (!ReikaItemHelper.matchStacks(inv[0], sapling)) {
			this.dumpInventory();
		}
	}

	private void dropBlocks(World world, int x, int y, int z) {
		Block drop = world.getBlock(x, y, z);
		if (drop == Blocks.air)
			return;
		int dropmeta = world.getBlockMetadata(x, y, z);
		ItemStack sapling = null;
		Block logID = null;
		if (tree.isVanillaTree()) {
			sapling = tree.getVanillaTree().getSapling();
			logID = Block.getBlockFromItem(tree.getVanillaTree().getLog().getItem());
		}
		else if (tree.isModTree()) {
			sapling = tree.getModTree().getCorrespondingSapling();
			logID = Block.getBlockFromItem(tree.getModTree().getItem().getItem());
		}

		List<ItemStack> drops = drop.getDrops(world, x, y, z, dropmeta, this.getEnchantment(Enchantment.fortune));
		if (drop == logID && logID != null) {
			if (rand.nextInt(3) == 0) {
				drops.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sawdust.copy(), 1+rand.nextInt(4)));
			}
		}

		for (int i = 0; i < drops.size(); i++) {
			ItemStack todrop = drops.get(i);

			if (ReikaItemHelper.matchStacks(todrop, sapling)) {
				ReikaInventoryHelper.addOrSetStack(todrop, inv, 0);
			}
			else {
				if (!this.chestCheck(todrop))
					ReikaItemHelper.dropItem(world, dropx, yCoord-0.25, dropz, todrop);
			}
		}
	}

	private boolean chestCheck(ItemStack is) {
		TileEntity te = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if (te instanceof IInventory) {
			IInventory ii = (IInventory)te;
			if (ReikaInventoryHelper.addToIInv(is, ii))
				return true;
		}
		return false;
	}

	private void dumpInventory() {
		if (inv[0] == null)
			return;
		ItemStack is = inv[0].copy();
		inv[0] = null;
		this.chestCheck(is);
	}

	public ItemStack getPlantedSapling() {
		if (!this.shouldPlantSapling())
			return null;
		if (treeCopy.isDyeTree())
			return new ItemStack(TreeGetter.getSaplingID(), 1, treeCopy.getDyeTreeMeta());
		else if (treeCopy.isVanillaTree())
			return treeCopy.getVanillaTree().getSapling();
		else if (treeCopy.isModTree())
			return treeCopy.getModTree().getCorrespondingSapling();
		else
			return null;
	}

	private boolean shouldPlantSapling() {
		if (this.hasEnchantment(Enchantment.infinity))
			return true;
		if (treeCopy.isDyeTree()) {
			return inv[0] != null && inv[0].stackSize > 0 && Block.getBlockFromItem(inv[0].getItem()) == TreeGetter.getSaplingID();
		}
		else if (treeCopy.isVanillaTree()) {
			return inv[0] != null && inv[0].stackSize > 0 && ReikaItemHelper.matchStacks(inv[0], treeCopy.getVanillaTree().getSapling());
		}
		else if (treeCopy.getModTree() != null)
			return inv[0] != null && inv[0].stackSize > 0 && ReikaItemHelper.matchStacks(inv[0], treeCopy.getModTree().getCorrespondingSapling());
			else
				return false;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			read = ForgeDirection.EAST;
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
			read = ForgeDirection.WEST;
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
			read = ForgeDirection.SOUTH;
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
			read = ForgeDirection.NORTH;
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
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.WOODCUTTER;
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
				Block id = worldObj.getBlock(editx+i, edity, editz+j);
				int meta = worldObj.getBlockMetadata(editx+i, edity, editz+j);
				if (id == Blocks.log || id == Blocks.log2)
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
		boolean accepted = false;
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.fortune, is)) {
			enchantments.put(Enchantment.fortune, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.infinity, is)) {
			enchantments.put(Enchantment.infinity, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.infinity, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.efficiency, is))	 {
			enchantments.put(Enchantment.efficiency, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency, is));
			accepted = true;
		}
		return accepted;
	}

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList<Enchantment>();
		li.add(Enchantment.fortune);
		li.add(Enchantment.infinity);
		li.add(Enchantment.efficiency);
		return li;
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

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				if (lvl > 0)
					NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}
	}

	@Override
	public void onEMP() {}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getOperationTime() {
		if (ConfigRegistry.INSTACUT.getState()) {
			int base = DurationRegistry.WOODCUTTER.getOperationTime(omega);
			float ench = ReikaEnchantmentHelper.getEfficiencyMultiplier(this.getEnchantment(Enchantment.efficiency));
			return (int)(base/ench);
		}
		return 0;
	}

	@Override
	public boolean areConditionsMet() {
		return this.hasWood();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Tree";
	}

	@Override
	public int getContactDamage() {
		return 4;
	}

	public boolean canDealDamage() {
		return power >= MINPOWER && torque >= MINTORQUE;
	}

	@Override
	public DamageSource getDamageType() {
		return DamageSource.generic;
	}
}