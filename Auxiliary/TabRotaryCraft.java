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
package Reika.RotaryCraft.Auxiliary;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class TabRotaryCraft extends CreativeTabs {

	public TabRotaryCraft(int position, String tabID) {
		super(position, tabID); //The constructor for your tab
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() //The item it displays for your tab
	{
		return new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.JET.getID());
	}

	@Override
	public String getTranslatedTabLabel()
	{
		return "RotaryCraft"; //The name of the tab ingame
	}
}
