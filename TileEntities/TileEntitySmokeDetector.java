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

import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TileEntitySmokeDetector extends TileEntitySpringPowered implements RangedEffect, OneSlotMachine {

	//public static int MINPOWER = 16; //runs off of 4AAA's (max power = 4W) , so 16W (one DC engine can run 64, or 8 at max range)
	//public static int BASESPEED = 0;

	private boolean isAlarm = false;
	private boolean isLowBatt = false;


	private int unwindtick = 0;

	public int soundDelay = -1;

	public boolean isAlarming() {
		return isAlarm;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		unwindtick++;
		//this.getPower4Sided(0, 1, 0);
		//if (this.power < MINPOWER)
		//	return;
		if (!this.checkValidCoil())
			return;
		if (unwindtick >= this.getUnwindTime()) {
			inv[0] = this.getDecrementedCharged();
			unwindtick = 0;
		}
		//ReikaChatHelper.write(ReikaWorldHelper.findNearBlock(world, x, y, z, 8, Blocks.fire.blockID));
		if (ReikaWorldHelper.findNearBlock(world, x, y, z, 8, Blocks.fire)) {
			if (!isAlarm)
				ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
			isAlarm = true;
		}
		else {
			if (isAlarm)
				ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
			isAlarm = false;
		}
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
			SoundRegistry.SMOKE.playSoundAtBlock(world, x, y, z, 0.1F, 1);
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
		return this.hasCoil();
	}

	public int getRange() {
		int overpower;
		//overpower  = (int)(this.power/MINPOWER);
		if (!this.checkValidCoil())
			return 0;
		int dmg = inv[0].getItemDamage();
		overpower = (int)ReikaMathLibrary.logbase(dmg*dmg, 2);
		if (overpower > 8)
			return 8;
		return overpower;
	}

	public boolean lowBattery() {
		if (!this.checkValidCoil())
			return false;
		if (inv[0].getItemDamage() > 8)
			return false;
		return true;
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
		return MachineRegistry.SMOKEDETECTOR;
	}

	@Override
	public int getMaxRange() {
		return this.getRange();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getBaseDischargeTime() {
		return 1200;
	}

}