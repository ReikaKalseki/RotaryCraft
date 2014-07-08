/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMachineGun extends InventoriedPowerReceiver implements RangedEffect, EnchantableMachine, DiscreteFunction {

	private HashMap<Enchantment, Integer> enchantments = new HashMap();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		if (DragonAPICore.debugtest) {
			ReikaInventoryHelper.addToIInv(Item.arrow, this);
		}

		//ReikaJavaLibrary.pConsole(tickcount+"/"+this.getFireRate()+":"+ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv));

		if (tickcount >= this.getOperationTime() && ReikaInventoryHelper.checkForItem(Item.arrow.itemID, inv)) {
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
		if (e.getEntityName().equals("Reika_Kalseki")) {
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
		return ReikaInventoryHelper.locateIDInInventory(Item.arrow.itemID, this);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is.itemID == Item.arrow.itemID;
	}

	public int getSizeInventory() {
		return 27;
	}

	private double getFirePower() {
		return this.getEnchantment(Enchantment.power)*0.5+ReikaMathLibrary.logbase(torque+1, 2);
	}

	public int getOperationTime() {
		return ReikaMathLibrary.extrema(16-(int)ReikaMathLibrary.logbase(omega+1, 2), 4, "max");
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
		if (!this.hasEnchantment(Enchantment.infinity))
			ReikaInventoryHelper.decrStack(this.getArrowSlot(), inv);
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.bow", 1, 1);
	}

	private AxisAlignedBB drawAABB(World world, int x, int y, int z, int meta) {
		double d = 0.1;
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1).contract(d, d, d);
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
	public boolean applyEnchants(ItemStack is) {
		boolean accepted = false;
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.infinity, is)) {
			enchantments.put(Enchantment.infinity, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.infinity, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.power, is)) {
			enchantments.put(Enchantment.power, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.power, is));
			accepted = true;
		}
		return accepted;
	}

	public HashMap<Enchantment,Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantments().containsKey(e);
	}

	@Override
	public int getEnchantment(Enchantment e) {
		if (!this.hasEnchantment(e))
			return 0;
		else
			return this.getEnchantments().get(e);
	}

	@Override
	public boolean hasEnchantments() {
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				if (this.getEnchantment(Enchantment.enchantmentsList[i]) > 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList();
		li.add(Enchantment.infinity);
		li.add(Enchantment.power);
		return li;
	}

}
