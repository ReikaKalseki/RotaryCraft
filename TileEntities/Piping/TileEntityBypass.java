package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityFuelLine.Fuels;

public class TileEntityBypass extends TileEntityPiping {

	private Fluid fluid;
	private int level;

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.collectLiquid(world, x, y, z);
		this.outputLiquid(world, x, y, z);

		if (level <= 0) {
			level = 0;
			fluid = null;
		}
	}

	public boolean canIntakeFuelFrom(TileEntityFuelLine te) {
		if (fluid == null)
			return true;
		if (te.getFuelType() == Fuels.ETHANOL && fluid.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		if (te.getFuelType() == Fuels.JETFUEL && fluid.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		return false;
	}

	public boolean canIntakeLiquidFrom(TileEntityPipe te) {
		return fluid == null || fluid.getBlockID() == te.liquidID;
	}

	public boolean canOutputFuelTo(TileEntityFuelLine te) {
		if (te.getFuelType() == Fuels.EMPTY)
			return true;
		if (te.getFuelType() == Fuels.ETHANOL && fluid.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		if (te.getFuelType() == Fuels.JETFUEL && fluid.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		return false;
	}

	public boolean canOutputLiquidTo(TileEntityPipe te) {
		return te.liquidID == -1 || fluid.getBlockID() == te.liquidID;
	}

	private void outputLiquid(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.PIPE) {
				TileEntityPipe te = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
				if (level > 0 && te != null && te.liquidLevel < level && this.canOutputLiquidTo(te)) {
					te.liquidID = fluid.getBlockID();
					//ReikaJavaLibrary.pConsole(level+">"+te.liquidLevel, Side.SERVER);
					te.liquidLevel += level/2;
					level -= level/2;
					//ReikaJavaLibrary.pConsole(level+"<"+te.liquidLevel, Side.SERVER);
				}
			}
			else if (m == MachineRegistry.FUELLINE) {
				TileEntityFuelLine te = (TileEntityFuelLine)world.getBlockTileEntity(dx, dy, dz);
				if (level > 0 && te != null && te.fuel < level && this.canOutputFuelTo(te)) {
					if (fluid.equals(FluidRegistry.getFluid("rc ethanol")))
						te.setFuelType(Fuels.ETHANOL);
					else if (fluid.equals(FluidRegistry.getFluid("jet fuel")))
						te.setFuelType(Fuels.JETFUEL);
					te.fuel += level/2;
					level -= level/2;
				}
			}
		}
	}

	private void collectLiquid(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.PIPE) {
				TileEntityPipe te = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
				if (te != null && te.liquidLevel > level && this.canIntakeLiquidFrom(te)) {
					fluid = FluidRegistry.lookupFluidForBlock(Block.blocksList[te.liquidID]);
					level += te.liquidLevel/2;
					te.liquidLevel -= te.liquidLevel/2;
				}
			}
			else if (m == MachineRegistry.FUELLINE) {
				TileEntityFuelLine te = (TileEntityFuelLine)world.getBlockTileEntity(dx, dy, dz);
				if (te != null && te.fuel > level && this.canIntakeFuelFrom(te)) {
					if (te.getFuelType() == Fuels.ETHANOL)
						fluid = FluidRegistry.getFluid("rc ethanol");
					if (te.getFuelType() == Fuels.JETFUEL)
						fluid = FluidRegistry.getFluid("jet fuel");
					level += te.fuel/2;
					te.fuel -= te.fuel/2;
				}
			}
		}
	}

	@Override
	public void draw(World world, int x, int y, int z) {

	}

	@Override
	public void transfer(World world, int x, int y, int z) {

	}

	@Override
	public Icon getBlockIcon() {
		return Block.blockEmerald.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return level > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return fluid;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BYPASS.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("amount", level);

		ReikaNBTHelper.writeFluidToNBT(NBT, fluid);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		level = NBT.getInteger("amount");

		fluid = ReikaNBTHelper.getFluidFromNBT(NBT);

		if (level < 0) {
			level = 0;
		}
	}

}
