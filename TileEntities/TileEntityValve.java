package Reika.RotaryCraft.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityValve extends TileEntityPiping {

	private Fluid fluid;
	private int level;

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return false;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			this.draw(world, x, y, z);
		this.transfer(world, x, y, z);
	}

	@Override
	public void draw(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y+1, z);
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y+1, z);
			if (!tile.isEmpty() && (tile.getFluid().equals(fluid) || level <= 0)) {
				fluid = tile.getFluid();
				level += tile.getLevel()/4+1;
				tile.setLevel(tile.getLevel()-tile.getLevel()/4-1);
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
		}
	}

	@Override
	public Icon getBlockIcon() {
		return Block.blockRedstone.getIcon(0, 0);
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
		return MachineRegistry.VALVE.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
