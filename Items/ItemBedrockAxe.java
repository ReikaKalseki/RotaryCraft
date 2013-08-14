/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.ModInteract.TwilightBlockHandler;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockAxe extends ItemAxe implements IndexedItemSprites {

	private int index;

	public ItemBedrockAxe(int ID, int tex) {
		super(ID, EnumToolMaterial.GOLD);
		this.setIndex(tex);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		// this.blocksEffectiveAgainst = par4ArrayOfBlock;
		maxStackSize = 1;
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 12F;
		// this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = 6;
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
		if (TwilightBlockHandler.getInstance().isTowerWood(par2Block))
			return 30F;
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
}
