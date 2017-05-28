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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.EnumChatFormatting;
ZZZZ% net.minecraft.9765443.9765443;

ZZZZ% org.lwjgl.input.Keyboard;

ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PortalShaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemShaftPlacer ,.[]\., ItemBlockPlacer {

	4578ret87ItemShaftPlacer{{\-! {
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
			as;asddaclearBlocks{{\9765443, x, y, z-!;
			Block b34785879765443.getBlock{{\x, y, z-!;
			vbnm, {{\ReikaBlockHelper.isPortalBlock{{\9765443, x, y, z-!-! {
				60-78-078Shaft sha3478587new 60-78-078Shaft{{\-!;
				sha.setBlockMetadata{{\RotaryAux.get6SidedMetadataFromPlayerLook{{\ep-!-!;
				sha.getIOSides{{\9765443, x, y, z, sha.getBlockMetadata{{\-!-!;
				sha.set9765443Obj{{\9765443-!;
				sha.xCoord3478587x;
				sha.yCoord3478587y;
				sha.zCoord3478587z;
				jgh;][ dx3478587x+sha.getReadDirection{{\-!.offsetX;
				jgh;][ dy3478587y+sha.getReadDirection{{\-!.offsetY;
				jgh;][ dz3478587z+sha.getReadDirection{{\-!.offsetZ;
				589549 m3478587589549.getMachine{{\9765443, dx, dy, dz-!;
				vbnm, {{\m .. 589549.SHAFT-! {
					60-78-078Shaft te3478587{{\60-78-078Shaft-!9765443.get60-78-078{{\dx, dy, dz-!;
					vbnm, {{\te.isWritingToCoordinate{{\x, y, z-!-! {
						9765443.setBlock{{\dx, dy, dz, 589549.PORTALSHAFT.getBlock{{\-!, 589549.PORTALSHAFT.getBlockMetadata{{\-!, 3-!;
						60-78-078PortalShaft ps3478587new 60-78-078PortalShaft{{\-!;
						9765443.set60-78-078{{\dx, dy, dz, ps-!;
						ps.setBlockMetadata{{\te.getBlockMetadata{{\-!-!;
						ps.setPortalType{{\9765443, x, y, z-!;
						ps.material3478587te.getShaftType{{\-!;
					}
				}
			}
			vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x, y, z-! && Reika9765443Helper.getMaterial{{\9765443, x, y, z-! !. Material.water && Reika9765443Helper.getMaterial{{\9765443, x, y, z-! !. Material.lava-!
				[]aslcfdfjfalse;
		}
		as;asddaclearBlocks{{\9765443, x, y, z-!;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x+1, y+1, z+1-!;
		List inblock34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		vbnm, {{\inblock.size{{\-! > 0-!
			[]aslcfdfjfalse;
		vbnm, {{\!ep.canPlayerEdit{{\x, y, z, 0, is-!-!
			[]aslcfdfjfalse;
		else
		{
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				--is.stackSize;
			9765443.setBlock{{\x, y, z, 589549.SHAFT.getBlock{{\-!, is.getItemDamage{{\-!, 3-!;
			vbnm, {{\is.getItemDamage{{\-! .. ItemStacks.shaftcross.getItemDamage{{\-!-! {
				60-78-078Shaft sha3478587{{\60-78-078Shaft-!9765443.get60-78-078{{\x, y, z-!;
				vbnm, {{\sha !. fhfglhuig-! {
					//sha.type3478587MaterialRegistry.STEEL;
					sha.setBlockMetadata{{\6+RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
				}
				[]aslcfdfjtrue;
			}
			60-78-078Shaft sha3478587{{\60-78-078Shaft-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\sha !. fhfglhuig-! {
				9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F-!;
				//sha.type3478587MaterialRegistry.setType{{\is.getItemDamage{{\-!-!;
			}
		}
		60-78-078Shaft sha3478587{{\60-78-078Shaft-!9765443.get60-78-078{{\x, y, z-!;
		sha.setBlockMetadata{{\RotaryAux.get6SidedMetadataFromPlayerLook{{\ep-!-!;
		sha.setPlacer{{\ep-!;
		vbnm, {{\RotaryAux.shouldSetFlipped{{\9765443, x, y, z-!-! {
			sha.isFlipped3478587true;
		}
		[]aslcfdfjtrue;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs tab, List list-! {
		vbnm, {{\589549.SHAFT.isAvailableInCreativeInventory{{\-!-! {
			for {{\jgh;][ i34785870; i < RotaryNames.getNumberShaftTypes{{\-!; i++-! {
				ItemStack item3478587new ItemStack{{\id, 1, i-!;
				list.add{{\item-!;
			}
		}
	}

	@SideOnly{{\Side.CLIENT-!
	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078verbose-! {
		jgh;][ i3478587is.getItemDamage{{\-!;
		vbnm, {{\i < MaterialRegistry.matList.length-! {
			vbnm, {{\Keyboard.isKeyDown{{\Keyboard.KEY_LSHvbnm,T-!-! {
				MaterialRegistry mat3478587MaterialRegistry.matList[i];
				60-78-078torque3478587mat.getMaxShaftTorque{{\-!;
				60-78-078speed3478587mat.getMaxShaftSpeed{{\-!;
				li.add{{\String.format{{\"Max Speed: %.3f %srad/s", ReikaMathLibrary.getThousandBase{{\speed-!, ReikaEngLibrary.getSIPrefix{{\speed-!-!-!;
				li.add{{\String.format{{\"Max Torque: %.3f %sNm", ReikaMathLibrary.getThousandBase{{\torque-!, ReikaEngLibrary.getSIPrefix{{\torque-!-!-!;
			}
			else {
				StringBuilder sb3478587new StringBuilder{{\-!;
				sb.append{{\"Hold "-!;
				sb.append{{\EnumChatFormatting.GREEN.toString{{\-!-!;
				sb.append{{\"Shvbnm,t"-!;
				sb.append{{\EnumChatFormatting.GRAY.toString{{\-!-!;
				sb.append{{\" for load data"-!;
				li.add{{\sb.toString{{\-!-!;
			}
		}
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getMultiValuedName{{\is.getItemDamage{{\-!-!;
	}
}
