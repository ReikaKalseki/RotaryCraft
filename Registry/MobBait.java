/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
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
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;

public enum MobBait {

	CREEPER(EntityCreeper.class, 50, Block.tnt.blockID, Block.obsidian.blockID),
	ENDERMAN(EntityEnderman.class, 58, Block.grass.blockID, Block.slowSand.blockID),
	ZOMBIE(EntityZombie.class, 54, Item.appleGold.itemID, 0, Item.potion.itemID, 8197), //instant health potion
	SKELETON(EntitySkeleton.class, 51, Item.saddle.itemID, Block.glowStone.blockID),
	SPIDER(EntitySpider.class, 52, ReikaItemHelper.cocoaBeans.itemID, ReikaItemHelper.cocoaBeans.getItemDamage(), Block.fence.blockID, -1),
	SLIME(EntitySlime.class, 55, Item.diamond.itemID, Block.ice.blockID),
	BLAZE(EntityBlaze.class, 61, Item.fireballCharge.itemID, Item.bucketWater.itemID),
	PIGZOMBIE(EntityPigZombie.class, 57, Item.appleRed.itemID, Item.blazeRod.itemID),
	LAVASLIME(EntityMagmaCube.class, 62, Item.blazePowder.itemID, Item.snowball.itemID),
	GHAST(EntityGhast.class, 56, Item.glowstone.itemID, Item.bow.itemID),
	SILVERFISH(EntitySilverfish.class, 60, Block.stoneBrick.blockID, Item.pickaxeWood.itemID),
	VILLAGER(EntityVillager.class, 120, Item.emerald.itemID, Block.cactus.blockID),
	IRONGOLEM(EntityIronGolem.class, 99, ReikaItemHelper.redWool.itemID, ReikaItemHelper.redWool.getItemDamage(), Item.bucketLava.itemID, -1),
	WITCH(EntityWitch.class, 66, Item.netherStalkSeeds.itemID, Item.enderPearl.itemID),
	SNOWGOLEM(EntitySnowman.class, 97, Item.carrot.itemID, Block.torchWood.blockID),
	BAT(EntityBat.class, 65, Item.melon.itemID, Block.music.blockID),
	COW(EntityCow.class, 92, Item.wheat.itemID, Item.stick.itemID),
	PIG(EntityPig.class, 90, Item.carrot.itemID, Item.stick.itemID),
	SHEEP(EntitySheep.class, 91, Item.wheat.itemID, Item.stick.itemID),
	CHICKEN(EntityChicken.class, 93, Item.seeds.itemID, Item.stick.itemID),
	OCELOT(EntityOcelot.class, 98, Item.fishRaw.itemID, Item.stick.itemID),
	WOLF(EntityWolf.class, 95, Item.porkRaw.itemID, Block.gravel.blockID),
	SQUID(EntitySquid.class, 94, Item.ingotGold.itemID, Block.waterlily.blockID);

	private Class entityClass;
	private int attractorID;
	private int attractorMetadata;
	private int repellentID;
	private int repellentMetadata;
	private int entityID;

	public static final MobBait[] baitList = MobBait.values();

	private MobBait(Class<? extends EntityLivingBase> cl, int id, int a, int r) {
		entityClass = cl;
		attractorID = a;
		repellentID = r;
		attractorMetadata = -1;
		repellentMetadata = -1;
		entityID = id;
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, int a, int am, int r, int rm) {
		entityClass = cl;
		attractorID = a;
		repellentID = r;
		attractorMetadata = am;
		repellentMetadata = rm;
		entityID = id;
	}

	public static MobBait getEntryFromEntity(EntityLivingBase e) {
		Class c = e.getClass();
		MobBait strict = null;
		MobBait len = null;
		for (int i = 0; i < baitList.length; i++) {
			if (c == baitList[i].entityClass)
				strict = baitList[i];
			if (baitList[i].entityClass.isAssignableFrom(c))
				len = baitList[i];
		}
		//ReikaChatHelper.write("Entity "+e.getEntityName()+" has no mapped bait items!");
		return strict != null ? strict : len;
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

	public static ItemStack getEntityAttractor(EntityLivingBase e) {
		MobBait mb = getEntryFromEntity(e);
		if (mb.isMetadataValuedAttractor())
			return new ItemStack(mb.attractorID, 1, mb.attractorMetadata);
		return new ItemStack(mb.attractorID, 1, 0);
	}

	public static ItemStack getEntityRepellent(EntityLivingBase e) {
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

	public static boolean hasRepelItem(EntityLivingBase e, ItemStack[] inv) {
		if (!isAffectableEntity(e))
			return false;
		ItemStack is = getEntityRepellent(e);
		//ReikaJavaLibrary.pConsole(is, e instanceof EntityPigZombie);
		return ReikaInventoryHelper.checkForItemStack(is, inv, false);
	}

	public static boolean hasAttractItem(EntityLivingBase e, ItemStack[] inv) {
		if (!isAffectableEntity(e))
			return false;
		ItemStack is = getEntityAttractor(e);
		return ReikaInventoryHelper.checkForItemStack(is, inv, false);
	}

	public static boolean isAffectableEntity(EntityLivingBase e) {
		return getEntryFromEntity(e) != null;
	}

	public int getMobIconU() {
		int UNIT = 4;
		int v = 2*UNIT*(entityID/16);
		int u = 2*UNIT*(entityID-(v/UNIT/2)*16);
		return u;
	}

	public int getMobIconV() {
		int UNIT = 4;
		int v = 2*UNIT*(entityID/16);
		int u = 2*UNIT*(entityID-(v/UNIT/2)*16);
		return v;
	}

	public String getEntityName() {
		return (String)EntityList.stringToClassMapping.get(entityClass);
	}

}
