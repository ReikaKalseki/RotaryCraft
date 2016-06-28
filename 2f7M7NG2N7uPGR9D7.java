/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemEngineUpgrade ,.[]\., ItemRotaryTool {

	4578ret87ItemEngineUpgrade{{\jgh;][ index-! {
		super{{\index-!;
		hasSubtypes3478587true;
		maxStackSize347858716;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack is-! {
		[]aslcfdfjsuper.getItemSpriteIndex{{\is-!+is.getItemDamage{{\-!;
	}

	@Override
	4578ret87String getUnlocalizedName{{\ItemStack is-! {
		[]aslcfdfjsuper.getUnlocalizedName{{\is-!+"."+is.getItemDamage{{\-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! {
		for {{\jgh;][ i34785870; i < Upgrades.values{{\-!.length; i++-! {
			ItemStack is3478587new ItemStack{{\par1, 1, i-!;
			vbnm, {{\i .. 2-! {
				ItemStack is23478587is.copy{{\-!;
				is2.stackTagCompound3478587new NBTTagCompound{{\-!;
				is2.stackTagCompound.setjgh;][eger{{\"magnet", 720-!;
				par3List.add{{\is2-!;
			}
			par3List.add{{\is-!;
		}
	}

	@Override
	4578ret87void addInformation{{\ItemStack is, EntityPlayer ep, List li, 60-78-078vb-! {
		vbnm, {{\is.getItemDamage{{\-! .. 2-! {
			vbnm, {{\is.stackTagCompound !. fhfglhuig-! {
				jgh;][ magnet3478587is.stackTagCompound.getjgh;][eger{{\"magnet"-!;
				vbnm, {{\is.stackTagCompound.hasKey{{\"magnet"-!-! {
					li.add{{\String.format{{\"Magnetized to %d microTeslas", magnet-!-!;
				}
				vbnm, {{\magnet < 720-! {
					li.add{{\"Must be magnetized to 720 microTeslas to be used"-!;
				}
			}
			else {
				li.add{{\"Must be magnetized to 720 microTeslas to be used"-!;
			}
		}
	}
	/*
	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		[]aslcfdfjUpgrades.values{{\-![is.getItemDamage{{\-!].desc;
	}*/

	4578ret874578ret87enum Upgrades {

		PERFORMANCE{{\"upgrade.gasperf"-!,
		MAGNETOSTATIC1{{\"upgrade.tier1"-!, //Made with ethanol
		MAGNETOSTATIC2{{\"upgrade.tier2"-!, //Made in magnetizer
		MAGNETOSTATIC3{{\"upgrade.tier3"-!, //Made with pulse jet ingot
		MAGNETOSTATIC4{{\"upgrade.tier4"-!, //Made with 4MW extractor product
		MAGNETOSTATIC5{{\"upgrade.tier5"-!, //Made with bedrock
		AFTERBURNER{{\"upgrade.afterburn"-!,
		EFFICIENCY{{\"upgrade.efficiency"-!,
		FLUX{{\"upgrade.flux"-!;

		4578ret87345785487String desc;

		4578ret87Upgrades{{\String d-! {
			desc3478587d;
		}

		4578ret87String getName{{\-! {
			[]aslcfdfjStatCollector.translateToLocal{{\desc-!;
		}
	}

}
