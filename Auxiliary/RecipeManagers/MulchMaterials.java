/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.ChromatiCraft.API.TreeGetter;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.PlantMaterials;

public class MulchMaterials {

	public static final MulchMaterials instance = new MulchMaterials();

	private final ItemHashMap<Integer> values = new ItemHashMap().setOneWay();

	private MulchMaterials() {
		if (ModList.CHROMATICRAFT.isLoaded()) {
			for (int i = 0; i < 16; i++) {
				this.addValue(TreeGetter.getDyeSapling(i), PlantMaterials.SAPLING.getPlantValue());
				this.addValue(TreeGetter.getHeldDyeLeaf(i), PlantMaterials.LEAVES.getPlantValue());
				this.addValue(TreeGetter.getDyeFlower(i), PlantMaterials.FLOWER.getPlantValue());
			}

			this.addValue(TreeGetter.getRainbowLeaf(), 16);
			this.addValue(TreeGetter.getRainbowSapling(), 8);
		}
		if (ModList.FORESTRY.isLoaded()) {
			this.addValue(ForestryHandler.ItemEntry.SAPLING.getItem(), 2);
			this.addValue(ForestryHandler.ItemEntry.HONEY.getItem(), 1);
			this.addValue(ForestryHandler.ItemEntry.HONEYDEW.getItem(), 1);
			this.addValue(ForestryHandler.BlockEntry.LEAF.getBlock(), 4);
		}
		if (ModList.EMASHER.isLoaded()) {
			this.addValue(ModCropList.ALGAE.blockID, 3);
		}
		for (int i = 0; i < ModWoodList.woodList.length; i++) {
			ModWoodList wood = ModWoodList.woodList[i];
			if (wood.exists()) {
				this.addValue(wood.getCorrespondingSapling(), PlantMaterials.SAPLING.getPlantValue()*this.getModWoodValue(wood));
				for (ItemStack leaf : wood.getAllLeaves()) {
					if (leaf == null || leaf.getItem() == null) {
						RotaryCraft.logger.logError("Tried to add mulch recipe for a stack ("+wood+" leaves) which does not exist!");
					}
					if (!values.containsKey(leaf))
						this.addValue(leaf, PlantMaterials.LEAVES.getPlantValue()*this.getModWoodValue(wood));
				}
			}
		}
		for (int i = 0; i < PlantMaterials.plantList.length; i++) {
			PlantMaterials plant = PlantMaterials.plantList[i];
			for (int k = 0; k < 16; k++) {
				ItemStack is = plant.getPlantItem();
				is.setItemDamage(k);
				this.addValue(is, plant.getPlantValue());
			}
		}
		this.addValue(ItemStacks.sawdust.copy(), 1);
	}

	private void addValue(Item i, int amt) {
		this.addValue(new ItemStack(i), amt);
	}

	private void addValue(Block i, int amt) {
		this.addValue(new ItemStack(i), amt);
	}

	private void addValue(ItemStack is, int amt) {
		if (is == null || is.getItem() == null) {
			RotaryCraft.logger.logError("Tried to add mulch recipe for a stack which does not exist!");
			Thread.dumpStack();
			return;
		}
		if (amt <= 0) {
			RotaryCraft.logger.logError("Tried to add mulch recipe for "+is+" ("+is.getDisplayName()+") that produces zero sludge?!");
			return;
		}
		values.put(is, amt);
	}

	public Collection<ItemStack> getAllValidPlants() {
		return values.keySet();
	}

	public boolean isMulchable(ItemStack is) {
		return values.containsKey(is);
	}

