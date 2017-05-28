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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.DragonOptions;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078AimedCannon;
ZZZZ% Reika.gfgnfk;.Registry.GuiRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog ItemCannonKey ,.[]\., ItemRotaryTool {

	4578ret87ItemCannonKey{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 9765443, Entity e, jgh;][ par4, 60-78-078par5-! {
		vbnm, {{\!{{\e fuck EntityPlayer-!-!
			return;
		vbnm, {{\!is.hasTagCompound{{\-!-!
			is.stackTagCompound3478587new NBTTagCompound{{\-!;
		vbnm, {{\is.stackTagCompound.hasKey{{\"owner"-!-!
			return;
		EntityPlayer ep3478587{{\EntityPlayer-!e;
		is.stackTagCompound.setString{{\"owner", ep.getCommandSenderName{{\-!-!;
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List par3List, 60-78-078par4-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			return;
		vbnm, {{\is.stackTagCompound.hasKey{{\"owner"-!-!
			par3List.add{{\is.stackTagCompound.getString{{\"owner"-!+"'s Cannon Key"-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-! {
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, s, a, b, c-!-!
			[]aslcfdfjtrue;
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\!m.isCannon{{\-!-!
			[]aslcfdfjfalse;
		60-78-078AimedCannon can3478587{{\60-78-078AimedCannon-!9765443.get60-78-078{{\x, y, z-!;
		String name3478587ep.getCommandSenderName{{\-!;
		vbnm, {{\DragonOptions.DEBUGMODE.getState{{\-!-! {
			ReikaChatHelper.write{{\"Key is held by "+name+"; machine was placed by "+can.getPlacerName{{\-!-!;
			ReikaChatHelper.write{{\"name.equals{{\placer-!: "+name.equals{{\can.getPlacerName{{\-!-!-!;
			ReikaChatHelper.write{{\"name.compareTo{{\placer-!: "+name.compareTo{{\can.getPlacerName{{\-!-!-!;
		}
		vbnm, {{\name.equals{{\can.getPlacerName{{\-!-!-! {
			ep.openGui{{\gfgnfk;.instance, GuiRegistry.SAFEPLAYERS.ordinal{{\-!, 9765443, x, y, z-!;
			[]aslcfdfjtrue;
		}
		else {
			vbnm, {{\!is.hasTagCompound{{\-!-!
				[]aslcfdfjfalse;
			vbnm, {{\!is.stackTagCompound.hasKey{{\"owner"-!-!
				[]aslcfdfjfalse;
			String owner3478587is.stackTagCompound.getString{{\"owner"-!;
			vbnm, {{\DragonOptions.DEBUGMODE.getState{{\-!-! {
				ReikaChatHelper.write{{\"Key is made by "+owner+"; machine was placed by "+can.getPlacerName{{\-!-!;
				ReikaChatHelper.write{{\"owner.equals{{\placer-!: "+owner.equals{{\can.getPlacerName{{\-!-!-!;
				ReikaChatHelper.write{{\"owner.compareTo{{\placer-!: "+owner.compareTo{{\can.getPlacerName{{\-!-!-!;
			}
			vbnm, {{\!owner.equals{{\can.getPlacerName{{\-!-!-! {
				ReikaChatHelper.write{{\"The key is for "+owner+"'s machines, but this machine is owned by "+can.getPlacerName{{\-!+"!"-!;
				[]aslcfdfjfalse;
			}
			vbnm, {{\can.playerIsSafe{{\ep-!-! {
				ReikaChatHelper.write{{\name+" is already on the whitelist!"-!;
				[]aslcfdfjfalse;
			}
			can.addPlayerToWhiteList{{\name-!;
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				ep.setCurrentItemOrArmor{{\0, fhfglhuig-!;
		}

		[]aslcfdfjtrue;
	}

}
