/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.util.Comparator;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Comparators.CoordinateDistanceComparator;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DurationRegistry;


public abstract class TileEntityAreaFiller extends TileEntityPowerReceiver implements DiscreteFunction {

	private BlockArray blocks = new BlockArray();

	private Comparator<Coordinate> distanceComparator;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (world.isRemote)
			return;

		this.getSummativeSidedPower();

		this.legacyFunction(world, x, y, z, meta);
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		super.onFirstTick(world, x, y, z);

		distanceComparator = new CoordinateDistanceComparator(new Coordinate(this));
	}

	private void legacyFunction(World world, int x, int y, int z, int meta) {
		tickcount++;
		if (power >= MINPOWER && this.hasRemainingBlocks()) {
			BlockKey bk = this.getNextPlacedBlock();
			if (bk != null && power >= this.getRequiredPower()) {
				if (blocks.isEmpty()) {
					int r = ConfigRegistry.SPILLERRANGE.getValue();
					if (r > 0) {
						blocks.recursiveAddWithBounds(world, x, y-1, z, Blocks.air, x-r, 1, z-r, x+r, y-1, z+r);
						if (this.allowFluidOverwrite())
							blocks.recursiveAddLiquidWithBounds(world, x, y-1, z, x-r, 0, z-r, x+r, y-1, z+r, null);
						if (ReikaBlockHelper.isLiquid(bk.blockID))
							blocks.recursiveAddWithBoundsNoFluidSource(world, x, y-1, z, bk.blockID, x-r, 0, z-r, x+r, y-1, z+r);
					}
					blocks.sort(distanceComparator);
					blocks.sortBlocksByHeight(false);
				}
				if (tickcount >= this.getOperationTime() && !blocks.isEmpty()) {
					tickcount = 0;
					Coordinate c = blocks.getNextAndMoveOn();
					c.setBlock(world, bk.blockID, bk.metadata);
					bk.blockID.onBlockAdded(world, c.xCoord, c.yCoord, c.zCoord);
					//ReikaJavaLibrary.pConsole(c.xCoord+" "+c.yCoord+" "+c.zCoord);
					world.markBlockForUpdate(c.xCoord, c.yCoord, c.zCoord);
					String snd = bk.blockID.stepSound.getBreakSound();
					float f = 1;
					if (ReikaBlockHelper.isLiquid(bk.blockID)) {
						f = 0.5F+rand.nextFloat();
						snd = "game.neutral.swim";
					}
					ReikaSoundHelper.playSoundFromServerAtBlock(world, c.xCoord, c.yCoord, c.zCoord, snd, 1, f, true);
					this.onBlockPlaced();
				}
			}
		}
		else {
			//blocks.clear();
		}
	}

	protected abstract boolean allowFluidOverwrite();

	protected abstract long getRequiredPower();

	protected abstract void onBlockPlaced();

	protected abstract boolean hasRemainingBlocks();

	protected abstract BlockKey getNextPlacedBlock();

	public int getOperationTime() {
		return DurationRegistry.FILLER.getOperationTime(omega);
	}

}
