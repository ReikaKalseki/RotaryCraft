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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.ReikaModelledBreakFX;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBedrockPickaxe extends ItemPickaxe implements IndexedItemSprites {

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

	@Override
	public boolean canHarvestBlock(Block b) {
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer ep)
	{
		World world = ep.worldObj;
		if (world.getBlockId(x, y, z) != Block.silverfish.blockID)
			return false;
		int meta = world.getBlockMetadata(x, y, z);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "dig.stone", 1F, 0.85F);
		world.setBlock(x, y, z, 0);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			this.spawnDropParticles(world, x, y, z);
		}
		ItemStack drop;
		switch(meta) {
		case 0:
			drop = new ItemStack(Block.stone);
			break;
		case 1:
			drop = new ItemStack(Block.cobblestone);
			break;
		case 2:
			drop = new ItemStack(Block.stoneBrick);
			break;
		default:
			drop = null;
		}
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

	@SideOnly(Side.CLIENT)
	private void spawnDropParticles(World world, int x, int y, int z) {
		Icon ico = new RenderBlocks().getBlockIcon(Block.silverfish);
		for (int i = 0; i < 16; i++) {
			Minecraft.getMinecraft().effectRenderer.addEffect(new ReikaModelledBreakFX(world, x+itemRand.nextDouble(), y+itemRand.nextDouble(), z+itemRand.nextDouble(), -1+itemRand.nextDouble()*2, 2, -1+itemRand.nextDouble()*2, Block.silverfish, 0, world.getBlockMetadata(x, y, z), Minecraft.getMinecraft().renderEngine, "/terrain.png", ico.getInterpolatedU(0), ico.getInterpolatedV(0)));
		}
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

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerIcons(IconRegister ico) {}
}
