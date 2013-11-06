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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;

public class TileEntityPipe extends TileEntityPiping {

	private Fluid liquid;
	public int liquidLevel = 0;
	public int fluidPressure = 0;
	public int fluidrho;

	public static final int HORIZLOSS = 1*0;	// all are 1(friction)+g (10m) * delta h (0 or 1m)
	public static final int UPLOSS = 1*0;
	public static final int DOWNLOSS = -1*0;

	public static final int UPPRESSURE = 40;
	public static final int HORIZPRESSURE = 20;
	public static final int DOWNPRESSURE = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateEntity(world, x, y, z, meta);
		fluidrho = this.getDensity();
		//this.transfer(world, x, y, z);
		//this.drainReservoir(world, x, y, z);
		//this.getFromBucketFiller(world, x, y, z);
		//this.drainLavaMaker(world, x, y, z);
		if (liquidLevel < 0)
			liquidLevel = 0;
		if (liquidLevel == 0)
			liquid = null;
		if (fluidPressure > 0 && tickcount > 40) {
			fluidPressure--;
			tickcount = 0;
		}
		if (fluidPressure < 0)
			fluidPressure = 0;
	}

	public int getDensity() {
		if (FluidRegistry.LAVA.equals(liquid))
			return (int)ReikaEngLibrary.rholava/100;
		if (FluidRegistry.WATER.equals(liquid))
			return (int)ReikaEngLibrary.rhowater/100;
		return liquid != null ? liquid.getDensity() : 0;
	}

	private void drainReservoir(World world, int x, int y, int z) {
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tile = (TileEntityReservoir)world.getBlockTileEntity(x, y+1, z);
			if (tile != null && !tile.isEmpty()) {
				if (this.canTakeInFluid(tile.getFluid())) {
					liquidLevel += tile.getLevel()/4+1;
					tile.setLevel(tile.getLevel()-tile.getLevel()/4-1);
					liquid = tile.getFluid();
				}
			}
		}
	}

	public boolean canTakeInFluid(Fluid f) {
		if (f == null)
			return false;
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return false;
		return liquid == null || liquid.equals(f);
	}

	@Override
	protected void onIntake(TileEntity te) {
		if (te instanceof TileEntityPump) {
			TileEntityPump tile = (TileEntityPump)te;
			fluidPressure = tile.liquidPressure;
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("pressure", fluidPressure);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		fluidPressure = NBT.getInteger("pressure");
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PIPE.ordinal();
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE || m == MachineRegistry.VALVE || m == MachineRegistry.SPILLER || m == MachineRegistry.SEPARATION || m == MachineRegistry.BYPASS;
	}

	@Override
	public Icon getBlockIcon() {
		return RotaryCraft.decoblock.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return liquid != null && liquidLevel > 0;
	}

	@Override
	public Fluid getLiquidType() {
		return liquid;
	}

	public boolean contains(Fluid f) {
		return f.equals(liquid);
	}

	@Override
	public void setFluid(Fluid f) {
		liquid = f;
	}

	@Override
	public int getLiquidLevel() {
		return liquidLevel;
	}

	@Override
	protected void setLevel(int amt) {
		liquidLevel = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return true;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		if (f.equals(FluidRegistry.getFluid("jet fuel")))
			return false;
		if (f.equals(FluidRegistry.getFluid("lubricant")))
			return false;
		if (f.equals(FluidRegistry.getFluid("rc ethanol")))
			return false;
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