	public int getPlantValue(ItemStack is) {
		Integer ret = values.get(is);
		return ret != null ? ret.intValue() : 0;
	}
	/*
	public static List<ItemStack> getAllValidPlants() {
		List<ItemStack> in = new ArrayList();
		for (int i = 0; i < PlantMaterials.plantList.length; i++) {
			PlantMaterials p = PlantMaterials.plantList[i];
			Item item = p.getPlantItem().getItem();
			item.getSubItems(item, item.getCreativeTab(), in);
		}
		for (int i = 0; i < ModWoodList.woodList.length; i++) {
			if (ModWoodList.woodList[i].exists()) {
				in.add(ModWoodList.woodList[i].getCorrespondingSapling());
				in.add(ModWoodList.woodList[i].getCorrespondingLeaf());
			}
		}
		if (ModList.CHROMATICRAFT.isLoaded()) {
			for (int j = 0; j < 16; j++) {
				in.add(TreeGetter.getDyeSapling(j));
				in.add(TreeGetter.getHeldDyeLeaf(j));
				in.add(TreeGetter.getDyeFlower(j));
			}
			in.add(TreeGetter.getRainbowLeaf());
			in.add(TreeGetter.getRainbowSapling());
		}
		if (ModList.EMASHER.isLoaded()) {
			in.add(new ItemStack(ModCropList.ALGAE.blockID, 1, 0));
		}
		if (ModList.FORESTRY.isLoaded()) {
			in.add(new ItemStack(ForestryHandler.ItemEntry.SAPLING.getItem()));
			in.add(new ItemStack(ForestryHandler.BlockEntry.LEAF.getBlock()));
			in.add(new ItemStack(ForestryHandler.ItemEntry.HONEY.getItem()));
			in.add(new ItemStack(ForestryHandler.ItemEntry.HONEYDEW.getItem()));
		}
		return in;
	}

	public static int getPlantValue(ItemStack is) {
		if (is == null)
			return 0;
		if (ModList.CHROMATICRAFT.isLoaded()) {
			if (TreeGetter.isDyeSapling(is))
				return PlantMaterials.SAPLING.getPlantValue();
			if (TreeGetter.isDyeLeaf(is))
				return PlantMaterials.LEAVES.getPlantValue();
			if (TreeGetter.isDyeFlower(is))
				return PlantMaterials.FLOWER.getPlantValue();
			if (TreeGetter.isRainbowLeaf(is))
				return 16;
			if (TreeGetter.isRainbowSapling(is))
				return 8;
		}
		if (ModList.FORESTRY.isLoaded() && is.getItem() == ForestryHandler.ItemEntry.SAPLING.getItem()) {
			return 2;
		}
		if (ModList.FORESTRY.isLoaded() && is.getItem() == ForestryHandler.ItemEntry.HONEY.getItem()) {
			return 1;
		}
		if (ModList.FORESTRY.isLoaded() && is.getItem() == ForestryHandler.ItemEntry.HONEYDEW.getItem()) {
			return 1;
		}
		if (ModList.FORESTRY.isLoaded() && ReikaItemHelper.matchStackWithBlock(is, ForestryHandler.BlockEntry.LEAF.getBlock())) {
			return 4;
		}
		if (ModList.EMASHER.isLoaded() && ReikaItemHelper.matchStackWithBlock(is, ModCropList.ALGAE.blockID)) {
			return 3;
		}
		ModWoodList sap = ModWoodList.getModWoodFromSapling(is);
		if (sap != null) {
			return PlantMaterials.SAPLING.getPlantValue()*getModWoodValue(sap);
		}
		ModWoodList leaf = ModWoodList.getModWoodFromLeaf(is);
		if (leaf != null) {
			return PlantMaterials.LEAVES.getPlantValue()*getModWoodValue(leaf);
		}
		PlantMaterials plant = PlantMaterials.getPlantEntry(is);
		if (plant == null)
			return 0;
		return plant.getPlantValue();
	}
	 */
	private int getModWoodValue(ModWoodList wood) {
		if (wood == null)
			return 0;
		if (wood.isRareTree())
			return 32;
		ModList mod = wood.getParentMod();
		if (mod == ModList.THAUMCRAFT)
			return 4;
		if (mod == ModList.TWILIGHT)
			return 3;
		return 1;
	}

}
