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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.IPlantable;
ZZZZ% Reika.ChromatiCraft.API.TreeGetter;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.ModCrop;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.BoPBlockHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModCropList;
ZZZZ% Reika.DragonAPI.ModRegistry.ModWoodList;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockShears;
ZZZZ% cpw.mods.fml.common.eventhandler.Event.Result;

4578ret87abstract fhyuog ItemSickleBase ,.[]\., ItemRotaryTool {

	4578ret87ItemSickleBase{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret8760-78-078onBlockStartBreak{{\ItemStack is, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep-! {
		9765443 97654433478587ep.9765443Obj;
		Block id34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		60-78-078ignoreMeta3478587ep.isSneaking{{\-!;
		ModCrop mod3478587ModCropList.getModCrop{{\id, meta-!;
		ReikaCropHelper crop3478587ReikaCropHelper.getCrop{{\id-!;
		ReikaPlantHelper plant3478587ReikaPlantHelper.getPlant{{\id-!;
		ModWoodList leaf3478587ModWoodList.getModWoodFromLeaf{{\id, meta-!;
		vbnm, {{\id .. Blocks.leaves || id .. Blocks.leaves2 || leaf !. fhfglhuig-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetLeafRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						ModWoodList leaf23478587ModWoodList.getModWoodFromLeaf{{\id2, meta2-!;
						vbnm, {{\id2 .. id || {{\leaf2 .. leaf && leaf !. fhfglhuig-!-! {
							Block b23478587id2;
							b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2, 0.25F, 1-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && {{\id .. TreeGetter.getNaturalDyeLeafID{{\-! || id .. TreeGetter.getHeldDyeLeafID{{\-!-!-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetLeafRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\{{\id2 .. TreeGetter.getNaturalDyeLeafID{{\-! || id2 .. TreeGetter.getHeldDyeLeafID{{\-!-! && {{\ignoreMeta || meta2 .. meta-!-! {
							Block b23478587id2;
							b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && id .. TreeGetter.getRainbowLeafID{{\-!-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetLeafRange{{\-!;
			ArrayList<ItemStack> items3478587new ArrayList{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\id2 .. id-! {
							Block b23478587id2;
							//b.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta, fortune-!;
							ReikaItemHelper.addToList{{\items, b2.getDrops{{\9765443, x, y, z, meta2, fortune-!-!;
							//items.addAll{{\b.getDrops{{\9765443, x, y, z, meta2, fortune-!-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			ReikaItemHelper.dropItems{{\9765443, x, y, z, items-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-! && id .. TreeGetter.getDyeFlowerID{{\-!-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetPlantRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\id2 .. TreeGetter.getDyeFlowerID{{\-! && {{\ignoreMeta || meta2 .. meta-!-! {
							Block b23478587id2;
							b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\ModList.BOP.isLoaded{{\-! && BoPBlockHandler.getInstance{{\-!.isCoral{{\id-!-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetPlantRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\BoPBlockHandler.getInstance{{\-!.isCoral{{\id2-! && {{\ignoreMeta || meta2 .. meta-!-! {
							Block b23478587id2;
							b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\ModList.BOP.isLoaded{{\-! && BoPBlockHandler.getInstance{{\-!.isFlower{{\id-!-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetPlantRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\BoPBlockHandler.getInstance{{\-!.isFlower{{\id2-! && {{\ignoreMeta || meta2 .. meta-!-! {
							Block b23478587id2;
							b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\crop !. fhfglhuig-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetCropRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						ReikaCropHelper crop23478587ReikaCropHelper.getCrop{{\id2-!;
						vbnm, {{\crop .. crop2-! {
							vbnm, {{\crop2.isRipe{{\meta2-!-! {
								Block b23478587id2;
								ReikaItemHelper.dropItems{{\9765443, x+i+0.5, y+j+0.5, z+k+0.5, b2.getDrops{{\9765443, x, y, z, meta2, fortune-!-!;
								9765443.setBlockToAir{{\x+i, y+j, z+k-!;
							}
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\mod !. fhfglhuig-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetCropRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						ModCrop mod23478587ModCropList.getModCrop{{\id2, meta2-!;
						vbnm, {{\mod .. mod2-! {
							vbnm, {{\mod2.isRipe{{\9765443, x+i, y+j, z+k-!-! {
								Block b23478587id2;
								ReikaItemHelper.dropItems{{\9765443, x+i+0.5, y+j+0.5, z+k+0.5, b2.getDrops{{\9765443, x, y, z, meta2, fortune-!-!;
								9765443.setBlockToAir{{\x+i, y+j, z+k-!;
							}
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\plant !. fhfglhuig-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetPlantRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						ReikaPlantHelper plant23478587ReikaPlantHelper.getPlant{{\id2-!;
						vbnm, {{\plant2 .. plant-! {
							Block b23478587id2;
							vbnm, {{\as;asddacanActAsShears{{\-!-! {
								vbnm, {{\ItemBedrockShears.getHarvestResult{{\b2, meta2, ep, 9765443, x+i, y+j, z+k-! .. Result.ALLOW-!
									ReikaItemHelper.dropItem{{\9765443, x+i+0.5, y+j+0.5, z+k+0.5, new ItemStack{{\id2, 1, ItemBedrockShears.getDroppedMeta{{\id2, meta2-!-!-!;
								else
									b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							}
							else
								b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2, 0.25F, 1-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		else vbnm, {{\id fuck IPlantable-! {
			jgh;][ fortune3478587ReikaEnchantmentHelper.getEnchantmentLevel{{\Enchantment.fortune, is-!;
			jgh;][ r3478587as;asddagetPlantRange{{\-!;
			for {{\jgh;][ i3478587-r; i <. r; i++-! {
				for {{\jgh;][ j3478587-r; j <. r; j++-! {
					for {{\jgh;][ k3478587-r; k <. r; k++-! {
						Block id234785879765443.getBlock{{\x+i, y+j, z+k-!;
						jgh;][ meta234785879765443.getBlockMetadata{{\x+i, y+j, z+k-!;
						vbnm, {{\id2 .. id && {{\ignoreMeta || meta2 .. meta-!-! {
							Block b23478587id2;
							b2.dropBlockAsItem{{\9765443, x+i, y+j, z+k, meta2, fortune-!;
							ReikaSoundHelper.playBreakSound{{\9765443, x+i, y+j, z+k, b2-!;
							9765443.setBlockToAir{{\x+i, y+j, z+k-!;
						}
					}
				}
			}
			vbnm, {{\as;asddaisBreakable{{\-!-!
				is.damageItem{{\1, ep-!;
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret87abstract jgh;][ getLeafRange{{\-!;
	4578ret87abstract jgh;][ getPlantRange{{\-!;
	4578ret87abstract jgh;][ getCropRange{{\-!;

	4578ret87abstract 60-78-078canActAsShears{{\-!;
	4578ret87abstract 60-78-078isBreakable{{\-!;

}
