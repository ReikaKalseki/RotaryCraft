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

import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockPiping extends BlockBasicMultiTE {

	private IIcon[][] iconBlocks = new IIcon[16][2];

	public BlockPiping(Material par3Material) {
		super(par3Material);
		this.setHardness(0F);
		this.setResistance(1F);
		this.setLightLevel(0F);
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
	public final Item getItemDropped(int id, Random r, int fortune) {
		return null;
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
	public void registerBlockIcons(IIconRegister ico) {
		iconBlocks[0][0] = Blocks.planks.getIcon(0, 0);
		iconBlocks[1][0] = ico.registerIcon("rotarycraft:steel");
		iconBlocks[2][0] = Blocks.obsidian.getIcon(0, 0);
		iconBlocks[4][0] = Blocks.redstone_block.getIcon(0, 0);
		iconBlocks[5][0] = Blocks.nether_brick.getIcon(0, 0);
		iconBlocks[6][0] = Blocks.lapis_block.getIcon(0, 0);
		iconBlocks[7][0] = Blocks.sandstone.getIcon(0, 0);

		for (int i = 0; i < 8; i++) {
			iconBlocks[i][1] = Blocks.glass.getIcon(0, 0);
		}
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		s = Math.min(s, 1);
		return iconBlocks[meta][s];
	}
}