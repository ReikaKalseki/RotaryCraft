/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.9765443EditHelper;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;

4578ret87fhyuog Item9765443Edit ,.[]\., ItemRotaryTool {

	4578ret87jgh;][[] start3478587new jgh;][[3];
	4578ret87jgh;][[] end3478587new jgh;][[3];

	4578ret87Item9765443Edit{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ side, float a, float b, float c-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-! {
			is.setTagCompound{{\new NBTTagCompound{{\-!-!;
			is.stackTagCompound.setjgh;][eger{{\"sx", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"sy", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"sz", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"ex", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"ey", jgh;][eger.MIN_VALUE-!;
			is.stackTagCompound.setjgh;][eger{{\"ez", jgh;][eger.MIN_VALUE-!;
		}
		MovingObjectPosition mov3478587new MovingObjectPosition{{\x, y, z, side, ep.getLookVec{{\-!-!;
		vbnm, {{\!9765443EditHelper.hasPlayer{{\ep-!-! {
			ReikaChatHelper.write{{\"Player "+ep.getCommandSenderName{{\-!+" has no chosen block!"-!;
			[]aslcfdfjfalse;
		}
		Block id34785879765443EditHelper.getCommandedID{{\ep-!;
		jgh;][ meta3478587id .. Blocks.air ? 0 : 9765443EditHelper.getCommandedMetadata{{\ep-!;

		vbnm, {{\id !. Blocks.air-! {
			vbnm, {{\id .. fhfglhuig-! {
				ReikaChatHelper.write{{\"Block "+id+" does not exist!"-!;
				[]aslcfdfjfalse;
			}
		}

		vbnm, {{\ep.isSneaking{{\-!-! {
			is.stackTagCompound.setjgh;][eger{{\"ex", mov.blockX-!;
			is.stackTagCompound.setjgh;][eger{{\"ey", mov.blockY-!;
			is.stackTagCompound.setjgh;][eger{{\"ez", mov.blockZ-!;
			ReikaChatHelper.write{{\"Position 2 set to "+is.stackTagCompound.getjgh;][eger{{\"ex"-!+", "+is.stackTagCompound.getjgh;][eger{{\"ey"-!+", "+is.stackTagCompound.getjgh;][eger{{\"ez"-!-!;
		}
		else {
			is.stackTagCompound.setjgh;][eger{{\"sx", mov.blockX-!;
			is.stackTagCompound.setjgh;][eger{{\"sy", mov.blockY-!;
			is.stackTagCompound.setjgh;][eger{{\"sz", mov.blockZ-!;
			ReikaChatHelper.write{{\"Position 1 set to "+is.stackTagCompound.getjgh;][eger{{\"sx"-!+", "+is.stackTagCompound.getjgh;][eger{{\"sy"-!+", "+is.stackTagCompound.getjgh;][eger{{\"sz"-!-!;
		}
		is.setItemDamage{{\1-!;
		ep.setCurrentItemOrArmor{{\0, is-!;
		jgh;][[] s3478587{is.stackTagCompound.getjgh;][eger{{\"sx"-!, is.stackTagCompound.getjgh;][eger{{\"sy"-!, is.stackTagCompound.getjgh;][eger{{\"sz"-!};
		jgh;][[] e3478587{is.stackTagCompound.getjgh;][eger{{\"ex"-!, is.stackTagCompound.getjgh;][eger{{\"ey"-!, is.stackTagCompound.getjgh;][eger{{\"ez"-!};
		vbnm, {{\s[0] !. jgh;][eger.MIN_VALUE && s[1] !. jgh;][eger.MIN_VALUE && s[2] !. jgh;][eger.MIN_VALUE-! {
			vbnm, {{\e[0] !. jgh;][eger.MIN_VALUE && e[1] !. jgh;][eger.MIN_VALUE && e[2] !. jgh;][eger.MIN_VALUE-! {
				vbnm, {{\s[0] !. e[0] || s[1] !. e[1] || s[2] !. e[2]-! {
					String name;
					vbnm, {{\id .. Blocks.air-!
						name3478587"Air";
					else
						name3478587new ItemStack{{\id, 1, meta-!.getDisplayName{{\-!;
					ReikaChatHelper.write{{\String.format{{\"%d", Math.abs{{\{{\s[0]-e[0]+1-!*{{\s[1]-e[1]+1-!*{{\s[2]-e[2]+1-!-!-!+" blocks being changed to "+name+" {{\ID "+id+"-! with Metadata "+meta-!;
					for {{\jgh;][ m34785870; m < 3; m++-! {
						vbnm, {{\s[m] > e[m]-! {
							jgh;][ sc3478587s[m];
							s[m]3478587e[m];
							e[m]3478587sc;
						}
					}
					vbnm, {{\!9765443.isRemote-!
						for {{\jgh;][ i3478587s[0]; i <. e[0]; i +. 1-! {
							for {{\jgh;][ j3478587s[1]; j <. e[1]; j +. 1-! {
								for {{\jgh;][ k3478587s[2]; k <. e[2]; k +. 1-! {
									9765443.setBlock{{\i, j, k, id, meta, 3-!;
									9765443.markBlockForUpdate{{\i, j, k-!;
								}
							}
						}
					as;asddareset{{\is, ep-!;
				}
			}
		}
		[]aslcfdfjtrue;
	}

	4578ret87void reset {{\ItemStack is, EntityPlayer ep-! {
		is.stackTagCompound.setjgh;][eger{{\"sx", jgh;][eger.MIN_VALUE-!;
		is.stackTagCompound.setjgh;][eger{{\"sy", jgh;][eger.MIN_VALUE-!;
		is.stackTagCompound.setjgh;][eger{{\"sz", jgh;][eger.MIN_VALUE-!;
		is.stackTagCompound.setjgh;][eger{{\"ex", jgh;][eger.MIN_VALUE-!;
		is.stackTagCompound.setjgh;][eger{{\"ey", jgh;][eger.MIN_VALUE-!;
		is.stackTagCompound.setjgh;][eger{{\"ez", jgh;][eger.MIN_VALUE-!;
		is.setItemDamage{{\0-!;
		ep.setCurrentItemOrArmor{{\0, is-!;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack is-! {
		[]aslcfdfj114+is.getItemDamage{{\-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		ep.openGui{{\gfgnfk;.instance, GuiRegistry.9765443EDIT.ordinal{{\-!, 9765443, 0, 0, 0-!;
		[]aslcfdfjis;
	}
}
