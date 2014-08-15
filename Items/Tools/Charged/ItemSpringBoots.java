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

import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import Reika.RotaryCraft.Items.Tools.Bedrock.ItemBedrockArmor;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemSpringBoots extends ItemChargedArmor {

	public final int JUMP_LEVEL = 3;
	public final int SPEED_LEVEL = 2;

	public ItemSpringBoots(ArmorMaterial mat, int tex, int render) {
		super(mat, render, 3, tex);
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		ItemRegistry ir = ItemRegistry.getEntry(item);
		return ir != null ? ir.getTextureIndex() : 0;
	}

	@Override
	public boolean providesProtection() {
		return this == ItemRegistry.BEDJUMP.getItemInstance();
	}

	@Override
	public boolean canBeDamaged() {
		return false;
	}

	@Override
	public double getDamageMultiplier(DamageSource src) {
		ItemBedrockArmor arm = (ItemBedrockArmor)ItemRegistry.BEDBOOTS.getItemInstance();
		return this == ItemRegistry.BEDJUMP.getItemInstance() ? arm.getDamageMultiplier(src) : 1;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer ep, ItemStack is) {
		if (is.getItem() == ItemRegistry.BEDJUMP.getItemInstance() || is.getItemDamage() > 0) {
			PotionEffect pot = ep.getActivePotionEffect(Potion.jump);
			if (pot == null || pot.getAmplifier() < JUMP_LEVEL) {
				ep.addPotionEffect(new PotionEffect(Potion.jump.id, 1, JUMP_LEVEL));
			}
			pot = ep.getActivePotionEffect(Potion.moveSpeed);
			if (pot == null || pot.getAmplifier() < SPEED_LEVEL) {
				ep.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, SPEED_LEVEL));
			}
			ep.stepHeight = 1.25F;
			if (itemRand.nextInt(160) == 0) {
				if (is.getItem() != ItemRegistry.BEDJUMP.getItemInstance()) {
					ep.setCurrentItemOrArmor(1, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()-1));
					this.warnCharge(is);
				}
			}
		}
		else
			ep.stepHeight = 0.5F;
	}

	@Override
	public void getSubItems(Item id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 24000);
		if (id == ItemRegistry.BEDJUMP.getItemInstance()) {
			HashMap<Enchantment, Integer> ench = ((ItemBedrockArmor)ItemRegistry.BEDBOOTS.getItemInstance()).getDefaultEnchantments();
			ReikaEnchantmentHelper.applyEnchantments(is, ench);
		}
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir.isAvailableInCreativeInventory())
			li.add(is);
	}
}