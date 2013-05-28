/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.mod_RotaryCraft;

public class ItemBedrockShovel extends ItemSpade implements IndexedItemSprites {

	private int index;

	public ItemBedrockShovel(int par1) {
		super(par1, EnumToolMaterial.GOLD);
		this.setCreativeTab(mod_RotaryCraft.tabRotaryItems);
		// this.blocksEffectiveAgainst = par4ArrayOfBlock;
		this.maxStackSize = 1;
		this.setMaxDamage(0);
		this.efficiencyOnProperMaterial = 12F;
		// this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
		this.damageVsEntity = 4;
		this.setNoRepair();
		this.setIndex(102);
	}

	@Override
	public boolean canHarvestBlock(Block b) {
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block par2Block) {
		if (par2Block == null)
			return 0;
		for (int i = 0; i < this.blocksEffectiveAgainst.length; i++) {
			if (this.blocksEffectiveAgainst[i] == par2Block)
				return 12F;
		}
		return 1F;
	}

	public String getTextureFile() {
		return "/Reika/RotaryCraft/Textures/GUI/items.png"; //return the block texture where the block texture is saved in
	}

	public int getItemSpriteIndex(ItemStack item) {
		return this.index;
	}

	public void setIndex(int a) {
		this.index = a;
	}
}
