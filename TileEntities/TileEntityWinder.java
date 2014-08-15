/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.TensionStorage;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityWinder extends InventoriedPowerReceiver implements OneSlotMachine, SimpleProvider, DiscreteFunction, ConditionalOperation {

	//Whether in wind or unwind mode
	public boolean winding = true;

	public final int getUnwindTorque() {
		if (inv[0] == null)
			return 0;
		return 8*((TensionStorage)inv[0].getItem()).getPowerScale(inv[0]);
	}

	public final int getUnwindSpeed() {
		if (inv[0] == null)
			return 0;
		return 1024*((TensionStorage)inv[0].getItem()).getPowerScale(inv[0]);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		if (!winding) {
			write = read;
			read = null;
		}
		tickcount++;
		if (inv[0] == null) {
			if (!winding) {
				torque = 0;
				omega = 0;
			}
			return;
		}
		if (!(inv[0].getItem() instanceof TensionStorage)) {
			if (!winding) {
				torque = 0;
				omega = 0;
			}
			return;
		}
		if (winding) {
			if (tickcount < this.getOperationTime())
				return;
			tickcount = 0;
			if (inv[0].getItemDamage() >= this.getMaxWind())
				return;
			inv[0] = new ItemStack(inv[0].getItem(), 1, inv[0].getItemDamage()+1);
			if (!world.isRemote && this.breakCoil()) {
				inv[0] = null;
				world.playSoundEffect(x, y, z, "random.break", 1F, 1F);
			}
		}
		else {
			if (inv[0].getItemDamage() <= 0) {
				omega = 0;
				torque = 0;
				power = 0;
				return;
			}
			omega = this.getUnwindSpeed();
			torque = this.getUnwindTorque();
			power = (long)omega*(long)torque;
			if (tickcount < this.getUnwindTime())
				return;
			tickcount = 0;
			inv[0] = new ItemStack(inv[0].getItem(), 1, inv[0].getItemDamage()-1);
		}

	}

	protected final int getUnwindTime() {
		ItemStack is = inv[0];
		int base = 20;
		return base*((TensionStorage)is.getItem()).getStiffness(is);
	}

	private boolean breakCoil() {
		ItemStack is = inv[0];
		if (is == null)
			return false;
		if (!(is.getItem() instanceof TensionStorage))
			return false;
		TensionStorage ts = (TensionStorage)is.getItem();
		if (!ts.isBreakable(is))
			return false;
		int dmg = inv[0].getItemDamage();
		float diff = (float)dmg/65536*0.05F;
		boolean rand = ReikaRandomHelper.doWithChance(diff);
		return rand;
	}

	public int getOperationTime() {
		if (inv[0] == null)
			return 1;
		int base = (int)ReikaMathLibrary.logbase(inv[0].getItemDamage(), 2);
		double factor = 1D/(int)(ReikaMathLibrary.logbase(omega+1, 2));
		return (int)(base*factor);
	}

	public int getMaxWind() {
		if (inv[0] == null)
			return 0;
		if (!(inv[0].getItem() instanceof TensionStorage))
			return 0;
		Item id = inv[0].getItem();
		int max = 0;
		max = torque/((TensionStorage)inv[0].getItem()).getStiffness(inv[0]);
		if (max > ItemRegistry.SPRING.getNumberMetadatas()) //technical limit
			return ItemRegistry.SPRING.getNumberMetadatas();
		return max;
	}

	public int getSizeInventory() {
		return 1;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		winding = NBT.getBoolean("winding");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setBoolean("winding", winding);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
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
	public boolean canProvidePower() {
		return !winding;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.WINDER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (is.getItem() == ItemRegistry.SPRING.getItemInstance())
			return true;
		if (is.getItem() == ItemRegistry.STRONGCOIL.getItemInstance())
			return true;
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (inv[0] == null)
			return 15;
		if (inv[0].getItem() != ItemRegistry.SPRING.getItemInstance())
			return 15;
		if (inv[0].getItemDamage() >= torque && winding)
			return 15;
		return 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onEMP() {}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && inv[0].getItem() instanceof TensionStorage;
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Coil";
	}
}