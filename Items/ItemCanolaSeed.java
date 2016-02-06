/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Registry.BlockRegistry;

public class ItemCanolaSeed extends ItemBasic implements IPlantable {

	public ItemCanolaSeed(int tex) {
		super(tex);
		this.setMaxDamage(0);
		hasSubtypes = true;
	}

	@Override
	public boolean onItemUse(ItemStack items, EntityPlayer player, World world, int x, int y, int z, int side, float a, float b, float c) {
		if (items.getItemDamage() > 1)
			return false;
		boolean spread = items.getItemDamage() == 1;
		if (!ReikaWorldHelper.softBlocks(world, x, y, z)) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
		}
		boolean flag = false;
		if (ReikaWorldHelper.softBlocks(world, x, y, z) && BlockCanola.isValidFarmBlock(world, x, y-1, z)) {
			int minx = spread ? x-1 : x;
			int maxx = spread ? x+1 : x;
			int minz = spread ? z-1 : z;
			int maxz = spread ? z+1 : z;
			for (int xi = minx; xi <= maxx; xi++) {
				for (int zi = minz; zi <= maxz; zi++) {
					if ((!ReikaWorldHelper.softBlocks(world.getBlock(xi, y, zi))) || !BlockCanola.isValidFarmBlock(world, xi, y-1, zi)) {
						ReikaItemHelper.dropItem(world, xi+0.5, y+0.5, zi+0.5, ItemStacks.canolaSeeds);
					}
					else if (!player.canPlayerEdit(xi, y, zi, 0, items)) {
						ReikaItemHelper.dropItem(world, xi+0.5, y+0.5, zi+0.5, ItemStacks.canolaSeeds);
					}
					else {
						world.setBlock(xi, y, zi, BlockRegistry.CANOLA.getBlockInstance());
						flag = true;
					}
				}
			}
			if (!player.capabilities.isCreativeMode) {
				--items.stackSize;
			}
		}
		return flag;
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		return 80+item.getItemDamage();
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return BlockRegistry.CANOLA.getBlockInstance();
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return 0;
	}
}
