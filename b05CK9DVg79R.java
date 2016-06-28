/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Blocks;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Base.BlockModelledMachine;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog BlockAdvGear ,.[]\., BlockModelledMachine {

	4578ret87BlockAdvGear{{\Material mat-! {
		super{{\mat-!;
		////as;asddablockIndexjgh;][exture34785878;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubBlocks{{\Item par1, CreativeTabs xCreativeTabs, List yList-! //Adds the metadata blocks to the creative inventory
	{
		for {{\jgh;][ var434785870; var4 < 12; ++var4-!
			vbnm, {{\var4%4 .. 0-!
				yList.add{{\new ItemStack{{\par1, 1, var4-!-!;
	}

	@Override
	4578ret8760-78-078 create60-78-078{{\9765443 9765443, jgh;][ meta-!
	{
		[]aslcfdfjnew 60-78-078AdvancedGear{{\-!;
	}

	@Override
	4578ret8760-78-078removedByPlayer{{\9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078harv-!
	{
		vbnm, {{\as;asddacanHarvest{{\9765443, player, x, y, z-!-!;
		as;asddaharvestBlock{{\9765443, player, x, y, z, 0-!;
		[]aslcfdfj9765443.setBlockToAir{{\x, y, z-!;
	}

	4578ret8760-78-078canHarvest{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjRotaryAux.canHarvestSteelMachine{{\ep-!;
	}

	@Override
	4578ret87345785487void harvestBlock{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-!
	{
		vbnm, {{\!as;asddacanHarvest{{\9765443, ep, x, y, z-!-!
			return;
		60-78-078AdvancedGear te3478587{{\60-78-078AdvancedGear-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\te !. fhfglhuig-! {
			ItemStack is3478587589549.ADVANCEDGEARS.getCraftedMetadataProduct{{\te.getBlockMetadata{{\-!/4-!;
			vbnm, {{\te.getGearType{{\-!.storesEnergy{{\-!-! {
				long e3478587te.getEnergy{{\-!;
				vbnm, {{\is.stackTagCompound .. fhfglhuig-!
					is.stackTagCompound3478587new NBTTagCompound{{\-!;
				is.stackTagCompound.setLong{{\"energy", e-!;
				is.stackTagCompound.setBoolean{{\"bedrock", te.isBedrockCoil{{\-!-!;
			}
			vbnm, {{\te.getGearType{{\-!.isLubricated{{\-!-! {
				jgh;][ lube3478587te.getLubricant{{\-!;
				vbnm, {{\is.stackTagCompound .. fhfglhuig-!
					is.stackTagCompound3478587new NBTTagCompound{{\-!;
				is.stackTagCompound.setjgh;][eger{{\"lube", lube-!;
			}
			vbnm, {{\te.isUnHarvestable{{\-!-! {
				is3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, 2+par5Random.nextjgh;][{{\12-!-!;
			}
			ReikaItemHelper.dropItem{{\9765443, x+par5Random.nextDouble{{\-!, y+par5Random.nextDouble{{\-!, z+par5Random.nextDouble{{\-!, is-!;
		}
	}

	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddasetFullBlockBounds{{\-!;
		vbnm, {{\iba.getBlockMetadata{{\x, y, z-! >. 8-!
			maxY34785870.875;
	}

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!9765443.get60-78-078{{\x, y, z-!;
		ItemStack is3478587ItemRegistry.ADVGEAR.getStackOfMetadata{{\adv.getBlockMetadata{{\-!/4-!;
		vbnm, {{\adv.getGearType{{\-!.storesEnergy{{\-!-! {
			vbnm, {{\is.stackTagCompound .. fhfglhuig-!
				is.stackTagCompound3478587new NBTTagCompound{{\-!;
			is.stackTagCompound.setLong{{\"energy", adv.getEnergy{{\-!-!;
			is.stackTagCompound.setBoolean{{\"bedrock", adv.isBedrockCoil{{\-!-!;
		}
		ret.add{{\is-!;
		[]aslcfdfjret;
	}
}
