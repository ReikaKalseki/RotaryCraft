/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;

public class BlockFallingWater extends Block {

	public BlockFallingWater(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public int tickRate(World world) {
		return 4;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {

	}

	private boolean shouldTurnIntoWater(World world, int x, int y, int z) {
		return !this.canMove(world, x, y, z);
	}

	private boolean canMove(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
		}
		return false;
	}

	private boolean obsidianize(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {

		}
		return false;
	}

	public static final boolean canMoveInto(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (id == 0)
			return true;
		Block b = Block.blocksList[id];
		if (b instanceof BlockFluid && meta == 0)
			return false;
		return ReikaWorldHelper.softBlocks(world, x, y, z);
	}

}
