/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.XPProducer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBigFurnace extends InventoriedPowerLiquidReceiver implements TemperatureTE, XPProducer, DiscreteFunction, ConditionalOperation {

	public static final int HEIGHT = 2;
	public static final int WIDTH = 9;

	public static final int MAXTEMP = 1000;

	public static final int SMELT_TEMP = 400;

	private float xp;

	private int temperature;

	public int smeltTick;
	private StepTimer smelter = new StepTimer(200);
	private StepTimer tempTimer = new StepTimer(20);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		tempTimer.update();
		if (tempTimer.checkCap())
			this.updateTemperature(world, x, y, z, meta);

		smelter.setCap(this.getOperationTime());

		if (!worldObj.isRemote) {
			if (this.canSmelt()) {
				smelter.update();
				if (smelter.checkCap())
					if (!worldObj.isRemote)
						this.smelt();
			}
			else
				smelter.reset();
		}
		smeltTick = smelter.getTick();
	}

	public int getNumberInputSlots() {
		return WIDTH * HEIGHT;
	}

	private void smelt() {
		int n = this.getNumberInputSlots();
		for (int i = 0; i < n; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				ItemStack to = FurnaceRecipes.smelting().getSmeltingResult(is);
				if (to != null) {
					boolean add = false;
					if (inv[i+n] == null) {
						inv[i+n] = to.copy();
						add = true;
					}
					else {
						if (ReikaItemHelper.canCombineStacks(to, inv[i+n])) {
							add = true;
							inv[i+n].stackSize += to.stackSize;
						}
					}
					if (add)
						ReikaInventoryHelper.decrStack(i, inv);
				}
			}
		}
	}

	private boolean canSmelt() {
		if (temperature < SMELT_TEMP)
			return false;
		if (power < MINPOWER)
			return false;
		int n = this.getNumberInputSlots();
		for (int i = 0; i < n; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				ItemStack to = FurnaceRecipes.smelting().getSmeltingResult(is);
				if (to != null) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i >= this.getNumberInputSlots();
	}

	@Override
	public int getSizeInventory() {
		return WIDTH * HEIGHT * 2;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);

		if (!tank.isEmpty() && tank.getActualFluid().equals(FluidRegistry.LAVA)) {
			tank.removeLiquid(15);
			Tamb += 600;
		}

		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
		if (temperature > 100) {
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
		return temperature/200;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot < this.getNumberInputSlots();
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BIGFURNACE;
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
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.LAVA;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
	}

	@Override
	public int getCapacity() {
		return 16000;
	}

	@Override
	public void clearXP() {
		xp = 0;
	}

	@Override
	public float getXP() {
		return xp;
	}

	public int getCookScaled(int i) {
		return smeltTick * i / smelter.getCap();
	}

	public int getLavaScaled(int i) {
		return tank.getLevel() * i / tank.getCapacity();
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		temperature = NBT.getInteger("temp");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("temp", temperature);
	}

	@Override
	public int getOperationTime() {
		return temperature >= 600 ? 150 : 200;
	}

	@Override
	public boolean areConditionsMet() {
		return this.canSmelt();
	}

	@Override
	public String getOperationalStatus() {
		if (temperature < SMELT_TEMP)
			return "Insufficient Temperature";
		return this.areConditionsMet() ? "Operational" : "No Smeltable Items";
	}

}
