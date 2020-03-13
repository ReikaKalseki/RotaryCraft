/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.CompactorManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;

public class RecipesCompactor extends RecipeHandler implements CompactorManager
{
	private static final RecipesCompactor CompactorBase = new RecipesCompactor();

	private ItemHashMap<CompactingRecipe> recipes = new ItemHashMap();

	public static final RecipesCompactor getRecipes()
	{
		return CompactorBase;
	}

	private RecipesCompactor() {
		super(MachineRegistry.COMPACTOR);
		RecipeInterface.compactor = this;

		int rp = TileEntityCompactor.REQPRESS;
		int rt = TileEntityCompactor.REQTEMP;
		this.addRecipe(new ItemStack(Items.coal), ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 0), rp, rt, RecipeLevel.CORE);
		this.addRecipe(new ItemStack(Items.coal, 1, 1), ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep()*3/2, 0), rp, rt, RecipeLevel.CORE);
		this.addRecipe(ItemStacks.anthracite, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 1), rp, rt, RecipeLevel.CORE);
		this.addRecipe(ItemStacks.prismane, ItemRegistry.COMPACTS.getCraftedMetadataProduct(this.getNumberPerStep(), 2), rp, rt, RecipeLevel.CORE);
		this.addRecipe(ItemStacks.lonsda, new ItemStack(Items.diamond, this.getNumberPerStep(), 0), rp, rt, RecipeLevel.CORE);

		this.addRecipe(new ItemStack(Items.blaze_powder), new ItemStack(Blocks.glowstone, 1, 0), 2000, 600, RecipeLevel.PERIPHERAL);

		this.addRecipe(new ItemStack(Blocks.ice), new ItemStack(Blocks.packed_ice), 24000, -80, RecipeLevel.PERIPHERAL);
	}

	private static class CompactingRecipe implements MachineRecipe {

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

		@Override
		public String getUniqueID() {
			return fullID(in)+">"+fullID(out)+"@"+temperature+"&"+pressure;
		}

		@Override
		public String getAllInfo() {
			return "Compacting "+fullID(in)+" to "+fullID(out)+" @ "+temperature+"C & "+pressure+" kPa";
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(in, out);
		}

	}

	public final int getNumberPerStep() {
		return DifficultyEffects.COMPACTOR.getInt();
	}

	public void addAPIRecipe(ItemStack in, ItemStack itemstack, int pressure, int temperature) {
		this.addRecipe(in, itemstack, pressure, temperature, RecipeLevel.API);
	}

	public void addRecipe(ItemStack in, ItemStack itemstack, int pressure, int temperature) {
		this.addRecipe(in, itemstack, pressure, temperature, RecipeLevel.CORE);
	}

	private void addRecipe(ItemStack in, ItemStack itemstack, int pressure, int temperature, RecipeLevel rl) {
		CompactingRecipe rec = new CompactingRecipe(in, itemstack, temperature, pressure);
		recipes.put(in, rec);
		this.onAddRecipe(rec, rl);
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

	@Override
	public void addPostLoadRecipes() {
		if (ModList.IC2.isLoaded()) {
			ItemStack plantball = ReikaItemHelper.lookupItem("IC2:itemFuelPlantBall");
			ItemStack chaff = IC2Handler.IC2Stacks.BIOCHAFF.getItem();
			if (chaff != null && plantball != null) {
				this.addRecipe(chaff, ReikaItemHelper.getSizedItemStack(plantball, 4), 500, 0, RecipeLevel.MODINTERACT);
			}
		}
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipes.removeValue((CompactingRecipe)recipe);
	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), lb.getChild("input_nbt"), false);
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		this.verifyOutputItem(out);
		this.addRecipe(in, out, lb.getInt("pressure"), lb.getInt("temperature"), RecipeLevel.CUSTOM);
		return true;
	}
}
