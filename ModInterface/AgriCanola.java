/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import com.InfinityRaider.AgriCraft.api.API;
import com.InfinityRaider.AgriCraft.api.v1.IGrowthRequirement;
import com.InfinityRaider.AgriCraft.api.v2.APIv2;
import com.InfinityRaider.AgriCraft.api.v2.IAdditionalCropData;
import com.InfinityRaider.AgriCraft.api.v2.ICrop;
import com.InfinityRaider.AgriCraft.api.v2.ICropPlant;

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
		return true;
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

	public static void register() {
		//Remove native handler for now
		try {
			Class c = Class.forName("com.InfinityRaider.AgriCraft.compatibility.ModHelper");
			Field f = c.getDeclaredField("modHelpers");
			f.setAccessible(true);
			HashMap<String, Object> map = (HashMap<String, Object>)f.get(null);
			map.remove(RotaryCraft.instance.getModContainer().getModId());
		}
		catch (Exception e) {
			ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.AGRICRAFT, e);
			e.printStackTrace();
		}

		((APIv2)API.getAPI(2)).registerCropPlant(new AgriCanola());
	}

	@Override
	public IAdditionalCropData getInitialCropData(World world, int x, int y, int z, ICrop crop) {
		return null;
	}

	@Override
	public IAdditionalCropData readCropDataFromNBT(NBTTagCompound tag) {
		return null;
	}

	@Override
	public void onValidate(World world, int x, int y, int z, ICrop crop) {

	}

	@Override
	public void onInvalidate(World world, int x, int y, int z, ICrop crop) {

	}

	@Override
	public void onChunkUnload(World world, int x, int y, int z, ICrop crop) {

	}

	@Override
	public IGrowthRequirement getGrowthRequirement() {
		return ((APIv2)API.getAPI(2)).createDefaultGrowthRequirement();
	}

}
