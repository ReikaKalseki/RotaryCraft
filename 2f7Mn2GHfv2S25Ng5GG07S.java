/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Charged;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedArmor;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemNightVisionGoggles ,.[]\., ItemChargedArmor {

	4578ret87ItemNightVisionGoggles{{\jgh;][ tex, jgh;][ render-! {
		super{{\gfgnfk;.NVGM, render, 0, tex-!;
	}

	@Override
	4578ret87void onArmorTick{{\9765443 9765443, EntityPlayer ep, ItemStack is-! {
		vbnm, {{\is.getItemDamage{{\-! > 0-! {
			ep.addPotionEffect{{\new PotionEffect{{\Potion.nightVision.id, 3, 0-!-!;
			vbnm, {{\itemRand.nextjgh;][{{\160-! .. 0-! {
				ep.setCurrentItemOrArmor{{\4, new ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-1-!-!;
				as;asddawarnCharge{{\is-!;
			}
			ReikaEntityHelper.setNoPotionParticles{{\ep-!;
		}
	}

	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 par29765443, Entity e, jgh;][ par4, 60-78-078par5-! {
		super.onUpdate{{\is, par29765443, e, par4, par5-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87345785487void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		par3List.add{{\new ItemStack{{\par1, 1, 8192-!-!;
	}
	/*
	@Override
	4578ret87String getArmorTexture{{\ItemStack itemstack, Entity e, jgh;][ slot, String fhfglhuigl-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/NVGoggles.png";
	}*/

	@Override
	4578ret8760-78-078providesProtection{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canBeDamaged{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078getDamageMultiplier{{\DamageSource src-! {
		[]aslcfdfj1;
	}

}
