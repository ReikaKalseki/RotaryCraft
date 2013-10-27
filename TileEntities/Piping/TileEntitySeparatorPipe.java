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

public class TileEntitySeparatorPipe extends TileEntityPiping {

	private Fluid fluid;
	private int level;

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.FUELLINE || m == MachineRegistry.HOSE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.draw(world, x, y, z);
		this.transfer(world, x, y, z);
		if (level <= 0) {
			level = 0;
			fluid = null;
		}
	}

	private boolean canIntakeFluid(Fluid f) {
		return fluid == null || f.equals(fluid);
	}

	private boolean canIntakeFluid(String s) {
		return this.canIntakeFluid(FluidRegistry.getFluid(s));
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.FUELLINE) {
				TileEntityFuelLine te = (TileEntityFuelLine)world.getBlockTileEntity(dx, dy, dz);
				if (te.fuel > level)
					if (te.getFuelType() == TileEntityFuelLine.Fuels.ETHANOL && this.canIntakeFluid("rc ethanol")) {
						fluid = FluidRegistry.getFluid("rc ethanol");
						level += te.fuel/4+1;
						te.fuel -= te.fuel/4+1;
					}
					else if (te.getFuelType() == TileEntityFuelLine.Fuels.JETFUEL && this.canIntakeFluid("jet fuel")) {
						fluid = FluidRegistry.getFluid("jet fuel");
						level += te.fuel/4+1;
						te.fuel -= te.fuel/4+1;
					}
			}
			else if (m == MachineRegistry.HOSE) {
				TileEntityHose te = (TileEntityHose)world.getBlockTileEntity(dx, dy, dz);
				if (te.lubricant > level)
					if (this.canIntakeFluid("lubricant")) {
						fluid = FluidRegistry.getFluid("lubricant");
						level += te.lubricant/4+1;
						te.lubricant -= te.lubricant/4+1;
					}
			}
			else if (m == MachineRegistry.PIPE) {
				TileEntityPipe te = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
				if (te.liquidLevel > level) {
					Fluid f = FluidRegistry.lookupFluidForBlock(Block.blocksList[te.liquidID]);
					if (this.canIntakeFluid(f)) {
						fluid = f;
						level += te.liquidLevel/4+1;
						te.liquidLevel -= te.liquidLevel/4+1;
					}
				}
			}
		}
	}

	@Override
	public void transfer(World world, int x, int y, int z) {
		ForgeDirection dir = this.getDirectionDir();
		int dx = x+dir.offsetX;
		int dy = y+dir.offsetY;
		int dz = z+dir.offsetZ;
		MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
		if (m == MachineRegistry.FUELLINE) {
			TileEntityFuelLine te = (TileEntityFuelLine)world.getBlockTileEntity(dx, dy, dz);
			if (te.fuel < level)
				if (fluid.equals(FluidRegistry.getFluid("rc ethanol")) && te.canIntakeFluid("rc ethanol")) {
					te.setFuelType(TileEntityFuelLine.Fuels.ETHANOL);
					te.fuel += level/4+1;
					level -= level/4+1;
				}
				else if (fluid.equals(FluidRegistry.getFluid("jet fuel")) && te.canIntakeFluid("jet fuel")) {
					te.setFuelType(TileEntityFuelLine.Fuels.JETFUEL);
					te.fuel += level/4+1;
					level -= level/4+1;
				}
		}
		else if (m == MachineRegistry.HOSE) {
			TileEntityHose te = (TileEntityHose)world.getBlockTileEntity(dx, dy, dz);
			if (te.lubricant < level)
				if (fluid.equals(FluidRegistry.getFluid("lubricant"))) {
					te.lubricant += level/4+1;
					level -= level/4+1;
				}
		}
		else if (m == MachineRegistry.PIPE) {
			TileEntityPipe te = (TileEntityPipe)world.getBlockTileEntity(dx, dy, dz);
			if (te.liquidLevel < level)
				if (fluid.canBePlacedInWorld() && (te.liquidID == -1 || te.liquidID == fluid.getBlockID())) {
					te.liquidID = fluid.getBlockID();
					te.liquidLevel += level/4+1;
					level -= level/4+1;
				}
		}
	}

	private ForgeDirection getDirectionDir() {
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ? ForgeDirection.UP : ForgeDirection.DOWN;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.blockLapis.getIcon(0, 0);
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
		return MachineRegistry.SEPARATION.ordinal();
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
