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

import ic2.api.recipe.Recipes;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;


public class TileEntityDropProcessor extends InventoriedPowerReceiver implements ConditionalOperation, MultiOperational, EnchantableMachine {

	private final ArrayList<ItemStack> overflow = new ArrayList();

	private HashMap<Enchantment, Integer> enchantments = new HashMap();

	public int dropProcessTime;

	@Override
	public boolean canExtractItem(int slot, ItemStack is, int side) {
		return slot > 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		if (world.isRemote)
			return;

		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		boolean flag1 = false;
		if (power >= MINPOWER && torque >= MINTORQUE) {
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				flag1 |= this.doOperation(world, x, y, z, n > 1);
		}
		else {
			dropProcessTime = 0;
		}

		if (flag1)
			this.markDirty();
	}

	private boolean doOperation(World world, int x, int y, int z, boolean multiple) {
		if (inv[1] == null) {
			if (overflow.isEmpty()) {
				if (inv[0] != null && this.isValid(inv[0])) {
					dropProcessTime++;
					if (multiple || dropProcessTime >= this.getOperationTime()) {
						dropProcessTime = 0;
						this.processItem(world, x, y, z);
					}
					return true;
				}
				else {
					dropProcessTime = 0;
					return false;
				}
			}
			else {
				dropProcessTime = 0;
				inv[1] = overflow.remove(0);
				return false;
			}
		}
		else {
			dropProcessTime = 0;
			return false;
		}
	}

	private boolean isValid(ItemStack is) {
		if (ReikaItemHelper.isBlock(is))
			return true;
		if (ModList.IC2.isLoaded() && IC2Handler.IC2Stacks.SCRAPBOX.match(is))
			return true;
		return false;
	}

	private void processItem(World world, int x, int y, int z) {
		if (ReikaItemHelper.isBlock(inv[0])) {
			Block b = Block.getBlockFromItem(inv[0].getItem());
			ArrayList<ItemStack> li = b.getDrops(world, x, y, z, inv[0].getItemDamage(), this.getEnchantment(Enchantment.fortune));
			li = ReikaItemHelper.collateItemList(li);
			if (!li.isEmpty()) {
				inv[1] = li.remove(0);
				overflow.addAll(li);
			}
		}
		else if (ModList.IC2.isLoaded() && IC2Handler.IC2Stacks.SCRAPBOX.match(inv[0])) {
			inv[1] = Recipes.scrapboxDrops.getDrop(inv[0], false);
		}
		ReikaInventoryHelper.decrStack(0, inv);
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isValid(is) && slot == 0;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.DROPS;
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
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = this.getEnchantment(Enchantment.enchantmentsList[i]);
				if (lvl > 0)
					NBT.setInteger(Enchantment.enchantmentsList[i].getName(), lvl);
			}
		}

		NBTTagList li = new NBTTagList();
		NBT.setTag("extra", li);
		for (ItemStack is : overflow) {
			NBTTagCompound tag = new NBTTagCompound();
			is.writeToNBT(tag);
			li.appendTag(tag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments = new HashMap<Enchantment,Integer>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				int lvl = NBT.getInteger(Enchantment.enchantmentsList[i].getName());
				enchantments.put(Enchantment.enchantmentsList[i], lvl);
			}
		}

		NBTTagList li = NBT.getTagList("extra", NBTTypes.COMPOUND.ID);
		overflow.clear();
		for (Object o : li.tagList) {
			NBTTagCompound tag = (NBTTagCompound)o;
			ItemStack is = ItemStack.loadItemStackFromNBT(tag);
			if (is != null)
				overflow.add(is);
		}
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.DROPS.getOperationTime(omega);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.DROPS.getNumberOperations(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && ReikaItemHelper.isBlock(inv[0]) && inv[1] == null && overflow.isEmpty();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Missing Items/Full Output";
	}

	@Override
	public boolean applyEnchants(ItemStack is) {
		boolean accepted = false;
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.fortune, is)) {
			enchantments.put(Enchantment.fortune, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.infinity, is)) {
			enchantments.put(Enchantment.infinity, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.infinity, is));
			accepted = true;
		}
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.efficiency, is))	 {
			enchantments.put(Enchantment.efficiency, ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency, is));
			accepted = true;
		}
		return accepted;
	}

	public ArrayList<Enchantment> getValidEnchantments() {
		ArrayList<Enchantment> li = new ArrayList<Enchantment>();
		li.add(Enchantment.fortune);
		return li;
	}

	@Override
	public HashMap<Enchantment, Integer> getEnchantments() {
		return enchantments;
	}

	@Override
	public boolean hasEnchantment(Enchantment e) {
		return this.getEnchantments().containsKey(e);
	}

	@Override
	public boolean hasEnchantments() {
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if (Enchantment.enchantmentsList[i] != null) {
				if (this.getEnchantment(Enchantment.enchantmentsList[i]) > 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public int getEnchantment(Enchantment e) {
		if (!this.hasEnchantment(e))
			return 0;
		else
			return this.getEnchantments().get(e);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		dropProcessTime = NBT.getShort("CookTime");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)dropProcessTime);
	}

	public int getProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.tickcount);
		return (dropProcessTime * par1)/2 / this.getOperationTime();
	}

}
