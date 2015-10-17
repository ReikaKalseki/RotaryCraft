/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.PumpablePipe;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityHose extends TileEntityPiping implements PumpablePipe {

	private int lubricant = 0;
	private int burnIn = 0;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateEntity(world, x, y, z, meta);

		if (burnIn > 0) {
			burnIn--;
			if (burnIn == 0) {
				this.doBurn();
			}
		}
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.HOSE;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE || m == MachineRegistry.VALVE || m == MachineRegistry.SEPARATION || m == MachineRegistry.SUCTION;
	}

	@Override
	public IIcon getBlockIcon() {
		return Blocks.planks.getIcon(0, 0);
	}

	@Override
	public boolean hasLiquid() {
		return lubricant > 0;
	}

	@Override
	public Fluid getFluidType() {
		return this.hasLiquid() ? FluidRegistry.getFluid("rc lubricant") : null;
	}

	@Override
	public int getFluidLevel() {
		return lubricant;
	}

	@Override
	protected void setFluid(Fluid f) { }

	@Override
	protected void setLevel(int amt) {
		lubricant = amt;
	}

	@Override
	protected boolean interactsWithMachines() {
		return true;
	}

	@Override
	protected void onIntake(TileEntity te) {

	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return f.equals(FluidRegistry.getFluid("rc lubricant"));
	}

	@Override
	public boolean canReceiveFromPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean canEmitToPipeOn(ForgeDirection side) {
		return true;
	}

	@Override
	public Block getPipeBlockType() {
		return Blocks.planks;
	}

	@Override
	public boolean canIntakeFromIFluidHandler(ForgeDirection side) {
		return side.offsetY != 0;
	}

	@Override
	public boolean canOutputToIFluidHandler(ForgeDirection side) {
		return side.offsetY == 0;
	}

	@Override
	public boolean canTransferTo(PumpablePipe p, ForgeDirection dir) {
		return p instanceof TileEntityHose;
	}

	@Override
	public void transferFrom(PumpablePipe from, int amt) {
		((TileEntityHose)from).lubricant -= amt;
		lubricant += amt;
	}

	public void burn() {
		this.burn(true);
	}

	private void burn(boolean immediate) {
		ReikaWorldHelper.ignite(worldObj, xCoord, yCoord, zCoord);
		if (immediate) {
			this.doBurn();
		}
		else {
			int time = this.hasLiquid() ? 5+rand.nextInt(15) : 10+rand.nextInt(30);
			if (burnIn <= 0)
				burnIn = time;
		}
	}

	private void doBurn() {
		for (int i = 0; i < 6; i++) {
			if (rand.nextInt(3) > 0) {
				TileEntity te = this.getAdjacentTileEntity(dirs[i]);
				if (te instanceof TileEntityHose) {
					((TileEntityHose)te).burn(false);
				}
			}
		}
		worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
		if (this.hasLiquid() && rand.nextInt(4) == 0)
			worldObj.newExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 2, true, false);
	}
}
