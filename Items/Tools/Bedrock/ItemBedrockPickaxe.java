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

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.MFRHandler;
import Reika.DragonAPI.ModInteract.MagicCropHandler;
import Reika.DragonAPI.ModInteract.MekanismHandler;
import Reika.DragonAPI.ModInteract.OpenBlockHandler;
import Reika.DragonAPI.ModInteract.ThaumBlockHandler;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModInteract.ThermalHandler;
import Reika.DragonAPI.ModInteract.TransitionalOreHandler;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.xcompwiz.mystcraft.api.MystObjects;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ItemBedrockPickaxe extends ItemPickaxe implements IndexedItemSprites {

	private int index;

	private final ArrayList<Enchantment> forbiddenEnchants = new ArrayList();

	public ItemBedrockPickaxe(int tex) {
		super(ToolMaterial.EMERALD);
		this.setIndex(tex);
		maxStackSize = 1;
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 12F;
		damageVsEntity = 5;
		this.setNoRepair();
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotaryTools);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		ItemStack item = new ItemStack(par1, 1, 0);
		item.addEnchantment(Enchantment.silkTouch, 1);
		par3List.add(item);
	}

	// To make un-unenchantable
	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int slot, boolean par5) {
		this.forceSilkTouch(is, world, entity, slot);
	}

	private void forceSilkTouch(ItemStack is, World world, Entity entity, int slot) {
		if (!ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			entity.playSound("random.break", 1, 1);
			if (entity instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer)entity;
				ep.inventory.setInventorySlotContents(slot, null);
				ReikaChatHelper.sendChatToPlayer(ep, "The dulled tool has broken.");
				is = null;
			}
		}
	}

	@Override
	public boolean canHarvestBlock(Block b, ItemStack is) {
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep)
	{
		if (ep.capabilities.isCreativeMode)
			return false;
		World world = ep.worldObj;
		Block id = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ItemStack block = new ItemStack(id, 1, meta);

		if (ConfigRegistry.MODORES.getState()) {
			if (ModList.THAUMCRAFT.isLoaded() && ThaumOreHandler.getInstance().isThaumOre(block)) {
				this.dropDirectBlock(block, world, x, y, z);
				return true;
			}
			if (ModList.THAUMCRAFT.isLoaded() && ThaumBlockHandler.getInstance().isCrystalCluster(block)) {
				this.dropDirectBlock(block, world, x, y, z);
				return true;
			}
			if (ModList.DARTCRAFT.isLoaded() && DartOreHandler.getInstance().isDartOre(block)) {
				this.dropDirectBlock(block, world, x, y, z);
				return true;
			}
			if (ModList.TRANSITIONAL.isLoaded() && TransitionalOreHandler.getInstance().isMagmaniteOre(block)) {
				this.dropDirectBlock(block, world, x, y, z);
				return true;
			}
			if (ModList.MAGICCROPS.isLoaded() && MagicCropHandler.getInstance().isEssenceOre(id)) {
				this.dropDirectBlock(block, world, x, y, z);
				return true;
			}
		}
		if (ConfigRegistry.BEDPICKSPAWNERS.getState() && id == Blocks.mob_spawner) {
			TileEntityMobSpawner spw = (TileEntityMobSpawner)world.getTileEntity(x, y, z);
			if (ConfigRegistry.SPAWNERLEAK.getState())
				ReikaSpawnerHelper.forceSpawn(spw, 12+itemRand.nextInt(25));
			ItemStack item = ItemRegistry.SPAWNER.getStackOf();
			ReikaSpawnerHelper.addMobNBTToItem(item, spw);
			ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), item);
			//world.setBlockToAir(x, y, z);
			//world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 1.25F);
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
				;//ReikaRenderHelper.spawnDropParticles(world, x, y, z, Blocks.mob_spawner, meta);
			}
			return false;
		}
		if (id != Blocks.monster_egg)
			return false;
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
		world.setBlockToAir(x, y, z);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaRenderHelper.spawnDropParticles(world, x, y, z, Blocks.monster_egg, meta);
		}
		ItemStack drop = new ItemStack(ReikaBlockHelper.getSilverfishImitatedBlock(meta), 1, 0);
		ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), drop);
		EntitySilverfish si = new EntitySilverfish(world);
		si.setPosition(x+0.5, y, z+0.5);
		si.setHealth(0);
		if (world.isRemote)
			world.spawnEntityInWorld(si);
		world.playSoundAtEntity(si, "mob.silverfish.kill", 0.5F, 1);
		ReikaWorldHelper.splitAndSpawnXP(world, x+0.5F, y+0.125F, z+0.5F, si.experienceValue);
		return true;
	}

	private void dropDirectBlock(ItemStack block, World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaRenderHelper.spawnDropParticles(world, x, y, z, Block.getBlockFromItem(block.getItem()), block.getItemDamage());
		}
		ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), block);
	}

	@Override
	public float getDigSpeed(ItemStack is, Block b, int meta) {
		if (b == null)
			return 0;
		if (b == Blocks.obsidian)
			return 48F;
		if (b == BlockRegistry.BLASTPANE.getBlockInstance())
			return 32F;
		if (b == BlockRegistry.BLASTGLASS.getBlockInstance())
			return 48F;
		if (b == MachineRegistry.SHAFT.getBlock())
			return 32F;
		if (b == MachineRegistry.GEARBOX.getBlock())
			return 32F;
		if (b == Blocks.mob_spawner)
			return 18F;
		if (b == Blocks.monster_egg)
			return 6F;
		if (b == Blocks.glowstone)
			return 8F;
		if (b == Blocks.piston)
			return 8F;
		if (b == Blocks.sticky_piston)
			return 8F;
		if (b == Blocks.lever)
			return 18F;
		if (b == Blocks.stone_button)
			return 18F;
		if (b == Blocks.stone_pressure_plate)
			return 18F;
		if (b == Blocks.heavy_weighted_pressure_plate)
			return 18F;
		if (b == Blocks.light_weighted_pressure_plate)
			return 18F;
		if (b == Blocks.lit_redstone_lamp || b == Blocks.redstone_lamp)
			return 10F;
		if (b == Blocks.iron_door)
			return 18F;

		if (b == ThaumBlockHandler.getInstance().totemID)
			return 48F;
		if (b == MekanismHandler.getInstance().cableID)
			return 20F;
		if (b == MFRHandler.getInstance().cableID)
			return 15F;
		if (b == OpenBlockHandler.getInstance().tankID)
			return 20F;
		if (b == ThermalHandler.getInstance().ductID) //fails as of newer TE, because is TileMultipart
			return 48F;
		if (MystObjects.crystal != null && b == MystObjects.crystal)
			return 20F;
		if (TwilightForestHandler.getInstance().isMazeStone(b))
			return 60F;

		if (ReikaBlockHelper.isOre(b, meta))
			return 24F;

		if (field_150914_c.contains(b))
			return 12F;
		if (((ItemBedrockShovel)ItemRegistry.BEDSHOVEL.getItemInstance()).isAcceleratedOn(b))
			return 6F;

		if (b.getMaterial() == Material.rock || b.getMaterial() == Material.iron)
			return 12F;
		if (b.getMaterial() == Material.glass)
			return 12F;
		if (b.getMaterial() == Material.ice)
			return 12F;
		if (b == BlockRegistry.DECO.getBlockInstance())
			return 12F;
		if (b instanceof BlockBasicMachine)
			return 12F;
		if (b instanceof BlockBasicMultiTE)
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

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerIcons(IIconRegister ico) {}

	@Override
	public final IIcon getIconFromDamage(int dmg) { //To get around a bug in backtools
		return RotaryCraft.instance.isLocked() ? ReikaTextureHelper.getMissingIcon() : Items.stone_pickaxe.getIconFromDamage(0);
	}

	@Override
	public int getItemEnchantability()
	{
		return ConfigRegistry.PREENCHANT.getState() ? 0 : Items.iron_pickaxe.getItemEnchantability();
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem ei) {
		ItemStack is = ei.getEntityItem();
		if (!ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			ei.playSound("random.break", 1, 1);
			ei.setDead();
		}
		return false;
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