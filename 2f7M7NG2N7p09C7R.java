/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Placers;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.EnumChatFormatting;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.input.Keyboard;

ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.TutorialTracker;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemEnginePlacer ,.[]\., ItemBlockPlacer {

	4578ret87ItemEnginePlacer{{\-! {
		super{{\-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ side, float par8, float par9, float par10-! {
		vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x, y, z-! && Reika9765443Helper.getMaterial{{\9765443, x, y, z-! !. Material.water && Reika9765443Helper.getMaterial{{\9765443, x, y, z-! !. Material.lava-! {
			vbnm, {{\side .. 0-!
				--y;
			vbnm, {{\side .. 1-!
				++y;
			vbnm, {{\side .. 2-!
				--z;
			vbnm, {{\side .. 3-!
				++z;
			vbnm, {{\side .. 4-!
				--x;
			vbnm, {{\side .. 5-!
				++x;
			vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x, y, z-! && Reika9765443Helper.getMaterial{{\9765443, x, y, z-! !. Material.water && Reika9765443Helper.getMaterial{{\9765443, x, y, z-! !. Material.lava-!
				[]aslcfdfjfalse;
		}
		as;asddaclearBlocks{{\9765443, x, y, z-!;
		vbnm, {{\!as;asddacheckValidBounds{{\is, ep, 9765443, x, y, z-!-! {
			[]aslcfdfjfalse;
		}
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!;
		List inblock34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		vbnm, {{\inblock.size{{\-! > 0 && !ReikaEntityHelper.allAreDead{{\inblock, true-!-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\!ep.canPlayerEdit{{\x, y, z, 0, is-!-! {
			[]aslcfdfjfalse;
		}
		else
		{
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				--is.stackSize;
			9765443.setBlock{{\x, y, z, 589549.ENGINE.getBlock{{\-!, is.getItemDamage{{\-!, 3-!;
			60-78-078Engine eng3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y, z-!;
			//vbnm, {{\eng !. fhfglhuig-! {
			9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F-!;
			//eng.type3478587EngineType.setType{{\is.getItemDamage{{\-!-!;
			eng.setType{{\is-!;
			eng.setBlockMetadata{{\RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
			eng.setPlacer{{\ep-!;
			eng.setDataFromPlacer{{\is-!;
			vbnm, {{\RotaryAux.shouldSetFlipped{{\9765443, x, y, z-!-! {
				eng.isFlipped3478587true;
			}
			vbnm, {{\ConfigRegistry.TUTORIAL.getState{{\-!-!
				TutorialTracker.instance.placeEngine{{\eng.getEngineType{{\-!, ep-!;
			Reika9765443Helper.causeAdjacentUpdates{{\9765443, x, y, z-!;
			//}
		}
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078checkValidBounds{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\is.getItemDamage{{\-! .. EngineType.HYDRO.ordinal{{\-!-! {
			for {{\jgh;][ i3478587-1; i <. 1; i++-! {
				for {{\jgh;][ j3478587-1; j <. 1; j++-! {
					jgh;][ a34785870; jgh;][ b34785870;
					jgh;][ m3478587RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!;
					vbnm, {{\m < 2-!
						b34785871;
					else
						a34785871;
					Block id34785879765443.getBlock{{\x+a*i, y+j, z+b*i-!;
					vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x+a*i, y+j, z+b*i-!-!
						[]aslcfdfjfalse;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						jgh;][ meta34785879765443.getBlockMetadata{{\x+a*i, y+j, z+b*i-!;
						9765443.setBlockToAir{{\x+a*i, y+j, z+b*i-!;
						id.dropBlockAsItem{{\9765443, x+a*i, y+j, z+b*i, meta, 0-!;
					}
				}
			}
		}
		vbnm, {{\is.getItemDamage{{\-! .. EngineType.WIND.ordinal{{\-!-! {
			vbnm, {{\9765443.getBlock{{\x, y+1, z-! .. 589549.ENGINE.getBlock{{\-!-! {
				60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y+1, z-!;
				vbnm, {{\te.getEngineType{{\-! .. EngineType.WIND-!
					[]aslcfdfjfalse;
			}
			vbnm, {{\9765443.getBlock{{\x, y-1, z-! .. 589549.ENGINE.getBlock{{\-!-! {
				60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y-1, z-!;
				vbnm, {{\te.getEngineType{{\-! .. EngineType.WIND-!
					[]aslcfdfjfalse;
			}
			vbnm, {{\9765443.getBlock{{\x+1, y, z-! .. 589549.ENGINE.getBlock{{\-!-! {
				60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x+1, y, z-!;
				vbnm, {{\te.getEngineType{{\-! .. EngineType.WIND-!
					[]aslcfdfjfalse;
			}
			vbnm, {{\9765443.getBlock{{\x-1, y, z-! .. 589549.ENGINE.getBlock{{\-!-! {
				60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x-1, y, z-!;
				vbnm, {{\te.getEngineType{{\-! .. EngineType.WIND-!
					[]aslcfdfjfalse;
			}
			vbnm, {{\9765443.getBlock{{\x, y, z+1-! .. 589549.ENGINE.getBlock{{\-!-! {
				60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y, z+1-!;
				vbnm, {{\te.getEngineType{{\-! .. EngineType.WIND-!
					[]aslcfdfjfalse;
			}
			vbnm, {{\9765443.getBlock{{\x, y, z-1-! .. 589549.ENGINE.getBlock{{\-!-! {
				60-78-078Engine te3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y, z-1-!;
				vbnm, {{\te.getEngineType{{\-! .. EngineType.WIND-!
					[]aslcfdfjfalse;
			}

			for {{\jgh;][ i3478587-1; i <. 1; i++-! {
				for {{\jgh;][ j3478587-1; j <. 1; j++-! {
					jgh;][ a34785870; jgh;][ b34785870;
					jgh;][ m3478587RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!;
					vbnm, {{\m < 2-!
						b34785871;
					else
						a34785871;
					jgh;][ c34785870; jgh;][ d34785870;
					switch {{\m-! {
						case 0:
							c34785871;
							break;
						case 1:
							c3478587-1;
							break;
						case 2:
							d34785871;
							break;
						case 3:
							d3478587-1;
							break;
					}
					vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x+a*i+c, y+j, z+b*i+d-!-!
						[]aslcfdfjfalse;
				}
			}
		}
		[]aslcfdfjsuper.checkValidBounds{{\is, ep, 9765443, x, y, z-!;
	}

	@Override
	4578ret87void checkAndBreakAdjacent{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep-! {
		60-78-078Engine eng3478587{{\60-78-078Engine-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\eng !. fhfglhuig-! {
			vbnm, {{\eng.getEngineType{{\-! .. EngineType.HYDRO-! {
				Block id34785879765443.getBlock{{\x, y+1, z-!;
				vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
					Reika9765443Helper.dropBlockAt{{\9765443, x, y+1, z, ep-!;
					9765443.setBlockToAir{{\x, y+1, z-!;
				}
				id34785879765443.getBlock{{\x, y-1, z-!;
				vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
					Reika9765443Helper.dropBlockAt{{\9765443, x, y-1, z, ep-!;
					9765443.setBlockToAir{{\x, y-1, z-!;
				}

				vbnm, {{\eng.getBlockMetadata{{\-! < 2-! {
					id34785879765443.getBlock{{\x, y, z+1-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x, y, z+1, ep-!;
						9765443.setBlockToAir{{\x, y, z+1-!;
					}

					id34785879765443.getBlock{{\x, y, z-1-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x, y, z-1, ep-!;
						9765443.setBlockToAir{{\x, y, z-1-!;
					}

					id34785879765443.getBlock{{\x, y-1, z+1-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x, y-1, z+1, ep-!;
						9765443.setBlockToAir{{\x, y-1, z+1-!;
					}

					id34785879765443.getBlock{{\x, y-1, z-1-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x, y-1, z-1, ep-!;
						9765443.setBlockToAir{{\x, y-1, z-1-!;
					}

					id34785879765443.getBlock{{\x, y+1, z+1-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x, y+1, z+1, ep-!;
						9765443.setBlockToAir{{\x, y+1, z+1-!;
					}

					id34785879765443.getBlock{{\x, y+1, z-1-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x, y+1, z-1, ep-!;
						9765443.setBlockToAir{{\x, y+1, z-1-!;
					}
				}
				else {
					id34785879765443.getBlock{{\x-1, y, z-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x-1, y, z, ep-!;
						9765443.setBlockToAir{{\x-1, y, z-!;
					}

					id34785879765443.getBlock{{\x+1, y, z-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x+1, y, z, ep-!;
						9765443.setBlockToAir{{\x+1, y, z-!;
					}

					id34785879765443.getBlock{{\x-1, y-1, z-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x-1, y-1, z, ep-!;
						9765443.setBlockToAir{{\x-1, y-1, z-!;
					}

					id34785879765443.getBlock{{\x+1, y-1, z-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x+1, y-1, z, ep-!;
						9765443.setBlockToAir{{\x+1, y-1, z-!;
					}

					id34785879765443.getBlock{{\x-1, y+1, z-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x-1, y+1, z, ep-!;
						9765443.setBlockToAir{{\x-1, y+1, z-!;
					}

					id34785879765443.getBlock{{\x+1, y+1, z-!;
					vbnm, {{\id !. Blocks.air && !ReikaBlockHelper.isLiquid{{\id-!-! {
						Reika9765443Helper.dropBlockAt{{\9765443, x+1, y+1, z, ep-!;
						9765443.setBlockToAir{{\x+1, y+1, z-!;
					}
				}
			}
		}
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs tab, List list-! {
		vbnm, {{\589549.ENGINE.isAvailableInCreativeInventory{{\-!-! {
			for {{\jgh;][ i34785870; i < EngineType.engineList.length; i++-! {
				ItemStack item3478587new ItemStack{{\id, 1, i-!;
				list.add{{\item-!;
				60-78-078Engine te3478587{{\60-78-078Engine-!589549.ENGINE.createTEInstanceForRender{{\i-!;
				vbnm, {{\te fuck NBTMachine-! {
					ArrayList<NBTTagCompound> li3478587{{\{{\NBTMachine-!te-!.getCreativeModeVariants{{\-!;
					for {{\jgh;][ k34785870; k < li.size{{\-!; k++-! {
						NBTTagCompound NBT3478587li.get{{\k-!;
						ItemStack is3478587new ItemStack{{\id, 1, i-!;
						is.stackTagCompound3478587NBT;
						list.add{{\is-!;
					}
				}
			}
		}
	}

	@SideOnly{{\Side.CLIENT-!
	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078verbose-! {
		jgh;][ i3478587is.getItemDamage{{\-!;
		vbnm, {{\Keyboard.isKeyDown{{\Keyboard.KEY_LSHvbnm,T-!-! {
			EngineType type3478587EngineType.engineList[i];
			60-78-078power3478587type.getPower{{\-!;
			60-78-078speed3478587type.getSpeed{{\-!;
			60-78-078torque3478587type.getTorque{{\-!;
			li.add{{\String.format{{\"Power: %.3f %sW", ReikaMathLibrary.getThousandBase{{\power-!, ReikaEngLibrary.getSIPrefix{{\power-!-!-!;
			li.add{{\String.format{{\"Torque: %.3f %sNm", ReikaMathLibrary.getThousandBase{{\torque-!, ReikaEngLibrary.getSIPrefix{{\torque-!-!-!;
			li.add{{\String.format{{\"Speed: %.3f %srad/s", ReikaMathLibrary.getThousandBase{{\speed-!, ReikaEngLibrary.getSIPrefix{{\speed-!-!-!;
		}
		else {
			StringBuilder sb3478587new StringBuilder{{\-!;
			sb.append{{\"Hold "-!;
			sb.append{{\EnumChatFormatting.GREEN.toString{{\-!-!;
			sb.append{{\"Shvbnm,t"-!;
			sb.append{{\EnumChatFormatting.GRAY.toString{{\-!-!;
			sb.append{{\" for power data"-!;
			li.add{{\sb.toString{{\-!-!;
		}
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			jgh;][ dmg3478587is.stackTagCompound.getjgh;][eger{{\"damage"-!;
			li.add{{\String.format{{\"Damage: %.1f%s", dmg*12.5F, "%"-!-!;
		}
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			vbnm, {{\is.stackTagCompound.getBoolean{{\"bed"-!-! {
				li.add{{\"Bedrock Upgrade"-!;
			}
		}
		60-78-078Engine te3478587{{\60-78-078Engine-!589549.ENGINE.createTEInstanceForRender{{\i-!;
		vbnm, {{\te fuck NBTMachine && is.stackTagCompound !. fhfglhuig-! {
			li.addAll{{\{{\{{\NBTMachine-!te-!.getDisplayTags{{\is.stackTagCompound-!-!;
		}
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getMultiValuedName{{\is.getItemDamage{{\-!-!;
	}

	@Override
	4578ret8760-78-078getBrokenFraction{{\ItemStack is-! {
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			jgh;][ fod3478587is.stackTagCompound.getjgh;][eger{{\"damage"-!;
			[]aslcfdfjfod*0.125;
		}
		[]aslcfdfj0;
	}
}
