/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.item.EnumArmorMaterial;

public abstract class ItemChargedArmor extends ItemRotaryArmor {

	public ItemChargedArmor(int par1, EnumArmorMaterial par2, int par3, int par4, int ind) {
		super(par1, par2, par3, par4, ind);
		this.setNoRepair();
		hasSubtypes = true;
	}

}
