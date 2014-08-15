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

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityAutoBreeder extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation {

	public static final int FALLOFF = 2048; //2kW per extra meter

	public boolean idle = false;

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

	public void testIdle() {
		boolean wheat = false;
		boolean carrot = false;
		boolean meat = false;
		boolean fish = false;
		boolean seed = false;
		wheat = ReikaInventoryHelper.checkForItem(Items.wheat, inv);
		carrot = ReikaInventoryHelper.checkForItem(Items.carrot, inv);
		meat = ReikaInventoryHelper.checkForItem(Items.porkchop, inv);
		fish = ReikaInventoryHelper.checkForItem(Items.fish, inv);
		seed = ReikaInventoryHelper.checkForItem(Items.wheat_seeds, inv);
		idle = (!wheat && !carrot && !meat && !fish && !seed);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		//ReikaChunkHelper.emptyChunk(world, x, z);
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		this.testIdle();
		List inrange = this.getEntities(world, x, y, z, EntityAnimal.class);
		this.breed(world, x, y, z, inrange);
	}

	private boolean canBreed(EntityAnimal ent) {
		ItemStack item = null;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				item = new ItemStack(inv[i].getItem(), 1, inv[i].getItemDamage());
				if (ent.isBreedingItem(item)) {
					return true;
				}
			}
		}
		return false;
	}

	private int getFeedItem(EntityAnimal ent) {
		ItemStack item = ReikaEntityHelper.getBreedItem(ent);
		int slot = ReikaInventoryHelper.locateInInventory(item, inv, false);
		return slot;
	}

	private void useFeedItem(EntityAnimal ent) {
		int slot = this.getFeedItem(ent);
		if (slot == -1)
			return;
		if (inv[slot] == null)
			return;
		ReikaInventoryHelper.decrStack(slot, inv);
	}

	private void breed(World world, int x, int y, int z, List inroom) {
		boolean pathing = false;
		if (tickcount >= 20) {
			tickcount = 0;
			pathing = true;
		}
		for (int i = 0; i < inroom.size(); i++) {
			EntityAnimal ent = (EntityAnimal)inroom.get(i);
			//ReikaJavaLibrary.pConsole(this.canBreed(ent)+" for "+ent.getCommandSenderName());
			if (this.canBreed(ent)) {
				if (!(ent instanceof EntityTameable) || (ent instanceof EntityTameable && !((EntityTameable)ent).isSitting())) {
					if (!ent.isInLove() && !ent.isChild() && pathing && ent.getGrowingAge() == 0) {
						ent.getNavigator().clearPathEntity();
						PathEntity path = ent.getNavigator().getPathToXYZ(x, y, z);
						ent.getNavigator().setPath(path, 1F);
					}
					else if (pathing) {
						ent.getNavigator().clearPathEntity();
					}
				}
				if (!ent.isChild() && ent.getGrowingAge() <= 0 && ReikaMathLibrary.py3d(x-ent.posX, y-ent.posY, z-ent.posZ) <= 2.4) {
					if (!(ent instanceof EntityTameable) || (ent instanceof EntityTameable && ((EntityTameable)ent).isTamed())) {
						if (!ent.isInLove())
							this.useFeedItem(ent);
						ent.inLove = 600;
						for (int var3 = 0; var3 < 1; ++var3)
						{
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

	private List getEntities(World world, int x, int y, int z, Class entity) {
		AxisAlignedBB room = this.getBox(x, y, z, this.getRange());
		List inroom = world.getEntitiesWithinAABB(entity, room);
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
		Item i = is.getItem();
		return (i == Items.wheat || i == Items.carrot || i == Items.fish || i == Items.wheat_seeds || i == Items.porkchop);
	}

	@Override
	public int getRedstoneOverride() {
		if (idle)
			return 15;
		return 0;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Items";
	}

}