/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;

public class ItemNightVisionHelmet extends ItemRotaryArmor {

	public ItemNightVisionHelmet(int ID, int tex, int render) {
		super(ID, RotaryCraft.NVHM, render, 0, tex);
		//this.setNoRepair();
		//this.damageReduceAmount = EnumArmorMaterial.DIAMOND.getDamageReductionAmount(0);
		//this.setMaxDamage((int)(1.2*EnumArmorMaterial.DIAMOND.getDurability(0)));
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {
		ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 0));
		ReikaEntityHelper.setNoPotionParticles(ep);
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity par3Entity, int par4, boolean par5) {}
	/*
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity e, int slot, String nulll) {
		return "/Reika/RotaryCraft/Textures/Misc/NVHelmet.png";
	}*/

	@Override
	public boolean providesProtection() {
		return true;
	}

	@Override
	public boolean canBeDamaged() {
		return true;
	}

	@Override
	public double getDamageMultiplier(DamageSource src) {
		return 1;
	}

}
