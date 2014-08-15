/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FreezePotion extends Potion {

	public FreezePotion(int par1) {
		super(par1, true, 0x289EFF);
		this.func_111184_a(SharedMonsterAttributes.movementSpeed, "2532FA5D-7CC8-4440-140E-514A1A162299", -10, 2);
	}

	@Override
	public void performEffect(EntityLivingBase e, int level) {
		e.addPotionEffect(new PotionEffect(Potion.jump.id, 20, -30));
		e.fallDistance = 0;
	}

	@Override
	public String getName()
	{
		return "Frozen Solid";
	}

	@Override
	public boolean isReady(int time, int amp)
	{
		return true;
	}
}