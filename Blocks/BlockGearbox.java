/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Items.ItemDebug;
import Reika.RotaryCraft.Items.ItemMeter;
import Reika.RotaryCraft.Items.ItemScrewdriver;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;

public class BlockGearbox extends BlockModelledMachine {

	public BlockGearbox(int ID, Material mat) {
		super(ID, mat);
		//this.blockIndexInTexture = 8;
		//this.blockHardness = 0.5F;
	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityGearbox();
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		TileEntityGearbox tg = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
		if (tg == null)
			return 0;
		if (tg.type != MaterialRegistry.WOOD)
			return 0;
		return 60;
	}

	@Override
	public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double eX, double eY, double eZ)
	{
		TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
		if (gbx == null)
			return 0;
		MaterialRegistry type = gbx.type;
		switch(type) {
		case WOOD:
			return 5F;
		case STONE:
			return 10F;
		case STEEL:
		case DIAMOND:
		case BEDROCK:
			return 15F;
		}
		return 0;
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer ep, World world, int x, int y, int z)
	{
		TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
		if (gbx == null)
			return 0.01F;
		int mult = 1;
		if (ep.inventory.getCurrentItem() != null) {
			if (ep.inventory.getCurrentItem().itemID == ItemRegistry.BEDPICK.getShiftedID())
				mult = 2;
		}
		if (this.canHarvest(world, ep, x, y, z))
			return mult*0.2F/(gbx.type.ordinal()+1);
		return 0.01F/(gbx.type.ordinal()+1);
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (this.canHarvest(world, player, x, y, z));
		this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlock(x, y, z, 0);
	}

	public boolean canHarvest(World world, EntityPlayer player, int x, int y, int z)
	{
		if (player.capabilities.isCreativeMode)
			return false;
		TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
		if (gbx == null)
			return false;
		MaterialRegistry type = gbx.type;
		return type.isHarvestablePickaxe(player.inventory.getCurrentItem());
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
		if (gbx != null) {
			int type = gbx.type.ordinal();
			int ratio = gbx.getBlockMetadata()/4;
			ItemStack todrop = new ItemStack(RotaryCraft.gbxitems.itemID, 1, type+5*ratio); //drop gearbox item
			if (todrop.stackTagCompound == null)
				todrop.setTagCompound(new NBTTagCompound());
			todrop.stackTagCompound.setInteger("damage", gbx.damage);
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, todrop);
		}
		super.harvestBlock(world, ep, x, y, z, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving par5EntityLiving, ItemStack is)		//Directional code
	{
		int prevmeta = world.getBlockMetadata(x, y, z);
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", prevmeta));

		int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;

		switch (i) {
		case 0:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+2);
			break;
		case 1:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+1);
			break;
		case 2:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+3);
			break;
		case 3:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+0);
			break;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9)
	{
		if (ep.isSneaking())
			return true;

		if (ep.getCurrentEquippedItem() != null && (ep.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver || ep.getCurrentEquippedItem().getItem() instanceof ItemMeter || ep.getCurrentEquippedItem().getItem() instanceof ItemDebug)) {
			return false;
		}
		TileEntityGearbox tile = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			ItemStack fix;
			switch(tile.type) {
			case WOOD:
				fix = ItemStacks.woodgear;
				break;
			case STONE:
				fix = ItemStacks.stonegear;
				break;
			case STEEL:
				fix = ItemStacks.steelgear;
				break;
			case DIAMOND:
				fix = ItemStacks.diamondgear;
				break;
			case BEDROCK:
				fix = ItemStacks.bedrockgear;
				break;
			default:
				fix = new ItemStack(Block.stone);
				break;
			}
			if (ep.getCurrentEquippedItem() != null && (ep.getCurrentEquippedItem().itemID == fix.itemID && ep.getCurrentEquippedItem().getItemDamage() == fix.getItemDamage())) {
				tile.damage -= 1 + 20 * tile.getRandom().nextInt(18 - tile.ratio);
				if (tile.damage < 0)
					tile.damage = 0;
				if (!ep.capabilities.isCreativeMode) {
					int num = ep.getCurrentEquippedItem().stackSize;
					if (num > 1)
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, new ItemStack(fix.itemID, num-1, fix.getItemDamage()));
					else
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, null);
				}
				return false;
			}
		}

		TileEntity tileentity = world.getBlockTileEntity(x, y, z);

		if (tileentity != null)
		{
			ep.openGui(RotaryCraft.instance, GuiRegistry.MACHINE.ordinal(), world, x, y, z);
		}

		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int x, int y, int z) {
		this.setFullBlockBounds();
	}
}
