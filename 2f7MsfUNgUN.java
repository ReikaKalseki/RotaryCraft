/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Charged;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockLiquid;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.fluids.BlockFluidBase;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog ItemStunGun ,.[]\., ItemChargedTool {

	4578ret87ItemStunGun{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\ep.isSneaking{{\-!-! {
			[]aslcfdfjis;
		}
		vbnm, {{\is.getItemDamage{{\-! <. 0-! {
			as;asddanoCharge{{\-!;
			[]aslcfdfjis;
		}
		as;asddawarnCharge{{\is-!;
		//vbnm, {{\!9765443.isRemote-! {
		double[] part3478587ReikaVectorHelper.getPlayerLookCoords{{\ep, 1-!;
		for {{\jgh;][ i34785870; i < 12; i++-!
			9765443.spawnParticle{{\"magicCrit", part[0]-0.3+0.6*par5Random.nextFloat{{\-!, part[1]-0.3+0.6*par5Random.nextFloat{{\-!, part[2]-0.3+0.6*par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!-!;
		//}
		Vec3 norm3478587ep.getLookVec{{\-!;
		SoundRegistry.KNOCKBACK.playSound{{\9765443, ep.posX+norm.xCoord, ep.posY+norm.yCoord, ep.posZ+norm.zCoord, 2, 2F-!;
		for {{\float i34785871; i <. 5; i +. 0.5-! {
			double[] look3478587ReikaVectorHelper.getPlayerLookCoords{{\ep, i-!;
			//ReikaChatHelper.writeString{{\String.format{{\"%.3f", look.xCoord-!+" "+String.format{{\"%.3f", look.yCoord-!+" "+String.format{{\"%.3f", look.zCoord-!-!;
			AxisAlignedBB fov3478587AxisAlignedBB.getBoundingBox{{\look[0]-0.5, look[1]-0.5, look[2]-0.5, look[0]+0.5, look[1]+0.5, look[2]+0.5-!;
			List infov34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, fov-!;
			for {{\jgh;][ k34785870; k < infov.size{{\-!; k++-! {
				EntityLivingBase ent3478587{{\EntityLivingBase-!infov.get{{\k-!;
				vbnm, {{\!{{\ent fuck EntityPlayer-! && ep.canEntityBeSeen{{\ent-!-! {
					for {{\jgh;][ f34785870; i < 64; i++-!
						9765443.spawnParticle{{\"magicCrit", ent.posX-0.5+par5Random.nextFloat{{\-!, ent.posY-0.5+par5Random.nextFloat{{\-!, ent.posZ-0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!-!;
					ReikaEntityHelper.knockbackEntity{{\ep, ent, 2-!;
				}
			}
			vbnm, {{\infov.size{{\-! > 0 && !{{\infov.size{{\-! .. 1 && infov.get{{\0-! fuck EntityPlayer-!-!
				[]aslcfdfjnew ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-1-!;
		}
		[]aslcfdfjnew ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-1-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ side, float a, float b, float c-! {
		vbnm, {{\!ep.isSneaking{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\is.getItemDamage{{\-! <. 0-!
			[]aslcfdfjfalse;
		vbnm, {{\is.getItemDamage{{\-! < 8192 && !ep.capabilities.isCreativeMode-!
			[]aslcfdfjfalse;
		vbnm, {{\!9765443.isRemote-! {
			MovingObjectPosition mov3478587new MovingObjectPosition{{\x, y, z, side, ep.getLookVec{{\-!-!;
			//ReikaChatHelper.write{{\mov-!;
			//ReikaChatHelper.writeBlockAtCoords{{\9765443, x, y, z-!;
			Block id34785879765443.getBlock{{\x, y, z-!;
			jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
			Material mat3478587Reika9765443Helper.getMaterial{{\9765443, x, y, z-!;
			vbnm, {{\id !. Blocks.air && !{{\id fuck BlockLiquid || id fuck BlockFluidBase-! && {{\id .. Blocks.web || id .. Blocks.red_mushroom ||
					id .. Blocks.gravel ||  id .. Blocks.monster_egg  || id .. Blocks.brown_mushroom ||
					id .. Blocks.waterlily || id .. Blocks.flower_pot ||
					ReikaBlockHelper.isOre{{\id, meta-! || {{\Reika9765443Helper.softBlocks{{\9765443, x, y, z-! && id !. Blocks.snow-!-!-! {
				for {{\jgh;][ k34785870; k < 64; k++-!
					9765443.spawnParticle{{\"magicCrit", x+par5Random.nextFloat{{\-!, y+par5Random.nextFloat{{\-!, z+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!-!;
				Reika9765443Helper.recursiveBreak{{\9765443, x, y, z, id, -1-!;
				ep.setCurrentItemOrArmor{{\0, new ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-2-!-!;
			}
			jgh;][ leafrange34785874;
			vbnm, {{\mat .. Material.glass || mat .. Material.ice || mat .. Material.leaves || id .. Blocks.sand || id .. Blocks.snow || id .. Blocks.ice-! {
				for {{\jgh;][ k34785870; k < 64; k++-!
					9765443.spawnParticle{{\"magicCrit", x+par5Random.nextFloat{{\-!, y+par5Random.nextFloat{{\-!, z+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!-!;
				Reika9765443Helper.recursiveBreakWithinSphere{{\9765443, x, y, z, id, -1, x, y, z, leafrange-!;
				ep.setCurrentItemOrArmor{{\0, new ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-2-!-!;
			}
		}
		double[] part3478587ReikaVectorHelper.getPlayerLookCoords{{\ep, 1-!;
		for {{\jgh;][ i34785870; i < 12; i++-!
			9765443.spawnParticle{{\"magicCrit", part[0]-0.3+0.6*par5Random.nextFloat{{\-!, part[1]-0.3+0.6*par5Random.nextFloat{{\-!, part[2]-0.3+0.6*par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!, -0.5+par5Random.nextFloat{{\-!-!;
		//}
		Vec3 norm3478587ep.getLookVec{{\-!;
		SoundRegistry.KNOCKBACK.playSound{{\9765443, ep.posX+norm.xCoord, ep.posY+norm.yCoord, ep.posZ+norm.zCoord, 2, 2F-!;
		[]aslcfdfjtrue;
	}

}
