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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCrystallizer;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityCrystallizer extends InventoriedPowerLiquidReceiver implements TemperatureTE {

	private ItemStack[] inv = new ItemStack[1];
	private StepTimer timer = new StepTimer(400);
	private StepTimer tempTimer = new StepTimer(20);
	private StepTimer sound = new StepTimer(45);

	private int temperature;

	public int smeltTick;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false, false);

		int dur = Math.max(1, 400 - (int)(24*ReikaMathLibrary.logbase(omega-MINSPEED+1, 2)));
		//ReikaJavaLibrary.pConsole((omega-MINSPEED)+":"+dur);
		timer.setCap(dur);

		tempTimer.update();
		if (tempTimer.checkCap())
			this.updateTemperature(world, x, y, z, meta);

		if (!worldObj.isRemote) {
			if (!tank.isEmpty()) {
				ItemStack toMake = RecipesCrystallizer.getRecipes().getSmeltingResult(tank.getFluid());
				//ReikaJavaLibrary.pConsole(timer.getTick()+"/"+timer.getCap()+":"+toMake);
				if (this.canOperate(toMake)) {
					timer.update();
					if (timer.checkCap()) {
						this.make(toMake);
					}
				}
				else
					timer.reset();
			}
			else
				timer.reset();
		}

		smeltTick = timer.getTick();

		sound.update();
		if (omega > 0) {
			if (sound.checkCap())
				SoundRegistry.FAN.playSoundAtBlock(world, x, y, z, 0.4F, 0.6F);
		}
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
		return ReikaItemHelper.canCombineStacks(toMake, inv[0]);
	}

	public int getFreezingPoint() {
		return !tank.isEmpty() ? -273+(int)(0.9*tank.getActualFluid().getTemperature(tank.getFluid())) : 0;
	}

	public int getProgressScaled(int s) {
		return s * smeltTick / timer.getCap();
	}

	public int getLiquidScaled(int s) {
		return s * tank.getLevel() / tank.getCapacity();
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return false;
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return true;
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
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || omega < MINSPEED)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.CRYSTALLIZER.ordinal();
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
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		if (ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.blockSnow.blockID) != null)
			Tamb -= 5;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water) != null)
			Tamb -= 15;
		if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.ice) != null)
			Tamb -= 30;
		int dT = Tamb-temperature;
		temperature += dT/4;
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
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		temperature = NBT.getInteger("temp");

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");
			if (byte0 >= 0 && byte0 < inv.length)
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setInteger("temp", temperature);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		NBT.setTag("Items", nbttaglist);
	}

}
