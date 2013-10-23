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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipesExtractor;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityLiquidInventoryReceiver;
import Reika.RotaryCraft.Models.ModelExtractor;
import Reika.RotaryCraft.Registry.ExtractorBonus;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;

public class TileEntityExtractor extends TileEntityLiquidInventoryReceiver {

	private ItemStack inv[] = new ItemStack[9];

	public static final int oreCopy = 50; //50% chance of doubling -> 1.5^4 = 5.1
	public static final int oreCopyNether = 75; //75% chance of doubling -> 1.75^4 = 9.3
	public static final int oreCopyRare = 90; //90% chance of doubling -> 1.9^4 = 13.1

	/** The number of ticks that the current item has been cooking for */
	public int[] extractorCookTime = new int[4];

	public static final int CAPACITY = 16*RotaryConfig.MILLIBUCKET;

	public boolean idle = false;

	public void testIdle() {
		for (int i = 0; i < 4; i++)
			if (power < machine.getMinPower(i))
				return;
		boolean works = false;
		for (int i = 0; i < 4; i++) {
			if (this.canSmelt(i))
				works = true;
		}
		idle = !works;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 7 || i == 8;
	}

	private int getSmeltNumber(ModOreList ore) {
		//ReikaJavaLibrary.pConsole(RotaryConfig.getDifficulty());
		if (ore != null) {
			if (ore.isNetherOres()) {
				if (ReikaMathLibrary.doWithChance(oreCopyNether/100D))
					return 2;
				else
					return 1;
			}
			if (ore.isRare()) {
				if (ReikaMathLibrary.doWithChance(oreCopyRare/100D))
					return 2;
				else
					return 1;
			}
		}
		return ReikaMathLibrary.doWithChance(oreCopy/100D) ? 2 : 1;
	}

	public void throughPut() {
		for (int i = 1; i < 4; i++) {
			if (inv[i+3] != null) {
				if (inv[i] == null) {
					inv[i] = inv[i+3];
					inv[i+3] = null;
				}
				else if (inv[i].stackSize < inv[i].getMaxStackSize()) {
					if (inv[i].itemID == inv[i+3].itemID && inv[i].getItemDamage() == inv[i+3].getItemDamage()) {
						inv[i].stackSize++;
						ReikaInventoryHelper.decrStack(i+3, inv);
					}
				}
			}
		}
	}

