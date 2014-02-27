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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;

public class RecipesCompactor
{
	private static final RecipesCompactor CompactorBase = new RecipesCompactor();

	private Map metaCompactingList = new HashMap();
	private Map pressureMap = new HashMap();
	private Map temperatureMap = new HashMap();
	private List<ItemStack> compactables = new ArrayList();

	public static final RecipesCompactor getRecipes()
	{
		return CompactorBase;
	}

	private RecipesCompactor()
	{
		int rp = TileEntityCompactor.REQPRESS;
		int rt = TileEntityCompactor.REQTEMP;
		this.addCompacting(Item.coal, new ItemStack(RotaryCraft.compacts.itemID, this.getNumberPerStep(), 0), 0.2F, rp, rt); //No charcoal
		this.addCompacting(ItemStacks.anthracite, new ItemStack(RotaryCraft.compacts.itemID, this.getNumberPerStep(), 1), 0.2F, rp, rt);
		this.addCompacting(ItemStacks.prismane, new ItemStack(RotaryCraft.compacts.itemID, this.getNumberPerStep(), 2), 0.2F, rp, rt);
		this.addCompacting(ItemStacks.lonsda, new ItemStack(Item.diamond.itemID, this.getNumberPerStep(), 0), 0.2F, rp, rt);

		this.addCompacting(Item.blazePowder, new ItemStack(Block.glowStone.blockID, 1, 0), 0.2F, 2000, 600);
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
		metaCompactingList.put(Arrays.asList(in.itemID, in.getItemDamage()), itemstack);
		//this.ExtractorExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(xp));
		compactables.add(in.copy());
		pressureMap.put(Arrays.asList(in.itemID, in.getItemDamage()), pressure);
		temperatureMap.put(Arrays.asList(in.itemID, in.getItemDamage()), temperature);
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
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", item.itemID, item.getItemDamage()));
		ItemStack ret = (ItemStack)metaCompactingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		return ret;
	}

	public boolean isCompactable(ItemStack item) {
		return this.getCompactingResult(item) != null;
	}

	public int getReqPressure(ItemStack item) {
		Integer ret = (Integer)pressureMap.get(Arrays.asList(item.itemID, item.getItemDamage()));
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
		Integer ret = (Integer)temperatureMap.get(Arrays.asList(item.itemID, item.getItemDamage()));
		return ret != null ? ret.intValue() : 0;
	}
}
