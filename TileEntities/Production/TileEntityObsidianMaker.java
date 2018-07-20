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

import net.minecraft.block.Block;
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
import Reika.DragonAPI.Instantiable.Interpolation;
import Reika.DragonAPI.Instantiable.Data.WeightedRandom;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityObsidianMaker extends InventoriedPowerReceiver implements TemperatureTE, PipeConnector, IFluidHandler, MultiOperational, ConditionalOperation {

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

	private static final Interpolation obsidianWeight = new Interpolation(false).addPoint(0.5, 0).addPoint(1, 100);
	private static final Interpolation stoneWeight = new Interpolation(false).addPoint(0.05, 0).addPoint(0.5, 100).addPoint(0.95, 0);
	private static final Interpolation cobbleWeight = new Interpolation(false).addPoint(0, 100).addPoint(0.5, 0);

	private ItemStack getProducedItem() {
		if (water.getFraction() <= 0)
			return null;
		WeightedRandom<Block> w = new WeightedRandom();
		float f = lava.getFraction()/water.getFraction();
		w.addEntry(Blocks.obsidian, obsidianWeight.getValue(f));
		w.addEntry(Blocks.stone, stoneWeight.getValue(f));
		w.addEntry(Blocks.cobblestone, cobbleWeight.getValue(f));
		return new ItemStack(w.getRandomEntry());
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
		if (temptick >= 20) {
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

		//if (rand.nextInt(20/20) == 0) {
		if (temperature > Tamb) {
			temperature--;
		}
		if (temperature < Tamb) {
			temperature++;
		}

		if (!lava.isEmpty() && water.isEmpty())
			temperature += 3;
		//}

		if (temperature > MAXTEMP/2) { //500C
			overred = 0.25F;
		}
		if (temperature > MAXTEMP/1.25) { //800C
			overred = 0.4F;
			overgreen = 0.1F;
			RotaryCraft.logger.warn("WARNING: "+this+" is reaching very high temperature!");
		}
		if (temperature > MAXTEMP && ConfigRegistry.BLOCKDAMAGE.getState()) { //1000C
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
		int lavaamt = !ReikaItemHelper.matchStackWithBlock(is, Blocks.obsidian) ? 5 : 1000;
		if (lava.getLevel() < lavaamt || water.getLevel() < 1000)
			return;
		ReikaInventoryHelper.addOrSetStack(is, inv, slot);
		if (!ReikaItemHelper.matchStackWithBlock(is, Blocks.cobblestone))
			lava.removeLiquid(lavaamt);
		water.removeLiquid(1000);
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

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
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
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		water.readFromNBT(NBT);
		lava.readFromNBT(NBT);

		mixTime = NBT.getInteger("mix");

		temperature = NBT.getInteger("temp");
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
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
}
