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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;

public class BlockHydraulicLine extends BlockBasicMultiTE {

	public Icon centerIcon;
	public Icon outIcon;
	public Icon inIcon;
	public Icon connectionIcon;

	public BlockHydraulicLine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public final int getRenderType() {
		return RotaryCraft.proxy.lineRender;
	}

	@Override
	public void registerIcons(IconRegister ico) {
		centerIcon = ico.registerIcon("RotaryCraft:hydraulic_center");
		outIcon = ico.registerIcon("RotaryCraft:hydraulic_out");
		inIcon = ico.registerIcon("RotaryCraft:hydraulic_in");
		connectionIcon = ico.registerIcon("RotaryCraft:hydraulic_connection");
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

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		this.setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public final AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		double d = 0.25;
		return ReikaAABBHelper.getBlockAABB(x, y, z).expand(-d, -d, -d);
	}

	@Override
	public final AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		double d = 0.3125;
		return ReikaAABBHelper.getBlockAABB(x, y, z).expand(-d, -d, -d);
	}

	@Override
	public final boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public final boolean isOpaqueCube() {
		return false;
	}
}
