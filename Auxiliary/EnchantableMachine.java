/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public interface EnchantableMachine {

	public void applyEnchants(ItemStack is);

	public List<Enchantment> getEnchantments();

}
