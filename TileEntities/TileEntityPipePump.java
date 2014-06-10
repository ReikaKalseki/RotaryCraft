package Reika.RotaryCraft.TileEntities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.Auxiliary.Interfaces.PumpablePipe;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityPipePump extends TileEntityPowerReceiver {

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.PIPEPUMP;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		this.getSummativeSidedPower();
		if (power >= MINPOWER && omega >= MINSPEED) {
			ForgeDirection dir = this.getFacing();
			ForgeDirection dir2 = dir.getOpposite();
			TileEntity te = this.getAdjacentTileEntity(dir);
			TileEntity te2 = this.getAdjacentTileEntity(dir2);
			if (te instanceof PumpablePipe && te2 instanceof PumpablePipe) {
				PumpablePipe p1 = (PumpablePipe)te;
				PumpablePipe p2 = (PumpablePipe)te2;
				int lvl1 = p1.getFluidLevel();
				int lvl2 = p2.getFluidLevel();
				if (lvl1 > lvl2) {
					if (p1.canTransferTo(p2, dir2)) {
						p2.transferFrom(p1, this.getTransferrableAmount(lvl1));
					}
				}
				else {
					if (p2.canTransferTo(p1, dir)) {
						p1.transferFrom(p2, this.getTransferrableAmount(lvl2));
					}
				}
			}
		}
	}

	public ForgeDirection getFacing() {
		return dirs[this.getBlockMetadata()];
	}

	private int getTransferrableAmount(int amt) {
		return Math.min(amt, omega/4);
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
