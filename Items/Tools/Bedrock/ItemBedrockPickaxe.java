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
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
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
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import com.xcompwiz.mystcraft.api.MystObjects;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ItemBedrockPickaxe extends ItemPickaxe implements IndexedItemSprites {

	private int index;

	private final ArrayList<Enchantment> forbiddenEnchants = new ArrayList();

	public ItemBedrockPickaxe(int ID, int tex) {
		super(ID, EnumToolMaterial.EMERALD);
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
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
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
	public boolean canHarvestBlock(Block b) {
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep)
	{
		if (ep.capabilities.isCreativeMode)
			return false;
		World world = ep.worldObj;
		int id = world.getBlockId(x, y, z);
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
		if (ConfigRegistry.BEDPICKSPAWNERS.getState() && id == Block.mobSpawner.blockID) {
			TileEntityMobSpawner spw = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);
			if (ConfigRegistry.SPAWNERLEAK.getState())
				ReikaSpawnerHelper.forceSpawn(spw, 12+itemRand.nextInt(25));
			ItemStack item = new ItemStack(RotaryCraft.spawner);
			ReikaSpawnerHelper.addMobNBTToItem(item, spw);
			ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), item);
			//world.setBlock(x, y, z, 0);
			//world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 1.25F);
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
				;//ReikaRenderHelper.spawnDropParticles(world, x, y, z, Block.mobSpawner, meta);
			}
			return false;
		}
		if (id != Block.silverfish.blockID)
			return false;
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
		world.setBlock(x, y, z, 0);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaRenderHelper.spawnDropParticles(world, x, y, z, Block.silverfish, meta);
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
		world.setBlock(x, y, z, 0);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ReikaRenderHelper.spawnDropParticles(world, x, y, z, block.itemID, block.getItemDamage());
		}
		ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), block);
	}

	@Override
	public float getStrVsBlock(ItemStack is, Block b, int meta) {
		if (b == null)
			return 0;
		if (b.blockID == Block.obsidian.blockID)
			return 48F;
		if (b.blockID == RotaryCraft.blastpane.blockID)
			return 32F;
		if (b.blockID == RotaryCraft.blastglass.blockID)
			return 48F;
		if (b.blockID == MachineRegistry.SHAFT.getBlockID())
			return 32F;
		if (b.blockID == MachineRegistry.GEARBOX.getBlockID())
			return 32F;
		if (b.blockID == Block.mobSpawner.blockID)
			return 18F;
		if (b.blockID == Block.silverfish.blockID)
			return 6F;
		if (b.blockID == Block.glowStone.blockID)
			return 8F;
		if (b.blockID == Block.pistonBase.blockID)
			return 8F;
		if (b.blockID == Block.pistonStickyBase.blockID)
			return 8F;
		if (b.blockID == Block.lever.blockID)
			return 18F;
		if (b.blockID == Block.stoneButton.blockID)
			return 18F;
		if (b.blockID == Block.pressurePlateStone.blockID)
			return 18F;
		if (b.blockID == Block.pressurePlateIron.blockID)
			return 18F;
		if (b.blockID == Block.pressurePlateGold.blockID)
			return 18F;
		if (b.blockID == Block.redstoneLampActive.blockID || b.blockID == Block.redstoneLampIdle.blockID)
			return 10F;
		if (b.blockID == Block.doorIron.blockID)
			return 18F;

		if (b.blockID == ThaumBlockHandler.getInstance().totemID)
			return 48F;
		if (b.blockID == MekanismHandler.getInstance().cableID)
			return 20F;
		if (b.blockID == MFRHandler.getInstance().cableID)
			return 15F;
		if (b.blockID == OpenBlockHandler.getInstance().tankID)
			return 20F;
		if (b.blockID == ThermalHandler.getInstance().ductID) //fails as of newer TE, because is TileMultipart
			return 48F;
		if (MystObjects.crystal != null && b.blockID == MystObjects.crystal.blockID)
			return 20F;
		if (TwilightForestHandler.getInstance().isMazeStone(b))
			return 60F;

		if (ReikaBlockHelper.isOre(b.blockID, meta))
			return 24F;
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if (blocksEffectiveAgainst[i] == b)
				return 12F;
		}
		for (int i = 0; i < ItemSpade.blocksEffectiveAgainst.length; i++) { //Also can dig dirt, etc
			if (ItemSpade.blocksEffectiveAgainst[i] == b)
				return 6F;
		}
		if (b.blockMaterial == Material.rock || b.blockMaterial == Material.iron)
			return 12F;
		if (b.blockMaterial == Material.glass)
			return 12F;
		if (b.blockMaterial == Material.ice)
			return 12F;
		if (b == RotaryCraft.decoblock)
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
	public final void registerIcons(IconRegister ico) {}

	@Override
	public final Icon getIconFromDamage(int dmg) { //To get around a bug in backtools
		return RotaryCraft.instance.isLocked() ? ReikaTextureHelper.getMissingIcon() : Item.pickaxeStone.getIconFromDamage(0);
	}

	@Override
	public int getItemEnchantability()
	{
		return ConfigRegistry.PREENCHANT.getState() ? 0 : Item.pickaxeIron.getItemEnchantability();
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
}
