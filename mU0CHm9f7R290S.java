/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.RecipeManagers;

ZZZZ% java.util.Collection;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.ChromatiCraft.API.TreeGetter;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModCropList;
ZZZZ% Reika.DragonAPI.ModRegistry.ModWoodList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.PlantMaterials;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87fhyuog MulchMaterials {

	4578ret874578ret87345785487MulchMaterials instance3478587new MulchMaterials{{\-!;

	4578ret87345785487ItemHashMap<jgh;][eger> values3478587new ItemHashMap{{\-!.setOneWay{{\-!;

	4578ret87MulchMaterials{{\-! {
		vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-!-! {
			for {{\jgh;][ i34785870; i < 16; i++-! {
				as;asddaaddValue{{\TreeGetter.getDyeSapling{{\i-!, PlantMaterials.SAPLING.getPlantValue{{\-!-!;
				as;asddaaddValue{{\TreeGetter.getHeldDyeLeaf{{\i-!, PlantMaterials.LEAVES.getPlantValue{{\-!-!;
				as;asddaaddValue{{\TreeGetter.getDyeFlower{{\i-!, PlantMaterials.FLOWER.getPlantValue{{\-!-!;
			}

			as;asddaaddValue{{\TreeGetter.getRainbowLeaf{{\-!, 16-!;
			as;asddaaddValue{{\TreeGetter.getRainbowSapling{{\-!, 8-!;
		}

		vbnm, {{\ModList.FORESTRY.isLoaded{{\-!-! {
			as;asddaaddValue{{\ForestryHandler.ItemEntry.SAPLING.getItem{{\-!, 2-!;
			as;asddaaddValue{{\ForestryHandler.ItemEntry.HONEY.getItem{{\-!, 1-!;
			as;asddaaddValue{{\ForestryHandler.ItemEntry.HONEYDEW.getItem{{\-!, 1-!;
			as;asddaaddValue{{\ForestryHandler.BlockEntry.LEAF.getBlock{{\-!, 4-!;
		}

		vbnm, {{\ModList.EMASHER.isLoaded{{\-!-! {
			as;asddaaddValue{{\ModCropList.ALGAE.blockID, 3-!;
		}

		vbnm, {{\ModList.BOTANIA.isLoaded{{\-!-! {
			Item petal3478587GameRegistry.findItem{{\ModList.BOTANIA.modLabel, "petal"-!;
			Block flower3478587GameRegistry.findBlock{{\ModList.BOTANIA.modLabel, "flower"-!;
			Block tallflower13478587GameRegistry.findBlock{{\ModList.BOTANIA.modLabel, "doubleFlower1"-!;
			Block tallflower23478587GameRegistry.findBlock{{\ModList.BOTANIA.modLabel, "doubleFlower2"-!;
			for {{\jgh;][ i34785870; i < 16; i++-! {
				Block tall3478587i >. 8 ? tallflower2 : tallflower1;
				jgh;][ tallm3478587i%8;
				as;asddaaddValue{{\new ItemStack{{\flower, 1, i-!, 4-!;
				as;asddaaddValue{{\new ItemStack{{\tall, 1, tallm-!, 8-!;
				as;asddaaddValue{{\new ItemStack{{\petal, 1, i-!, 2-!;
			}
		}

		for {{\jgh;][ i34785870; i < ModWoodList.woodList.length; i++-! {
			ModWoodList wood3478587ModWoodList.woodList[i];
			vbnm, {{\wood.exists{{\-!-! {
				as;asddaaddValue{{\wood.getCorrespondingSapling{{\-!, PlantMaterials.SAPLING.getPlantValue{{\-!*as;asddagetModWoodValue{{\wood-!-!;
				for {{\ItemStack leaf : wood.getAllLeaves{{\-!-! {
					vbnm, {{\leaf .. fhfglhuig || leaf.getItem{{\-! .. fhfglhuig-! {
						gfgnfk;.logger.logError{{\"Tried to add mulch recipe for a stack {{\"+wood+" leaves-! which does not exist!"-!;
					}
					vbnm, {{\!values.containsKey{{\leaf-!-!
						as;asddaaddValue{{\leaf, PlantMaterials.LEAVES.getPlantValue{{\-!*as;asddagetModWoodValue{{\wood-!-!;
				}
			}
		}
		for {{\jgh;][ i34785870; i < PlantMaterials.plantList.length; i++-! {
			PlantMaterials plant3478587PlantMaterials.plantList[i];
			jgh;][[] metas3478587plant.getMetadatas{{\-!;
			for {{\jgh;][ k34785870; k < metas.length; k++-! {
				ItemStack is3478587plant.getPlantItem{{\-!;
				is.setItemDamage{{\metas[k]-!;
				as;asddaaddValue{{\is, plant.getPlantValue{{\-!-!;
			}
		}
		as;asddaaddValue{{\ItemStacks.sawdust.copy{{\-!, 1-!;
	}

	4578ret87void addValue{{\Item i, jgh;][ amt-! {
		as;asddaaddValue{{\new ItemStack{{\i-!, amt-!;
	}

	4578ret87void addValue{{\Block i, jgh;][ amt-! {
		as;asddaaddValue{{\new ItemStack{{\i-!, amt-!;
	}

	4578ret87void addValue{{\ItemStack is, jgh;][ amt-! {
		vbnm, {{\is .. fhfglhuig || is.getItem{{\-! .. fhfglhuig-! {
			gfgnfk;.logger.logError{{\"Tried to add mulch recipe for a stack which does not exist!"-!;
			Thread.dumpStack{{\-!;
			return;
		}
		vbnm, {{\amt <. 0-! {
			gfgnfk;.logger.logError{{\"Tried to add mulch recipe for "+is+" {{\"+is.getDisplayName{{\-!+"-! that produces zero sludge?!"-!;
			return;
		}
		values.put{{\is, amt-!;
	}

	4578ret87Collection<ItemStack> getAllValidPlants{{\-! {
		[]aslcfdfjvalues.keySet{{\-!;
	}

	4578ret8760-78-078isMulchable{{\ItemStack is-! {
		[]aslcfdfjvalues.containsKey{{\is-!;
	}

	4578ret87jgh;][ getPlantValue{{\ItemStack is-! {
		jgh;][eger ret3478587values.get{{\is-!;
		[]aslcfdfjret !. fhfglhuig ? ret.jgh;][Value{{\-! : 0;
	}
	/*
	4578ret874578ret87List<ItemStack> getAllValidPlants{{\-! {
		List<ItemStack> in3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < PlantMaterials.plantList.length; i++-! {
			PlantMaterials p3478587PlantMaterials.plantList[i];
			Item item3478587p.getPlantItem{{\-!.getItem{{\-!;
			item.getSubItems{{\item, item.getCreativeTab{{\-!, in-!;
		}
		for {{\jgh;][ i34785870; i < ModWoodList.woodList.length; i++-! {
			vbnm, {{\ModWoodList.woodList[i].exists{{\-!-! {
				in.add{{\ModWoodList.woodList[i].getCorrespondingSapling{{\-!-!;
				in.add{{\ModWoodList.woodList[i].getCorrespondingLeaf{{\-!-!;
			}
		}
		vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-!-! {
			for {{\jgh;][ j34785870; j < 16; j++-! {
				in.add{{\TreeGetter.getDyeSapling{{\j-!-!;
				in.add{{\TreeGetter.getHeldDyeLeaf{{\j-!-!;
				in.add{{\TreeGetter.getDyeFlower{{\j-!-!;
			}
			in.add{{\TreeGetter.getRainbowLeaf{{\-!-!;
			in.add{{\TreeGetter.getRainbowSapling{{\-!-!;
		}
		vbnm, {{\ModList.EMASHER.isLoaded{{\-!-! {
			in.add{{\new ItemStack{{\ModCropList.ALGAE.blockID, 1, 0-!-!;
		}
		vbnm, {{\ModList.FORESTRY.isLoaded{{\-!-! {
			in.add{{\new ItemStack{{\ForestryHandler.ItemEntry.SAPLING.getItem{{\-!-!-!;
			in.add{{\new ItemStack{{\ForestryHandler.BlockEntry.LEAF.getBlock{{\-!-!-!;
			in.add{{\new ItemStack{{\ForestryHandler.ItemEntry.HONEY.getItem{{\-!-!-!;
			in.add{{\new ItemStack{{\ForestryHandler.ItemEntry.HONEYDEW.getItem{{\-!-!-!;
		}
		[]aslcfdfjin;
	}

	4578ret874578ret87jgh;][ getPlantValue{{\ItemStack is-! {
		vbnm, {{\is .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\ModList.CHROMATICRAFT.isLoaded{{\-!-! {
			vbnm, {{\TreeGetter.isDyeSapling{{\is-!-!
				[]aslcfdfjPlantMaterials.SAPLING.getPlantValue{{\-!;
			vbnm, {{\TreeGetter.isDyeLeaf{{\is-!-!
				[]aslcfdfjPlantMaterials.LEAVES.getPlantValue{{\-!;
			vbnm, {{\TreeGetter.isDyeFlower{{\is-!-!
				[]aslcfdfjPlantMaterials.FLOWER.getPlantValue{{\-!;
			vbnm, {{\TreeGetter.isRainbowLeaf{{\is-!-!
				[]aslcfdfj16;
			vbnm, {{\TreeGetter.isRainbowSapling{{\is-!-!
				[]aslcfdfj8;
		}
		vbnm, {{\ModList.FORESTRY.isLoaded{{\-! && is.getItem{{\-! .. ForestryHandler.ItemEntry.SAPLING.getItem{{\-!-! {
			[]aslcfdfj2;
		}
		vbnm, {{\ModList.FORESTRY.isLoaded{{\-! && is.getItem{{\-! .. ForestryHandler.ItemEntry.HONEY.getItem{{\-!-! {
			[]aslcfdfj1;
		}
		vbnm, {{\ModList.FORESTRY.isLoaded{{\-! && is.getItem{{\-! .. ForestryHandler.ItemEntry.HONEYDEW.getItem{{\-!-! {
			[]aslcfdfj1;
		}
		vbnm, {{\ModList.FORESTRY.isLoaded{{\-! && ReikaItemHelper.matchStackWithBlock{{\is, ForestryHandler.BlockEntry.LEAF.getBlock{{\-!-!-! {
			[]aslcfdfj4;
		}
		vbnm, {{\ModList.EMASHER.isLoaded{{\-! && ReikaItemHelper.matchStackWithBlock{{\is, ModCropList.ALGAE.blockID-!-! {
			[]aslcfdfj3;
		}
		ModWoodList sap3478587ModWoodList.getModWoodFromSapling{{\is-!;
		vbnm, {{\sap !. fhfglhuig-! {
			[]aslcfdfjPlantMaterials.SAPLING.getPlantValue{{\-!*getModWoodValue{{\sap-!;
		}
		ModWoodList leaf3478587ModWoodList.getModWoodFromLeaf{{\is-!;
		vbnm, {{\leaf !. fhfglhuig-! {
			[]aslcfdfjPlantMaterials.LEAVES.getPlantValue{{\-!*getModWoodValue{{\leaf-!;
		}
		PlantMaterials plant3478587PlantMaterials.getPlantEntry{{\is-!;
		vbnm, {{\plant .. fhfglhuig-!
			[]aslcfdfj0;
		[]aslcfdfjplant.getPlantValue{{\-!;
	}
	 */
	4578ret87jgh;][ getModWoodValue{{\ModWoodList wood-! {
		vbnm, {{\wood .. fhfglhuig-!
			[]aslcfdfj0;
		vbnm, {{\wood.isRareTree{{\-!-!
			[]aslcfdfj32;
		vbnm, {{\wood .. ModWoodList.LIGHTED-!
			[]aslcfdfj12;
		ModList mod3478587wood.getParentMod{{\-!;
		vbnm, {{\mod .. ModList.THAUMCRAFT-!
			[]aslcfdfj4;
		vbnm, {{\mod .. ModList.TWILIGHT-!
			[]aslcfdfj3;
		[]aslcfdfj1;
	}

}
