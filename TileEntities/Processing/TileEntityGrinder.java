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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
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
import Reika.RotaryCraft.Auxiliary.GrinderDamage;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DamagingContact;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGrinder extends InventoriedPowerReceiver implements PipeConnector, IFluidHandler, DiscreteFunction,
ConditionalOperation, DamagingContact {

	/** The number of ticks that the current item has been cooking for */
	public int grinderCookTime;

	public boolean idle = false;

	public static final int MAXLUBE = 4000;

	private HybridTank tank = new HybridTank("grinder", MAXLUBE);

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
			read = ForgeDirection.EAST;
			break;
		case 1:
			id = world.getBlockId(x-1, y, z);
			read = ForgeDirection.WEST;
			break;
		case 2:
			id = world.getBlockId(x, y, z+1);
			read = ForgeDirection.SOUTH;
			break;
		case 3:
			id = world.getBlockId(x, y, z-1);
			read = ForgeDirection.NORTH;
			break;
		default:
			id = 0;
			break;
		}
		//ReikaWorldHelper.legacySetBlockWithNotify(world, readx, ready+3, readz, 4);
		return true;
	}

	public void readPower() {
		if (!this.getReceptor(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata())) {
			return;
		}
		//ReikaJavaLibrary.pConsole(readx+", "+ready+", "+readz, power > 0);
		this.getPower(false);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 1200-this.omega, "max")));
	}

	/**
	 * Returns the number of slots in the inv.
	 */
	public int getSizeInventory()
	{
		return 3;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		grinderCookTime = NBT.getShort("CookTime");

		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)grinderCookTime);

		tank.writeToNBT(NBT);
	}

	public int getCookProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.tickcount);
		return (grinderCookTime * par1)/2 / this.getOperationTime();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.testIdle();
		boolean flag1 = false;
		tickcount++;

		this.readPower();
		if (power < MINPOWER || torque < MINTORQUE) {
			grinderCookTime = 0;
			return;
		}
		if (this.canSmelt()) {
			grinderCookTime++;
			if (grinderCookTime >= this.getOperationTime()) {
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
		if (inv[2] != null && tank.getLevel() >= 1000 && !world.isRemote) {
			if (inv[2].itemID == Item.bucketEmpty.itemID && inv[2].stackSize == 1) {
				inv[2] = ItemStacks.lubebucket.copy();
				tank.removeLiquid(1000);
			}
		}
	}

	private boolean canSmelt()
	{
		if (inv[0] == null)
		{
			return false;
		}

		if (ReikaItemHelper.listContainsItemStack(grindableSeeds, inv[0])) {
			return (!tank.isFull());
		}

		ItemStack itemstack = RecipesGrinder.getRecipes().getGrindingResult(inv[0]);

		if (itemstack == null)
		{
			return false;
		}

		if (inv[1] == null)
		{
			return true;
		}

		if (!inv[1].isItemEqual(itemstack))
		{
			return false;
		}

		if (inv[1].stackSize < this.getInventoryStackLimit() && inv[1].stackSize < inv[1].getMaxStackSize())
		{
			return true;
		}

		return inv[1].stackSize < itemstack.getMaxStackSize();
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
		if (inv[0] != null && ReikaItemHelper.listContainsItemStack(grindableSeeds, inv[0])) {
			int num = 1;
			inv[0].stackSize -= num;
			tank.addLiquid(DifficultyEffects.CANOLA.getInt()*num, FluidRegistry.getFluid("lubricant"));
			if (inv[0].stackSize <= 0)
				inv[0] = null;
			return;
		}

		ItemStack itemstack = RecipesGrinder.getRecipes().getGrindingResult(inv[0]);
		if (inv[1] == null)
		{
			inv[1] = itemstack.copy();
		}
		else if (inv[1].itemID == itemstack.itemID)
		{
			inv[1].stackSize += itemstack.stackSize;
		}

		inv[0].stackSize--;

		if (inv[0].stackSize <= 0)
		{
			inv[0] = null;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += 0.85F*ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GRINDER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 1)
			return false;
		if (slot == 2)
			return is.itemID == Item.bucketEmpty.itemID;
		return is.itemID == ItemRegistry.CANOLA.getShiftedID() || RecipesGrinder.getRecipes().isGrindable(is);
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

	@Override
	public int getOperationTime() {
		return DurationRegistry.GRINDER.getOperationTime(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return this.canSmelt();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}

	@Override
	public int getContactDamage() {
		return 3;
	}

	public boolean canDealDamage() {
		return power >= MINPOWER && torque >= MINTORQUE;
	}

	@Override
	public DamageSource getDamageType() {
		return new GrinderDamage();
	}
}
