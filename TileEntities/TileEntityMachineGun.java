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

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMachineGun extends TileEntityInventoriedPowerReceiver implements RangedEffect {

	private StepTimer timer = new StepTimer(5); // controls max fire rate

	private ItemStack[] inv = new ItemStack[27];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false, false);

		timer.update();
		if (timer.checkCap() && ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv)) {
			AxisAlignedBB box = this.drawAABB(x, y, z, meta);
			List<EntityLiving> li = world.getEntitiesWithinAABB(EntityLiving.class, box);
			if (li.size() > 0) {
				this.fire(world, x, y, z, meta);
			}
		}
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	private int getArrowSlot() {
		return ReikaInventoryHelper.locateIDInInventory(Item.arrow.itemID, this);
	}

	public ItemStack getStackInSlot(int sl) {
		return inv[sl];
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is) {
		return is.itemID == Item.arrow.itemID;
	}

	public int getSizeInventory() {
		return 27;
	}

	private void fire(World world, int x, int y, int z, int meta) {
		double vx = 0;
		double vz = 0;
		double v = 2;
		switch(meta) {
		case 0:
			x++;
			vx = v;
			break;
		case 1:
			x--;
			vx = -v;
			break;
		case 2:
			z++;
			vz = v;
			break;
		case 3:
			z--;
			vz = -v;
			break;
		}
		EntityArrow ar = new EntityArrow(world);
		ar.setLocationAndAngles(x, y, z, 0, 0);
		ar.motionX = vx;
		ar.motionZ = vz;
		if (!world.isRemote) {
			ar.velocityChanged = true;
			world.spawnEntityInWorld(ar);
		}
		ReikaInventoryHelper.decrStack(this.getArrowSlot(), inv);
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.bow", 1, 1);
	}

	private AxisAlignedBB drawAABB(int x, int y, int z, int meta) {
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1);
		switch(meta) {
		case 0:
			box.offset(1, 0, 0);
			box.maxX += this.getRange();
			break;
		case 1:
			box.offset(-1, 0, 0);
			box.minX -= this.getRange();
			break;
		case 2:
			box.offset(0, 0, 1);
			box.maxZ += this.getRange();
			break;
		case 3:
			box.offset(0, 0, -1);
			box.minZ -= this.getRange();
			break;
		}

		return box;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ARROWGUN.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRange() {
		return 8;
	}

	@Override
	public int getMaxRange() {
		return 16;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int getRedstoneOverride() {
		return Container.calcRedstoneFromInventory(this);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

}
