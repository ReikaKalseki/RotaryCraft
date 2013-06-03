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

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockGravity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGravLog extends BlockGravity implements SidedTextureIndex {

	public BlockGravLog(int ID) {
		super(ID, Material.wood);
		this.setHardness(2F);
		this.setResistance(2F);
		this.setLightValue(0F);
		this.setBurnProperties(blockID, 10, 10); //2x
		this.setStepSound(soundWoodFootstep);


		//this.blockIndexInTexture = 20;
		//setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < 4; ++var4)
			par3List.add(new ItemStack(par1, 1, var4));
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	/*
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		if ((side == 1 || side == 0) && meta < 4)
			return //this.blockIndexInTexture+1;
		if ((side == 4 || side == 5) && meta >= 4 && meta < 8)
			return //this.blockIndexInTexture+1;
		if ((side == 2 || side == 3) && meta >= 8 && meta < 12)
			return //this.blockIndexInTexture+1;
    	switch (meta) {
    	case 1:
    	case 5:
    	case 9:
    		return 116;
    	case 2:
    	case 6:
    	case 10:
    		return 117;
    	case 3:
    	case 7:
    	case 11:
    		return 153;
    	}
    	return //this.blockIndexInTexture;
    }
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		this.propagate(world, x, y, z);

		super.onBlockAdded(world, x, y, z);
		//this.metadata = world.getBlockMetadata(x, y, z);
	}

	public void propagate(World world, int x, int y, int z) {
		if (world.getBlockId(x, y-1, z) == Block.vine.blockID)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y-1, z, 0);

		if (world.getBlockId(x+1, y, z) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x-1, y, z) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x-1, y, z) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x, y+1, z) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y+1, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y+1, z, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x, y+1, z) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y+1, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y+1, z, RotaryCraft.gravleaves.blockID, meta);
		}
		/*	Do not propagate downwards - to avoid affecting an entire biome, especially jungle
    	if (world.getBlockId(x, y-1, z) == Block.wood.blockID) {
    		int meta = world.getBlockMetadata(x, y-1, z);
    		ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y-1, z, RotaryCraft.gravlog.blockID, meta);
    	}
    	if (world.getBlockId(x, y-1, z) == Block.leaves.blockID) {
    		int meta = world.getBlockMetadata(x, y-1, z);
    		ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y-1, z, RotaryCraft.gravleaves.blockID, meta);
    	}*/

		if (world.getBlockId(x, y, z+1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z+1, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x, y, z+1) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z+1, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x, y, z-1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z-1, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x, y, z-1) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z-1, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x-1, y, z-1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z-1, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x-1, y, z-1) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z-1, RotaryCraft.gravleaves.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z-1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z-1, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z-1) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z-1, RotaryCraft.gravleaves.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z+1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z+1, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z+1) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z+1, RotaryCraft.gravleaves.blockID, meta);
		}
		if (world.getBlockId(x-1, y, z+1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z+1, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x-1, y, z+1) == Block.leaves.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z+1, RotaryCraft.gravleaves.blockID, meta);
		}
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int meta, int a) {
		this.propagate(world, x, y, z);
		super.breakBlock(world, x, y, z, meta, a);
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[meta][s];
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		for (int i = 2; i < 6; i++) {
			icons[0][i] = par1IconRegister.registerIcon("RotaryCraft:log_oak");
			icons[1][i] = par1IconRegister.registerIcon("RotaryCraft:log_spruce");
			icons[2][i] = par1IconRegister.registerIcon("RotaryCraft:log_birch");
			icons[3][i] = par1IconRegister.registerIcon("RotaryCraft:log_jungle");
		}
		for (int i = 0; i < 4; i++) {
			icons[i][0] = par1IconRegister.registerIcon("RotaryCraft:log_top");
			icons[i][1] = par1IconRegister.registerIcon("RotaryCraft:log_top");
		}
	}

	/*
	@Override
	public String getTextureFile(){
		return "/Reika/RotaryCraft/textures.png"; //return the block texture where the block texture is saved in
	}*/
}
