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
import Reika.DragonAPI.Interfaces.MultisheetItem;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ExtractorModOres;
import Reika.RotaryCraft.Base.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModOre extends ItemBasic implements MultisheetItem {

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
		int step = 0;
		if (item.itemID == RotaryCraft.modextracts.itemID)
			step = ExtractorModOres.getSpritesheet(ModOreList.oreList[item.getItemDamage()/4]);
		else
			step = ExtractorModOres.getSpritesheet(ModOreList.oreList[item.getItemDamage()]);
		if (item.itemID == RotaryCraft.modingots.itemID)
			return item.getItemDamage()*4+ExtractorModOres.getIndexOffsetForIngot(item)-step/256;
		return item.getItemDamage()-step/256;
	}

	@Override
	public String getSpritesheet(ItemStack is) {
		String base = "";
		if (is.itemID == RotaryCraft.modextracts.itemID) {
			base = "Textures/Items/modextracts.png";
		}
		else if (is.itemID == RotaryCraft.modingots.itemID) {
			base = "Textures/Items/modingots.png";
		}
		int step = 0;
		if (is.itemID == RotaryCraft.modextracts.itemID)
			step = ExtractorModOres.getSpritesheet(ModOreList.oreList[is.getItemDamage()/4]);
		else
			step = ExtractorModOres.getSpritesheet(ModOreList.oreList[is.getItemDamage()]);
		if (step > 0) {
			base = base.substring(0, base.length()-4);
			base += String.format("%d", 1+step);
			base += ".png";
		}
		return base;
	}

}
