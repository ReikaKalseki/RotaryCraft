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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.Data.TreeReader;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
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
		this.setCreativeTab(RotaryCraft.tabRotaryTools);
	}

	@Override
	public boolean isItemTool(ItemStack is) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block b) {
		return true;
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
		World world = ep.worldObj;
		TreeReader tree = new TreeReader();
		tree.setWorld(world);
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ReikaTreeHelper vanilla = ReikaTreeHelper.getTree(id, meta);
		ModWoodList wood = ModWoodList.getModWood(id, meta);
		tree.checkAndAddRainbowTree(world, x, y, z);
		if (tree.isEmpty() || !tree.isValidTree())
			tree.clear();
		tree.checkAndAddDyeTree(world, x, y, z);
		if (tree.isEmpty() || !tree.isValidTree())
			tree.clear();
		if (tree.isEmpty()) {
			if (wood == ModWoodList.SEQUOIA) {
				for (int i = -32; i < 255; i += 16)
					tree.addSequoia(world, x, y, z, RotaryCraft.logger.shouldDebug());
			}
			else if (wood == ModWoodList.DARKWOOD) {
				tree.addDarkForest(world, x, y, z, x-8, x+8, z-8, z+8, RotaryCraft.logger.shouldDebug());
			}
			else if (wood != null) {
				tree.setModTree(wood);
				tree.addModTree(world, x, y, z);
			}
			else if (vanilla != null) {
				tree.setTree(vanilla);
				tree.addTree(world, x, y, z);
			}
		}
		if (!tree.isEmpty() && tree.isValidTree()) {
			this.cutEntireTree(is, world, tree);
			return true;
		}
		return false;
	}

	private void cutEntireTree(ItemStack is, World world, TreeReader tree) {
		for (int i = 0; i < tree.getSize(); i++) {
			int[] xyz = tree.getNthBlock(i);
			int x = xyz[0];
			int y = xyz[1];
			int z = xyz[2];
			int id = world.getBlockId(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			Block b = Block.blocksList[id];
			if (b != null) {
				int fortune = ReikaEnchantmentHelper.getEnchantmentLevel(Enchantment.fortune, is);
				b.dropBlockAsItem(world, x, y, z, meta, fortune);
				ReikaSoundHelper.playBreakSound(world, x, y, z, b);
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
		return Item.axeStone.getIconFromDamage(0);
	}

	public Class getTextureReferenceClass() {
		return RotaryCraft.class;
	}

	@Override
	public String getTexture(ItemStack is) {
		return "/Reika/RotaryCraft/Textures/Items/items2.png";
	}
}
