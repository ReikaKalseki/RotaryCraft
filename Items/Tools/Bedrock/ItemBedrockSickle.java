/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import Reika.RotaryCraft.Base.ItemSickleBase;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockSickle extends ItemSickleBase {

	public ItemBedrockSickle(int index) {
		super(index);
	}

	@Override
	public int getLeafRange() {
		return 6;
	}

	@Override
	public int getCropRange() {
		return 8;
	}

	@Override
	public int getPlantRange() {
		return 7;
	}

	@Override
	public boolean canActAsShears() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		ItemStack item = new ItemStack(par1, 1, 0);
		item.addEnchantment(Enchantment.fortune, 5);
		par3List.add(item);
	}

}