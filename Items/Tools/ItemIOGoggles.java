/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;

public class ItemIOGoggles extends ItemRotaryArmor {

	public static final String NBT_KEY = "iooverlay";

	public ItemIOGoggles(int tex, int render) {
		super(RotaryCraft.IOGM, render, 0, tex);
		this.setNoRepair();
	}

	@Override
	public void onArmorTick(World world, EntityPlayer ep, ItemStack is) {
		ep.getEntityData().setLong(NBT_KEY, world.getTotalWorldTime());
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity par3Entity, int par4, boolean par5) {

	}

	@Override
	public boolean providesProtection() {
		return false;
	}

	@Override
	public boolean canBeDamaged() {
		return false;
	}

	@Override
	public double getDamageMultiplier(DamageSource src) {
		return 1;
	}
}
