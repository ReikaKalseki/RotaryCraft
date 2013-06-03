/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelFriction;

public class TileEntityFurnaceHeater extends TileEntityPowerReceiver implements TemperatureTE {

	public int temperature;
	public int fx;
	public int fy;
	public int fz;

	public static final int MAXTEMP = 1200;

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		if (this.hasFurnace(world)) {
			temperature += 2*ReikaMathLibrary.logbase(omega, 2)*ReikaMathLibrary.logbase(torque, 2);
		}
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/5;
		}
		else {
			temperature += (temperature-Tamb)/5;
		}
		if (temperature - Tamb <= 4 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
		if (temperature >= MAXTEMP)
			if (par5Random.nextInt(600) == 0)
				this.meltFurnace(world);
	}

	@Override
	public int getThermalDamage() {
		return temperature*5/1200;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelFriction();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		if (torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FRICTION.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false, false);
		if (tickcount >= 20) {
			tickcount = 0;
			this.updateTemperature(world, x, y, z, meta);
		}
		if (power < MINPOWER)
			return;
		if (torque < MINTORQUE)
			return;
		this.getFurnaceCoordinates(world, x, y, z, meta);
		if (!this.hasFurnace(world)) {
			return;
		}
		this.hijackFurnace(world, x, y, z, meta);
	}

	private void hijackFurnace(World world, int x, int y, int z, int meta) {
		TileEntityFurnace tile = (TileEntityFurnace)world.getBlockTileEntity(fx, fy, fz);
		tile.currentItemBurnTime = this.getBurnTimeFromTemperature();
		tile.furnaceBurnTime = this.getBurnTimeFromTemperature();
		tile.furnaceCookTime = ReikaMathLibrary.extrema(tile.furnaceCookTime+this.getSpeedFactorFromTemperature(), 200, "min");
		if (par5Random.nextInt(6) == 0)
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.gravel", 2F, 3F);
		switch(meta) {
		case 0:
			world.spawnParticle("crit", x, fy+par5Random.nextDouble(), fz+par5Random.nextDouble(), -0.2+0.4*par5Random.nextDouble(), 0.4*par5Random.nextDouble(), -0.2+0.4*par5Random.nextDouble());
			break;
		case 1:
			world.spawnParticle("crit", x+1, fy+par5Random.nextDouble(), fz+par5Random.nextDouble(), -0.2+0.4*par5Random.nextDouble(), 0.4*par5Random.nextDouble(), -0.2+0.4*par5Random.nextDouble());
			break;
		case 2:
			world.spawnParticle("crit", fx+par5Random.nextDouble(), fy+par5Random.nextDouble(), z, -0.2+0.4*par5Random.nextDouble(), 0.4*par5Random.nextDouble(), -0.2+0.4*par5Random.nextDouble());
			break;
		case 3:
			world.spawnParticle("crit", fx+par5Random.nextDouble(), fy+par5Random.nextDouble(), z+1, -0.2+0.4*par5Random.nextDouble(), 0.4*par5Random.nextDouble(), -0.2+0.4*par5Random.nextDouble());
			break;
		}
	}

	private void getFurnaceCoordinates(World world, int x, int y, int z, int meta) {
		fy = y;
		fx = x;
		fz = z;
		switch(meta) {
		case 0:
			fx = x-1;
			break;
		case 1:
			fx = x+1;
			break;
		case 2:
			fz = z-1;
			break;
		case 3:
			fz = z+1;
			break;
		}
	}

	private void meltFurnace(World world) {
		world.createExplosion(null, fx+0.5, fy+0.5, fz+0.5, 1F, false);
		world.setBlock(fx, fy, fz, Block.lavaMoving.blockID);
		ItemStack cobb = new ItemStack(Block.cobblestone);
		for (int i = 0; i < 8; i++)
			ReikaItemHelper.dropItem(world, fx+par5Random.nextDouble(), fy+par5Random.nextDouble(), fz+par5Random.nextDouble(), cobb);
	}

	private boolean hasFurnace(World world) {
		return world.getBlockTileEntity(fx, fy, fz) instanceof TileEntityFurnace;
	}

	private int getBurnTimeFromTemperature() {
		if (temperature < 300)
			return 0;
		return (temperature-300)*2;
	}

	private int getSpeedFactorFromTemperature() {
		if (temperature < 800)
			return 0;
		return (temperature-800)/100;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("temp", temperature);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		temperature = NBT.getInteger("temp");
	}

}
