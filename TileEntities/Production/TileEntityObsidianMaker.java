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
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Models.ModelObsidian;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;

public class TileEntityObsidianMaker extends TileEntityInventoriedPowerReceiver implements TemperatureTE, PipeConnector, IFluidHandler {

	public int mixTime;

	public int temperature;

	private int temptick = 0;

	public float overred;
	public float overgreen;
	public float overblue;

	public boolean idle = false;

	public static final int CAPACITY = 320*RotaryConfig.MILLIBUCKET;
	public static final int MAXTEMP = 1000;

	public ItemStack[] inventory = new ItemStack[9];

	private HybridTank lava = new HybridTank("lavamix", CAPACITY);
	private HybridTank water = new HybridTank("watermix", CAPACITY);

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
		this.getWater(world, x, y, z, meta);
		this.getLava(world, x, y, z, meta);
		if (temptick >= 20) {
			this.updateTemperature(world, x, y, z, meta);
			temptick = 0;
		}
		if (power < MINPOWER || omega < MINSPEED || water.isEmpty() || lava.isEmpty())
			return;
		this.testIdle();
		if (this.operationComplete(tickcount, 0)) {
			tickcount = 0;
			this.mix();
		}
	}

	public void testIdle() {
		boolean noliq = false;
		if (water.isEmpty() || lava.isEmpty())
			noliq = true;
		boolean full = (this.getNonFullStack() == -1);
		idle = (full || noliq);
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		overblue = 0;
		overgreen = 0;
		overred = 0;

		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);

		if (rand.nextInt(20) == 0) {
			if (temperature > Tamb) {
				temperature--;
			}
			if (temperature < Tamb) {
				temperature++;
			}

			if (!lava.isEmpty() && water.isEmpty())
				temperature += 3;
		}

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
		ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, Block.lavaMoving.blockID);
	}

	public void mix() {
		int slot = this.getNonFullStack();
		if (slot == -1)
			return;
		lava.removeLiquid(RotaryConfig.MILLIBUCKET);
		water.removeLiquid(RotaryConfig.MILLIBUCKET);
		ReikaInventoryHelper.addOrSetStack(Block.obsidian.blockID, 1, 0, inventory, slot);
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.fizz", 0.5F+0.5F*rand.nextFloat(), 0.7F+0.3F*rand.nextFloat());
		worldObj.spawnParticle("smoke", xCoord+0.5, yCoord+0.75, zCoord+0.25, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.5, yCoord+0.75, zCoord+0.5, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.5, yCoord+0.75, zCoord+0.75, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.25, yCoord+0.75, zCoord+0.25, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.25, yCoord+0.75, zCoord+0.5, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.25, yCoord+0.75, zCoord+0.75, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.75, yCoord+0.75, zCoord+0.25, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.75, yCoord+0.75, zCoord+0.5, 0, 0, 0);
		worldObj.spawnParticle("smoke", xCoord+0.75, yCoord+0.75, zCoord+0.75, 0, 0, 0);
	}

	public int getNonFullStack() {
		int slot = -1;
		for (int k = 0; k < inventory.length && slot == -1; k++) {
			if (inventory[k] == null)
				slot = k;
			else if (inventory[k].itemID == Block.obsidian.blockID && inventory[k].stackSize < this.getInventoryStackLimit())
				slot = k;
		}
		return slot;
	}

	public int getWaterScaled(int par1)
	{
		return (water.getLevel()*par1)/CAPACITY;
	}

	public int getLavaScaled(int par1)
	{
		return (lava.getLevel()*par1)/CAPACITY;
	}

	public void getLava(World world, int x, int y, int z, int metadata) {
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Lava!");
		int oldLevel = 0;
		if (lava.getLevel() < CAPACITY) {
			for (int i = 2; i < 6; i++) {
				ForgeDirection dir = dirs[i];
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if (MachineRegistry.getMachine(world, dx, dy, dz) == MachineRegistry.PIPE) {
					TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
					if (tile != null && tile.contains(FluidRegistry.LAVA) && tile.liquidLevel > 0) {
						oldLevel = tile.liquidLevel;
						tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
						lava.addLiquid(oldLevel/4+1, FluidRegistry.LAVA);
					}
				}
			}
		}
	}

	public void getWater(World world, int x, int y, int z, int metadata) {
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Water!");
		int oldLevel = 0;
		if (water.getLevel() < CAPACITY) {
			for (int i = 2; i < 6; i++) {
				ForgeDirection dir = dirs[i];
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if (MachineRegistry.getMachine(world, dx, dy, dz) == MachineRegistry.PIPE) {
					TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
					if (tile != null && tile.contains(FluidRegistry.WATER) && tile.liquidLevel > 0) {
						oldLevel = tile.liquidLevel;
						tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
						water.addLiquid(oldLevel/4+1, FluidRegistry.WATER);
					}
				}
			}
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inventory.length;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inventory[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
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
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		lava.writeToNBT(NBT);
		water.writeToNBT(NBT);

		NBT.setInteger("mix", mixTime);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		water.readFromNBT(NBT);
		lava.readFromNBT(NBT);

		mixTime = NBT.getInteger("mix");

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelObsidian();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.OBSIDIAN.ordinal();
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
		if (this.getNonFullStack() == -1)
			return 15;
		if (lava.isEmpty())
			return 15;
		if (water.isEmpty())
			return 15;
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
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

	public void setLava(int amt) {
		lava.setContents(amt, FluidRegistry.LAVA);
	}

	public void setWater(int amt) {
		water.setContents(amt, FluidRegistry.WATER);
	}
}
