/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockShovel extends ItemSpade implements IndexedItemSprites {

	private int index;

	public ItemBedrockShovel(int ID, int tex) {
		super(ID, EnumToolMaterial.GOLD);
		this.setIndex(tex);
		// this.blocksEffectiveAgainst = par4ArrayOfBlock;
		maxStackSize = 1;
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 12F;
		// this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = 4;
		this.setNoRepair();
	}

	@Override
	public boolean canHarvestBlock(Block b) {
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block par2Block) {
		if (par2Block == null)
			return 0;
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if (blocksEffectiveAgainst[i] == par2Block)
				return 12F;
		}
		return 1F;
	}

	public String getTextureFile() {
		return "/Reika/RotaryCraft/Textures/GUI/items.png"; //return the block texture where the block texture is saved in
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

	@Override
	public final Icon getIconFromDamage(int dmg) {
		return Item.shovelStone.getIconFromDamage(0);
	}
}
