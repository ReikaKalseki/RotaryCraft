/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.ArrayList;
import java.util.List;

import com.InfinityRaider.AgriCraft.api.v2.ICrop;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import Reika.ChromatiCraft.ChromaticEventManager;
import Reika.ChromatiCraft.API.ChromatiAPI;
import Reika.ChromatiCraft.API.Interfaces.EnchantableItem;
import Reika.ChromatiCraft.Registry.ChromaBlocks;
import Reika.ChromatiCraft.Registry.ChromaEnchants;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Base.BlockTieredResource;
import Reika.DragonAPI.Interfaces.Registry.ModCrop;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.BoPBlockHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockShears;

import cpw.mods.fml.common.eventhandler.Event.Result;

public abstract class ItemSickleBase extends ItemRotaryTool implements EnchantableItem {

	public ItemSickleBase(int index) {
		super(index);
	}

	@Override
	public final boolean onLeftClickEntity(ItemStack is, EntityPlayer ep, Entity e) {
		if (e instanceof EntityLivingBase) {
			EntityLivingBase elb = (EntityLivingBase)e;
			AxisAlignedBB box = ReikaAABBHelper.getEntityCenteredAABB(e, 2).expand(2, 0, 2);
			List<EntityLivingBase> li = ep.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box);
			Class<? extends EntityLivingBase> cat = ReikaEntityHelper.getEntityCategoryClass(elb);
			for (EntityLivingBase e2 : li) {
				if (e2 != e && e2 != ep && ReikaEntityHelper.getEntityCategoryClass(e2) == cat) {
					e2.attackEntityFrom(DamageSource.causePlayerDamage(ep), damageVsEntity);
				}
			}
			if (this.isBreakable()) {
				is.damageItem(20, ep);
			}
		}
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		if (ModList.AGRICRAFT.isLoaded()) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof ICrop) {
				int r = this.getCropRange();
				for (int i = -r; i <= r; i++) {
					for (int k = -r; k <= r; k++) {
						int dx = x+i;
						int dz = z+k;
						TileEntity te2 = world.getTileEntity(dx, y, dz);
						if (te2 instanceof ICrop) {
							ICrop ic = (ICrop)te2;
							if (ic.hasWeed()) {
								ic.clearWeed();
								ReikaSoundHelper.playBreakSound(world, dx, y, dz, Blocks.tallgrass);
								double ch = this.isBreakable() ? 40 : 80;
								if (ReikaRandomHelper.doWithChance(ch))
									this.dropItem(is, ep, world, dx+0.5, y+0.5, dz+0.5, ReikaItemHelper.tallgrass.asItemStack());
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep) {
		World world = ep.worldObj;
		Block id = world.getBlock(x, y, z);
		if (id instanceof BlockTieredResource)
			return false;
		int meta = world.getBlockMetadata(x, y, z);
		boolean ignoreMeta = ep.isSneaking();
		ModCrop mod = ModCropList.getModCrop(id, meta);
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
						if (id2 == id || (leaf2 == leaf && leaf != null)) {
							Block b2 = id2;
							this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2, 0.25F, 1);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (ModList.CHROMATICRAFT.isLoaded() && (id == ChromatiAPI.trees.getDyeLeaf(false) || id == ChromatiAPI.trees.getDyeLeaf(true))) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getLeafRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if ((id2 == ChromatiAPI.trees.getDyeLeaf(false) || id2 == ChromatiAPI.trees.getDyeLeaf(true)) && (ignoreMeta || meta2 == meta)) {
							this.dropBlockAsItem(is, ep, id2, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, id2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (ModList.CHROMATICRAFT.isLoaded() && id == ChromatiAPI.trees.getRainbowLeaf()) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getLeafRange();
			ArrayList<ItemStack> items = new ArrayList();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == id) {
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
			if (this.isBreakable())
				is.damageItem(1, ep);
			this.dropItems(is, ep, world, x, y, z, items);
			return true;
		}
		else if (ModList.CHROMATICRAFT.isLoaded() && id == ChromaBlocks.DECOFLOWER.getBlockInstance()) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == id && (ignoreMeta || meta2 == meta)) {
							Block b2 = id2;
							if (this.canActAsShears()) {
								ArrayList<ItemStack> li = ((IShearable)b2).onSheared(is, world, x+i, y+j, z+k, fortune);
								this.dropItems(is, ep, world, x+i+itemRand.nextDouble(), y+j+itemRand.nextDouble(), z+k+itemRand.nextDouble(), li);
							}
							else {
								this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							}
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (ModList.CHROMATICRAFT.isLoaded() && id == ChromatiAPI.trees.getDyeFlower()) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == id && (ignoreMeta || meta2 == meta)) {
							this.dropBlockAsItem(is, ep, id, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, id);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (ModList.BOP.isLoaded() && BoPBlockHandler.getInstance().isCoral(id)) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (BoPBlockHandler.getInstance().isCoral(id2) && (ignoreMeta || meta2 == meta)) {
							Block b2 = id2;
							this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (ModList.BOP.isLoaded() && BoPBlockHandler.getInstance().isFlower(id)) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (BoPBlockHandler.getInstance().isFlower(id2) && (ignoreMeta || meta2 == meta)) {
							Block b2 = id2;
							this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (ModList.BOP.isLoaded() && id == BoPBlockHandler.getInstance().foliage) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id == id2 && (ignoreMeta || meta2 == meta)) {
							Block b2 = id2;
							if (this.canActAsShears()) {
								this.dropItem(is, ep, world, x+i+0.5, y+j+0.5, z+k+0.5, new ItemStack(b2, 1, meta2));
							}
							else {
								this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							}
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
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
								this.dropItems(is, ep, world, x+i+0.5, y+j+0.5, z+k+0.5, b2.getDrops(world, x, y, z, meta2, fortune));
								world.setBlockToAir(x+i, y+j, z+k);
							}
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
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
						ModCrop mod2 = ModCropList.getModCrop(id2, meta2);
						if (mod == mod2) {
							if (mod2.isRipe(world, x+i, y+j, z+k)) {
								Block b2 = id2;
								//dropItems(is, ep, world, x+i+0.5, y+j+0.5, z+k+0.5, b2.getDrops(world, x, y, z, meta2, fortune));
								this.dropItems(is, ep, world, x+i+0.5, y+j+0.5, z+k+0.5, mod2.getDrops(world, x+i, y+j, z+k, fortune));
								if (mod2.destroyOnHarvest())
									world.setBlockToAir(x+i, y+j, z+k);
								else
									mod2.setHarvested(world, x+i, y+j, z+k);
							}
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		else if (plant != null) {
			int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
			int r = this.getPlantRange();
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						Block id2 = world.getBlock(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						ReikaPlantHelper plant2 = ReikaPlantHelper.getPlant(id2);
						if (plant2 == plant) {
							if (plant == ReikaPlantHelper.FLOWER && !ep.isSneaking() && (id != id2 || meta2 != meta))
								continue;
							Block b2 = id2;
							if (this.canActAsShears()) {
								if (ItemBedrockShears.getHarvestResult(b2, meta2, ep, world, x+i, y+j, z+k) == Result.ALLOW)
									this.dropItem(is, ep, world, x+i+0.5, y+j+0.5, z+k+0.5, new ItemStack(id2, 1, ItemBedrockShears.getDroppedMeta(id2, meta2)));
								else
									this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							}
							else
								this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2, 0.25F, 1);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
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
						if (id2 == id && (ignoreMeta || meta2 == meta)) {
							Block b2 = id2;
							this.dropBlockAsItem(is, ep, b2, world, x+i, y+j, z+k, meta2, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b2);
							world.setBlockToAir(x+i, y+j, z+k);
						}
					}
				}
			}
			if (this.isBreakable())
				is.damageItem(1, ep);
			return true;
		}
		return false;
	}

	private void dropItems(ItemStack tool, EntityPlayer ep, World world, double x, double y, double z, ArrayList<ItemStack> drops) {
		if (ModList.CHROMATICRAFT.isLoaded() && this.isAutoCollect(tool)) {
			for (ItemStack is : drops) {
				this.handleItem(is, ep);
			}
		}
		else {
			ReikaItemHelper.dropItems(world, x, y, z, drops);
		}
	}

	private void dropBlockAsItem(ItemStack tool, EntityPlayer ep, Block b2, World world, int x, int y, int z, int meta2, int fortune) {
		if (ModList.CHROMATICRAFT.isLoaded() && this.isAutoCollect(tool)) {
			this.setItemCollection(ep);
		}
		b2.dropBlockAsItem(world, x, y, z, meta2, fortune);
	}

	private void dropItem(ItemStack tool, EntityPlayer ep, World world, double x, double y, double z, ItemStack drop) {
		if (ModList.CHROMATICRAFT.isLoaded() && this.isAutoCollect(tool)) {
			this.handleItem(drop, ep);
		}
		else {
			ReikaItemHelper.dropItem(world, x, y, z, drop);
		}
	}

	private void handleItem(ItemStack drop, EntityPlayer ep) {
		if (!MinecraftForge.EVENT_BUS.post(new EntityItemPickupEvent(ep, new EntityItem(ep.worldObj, ep.posX, ep.posY, ep.posZ, drop))))
			ReikaPlayerAPI.addOrDropItem(drop, ep);
	}

	public abstract int getLeafRange();
	public abstract int getPlantRange();
	public abstract int getCropRange();

	public abstract boolean canActAsShears();
	public abstract boolean isBreakable();

	@ModDependent(ModList.CHROMATICRAFT)
	public final Result getEnchantValidity(Enchantment e, ItemStack is) {
		if (is.getItem() == this) {
			if (e == Enchantment.fortune || e == Enchantment.efficiency || e == Enchantment.unbreaking)
				return Result.ALLOW;
			if (e == ChromaEnchants.AIRMINER.getEnchantment() || e == ChromaEnchants.AUTOCOLLECT.getEnchantment())
				return Result.ALLOW;
		}
		return Result.DEFAULT;
	}

	@ModDependent(ModList.CHROMATICRAFT)
	private boolean isAutoCollect(ItemStack tool) {
		return ChromaEnchants.AUTOCOLLECT.getLevel(tool) > 0;
	}

	@ModDependent(ModList.CHROMATICRAFT)
	private void setItemCollection(EntityPlayer ep) {
		ChromaticEventManager.instance.collectItemPlayer = ep;
	}

}
