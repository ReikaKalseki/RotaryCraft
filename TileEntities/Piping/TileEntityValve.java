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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
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
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return side != ForgeDirection.UP && this.canConnectToPipe(p);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			this.draw(world, x, y, z);
		this.transfer(world, x, y, z);
		if (level <= 0) {
			level = 0;
			fluid = null;
		}
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
