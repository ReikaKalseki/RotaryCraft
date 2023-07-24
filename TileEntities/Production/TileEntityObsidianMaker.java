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

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.ProcessingMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityObsidianMaker extends InventoriedPowerReceiver implements TemperatureTE, PipeConnector, IFluidHandler, MultiOperational, ProcessingMachine {

	public int mixTime;

	public int temperature;

	private int temptick = 0;

	public float overred;
	public float overgreen;
	public float overblue;

	public boolean idle = false;

	public static final int CAPACITY = 320*1000;
	public static final int MAXTEMP = 1000;

	private final HybridTank lava = new HybridTank("lavamix", CAPACITY);
	private final HybridTank water = new HybridTank("watermix", CAPACITY);

	private static final int MIN_OBSIDIAN_TEMP_0 = 500;
	public static final int MIN_OBSIDIAN_TEMP_100 = 550;
	public static final int MAX_OBSIDIAN_TEMP_100 = 750;
	private static final int MAX_OBSIDIAN_TEMP_0 = 900;

	private ItemStack getProducedItem() {
		if (ReikaMathLibrary.isValueInsideBoundsIncl(MIN_OBSIDIAN_TEMP_100, MAX_OBSIDIAN_TEMP_100, temperature))
			return new ItemStack(Blocks.obsidian);
		else if (temperature > MAX_OBSIDIAN_TEMP_100) {
			if (temperature > MAX_OBSIDIAN_TEMP_0) {
				return new ItemStack(Blocks.cobblestone);
			}
			else {
				float f = (temperature-MAX_OBSIDIAN_TEMP_100)/(float)(MAX_OBSIDIAN_TEMP_0-MAX_OBSIDIAN_TEMP_100);
				return ReikaRandomHelper.doWithChance(1-f) ? new ItemStack(Blocks.obsidian) : new ItemStack(Blocks.cobblestone);
			}
		}
		else if (temperature < MIN_OBSIDIAN_TEMP_100) {
			if (temperature < MIN_OBSIDIAN_TEMP_0) {
				return new ItemStack(Blocks.cobblestone);
			}
			else {
				float f = (MIN_OBSIDIAN_TEMP_100-temperature)/(float)(MIN_OBSIDIAN_TEMP_100-MIN_OBSIDIAN_TEMP_0);
				return ReikaRandomHelper.doWithChance(1-f) ? new ItemStack(Blocks.obsidian) : new ItemStack(Blocks.cobblestone);
			}
		}
		else {
			return new ItemStack(Blocks.bedrock); //never happens
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		temptick++;
		this.getPowerBelow();
		if (temptick >= 20 && !world.isRemote) {
			this.updateTemperature(world, x, y, z, meta);
			temptick = 0;
		}
		if (power < MINPOWER || omega < MINSPEED || water.isEmpty() || lava.isEmpty())
			return;
		this.testIdle();

		if (!world.isRemote) {
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				this.doOperation(n > 1);
		}
	}

	private void doOperation(boolean multiple) {
		if (multiple || tickcount >= this.getOperationTime()) {
			tickcount = 0;
			this.mix();
		}
	}

	public void testIdle() {
		boolean noliq = false;
		if (water.isEmpty() || lava.isEmpty())
			noliq = true;
		boolean full = this.getNonFullStack(this.getProducedItem()) == -1;
		idle = full || noliq;
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		overblue = 0;
		overgreen = 0;
		overred = 0;

		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		int max = MAXTEMP;

		//if (rand.nextInt(20/20) == 0) {
		if (temperature > Tamb) {
			temperature--;
		}
		if (temperature < Tamb) {
			temperature++;
		}

		if (!water.isEmpty()) {
			max = 600;
			if (lava.isEmpty())
				temperature = Math.max(temperature-2, Tamb);
		}

		if (!lava.isEmpty())
			temperature = Math.min(temperature+3, max);
		//}

		if (temperature > 600+(MAXTEMP-600)/2) {
			overred = 0.25F;
		}
		if (temperature > 600+(MAXTEMP-600)/1.375) {
			overred = 0.4F;
			overgreen = 0.1F;
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high temperature!");
		}
		if (temperature > 600+(MAXTEMP-600)/1.1) {
			overred = 0.55F;
			overgreen = 0.2F;
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high temperature!");
		}
		if (temperature >= MAXTEMP && ConfigRegistry.BLOCKDAMAGE.getState()) {
			this.overheat(world, x, y, z);
		}
	}

	public void overheat(World world, int x, int y, int z) {
		world.playSoundEffect(x+0.5, y+0.5, z+0.5,"random.fizz", 3F, 1F);
		world.setBlock(x, y, z, Blocks.flowing_lava);
	}

	public void mix() {
		ItemStack is = this.getProducedItem();
		if (is == null)
			return;
		int slot = this.getNonFullStack(is);
		if (slot == -1)
			return;
		//ReikaJavaLibrary.pConsole(is);
		boolean obsid = ReikaItemHelper.matchStackWithBlock(is, Blocks.obsidian);
		int lavaamt = obsid ? 1000 : 50;
		if (lava.getLevel() < lavaamt || water.getLevel() < 1000)
			return;
		ReikaInventoryHelper.addOrSetStack(is, inv, slot);
		if (!ReikaItemHelper.matchStackWithBlock(is, Blocks.cobblestone))
			lava.removeLiquid(lavaamt);
		water.removeLiquid(obsid ? 2500 : 1000);
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.fizz", 0.5F+0.5F*rand.nextFloat(), 0.7F+0.3F*rand.nextFloat());
		for (double x = xCoord+0.25; x <= xCoord+0.75; x += 0.25) {
			for (double z = zCoord+0.25; z <= zCoord+0.75; z += 0.25) {
				worldObj.spawnParticle("smoke", x, yCoord+0.75, z, 0, 0, 0);
			}
		}
	}

	public int getNonFullStack(ItemStack is) {
		for (int k = 0; k < inv.length; k++) {
			if (inv[k] == null)
				return k;
			else if (ReikaItemHelper.matchStacks(inv[k], is) && inv[k].stackSize < this.getInventoryStackLimit())
				return k;
		}
		return -1;
	}

	public int getWaterScaled(int par1)
	{
		return (water.getLevel()*par1)/CAPACITY;
	}

	public int getLavaScaled(int par1)
	{
		return (lava.getLevel()*par1)/CAPACITY;
	}

	/**
	 * Returns the number of slots in the inv.
	 */
	public int getSizeInventory()
	{
		return 9;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	public int getCookProgressScaled(int par1)
	{
		return (mixTime * par1) / (600-(int)(40*ReikaMathLibrary.logbase(omega, 2)));
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		lava.writeToNBT(NBT);
		water.writeToNBT(NBT);

		NBT.setInteger("mix", mixTime);

		NBT.setInteger("temp", temperature);

		NBT.setFloat("red", overred);
		NBT.setFloat("green", overgreen);
		NBT.setFloat("blue", overblue);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		water.readFromNBT(NBT);
		lava.readFromNBT(NBT);

		mixTime = NBT.getInteger("mix");

		temperature = NBT.getInteger("temp");

		overred = NBT.getFloat("red");
		overgreen = NBT.getFloat("green");
		overblue = NBT.getFloat("blue");
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.OBSIDIAN;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return false;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public int getRedstoneOverride() {
		return idle ? 15 : 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side.offsetY == 0;
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
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid f = resource.getFluid();
		if (!this.canFill(from, f))
			return 0;
		if (f.equals(FluidRegistry.WATER))
			return water.fill(resource, doFill);
		if (f.equals(FluidRegistry.LAVA))
			return lava.fill(resource, doFill);
		return 0;
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
		if (from.offsetY != 0)
			return false;
		return fluid.equals(FluidRegistry.WATER) || fluid.equals(FluidRegistry.LAVA);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{lava.getInfo(), water.getInfo()};
	}

	public int getWater() {
		return water.getLevel();
	}

	public int getLava() {
		return lava.getLevel();
	}

	public void addWater(int amt) {
		water.addLiquid(amt, FluidRegistry.WATER);
	}

	public void addLava(int amt) {
		lava.addLiquid(amt, FluidRegistry.LAVA);
	}

	public void setLava(int amt) {
		lava.setContents(amt, FluidRegistry.LAVA);
	}

	public void setWater(int amt) {
		water.setContents(amt, FluidRegistry.WATER);
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side.offsetY == 0 ? Flow.INPUT : Flow.NONE;
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.OBSIDIAN.getOperationTime(omega);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.OBSIDIAN.getNumberOperations(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return !lava.isEmpty() && !water.isEmpty();
	}

	@Override
	public String getOperationalStatus() {
		return lava.isEmpty() ? "No Lava" : water.isEmpty() ? "No Water" : "Operational";
	}

	@Override
	public boolean canBeCooledWithFins() {
		return true;
	}

	@Override
	public boolean allowHeatExtraction() {
		return false;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public boolean allowExternalHeating() {
		return false;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}

	@Override
	public boolean hasWork() {
		return this.areConditionsMet();
	}
}
