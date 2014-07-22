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

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNightVisionGoggles extends ItemChargedArmor {

	public ItemNightVisionGoggles(int ID, int tex, int render) {
		super(ID, RotaryCraft.NVGM, render, 0, tex);
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {
		if (is.getItemDamage() > 0) {
			ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 0));
			if (itemRand.nextInt(160) == 0) {
				ep.setCurrentItemOrArmor(4, new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1));
				this.warnCharge(is);
			}
			ReikaEntityHelper.setNoPotionParticles(ep);
		}
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity e, int par4, boolean par5) {
		super.onUpdate(is, par2World, e, par4, par5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		par3List.add(new ItemStack(par1, 1, 8192));
	}
	/*
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity e, int slot, String nulll) {
		return "/Reika/RotaryCraft/Textures/Misc/NVGoggles.png";
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
