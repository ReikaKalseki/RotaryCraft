/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Interfaces.Block.FluidBlockSurrogate;
import Reika.DragonAPI.Interfaces.TileEntity.AdjacentUpdateWatcher;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ReikaXPFluidHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.DartItemHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.OldTextureLoader;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Auxiliary.Interfaces.CachedConnection;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DamagingContact;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.PressureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidIO;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidProducer;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidReceiver;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.ModInterface.TileEntityDynamo;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySmokeDetector;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityDisplay;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFertilizer;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityBigFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCentrifuge;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDropProcessor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDryingBed;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityFuelConverter;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityAggregator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBedrockBreaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBusController;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPortalShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLandmine;

@Strippable(value = {"mcp.mobius.waila.api.IWailaDataProvider"})
public abstract class BlockBasicMultiTE extends BlockRotaryCraftMachine implements FluidBlockSurrogate {

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public IIcon[][][][] icons = new IIcon[16][16][6][8];

	public BlockBasicMultiTE(Material mat) {
		super(mat);
	}

	@Override
	public final boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public final IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)iba.getTileEntity(x, y, z);
		if (te == null)
			return null;
		int meta = te.getBlockMetadata();
		MachineRegistry m = te.getMachine();
		if (te.hasIconOverride())
			return te.getIconForSide(ForgeDirection.VALID_DIRECTIONS[s]);
		int machine = m.getBlockMetadata();
		//ReikaJavaLibrary.pConsole(s+": "+icons[machine][meta][s][te.getTextureStateForSide(s)].getIconName());
		return OldTextureLoader.instance.loadOldTextures() ? OldTextureLoader.instance.getOldTexture(this, m.getBlockMetadata(), s) : icons[machine][meta][s][te.getTextureStateForSide(s)];
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		try {
			return OldTextureLoader.instance.loadOldTextures() ? OldTextureLoader.instance.getOldTexture(this, meta, s) : icons[meta][0][s][0];
		}
		catch (NullPointerException e) {
			return ReikaTextureHelper.getMissingIcon();
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return ReikaTextureHelper.getMissingIcon();
		}
	}

	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	public abstract void registerBlockIcons(IIconRegister ico);

	@Override
	public int getRenderType() {
		return 0;//RotaryCraft.proxy.cubeRender;
	}

	@Override
	public Item getItemDropped(int id, Random r, int fortune) {
		return null;//return RotaryCraft.machineplacer;
	}

	@Override
	public int damageDropped(int par1)
	{
		return MachineRegistry.getMachineIndexFromIDandMetadata(this, par1);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public final boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int side, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, ep, side, par7, par8, par9);
		if (RotaryCraft.instance.isLocked())
			return false;
		world.markBlockForUpdate(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		ItemStack is = ep.getCurrentEquippedItem();

		if (ModList.DARTCRAFT.isLoaded() && DartItemHandler.getInstance().isWrench(is)) {
			ep.setCurrentItemOrArmor(0, null);
			ep.playSound("random.break", 1, 1);
			ep.attackEntityFrom(DamageSource.inWall, 2);
			ReikaChatHelper.write("Your tool has shattered into a dozen pieces.");
			return true;
		}
		if (ep.isSneaking() && !m.hasSneakActions())
			return false;
		if (is != null && ItemRegistry.isRegistered(is) && ItemRegistry.getEntry(is).overridesRightClick(is)) {
			return false;
		}
		if (is != null && is.getItem() == Items.enchanted_book && m.isEnchantable()) {
			if (((EnchantableMachine)te).applyEnchants(is)) {
				if (!ep.capabilities.isCreativeMode)
					ep.setCurrentItemOrArmor(0, null);
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
			return false;
		}
		if (m == MachineRegistry.MUSICBOX) {
			if (is != null && is.getItem() == ItemRegistry.DISK.getItemInstance()) {
				TileEntityMusicBox tile = (TileEntityMusicBox)te;
				if (is.stackTagCompound != null) {
					tile.setMusicFromDisc(is);
				}
				else {
					tile.saveMusicToDisk(is);
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.SPLITTER) {
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.bedrock2x)) {
				TileEntitySplitter tile = (TileEntitySplitter)te;
				tile.setBedrock();
				if (!ep.capabilities.isCreativeMode)
					is.stackSize--;
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.BORER) {
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.drill)) {
				TileEntityBorer tile = (TileEntityBorer)te;
				if (tile.repair()) {
					if (!ep.capabilities.isCreativeMode)
						is.stackSize--;
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.BUSCONTROLLER) {
			if (is != null) {
				if (FluidContainerRegistry.isFilledContainer(is)) {
					FluidStack f = FluidContainerRegistry.getFluidForFilledItem(is);
					if (f != null && f.getFluid().equals(FluidRegistry.getFluid("rc lubricant"))) {
						TileEntityBusController tb = (TileEntityBusController)te;
						tb.fill(ForgeDirection.DOWN, f, true);
						if (!ep.capabilities.isCreativeMode)
							ep.setCurrentItemOrArmor(0, is.getItem().getContainerItem(is));
						((TileEntityBase)te).syncAllData(true);
						return true;
					}
				}
			}
		}
		if (m == MachineRegistry.POWERBUS) {
			TileEntityPowerBus tile = (TileEntityPowerBus)te;
			if (is != null && ep.isSneaking()) {
				if (tile.insertItem(is, ForgeDirection.VALID_DIRECTIONS[side])) {
					if (!ep.capabilities.isCreativeMode)
						is.stackSize--;
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.FUELENGINE) {
			if (is != null) {
				if (FluidContainerRegistry.isFilledContainer(is)) {
					FluidStack f = FluidContainerRegistry.getFluidForFilledItem(is);
					if (f != null) {
						TileEntityFuelEngine tf = (TileEntityFuelEngine)te;
						if (f.getFluid().equals(FluidRegistry.getFluid("fuel"))) {
							tf.fill(ForgeDirection.DOWN, f, true);
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, is.getItem().getContainerItem(is));
							((TileEntityBase)te).syncAllData(true);
							return true;
						}
						else if (f.getFluid().equals(FluidRegistry.WATER)) {
							tf.addWater(f.amount);
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, is.getItem().getContainerItem(is));
							((TileEntityBase)te).syncAllData(true);
							return true;
						}
						else if (f.getFluid().equals(FluidRegistry.getFluid("rc lubricant"))) {
							tf.addLube(f.amount);
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, is.getItem().getContainerItem(is));
							((TileEntityBase)te).syncAllData(true);
							return true;
						}
					}
				}
			}
		}

		if (m == MachineRegistry.DRYING) {
			TileEntityDryingBed tr = (TileEntityDryingBed)te;
			if (!tr.isPlayerAccessible(ep))
				return false;
			if (is != null && FluidContainerRegistry.isFilledContainer(is)) {
				boolean bucket = FluidContainerRegistry.isBucket(is);
				FluidStack f = FluidContainerRegistry.getFluidForFilledItem(is);
				if (f != null) {
					Fluid fluid = f.getFluid();
					int size = is.stackSize;
					if (tr.getLevel()+size*f.amount <= tr.CAPACITY) {
						if (tr.isEmpty() || f.getFluid().equals(tr.getFluid())) {
							tr.addLiquid(fluid, size*f.amount);
							if (!ep.capabilities.isCreativeMode) {
								if (bucket)
									ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket, size, 0));
								else
									ep.setCurrentItemOrArmor(0, null);
							}
							((TileEntityBase)te).syncAllData(true);
							if (!world.isRemote)
								ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, tr, "tank");
							return true;
						}
					}
				}
			}
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tr = (TileEntityReservoir)te;
			if (!tr.isPlayerAccessible(ep))
				return false;
			if (is == null) {
				Fluid f = tr.getFluid();
				if (ep.isSneaking() && ReikaXPFluidHelper.fluidsExist() && f == ReikaXPFluidHelper.getFluid().getFluid()) {
					int amt = Math.min(Math.max(1000, Math.min(tr.getLevel()/4, 4000)), tr.getLevel());
					int xp = ReikaXPFluidHelper.getXPForAmount(amt);
					tr.removeLiquid(amt);
					ep.addExperience(xp);
					ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.orb");
					return true;
				}
			}
			else {
				if (ReikaItemHelper.matchStackWithBlock(is, Blocks.glass_pane)) {
					if (!tr.isCovered) {
						tr.isCovered = true;
						if (!ep.capabilities.isCreativeMode)
							ep.setCurrentItemOrArmor(0, ReikaItemHelper.getSizedItemStack(is, is.stackSize-1));
						((TileEntityBase)te).syncAllData(true);
						return true;
					}
				}
				else if (FluidContainerRegistry.isFilledContainer(is)) {
					FluidStack f = FluidContainerRegistry.getFluidForFilledItem(is);
					if (f != null) {
						Fluid fluid = f.getFluid();
						int size = is.stackSize;
						if (tr.getLevel()+(size-1)*f.amount <= tr.CAPACITY) {
							if (tr.isEmpty()) {
								tr.addLiquid(size*f.amount, fluid);
								if (!ep.capabilities.isCreativeMode) {
									ItemStack ret = FluidContainerRegistry.drainFluidContainer(is);
									ep.setCurrentItemOrArmor(0, ret != null ? ReikaItemHelper.getSizedItemStack(ret, size) : null);
								}
								((TileEntityBase)te).syncAllData(true);
								if (!world.isRemote)
									ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, tr, "tank");
								return true;
							}
							else if (f.getFluid().equals(tr.getFluid())) {
								tr.addLiquid(size*f.amount, fluid);
								if (!ep.capabilities.isCreativeMode) {
									ItemStack ret = FluidContainerRegistry.drainFluidContainer(is);
									ep.setCurrentItemOrArmor(0, ret != null ? ReikaItemHelper.getSizedItemStack(ret, size) : null);
								}
								((TileEntityBase)te).syncAllData(true);
								if (!world.isRemote)
									ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, tr, "tank");
								return true;
							}
						}
					}
				}
				else if (FluidContainerRegistry.isEmptyContainer(is) && !tr.isEmpty()) {
					int size = is.stackSize;
					FluidStack stack = tr.getContents();
					ItemStack ret = FluidContainerRegistry.fillFluidContainer(stack, is);
					if (ret != null) {
						int amt = FluidContainerRegistry.getFluidForFilledItem(ret).amount*size;
						if (tr.getLevel() >= amt) {
							tr.removeLiquid(amt);
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, ReikaItemHelper.getSizedItemStack(ret, size));
							((TileEntityBase)te).syncAllData(true);
							if (!world.isRemote)
								ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, tr, "tank");
							return true;
						}
					}
				}
				else if (is.getItem() == Items.glass_bottle) {
					int size = is.stackSize;
					if (tr.getLevel() > 0 && tr.getFluid().equals(FluidRegistry.WATER)) {
						ep.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, size, 0));
						((TileEntityBase)te).syncAllData(true);
						return true;
					}
				}
				else if (is.getItem() == ItemRegistry.FUEL.getItemInstance()) {
					return false;
				}
			}
		}
		if (m == MachineRegistry.SCALECHEST) {
			TileEntityScaleableChest tc = (TileEntityScaleableChest)te;
			if (!tc.isUseableByPlayer(ep))
				return false;
		}
		if (m == MachineRegistry.BEDROCKBREAKER && !ep.isSneaking()) {
			TileEntityBedrockBreaker tb = (TileEntityBedrockBreaker)te;
			tb.dropInventory();
			((TileEntityBase)te).syncAllData(true);
			return true;
		}
		if (m == MachineRegistry.EXTRACTOR) {
			TileEntityExtractor ex = (TileEntityExtractor)te;
			if (is != null) {
				if (is.getItem() == Items.water_bucket && is.stackSize == 1 && ex.getLevel()+1000 <= ex.CAPACITY) {
					ex.addLiquid(1000);
					if (!ep.capabilities.isCreativeMode) {
						ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
					}
					((TileEntityBase)te).syncAllData(true);
					return true;
				}
				else if (ReikaItemHelper.matchStacks(is, ItemStacks.bedrockdrill)) {
					if (ex.upgrade() && !ep.capabilities.isCreativeMode)
						is.stackSize--;
					((TileEntityBase)te).syncAllData(true);
					return true;
				}
			}
		}
		if (m == MachineRegistry.PULSEJET) {
			TileEntityPulseFurnace ex = (TileEntityPulseFurnace)te;
			int f = ex.getFuel();
			if (is != null && f+1000*is.stackSize <= ex.MAXFUEL && ReikaItemHelper.matchStacks(is, ItemStacks.fuelbucket)) {
				ex.addFuel(1000*is.stackSize);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket, is.stackSize, 0));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
			int water = ex.getWater();
			if (water+1000 <= ex.CAPACITY && is != null && is.getItem() == Items.water_bucket) {
				ex.addWater(1000);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.FERMENTER) {
			TileEntityFermenter fm = (TileEntityFermenter)te;
			if (fm.getLevel()+1000 <= fm.CAPACITY && is != null && is.getItem() == Items.water_bucket) {
				fm.addLiquid(1000);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.OBSIDIAN) {
			TileEntityObsidianMaker fm = (TileEntityObsidianMaker)te;
			if (fm.getWater()+1000 <= fm.CAPACITY && is != null && is.getItem() == Items.water_bucket) {
				fm.addWater(1000);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
			else if (fm.getLava()+1000 <= fm.CAPACITY && is != null && is.getItem() == Items.lava_bucket) {
				fm.addLava(1000);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.FERTILIZER) {
			TileEntityFertilizer fm = (TileEntityFertilizer)te;
			if (fm.getLevel()+1000 <= fm.getCapacity() && is != null && is.getItem() == Items.water_bucket) {
				fm.addLiquid(1000);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.BIGFURNACE) {
			TileEntityBigFurnace bf = (TileEntityBigFurnace)te;
			if (bf.getLevel()+1000 <= bf.getCapacity() && is != null && is.getItem() == Items.lava_bucket) {
				bf.addLiquid(1000);
				if (!ep.capabilities.isCreativeMode) {
					ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.EMP) {
			TileEntityEMP tp = (TileEntityEMP)te;
			tp.updateListing();
			((TileEntityBase)te).syncAllData(true);
			return true;
		}
		if (m == MachineRegistry.FUELENHANCER) {
			TileEntityFuelConverter tf = (TileEntityFuelConverter)te;
			if (is != null) {
				FluidStack liq = FluidContainerRegistry.getFluidForFilledItem(is);
				if (liq != null && liq.getFluid().equals(FluidRegistry.getFluid("fuel"))) {
					boolean bucket = FluidContainerRegistry.isBucket(is);
					tf.fill(ForgeDirection.UP, liq, true);
					if (!ep.capabilities.isCreativeMode) {
						if (bucket)
							ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
						else
							ep.setCurrentItemOrArmor(0, null);
					}
					((TileEntityBase)te).syncAllData(true);
					return true;
				}
			}
		}
		if (m == MachineRegistry.DISPLAY && is != null) {
			if (ReikaDyeHelper.isDyeItem(is)) {
				TileEntityDisplay td = (TileEntityDisplay)te;
				td.setDyeColor(ReikaDyeHelper.getColorFromItem(is));
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
			if (is.getItem() == Items.glowstone_dust) {
				TileEntityDisplay td = (TileEntityDisplay)te;
				td.setColorToArgon();
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
			if (is.getItem() == Items.written_book) {
				try {
					TileEntityDisplay td = (TileEntityDisplay)te;
					NBTTagCompound nbt = is.stackTagCompound;
					NBTTagList li = nbt.getTagList("pages", NBTTypes.STRING.ID);
					ArrayList<String> s = new ArrayList();
					for (int i = 0; i < li.tagCount(); i++) {
						String ns = li.getStringTagAt(i);
						s.add(ns);
					}
					td.clearMessage();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < s.size(); i++) {
						sb.append(s.get(i));
						if (i < s.size()-1 && !s.get(i).endsWith(" ")) {
							sb.append(" ");
						}
					}
					td.setMessage(sb.toString());
				}
				catch (Exception e) {
					ReikaChatHelper.writeString("Error reading book.");
					e.printStackTrace();
				}
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.BLOWER) {
			if (is != null && ReikaItemHelper.matchStacks(is, m.getCraftedProduct()))
				return false;
		}
		if (m == MachineRegistry.MIRROR) {
			TileEntityMirror tm = (TileEntityMirror)te;
			if (tm.broken) {
				if (ReikaItemHelper.matchStacks(is, ItemStacks.mirror)) {
					tm.repair(world, x, y, z);
					if (!ep.capabilities.isCreativeMode) {
						ep.setCurrentItemOrArmor(0, new ItemStack(is.getItem(), is.stackSize-1, is.getItemDamage()));
					}
					((TileEntityBase)te).syncAllData(true);
					return true;
				}
			}
		}
		if (te != null && RotaryAux.hasGui(world, x, y, z, ep) && ((RotaryCraftTileEntity)te).isPlayerAccessible(ep)) {
			ep.openGui(RotaryCraft.instance, GuiRegistry.MACHINE.ordinal(), world, x, y, z);
			return true;
		}
		if (m == MachineRegistry.SCREEN) {
			TileEntityScreen tc = (TileEntityScreen)te;
			if (ep.isSneaking()) {
				tc.activate(ep);
				((TileEntityBase)te).syncAllData(true);
				return true;
			}
		}
		if (m == MachineRegistry.CAVESCANNER) {
			TileEntityCaveFinder tc = (TileEntityCaveFinder)te;
			ForgeDirection dir = ReikaEntityHelper.getDirectionFromEntityLook(ep, true);
			int mov = 4;
			if (ep.isSneaking())
				mov *= -1;
			tc.moveSrc(mov, dir);
			((TileEntityBase)te).syncAllData(true);
			return true;
		}

		((TileEntityBase)te).syncAllData(true);
		return false;
	}

	@Override
	public final ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(this, meta);
		if (m == null)
			return null;
		TileEntity tile = world.getTileEntity(target.blockX, target.blockY, target.blockZ);
		ItemStack core = m.getCraftedProduct();
		if (m.isEnchantable()) {
			HashMap<Enchantment, Integer> ench = ((EnchantableMachine)tile).getEnchantments();
			ReikaEnchantmentHelper.applyEnchantments(core, ench);
		}
		if (m.hasNBTVariants()) {
			NBTMachine nb = (NBTMachine)tile;
			NBTTagCompound nbt = nb.getTagsToWriteToStack();
			//core.stackTagCompound = nbt;
		}
		if (m == MachineRegistry.PORTALSHAFT) {
			return MachineRegistry.SHAFT.getCraftedMetadataProduct(((TileEntityPortalShaft)tile).material.ordinal());
		}
		return core;
	}

	@Override
	public final boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return false;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harvest)
	{
		if (this.canHarvest(world, player, x, y, z))
			this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlockToAir(x, y, z);
	}

	private boolean canHarvest(World world, EntityPlayer ep, int x, int y, int z) {
		if (ep.capabilities.isCreativeMode)
			return false;
		if (this instanceof BlockPiping)
			return true;
		return RotaryAux.canHarvestSteelMachine(ep);
	}

	@Override
	public final void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta)
	{
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntity te = world.getTileEntity(x, y, z);
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null) {
			ItemStack is = m.getCraftedProduct();
			if (m == MachineRegistry.PORTALSHAFT)
				is = MachineRegistry.SHAFT.getCraftedMetadataProduct(((TileEntityPortalShaft)te).material.ordinal());
			List li;
			if (m.isEnchantable()) {
				HashMap<Enchantment,Integer> map = ((EnchantableMachine)te).getEnchantments();
				ReikaEnchantmentHelper.applyEnchantments(is, map);
			}
			if (m.hasNBTVariants()) {
				NBTTagCompound nbt = ((NBTMachine)te).getTagsToWriteToStack();
				is.stackTagCompound = (NBTTagCompound)(nbt != null ? nbt.copy() : null);
			}
			if (m == MachineRegistry.SCALECHEST) {
				((TileEntityScaleableChest)te).writeInventoryToItem(is);
			}
			if (((RotaryCraftTileEntity)te).isUnHarvestable()) {
				li = ReikaJavaLibrary.makeListFrom(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 2+par5Random.nextInt(12)));
			}
			else if (m.isBroken((RotaryCraftTileEntity)te)) {
				li = m.getBrokenProducts();
			}
			else {
				li = ReikaJavaLibrary.makeListFrom(is);
			}
			ReikaItemHelper.dropItems(world, x+par5Random.nextDouble(), y+par5Random.nextDouble(), z+par5Random.nextDouble(), li);
		}
	}

	@Override
	public final void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof IInventory && !(te instanceof TileEntityScaleableChest))
			ReikaItemHelper.dropInventory(world, x, y, z);
		if (te instanceof TileEntityDropProcessor)
			((TileEntityDropProcessor)te).dropCache();
		if (te instanceof BreakAction) {
			((BreakAction)te).breakBlock();
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public final TileEntity createTileEntity(World world, int meta) {
		return MachineRegistry.createTEFromIDAndMetadata(this, meta);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e)
	{
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == null)
			return;
		RotaryCraftTileEntity tile = (RotaryCraftTileEntity)world.getTileEntity(x, y, z);
		if (!(e instanceof EntityItem || e instanceof EntityXPOrb)) {
			if (tile instanceof DamagingContact) {
				DamagingContact dg = (DamagingContact)tile;
				int dmg = dg.getContactDamage();
				if (dg.canDealDamage() && dmg > 0)
					e.attackEntityFrom(dg.getDamageType(), dmg);
			}
			if (m.dealsHeatDamage(e) && tile instanceof TemperatureTE) {
				int dmg = ((TemperatureTE)tile).getThermalDamage();
				if (dmg > 0) {
					e.attackEntityFrom(DamageSource.lava, dmg);
					e.setFire(6);
				}
			}
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tr = (TileEntityReservoir)tile;
			if (e instanceof EntityLivingBase) {
				tr.applyFluidEffectsToEntity((EntityLivingBase)e);
			}
		}
		if (m == MachineRegistry.CENTRIFUGE) {
			TileEntityCentrifuge tr = (TileEntityCentrifuge)tile;
			if (tr.omega > 0 && world.isRemote) {
			
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(this, metadata);
		ItemStack is = m.getCraftedProduct();
		ret.add(is);
		return ret;
	}

	@Override
	public void fillWithRain(World world, int x, int y, int z) {/*
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.BELT) {
			TileEntityBeltHub te = (TileEntityBeltHub)world.getTileEntity(x, y, z);
			te.makeWet();
		}*/
	}

	@Override
	public final void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityLandmine) {
			TileEntityLandmine tl = (TileEntityLandmine)te;
			tl.detonate(world, x, y, z);
		}
		super.onBlockExploded(world, x, y, z, explosion);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iba, int x, int y, int z, int par5)
	{
		MachineRegistry m = MachineRegistry.getMachine(iba, x, y, z);
		if (m == MachineRegistry.PLAYERDETECTOR) {
			TileEntityPlayerDetector tp = (TileEntityPlayerDetector)iba.getTileEntity(x, y, z);
			return tp.isActive() ? 15 : 0;
		}
		else if (m == MachineRegistry.SMOKEDETECTOR) {
			TileEntitySmokeDetector tp = (TileEntitySmokeDetector)iba.getTileEntity(x, y, z);
			return tp.isAlarming() ? 15 : 0;
		}
		else {
			return 0;
		}
	}

	@Override
	public final void onNeighborBlockChange(World world, int x, int y, int z, Block id) {
		super.onNeighborBlockChange(world, x, y, z, id);
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (m.cachesConnections()) {
				CachedConnection tc = (CachedConnection)te;
				tc.recomputeConnections(world, x, y, z);
			}

			if (m == MachineRegistry.SMOKEDETECTOR) {
				Block upid = world.getBlock(x, y+1, z);
				if (upid == Blocks.air) {
					world.setBlockToAir(x, y, z);
					ItemStack is = MachineRegistry.SMOKEDETECTOR.getCraftedProduct();
					if (!world.isRemote)
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, is));
				}
				else if (!upid.isOpaqueCube()) {
					world.setBlockToAir(x, y, z);
					ItemStack is = MachineRegistry.SMOKEDETECTOR.getCraftedProduct();
					if (!world.isRemote)
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, is));
				}
			}

			if (te instanceof AdjacentUpdateWatcher) {
				((AdjacentUpdateWatcher)te).onAdjacentUpdate(world, x, y, z, id);
			}

			if (m.hasTemperature()) {
				TemperatureTE tr = (TemperatureTE)te;
				int temp = Math.min(tr.getTemperature(), 800);
				//ReikaWorldHelper.temperatureEnvironment(world, x, y, z, temp);
			}
		}
	}

	@Override
	public final void onBlockAdded(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null && m.cachesConnections()) {
			CachedConnection te = (CachedConnection)world.getTileEntity(x, y, z);
			te.addToAdjacentConnections(world, x, y, z);
			te.recomputeConnections(world, x, y, z);
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.FORCEFIELD || m == MachineRegistry.CONTAINMENT)
			return 15;
		if (m == MachineRegistry.DISPLAY)
			return 15;
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir te = (TileEntityReservoir)world.getTileEntity(x, y, z);
			if (te.isEmpty())
				return 0;
			return te.getFluid().getLuminosity(te.getContents());
		}
		if (m == MachineRegistry.PUMP) {
			TileEntityPump te = (TileEntityPump)world.getTileEntity(x, y, z);
			if (te.getLevel() <= 0)
				return 0;
			return te.getLiquid().getLuminosity();
		}
		if (m == MachineRegistry.LAVAMAKER) {
			TileEntityLavaMaker te = (TileEntityLavaMaker)world.getTileEntity(x, y, z);
			if (te.getLevel() <= 0)
				return 0;
			return 15;
		}
		if (m != null && m.isPipe()) {
			TileEntityPiping te = (TileEntityPiping)world.getTileEntity(x, y, z);
			if (te.getFluidLevel() <= 0 || te.getFluidType() == null)
				return 0;
			Fluid f = te.getFluidType();
			return f.getLuminosity();
		}
		if (m == MachineRegistry.DYNAMO) {
			TileEntityDynamo te = (TileEntityDynamo)world.getTileEntity(x, y, z);
			return te.power > 0 ? 7 : 0;
		}
		return 0;
	}

	public final Fluid getFluid(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.RESERVOIR) {
			return ((TileEntityReservoir)world.getTileEntity(x, y, z)).getFluid();
		}
		return null;
	}

	public final boolean supportsQuantization(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.RESERVOIR) {
			return true;
		}
		return false;
	}

	public final int drain(World world, int x, int y, int z, Fluid f, int amt, boolean doDrain) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir te = (TileEntityReservoir)world.getTileEntity(x, y, z);
			int ret = Math.min(amt, te.getLevel());
			if (doDrain) {
				te.removeLiquid(ret);
			}
			return ret;
		}
		return 0;
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
		}
		 */
		return currenttip;
	}

	@ModDependent(ModList.WAILA)
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		//if (/*LegacyWailaHelper.cacheAndReturn(acc)*/!currenttip.isEmpty())
		//	return currenttip;
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)acc.getTileEntity();
		te.syncAllData(false);
		if (te instanceof TemperatureTE)
			currenttip.add(String.format("Temperature: %dC", ((TemperatureTE) te).getTemperature()));
		if (te instanceof PressureTE)
			currenttip.add(String.format("Pressure: %dkPa", ((PressureTE) te).getPressure()));
		if (te instanceof TileEntitySplitter) {
			TileEntitySplitter spl = (TileEntitySplitter)te;
			if (spl.isSplitting()) {
				currenttip.add("Splitting Power");
				currenttip.add(spl.getRatioForDisplay());
			}
			else {
				currenttip.add("Merging Power");
			}
		}
		if (te instanceof EnergyToPowerBase) {
			EnergyToPowerBase e = (EnergyToPowerBase)te;
			currenttip.add(String.format("Consuming %d %s/t", e.getConsumedUnitsPerTick(), e.getUnitDisplay()));
			currenttip.add(String.format("Contains %d %s", e.getStoredPower(), e.getUnitDisplay()));
			//currenttip.add(String.format("Lubricant: %d mB", e.getLubricant()));
		}
		if (te instanceof PoweredLiquidIO) {
			PoweredLiquidIO liq = (PoweredLiquidIO)te;
			Fluid in = liq.getFluidInInput();
			Fluid out = liq.getFluidInOutput();
			int amtin = liq.getInputLevel();
			int amtout = liq.getOutputLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amtin, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			String output = out != null ? String.format("%d/%d mB of %s", amtout, liq.getCapacity(), out.getLocalizedName()) : "Empty";
			currenttip.add("Input Tank: "+input);
			currenttip.add("Output Tank: "+output);
		}
		else if (te instanceof PoweredLiquidReceiver) {
			PoweredLiquidReceiver liq = (PoweredLiquidReceiver)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amt, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		else if (te instanceof PoweredLiquidProducer) {
			PoweredLiquidProducer liq = (PoweredLiquidProducer)te;
			Fluid in = liq.getContainedFluid();
			int amt = liq.getLevel();
			String input = in != null ? String.format("%d/%d mB of %s", amt, liq.getCapacity(), in.getLocalizedName()) : "Empty";
			currenttip.add("Tank: "+input);
		}
		else if (te instanceof IFluidHandler) {
			FluidTankInfo[] tanks = ((IFluidHandler)te).getTankInfo(ForgeDirection.UP);
			if (tanks != null) {
				for (int i = 0; i < tanks.length; i++) {
					FluidTankInfo info = tanks[i];
					FluidStack fs = info.fluid;
					String input = fs != null ? String.format("%d/%d mB of %s", fs.amount, info.capacity, fs.getFluid().getLocalizedName(fs)) : "Empty";
					currenttip.add("Tank "+i+": "+input);
				}
			}
			/*
			if (te instanceof TileEntityReservoir) {
				TileEntityReservoir tr = (TileEntityReservoir)te;
				CompoundReservoir cr = tr.getCompound();
				currenttip.add(" x"+cr.getSize()+" = "+cr.getTotalAmount());
			}*/
		}
		if (te instanceof DiscreteFunction) {
			int ticks = ((DiscreteFunction)te).getOperationTime();
			float sec = Math.max(0.05F, ticks/20F);
			currenttip.add(String.format("Operation Time: %.2fs", sec));
			if (te instanceof MultiOperational && sec <= 0.05F) {
				int num = ((MultiOperational)te).getNumberConsecutiveOperations();
				currenttip.add(String.format("%d Operations Per Tick", num));
			}
		}
		if (te instanceof TileEntityPiping) {
			TileEntityPiping tp = (TileEntityPiping)te;
			Fluid f = tp.getFluidType();
			if (f != null) {
				currenttip.add(String.format("%s", f.getLocalizedName()));
				if (tp instanceof TileEntityPipe) {
					int p = ((TileEntityPipe)tp).getPressure();
					double val = ReikaMathLibrary.getThousandBase(p);
					String sg = ReikaEngLibrary.getSIPrefix(p);
					currenttip.add(String.format("Pressure: %.3f%sPa", val, sg));
				}
			}
		}
		if (te instanceof TileEntityBorer) {
			currenttip.add(((TileEntityBorer)te).getCurrentRequiredPower());
		}
		if (te instanceof TileEntityAggregator) {
			currenttip.add(String.format("Producing %d mB per tick", ((TileEntityAggregator)te).getProductionPerTick(acc.getWorld().getBiomeGenForCoords(te.xCoord, te.zCoord))));
		}
		if (te instanceof TileEntityBusController) {
			ShaftPowerBus bus = ((TileEntityBusController)te).getBus();
			if (bus != null) {
				currenttip.add("Power Bus Receiving "+bus.getInputTorque()+" Nm @ "+bus.getSpeed()+" rad/s");
				currenttip.add(bus.getInputPower()+"W is being split to "+bus.getTotalOutputSides()+" devices");
				currenttip.add("(Power per side is "+bus.getInputPower()/bus.getTotalOutputSides()+"W)");
			}
		}
		if (te instanceof TileEntitySolar) {
			TileEntitySolar sol = (TileEntitySolar)te;
			currenttip.add("Consuming "+sol.getConsumedWater()+" mB/t of fluid.");
		}
		if (te.getMachine().isEnchantable()) {
			if (((EnchantableMachine)te).hasEnchantments()) {
				currenttip.add("Enchantments: ");
				ArrayList<Enchantment> li = ((EnchantableMachine)te).getValidEnchantments();
				for (int i = 0; i < li.size(); i++) {
					Enchantment e = li.get(i);
					int level = ((EnchantableMachine)te).getEnchantment(e);
					if (level > 0)
						currenttip.add("  "+EnumChatFormatting.LIGHT_PURPLE.toString()+e.getTranslatedName(level));
				}
			}
		}
		if (te instanceof ConditionalOperation) {
			currenttip.add(((ConditionalOperation) te).getOperationalStatus());
		}
		if (te instanceof TileEntityPulseFurnace) {
			TileEntityPulseFurnace tpf = (TileEntityPulseFurnace)te;
			int lvl = tpf.getAccelerant();
			if (lvl > 0) {
				Fluid f = tpf.getAccelerantType();
				currenttip.add(String.format("Accelerant: %dmB of %s", lvl, f.getLocalizedName()));
			}
			else
				currenttip.add("Accelerant: Empty");
		}
		if (te instanceof TileEntityPulseFurnace) {
			TileEntityFluidCompressor tfc = (TileEntityFluidCompressor)te;
			if (!tfc.isEmpty())
				currenttip.add(String.format("Capacity: %.3fB", tfc.getCapacity()));
		}
		return currenttip;
	}

	@ModDependent(ModList.WAILA)
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config) {
		/*
		String s1 = EnumChatFormatting.ITALIC.toString();
		String s2 = EnumChatFormatting.BLUE.toString();
		currenttip.add(s2+s1+"RotaryCraft");
		 */
		return currenttip;
	}

}
