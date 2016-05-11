/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiBlockMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;

public class TileEntitySolar extends TileEntityIOMachine implements MultiBlockMachine, SimpleProvider, PipeConnector, PowerGenerator, IFluidHandler {

	private BlockArray solarBlocks = new BlockArray();
	private final StepTimer mirrorTimer = new StepTimer(100);
	private int numberMirrors = 0;

	private float lightMultiplier = 0;

	public static final int GENOMEGA = 512;

	private final HybridTank tank = new HybridTank("solar", 4000);

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
		int temp = (int)(15*this.getArraySize()*this.getArrayOverallBrightness());
		for (int i = -3; i <= 3; i++) {
			for (int j = -3; j <= 3; j++) {
				if (ConfigRegistry.BLOCKDAMAGE.getState()) {
					ReikaWorldHelper.temperatureEnvironment(world, x+i, y+1, z+j, Math.min(temp, 1750));
					if (temp >= 1500) {
						this.delete();
						world.setBlock(x, y, z, Blocks.flowing_lava);
					}
				}
				AxisAlignedBB above = AxisAlignedBB.getBoundingBox(x+i, y+1, z+j, x+i+1, y+2, z+j+1);
				List<EntityLivingBase> in = world.getEntitiesWithinAABB(EntityLivingBase.class, above);
				for (EntityLivingBase e : in) {
					if (temp > 400)
						e.setFire(3);
				}
			}
		}
		if (world.getBlock(x, y-1, z) == Blocks.air || MachineRegistry.getMachine(world, x, y-1, z) != this.getMachine()) {
			//ReikaJavaLibrary.pConsole("TOWER: "+this.getTowerHeight()+";  SIZE: "+this.getArraySize());
			this.generatePower(world, x, y, z);
		}
		else {
			write = null;
		}
		if (world.getBlock(x, y+1, z) != Blocks.air)
			return;

		mirrorTimer.update();
		if (mirrorTimer.checkCap()) {
			if (solarBlocks.isEmpty()) {
				lightMultiplier = 0;
				solarBlocks.recursiveAdd(world, x, y, z, this.getTileEntityBlockID());
				numberMirrors = solarBlocks.getSize();
				while (solarBlocks.getSize() > 0) {
					Coordinate c = solarBlocks.getNextAndMoveOn();
					MachineRegistry m = MachineRegistry.getMachine(world, c.xCoord, c.yCoord, c.zCoord);
					if (m == MachineRegistry.MIRROR) {
						TileEntityMirror te = (TileEntityMirror)world.getTileEntity(c.xCoord, c.yCoord, c.zCoord);
						te.targetloc = new Coordinate(x, y, z);
						float light = te.getLightLevel();
						lightMultiplier += light;
					}
					else numberMirrors--;
				}
				lightMultiplier /= 15F;
				lightMultiplier /= numberMirrors;
			}
		}

		if (write != null) {
			this.basicPowerReceiver();
		}
	}

	private void generatePower(World world, int x, int y, int z) {
		this.getTowerWater(world, x, y, z);
		write = ForgeDirection.DOWN;
		//omega = 1*ReikaMathLibrary.extrema(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 8, "min")*(this.getArraySize()+1);
		omega = GENOMEGA;
		torque = this.getGenTorque(world, x, y, z);
		if (this.getArraySize() <= 0 || torque == 0 || tank.getLevel() < this.getConsumedWater()) {
			omega = 0;
			torque = 0;
		}
		power = (long)omega*(long)torque;
		if (power > 0 && tank.getLevel() > 0) {
			tank.removeLiquid(this.getConsumedWater());
		}
	}

	private int getGenTorque(World world, int x, int y, int z) {
		return Math.min(16384, (int)(this.getArrayOverallBrightness()*Math.min(ReikaMathLibrary.ceil2exp(this.getTowerHeight()), 64)*(this.getArraySize()+1)));
	}

	public int getConsumedWater() {
		int base = 10+16*ReikaMathLibrary.logbase2(power);
		int rnd = 10;
		if (base >= 1000)
			rnd = 1000;
		else if (base >= 100)
			rnd = 100;
		return ReikaMathLibrary.roundUpToX(rnd, base);
	}

	public int getTowerHeight() {
		return this.getTopOfTower()-yCoord;
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
		TileEntity tile = worldObj.getTileEntity(xCoord, this.getTopOfTower(), zCoord);
		if (tile == null)
			return 0;
		return ((TileEntitySolar)tile).numberMirrors;
	}

	public float getArrayOverallBrightness() {
		TileEntity tile = worldObj.getTileEntity(xCoord, this.getTopOfTower(), zCoord);
		if (tile == null)
			return 0;
		return ((TileEntitySolar)tile).lightMultiplier;
	}

	public int getTopOfTower() {
		int y = yCoord;
		while (MachineRegistry.getMachine(worldObj, xCoord, y, zCoord) == MachineRegistry.SOLARTOWER) {
			y++;
		}
		return y-1;
	}

	public void getTowerWater(World world, int x, int y, int z) {
		int water = tank.getLevel();
		int cy = y+1;
		while (MachineRegistry.getMachine(world, x, cy, z) == MachineRegistry.SOLARTOWER) {
			TileEntitySolar tile = (TileEntitySolar)(world.getTileEntity(x, cy, z));
			water += tile.tank.getLevel();
			tile.tank.empty();
			cy++;
		}
		tank.setContents(water, FluidRegistry.WATER);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	public int getBottomOfTower() {
		int y = yCoord;
		while (MachineRegistry.getMachine(worldObj, xCoord, y, zCoord) == MachineRegistry.SOLARTOWER) {
			y--;
		}
		return y+1;
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
		return fluid.equals(FluidRegistry.WATER);
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

}
