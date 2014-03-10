package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidProducer;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityRefrigerator extends InventoriedPowerLiquidProducer implements TemperatureTE, PressureTE, DiscreteFunction {

	private int pressure;
	private int temperature;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
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
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 4000;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.REFRIGERATOR;
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
	public void updatePressure(World world, int x, int y, int z, int meta) {

	}

	@Override
	public void addPressure(int press) {
		pressure += pressure;
	}

	@Override
	public int getPressure() {
		return pressure;
	}

	@Override
	public void overpressure(World world, int x, int y, int z) {

	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {

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
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.REFRIGERATOR.getOperationTime(omega);
	}

}
