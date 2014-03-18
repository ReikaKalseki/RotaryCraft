/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;

public abstract class ItemChargedArmor extends ItemRotaryArmor {

	public ItemChargedArmor(int par1, EnumArmorMaterial par2, int par3, int par4, int ind) {
		super(par1, par2, par3, par4, ind);
		this.setNoRepair();
		hasSubtypes = true;
	}

	protected void warnCharge(ItemStack is) {
		ReikaChatHelper.clearChat();
		if (is.getItemDamage() == 2) {
			ReikaChatHelper.write("Armor charge is very low (2 kJ)!");
		}
		if (is.getItemDamage() == 4) {
			ReikaChatHelper.write("Armor charge is low (4 kJ)!");
		}
		if (is.getItemDamage() == 16) {
			ReikaChatHelper.write("Armor charge is low (16 kJ)!");
		}
		if (is.getItemDamage() == 32) {
			ReikaChatHelper.write("Armor charge is low (32 kJ)!");
		}
	}

	protected void noCharge() {
		ReikaChatHelper.clearChat();
		ReikaChatHelper.write("Armor charge is depleted!");
	}

}
