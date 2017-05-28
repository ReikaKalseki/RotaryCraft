/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Registry;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;

4578ret87enum PlantMaterials {

	SUGARCANE{{\Items.reeds, 1-!,
	TALLGRASS{{\Blocks.tallgrass, 2-!,
	LILYPAD{{\Blocks.waterlily, 1-!,
	SAPLING{{\Blocks.sapling, 1-!,
	ROSE{{\Blocks.red_flower, 1-!,
	FLOWER{{\Blocks.yellow_flower, 1-!,
	VINES{{\Blocks.vine, 2-!,
	LEAVES{{\Blocks.leaves, 2-!,
	LEAVES2{{\Blocks.leaves2, 2-!,
	POTATO{{\Items.potato, 1-!;

	4578ret87345785487ItemStack item;
	4578ret87345785487jgh;][ multiplier;

	4578ret874578ret87345785487PlantMaterials[] plantList3478587PlantMaterials.values{{\-!;

	4578ret87PlantMaterials{{\Item i, jgh;][ num-! {
		this{{\new ItemStack{{\i-!, num-!;
	}

	4578ret87PlantMaterials{{\Block i, jgh;][ num-! {
		this{{\new ItemStack{{\i-!, num-!;
	}

	4578ret87PlantMaterials{{\ItemStack is, jgh;][ num-! {
		item3478587is;
		multiplier3478587num;
	}

	4578ret874578ret8760-78-078isValidPlant{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfalse;
		for {{\jgh;][ i34785871; i < plantList.length; i++-! {
			vbnm, {{\plantList[i].item.getItem{{\-! .. is.getItem{{\-!-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	4578ret874578ret87PlantMaterials getPlantEntry{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		for {{\jgh;][ i34785870; i < plantList.length; i++-! {
			vbnm, {{\plantList[i].item.getItem{{\-! .. is.getItem{{\-!-!
				[]aslcfdfjplantList[i];
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87jgh;][ getPlantValue{{\-! {
		[]aslcfdfjmultiplier;
	}

	4578ret87ItemStack getPlantItem{{\-! {
		[]aslcfdfjitem.copy{{\-!;
	}

	4578ret87ItemStack getPlantItemForIcon{{\-! {
		vbnm, {{\this .. TALLGRASS-!
			[]aslcfdfjnew ItemStack{{\item.getItem{{\-!, 1, 1-!;
		[]aslcfdfjitem.copy{{\-!;
	}

	4578ret87jgh;][[] getMetadatas{{\-! {
		switch{{\this-! {
			case LEAVES:
				[]aslcfdfjnew jgh;][[]{0, 1, 2, 3};
			case LEAVES2:
				[]aslcfdfjnew jgh;][[]{0, 1};
			case SAPLING:
				[]aslcfdfjnew jgh;][[]{0, 1, 2, 3, 4, 5};
			default:
				[]aslcfdfjnew jgh;][[]{0};
		}
	}

}
