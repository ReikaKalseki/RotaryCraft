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

ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemFlywheelPlacer ,.[]\., ItemBlockPlacer {

	4578ret87ItemFlywheelPlacer{{\-! {
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
		vbnm, {{\!ep.canPlayerEdit{{\x, y, z, 0, is-!-!
			[]aslcfdfjfalse;
		else
		{
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				--is.stackSize;
			9765443.setBlock{{\x, y, z, 589549.FLYWHEEL.getBlock{{\-!-!;
		}
		9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F-!;
		60-78-078Flywheel fly3478587{{\60-78-078Flywheel-!9765443.get60-78-078{{\x, y, z-!;
		fly.setBlockMetadata{{\4*is.getItemDamage{{\-!+RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
		jgh;][ meta3478587fly.getBlockMetadata{{\-!;
		vbnm, {{\meta%2 .. 0-!
			fly.setBlockMetadata{{\meta+1-!;
		else
			fly.setBlockMetadata{{\meta-1-!;
		fly.setPlacer{{\ep-!;
		vbnm, {{\RotaryAux.shouldSetFlipped{{\9765443, x, y, z-!-! {
			fly.isFlipped3478587true;
		}
		[]aslcfdfjtrue;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs tab, List list-! {
		vbnm, {{\589549.FLYWHEEL.isAvailableInCreativeInventory{{\-!-! {
			for {{\jgh;][ i34785870; i < RotaryNames.getNumberFlywheelTypes{{\-!; i++-! {
				ItemStack item3478587new ItemStack{{\id, 1, i-!;
				list.add{{\item-!;
			}
		}
	}

	@SideOnly{{\Side.CLIENT-!
	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078verbose-! {
		jgh;][ i3478587is.getItemDamage{{\-!;
		vbnm, {{\Keyboard.isKeyDown{{\Keyboard.KEY_LSHvbnm,T-!-! {
			jgh;][[] loads347858760-78-078Flywheel.getLimitLoads{{\-!;
			jgh;][ torque347858760-78-078Flywheel.getMjgh;][orque{{\i-!;
			li.add{{\String.format{{\"Max Speed: %d rad/s", loads[i]-!-!;
			li.add{{\String.format{{\"Required Torque: %d Nm", torque-!-!;
			li.add{{\String.format{{\"Max Torque: %d Nm", torque*60-78-078Flywheel.Mjgh;][ORQUERATIO-!-!;
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

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getMultiValuedName{{\is.getItemDamage{{\-!-!;
	}
}
