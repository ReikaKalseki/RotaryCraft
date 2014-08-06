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

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityGrindstone extends InventoriedPowerLiquidReceiver implements DiscreteFunction, ConditionalOperation, OneSlotMachine {

	private static final String NBT_TAG = "repairs";
	private int soundtick;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(true);
		tickcount++;
		if (power < MINPOWER || torque < MINTORQUE) {
			return;
		}

		if (inv[0] != null) {
			soundtick++;
			if (soundtick > 49) {
				SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, RotaryAux.isMuffled(this) ? 0.1F : 0.5F, 1);
				soundtick = 0;
			}
		}

		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;

		if (world.isRemote)
			return;

		if (tank.isEmpty())
			return;

		if (this.hasValidItem()) {
			this.createUsesTag();
			this.repair();
			tank.removeLiquid(100);
		}
	}

	private void createUsesTag() {
		if (inv[0].stackTagCompound == null)
			inv[0].stackTagCompound = new NBTTagCompound();
		if (!inv[0].stackTagCompound.hasKey(NBT_TAG))
			inv[0].stackTagCompound.setInteger(NBT_TAG, inv[0].getMaxDamage()*2);
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 1:
			read = ForgeDirection.EAST;
			read2 = ForgeDirection.WEST;
			break;
		case 0:
			read = ForgeDirection.SOUTH;
			read2 = ForgeDirection.NORTH;
			break;
		}
	}

	private void repair() {
		int dmg = inv[0].getItemDamage();
		int newdmg = dmg-1;
		inv[0].setItemDamage(newdmg);
		int repair = inv[0].stackTagCompound.getInteger(NBT_TAG);
		inv[0].stackTagCompound.setInteger(NBT_TAG, repair-1);
		//ReikaJavaLibrary.pConsole(inv[0].stackTagCompound);
	}

	public int getMinimumDamageForItem(ItemStack is) {
		return is.stackTagCompound != null && is.stackTagCompound.hasKey(NBT_TAG) ? is.getMaxDamage()-MathHelper.ceiling_float_int(is.stackTagCompound.getInteger(NBT_TAG)/2F) : 0;
	}

	public boolean hasValidItem() {
		if (inv[0] == null)
			return false;
		if (!this.isItemValidForSlot(0, inv[0]))
			return false;
		return inv[0].getItemDamage() > this.getMinimumDamageForItem(inv[0]);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int j) {
		return is.getItemDamage() <= this.getMinimumDamageForItem(is);
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.isItemStackDamageable() && (is.getItem() instanceof ItemShears || is.getItem() instanceof ItemSword || is.getItem() instanceof ItemTool);
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 1000;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GRINDSTONE;
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
	public boolean areConditionsMet() {
		return this.hasValidItem();
	}

	@Override
	public String getOperationalStatus() {
		return inv[0] == null ? "No Tool" : this.areConditionsMet() ? "Operational" : "Invalid Item";
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.GRINDSTONE.getOperationTime(omega);
	}

}
