/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import Reika.ChromatiCraft.API.TreeGetter;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;

public abstract class ItemSickleBase extends ItemRotaryTool {

	public ItemSickleBase(int index) {
		super(index);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep) {
		World world = ep.worldObj;
		Block id = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ModCropList mod = ModCropList.getModCrop(id, meta);
		ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
		ReikaPlantHelper plant = ReikaPlantHelper.getPlant(id);
		ModWoodList leaf = ModWoodList.getModWoodFromLeaf(id, meta);
		if (id == Blocks.leaves || id == Blocks.leaves2 || leaf != null) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getLeafRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						ModWoodList leaf2 = ModWoodList.getModWoodFromLeaf(id2, meta2);
						if (id2 == id || leaf2 == leaf) {
							Block b2 = id2;
							b2.dropBlockAsItem(world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2, 0.25F, 1);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			return true;
		}
		else if (ModList.DYETREES.isLoaded() && (id == TreeGetter.getNaturalDyeLeafID() || id == TreeGetter.getHeldDyeLeafID())) {
			;
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getLeafRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == TreeGetter.getNaturalDyeLeafID() || id2 == TreeGetter.getHeldDyeLeafID()) {
							Block b2 = id2;
							b2.dropBlockAsItem(world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			return true;
		}
		else if (ModList.DYETREES.isLoaded() && id == TreeGetter.getRainbowLeafID()) {
			;
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getLeafRange();
			ArrayList<ItemStack> items = new ArrayList();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == TreeGetter.getRainbowLeafID()) {
							Block b2 = id2;
							//b.dropBlockAsItem(world, x+i, y+j, z+k, meta, fortune);
							ReikaItemHelper.addToList(items, b2.getDrops(world, x, y, z, meta2, fortune));
							//items.addAll(b.getDrops(world, x, y, z, meta2, fortune));
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			ReikaItemHelper.dropItems(world, x, y, z, items);
			return true;
		}
		else if (ModList.DYETREES.isLoaded() && id == TreeGetter.getDyeFlowerID()) {
			;
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == TreeGetter.getDyeFlowerID()) {
							Block b2 = id2;
							b2.dropBlockAsItem(world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			return true;
		}
		else if (crop != null) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getCropRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						ReikaCropHelper crop2 = ReikaCropHelper.getCrop(id2);
						if (crop == crop2) {
							if (crop2.isRipe(meta2)) {
								Block b2 = id2;
								ReikaItemHelper.dropItems(world, x+i+0.5, y+j+0.5, z+k+0.5, b2.getDrops(world, x, y, z, meta2, fortune));
								world.setBlockToAir(x+i, y+j, z+k);
							}
						}
					}
				}
			}
			return true;
		}
		else if (mod != null) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getCropRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						ModCropList mod2 = ModCropList.getModCrop(id2, meta2);
						if (mod == mod2) {
							if (mod2.isRipe(world, x+i, y+j, z+k)) {
								Block b2 = id2;
								ReikaItemHelper.dropItems(world, x+i+0.5, y+j+0.5, z+k+0.5, b2.getDrops(world, x, y, z, meta2, fortune));
								world.setBlockToAir(x+i, y+j, z+k);
							}
						}
					}
				}
			}
			return true;
		}
		else if (plant != null) {
			;
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						ReikaPlantHelper plant2 = ReikaPlantHelper.getPlant(id2);
						if (plant2 == plant) {
							Block b2 = id2;
							if (this.canActAsShears()) {
								if (b2.canSilkHarvest(world, ep, x, y, z, meta2))
									ReikaItemHelper.dropItem(world, x+i+0.5, y+j+0.5, z+k+0.5, new ItemStack(id2, 1, meta2));
								else if (b2 instanceof IShearable)
									ReikaItemHelper.dropItem(world, x+i+0.5, y+j+0.5, z+k+0.5, new ItemStack(id2, 1, meta2));
								else
									b2.dropBlockAsItem(world, x+i, y+j, z+k, meta2, fortune);
							}
							else
								b2.dropBlockAsItem(world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2, 0.25F, 1);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			return true;
		}
		else if (id instanceof IPlantable) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == id && meta2 == meta) {
							Block b2 = id2;
							b2.dropBlockAsItem(world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public abstract int getLeafRange();
	public abstract int getPlantRange();
	public abstract int getCropRange();

	public abstract boolean canActAsShears();

}