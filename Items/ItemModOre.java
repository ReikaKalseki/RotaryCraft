/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Base.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModOre extends ItemBasic implements IndexedItemSprites {

	public ItemModOre(int ID) {
		super(ID, 0); //Returns super constructor: par1 is ID
		////setItemName("shaftcraft"); //Sets the incode name of the item, make sure it doesn't clash with other items, weird stuff happens
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		maxStackSize = 64;
		//this.setIconCoord(0, 0);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int i = 0; i < RotaryNames.modOreNames.length; i++) {
			ItemStack item = new ItemStack(par1, 1, i);
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
		return super.getUnlocalizedName() + "." + RotaryNames.modOreNames[d];
	}

	public static String getTextureFile() {
		return "/Reika/RotaryCraft/Textures/GUI/items.png"; //return the block texture where the block texture is saved in
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		return item.getItemDamage();
	}

}
