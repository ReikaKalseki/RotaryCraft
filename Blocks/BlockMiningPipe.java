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

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaDirectionHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasic;

public class BlockMiningPipe extends BlockBasic {

	public BlockMiningPipe(int ID) {
		super(ID, Material.iron);
		this.setHardness(0F);
		this.setResistance(0F);
		this.setLightValue(0F);
		this.setStepSound(soundMetalFootstep);
		//this.blockIndexInTexture = 60;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}

	@Override
	public int damageDropped(int par1)
	{
		return 0;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	public int getBlockTextureFromSideAndMetadata(int s, int dmg) {
		// We want the texture next to our default texture from this block for the bottom and top side
		// so we just add 1 when the side is 0 or 1 else we return the default one

		if (dmg < 4)
			return 0;//this.blockIndexInTexture;
		if (dmg == 4)
			return 78;
		return 0;
	}

	@Override
	public final ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		ForgeDirection dir = this.getDirectionFromMeta(meta);
		ForgeDirection left = ReikaDirectionHelper.getLeftBy90(dir);
		int r = 0; //some way to make bigger on demand...?
		int d = 64;
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				for (int k = -d; k <= d; k++) {
					int dx = x+left.offsetX*i+dir.offsetX*k;
					int dy = y+j;
					int dz = z+left.offsetZ*i+dir.offsetZ*k;
					int id = world.getBlockId(dx, dy, dz);
					if (id == blockID) {
						ReikaSoundHelper.playBreakSound(world, dx, dy, dz, this);
						world.setBlock(dx, dy, dz, 0);
						world.markBlockForUpdate(dx, dy, dz);
					}
				}
			}
		}
	}

	private ForgeDirection getDirectionFromMeta(int meta) {
		switch (meta) {
		case 0:
			return ForgeDirection.EAST;
		case 2:
			return ForgeDirection.SOUTH;
		default:
			return ForgeDirection.UNKNOWN;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
	{
		this.setBlockBounds(0.33F, 0.33F, 0.33F, 0.67F, 0.67F, 0.67F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;

		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

		switch (meta) {
		case 0:
			maxx = 1;
			minx = 0;
			break;
		case 1:
			maxy = 1;
			miny = 0;
			break;
		case 2:
			maxz = 1;
			minz = 0;
			break;
		case 3:
		case 4:
			maxz = 1;
			maxx = 1;
			minz = 0;
			minx = 0;
			miny = 0;
			maxy = 1;
			break;
		}

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[meta][s];
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 4; j++)
				icons[j][i] = par1IconRegister.registerIcon("RotaryCraft:minepipe");
		for (int i = 0; i < 6; i++)
			icons[4][i] = par1IconRegister.registerIcon("RotaryCraft:minepipe2");
	}
}
