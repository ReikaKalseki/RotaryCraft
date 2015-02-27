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
import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;

public class RecipesCompactor
{
	private static final RecipesCompactor CompactorBase = new RecipesCompactor();

	private ItemHashMap<CompactingRecipe> recipes = new ItemHashMap().setOneWay();

	public static final RecipesCompactor getRecipes()
	{
		return CompactorBase;
	}

	private RecipesCompactor()
	{
		int rp = TileEntityCompactor.REQPRESS;
		int rt = TileEntityCompactor.REQTEMP;
		this.addCompacting(Items.coal, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 0), rp, rt); //No charcoal
		this.addCompacting(ItemStacks.anthracite, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 1), rp, rt);
		this.addCompacting(ItemStacks.prismane, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 2), rp, rt);
		this.addCompacting(ItemStacks.lonsda, new ItemStack(Items.diamond, this.getNumberPerStep(), 0), rp, rt);

		this.addCompacting(Items.blaze_powder, new ItemStack(Blocks.glowstone, 1, 0), 2000, 600);
	}

	private static class CompactingRecipe {

		private final ItemStack in;
		private final ItemStack out;
		private final int temperature;
		private final int pressure;

		private CompactingRecipe(ItemStack is, ItemStack is2, int t, int p) {
			in = is;
			out = is2;
			temperature = t;
			pressure = p;
		}

	}

	public final int getNumberPerStep() {
		return DifficultyEffects.COMPACTOR.getInt();
	}

	public void addCompacting(ItemStack in, ItemStack itemstack, int pressure, int temperature)
	{
		recipes.put(in, new CompactingRecipe(in, itemstack, temperature, pressure));
	}

	public void addCompacting(Item in, ItemStack itemstack, int pressure, int temperature)
	{
		this.addCompacting(new ItemStack(in), itemstack, pressure, temperature);
	}

	public void addCompacting(Block in, ItemStack itemstack, int pressure, int temperature)
	{
		this.addCompacting(new ItemStack(in), itemstack, pressure, temperature);
	}

	public ItemStack getCompactingResult(ItemStack item)
	{
		if (item == null)
			return null;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d", Items.itemID, item.getItemDamage()));
		CompactingRecipe ret = recipes.get(item);
		return ret != null ? ret.out.copy() : null;
	}

	public boolean isCompactable(ItemStack item) {
		return this.getCompactingResult(item) != null;
	}

	public int getReqPressure(ItemStack item) {
		CompactingRecipe ret = recipes.get(item);
		return ret != null ? ret.pressure : 0;
	}

	public ItemStack getSource(ItemStack result) {
		for (ItemStack in : recipes.keySet()) {
			ItemStack out = this.getCompactingResult(in);
			if (ReikaItemHelper.matchStacks(result, out))
				return in.copy();
		}
		return null;
	}

	public int getReqTemperature(ItemStack item) {
		CompactingRecipe ret = recipes.get(item);
		return ret != null ? ret.temperature : 0;
	}

	public Collection<ItemStack> getAllCompactables() {
		return Collections.unmodifiableCollection(recipes.keySet());
	}
}
