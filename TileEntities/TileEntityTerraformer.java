/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.liquids.LiquidDictionary;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Instantiable.ObjectWeb;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityTerraformer extends TileEntityInventoriedPowerReceiver implements PipeConnector {

	private static final ObjectWeb transforms = new ObjectWeb(BiomeGenBase.class);
	private static final HashMap<List<BiomeGenBase>, List<Object>> changeReqs = new HashMap<List<BiomeGenBase>, List<Object>>();
	private static final HashMap<List<BiomeGenBase>, Integer> powerReqs = new HashMap<List<BiomeGenBase>, Integer>();

	private ItemStack[] inv = new ItemStack[36];

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.TERRAFORMER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return p == MachineRegistry.PIPE;
	}

	private boolean hasReqsForTransform(BiomeGenBase from, BiomeGenBase to) {
		return false;
	}

	private static List getReqsForBiomeTransform(BiomeGenBase from, BiomeGenBase to) {
		return changeReqs.get(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to}));
	}

	private static void addBiomeTransformation(BiomeGenBase from, BiomeGenBase to, int power, Object... req) {
		transforms.addChild(from, to);
		changeReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to}), ReikaJavaLibrary.makeListFromArray(req));
		powerReqs.put(ReikaJavaLibrary.makeListFromArray(new BiomeGenBase[]{from, to}), power);
	}

	static {
		addBiomeTransformation(BiomeGenBase.desert, BiomeGenBase.plains, 0, LiquidDictionary.getLiquid("Water", 10000), new ItemStack(Block.tallGrass.blockID, 64, 0));
		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.forest, 0, LiquidDictionary.getLiquid("Water", 20000), new ItemStack(Block.sapling.blockID, 36, 0), new ItemStack(Block.sapling.blockID, 12, 2));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.jungle, 0, LiquidDictionary.getLiquid("Water", 50000), new ItemStack(Block.sapling.blockID, 24, 0), new ItemStack(Block.sapling.blockID, 40, 3));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.swampland, 0, LiquidDictionary.getLiquid("Water", 100000), new ItemStack(Block.sapling.blockID, 12, 0), new ItemStack(Block.mushroomRed.blockID, 6, 0), new ItemStack(Block.mushroomBrown.blockID, 24, 0));
		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.ocean, 0, LiquidDictionary.getLiquid("Water", 500000));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.frozenOcean, 0, new ItemStack(Block.ice.blockID, 64, 0));

		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.extremeHills, 0, new ItemStack(Block.sapling.blockID, 3, 0), new ItemStack(Item.coal.itemID, 12, 0));

		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.mushroomIsland, 0, new ItemStack(Block.mycelium.blockID, 32, 0), new ItemStack(Block.mushroomRed.blockID, 64, 0), new ItemStack(Block.mushroomBrown.blockID, 64, 0)); //?

		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.taiga, 0, new ItemStack(Block.blockSnow.blockID, 24, 0), new ItemStack(Block.sapling.blockID, 24, 1));
		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.icePlains, 0, new ItemStack(Block.blockSnow.blockID, 64, 0), new ItemStack(Block.sapling.blockID, 4, 0));
		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.frozenOcean, 0, LiquidDictionary.getLiquid("Water", 1000000), new ItemStack(Block.ice.blockID, 64, 0));


		addBiomeTransformation(BiomeGenBase.plains, BiomeGenBase.desert, 0, new ItemStack(Block.sand.blockID, 64, 0), new ItemStack(Block.sandStone.blockID, 32, 0), new ItemStack(Block.cactus.blockID, 12, 0));
		addBiomeTransformation(BiomeGenBase.forest, BiomeGenBase.plains, 0, new ItemStack(Block.tallGrass.blockID, 64, 0));
		addBiomeTransformation(BiomeGenBase.jungle, BiomeGenBase.forest, 0, new ItemStack(Block.sapling.blockID, 36, 0), new ItemStack(Block.sapling.blockID, 12, 2));

		addBiomeTransformation(BiomeGenBase.swampland, BiomeGenBase.plains, 0, new ItemStack(Block.tallGrass.blockID, 64, 0), new ItemStack(Block.dirt.blockID, 64, 0));
		addBiomeTransformation(BiomeGenBase.ocean, BiomeGenBase.swampland, 0, new ItemStack(Block.sapling.blockID, 12, 0), new ItemStack(Block.mushroomRed.blockID, 6, 0), new ItemStack(Block.mushroomBrown.blockID, 24, 0), new ItemStack(Block.dirt.blockID, 64, 0), new ItemStack(Block.grass.blockID, 8, 0));

		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.ocean, 0);
		addBiomeTransformation(BiomeGenBase.extremeHills, BiomeGenBase.plains, 0, new ItemStack(Block.tallGrass.blockID, 24, 0));

		addBiomeTransformation(BiomeGenBase.taiga, BiomeGenBase.forest, 0, new ItemStack(Block.sapling.blockID, 18, 0), new ItemStack(Block.sapling.blockID, 6, 2));

		addBiomeTransformation(BiomeGenBase.icePlains, BiomeGenBase.taiga, 0, new ItemStack(Block.sapling.blockID, 24, 1));
		addBiomeTransformation(BiomeGenBase.frozenOcean, BiomeGenBase.icePlains, 0, new ItemStack(Block.sapling.blockID, 3, 0), new ItemStack(Block.dirt.blockID, 64, 0), new ItemStack(Block.grass.blockID, 8, 0));
	}

}
