/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.RotaryCraft;

import com.google.common.collect.Multimap;



public abstract class ItemRotaryTool extends ItemBasic {

	protected int damageVsEntity = 1;

	public ItemRotaryTool(int index) {
		super(index);
		maxStackSize = 1;
		this.setNoRepair();
	}

	@Override
	protected final CreativeTabs getCreativePage() {
		return RotaryCraft.tabRotaryTools;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack is) {
		Multimap multimap = super.getAttributeModifiers(is);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", damageVsEntity, 0));
		return multimap;
	}

	//public abstract boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10);

}
