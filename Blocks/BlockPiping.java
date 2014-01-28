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
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;

public class BlockPiping extends BlockBasicMultiTE {

	private Icon[] iconBlocks = new Icon[16];

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
		for (int i = 0; i < 6; i++) {
			iconBlocks[0] = Block.planks.getIcon(0, 0);
			iconBlocks[1] = RotaryCraft.decoblock.getIcon(0, 0);
			iconBlocks[2] = Block.obsidian.getIcon(0, 0);
			iconBlocks[4] = Block.blockRedstone.getIcon(0, 0);
			iconBlocks[5] = Block.netherBrick.getIcon(0, 0);
			iconBlocks[6] = Block.blockLapis.getIcon(0, 0);
			iconBlocks[7] = Block.sandStone.getIcon(0, 0);
		}
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return iconBlocks[meta];
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		TileEntityPiping te = (TileEntityPiping)world.getBlockTileEntity(x, y, z);
		te.recomputeConnections(world, x, y, z);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntityPiping te = (TileEntityPiping)world.getBlockTileEntity(x, y, z);
		te.addToAdjacentConnections(world, x, y, z);
		te.recomputeConnections(world, x, y, z);
	}
}
