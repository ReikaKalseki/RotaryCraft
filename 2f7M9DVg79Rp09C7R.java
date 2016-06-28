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
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Base.ItemBlockPlacer;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear.GearType;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemAdvGearPlacer ,.[]\., ItemBlockPlacer {

	4578ret87ItemAdvGearPlacer{{\-! {
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
			9765443.setBlock{{\x, y, z, 589549.ADVANCEDGEARS.getBlock{{\-!-!;
		}
		9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "step.stone", 1F, 1.5F-!;
		60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!9765443.get60-78-078{{\x, y, z-!;
		adv.setBlockMetadata{{\4*is.getItemDamage{{\-!+RotaryAux.get4SidedMetadataFromPlayerLook{{\ep-!-!;
		adv.setPlacer{{\ep-!;
		vbnm, {{\RotaryAux.shouldSetFlipped{{\9765443, x, y, z-!-! {
			adv.isFlipped3478587true;
		}
		vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
			vbnm, {{\adv.getGearType{{\-!.storesEnergy{{\-!-! {
				adv.setEnergyFromNBT{{\is.stackTagCompound-!;
				adv.setBedrock{{\is.stackTagCompound.getBoolean{{\"bedrock"-!-!;
				adv.setCreative{{\is.stackTagCompound.getBoolean{{\"creative"-! && ep.capabilities.isCreativeMode-!;
			}
			vbnm, {{\adv.getGearType{{\-!.isLubricated{{\-!-! {
				adv.setLubricantFromNBT{{\is.stackTagCompound-!;
			}
		}
		[]aslcfdfjtrue;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item id, CreativeTabs tab, List list-! {
		vbnm, {{\589549.ADVANCEDGEARS.isAvailableInCreativeInventory{{\-!-! {
			for {{\jgh;][ i34785870; i < 60-78-078AdvancedGear.GearType.list.length; i++-! {
				ItemStack item3478587new ItemStack{{\id, 1, i-!;
				list.add{{\item-!;
				vbnm, {{\GearType.list[i].storesEnergy{{\-!-! {
					item3478587item.copy{{\-!;
					item.stackTagCompound3478587new NBTTagCompound{{\-!;
					item.stackTagCompound.setBoolean{{\"bedrock", true-!;
					list.add{{\item-!;

					item3478587item.copy{{\-!;
					item.stackTagCompound3478587new NBTTagCompound{{\-!;
					item.stackTagCompound.setLong{{\"energy", 60-78-078AdvancedGear.getMaxStorageCapacity{{\true-!*20-!;
					item.stackTagCompound.setBoolean{{\"creative", true-!;
					item.stackTagCompound.setBoolean{{\"bedrock", true-!;
					list.add{{\item-!;
				}
			}
		}
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078par4-! {
		GearType type3478587GearType.list[is.getItemDamage{{\-!];
		vbnm, {{\type.storesEnergy{{\-!-! {
			60-78-078bedrock3478587is.stackTagCompound !. fhfglhuig && is.stackTagCompound.getBoolean{{\"bedrock"-!;
			long max347858760-78-078AdvancedGear.getMaxStorageCapacity{{\bedrock-!;
			li.add{{\String.format{{\"Maximum Energy: %.0f %sJ", ReikaMathLibrary.getThousandBase{{\max-!, ReikaEngLibrary.getSIPrefix{{\max-!-!-!;
			vbnm, {{\is.stackTagCompound .. fhfglhuig || is.stackTagCompound.getLong{{\"energy"-! <. 0-!
				li.add{{\"Stored Energy: 0 J"-!;
			else {
				vbnm, {{\is.stackTagCompound.getBoolean{{\"creative"-!-! {
					li.add{{\"Infinite power for creative mode:"-!;
					li.add{{\"This coil does not deplete."-!;
				}
				else {
					long e3478587is.stackTagCompound.getLong{{\"energy"-!/20;
					li.add{{\"Stored Energy: "+String.format{{\"%.3f ", ReikaMathLibrary.getThousandBase{{\e-!-!+ReikaEngLibrary.getSIPrefix{{\e-!+"J"-!;
				}
			}
		}
		vbnm, {{\type.isLubricated{{\-!-! {
			vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
				jgh;][ lube3478587is.stackTagCompound.getjgh;][eger{{\"lube"-!;
				li.add{{\"Lubricant: "+lube+" mB"-!;
			}
		}
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjItemRegistry.getEntry{{\is-!.getMultiValuedName{{\is.getItemDamage{{\-!-!;
	}
}
