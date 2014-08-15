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

import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.ModInteract.TinkerBlockHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockShovel extends ItemSpade implements IndexedItemSprites {

	private int index;

	public ItemBedrockShovel(int tex) {
		super(ToolMaterial.GOLD);
		this.setIndex(tex);
		// this.blocksEffectiveAgainst = par4ArrayOfBlock;
		maxStackSize = 1;
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 20F;
		// this.efficiencyOnProperMaterial = par3ToolMaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = 4;
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public boolean isItemTool(ItemStack is) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block b, ItemStack is) {
		return b.getMaterial() != Material.rock && b.getMaterial() != Material.iron;
	}

	@Override
	public int getItemEnchantability()
	{
		return Items.iron_shovel.getItemEnchantability();
	}

	@Override
	public float getDigSpeed(ItemStack is, Block b, int meta) {
		if (b == null)
			return 0;
		if (b.getMaterial() == Material.grass)
			return 24F;
		if (b.getMaterial() == Material.ground)
			return 24F;
		if (b.getMaterial() == Material.sand)
			return 24F;
		if (b == TinkerBlockHandler.getInstance().gravelOreID)
			return 36F;
		if (field_150914_c.contains(b))
			return 24F;
		return 1F;
	}

	public boolean isAcceleratedOn(Block b) {
		return field_150914_c.contains(b);
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
	public final IIcon getIconFromDamage(int dmg) {
		return RotaryCraft.instance.isLocked() ? ReikaTextureHelper.getMissingIcon() : Items.stone_shovel.getIconFromDamage(0);
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
}