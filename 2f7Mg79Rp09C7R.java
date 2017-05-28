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
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemGearPlacer ,.[]\., ItemBlockPlacer {

	4578ret87ItemGearPlacer{{\-! {
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
			9765443.setBlock{{\x, y, z, 589549.GEARBOX.getBlock{{\-!, is.getItemDamage{{\-!%5, 3-!;
			60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!9765443.get60-78-078{{\x, y, z-!;
			vbnm, {{\gbx !. fhfglhuig-! {
				9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F-!;
				//gbx.type3478587MaterialRegistry.setType{{\is.getItemDamage{{\-!%5-!;
				gbx.setBlockMetadata{{\is.getItemDamage{{\-!/5*4+RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
				gbx.setPlacer{{\ep-!;
			}
		}
		60-78-078 tile34785879765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\tile fuck 60-78-078Gearbox-! {
			vbnm, {{\is.stackTagCompound .. fhfglhuig-!
				is.setTagCompound{{\new NBTTagCompound{{\-!-!;
			{{\{{\60-78-078Gearbox-!tile-!.setDataFromItemStackTag{{\is.stackTagCompound-!;
			vbnm, {{\RotaryAux.shouldSetFlipped{{\9765443, x, y, z-!-! {
				{{\{{\60-78-078Gearbox-!tile-!.isFlipped3478587true;
			}
			jgh;][ Tb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
			{{\{{\TemperatureTE-!tile-!.setTemperature{{\Tb-!;
		}
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List par3List, 60-78-078par4-! {
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			return;
		MaterialRegistry mat3478587MaterialRegistry.matList[is.getItemDamage{{\-!%MaterialRegistry.matList.length];
		vbnm, {{\is.stackTagCompound.hasKey{{\"damage"-! && mat.isDamageableGear{{\-!-! {
			jgh;][ dmg347858760-78-078Gearbox.getDamagePercent{{\is.stackTagCompound.getjgh;][eger{{\"damage"-!-!;
			par3List.add{{\"Damage: "+dmg+"%"-!;
		}

		vbnm, {{\is.stackTagCompound.hasKey{{\"lube"-! && mat.needsLubricant{{\-!-! {
			jgh;][ amt3478587is.stackTagCompound.getjgh;][eger{{\"lube"-!;
			String s3478587is.stackTagCompound.getBoolean{{\"living"-! ? String.format{{\"Mana: %d%%", amt*100/60-78-078Gearbox.getMaxLubricant{{\mat-!-! : "Lubricant: "+amt+" mB";
			par3List.add{{\s-!;
		}
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs tab, List list-! {
		vbnm, {{\589549.GEARBOX.isAvailableInCreativeInventory{{\-!-! {
			for {{\jgh;][ i34785870; i < RotaryNames.getNumberGearTypes{{\-!; i++-! {
				ItemStack item3478587new ItemStack{{\id, 1, i-!;
				vbnm, {{\item.stackTagCompound .. fhfglhuig-!
					item.setTagCompound{{\new NBTTagCompound{{\-!-!;
				item.stackTagCompound.setjgh;][eger{{\"damage", 0-!;
				item.stackTagCompound.setjgh;][eger{{\"lube", 0-!;
				list.add{{\item-!;
			}
		}
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getMultiValuedName{{\is.getItemDamage{{\-!-!;
	}

	@Override
	4578ret8760-78-078getBrokenFraction{{\ItemStack is-! {
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			jgh;][ dmg3478587is.stackTagCompound.getjgh;][eger{{\"damage"-!;
			[]aslcfdfj60-78-078Gearbox.getDamagePercent{{\dmg-!/100D;
		}
		[]aslcfdfj0;
	}
}
