package Reika.RotaryCraft.TileEntities.Processing;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Instantiable.InertItem;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesWetter;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesWetter.WettingRecipe;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWetter extends InventoriedPowerLiquidReceiver implements OneSlotMachine {

	private int tick = 0;
	private EntityItem item;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getPowerBelow();
		this.updateItem();

		if (power >= MINPOWER && omega >= MINSPEED) {
			ItemStack is = inv[0];
			if (is != null) {
				FluidStack fs = tank.getFluid();
				if (fs != null) {
					WettingRecipe wr = RecipesWetter.getRecipes().getRecipe(is, fs);
					if (wr != null) {
						if (tick >= wr.duration) {
							tank.removeLiquid(wr.getFluid().amount);
							inv[0] = wr.getOutput();
							tick = 0;
							this.onItemSet(0, inv[0]);
						}
						else {
							tick += 1+4*ReikaMathLibrary.logbase2(omega/MINSPEED);
						}
					}
					else {
						tick = 0;
					}
				}
				else {
					tick = 0;
				}
			}
			else {
				tick = 0;
			}
		}
		else {
			tick = 0;
		}
	}

	@Override
	protected void onItemSet(int slot, ItemStack is) {
		this.updateItem();
	}

	private void updateItem() {
		if (item == null && inv[0] == null)
			return;
		if ((item == null && inv[0] != null) || (item != null && inv[0] == null) || !ReikaItemHelper.matchStacks(item.getEntityItem(), inv[0])) {
			item = inv[0] != null ? new InertItem(worldObj, inv[0]) : null;
		}
		this.syncAllData(true);
	}

	public EntityItem getItem() {
		return item;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack is, int side) {
		return tick == 0;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE || m.isStandardPipe();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (tank.getActualFluid() != null) {
			return RecipesWetter.getRecipes().isWettableWith(is, tank.getActualFluid());
		}
		return RecipesWetter.getRecipes().isWettable(is);
	}

	@Override
	public Fluid getInputFluid() {
		return null;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		if (tick > 0)
			return false;
		if (inv[0] != null) {
			return RecipesWetter.getRecipes().isWettableWith(inv[0], f);
		}
		return RecipesWetter.getRecipes().isValidFluid(f);
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
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
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.WETTER;
	}

	@Override
	public boolean hasModelTransparency() {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
