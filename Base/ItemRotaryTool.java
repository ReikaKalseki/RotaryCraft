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

import net.minecraft.creativetab.CreativeTabs;
import Reika.RotaryCraft.RotaryCraft;



public abstract class ItemRotaryTool extends ItemBasic {

	public ItemRotaryTool(int ID, int index) {
		super(ID, index);
		maxStackSize = 1;
		this.setNoRepair();
	}

	@Override
	protected final CreativeTabs getCreativePage() {
		return RotaryCraft.tabRotaryTools;
	}

	//public abstract boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10);

}
