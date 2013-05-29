/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.Base.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRailGunAmmo extends ItemBasic {

	private static final String subNames[] = new String[16];

	public ItemRailGunAmmo(int itemID) {
		super(itemID, 113);
		maxStackSize = 16;
		hasSubtypes = true;
		this.setMaxDamage(0);
		for (int i = 0; i < 16; i++)
			subNames[i] = String.format("%d", i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int i = 0; i < 16; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
	     int d = is.getItemDamage();
	    	return super.getUnlocalizedName() + "." + String.valueOf(d);
	}

}
