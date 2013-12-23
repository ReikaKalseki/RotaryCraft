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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class BlockPiping extends BlockBasicMultiTE {

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
			icons[MachineRegistry.HOSE.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:hose");
			icons[MachineRegistry.PIPE.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:pipe");
			icons[MachineRegistry.FUELLINE.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:fuelline");
			icons[MachineRegistry.SPILLER.getMachineMetadata()][0][i][0] = ico.registerIcon("RotaryCraft:spiller");
		}
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
