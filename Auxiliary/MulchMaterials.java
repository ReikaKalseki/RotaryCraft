package Reika.RotaryCraft.Auxiliary;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import Reika.ChromatiCraft.API.TreeGetter;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.Registry.PlantMaterials;

public class MulchMaterials {

	public static final MulchMaterials instance = new MulchMaterials();

	private final ItemHashMap<Integer> values = new ItemHashMap().setOneWay();

	private MulchMaterials() {
		if (ModList.CHROMATICRAFT.isLoaded()) {
			for (int i = 0; i < 16; i++) {
				values.put(TreeGetter.getDyeSapling(i), PlantMaterials.SAPLING.getPlantValue());
				values.put(TreeGetter.getHeldDyeLeaf(i), PlantMaterials.SAPLING.getPlantValue());
				values.put(TreeGetter.getDyeFlower(i), PlantMaterials.SAPLING.getPlantValue());
			}

			values.put(TreeGetter.getRainbowLeaf(), 16);
			values.put(TreeGetter.getRainbowSapling(), 8);
		}
		if (ModList.FORESTRY.isLoaded()) {
			values.put(ForestryHandler.ItemEntry.SAPLING.getItem(), 2);
			values.put(ForestryHandler.ItemEntry.HONEY.getItem(), 1);
			values.put(ForestryHandler.ItemEntry.HONEYDEW.getItem(), 1);
			values.put(ForestryHandler.BlockEntry.LEAF.getBlock(), 4);
		}
		if (ModList.EMASHER.isLoaded()) {
			values.put(ModCropList.ALGAE.blockID, 3);
		}
		for (int i = 0; i < ModWoodList.woodList.length; i++) {
			ModWoodList wood = ModWoodList.woodList[i];
			if (wood.exists()) {
				values.put(wood.getCorrespondingSapling(), PlantMaterials.SAPLING.getPlantValue()*this.getModWoodValue(wood));
				for (ItemStack leaf : wood.getAllLeaves())
					values.put(leaf, PlantMaterials.LEAVES.getPlantValue()*this.getModWoodValue(wood));
			}
		}
		for (int i = 0; i < PlantMaterials.plantList.length; i++) {
			PlantMaterials plant = PlantMaterials.plantList[i];
			values.put(plant.getPlantItem(), plant.getPlantValue());
		}
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
