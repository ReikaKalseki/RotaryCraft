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

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Models.ModelSmokeDetector;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntitySmokeDetector extends RotaryCraftTileEntity implements RangedEffect, IInventory, OneSlotMachine {

	//public static int MINPOWER = 16; //runs off of 4AAA's (max power = 4W) , so 16W (one DC engine can run 64, or 8 at max range)
	//public static int BASESPEED = 0;

	public boolean isAlarm = false;
	public boolean isLowBatt = false;


	private int unwindtick = 0;

	public int soundDelay = -1;

	public ItemStack[] battery = new ItemStack[1];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		unwindtick++;
		//this.getPower4Sided(0, 1, 0);
		//if (this.power < MINPOWER)
		//	return;
		if (!this.checkValidCoil())
			return;
		if (unwindtick >= 1200) {
			battery[0].setItemDamage(battery[0].getItemDamage()-1);
			unwindtick = 0;
		}
		//ReikaChatHelper.write(ReikaWorldHelper.findNearBlock(world, x, y, z, 8, Block.fire.blockID));
		if (ReikaWorldHelper.findNearBlock(world, x, y, z, 8, Block.fire.blockID))
			isAlarm = true;
		else
			isAlarm = false;
		if (this.lowBattery())
			isLowBatt = true;
		else
			isLowBatt = false;
		if (isAlarm)
			soundDelay = 4;
		else if (isLowBatt)
			soundDelay = 600;
		else
			soundDelay = -1;
		if (tickcount >= soundDelay && soundDelay != -1) {
			tickcount = 0;
			SoundRegistry.playSoundAtBlock(SoundRegistry.SMOKE, world, x, y, z, 0.1F, 1);
		}
	}
	/*
	public int getReactionTime() {
		int time = (int)(10-ReikaMathLibrary.logbase(this.omega, 2));
		if (time < 1)
			return 1;
		return time;
	}
	 */
	public boolean checkValidCoil() {
		if (battery[0] == null)
			return false;
		if (battery[0].itemID != ItemRegistry.SPRING.getShiftedID())
			return false;
		return true;
	}

	public int getRange() {
		int overpower;
		//overpower  = (int)(this.power/MINPOWER);
		if (!this.checkValidCoil())
			return 0;
		overpower = (int)(ReikaMathLibrary.logbase(ReikaMathLibrary.intpow(battery[0].getItemDamage(), 2), 2));
		if (overpower > 8)
			return 8;
		return overpower;
	}

	public boolean lowBattery() {
		if (!this.checkValidCoil())
			return false;
		if (battery[0].getItemDamage() > 8)
			return false;
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}
	@Override
	public ItemStack getStackInSlot(int var1) {
		return battery[0];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		if (battery[par1] != null) {
			if (battery[par1].stackSize <= par2) {
				ItemStack itemstack = battery[par1];
				battery[par1] = null;
				return itemstack;
			}
			ItemStack itemstack1 = battery[par1].splitStack(par2);
			if (battery[par1].stackSize == 0)
				battery[par1] = null;
			return itemstack1;
		}
		else
			return null;
	}

	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (battery[par1] != null) {
			ItemStack itemstack = battery[par1];
			battery[par1] = null;
			return itemstack;
		}
		else
			return null;
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		battery[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void openChest() {
	}
	@Override
	public void closeChest() {
	}
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelSmokeDetector();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SMOKEDETECTOR.ordinal();
	}
	@Override
	public int getMaxRange() {
		return this.getRange();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
