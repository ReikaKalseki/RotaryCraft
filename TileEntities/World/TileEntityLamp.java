/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Interfaces.BreakAction;
import Reika.DragonAPI.Interfaces.InertIInv;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntitySpringPowered;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityLamp extends TileEntitySpringPowered implements InertIInv, RangedEffect, OneSlotMachine, BreakAction {

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
			Coordinate c = light.getNthBlock(n);
			if (c.getBlock(world) == Blocks.air)
				c.setBlock(world, BlockRegistry.LIGHT.getBlockInstance(), 15);
			worldObj.func_147451_t(c.xCoord, c.yCoord, c.zCoord);
		}
	}

	public boolean canEditAt(World world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		return id == Blocks.air || id.isAir(world, x, y, z);
	}

	private void goDark() {
		for (int n = 0; n < light.getSize(); n++) {
			Coordinate c = light.getNthBlock(n);
			if (c.getBlock(worldObj) == BlockRegistry.LIGHT.getBlockInstance())
				c.setBlock(worldObj, Blocks.air);
			worldObj.func_147451_t(c.xCoord, c.yCoord, c.zCoord);
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
		return this.getMaxRange();
	}

	@Override
	public int getMaxRange() {
		return MAXRANGE;
	}

	private void clearAll() {
		for (int k = 0; k < light.getSize(); k++) {
			Coordinate c = light.getNthBlock(k);
			c.setBlock(worldObj, Blocks.air);
		}
	}

	@Override
	public int getBaseDischargeTime() {
		return 120;
	}

	@Override
	public void breakBlock() {
		this.clearAll();
	}

}
