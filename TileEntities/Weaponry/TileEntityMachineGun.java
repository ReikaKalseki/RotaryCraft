/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMachineGun extends TileEntityInventoriedPowerReceiver implements RangedEffect {

	private ItemStack[] inv = new ItemStack[27];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		//ReikaJavaLibrary.pConsole(tickcount+"/"+this.getFireRate()+":"+ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv));

		if (tickcount >= this.getFireRate() && ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv)) {
			AxisAlignedBB box = this.drawAABB(x, y, z, meta);
			List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
			if (li.size() > 0 && !ReikaEntityHelper.allAreDead(li, false)) {
				this.fire(world, x, y, z, meta);
			}
			tickcount = 0;
		}
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 1:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			break;
		case 2:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			break;
		case 3:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			break;
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
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is.itemID == Item.arrow.itemID;
	}

	public int getSizeInventory() {
		return 27;
	}

	private double getFirePower() {
		return ReikaMathLibrary.logbase(torque, 2);
	}

	private int getFireRate() {
		return ReikaMathLibrary.extrema(16-(int)ReikaMathLibrary.logbase(omega, 2), 4, "max");
	}

	private void fire(World world, int x, int y, int z, int meta) {
		double vx = 0;
		double vz = 0;
		double v = this.getFirePower();
		switch(meta) {
		case 1:
			x++;
			vx = v;
			break;
		case 0:
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
		ar.setLocationAndAngles(x+0.5, y+0.8, z+0.5, 0, 0);
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
		double d = 0.1;
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1).contract(d, d, d);
		switch(meta) {
		case 1:
			box.offset(1, 0, 0);
			box.maxX += this.getRange();
			break;
		case 0:
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
		return this.getMaxRange();
	}

	@Override
	public int getMaxRange() {
		return 10+2*(int)ReikaMathLibrary.logbase(torque, 2);
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
