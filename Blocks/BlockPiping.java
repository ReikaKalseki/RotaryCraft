/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;

public class BlockPiping extends BlockBasicMultiTE {

	private Icon[][] iconBlocks = new Icon[16][2];

	public BlockPiping(int par1, Material par3Material) {
		super(par1, par3Material);
		this.setHardness(0F);
		this.setResistance(1F);
		this.setLightValue(0F);
	}

	@Override
	public final boolean isOpaqueCube() {
		return false;
	}

	@Override
	public final int getRenderType() {
		return RotaryCraft.proxy.pipeRender;
	}

	@Override
	public final int idDropped(int id, Random r, int fortune) {
		return 0;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public final boolean canRenderInPass(int pass)
	{
		if (RotaryCraft.instance.isLocked())
			return false;
		ClientProxy.pipe.renderPass = pass;
		return true;
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
	public final boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public final boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return true;
	}

	@Override
	public void registerIcons(IconRegister ico) {
		iconBlocks[0][0] = Block.planks.getIcon(0, 0);
		iconBlocks[1][0] = ico.registerIcon("rotarycraft:steel");
		iconBlocks[2][0] = Block.obsidian.getIcon(0, 0);
		iconBlocks[4][0] = Block.blockRedstone.getIcon(0, 0);
		iconBlocks[5][0] = Block.netherBrick.getIcon(0, 0);
		iconBlocks[6][0] = Block.blockLapis.getIcon(0, 0);
		iconBlocks[7][0] = Block.sandStone.getIcon(0, 0);

		for (int i = 0; i < 8; i++) {
			iconBlocks[i][1] = Block.glass.getIcon(0, 0);
		}
	}

	@Override
	public Icon getIcon(int s, int meta) {
		s = Math.min(s, 1);
		return iconBlocks[meta][s];
	}
}
