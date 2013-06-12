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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.BlockModelledMultiTE;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class BlockMIMachine extends BlockModelledMultiTE {

	public BlockMIMachine(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.SMOKEDETECTOR) {
			int upid = world.getBlockId(x, y+1, z);
			if (upid == 0) {
				world.setBlockToAir(x, y, z);
				ItemStack is = MachineRegistry.SMOKEDETECTOR.getCraftedProduct();
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityItem(world, x, y, z, is));
			}
			else if (!Block.blocksList[upid].isOpaqueCube()) {
				world.setBlockToAir(x, y, z);
				ItemStack is = MachineRegistry.SMOKEDETECTOR.getCraftedProduct();
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityItem(world, x, y, z, is));
			}
		}
	}
}
