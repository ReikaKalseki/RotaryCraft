/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import com.InfinityRaider.AgriCraft.api.v1.ICropPlant;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class AgriCanola implements ICropPlant {

	private static final int[] GAIN_FORTUNE_MAPPING = { 0, 1, 1, 2, 2, 3, 4, 5, 7, 10 };
	private static final int[] METADATA_CONVERSION = { 0, 1, 2, 4, 5, 6, 8, 9 };

	@Override
	public int tier() {
		return 2;
	}

	@Override
	public ItemStack getSeed() {
		return ItemRegistry.CANOLA.getStackOf();
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.CANOLA.getBlockInstance();
	}

	@Override
	public ArrayList<ItemStack> getAllFruits() {
		return ReikaJavaLibrary.makeListFrom(this.getSeed());
	}

	@Override
	public ItemStack getRandomFruit(Random rand) {
		return this.getSeed();
	}

	@Override
	public ArrayList<ItemStack> getFruitsOnHarvest(int gain, Random rand) {
		ArrayList<ItemStack> li = new ArrayList();
		int n = BlockCanola.getDrops(GAIN_FORTUNE_MAPPING[gain-1], rand);
		while (n > 0) {
			int rem = Math.min(n, ItemRegistry.CANOLA.getItemInstance().getItemStackLimit());
			li.add(ItemRegistry.CANOLA.getCraftedProduct(rem));
			n -= rem;
		}
		return li;
	}

	@Override
	public boolean onHarvest(World world, int x, int y, int z, EntityPlayer player) {
		return false;
	}

	@Override
	public void onSeedPlanted(World world, int x, int y, int z) {

	}

	@Override
	public void onPlantRemoved(World world, int x, int y, int z) {

	}

	@Override
	public boolean canBonemeal() {
		return true;
	}

	@Override
	public boolean onAllowedGrowthTick(World world, int x, int y, int z, int oldGrowthStage) {
		return true;
	}

	@Override
	public boolean isFertile(World world, int x, int y, int z) {
		return BlockCanola.canGrowAt(world, x, y, z);
	}

	@Override
	public boolean isMature(IBlockAccess world, int x, int y, int z) {
		return METADATA_CONVERSION[world.getBlockMetadata(x, y, z)] == BlockCanola.GROWN;
	}

	@Override
	public float getHeight(int meta) {
		return BlockCanola.getPlantHeight(METADATA_CONVERSION[meta]);
	}

	@Override
	public IIcon getPlantIcon(int growthStage) {
		return BlockRegistry.CANOLA.getBlockInstance().getIcon(2, METADATA_CONVERSION[growthStage]);
	}

	@Override
	public boolean renderAsFlower() {
		return false;
	}

	@Override
	public String getInformation() {
		return "A coarse black seed, often ground for oil, used either for cooking or for industrial lubrication.";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean overrideRendering() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderPlantInCrop(IBlockAccess world, int x, int y, int z, RenderBlocks renderer) {

	}

}
