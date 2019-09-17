/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityMobRadar;

public class LuaGetMobs extends LuaMethod {

	public LuaGetMobs() {
		super("getMobs", TileEntityMobRadar.class);
	}

	@Override
	protected Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		List<EntityLivingBase> li = ((TileEntityMobRadar)te).getEntities();
		ArrayList<Object[]> entities = new ArrayList();
		for (EntityLivingBase e : li) {
			entities.add(this.encodeEntity(e));
		}
		return entities.toArray(new Object[entities.size()]);
	}

	private Object[] encodeEntity(EntityLivingBase e) {
		ArrayList<Object> params = new ArrayList();
		params.add(e instanceof EntityPlayer ? "EntityPlayer" : e.getClass().getSimpleName());
		params.add(e.posX);
		params.add(e.posY);
		params.add(e.posZ);
		params.add(e.getUniqueID());
		params.add(e.getHealth()*100F/e.getMaxHealth());
		if (e instanceof EntityCreeper) {
			params.add(ReikaEntityHelper.getCreeperFuse((EntityCreeper)e));
			params.add(ReikaEntityHelper.isCreeperCharged((EntityCreeper)e));
		}
		if (e instanceof EntitySkeleton) {
			params.add(((EntitySkeleton)e).getSkeletonType());
		}
		if (e instanceof EntitySlime) {
			params.add(((EntitySlime)e).getSlimeSize());
		}
		if (e instanceof EntityPigZombie) {
			params.add(ReikaEntityHelper.isPigZombieAngry((EntityPigZombie)e));
		}
		if (e instanceof EntityVillager) {
			params.add(((EntityVillager)e).getProfession());
		}
		if (e instanceof EntityEnderman) {
			params.add(Block.blockRegistry.getNameForObject(((EntityEnderman)e).func_146080_bZ()));
			params.add(((EntityEnderman)e).getCarryingData());
		}
		return params.toArray(new Object[params.size()]);
	}

	@Override
	public String getDocumentation() {
		return "Returns the list of mobs in range around a radar.";
	}

	@Override
	public String getArgsAsString() {
		return "";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.ARRAY;
	}

}
