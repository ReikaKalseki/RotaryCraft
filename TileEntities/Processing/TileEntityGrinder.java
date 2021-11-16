/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.Collection;

import net.minecraft.enchantment.Enchantment;
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
import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWaySet;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.DamagingContact;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.ProcessingMachine;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGrinder extends InventoriedPowerReceiver implements PipeConnector, IFluidHandler, MultiOperational,
ProcessingMachine, DamagingContact, EnchantableMachine {

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.looting).addFilter(Enchantment.knockback).addFilter(Enchantment.flame).addFilter(Enchantment.fortune);

	public int grinderCookTime;

	public boolean idle = false;

	public static final int MAXLUBE = 4000;
	private static final int MIN_LUBE_PRODUCTION = DifficultyEffects.CANOLA.getAverageAmount();

	private final HybridTank tank = new HybridTank("grinder", MAXLUBE);

	private static final ItemHashMap<Float> grindableSeeds = new ItemHashMap();
	private static final OneWaySet<KeyedItemStack> lockedSeeds = new OneWaySet();

	static {
		//addGrindableSeed(ItemRegistry.CANOLA.getStackOf(), 1F);
		grindableSeeds.put(ItemStacks.canolaSeeds, 1F);
		lockedSeeds.add(new KeyedItemStack(ItemRegistry.CANOLA.getItemInstance()).lock());
		//addGrindableSeed(ItemRegistry.CANOLA.getStackOfMetadata(2), 0.65F);
	}

	public static void addGrindableSeed(ItemStack seed, float factor) {
		if (!lockedSeeds.contains(seed))
			grindableSeeds.put(seed, MathHelper.clamp_float(factor, 0, 0.75F));
	}

	public static boolean isGrindableSeed(ItemStack seed) {
		return grindableSeeds.containsKey(seed);
	}

	public static Collection<ItemStack> getGrindableSeeds() {
		return grindableSeeds.keySet();
	}

	public static void removeGrindableSeed(ItemStack seed) {
		if (!lockedSeeds.contains(seed))
			grindableSeeds.remove(seed);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	public void testIdle() {
		idle = !this.canGrind();
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

	public int getSizeInventory() {
		return 3;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		grinderCookTime = NBT.getShort("CookTime");

		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)grinderCookTime);

		tank.writeToNBT(NBT);
	}

	public int getCookProgressScaled(int par1) {
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

		int n = this.getNumberConsecutiveOperations();
		for (int i = 0; i < n; i++)
			flag1 |= this.doOperation(n > 1);

		if (flag1)
			this.markDirty();
		if (inv[2] != null && tank.getLevel() >= 1000 && !world.isRemote) {
			if (inv[2].getItem() == Items.bucket && inv[2].stackSize == 1) {
				inv[2] = ItemStacks.lubebucket.copy();
				tank.removeLiquid(1000);
			}
		}
	}

	private boolean doOperation(boolean multiple) {
		if (this.canGrind()) {
			grinderCookTime++;
			if (multiple || grinderCookTime >= this.getOperationTime()) {
				grinderCookTime = 0;
				tickcount = 0;
				this.grind();
			}
			return true;
		}
		else {
			grinderCookTime = 0;
			return false;
		}
	}

	private boolean canGrind() {
		if (inv[0] == null)
			return false;

		boolean flag = false;
		if (isGrindableSeed(inv[0])) {
			flag = true;
			if (tank.getRemainingSpace() < MIN_LUBE_PRODUCTION) {
				return false;
			}
		}

		ItemStack out = RecipesGrinder.getRecipes().getGrindingResult(inv[0]);

		if (flag && out == null)
			return true;
		if (out == null)
			return false;

		if (inv[1] == null)
			return true;

		if (!inv[1].isItemEqual(out))
			return false;

		return inv[1].stackSize+out.stackSize <= Math.min(this.getInventoryStackLimit(), out.getMaxStackSize());
	}

	public int getLubricantScaled(int par1) {
		return tank.getLevel()*par1/MAXLUBE;
	}

	private void grind() {
		ItemStack is = inv[0];

		if (is != null && isGrindableSeed(is)) {
			float num = grindableSeeds.get(is);
			tank.addLiquid((int)(DifficultyEffects.CANOLA.getInt()*this.getFortuneLubricantFactor()*num), FluidRegistry.getFluid("rc lubricant"));
		}

		ItemStack out = RecipesGrinder.getRecipes().getGrindingResult(is);
		if (out != null) {
			if (inv[1] == null)
				inv[1] = out.copy();
			else if (inv[1].getItem() == out.getItem())
				inv[1].stackSize += out.stackSize;
		}

		is.stackSize--;

		if (is.stackSize <= 0)
			inv[0] = null;
	}

	private float getFortuneLubricantFactor() {
		return 1F+(float)(enchantments.getEnchantment(Enchantment.fortune)*ReikaRandomHelper.getRandomBetween(0.1, 0.2));
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
		return m == MachineRegistry.HOSE || m == MachineRegistry.BEDPIPE;
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
		return this.canDrain(from, resource.getFluid()) ? tank.drain(resource.amount, doDrain) : null;
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
		return from != ForgeDirection.UP && ReikaFluidHelper.isFluidDrainableFromTank(fluid, tank);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public void setLevel(int amt) {
		tank.setContents(amt, FluidRegistry.getFluid("rc lubricant"));
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
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.GRINDER.getNumberOperations(omega);
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
		return RotaryCraft.grind;
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}

	@Override
	public boolean hasWork() {
		return this.areConditionsMet();
	}
}
