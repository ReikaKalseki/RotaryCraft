/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemBlockPlacer extends Item {

	public ItemBlockPlacer(int id) {
		super(id);
		this.setHasSubtypes(true); //Marks item as having metadata
		this.setMaxDamage(0);
		maxStackSize = 64;
		this.setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	public abstract boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10);

	protected boolean checkValidBounds(ItemStack is, EntityPlayer ep, World world, int x, int y, int z) {
		return true;
	}

	protected void checkAndBreakAdjacent(World world, int x, int y, int z) {}

	@Override
	public int getMetadata(int damageValue) {
		return 0;
	}

	@Override
	public final String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + String.valueOf(d);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public abstract void getSubItems(int id, CreativeTabs tab, List list);
}
