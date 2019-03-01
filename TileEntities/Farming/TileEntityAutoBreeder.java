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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap.CollectionType;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.IdleComparator;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAutoBreeder extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation, IdleComparator {

	public static final int FALLOFF = 2048; //2kW per extra meter

	private static final MultiMap<Class, KeyedItemStack> feedItems = new MultiMap(CollectionType.HASHSET);

	@Override
	public boolean isIdle() {
		for (int i = 0; i < inv.length; i++) {
			ItemStack in = inv[i];
			if (in != null) {
				if (feedItems.containsValue(new KeyedItemStack(in).setSimpleHash(true))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public int getRange() {
		int range = 8+(int)((power-MINPOWER)/FALLOFF);
		if (range > this.getMaxRange())
			return this.getMaxRange();
		return range;
	}

	public int getMaxRange() {
		return Math.max(24, ConfigRegistry.BREEDERRANGE.getValue());
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		//ReikaChunkHelper.emptyChunk(world, x, z);
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		List<EntityAgeable> inrange = this.getEntities(world, x, y, z);
		this.breed(world, x, y, z, inrange);
	}

	private boolean canBreed(Entity ent) {
		return this.getFeedItem(ent) >= 0;
	}

	private int getFeedItem(Entity ent) {
		for (KeyedItemStack kis : feedItems.get(ent.getClass())) {
			int slot = ReikaInventoryHelper.locateInInventory(kis, inv);
			if (slot != -1)
				return slot;
		}
		return -1;
	}

	private void useFeedItem(Entity ent) {
		int slot = this.getFeedItem(ent);
		if (slot == -1)
			return;
		if (inv[slot] == null)
			return;
		ReikaInventoryHelper.decrStack(slot, inv);
	}

	private void breed(World world, int x, int y, int z, List<EntityAgeable> inroom) {
		boolean pathing = false;
		if (tickcount >= 20) {
			tickcount = 0;
			pathing = true;
		}
		for (EntityAgeable ent : inroom) {
			//ReikaJavaLibrary.pConsole(this.canBreed(ent)+" for "+ent.getCommandSenderName());
			if (this.canBreed(ent)) {
				if (!(ent instanceof EntityTameable) || (ent instanceof EntityTameable && !((EntityTameable)ent).isSitting())) {
					if (pathing) {
						if (this.canDirectToMate(ent)) {
							ent.getNavigator().clearPathEntity();
							PathEntity path = ent.getNavigator().getPathToXYZ(x, y, z);
							ent.getNavigator().setPath(path, 1F);
						}
						else {
							ent.getNavigator().clearPathEntity();
						}
					}
				}
				if (!ent.isChild() && ent.getGrowingAge() <= 0 && ReikaMathLibrary.py3d(x-ent.posX, y-ent.posY, z-ent.posZ) <= 2.4) {
					if (!(ent instanceof EntityTameable) || (ent instanceof EntityTameable && ((EntityTameable)ent).isTamed())) {
						this.breed(ent);
						int n = 1+rand.nextInt(3);
						for (int i = 0; i < n; i++) {
							double var4 = rand.nextGaussian() * 0.02D;
							double var6 = rand.nextGaussian() * 0.02D;
							double var8 = rand.nextGaussian() * 0.02D;
							ent.worldObj.spawnParticle("heart", ent.posX + rand.nextFloat() * ent.width * 2.0F - ent.width, ent.posY + 0.5D + rand.nextFloat() * ent.height, ent.posZ + rand.nextFloat() * ent.width * 2.0F - ent.width, var4, var6, var8);
						}
					}
				}
			}
		}
	}

	private boolean canDirectToMate(EntityAgeable ent) {
		if (ent instanceof EntityAnimal && ((EntityAnimal)ent).isInLove())
			return false;
		if (ent instanceof EntityVillager && ((EntityVillager)ent).isMating())
			return false;
		return !ent.isChild() && ent.getGrowingAge() == 0;
	}

	private void breed(EntityAgeable e) {
		boolean flag = false;
		if (e instanceof EntityAnimal) {
			EntityAnimal ent = (EntityAnimal)e;
			if (!ent.isInLove()) {
				ent.inLove = 600;
				flag = true;
			}
		}
		else if (e instanceof EntityVillager) {
			EntityVillager ent = (EntityVillager)e;
			if (!ent.isMating()) {
				ent.setMating(true);
				flag = true;
			}
		}
		if (flag)
			this.useFeedItem(e);
	}

	private List<EntityAgeable> getEntities(World world, int x, int y, int z) {
		AxisAlignedBB room = this.getBox(x, y, z, this.getRange());
		List inroom = world.getEntitiesWithinAABB(EntityAgeable.class, room);
		return inroom;
	}

	private AxisAlignedBB getBox(int x, int y, int z, int range) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		return box;
	}

	private AxisAlignedBB getRoom(World world, int x, int y, int z) {
		int minx = x;
		int maxx = x;
		int miny = y;
		int maxy = y;
		int minz = z;
		int maxz = z;

		boolean exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (world.getBlock(x+i+1, y, z).isOpaqueCube())
				exit = true;
			else
				maxx = x+i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (world.getBlock(x-i, y, z).isOpaqueCube())
				exit = true;
			else
				minx = x-i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (world.getBlock(x, y+i+1, z).isOpaqueCube())
				exit = true;
			else
				maxy = y+i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (world.getBlock(x, y-i, z).isOpaqueCube())
				exit = true;
			else
				miny = x-i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (world.getBlock(x, y, z+i+1).isOpaqueCube())
				exit = true;
			else
				maxz = z+i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (world.getBlock(x, y, z-i).isOpaqueCube())
				exit = true;
			else
				minz = z-i;
		}
		exit = false;
		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.AUTOBREEDER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return feedItems.containsValue(new KeyedItemStack(is).setSimpleHash(true));
	}

	@Override
	public int getRedstoneOverride() {
		return this.isIdle() ? 15 : 0;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Items";
	}

	static {
		addFeedItem(EntitySheep.class, Items.wheat);
		addFeedItem(EntityCow.class, Items.wheat);
		addFeedItem(EntityChicken.class, Items.wheat_seeds);
		addFeedItem(EntityPig.class, Items.carrot);
		addFeedItem(EntityWolf.class, Items.porkchop);
		addFeedItem(EntityWolf.class, Items.cooked_porkchop);
		addFeedItem(EntityWolf.class, Items.beef);
		addFeedItem(EntityWolf.class, Items.cooked_beef);
		addFeedItem(EntityWolf.class, Items.chicken);
		addFeedItem(EntityWolf.class, Items.cooked_chicken);
		addFeedItem(EntityWolf.class, Items.fish);
		addFeedItem(EntityWolf.class, Items.cooked_fished);
		addFeedItem(EntityOcelot.class, Items.fish);
		addFeedItem(EntityOcelot.class, Items.cooked_fished);

		addFeedItem(EntitySheep.class, ItemStacks.canolaHusks);
		addFeedItem(EntityCow.class, ItemStacks.canolaHusks);
		addFeedItem(EntityChicken.class, ItemStacks.canolaHusks);
		addFeedItem(EntityPig.class, ItemStacks.canolaHusks);

		if (ModList.TWILIGHT.isLoaded()) {
			try {
				addFeedItem(Class.forName("twilightforest.entity.passive.EntityTFBighorn"), Items.wheat);
				addFeedItem(Class.forName("twilightforest.entity.passive.EntityTFBighorn"), ItemStacks.canolaHusks);
				addFeedItem(Class.forName("twilightforest.entity.passive.EntityTFDeer"), Items.wheat);
				addFeedItem(Class.forName("twilightforest.entity.passive.EntityTFDeer"), ItemStacks.canolaHusks);
			}
			catch (Exception e) {
				e.printStackTrace();
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.TWILIGHT, e);
			}
		}

		addFeedItem(EntityVillager.class, Items.emerald);
	}

	private static void addFeedItem(Class entity, ItemStack food) {
		addFeedItem(entity, new KeyedItemStack(food));
	}

	private static void addFeedItem(Class entity, Item food) {
		addFeedItem(entity, new KeyedItemStack(food));
	}

	private static void addFeedItem(Class entity, KeyedItemStack food) {
		food.setSimpleHash(true).setSized(false);
		feedItems.addValue(entity, food);
	}

}
