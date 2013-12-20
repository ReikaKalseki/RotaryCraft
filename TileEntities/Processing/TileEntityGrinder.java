/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
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
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGrinder extends InventoriedPowerReceiver implements PipeConnector, IFluidHandler {

	private ItemStack inventory[];

	/** The number of ticks that the current item has been cooking for */
	public int grinderCookTime;

	public boolean idle = false;

	public static final int MAXLUBE = 4000;

	private HybridTank tank = new HybridTank("grinder", MAXLUBE);

	public TileEntityGrinder()
	{
		inventory = new ItemStack[3];
		grinderCookTime = 0;
	}

	private static final List<ItemStack> grindableSeeds = new ArrayList();

	static {
		addGrindableSeed(ItemRegistry.CANOLA.getStackOf());
	}

	public static void addGrindableSeed(ItemStack seed) {
		grindableSeeds.add(seed);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	public void testIdle() {
		idle = (!this.canSmelt());
	}

	public boolean getReceptor(World world, int x, int y, int z, int metadata) {
		if (y == 0)
			return false;
		int id = 0;
		switch (metadata) {
		case 0:
			id = world.getBlockId(x+1, y, z);
			readx = x+1;
			readz = z;
			break;
		case 1:
			id = world.getBlockId(x-1, y, z);
			readx = x-1;
			readz = z;
			break;
		case 2:
			id = world.getBlockId(x, y, z+1);
			readx = x;
			readz = z+1;
			break;
		case 3:
			id = world.getBlockId(x, y, z-1);
			readx = x;
			readz = z-1;
			break;
		default:
			id = 0;
			break;
		}
		ready = yCoord;
		//ReikaWorldHelper.legacySetBlockWithNotify(world, readx, ready+3, readz, 4);
		return true;
	}

	public void readPower() {
		if (!this.getReceptor(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata())) {
			return;
		}
		//ReikaJavaLibrary.pConsole(readx+", "+ready+", "+readz, power > 0);
		this.getPower(false, false);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 1200-this.omega, "max")));
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
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
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

		grinderCookTime = NBT.getShort("CookTime");

		tank.readFromNBT(NBT);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setShort("CookTime", (short)grinderCookTime);

		tank.writeToNBT(NBT);

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
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.tickcount);
		return (grinderCookTime * par1)/2 / this.operationTime(omega, 0);
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.testIdle();
		boolean flag1 = false;
		tickcount++;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));

		if (this.canSmelt()) {
			flag1 = true;/*
				if (inventory[1] != null) {
					inventory[1].stackSize--;
					if (inventory[1].stackSize <= 0)
						inventory[1] = null;
				}*/
		}
		if (this.canSmelt()) {
			grinderCookTime++;
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
			if (this.operationComplete(grinderCookTime, 0)) {
				grinderCookTime = 0;
				tickcount = 0;
				this.smeltItem();
				flag1 = true;
			}
		}
		else
			grinderCookTime = 0;
		if (flag1)
			this.onInventoryChanged();
		if (inventory[2] != null && tank.getLevel() >= ItemFuelLubeBucket.LUBE_VALUE*1000 && !world.isRemote) {
			if (inventory[2].itemID == Item.bucketEmpty.itemID && inventory[2].stackSize == 1) {
				inventory[2] = ItemStacks.lubebucket.copy();
				tank.removeLiquid(ItemFuelLubeBucket.LUBE_VALUE*1000);
			}
		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt()
	{
		this.readPower();
		if (!(power >= MINPOWER && torque >= MINTORQUE))
			return false;
		if (inventory[0] == null)
		{
			return false;
		}

		if (ReikaItemHelper.listContainsItemStack(grindableSeeds, inventory[0])) {
			return (!tank.isFull());
		}

		ItemStack itemstack = RecipesGrinder.getRecipes().getSmeltingResult(inventory[0]);

		if (itemstack == null)
		{
			return false;
		}

		if (inventory[1] == null)
		{
			return true;
		}

		if (!inventory[1].isItemEqual(itemstack))
		{
			return false;
		}

		if (inventory[1].stackSize < this.getInventoryStackLimit() && inventory[1].stackSize < inventory[1].getMaxStackSize())
		{
			return true;
		}

		return inventory[1].stackSize < itemstack.getMaxStackSize();
	}

	public int getLubricantScaled(int par1)
	{
		return tank.getLevel()*par1/MAXLUBE;
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem()
	{
		if (!this.canSmelt())
		{
			return;
		}
		if (inventory[0] != null && ReikaItemHelper.listContainsItemStack(grindableSeeds, inventory[0])) {
			int num = 1;
			inventory[0].stackSize -= num;
			tank.addLiquid(DifficultyEffects.CANOLA.getInt()*num, FluidRegistry.getFluid("lubricant"));
			if (inventory[0].stackSize <= 0)
				inventory[0] = null;
			return;
		}

		ItemStack itemstack = RecipesGrinder.getRecipes().getSmeltingResult(inventory[0]);
		if (inventory[1] == null)
		{
			inventory[1] = itemstack.copy();
		}
		else if (inventory[1].itemID == itemstack.itemID)
		{
			inventory[1].stackSize += itemstack.stackSize;
		}

		inventory[0].stackSize--;

		if (inventory[0].stackSize <= 0)
		{
			inventory[0] = null;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += 0.85F*ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.GRINDER.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 1)
			return false;
		if (slot == 2)
			return is.itemID == Item.bucketEmpty.itemID;
		return is.itemID == ItemRegistry.CANOLA.getShiftedID() || RecipesGrinder.getRecipes().getSmeltingResult(is) != null;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canSmelt())
			return 15;
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side != ForgeDirection.DOWN;
	}

	@Override
	public void onEMP() {}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (this.canDrain(from, null))
			return tank.drain(resource.amount, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (this.canDrain(from, null))
			return tank.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public void setLevel(int amt) {
		tank.setContents(amt, FluidRegistry.getFluid("lubricant"));
	}

	public void removeLiquid(int amt) {
		tank.removeLiquid(amt);
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side != ForgeDirection.UP ? Flow.OUTPUT : Flow.NONE;
	}
}
