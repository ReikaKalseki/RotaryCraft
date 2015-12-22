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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSteelPick extends ItemPickaxe implements IndexedItemSprites {

	private int index;

	public ItemSteelPick(int tex) {
		super(ToolMaterial.IRON);
		damageVsEntity += 1;
		this.setMaxDamage(600);
		this.setIndex(tex);
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
	{
		float amt = super.func_150893_a(par1ItemStack, par2Block);
		return amt > 1 ? amt*1.2F : 1;
	}

	@Override
	public boolean canHarvestBlock(Block b, ItemStack is) {
		if (ConfigRegistry.HSLAHARVEST.getState() && b.blockHardness < 20 && this.getDigSpeed(is, b, 0) > 1) {
			return true;
		}
		else
			return Items.iron_pickaxe.canHarvestBlock(b, is);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		if (ConfigRegistry.HSLAHARVEST.getState() && toolClass.toLowerCase().contains("pick")) {
			return 5;
		}
		else {
			return super.getHarvestLevel(stack, toolClass);
		}
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		if (block.getMaterial() == Material.glass)
			return 8F;
		float amt = super.getDigSpeed(stack, block, meta);
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
	public final void registerIcons(IIconRegister ico) {}

	@Override
	public final IIcon getIconFromDamage(int dmg) { //To get around a bug in backtools
		return Items.stone_pickaxe.getIconFromDamage(0);
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
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
