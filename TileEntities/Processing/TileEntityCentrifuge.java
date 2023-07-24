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
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.TemporaryInventory;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceManipulator;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ItemWithChance;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.ProcessingMachine;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge.CentrifugeRecipe;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCentrifuge extends InventoriedPowerReceiver implements MultiOperational, ProcessingMachine, IFluidHandler, PipeConnector, EnchantableMachine {

	private int progressTime;
	public static final int CAPACITY = 10000;
	private final HybridTank tank = new HybridTank("centrifuge", CAPACITY);

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.fortune).addFilter(Enchantment.field_151370_z);

	private final ChanceManipulator fortuneManipulator = new ChanceManipulator() {

		@Override
		public float getChance(float original) {
			float val = original;
			int fortune = enchantments.getEnchantment(Enchantment.fortune);
			if (fortune > 0 && val < 1) {
				double f = 1+fortune/3D;
				//val = (float)Math.pow(1+val, f);
				val = (float)Math.pow(val, 1D/f);
				val = RecipesCentrifuge.getRecipes().rounder.getChance(val);
			}
			return val;
		}

	};

	public int getProgressScaled(int l) {
		return l * progressTime / this.getOperationTime();
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		phi += ReikaMathLibrary.logbase2(omega);
	}

	@Override
	public MachineRegistry getTile() {
		return MachineRegistry.CENTRIFUGE;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (isFlipped)
			this.getPowerAbove();
		else
			this.getPowerBelow();

		if (power >= MINPOWER && omega >= MINSPEED) {
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				this.doOperation(n > 1);
		}
		else {
			progressTime = 0;
		}
	}

	private void doOperation(boolean multiple) {
		ItemStack in = inv[0];
		if (in != null && RecipesCentrifuge.getRecipes().isCentrifugable(in)) {
			progressTime++;

			if (multiple || progressTime >= this.getOperationTime()) {
				CentrifugeRecipe out = RecipesCentrifuge.getRecipes().getRecipeResult(in);
				Collection<ItemWithChance> items = out.getItems();
				for (int i = 0; i < out.maxStack && inv[0] != null; i++) {
					if (this.canMakeAllOf(items)) {
						FluidStack fs = out.getFluid();
						double enchantBonus = 1+0.1*enchantments.getEnchantment(Enchantment.field_151370_z);
						if (fs != null) {
							fs = fs.copy();
							fs.amount *= enchantBonus;
						}
						if (fs == null || tank.canTakeIn(fs)) {
							Collection<ItemStack> c = out.rollItems(fortuneManipulator);
							for (ItemStack is : c) {
								ReikaInventoryHelper.addToIInv(is, this, true, 1, this.getSizeInventory());
							}
							fs = out.rollFluid(fortuneManipulator);
							if (fs != null) {
								int amt = fs.amount;
								amt *= enchantBonus;
								tank.addLiquid(amt, fs.getFluid());
							}
							ReikaInventoryHelper.decrStack(0, inv);
						}
						else {
							break;
						}
					}
					else {
						break;
					}
				}
				progressTime = 0;
			}
		}
		else {
			progressTime = 0;
		}
	}

	private boolean canMakeAllOf(Collection<ItemWithChance> out) {
		IInventory temp = new TemporaryInventory(9);
		for (int i = 0; i < 9; i++) {
			ItemStack in = inv[i+1];
			if (in != null)
				temp.setInventorySlotContents(i, in.copy());
		}
		for (ItemWithChance is : out) {
			if (!ReikaInventoryHelper.addToIInv(is.getItem(), temp))
				return false;
		}
		return true;
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getFluid() {
		return tank.getActualFluid();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i != 0;
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 0 && RecipesCentrifuge.getRecipes().isCentrifugable(itemstack);
	}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && RecipesCentrifuge.getRecipes().isCentrifugable(inv[0]);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Missing Items";
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.CENTRIFUGE.getOperationTime(omega);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.CENTRIFUGE.getNumberOperations(omega);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		progressTime = NBT.getInteger("CookTime");
		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("CookTime", progressTime);
		tank.writeToNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setTag("enchants", enchantments.writeToNBT());
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe() || m == MachineRegistry.HOSE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side.offsetY == 0;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side.offsetY == 0 ? Flow.OUTPUT : Flow.NONE;
	}

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
		return this.canDrain(from, null) ? tank.drain(maxDrain, doDrain) : null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from.offsetY == 0 && ReikaFluidHelper.isFluidDrainableFromTank(fluid, tank);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLiquidScaled(int a) {
		return a * tank.getLevel() / tank.getCapacity();
	}

	public int getProgress() {
		return progressTime;
	}

	@SideOnly(Side.CLIENT)
	public void syncProgress(int time) {
		progressTime = time;
	}

	@Override
	public boolean hasWork() {
		return this.areConditionsMet();
	}

}
