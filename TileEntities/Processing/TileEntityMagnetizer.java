/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RedstoneCycleTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.MagnetizationCore;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesMagnetizer;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesMagnetizer.MagnetizerRecipe;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMagnetizer extends InventoriedPowerReceiver implements OneSlotMachine, DiscreteFunction, ConditionalOperation, MagnetizationCore {

	public static final int MIN_DURATION = 2;

	private final RedstoneCycleTracker redstone = new RedstoneCycleTracker(3);
	private boolean hasLodestone = false;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.MAGNETIZER;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		if (power < MINPOWER) {
			tickcount = 0;
			return;
		}
		if (omega < MINSPEED) {
			tickcount = 0;
			return;
		}
		redstone.update(world, x, y, z);
		if (!redstone.isAlternating())
			return;
		tickcount++;
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;
		if (inv[0] != null) {
			MagnetizerRecipe r = RecipesMagnetizer.getRecipes().getRecipe(inv[0]);
			if (r != null && this.canRunRecipe(r))
				this.magnetize(r);
		}
	}

	private boolean hasRecipe() {
		return inv[0] != null && RecipesMagnetizer.getRecipes().getRecipe(inv[0]) != null;
	}

	private boolean canRunRecipe(MagnetizerRecipe r) {
		int ms = r.minSpeed;
		if (this.hasLodestoneUpgrade())
			ms /= 2;
		return omega >= ms && (r.allowStacking || inv[0].stackSize == 1);
	}

	private void magnetize(MagnetizerRecipe r) {
		if (rand.nextInt(r.timeFactor) > 0)
			return;
		ItemStack is = inv[0];
		if (r.action != null) {
			r.action.step(this.hasLodestoneUpgrade() ? omega*2 : omega, inv[0]);
		}
		else {
			if (is.stackTagCompound == null) {
				is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setInteger("magnet", 1);
			}
			else if (is.stackTagCompound.hasKey("magnet")){
				int m = is.stackTagCompound.getInteger("magnet");
				if (m < this.getMaxCharge(r))
					m++;
				is.stackTagCompound.setInteger("magnet", m);
			}
			else {
				is.stackTagCompound.setInteger("magnet", 1);
			}
		}
	}

	private int getMaxCharge(MagnetizerRecipe r) {
		return omega/r.speedPeruT;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		MagnetizerRecipe rec = RecipesMagnetizer.getRecipes().getRecipe(is);
		return rec != null && ((inv[0] == null && is.stackSize == 1) || rec.allowStacking);
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasRecipe())
			return 15;
		return 0;
	}

	@Override
	public int getOperationTime() {
		int base = DurationRegistry.MAGNETIZER.getOperationTime(omega);
		return Math.max(MIN_DURATION, this.hasLodestoneUpgrade() ? base/2 : base);
	}

	@Override
	public boolean areConditionsMet() {
		return this.hasRecipe();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Shaft Core";
	}

	@Override
	public int getCoreMagnetization() {
		return inv[0] != null && inv[0].stackTagCompound != null ? inv[0].stackTagCompound.getInteger("magnet") : 0;
	}

	@Override
	public void addRedstoneUpgrade() {
		redstone.addIntegrated();
	}

	@Override
	public boolean hasRedstoneUpgrade() {
		return redstone.hasIntegrated();
	}

	public void addLodestoneUpgrade() {
		hasLodestone = true;
	}

	public boolean hasLodestoneUpgrade() {
		return hasLodestone;
	}

	@Override
	public void upgrade(ItemStack is) {
		switch(Upgrades.list[is.getItemDamage()]) {
			case REDSTONE:
				this.addRedstoneUpgrade();
				break;
			case LODESTONE:
				this.addLodestoneUpgrade();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		if (ItemRegistry.UPGRADE.matchItem(item)) {
			switch(Upgrades.list[item.getItemDamage()]) {
				case REDSTONE:
					return !this.hasRedstoneUpgrade();
				case LODESTONE:
					return !this.hasLodestoneUpgrade();
				default:
					return false;
			}
		}
		return false;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("redstoneUpgrade", redstone.hasIntegrated());
		NBT.setBoolean("lodestoneUpgrade", hasLodestone);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		redstone.reset();
		if (NBT.getBoolean("redstoneUpgrade"))
			redstone.addIntegrated();
		hasLodestone = NBT.getBoolean("lodestoneUpgrade");
	}

	@Override
	public void breakBlock() {
		if (this.hasRedstoneUpgrade()) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.REDSTONE.ordinal()));
		}
		if (this.hasLodestoneUpgrade()) {
			ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.LODESTONE.ordinal()));
		}
	}

	public boolean hasCore() {
		return ReikaItemHelper.matchStacks(inv[0], ItemStacks.shaftcore) || ReikaItemHelper.matchStacks(inv[0], ItemStacks.tungstenshaftcore);
	}

}
