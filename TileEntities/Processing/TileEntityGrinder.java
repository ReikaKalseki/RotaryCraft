/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.Collection;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
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

	public int grinderCookTime;

	public boolean idle = false;

	public static final int MAXLUBE = 4000;

	private HybridTank tank = new HybridTank("grinder", MAXLUBE);

	private static final ItemHashMap<Float> grindableSeeds = new ItemHashMap();
	private static final List<ItemStack> protectedEntries = new ArrayList<ItemStack>();

	static {
		addGrindableSeed(ItemRegistry.CANOLA.getStackOf(), 1F);
		protectedEntries.add(ItemRegistry.CANOLA.getStackOf());
		//addGrindableSeed(ItemRegistry.CANOLA.getStackOfMetadata(2), 0.65F);
	}

	public static void addGrindableSeed(ItemStack seed, float factor) {
		grindableSeeds.put(seed, MathHelper.clamp_float(factor, 0, 1));
	}
	
	public static void removeGrindableSeed(ItemStack seed) {
		for (ItemStack entry : protectedEntries) {
			if (entry.isItemEqual(seed)) return;
		}
		grindableSeeds.remove(seed);
	}

	public static boolean isGrindableSeed(ItemStack seed) {
		return grindableSeeds.containsKey(seed);
	}

	public static Collection<ItemStack> getGrindableSeeds() {
		return grindableSeeds.keySet();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	public void testIdle() {
		idle = (!this.canGrind());
	}

	public boolean getReceptor(World world, int x, int y, int z, int metadata) {
		if (y == 0)
			return false;
		switch (metadata) {
		case 0:
			read = ForgeDirection.EAST;
			break;
		case 1:
			read = ForgeDirection.WEST;
			break;
		case 2:
			read = ForgeDirection.SOUTH;
			break;
		case 3:
			read = ForgeDirection.NORTH;
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
		if (this.canGrind()) {
			grinderCookTime++;
			if (grinderCookTime >= this.getOperationTime()) {
				grinderCookTime = 0;
				tickcount = 0;
				this.grind();
				flag1 = true;
			}
		}
		else
			grinderCookTime = 0;
		if (flag1)
			this.markDirty();
		if (inv[2] != null && tank.getLevel() >= 1000 && !world.isRemote) {
			if (inv[2].getItem() == Items.bucket && inv[2].stackSize == 1) {
				inv[2] = ItemStacks.lubebucket.copy();
				tank.removeLiquid(1000);
			}
		}
	}

	private boolean canGrind() {
		if (inv[0] == null)
			return false;

		boolean flag = false;
		if (isGrindableSeed(inv[0])) {
			flag = true;
			if (tank.isFull()) {
				return false;
			}
		}

		ItemStack itemstack = RecipesGrinder.getRecipes().getGrindingResult(inv[0]);

		if (flag && itemstack == null)
			return true;
		if (itemstack == null)
			return false;

		if (inv[1] == null)
			return true;

		if (!inv[1].isItemEqual(itemstack))
			return false;

		if (inv[1].stackSize < this.getInventoryStackLimit() && inv[1].stackSize < inv[1].getMaxStackSize())
			return true;

		return inv[1].stackSize < itemstack.getMaxStackSize();
	}

	public int getLubricantScaled(int par1) {
		return tank.getLevel()*par1/MAXLUBE;
	}

	private void grind() {
		ItemStack is = inv[0];

		if (is != null && isGrindableSeed(is)) {
			float num = grindableSeeds.get(is);
			tank.addLiquid((int)(DifficultyEffects.CANOLA.getInt()*num), FluidRegistry.getFluid("lubricant"));
		}

		ItemStack itemstack = RecipesGrinder.getRecipes().getGrindingResult(is);
		if (itemstack != null) {
			if (inv[1] == null)
				inv[1] = itemstack.copy();
			else if (inv[1].getItem() == itemstack.getItem())
				inv[1].stackSize += itemstack.stackSize;
		}

		is.stackSize--;

		if (is.stackSize <= 0)
			inv[0] = null;
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
			return is.getItem() == Items.bucket;
		return is.getItem() == ItemRegistry.CANOLA.getItemInstance() || RecipesGrinder.getRecipes().isGrindable(is);
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canGrind())
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
		return this.canGrind();
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
