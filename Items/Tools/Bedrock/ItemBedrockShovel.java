/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Bedrock;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
import Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
import Reika.DragonAPI.Interfaces.Item.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerBlockHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockShovel extends ItemSpade implements IndexedItemSprites {

	private static final BlockMap<ChancedOutputList> extraDrops = new BlockMap();

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
	public void onCreated(ItemStack is, World world, EntityPlayer ep) {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement(ep);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return toolClass.toLowerCase().contains("shovel") || toolClass.toLowerCase().contains("spade") ? Integer.MAX_VALUE : super.getHarvestLevel(stack, toolClass);
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
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep) {
		if (ConfigRegistry.FAKEBEDROCK.getState() || !ReikaPlayerAPI.isFake(ep)) {
			ChancedOutputList co = extraDrops.get(ep.worldObj.getBlock(x, y, z), ep.worldObj.getBlockMetadata(x, y, z));
			if (co != null) {
				double mult = Math.sqrt(1+EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, is));
				Collection<ItemStack> c = co.calculate(mult);
				for (ItemStack drop : c) {
					ReikaItemHelper.dropItem(ep.worldObj, x+0.5, y+0.5, z+0.5, drop);
				}
			}
		}
		return false;
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

	static {
		addDrop(Blocks.grass, 0, Items.wheat_seeds, 10);
		addDrop(Blocks.grass, 0, Items.clay_ball, 5);
		addDrop(Blocks.grass, 0, Blocks.mycelium, 0.5F);
		addDrop(Blocks.grass, 0, Items.pumpkin_seeds, 5);
		addDrop(Blocks.grass, 0, Items.melon_seeds, 5);

		addDrop(Blocks.dirt, 0, Items.wheat_seeds, 10);
		addDrop(Blocks.dirt, 0, Items.glowstone_dust, 2);
		addDrop(Blocks.dirt, 0, Items.nether_wart, 0.5F);
		addDrop(Blocks.dirt, 0, Items.emerald, 0.05F);
		addDrop(Blocks.dirt, 0, Items.diamond, 0.05F);

		addDrop(Blocks.sand, 0, Items.gunpowder, 2);

		addDrop(Blocks.clay, 0, Items.bone, 5);
		addDrop(Blocks.clay, 0, Blocks.soul_sand, 2);
		addDrop(Blocks.clay, 0, Items.gold_nugget, 4);

		addDrop(Blocks.soul_sand, 0, Items.blaze_powder, 4);
		addDrop(Blocks.soul_sand, 0, Items.nether_wart, 5);
		addDrop(Blocks.soul_sand, 0, Items.quartz, 2);
	}

	private static void addDrop(Block b, int meta, Block i, float chance) {
		addDrop(b, meta, new ItemStack(i), chance);
	}

	private static void addDrop(Block b, int meta, Item i, float chance) {
		addDrop(b, meta, new ItemStack(i), chance);
	}

	private static void addDrop(Block b, int meta, ItemStack is, float chance) {
		ChancedOutputList co = extraDrops.get(b, meta);
		if (co == null) {
			co = new ChancedOutputList();
			extraDrops.put(b, meta, co);
		}
		co.addItem(is, chance);
	}
}
