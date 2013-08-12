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

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.TileEntities.TileEntityDeadMachine;

public class BlockDeadMachine extends BlockContainer {

	public BlockDeadMachine(int ID, Material mat) {
		super(ID, mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDeadMachine();
	}
	/*
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
		TileEntityDeadMachine te = ((TileEntityDeadMachine)world.getBlockTileEntity(x, y, z));
		if (te != null)
			return te.getDrops(fortune);
		else {
			ReikaJavaLibrary.pConsole("NULL on "+FMLCommonHandler.instance().getEffectiveSide());
			return null;
		}
	}*/

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (!player.capabilities.isCreativeMode)
			this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlock(x, y, z, 0);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		TileEntityDeadMachine dead = (TileEntityDeadMachine)world.getBlockTileEntity(x, y, z);
		ItemStack is = ep.getHeldItem();
		int fortune;
		if (is == null)
			fortune = 0;
		else
			fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
		if (dead != null) {
			ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, dead.getDrops(fortune));
		}
	}

}