	public void getLiq(World world, int x, int y, int z, int metadata) {
		int oldLevel = 0;
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (tank.getLevel() < CAPACITY) {
				int level = tank.getLevel();
				if (MachineRegistry.getMachine(world, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ) == MachineRegistry.PIPE) {
					TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
					if (tile != null && (tile.liquidID == 8 || tile.liquidID == 9) && tile.liquidLevel > 0) {
						oldLevel = tile.liquidLevel;
						tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
						int newlevel = ReikaMathLibrary.extrema(level+oldLevel/4+1, 0, "max");
						tank.setContents(newlevel, FluidRegistry.WATER);
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
		return inv.length;
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
		return inv[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

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

		extractorCookTime = NBT.getIntArray("CookTime");

		tank.readFromNBT(NBT);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setIntArray("CookTime", extractorCookTime);

		tank.writeToNBT(NBT);

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

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1, int i)
	{
		int j = i+1;
		int time = -1;
		switch (j) {
		case 1:
			time = 30*(30-(int)(2*ReikaMathLibrary.logbase(omega, 2)));
			break;
		case 2:
			time = (800-(int)(40*ReikaMathLibrary.logbase(omega, 2)))/2;
			break;
		case 3:
			time = 600-(int)(30*ReikaMathLibrary.logbase(omega, 2));
			break;
		case 4:
			time = 1200-(int)(80*ReikaMathLibrary.logbase(omega, 2));
			break;
		}
		if (time == -1)
			return 0;
		if (time <= 0)
			time = 1;
		return (extractorCookTime[i] * par1)/2 / time;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		//this.getPower(false);
		//this.getReceptor(world, x, y, z, meta);
		this.getPowerBelow();
		this.testIdle();
		this.throughPut();
		this.getLiq(world, x, y, z, meta);
		if (world.isRemote)
			return;
		for (int i = 0; i < 4; i++) {
			boolean flag1 = false;
			if (this.canSmelt(i)) {
				flag1 = true;
			}
			if (this.canSmelt(i)) {
				extractorCookTime[i]++;
				if (this.operationComplete(extractorCookTime[i], i+1)) {
					extractorCookTime[i] = 0;
					if (!this.processModOre(i))
						this.smeltItem(i);
					flag1 = true;
				}
			}
			else
				extractorCookTime[i] = 0;
			if (flag1)
				this.onInventoryChanged();
		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt(int i)
	{
		if (power < machine.getMinPower(i) || omega < machine.getMinSpeed(i) || torque < machine.getMinTorque(i))
			return false;
		if ((i == 1 || i == 2) && tank.isEmpty())
			return false;

		if (inv[i] == null)
			return false;
		if (inv[i+4] != null && inv[i+4].stackSize+1 >= inv[i+4].getMaxStackSize())
			return false;
		if (inv[8] != null) {
			if (inv[8].stackSize+1 > inv[8].getMaxStackSize())
				return false;
			if (inv[3] != null) {
				ItemStack bonus = ExtractorBonus.getBonusForIngredient(inv[3]).getBonus();
				if (bonus != null) {
					if (!ReikaItemHelper.matchStacks(bonus, inv[8]))
						return false;
				}
			}
		}
		ModOreList entry = ModOreList.getEntryFromDamage(inv[i].getItemDamage()/4);
		if (inv[i].itemID == RotaryCraft.modextracts.itemID || ModOreList.isModOre(inv[i])) {
			switch (i) {
			case 0:
				if (ModOreList.isModOre(inv[i])) {
					if (inv[i+4] == null)
						return true;
					ModOreList ore = ModOreList.getModOreFromOre(inv[i]);
					return ExtractorModOres.isDust(ore, inv[i+4].getItemDamage());
				}
				break;
			case 1:
				if (ExtractorModOres.isDust(entry, inv[i].getItemDamage())) {
					if (inv[i+4] == null)
						return true;
					return ExtractorModOres.isSlurry(entry, inv[i+4].getItemDamage());
				}
				break;
			case 2:
				if (ExtractorModOres.isSlurry(entry, inv[i].getItemDamage())) {
					if (inv[i+4] == null)
						return true;
					return ExtractorModOres.isSolution(entry, inv[i+4].getItemDamage());
				}
				break;
			case 3:
				if (ExtractorModOres.isSolution(entry, inv[i].getItemDamage())) {
					if (inv[i+4] == null)
						return true;
					return ExtractorModOres.isFlakes(entry, inv[i+4].getItemDamage());
				}
				break;
			}
		}

		ItemStack itemstack = RecipesExtractor.smelting().getSmeltingResult(inv[i]);
		if (itemstack == null) {
			return false;
		}
		if (inv[i+4] == null)
			return true;
		if (!inv[i+4].isItemEqual(itemstack))
			return false;
		if (inv[i+4].stackSize < this.getInventoryStackLimit() && inv[i+4].stackSize < inv[i+4].getMaxStackSize())
			return true;
		return inv[i+4].stackSize < itemstack.getMaxStackSize();
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem(int i)
	{
		if (!this.canSmelt(i))
			return;
		ItemStack itemstack = RecipesExtractor.smelting().getSmeltingResult(inv[i]);
		//ReikaJavaLibrary.pConsole("sSmelt :"+(inv[i+4] == null)+"   - "+ReikaItemHelper.matchStacks(inv[i+4], itemstack));
		int num = this.getSmeltNumber(null);
		if (inv[i+4] == null) {
			inv[i+4] = itemstack.copy();
			inv[i+4].stackSize *= num;
		}
		else if (ReikaItemHelper.matchStacks(inv[i+4], itemstack))
			inv[i+4].stackSize += num;

		if (i == 3)
			this.bonusItems(inv[i]);

		inv[i].stackSize--;
		if (i == 1 || i == 2)
			tank.removeLiquid(RotaryConfig.MILLIBUCKET/8); //millis

		if (inv[i].stackSize <= 0)
			inv[i] = null;/*
        if (i == 3) {
        	int xp = 0;
        	switch(inv[i+4].getItemDamage()) {

        	}
        	ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord, yCoord, zCoord, xp);
        }*/
	}

	private void bonusItems(ItemStack is) {
		ExtractorBonus e = ExtractorBonus.getBonusForIngredient(is);
		if (e != null)
			e.addBonusToItemStack(inv, 8);
	}

	private boolean isValidModOre(ItemStack is) {
		return ExtractorModOres.isModOreIngredient(is) || ModOreList.isModOre(is);
	}

	private boolean processModOre(int i) {
		if (this.isValidModOre(inv[i])) {
			ModOreList m = ModOreList.getEntryFromDamage(inv[i].getItemDamage()/4);
			if (ModOreList.isModOre(inv[i]) && i == 0) {
				m = ModOreList.getModOreFromOre(inv[0]);
				ItemStack is = ExtractorModOres.getDustProduct(m);
				if (ReikaInventoryHelper.addOrSetStack(is.itemID, this.getSmeltNumber(m), is.getItemDamage(), inv, i+4)) {
					ReikaInventoryHelper.decrStack(i, inv);
				}
				return true;
			}
			else if (ExtractorModOres.isModOreIngredient(inv[i])) {
				if (ExtractorModOres.isDust(m, inv[i].getItemDamage()) && i == 1) {
					ItemStack is = ExtractorModOres.getSlurryProduct(m);
					if (ReikaInventoryHelper.addOrSetStack(is.itemID, this.getSmeltNumber(m), is.getItemDamage(), inv, i+4)) {
						ReikaInventoryHelper.decrStack(i, inv);
						tank.removeLiquid(RotaryConfig.MILLIBUCKET/8);
					}
					return true;
				}
				if (ExtractorModOres.isSlurry(m, inv[i].getItemDamage()) && i == 2) {
					ItemStack is = ExtractorModOres.getSolutionProduct(m);
					if (ReikaInventoryHelper.addOrSetStack(is.itemID, this.getSmeltNumber(m), is.getItemDamage(), inv, i+4)) {
						ReikaInventoryHelper.decrStack(i, inv);
						tank.removeLiquid(RotaryConfig.MILLIBUCKET/8);
					}
					return true;
				}
				if (ExtractorModOres.isSolution(m, inv[i].getItemDamage()) && i == 3) {
					ItemStack is = ExtractorModOres.getFlakeProduct(m);
					if (ReikaInventoryHelper.addOrSetStack(is.itemID, this.getSmeltNumber(m), is.getItemDamage(), inv, i+4)) {
						ReikaInventoryHelper.decrStack(i, inv);
						this.bonusItems(inv[i]);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelExtractor();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.EXTRACTOR.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 0)
			return ReikaBlockHelper.isOre(is);
		if (is.itemID == RotaryCraft.extracts.itemID) {
			return slot == 1+is.getItemDamage()/8;
		}
		if (is.itemID == RotaryCraft.modextracts.itemID) {
			return slot == 1+is.getItemDamage()%4;
		}
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canSmelt(0) && !this.canSmelt(1) && !this.canSmelt(2) && !this.canSmelt(3))
			return 15;
		return 0;
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
	public boolean canReceiveFrom(ForgeDirection dir) {
		return dir.offsetY == 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}
}
