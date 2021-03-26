/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Interfaces.Registry.OreType;
import Reika.RotaryCraft.RotaryCraft;

public abstract class AutoOreItem extends ItemBasic {

	public AutoOreItem(int idx) {
		super(idx);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		maxStackSize = 64;
	}

	@Override
	protected final CreativeTabs getCreativePage() {
		return RotaryCraft.tabModOres;
	}

	@Override
	public final int getMetadata (int damageValue) {
		return damageValue;
	}

	@Override
	public final String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + d;
	}

	@Override
	public abstract String getItemStackDisplayName(ItemStack is);
	@Override
	public abstract int getItemSpriteIndex(ItemStack item);

	public abstract OreType getOreType(ItemStack item);

}
