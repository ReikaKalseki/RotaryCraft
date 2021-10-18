package Reika.RotaryCraft.API.Interfaces;

import java.util.ArrayList;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;

public interface LeafBlockWithExtras {

	void onPreWoodcutterBreak(World world, int x, int y, int z);

	ArrayList<ItemStack> getExtraDrops(World world, int x, int y, int z, int fortune);

	public static void breakHangingVine(World world, int x, int y, int z, Block b, int meta) {
		int y2 = y;
		while (world.getBlock(x, y2-1, z) == b && world.getBlockMetadata(x, y2-1, z) == meta) {
			y2--;
		}
		for (; y2 < y; y2++) {
			world.setBlockToAir(x, y2, z);
		}
	}

	public static void breakHangingVines(World world, int x, int y, int z, Function<BlockKey, Boolean> func) {
		int y2 = y;
		while (func.apply(BlockKey.getAt(world, x, y2-1, z))) {
			y2--;
		}
		for (; y2 < y; y2++) {
			world.setBlockToAir(x, y2, z);
		}
	}

	public static void breakMountedBlock(World world, int x, int y, int z, Block b, int meta) {
		if (world.getBlock(x, y+1, z) == b && world.getBlockMetadata(x, y+1, z) == meta)
			world.setBlockToAir(x, y+1, z);
	}

	public static ArrayList<ItemStack> getExtraDropsForHangingVine(World world, int x, int y, int z, int fortune, Block b, int meta) {
		ArrayList<ItemStack> ret = new ArrayList();
		int y2 = y;
		while (world.getBlock(x, y2-1, z) == b && world.getBlockMetadata(x, y2-1, z) == meta) {
			y2--;
			ret.addAll(b.getDrops(world, x, y2, z, meta, fortune));
		}
		return ret;
	}

	public static ArrayList<ItemStack> getExtraDropsForHangingVines(World world, int x, int y, int z, int fortune, Function<BlockKey, Boolean> func) {
		ArrayList<ItemStack> ret = new ArrayList();
		int y2 = y;
		while (func.apply(BlockKey.getAt(world, x, y2-1, z))) {
			y2--;
			ret.addAll(world.getBlock(x, y2, z).getDrops(world, x, y2, z, world.getBlockMetadata(x, y2, z), fortune));
		}
		return ret;
	}

	public static ArrayList<ItemStack> getMountedDrops(World world, int x, int y, int z, int fortune, Block b, int meta) {
		ArrayList<ItemStack> ret = new ArrayList();
		if (world.getBlock(x, y+1, z) == b && world.getBlockMetadata(x, y+1, z) == meta)
			ret.addAll(b.getDrops(world, x, y+1, z, meta, fortune));
		return ret;
	}

}
