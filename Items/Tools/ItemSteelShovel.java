package Reika.RotaryCraft.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.RotaryCraft;

public class ItemSteelShovel extends ItemSpade {

	public ItemSteelShovel(int ID) {
		super(ID, EnumToolMaterial.IRON);
		damageVsEntity += 1;
		this.setMaxDamage(600);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
	}

	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return super.getStrVsBlock(par1ItemStack, par2Block)*1.2F;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta)
	{
		return super.getStrVsBlock(stack, block, meta)*1.2F;
	}

}
