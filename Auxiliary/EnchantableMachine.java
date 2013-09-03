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

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public interface EnchantableMachine {

	public boolean applyEnchants(ItemStack is);

	public HashMap<Enchantment,Integer> getEnchantments();

	public boolean hasEnchantment(Enchantment e);

	public boolean hasEnchantments();

	public int getEnchantment(Enchantment e);

	public ArrayList<Enchantment> getValidEnchantments();

}
