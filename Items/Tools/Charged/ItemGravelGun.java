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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
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

import Reika.DragonAPI.Instantiable.Data.Immutable.DecimalPosition;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.GravelGunDamage;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

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
		Vec3 look = ep.getLookVec();
		for (float i = 1; i <= 128; i += 0.5) {
			DecimalPosition looks = ReikaVectorHelper.getPlayerLookCoords(ep, i);
			AxisAlignedBB fov = looks.getAABB(0.5);
			List<Entity> li = world.getEntitiesWithinAABB(Entity.class, fov);
			ArrayList<Entity> infov = new ArrayList();
			for (Entity e : li) {
				if (!this.isFiringPlayer(e, ep)) {
					if (!ep.equals(e) && this.isEntityAttackable(e) && ReikaWorldHelper.lineOfSight(world, ep, e)) {
						infov.add(e);
					}
				}
			}
			for (Entity ent : infov) {
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
					else if (ent instanceof EntityEnderCrystal) {
						ent.attackEntityFrom(DamageSource.causePlayerDamage(ep), this.getAttackDamage(is.getItemDamage()));
					}
					else {
						EntityLivingBase e = (EntityLivingBase)ent;
						float dmg = this.getAttackDamage(is.getItemDamage());
						if (ent instanceof EntityPlayer) {
							for (int n = 1; n < 5; n++) {
								ItemRegistry ir = ItemRegistry.getEntry(e.getEquipmentInSlot(n));
								if (ir != null) {
									if (ir.isBedrockArmor())
										dmg *= 0.75;
								}
							}
						}
						float pre = e.getHealth();
						ent.attackEntityFrom(new GravelGunDamage(ep), dmg);
						float post = e.getHealth();
						if (post > 0) {
							float newh = Math.min(post, pre-Math.min(10, dmg));
							if (newh <= 0) {
								e.setHealth(0.01F);
								ent.attackEntityFrom(new GravelGunDamage(ep), dmg);
							}
							else {
								e.setHealth(newh);
							}
						}
						if (dmg >= 500)
							RotaryAchievements.MASSIVEHIT.triggerAchievement(ep);
					}
					if (ent instanceof EntityMob && (ent.isDead || ((EntityLivingBase)ent).getHealth() <= 0) && ReikaMathLibrary.py3d(ep.posX-ent.posX, ep.posY-ent.posY, ep.posZ-ent.posZ) >= 80)
						RotaryAchievements.GRAVELGUN.triggerAchievement(ep);
				}
				//ReikaWorldHelper.spawnParticleLine(world, x, y, z, ent.posX, ent.posY+ent.height/2, ent.posZ, "crit", 0, 0, 0, 60);
				for (float t = 0; t < 2; t += 0.05F)
					world.spawnParticle("crit", x, y, z, dx/dist*t, dy/dist*t, dz/dist*t);
			}
			if (infov.size() > 1) {
				RotaryAchievements.DOUBLEKILL.triggerAchievement(ep);
			}
			if (infov.size() > 0) {
				if (!ep.capabilities.isCreativeMode)
					ReikaInventoryHelper.findAndDecrStack(Blocks.gravel, -1, ep.inventory.mainInventory);
				is.setItemDamage(is.getItemDamage()-this.getChargeConsumed(is.getItemDamage()));
				return is;
			}
		}
		return is;
	}

	private boolean isFiringPlayer(Entity e, EntityPlayer ep) {
		return e instanceof EntityPlayer && (e.getCommandSenderName().equals(ep.getCommandSenderName()));
	}

	private boolean isEntityAttackable(Entity ent) {
		if (ent instanceof EntityPlayer && ReikaPlayerAPI.isReika((EntityPlayer)ent))
			return false;
		if (!(ent instanceof EntityLivingBase))
			return ent instanceof EntityEnderCrystal;
		return ConfigRegistry.GRAVELPLAYER.getState() || !(ent instanceof EntityPlayer);
	}

	private int getChargeConsumed(int charge) {
		return Math.max(1, ReikaMathLibrary.logbase2(1+charge));
	}

	private float getAttackDamage(int charge) {
		if (charge <= 0)
			return 0;
		if (charge == 1)
			return 1;
		long pow = ReikaMathLibrary.longpow(charge/2, 3); //fits in long (^6 does not)
		double base = this.getExpBase()+Math.pow(charge, this.getPowR())/150000D;
		return (float)(1+(ReikaMathLibrary.logbase(pow, 2)/2)*ReikaMathLibrary.doubpow(base, charge));
	}

	private double getPowR() {
		return ConfigRegistry.HARDGRAVELGUN.getState() ? 0.15 : 0.1875;
	}

	private double getExpBase() {
		return ConfigRegistry.HARDGRAVELGUN.getState() ? 1.00005 : 1.0001;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		float dmg = this.getAttackDamage(is.getItemDamage());
		String s = dmg > 0 ? String.format("Dealing %.1f hearts of damage per shot", dmg/2F) : "Unable to fire - requires charging";
		li.add(s);
	}

}
