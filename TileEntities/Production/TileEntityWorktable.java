/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.Instantiable.Recipe.CraftingInputMatrix;
import Reika.DragonAPI.Interfaces.CraftingContainer;
import Reika.DragonAPI.Interfaces.TileEntity.CraftingTile;
import Reika.DragonAPI.Interfaces.TileEntity.TriggerableAction;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.Event.WorktableCraftEvent;
import Reika.RotaryCraft.API.Interfaces.ChargeableTool;
import Reika.RotaryCraft.Auxiliary.Interfaces.AlternatingRedstoneUser;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerWorktable;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern.RecipeMode;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Items.Tools.ItemJetPack.PackUpgrades;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor.HelmetUpgrades;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityWorktable extends InventoriedRCTileEntity implements CraftingTile<WorktableRecipe>, TriggerableAction, AlternatingRedstoneUser {

	private final CraftingInputMatrix matrix = new CraftingInputMatrix(this);

	private boolean hasUpgrade;
	private WorktableRecipe toCraft;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			matrix.update();
			if (matrix.isEmpty())
				return;
			if (hasUpgrade && this.getTicksExisted()%6 == 0 && !this.hasRedstoneSignal()) {
				this.onPositiveRedstoneEdge();
				if (this.getTicksExisted()%6 == 0) {
					ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.click", 0.5F, 0.675F);
				}
			}
			if (this.isReadyToCraft()) {
				if (this.getTicksExisted()%4 == 0) {
					this.chargeTools();
					this.makeJetplate();
					this.makeJetPropel();
					this.coolJetpacks();
					this.wingJetpacks();
					this.makeBedjump();
					this.makeHelmetUpgrades();
				}
			}
		}
	}

	@Override
	protected void onPositiveRedstoneEdge() {
		if (!worldObj.isRemote) {
			if (!this.craft()) {
				if (this.canUncraft())
					this.uncraft();
			}
			this.uncraftJetplate();
		}
	}

	private void makeHelmetUpgrades() {
		int armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDHELM.getItemInstance(), inv);
		if (armorslot == -1)
			armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDREVEAL.getItemInstance(), inv);
		if (armorslot != -1) {
			for (int i = 0; i < HelmetUpgrades.list.length; i++) {
				HelmetUpgrades g = HelmetUpgrades.list[i];
				if (g.isAvailable && !g.existsOn(inv[armorslot])) {
					ItemStack[] rec = g.getUpgradeItems();
					boolean flag = false;
					int itemslot = -1;
					if (rec.length == 1) { //shapeless
						itemslot = ReikaInventoryHelper.locateInInventory(rec[0], inv, false);
						flag = itemslot != -1;
					}
					else if (armorslot == 4) {
						boolean flag2 = true;
						for (int k = 0; k < rec.length; k++) {
							ItemStack is = rec[k];
							ItemStack in = inv[k >= 4 ? k+1 : k];
							if (!ReikaItemHelper.matchStacks(in, is)) {
								flag2 = false;
								break;
							}
						}
						flag = flag2;
					}
					if (flag && ReikaInventoryHelper.isEmptyFrom(this, 9, 17)) {
						ItemStack is = inv[armorslot].copy();
						if (itemslot != -1) {
							inv[itemslot] = null;
							inv[armorslot] = null;
						}
						else {
							for (int k = 0; k < 9; k++) {
								ReikaInventoryHelper.decrStack(k, inv);
							}
							inv[armorslot] = null;
						}
						g.enable(is, true);
						inv[9] = is;
					}
				}
			}
		}
	}

	private void coolJetpacks() {
		ItemStack is = inv[4];
		if (is != null) {
			Item item = is.getItem();
			if (item instanceof ItemJetPack) {
				ItemJetPack pack = (ItemJetPack)item;
				if (!PackUpgrades.COOLING.existsOn(is)) {
					boolean items = ReikaItemHelper.matchStacks(inv[3], MachineRegistry.COOLINGFIN.getCraftedProduct());
					items &= ReikaItemHelper.matchStacks(inv[5], MachineRegistry.COOLINGFIN.getCraftedProduct());
					if (items) {
						ReikaInventoryHelper.decrStack(3, inv);
						ReikaInventoryHelper.decrStack(5, inv);
						PackUpgrades.COOLING.enable(is, true);
						inv[13] = is.copy();
						inv[4] = null;
					}
				}
			}
		}
	}

	private void makeJetPropel() {
		ItemStack is = inv[4];
		if (is != null) {
			Item item = is.getItem();
			if (item instanceof ItemJetPack) {
				ItemJetPack pack = (ItemJetPack)item;
				if (!PackUpgrades.JET.existsOn(is)) {
					if (ReikaItemHelper.matchStacks(inv[7], EngineType.JET.getCraftedProduct())) {
						ReikaInventoryHelper.decrStack(7, inv);
						PackUpgrades.JET.enable(is, true);
						inv[13] = is.copy();
						inv[4] = null;
					}
				}
			}
		}
	}

	private void wingJetpacks() {
		ItemStack is = inv[4];
		if (is != null) {
			Item item = is.getItem();
			if (item instanceof ItemJetPack) {
				ItemJetPack pack = (ItemJetPack)item;
				if (!PackUpgrades.WING.existsOn(is)) {
					ItemStack ingot = pack.getMaterial();
					for (int i = 0; i < 3; i++) {
						if (!ReikaItemHelper.matchStacks(inv[i], ingot))
							return;
					}
					for (int i = 0; i < 3; i++) {
						ReikaInventoryHelper.decrStack(i, inv);
					}
					PackUpgrades.WING.enable(is, true);
					inv[13] = is.copy();
					inv[4] = null;
				}
			}
		}
	}

	public CraftingContainer constructContainer() {
		return new ContainerWorktable(this.getPlacer(), this, worldObj, false);
	}

	@Override
	public int getOutputSlot() {
		return 13;
	}

	private boolean craft() {
		WorktableRecipe wr = WorktableRecipes.getInstance().findMatchingRecipe(matrix, worldObj);
		if (wr != null) {
			return this.handleCrafting(wr, this.getPlacer(), false);
		}
		return false;
	}

	public boolean isReadyToCraft() {
		for (int i = 9; i < 18; i++) {
			if (inv[i] != null)
				return false;
		}
		return true;
	}

	public boolean handleCrafting(WorktableRecipe wr, EntityPlayer ep, boolean craftAll) {
		int maxCrafts = craftAll ? this.getSmallestInputStack() : 1;
		int crafts = 0;
		if (wr.isRecycling()) {
			ArrayList<ItemStack> li = wr.getRecycling().getSplitOutput();
			int i = 9;
			for (ItemStack is : li) {
				ReikaInventoryHelper.addOrSetStack(is, inv, i);
				i++;
			}
			RotaryAchievements.RECYCLE.triggerAchievement(ep);
			crafts = 1;
		}
		else {
			for (int i = 0; i < maxCrafts; i++) {
				ItemStack is = wr.getOutput();
				if (inv[13] != null && inv[13].stackSize+is.stackSize > Math.min(this.getInventoryStackLimit(), is.getMaxStackSize()))
					break;
				is.onCrafting(worldObj, ep, is.stackSize);
				ReikaInventoryHelper.addOrSetStack(is, inv, 13);
				crafts++;
			}
			ItemStack out = wr.getOutput().copy();
			out.stackSize *= crafts;
			MinecraftForge.EVENT_BUS.post(new WorktableCraftEvent(this, ep, true, out));
		}
		if (crafts > 0) {
			for (int i = 0; i < 9; ++i) {
				ItemStack item = this.getStackInSlot(i);
				if (item != null) {
					//noUpdate = true;
					ReikaInventoryHelper.decrStack(i, this, crafts);
				}
			}
			SoundRegistry.CRAFT.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 0.3F, 1.5F);
			return true;
		}
		else {
			return false;
		}
	}

	private int getSmallestInputStack() {
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < 9; ++i) {
			ItemStack item = this.getStackInSlot(i);
			if (item != null && item.stackSize < smallest) {
				smallest = item.stackSize;
			}
		}
		return smallest < Integer.MAX_VALUE ? smallest : -1;
	}

	private void makeBedjump() {
		int armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDBOOTS.getItemInstance(), inv);
		int jumpslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JUMP.getItemInstance(), inv);
		if (jumpslot != -1 && armorslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 17)) {
			NBTTagCompound tag = (NBTTagCompound)inv[armorslot].stackTagCompound.copy();
			inv[jumpslot] = null;
			inv[armorslot] = null;
			ItemStack is = ItemRegistry.BEDJUMP.getEnchantedStack();
			ReikaNBTHelper.combineNBT(is.stackTagCompound, tag);
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
						List<ItemStack>[] in = ReikaRecipeHelper.getRecipeArray(ir);
						if (in != null) {
							boolean flag = true;
							for (int k = 0; k < 9; k++) {
								if (in[k] != null && !in[k].isEmpty()) {
									if (inv[k+9] != null) {
										if (!ReikaItemHelper.collectionContainsItemStack(in[k], inv[k+9]))
											flag = false;
										if (inv[k+9].stackSize >= Math.min(this.getInventoryStackLimit(), inv[k+9].getMaxStackSize()))
											flag = false;
									}
								}
							}
							can = flag;
						}
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
		if (ir == ItemRegistry.MACHINE) {
			MachineRegistry r = MachineRegistry.machineList.get(is.getItemDamage());
			return !r.isUncraftable();
		}
		return false;
	}

	private void uncraft() {
		ItemStack is = inv[4];
		IRecipe ir = WorktableRecipes.getInstance().getInputRecipe(is);
		List<ItemStack>[] in = ReikaRecipeHelper.getRecipeArray(ir);
		if (in == null)
			return;

		for (int i = 0; i < ir.getRecipeOutput().stackSize; i++)
			ReikaInventoryHelper.decrStack(4, inv);

		for (int i = 0; i < 9; i++) {
			if (in[i] != null && !in[i].isEmpty()) {
				if (inv[i+9] == null) {
					inv[i+9] = in[i].get(0).copy();
					if (inv[i+9].getItemDamage() == OreDictionary.WILDCARD_VALUE)
						inv[i+9].setItemDamage(0);
				}
				else {
					++inv[i+9].stackSize;
				}
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
		int coilslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.SPRING.getItemInstance(), inv);
		if (coilslot == -1)
			coilslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.STRONGCOIL.getItemInstance(), inv);
		Item toolid = this.getTool();
		int toolslot = ReikaInventoryHelper.locateInInventory(toolid, inv);

		if (toolslot != -1 && coilslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 17)) {
			Item coilid = inv[coilslot].getItem();
			int coilmeta = inv[coilslot].getItemDamage();
			ItemStack tool = inv[toolslot];
			if (toolid instanceof ChargeableTool) {
				int newcoilcharge = ((ChargeableTool)toolid).setCharged(tool, coilmeta, coilid == ItemRegistry.STRONGCOIL.getItemInstance());
				ItemStack newcoil = new ItemStack(coilid, 1, newcoilcharge);
				inv[toolslot] = null;
				inv[coilslot] = null;
				inv[9] = tool.copy();
				inv[10] = newcoil;
			}
			else {
				int toolmeta = tool.getItemDamage();
				ItemStack newtool = new ItemStack(toolid, 1, coilmeta);
				NBTTagCompound tag = tool.stackTagCompound != null ? (NBTTagCompound)tool.stackTagCompound.copy() : null;
				newtool.stackTagCompound = tag;
				ItemStack newcoil = new ItemStack(coilid, 1, toolmeta);
				inv[toolslot] = null;
				inv[coilslot] = null;
				inv[9] = newtool;
				inv[10] = newcoil;
			}
		}
	}

	private Item getTool() {
		for (int i = 0; i < 9; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				if (is.getItem() instanceof ItemChargedTool || is.getItem() instanceof ItemChargedArmor || is.getItem() instanceof ChargeableTool)
					return inv[i].getItem();
			}
		}
		return null;
	}

	private void makeJetplate() {
		boolean bed = false;
		int plateslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDCHEST.getItemInstance(), inv);
		if (plateslot == -1)
			plateslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.STEELCHEST.getItemInstance(), inv);
		else
			bed = true;
		//ReikaJavaLibrary.pConsole(plateslot, Side.SERVER);
		int jetslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JETPACK.getItemInstance(), inv);
		if (jetslot != -1 && plateslot != -1 && plateslot < 9 && jetslot < 9 && ReikaInventoryHelper.hasNEmptyStacks(inv, 17)) {
			ItemStack jet = inv[jetslot];
			ItemStack plate = inv[plateslot];
			NBTTagCompound tag1 = plate.stackTagCompound != null ? (NBTTagCompound)plate.stackTagCompound.copy() : null;
			NBTTagCompound tag2 = jet.stackTagCompound != null ? (NBTTagCompound)jet.stackTagCompound.copy() : null;
			inv[jetslot] = null;
			inv[plateslot] = null;
			ItemStack is = bed ? ItemRegistry.BEDPACK.getEnchantedStack() : ItemRegistry.STEELPACK.getStackOf();
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			ReikaNBTHelper.combineNBT(is.stackTagCompound, tag1);
			ReikaNBTHelper.combineNBT(is.stackTagCompound, tag2);
			inv[9] = is;
			for (PackUpgrades u : PackUpgrades.values()) {
				if (u.existsOn(jet)) {
					u.enable(is, true);
				}
			}
		}
	}

	private void uncraftJetplate() {
		ItemStack combine = inv[4];
		boolean bed = ItemRegistry.BEDPACK.matchItem(combine);
		if (combine != null && (ItemRegistry.BEDPACK.matchItem(combine) || ItemRegistry.STEELPACK.matchItem(combine))) {
			ItemJetPack pack = (ItemJetPack)combine.getItem();
			//ReikaJavaLibrary.pConsole(plateslot, Side.SERVER);
			if (ReikaInventoryHelper.hasNEmptyStacks(inv, 18)) {
				ItemStack jet = ItemRegistry.JETPACK.getStackOf();
				((ItemJetPack)jet.getItem()).addFluid(jet, pack.getCurrentFluid(combine), pack.getFuel(combine));
				for (PackUpgrades p : pack.getUpgrades(combine))
					p.enable(jet, true);
				inv[4] = null;
				ItemStack plate = bed ? ItemRegistry.BEDCHEST.getEnchantedStack() : ItemRegistry.STEELCHEST.getStackOf();
				inv[9] = plate;
				inv[10] = jet;
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 19;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i >= 9)
			return false;
		//return hasProgram ? inv[i+18] != null && ReikaItemHelper.matchStacks(inv[i+18], itemstack) : true;
		return ItemRegistry.CRAFTPATTERN.matchItem(inv[18]) ? ItemCraftPattern.checkPatternForMatch(this, RecipeMode.WORKTABLE, i, i, itemstack, inv[18]) : true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i >= 9 && i != 18;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("redstoneUpgrade", hasUpgrade);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		hasUpgrade = NBT.getBoolean("redstoneUpgrade");
	}

	@Override
	public void onEMP() {

	}
	/*
	public ItemStack getProgrammedSlot(int i, int k) {
		ItemStack is = inv[18+i*3+k];
		return is != null ? is.copy() : null;
	}

	public void setMapping(int slot, ItemStack is) {
		inv[slot] = is != null ? is.copy() : null;
	}

	@Override
	public void markDirty() {
		super.markDirty();

		hasProgram = false;
		for (int i = 18; i < 27; i++) {
			if (inv[i] != null) {
				hasProgram = true;
			}
		}
	}
	 */
	@Override
	public boolean trigger() {
		return this.craft();
	}

	@Override
	public void upgrade(ItemStack is) {
		this.addRedstoneUpgrade();
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		return !this.hasRedstoneUpgrade() && ItemRegistry.UPGRADE.matchItem(item) && item.getItemDamage() == Upgrades.REDSTONE.ordinal();
	}

	@Override
	public void addRedstoneUpgrade() {
		hasUpgrade = true;
	}

	@Override
	public boolean hasRedstoneUpgrade() {
		return hasUpgrade;
	}

	@Override
	public void breakBlock() {
		if (this.hasRedstoneUpgrade()) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.REDSTONE.ordinal()));
		}
	}

	@Override
	public WorktableRecipe getToCraft() {
		return toCraft;
	}

	@Override
	public void setToCraft(WorktableRecipe wr) {
		toCraft = wr;
	}
}
