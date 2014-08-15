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

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.RotaryCraft.Registry.ConfigRegistry;

import net.minecraft.item.ItemStack;

public abstract class ItemChargedArmor extends ItemRotaryArmor {

	public ItemChargedArmor(ArmorMaterial par2, int par3, int par4, int ind) {
		super(par2, par3, par4, ind);
		this.setNoRepair();
		hasSubtypes = true;
	}

	protected final void warnCharge(ItemStack is) {
		if (ConfigRegistry.CLEARCHAT.getState())
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

	protected final void noCharge() {
		if (ConfigRegistry.CLEARCHAT.getState())
			ReikaChatHelper.clearChat();
		ReikaChatHelper.write("Armor charge is depleted!");
	}

}