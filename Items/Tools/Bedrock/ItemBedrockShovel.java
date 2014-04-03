/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.ModInteract.TinkerBlockHandler;
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
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 20F;
		// this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = 4;
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public boolean isItemTool(ItemStack is) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block b) {
		return b.blockMaterial != Material.rock && b.blockMaterial != Material.iron;
	}

	@Override
	public int getItemEnchantability()
	{
		return Item.shovelIron.getItemEnchantability();
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block b) {
		if (b == null)
			return 0;
		if (b.blockMaterial == Material.grass)
			return 24F;
		if (b.blockMaterial == Material.ground)
			return 24F;
		if (b.blockMaterial == Material.sand)
			return 24F;
		if (b.blockID == TinkerBlockHandler.getInstance().gravelOreID)
			return 36F;
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if (blocksEffectiveAgainst[i] == b)
				return 24F;
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
		return RotaryCraft.instance.isLocked() ? ReikaTextureHelper.getMissingIcon() : Item.shovelStone.getIconFromDamage(0);
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}
}
