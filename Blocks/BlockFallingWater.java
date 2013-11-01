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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;

public class BlockFallingWater extends Block {

	public BlockFallingWater(int par1) {
		super(par1, Material.ground);
		this.setTickRandomly(true);
		this.setResistance(3600000);
		this.setLightOpacity(0);
		this.setBlockBounds(0, 0, 0, 1, 0.875F, 1);
	}

	@Override
	public int tickRate(World world) {
		return 4;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, blockID, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {
		boolean flag = false;
		if (this.obsidianize(world, x, y, z)) {
			world.setBlock(x, y, z, 0);
			ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.fizz");
			ReikaParticleHelper.SMOKE.spawnAroundBlock(world, x, y-1, z, 8);
			return;
		}
		if (this.canMoveInto(world, x, y-1, z)) {
			world.setBlock(x, y-1, z, blockID);
			world.setBlock(x, y, z, 0);
			world.markBlockForRenderUpdate(x, y, z);
			world.markBlockForRenderUpdate(x, y-1, z);
			flag = true;
		}
		else {
			ForgeDirection[] dir = new ForgeDirection[]{ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.NORTH};
			ReikaArrayHelper.shuffleArray(dir);
			for (int i = 0; i < dir.length; i++) {
				int dx = x+dir[i].offsetX;
				int dy = y+dir[i].offsetY;
				int dz = z+dir[i].offsetZ;
				if (!flag && this.canMoveInto(world, dx, dy, dz)) {
					world.setBlock(dx, dy, dz, blockID);
					world.setBlock(x, y, z, 0);
					world.markBlockForRenderUpdate(x, y, z);
					world.markBlockForRenderUpdate(dx, dy, dz);
					flag = true;
				}
			}
		}
		if (flag)
			world.scheduleBlockUpdate(x, y, z, blockID, this.tickRate(world));
		else {
			world.setBlock(x, y, z, Block.waterMoving.blockID);
		}
	}

	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	public Icon getBlockTexture(IBlockAccess iba, int x, int y, int z) {
		return this.getIcon(0, 0);
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return Block.waterStill.getIcon(s, 0);
	}

	@Override
	public void registerIcons(IconRegister ico) {
		blockIcon = ico.registerIcon("ReactorCraft:steam");
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
		return true;
	}

	private boolean obsidianize(World world, int x, int y, int z) {
		boolean flag = false;
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;

			int id = world.getBlockId(dx, dy, dz);
			int meta = world.getBlockMetadata(dx, dy, dz);
			if (id != 0 && meta == 0) {
				Block b = Block.blocksList[id];
				if (b.blockMaterial == Material.lava) {
					world.setBlock(dx, dy, dz, Block.obsidian.blockID);
					flag = true;
				}
			}
		}
		return flag;
	}

	public static final boolean canMoveInto(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (id == 0)
			return true;
		if (id == RotaryCraft.waterblock.blockID)
			return false;
		Block b = Block.blocksList[id];
		if (b instanceof BlockFluid && meta == 0)
			return false;
		return ReikaWorldHelper.softBlocks(world, x, y, z);
	}

}
