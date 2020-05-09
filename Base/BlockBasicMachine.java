/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Interfaces.Block.SidedTextureIndex;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.DartItemHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.Variables;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidProducer;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear.GearType;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public abstract class BlockBasicMachine extends BlockRotaryCraftMachine implements SidedTextureIndex {

	protected Random par5Random = new Random();

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public IIcon[][] icons = new IIcon[16][6];

	public BlockBasicMachine(Material par3Material) {
		super(par3Material);
		this.setHardness(4F);
		this.setResistance(15F);
		this.setLightLevel(0F);
		if (par3Material == Material.iron)
			this.setStepSound(soundTypeMetal);
	}

	@Override
	public final boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public abstract IIcon getIcon(int s, int meta);
	/* Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	public abstract void registerBlockIcons(IIconRegister par1IconRegister);

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
	public final Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;
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
		TileEntity te = world.getTileEntity(x, y, z);

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
						ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
					return true;
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
		int meta = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(this, meta);
		if (m == null)
			return null;
		if (m == MachineRegistry.ENGINE) {
			TileEntityEngine eng = (TileEntityEngine)world.getTileEntity(x, y, z);
			if (eng == null)
				return null;
			if (eng.getEngineType() == null)
				return null;
			return eng.getEngineType().getCraftedProduct();
		}
		if (m == MachineRegistry.GEARBOX) {
			TileEntityGearbox gbx = (TileEntityGearbox)world.getTileEntity(x, y, z);
			if (gbx.getGearboxType() == null)
				return null;
			return gbx.getGearboxType().getGearboxItem(gbx.getRatio());
		}
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft sha = (TileEntityShaft)world.getTileEntity(x, y, z);
			if (sha.isCross())
				return RotaryAux.getShaftCrossItem();
			if (sha.getShaftType() == null)
				return null;
			return sha.getShaftType().getShaftItem();
		}
		if (m == MachineRegistry.FLYWHEEL) {
			TileEntityFlywheel fly = (TileEntityFlywheel)world.getTileEntity(x, y, z);
			meta = fly.getBlockMetadata();
			return new ItemStack(ItemRegistry.FLYWHEEL.getItemInstance(), 1, meta/4);
		}
		if (m == MachineRegistry.ADVANCEDGEARS) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)world.getTileEntity(x, y, z);
			meta = adv.getBlockMetadata();
			ItemStack is = new ItemStack(ItemRegistry.ADVGEAR.getItemInstance(), 1, meta/4);
			if (adv.isBedrockCoil()) {
				is.stackTagCompound = new NBTTagCompound();
				is.stackTagCompound.setBoolean("bedrock", true);
			}
			return is;
		}
		if (m != null && m.isPipe()) {
			return ((TileEntityPiping)world.getTileEntity(x, y, z)).getMachine().getCraftedProduct();
		}
		return m.getCraftedProduct();
	}

	@Override
	public final boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	{
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 0;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return ForgeHooks.canHarvestBlock(this, player, meta);
	}

	public final String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}

	@ModDependent(ModList.WAILA)
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;//MachineRegistry.getMachineFromIDandMetadata(this, accessor.getMetadata()).getCraftedProduct();
	}

	@ModDependent(ModList.WAILA)
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		/*
		World world = acc.getWorld();
		MovingObjectPosition mov = acc.getPosition();
		if (mov != null) {
			int x = mov.blockX;
			int y = mov.blockY;
			int z = mov.blockZ;
			currenttip.add(EnumChatFormatting.WHITE+this.getPickBlock(mov, world, x, y, z).getDisplayName());
		}*/
		return currenttip;
	}

	@ModDependent(ModList.WAILA)
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		//if (/*LegacyWailaHelper.cacheAndReturn(acc)*/!currenttip.isEmpty())
		//	return currenttip;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)acc.getTileEntity();
		te.syncAllData(false);
		if (te instanceof TemperatureTE && !(te instanceof TileEntityEngine))
			currenttip.add(Variables.TEMPERATURE+": "+RotaryAux.formatTemperature(((TemperatureTE) te).getTemperature()));
		if (te instanceof PressureTE)
			currenttip.add(Variables.PRESSURE+": "+RotaryAux.formatPressure(((PressureTE) te).getPressure()));
		if (te instanceof PoweredLiquidIO) {
			PoweredLiquidIO liq = (PoweredLiquidIO)te;
			Fluid in = liq.getFluidInInput();
			Fluid out = liq.getFluidInOutput();
			int amtin = liq.getInputLevel();
			int amtout = liq.getOutputLevel();
			String input = in != null ? String.format("%s of %s", RotaryAux.formatLiquidFillFraction(amtin, liq.getInputCapacity()), in.getLocalizedName()) : "Empty";
			String output = out != null ? String.format("%s of %s", RotaryAux.formatLiquidFillFraction(amtout, liq.getOutputCapacity()), out.getLocalizedName()) : "Empty";
			currenttip.add("Input Tank: "+input);
			currenttip.add("Output Tank: "+output);
		}
		if (te instanceof PoweredLiquidReceiver) {
			PoweredLiquidReceiver liq = (PoweredLiquidReceiver)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%s of %s", RotaryAux.formatLiquidFillFraction(amt, liq.getCapacity()), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		if (te instanceof PoweredLiquidProducer) {
			PoweredLiquidProducer liq = (PoweredLiquidProducer)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%s of %s", RotaryAux.formatLiquidFillFraction(amt, liq.getCapacity()), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		if (te instanceof TileEntityGearbox) {
			TileEntityGearbox gbx = (TileEntityGearbox)te;
			currenttip.add(gbx.reduction ? "Torque Mode" : "Speed Mode");
			if (gbx.getGearboxType().isDamageableGear()) {
				currenttip.add(String.format("Damage: %d%s", gbx.getDamagePercent(), "%"));
			}
			if (gbx.getGearboxType().needsLubricant()) {
				String s = gbx.isLiving() ? String.format("Mana: %d%%", gbx.getLubricant()*100/gbx.getMaxLubricant()) : String.format("Lubricant: %d mB", gbx.getLubricant());
				currenttip.add(s);
			}
		}
		if (te instanceof TileEntityAdvancedGear) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)te;
			if (adv.getGearType().consumesLubricant()) {
				currenttip.add(String.format("Lubricant: %s", RotaryAux.formatLiquidAmount(adv.getLubricant())));
			}
			if (adv.getGearType() == GearType.HIGH) {
				currenttip.add(adv.torquemode ? "Torque Mode" : "Speed Mode");
			}
		}
		if (te instanceof TileEntityEngine) {
			TileEntityEngine eng = (TileEntityEngine)te;
			if (eng.getEngineType().requiresLubricant()) {
				currenttip.add(String.format("Lubricant: %s", RotaryAux.formatLiquidAmount(eng.getLube())));
			}
			if (eng.getEngineType().burnsFuel()) {
				currenttip.add(String.format("Fuel: %s", RotaryAux.formatLiquidAmount(eng.getFuelLevel())));
			}
			if (eng instanceof TileEntityPerformanceEngine) {
				currenttip.add(String.format("Additives: %s", RotaryAux.formatLiquidAmount(((TileEntityPerformanceEngine)eng).additives)));
				currenttip.add(String.format("Water: %s", RotaryAux.formatLiquidAmount(((TileEntityPerformanceEngine)eng).getWater())));
			}
			if (eng.hasTemperature()) {
				currenttip.add(Variables.TEMPERATURE+": "+RotaryAux.formatTemperature(eng.getTemperature()));
			}
		}
		return currenttip;
	}

	@ModDependent(ModList.WAILA)
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		/*
		String s1 = EnumChatFormatting.ITALIC.toString();
		String s2 = EnumChatFormatting.BLUE.toString();
		currenttip.add(s2+s1+"RotaryCraft");*/
		return currenttip;
	}
}
