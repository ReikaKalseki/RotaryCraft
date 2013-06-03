/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Base.BlockBasic;

public class BlockLightblock extends BlockBasic {

	public Icon icon;

	public BlockLightblock(int ID) {
		super(ID, Material.circuits);	// no pistons, breaks with water
		//this.setBlockUnbreakable();
		this.setResistance(3600000F);
		this.setStepSound(soundGlassFootstep);
		////this.requiresSelfNotify[this.blockID] = true;

		//this.blockIndexInTexture = 33;
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

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}


	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id)
	{
		//ReikaJavaLibrary.pConsole(id);
		if (id != 0) {
			world.setBlock(x, y, z, 0);/*
    		boolean px = true;
    		boolean py = true;
    		boolean pz = true;
    		boolean nx = true;
    		boolean ny = true;
    		boolean nz = true;*/
			int i = 1;
			//(px || py || pz || nx || ny || nz) &&
			while (i <= RotaryConfig.maxlamprange) {
				//if (px)
				world.notifyBlocksOfNeighborChange(x + i, y, z, blockID);
				//ReikaChatHelper.writeBlockAtCoords(world, x+i, y, z);
				//if (nx)
				world.notifyBlocksOfNeighborChange(x - i, y, z, blockID);
				//if (nz)
				world.notifyBlocksOfNeighborChange(x, y, z - i, blockID);
				//if (pz)
				world.notifyBlocksOfNeighborChange(x, y, z + i, blockID);
				//if (ny)
				world.notifyBlocksOfNeighborChange(x, y - i, z, blockID);
				//if (py)
				world.notifyBlocksOfNeighborChange(x, y + i, z, blockID);
				i += 1;
			}
			world.notifyBlocksOfNeighborChange(x, y, z, 0);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random par5) {
		world.setBlockToAir(x, y, z);
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z)
	{
		return true;
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
	 */
	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return false;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 *//*
    public void onBlockAdded(World world, int x, int y, int z) {
    	this.metadata = world.getBlockMetadata(x, y, z);
    	setLightValue(this.metadata/16F);
    }*/

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 0;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icon;
		//return null;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		icon = par1IconRegister.registerIcon("RotaryCraft:trans");
	}
}
