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

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBasic extends Item implements IndexedItemSprites {

	protected Random par5Random = new Random();

	private int index;

	public ItemBasic(int ID, int tex) {
		super(ID);
		maxStackSize = 64;
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		this.setIndex(tex);
	}

	public ItemBasic(int ID, int tex, int max) {
		super(ID);
		maxStackSize = max;
		if (max == 1);
		hasSubtypes = true;
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		this.setIndex(tex);
	}

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
