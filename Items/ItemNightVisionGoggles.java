/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;

public class ItemNightVisionGoggles extends ItemRotaryArmor implements IArmorTextureProvider {

	public ItemNightVisionGoggles(int itemID, int texID) {
		super(itemID, RotaryCraft.NVGM, texID, 0, 97);
		this.setNoRepair();
		hasSubtypes = true;
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {
		if (is.getItemDamage() > 0) {
			ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 0));
			ep.setCurrentItemOrArmor(4, new ItemStack(is.itemID, is.stackSize, is.getItemDamage()-1));
		}
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity par3Entity, int par4, boolean par5) {}

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		return "/Reika/RotaryCraft/Textures/Misc/NVGoggles.png";
	}

}
