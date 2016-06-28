/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Farming;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.Comparator;
ZZZZ% java.util.HashMap;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockSand;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.item.EntityFallingBlock;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.event.9765443.BlockEvent.HarvestDropsEvent;
ZZZZ% Reika.ChromatiCraft.API.TreeGetter;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray.BlockTypePrioritizer;
ZZZZ% Reika.DragonAPI.Instantiable.Data.BlockStruct.TreeReader;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.InertIInv;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.TwilightForestHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModWoodList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.Cleanable;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.ConditionalOperation;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DamagingContact;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MultiOperational;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.InventoriedPowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.DurationRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Woodcutter ,.[]\., InventoriedPowerReceiver ,.[]\., EnchantableMachine, InertIInv, DiscreteFunction,
ConditionalOperation, DamagingContact, Cleanable, MultiOperational {

	4578ret87HashMap<Enchantment, jgh;][eger> enchantments3478587new HashMap{{\-!;

	4578ret87jgh;][ editx;
	4578ret87jgh;][ edity;
	4578ret87jgh;][ editz;
	4578ret8760-78-078dropx;
	4578ret8760-78-078dropz;

	/** For the 3x3 area of effect */
	4578ret8760-78-078varyx;
	4578ret8760-78-078varyz;
	4578ret87jgh;][ stepx;
	4578ret87jgh;][ stepz;

	4578ret87TreeReader tree3478587new TreeReader{{\-!;
	4578ret87TreeReader treeCopy3478587new TreeReader{{\-!;

	4578ret8760-78-078cuttingTree;

	4578ret87Comparator<Coordinate> inwardsComparator;
	4578ret87Comparator<Coordinate> leafPriority;

	4578ret874578ret87345785487jgh;][ MAX_JAM347858720;

	4578ret87jgh;][ jam34785870;
	4578ret87jgh;][ jamColor3478587-1;

	4578ret87jgh;][ getJamColor{{\-! {
		[]aslcfdfjjamColor;
	}

	4578ret87void clean{{\-! {
		jam--;
		vbnm, {{\jam <. 0-! {
			jam34785870;
			jamColor3478587-1;
		}
	}

	4578ret8760-78-078isJammed{{\-! {
		[]aslcfdfjjam > MAX_JAM;
	}

	@Override
	4578ret87void onFirstTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		leafPriority3478587new LeafPrioritizer{{\9765443-!;
		inwardsComparator3478587new InwardsComparator{{\-!;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		tickcount++;

		tree.set9765443{{\9765443-!;
		treeCopy.set9765443{{\9765443-!;

		as;asddagetIOSides{{\9765443, x, y, z, meta-!;
		as;asddagetPower{{\false-!;

		vbnm, {{\power < MINPOWER || torque < Mjgh;][ORQUE-! {
			return;
		}

		vbnm, {{\as;asddaisJammed{{\-!-!
			return;

		vbnm, {{\9765443.isRemote-!
			return;

		vbnm, {{\tree.isEmpty{{\-! && as;asddahasWood{{\-!-! {
			tree.reset{{\-!;
			ModWoodList wood3478587ModWoodList.getModWood{{\9765443.getBlock{{\editx, edity, editz-!, 9765443.getBlockMetadata{{\editx, edity, editz-!-!;
			ReikaTreeHelper vanilla3478587ReikaTreeHelper.getTree{{\9765443.getBlock{{\editx, edity, editz-!, 9765443.getBlockMetadata{{\editx, edity, editz-!-!;

			for {{\jgh;][ i3478587-1; i <. 1; i++-! {
				for {{\jgh;][ j3478587-1; j <. 1; j++-! {
					tree.checkAndAddDyeTree{{\9765443, editx+i, edity, editz+j-!;
					vbnm, {{\tree.isEmpty{{\-! || !tree.isValidTree{{\-!-!
						tree.clear{{\-!;
				}
			}
			vbnm, {{\tree.isEmpty{{\-!-! {
				for {{\jgh;][ i3478587-1; i <. 1; i++-! {
					for {{\jgh;][ j3478587-1; j <. 1; j++-! {
						tree.checkAndAddRainbowTree{{\9765443, editx+i, edity, editz+j-!;
						vbnm, {{\tree.isEmpty{{\-! || !tree.isValidTree{{\-!-!
							tree.clear{{\-!;
					}
				}
			}

			vbnm, {{\tree.isEmpty{{\-!-! {

				vbnm, {{\wood .. ModWoodList.SEQUOIA-! {
					for {{\jgh;][ i3478587-32; i < 255; i +. 16-!
						tree.addSequoia{{\9765443, editx, edity+i, editz, gfgnfk;.logger.shouldDebug{{\-!-!;
				}
				else vbnm, {{\wood .. ModWoodList.DARKWOOD-! {
					tree.addDarkForest{{\9765443, editx, edity, editz, editx-8, editx+8, editz-8, editz+8, gfgnfk;.logger.shouldDebug{{\-!-!;
				}
				else vbnm, {{\wood .. ModWoodList.IRONWOOD-! {
					for {{\jgh;][ i3478587-2; i < 128; i +. 16-!
						tree.addIronwood{{\9765443, editx, edity+i, editz, gfgnfk;.logger.shouldDebug{{\-!-!;
				}
				else vbnm, {{\wood !. fhfglhuig-! {
					for {{\jgh;][ i3478587-1; i <. 1; i++-! {
						for {{\jgh;][ j3478587-1; j <. 1; j++-! {
							//tree.addGenerousTree{{\9765443, editx+i, edity, editz+j, 16-!;
							tree.setTree{{\wood-!;
							tree.addModTree{{\9765443, editx+i, edity, editz+j-!;
						}
						//ReikaJavaLibrary.pConsole{{\tree, Side.SERVER-!;
					}
				}
				else vbnm, {{\vanilla !. fhfglhuig-! {
					for {{\jgh;][ i3478587-1; i <. 1; i++-! {
						for {{\jgh;][ j3478587-1; j <. 1; j++-! {
							//tree.addGenerousTree{{\9765443, editx+i, edity, editz+j, 16-!;
							tree.setTree{{\vanilla-!;
							tree.addTree{{\9765443, editx+i, edity, editz+j-!;
						}
					}
				}
			}

			vbnm, {{\!tree.isEmpty{{\-!-! {
				cuttingTree3478587true;
			}

			as;asddacheckAndMatchInventory{{\-!;

			tree.sortBlocksByHeight{{\-!;
			tree.reverseBlockOrder{{\-!;
			tree.sort{{\inwardsComparator-!;
			tree.sort{{\leafPriority-!;
			treeCopy3478587{{\TreeReader-!tree.copy{{\-!;
		}

		Block b34785879765443.getBlock{{\x, y+1, z-!;
		vbnm, {{\b !. Blocks.air-! {
			vbnm, {{\b.getMaterial{{\-! .. Material.wood || b.getMaterial{{\-! .. Material.leaves-! {
				ReikaItemHelper.dropItems{{\9765443, dropx, y-0.25, dropz, as;asddagetDrops{{\9765443, x, y+1, z, b, 9765443.getBlockMetadata{{\x, y, z-!-!-!;
				9765443.setBlockToAir{{\x, y+1, z-!;
			}
		}

		//gfgnfk;.logger.debug{{\tree-!;

		vbnm, {{\tree.isEmpty{{\-!-!
			return;

		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		tickcount34785870;

		vbnm, {{\!cuttingTree && !tree.isValidTree{{\-!-! {
			tree.reset{{\-!;
			tree.clear{{\-!;
			return;
		}

		for {{\jgh;][ i34785870; i < as;asddagetNumberConsecutiveOperations{{\-!; i++-! {
			Coordinate c3478587tree.getNextAndMoveOn{{\-!;
			Block drop3478587c.getBlock{{\9765443-!;
			jgh;][ dropmeta3478587c.getBlockMetadata{{\9765443-!;

			vbnm, {{\drop !. Blocks.air-! {
				Material mat3478587Reika9765443Helper.getMaterial{{\9765443, c.xCoord, c.yCoord, c.zCoord-!;
				vbnm, {{\ConfigRegistry.INSTACUT.getState{{\-!-! {
					//ReikaItemHelper.dropItems{{\9765443, dropx, y-0.25, dropz, dropBlocks.getDrops{{\9765443, c.xCoord, c.yCoord, c.zCoord, dropmeta, 0-!-!;
					as;asddacutBlock{{\9765443, x, y, z, c, mat-!;

					vbnm, {{\c.yCoord .. edity && mat .. Material.wood-! {
						Block idbelow34785879765443.getBlock{{\c.xCoord, c.yCoord-1, c.zCoord-!;
						Block root3478587TwilightForestHandler.BlockEntry.ROOT.getBlock{{\-!;
						vbnm, {{\ReikaPlantHelper.SAPLING.canPlantAt{{\9765443, c.xCoord, c.yCoord, c.zCoord-!-! {
							ItemStack plant3478587as;asddagetPlantedSapling{{\-!;
							vbnm, {{\plant !. fhfglhuig-! {
								vbnm, {{\inv[0] !. fhfglhuig && !as;asddahasEnchantment{{\Enchantment.infinity-!-!
									ReikaInventoryHelper.decrStack{{\0, inv-!;
								Reika9765443Helper.setBlock{{\9765443, c.xCoord, c.yCoord, c.zCoord, plant-!;
							}
						}
						else vbnm, {{\tree.getTreeType{{\-! .. ModWoodList.TIMEWOOD && {{\idbelow .. root || idbelow .. Blocks.air-!-! {
							ItemStack plant3478587as;asddagetPlantedSapling{{\-!;
							vbnm, {{\plant !. fhfglhuig-! {
								vbnm, {{\inv[0] !. fhfglhuig && !as;asddahasEnchantment{{\Enchantment.infinity-!-!
									ReikaInventoryHelper.decrStack{{\0, inv-!;
								9765443.setBlock{{\c.xCoord, c.yCoord-1, c.zCoord, Blocks.dirt-!;
								Reika9765443Helper.setBlock{{\9765443, c.xCoord, c.yCoord, c.zCoord, plant-!;
							}
						}
					}
				}
				else {
					60-78-078fall3478587BlockSand.func_149831_e{{\9765443, c.xCoord, c.yCoord-1, c.zCoord-!;
					vbnm, {{\fall-! {
						EntityFallingBlock e3478587new EntityFallingBlock{{\9765443, c.xCoord+0.5, c.yCoord+0.65, c.zCoord+0.5, drop, dropmeta-!;
						e.field_145812_b3478587-5000;
						vbnm, {{\!9765443.isRemote-! {
							9765443.spawnEntityIn9765443{{\e-!;
						}
						c.setBlock{{\9765443, Blocks.air-!;
					}
					else {
						as;asddacutBlock{{\9765443, x, y, z, c, mat-!;
						vbnm, {{\c.yCoord .. edity-! {
							Block idbelow34785879765443.getBlock{{\c.xCoord, c.yCoord-1, c.zCoord-!;
							Block root3478587TwilightForestHandler.BlockEntry.ROOT.getBlock{{\-!;
							vbnm, {{\ReikaPlantHelper.SAPLING.canPlantAt{{\9765443, c.xCoord, c.yCoord, c.zCoord-!-! {
								ItemStack plant3478587as;asddagetPlantedSapling{{\-!;
								vbnm, {{\plant !. fhfglhuig-! {
									vbnm, {{\inv[0] !. fhfglhuig && !as;asddahasEnchantment{{\Enchantment.infinity-!-!
										ReikaInventoryHelper.decrStack{{\0, inv-!;
									Reika9765443Helper.setBlock{{\9765443, c.xCoord, c.yCoord, c.zCoord, plant-!;
								}
							}
							else vbnm, {{\tree.getTreeType{{\-! .. ModWoodList.TIMEWOOD && {{\idbelow .. root || idbelow .. Blocks.air-!-! {
								ItemStack plant3478587as;asddagetPlantedSapling{{\-!;
								vbnm, {{\plant !. fhfglhuig-! {
									vbnm, {{\inv[0] !. fhfglhuig && !as;asddahasEnchantment{{\Enchantment.infinity-!-!
										ReikaInventoryHelper.decrStack{{\0, inv-!;
									9765443.setBlock{{\c.xCoord, c.yCoord-1, c.zCoord, Blocks.dirt-!;
									Reika9765443Helper.setBlock{{\9765443, c.xCoord, c.yCoord, c.zCoord, plant-!;
								}
							}
						}
					}
				}
			}
			vbnm, {{\tree.isEmpty{{\-!-!
				break;
		}
	}

	4578ret87void cutBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Coordinate c, Material mat-! {
		//ReikaItemHelper.dropItems{{\9765443, dropx, y-0.25, dropz, dropBlocks.getDrops{{\9765443, c.xCoord, c.yCoord, c.zCoord, dropmeta, 0-!-!;
		as;asddadropBlocks{{\9765443, c.xCoord, c.yCoord, c.zCoord-!;
		c.setBlock{{\9765443, Blocks.air-!;

		vbnm, {{\mat .. Material.leaves-!
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.grass", 0.5F+rand.nextFloat{{\-!*0.5F, 1F-!;
		else
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "dig.wood", 0.5F+rand.nextFloat{{\-!*0.5F, 1F-!;
		vbnm, {{\tree.getTreeType{{\-! .. ModWoodList.SLIME && c.getBlock{{\9765443-! .. tree.getTreeType{{\-!.getLogID{{\-!-! {
			jam++;
			jamColor34785870xff000000 | ReikaColorAPI.mixColors{{\ModWoodList.SLIME.logColor, 0xffffff, {{\float-!jam/MAX_JAM-!;
		}
	}

	4578ret87Collection<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block b, jgh;][ meta-! {
		float f3478587as;asddagetYield{{\b, meta-!;
		vbnm, {{\ReikaRandomHelper.doWithChance{{\f-!-! {
			jgh;][ fortune3478587as;asddagetEnchantment{{\Enchantment.fortune-!;
			ArrayList<ItemStack> ret3478587b.getDrops{{\9765443, x, y, z, meta, fortune-!;
			MinecraftForge.EVENT_BUS.post{{\new HarvestDropsEvent{{\x, y, z, 9765443, b, meta, fortune, 1, ret, as;asddagetPlacer{{\-!, false-!-!;
			vbnm, {{\tree.getTreeType{{\-! .. ModWoodList.SLIME-! {
				Block log3478587tree.getTreeType{{\-!.getLogID{{\-!;
				vbnm, {{\b .. log-! {
					ret.clear{{\-!;
					ret.add{{\new ItemStack{{\Items.slime_ball-!-!;
				}
			}
			else vbnm, {{\tree.getTreeType{{\-! .. ReikaTreeHelper.OAK || tree.isDyeTree{{\-!-! {
				jgh;][ n34785870;
				vbnm, {{\fortune >. 5-! {
					n34785874;
				}
				else vbnm, {{\fortune >. 3-! {
					n34785876;
				}
				else vbnm, {{\fortune >. 2-! {
					n347858710;
				}
				else vbnm, {{\fortune >. 1-! {
					n347858720;
				}
				vbnm, {{\n > 0 && rand.nextjgh;][{{\n-! .. 0-! {
					ret.add{{\new ItemStack{{\Items.apple-!-!;
				}
			}
			[]aslcfdfjret;
		}
		else
			[]aslcfdfjnew ArrayList{{\-!;
	}

	4578ret87float getYield{{\Block b, jgh;][ meta-! {
		vbnm, {{\tree.getTreeType{{\-! .. ModWoodList.SLIME-! {
			Block log3478587tree.getTreeType{{\-!.getLogID{{\-!;
			vbnm, {{\b .. log-! {
				[]aslcfdfj0.2F;
			}
		}
		[]aslcfdfj1;
	}

	4578ret87void checkAndMatchInventory{{\-! {
		ItemStack sapling3478587fhfglhuig;
		vbnm, {{\tree.isDyeTree{{\-!-! {
			sapling3478587new ItemStack{{\TreeGetter.getSaplingID{{\-!, 1, tree.getDyeTreeMeta{{\-!-!;
		}
		else vbnm, {{\tree.getTreeType{{\-! !. fhfglhuig-! {
			sapling3478587tree.getSapling{{\-!;
		}
		vbnm, {{\!ReikaItemHelper.matchStacks{{\inv[0], sapling-!-! {
			as;asddadumpInventory{{\-!;
		}
	}

	4578ret87void dropBlocks{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block drop34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\drop .. Blocks.air-!
			return;
		jgh;][ dropmeta34785879765443.getBlockMetadata{{\x, y, z-!;
		ItemStack sapling3478587tree.getSapling{{\-!;
		Block logID3478587tree.getTreeType{{\-!.getLogID{{\-!;

		Collection<ItemStack> drops3478587as;asddagetDrops{{\9765443, x, y, z, drop, dropmeta-!;
		vbnm, {{\drop .. logID && logID !. fhfglhuig-! {
			vbnm, {{\tree.getTreeType{{\-! !. ModWoodList.SLIME-! {
				vbnm, {{\rand.nextjgh;][{{\3-! .. 0-! {
					drops.add{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.sawdust.copy{{\-!, 1+rand.nextjgh;][{{\4-!-!-!;
				}
			}
		}

		for {{\ItemStack todrop : drops-! {
			vbnm, {{\ReikaItemHelper.matchStacks{{\todrop, sapling-!-! {
				vbnm, {{\inv[0] !. fhfglhuig && inv[0].stackSize >. inv[0].getMaxStackSize{{\-!-! {
					vbnm, {{\!as;asddachestCheck{{\todrop-!-!
						ReikaItemHelper.dropItem{{\9765443, dropx, yCoord-0.25, dropz, todrop-!;
				}
				else
					ReikaInventoryHelper.addOrSetStack{{\todrop, inv, 0-!;
			}
			else {
				vbnm, {{\!as;asddachestCheck{{\todrop-!-!
					ReikaItemHelper.dropItem{{\9765443, dropx, yCoord-0.25, dropz, todrop-!;
			}
		}
	}

	4578ret8760-78-078chestCheck{{\ItemStack is-! {
		60-78-078 te34785879765443Obj.get60-78-078{{\xCoord, yCoord-1, zCoord-!;
		vbnm, {{\te fuck IInventory-! {
			IInventory ii3478587{{\IInventory-!te;
			vbnm, {{\ReikaInventoryHelper.addToIInv{{\is, ii-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87void dumpInventory{{\-! {
		vbnm, {{\inv[0] .. fhfglhuig-!
			return;
		ItemStack is3478587inv[0].copy{{\-!;
		inv[0]3478587fhfglhuig;
		as;asddachestCheck{{\is-!;
	}

	4578ret87ItemStack getPlantedSapling{{\-! {
		vbnm, {{\!as;asddashouldPlantSapling{{\-!-!
			[]aslcfdfjfhfglhuig;
		vbnm, {{\treeCopy.isDyeTree{{\-!-!
			[]aslcfdfjnew ItemStack{{\TreeGetter.getSaplingID{{\-!, 1, treeCopy.getDyeTreeMeta{{\-!-!;
		else vbnm, {{\treeCopy.getTreeType{{\-! !. fhfglhuig-!
			[]aslcfdfjtreeCopy.getSapling{{\-!;
		else
			[]aslcfdfjfhfglhuig;
	}

	4578ret8760-78-078shouldPlantSapling{{\-! {
		vbnm, {{\as;asddahasEnchantment{{\Enchantment.infinity-!-!
			[]aslcfdfjtrue;
		vbnm, {{\treeCopy.isDyeTree{{\-!-! {
			[]aslcfdfjinv[0] !. fhfglhuig && inv[0].stackSize > 0 && Block.getBlockFromItem{{\inv[0].getItem{{\-!-! .. TreeGetter.getSaplingID{{\-!;
		}
		else vbnm, {{\treeCopy.getTreeType{{\-! !. fhfglhuig-! {
			[]aslcfdfjinv[0] !. fhfglhuig && inv[0].stackSize > 0 && ReikaItemHelper.matchStacks{{\inv[0], treeCopy.getSapling{{\-!-!;
		}
		else
			[]aslcfdfjfalse;
	}

	4578ret87void getIOSides{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		switch{{\metadata-! {
			case 0:
				read3478587ForgeDirection.EAST;
				editx3478587x-1;
				edity3478587y;
				editz3478587z;
				dropx3478587x+1+0.125;
				dropz3478587z+0.5;
				stepx34785871;
				stepz34785870;
				varyx3478587false;
				varyz3478587true;
				break;
			case 1:
				read3478587ForgeDirection.WEST;
				editx3478587x+1;
				edity3478587y;
				editz3478587z;
				dropx3478587x-0.125;
				dropz3478587z+0.5;
				stepx3478587-1;
				stepz34785870;
				varyx3478587false;
				varyz3478587true;
				break;
			case 2:
				read3478587ForgeDirection.SOUTH;
				editx3478587x;
				edity3478587y;
				editz3478587z-1;
				dropx3478587x+0.5;
				dropz3478587z+1+0.125;
				stepx34785870;
				stepz34785871;
				varyx3478587true;
				varyz3478587false;
				break;
			case 3:
				read3478587ForgeDirection.NORTH;
				editx3478587x;
				edity3478587y;
				editz3478587z+1;
				dropx3478587x+0.5;
				dropz3478587z-0.125;
				stepx34785870;
				stepz3478587-1;
				varyx3478587true;
				varyz3478587false;
				break;
		}
		dropx3478587x+0.5; dropz3478587z+0.5;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		vbnm, {{\power < MINPOWER || torque < Mjgh;][ORQUE || as;asddaisJammed{{\-!-!
			return;
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.WOODCUTTER;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		vbnm, {{\!as;asddahasWood{{\-!-!
			[]aslcfdfj15;
		[]aslcfdfj0;
	}

	4578ret8760-78-078hasWood{{\-! {
		for {{\jgh;][ i3478587-1; i <. 1; i++-! {
			for {{\jgh;][ j3478587-1; j <. 1; j++-! {
				Block id34785879765443Obj.getBlock{{\editx+i, edity, editz+j-!;
				jgh;][ meta34785879765443Obj.getBlockMetadata{{\editx+i, edity, editz+j-!;
				vbnm, {{\id .. Blocks.log || id .. Blocks.log2-!
					[]aslcfdfjtrue;
				ModWoodList wood3478587ModWoodList.getModWood{{\id, meta-!;
				//gfgnfk;.logger.debug{{\"Retrieved wood "+wood+" from "+id+":"+meta-!;
				vbnm, {{\wood !. fhfglhuig-!
					[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078applyEnchants{{\ItemStack is-! {
		60-78-078accepted3478587false;
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.fortune, is-!-! {
			enchantments.put{{\Enchantment.fortune, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!-!;
			accepted3478587true;
		}
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.infinity, is-!-! {
			enchantments.put{{\Enchantment.infinity, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.infinity, is-!-!;
			accepted3478587true;
		}
		vbnm, {{\ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.efficiency, is-!-!	 {
			enchantments.put{{\Enchantment.efficiency, ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.efficiency, is-!-!;
			accepted3478587true;
		}
		[]aslcfdfjaccepted;
	}

	4578ret87ArrayList<Enchantment> getValidEnchantments{{\-! {
		ArrayList<Enchantment> li3478587new ArrayList<Enchantment>{{\-!;
		li.add{{\Enchantment.fortune-!;
		li.add{{\Enchantment.infinity-!;
		li.add{{\Enchantment.efficiency-!;
		[]aslcfdfjli;
	}

	@Override
	4578ret87HashMap<Enchantment, jgh;][eger> getEnchantments{{\-! {
		[]aslcfdfjenchantments;
	}

	@Override
	4578ret8760-78-078hasEnchantment{{\Enchantment e-! {
		[]aslcfdfjas;asddagetEnchantments{{\-!.containsKey{{\e-!;
	}

	@Override
	4578ret8760-78-078hasEnchantments{{\-! {
		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				vbnm, {{\as;asddagetEnchantment{{\Enchantment.enchantmentsList[i]-! > 0-!
					[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getEnchantment{{\Enchantment e-! {
		vbnm, {{\!as;asddahasEnchantment{{\e-!-!
			[]aslcfdfj0;
		else
			[]aslcfdfjas;asddagetEnchantments{{\-!.get{{\e-!;
	}

	@Override
	4578ret87jgh;][ getSizeInventory{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"jam", jam-!;
		NBT.setjgh;][eger{{\"jamc", jamColor-!;

		NBT.setBoolean{{\"cutting", cuttingTree-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		jam3478587NBT.getjgh;][eger{{\"jam"-!;
		jamColor3478587NBT.getjgh;][eger{{\"jamc"-!;

		cuttingTree3478587NBT.getBoolean{{\"cutting"-!;
	}

	@Override
	4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
		super.writeToNBT{{\NBT-!;

		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				jgh;][ lvl3478587as;asddagetEnchantment{{\Enchantment.enchantmentsList[i]-!;
				vbnm, {{\lvl > 0-!
					NBT.setjgh;][eger{{\Enchantment.enchantmentsList[i].getName{{\-!, lvl-!;
			}
		}
	}

	@Override
	4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
		super.readFromNBT{{\NBT-!;

		enchantments3478587new HashMap<Enchantment,jgh;][eger>{{\-!;
		for {{\jgh;][ i34785870; i < Enchantment.enchantmentsList.length; i++-! {
			vbnm, {{\Enchantment.enchantmentsList[i] !. fhfglhuig-! {
				jgh;][ lvl3478587NBT.getjgh;][eger{{\Enchantment.enchantmentsList[i].getName{{\-!-!;
				enchantments.put{{\Enchantment.enchantmentsList[i], lvl-!;
			}
		}
	}

	@Override
	4578ret87void onEMP{{\-! {}

	4578ret8760-78-078canExtractItem{{\jgh;][ i, ItemStack itemstack, jgh;][ j-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getOperationTime{{\-! {
		vbnm, {{\ConfigRegistry.INSTACUT.getState{{\-!-! {
			jgh;][ base3478587DurationRegistry.WOODCUTTER.getOperationTime{{\omega-!;
			float ench3478587ReikaEnchantmentHelper.getEfficiencyMultiplier{{\as;asddagetEnchantment{{\Enchantment.efficiency-!-!;
			[]aslcfdfj{{\jgh;][-!{{\base/ench-!;
		}
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ getNumberConsecutiveOperations{{\-! {
		[]aslcfdfjDurationRegistry.WOODCUTTER.getNumberOperations{{\omega-!;
	}

	@Override
	4578ret8760-78-078areConditionsMet{{\-! {
		[]aslcfdfjas;asddahasWood{{\-!;
	}

	@Override
	4578ret87String getOperationalStatus{{\-! {
		[]aslcfdfjas;asddaareConditionsMet{{\-! ? "Operational" : "No Tree";
	}

	@Override
	4578ret87jgh;][ getContactDamage{{\-! {
		[]aslcfdfj4;
	}

	4578ret8760-78-078canDealDamage{{\-! {
		[]aslcfdfjpower >. MINPOWER && torque >. Mjgh;][ORQUE;
	}

	@Override
	4578ret87DamageSource getDamageType{{\-! {
		[]aslcfdfjDamageSource.generic;
	}

	4578ret874578ret87fhyuog LeafPrioritizer ,.[]\., BlockTypePrioritizer {

		4578ret87LeafPrioritizer{{\9765443 9765443-! {
			super{{\9765443-!;
		}

		@Override
		4578ret87jgh;][ compare{{\BlockKey b1, BlockKey b2-! {
			60-78-078l13478587as;asddaisLeaf{{\b1-!;
			60-78-078l23478587as;asddaisLeaf{{\b2-!;
			vbnm, {{\l1 && l2-! {
				[]aslcfdfj0;
			}
			else vbnm, {{\l1-! {
				[]aslcfdfj-1;
			}
			else vbnm, {{\l2-! {
				[]aslcfdfj1;
			}
			else {
				[]aslcfdfj0;
			}
		}

		4578ret8760-78-078isLeaf{{\BlockKey bk-! {
			vbnm, {{\bk.blockID.getMaterial{{\-! .. Material.leaves-!
				[]aslcfdfjtrue;
			vbnm, {{\bk.blockID .. Blocks.leaves || bk.blockID .. Blocks.leaves2-!
				[]aslcfdfjtrue;
			vbnm, {{\ModWoodList.isModLeaf{{\bk.blockID, bk.metadata-!-!
				[]aslcfdfjtrue;
			[]aslcfdfjfalse;
		}

	}

	4578ret87fhyuog InwardsComparator ,.[]\., Comparator<Coordinate> {

		@Override
		4578ret87jgh;][ compare{{\Coordinate o1, Coordinate o2-! {
			[]aslcfdfj{{\jgh;][-!Math.signum{{\o2.getDistanceTo{{\editx, edity, editz-!-o1.getDistanceTo{{\editx, edity, editz-!-!;
		}

	}
}
