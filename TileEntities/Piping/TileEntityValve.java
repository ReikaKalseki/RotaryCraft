/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;

public class TileEntityValve extends TileEntityPiping {

	private Fluid fluid;
	private int level;

	@Override
	public boolean canConnectToPipe(MachineRegistry p) {
		return p == MachineRegistry.PIPE || p == MachineRegistry.HOSE || p == MachineRegistry.FUELLINE || p == MachineRegistry.SEPARATION;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateEntity(world, x, y, z, meta);
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			this.drawReservoir(world, x, y, z);
	}

	public void drawReservoir(World world, int x, int y, int z) {
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
	public int getLiquidLevel() {
		return level;
	}

	@Override
	protected void setFluid(Fluid f) {
		fluid = f;
	}

	@Override
	protected void setLevel(int amt) {
		level = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return false;
	}

	@Override
	protected void onIntake(TileEntity te) {

	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return true;
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return true;
	}

}
