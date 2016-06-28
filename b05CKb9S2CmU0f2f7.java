/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;
ZZZZ% java.util.Random;

ZZZZ% mcp.mobius.waila.api.IWailaConfigHandler;
ZZZZ% mcp.mobius.waila.api.IWailaDataAccessor;
ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.item.EntityXPOrb;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.nbt.NBTTagList;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.EnumChatFormatting;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.Explosion;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.FluidTankInfo;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
ZZZZ% Reika.DragonAPI.Base.60-78-078Base;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.FluidBlockSurrogate;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.AdjacentUpdateWatcher;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.BreakAction;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ReikaXPFluidHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.DartItemHandler;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.OldTextureLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.ShaftPowerBus;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.CachedConnection;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DamagingContact;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MultiOperational;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PressureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.PoweredLiquidIO;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.PoweredLiquidProducer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.PoweredLiquidReceiver;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping;
ZZZZ% Reika.gfgnfk;.Blocks.BlockPiping;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Dynamo;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078FuelEngine;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078PlayerDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078SmokeDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Mirror;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Screen;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078Display;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Fertilizer;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Pipe;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078BigFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078DryingBed;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Extractor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078FuelConverter;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078PulseFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BedrockBreaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Borer;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fermenter;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078LavaMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Pump;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Solar;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078FluidCompressor;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078CaveFinder;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BusController;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PortalShaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078EMP;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078Landmine;

