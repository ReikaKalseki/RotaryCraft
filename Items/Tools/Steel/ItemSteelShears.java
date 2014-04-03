/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Steel;

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryCraft;

public class ItemSteelShears extends ItemShears implements IndexedItemSprites {

	private int index;

	public ItemSteelShears(int par1, int tex) {
		super(par1);
		this.setIndex(tex);
		maxStackSize = 1;
		this.setMaxDamage(600);
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	private void setIndex(int tex) {
		index = tex;
	}

	@Override
	public int getItemSpriteIndex(ItemStack is) {
		return index;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}

	@Override
	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

}
