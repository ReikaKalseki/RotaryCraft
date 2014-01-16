/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.Random;

import mcp.mobius.waila.api.IWailaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class BlockRotaryCraftMachine extends Block implements IWailaBlock {

	protected Random par5Random = new Random();

	public BlockRotaryCraftMachine(int ID, Material mat) {
		super(ID, mat);
		this.setHardness(4F);
		this.setResistance(15F);
		this.setLightValue(0F);
		if (mat == Material.iron)
			this.setStepSound(soundMetalFootstep);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public final boolean canBeReplacedByLeaves(World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
	{
		return 0;
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z, Entity e)
	{
		return e instanceof EntityDragon;
	}

}
