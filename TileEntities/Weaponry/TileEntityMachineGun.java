/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.ChromatiCraft.API.AdjacencyUpgradeAPI;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMachineGun extends InventoriedPowerReceiver implements RangedEffect, EnchantableMachine, DiscreteFunction {

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.infinity).addFilter(Enchantment.power);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		if (DragonAPICore.debugtest) {
			ReikaInventoryHelper.addToIInv(Items.arrow, this);
		}

		//ReikaJavaLibrary.pConsole(tickcount+"/"+this.getFireRate()+":"+ReikaInventoryHelper.checkForItem(Items.arrow.itemID, inv));

		if (tickcount >= this.getOperationTime() && ReikaInventoryHelper.checkForItem(Items.arrow, inv)) {
			AxisAlignedBB box = this.drawAABB(world, x, y, z, meta);
			List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
			if (li.size() > 0 && !ReikaEntityHelper.allAreDead(li, false) && !this.isReikaOnly(li)) {
				this.fire(world, x, y, z, meta);
			}
			tickcount = 0;
		}
	}

	private boolean isReikaOnly(List<EntityLivingBase> li) {
		if (li.size() != 1)
			return false;
		EntityLivingBase e = li.get(0);
		if (!(e instanceof EntityPlayer))
			return false;
		if (ReikaPlayerAPI.isReika((EntityPlayer)e)) {
			return !((EntityPlayer)e).capabilities.isCreativeMode;
		}
		return false;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
			case 1:
				read = ForgeDirection.WEST;
				break;
			case 0:
				read = ForgeDirection.EAST;
				break;
			case 2:
				read = ForgeDirection.NORTH;
				break;
			case 3:
				read = ForgeDirection.SOUTH;
				break;
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	private int getArrowSlot() {
		return ReikaInventoryHelper.locateIDInInventory(Items.arrow, this);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is.getItem() == Items.arrow;
	}

	public int getSizeInventory() {
		return 27;
	}

	private double getFirePower() {
		return enchantments.getEnchantment(Enchantment.power)*0.5+ReikaMathLibrary.logbase(torque+1, 2);
	}

	public int getOperationTime() {
		return Math.max(16-(int)ReikaMathLibrary.logbase(omega+1, 2), 4);
	}

	private void fire(World world, int x, int y, int z, int meta) {
		double vx = 0;
		double vz = 0;
		double v = this.getFirePower();
		if (ModList.CHROMATICRAFT.isLoaded()) {
			v *= AdjacencyUpgradeAPI.getFactorSimple(worldObj, xCoord, yCoord, zCoord, "PINK");
		}
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
		if (!enchantments.hasEnchantment(Enchantment.infinity))
			ReikaInventoryHelper.decrStack(this.getArrowSlot(), inv);
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.bow", 1, 1);
	}

	private AxisAlignedBB drawAABB(World world, int x, int y, int z, int meta) {
		double d = 0.1;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).contract(d, d, d);
		int r = this.getRange();
		switch(meta) {
			case 1:
				box.offset(1, 0, 0);
				box.maxX += Math.min(ReikaWorldHelper.getFreeDistance(world, x, y, z, ForgeDirection.EAST, r), r);
				break;
			case 0:
				box.offset(-1, 0, 0);
				box.minX -= Math.min(ReikaWorldHelper.getFreeDistance(world, x, y, z, ForgeDirection.WEST, r), r);
				break;
			case 2:
				box.offset(0, 0, 1);
				box.maxZ += Math.min(ReikaWorldHelper.getFreeDistance(world, x, y, z, ForgeDirection.SOUTH, r), r);
				break;
			case 3:
				box.offset(0, 0, -1);
				box.minZ -= Math.min(ReikaWorldHelper.getFreeDistance(world, x, y, z, ForgeDirection.NORTH, r), r);
				break;
		}

		return box;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.ARROWGUN;
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
		return 10+2*(int)ReikaMathLibrary.logbase(torque+1, 2);
	}

	@Override
	public int getRedstoneOverride() {
		return Container.calcRedstoneFromInventory(this);
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setTag("enchants", enchantments.writeToNBT());
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));
	}

}
