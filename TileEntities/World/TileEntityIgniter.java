/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityIgniter extends InventoriedPowerReceiver implements TemperatureTE, RangedEffect, ConditionalOperation {

	public int temperature;

	public static final int ANIMALIGNITION = 280; //Fat ignition temperature
	public static final int MAXTEMP = 2500;

	public int theta;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		if (omega < MINSPEED)
			return;
		if (tickcount >= 40) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
			int fueltemp = this.getMaxFuelTemperature();
			if (temperature < fueltemp)
				this.burnFuel(fueltemp);
		}
		int spread = this.getRange();
		int yspread = this.getRange()/2;
		for (int i = 0; i < 3; i++) {
			int fx = x-spread+rand.nextInt(spread*2+1);
			int fz = z-spread+rand.nextInt(spread*2+1);
			int fy = y-yspread+rand.nextInt(yspread+1);
			this.fire(world, x, y, z, fx, fy, fz);
		}
		if (temperature < ANIMALIGNITION)
			return;
		List in = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(spread, yspread, spread));
		for (int i = 0; i < in.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)in.get(i);
			ent.setFire(1);
		}
	}

	private void fire (World world, int x, int y, int z, int fx, int fy, int fz) {
		double dd = ReikaMathLibrary.py3d(x-fx, y-fy, z-fz);
		int d = this.getRange();
		//ReikaWorldHelper.spawnParticleLine(world, x+0.5, y+0.5, z+0.5, fx+0.5, fy+0.5, fz+0.5, "flame", 0, 0, 0, 20);
		for (int i = 0; i < this.getRange()*this.getRange()/2; i++) {
			int dx = x-d+rand.nextInt(d*2+1);
			int dz = z-d+rand.nextInt(d*2+1);
			int dy = y-d/2+rand.nextInt(d/2+1);
			world.spawnParticle("flame", dx, dy+1, dz, 0, 0, 0);
			world.spawnParticle("smoke", dx, dy+1, dz, 0, 0, 0);
		}
		if (world.isRemote)
			return;
		ReikaWorldHelper.temperatureEnvironment(world, fx, fy, fz, this.getAffectiveTemperature());
	}

	private int getAffectiveTemperature() {
		return ConfigRegistry.ATTACKBLOCKS.getState() ? temperature : Math.min(400, temperature);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (temperature > Tamb) {
			int Tdiff = temperature-Tamb;
			temperature -= (int)Math.log(Tdiff);
		}
		if (temperature < Tamb) {
			int Tdiff = Tamb-temperature;
			temperature += Tdiff/24;
		}
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
	}

	private void burnFuel(int temp) {
		int slot = -1;
		int slot2 = -1;
		switch(temp) {
		case 300:
			slot = ReikaInventoryHelper.locateInInventory(Blocks.planks, inv);
			break;
		case 400:
			slot = ReikaInventoryHelper.locateInInventory(Blocks.log, inv);
			if (slot == -1)
				slot = ReikaInventoryHelper.locateInInventory(Blocks.log2, inv);
			break;
		case 600:
			slot = ReikaInventoryHelper.locateInInventory(Items.coal, inv);
			break;
		case 800:
			slot = ReikaInventoryHelper.locateInInventory(Items.blaze_powder, inv);
			break;
		case 1200:
			slot = ReikaInventoryHelper.locateInInventory(Items.lava_bucket, inv);
			break;
		case 2500:
			slot = ReikaInventoryHelper.locateInInventory(Items.iron_ingot, inv);
			slot2 = ReikaInventoryHelper.locateInInventory(ItemStacks.aluminumpowder, inv, false);
			break;
		}
		ReikaInventoryHelper.decrStack(slot, inv);
		if (slot2 > -1)
			ReikaInventoryHelper.decrStack(slot2, inv);
		if (temperature < temp)
			temperature += (temp-temperature)/4;
		if (temp == 1200) {
			int leftover = ReikaInventoryHelper.addToInventoryWithLeftover(Items.bucket, -1, 1, inv);
			if (leftover > 0) {
				EntityItem ei = new EntityItem(worldObj, xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), new ItemStack(Items.lava_bucket, leftover, 0));
				ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
				if (!worldObj.isRemote)
					worldObj.spawnEntityInWorld(ei);
			}
		}
	}

	private int getMaxFuelTemperature() {
		if (!this.hasValidItems())
			return Integer.MIN_VALUE;
		if (ReikaInventoryHelper.checkForItem(Items.iron_ingot, inv) && ReikaInventoryHelper.checkForItemStack(ItemStacks.aluminumpowder, inv, false))
			return 2500;
		if (ReikaInventoryHelper.checkForItem(Items.lava_bucket, inv))
			return 1200;
		if (ReikaInventoryHelper.checkForItem(Items.blaze_powder, inv))
			return 800;
		if (ReikaInventoryHelper.checkForItem(Items.coal, inv))
			return 600; //really 580
		if (ReikaInventoryHelper.checkForItem(Blocks.log, inv))
			return 400;
		if (ReikaInventoryHelper.checkForItem(Blocks.log2, inv))
			return 400;
		if (ReikaInventoryHelper.checkForItem(Blocks.planks, inv))
			return 300;
		return 0;
	}

	private boolean hasValidItems() {
		if (ReikaInventoryHelper.checkForItem(Items.iron_ingot, inv) && ReikaInventoryHelper.checkForItemStack(ItemStacks.aluminumpowder, inv, false))
			return true;
		if (ReikaInventoryHelper.checkForItem(Items.lava_bucket, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Items.blaze_powder, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Items.coal, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Blocks.planks, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Blocks.log, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Blocks.log2, inv))
			return true;
		return false;
	}

	private boolean isValidFuel(ItemStack is) {
		if (is.getItem() == Items.iron_ingot)
			return true;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.aluminumpowder))
			return true;
		if (is.getItem() == Items.lava_bucket)
			return true;
		if (is.getItem() == Items.blaze_powder)
			return true;
		if (is.getItem() == Items.coal)
			return true;
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.planks))
			return true;
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.log))
			return true;
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.log2))
			return true;
		return false;
	}

	@Override
	public int getRange() {
		return temperature/50;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.IGNITER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isValidFuel(is);
	}

	@Override
	public int getMaxRange() {
		return this.getRange();
	}

	@Override
	public int getThermalDamage() {
		return (temperature)/50;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasValidItems())
			return 15;
		return 0;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Fuel";
	}
}