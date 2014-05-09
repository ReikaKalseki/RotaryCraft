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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesLavaMaker;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityLavaMaker extends InventoriedPowerLiquidProducer implements IFluidHandler, PipeConnector, ConditionalOperation {

	public static final int CAPACITY = 64000;

	public static final int MELT_ENERGY = 2820000; //approx

	public static final int MAXTEMP = 1500;

	private long energy;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		tickcount++;
		energy += power;

		if (omega > 0 && power > 0) {
			if (tickcount > 98) {
				SoundRegistry.FRICTION.playSoundAtBlock(world, x, y, z, 0.5F, 0.5F);
				tickcount = 0;
			}
			world.spawnParticle("crit", x+rand.nextDouble(), y, z+rand.nextDouble(), -0.2+0.4*rand.nextDouble(), 0.4*rand.nextDouble(), -0.2+0.4*rand.nextDouble());
		}

		//ReikaJavaLibrary.pConsole(energy/20L+":"+MELT_ENERGY, Side.SERVER);

		if (energy/20 >= MELT_ENERGY) {
			for (int i = 0; i < inv.length; i++) {
				ItemStack is = inv[i];
				if (is != null) {
					FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(is);
					if (fs != null) {
						if (this.canMake(fs)) {
							tank.addLiquid(fs.amount, fs.getFluid());
							ReikaInventoryHelper.decrStack(i, inv);
							energy -= MELT_ENERGY*20;
							return;
						}
					}
				}
			}
		}
	}

	private boolean canMake(FluidStack liq) {
		if (worldObj.isRemote)
			return false;
		if (tank.isEmpty())
			return true;
		if (!tank.getActualFluid().equals(liq.getFluid()))
			return false;
		if (tank.getLevel()+liq.amount > tank.getCapacity())
			return false;
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return RecipesLavaMaker.getRecipes().isValidFuel(is);
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
		return MachineRegistry.LAVAMAKER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		if (tank.isFull())
			return 15;
		if (!this.canMake())
			return 15;
		return 0;
	}

	private boolean canMake() {
		for (int i = 0; i < inv.length; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(is);
				if (fs != null)
					if (this.canMake(fs))
						return true;
			}
		}
		return false;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		energy = NBT.getLong("e");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setLong("e", energy);
	}

	public boolean isEmpty() {
		return tank.isEmpty();
	}

	public boolean hasStone() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	public void setEmpty() {
		tank.empty();
	}

	public void removeLava(int amt) {
		tank.removeLiquid(amt);
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to.offsetY == 0;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Items";
	}

}
