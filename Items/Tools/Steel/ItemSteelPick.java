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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSteelPick extends ItemPickaxe implements IndexedItemSprites {

	private int index;

	public ItemSteelPick(int ID, int tex) {
		super(ID, EnumToolMaterial.IRON);
		damageVsEntity += 1;
		this.setMaxDamage(600);
		this.setIndex(tex);
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		float amt = super.getStrVsBlock(par1ItemStack, par2Block);
		return amt > 1 ? amt*1.2F : 1;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta)
	{
		if (block.blockMaterial == Material.glass)
			return 8F;
		float amt = super.getStrVsBlock(stack, block, meta);
		return amt > 1 ? amt*1.2F : 1;
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
	public final Icon getIconFromDamage(int dmg) { //To get around a bug in backtools
		return Item.pickaxeStone.getIconFromDamage(0);
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}

}
