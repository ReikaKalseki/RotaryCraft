/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeRenderConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.PumpablePipe;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipePump extends TileEntityPowerReceiver implements PipeRenderConnector {

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
		this.getIOSidesDefault(world, x, y, z, meta);

		if (this.getTicksExisted() < 2)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		if (power >= MINPOWER && omega >= MINSPEED) {
			ForgeDirection dir = read.getOpposite();
			ForgeDirection dir2 = dir.getOpposite();
			TileEntity te = this.getAdjacentTileEntity(dir);
			TileEntity te2 = this.getAdjacentTileEntity(dir2);
			if (te instanceof PumpablePipe && te2 instanceof PumpablePipe) {
				PumpablePipe p1 = (PumpablePipe)te;
				PumpablePipe p2 = (PumpablePipe)te2;
				int lvl1 = p1.getFluidLevel();
				int lvl2 = p2.getFluidLevel();
				if (p2.canTransferTo(p1, dir)) {
					p1.transferFrom(p2, this.getTransferrableAmount(lvl2));
				}
			}
		}
	}

	private int getTransferrableAmount(int amt) {
		return Math.min(amt, omega/4);
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipeOnSide(ForgeDirection dir) {
		return dir == read || dir.getOpposite() == read;
	}

}