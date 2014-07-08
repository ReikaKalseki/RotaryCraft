/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.API.ChargeableTool;
import Reika.RotaryCraft.API.Event.WorktableCraftEvent;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityWorktable extends InventoriedRCTileEntity {

	public boolean craftable = false;
	private ItemStack toCraft;
	private boolean lastPower;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			this.chargeTools();
			this.makeJetplate();
			this.makeBedjump();

			if (!world.isRemote && ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower)) {
				if (!this.craft()) {
					if (this.canUncraft())
						this.uncraft();
				}
			}
			lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
		}
	}

	private boolean craft() {
		EntityPlayer ep = this.getPlacer();
		ContainerWorktable cw = new ContainerWorktable(ep, this, worldObj);
		InventoryCrafting cm = new InventoryCrafting(cw, 3, 3);
		for (int i = 0; i < 9; i++)
			cm.setInventorySlotContents(i, this.getStackInSlot(i));
		ItemStack is = WorktableRecipes.getInstance().findMatchingRecipe(cm, worldObj);
		if (is != null) {
			for (int i = 0; i < 9; ++i) {
				ItemStack item = this.getStackInSlot(i);
				if (item != null) {
					//noUpdate = true;
					if (item.stackSize == 1)
						this.setInventorySlotContents(i, null);
					else
						this.getStackInSlot(i).stackSize--;
				}
			}
			is.onCrafting(worldObj, ep, is.stackSize);
			ReikaInventoryHelper.addOrSetStack(is, inv, 13);
			SoundRegistry.CRAFT.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 0.3F, 1.5F);
			MinecraftForge.EVENT_BUS.post(new WorktableCraftEvent(this, ep.getEntityName(), true, is));
			return true;
		}
		return false;
	}

	private void makeBedjump() {
		int armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDBOOTS.getShiftedID(), inv);
		int jumpslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JUMP.getShiftedID(), inv);
		if (jumpslot != -1 && armorslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 16)) {
			inv[jumpslot] = null;
			inv[armorslot] = null;
			ItemStack is = ItemRegistry.BEDJUMP.getEnchantedStack();
			inv[9] = is;
		}
	}

	public boolean canUncraft() {
		boolean can = false;
		for (int i = 0; i < 9; i++) {
			ItemStack is = inv[i];
			if (i == 4) {
				if (is == null || this.isNotUncraftable(is))
					return false;
				else {
					IRecipe ir = WorktableRecipes.getInstance().getInputRecipe(is);
					if (ir == null)
						return false;
					else {
						ItemStack[] in = new ItemStack[9];
						ReikaRecipeHelper.copyRecipeToItemStackArray(in, ir);
						boolean flag = true;
						for (int k = 0; k < 9; k++) {
							if (inv[k+9] != null) {
								if (!ReikaItemHelper.matchStacks(inv[k+9], in[k]))
									flag = false;
								if (inv[k+9].stackSize >= Math.min(this.getInventoryStackLimit(), inv[k+9].getMaxStackSize()))
									flag = false;
							}
						}
						can = flag;
					}
				}
			}
			else {
				if (is != null)
					return false;
			}
		}
		return can;
	}

	private boolean isNotUncraftable(ItemStack is) {
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir != null && (ir.isTool() || ir.isArmor())) {
			return is.getItemDamage() > 0;
		}
		if (is.stackTagCompound == null)
			return false;
		if (is.stackTagCompound.getInteger("dmg") > 0)
			return true;
		if (is.stackTagCompound.getInteger("damage") > 0)
			return true;
		if (is.stackTagCompound.getInteger("lube") > 0)
			return true;
		if (is.stackTagCompound.getInteger("lvl") > 0)
			return true;
		if (is.stackTagCompound.hasKey("ench"))
			return true;
		return false;
	}

	private void uncraft() {
		ItemStack is = inv[4];
		IRecipe ir = WorktableRecipes.getInstance().getInputRecipe(is);
		ItemStack[] in = new ItemStack[9];
		ReikaRecipeHelper.copyRecipeToItemStackArray(in, ir);

		for (int i = 0; i < ir.getRecipeOutput().stackSize; i++)
			ReikaInventoryHelper.decrStack(4, inv);

		for (int i = 0; i < 9; i++) {
			if (inv[i+9] == null) {
				inv[i+9] = in[i];
			}
			else {
				inv[i+9] = ReikaItemHelper.getSizedItemStack(inv[i+9], inv[i+9].stackSize+1);
			}
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.WORKTABLE;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private void chargeTools() {
		int coilslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.SPRING.getShiftedID(), inv);
		if (coilslot == -1)
			coilslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.STRONGCOIL.getShiftedID(), inv);
		int toolid = this.getTool();
		int toolslot = ReikaInventoryHelper.locateInInventory(toolid, inv);
		if (toolslot != -1 && coilslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 16)) {
			int coilid = inv[coilslot].itemID;
			int toolmeta = inv[toolslot].getItemDamage();
			int coilmeta = inv[coilslot].getItemDamage();
			ItemStack newtool = new ItemStack(toolid, 1, coilmeta);
			ItemStack newcoil = new ItemStack(coilid, 1, toolmeta);
			inv[toolslot] = null;
			inv[coilslot] = null;
			inv[9] = newtool;
			inv[10] = newcoil;
		}
	}

	private int getTool() {
		for (int i = 0; i < 9; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				if (is.getItem() instanceof ItemChargedTool || is.getItem() instanceof ItemChargedArmor || is.getItem() instanceof ChargeableTool)
					return inv[i].itemID;
			}
		}
		return -1;
	}

	private void makeJetplate() {
		int plateslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDCHEST.getShiftedID(), inv);
		int jetslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JETPACK.getShiftedID(), inv);
		if (jetslot != -1 && plateslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 16)) {
			ItemStack jet = inv[jetslot];
			int original = jet.stackTagCompound != null ? jet.stackTagCompound.getInteger("fuel") : 0;
			inv[jetslot] = null;
			inv[plateslot] = null;
			ItemStack is = ItemRegistry.BEDPACK.getEnchantedStack();
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setInteger("charge", original);
			inv[9] = is;
		}
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i >= 9)
			return false;
		return i < 9;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i >= 9;
	}

	public ItemStack getToCraft() {
		if (toCraft == null)
			return null;
		return toCraft.copy();
	}

	public void setToCraft(ItemStack is) {
		if (is != null)
			toCraft = is.copy();
		else
			toCraft = null;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("lastpwr", lastPower);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		lastPower = NBT.getBoolean("lastpwr");
	}

	@Override
	public void onEMP() {}
}
