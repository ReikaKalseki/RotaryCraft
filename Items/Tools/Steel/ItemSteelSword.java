/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Steel;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class ItemSteelSword extends ItemSword implements IndexedItemSprites {

	private int index;

	public ItemSteelSword(int tex) {
		super(ToolMaterial.IRON);
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

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		return ItemRegistry.getEntry(is).getBasicName();
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack item) {
		return tool.getItem() == this && ReikaItemHelper.matchStacks(item, ItemStacks.steelingot);
	}

}
