package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityInterface extends TileEntityPiping {

	private Fluid fluid;
	private int level;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.draw(world, x, y, z);
		this.transfer(world, x, y, z);
	}

	private boolean isTarget(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (id == 0)
			return false;
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null && m.isPipe())
			return false;
		return Block.blocksList[id].hasTileEntity(meta);
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (this.canInteractWith(world, x, y, z, dir)) {
				int dx = x+dir.offsetX;
				int dy = y+dir.offsetY;
				int dz = z+dir.offsetZ;
				if (this.isTarget(world, dx, dy, dz)) {
					TileEntity te = world.getBlockTileEntity(dx, dy, dz);
				}
			}
		}
	}

	@Override
	public void transfer(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
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
					if (te.canTakeInFluid(fluid)) {
						te.setFluid(fluid);
						te.liquidLevel += level/4+1;
						level -= level/4+1;
					}
			}
		}
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry p) {
		return p == MachineRegistry.PIPE || p == MachineRegistry.HOSE || p == MachineRegistry.FUELLINE;
	}

	@Override
	public Icon getBlockIcon() {
		return Block.stone.getIcon(0, 0);
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
		return MachineRegistry.INTERFACE.ordinal();
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
