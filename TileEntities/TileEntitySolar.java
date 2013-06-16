/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.world.World;
import Reika.DragonAPI.BlockArray;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.MultiBlockMachine;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySolar extends TileEntityIOMachine implements MultiBlockMachine {

	private BlockArray solarBlocks = new BlockArray();

	@Override
	public boolean canProvidePower() {
		return this.isMultiBlock(worldObj, xCoord, yCoord, zCoord) && this.getMultiBlockPosition(worldObj, xCoord, yCoord, zCoord)[1] == 0;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.SOLARTOWER.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (world.getBlockId(x, y+1, z) != 0)
			return;
		if (MachineRegistry.getMachine(world, x, y-1, z) != this.getMachine())
			return;
		if (solarBlocks.isEmpty()) {
			solarBlocks.recursiveFill(world, x, y, z, this.getTileEntityBlockID());
			ReikaJavaLibrary.pConsole(y);
			while (solarBlocks.getSize() > 0) {
				int[] xyz = solarBlocks.getNextAndMoveOn();
				MachineRegistry m = MachineRegistry.getMachine(world, xyz[0], xyz[1], xyz[2]);
				if (m == MachineRegistry.MIRROR) {
					TileEntityMirror te = (TileEntityMirror)world.getBlockTileEntity(xyz[0], xyz[1], xyz[2]);
					te.targetloc = new int[]{x,y,z};
				}
			}
		}
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
	public boolean isMultiBlock(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int[] getMultiBlockPosition(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int[] getMultiBlockSize(World world, int x, int y, int z) {
		return null;
	}

}
