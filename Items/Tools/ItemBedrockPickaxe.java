/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

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
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DartOreHandler;
import Reika.DragonAPI.ModInteract.ThaumBlockHandler;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModInteract.TwilightBlockHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import Reika.RotaryCraft.Base.BlockBasicMultiTE;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ItemBedrockPickaxe extends ItemPickaxe implements IndexedItemSprites {

	private int index;

	public ItemBedrockPickaxe(int ID, int tex) {
		super(ID, EnumToolMaterial.EMERALD);
		this.setIndex(tex);
		this.setCreativeTab(RotaryCraft.tabRotaryItems);
		maxStackSize = 1;
		this.setMaxDamage(0);
		efficiencyOnProperMaterial = 12F;
		damageVsEntity = 5;
		this.setNoRepair();
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
	public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
		this.forceSilkTouch(is);
	}

	private void forceSilkTouch(ItemStack is) {
		if (!ReikaEnchantmentHelper.hasEnchantment(Enchantment.silkTouch, is)) {
			is.stackTagCompound = null;
			is.addEnchantment(Enchantment.silkTouch, 1);
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
		if (APIRegistry.THAUMCRAFT.conditionsMet() && ThaumOreHandler.getInstance().isThaumOre(block) && ConfigRegistry.MODORES.getState() && ThaumOreHandler.getInstance().isShardOre(block)) {
			world.setBlock(x, y, z, 0);
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
				ReikaRenderHelper.spawnModBlockDropParticles(world, x, y, z, ThaumOreHandler.getInstance().oreID);
			}
			ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), block);
			return true;
		}
		if (APIRegistry.DARTCRAFT.conditionsMet() && DartOreHandler.getInstance().isDartOre(block) && ConfigRegistry.MODORES.getState()) {
			world.setBlock(x, y, z, 0);
			world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
				ReikaRenderHelper.spawnModBlockDropParticles(world, x, y, z, DartOreHandler.getInstance().oreID);
			}
			ReikaItemHelper.dropItem(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), block);
			return true;
		}
		if (ConfigRegistry.BEDPICKSPAWNERS.getState() && id == Block.mobSpawner.blockID) {
			TileEntityMobSpawner spw = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);
			if (ConfigRegistry.SPAWNERLEAK.getState())
				ReikaSpawnerHelper.forceSpawn(spw, world, 12+itemRand.nextInt(25));
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
		si.setEntityHealth(0);
		if (world.isRemote)
			world.spawnEntityInWorld(si);
		world.playSoundAtEntity(si, "mob.silverfish.kill", 0.5F, 1);
		ReikaWorldHelper.splitAndSpawnXP(world, x+0.5F, y+0.125F, z+0.5F, si.experienceValue);
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
		if (par2Block.blockID == Block.silverfish.blockID)
			return 6F;
		if (par2Block.blockID == Block.glowStone.blockID)
			return 6F;

		if (par2Block.blockID == ThaumBlockHandler.getInstance().totemID)
			return 48F;
		if (TwilightBlockHandler.getInstance().isMazeStone(par2Block))
			return 48F;

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
		if (par2Block.blockMaterial == Material.glass)
			return 12F;
		if (par2Block == RotaryCraft.decoblock)
			return 12F;
		if (par2Block instanceof BlockBasicMachine)
			return 12F;
		if (par2Block instanceof BlockBasicMultiTE)
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
	public final int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem ei) {
		ItemStack is = ei.getEntityItem();
		this.forceSilkTouch(is);
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerIcons(IconRegister ico) {}

	@Override
	public final Icon getIconFromDamage(int dmg) { //To get around a bug in backtools
		return Item.pickaxeStone.getIconFromDamage(0);
	}
}
