/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockArmor extends ItemRotaryArmor {

	public ItemBedrockArmor(int ID, int tex, int render, int type) {
		super(ID, RotaryCraft.BEDROCK, render, type, tex);
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs cr, List li) //Adds the metadata blocks to the creative inventory
	{
		ItemStack is = new ItemStack(id, 1, 0);
		Enchantment ench = this.getDefaultEnchantment();
		if (ench != null)
			is.addEnchantment(ench, 4);
		li.add(is);
	}

	public Enchantment getDefaultEnchantment() {
		if (ItemRegistry.getEntryByID(itemID).isBedrockArmor()) {
			switch(armorType) {
			case 0:
				return Enchantment.projectileProtection;
			case 1:
				return Enchantment.blastProtection;
			case 2:
				return Enchantment.fireProtection;
			case 3:
				return Enchantment.featherFalling;
			}
		}
		return null;
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
		this.forceEnchantments(is);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem ei) {
		ItemStack is = ei.getEntityItem();
		this.forceEnchantments(is);
		return false;
	}

	private void forceEnchantments(ItemStack is) {
		if (ConfigRegistry.PREENCHANT.getState()) {
			Map map = new HashMap();
			map.put(this.getDefaultEnchantment().effectId, 4);
			EnchantmentHelper.setEnchantments(map, is);
		}
	}

	@Override
	public boolean providesProtection() {
		return true;
	}

	@Override
	public boolean canBeDamaged() {
		return false;
	}

	@Override
	public double getDamageMultiplier() {
		return 0.35;
	}

	@Override
	public int getItemEnchantability()
	{
		return ConfigRegistry.PREENCHANT.getState() ? 0 : Item.pickaxeIron.getItemEnchantability();
	}

}
