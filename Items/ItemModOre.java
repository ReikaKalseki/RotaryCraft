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
import Reika.DragonAPI.Auxiliary.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ExtractorModOres;
import Reika.RotaryCraft.Base.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModOre extends ItemBasic {

	public ItemModOre(int ID) {
		super(ID, 0);
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		maxStackSize = 64;
		this.setCreativeTab(RotaryCraft.tabModOres);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int ID, CreativeTabs tab, List list) //Adds the metadata blocks to the creative inventory
	{
		int num = ModOreList.oreList.length;
		if (ID == RotaryCraft.modextracts.itemID)
			num *= 4;
		for (int i = 0; i < num; i++) {
			ItemStack item = new ItemStack(ID, 1, i);
			list.add(item);
		}
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + d;
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		if (item.itemID == RotaryCraft.modingots.itemID)
			return item.getItemDamage()*4+ExtractorModOres.getIndexOffsetForIngot(item);
		return item.getItemDamage();
	}

}
