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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;

public class ItemGravelGun extends ItemChargedTool {

	public ItemGravelGun(int itemID) {
		super(itemID, 176);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		is.setItemDamage(30000);
		if (is.getItemDamage() <= 0) {
			return is;
		}
		if (!ReikaInventoryHelper.playerHasOrIsCreative(ep, Block.gravel.blockID, -1)) {
			if (!world.isRemote)
				world.playAuxSFX(1001, (int)ep.posX, (int)ep.posY, (int)ep.posZ, 1);
			return is;
		}/*
		MovingObjectPosition mov = ModLoader.getMinecraftInstance().thePlayer.rayTrace(128, 1);
		if (mov != null) {
			if (mov.typeOfHit == EnumMovingObjectType.ENTITY) {
				Entity ent = mov.entityHit;
				if (ent instanceof EntityLiving)
					this.fire(is, world, ep, (EntityLiving)ent);
			}
		}*/
		for (float i = 1; i <= 128; i += 0.5) {
			Vec3 look = ep.getLookVec();
			double[] looks = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			AxisAlignedBB fov = AxisAlignedBB.getBoundingBox(looks[0]-0.5, looks[1]-0.5, looks[2]-0.5, looks[0]+0.5, looks[1]+0.5, looks[2]+0.5);
			List infov = world.getEntitiesWithinAABB(EntityLiving.class, fov);
			for (int k = 0; k < infov.size(); k++) {
				EntityLiving ent = (EntityLiving)infov.get(k);
				if (!(ent instanceof EntityPlayer) && ReikaWorldHelper.lineOfSight(world, ep, ent)) {
					double dist = ReikaMathLibrary.py3d(ep.posX-ent.posX, ep.posY-ent.posY, ep.posZ-ent.posZ);
					ItemStack flint = new ItemStack(Item.flint.itemID, 0, 0);
					EntityItem ei = new EntityItem(world, look.xCoord/look.lengthVector()+ep.posX, look.yCoord/look.lengthVector()+ep.posY, look.zCoord/look.lengthVector()+ep.posZ, flint);
					ei.delayBeforeCanPickup = 100;
					ei.motionX = look.xCoord/look.lengthVector()*dist/5;
					ei.motionY = look.yCoord/look.lengthVector()*dist/5;
					ei.motionZ = look.zCoord/look.lengthVector()*dist/5;
					//ReikaChatHelper.writeCoords(world, ei.posX, ei.posY, ei.posZ);
					if (!world.isRemote) {
						ei.velocityChanged = true;
						world.playSoundAtEntity(ep, "dig.gravel", 1.5F, 2F);
						world.spawnEntityInWorld(ei);
					}
					ent.attackEntityFrom(DamageSource.causePlayerDamage(ep), this.getDamage(is));
					//ReikaEntityHelper.knockbackEntity(ep, ent, 0.4);
					//ent.setRevengeTarget(ep);
				}
			}
			if (infov.size() > 0 && !(infov.size() == 1 && infov.get(0) instanceof EntityPlayer)) {
				if (!ep.capabilities.isCreativeMode)
					ReikaInventoryHelper.findAndDecrStack(Block.gravel.blockID, -1, ep.inventory.mainInventory);
				return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
			}
		}
		return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
	}

	private void fire(ItemStack is, World world, EntityPlayer ep, Entity ent) {
		Vec3 look = ep.getLookVec();
		double[] looks = ReikaVectorHelper.getPlayerLookCoords(ep, 2);
		if (!(ent instanceof EntityPlayer) && ReikaWorldHelper.lineOfSight(world, ep, ent)) {
			ItemStack flint = new ItemStack(Item.flint.itemID, 0, 0);
			EntityItem ei = new EntityItem(world, looks[0]/look.lengthVector(), looks[1]/look.lengthVector(), looks[2]/look.lengthVector(), flint);
			ei.delayBeforeCanPickup = 100;
			ei.motionX = look.xCoord/look.lengthVector();
			ei.motionY = look.yCoord/look.lengthVector();
			ei.motionZ = look.zCoord/look.lengthVector();
			if (!world.isRemote)
				ei.velocityChanged = true;
			if (!world.isRemote)
				world.playSoundAtEntity(ep, "dig.gravel", 1.5F, 2F);
			world.spawnEntityInWorld(ei);
			ent.attackEntityFrom(DamageSource.causePlayerDamage(ep), this.getDamage(is));
			ReikaEntityHelper.knockbackEntity(ep, ent, 0.4);
			//ent.setRevengeTarget(ep);
		}
	}

	private int getDamage(ItemStack is) {
		if (is == null)
			return 0;
		double pow = 2+ReikaMathLibrary.intpow(is.getItemDamage()/2, 6);
		return (int)((ReikaMathLibrary.logbase(pow, 2)/4)*ReikaMathLibrary.doubpow(1.001, is.getItemDamage()));
	}

}
