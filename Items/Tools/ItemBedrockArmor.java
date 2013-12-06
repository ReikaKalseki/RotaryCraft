/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryArmor;
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
	/*
	@Override
	public String getArmorTexture(ItemStack is, Entity entity, int slot, String type) {
		ItemRegistry item = ItemRegistry.getEntry(is);
		switch(item) {
		case BEDHELM:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png";
		case BEDLEGS:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_2.png";
		case BEDCHEST:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png";
		case BEDBOOTS:
			return "/Reika/RotaryCraft/Textures/Misc/bedrock_1.png";
		default:
			return "";
		}
	}*/

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

}
