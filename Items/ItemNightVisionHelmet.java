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

public class ItemNightVisionHelmet extends ItemRotaryArmor implements IArmorTextureProvider {

	public ItemNightVisionHelmet(int itemID, int texID) {
		super(itemID, RotaryCraft.NVHM, texID, 0, 48);
		//this.setNoRepair();
		//this.damageReduceAmount = EnumArmorMaterial.DIAMOND.getDamageReductionAmount(0);
		//this.setMaxDamage((int)(1.2*EnumArmorMaterial.DIAMOND.getDurability(0)));
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {
		ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 0));
	}

	@Override
	public void onUpdate(ItemStack is, World par2World, Entity par3Entity, int par4, boolean par5) {}

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		return "/Reika/RotaryCraft/Textures/Misc/NVHelmet.png";
	}

}
