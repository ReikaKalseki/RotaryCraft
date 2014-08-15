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

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemIOGoggles extends ItemRotaryArmor {

	public ItemIOGoggles(int tex, int render) {
		super(RotaryCraft.IOGM, render, 0, tex);
		this.setNoRepair();
	}

	@Override
	public void onArmorTick(World world, EntityPlayer ep, ItemStack is) {/*
		int x = (int)ep.posX;
		int y = (int)ep.posY;
		int z = (int)ep.posZ;
		for (int i = -6; i <= 6; i++) {
			for (int j = -6; j <= 6; j++) {
				for (int k = -6; k <= 6; k++) {
					TileEntity te = world.getTileEntity(x+i, y+j, z+k);
					if (te instanceof TileEntityIOMachine) {
						TileEntityIOMachine io = (TileEntityIOMachine)te;
						io.iotick = 512;
					}
					else if (te instanceof ShaftMachine) {
						ShaftMachine sm = (ShaftMachine)te;
						sm.setIORenderAlpha(512);
					}
				}
			}
		}*/
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity par3Entity, int par4, boolean par5) {}
	/*
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity e, int slot, String nulll) {
		return "/Reika/RotaryCraft/Textures/Misc/IOGoggles.png";
	}*/

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