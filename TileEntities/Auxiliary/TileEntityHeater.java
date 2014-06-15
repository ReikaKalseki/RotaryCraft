/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
import Reika.RotaryCraft.API.ThermalMachine;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.World.TileEntityIgniter;

public class TileEntityHeater extends InventoriedPowerReceiver implements TemperatureTE, DiscreteFunction {

	public int temperature;
	public int setTemperature;


	private int tickcount2 = 0;

	public static final int MAXTEMP = 1500;

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
		List inbox = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < inbox.size(); i++) {
			EntityLivingBase hot = (EntityLivingBase)inbox.get(i);
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
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 17, true, 1.2F, true, true, 1F);
		world.setBlock(x, y, z, Block.lavaMoving.blockID);
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
			int id = inv[slot].itemID;
			ReikaInventoryHelper.decrStack(slot, inv);
			if (id == Item.bucketLava.itemID) {
				int leftover = ReikaInventoryHelper.addToInventoryWithLeftover(Item.bucketEmpty.itemID, -1, 1, inv);
				if (leftover > 0) {
					EntityItem ei = new EntityItem(worldObj, xCoord+rand.nextFloat(), yCoord+rand.nextFloat(), zCoord+rand.nextFloat(), new ItemStack(Item.bucketLava.itemID, leftover, 0));
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
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (id == MachineRegistry.PULSEJET) {
			TileEntityPulseFurnace tile = (TileEntityPulseFurnace)te;
			if (tile == null)
				return;
			int tempdiff = temperature-tile.temperature;
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.temperature += tempdiff/16;
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.temperature += tempdiff/8;
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.temperature += tempdiff/4;
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.temperature += tempdiff;
				//this.temperature -= tempdiff;
			}
			if (tile.temperature > tile.MAXTEMP)
				tile.updateTemperature(world, x, y, z, world.getBlockMetadata(x, y, z));
		}
		if (id == MachineRegistry.IGNITER) {
			TileEntityIgniter tile = (TileEntityIgniter)te;
			if (tile == null)
				return;
			int tempdiff = temperature-tile.temperature;
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.temperature += tempdiff/16;
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.temperature += tempdiff/8;
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.temperature += tempdiff/4;
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.temperature += tempdiff;
				//this.temperature -= tempdiff;
			}
			if (tile.temperature > tile.MAXTEMP)
				tile.temperature = tile.MAXTEMP;
		}
		if (id == MachineRegistry.COMPACTOR) {
			TileEntityCompactor tile = (TileEntityCompactor)te;
			if (tile == null)
				return;
			int tempdiff = temperature-tile.temperature;
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.temperature += tempdiff/16;
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.temperature += tempdiff/8;
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.temperature += tempdiff/4;
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.temperature += tempdiff;
				//this.temperature -= tempdiff;
			}
			if (tile.temperature > tile.MAXTEMP)
				tile.overheat(world, x, y, z);
		}
		if (id == MachineRegistry.OBSIDIAN) {
			TileEntityObsidianMaker tile = (TileEntityObsidianMaker)te;
			if (tile == null)
				return;
			int tempdiff = temperature-tile.temperature;
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.temperature += tempdiff/16;
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.temperature += tempdiff/8;
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.temperature += tempdiff/4;
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.temperature += tempdiff;
				//this.temperature -= tempdiff;
			}
			if (tile.temperature > tile.MAXTEMP)
				tile.overheat(world, x, y, z);
		}
		if (id == MachineRegistry.FERMENTER) {
			TileEntityFermenter tile = (TileEntityFermenter)te;
			if (tile == null)
				return;
			int tempdiff = temperature-tile.temperature;
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.temperature += tempdiff/16;
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.temperature += tempdiff/8;
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.temperature += tempdiff/4;
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.temperature += tempdiff;
				//this.temperature -= tempdiff;
			}
			if (rand.nextInt(5) == 0)
				tile.testYeastKill();
		}
		if (id == MachineRegistry.BLASTFURNACE) {
			TileEntityBlastFurnace tile = (TileEntityBlastFurnace)te;
			if (tile == null)
				return;
			int tempdiff = temperature-tile.getTemperature();
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.addTemperature(tempdiff/16);
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.addTemperature(tempdiff/8);
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.addTemperature(tempdiff/4);
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.addTemperature(tempdiff);
				//this.temperature -= tempdiff;
			}
		}
		if (id == MachineRegistry.ENGINE) {
			TileEntityEngine tile = (TileEntityEngine)te;
			if (tile == null)
				return;
			if (!tile.getEngineType().isCooled())
				return;
			int tempdiff = temperature-tile.temperature;
			if (tempdiff <= 0)
				return;
			if (tempdiff > 100) {
				tile.temperature += tempdiff/16;
				//this.temperature -= tempdiff/16;
			}
			else if (tempdiff > 16) {
				tile.temperature += tempdiff/8;
				//this.temperature -= tempdiff/8;
			}
			else if (tempdiff > 8) {
				tile.temperature += tempdiff/4;
				//this.temperature -= tempdiff/4;
			}
			else {
				tile.temperature += tempdiff;
				//this.temperature -= tempdiff;
			}
			if (tile.temperature > tile.MAXTEMP)
				tile.overheat(world, x, y, z);
		}
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
}
