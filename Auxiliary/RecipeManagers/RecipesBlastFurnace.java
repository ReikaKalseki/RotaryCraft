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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Instantiable.Recipe.FlexibleIngredient;
import Reika.DragonAPI.Instantiable.Recipe.FlexibleIngredient.IngredientIDHandler;
import Reika.DragonAPI.Instantiable.Recipe.RecipePattern;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.BlastFurnaceManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RecipesBlastFurnace extends RecipeHandler implements BlastFurnaceManager {

	private static final RecipesBlastFurnace BlastFurnaceBase = new RecipesBlastFurnace();

	private final ArrayList<BlastRecipe> recipeList = new ArrayList();
	private final ArrayList<BlastCrafting> craftingList = new ArrayList();

	private final ItemHashMap<HashSet<Integer>> itemInputTypes = new ItemHashMap();

	private RecipesBlastFurnace() {
		super(MachineRegistry.BLASTFURNACE);
		RecipeInterface.blastfurn = this;

		FlexibleIngredient in1 = new FlexibleIngredient(new ItemStack(Items.coal), 100, 1);
		FlexibleIngredient in2 = new FlexibleIngredient(Items.gunpowder, 3.6F, 1);
		FlexibleIngredient in3 = new FlexibleIngredient(Blocks.sand, 0.2F, 1);
		BlastRecipe hsla = new BlastRecipe(in1, in2, in3, Items.iron_ingot, ItemStacks.steelingot, 0, TileEntityBlastFurnace.SMELT_XP, TileEntityBlastFurnace.SMELTTEMP);
		this.addRecipe(hsla, RecipeLevel.CORE);

		in1 = new FlexibleIngredient(new ItemStack(Items.coal, 1, 1), 100, 1);
		in2 = new FlexibleIngredient(Items.gunpowder, 3.2F, 1);
		in3 = new FlexibleIngredient(Blocks.sand, 0.2F, 1);
		BlastRecipe hsla1b = new BlastRecipe(in1, in2, in3, Items.iron_ingot, ItemStacks.steelingot, 0, TileEntityBlastFurnace.SMELT_XP, TileEntityBlastFurnace.SMELTTEMP);
		this.addRecipe(hsla1b, RecipeLevel.CORE);

		in1 = new FlexibleIngredient(ItemStacks.coke, 100, 1);
		in2 = new FlexibleIngredient(Items.gunpowder, 1.8F, 1);
		in3 = new FlexibleIngredient(Blocks.sand, 0.1F, 1);
		BlastRecipe hsla2 = new BlastRecipe(in1, in2, in3, Items.iron_ingot, ItemStacks.steelingot, 1, TileEntityBlastFurnace.SMELT_XP, TileEntityBlastFurnace.SMELTTEMP);
		this.addRecipe(hsla2, RecipeLevel.CORE);

		in1 = new FlexibleIngredient(ItemStacks.coke, 100, 1);
		in2 = new FlexibleIngredient(Items.gunpowder, 1.8F*9, 1);
		in3 = new FlexibleIngredient(Blocks.sand, 0.1F*9, 1);
		BlastRecipe hslablock = new BlastRecipe(in1, in2, in3, Blocks.iron_block, ItemStacks.steelblock, 0, TileEntityBlastFurnace.SMELT_XP*9, TileEntityBlastFurnace.SMELTTEMP);
		hslablock.setNeedsEmpty();
		this.addRecipe(hslablock, RecipeLevel.CORE);

		in1 = new FlexibleIngredient(ItemStacks.bedrockdust, 100, 4);
		in2 = FlexibleIngredient.EMPTY;
		in3 = FlexibleIngredient.EMPTY;
		BlastRecipe bedrock = new BlastRecipe(in1, in2, in3, ItemStacks.steelingot, 1, ItemStacks.bedingot, 0, 0, TileEntityBlastFurnace.BEDROCKTEMP);
		this.addRecipe(bedrock.setAlloy(), RecipeLevel.CORE);

		in1 = FlexibleIngredient.EMPTY;
		in2 = FlexibleIngredient.EMPTY;
		in3 = FlexibleIngredient.EMPTY;
		BlastRecipe scrap = new BlastRecipe(in1, in2, in3, ItemStacks.scrap, 9, ItemStacks.steelingot, 0, 0, TileEntityBlastFurnace.SMELTTEMP);
		this.addRecipe(scrap, RecipeLevel.PROTECTED);

		in1 = FlexibleIngredient.EMPTY;
		in2 = FlexibleIngredient.EMPTY;
		in3 = FlexibleIngredient.EMPTY;
		BlastRecipe coke = new BlastRecipe(in1, in2, in3, new ItemStack(Items.coal), ItemStacks.coke, 0, 0, 400);
		this.addRecipe(coke, RecipeLevel.CORE);

		in1 = ConfigRegistry.OREALUDUST.getState() ? new FlexibleIngredient("dustAluminum", 25F, 1) : new FlexibleIngredient(ItemStacks.aluminumpowder, 25F, 1);
		in2 = new FlexibleIngredient(Items.blaze_powder, 2.5F, 1);
		in3 = FlexibleIngredient.EMPTY;
		BlastRecipe sili = new BlastRecipe(in1, in2, in3, Blocks.sand, ItemStacks.silicondust, 0.8F, 0, 700);
		this.addRecipe(sili, RecipeLevel.CORE);

		in1 = new FlexibleIngredient(ItemStacks.coke, 75F, 1);
		in2 = new FlexibleIngredient(Items.redstone, 40F, 1);
		in3 = FlexibleIngredient.EMPTY;
		BlastRecipe spring = new BlastRecipe(in1, in2, in3, ItemStacks.steelingot, ItemStacks.springingot, 0, 0, 1150);
		this.addRecipe(spring, RecipeLevel.CORE);

		in1 = new FlexibleIngredient(ItemStacks.silicondust, 20F, 1);
		in2 = FlexibleIngredient.EMPTY;
		in3 = FlexibleIngredient.EMPTY;
		BlastRecipe silu = new BlastRecipe(in1, in2, in3, "ingotAluminum", ItemStacks.silumin, 0, 0, 900);
		this.addRecipe(silu, RecipeLevel.CORE);
	}

	private void addRecipe(BlastRecipe br, RecipeLevel rl) {
		recipeList.add(br);
		itemInputTypes.clear();
		this.onAddRecipe(br, rl);
	}

	public static final RecipesBlastFurnace getRecipes() {
		return BlastFurnaceBase;
	}

	public void addRecipe(ItemStack out, int temperature, IRecipe in, int speed, float xp) {
		BlastCrafting c = new BlastCrafting(out, temperature, speed, in, xp);
		this.addCrafting(c, RecipeLevel.CORE);
	}

	public void addAlloyingRecipe(ItemStack out, int temperature, IRecipe in, int speed, float xp) {
		BlastCrafting c = new BlastCrafting(out, temperature, speed, in, xp).setAlloying();
		this.addCrafting(c, RecipeLevel.CORE);
	}

	public void add3x3AlloyingRecipe(ItemStack out, int temperature, IRecipe in, int speed, float xp) {
		ShapedRecipes r = ReikaRecipeHelper.getShapedRecipeFor(out, in);
		BlastCrafting c = new BlastCrafting(out, temperature, speed, r, xp).setAlloying();
		this.addCrafting(c, RecipeLevel.CORE);
	}

	public void add3x3Crafting(ItemStack out, int temperature, int speed, float xp, Object... in) {
		ShapedRecipes r = ReikaRecipeHelper.getShapedRecipeFor(out, in);
		BlastCrafting c = new BlastCrafting(out, temperature, speed, r, xp);
		this.addCrafting(c, RecipeLevel.CORE);
	}

	private void addCrafting(BlastCrafting cr, RecipeLevel rl) {
		craftingList.add(cr);
		itemInputTypes.clear();
		this.onAddRecipe(cr, rl);
	}

	public static final class BlastCrafting implements BlastFurnacePattern {

		public final int temperature;
		private final IRecipe recipe;
		private final ItemStack output;
		public final int speed;
		public final float xp;
		private boolean alloy;

		/*
		public BlastCrafting(int width, int height, ItemStack[] input, ItemStack out, int temp) {
			super(width, height, input, out);
			temperature = temp;
			output = out.copy();
		}*/

		private BlastCrafting(ItemStack out, int temp, int speed, IRecipe ir, float xp) {
			recipe = ir;
			output = out;
			temperature = temp;
			this.speed = speed;
			this.xp = xp;
		}

		private BlastCrafting setAlloying() {
			alloy = true;
			return this;
		}

		public final ItemStack outputItem() {
			return output.copy();
		}

		public BlastCrafting copy() {
			//return new BlastCrafting(recipeWidth, recipeHeight, recipeItems, output, temperature);
			BlastCrafting bc = new BlastCrafting(output, temperature, speed, recipe, xp);
			bc.alloy = alloy;
			return bc;
		}

		public boolean matches(InventoryCrafting ic, int temperature) {
			return temperature >= this.temperature && recipe.matches(ic, null);
		}

		public boolean usesItem(ItemStack is) {
			return ReikaItemHelper.collectionContainsItemStack(ReikaRecipeHelper.getAllItemsInRecipe(recipe), is);
		}

		@SideOnly(Side.CLIENT)
		public ItemStack[] getArrayForDisplay() {
			return ReikaRecipeHelper.getPermutedRecipeArray(recipe);
		}

		@Override
		public boolean isValidInputForSlot(int slot, ItemStack is) {/*
			if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe) {
				ArrayList<ItemStack> li = ReikaRecipeHelper.getAllItemsInRecipe(recipe);
			}
			else if (recipe instanceof ShapedOreRecipe || recipe instanceof ShapedRecipes) {

			}*/
			if (slot == TileEntityBlastFurnace.CENTER_ADDITIVE || slot > 9)
				return false;
			return ReikaRecipeHelper.getRecipeLocationIndices(recipe, is).contains(slot-1);
		}

		@Override
		public float getXPPerProduct() {
			return xp;
		}

		public boolean isAlloying() {
			return alloy;
		}

		public int getRequiredTemperature() {
			return temperature;
		}

		@Override
		public String getUniqueID() {
			return "CRAFT/"+recipe.getClass().getName()+"^"+ReikaRecipeHelper.toDeterministicString(recipe)+">"+output;
		}

		@Override
		public String getAllInfo() {
			return "Crafting "+output+", Recipe="+ReikaRecipeHelper.toString(recipe)+" @ "+temperature+"C; xp="+xp+", speed="+speed+", alloy:"+alloy;
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			ArrayList<ItemStack> li = new ArrayList(ReikaRecipeHelper.getAllItemsInRecipe(recipe));
			li.add(output);
			return li;
		}

		@Override
		public boolean requiresEmptyOutput() {
			return false;
		}

		public boolean usesSlot(int i) {
			List<ItemStack>[] arr = ReikaRecipeHelper.getRecipeArray(recipe);
			return arr != null && arr[i] != null;
		}
	}

	public static interface BlastFurnacePattern extends MachineRecipe {

		public ItemStack outputItem();

		public boolean isValidInputForSlot(int slot, ItemStack is);

		public float getXPPerProduct();

		public boolean isAlloying();

		public int getRequiredTemperature();

		public boolean requiresEmptyOutput();

		public boolean usesSlot(int slot);
	}

	public static final class BlastRecipe implements BlastFurnacePattern, IngredientIDHandler {

		public final FlexibleIngredient primary;
		public final FlexibleIngredient secondary;
		public final FlexibleIngredient tertiary;

		private final FlexibleIngredient main;

		private boolean matchNumberExactly;
		private final ItemStack output;
		public final float bonusYield;
		public final float xp;
		public final int temperature;

		private boolean alloy;
		private boolean needsEmpty;

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, Item main, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, new ItemStack(main), out, bonus, xp, temp);
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, Block main, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, new ItemStack(main), out, bonus, xp, temp);
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, Collection<ItemStack> main, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, main, 1, out, bonus, xp, temp);
			matchNumberExactly = false;
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, String main, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, main, 1, out, bonus, xp, temp);
			matchNumberExactly = false;
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, ItemStack main, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, main, 1, out, bonus, xp, temp);
			matchNumberExactly = false;
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, ItemStack main, int req, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, ReikaJavaLibrary.makeListFrom(main), req, out, bonus, xp, temp);
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, String main, int req, ItemStack out, float bonus, float xp, int temp) {
			this(in1, in2, in3, OreDictionary.getOres(main), req, out, bonus, xp, temp);
		}

		private BlastRecipe(FlexibleIngredient in1, FlexibleIngredient in2, FlexibleIngredient in3, Collection<ItemStack> main, int req, ItemStack out, float bonus, float xp, int temp) {
			primary = in1.lock();
			secondary = in2.lock();
			tertiary = in3.lock();
			bonusYield = bonus;

			this.xp = xp;
			output = out;
			matchNumberExactly = true;
			temperature = temp;

			if (main.isEmpty()) {
				throw new IllegalArgumentException("Empty item list for main item in Blast Recipe "+this.toString());
			}
			this.main = new FlexibleIngredient(main, 100, req).lock();
		}

		private BlastRecipe setAlloy() {
			alloy = true;
			return this;
		}

		private BlastRecipe setNeedsEmpty() {
			needsEmpty = true;
			return this;
		}

		@SideOnly(Side.CLIENT)
		public ItemStack mainItemForDisplay() {
			return main.getItemForDisplay();
		}

		public ItemStack outputItem() {
			return output != null ? output.copy() : null;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(primary+" + ");
			sb.append(secondary+" + ");
			sb.append(tertiary+" + ");
			sb.append(matchNumberExactly ? "== " : ">= ");
			sb.append(main.numberToUse+" of ");
			sb.append(main);
			sb.append(" >> ");
			sb.append(output);
			if (bonusYield > 0)
				sb.append("*"+bonusYield);
			return sb.toString();
		}

		public int getNumberProduced(int main) {
			return this.main.numberToUse > 1 ? main/this.main.numberToUse : main;
		}

		public boolean matchInputExactly() {
			return matchNumberExactly;
		}

		public ArrayList<Integer> getValidInputNumbers() {
			ArrayList<Integer> li = new ArrayList();
			for (int i = main.numberToUse; i <= 9; i += main.numberToUse) {
				if (i == main.numberToUse || !this.matchInputExactly())
					li.add(i);
			}
			return li;
		}

		@Override
		public boolean isValidInputForSlot(int slot, ItemStack is) {
			if (slot == TileEntityBlastFurnace.CENTER_ADDITIVE)
				return primary.match(is);
			if (slot == TileEntityBlastFurnace.LOWER_ADDITIVE)
				return secondary.match(is);
			if (slot == TileEntityBlastFurnace.UPPER_ADDITIVE)
				return tertiary.match(is);
			if (slot >= 1 && slot < 10)
				return this.isValidMainItem(is);
			return false;
		}

		public boolean isValidMainItem(ItemStack is) {
			return main.match(is);
		}

		public Collection<ItemStack> getMainItems() {
			ArrayList<ItemStack> c = new ArrayList();
			for (ItemStack ks : main.getItems()) {
				c.add(ks);
			}
			return c;
		}

		@Override
		public float getXPPerProduct() {
			return xp;
		}

		public boolean isAlloying() {
			return alloy;
		}

		public int getRequiredTemperature() {
			return temperature;
		}

		@Override
		public String getUniqueID() {
			return "RECIPE/"+primary.fullID(this)+"&"+secondary.fullID(this)+"&"+tertiary.fullID(this)+">"+main.fullID(this)+"^"+fullID(output)+"?"+bonusYield;
		}

		@Override
		public String getAllInfo() {
			return "Mainline production, "+this.toString();
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			ArrayList<ItemStack> li = new ArrayList();
			if (primary != null)
				li.addAll(primary.getItems());
			if (secondary != null)
				li.addAll(secondary.getItems());
			if (tertiary != null)
				li.addAll(tertiary.getItems());
			li.addAll(this.getMainItems());
			li.add(output);
			return li;
		}

		@Override
		public boolean requiresEmptyOutput() {
			return needsEmpty;
		}

		public boolean usesSlot(int i) {
			return true;
		}

		public final String fullIDForItems(Collection<KeyedItemStack> c) {
			return RecipeHandler.fullIDKeys(c);
		}
	}

	public BlastCrafting getCrafting(ItemStack[] main, int temp) {
		RecipePattern ic = new RecipePattern(main);
		for (int i = 0; i < craftingList.size(); i++) {
			BlastCrafting c = craftingList.get(i);
			if (c.matches(ic, temp)) {
				return c;
			}
		}
		return null;
	}

	public BlastRecipe getRecipe(ItemStack in1, ItemStack in2, ItemStack in3, ItemStack[] main, int temp) {
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (temp >= r.temperature && this.matchRecipe(r, in1, in2, in3, main)) //allows for two recipes with same items but diff temp
				return r;
		}
		return null;
	}

	private boolean matchRecipe(BlastRecipe r, ItemStack in1, ItemStack in2, ItemStack in3, ItemStack[] main) {
		if (!r.primary.match(in1))
			return false;
		if (!r.secondary.match(in2))
			return false;
		if (!r.tertiary.match(in3))
			return false;
		int num = 0;
		for (int i = 0; i < main.length; i++) {
			ItemStack is = main[i];
			if (is != null) {
				if (r.isValidMainItem(is)) {
					num++;
				}
				else {
					return false;
				}
			}
		}
		return r.matchNumberExactly ? num == r.main.numberToUse : num >= r.main.numberToUse;
	}

	public boolean isProduct(ItemStack result) {
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(result, r.outputItem()))
				return true;
		}
		return false;
	}

	public boolean isInput(ItemStack ingredient) {
		return !this.getInputTypesForItem(ingredient).isEmpty();
	}

	public Set<Integer> getInputTypesForItem(ItemStack is) {
		HashSet<Integer> set = itemInputTypes.get(is);
		if (set == null) {
			set = new HashSet();
			for (int i = 0; i < recipeList.size(); i++) {
				BlastRecipe r = recipeList.get(i);
				if (r.isValidMainItem(is))
					set.add(0);
				else if (r.primary.match(is))
					set.add(1);
				else if (r.secondary.match(is))
					set.add(2);
				else if (r.tertiary.match(is))
					set.add(3);
			}
			itemInputTypes.put(is, set);
		}
		return Collections.unmodifiableSet(set);
	}

	public ArrayList<BlastRecipe> getAllRecipesUsing(ItemStack is) {
		ArrayList<BlastRecipe> li = new ArrayList();
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (r.isValidMainItem(is))
				li.add(r);
			if (r.primary.match(is))
				li.add(r);
			if (r.secondary.match(is))
				li.add(r);
			if (r.tertiary.match(is))
				li.add(r);
		}
		return li;
	}

	public ArrayList<BlastRecipe> getAllRecipesMaking(ItemStack is) {
		ArrayList<BlastRecipe> li = new ArrayList();
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(is, r.outputItem()))
				li.add(r);
		}
		return li;
	}

	public ArrayList<BlastCrafting> getAllCraftingUsing(ItemStack is) {
		ArrayList<BlastCrafting> li = new ArrayList();
		for (int i = 0; i < craftingList.size(); i++) {
			BlastCrafting r = craftingList.get(i);
			if (r.usesItem(is)) {
				li.add(r.copy());
			}
			/*
			for (int k = 0; k < r.recipeItems.length; k++) {
				if (ReikaItemHelper.matchStacks(is, r.recipeItems[k])) {
					li.add(r.copy());
					k = r.recipeItems.length;
				}
			}
			 */
		}
		return li;
	}

	public ArrayList<BlastCrafting> getAllCraftingMaking(ItemStack is) {
		ArrayList<BlastCrafting> li = new ArrayList();
		for (int i = 0; i < craftingList.size(); i++) {
			BlastCrafting r = craftingList.get(i);
			//ReikaJavaLibrary.pConsole(r.output.getDisplayName());
			if (ReikaItemHelper.matchStacks(is, r.outputItem())) {
				li.add(r.copy());
			}
		}
		return li;
	}

	public ArrayList<BlastFurnacePattern> getAllAlloyingRecipes() {
		ArrayList<BlastFurnacePattern> li = new ArrayList();
		for (BlastRecipe br : recipeList) {
			if (br.isAlloying()) {
				li.add(br);
			}
		}
		for (BlastCrafting bc : craftingList) {
			if (bc.isAlloying()) {
				li.add(bc);
			}
		}
		return li;
	}

	public Collection<BlastRecipe> getAllMainlineRecipes() {
		return Collections.unmodifiableCollection(recipeList);
	}

	public Collection<BlastCrafting> getAllCraftingRecipes() {
		return Collections.unmodifiableCollection(craftingList);
	}

	public Collection<BlastFurnacePattern> getAllRecipes() {
		Collection<BlastFurnacePattern> c = new ArrayList();
		c.addAll(recipeList);
		c.addAll(craftingList);
		return c;
	}

	@Override
	public void addAPIAlloying(Collection<ItemStack> in1, float c1, int decr1, Collection<ItemStack> in2, float c2, int decr2, Collection<ItemStack> in3, float c3, int decr3, ItemStack main, ItemStack out, int req, float bonus, float xp, int temp) {
		FlexibleIngredient b1 = new FlexibleIngredient(in1, in1 != null ? c1 : 0, decr1);
		FlexibleIngredient b2 = new FlexibleIngredient(in2, in2 != null ? c2 : 0, decr2);
		FlexibleIngredient b3 = new FlexibleIngredient(in3, in3 != null ? c3 : 0, decr3);
		BlastRecipe br = req > 0 ? new BlastRecipe(b1, b2, b3, main, req, out, bonus, xp, temp) : new BlastRecipe(b1, b2, b3, main, out, bonus, xp, temp);
		this.addRecipe(br, RecipeLevel.API);
	}

	@Override
	public void addAPIRecipe(ItemStack out, int temperature, IRecipe in, int speed, float xp) {
		BlastCrafting bc = new BlastCrafting(out, temperature, speed, in, xp);
		this.addCrafting(bc, RecipeLevel.API);
	}

	@Override
	public void addPostLoadRecipes() {

	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		itemInputTypes.clear();
		return recipeList.remove(recipe) | craftingList.remove(recipe);
	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		this.verifyOutputItem(out);
		Collection<ItemStack> main = crl.parseItemCollection(lb.getChild("main_item").getDataValues(), true);
		if (main.isEmpty())
			throw new IllegalArgumentException("You need at least one main item!");
		int req = lb.getInt("required_main_item");
		boolean match = lb.getBoolean("match_number_exactly");
		boolean bonus_basic = lb.getBoolean("has_bonus");
		float bonus = (float)lb.getDouble("bonus_yield");
		if (bonus <= 0 && bonus_basic)
			bonus = 1;
		float xp = (float)lb.getDouble("xp");
		int temp = lb.getInt("temperature");
		if (temp > TileEntityBlastFurnace.MAXTEMP)
			throw new IllegalArgumentException("Recipe temperature exceeds max temperature ("+TileEntityBlastFurnace.MAXTEMP+")!");
		boolean alloy = lb.getBoolean("alloying");
		LuaBlock first = lb.getChild("primary_input");
		LuaBlock second = lb.hasChild("secondary_input") ? lb.getChild("secondary_input") : null;
		LuaBlock third = lb.hasChild("tertiary_input") ? lb.getChild("tertiary_input") : null;
		if (first == null || (second == null && third != null)) {
			throw new IllegalArgumentException("Secondary inputs must be specified sequentially: Primary-secondary-tertiary!");
		}
		BlastRecipe br = new BlastRecipe(FlexibleIngredient.parseLua(crl, first, false), FlexibleIngredient.parseLua(crl, second, false), FlexibleIngredient.parseLua(crl, third, false), main, req, out, bonus, xp, temp);
		br.matchNumberExactly = match;
		if (alloy)
			br.setAlloy();
		this.addRecipe(br, RecipeLevel.CUSTOM);
		return true;
	}
}
