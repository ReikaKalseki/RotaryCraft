/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityItemRefresher extends TileEntityPowerReceiver implements RangedEffect {

	public static final int FALLOFF = 1024;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		int range = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x-range, y-range, z-range, x+1+range, y+1+range, z+1+range);
		List items = world.getEntitiesWithinAABB(EntityItem.class, box);
		for (int i = 0; i < items.size(); i++) {
			EntityItem item = (EntityItem)items.get(i);
			if (item.age > item.lifespan-20)
				item.age = item.lifespan-20;
			if (item.motionY == 0)
				item.motionY = 0.4;
		}

	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRange() {
		return Math.min(this.getMaxRange(), 4+(int)(power-MINPOWER)/FALLOFF);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.REFRESHER;
	}

	@Override
	public int getMaxRange() {
		return 128;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
