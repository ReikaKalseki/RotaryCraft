/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Items.Tools.ItemDebug;
import Reika.RotaryCraft.Items.Tools.ItemMeter;
import Reika.RotaryCraft.Items.Tools.ItemScrewdriver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockShaft extends BlockModelledMachine {


	public BlockShaft(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityShaft(meta < 5 ? MaterialRegistry.setType(meta) : MaterialRegistry.STEEL);
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		TileEntityShaft ts = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (ts == null)
			return 0;
		if (ts.getShaftType().isFlammable())
			return 60;
		return 0;
	}

	@Override
	public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double eX, double eY, double eZ)
	{
		TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (sha == null)
			return 0;
		MaterialRegistry type = sha.getShaftType();
		switch(type) {
		case WOOD:
			return 3F;
		case STONE:
			return 8F;
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
		TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (sha == null)
			return 0.01F;
		int mult = 1;
		if (ep.inventory.getCurrentItem() != null) {
			if (ep.inventory.getCurrentItem().getItem() == ItemRegistry.BEDPICK.getItemInstance())
				mult = 4;
		}
		if (this.canHarvest(world, ep, x, y, z))
			return mult*0.2F/(sha.getShaftType().ordinal()+1);
		return 0.01F/(sha.getShaftType().ordinal()+1);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harv)
	{
		if (this.canHarvest(world, player, x, y, z))
			this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9)
	{
		if (ep.isSneaking())
			return true;
		if (ep.getCurrentEquippedItem() != null && (ep.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver || ep.getCurrentEquippedItem().getItem() instanceof ItemMeter || ep.getCurrentEquippedItem().getItem() instanceof ItemDebug)) {
			return false;
		}
		TileEntityShaft tile = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (tile != null) {
			ItemStack fix;
			if (tile.getShaftType() == null)
				return false;
			switch(tile.getShaftType()) {
			case WOOD:
				fix = new ItemStack(Items.stick);
				break;
			case STONE:
				fix = ItemStacks.stonerod;
				break;
			case STEEL:
				fix = ItemStacks.shaftitem;
				break;
			case DIAMOND:
				fix = ItemStacks.diamondshaft;
				break;
			case BEDROCK:
				fix = ItemStacks.bedrockshaft;
				break;
			default:
				fix = new ItemStack(Blocks.stone);
				break;
			}
			if (ep.getCurrentEquippedItem() != null && ReikaItemHelper.matchStacks(fix, ep.getCurrentEquippedItem())) {
				tile.repair();
				if (!ep.capabilities.isCreativeMode) {
					int num = ep.getCurrentEquippedItem().stackSize;
					if (num > 1)
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, ReikaItemHelper.getSizedItemStack(fix, num-1));
					else
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, null);
				}
				return false;
			}
		}
		return false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (sha != null) {
			if (sha.failed()) {
				ItemStack todrop = null;
				if (par5Random.nextInt(8) == 0) {
					switch(sha.getShaftType()) {
					case WOOD:
						todrop = new ItemStack(Blocks.planks, 5, 0);
						break;
					case STONE:
						todrop = ReikaItemHelper.getSizedItemStack(ReikaItemHelper.cobbleSlab, 5);
						break;
					case STEEL:
						todrop = ItemStacks.mount.copy();	//drop mount
						break;
					case DIAMOND:
						todrop = ItemStacks.mount.copy();	//drop mount
						break;
					case BEDROCK:
						todrop = ItemStacks.mount.copy();	//drop mount
						break;
					}
					EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
					item.delayBeforeCanPickup = 10;
					if (!world.isRemote && !ep.capabilities.isCreativeMode)
						world.spawnEntityInWorld(item);
				}
			}
			else if (sha.getBlockMetadata() < 6) {
				ItemStack todrop = ItemRegistry.SHAFT.getStackOfMetadata(sha.getShaftType().ordinal()); //drop shaft item
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
			}
			else {/*
				ItemStack todrop = new ItemStack(MachineRegistry.SHAFT.getBlock(), 1, 6); //drop shaft block (cross)
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				Items.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);*/
				ItemStack todrop = ItemStacks.shaftcross.copy(); //drop shaft cross
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack is)		//Directional code
	{
		int base = 0;
		int heldmeta = par5EntityLiving.getHeldItem().getItemDamage();
		if (heldmeta == 6)
			base = 6;
		if (MathHelper.abs(par5EntityLiving.rotationPitch) < 60) {
			int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
			while (i > 3)
				i -= 4;
			while (i < 0)
				i += 4;
			switch (i) {
			case 0:
				world.setBlockMetadataWithNotify(x, y, z, base+0, 3);
				break;
			case 1:
				world.setBlockMetadataWithNotify(x, y, z, base+3, 3);
				break;
			case 2:
				world.setBlockMetadataWithNotify(x, y, z, base+2, 3);
				break;
			case 3:
				world.setBlockMetadataWithNotify(x, y, z, base+1, 3);
				break;
			}
		}
		else { //Looking up/down
			if (base == 6) { //cross
				world.setBlockMetadataWithNotify(x, y, z, base+0, 3);
				return;
			}
			if (par5EntityLiving.rotationPitch > 0)
				world.setBlockMetadataWithNotify(x, y, z, 4, 3); //set to up
			else
				world.setBlockMetadataWithNotify(x, y, z, 5, 3); //set to down
		}
	}

	public boolean canHarvest(World world, EntityPlayer player, int x, int y, int z)
	{
		if (player.capabilities.isCreativeMode)
			return false;
		TileEntityShaft ts = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (ts == null)
			return false;
		MaterialRegistry type = ts.getShaftType();
		return type != null ? type.isHarvestablePickaxe(player.inventory.getCurrentItem()) : false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		this.setFullBlockBounds();
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)iba.getTileEntity(x, y, z);
		if (te.getBlockMetadata() < 6)
			return;
		this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;
		maxy -= 0.1825F;

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntityShaft tile = (TileEntityShaft)world.getTileEntity(x, y, z);
		if (tile == null)
			return ret;
		ret.add(ItemRegistry.SHAFT.getStackOfMetadata(tile.getShaftType().ordinal()));
		return ret;
	}
}