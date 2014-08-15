/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public class TileEntityDynamo extends TileEntityPowerReceiver implements IEnergyHandler {

	private ForgeDirection facingDir;

	public static final int MAXTORQUE = 8192;
	public static final int MAXOMEGA = 8192;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.DYNAMO;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false);

		if ((world.getWorldTime()&31) == 0)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		int writex = x+write.offsetX;
		int writey = y+write.offsetY;
		int writez = z+write.offsetZ;
		if (power > 0) {
			Block b = world.getBlock(writex, writey, writez);
			if (b != Blocks.air) {
				int metadata = world.getBlockMetadata(writex, writey, writez);
				if (b.hasTileEntity(metadata)) {
					TileEntity tile = world.getTileEntity(writex, writey, writez);
					if (tile instanceof IEnergyHandler) {
						IEnergyHandler rc = (IEnergyHandler)tile;
						if (rc.canInterface(facingDir.getOpposite())) {
							int rf = this.getGenRF();
							float used = rc.receiveEnergy(facingDir.getOpposite(), rf, false);
						}
					}
				}
			}
		}
	}

	public int getGenRF() {
		int tq = Math.min(torque, MAXTORQUE);
		int om = Math.min(omega, MAXOMEGA);
		long pwr = (long)tq*(long)om;
		return (int)(pwr*10/ReikaBuildCraftHelper.getWattsPerMJ());
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 2:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			facingDir = ForgeDirection.EAST;
			break;
		case 4:
			facingDir = ForgeDirection.NORTH;
			break;
		case 5:
			facingDir = ForgeDirection.WEST;
			break;
		case 1:
			facingDir = ForgeDirection.DOWN;
			break;
		case 0:
			facingDir = ForgeDirection.UP;
			break;
		}
		write = facingDir;
		read = write.getOpposite();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (this.canInterface(from)) {
			int rf = (int)((float)(power*10/ReikaBuildCraftHelper.getWattsPerMJ()));
			//return rf;
		}
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return from == facingDir;
	}

	private ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
	}

}