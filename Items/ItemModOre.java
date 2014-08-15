/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import Reika.DragonAPI.Interfaces.MultisheetItem;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModOre extends ItemBasic implements MultisheetItem {

	public ItemModOre() {
		super(0);
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		maxStackSize = 64;
	}

	@Override
	protected CreativeTabs getCreativePage() {
		return RotaryCraft.tabModOres;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item ID, CreativeTabs tab, List list) //Adds the metadata blocks to the creative inventory
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
		int step = ExtractorModOres.getSpritesheet(this.getModOre(item));
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
		int step = ExtractorModOres.getSpritesheet(this.getModOre(is));
		if (step > 0) {
			base = base.substring(0, base.length()-4);
			base += String.format("%d", 1+step);
			base += ".png";
		}
		return base;
	}

	private ModOreList getModOre(ItemStack is) {
		if (ItemRegistry.MODEXTRACTS.matchItem(is))
			return ModOreList.oreList[is.getItemDamage()/4];
		else
			return ModOreList.oreList[is.getItemDamage()];
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		ModOreList ore = this.getModOre(is);
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