/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;

public class ItemStunGun extends ItemChargedTool {

	public ItemStunGun(int itemID) {
		super(itemID, 192);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.getItemDamage() <= 0) {
			this.noCharge();
			return is;
		}
		this.warnCharge(is);
		//if (!world.isRemote) {
		double[] part = ReikaVectorHelper.getPlayerLookCoords(ep, 1);
		for (int i = 0; i < 12; i++)
			world.spawnParticle("magicCrit", part[0]-0.3+0.6*par5Random.nextFloat(), part[1]-0.3+0.6*par5Random.nextFloat(), part[2]-0.3+0.6*par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
		//}
		world.playSoundAtEntity(ep, "Reika.RotaryCraft.knockback", 2, 2F);
		if (ep.isSneaking()) {
			if (world.isRemote)
				return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-2);/*
			for (float i = 0; i <= 5; i += 0.5) {
				double[] look = ReikaVectorHelper.getPlayerLookCoords(ep, i);
				int x = (int)look[0];
				int y = (int)look[1];
				int z = (int)look[2];
				int id = world.getBlockId(x, y, z);

				if (id != 0 && (id < 8 || id > 11) && (id == Block.web.blockID || id == Block.mushroomRed.blockID ||
				id == Block.gravel.blockID ||  id == Block.silverfish.blockID  || id == Block.mushroomBrown.blockID ||
					id == Block.waterlily.blockID || id == Block.flowerPot.blockID ||
					ReikaWorldHelper.isOre(id) || ReikaWorldHelper.softBlocks(id))) {
					for (int k = 0; i < 64; i++)
						world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaWorldHelper.recursiveBreak(world, x, y, z, id, -1);
					return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-2);
				}
				int leafrange = 4;
				if (id == Block.leaves.blockID || id == Block.sand.blockID) {
					for (int k = 0; i < 64; i++)
						world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y, z, id, -1, x, y, z, leafrange);
					return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-2);
				}
			}*/
			MovingObjectPosition mov = ReikaPlayerAPI.getLookedAtBlock(5);
			//ReikaChatHelper.write(mov);
			if (mov != null) {
				int x = mov.blockX;
				int y = mov.blockY;
				int z = mov.blockZ;
				//ReikaChatHelper.writeBlockAtCoords(world, x, y, z);
				int id = world.getBlockId(x, y, z);
				if (id != 0 && (id < 8 || id > 11) && (id == Block.web.blockID || id == Block.mushroomRed.blockID ||
						id == Block.gravel.blockID ||  id == Block.silverfish.blockID  || id == Block.mushroomBrown.blockID ||
						id == Block.waterlily.blockID || id == Block.flowerPot.blockID ||
						ReikaBlockHelper.isOre(id) || (ReikaWorldHelper.softBlocks(id) && id != Block.snow.blockID))) {
					for (int k = 0; k < 64; k++)
						world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaWorldHelper.recursiveBreak(world, x, y, z, id, -1);
					return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-2);
				}
				int leafrange = 4;
				if (id == Block.leaves.blockID || id == Block.sand.blockID || id == Block.snow.blockID) {
					for (int k = 0; k < 64; k++)
						world.spawnParticle("magicCrit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y, z, id, -1, x, y, z, leafrange);
					return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-2);
				}
			}
			return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-2);
		}
		for (float i = 1; i <= 5; i += 0.5) {
			double[] look = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			//ReikaChatHelper.writeString(String.format("%.3f", look.xCoord)+" "+String.format("%.3f", look.yCoord)+" "+String.format("%.3f", look.zCoord));
			AxisAlignedBB fov = AxisAlignedBB.getBoundingBox(look[0]-0.5, look[1]-0.5, look[2]-0.5, look[0]+0.5, look[1]+0.5, look[2]+0.5);
			List infov = world.getEntitiesWithinAABB(EntityLiving.class, fov);
			for (int k = 0; k < infov.size(); k++) {
				EntityLiving ent = (EntityLiving)infov.get(k);
				if (!(ent instanceof EntityPlayer) && ep.canEntityBeSeen(ent)) {
					for (int f = 0; i < 64; i++)
						world.spawnParticle("magicCrit", ent.posX-0.5+par5Random.nextFloat(), ent.posY-0.5+par5Random.nextFloat(), ent.posZ-0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					ReikaEntityHelper.knockbackEntity(ep, ent, 2);
				}
			}
			if (infov.size() > 0 && !(infov.size() == 1 && infov.get(0) instanceof EntityPlayer))
				return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
		}
		return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
	}

}
