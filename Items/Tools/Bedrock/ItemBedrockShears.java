/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import Reika.ChromatiCraft.API.TreeGetter;
import Reika.ChromatiCraft.Block.Dye.BlockDyeLeaf;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Base.BlockTieredResource;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemBedrockShears extends ItemShears implements IndexedItemSprites {

	private static final HashSet<Block> noDrops = new HashSet();

	private int index;

	public ItemBedrockShears(int tex) {
		this.setIndex(tex);
		maxStackSize = 1;
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer ep) {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement(ep);
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
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
	{
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack is)
	{
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer player)
	{
		if (player.worldObj.isRemote)
			return false;
		else {
			Block b = player.worldObj.getBlock(x, y, z);
			int meta = player.worldObj.getBlockMetadata(x, y, z);
			boolean drop = false;
			boolean flag = false;
			Result res = getHarvestResult(b, meta, player, player.worldObj, x, y, z);
			switch(res) {
				case ALLOW:
					drop = flag = true;
					break;
				case DEFAULT:
					flag = super.onBlockStartBreak(is, x, y, z, player);
					break;
				case DENY:
					drop = flag = false;
					break;
			}
			if (drop) {
				ItemStack block = new ItemStack(b, 1, this.getDroppedMeta(b, meta));
				ReikaItemHelper.dropItem(player.worldObj, x+0.5, y+0.5, z+0.5, block);
				player.worldObj.setBlockToAir(x, y, z);
			}
			return flag;
		}
	}

	public static Result getHarvestResult(Block b, int meta, EntityPlayer player, World world, int x, int y, int z) {
		if (b instanceof BlockTieredResource) {
			return Result.DENY;
		}
		else if (noDrops.contains(b)) {
			return Result.DENY;
		}
		else if (b.canSilkHarvest(world, player, x, y, z, meta)) {
			return Result.ALLOW;
		}
		else if (b == Blocks.web) {
			return Result.ALLOW;
		}
		else if (ModList.CHROMATICRAFT.isLoaded() && b instanceof BlockDyeLeaf) {
			return Result.DEFAULT;
		}
		else if (world.getTileEntity(x, y, z) instanceof TileEntityBase) {
			return Result.DENY;
		}
		else if (b instanceof IShearable) {
			((IShearable)b).onSheared(player.getCurrentEquippedItem(), world, x, y, z, 0);
			return Result.ALLOW;
		}
		else if (b.getMaterial() == Material.plants) {
			return Result.ALLOW;
		}
		else {
			return Result.DEFAULT;
		}
	}

	public static int getDroppedMeta(Block id, int meta) {
		if (id == Blocks.leaves || id == Blocks.leaves2)
			return meta&3;
		if (ModList.CHROMATICRAFT.isLoaded() && id == TreeGetter.getRainbowLeafID())
			return 0;
		if (id == Blocks.vine)
			return 0;
		if (id == Blocks.waterlily)
			return 0;
		if (id == Blocks.sapling)
			return meta&3;
		if (id instanceof BlockDoublePlant)
			return meta%BlockDoublePlant.field_149892_a.length;
		if (id.getClass().getName().equals("vazkii.botania.common.block.BlockModFlower")) {
			return id.damageDropped(meta);
		}
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

				if (ConfigRegistry.FAKEBEDROCK.getState() || !ReikaPlayerAPI.isFake(player)) {
					for (ItemStack stack : drops) {
						stack.stackSize *= 2;
					}
				}
				ReikaItemHelper.dropItems(entity.worldObj, x+0.5, y+0.8, z+0.5, drops);
			}
			return true;
		}
		return false;
	}

	@Override
	public float getDigSpeed(ItemStack is, Block b, int meta)
	{
		float f = 0.75F;
		if (b != null) {
			if (b instanceof IShearable) {
				f = 8F;
			}
			else if (b.getMaterial() == Material.plants) {
				f = 8F;
			}
			else if (b.getMaterial() == Material.web || b == Blocks.web) {
				f = 40F;
			}
			else if (b.getMaterial() == Material.cloth || b == Blocks.wool) {
				f = 16;
			}
		}
		return f;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		return ItemRegistry.getEntry(is).getBasicName();
	}

	static {
		noDrops.add(Blocks.reeds);
		noDrops.add(Blocks.melon_stem);
		noDrops.add(Blocks.pumpkin_stem);
	}

}
