/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFuelConverter extends TileEntityInventoriedPowerReceiver implements ITankContainer {

	public static final LiquidStack BC_FUEL = LiquidDictionary.getCanonicalLiquid("Fuel");
	public static final LiquidStack JET_FUEL = LiquidDictionary.getCanonicalLiquid("Jet Fuel");

	public static final int CAPACITY = 5*LiquidContainerRegistry.BUCKET_VOLUME;

	private LiquidTank bctank = new LiquidTank(CAPACITY);
	private LiquidTank jettank = new LiquidTank(CAPACITY);

	public static final ItemStack[] ingredients =
		{new ItemStack(Item.blazePowder.itemID, 1, 0), new ItemStack(RotaryCraft.powders.itemID, 1, 0),
		new ItemStack(RotaryCraft.powders.itemID, 1, 1), new ItemStack(Item.magmaCream.itemID, 1, 0)};

	private ItemStack[] inv = new ItemStack[9];

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FUELENHANCER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getPowerBelow();

		int factor = 1;

		//ReikaJavaLibrary.pConsoleSideOnly("BC: "+this.getBCFuel()+"    JET: "+this.getJetFuel(), Side.CLIENT);

		boolean convert = true;

		if (power < MINPOWER)
			convert = false;
		if (omega < MINSPEED)
			convert = false;

		if (convert && bctank.getLiquid() != null && bctank.getLiquid().amount >= 8*factor && this.hasItems()) {
			LiquidStack drain = bctank.drain(8*factor, true);
			jettank.fill(LiquidDictionary.getLiquid("Jet Fuel", factor), true);
			if (DifficultyEffects.CONSUMEFRAC.testChance() && DifficultyEffects.CONSUMEFRAC.testChance()) //chance^2
				this.consumeItems();
		}
	}

	private boolean hasItems() {
		for (int i = 0; i < ingredients.length; i++) {
			if (!ReikaInventoryHelper.checkForItemStack(ingredients[i], inv, false))
				return false;
		}
		return true;
	}

	private void consumeItems() {
		for (int i = 0; i < ingredients.length; i++) {
			ReikaInventoryHelper.decrStack(ReikaInventoryHelper.locateInInventory(ingredients[i], inv, false), inv);
		}
	}

	@Override
	public int getSizeInventory() {
		return 9;
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
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return ReikaInventoryHelper.checkForItemStack(is, ingredients, false);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		if (NBT.hasKey("jetinternalLiquid")) {
			jettank.setLiquid(new LiquidStack(NBT.getInteger("jetliquidId"), NBT.getInteger("jetinternalLiquid")));
		}
		else if (NBT.hasKey("jettank")) {
			jettank.setLiquid(LiquidStack.loadLiquidStackFromNBT(NBT.getCompoundTag("jettank")));
		}

		if (NBT.hasKey("bcinternalLiquid")) {
			bctank.setLiquid(new LiquidStack(NBT.getInteger("bcliquidId"), NBT.getInteger("bcinternalLiquid")));
		}
		else if (NBT.hasKey("bctank")) {
			bctank.setLiquid(LiquidStack.loadLiquidStackFromNBT(NBT.getCompoundTag("bctank")));
		}

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		if (bctank.getLiquid() != null) {
			NBT.setTag("bctank", bctank.getLiquid().writeToNBT(new NBTTagCompound()));
		}

		if (jettank.getLiquid() != null) {
			NBT.setTag("jettank", jettank.getLiquid().writeToNBT(new NBTTagCompound()));
		}

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return this.fill(0, resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		if (!resource.isLiquidEqual(BC_FUEL))
			return 0;
		return bctank.fill(resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.drain(0, maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return jettank.drain(maxDrain, doDrain);
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[]{bctank, jettank};
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if (type.isLiquidEqual(BC_FUEL))
			return bctank;
		else if (type.isLiquidEqual(LiquidDictionary.getCanonicalLiquid("Jet Fuel")))
			return jettank;
		return null;
	}

	public int getFuel(LiquidStack liquid) {
		if (liquid.isLiquidEqual(BC_FUEL))
			return this.getBCFuel();
		else if (liquid.isLiquidEqual(JET_FUEL))
			return this.getJetFuel();
		return 0;
	}

	public double getLiquidModelOffset(LiquidStack liquid) {
		if (liquid.isLiquidEqual(BC_FUEL))
			return 10/16D;
		else if (liquid.isLiquidEqual(JET_FUEL))
			return 1/16D;
		return 0;
	}

	public int getBCFuel() {
		if (bctank.getLiquid() == null)
			return 0;
		return bctank.getLiquid().amount;
	}

	public int getJetFuel() {
		if (jettank.getLiquid() == null)
			return 0;
		return jettank.getLiquid().amount;
	}

}
