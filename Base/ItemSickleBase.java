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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.InfinityRaider.AgriCraft.api.v2.ICrop;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import Reika.ChromatiCraft.ChromaticEventManager;
import Reika.ChromatiCraft.API.ChromatiAPI;
import Reika.ChromatiCraft.API.Interfaces.EnchantableItem;
import Reika.ChromatiCraft.Registry.ChromaEnchants;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Base.BlockTieredResource;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
import Reika.DragonAPI.Interfaces.Block.Reedlike;
import Reika.DragonAPI.Interfaces.Registry.CropType;
import Reika.DragonAPI.Interfaces.Registry.CropType.CropMethods;
import Reika.DragonAPI.Interfaces.Registry.TreeType;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.BoPBlockHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockShears;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.Event.Result;

public abstract class ItemSickleBase extends ItemRotaryTool implements EnchantableItem {

	private static final ArrayList<ScytheEffect> allEffects = new ArrayList();
	private static final BasicCropEffect crops = new BasicCropEffect(1);
	private final BlockMap<ScytheEffect> effects = new BlockMap();

	static {
		allEffects.add(new BasicPlantEffect(1));
		allEffects.add(new SugarcaneEffect(0.5F));
		allEffects.add(new BasicLeafEffect(1, 1));
		allEffects.add(crops);
		allEffects.add(new IPlantableEffect(1));
		if (ModList.CHROMATICRAFT.isLoaded()) {
			allEffects.add(new DyeLeafEffect(1));
			allEffects.add(new RainbowLeafEffect(1));
			allEffects.add(new DyeFlowerEffect(1));
			allEffects.add(new DecoFlowerEffect(1));
		}
		if (ModList.BOP.isLoaded()) {
			allEffects.add(new BopFlowerEffect(1));
			allEffects.add(new BopCoralEffect(1));
			allEffects.add(new BopFoliageEffect(1));
		}
		if (Loader.isModLoaded("Growthcraft|Apples")) {
			allEffects.add(new AppleEffect(1));
		}
		Collections.sort(allEffects);
	}

	public ItemSickleBase(int index) {
		super(index);
	}

	@Override
	public final int getItemEnchantability() {
		ItemStack ref = this.getEnchantabilityReference();
		return ref != null ? ref.getItem().getItemEnchantability(ref) : 0;
	}

