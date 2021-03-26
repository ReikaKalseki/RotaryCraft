/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import Reika.ChromatiCraft.API.TreeGetter;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray.BlockTypePrioritizer;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.TreeReader;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Interfaces.Registry.TreeType;
import Reika.DragonAPI.Interfaces.TileEntity.InertIInv;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.TwilightForestHandler;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.Cleanable;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DamagingContact;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWoodcutter extends InventoriedPowerReceiver implements EnchantableMachine, InertIInv, DiscreteFunction,
ConditionalOperation, DamagingContact, Cleanable, MultiOperational {

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.infinity).addFilter(Enchantment.fortune).addFilter(Enchantment.efficiency);

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

	private boolean cuttingTree;

	private Comparator<Coordinate> leafPriority;

	private static final int MAX_JAM = 20;

	private int jam = 0;
	private int jamColor = -1;

	public int getJamColor() {
		return jamColor;
	}

	public void clean() {
		jam--;
		if (jam <= 0) {
			jam = 0;
			jamColor = -1;
		}
	}

	public boolean isJammed() {
		return jam > MAX_JAM;
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		leafPriority = new LeafPrioritizer(world);
	}

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

		if (this.isJammed())
			return;

		if (world.isRemote)
			return;

		if (tree.isEmpty() && this.hasWood()) {
			tree.reset();
			TreeType type = ReikaTreeHelper.getTree(world.getBlock(editx, edity, editz), world.getBlockMetadata(editx, edity, editz));
			if (type == null)
				type = ModWoodList.getModWood(world.getBlock(editx, edity, editz), world.getBlockMetadata(editx, edity, editz));

			if (type != null) {
				tree.setTree(type);
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						tree.addTree(world, editx+i, edity, editz+j);
					}
				}
			}

			if (!tree.isEmpty()) {
				cuttingTree = true;
			}

			this.checkAndMatchInventory();

			tree.sortBlocksByHeight(false);
			tree.reverseBlockOrder();
			tree.sortBlocksByDistance(new Coordinate(this));
			tree.sort(leafPriority);
			treeCopy = (TreeReader)tree.copy();
		}

		Block b = world.getBlock(x, y+1, z);
		if (b != Blocks.air) {
			if (b.getMaterial() == Material.wood || b.getMaterial() == Material.leaves) {
				ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, this.getDrops(world, x, y+1, z, b, world.getBlockMetadata(x, y, z)));
				world.setBlockToAir(x, y+1, z);
			}
		}

		//RotaryCraft.logger.debug(tree);

		if (tree.isEmpty())
			return;

		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;

		if (!cuttingTree && !tree.isValidTree()) {
			tree.reset();
			tree.clear();
			return;
		}

		for (int i = 0; i < this.getNumberConsecutiveOperations(); i++) {
			Coordinate c = tree.getNextAndMoveOn();
			this.cutCoord(world, x, y, z, c);
			if (tree.isEmpty())
				break;
		}
	}

	private void cutCoord(World world, int x, int y, int z, Coordinate c) {
		Block drop = c.getBlock(world);
		int dropmeta = c.getBlockMetadata(world);

		if (drop != Blocks.air) {
			Material mat = ReikaWorldHelper.getMaterial(world, c.xCoord, c.yCoord, c.zCoord);
			if (ConfigRegistry.INSTACUT.getState()) {
				//ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlocks.getDrops(world, c.xCoord, c.yCoord, c.zCoord, dropmeta, 0));
				this.cutBlock(world, x, y, z, c, mat);

				if (c.yCoord == edity && drop == tree.getTreeType().getLogID()) {
					Block idbelow = world.getBlock(c.xCoord, c.yCoord-1, c.zCoord);
					Block root = TwilightForestHandler.BlockEntry.ROOT.getBlock();
					if (ReikaPlantHelper.SAPLING.canPlantAt(world, c.xCoord, c.yCoord, c.zCoord)) {
						BlockKey plant = this.getPlantedSapling();
						if (plant != null) {
							if (inv[0] != null && !enchantments.hasEnchantment(Enchantment.infinity))
								ReikaInventoryHelper.decrStack(0, inv);
							plant.place(world, c.xCoord, c.yCoord, c.zCoord);
						}
					}
					else if (tree.getTreeType() == ModWoodList.TIMEWOOD && (idbelow == root || idbelow == Blocks.air)) {
						BlockKey plant = this.getPlantedSapling();
						if (plant != null) {
							if (inv[0] != null && !enchantments.hasEnchantment(Enchantment.infinity))
								ReikaInventoryHelper.decrStack(0, inv);
							world.setBlock(c.xCoord, c.yCoord-1, c.zCoord, Blocks.dirt);
							plant.place(world, c.xCoord, c.yCoord, c.zCoord);
						}
					}
				}
			}
			else {
				boolean fall = BlockSand.func_149831_e(world, c.xCoord, c.yCoord-1, c.zCoord);
				if (fall) {
					EntityFallingBlock e = new EntityFallingBlock(world, c.xCoord+0.5, c.yCoord+0.65, c.zCoord+0.5, drop, dropmeta);
					e.field_145812_b = -5000;
					if (!world.isRemote) {
						world.spawnEntityInWorld(e);
					}
					c.setBlock(world, Blocks.air);
				}
				else {
					this.cutBlock(world, x, y, z, c, mat);
					if (c.yCoord == edity) {
						Block idbelow = world.getBlock(c.xCoord, c.yCoord-1, c.zCoord);
						Block root = TwilightForestHandler.BlockEntry.ROOT.getBlock();
						if (ReikaPlantHelper.SAPLING.canPlantAt(world, c.xCoord, c.yCoord, c.zCoord)) {
							BlockKey plant = this.getPlantedSapling();
							if (plant != null) {
								if (inv[0] != null && !enchantments.hasEnchantment(Enchantment.infinity))
									ReikaInventoryHelper.decrStack(0, inv);
								plant.place(world, c.xCoord, c.yCoord, c.zCoord);
							}
						}
						else if (tree.getTreeType() == ModWoodList.TIMEWOOD && (idbelow == root || idbelow == Blocks.air)) {
							BlockKey plant = this.getPlantedSapling();
							if (plant != null) {
								if (inv[0] != null && !enchantments.hasEnchantment(Enchantment.infinity))
									ReikaInventoryHelper.decrStack(0, inv);
								world.setBlock(c.xCoord, c.yCoord-1, c.zCoord, Blocks.dirt);
								plant.place(world, c.xCoord, c.yCoord, c.zCoord);
							}
						}
					}
				}
			}
		}
	}

	private void cutBlock(World world, int x, int y, int z, Coordinate c, Material mat) {
		//ReikaItemHelper.dropItems(world, dropx, y-0.25, dropz, dropBlocks.getDrops(world, c.xCoord, c.yCoord, c.zCoord, dropmeta, 0));
		this.dropBlocks(world, c.xCoord, c.yCoord, c.zCoord);
		c.setBlock(world, Blocks.air);

		if (mat == Material.leaves)
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+rand.nextFloat()*0.5F, 1F);
		else
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+rand.nextFloat()*0.5F, 1F);
		if (tree.getTreeType() == ModWoodList.SLIME && c.getBlock(world) == tree.getTreeType().getLogID()) {
			jam++;
			jamColor = 0xff000000 | ReikaColorAPI.mixColors(ModWoodList.SLIME.logColor, 0xffffff, (float)jam/MAX_JAM);
		}
	}

	private Collection<ItemStack> getDrops(World world, int x, int y, int z, Block b, int meta) {
		float f = this.getYield(b, meta);
		if (ReikaRandomHelper.doWithChance(f)) {
			int fortune = enchantments.getEnchantment(Enchantment.fortune);
			ArrayList<ItemStack> ret = b.getDrops(world, x, y, z, meta, fortune);
			MinecraftForge.EVENT_BUS.post(new HarvestDropsEvent(x, y, z, world, b, meta, fortune, 1, ret, this.getPlacer(), false));
			if (tree.getTreeType() == ModWoodList.SLIME) {
				Block log = tree.getTreeType().getLogID();
				if (b == log) {
					ret.clear();
					ret.add(new ItemStack(Items.slime_ball));
				}
			}
			else if (tree.getTreeType() == ReikaTreeHelper.OAK || tree.isDyeTree()) {
				int n = 0;
				if (fortune >= 5) {
					n = 4;
				}
				else if (fortune >= 3) {
					n = 6;
				}
				else if (fortune >= 2) {
					n = 10;
				}
				else if (fortune >= 1) {
					n = 20;
				}
				if (n > 0 && rand.nextInt(n) == 0) {
					ret.add(new ItemStack(Items.apple));
				}
			}
			return ret;
		}
		else
			return new ArrayList();
	}

	private float getYield(Block b, int meta) {
		if (tree.getTreeType() == ModWoodList.SLIME) {
			Block log = tree.getTreeType().getLogID();
			if (b == log) {
				return 0.6F;
			}
		}
		return 1;
	}

	private void checkAndMatchInventory() {
		BlockKey sapling = null;
		if (tree.isDyeTree()) {
			sapling = new BlockKey(TreeGetter.getSaplingID(), tree.getDyeTreeMeta());
		}
		else if (tree.getTreeType() != null) {
			sapling = tree.getSapling();
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
		BlockKey sapling = tree.getSapling();
		Block logID = tree.getTreeType().getLogID();

		Collection<ItemStack> drops = this.getDrops(world, x, y, z, drop, dropmeta);
		if (drop == logID && logID != null) {
			if (tree.getTreeType() != ModWoodList.SLIME) {
				if (rand.nextInt(3) == 0) {
					drops.add(ReikaItemHelper.getSizedItemStack(ItemStacks.sawdust.copy(), 1+rand.nextInt(4)));
				}
			}
		}

		for (ItemStack todrop : drops) {
			if (ReikaItemHelper.matchStacks(todrop, sapling)) {
				if (inv[0] != null && inv[0].stackSize >= inv[0].getMaxStackSize()) {
					this.chestCheck(todrop);
					if (todrop.stackSize > 0)
						ReikaItemHelper.dropItem(world, dropx, yCoord-0.25, dropz, todrop);
				}
				else
					ReikaInventoryHelper.addOrSetStack(todrop, inv, 0);
			}
			else {
				this.chestCheck(todrop);
				if (todrop.stackSize > 0)
					ReikaItemHelper.dropItem(world, dropx, yCoord-0.25, dropz, todrop);
			}
		}
	}

	private void chestCheck(ItemStack is) {
		TileEntity te = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if (te instanceof IInventory) {
			IInventory ii = (IInventory)te;

			//build in auto collation
			int max = Math.min(ii.getInventoryStackLimit(), is.getMaxStackSize());
			for (int i = 0; i < ii.getSizeInventory(); i++) {
				ItemStack in = ii.getStackInSlot(i);
				if (in != null && ReikaItemHelper.areStacksCombinable(is, in, max)) {
					int fit = max-in.stackSize;
					int add = Math.min(fit, is.stackSize);
					if (add > 0) {
						is.stackSize -= add;
						in.stackSize += add;
					}
					if (is.stackSize <= 0)
						return;
				}
			}

			if (ReikaInventoryHelper.addToIInv(is, ii)) {
				is.stackSize = 0;
			}
		}
	}

	private void dumpInventory() {
		if (inv[0] == null)
			return;
		ItemStack is = inv[0].copy();
		inv[0] = null;
		this.chestCheck(is);
	}

	public BlockKey getPlantedSapling() {
		if (!this.shouldPlantSapling())
			return null;
		if (treeCopy.isDyeTree())
			return new BlockKey(TreeGetter.getSaplingID(), treeCopy.getDyeTreeMeta());
		else if (treeCopy.isRainbowTree())
			return new BlockKey(TreeGetter.getRainbowSaplingID());
		else if (treeCopy.getTreeType() != null)
			return treeCopy.getSapling();
		else
			return null;
	}

	private boolean shouldPlantSapling() {
		if (enchantments.hasEnchantment(Enchantment.infinity))
			return true;
		if (treeCopy.isDyeTree()) {
			return inv[0] != null && inv[0].stackSize > 0 && Block.getBlockFromItem(inv[0].getItem()) == TreeGetter.getSaplingID();
		}
		else if (treeCopy.getTreeType() != null) {
			return inv[0] != null && inv[0].stackSize > 0 && ReikaItemHelper.matchStacks(inv[0], treeCopy.getSapling());
		}
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
		if (power < MINPOWER || torque < MINTORQUE || this.isJammed())
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
				//RotaryCraft.logger.debug("Retrieved wood "+wood+" from "+id+":"+meta);
				if (wood != null)
					return true;
			}
		}
		return false;
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

		NBT.setInteger("jam", jam);
		NBT.setInteger("jamc", jamColor);

		NBT.setBoolean("cutting", cuttingTree);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		jam = NBT.getInteger("jam");
		jamColor = NBT.getInteger("jamc");

		cuttingTree = NBT.getBoolean("cutting");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setTag("enchants", enchantments.writeToNBT());
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));
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
			float ench = ReikaEnchantmentHelper.getEfficiencyMultiplier(enchantments.getEnchantment(Enchantment.efficiency));
			return (int)(base/ench);
		}
		return 0;
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.WOODCUTTER.getNumberOperations(omega);
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
		return RotaryCraft.grind;
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}

	private static class LeafPrioritizer extends BlockTypePrioritizer {

		private LeafPrioritizer(World world) {
			super(world);
		}

		@Override
		protected int compare(BlockKey b1, BlockKey b2) {
			boolean l1 = this.isLeaf(b1);
			boolean l2 = this.isLeaf(b2);
			if (l1 && l2) {
				return 0;
			}
			else if (l1) {
				return -1;
			}
			else if (l2) {
				return 1;
			}
			else {
				return 0;
			}
		}

		private boolean isLeaf(BlockKey bk) {
			if (bk.blockID.getMaterial() == Material.leaves)
				return true;
			if (bk.blockID == Blocks.leaves || bk.blockID == Blocks.leaves2)
				return true;
			if (ModWoodList.isModLeaf(bk.blockID, bk.metadata))
				return true;
			return false;
		}

	}
}
