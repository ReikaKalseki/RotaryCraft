/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cofh.api.energy.IEnergyHandler;

public class TileEntityDynamo extends TileEntityPowerReceiver implements IEnergyHandler {

	private ForgeDirection facingDir;

	public static final int MAXTORQUE = 8192;
	public static final int MAXOMEGA = 8192;

	private static final boolean reika = DragonAPICore.isReikasComputer();

	public final boolean isFlexibleMode() {
		return reika;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
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

		if (power > 0) {
			int id = world.getBlockId(writex, writey, writez);
			if (id != 0) {
				Block b = Block.blocksList[id];
				int metadata = world.getBlockMetadata(writex, writey, writez);
				if (b.hasTileEntity(metadata)) {
					TileEntity tile = world.getBlockTileEntity(writex, writey, writez);
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
		if (this.isFlexibleMode())
			pwr = power;
		return (int)((float)(pwr*10/ReikaBuildCraftHelper.getWattsPerMJ()));
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 2:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.EAST;
			break;
		case 4:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 5:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.WEST;
			break;
		case 1:
			ready = y+1;
			writey = y-1;
			facingDir = ForgeDirection.DOWN;
			break;
		case 0:
			ready = y-1;
			writey = y+1;
			facingDir = ForgeDirection.UP;
			break;
		}
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
