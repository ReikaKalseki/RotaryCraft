/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;
import java.util.List;
import java.util.Random;

import mcp.mobius.waila.api.IWailaBlock;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.Fluid;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Base.BlockTEBase;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DartItemHandler;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidProducer;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;


public abstract class BlockBasicMachine extends BlockTEBase implements SidedTextureIndex, IWailaBlock {

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

	@Override
	public final boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public abstract Icon getIcon(int s, int meta);
	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	public abstract void registerIcons(IconRegister par1IconRegister);

	@Override
	public int getRenderType() {
		return 0;//RotaryCraft.proxy.cubeRender;//ClientProxy.BlockSheetTexRenderID;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int side, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, ep, side, par7, par8, par9);
		if (RotaryCraft.instance.isLocked())
			return false;
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
		ItemRegistry ir = ItemRegistry.getEntry(is);
		if (ir != null && ir.overridesRightClick(is)) {
			return false;
		}
		if (te instanceof TileEntityAdvancedGear) {
			TileEntityAdvancedGear tile = (TileEntityAdvancedGear)te;
			if (tile.getGearType().isLubricated() && tile.canAcceptAnotherLubricantBucket()) {
				if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.lubebucket)) {
					tile.addLubricant(1000);
					if (!ep.capabilities.isCreativeMode)
						ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
					return true;
				}
			}
		}
		else if (te instanceof TileEntityEngine) {
			if (is != null && is.itemID == ItemRegistry.FUEL.getShiftedID())
				return false;
			TileEntityEngine tile = (TileEntityEngine)te;
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.turbine)) {
				if (tile.getEngineType() == EngineType.JET && ((TileEntityJetEngine)tile).FOD > 0) {
					((TileEntityJetEngine)tile).repairJet();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.compressor)) {
				if (tile.getEngineType() == EngineType.JET && ((TileEntityJetEngine)tile).FOD > 0) {
					((TileEntityJetEngine)tile).repairJetPartial();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && is.stackSize == 1) {
				if (is.itemID == Item.bucketEmpty.itemID) {
					if (tile.getEngineType().isEthanolFueled()) {
						if (tile.getFuelLevel() >= RotaryConfig.MILLIBUCKET) {
							ep.setCurrentItemOrArmor(0, ItemStacks.ethanolbucket.copy());
							tile.subtractFuel(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
					if (tile.getEngineType().isJetFueled()) {
						if (tile.getFuelLevel() >= RotaryConfig.MILLIBUCKET) {
							ep.setCurrentItemOrArmor(0, ItemStacks.fuelbucket.copy());
							tile.subtractFuel(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
					if (tile.getEngineType().requiresLubricant()) {
						if (tile.getLube() >= RotaryConfig.MILLIBUCKET) {
							ep.setCurrentItemOrArmor(0, ItemStacks.lubebucket.copy());
							tile.removeLubricant(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
				}
				if (tile.getEngineType().isJetFueled()) {
					if (ReikaItemHelper.matchStacks(is, ItemStacks.fuelbucket)) {
						if (tile.getFuelLevel() <= tile.FUELCAP-RotaryConfig.MILLIBUCKET) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
							tile.addFuel(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add fuel!");
						}
						return true;
					}
				}
				if (tile.getEngineType().isEthanolFueled()) {
					if (ReikaItemHelper.matchStacks(is, ItemStacks.ethanolbucket)) {
						if (tile.getFuelLevel() <= tile.FUELCAP-RotaryConfig.MILLIBUCKET) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
							tile.addFuel(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add fuel!");
						}
						return true;
					}
				}
				if (tile.getEngineType().requiresLubricant()) {
					if (ReikaItemHelper.matchStacks(is, ItemStacks.lubebucket)) {
						if (tile.getLube() <= tile.LUBECAP-RotaryConfig.MILLIBUCKET) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
							tile.addLubricant(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add lubricant!");
						}
						return true;
					}
				}
				if (tile.getEngineType().needsWater()) {
					if (is != null && is.itemID == Item.bucketWater.itemID) {
						if (tile.getWater() <= tile.CAPACITY-RotaryConfig.MILLIBUCKET) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
							tile.addWater(RotaryConfig.MILLIBUCKET);
						}
						else {
							ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add water!");
						}
						return true;
					}
				}
			}
		}
		if (te != null && RotaryAux.hasGui(world, x, y, z, ep) && ((RotaryCraftTileEntity)te).isPlayerAccessible(ep)) {
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
			if (eng.getEngineType() == null)
				return null;
			return new ItemStack(RotaryCraft.engineitems.itemID, 1, eng.getEngineType().ordinal());
		}
		if (m == MachineRegistry.GEARBOX) {
			TileEntityGearbox gbx = (TileEntityGearbox)world.getBlockTileEntity(x, y, z);
			meta = gbx.getBlockMetadata();
			if (gbx.getGearboxType() == null)
				return null;
			int dmg = gbx.getGearboxType().ordinal();
			switch(gbx.getRatio()) {
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
				return new ItemStack(RotaryCraft.shaftitems.itemID, 1, RotaryNames.getNumberShaftTypes()-1);
			if (sha.getShaftType() == null)
				return null;
			return new ItemStack(RotaryCraft.shaftitems.itemID, 1, sha.getShaftType().ordinal());
		}
		if (m == MachineRegistry.FLYWHEEL) {
			TileEntityFlywheel fly = (TileEntityFlywheel)world.getBlockTileEntity(x, y, z);
			meta = fly.getBlockMetadata();
			return new ItemStack(RotaryCraft.flywheelitems.itemID, 1, meta/4);
		}
		if (m == MachineRegistry.ADVANCEDGEARS) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)world.getBlockTileEntity(x, y, z);
			meta = adv.getBlockMetadata();
			ItemStack is = new ItemStack(RotaryCraft.advgearitems.itemID, 1, meta/4);
			if (adv.isBedrockCoil()) {
				is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setBoolean("bedrock", true);
			}
			return is;
		}
		if (m.isPipe()) {
			return ((TileEntityPiping)world.getBlockTileEntity(x, y, z)).getMachine().getCraftedProduct();
		}
		return m.getCraftedProduct();
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

	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return MachineRegistry.getMachineFromIDandMetadata(blockID, accessor.getMetadata()).getCraftedProduct();
	}

	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		World world = acc.getWorld();
		MovingObjectPosition mov = acc.getPosition();
		if (mov != null) {
			int x = mov.blockX;
			int y = mov.blockY;
			int z = mov.blockZ;
			currenttip.add(EnumChatFormatting.WHITE+this.getPickBlock(mov, world, x, y, z).getDisplayName());
		}
		return currenttip;
	}

	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)acc.getTileEntity();
		if (te instanceof TemperatureTE && !(te instanceof TileEntityEngine))
			currenttip.add(String.format("Temperature: %dC", ((TemperatureTE) te).getTemperature()));
		if (te instanceof PressureTE)
			currenttip.add(String.format("Pressure: %dkPa", ((PressureTE) te).getPressure()));
		if (te instanceof PoweredLiquidIO) {
			PoweredLiquidIO liq = (PoweredLiquidIO)te;
			Fluid in = liq.getFluidInInput();
			Fluid out = liq.getFluidInOutput();
			int amtin = liq.getInputLevel();
			int amtout = liq.getOutputLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amtin, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			String output = in != null ? String.format("%d/%d mB of %s", amtout, liq.getCapacity(), out.getLocalizedName()) : "Empty";
			currenttip.add("Input Tank: "+input);
			currenttip.add("Output Tank: "+output);
		}
		if (te instanceof PoweredLiquidReceiver) {
			PoweredLiquidReceiver liq = (PoweredLiquidReceiver)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amt, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		if (te instanceof PoweredLiquidProducer) {
			PoweredLiquidProducer liq = (PoweredLiquidProducer)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amt, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		if (te instanceof TileEntityGearbox) {
			TileEntityGearbox gbx = (TileEntityGearbox)te;
			if (gbx.getGearboxType().isDamageableGear()) {
				currenttip.add(String.format("Damage: %d%s", gbx.getDamagePercent(), "%"));
			}
			currenttip.add(String.format("Lubricant: %d mB", gbx.getLubricant()));
		}
		if (te instanceof TileEntityAdvancedGear) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)te;
			if (adv.getGearType().consumesLubricant()) {
				currenttip.add(String.format("Lubricant: %d mB", adv.getLubricant()));
			}
		}
		if (te instanceof TileEntityEngine) {
			TileEntityEngine eng = (TileEntityEngine)te;
			if (eng.getEngineType().requiresLubricant()) {
				currenttip.add(String.format("Lubricant: %d mB", eng.getLube()));
			}
			if (eng.getEngineType().burnsFuel()) {
				currenttip.add(String.format("Fuel: %d mB", eng.getFuelLevel()));
			}
			if (eng instanceof TileEntityPerformanceEngine) {
				currenttip.add(String.format("Additives: %d mB", ((TileEntityPerformanceEngine)eng).additives));
				currenttip.add(String.format("Water: %d mB", ((TileEntityPerformanceEngine)eng).getWater()));
			}
			if (eng.hasTemperature()) {
				currenttip.add(String.format("Temperature: %dC", eng.getTemperature()));
			}
		}
		return currenttip;
	}

	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		String s1 = EnumChatFormatting.ITALIC.toString();
		String s2 = EnumChatFormatting.BLUE.toString();
		currenttip.add(s2+s1+"RotaryCraft");
		return currenttip;
	}
}
