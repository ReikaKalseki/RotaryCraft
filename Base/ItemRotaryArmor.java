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

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryCraft;

public abstract class ItemRotaryArmor extends ItemArmor implements IndexedItemSprites {

	private int index;

	public ItemRotaryArmor(int par1, EnumArmorMaterial par2, int par3, int par4, int ind) {
		super(par1, par2, par3, par4);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		maxStackSize = 1;
		this.setIndex(ind);
	}

	@Override
	public final boolean isValidArmor(ItemStack stack, int type) {
		return true;
	}

	@Override
	public final ItemStack onItemRightClick(ItemStack is, World par2World, EntityPlayer par3Entity) {
		return is;
	}

	@Override
	public abstract void onArmorTickUpdate(World world, EntityPlayer ep, ItemStack is);

	public int getItemSpriteIndex(ItemStack item) {
		return index;
	}

	public void setIndex(int a) {
		index = a;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerIcons(IconRegister ico) {}

}