	public abstract ItemStack getEnchantabilityReference();

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
				is.damageItem(10*li.size(), ep);
			}
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c) {
		if (ModList.AGRICRAFT.isLoaded() && !world.isRemote) {
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
				ItemSickleBase sc = (ItemSickleBase)is.getItem();
				int mined = crops.onBreakAt(world, x, y, z, te.getBlockType(), te.getBlockMetadata(), ep, is, sc, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is), false);
				if (sc.isBreakable())
					is.damageItem(crops.doesDamagePerBlock(is, mined), ep);
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
		ScytheEffect eff = this.getEffect(world, x, y, z, id, meta);
		if (world.isRemote)
			return eff != null;
		if (eff != null) {
			int num = eff.onBreakAt(world, x, y, z, id, meta, ep, is, this, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is), ep.isSneaking());
			if (this.isBreakable()) {
				int dmg = eff.doesDamagePerBlock(is, num);
				is.damageItem(dmg, ep);
			}
			return true;
		}
		return false;
	}

	private ScytheEffect getEffect(World world, int x, int y, int z, Block id, int meta) {
		ScytheEffect get = effects.get(id, meta);
		if (get != null)
			return get;
		for (ScytheEffect e : allEffects) {
			if (e.isValidStartingBlock(world, x, y, z, id, meta)) {
				effects.put(id, meta, e);
				return e;
			}
		}
		return null;
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

	@Override
	public final EnumEnchantmentType getEnchantingCategory() {
		return EnumEnchantmentType.digger;
	}

	private static abstract class ScytheEffect implements Comparable<ScytheEffect> {

		protected final BlockKey block;
		protected final float rangeXZ;
		protected final float rangeY;

		protected ScytheEffect(BlockKey bk, float rx, float ry) {
			block = bk;
			rangeXZ = rx;
			rangeY = ry;
		}

		protected abstract int getRange(ItemStack is, ItemSickleBase item);

		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return block.match(b, meta);
		}

		protected abstract int onBreakAt(World world, int x, int y, int z, Block b, int meta, EntityPlayer ep, ItemStack is, ItemSickleBase item, int fortune, boolean ignoreMeta);

		protected int doesDamagePerBlock(ItemStack is, int mined) {
			return 1;
		}

		protected int getPriority() {
			return 0;
		}

		public final int compareTo(ScytheEffect e) {
			return Integer.compare(this.getPriority(), e.getPriority());
		}

	}

	private abstract static class MineSimilarEffect extends ScytheEffect {

		protected MineSimilarEffect(BlockKey bk, float rx, float ry) {
			super(bk, rx, ry);
		}

		protected boolean matchesBlock(Block src, int srcmeta, World world, int x, int y, int z, Block b2, int meta2, boolean ignoreMeta) {
			return this.matchesBlock(src, srcmeta, b2, meta2, ignoreMeta);
		}

		protected boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {
			return b == src && (ignoreMeta || srcmeta == meta);
		}

		@Override
		protected final int onBreakAt(World world, int x, int y, int z, Block b, int meta, EntityPlayer ep, ItemStack is, ItemSickleBase item, int fortune, boolean ignoreMeta) {
			int ret = 0;
			int r = this.getRange(is, item);
			int rx = MathHelper.floor_float(rangeXZ*r);
			int ry = MathHelper.floor_float(rangeY*r);
			for (int i = -rx; i <= rx; i++) {
				for (int j = -ry; j <= ry; j++) {
					for (int k = -rx; k <= rx; k++) {
						int dx = x+i;
						int dy = y+j;
						int dz = z+k;
						Block id2 = world.getBlock(dx, dy, dz);
						int meta2 = world.getBlockMetadata(dx, dy, dz);
						if (this.matchesBlock(b, meta, world, dx, dy, dz, id2, meta2, ignoreMeta)) {
							this.doDrops(world, dx, dy, dz, id2, meta2, ep, is, item, fortune);
							ReikaSoundHelper.playBreakSound(world, dx, dy, dz, id2);
							this.breakAt(world, dx, dy, dz, id2, meta2);
							ret++;
						}
					}
				}
			}
			return ret;
		}

		protected void doDrops(World world, int dx, int dy, int dz, Block id2, int meta2, EntityPlayer ep, ItemStack is, ItemSickleBase item, int fortune) {
			ArrayList<ItemStack> items = new ArrayList();
			if (this.collateItems()) {
				ReikaItemHelper.addToList(items, id2.getDrops(world, dx, dy, dz, meta2, fortune));
			}
			else if (this.allowShearing(world, dx, dy, dz, id2, meta2) && item.canActAsShears() && ItemBedrockShears.getHarvestResult(id2, meta2, ep, world, dx, dy, dz) == Result.ALLOW) {
				if (id2 instanceof IShearable) {
					ArrayList<ItemStack> li = ((IShearable)id2).onSheared(is, world, dx, dy, dz, fortune);
					item.dropItems(is, ep, world, dx+itemRand.nextDouble(), dy+itemRand.nextDouble(), dz+itemRand.nextDouble(), li);
				}
				else {
					item.dropItem(is, ep, world, dx+0.5, dy+0.5, dz+0.5, new ItemStack(id2, 1, ItemBedrockShears.getDroppedMeta(id2, meta2)));
				}
			}
			else {
				item.dropBlockAsItem(is, ep, id2, world, dx, dy, dz, meta2, fortune);
			}
			if (!items.isEmpty())
				item.dropItems(is, ep, world, dx, dy, dz, items);
		}

		protected void breakAt(World world, int x, int y, int z, Block b, int meta) {
			world.setBlockToAir(x, y, z);
		}

		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return false;
		}

		protected boolean collateItems() {
			return false;
		}

	}

	private static class IPlantableEffect extends MineSimilarEffect {

		protected IPlantableEffect(float r) {
			super(null, r, r);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return b instanceof IPlantable;
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return true;//b instanceof IShearable;
		}

		@Override
		protected int getPriority() {
			return Integer.MAX_VALUE;
		}

	}

	private static class BasicPlantEffect extends MineSimilarEffect {

		protected BasicPlantEffect(float r) {
			super(null, r, r);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			ReikaPlantHelper plant = ReikaPlantHelper.getPlant(b);
			return plant != null && plant != ReikaPlantHelper.CROP && plant != ReikaPlantHelper.SUGARCANE;
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return ReikaPlantHelper.getPlant(b) != ReikaPlantHelper.NETHERWART;
		}

	}

	private static class SugarcaneEffect extends MineSimilarEffect {

		protected SugarcaneEffect(float r) {
			super(null, r, r);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return (b instanceof BlockReed || b instanceof Reedlike) && world.getBlock(x, y-1, z) == b;
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, World world, int x, int y, int z, Block b2, int meta2, boolean ignoreMeta) {
			return super.matchesBlock(src, srcmeta, world, x, y, z, b2, meta2, true) && world.getBlock(x, y-1, z) == src;
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getCropRange();
		}

		@Override
		protected int doesDamagePerBlock(ItemStack is, int mined) {
			return Math.max(1, mined/2);
		}

	}

	private static class BasicLeafEffect extends MineSimilarEffect {

		protected BasicLeafEffect(float rx, float ry) {
			super(null, rx, ry);
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {
			return this.getTree(src, srcmeta) == this.getTree(b, meta);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return this.getTree(b, meta) != null;
		}

		private TreeType getTree(Block b, int meta) {
			TreeType t = ReikaTreeHelper.getTreeFromLeaf(b, meta);
			if (t == null)
				t = ModWoodList.getModWoodFromLeaf(b, meta);
			return t;
		}

		@Override
		protected int doesDamagePerBlock(ItemStack is, int mined) {
			return Math.max(1, mined/12);
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getLeafRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return false;
		}

	}

	private static class BasicCropEffect extends MineSimilarEffect {

		protected BasicCropEffect(float r) {
			super(null, r, r/3F);
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, World world, int x, int y, int z, Block b2, int meta2, boolean ignoreMeta) {
			CropType c = this.getCrop(b2, meta2);
			return this.getCrop(src, srcmeta) == c && c.isRipe(world, x, y, z);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			CropType c = this.getCrop(b, meta);
			return c != null && c.isRipe(world, x, y, z);
		}

		private CropType getCrop(Block b, int meta) {
			CropType t = ReikaCropHelper.getCrop(b);
			if (t == null)
				t = ModCropList.getModCrop(b, meta);
			return t;
		}

		@Override
		protected int doesDamagePerBlock(ItemStack is, int mined) {
			return Math.max(1, mined/2);
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getCropRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return false;
		}

		@Override
		protected void doDrops(World world, int x, int y, int z, Block b, int meta, EntityPlayer ep, ItemStack is, ItemSickleBase item, int fortune) {
			CropType c = this.getCrop(b, meta);
			ArrayList<ItemStack> li = c.getDrops(world, x, y, z, fortune);
			if (!c.destroyOnHarvest())
				CropMethods.removeOneSeed(c, li);
			item.dropItems(is, ep, world, x, y, z, li);
		}

		@Override
		protected void breakAt(World world, int x, int y, int z, Block b, int meta) {
			CropType c = this.getCrop(b, meta);
			if (c.destroyOnHarvest())
				super.breakAt(world, x, y, z, b, meta);
			else
				c.setHarvested(world, x, y, z);
		}

	}

	private static class DyeLeafEffect extends MineSimilarEffect {

		protected DyeLeafEffect(float r) {
			super(null, r, r);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return b == ChromatiAPI.getAPI().trees().getDyeLeaf(true) || b == ChromatiAPI.getAPI().trees().getDyeLeaf(false);
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {  //never jump metas
			return (b == ChromatiAPI.getAPI().trees().getDyeLeaf(false) || b == ChromatiAPI.getAPI().trees().getDyeLeaf(true)) && srcmeta == meta;
		}

		@Override
		protected int doesDamagePerBlock(ItemStack is, int mined) {
			return Math.max(1, mined/12);
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getLeafRange();
		}

	}

	private static class RainbowLeafEffect extends MineSimilarEffect {

		protected RainbowLeafEffect(float r) {
			super(null, r, r);
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {  //always jump metas
			return b == ChromatiAPI.getAPI().trees().getRainbowLeaf();
		}

		@Override
		protected int doesDamagePerBlock(ItemStack is, int mined) {
			return Math.max(1, mined/9);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return b == ChromatiAPI.getAPI().trees().getRainbowLeaf();
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getLeafRange();
		}

		@Override
		protected boolean collateItems() {
			return true;
		}

	}

	private static class DecoFlowerEffect extends MineSimilarEffect {

		protected DecoFlowerEffect(float r) {
			super(null, r, r);
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {  //never jump metas
			return b == ChromatiAPI.getAPI().trees().getDecoFlower() && srcmeta == meta;
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return b == ChromatiAPI.getAPI().trees().getDecoFlower();
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return true;
		}

	}

	private static class AppleEffect extends MineSimilarEffect {

		protected AppleEffect(float r) {
			super(null, r, r/5F);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return this.matchesBlock(b, meta, b, meta, false);
		}

		@Override
		protected boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {
			if (meta < 2)
				return false;
			String n = b.getClass().getName().toLowerCase(Locale.ENGLISH);
			return n.startsWith("growthcraft.apples") && n.endsWith("apple");
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getCropRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return false;
		}

	}

	private static abstract class BlockMatchEffect extends MineSimilarEffect {

		protected BlockMatchEffect(float rx, float ry) {
			super(null, rx, ry);
		}

		@Override
		protected boolean isValidStartingBlock(World world, int x, int y, int z, Block b, int meta) {
			return this.matchBlock(b);
		}

		@Override
		protected final boolean matchesBlock(Block src, int srcmeta, Block b, int meta, boolean ignoreMeta) {
			return this.matchBlock(b) && (ignoreMeta || meta == srcmeta);
		}

		protected abstract boolean matchBlock(Block b);

	}

	private static class DyeFlowerEffect extends BlockMatchEffect {

		protected DyeFlowerEffect(float r) {
			super(r, r);
		}

		@Override
		protected boolean matchBlock(Block b) {
			return ChromatiAPI.getAPI().trees().getDyeFlower() == b;
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return true;
		}

	}

	private static class BopFlowerEffect extends BlockMatchEffect {

		protected BopFlowerEffect(float r) {
			super(r, r);
		}

		@Override
		protected boolean matchBlock(Block b) {
			return BoPBlockHandler.getInstance().isFlower(b);
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return true;
		}

	}

	private static class BopCoralEffect extends BlockMatchEffect {

		protected BopCoralEffect(float r) {
			super(r, r);
		}

		@Override
		protected boolean matchBlock(Block b) {
			return BoPBlockHandler.getInstance().isCoral(b);
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return true;
		}

	}

	private static class BopFoliageEffect extends BlockMatchEffect {

		protected BopFoliageEffect(float r) {
			super(r, r);
		}

		@Override
		protected boolean matchBlock(Block b) {
			return BoPBlockHandler.getInstance().foliage == b;
		}

		@Override
		protected int getRange(ItemStack is, ItemSickleBase item) {
			return item.getPlantRange();
		}

		@Override
		protected boolean allowShearing(World world, int x, int y, int z, Block b, int meta) {
			return true;
		}

	}
}
