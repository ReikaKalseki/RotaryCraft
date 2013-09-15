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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelFriction;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityFurnaceHeater extends TileEntityPowerReceiver implements TemperatureTE {
	//give ability to heat blast furnace
	private int temperature;
	public int fx;
	public int fy;
	public int fz;

	public static final int MAXTEMP = 1200;
	private int smeltTime = 0;
	private int soundtick = 0;

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		if ((this.hasFurnace(world) || this.hasBlastFurnace(world)) && power > 0) {
			temperature += 3*ReikaMathLibrary.logbase(omega, 2)*ReikaMathLibrary.logbase(torque, 2);
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
			if (!world.isRemote && par5Random.nextInt(DifficultyEffects.FURNACEMELT.getInt()) == 0 && ConfigRegistry.BLOCKDAMAGE.getState())
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
			if (this.hasBlastFurnace(world)) {
				this.heatBlast(world);
			}
			return;
		}
		this.hijackFurnace(world, x, y, z, meta);
	}

	private void heatBlast(World world) {
		TileEntityBlastFurnace te = (TileEntityBlastFurnace)world.getBlockTileEntity(fx, fy, fz);
		int tdiff = temperature-te.getTemperature();
		te.addTemperature(tdiff);
	}

	private boolean hasBlastFurnace(World world) {
		return MachineRegistry.getMachine(world, fx, fy, fz) == MachineRegistry.BLASTFURNACE;
	}

	private void hijackFurnace(World world, int x, int y, int z, int meta) {
		TileEntity te = world.getBlockTileEntity(fx, fy, fz);
		TileEntityFurnace tile = (TileEntityFurnace)te;
		tile.currentItemBurnTime = this.getBurnTimeFromTemperature();
		tile.furnaceBurnTime = this.getBurnTimeFromTemperature();
		this.smeltCalculation();
		smeltTime++;
		tile.furnaceCookTime = Math.min(smeltTime, 195);
		if (tile.getStackInSlot(0) != null) {
			if (smeltTime >= 200) {
				tile.smeltItem();
				smeltTime = 0;
			}
		}
		else {
			tile.furnaceCookTime = 0;
		}
		//ReikaJavaLibrary.pConsole(smeltTime+" , "+tile.furnaceCookTime);
		soundtick++;
		if (soundtick > 49) {
			SoundRegistry.playSoundAtBlock(SoundRegistry.FRICTION, world, x, y, z, 0.5F, 1);
			soundtick = 0;
		}
		// world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.gravel", 1F, 2F);
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

	private void smeltCalculation() {
		int factor = this.getSpeedFactorFromTemperature();
		smeltTime += factor;
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
		if (world.getBlockId(fx, fy, fz) == 0)
			return;
		world.createExplosion(null, fx+0.5, fy+0.5, fz+0.5, 1F, false);
		//world.setBlock(fx, fy, fz, Block.lavaMoving.blockID);
		world.setBlock(fx, fy, fz, 0);
		//ItemStack cobb = new ItemStack(Block.cobblestone);
		//for (int i = 0; i < 8; i++)
		//	ReikaItemHelper.dropItem(world, fx+par5Random.nextDouble(), fy+par5Random.nextDouble(), fz+par5Random.nextDouble(), cobb);
	}

	public boolean hasFurnace(World world) {
		return world.getBlockTileEntity(fx, fy, fz) instanceof TileEntityFurnace;
	}

	private int getBurnTimeFromTemperature() {
		if (temperature < 300)
			return 0;
		return (temperature-300)*2;
	}

	private int getSpeedFactorFromTemperature() {
		if (temperature < 500)
			return 1;
		return 1+(int)((temperature-500)/100F)*2;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return temperature/100;
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

	@Override
	public void addTemperature(int temp) {

	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public void onEMP() {}

}
