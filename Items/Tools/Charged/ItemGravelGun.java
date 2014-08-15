/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Charged;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGravelGun extends ItemChargedTool {

	public ItemGravelGun(int tex) {
		super(tex);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (is.getItemDamage() <= 0) {
			this.noCharge();
			return is;
		}
		this.warnCharge(is);
		if (!ReikaPlayerAPI.playerHasOrIsCreative(ep, Blocks.gravel, -1)) {
			if (!world.isRemote)
				world.playAuxSFX(1001, (int)ep.posX, (int)ep.posY, (int)ep.posZ, 1);
			return is;
		}
		for (float i = 1; i <= 128; i += 0.5) {
			Vec3 look = ep.getLookVec();
			double[] looks = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			AxisAlignedBB fov = AxisAlignedBB.getBoundingBox(looks[0]-0.5, looks[1]-0.5, looks[2]-0.5, looks[0]+0.5, looks[1]+0.5, looks[2]+0.5);
			List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, fov);
			ArrayList<EntityLivingBase> infov = new ArrayList();
			for (int k = 0; k < li.size(); k++) {
				EntityLivingBase e = li.get(k);
				if (!this.isFiringPlayer(e, ep))
					infov.add(e);
			}
			for (int k = 0; k < infov.size(); k++) {
				EntityLivingBase ent = infov.get(k);
				if (!ep.equals(ent) && this.isEntityAttackable(ent) && ReikaWorldHelper.lineOfSight(world, ep, ent)) {
					double dist = ReikaMathLibrary.py3d(ep.posX-ent.posX, ep.posY-ent.posY, ep.posZ-ent.posZ);
					double x = ep.posX+look.xCoord;
					double y = ep.posY+ep.getEyeHeight()+look.yCoord;
					double z = ep.posZ+look.zCoord;
					double dx = ent.posX-ep.posX;
					double dy = ent.posY-ep.posY;
					double dz = ent.posZ-ep.posZ;
					if (!world.isRemote) {
						ItemStack fl = new ItemStack(Items.flint);
						EntityItem ei = new EntityItem(world, look.xCoord/look.lengthVector()+ep.posX, look.yCoord/look.lengthVector()+ep.posY, look.zCoord/look.lengthVector()+ep.posZ, fl);
						ei.delayBeforeCanPickup = 100;
						ei.motionX = dx;
						ei.motionY = dy+1;
						ei.motionZ = dz;
						//ReikaChatHelper.writeCoords(world, ei.posX, ei.posY, ei.posZ);
						ei.velocityChanged = true;
						world.playSoundAtEntity(ep, "dig.gravel", 1.5F, 2F);
						ei.lifespan = 5;
						world.spawnEntityInWorld(ei);

						if (is.getItemDamage() > 4096) { //approx the 1-hit kill of a 10-heart mob
							//ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.GRAVELGUN.getMinValue(), world, (int)ent.posX, (int)ent.posY, (int)ent.posZ);
							//world.playSoundAtEntity(ep, "random.explode", 0.25F, 1F);
						}
						if (ent instanceof EntityDragon) {
							EntityDragon ed = (EntityDragon)ent;
							ed.attackEntityFromPart(ed.dragonPartBody, DamageSource.causePlayerDamage(ep), this.getAttackDamage(is.getItemDamage()));
						}
						else {
							int dmg = this.getAttackDamage(is.getItemDamage());
							if (ent instanceof EntityPlayer) {
								for (int n = 1; n < 5; n++) {
									ItemRegistry ir = ItemRegistry.getEntry(ent.getEquipmentInSlot(n));
									if (ir != null) {
										if (ir.isBedrockArmor())
											dmg *= 0.75;
									}
								}
							}
							ent.attackEntityFrom(DamageSource.causePlayerDamage(ep), dmg);
							if (dmg >= 500)
								RotaryAchievements.MASSIVEHIT.triggerAchievement(ep);
						}
						if (ent instanceof EntityMob && (ent.isDead || ent.getHealth() <= 0) && ReikaMathLibrary.py3d(ep.posX-ent.posX, ep.posY-ent.posY, ep.posZ-ent.posZ) >= 80)
							RotaryAchievements.GRAVELGUN.triggerAchievement(ep);
					}
					//ReikaWorldHelper.spawnParticleLine(world, x, y, z, ent.posX, ent.posY+ent.height/2, ent.posZ, "crit", 0, 0, 0, 60);
					for (float t = 0; t < 2; t += 0.05F)
						world.spawnParticle("crit", x, y, z, dx/dist*t, dy/dist*t, dz/dist*t);
				}
			}
			if (infov.size() > 1) {
				RotaryAchievements.DOUBLEKILL.triggerAchievement(ep);
			}
			if (infov.size() > 0) {
				if (!ep.capabilities.isCreativeMode)
					ReikaInventoryHelper.findAndDecrStack(Blocks.gravel, -1, ep.inventory.mainInventory);
				return new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-1);
			}
		}
		return is;
	}

	private boolean isFiringPlayer(EntityLivingBase e, EntityPlayer ep) {
		return e instanceof EntityPlayer && (e.getCommandSenderName().equals(ep.getCommandSenderName()));
	}

	private boolean isEntityAttackable(EntityLivingBase ent) {
		if ("Reika_Kalseki".equals(ent.getCommandSenderName()))
			return false;
		return ConfigRegistry.GRAVELPLAYER.getState() || !(ent instanceof EntityPlayer);
	}

	private void fire(ItemStack is, World world, EntityPlayer ep, Entity ent) {
		Vec3 look = ep.getLookVec();
		double[] looks = ReikaVectorHelper.getPlayerLookCoords(ep, 2);
		if (!(ent instanceof EntityPlayer) && ReikaWorldHelper.lineOfSight(world, ep, ent)) {
			ItemStack fl = new ItemStack(Items.flint, 0, 0);
			EntityItem ei = new EntityItem(world, looks[0]/look.lengthVector(), looks[1]/look.lengthVector(), looks[2]/look.lengthVector(), fl);
			ei.delayBeforeCanPickup = 100;
			ei.motionX = look.xCoord/look.lengthVector();
			ei.motionY = look.yCoord/look.lengthVector();
			ei.motionZ = look.zCoord/look.lengthVector();
			if (!world.isRemote)
				ei.velocityChanged = true;
			if (!world.isRemote)
				world.playSoundAtEntity(ep, "dig.gravel", 1.5F, 2F);
			world.spawnEntityInWorld(ei);
			if (is.getItemDamage() > 4096) { //approx the 1-hit kill of a 10-heart mob
				ReikaParticleHelper.EXPLODE.spawnAt(world, ent.posX, ent.posY, ent.posZ);
				world.playSoundAtEntity(ent, "random.explode", 1, 1);
			}
			ent.attackEntityFrom(DamageSource.causePlayerDamage(ep), this.getAttackDamage(is.getItemDamage()));
			ReikaEntityHelper.knockbackEntity(ep, ent, 0.4);
			//ent.setRevengeTarget(ep);
		}
	}

	private int getAttackDamage(int charge) {
		long pow = ReikaMathLibrary.longpow(charge/2, 3); //fits in long (^6 does not)
		double base = 1.0001+Math.pow(charge, 0.1875)/150000D;
		return (int)(1+(ReikaMathLibrary.logbase(pow, 2)/2)*ReikaMathLibrary.doubpow(base, charge));
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		int dmg = this.getAttackDamage(is.getItemDamage());
		li.add(String.format("Dealing %.1f hearts of damage per shot", dmg/2F));
	}

}