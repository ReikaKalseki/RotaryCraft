/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Interfaces.InertIInv;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityLamp extends TileEntitySpringPowered implements InertIInv, RangedEffect, OneSlotMachine {

	private BlockArray light = new BlockArray();

	private boolean canlight;

	public static final int MAXRANGE = 12;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.LAMP;
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
		boolean red = world.isBlockIndirectlyGettingPowered(x, y, z);

		if (!red)
			this.updateCoil();

		if (world.isRemote)
			return;

		if (red)
			canlight = false;

		if (!canlight) {
			this.goDark();
			return;
		}

		if (light.isEmpty()) {
			for (int i = 1; i <= this.getRange(); i++) {
				if (this.canEditAt(world, x+i, y, z))
					light.addBlockCoordinate(x+i, y, z);
				if (this.canEditAt(world, x, y+i, z))
					light.addBlockCoordinate(x, y+i, z);
				if (this.canEditAt(world, x, y, z+i))
					light.addBlockCoordinate(x, y, z+i);
				if (this.canEditAt(world, x-i, y, z))
					light.addBlockCoordinate(x-i, y, z);
				if (this.canEditAt(world, x, y-i, z))
					light.addBlockCoordinate(x, y-i, z);
				if (this.canEditAt(world, x, y, z-i))
					light.addBlockCoordinate(x, y, z-i);
			}
			for (int r = 2; r <= this.getRange()*0.8; r += 2) {
				if (this.canEditAt(world, x+r, y, z+r))
					light.addBlockCoordinate(x+r, y, z+r);
				if (this.canEditAt(world, x-r, y, z+r))
					light.addBlockCoordinate(x-r, y, z+r);
				if (this.canEditAt(world, x+r, y, z-r))
					light.addBlockCoordinate(x+r, y, z-r);
				if (this.canEditAt(world, x-r, y, z-r))
					light.addBlockCoordinate(x-r, y, z-r);

				if (this.canEditAt(world, x+r, y+r, z+r))
					light.addBlockCoordinate(x+r, y+r, z+r);
				if (this.canEditAt(world, x-r, y+r, z+r))
					light.addBlockCoordinate(x-r, y+r, z+r);
				if (this.canEditAt(world, x+r, y+r, z-r))
					light.addBlockCoordinate(x+r, y+r, z-r);
				if (this.canEditAt(world, x-r, y+r, z-r))
					light.addBlockCoordinate(x-r, y+r, z-r);

				if (this.canEditAt(world, x+r, y-r, z+r))
					light.addBlockCoordinate(x+r, y-r, z+r);
				if (this.canEditAt(world, x-r, y-r, z+r))
					light.addBlockCoordinate(x-r, y-r, z+r);
				if (this.canEditAt(world, x+r, y-r, z-r))
					light.addBlockCoordinate(x+r, y-r, z-r);
				if (this.canEditAt(world, x-r, y-r, z-r))
					light.addBlockCoordinate(x-r, y-r, z-r);
			}
			return;
		}
		//int[] xyz = light.getNextAndMoveOn();
		for (int n = 0; n < light.getSize(); n++) {
			int[] xyz = light.getNthBlock(n);
			if (world.getBlockId(xyz[0], xyz[1], xyz[2]) == 0)
				world.setBlock(xyz[0], xyz[1], xyz[2], RotaryCraft.lightblock.blockID, 15, 3);
			worldObj.updateAllLightTypes(xyz[0], xyz[1], xyz[2]);
		}
	}

	public boolean canEditAt(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		return id == 0 || Block.blocksList[id].isAirBlock(world, x, y, z);
	}

	private void goDark() {
		for (int n = 0; n < light.getSize(); n++) {
			int[] xyz = light.getNthBlock(n);
			if (worldObj.getBlockId(xyz[0], xyz[1], xyz[2]) == RotaryCraft.lightblock.blockID)
				worldObj.setBlock(xyz[0], xyz[1], xyz[2], 0);
			worldObj.updateAllLightTypes(xyz[0], xyz[1], xyz[2]);
		}
	}

	private void updateCoil() {
		if (!this.hasCoil()) {
			canlight = false;
			return;
		}
		tickcount++;
		if (tickcount > this.getUnwindTime()) {
			ItemStack is = this.getDecrementedCharged();
			inv[0] = is;
			tickcount = 0;
		}
		canlight = true;
	}

	@Override
	public int getRange() {
		return 8;
	}

	@Override
	public int getMaxRange() {
		return MAXRANGE;
	}

	public void clearAll() {
		for (int k = 0; k < light.getSize(); k++) {
			int[] ijk = light.getNthBlock(k);
			worldObj.setBlock(ijk[0], ijk[1], ijk[2], 0);
		}
	}

	@Override
	public int getBaseDischargeTime() {
		return 120;
	}

}
