/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesCompactor
{
	private static final RecipesCompactor CompactorBase = new RecipesCompactor();

	private ItemHashMap<ItemStack> compactingList = new ItemHashMap();
	private ItemHashMap<Integer> pressureMap = new ItemHashMap();
	private ItemHashMap<Integer> temperatureMap = new ItemHashMap();
	private List<ItemStack> compactables = new ArrayList();

	public static final RecipesCompactor getRecipes()
	{
		return CompactorBase;
	}

	private RecipesCompactor()
	{
		int rp = TileEntityCompactor.REQPRESS;
		int rt = TileEntityCompactor.REQTEMP;
		this.addCompacting(Items.coal, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 0), 0.2F, rp, rt); //No charcoal
		this.addCompacting(ItemStacks.anthracite, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 1), 0.2F, rp, rt);
		this.addCompacting(ItemStacks.prismane, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 2), 0.2F, rp, rt);
		this.addCompacting(ItemStacks.lonsda, new ItemStack(Items.diamond, this.getNumberPerStep(), 0), 0.2F, rp, rt);

		this.addCompacting(Items.blaze_powder, new ItemStack(Blocks.glowstone, 1, 0), 0.2F, 2000, 600);
	}

	public final int getNumberPerStep() {
		return DifficultyEffects.COMPACTOR.getInt();
	}

	/**
	 * Add a metadata-sensitive compactor recipe
	 * @param itemID The Item ID
	 * @param metadata The Item Metadata
	 * @param itemstack The ItemStack for the result
	 */
	public void addCompacting(ItemStack in, ItemStack itemstack, float xp, int pressure, int temperature)
	{
		compactingList.put(in, itemstack);
		//this.ExtractorExperience.put(Integer.valueOf(itemstack.getItem), Float.valueOf(xp));
		compactables.add(in.copy());
		pressureMap.put(in, pressure);
		temperatureMap.put(in, temperature);
	}

	public void addCompacting(Item in, ItemStack itemstack, float xp, int pressure, int temperature)
	{
		this.addCompacting(new ItemStack(in), itemstack, xp, pressure, temperature);
	}

	public void addCompacting(Block in, ItemStack itemstack, float xp, int pressure, int temperature)
	{
		this.addCompacting(new ItemStack(in), itemstack, xp, pressure, temperature);
	}

	/**
	 * Used to get the resulting ItemStack form a source ItemStack
	 * @param item The Source ItemStack
	 * @return The result ItemStack
	 */
	public ItemStack getCompactingResult(ItemStack item)
	{
		if (item == null)
			return null;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", Items.itemID, item.getItemDamage()));
		ItemStack ret = compactingList.get(item);
		return ret;
	}

	public boolean isCompactable(ItemStack item) {
		return this.getCompactingResult(item) != null;
	}

	public int getReqPressure(ItemStack item) {
		Integer ret = pressureMap.get(item);
		return ret != null ? ret.intValue() : 0;
	}

	public List<ItemStack> getCompactables() {
		return compactables;
	}

	public ItemStack getSource(ItemStack result) {
		for (int i = 0; i < compactables.size(); i++) {
			ItemStack in = compactables.get(i);
			ItemStack out = this.getCompactingResult(in);
			if (ReikaItemHelper.matchStacks(result, out))
				return in;
		}
		return null;
	}

	public int getReqTemperature(ItemStack item) {
		Integer ret = temperatureMap.get(item);
		return ret != null ? ret.intValue() : 0;
	}
}