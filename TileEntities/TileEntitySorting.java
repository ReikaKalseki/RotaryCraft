/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySorting extends TileEntityPowerReceiver {

	public static final int LENGTH = 9;

	private ForgeDirection facingDir;

	private ItemStack[][] mappings = new ItemStack[LENGTH][3];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);
		AxisAlignedBB box = this.getBox();
		List<EntityItem> li = world.getEntitiesWithinAABB(EntityItem.class, box);
		this.sortItems(world, x, y, z, li);
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			facingDir = ForgeDirection.EAST;
			break;
		case 1:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			facingDir = ForgeDirection.NORTH;
			break;
		case 3:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			facingDir = ForgeDirection.SOUTH;
			break;
		}
	}

	private void sortItems(World world, int x, int y, int z, List<EntityItem> li) {
		for (int i = 0; i < li.size(); i++) {
			EntityItem ei = li.get(i);
			ItemStack is = ei.getEntityItem().copy();
			ei.setDead();
			ForgeDirection dir = this.getSideForItem(is);
			double dx = x+0.5+dir.offsetX*0.75;
			double dy = y+0.5+dir.offsetY*0.75;
			double dz = z+0.5+dir.offsetZ*0.75;
			EntityItem e = new EntityItem(world, dx, dy, dz, is);
			double v = 0.1;
			e.motionX = dir.offsetX*v;
			e.motionY = dir.offsetY*v;
			e.motionZ = dir.offsetZ*v;
			if (!world.isRemote)
				world.spawnEntityInWorld(e);
		}
	}

	private ForgeDirection getSideForItem(ItemStack is) {
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < LENGTH; k++) {
				ItemStack map = mappings[k][i];
				if (map != null) {
					Item item = is.getItem();
					Item item2 = map.getItem();
					if (item.getHasSubtypes() || item2.getHasSubtypes()) {
						if (ReikaItemHelper.matchStacks(map, is))
							return this.getDirection(k);
					}
					else {
						if (is.itemID == map.itemID)
							return this.getDirection(k);
					}
				}
			}
		}
		return ForgeDirection.DOWN;
	}

	private ForgeDirection getDirection(int index) {
		List<ForgeDirection> li = new ArrayList();
		for (int i = 2; i < 6; i++)
			li.add(dirs[i]);
		li.remove(facingDir);
		return li.get(index);
	}

	private ForgeDirection getFacingDir(int meta) {
		switch(meta) {
		case 0:
			return ForgeDirection.EAST;
		case 1:
			return ForgeDirection.WEST;
		case 2:
			return ForgeDirection.NORTH;
		case 3:
			return ForgeDirection.SOUTH;
		}
		return ForgeDirection.DOWN;
	}

	private AxisAlignedBB getBox() {
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord+1, zCoord, xCoord+1, yCoord+1.25, zCoord+1);
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SORTING.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
