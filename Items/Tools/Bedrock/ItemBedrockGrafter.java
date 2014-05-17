/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import forestry.api.arboriculture.IToolGrafter;

public class ItemBedrockGrafter extends ItemRotaryTool implements IToolGrafter {

	public ItemBedrockGrafter(int ID, int index) {
		super(ID, index);
	}

	@Override
	public float getSaplingModifier(ItemStack stack, World world, EntityPlayer player, int x, int y, int z) {
		return 100;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World world, int blockID, int x, int y, int z, EntityLivingBase e)
	{
		if (e instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer)e;
			Block b = Block.blocksList[blockID];
			if (b.blockMaterial == Material.leaves) {
				int r = 4;
				for (int i = -r; i <= r; i++) {
					for (int j = -r; j <= r; j++) {
						for (int k = -r; k <= r; k++) {
							int dx = x+i;
							int dy = y+j;
							int dz = z+k;
							Block b2 = Block.blocksList[world.getBlockId(dx, dy, dz)];
							if (b2 != null && b2.blockMaterial == Material.leaves) {
								b2.dropBlockAsItem(world, dx, dy, dz, world.getBlockMetadata(dx, dy, dz), 1);
								b2.removeBlockByPlayer(world, ep, dx, dy, dz);
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}

}
