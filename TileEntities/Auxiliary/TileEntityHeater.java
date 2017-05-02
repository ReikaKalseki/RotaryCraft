/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Interfaces.ThermalMachine;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityHeater extends InventoriedPowerReceiver implements TemperatureTE, DiscreteFunction {

	public int temperature;
	public int setTemperature;


	private int tickcount2 = 0;

	public static final int MAXTEMP = 2000;

	public boolean idle = false;

	public void testIdle() {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(worldObj, xCoord, yCoord, zCoord);
		if (setTemperature <= Tamb) {
			idle = true;
			return;
		}
		if (this.findHottestUsefulItemTemp(setTemperature, false) == -1) {
			idle = true;
			return;
		}
		idle = false;
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		tickcount2++;
		this.getPowerBelow();
		if (tickcount2 >= 20) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount2 = 0;
		}
		if (power < MINPOWER)
			return;
		this.testIdle();
		if (tickcount >= this.getOperationTime()) {
			this.addHeat();
			tickcount = 0;
		}
		this.transferHeat(world, x, y+1, z);
		if (temperature >= 240) {
			this.ignite(world, x, y, z);
		}
	}

	private void ignite(World world, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+2, z+1);
		List<EntityLivingBase> inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (EntityLivingBase hot : inbox) {
			hot.setFire(temperature/50);
		}
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (temperature > Tamb)
			temperature -= ReikaMathLibrary.extrema((temperature-Tamb)/200, 1, "max");
		if (temperature < Tamb)
			temperature += ReikaMathLibrary.extrema((Tamb-temperature)/40, 1, "max");
	}

	private void addHeat() {
		int tempdiff = setTemperature-temperature;
		if (tempdiff <= 0)
			return;
		int deltaT = this.findHottestUsefulItemTemp(tempdiff, true);
		if (deltaT != -1)
			temperature += deltaT * 1.5;

		if (temperature >= MAXTEMP*0.9) {
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching a very high temperature!");
		}

		if (temperature > MAXTEMP) {
			this.overheat(worldObj, xCoord, yCoord, zCoord);
			temperature = MAXTEMP;
		}
	}

	public void overheat(World world, int x, int y, int z) {
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.copy(), 0, 17, true, 1.2F, true, true, 1F);
		world.setBlock(x, y, z, Blocks.flowing_lava);
		temperature = 0;
		setTemperature = 0;
	}

	private int findHottestUsefulItemTemp(int maxT, boolean consume) {
		ItemStack item = null;
		int itemheat = -1;
		int slot = -1;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				//ReikaChatHelper.writeInt(TileEntityFurnace.getItemBurnTime(inv[i]));
				int heat = (TileEntityFurnace.getItemBurnTime(inv[i])/25);
				if (heat <= maxT && heat > itemheat) {
					itemheat = heat;
					item = inv[i];
					slot = i;
				}
			}
		}
		if (slot != -1 && consume) {
			Item id = inv[slot].getItem();
			ReikaInventoryHelper.decrStack(slot, inv);
			if (id == Items.lava_bucket) {
				int leftover = ReikaInventoryHelper.addToInventoryWithLeftover(Items.bucket, -1, 1, inv);
				if (leftover > 0) {
					EntityItem ei = new EntityItem(worldObj, xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), new ItemStack(Items.lava_bucket, leftover, 0));
					ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
					if (!worldObj.isRemote)
						worldObj.spawnEntityInWorld(ei);
				}
			}
		}
		//ReikaChatHelper.writeInt(itemheat);
		return itemheat;
	}

	private void transferHeat(World world, int x, int y, int z) {
		if (!world.isRemote)
			ReikaWorldHelper.temperatureEnvironment(world, x, y-1, z, temperature);
		MachineRegistry id = MachineRegistry.getMachine(world, x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof ThermalMachine) {
			ThermalMachine th = (ThermalMachine)te;
			int tempdiff = temperature-th.getTemperature();
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				th.addTemperature(tempdiff/16);
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				th.addTemperature(tempdiff/8);
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				th.addTemperature(tempdiff/4);
				//this.temperature -= tempdiff/4;
			}
			else {
				th.addTemperature(tempdiff);
				//this.temperature -= tempdiff;
			}
			if (th.getTemperature() > th.getMaxTemperature())
				th.onOverheat(world, x, y, z);
		}
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");
		setTemperature = NBT.getInteger("stemp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);
		NBT.setInteger("stemp", setTemperature);
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
		return MachineRegistry.HEATER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return TileEntityFurnace.getItemBurnTime(is) > 0;
	}

	@Override
	public int getThermalDamage() {
		return 0; // Done in TE code itself
	}

	@Override
	public int getRedstoneOverride() {
		if (idle)
			return 15;
		return 0;
	}

	@Override
	public void addTemperature(int temp) {

	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.HEATER.getOperationTime(omega);
	}

	@Override
	public boolean canBeCooledWithFins() {
		return false;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}
}
