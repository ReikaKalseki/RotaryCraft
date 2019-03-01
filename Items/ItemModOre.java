/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Interfaces.Item.MultisheetItem;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
import Reika.RotaryCraft.Base.AutoOreItem;
import Reika.RotaryCraft.Registry.ItemRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModOre extends AutoOreItem implements MultisheetItem {

	public ItemModOre() {
		super(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item ID, CreativeTabs tab, List list)
	{
		int num = ModOreList.oreList.length;
		if (ID == ItemRegistry.MODEXTRACTS.getItemInstance())
			num *= 4;
		for (int i = 0; i < num; i++) {
			ItemStack item = new ItemStack(ID, 1, i);
			list.add(item);
		}
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		int step = ExtractorModOres.getSpritesheet(this.getOreType(item));
		if (ItemRegistry.MODINGOTS.matchItem(item))
			return item.getItemDamage()*4+ExtractorModOres.getIndexOffsetForIngot(item)-step/256;
		return item.getItemDamage()-step/256;
	}

	@Override
	public String getSpritesheet(ItemStack is) {
		String base = "";
		if (ItemRegistry.MODEXTRACTS.matchItem(is)) {
			base = "Textures/Items/modextracts.png";
		}
		else if (ItemRegistry.MODINGOTS.matchItem(is)) {
			base = "Textures/Items/modingots.png";
		}
		int step = ExtractorModOres.getSpritesheet(this.getOreType(is));
		if (step > 0) {
			base = base.substring(0, base.length()-4);
			base += String.format("%d", 1+step);
			base += ".png";
		}
		return base;
	}

	@Override
	public ModOreList getOreType(ItemStack is) {
		if (ItemRegistry.MODEXTRACTS.matchItem(is))
			return ModOreList.oreList[is.getItemDamage()/4];
		else
			return ModOreList.oreList[is.getItemDamage()];
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		ModOreList ore = this.getOreType(is);
		if (ore == null)
			return "Null Ore Item";
		if (ItemRegistry.MODEXTRACTS.matchItem(is)) {
			ExtractorStage s = ExtractorModOres.getStageFromMetadata(is);
			return s != null ? ore.displayName+" "+ReikaStringParser.capFirstChar(s.name()) : "null";
		}
		else {
			return ore.displayName+" "+ore.getTypeName();
		}
	}

}
