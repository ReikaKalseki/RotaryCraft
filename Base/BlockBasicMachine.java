/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeHooks;
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DartItemHandler;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Items.Tools.ItemDebug;
import Reika.RotaryCraft.Items.Tools.ItemMeter;
import Reika.RotaryCraft.Items.Tools.ItemScrewdriver;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;


public abstract class BlockBasicMachine extends BlockContainer implements SidedTextureIndex {

	protected Random par5Random = new Random();

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public Icon[][] icons = new Icon[16][6];

	public BlockBasicMachine(int ID, Material par3Material) {
		super(ID, par3Material);
		this.setHardness(4F);
		this.setResistance(15F);
		this.setLightValue(0F);
		if (par3Material == Material.iron)
			this.setStepSound(soundMetalFootstep);
	}

	public final AxisAlignedBB getBlockAABB(int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1);
	}

	public final void setFullBlockBounds() {
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public abstract Icon getIcon(int s, int meta);
	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	public abstract void registerIcons(IconRegister par1IconRegister);

	public abstract TileEntity createNewTileEntity(World var1);

	@Override
	public int getRenderType() {
		return 0;//ClientProxy.BlockSheetTexRenderID;
	}

	@Override
	public final int damageDropped(int par1)
	{
		return 0;
	}

	@Override
	public final int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public final int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}

	@Override
	public final void addCreativeItems(ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		ItemStack is = ep.getCurrentEquippedItem();
		if (ModList.DARTCRAFT.isLoaded() && DartItemHandler.getInstance().isWrench(is)) {
			ep.setCurrentItemOrArmor(0, null);
			ep.playSound("random.break", 1, 1);
			ep.attackEntityFrom(DamageSource.inWall, 4);
			ReikaChatHelper.write("Your tool has shattered into a dozen pieces.");
			return true;
		}
		if (ep.isSneaking() && !(te instanceof TileEntityCaveFinder))
			return false;
		if (ep.getCurrentEquippedItem() != null && (ep.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver || ep.getCurrentEquippedItem().getItem() instanceof ItemMeter || ep.getCurrentEquippedItem().getItem() instanceof ItemDebug)) {
			return false;
		}
		if (te instanceof TileEntityEngine) {
			TileEntityEngine tile = (TileEntityEngine)te;
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.turbine)) {
				if (tile.type == EnumEngineType.JET && tile.FOD > 0) {
					tile.repairJet();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.compressor)) {
				if (tile.type == EnumEngineType.JET && tile.FOD > 0) {
					tile.repairJetPartial();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && is.stackSize == 1) {
				if (is.itemID == Item.bucketEmpty.itemID) {
					if (tile.type.isEthanolFueled()) {
						if (tile.getFuelLevel() >= ItemFuelLubeBucket.ETHANOL_VALUE) {
							ep.setCurrentItemOrArmor(0, ItemStacks.ethanolbucket);
							tile.subtractFuel(ItemFuelLubeBucket.ETHANOL_VALUE*RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
					if (tile.type.isJetFueled()) {
						if (tile.getFuelLevel() >= ItemFuelLubeBucket.JET_VALUE) {
							ep.setCurrentItemOrArmor(0, ItemStacks.fuelbucket);
							tile.subtractFuel(ItemFuelLubeBucket.JET_VALUE*RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
				}
				if (tile.type.isJetFueled()) {
					if (is.itemID == ItemStacks.fuelbucket.itemID && is.getItemDamage() == ItemStacks.fuelbucket.getItemDamage()) {
						if (tile.getFuelLevel() <= tile.FUELCAP-ItemFuelLubeBucket.JET_VALUE) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
							tile.addFuel(ItemFuelLubeBucket.JET_VALUE*RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add fuel!");
						}
						return true;
					}
				}
				if (tile.type.isEthanolFueled()) {
					if (is.itemID == ItemStacks.ethanolbucket.itemID && is.getItemDamage() == ItemStacks.ethanolbucket.getItemDamage()) {
						if (tile.getFuelLevel() <= tile.FUELCAP-ItemFuelLubeBucket.ETHANOL_VALUE) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
							tile.addFuel(ItemFuelLubeBucket.ETHANOL_VALUE*RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add fuel!");
						}
						return true;
					}
				}
			}
		}
		if (te != null && RotaryAux.hasGui(world, x, y, z, ep) && ((RotaryCraftTileEntity)te).isUseableByPlayer(ep)) {
			ep.openGui(RotaryCraft.instance, GuiRegistry.MACHINE.ordinal(), world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public final ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		int id = this.idPicked(world, x, y, z);
		int meta = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(id, meta);
		if (m == null)
			return null;
		if (id == 0)
			return null;
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine eng = (TileEntityEngine)world.getBlockTileEntity(x, y, z);
			if (eng == null)
				return null;
			if (eng.type == null)
				return null;
			return new ItemStack(RotaryCraft.engineitems.itemID, 1, eng.type.ordinal());
		}
		if (m == MachineRegistry.GEARBOX) {
			TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
			meta = gbx.getBlockMetadata();
			if (gbx.type == null)
				return null;
			int dmg = gbx.type.ordinal();
			switch(gbx.ratio) {
			case 4:
				dmg += 5;
				break;
			case 8:
				dmg += 10;
				break;
			case 16:
				dmg += 15;
				break;
			}
			return new ItemStack(RotaryCraft.gbxitems.itemID, 1, dmg);
		}
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft sha = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
			meta = sha.getBlockMetadata();
			if (meta >= 6)
				return new ItemStack(RotaryCraft.shaftitems.itemID, 1, RotaryNames.shaftItemNames.length-1);
			if (sha.type == null)
				return null;
			return new ItemStack(RotaryCraft.shaftitems.itemID, 1, sha.type.ordinal());
		}
		if (m == MachineRegistry.FLYWHEEL) {
			TileEntityFlywheel fly = (TileEntityFlywheel)world.getBlockTileEntity(x, y, z);
			meta = fly.getBlockMetadata();
			return new ItemStack(RotaryCraft.flywheelitems.itemID, 1, meta/4);
		}
		if (m == MachineRegistry.ADVANCEDGEARS) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)world.getBlockTileEntity(x, y, z);
			meta = adv.getBlockMetadata();
			return new ItemStack(RotaryCraft.advgearitems.itemID, 1, meta/4);
		}
		if (m.isPipe()) {
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, meta);
		}
		return null;
	}

	@Override
	public final boolean canBeReplacedByLeaves(World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public final boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	{
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
	{
		return 0;
	}

	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return ForgeHooks.canHarvestBlock(this, player, meta);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof IInventory)
			ReikaItemHelper.dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	public final String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}
}
