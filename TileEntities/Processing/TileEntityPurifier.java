/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityPurifier extends InventoriedPowerReceiver implements TemperatureTE, DiscreteFunction, ConditionalOperation {

	public int cookTime = 0;

	public int temperature;

	public static final int SMELTTEMP = 600;
	public static final int MAXTEMP = 1000;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 6;
	}

	@Override
	public int getSizeInventory() {
		return 8;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		cookTime = tickcount;
		tickcount++;
		this.updateTemperature(world, x, y, z, meta);
		this.getSummativeSidedPower();
		if (power < MINPOWER) {
			tickcount = 0;
			return;
		}
		if (!this.canSmelt()) {
			tickcount = 0;
			return;
		}
		if (tickcount < this.getOperationTime())
			return;
		this.smelt();
	}

	private void smelt() {
		tickcount = 0;
		int count = 0;
		for (int i = 1; i < 6; i++) {
			if (this.isModSteel(inv[i])) {
				ReikaInventoryHelper.decrStack(i, inv);
				count++;
			}
		}
		if (count <= 0)
			return;
		ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, count, ItemStacks.steelingot.getItemDamage(), inv, 6);
		if (rand.nextInt(25) == 0)
			ReikaInventoryHelper.decrStack(0, inv);
		if (rand.nextInt(5) == 0)
			ReikaInventoryHelper.decrStack(7, inv);
	}

	public int getCookScaled(int par1) {
		return (par1*cookTime)/this.getOperationTime();
	}

	private boolean canSmelt() {
		if (temperature < SMELTTEMP)
			return false;
		if (inv[0] == null)
			return false;
		if (inv[0].itemID != Item.gunpowder.itemID)
			return false;
		if (inv[7] == null)
			return false;
		if (inv[7].itemID != Block.sand.blockID)
			return false;
		for (int i = 1; i < 6; i++) {
			if (this.isModSteel(inv[i]))
				return true;
		}
		return false;
	}

	private boolean isModSteel(ItemStack is) {
		if (is == null)
			return false;
		List steel = ItemStacks.getModSteels();
		for (int i = 0; i < steel.size(); i++) {
			ItemStack s = (ItemStack)steel.get(i);
			if (is.itemID == s.itemID && (is.getItemDamage() == s.getItemDamage() || !s.getHasSubtypes()))
				return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 0)
			return is.itemID == Item.gunpowder.itemID;
		if (slot == 7)
			return is.itemID == Block.sand.blockID;
		if (slot == 6)
			return false;
		return this.isModSteel(is);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PURIFIER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		temperature = NBT.getInteger("temperature");
		cookTime = NBT.getInteger("time");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("temperature", temperature);
		NBT.setInteger("time", cookTime);
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);

		ForgeDirection waterside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water);
		if (waterside != null) {
			Tamb /= 2;
		}
		ForgeDirection iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
		if (iceside != null) {
			if (Tamb > 0)
				Tamb /= 4;
			ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Block.waterMoving.blockID, 0);
		}
		ForgeDirection fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.fire.blockID);
		if (fireside != null) {
			Tamb += 200;
		}
		ForgeDirection lavaside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava);
		if (lavaside != null) {
			Tamb += 600;
		}
		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
	}

	public void overheat(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 7, false, 1F, false, true, 2F);
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public int getRedstoneOverride() {
		if (inv[0] == null)
			return 15;
		if (inv[0].itemID != Item.gunpowder.itemID)
			return 0;
		boolean hasModSteel = false;
		for (int i = 1; i < 6; i++) {
			if (this.isModSteel(inv[i]))
				hasModSteel = true;
		}
		if (!hasModSteel)
			return 15;
		if (inv[6] == null)
			return 0;
		if (ReikaItemHelper.matchStacks(inv[6], ItemStacks.steelingot))
			return 15;
		if (inv[6].stackSize >= ItemStacks.steelingot.getMaxStackSize())
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
	public void onEMP() {}

	@Override
	public int getOperationTime() {
		return DurationRegistry.PURIFIER.getOperationTime(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return this.canSmelt();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Invalid or Missing Items";
	}

}
