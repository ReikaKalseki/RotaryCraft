/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.RotaryCraft.Base.ItemChargedTool;

import forestry.api.arboriculture.IToolGrafter;

@Strippable(value = {"forestry.api.arboriculture.IToolGrafter"})
public class ItemChargedGrafter extends ItemChargedTool implements IToolGrafter {

	public ItemChargedGrafter(int index) {
		super(index);
	}

	@Override
	public float getSaplingModifier(ItemStack stack, World world, EntityPlayer player, int x, int y, int z) {
		return this.getChanceFromCharge(stack.getItemDamage());
	}
	/*
	@Override
	public boolean onBlockDestroyed(ItemStack is, World world, Block blockID, int x, int y, int z, EntityLivingBase e)
	{
		if (is.getItemDamage() > 0 && e instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer)e;
			Block b = blockID;
			if (b.getMaterial() == Material.leaves) {
				is.setItemDamage(is.getItemDamage()-1);
				int r = 3;
				for (int i = -r; i <= r; i++) {
					for (int j = -r; j <= r; j++) {
						for (int k = -r; k <= r; k++) {
							int dx = x+i;
							int dy = y+j;
							int dz = z+k;
							Block b2 = world.getBlock(dx, dy, dz);
							if (b2 != null && b2.getMaterial() == Material.leaves) {
								b2.dropBlockAsItem(world, dx, dy, dz, world.getBlockMetadata(dx, dy, dz), 0);
								b2.removedByPlayer(world, ep, dx, dy, dz);
								is.setItemDamage(is.getItemDamage()-1);
							}
						}
					}
				}
				e.setCurrentItemOrArmor(0, is);
				return true;
			}
		}
		return false;
	}
	 */
	private float getChanceFromCharge(int charge) {
		if (charge < 8)
			return charge;
		if (charge > 4096)
			return 100;
		return (int)(10*ReikaMathLibrary.logbase(charge, 2))-20;
	}

	@Override
	public float getDigSpeed(ItemStack is, Block b, int meta) {
		if (b == null)
			return 0;
		if (ForestryHandler.BlockEntry.LEAF.getBlock() == b)
			return 30;
		return super.getDigSpeed(is, b, meta);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		return is;
	}

}
