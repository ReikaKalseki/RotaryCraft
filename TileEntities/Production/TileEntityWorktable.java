/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.API.Event.WorktableCraftEvent;
import Reika.RotaryCraft.API.Interfaces.ChargeableTool;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.Items.Tools.ItemJetPack;
import Reika.RotaryCraft.Items.Tools.ItemJetPack.PackUpgrades;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor.HelmetUpgrades;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityWorktable extends InventoriedRCTileEntity {

	public boolean craftable = false;
	private ItemStack toCraft;
	private boolean lastPower;
	private boolean hasProgram;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			this.chargeTools();
			this.makeJetplate();
			this.makeJetPropel();
			this.coolJetpacks();
			this.wingJetpacks();
			this.makeBedjump();
			this.makeNightHelmet();

			if (!world.isRemote && ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower)) {
				if (!this.craft()) {
					if (this.canUncraft())
						this.uncraft();
				}
			}
			lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
		}
	}

	private void makeNightHelmet() {
		int armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDHELM.getItemInstance(), inv);
		int visslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.NVG.getItemInstance(), inv);
		if (visslot != -1 && armorslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 25)) {
			ItemStack is = inv[armorslot].copy();
			inv[visslot] = null;
			inv[armorslot] = null;
			HelmetUpgrades.NIGHTVISION.enable(is, true);
			inv[9] = is;
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
			MinecraftForge.EVENT_BUS.post(new WorktableCraftEvent(this, ep.getCommandSenderName(), true, is));
			return true;
		}
		return false;
	}

	private void makeBedjump() {
		int armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDBOOTS.getItemInstance(), inv);
		int jumpslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JUMP.getItemInstance(), inv);
		if (jumpslot != -1 && armorslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 25)) {
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
						List<ItemStack>[] in = ReikaRecipeHelper.getRecipeArray(ir);
						boolean flag = true;
						for (int k = 0; k < 9; k++) {
							if (in[k] != null && !in[k].isEmpty()) {
								if (inv[k+9] != null) {
									if (!ReikaItemHelper.listContainsItemStack(in[k], inv[k+9]))
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

		for (int i = 0; i < ir.getRecipeOutput().stackSize; i++)
			ReikaInventoryHelper.decrStack(4, inv);

		for (int i = 0; i < 9; i++) {
			if (in[i] != null && !in[i].isEmpty()) {
				if (inv[i+9] == null) {
					inv[i+9] = in[i].get(0).copy();
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
		if (toolslot != -1 && coilslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 25)) {
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
		if (jetslot != -1 && plateslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inv, 25)) {
			ItemStack jet = inv[jetslot];
			NBTTagCompound tag = jet.stackTagCompound != null ? (NBTTagCompound)jet.stackTagCompound.copy() : null;
			inv[jetslot] = null;
			inv[plateslot] = null;
			ItemStack is = bed ? ItemRegistry.BEDPACK.getEnchantedStack() : ItemRegistry.STEELPACK.getStackOf();
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			ReikaNBTHelper.combineNBT(is.stackTagCompound, tag);
			inv[9] = is;
		}
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i >= 9)
			return false;
		return hasProgram ? inv[i+18] != null && ReikaItemHelper.matchStacks(inv[i+18], itemstack) : true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i >= 9 && i < 18;
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
		NBT.setBoolean("prog", hasProgram);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		lastPower = NBT.getBoolean("lastpwr");
		hasProgram = NBT.getBoolean("prog");
	}

	@Override
	public void onEMP() {

	}

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
}
