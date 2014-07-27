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

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;

public class ItemBedrockShears extends ItemShears implements IndexedItemSprites {

	private int index;

	public ItemBedrockShears(int ID, int tex) {
		super(ID);
		this.setIndex(tex);
		maxStackSize = 1;
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean isItemTool(ItemStack is) {
		return true;
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
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
	{
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block par1Block)
	{
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer player)
	{
		if (player.worldObj.isRemote)
			return false;
		else {
			int id = player.worldObj.getBlockId(x, y, z);
			int meta = player.worldObj.getBlockMetadata(x, y, z);
			boolean drop = false;
			boolean flag = false;
			Block b = Block.blocksList[id];
			if (b != null) {
				if (b instanceof IShearable) {
					drop = true;
					flag = true;
				}
				else if (b.blockMaterial == Material.plants) {
					drop = true;
					flag = true;
				}
				else
					flag = super.onBlockStartBreak(is, x, y, z, player);
			}
			if (drop) {
				ItemStack block = new ItemStack(id, 1, this.getDroppedMeta(id, meta));
				ReikaItemHelper.dropItem(player.worldObj, x+0.5, y+0.5, z+0.5, block);
				player.worldObj.setBlock(x, y, z, 0);
			}
			return flag;
		}
	}

	private int getDroppedMeta(int id, int meta) {
		if (id == Block.leaves.blockID)
			return meta&3;
		if (id == Block.vine.blockID)
			return 0;
		if (id == Block.waterlily.blockID)
			return 0;
		if (id == Block.sapling.blockID)
			return meta&3;
		ModWoodList wood = ModWoodList.getModWoodFromLeaf(id, meta);
		if (wood != null) {
			return wood.getLeafMetadatas().get(0);
		}
		return meta;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
	{
		if (entity.worldObj.isRemote)
			return false;
		if (entity instanceof IShearable) {
			IShearable target = (IShearable)entity;
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY);
			int z = MathHelper.floor_double(entity.posZ);
			if (target.isShearable(itemstack, entity.worldObj, x, y, z)) {
				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack);
				ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, x, y, z, fortune);

				Random rand = new Random();
				for (ItemStack stack : drops) {
					stack.stackSize *= 2;
				}
				ReikaItemHelper.dropItems(entity.worldObj, x+0.5, y+0.8, z+0.5, drops);
			}
			return true;
		}
		return false;
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block b)
	{
		float f = 0.75F;
		if (b != null) {
			if (b instanceof IShearable) {
				f = 8F;
			}
			else if (b.blockMaterial == Material.plants) {
				f = 8F;
			}
			else if (b.blockMaterial == Material.web || b.blockID == Block.web.blockID) {
				f = 40F;
			}
			else if (b.blockMaterial == Material.cloth || b.blockID == Block.cloth.blockID) {
				f = 16;
			}
		}
		return f;
	}

}
