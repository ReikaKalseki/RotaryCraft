/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
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
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityLavaMaker extends InventoriedPowerLiquidProducer implements IFluidHandler, PipeConnector, TemperatureTE, ConditionalOperation {

	public static final int CAPACITY = 64000;

	public static final int MELT_ENERGY = 2820000; //approx

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
				SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, 0.5F, 0.5F);
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
		if (worldObj.isRemote)
			return false;
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
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return fuels.contains(is.itemID);
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
	public MachineRegistry getMachine() {
		return MachineRegistry.LAVAMAKER;
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
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		energy = NBT.getLong("e");
		temperature = NBT.getInteger("temp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setLong("e", energy);
		NBT.setInteger("temp", temperature);
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaBiomeHelper.getBiomeTemp(world, x, z);
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.fire.blockID);
		if (fireside != null) {
			Tamb += 200;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava);
		if (lavaside != null) {
			Tamb += 600;
		}
		if (power > 0) {
			temperature += 2.5*ReikaMathLibrary.logbase(power, 2);
		}
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/12;
		}
		else {
			temperature += (Tamb-temperature)/12;
		}
		if (temperature - Tamb <= 4 && temperature > Tamb)
			temperature--;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
		if (temperature > 50) {
			ForgeDirection side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.snow.blockID);
			if (side != null)
				ReikaWorldHelper.changeAdjBlock(world, x, y, z, side, 0, 0);
			side = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
			if (side != null)
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

	public boolean hasStone() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	public void setEmpty() {
		tank.empty();
	}

	public void removeLava(int amt) {
		tank.removeLiquid(amt);
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to.offsetY == 0;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Rock";
	}

}
