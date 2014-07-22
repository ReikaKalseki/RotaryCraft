/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
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
		ReikaEnchantmentHelper.applyEnchantments(is, this.getDefaultEnchantments());
		li.add(is);
	}

	public HashMap<Enchantment, Integer> getDefaultEnchantments() {
		HashMap<Enchantment, Integer> map = new HashMap();
		if (ItemRegistry.getEntryByID(itemID).isBedrockArmor()) {
			switch(armorType) {
			case 0:
				map.put(Enchantment.projectileProtection, 4);
				map.put(Enchantment.respiration, 3);
				break;
			case 1:
				map.put(Enchantment.blastProtection, 4);
				break;
			case 2:
				map.put(Enchantment.fireProtection, 4);
				break;
			case 3:
				map.put(Enchantment.featherFalling, 4);
				break;
			}
		}
		return map;
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
		this.forceEnchantments(is, world, entity, par4);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem ei) {
		ItemStack is = ei.getEntityItem();
		HashMap<Enchantment, Integer> map = this.getDefaultEnchantments();
		for (Enchantment e : map.keySet()) {
			if (!ReikaEnchantmentHelper.hasEnchantment(e, is)) {
				ei.playSound("random.break", 1, 1);
				ei.setDead();
			}
		}
		return false;
	}

	private void forceEnchantments(ItemStack is, World world, Entity entity, int slot) {
		HashMap<Enchantment, Integer> map = this.getDefaultEnchantments();
		for (Enchantment e : map.keySet()) {
			if (!ReikaEnchantmentHelper.hasEnchantment(e, is)) {
				entity.playSound("random.break", 1, 1);
				if (entity instanceof EntityPlayer) {
					EntityPlayer ep = (EntityPlayer)entity;
					ep.inventory.setInventorySlotContents(slot, null);
					ReikaChatHelper.sendChatToPlayer(ep, "The damaged tool has broken.");
					is = null;
					break;
				}
			}
		}
	}

	@Override
	public boolean providesProtection() {
		return true;
	}

	@Override
	public boolean isVulnerableTo(DamageSource src) {
		return true;
	}

	@Override
	public boolean canBeDamaged() {
		return false;
	}

	@Override
	public double getDamageMultiplier(DamageSource src) {
		return src.isUnblockable() ? 0.75 : 0.35;
	}

	@Override
	public int getItemEnchantability()
	{
		return ConfigRegistry.PREENCHANT.getState() ? 0 : Item.pickaxeIron.getItemEnchantability();
	}

	public static boolean isWearingFullSuitOf(EntityLivingBase e) {
		for (int i = 1; i < 5; i++) {
			ItemStack is = e.getCurrentItemOrArmor(i);
			if (is == null)
				return false;
			ItemRegistry ir = ItemRegistry.getEntry(is);
			if (ir == null)
				return false;
			if (!ir.isBedrockTypeArmor())
				return false;
		}
		return true;
	}

}
