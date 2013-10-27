/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityLavaMaker extends TileEntityInventoriedPowerReceiver implements IFluidHandler, PipeConnector, TemperatureTE {

	private ItemStack[] inv = new ItemStack[9];

	public static final int CAPACITY = 64000;

	private HybridTank tank = new HybridTank("lavamaker", CAPACITY);

	public static final int MELT_ENERGY = 880000; //approx

	public static final int MAXTEMP = 1500;

	private long energy;

	private int temperature;

	private StepTimer timer = new StepTimer(20);

	private static final HashMap<Integer, FluidStack> recipes = new HashMap();
	private static final List<Integer> fuels = new ArrayList();

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		tickcount++;
		energy += power;

		if (omega > 0 && power > 0) {
			if (tickcount > 98) {
				SoundRegistry.playSoundAtBlock(SoundRegistry.FRICTION, world, x, y, z, 0.5F, 0.5F);
				tickcount = 0;
			}
			world.spawnParticle("crit", x+rand.nextDouble(), y, z+rand.nextDouble(), -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
		}

		timer.update();
		if (timer.checkCap())
			this.updateTemperature(world, x, y, z, meta);

		if (energy/20 >= MELT_ENERGY) {
			for (int i = 0; i < fuels.size(); i++) {
				int id = fuels.get(i);
				int slot = ReikaInventoryHelper.locateIDInInventory(id, this);
				if (slot != -1) {
					if (this.melt(slot))
						return;
				}
			}
		}
	}

	private boolean melt(int slot) {
		if (inv[slot] == null)
			return false;
		int id = inv[slot].itemID;
		FluidStack liq = recipes.get(id);
		if (tank.getLevel()+liq.amount > tank.getCapacity())
			return false;
		tank.addLiquid(liq.amount, FluidRegistry.LAVA);
		ReikaInventoryHelper.decrStack(slot, inv);
		energy -= MELT_ENERGY*20;
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (!this.canDrain(from, resource.getFluid()))
			return null;
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return fluid.equals(FluidRegistry.LAVA);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return fuels.contains(is.itemID);
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LAVAMAKER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (tank.isFull())
			return 15;
		if (!this.canMake())
			return 15;
		return 0;
	}

	private boolean canMake() {
		for (int i = 0; i < fuels.size(); i++) {
			int id = fuels.get(i);
			int slot = ReikaInventoryHelper.locateIDInInventory(id, this);
			if (slot != -1) {
				return true;
			}
		}
		return false;
	}

	private static void addFuel(Block b, int amt) {
		fuels.add(b.blockID);
		recipes.put(b.blockID, new FluidStack(FluidRegistry.LAVA, amt));
	}

	static {
		addFuel(Block.stone, 1000);
		addFuel(Block.cobblestone, 500);
		addFuel(Block.netherrack, 2000);
		addFuel(Block.stoneBrick, 1000);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		tank.readFromNBT(NBT);

		energy = NBT.getLong("e");

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");
			if (byte0 >= 0 && byte0 < inv.length)
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		tank.writeToNBT(NBT);

		NBT.setLong("e", energy);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		NBT.setTag("Items", nbttaglist);
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		if (power > 0) {
			temperature += 2.5*ReikaMathLibrary.logbase(power, 2);
		}
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/5;
		}
		else {
			temperature += (temperature-Tamb)/5;
		}
		if (temperature - Tamb <= 4 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
		if (temperature > 50) {
			int side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.snow.blockID);
			if (side != -1)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, 0, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
			if (side != -1)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, Block.waterMoving.blockID, 0);
		}
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
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {
		world.createExplosion(null, x+0.5, y+0.5, z+0.5, 3F, false);
		if (tank.isEmpty())
			world.setBlock(x, y, z, 0);
		else
			world.setBlock(x, y, z, Block.lavaMoving.blockID);
	}

	public boolean isEmpty() {
		return tank.isEmpty();
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public boolean hasStone() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	public void setEmpty() {
		tank.empty();
	}

}
