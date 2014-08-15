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
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Items.Tools.ItemDebug;
import Reika.RotaryCraft.Items.Tools.ItemMeter;
import Reika.RotaryCraft.Items.Tools.ItemScrewdriver;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGearbox extends BlockModelledMachine {

	public BlockGearbox(Material mat) {
		super(mat);
		//this.blockIndexInTexture = 8;
		//this.blockHardness = 0.5F;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityGearbox(MaterialRegistry.setType(meta));
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		TileEntityGearbox tg = (TileEntityGearbox)world.getTileEntity(x, y, z);
		if (tg == null)
			return 0;
		if (tg.getGearboxType().isFlammable())
			return 60;
		return 0;
	}

	@Override
	public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double eX, double eY, double eZ)
	{
		TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
		if (gbx == null)
			return 0;
		MaterialRegistry type = gbx.getGearboxType();
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
		TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
		if (gbx == null)
			return 0.01F;
		int mult = 1;
		if (ep.inventory.getCurrentItem() != null) {
			if (ep.inventory.getCurrentItem().getItem() == ItemRegistry.BEDPICK.getItemInstance())
				mult = 2;
		}
		if (this.canHarvest(world, ep, x, y, z))
			return mult*0.2F/(gbx.getGearboxType().ordinal()+1);
		return 0.01F/(gbx.getGearboxType().ordinal()+1);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harv)
	{
		if (this.canHarvest(world, player, x, y, z))
			this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlockToAir(x, y, z);
	}

	public boolean canHarvest(World world, EntityPlayer player, int x, int y, int z)
	{
		if (player.capabilities.isCreativeMode)
			return false;
		TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
		if (gbx == null)
			return false;
		MaterialRegistry type = gbx.getGearboxType();
		return type.isHarvestablePickaxe(player.inventory.getCurrentItem());
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
		if (gbx != null) {
			int type = gbx.getGearboxType().ordinal();
			int ratio = gbx.getBlockMetadata()/4;
			ItemStack todrop = ItemRegistry.GEARBOX.getStackOfMetadata(type+5*ratio); //drop gearbox item
			if (todrop.stackTagCompound == null)
				todrop.setTagCompound(new NBTTagCompound());
			todrop.stackTagCompound.setInteger("damage", gbx.getDamage());
			todrop.stackTagCompound.setInteger("lube", gbx.getLubricant());
			ReikaItemHelper.dropItem(world, x+0.5, y+0.5, z+0.5, todrop);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack is)		//Directional code
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
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+2, 3);
			break;
		case 1:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+1, 3);
			break;
		case 2:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+3, 3);
			break;
		case 3:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+0, 3);
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
		TileEntityGearbox tile = (TileEntityGearbox)world.getTileEntity(x, y, z);
		if (tile != null) {
			ItemStack fix;
			switch(tile.getGearboxType()) {
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
				fix = new ItemStack(Blocks.stone);
				break;
			}
			ItemStack held = ep.getCurrentEquippedItem();
			if (held != null) {
				if ((ReikaItemHelper.matchStacks(fix, held))) {
					tile.repair(1 + 20 * tile.getRandom().nextInt(18 - tile.getRatio()));
					if (!ep.capabilities.isCreativeMode) {
						int num = held.stackSize;
						if (num > 1)
							ep.inventory.setInventorySlotContents(ep.inventory.currentItem, ReikaItemHelper.getSizedItemStack(fix, num-1));
						else
							ep.inventory.setInventorySlotContents(ep.inventory.currentItem, null);
					}
					return true;
				}
				else if (ReikaItemHelper.matchStacks(held, ItemStacks.lubebucket) && held.stackSize == 1) {
					if (tile.getGearboxType().isDamageableGear()) {
						int amt = 1000;
						if (tile.canTakeLubricant(amt)) {
							tile.addLubricant(amt);
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
						}
					}
					return true;
				}
			}
		}

		TileEntity tileentity = world.getTileEntity(x, y, z);

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

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
		ItemStack is = ItemRegistry.GEARBOX.getStackOfMetadata((gbx.getBlockMetadata()/4)*5+gbx.getGearboxType().ordinal());
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("damage", gbx.getDamage());
		is.stackTagCompound.setInteger("lube", gbx.getLubricant());
		ret.add(is);
		return ret;
	}
}