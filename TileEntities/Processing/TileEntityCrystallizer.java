/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCrystallizer;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityCrystallizer extends InventoriedPowerLiquidReceiver implements TemperatureTE, MultiOperational, ConditionalOperation {

	private StepTimer timer = new StepTimer(400);
	private StepTimer tempTimer = new StepTimer(20);
	private StepTimer sound = new StepTimer(45);

	private int temperature;

	public int freezeTick;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);

		//ReikaJavaLibrary.pConsole((omega-MINSPEED)+":"+dur);
		timer.setCap(this.getOperationTime());

		tempTimer.update();
		if (tempTimer.checkCap())
			this.updateTemperature(world, x, y, z, meta);

		if (!worldObj.isRemote) {
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				this.doOperation(n > 1);

			freezeTick = timer.getTick();
		}

		sound.update();
		if (omega > 0) {
			if (sound.checkCap())
				SoundRegistry.FAN.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.1F : 0.4F, 0.6F);
		}
	}

	private void doOperation(boolean multiple) {
		if (!tank.isEmpty()) {
			ItemStack toMake = RecipesCrystallizer.getRecipes().getFreezingResult(tank.getFluid());
			//ReikaJavaLibrary.pConsole(timer.getTick()+"/"+timer.getCap()+":"+toMake);
			if (this.canOperate(toMake)) {
				timer.update();
				if (multiple || timer.checkCap()) {
					this.make(toMake);
				}
			}
			else
				timer.reset();
		}
		else
			timer.reset();
	}

	private void make(ItemStack toMake) {
		ReikaInventoryHelper.addOrSetStack(toMake, inv, 0);
		int amt = RecipesCrystallizer.getRecipes().getRecipeConsumption(toMake);
		tank.removeLiquid(amt);
	}

	private boolean canOperate(ItemStack toMake) {
		if (toMake == null)
			return false;
		if (power < MINPOWER || omega < MINSPEED)
			return false;
		if (temperature > this.getFreezingPoint())
			return false;
		if (inv[0] == null)
			return true;
		return ReikaItemHelper.areStacksCombinable(toMake, inv[0], this.getInventoryStackLimit());
	}

	public int getFreezingPoint() {
		return !tank.isEmpty() ? this.getFreezingPoint(tank.getFluid()) : 0;
	}

	public static int getFreezingPoint(FluidStack fs) {
		return -273+(int)(0.9*fs.getFluid().getTemperature(fs));
	}

	public int getProgressScaled(int s) {
		return s * freezeTick / timer.getCap();
	}

	public int getLiquidScaled(int s) {
		return s * tank.getLevel() / tank.getCapacity();
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot == 1 && ReikaItemHelper.matchStacks(is, ItemStacks.dryice);
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return RecipesCrystallizer.getRecipes().isValidFluid(f);
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 8000;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || omega < MINSPEED)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CRYSTALLIZER;
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
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Blocks.snow) != null)
			Tamb -= 5;
		if (RotaryAux.isNextToWater(world, x, y, z))
			Tamb -= 15;
		if (RotaryAux.isNextToIce(world, x, y, z))
			Tamb -= 30;

		ItemStack cryo = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "dustCryotheum", 1);
		if (ReikaItemHelper.matchStacks(ItemStacks.dryice, inv[1]) || (cryo != null && ReikaItemHelper.matchStacks(cryo, inv[1]))) {
			Tamb -= 40;
			if (temperature > Tamb+4 || rand.nextInt(20) == 0)
				ReikaInventoryHelper.decrStack(1, inv);
		}

		int dT = Tamb-temperature;

		temperature += Math.abs(dT) < 4 ? Math.signum(dT) : dT/4;
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
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

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
		return DurationRegistry.CRYSTALLIZER.getOperationTime(Math.max(0, omega-MINSPEED));
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.CRYSTALLIZER.getNumberOperations(Math.max(0, omega-MINSPEED));
	}

	@Override
	public boolean areConditionsMet() {
		return !tank.isEmpty();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Liquid";
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
		return 1000;
	}

}
