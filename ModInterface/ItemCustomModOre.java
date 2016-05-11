/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.Item.GradientBlend;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
import Reika.RotaryCraft.Base.AutoOreItem;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class ItemCustomModOre extends AutoOreItem implements GradientBlend {

	public ItemCustomModOre(int idx) {
		super(idx);
	}

	@Override
	public void getSubItems(Item ID, CreativeTabs tab, List list)
	{
		boolean extract = this == ItemRegistry.CUSTOMEXTRACT.getItemInstance();
		int i = 0;
		for (CustomExtractEntry ore : CustomExtractLoader.instance.getEntries()) {
			if (extract) {
				list.add(new ItemStack(this, 1, i));
				list.add(new ItemStack(this, 1, i+1));
				list.add(new ItemStack(this, 1, i+2));
				list.add(new ItemStack(this, 1, i+3));
				i += 4;
			}
			else {
				list.add(new ItemStack(this, 1, i));
				i++;
			}
		}
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		int base = this.getRootIndex();
		return this == ItemRegistry.CUSTOMEXTRACT.getItemInstance() ? base+item.getItemDamage()%4 : base+this.getOreType(item).type.ordinal();
	}

	@Override
	public int getColorOne(ItemStack is) {
		return this.getOreType(is).color2;
	}

	@Override
	public int getColorTwo(ItemStack is) {
		return this.getOreType(is).color1;
	}

	@Override
	public int getColorThree(ItemStack is) {
		return this.getOreType(is).color1;
	}

	@Override
	public int getColorFour(ItemStack is) {
		return this.getOreType(is).color1;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		CustomExtractEntry ore = this.getOreType(is);
		if (ore == null)
			return "Null Ore Item";
		if (ItemRegistry.CUSTOMEXTRACT.matchItem(is)) {
			ExtractorStage s = ExtractorModOres.getStageFromMetadata(is);
			return s != null ? ore.displayName+" "+ReikaStringParser.capFirstChar(s.name()) : "null";
		}
		else {
			return ore.displayName+" "+ore.type.displayName;
		}
	}

	@Override
	public CustomExtractEntry getOreType(ItemStack item) {
		return this.getExtractType(item);
	}

	public static CustomExtractEntry getExtractType(ItemStack is) {
		if (CustomExtractLoader.instance.getEntries().isEmpty())
			return null;
		int idx = getEntryIndex(is);
		return CustomExtractLoader.instance.getEntries().get(idx);
	}

	public static int getEntryIndex(ItemStack is) {
		return is.getItem() == ItemRegistry.CUSTOMEXTRACT.getItemInstance() ? is.getItemDamage()/4 : is.getItemDamage();
	}

	public static ItemStack getItem(int idx, ExtractorStage s) {
		return ItemRegistry.CUSTOMEXTRACT.getStackOfMetadata(idx*4+s.ordinal());
	}

	public static ItemStack getSmeltedItem(int idx) {
		return ItemRegistry.CUSTOMINGOT.getStackOfMetadata(idx);
	}
}
