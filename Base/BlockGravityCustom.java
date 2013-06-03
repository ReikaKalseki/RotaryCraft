/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Entities.EntityFallingBlock;

public class BlockGravityCustom extends Block
{
	/** Do blocks fall instantly to where they stop or do they fall over time */
	public static boolean fallInstantly = false;
	public boolean nonRandomTick = false;

	public BlockGravityCustom(int par1)
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public BlockGravityCustom(int par1, Material yMaterial)
	{
		super(par1, yMaterial);
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		nonRandomTick = true;
		world.scheduleBlockUpdate(x, y, z, blockID, this.tickRate());
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
	{
		nonRandomTick = true;
		world.scheduleBlockUpdate(x, y, z, blockID, this.tickRate());
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random par5Random)
	{
		if (!world.isRemote)
		{
			this.tryToFall(world, x, y, z);
		}
	}

	/**
	 * If there is space to fall below will start this block falling
	 */
	private void tryToFall(World world, int x, int y, int z)
	{
		if (canFallBelow(world, x, y - 1, z) && y >= 0)
		{
			byte var8 = 32;

			if (!fallInstantly && world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
			{
				if (!world.isRemote)
				{

					EntityFallingBlock var9 = new EntityFallingBlock(world, x + 0.5F, y + 0.5F, z + 0.5F, blockID, world.getBlockMetadata(x, y, z));
					this.onStartFalling(var9);
					world.spawnEntityInWorld(var9);
				}
			}
			else
			{
				ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 0);

				while (canFallBelow(world, x, y - 1, z) && y > 0)
				{
					--y;
				}

				if (y > 0)
				{
					ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, blockID);
				}
			}
		}
	}

	/**
	 * Called when the falling block entity for this block is created
	 */
	protected void onStartFalling(EntityFallingBlock par1EntityFallingGravity) {}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate()
	{
		return 5;
	}

	/**
	 * Checks to see if the gravity can fall into the block below it
	 */
	public static boolean canFallBelow(World par0World, int par1, int x, int y)
	{
		int var4 = par0World.getBlockId(par1, x, y);

		if (var4 == 0)
		{
			return true;
		}
		else if (var4 == Block.fire.blockID)
		{
			return true;
		}
		else
		{
			Material var5 = Block.blocksList[var4].blockMaterial;
			return var5 == Material.water ? true : var5 == Material.lava;
		}
	}

	/**
	 * Called when the falling block entity for this block hits the ground and turns back into a block
	 */
	public void onFinishFalling(World world, int x, int y, int z, int par5) {}
}
