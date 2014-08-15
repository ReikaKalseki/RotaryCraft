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

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;

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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum MobBait {

	CREEPER(EntityCreeper.class, 50, Blocks.tnt, Blocks.obsidian),
	ENDERMAN(EntityEnderman.class, 58, Blocks.grass, Blocks.soul_sand),
	ZOMBIE(EntityZombie.class, 54, Items.golden_apple, 0, Items.potionitem, 8197), //instant health potion
	SKELETON(EntitySkeleton.class, 51, Items.saddle, Blocks.glowstone),
	SPIDER(EntitySpider.class, 52, Items.dye, ReikaItemHelper.cocoaBeans.getItemDamage(), Blocks.fence, -1),
	SLIME(EntitySlime.class, 55, Items.diamond, Blocks.ice),
	BLAZE(EntityBlaze.class, 61, Items.fire_charge, Items.water_bucket),
	PIGZOMBIE(EntityPigZombie.class, 57, Items.apple, Items.blaze_rod),
	LAVASLIME(EntityMagmaCube.class, 62, Items.blaze_powder, Items.snowball),
	GHAST(EntityGhast.class, 56, Items.glowstone_dust, Items.bow),
	SILVERFISH(EntitySilverfish.class, 60, Blocks.stonebrick, Items.wooden_pickaxe),
	VILLAGER(EntityVillager.class, 120, Items.emerald, Blocks.cactus),
	IRONGOLEM(EntityIronGolem.class, 99, Blocks.wool, ReikaItemHelper.redWool.getItemDamage(), Items.lava_bucket, -1),
	WITCH(EntityWitch.class, 66, Items.nether_wart, Items.ender_pearl),
	SNOWGOLEM(EntitySnowman.class, 97, Items.carrot, Blocks.torch),
	BAT(EntityBat.class, 65, Items.melon, Blocks.noteblock),
	COW(EntityCow.class, 92, Items.wheat, Items.stick),
	PIG(EntityPig.class, 90, Items.carrot, Items.stick),
	SHEEP(EntitySheep.class, 91, Items.wheat, Items.stick),
	CHICKEN(EntityChicken.class, 93, Items.wheat_seeds, Items.stick),
	OCELOT(EntityOcelot.class, 98, Items.fish, Items.stick),
	WOLF(EntityWolf.class, 95, Items.porkchop, Blocks.gravel),
	SQUID(EntitySquid.class, 94, Items.gold_ingot, Blocks.waterlily);

	private Class entityClass;
	private Item attractorID;
	private int attractorMetadata;
	private Item repellentID;
	private int repellentMetadata;
	private int entityID;

	public static final MobBait[] baitList = MobBait.values();

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Item a, Item r) {
		this(cl, id, a, -1, r, -1);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Block a, Item r) {
		this(cl, id, Item.getItemFromBlock(a), -1, r, -1);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Item a, Block r) {
		this(cl, id, a, -1, Item.getItemFromBlock(r), -1);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Block a, Block r) {
		this(cl, id, Item.getItemFromBlock(a), -1, Item.getItemFromBlock(r), -1);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Block a, int am, Item r, int rm) {
		this(cl, id, Item.getItemFromBlock(a), am, r, rm);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Item a, int am, Block r, int rm) {
		this(cl, id, a, am, Item.getItemFromBlock(r), rm);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Block a, int am, Block r, int rm) {
		this(cl, id, Item.getItemFromBlock(a), am, Item.getItemFromBlock(r), rm);
	}

	private MobBait(Class<? extends EntityLivingBase> cl, int id, Item a, int am, Item r, int rm) {
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
		//ReikaChatHelper.write("Entity "+e.getCommandSenderName()+" has no mapped bait items!");
		return strict != null ? strict : len;
	}

	public Item getAttractorItemID() {
		return attractorID;
	}

	public int getAttractorItemDamage() {
		return attractorMetadata;
	}

	public Item getRepellentItemID() {
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

	public String getCommandSenderName() {
		return (String)EntityList.stringToClassMapping.get(entityClass);
	}

}