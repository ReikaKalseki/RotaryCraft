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

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesLavaMaker;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityLavaMaker extends InventoriedPowerLiquidProducer implements IFluidHandler, PipeConnector, TemperatureTE, ConditionalOperation {

	public static final int CAPACITY = 64000;

	public static final int MAXTEMP = 1800;

	private int temperature;
	private long energy;

	private StepTimer timer = new StepTimer(20);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		timer.update();
		if (timer.checkCap())
			this.updateTemperature(world, x, y, z, meta);
		if (temperature > this.getMeltingTemperature())
			energy += power;
		else
			energy *= 0.85;

		//ReikaJavaLibrary.pConsole(this.getMeltingTemperature()+":"+energy/20F/MELT_ENERGY, Side.SERVER);

		tickcount++;
		if (omega > 0 && power > 0) {
			if (tickcount > 98) {
				SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.1F : 0.5F, 0.5F);
				tickcount = 0;
			}
			world.spawnParticle("crit", x+rand.nextDouble(), y, z+rand.nextDouble(), -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
		}


		for (int i = 0; i < inv.length; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(is);
				long melt_energy = RecipesLavaMaker.getRecipes().getMeltingEnergy(is);
				//ReikaJavaLibrary.pConsole(energy/20L+":"+melt_energy, Side.SERVER);
				if (fs != null) {
					if (this.canMake(fs) && energy >= melt_energy*20) {
						tank.addLiquid(fs.amount, fs.getFluid());
						ReikaInventoryHelper.decrStack(i, inv);
						energy -= melt_energy*20;
						return;
					}
				}
			}
		}
	}

	private int getMeltingTemperature() {
		for (int i = 0; i < inv.length; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				int temp = RecipesLavaMaker.getRecipes().getMeltTemperature(is);
				if (temp > Integer.MIN_VALUE) {
					return temp;
				}
			}
		}
		return Integer.MAX_VALUE;
	}

	private boolean canMake(FluidStack liq) {
		if (worldObj.isRemote)
			return false;
		if (tank.isEmpty())
			return true;
		if (!tank.getActualFluid().equals(liq.getFluid()))
			return false;
		if (tank.getLevel()+liq.amount > tank.getCapacity())
			return false;
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
		return RecipesLavaMaker.getRecipes().isValidFuel(is);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
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
		for (int i = 0; i < inv.length; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(is);
				if (fs != null)
					if (this.canMake(fs))
						return true;
			}
		}
		return false;
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
		return this.areConditionsMet() ? "Operational" : "No Items";
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (power > 0) {
			temperature += ReikaMathLibrary.logbase(power, 2);
		}
		if (temperature > Tamb) {
			temperature -= (temperature-Tamb)/64;
		}
		else {
			temperature += (temperature-Tamb)/64;
		}
		if (temperature - Tamb <= 64 && temperature > Tamb)
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

	}

}
