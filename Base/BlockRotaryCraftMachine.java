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

import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.Base.BlockTEBase;

@Strippable(value = {"mcp.mobius.waila.api.IWailaDataProvider"})
public abstract class BlockRotaryCraftMachine extends BlockTEBase implements IWailaDataProvider {

	protected Random par5Random = new Random();

	public BlockRotaryCraftMachine(Material mat) {
		super(mat);
		this.setHardness(4F);
		this.setResistance(15F);
		this.setLightLevel(0F);
		if (mat == Material.iron)
			this.setStepSound(soundTypeMetal);
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
	public final boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return 0;
	}

}
