/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.RotaryCraft.ClientProxy;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.ConfigRegistry;

public class BlockPiping extends BlockBasicMultiTE {

	private IIcon[][] iconBlocks = new IIcon[16][2];

	public BlockPiping(Material par3Material) {
		super(par3Material);
		this.setHardness(MathHelper.clamp_float(ConfigRegistry.PIPEHARDNESS.getFloat(), 0, 1));
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
		iconBlocks[8][0] = ico.registerIcon("rotarycraft:bedrock");

		for (int i = 0; i < 9; i++) {
			iconBlocks[i][1] = Blocks.glass.getIcon(0, 0);
		}
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		s = Math.min(s, 1);
		return iconBlocks[meta][s];
	}

	@Override
	public final AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		TileEntityPiping te = (TileEntityPiping)world.getTileEntity(x, y, z);
		double d = 0.125;
		double[] dd = new double[6];
		for (int i = 0; i < 6; i++)
			dd[i] = te.isConnectedDirectly(ForgeDirection.VALID_DIRECTIONS[i]) ? 0 : d;
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x+dd[4], y+dd[1], z+dd[2], x+1-dd[5], y+1-dd[0], z+1-dd[3]);
		this.setBounds(box, x, y, z);
		return box;
	}

	@Override
	public final AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
}
