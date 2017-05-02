/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.MulchMaterials;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFermenter extends InventoriedPowerLiquidReceiver implements TemperatureTE, MultiOperational, ConditionalOperation
{

	/** The number of ticks that the current item has been cooking for */
	public int fermenterCookTime = 0;

	public static final int MINUSEFULTEMP = 20;
	public static final int OPTMULTIPLYTEMP = 25;
	public static final int MAXUSEFULTEMP = 40;
	public static final int OPTFERMENTTEMP = 35;
	public static final int MAXTEMP = 60;

	public static final int CAPACITY = 4000;
	public static final int CONSUME_WATER = 50;

	public int temperature;

	public boolean idle = false;

	private int temperaturetick = 0;

	@Override
	protected int getActiveTexture() {
		return power >= MINPOWER && omega >= MINSPEED && this.canMake() ? 1 : 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 2;
	}

	// Return the itemstack product from the input items.
	private ItemStack getRecipe() {
		for (int i = 0; i < 2; i++)
			if (inv[i] == null)
				return null;
		if (inv[0].getItem() == Items.sugar) {
			if (this.hasWater())
				if (ReikaItemHelper.matchStackWithBlock(inv[1], Blocks.dirt))
					return !ConfigRegistry.enableFermenterYeast() ? null : new ItemStack(ItemRegistry.YEAST.getItemInstance(), 1, 0);
		}
		if (inv[0].getItem() == ItemRegistry.YEAST.getItemInstance()) {
			if (MulchMaterials.instance.isMulchable(inv[1]))
				if (this.hasWater())
					return ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, MulchMaterials.instance.getPlantValue(inv[1]));
		}
		return null;
	}

	private boolean hasWater() {
		return !tank.isEmpty();
	}

	private float getFermentRate() {
		boolean fermenting = true;
		if (this.getRecipe() == null)
			return -1F;
		if (this.getRecipe().getItem() == ItemRegistry.YEAST.getItemInstance())
			fermenting = false;
		if (temperature < MINUSEFULTEMP)
			return 1F/(MINUSEFULTEMP-temperature);
		if (temperature > MAXUSEFULTEMP)
			return 1F/(temperature-MAXUSEFULTEMP);
		float Tdiff = temperature-OPTMULTIPLYTEMP;
		if (fermenting)
			Tdiff = temperature-OPTFERMENTTEMP;
		if (Tdiff < 0)
			Tdiff = -Tdiff;
		return (float)Math.pow(1-Tdiff/16F, 0.2);
	}

	public void testIdle() {
		idle = (this.getRecipe() == null);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		temperaturetick++;
		tickcount++;
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);

		if (temperaturetick >= 20) {
			temperaturetick = 0;
			this.updateTemperature(world, x, y, z, meta);
		}

		if (power < MINPOWER || omega < MINSPEED)
			return;

		int n = this.getNumberConsecutiveOperations();
		for (int i = 0; i < n; i++)
			this.doOperation(n > 1);
	}

	private void doOperation(boolean multiple) {
		ItemStack product = this.getRecipe();

		if (tickcount >= 2+rand.nextInt(18)) {
			this.testYeastKill();
			tickcount = 0;
		}

		if (product == null) {
			idle = true;
			fermenterCookTime = 0;
			return;
		}
		if (product.getItem() != ItemRegistry.YEAST.getItemInstance() && !ReikaItemHelper.matchStacks(product, ItemStacks.sludge))
			return;

		if (inv[2] != null) {
			if (product.getItem() != inv[2].getItem()) {
				fermenterCookTime = 0;
				return;
			}
		}
		idle = false;
		if (inv[2] != null) {
			if (inv[2].stackSize+product.stackSize > inv[2].getMaxStackSize()) {
				fermenterCookTime = 0;
				return;
			}
		}
		fermenterCookTime++;
		if (multiple || fermenterCookTime >= this.getOperationTime()) {
			this.make(product);
			fermenterCookTime = 0;
		}
	}

	private boolean canMake() {
		ItemStack product = this.getRecipe();
		if (product == null) {
			return false;
		}
		if (product.getItem() != ItemRegistry.YEAST.getItemInstance() && !ReikaItemHelper.matchStacks(product, ItemStacks.sludge))
			return false;
		if (inv[2] != null) {
			if (product.getItem() != inv[2].getItem()) {
				return false;
			}
			if (inv[2].stackSize+product.stackSize > inv[2].getMaxStackSize()) {
				return false;
			}
		}
		return true;
	}

	private void make(ItemStack product) {
		if (product.getItem() == ItemRegistry.YEAST.getItemInstance()) {
			if (inv[2] == null)
				inv[2] = new ItemStack(ItemRegistry.YEAST.getItemInstance(), 1, 0);
			else if (inv[2].getItem() == ItemRegistry.YEAST.getItemInstance()) {
				if (inv[2].stackSize < inv[2].getMaxStackSize())
					inv[2].stackSize++;
				else
					return;
			}
			else {
				fermenterCookTime = 0;
				return;
			}
			ReikaInventoryHelper.decrStack(0, inv);
			if (rand.nextInt(4) == 0)
				ReikaInventoryHelper.decrStack(1, inv);
		}
		if (ReikaItemHelper.matchStacks(product, ItemStacks.sludge)) {
			if (inv[2] == null)
				inv[2] = product.copy();
			else if (ReikaItemHelper.matchStacks(inv[2], ItemStacks.sludge)) {
				if (inv[2].stackSize < inv[2].getMaxStackSize())
					inv[2].stackSize += product.stackSize;
				else
					return;
			}
			else {
				fermenterCookTime = 0;
				return;
			}
			ReikaInventoryHelper.decrStack(1, inv);
			if (rand.nextInt(2) == 0)
				ReikaInventoryHelper.decrStack(0, inv);
		}
		this.markDirty();
		tank.removeLiquid(CONSUME_WATER);
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		ForgeDirection waterside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.water);
		if (waterside != null) {
			Tamb -= 5;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.ice);
		if (iceside != null) {
			Tamb -= 15;
		}
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.fire);
		if (fireside != null) {
			Tamb += 50;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjSourceBlock(world, x, y, z, Material.lava);
		if (lavaside != null) {
			Tamb += 200;
		}
		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
	}

	public void testYeastKill() {
		if (temperature < MAXTEMP)
			return;
		int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.YEAST.getItemInstance(), inv);
		if (slot != -1) {
			ReikaInventoryHelper.decrStack(slot, inv);
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.8F, 0.8F);
		}
	}

	public int getSizeInventory() {
		return 3;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");

		fermenterCookTime = NBT.getShort("CookTime");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);
		NBT.setShort("CookTime", (short)fermenterCookTime);
	}

	public int getCookProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.operationTime(0));
		return (fermenterCookTime * par1)/2 / this.getOperationTime();
	}

	public int getTemperatureScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.operationTime(0));
		return (temperature * par1) / MAXTEMP;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FERMENTER;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		if (i >= 2)
			return false;
		if (this.hasRedstoneSignal() || !ConfigRegistry.enableFermenterYeast()) {
			switch(i) {
				case 0:
					return is.getItem() == ItemRegistry.YEAST.getItemInstance();
				case 1:
					return MulchMaterials.instance.isMulchable(is);//ReikaItemHelper.matchStacks(is, ItemStacks.mulch);
			}
		}
		else {
			switch(i) {
				case 0:
					return is.getItem() == Items.sugar;
				case 1:
					return ReikaItemHelper.matchStackWithBlock(is, Blocks.dirt);
			}
		}
		return false;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canMake())
			return 15;
		return 0;
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
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	public void setLiquid(int amt) {
		tank.setContents(amt, FluidRegistry.WATER);
	}

	@Override
	public int getOperationTime() {
		int base = DurationRegistry.FERMENTER.getOperationTime(omega);
		return Math.max(1, (int)(base/this.getFermentRate()));
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return (int)Math.max(1, this.getFermentRate()*DurationRegistry.FERMENTER.getNumberOperations(omega));
	}

	@Override
	public boolean areConditionsMet() {
		return this.canMake();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}

	@Override
	public boolean canBeCooledWithFins() {
		return true;
	}

	@Override
	public boolean allowExternalHeating() {
		return true;
	}

	public void setTemperature(int temp) {
		temperature = temp;
	}

	@Override
	public int getMaxTemperature() {
		return MAXTEMP;
	}
}