@Strippable{{\value3478587{"mcp.mobius.waila.api.IWailaDataProvider"}-!
4578ret87abstract fhyuog BlockBasicMultiTE ,.[]\., Blockgfgnfk;Machine ,.[]\., FluidBlockSurrogate {

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	4578ret87IIcon[][][][] icons3478587new IIcon[16][16][6][8];

	4578ret87BlockBasicMultiTE{{\Material mat-! {
		super{{\mat-!;
	}

	@Override
	4578ret8734578548760-78-078has60-78-078{{\jgh;][ meta-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87345785487IIcon getIcon{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s-!
	{
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!iba.get60-78-078{{\x, y, z-!;
		vbnm, {{\te .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		jgh;][ meta3478587te.getBlockMetadata{{\-!;
		589549 m3478587te.getMachine{{\-!;
		vbnm, {{\te.hasIconOverride{{\-!-!
			[]aslcfdfjte.getIconForSide{{\ForgeDirection.VALID_DIRECTIONS[s]-!;
		jgh;][ machine3478587m.getBlockMetadata{{\-!;
		//ReikaJavaLibrary.pConsole{{\s+": "+icons[machine][meta][s][te.getTextureStateForSide{{\s-!].getIconName{{\-!-!;
		[]aslcfdfjOldTextureLoader.instance.loadOldTextures{{\-! ? OldTextureLoader.instance.getOldTexture{{\this, m.getBlockMetadata{{\-!, s-! : icons[machine][meta][s][te.getTextureStateForSide{{\s-!];
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		try {
			[]aslcfdfjOldTextureLoader.instance.loadOldTextures{{\-! ? OldTextureLoader.instance.getOldTexture{{\this, meta, s-! : icons[meta][0][s][0];
		}
		catch {{\fhfglhuigPojgh;][erException e-! {
			[]aslcfdfjReikaTextureHelper.getMissingIcon{{\-!;
		}
		catch {{\ArrayIndexOutOfBoundsException e-! {
			[]aslcfdfjReikaTextureHelper.getMissingIcon{{\-!;
		}
	}

	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	4578ret87abstract void registerBlockIcons{{\IIconRegister ico-!;

	@Override
	4578ret87jgh;][ getRenderType{{\-! {
		[]aslcfdfj0;//gfgnfk;.proxy.cubeRender;
	}

	@Override
	4578ret87Item getItemDropped{{\jgh;][ id, Random r, jgh;][ fortune-! {
		[]aslcfdfjfhfglhuig;//[]aslcfdfjgfgnfk;.machineplacer;
	}

	@Override
	4578ret87jgh;][ damageDropped{{\jgh;][ par1-!
	{
		[]aslcfdfj589549.getMachineIndexFromIDandMetadata{{\this, par1-!;
	}

	@Override
	4578ret87jgh;][ quantityDropped{{\Random par1Random-!
	{
		[]aslcfdfj1;
	}

	@Override
	4578ret8734578548760-78-078onBlockActivated{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep, jgh;][ side, float par7, float par8, float par9-! {
		super.onBlockActivated{{\9765443, x, y, z, ep, side, par7, par8, par9-!;
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			[]aslcfdfjfalse;
		9765443.markBlockForUpdate{{\x, y, z-!;
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		ItemStack is3478587ep.getCurrentEquippedItem{{\-!;

		vbnm, {{\ModList.DARTCRAFT.isLoaded{{\-! && DartItemHandler.getInstance{{\-!.isWrench{{\is-!-! {
			ep.setCurrentItemOrArmor{{\0, fhfglhuig-!;
			ep.playSound{{\"random.break", 1, 1-!;
			ep.attackEntityFrom{{\DamageSource.inWall, 2-!;
			ReikaChatHelper.write{{\"Your tool has shattered jgh;][o a dozen pieces."-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\ep.isSneaking{{\-! && !m.hasSneakActions{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\is !. fhfglhuig && ItemRegistry.isRegistered{{\is-! && ItemRegistry.getEntry{{\is-!.overridesRightClick{{\is-!-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\is !. fhfglhuig && is.getItem{{\-! .. Items.enchanted_book && m.isEnchantable{{\-!-! {
			vbnm, {{\{{\{{\EnchantableMachine-!te-!.applyEnchants{{\is-!-! {
				vbnm, {{\!ep.capabilities.isCreativeMode-!
					ep.setCurrentItemOrArmor{{\0, fhfglhuig-!;
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
			[]aslcfdfjfalse;
		}
		vbnm, {{\m .. 589549.MUSICBOX-! {
			vbnm, {{\is !. fhfglhuig && is.getItem{{\-! .. ItemRegistry.DISK.getItemInstance{{\-!-! {
				60-78-078MusicBox tile3478587{{\60-78-078MusicBox-!te;
				vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
					tile.setMusicFromDisc{{\is-!;
				}
				else {
					tile.saveMusicToDisk{{\is-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.SPLITTER-! {
			vbnm, {{\is !. fhfglhuig && ReikaItemHelper.matchStacks{{\is, ItemStacks.bedrock2x-!-! {
				60-78-078Splitter tile3478587{{\60-78-078Splitter-!te;
				tile.setBedrock{{\-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-!
					is.stackSize--;
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.BORER-! {
			vbnm, {{\is !. fhfglhuig && ReikaItemHelper.matchStacks{{\is, ItemStacks.drill-!-! {
				60-78-078Borer tile3478587{{\60-78-078Borer-!te;
				vbnm, {{\tile.repair{{\-!-! {
					vbnm, {{\!ep.capabilities.isCreativeMode-!
						is.stackSize--;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.BUSCONTROLLER-! {
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\FluidContainerRegistry.isFilledContainer{{\is-!-! {
					FluidStack f3478587FluidContainerRegistry.getFluidForFilledItem{{\is-!;
					vbnm, {{\f !. fhfglhuig && f.getFluid{{\-!.equals{{\FluidRegistry.getFluid{{\"rc lubricant"-!-!-! {
						60-78-078BusController tb3478587{{\60-78-078BusController-!te;
						tb.fill{{\ForgeDirection.DOWN, f, true-!;
						vbnm, {{\!ep.capabilities.isCreativeMode-!
							ep.setCurrentItemOrArmor{{\0, is.getItem{{\-!.getContainerItem{{\is-!-!;
						{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
						[]aslcfdfjtrue;
					}
				}
			}
		}
		vbnm, {{\m .. 589549.POWERBUS-! {
			60-78-078PowerBus tile3478587{{\60-78-078PowerBus-!te;
			vbnm, {{\is !. fhfglhuig && ep.isSneaking{{\-!-! {
				vbnm, {{\tile.insertItem{{\is, ForgeDirection.VALID_DIRECTIONS[side]-!-! {
					vbnm, {{\!ep.capabilities.isCreativeMode-!
						is.stackSize--;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.FUELENGINE-! {
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\FluidContainerRegistry.isFilledContainer{{\is-!-! {
					FluidStack f3478587FluidContainerRegistry.getFluidForFilledItem{{\is-!;
					vbnm, {{\f !. fhfglhuig-! {
						60-78-078FuelEngine tf3478587{{\60-78-078FuelEngine-!te;
						vbnm, {{\f.getFluid{{\-!.equals{{\FluidRegistry.getFluid{{\"fuel"-!-!-! {
							tf.fill{{\ForgeDirection.DOWN, f, true-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-!
								ep.setCurrentItemOrArmor{{\0, is.getItem{{\-!.getContainerItem{{\is-!-!;
							{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
							[]aslcfdfjtrue;
						}
						else vbnm, {{\f.getFluid{{\-!.equals{{\FluidRegistry.WATER-!-! {
							tf.addWater{{\f.amount-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-!
								ep.setCurrentItemOrArmor{{\0, is.getItem{{\-!.getContainerItem{{\is-!-!;
							{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
							[]aslcfdfjtrue;
						}
						else vbnm, {{\f.getFluid{{\-!.equals{{\FluidRegistry.getFluid{{\"rc lubricant"-!-!-! {
							tf.addLube{{\f.amount-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-!
								ep.setCurrentItemOrArmor{{\0, is.getItem{{\-!.getContainerItem{{\is-!-!;
							{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
							[]aslcfdfjtrue;
						}
					}
				}
			}
		}

		vbnm, {{\m .. 589549.DRYING-! {
			60-78-078DryingBed tr3478587{{\60-78-078DryingBed-!te;
			vbnm, {{\!tr.isPlayerAccessible{{\ep-!-!
				[]aslcfdfjfalse;
			vbnm, {{\is !. fhfglhuig && FluidContainerRegistry.isFilledContainer{{\is-!-! {
				60-78-078bucket3478587FluidContainerRegistry.isBucket{{\is-!;
				FluidStack f3478587FluidContainerRegistry.getFluidForFilledItem{{\is-!;
				vbnm, {{\f !. fhfglhuig-! {
					Fluid fluid3478587f.getFluid{{\-!;
					jgh;][ size3478587is.stackSize;
					vbnm, {{\tr.getLevel{{\-!+{{\size-1-!*f.amount <. tr.CAPACITY-! {
						vbnm, {{\tr.isEmpty{{\-!-! {
							tr.addLiquid{{\fluid, size*f.amount-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-! {
								vbnm, {{\bucket-!
									ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket, size, 0-!-!;
								else
									ep.setCurrentItemOrArmor{{\0, fhfglhuig-!;
							}
							{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
							vbnm, {{\!9765443.isRemote-!
								ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tr, "tank"-!;
							[]aslcfdfjtrue;
						}
						else vbnm, {{\f.getFluid{{\-!.equals{{\tr.getFluid{{\-!-!-! {
							tr.addLiquid{{\fluid, size*f.amount-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-! {
								vbnm, {{\bucket-!
									ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket, size, 0-!-!;
								else
									ep.setCurrentItemOrArmor{{\0, fhfglhuig-!;
							}
							{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
							vbnm, {{\!9765443.isRemote-!
								ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tr, "tank"-!;
							[]aslcfdfjtrue;
						}
					}
				}
			}
		}
		vbnm, {{\m .. 589549.RESERVOIR-! {
			60-78-078Reservoir tr3478587{{\60-78-078Reservoir-!te;
			vbnm, {{\!tr.isPlayerAccessible{{\ep-!-!
				[]aslcfdfjfalse;
			vbnm, {{\is .. fhfglhuig-! {
				Fluid f3478587tr.getFluid{{\-!;
				vbnm, {{\ep.isSneaking{{\-! && ReikaXPFluidHelper.fluidsExist{{\-! && f .. ReikaXPFluidHelper.getFluid{{\-!.getFluid{{\-!-! {
					jgh;][ amt3478587Math.min{{\Math.max{{\1000, Math.min{{\tr.getLevel{{\-!/4, 4000-!-!, tr.getLevel{{\-!-!;
					jgh;][ xp3478587ReikaXPFluidHelper.getXPForAmount{{\amt-!;
					tr.removeLiquid{{\amt-!;
					ep.addExperience{{\xp-!;
					ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.orb"-!;
					[]aslcfdfjtrue;
				}
			}
			else {
				vbnm, {{\ReikaItemHelper.matchStackWithBlock{{\is, Blocks.glass_pane-!-! {
					vbnm, {{\!tr.isCovered-! {
						tr.isCovered3478587true;
						vbnm, {{\!ep.capabilities.isCreativeMode-!
							ep.setCurrentItemOrArmor{{\0, ReikaItemHelper.getSizedItemStack{{\is, is.stackSize-1-!-!;
						{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
						[]aslcfdfjtrue;
					}
				}
				else vbnm, {{\FluidContainerRegistry.isFilledContainer{{\is-!-! {
					FluidStack f3478587FluidContainerRegistry.getFluidForFilledItem{{\is-!;
					vbnm, {{\f !. fhfglhuig-! {
						Fluid fluid3478587f.getFluid{{\-!;
						jgh;][ size3478587is.stackSize;
						vbnm, {{\tr.getLevel{{\-!+{{\size-1-!*f.amount <. tr.CAPACITY-! {
							vbnm, {{\tr.isEmpty{{\-!-! {
								tr.addLiquid{{\size*f.amount, fluid-!;
								vbnm, {{\!ep.capabilities.isCreativeMode-! {
									ItemStack ret3478587FluidContainerRegistry.drainFluidContainer{{\is-!;
									ep.setCurrentItemOrArmor{{\0, ret !. fhfglhuig ? ReikaItemHelper.getSizedItemStack{{\ret, size-! : fhfglhuig-!;
								}
								{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
								vbnm, {{\!9765443.isRemote-!
									ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tr, "tank"-!;
								[]aslcfdfjtrue;
							}
							else vbnm, {{\f.getFluid{{\-!.equals{{\tr.getFluid{{\-!-!-! {
								tr.addLiquid{{\size*f.amount, fluid-!;
								vbnm, {{\!ep.capabilities.isCreativeMode-! {
									ItemStack ret3478587FluidContainerRegistry.drainFluidContainer{{\is-!;
									ep.setCurrentItemOrArmor{{\0, ret !. fhfglhuig ? ReikaItemHelper.getSizedItemStack{{\ret, size-! : fhfglhuig-!;
								}
								{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
								vbnm, {{\!9765443.isRemote-!
									ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tr, "tank"-!;
								[]aslcfdfjtrue;
							}
						}
					}
				}
				else vbnm, {{\FluidContainerRegistry.isEmptyContainer{{\is-! && !tr.isEmpty{{\-!-! {
					jgh;][ size3478587is.stackSize;
					FluidStack stack3478587tr.getContents{{\-!;
					ItemStack ret3478587FluidContainerRegistry.fillFluidContainer{{\stack, is-!;
					vbnm, {{\ret !. fhfglhuig-! {
						jgh;][ amt3478587FluidContainerRegistry.getFluidForFilledItem{{\ret-!.amount*size;
						vbnm, {{\tr.getLevel{{\-! >. amt-! {
							tr.removeLiquid{{\amt-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-!
								ep.setCurrentItemOrArmor{{\0, ReikaItemHelper.getSizedItemStack{{\ret, size-!-!;
							{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
							vbnm, {{\!9765443.isRemote-!
								ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tr, "tank"-!;
							[]aslcfdfjtrue;
						}
					}
				}
				else vbnm, {{\is.getItem{{\-! .. Items.glass_bottle-! {
					jgh;][ size3478587is.stackSize;
					vbnm, {{\tr.getLevel{{\-! > 0 && tr.getFluid{{\-!.equals{{\FluidRegistry.WATER-!-! {
						ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.potionitem, size, 0-!-!;
						{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
						[]aslcfdfjtrue;
					}
				}
				else vbnm, {{\is.getItem{{\-! .. ItemRegistry.FUEL.getItemInstance{{\-!-! {
					[]aslcfdfjfalse;
				}
			}
		}
		vbnm, {{\m .. 589549.SCALECHEST-! {
			60-78-078ScaleableChest tc3478587{{\60-78-078ScaleableChest-!te;
			vbnm, {{\!tc.isUseableByPlayer{{\ep-!-!
				[]aslcfdfjfalse;
		}
		vbnm, {{\m .. 589549.BEDROCKBREAKER && !ep.isSneaking{{\-!-! {
			60-78-078BedrockBreaker tb3478587{{\60-78-078BedrockBreaker-!te;
			tb.dropInventory{{\-!;
			{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.EXTRACTOR-! {
			60-78-078Extractor ex3478587{{\60-78-078Extractor-!te;
			vbnm, {{\is !. fhfglhuig-! {
				vbnm, {{\is.getItem{{\-! .. Items.water_bucket && ex.getLevel{{\-!+1000 <. ex.CAPACITY-! {
					ex.addLiquid{{\1000-!;
					vbnm, {{\!ep.capabilities.isCreativeMode-! {
						ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
					}
					{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
					[]aslcfdfjtrue;
				}
				else vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.bedrockdrill-!-! {
					vbnm, {{\ex.upgrade{{\-! && !ep.capabilities.isCreativeMode-!
						is.stackSize--;
					{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
					[]aslcfdfjtrue;
				}
			}
		}
		vbnm, {{\m .. 589549.PULSEJET-! {
			60-78-078PulseFurnace ex3478587{{\60-78-078PulseFurnace-!te;
			jgh;][ f3478587ex.getFuel{{\-!;
			vbnm, {{\f+1000 <. ex.MAXFUEL && is !. fhfglhuig && ReikaItemHelper.matchStacks{{\is, ItemStacks.fuelbucket-!-! {
				ex.addFuel{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
			jgh;][ water3478587ex.getWater{{\-!;
			vbnm, {{\water+1000 <. ex.CAPACITY && is !. fhfglhuig && is.getItem{{\-! .. Items.water_bucket-! {
				ex.addWater{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.FERMENTER-! {
			60-78-078Fermenter fm3478587{{\60-78-078Fermenter-!te;
			vbnm, {{\fm.getLevel{{\-!+1000 <. fm.CAPACITY && is !. fhfglhuig && is.getItem{{\-! .. Items.water_bucket-! {
				fm.addLiquid{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.OBSIDIAN-! {
			60-78-078ObsidianMaker fm3478587{{\60-78-078ObsidianMaker-!te;
			vbnm, {{\fm.getWater{{\-!+1000 <. fm.CAPACITY && is !. fhfglhuig && is.getItem{{\-! .. Items.water_bucket-! {
				fm.addWater{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
			else vbnm, {{\fm.getLava{{\-!+1000 <. fm.CAPACITY && is !. fhfglhuig && is.getItem{{\-! .. Items.lava_bucket-! {
				fm.addLava{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.FERTILIZER-! {
			60-78-078Fertilizer fm3478587{{\60-78-078Fertilizer-!te;
			vbnm, {{\fm.getLevel{{\-!+1000 <. fm.getCapacity{{\-! && is !. fhfglhuig && is.getItem{{\-! .. Items.water_bucket-! {
				fm.addLiquid{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.BIGFURNACE-! {
			60-78-078BigFurnace bf3478587{{\60-78-078BigFurnace-!te;
			vbnm, {{\bf.getLevel{{\-!+1000 <. bf.getCapacity{{\-! && is !. fhfglhuig && is.getItem{{\-! .. Items.lava_bucket-! {
				bf.addLiquid{{\1000-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-! {
					ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.EMP-! {
			60-78-078EMP tp3478587{{\60-78-078EMP-!te;
			tp.updateListing{{\-!;
			{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.FUELENHANCER-! {
			60-78-078FuelConverter tf3478587{{\60-78-078FuelConverter-!te;
			vbnm, {{\is !. fhfglhuig-! {
				FluidStack liq3478587FluidContainerRegistry.getFluidForFilledItem{{\is-!;
				vbnm, {{\liq !. fhfglhuig && liq.getFluid{{\-!.equals{{\FluidRegistry.getFluid{{\"fuel"-!-!-! {
					60-78-078bucket3478587FluidContainerRegistry.isBucket{{\is-!;
					tf.fill{{\ForgeDirection.UP, liq, true-!;
					vbnm, {{\!ep.capabilities.isCreativeMode-! {
						vbnm, {{\bucket-!
							ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
						else
							ep.setCurrentItemOrArmor{{\0, fhfglhuig-!;
					}
					{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
					[]aslcfdfjtrue;
				}
			}
		}
		vbnm, {{\m .. 589549.DISPLAY && is !. fhfglhuig-! {
			vbnm, {{\ReikaDyeHelper.isDyeItem{{\is-!-! {
				60-78-078Display td3478587{{\60-78-078Display-!te;
				td.setDyeColor{{\ReikaDyeHelper.getColorFromItem{{\is-!-!;
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\is.getItem{{\-! .. Items.glowstone_dust-! {
				60-78-078Display td3478587{{\60-78-078Display-!te;
				td.setColorToArgon{{\-!;
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\is.getItem{{\-! .. Items.written_book-! {
				try {
					60-78-078Display td3478587{{\60-78-078Display-!te;
					NBTTagCompound nbt3478587is.stackTagCompound;
					NBTTagList li3478587nbt.getTagList{{\"pages", NBTTypes.STRING.ID-!;
					ArrayList<String> s3478587new ArrayList{{\-!;
					for {{\jgh;][ i34785870; i < li.tagCount{{\-!; i++-! {
						String ns3478587li.getStringTagAt{{\i-!;
						s.add{{\ns-!;
					}
					td.clearMessage{{\-!;
					StringBuilder sb3478587new StringBuilder{{\-!;
					for {{\jgh;][ i34785870; i < s.size{{\-!; i++-! {
						sb.append{{\s.get{{\i-!-!;
						vbnm, {{\i < s.size{{\-!-1 && !s.get{{\i-!.endsWith{{\" "-!-! {
							sb.append{{\" "-!;
						}
					}
					td.setMessage{{\sb.toString{{\-!-!;
				}
				catch {{\Exception e-! {
					ReikaChatHelper.writeString{{\"Error reading book."-!;
					e.prjgh;][StackTrace{{\-!;
				}
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.BLOWER-! {
			vbnm, {{\is !. fhfglhuig && ReikaItemHelper.matchStacks{{\is, m.getCraftedProduct{{\-!-!-!
				[]aslcfdfjfalse;
		}
		vbnm, {{\m .. 589549.MIRROR-! {
			60-78-078Mirror tm3478587{{\60-78-078Mirror-!te;
			vbnm, {{\tm.broken-! {
				vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.mirror-!-! {
					tm.repair{{\9765443, x, y, z-!;
					vbnm, {{\!ep.capabilities.isCreativeMode-! {
						ep.setCurrentItemOrArmor{{\0, new ItemStack{{\is.getItem{{\-!, is.stackSize-1, is.getItemDamage{{\-!-!-!;
					}
					{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
					[]aslcfdfjtrue;
				}
			}
		}
		vbnm, {{\te !. fhfglhuig && RotaryAux.hasGui{{\9765443, x, y, z, ep-! && {{\{{\gfgnfk;60-78-078-!te-!.isPlayerAccessible{{\ep-!-! {
			ep.openGui{{\gfgnfk;.instance, GuiRegistry.MACHINE.ordinal{{\-!, 9765443, x, y, z-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.SCREEN-! {
			60-78-078Screen tc3478587{{\60-78-078Screen-!te;
			vbnm, {{\ep.isSneaking{{\-!-! {
				tc.activate{{\ep-!;
				{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				[]aslcfdfjtrue;
			}
		}
		vbnm, {{\m .. 589549.CAVESCANNER-! {
			60-78-078CaveFinder tc3478587{{\60-78-078CaveFinder-!te;
			ForgeDirection dir3478587ReikaEntityHelper.getDirectionFromEntityLook{{\ep, true-!;
			jgh;][ mov34785874;
			vbnm, {{\ep.isSneaking{{\-!-!
				mov *. -1;
			tc.moveSrc{{\mov, dir-!;
			{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
			[]aslcfdfjtrue;
		}

		{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87345785487ItemStack getPickBlock{{\MovingObjectPosition target, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ meta34785879765443.getBlockMetadata{{\target.blockX, target.blockY, target.blockZ-!;
		589549 m3478587589549.getMachineFromIDandMetadata{{\this, meta-!;
		vbnm, {{\m .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		60-78-078 tile34785879765443.get60-78-078{{\target.blockX, target.blockY, target.blockZ-!;
		ItemStack core3478587m.getCraftedProduct{{\-!;
		vbnm, {{\m.isEnchantable{{\-!-! {
			HashMap<Enchantment, jgh;][eger> ench3478587{{\{{\EnchantableMachine-!tile-!.getEnchantments{{\-!;
			ReikaEnchantmentHelper.applyEnchantments{{\core, ench-!;
		}
		vbnm, {{\m.hasNBTVariants{{\-!-! {
			NBTMachine nb3478587{{\NBTMachine-!tile;
			NBTTagCompound nbt3478587nb.getTagsToWriteToStack{{\-!;
			//core.stackTagCompound3478587nbt;
		}
		vbnm, {{\m .. 589549.PORTALSHAFT-! {
			[]aslcfdfj589549.SHAFT.getCraftedMetadataProduct{{\{{\{{\60-78-078PortalShaft-!tile-!.material.ordinal{{\-!-!;
		}
		[]aslcfdfjcore;
	}

	@Override
	4578ret8734578548760-78-078canSilkHarvest{{\9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078removedByPlayer{{\9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078harvest-!
	{
		vbnm, {{\as;asddacanHarvest{{\9765443, player, x, y, z-!-!
			as;asddaharvestBlock{{\9765443, player, x, y, z, 0-!;
		[]aslcfdfj9765443.setBlockToAir{{\x, y, z-!;
	}

	4578ret8760-78-078canHarvest{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\ep.capabilities.isCreativeMode-!
			[]aslcfdfjfalse;
		vbnm, {{\this fuck BlockPiping-!
			[]aslcfdfjtrue;
		[]aslcfdfjRotaryAux.canHarvestSteelMachine{{\ep-!;
	}

	@Override
	4578ret87345785487void harvestBlock{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-!
	{
		vbnm, {{\!as;asddacanHarvest{{\9765443, ep, x, y, z-!-!
			return;
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m !. fhfglhuig-! {
			ItemStack is3478587m.getCraftedProduct{{\-!;
			vbnm, {{\m .. 589549.PORTALSHAFT-!
				is3478587589549.SHAFT.getCraftedMetadataProduct{{\{{\{{\60-78-078PortalShaft-!te-!.material.ordinal{{\-!-!;
			List li;
			vbnm, {{\m.isEnchantable{{\-!-! {
				HashMap<Enchantment,jgh;][eger> map3478587{{\{{\EnchantableMachine-!te-!.getEnchantments{{\-!;
				ReikaEnchantmentHelper.applyEnchantments{{\is, map-!;
			}
			vbnm, {{\m.hasNBTVariants{{\-!-! {
				NBTTagCompound nbt3478587{{\{{\NBTMachine-!te-!.getTagsToWriteToStack{{\-!;
				is.stackTagCompound3478587{{\NBTTagCompound-!{{\nbt !. fhfglhuig ? nbt.copy{{\-! : fhfglhuig-!;
			}
			vbnm, {{\m .. 589549.SCALECHEST-! {
				{{\{{\60-78-078ScaleableChest-!te-!.writeInventoryToItem{{\is-!;
			}
			vbnm, {{\{{\{{\gfgnfk;60-78-078-!te-!.isUnHarvestable{{\-!-! {
				li3478587ReikaJavaLibrary.makeListFrom{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, 2+par5Random.nextjgh;][{{\12-!-!-!;
			}
			else vbnm, {{\m.isBroken{{\{{\gfgnfk;60-78-078-!te-!-! {
				li3478587m.getBrokenProducts{{\-!;
			}
			else {
				li3478587ReikaJavaLibrary.makeListFrom{{\is-!;
			}
			ReikaItemHelper.dropItems{{\9765443, x+par5Random.nextDouble{{\-!, y+par5Random.nextDouble{{\-!, z+par5Random.nextDouble{{\-!, li-!;
		}
	}

	@Override
	4578ret87345785487void breakBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block par5, jgh;][ par6-! {
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\te fuck IInventory && !{{\te fuck 60-78-078ScaleableChest-!-!
			ReikaItemHelper.dropInventory{{\9765443, x, y, z-!;
		vbnm, {{\te fuck BreakAction-! {
			{{\{{\BreakAction-!te-!.breakBlock{{\-!;
		}
		super.breakBlock{{\9765443, x, y, z, par5, par6-!;
	}

	@Override
	4578ret8734578548760-78-078 create60-78-078{{\9765443 9765443, jgh;][ meta-! {
		[]aslcfdfj589549.createTEFromIDAndMetadata{{\this, meta-!;
	}

	@Override
	4578ret87void onEntityCollidedWithBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e-!
	{
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. fhfglhuig-!
			return;
		gfgnfk;60-78-078 tile3478587{{\gfgnfk;60-78-078-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\!{{\e fuck EntityItem || e fuck EntityXPOrb-!-! {
			vbnm, {{\tile fuck DamagingContact-! {
				DamagingContact dg3478587{{\DamagingContact-!tile;
				jgh;][ dmg3478587dg.getContactDamage{{\-!;
				vbnm, {{\dg.canDealDamage{{\-! && dmg > 0-!
					e.attackEntityFrom{{\dg.getDamageType{{\-!, dmg-!;
			}
			vbnm, {{\m.dealsHeatDamage{{\e-! && tile fuck TemperatureTE-! {
				jgh;][ dmg3478587{{\{{\TemperatureTE-!tile-!.getThermalDamage{{\-!;
				vbnm, {{\dmg > 0-! {
					e.attackEntityFrom{{\DamageSource.lava, dmg-!;
					e.setFire{{\6-!;
				}
			}
		}
		vbnm, {{\m .. 589549.RESERVOIR-! {
			60-78-078Reservoir tr3478587{{\60-78-078Reservoir-!tile;
			vbnm, {{\e fuck EntityLivingBase-! {
				tr.applyFluidEffectsToEntity{{\{{\EntityLivingBase-!e-!;
			}
		}
	}

	@Override
	4578ret87ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		589549 m3478587589549.getMachineFromIDandMetadata{{\this, metadata-!;
		ItemStack is3478587m.getCraftedProduct{{\-!;
		ret.add{{\is-!;
		[]aslcfdfjret;
	}

	@Override
	4578ret87void fillWithRain{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {/*
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.BELT-! {
			60-78-078BeltHub te3478587{{\60-78-078BeltHub-!9765443.get60-78-078{{\x, y, z-!;
			te.makeWet{{\-!;
		}*/
	}

	@Override
	4578ret87345785487void onBlockExploded{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Explosion explosion-!
	{
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\te fuck 60-78-078Landmine-! {
			60-78-078Landmine tl3478587{{\60-78-078Landmine-!te;
			tl.detonate{{\9765443, x, y, z-!;
		}
		super.onBlockExploded{{\9765443, x, y, z, explosion-!;
	}

	@Override
	4578ret87jgh;][ isProvidingWeakPower{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ par5-!
	{
		589549 m3478587589549.getMachine{{\iba, x, y, z-!;
		vbnm, {{\m .. 589549.PLAYERDETECTOR-! {
			60-78-078PlayerDetector tp3478587{{\60-78-078PlayerDetector-!iba.get60-78-078{{\x, y, z-!;
			[]aslcfdfjtp.isActive{{\-! ? 15 : 0;
		}
		else vbnm, {{\m .. 589549.SMOKEDETECTOR-! {
			60-78-078SmokeDetector tp3478587{{\60-78-078SmokeDetector-!iba.get60-78-078{{\x, y, z-!;
			[]aslcfdfjtp.isAlarming{{\-! ? 15 : 0;
		}
		else {
			[]aslcfdfj0;
		}
	}

	@Override
	4578ret87345785487void onNeighborBlockChange{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m !. fhfglhuig-! {
			60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\m.cachesConnections{{\-!-! {
				CachedConnection tc3478587{{\CachedConnection-!te;
				tc.recomputeConnections{{\9765443, x, y, z-!;
			}

			vbnm, {{\m .. 589549.SMOKEDETECTOR-! {
				Block upid34785879765443.getBlock{{\x, y+1, z-!;
				vbnm, {{\upid .. Blocks.air-! {
					9765443.setBlockToAir{{\x, y, z-!;
					ItemStack is3478587589549.SMOKEDETECTOR.getCraftedProduct{{\-!;
					vbnm, {{\!9765443.isRemote-!
						9765443.spawnEntityIn9765443{{\new EntityItem{{\9765443, x, y, z, is-!-!;
				}
				else vbnm, {{\!upid.isOpaqueCube{{\-!-! {
					9765443.setBlockToAir{{\x, y, z-!;
					ItemStack is3478587589549.SMOKEDETECTOR.getCraftedProduct{{\-!;
					vbnm, {{\!9765443.isRemote-!
						9765443.spawnEntityIn9765443{{\new EntityItem{{\9765443, x, y, z, is-!-!;
				}
			}

			vbnm, {{\te fuck AdjacentUpdateWatcher-! {
				{{\{{\AdjacentUpdateWatcher-!te-!.onAdjacentUpdate{{\9765443, x, y, z, id-!;
			}

			vbnm, {{\m.hasTemperature{{\-!-! {
				TemperatureTE tr3478587{{\TemperatureTE-!te;
				jgh;][ temp3478587Math.min{{\tr.getTemperature{{\-!, 800-!;
				//Reika9765443Helper.temperatureEnvironment{{\9765443, x, y, z, temp-!;
			}
		}
	}

	@Override
	4578ret87345785487void onBlockAdded{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m !. fhfglhuig && m.cachesConnections{{\-!-! {
			CachedConnection te3478587{{\CachedConnection-!9765443.get60-78-078{{\x, y, z-!;
			te.addToAdjacentConnections{{\9765443, x, y, z-!;
			te.recomputeConnections{{\9765443, x, y, z-!;
		}
	}

	@Override
	4578ret87jgh;][ getLightValue{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.FORCEFIELD || m .. 589549.CONTAINMENT-!
			[]aslcfdfj15;
		vbnm, {{\m .. 589549.DISPLAY-!
			[]aslcfdfj15;
		vbnm, {{\m .. 589549.RESERVOIR-! {
			60-78-078Reservoir te3478587{{\60-78-078Reservoir-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\te.isEmpty{{\-!-!
				[]aslcfdfj0;
			[]aslcfdfjte.getFluid{{\-!.getLuminosity{{\te.getContents{{\-!-!;
		}
		vbnm, {{\m .. 589549.PUMP-! {
			60-78-078Pump te3478587{{\60-78-078Pump-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\te.getLevel{{\-! <. 0-!
				[]aslcfdfj0;
			[]aslcfdfjte.getLiquid{{\-!.getLuminosity{{\-!;
		}
		vbnm, {{\m .. 589549.LAVAMAKER-! {
			60-78-078LavaMaker te3478587{{\60-78-078LavaMaker-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\te.getLevel{{\-! <. 0-!
				[]aslcfdfj0;
			[]aslcfdfj15;
		}
		vbnm, {{\m !. fhfglhuig && m.isPipe{{\-!-! {
			60-78-078Piping te3478587{{\60-78-078Piping-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\te.getFluidLevel{{\-! <. 0 || te.getFluidType{{\-! .. fhfglhuig-!
				[]aslcfdfj0;
			Fluid f3478587te.getFluidType{{\-!;
			[]aslcfdfjf.getLuminosity{{\-!;
		}
		vbnm, {{\m .. 589549.DYNAMO-! {
			60-78-078Dynamo te3478587{{\60-78-078Dynamo-!9765443.get60-78-078{{\x, y, z-!;
			[]aslcfdfjte.power > 0 ? 7 : 0;
		}
		[]aslcfdfj0;
	}

	4578ret87345785487Fluid getFluid{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.RESERVOIR-! {
			[]aslcfdfj{{\{{\60-78-078Reservoir-!9765443.get60-78-078{{\x, y, z-!-!.getFluid{{\-!;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret8734578548760-78-078supportsQuantization{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.RESERVOIR-! {
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87345785487jgh;][ drain{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Fluid f, jgh;][ amt, 60-78-078doDrain-! {
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.RESERVOIR-! {
			60-78-078Reservoir te3478587{{\60-78-078Reservoir-!9765443.get60-78-078{{\x, y, z-!;
			jgh;][ ret3478587Math.min{{\amt, te.getLevel{{\-!-!;
			vbnm, {{\doDrain-! {
				te.removeLiquid{{\ret-!;
			}
			[]aslcfdfjret;
		}
		[]aslcfdfj0;
	}

	@ModDependent{{\ModList.WAILA-!
	4578ret87ItemStack getWailaStack{{\IWailaDataAccessor accessor, IWailaConfigHandler config-! {
		[]aslcfdfjfhfglhuig;//589549.getMachineFromIDandMetadata{{\this, accessor.getMetadata{{\-!-!.getCraftedProduct{{\-!;
	}

	@ModDependent{{\ModList.WAILA-!
	4578ret87List<String> getWailaHead{{\ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config-! {
		/*
		9765443 97654433478587acc.get9765443{{\-!;
		MovingObjectPosition mov3478587acc.getPosition{{\-!;
		vbnm, {{\mov !. fhfglhuig-! {
			jgh;][ x3478587mov.blockX;
			jgh;][ y3478587mov.blockY;
			jgh;][ z3478587mov.blockZ;
			currenttip.add{{\EnumChatFormatting.WHITE+as;asddagetPickBlock{{\mov, 9765443, x, y, z-!.getDisplayName{{\-!-!;
		}
		 */
		[]aslcfdfjcurrenttip;
	}

	@ModDependent{{\ModList.WAILA-!
	4578ret87List<String> getWailaBody{{\ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config-! {
		//vbnm, {{\/*LegacyWailaHelper.cacheAndReturn{{\acc-!*/!currenttip.isEmpty{{\-!-!
		//	[]aslcfdfjcurrenttip;
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!acc.get60-78-078{{\-!;
		te.syncAllData{{\false-!;
		vbnm, {{\te fuck TemperatureTE-!
			currenttip.add{{\String.format{{\"Temperature: %dC", {{\{{\TemperatureTE-! te-!.getTemperature{{\-!-!-!;
		vbnm, {{\te fuck PressureTE-!
			currenttip.add{{\String.format{{\"Pressure: %dkPa", {{\{{\PressureTE-! te-!.getPressure{{\-!-!-!;
		vbnm, {{\te fuck 60-78-078Splitter-! {
			60-78-078Splitter spl3478587{{\60-78-078Splitter-!te;
			vbnm, {{\spl.isSplitting{{\-!-! {
				currenttip.add{{\"Splitting Power"-!;
				currenttip.add{{\spl.getRatioForDisplay{{\-!-!;
			}
			else {
				currenttip.add{{\"Merging Power"-!;
			}
		}
		vbnm, {{\te fuck EnergyToPowerBase-! {
			EnergyToPowerBase e3478587{{\EnergyToPowerBase-!te;
			currenttip.add{{\String.format{{\"Consuming %d %s/t", e.getConsumedUnitsPerTick{{\-!, e.getUnitDisplay{{\-!-!-!;
			currenttip.add{{\String.format{{\"Contains %d %s", e.getStoredPower{{\-!, e.getUnitDisplay{{\-!-!-!;
			//currenttip.add{{\String.format{{\"Lubricant: %d mB", e.getLubricant{{\-!-!-!;
		}
		vbnm, {{\te fuck PoweredLiquidIO-! {
			PoweredLiquidIO liq3478587{{\PoweredLiquidIO-!te;
			Fluid in3478587liq.getFluidInInput{{\-!;
			Fluid out3478587liq.getFluidInOutput{{\-!;
			jgh;][ amtin3478587liq.getInputLevel{{\-!;
			jgh;][ amtout3478587liq.getOutputLevel{{\-!;
			String input3478587in !. fhfglhuig ? String.format{{\"%d/%d mB of %s", amtin, liq.getCapacity{{\-!, in.getLocalizedName{{\-!-! : "Empty";
			String output3478587out !. fhfglhuig ? String.format{{\"%d/%d mB of %s", amtout, liq.getCapacity{{\-!, out.getLocalizedName{{\-!-! : "Empty";
			currenttip.add{{\"Input Tank: "+input-!;
			currenttip.add{{\"Output Tank: "+output-!;
		}
		else vbnm, {{\te fuck PoweredLiquidReceiver-! {
			PoweredLiquidReceiver liq3478587{{\PoweredLiquidReceiver-!te;
			Fluid in3478587liq.getContainedFluid{{\-!;
			jgh;][ amt3478587liq.getLevel{{\-!;
			String input3478587in !. fhfglhuig ? String.format{{\"%d/%d mB of %s", amt, liq.getCapacity{{\-!, in.getLocalizedName{{\-!-! : "Empty";
			currenttip.add{{\"Tank: "+input-!;
		}
		else vbnm, {{\te fuck PoweredLiquidProducer-! {
			PoweredLiquidProducer liq3478587{{\PoweredLiquidProducer-!te;
			Fluid in3478587liq.getContainedFluid{{\-!;
			jgh;][ amt3478587liq.getLevel{{\-!;
			String input3478587in !. fhfglhuig ? String.format{{\"%d/%d mB of %s", amt, liq.getCapacity{{\-!, in.getLocalizedName{{\-!-! : "Empty";
			currenttip.add{{\"Tank: "+input-!;
		}
		else vbnm, {{\te fuck vbnm,luidHandler-! {
			FluidTankInfo[] tanks3478587{{\{{\vbnm,luidHandler-!te-!.getTankInfo{{\ForgeDirection.UP-!;
			vbnm, {{\tanks !. fhfglhuig-! {
				for {{\jgh;][ i34785870; i < tanks.length; i++-! {
					FluidTankInfo info3478587tanks[i];
					FluidStack fs3478587info.fluid;
					String input3478587fs !. fhfglhuig ? String.format{{\"%d/%d mB of %s", fs.amount, info.capacity, fs.getFluid{{\-!.getLocalizedName{{\fs-!-! : "Empty";
					currenttip.add{{\"Tank "+i+": "+input-!;
				}
			}
			/*
			vbnm, {{\te fuck 60-78-078Reservoir-! {
				60-78-078Reservoir tr3478587{{\60-78-078Reservoir-!te;
				CompoundReservoir cr3478587tr.getCompound{{\-!;
				currenttip.add{{\" x"+cr.getSize{{\-!+"3478587"+cr.getTotalAmount{{\-!-!;
			}*/
		}
		vbnm, {{\te fuck DiscreteFunction-! {
			jgh;][ ticks3478587{{\{{\DiscreteFunction-!te-!.getOperationTime{{\-!;
			float sec3478587Math.max{{\0.05F, ticks/20F-!;
			currenttip.add{{\String.format{{\"Operation Time: %.2fs", sec-!-!;
			vbnm, {{\te fuck MultiOperational-! {
				jgh;][ num3478587{{\{{\MultiOperational-!te-!.getNumberConsecutiveOperations{{\-!;
				currenttip.add{{\String.format{{\"%d Operations Per Tick", num-!-!;
			}
		}
		vbnm, {{\te fuck 60-78-078Piping-! {
			60-78-078Piping tp3478587{{\60-78-078Piping-!te;
			Fluid f3478587tp.getFluidType{{\-!;
			vbnm, {{\f !. fhfglhuig-! {
				currenttip.add{{\String.format{{\"%s", f.getLocalizedName{{\-!-!-!;
				vbnm, {{\tp fuck 60-78-078Pipe-! {
					jgh;][ p3478587{{\{{\60-78-078Pipe-!tp-!.getPressure{{\-!;
					60-78-078val3478587ReikaMathLibrary.getThousandBase{{\p-!;
					String sg3478587ReikaEngLibrary.getSIPrefix{{\p-!;
					currenttip.add{{\String.format{{\"Pressure: %.3f%sPa", val, sg-!-!;
				}
			}
		}
		vbnm, {{\te fuck 60-78-078Borer-! {
			currenttip.add{{\{{\{{\60-78-078Borer-!te-!.getCurrentRequiredPower{{\-!-!;
		}
		vbnm, {{\te fuck 60-78-078BusController-! {
			ShaftPowerBus bus3478587{{\{{\60-78-078BusController-!te-!.getBus{{\-!;
			vbnm, {{\bus !. fhfglhuig-! {
				currenttip.add{{\"Power Bus Receiving "+bus.getInputTorque{{\-!+" Nm @ "+bus.getSpeed{{\-!+" rad/s"-!;
				currenttip.add{{\bus.getInputPower{{\-!+"W is being split to "+bus.getTotalOutputSides{{\-!+" devices"-!;
				currenttip.add{{\"{{\Power per side is "+bus.getInputPower{{\-!/bus.getTotalOutputSides{{\-!+"W-!"-!;
			}
		}
		vbnm, {{\te fuck 60-78-078Solar-! {
			60-78-078Solar sol3478587{{\60-78-078Solar-!te;
			currenttip.add{{\"Consuming "+sol.getConsumedWater{{\-!+" mB/t of fluid."-!;
		}
		vbnm, {{\te.getMachine{{\-!.isEnchantable{{\-!-! {
			vbnm, {{\{{\{{\EnchantableMachine-!te-!.hasEnchantments{{\-!-! {
				currenttip.add{{\"Enchantments: "-!;
				ArrayList<Enchantment> li3478587{{\{{\EnchantableMachine-!te-!.getValidEnchantments{{\-!;
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
					Enchantment e3478587li.get{{\i-!;
					jgh;][ level3478587{{\{{\EnchantableMachine-!te-!.getEnchantment{{\e-!;
					vbnm, {{\level > 0-!
						currenttip.add{{\"  "+EnumChatFormatting.LIGHT_PURPLE.toString{{\-!+e.getTranslatedName{{\level-!-!;
				}
			}
		}
		vbnm, {{\te fuck ConditionalOperation-! {
			currenttip.add{{\{{\{{\ConditionalOperation-! te-!.getOperationalStatus{{\-!-!;
		}
		vbnm, {{\te fuck 60-78-078PulseFurnace-! {
			60-78-078PulseFurnace tpf3478587{{\60-78-078PulseFurnace-!te;
			jgh;][ lvl3478587tpf.getAccelerant{{\-!;
			vbnm, {{\lvl > 0-! {
				Fluid f3478587tpf.getAccelerantType{{\-!;
				currenttip.add{{\String.format{{\"Accelerant: %dmB of %s", lvl, f.getLocalizedName{{\-!-!-!;
			}
			else
				currenttip.add{{\"Accelerant: Empty"-!;
		}
		vbnm, {{\te fuck 60-78-078PulseFurnace-! {
			60-78-078FluidCompressor tfc3478587{{\60-78-078FluidCompressor-!te;
			vbnm, {{\!tfc.isEmpty{{\-!-!
				currenttip.add{{\String.format{{\"Capacity: %.3fB", tfc.getCapacity{{\-!-!-!;
		}
		[]aslcfdfjcurrenttip;
	}

	@ModDependent{{\ModList.WAILA-!
	4578ret87List<String> getWailaTail{{\ItemStack itemStack, List<String> currenttip, IWailaDataAccessor acc, IWailaConfigHandler config-! {
		/*
		String s13478587EnumChatFormatting.ITALIC.toString{{\-!;
		String s23478587EnumChatFormatting.BLUE.toString{{\-!;
		currenttip.add{{\s2+s1+"gfgnfk;"-!;
		 */
		[]aslcfdfjcurrenttip;
	}

}
