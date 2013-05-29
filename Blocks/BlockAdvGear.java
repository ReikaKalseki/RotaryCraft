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

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAdvGear extends BlockModelledMachine {

	public BlockAdvGear(int blockID, Material mat) {
		super(blockID, mat);
		////this.blockIndexInTexture = 8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs xCreativeTabs, List yList) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < 12; ++var4)
			if (var4%4 == 0)
				yList.add(new ItemStack(par1, 1, var4));
	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityAdvancedGear();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving par5EntityLiving, ItemStack is)		//Directional code
	{
		int prevmeta = world.getBlockMetadata(x, y, z);
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", prevmeta));

		int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;

		switch (i) {
		case 0:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+2);
			break;
		case 1:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+1);
			break;
		case 2:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+3);
			break;
		case 3:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+0);
			break;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int a, int b) {
		TileEntityAdvancedGear adv = (TileEntityAdvancedGear)world.getBlockTileEntity(x, y, z);
		int meta = adv.getBlockMetadata();
		ItemStack todrop = new ItemStack(RotaryCraft.advgearitems.itemID, 1, meta/4);
		EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
		item.delayBeforeCanPickup = 10;
		if (!world.isRemote)
			world.spawnEntityInWorld(item);
		super.breakBlock(world, x, y, z, a, b);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		this.setFullBlockBounds();
		if (iba.getBlockMetadata(x, y, z) >= 8)
			maxY = 0.875;
	}
}
