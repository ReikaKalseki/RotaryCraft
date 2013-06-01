package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;

public enum MobBait {

	CREEPER(EntityCreeper.class, Block.tnt.blockID, Block.obsidian.blockID),
	ENDERMAN(EntityEnderman.class, Block.grass.blockID, Block.slowSand.blockID),
	ZOMBIE(EntityZombie.class, Item.appleGold.itemID, 0, Item.potion.itemID, 8197), //instant health potion
	SKELETON(EntitySkeleton.class, Item.saddle.itemID, Block.glowStone.blockID),
	SPIDER(EntitySpider.class, ReikaItemHelper.cocoaBeans.itemID, ReikaItemHelper.cocoaBeans.getItemDamage(), Block.fence.blockID, -1),
	SLIME(EntitySlime.class, Item.diamond.itemID, Block.ice.blockID),
	BLAZE(EntityBlaze.class, Item.fireballCharge.itemID, Item.bucketWater.itemID),
	PIGZOMBIE(EntityPigZombie.class, Item.appleRed.itemID, Item.blazeRod.itemID),
	LAVASLIME(EntityMagmaCube.class, Item.blazePowder.itemID, Item.snowball.itemID),
	GHAST(EntityGhast.class, Item.lightStoneDust.itemID, Item.bow.itemID),
	SILVERFISH(EntitySilverfish.class, Block.stoneBrick.blockID, Item.pickaxeWood.itemID),
	VILLAGER(EntityVillager.class, Item.emerald.itemID, Block.cactus.blockID),
	IRONGOLEM(EntityIronGolem.class, ReikaItemHelper.redWool.itemID, ReikaItemHelper.redWool.getItemDamage(), Item.bucketLava.itemID, -1),
	WITCH(EntityWitch.class, Item.netherStalkSeeds.itemID, Item.enderPearl.itemID),
	SNOWGOLEM(EntitySnowman.class, Item.carrot.itemID, Block.torchWood.blockID),
	BAT(EntityBat.class, Item.melon.itemID, Block.music.blockID),
	COW(EntityCow.class, Item.wheat.itemID, Item.stick.itemID),
	PIG(EntityPig.class, Item.carrot.itemID, Item.stick.itemID),
	SHEEP(EntitySheep.class, Item.wheat.itemID, Item.stick.itemID),
	CHICKEN(EntityChicken.class, Item.seeds.itemID, Item.stick.itemID),
	OCELOT(EntityOcelot.class, Item.fishRaw.itemID, Item.stick.itemID),
	WOLF(EntityWolf.class, Item.porkRaw.itemID, Block.gravel.blockID),
	SQUID(EntitySquid.class, Item.ingotGold.itemID, Block.waterlily.blockID);

	private Class entityClass;
	private int attractorID;
	private int attractorMetadata;
	private int repellentID;
	private int repellentMetadata;

	public static final MobBait[] baitList = MobBait.values();

	private MobBait(Class<? extends EntityLiving> cl, int a, int r) {
		entityClass = cl;
		attractorID = a;
		repellentID = r;
		attractorMetadata = -1;
		repellentMetadata = -1;
	}

	private MobBait(Class<? extends EntityLiving> cl, int a, int am, int r, int rm) {
		entityClass = cl;
		attractorID = a;
		repellentID = r;
		attractorMetadata = am;
		repellentMetadata = rm;
	}

	public static MobBait getEntryFromEntity(EntityLiving e) {
		Class c = e.getClass();
		for (int i = 0; i < baitList.length; i++) {
			if (c == baitList[i].entityClass)
				return baitList[i];
		}
		ReikaChatHelper.write("Entity "+e.getEntityName()+" has no mapped bait items!");
		return null;
	}

	public int getAttractorItemID() {
		return attractorID;
	}

	public int getAttractorItemDamage() {
		return attractorMetadata;
	}

	public int getRepellentItemID() {
		return repellentID;
	}

	public int getRepellentItemDamage() {
		return repellentMetadata;
	}

	public ItemStack getAttractorItemStack() {
		if (this.isMetadataValuedAttractor())
			return new ItemStack(attractorID, 1, attractorMetadata);
		return new ItemStack(attractorID, 1, 0);
	}

	public ItemStack getRepellentItemStack() {
		if (this.isMetadataValuedRepellent())
			return new ItemStack(repellentID, 1, repellentMetadata);
		return new ItemStack(repellentID, 1, 0);
	}

	public static ItemStack getEntityAttractor(EntityLiving e) {
		MobBait mb = getEntryFromEntity(e);
		if (mb.isMetadataValuedAttractor())
			return new ItemStack(mb.attractorID, 1, mb.attractorMetadata);
		return new ItemStack(mb.attractorID, 1, 0);
	}

	public static ItemStack getEntityRepellent(EntityLiving e) {
		MobBait mb = getEntryFromEntity(e);
		if (mb.isMetadataValuedRepellent())
			return new ItemStack(mb.repellentID, 1, mb.repellentMetadata);
		return new ItemStack(mb.repellentID, 1, 0);
	}

	public boolean isMetadataValuedAttractor() {
		return attractorMetadata != -1;
	}

	public boolean isMetadataValuedRepellent() {
		return attractorMetadata != -1;
	}

	public static List<ItemStack> getAllAttractorItems() {
		List<ItemStack> l = new ArrayList<ItemStack>();
		for (int i = 0; i < baitList.length; i++) {
			l.add(baitList[i].getAttractorItemStack());
		}
		return l;
	}

	public static List<ItemStack> getAllRepellentItems() {
		List<ItemStack> l = new ArrayList<ItemStack>();
		for (int i = 0; i < baitList.length; i++) {
			l.add(baitList[i].getRepellentItemStack());
		}
		return l;
	}

	public static boolean isValidItem(ItemStack is) {
		List att = getAllAttractorItems();
		List rep = getAllRepellentItems();
		return (att.contains(is) || rep.contains(is));
	}

	public static boolean hasRepelItem(EntityLiving e, ItemStack[] inv) {
		if (!isAffectableEntity(e))
			return false;
		ItemStack is = getEntityRepellent(e);
		return ReikaInventoryHelper.checkForItemStack(is, inv, false);
	}

	public static boolean hasAttractItem(EntityLiving e, ItemStack[] inv) {
		if (!isAffectableEntity(e))
			return false;
		ItemStack is = getEntityAttractor(e);
		return ReikaInventoryHelper.checkForItemStack(is, inv, false);
	}

	public static boolean isAffectableEntity(EntityLiving e) {
		Class c = e.getClass();
		for (int i = 0; i < baitList.length; i++) {
			if (c == baitList[i].entityClass)
				return true;
		}
		return false;
	}

}
