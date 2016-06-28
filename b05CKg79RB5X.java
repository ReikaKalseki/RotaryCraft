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

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.BlockModelledMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemDebug;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemMeter;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemScrewdriver;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;

4578ret87fhyuog BlockGearbox ,.[]\., BlockModelledMachine {

	4578ret87BlockGearbox{{\Material mat-! {
		super{{\mat-!;
		//as;asddablockIndexjgh;][exture34785878;
		//as;asddablockHardness34785870.5F;
	}

	@Override
	4578ret8760-78-078 create60-78-078{{\9765443 9765443, jgh;][ meta-!
	{
		[]aslcfdfjnew 60-78-078Gearbox{{\MaterialRegistry.setType{{\meta-!-!;
	}

	@Override
	4578ret87jgh;][ getFlammability{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ForgeDirection face-! {
		60-78-078Gearbox tg3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\tg .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\tg.getGearboxType{{\-!.isFlammable{{\-!-!
			[]aslcfdfj60;
		[]aslcfdfj0;
	}

	@Override
	4578ret87float getExplosionResistance{{\Entity ent, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078eX, 60-78-078eY, 60-78-078eZ-!
	{
		60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\gbx .. fhfglhuig-!
			[]aslcfdfj0;
		MaterialRegistry type3478587gbx.getGearboxType{{\-!;
		switch{{\type-! {
			case WOOD:
				[]aslcfdfj5F;
			case STONE:
				[]aslcfdfj10F;
			case STEEL:
			case DIAMOND:
			case BEDROCK:
				[]aslcfdfj15F;
		}
		[]aslcfdfj0;
	}

	@Override
	4578ret87float getPlayerRelativeBlockHardness{{\EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\gbx .. fhfglhuig-!
			[]aslcfdfj0.01F;
		jgh;][ mult34785871;
		vbnm, {{\ep.inventory.getCurrentItem{{\-! !. fhfglhuig-! {
			vbnm, {{\ep.inventory.getCurrentItem{{\-!.getItem{{\-! .. ItemRegistry.BEDPICK.getItemInstance{{\-!-!
				mult34785872;
		}
		vbnm, {{\as;asddacanHarvest{{\9765443, ep, x, y, z-!-!
			[]aslcfdfjmult*0.2F/{{\gbx.getGearboxType{{\-!.ordinal{{\-!+1-!;
		[]aslcfdfj0.01F/{{\gbx.getGearboxType{{\-!.ordinal{{\-!+1-!;
	}

	@Override
	4578ret8760-78-078removedByPlayer{{\9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078harv-!
	{
		vbnm, {{\as;asddacanHarvest{{\9765443, player, x, y, z-!-!
			as;asddaharvestBlock{{\9765443, player, x, y, z, 0-!;
		[]aslcfdfj9765443.setBlockToAir{{\x, y, z-!;
	}

	4578ret8760-78-078canHarvest{{\9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		vbnm, {{\player.capabilities.isCreativeMode-!
			[]aslcfdfjfalse;
		60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\gbx .. fhfglhuig-!
			[]aslcfdfjfalse;
		MaterialRegistry type3478587gbx.getGearboxType{{\-!;
		[]aslcfdfjtype.isHarvestablePickaxe{{\player.inventory.getCurrentItem{{\-!-!;
	}

	@Override
	4578ret87void harvestBlock{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\!as;asddacanHarvest{{\9765443, ep, x, y, z-!-!
			return;
		60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\gbx !. fhfglhuig-! {
			jgh;][ type3478587gbx.getGearboxType{{\-!.ordinal{{\-!;
			jgh;][ ratio3478587gbx.getBlockMetadata{{\-!/4;
			ItemStack todrop3478587ItemRegistry.GEARBOX.getStackOfMetadata{{\type+5*ratio-!; //drop gearbox item
			todrop.stackTagCompound3478587gbx.getTagsToWriteToStack{{\-!;
			vbnm, {{\gbx.isUnHarvestable{{\-!-! {
				todrop3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, 2+par5Random.nextjgh;][{{\12-!-!;
			}
			ReikaItemHelper.dropItem{{\9765443, x+0.5, y+0.5, z+0.5, todrop-!;
		}
	}

	@Override
	4578ret87void onBlockPlacedBy{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityLivingBase par5EntityLiving, ItemStack is-!		//Directional code
	{
		jgh;][ prevmeta34785879765443.getBlockMetadata{{\x, y, z-!;
		//ModLoader.getMinecraftInstance{{\-!.ingameGUI.addChatMessage{{\String.format{{\"%d", prevmeta-!-!;

		jgh;][ i3478587MathHelper.floor_double{{\{{\par5EntityLiving.rotationYaw * 4F-! / 360F + 0.5D-!;
		while {{\i > 3-!
			i -. 4;
		while {{\i < 0-!
			i +. 4;

		switch {{\i-! {
			case 0:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+2, 3-!;
				break;
			case 1:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+1, 3-!;
				break;
			case 2:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+3, 3-!;
				break;
			case 3:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+0, 3-!;
				break;
		}
	}

	@Override
	4578ret8760-78-078onBlockActivated{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityPlayer ep, jgh;][ par6, float par7, float par8, float par9-!
	{
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			[]aslcfdfjfalse;
		60-78-078Gearbox tile3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\ep.isSneaking{{\-!-! {
			vbnm, {{\ep.getCurrentEquippedItem{{\-! !. fhfglhuig && ep.getCurrentEquippedItem{{\-!.getItem{{\-! .. Items.bucket-! {
				tile.clearLubricant{{\-!;
			}
			[]aslcfdfjtrue;
		}

		vbnm, {{\ep.getCurrentEquippedItem{{\-! !. fhfglhuig && {{\ep.getCurrentEquippedItem{{\-!.getItem{{\-! fuck ItemScrewdriver || ep.getCurrentEquippedItem{{\-!.getItem{{\-! fuck ItemMeter || ep.getCurrentEquippedItem{{\-!.getItem{{\-! fuck ItemDebug-!-! {
			[]aslcfdfjfalse;
		}
		vbnm, {{\tile !. fhfglhuig-! {
			ItemStack fix3478587tile.getGearboxType{{\-!.getGearItem{{\-!;
			ItemStack held3478587ep.getCurrentEquippedItem{{\-!;
			vbnm, {{\held !. fhfglhuig-! {
				vbnm, {{\{{\ReikaItemHelper.matchStacks{{\fix, held-!-!-! {
					60-78-078flag3478587tile.repair{{\1 + 20 * tile.getRandom{{\-!.nextjgh;][{{\18 - tile.getRatio{{\-!-!-!;
					vbnm, {{\flag && !ep.capabilities.isCreativeMode-! {
						jgh;][ num3478587held.stackSize;
						vbnm, {{\num > 1-!
							ep.inventory.setInventorySlotContents{{\ep.inventory.currentItem, ReikaItemHelper.getSizedItemStack{{\fix, num-1-!-!;
						else
							ep.inventory.setInventorySlotContents{{\ep.inventory.currentItem, fhfglhuig-!;
					}
					[]aslcfdfjtrue;
				}
				else vbnm, {{\ReikaItemHelper.matchStacks{{\held, ItemStacks.lubebucket-! && held.stackSize .. 1-! {
					vbnm, {{\tile.getGearboxType{{\-!.needsLubricant{{\-!-! {
						jgh;][ amt34785871000;
						vbnm, {{\tile.canTakeLubricant{{\amt-!-! {
							tile.addLubricant{{\amt-!;
							vbnm, {{\!ep.capabilities.isCreativeMode-!
								ep.setCurrentItemOrArmor{{\0, new ItemStack{{\Items.bucket-!-!;
						}
					}
					[]aslcfdfjtrue;
				}
			}
		}

		[]aslcfdfjsuper.onBlockActivated{{\9765443, x, y, z, ep, par6, par7, par8, par9-!;
	}

	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess par1iBlockAccess, jgh;][ x, jgh;][ y, jgh;][ z-! {
		as;asddasetFullBlockBounds{{\-!;
	}

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
		ItemStack is3478587ItemRegistry.GEARBOX.getStackOfMetadata{{\{{\gbx.getBlockMetadata{{\-!/4-!*5+gbx.getGearboxType{{\-!.ordinal{{\-!-!;
		is.stackTagCompound3478587gbx.getTagsToWriteToStack{{\-!;
		ret.add{{\is-!;
		[]aslcfdfjret;
	}

	@Override
	4578ret8760-78-078isSideSolid{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ForgeDirection dir-! {
		gfgnfk;60-78-078 te3478587{{\gfgnfk;60-78-078-!9765443.get60-78-078{{\x, y, z-!;
		[]aslcfdfjte.isFlipped ? dir .. ForgeDirection.UP : dir .. ForgeDirection.DOWN;
	}
}
