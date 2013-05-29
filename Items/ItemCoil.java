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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoil extends ItemBasic
{
	private static final String subNames[] = new String[65536];

	public ItemCoil(int itemID) {
		super(itemID, 96); //calling the super constructor and giving him the itemID so minecraft knows the itemID
		this.maxStackSize = 1;
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		for (int i = 0; i < 65536; i++)
			this.subNames[i] = String.format("%d", i);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int i = 0; i < 65536; i++)
			if (ReikaMathLibrary.isPowerOf(i, 2) || i == 65535 || i == 0)
				par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

	public String getItemNameIS(ItemStack is) {
		//int dmg = is.getItemDamage();
		//return this.subNames[dmg];
		if (is.getItemDamage() < 0)
			is.setItemDamage(0);
		return new StringBuilder().append("wind").append(subNames[is.getItemDamage()]).toString();
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + String.valueOf(d);
	}
}
