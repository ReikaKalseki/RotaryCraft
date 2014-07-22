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
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.DragonAPI.Auxiliary.ProgressiveRecursiveBreaker;
import Reika.DragonAPI.Auxiliary.ProgressiveRecursiveBreaker.ProgressiveBreaker;
import Reika.DragonAPI.Instantiable.Data.TreeReader;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockAxe extends ItemAxe implements IndexedItemSprites {

	private int index;

	public ItemBedrockAxe(int ID, int tex) {
		super(ID, EnumToolMaterial.GOLD);
		this.setIndex(tex);
		maxStackSize = 1;
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 12F;
		damageVsEntity = 6;
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int slot, boolean par5) {
		this.forceNoSilkTouch(is, world, entity, slot);
	}

	private void forceNoSilkTouch(ItemStack is, World world, Entity entity, int slot) {
		if (ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			HashMap<Enchantment, Integer> map = ReikaEnchantmentHelper.getEnchantments(is);
			is.stackTagCompound = null;
			map.remove(Enchantment.silkTouch);
			ReikaEnchantmentHelper.applyEnchantments(is, map);
		}
	}

	@Override
	public boolean isItemTool(ItemStack is) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block b) {
		return b.blockMaterial == Material.wood || b.blockMaterial == Material.pumpkin || b.blockMaterial.isToolNotRequired();
	}

	@Override
	public int getItemEnchantability()
	{
		return Item.axeIron.getItemEnchantability();
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block b) {
		if (b == null)
			return 0;
		if (TwilightForestHandler.getInstance().isTowerWood(b))
			return 30F;
		if (b.blockMaterial == Material.wood)
			return 20F;
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if (blocksEffectiveAgainst[i] == b)
				return 20F;
		}
		return 1F;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep)
	{
		if (ep.isSneaking())
			return false;
		World world = ep.worldObj;
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ModWoodList wood = ModWoodList.getModWood(id, meta);
		ReikaTreeHelper tree2 = ReikaTreeHelper.getTree(id, meta);
		int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
		TreeReader tree = new TreeReader();
		if (wood != ModWoodList.SEQUOIA) {
			tree.setWorld(world);
			tree.checkAndAddRainbowTree(world, x, y, z);
			if (tree.isEmpty() || !tree.isValidTree())
				tree.clear();
			tree.checkAndAddDyeTree(world, x, y, z);
		}
		if (id == TwilightForestHandler.getInstance().rootID) {
			Block b = Block.blocksList[id];
			int r = 2;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						int id2 = world.getBlockId(x+i, y+j, z+k);
						if (id2 == id) {
							b.dropBlockAsItem(world, x+i, y+j, z+k, meta, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b);
							world.setBlock(x+i, y+j, z+k, 0);
						}
					}
				}
			}
			return true;
		}
		else if (id == Block.mushroomCapRed.blockID || id == Block.mushroomCapBrown.blockID) {
			Block b = Block.blocksList[id];
			int r = 3;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = -r; k <= r; k++) {
						int id2 = world.getBlockId(x+i, y+j, z+k);
						int meta2 = world.getBlockMetadata(x+i, y+j, z+k);
						if (id2 == Block.mushroomCapRed.blockID || id2 == Block.mushroomCapBrown.blockID) {
							b.dropBlockAsItem(world, x+i, y+j, z+k, meta, fortune);
							ReikaSoundHelper.playBreakSound(world, x+i, y+j, z+k, b, 0.25F, 1);
							world.setBlock(x+i, y+j, z+k, 0);
						}
					}
				}
			}
			return true;
		}
		else if (!tree.isEmpty() && tree.isValidTree()) {
			this.cutEntireTree(is, world, tree, x, y, z);
			return true;
		}
		else if (!world.isRemote) {
			if (wood != null) {
				ProgressiveBreaker b = ProgressiveRecursiveBreaker.instance.getTreeBreaker(world, x, y, z, wood);
				b.player = ep;
				b.fortune = fortune;
				ProgressiveRecursiveBreaker.instance.addCoordinate(world, b);
				return true;
			}
			else if (tree2 != null) {
				ProgressiveBreaker b = ProgressiveRecursiveBreaker.instance.getTreeBreaker(world, x, y, z, tree2);
				b.player = ep;
				b.fortune = fortune;
				ProgressiveRecursiveBreaker.instance.addCoordinate(world, b);
				return true;
			}
		}
		return false;
	}

	private void cutEntireTree(ItemStack is, World world, TreeReader tree, int dx, int dy, int dz) {
		int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
		boolean rainbow = tree.isRainbowTree();
		if (rainbow) {
			ArrayList<ItemStack> items = tree.getAllDroppedItems(world, fortune);
			ReikaItemHelper.dropItems(world, dx, dy, dz, items);
		}
		for (int i = 0; i < tree.getSize(); i++) {
			int[] xyz = tree.getNthBlock(i);
			int x = xyz[0];
			int y = xyz[1];
			int z = xyz[2];
			int id = world.getBlockId(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			Block b = Block.blocksList[id];
			if (b != null) {
				ReikaSoundHelper.playBreakSound(world, x, y, z, b, 0.75F, 1);
				if (!rainbow)
					b.dropBlockAsItem(world, x, y, z, meta, fortune);
				world.setBlock(x, y, z, 0);
			}
		}
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
		return RotaryCraft.instance.isLocked() ? ReikaTextureHelper.getMissingIcon() : Item.axeStone.getIconFromDamage(0);
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase target, EntityLivingBase ep)
	{
		if (target.getClass().getSimpleName().equals("EntityEnt")) {
			DamageSource src = ep instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer)ep) : DamageSource.generic;
			target.setHealth(1);
			target.attackEntityFrom(src, Integer.MAX_VALUE);
			return true;
		}
		else {
			return super.hitEntity(is, target, ep);
		}
	}
}
