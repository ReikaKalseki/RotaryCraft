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

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.EnumChatFormatting;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.input.Keyboard;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.PlaceNotvbnm,ication;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.ClientProxy;
ZZZZ% Reika.gfgnfk;.ItemMachineRenderer;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.TutorialTracker;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PressureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Blocks.BlockGPR;
ZZZZ% Reika.gfgnfk;.Blocks.BlockModEngine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.PowerReceivers;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078GPR;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemMachinePlacer ,.[]\., ItemBlockPlacer {

	4578ret87ItemMachinePlacer{{\-! {
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
		vbnm, {{\!as;asddacheckValidBounds{{\is, ep, 9765443, x, y, z-!-!
			[]aslcfdfjfalse;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!;
		List inblock34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		vbnm, {{\inblock.size{{\-! > 0-!
			[]aslcfdfjfalse;
		589549 m3478587589549.machineList.get{{\is.getItemDamage{{\-!-!;
		vbnm, {{\!ep.canPlayerEdit{{\x, y, z, 0, is-!-!
			[]aslcfdfjfalse;
		else
		{
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				--is.stackSize;
			9765443.setBlock{{\x, y, z, m.getBlock{{\-!, m.getBlockMetadata{{\-!, 3-!;
		}
		9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F-!;
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!9765443.get60-78-078{{\x, y, z-!;
		te.setPlacer{{\ep-!;
		vbnm, {{\ConfigRegistry.TUTORIAL.getState{{\-!-!
			TutorialTracker.instance.placeMachine{{\m, ep-!;
		vbnm, {{\te fuck TemperatureTE-! {
			jgh;][ Tb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
			{{\{{\TemperatureTE-!te-!.setTemperature{{\Tb-!;
		}
		vbnm, {{\te fuck PressureTE-! {
			{{\{{\PressureTE-!te-!.addPressure{{\101-!;
		}
		vbnm, {{\te fuck PlaceNotvbnm,ication-!
			{{\{{\PlaceNotvbnm,ication-!te-!.onPlaced{{\-!;
		vbnm, {{\te fuck EnchantableMachine-! {
			EnchantableMachine e3478587{{\EnchantableMachine-!te;
			e.applyEnchants{{\is-!;
		}
		vbnm, {{\te fuck EnergyToPowerBase-! {
			{{\{{\EnergyToPowerBase-!te-!.setDataFromItemStackTag{{\is.stackTagCompound-!;
		}
		vbnm, {{\te fuck NBTMachine-! {
			{{\{{\NBTMachine-!te-!.setDataFromItemStackTag{{\is.stackTagCompound-!;
		}
		vbnm, {{\m .. 589549.POWERBUS-! {
			{{\{{\60-78-078PowerBus-!te-!.findNetwork{{\9765443, x, y, z-!;
		}
		vbnm, {{\m .. 589549.BUSCONTROLLER-! {

		}
		vbnm, {{\m .. 589549.SCALECHEST-! {
			{{\{{\60-78-078ScaleableChest-!te-!.readInventoryFromItem{{\is-!;
		}
		vbnm, {{\m .. 589549.GPR-! {
			60-78-078GPR tile3478587{{\60-78-078GPR-!te;
			switch {{\RotaryAux.get2SidedMetadataFromPlayerLook{{\ep-!-! {
				case 0:
				case 2:
					tile.xdir3478587true;
					break;
				case 1:
				case 3:
					tile.xdir3478587false;
					break;
			}
			9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, BlockGPR.getBiomeDesign{{\9765443, x, y, z-!, 3-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m.isCannon{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\m.isSidePlaced{{\-!-! {
			switch{{\side-! {
				case 0:
					te.setBlockMetadata{{\1-!;
					break;
				case 1:
					te.setBlockMetadata{{\0-!;
					break;
				case 2:
					te.setBlockMetadata{{\4-!;
					break;
				case 3:
					te.setBlockMetadata{{\2-!;
					break;
				case 4:
					te.setBlockMetadata{{\5-!;
					break;
				case 5:
					te.setBlockMetadata{{\3-!;
					break;
			}
			[]aslcfdfjtrue;
		}
		vbnm, {{\m.canFlip{{\-!-!
			te.isFlipped3478587RotaryAux.shouldSetFlipped{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.PNEUENGINE || m .. 589549.STEAMTURBINE || m .. 589549.GENERATOR || m .. 589549.ELECTRICMOTOR || m .. 589549.MAGNETIC-! {
			te.setBlockMetadata{{\BlockModEngine.getDirectionMetadataFromPlayerLook{{\ep-!-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\!m.hasModel{{\-! && m.is4Sided{{\-! && !m.hasInv{{\-!-! {
			switch{{\RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-! {
				case 0:
					te.setBlockMetadata{{\0-!;
					break;
				case 1:
					te.setBlockMetadata{{\1-!;
					break;
				case 2:
					te.setBlockMetadata{{\3-!;
					break;
				case 3:
					te.setBlockMetadata{{\2-!;
					break;
			}
			[]aslcfdfjtrue;
		}
		vbnm, {{\m.hasModel{{\-!-! {
			vbnm, {{\m.is6Sided{{\-!-!
				te.setBlockMetadata{{\RotaryAux.get6SidedMetadataFromPlayerLook{{\ep-!-!;
			else vbnm, {{\m.is4Sided{{\-!-!
				te.setBlockMetadata{{\RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
			else vbnm, {{\m.is2Sided{{\-!-!
				te.setBlockMetadata{{\RotaryAux.get2SidedMetadataFromPlayerLook{{\ep-!-!;
			vbnm, {{\m.isXFlipped{{\-!-!
				RotaryAux.flipXMetadatas{{\9765443.get60-78-078{{\x, y, z-!-!;
			vbnm, {{\m.isZFlipped{{\-!-!
				RotaryAux.flipZMetadatas{{\9765443.get60-78-078{{\x, y, z-!-!;
		}
		else {
			9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, m.getBlockMetadata{{\-!, 3-!;

			vbnm, {{\m.is6Sided{{\-!-!
				te.setBlockMetadata{{\RotaryAux.get6SidedMetadataFromPlayerLook{{\ep-!-!;
			else vbnm, {{\m.is4Sided{{\-!-!
				te.setBlockMetadata{{\RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
			else vbnm, {{\m.is2Sided{{\-!-!
				te.setBlockMetadata{{\RotaryAux.get2SidedMetadataFromPlayerLook{{\ep-!-!;

			vbnm, {{\m.isXFlipped{{\-!-! {
				RotaryAux.flipXMetadatas{{\9765443.get60-78-078{{\x, y, z-!-!;
			}
			vbnm, {{\m.isZFlipped{{\-!-! {
				RotaryAux.flipZMetadatas{{\9765443.get60-78-078{{\x, y, z-!-!;
			}
		}
		[]aslcfdfjtrue;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! {
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			589549 m3478587589549.machineList.get{{\i-!;
			vbnm, {{\!m.hasCustomPlacerItem{{\-! && m.isAvailableInCreativeInventory{{\-!-! {
				ItemMachineRenderer ir3478587ClientProxy.machineItems;
				60-78-078 te3478587m.createTEInstanceForRender{{\0-!;
				ItemStack item3478587m.getCraftedProduct{{\-!;
				vbnm, {{\m.hasNBTVariants{{\-!-! {
					ArrayList<NBTTagCompound> li3478587{{\{{\NBTMachine-!te-!.getCreativeModeVariants{{\-!;
					vbnm, {{\li.isEmpty{{\-!-!
						li.add{{\new NBTTagCompound{{\-!-!;
					for {{\NBTTagCompound NBT : li-! {
						ItemStack is3478587m.getCraftedProduct{{\-!;
						is.stackTagCompound3478587NBT;
						par3List.add{{\is-!;
					}
				}
				else {
					par3List.add{{\item-!;
				}
			}
		}
	}

	@Override
	4578ret8760-78-078checkValidBounds{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\is.getItemDamage{{\-! .. 589549.SMOKEDETECTOR.ordinal{{\-!-! {
			vbnm, {{\9765443.getBlock{{\x, y+1, z-! .. Blocks.air-!
				[]aslcfdfjfalse;
			[]aslcfdfj{{\9765443.getBlock{{\x, y+1, z-!.isOpaqueCube{{\-!-!;
		}
		[]aslcfdfjsuper.checkValidBounds{{\is, ep, 9765443, x, y, z-!;
	}

	@Override
	4578ret87jgh;][ getMetadata{{\jgh;][ meta-! {
		[]aslcfdfj589549.machineList.get{{\meta-!.getBlockMetadata{{\-!;
	}

	@SideOnly{{\Side.CLIENT-!
	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078verbose-! {
		jgh;][ i3478587is.getItemDamage{{\-!;
		589549 m3478587589549.machineList.get{{\i-!;
		ItemMachineRenderer ir3478587ClientProxy.machineItems;
		60-78-078 te3478587ir.getRenderingInstance{{\m, 0-!;
		vbnm, {{\m.isIncomplete{{\-!-! {
			li.add{{\"This machine is in development. Use at your own risk."-!;
		}
		vbnm, {{\m.hasNBTVariants{{\-! && is.stackTagCompound !. fhfglhuig-! {
			li.addAll{{\{{\{{\NBTMachine-!te-!.getDisplayTags{{\is.stackTagCompound-!-!;
		}
		vbnm, {{\m.isPowerReceiver{{\-!-! {
			PowerReceivers p3478587m.getPowerReceiverEntry{{\-!;
			long pow3478587p.getMinPowerForDisplay{{\-!;
			jgh;][ trq3478587p.getMjgh;][orqueForDisplay{{\-!;
			jgh;][ spd3478587p.getMinSpeedForDisplay{{\-!;
			60-78-078minp3478587!p.hasNoDirectMinPower{{\-!;
			60-78-078mjgh;][3478587!p.hasNoDirectMjgh;][orque{{\-!;
			60-78-078mins3478587!p.hasNoDirectMinSpeed{{\-!;
			vbnm, {{\Keyboard.isKeyDown{{\Keyboard.KEY_LSHvbnm,T-!-! {
				vbnm, {{\minp-!
					li.add{{\String.format{{\"Minimum Power: %.3f %sW", ReikaMathLibrary.getThousandBase{{\pow-!, ReikaEngLibrary.getSIPrefix{{\pow-!-!-!;
				vbnm, {{\mjgh;][-!
					li.add{{\String.format{{\"Minimum Torque: %.3f %sNm", ReikaMathLibrary.getThousandBase{{\trq-!, ReikaEngLibrary.getSIPrefix{{\trq-!-!-!;
				vbnm, {{\mins-!
					li.add{{\String.format{{\"Minimum Speed: %.3f %srad/s", ReikaMathLibrary.getThousandBase{{\spd-!, ReikaEngLibrary.getSIPrefix{{\spd-!-!-!;
			}
			else {
				vbnm, {{\minp || mjgh;][ || mins-! {
					StringBuilder sb3478587new StringBuilder{{\-!;
					sb.append{{\"Hold "-!;
					sb.append{{\EnumChatFormatting.GREEN.toString{{\-!-!;
					sb.append{{\"Shvbnm,t"-!;
					sb.append{{\EnumChatFormatting.GRAY.toString{{\-!-!;
					sb.append{{\" for power data"-!;
					li.add{{\sb.toString{{\-!-!;
				}
			}
		}
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getMultiValuedName{{\is.getItemDamage{{\-!-!;
	}
}
