/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.src.ModLoader;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.Base.ItemChargedTool;

public class ItemMotionTracker extends ItemChargedTool {

	public int rendertick = 0;
	public List inrange;

	double lastdist;
	String lastmobname;

	public ItemMotionTracker(int itemID) {
		super(itemID, 144);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		is.setItemDamage(30);
		if (is.getItemDamage() <= 0) {
			return is;
		}
		ReikaChatHelper.clearChat(); //clr
		rendertick = 512;
		for (float i = 1; i <= 128; i += 0.5) {
			Vec3 look = ep.getLookVec();
			double dx = ep.posX;
			double dy = ep.posY+ep.getEyeHeight();
			double dz = ep.posZ;
			//ReikaChatHelper.writeString(String.format("%.3f", look.xCoord)+" "+String.format("%.3f", look.yCoord)+" "+String.format("%.3f", look.zCoord));
			look.xCoord *= i;
			look.yCoord *= i;
			look.zCoord *= i;
			AxisAlignedBB fov = AxisAlignedBB.getBoundingBox(dx+look.xCoord-0.5, dy+look.yCoord-0.5, dz+look.zCoord-0.5, dx+look.xCoord+0.5, dy+look.yCoord+0.5, dz+look.zCoord+0.5);
			List infov = world.getEntitiesWithinAABB(EntityLiving.class, fov);
			if (infov.size() > 0) {
				String mob;
				if (infov.size() > 1)
					mob = "Mobs";
				else
					mob = "Mob";
				if (world.isRemote)
					return is;
				//ReikaChatHelper.write(infov.size()+String.format(" %s", mob)+" Detected:");
				for (int k = 0; k < infov.size(); k++) {
					EntityLiving ent = (EntityLiving)infov.get(k);
					double dist = ReikaMathLibrary.py3d(dx-ent.posX, dy-ent.posY-ent.getEyeHeight(), dz-ent.posZ);
					String color;
					String mobname = ent.getEntityName();
					if (ent instanceof EntityMob || ent instanceof EntitySlime || ent instanceof EntityGhast)
						color = "\u00A7c";
					else if (ent instanceof EntityAnimal || ent instanceof EntityBat || ent instanceof EntitySquid)
						color = "\u00A7a";
					else
						color = "\u00A7f";
					if (ent instanceof EntityEnderman || ent instanceof EntityPigZombie)
						color = "\u00A76";
					if (ent instanceof EntityDragon)
						color = "\u00A75";
					if (ent instanceof EntityWither)
						color = "\u00A78";
					if (!(ent instanceof EntityPlayer) && (dist <= 24 || ent instanceof EntityWither || ent instanceof EntityDragon) && !(lastmobname == mobname && dist == lastdist)) {
						ReikaChatHelper.write(String.format("%s", color)+mobname+"\u00A7f"+String.format(" %.2f", dist-1)+"m away.");
						if (ent instanceof EntityDragon) {
							EntityDragon ed = (EntityDragon)ent;
							if (ReikaMathLibrary.approxr(ed.targetX, ep.posX, 4))
								if (ReikaMathLibrary.approxr(ed.targetY, ep.posY, 4))
									if (ReikaMathLibrary.approxr(ed.targetZ, ep.posZ, 4))
										ReikaChatHelper.write("\u00A74Dragon is Attacking!");
						}
						else if (ent instanceof EntityMob) {
							EntityMob em = (EntityMob)ent;
							PathEntity path = em.getNavigator().getPath();
							if (em.getAttackTarget() == ep || em.getAITarget() == ep)
								ReikaChatHelper.write("\u00A74Mob is Attacking!");
						}
					}
					/*double[] vel = ReikaPhysicsHelper.cartesianToPolar(ent.motionX, ent.motionY, ent.motionZ);
					ReikaChatHelper.write(String.format(" %.2f", vel[0])+"  "+String.format(" %.2f", vel[1])+"  "+String.format(" %.2f", vel[2]));
					ReikaChatHelper.write("�4"+String.format(" %.2f", ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz)[0])+"  "+String.format(" %.2f", ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz)[1])+"  "+String.format(" %.2f", ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz)[2]));
					if (vel[1] == ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz)[1]) {
						if (vel[2] == ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz)[2]) {
							if (ent instanceof EntityDragon || ent instanceof EntityWither)
								color = "�4";
							else if (ent instanceof EntityMob)
								color = "�e";
							else
								color = "�f";
							ReikaChatHelper.write(String.format("%s", color)+"Mob is approaching you!");
						}
					}*/

					lastmobname = mobname;
					lastdist = dist;
				}
			}
		}
		return new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1);
	}

}
