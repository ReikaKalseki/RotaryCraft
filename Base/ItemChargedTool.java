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
package Reika.RotaryCraft.Base;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemChargedTool extends ItemRotaryTool {

	public ItemChargedTool(int ID, int index) {
		super(ID, index);
		hasSubtypes = true;
		//this.setMaxDamage(0);
	}

	@Override
	public abstract ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep);

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		par3List.add(new ItemStack(par1, 1, 8192));
	}

	protected void noCharge() {
		ReikaChatHelper.clearChat();
		ReikaChatHelper.write("Tool charge is depleted!");
	}

	protected void warnCharge(ItemStack is) {
		ReikaChatHelper.clearChat();
		if (is.getItemDamage() == 2) {
			ReikaChatHelper.write("Tool charge is very low (2 kJ)!");
		}
		if (is.getItemDamage() == 4) {
			ReikaChatHelper.write("Tool charge is low (4 kJ)!");
		}
		if (is.getItemDamage() == 16) {
			ReikaChatHelper.write("Tool charge is low (16 kJ)!");
		}
		if (is.getItemDamage() == 32) {
			ReikaChatHelper.write("Tool charge is low (32 kJ)!");
		}
	}

}
