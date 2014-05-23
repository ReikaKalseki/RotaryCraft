/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.Random;

import mcp.mobius.waila.api.IWailaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Base.BlockTEBase;

public abstract class BlockRotaryCraftMachine extends BlockTEBase implements IWailaBlock {

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

}
