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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockPickaxe extends ItemPickaxe implements IndexedItemSprites {

	private int index;

	public ItemBedrockPickaxe(int par1) {
		super(par1, EnumToolMaterial.GOLD);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		maxStackSize = 1;
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 12F;
		damageVsEntity = 5;
		this.setNoRepair();
		this.setIndex(101);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		ItemStack item = new ItemStack(par1, 1, 0);
		item.addEnchantment(Enchantment.silkTouch, 1);
		par3List.add(item);
	}

	@Override
	public boolean canHarvestBlock(Block b) {
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block par2Block) {
		if (par2Block == null)
			return 0;
		if (par2Block.blockID == Block.obsidian.blockID)
			return 48F;
		if (par2Block.blockID == RotaryCraft.blastglass.blockID)
			return 32F;
		if (par2Block.blockID == RotaryCraft.obsidianglass.blockID)
			return 48F;
		if (par2Block.blockID == MachineRegistry.SHAFT.getBlockID())
			return 32F;
		if (par2Block.blockID == MachineRegistry.GEARBOX.getBlockID())
			return 32F;
		if (par2Block.blockID == Block.mobSpawner.blockID)
			return 18F;
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if (blocksEffectiveAgainst[i] == par2Block)
				return 12F;
		}
		for (int i = 0; i < ItemSpade.blocksEffectiveAgainst.length; i++) { //Also can dig dirt, etc
			if (ItemSpade.blocksEffectiveAgainst[i] == par2Block)
				return 6F;
		}
		if (par2Block.blockMaterial == Material.rock || par2Block.blockMaterial == Material.iron)
			return 12F;
		if (par2Block == RotaryCraft.decoblock)
			return 12F;
		if (par2Block instanceof BlockBasicMachine)
			return 12F;
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
}
