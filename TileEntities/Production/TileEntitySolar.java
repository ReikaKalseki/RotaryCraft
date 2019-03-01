/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.Collection;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.SolarPlant;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiBlockMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.SodiumSolarUpgrades;
import Reika.RotaryCraft.Auxiliary.Interfaces.SodiumSolarUpgrades.SodiumSolarOutput;
import Reika.RotaryCraft.Auxiliary.Interfaces.SodiumSolarUpgrades.SodiumSolarReceiver;
import Reika.RotaryCraft.Auxiliary.Interfaces.SolarPlantBlock;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySolar extends TileEntityIOMachine implements MultiBlockMachine, SimpleProvider, PipeConnector, PowerGenerator, IFluidHandler, SolarPlantBlock {

	private BlockArray solarBlocks = new BlockArray();
	private final StepTimer mirrorTimer = new StepTimer(100);

	private float overallBrightness;
	private int size;
	private int topLocation = -1;

	private int temperature;

	public static final int GENOMEGA = 512;
	public static final int GENOMEGA_SODIUM = 4096;

	public static final int MAXTORQUE = 16384;
	public static final int MAXTORQUE_SODIUM = 65536;

	private final HybridTank tank = new HybridTank("solar", 4000);

	private SolarPlant plant;

	public void searchForPlant(World world, int x, int y, int z) {
		if (plant != null)
			return;
		plant = SolarPlant.build(world, x, y, z);
		size = -1;
	}

	public SolarPlant getPlant() {
		return plant;
	}

	public void setPlant(SolarPlant p) {
		plant = p;
		size = -1;
	}

	@Override
	public boolean canProvidePower() {
		//return this.isMultiBlock(worldObj, xCoord, yCoord, zCoord) && this.getMultiBlockPosition(worldObj, xCoord, yCoord, zCoord)[1] == 0;
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SOLARTOWER;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.searchForPlant(world, x, y, z);
		topLocation = this.getTopOfTower();

		if (!world.isRemote) {
			int temp = (int)(5*size*overallBrightness);
			for (int i = -3; i <= 3; i++) {
				for (int j = -3; j <= 3; j++) {
					if (ConfigRegistry.BLOCKDAMAGE.getState()) {
						ReikaWorldHelper.temperatureEnvironment(world, x+i, y+1, z+j, Math.min(temp, 1750));
						if (temp >= 1500) {
							this.delete();
							world.setBlock(x, y, z, Blocks.flowing_lava);
						}
					}
				}
			}
			if (temp > 400) {
				AxisAlignedBB above = AxisAlignedBB.getBoundingBox(x-3, y+1, z-3, x+4, y+2, z+4);
				List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, above);
				for (EntityLivingBase e : in) {
					if (!e.isPotionActive(Potion.fireResistance))
						e.setFire(3);
				}
			}
		}
		if (world.getBlock(x, y-1, z) == Blocks.air || MachineRegistry.getMachine(world, x, y-1, z) != this.getMachine()) {
			//ReikaJavaLibrary.pConsole("TOWER: "+this.getTowerHeight()+";  SIZE: "+this.getArraySize());
			if (plant.getPrimaryTower().to2D().equals(new Coordinate(x, 0, z)))
				this.generatePower(world, x, y, z);
			else
				power = omega = torque = 0;
		}
		else {
			write = null;
		}
		if (world.getBlock(x, y+1, z) != Blocks.air && !(world.getTileEntity(x, y+1, z) instanceof SodiumSolarUpgrades)) {
			if (MachineRegistry.getMachine(world, x, y+1, z) == this.getMachine())
				temperature = ((TileEntitySolar)world.getTileEntity(x, y+1, z)).temperature;
			return;
		}

		TileEntity top = world.getTileEntity(x, topLocation+1, z);
		if (top instanceof SodiumSolarReceiver) {
			SodiumSolarReceiver ss = (SodiumSolarReceiver)top;
			if (ss.isActive()) {
				ss.tick(size, overallBrightness);
				temperature = ss.getTemperature();
			}
		}

		if (plant != null) {
			if (this.isTopOfTower()) {
				mirrorTimer.update();
				if (mirrorTimer.checkCap() || size == -1) {
					overallBrightness = plant.getOverallBrightness(world);
					size = plant.mirrorCount();
				}
			}
			else {
				overallBrightness = this.getArrayOverallBrightness();
				size = this.getArraySize();
			}
		}
		else {
			size = -1;
			overallBrightness = 0;
		}

		if (write != null) {
			this.basicPowerReceiver();
		}
	}

	private void generatePower(World world, int x, int y, int z) {
		this.getTowerWater(world, x, y, z);
		int amt = this.getConsumedWater();
		write = ForgeDirection.DOWN;
		//omega = 1*ReikaMathLibrary.extrema(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 8, "min")*(this.getArraySize()+1);
		boolean water = tank.getActualFluid() == FluidRegistry.WATER;
		omega = water ? GENOMEGA : GENOMEGA_SODIUM;
		torque = this.getGenTorque(world, x, y, z);
		if (this.getArraySize() <= 0 || torque == 0 || tank.getLevel() < amt || (!water && temperature < 800)) {
			omega = 0;
			torque = 0;
		}
		power = (long)omega*(long)torque;
		if (tank.getActualFluid() == FluidRegistry.getFluid("rc sodium")) {
			amt = (int)Math.max(1, amt*power/((double)GENOMEGA_SODIUM*MAXTORQUE_SODIUM));
		}
		if (power > 0 && tank.getLevel() > 0 && amt > 0) {
			if (!water) {
				TileEntity te = this.getAdjacentTileEntity(ForgeDirection.DOWN);
				if (te instanceof SodiumSolarOutput) {
					SodiumSolarOutput ss = (SodiumSolarOutput)te;
					if (ss.isActive()) {
						amt = ss.receiveSodium(amt);
					}
				}
			}
			if (amt > 0)
				tank.removeLiquid(amt);
		}
	}

	private int getGenTorque(World world, int x, int y, int z) {
		if (tank.isEmpty() || plant == null)
			return 0;
		boolean water = tank.getActualFluid() == FluidRegistry.WATER;
		int cap = water ? MAXTORQUE : MAXTORQUE_SODIUM;
		float f = water ? 1 : 1.75F;
		float p = water ? 1 : 1.5F;
		return Math.min(cap, (int)(f*this.getArrayOverallBrightness()*plant.getTowerMultiplier()*(Math.pow(this.getArraySize()+1, p))));
	}

	public int getConsumedWater() {
		boolean sodium = tank.getActualFluid() == FluidRegistry.getFluid("rc sodium");
		int base = 10+(sodium ? 64 : 16)*ReikaMathLibrary.logbase2(power);
		int rnd = 10;
		if (base >= 1000)
			rnd = 1000;
		else if (base >= 100)
			rnd = 100;
		int ret = ReikaMathLibrary.roundUpToX(rnd, base);
		if (sodium)
			ret *= 1/128D;//0.00390625; //1/256
		return ret;
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
	public boolean isMultiBlock(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int[] getMultiBlockPosition(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int[] getMultiBlockSize(World world, int x, int y, int z) {
		return null;
	}

	public int getArraySize() {
		TileEntity tile = worldObj.getTileEntity(xCoord, topLocation, zCoord);
		if (tile == null)
			return 0;
		return ((TileEntitySolar)tile).size;
	}

	public float getArrayOverallBrightness() {
		TileEntity tile = worldObj.getTileEntity(xCoord, topLocation, zCoord);
		if (tile == null)
			return 0;
		return ((TileEntitySolar)tile).overallBrightness;
	}

	public int getTopOfTower() {
		int y = yCoord;
		while (MachineRegistry.getMachine(worldObj, xCoord, y, zCoord) == MachineRegistry.SOLARTOWER) {
			y++;
		}
		return y-1;
	}

	public int getBottomOfTower() {
		int y = yCoord;
		while (MachineRegistry.getMachine(worldObj, xCoord, y, zCoord) == MachineRegistry.SOLARTOWER) {
			y--;
		}
		return y+1;
	}

	public boolean isTopOfTower() {
		return yCoord == this.getTopOfTower();
	}

	private void getTowerWater(World world, int x, int y, int z) {
		int lvl = tank.getLevel();
		Fluid f = tank.getActualFluid();
		int cy = y+1;
		while (MachineRegistry.getMachine(world, x, cy, z) == MachineRegistry.SOLARTOWER) {
			TileEntitySolar tile = (TileEntitySolar)world.getTileEntity(x, cy, z);
			Fluid f2 = tile.tank.getActualFluid();
			if (f == null && f2 != null)
				f = f2;
			if (f2 != null && f.equals(f2)) {
				lvl += tile.tank.getLevel();
				tile.tank.empty();
			}
			cy++;
		}
		tank.setContents(lvl, f);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return true;
	}

	@Override
	public void onEMP() {}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	public long getMaxPower() {
		return torque*omega;
	}

	public long getCurrentPower() {
		return power;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!this.canFill(from, resource.getFluid()))
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid.equals(FluidRegistry.WATER) || (fluid.equals(FluidRegistry.getFluid("rc sodium")) && this.canUseSodium());
	}

	private boolean canUseSodium() {
		int y = this.getTopOfTower()+1;
		TileEntity te = worldObj.getTileEntity(xCoord, y, zCoord);
		if (te instanceof SodiumSolarReceiver) {
			SodiumSolarReceiver ss = (SodiumSolarReceiver)te;
			return ss.isActive();
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return Flow.INPUT;
	}

	@Override
	public int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

	@Override
	public void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		c.add(this.getAdjacentTileEntity(write));
	}

	@Override
	public void breakBlock() {
		plant.invalidate(worldObj);
	}

}
