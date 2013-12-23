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

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockModelledMultiTE;

public class BlockHydraulicLine extends BlockModelledMultiTE {

	public BlockHydraulicLine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public final int getRenderType() {
		return RotaryCraft.proxy.lineRender;
	}

	@Override
	public final int idDropped(int id, Random r, int fortune) {
		return 0;
	}

	@Override
	public final boolean canRenderInPass(int pass)
	{
		return pass == 0 || pass == 1;
	}

	@Override
	public final int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public final int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public final boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return true;
	}
}
