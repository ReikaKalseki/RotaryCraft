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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Instantiable.BasicModEntry;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceExponentiator;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ChanceManipulator;
import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ItemWithChance;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ReikaXPFluidHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.HarvestCraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicCropHandler.EssenceType;
import Reika.DragonAPI.ModInteract.ItemHandlers.OreBerryBushHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ThaumItemHelper;
import Reika.DragonAPI.ModInteract.RecipeHandlers.ForestryRecipeHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.CentrifugeManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.common.Loader;
import thaumcraft.api.aspects.Aspect;

public class RecipesCentrifuge extends RecipeHandler implements CentrifugeManager {

	private static final RecipesCentrifuge CentrifugeBase = new RecipesCentrifuge();

	private ItemHashMap<CentrifugeRecipe> recipeList = new ItemHashMap();

	private ArrayList<ItemStack> outputs = new ArrayList();

	public static final RecipesCentrifuge getRecipes() {
		return CentrifugeBase;
	}

	private RecipesCentrifuge() {
		super(MachineRegistry.CENTRIFUGE);
		RecipeInterface.centrifuge = this;

		this.addRecipe(Items.magma_cream, null, RecipeLevel.PERIPHERAL, Items.slime_ball, 100, Items.blaze_powder, 100);
		this.addRecipe(Items.melon, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.melon_seeds, 4), 100);
		this.addRecipe(Blocks.pumpkin, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.pumpkin_seeds, 12), 100);
		this.addRecipe(Items.wheat, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.wheat_seeds, 4), 100);
		this.addRecipe(Blocks.gravel, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.flint), 50, new ItemStack(Blocks.sand), 75);
		this.addRecipe(ItemStacks.netherrackdust, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.glowstone_dust), 25, new ItemStack(Items.gunpowder), 80, ExtractorModOres.getSmeltedIngot(ModOreList.SULFUR), 40);
		this.addRecipe(Blocks.dirt, null, RecipeLevel.PERIPHERAL, new ItemStack(Blocks.sand), 80, new ItemStack(Blocks.clay), 10, new ItemStack(Items.wheat_seeds), 2F, new ItemStack(Items.pumpkin_seeds), 0.125F, new ItemStack(Items.melon_seeds), 0.125F, new ItemStack(Blocks.sapling), 0.03125F, new ItemStack(Blocks.tallgrass, 1, 1), 0.0625F);
		this.addRecipe(Items.blaze_powder, null, RecipeLevel.PERIPHERAL, new ItemStack(Items.gunpowder), 100, ExtractorModOres.getSmeltedIngot(ModOreList.SULFUR), 75);

		this.addRecipe(ItemStacks.sludge, null, RecipeLevel.CORE, 2, new Object[]{ItemStacks.cleansludge, 80, ItemStacks.cleansludge, 20, ItemStacks.compost, 25});

		this.addRecipe(ItemStacks.slipperyComb, new FluidStack(FluidRegistry.getFluid("rc lubricant"), 50), 60, RecipeLevel.PROTECTED, ItemStacks.slipperyPropolis, 80);
		this.addRecipe(ItemStacks.slipperyPropolis, new FluidStack(FluidRegistry.getFluid("rc lubricant"), 150), 100, RecipeLevel.PROTECTED);

		int amt = ReikaMathLibrary.roundUpToX(10, (int)(DifficultyEffects.CANOLA.getAverageAmount()*0.75F));
		this.addRecipe(ItemStacks.canolaHusks, new FluidStack(FluidRegistry.getFluid("rc lubricant"), amt), 100, RecipeLevel.CORE);
	}

	public static class CentrifugeRecipe implements MachineRecipe {

		private final ItemStack in;
		public final int maxStack;
		private final ChancedOutputList out;
		private final FluidOut fluid;

		private CentrifugeRecipe(ItemStack is, ChancedOutputList li, FluidOut fs, int cycles) {
			in = is;
			out = li;
			fluid = fs;
			maxStack = cycles;
		}

		@Override
		public String getUniqueID() {
			return fullID(in)+">"+out.toString()+"&"+(fluid != null ? fluid.toString() : "X")+" {x"+maxStack+"}";
		}

		@Override
		public String getAllInfo() {
			return "Centrifuge "+fullID(in)+" to items["+out+"] and fluid "+fluid+"; can stack up to "+maxStack+"x";
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			ArrayList<ItemStack> li = new ArrayList(out.keySet());
			li.add(in);
			return li;
		}

		public FluidStack getFluid() {
			return fluid != null ? fluid.fluid.copy() : null;
		}

		public FluidStack rollFluid() {
			return fluid != null ? ReikaRandomHelper.doWithChance(fluid.chance) ? fluid.fluid.copy() : null : null;
		}

		public Collection<ItemWithChance> getItems() {
			return out.keySet();
		}

		public Collection<ItemStack> rollItems() {
			ArrayList<ItemStack> li = new ArrayList();
			for (ItemWithChance is : this.getItems()) {
				double ch = is.getNormalizedChance();
				while (ch >= 1) {
					li.add(is.getItem());
					ch -= 1.0D;
				}
				if (ReikaRandomHelper.doWithChance(ch)) {
					li.add(is.getItem());
				}
			}
			return li;
		}

		public ItemStack getInput() {
			return in.copy();
		}

		public float getFluidChance() {
			return fluid != null ? fluid.chance : 0;
		}

	}

	public void addRecipe(ItemStack in, ChancedOutputList out, FluidOut fs, RecipeLevel rl) {
		this.addRecipe(in, out, fs, rl, 1);
	}

	public void addRecipe(ItemStack in, ChancedOutputList out, FluidOut fs, RecipeLevel rl, int stack) {
		if (out.size() > 9)
			throw new IllegalArgumentException("Too many output items for "+in.getDisplayName()+" centrifuge recipe; only 9 inventory slots!");
		out.lock();
		for (ItemWithChance isout : out.keySet())
			if (!ReikaItemHelper.collectionContainsItemStack(outputs, isout.getItem()))
				outputs.add(isout.getItem());
		CentrifugeRecipe rec = new CentrifugeRecipe(in, out, fs, stack);
		recipeList.put(in, rec);
		this.onAddRecipe(rec, rl);
	}

	private void addRecipe(ItemStack in, FluidOut fs, RecipeLevel rl, int stack, Object... items) {
		this.addRecipe(in, ChancedOutputList.parseFromArray(true, items), fs, rl, stack);
	}

	private void addRecipe(ItemStack in, FluidOut fs, RecipeLevel rl, Object... items) {
		this.addRecipe(in, ChancedOutputList.parseFromArray(true, items), fs, rl);
	}

	public void addAPIRecipe(ItemStack item, FluidStack fs, float fc, Object... items) {
		this.addRecipe(item, fs != null ? new FluidOut(fs, fc) : null, RecipeLevel.API, items);
	}

	private void addRecipe(ItemStack item, FluidStack fs, float fc, RecipeLevel rl, Object... items) {
		this.addRecipe(item, new FluidOut(fs, fc), rl, items);
	}

	private void addRecipe(Block item, FluidOut fs, RecipeLevel rl, Object... items) {
		this.addRecipe(new ItemStack(item), fs, rl, items);
	}

	private void addRecipe(Item item, FluidOut fs, RecipeLevel rl, Object... items) {
		this.addRecipe(new ItemStack(item), fs, rl, items);
	}

	public CentrifugeRecipe getRecipeResult(ItemStack item) {
		CentrifugeRecipe cr = item != null ? recipeList.get(item) : null;
		return cr != null ? cr : null;
	}

	private Collection<ItemStack> getRecipeOutputs(ItemStack item) {
		CentrifugeRecipe cr = item != null ? recipeList.get(item) : null;
		return cr != null ? cr.out.itemSet() : null;
	}

	public ArrayList<ItemStack> getSources(ItemStack result) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack in : recipeList.keySet()) {
			Collection<ItemStack> out = this.getRecipeOutputs(in);
			if (ReikaItemHelper.collectionContainsItemStack(out, result))
				li.add(in.copy());
		}
		return li;
	}

	public ArrayList<ItemStack> getSources(Fluid result) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack in : recipeList.keySet()) {
			FluidOut fo = recipeList.get(in).fluid;
			if (fo != null && fo.fluid.getFluid().equals(result))
				li.add(in.copy());
		}
		return li;
	}

	public boolean isProduct(ItemStack result) {
		return ReikaItemHelper.collectionContainsItemStack(outputs, result);
	}

	public boolean isCentrifugable(ItemStack ingredient) {
		return this.getRecipeResult(ingredient) != null;
	}

	public Collection<ItemStack> getAllCentrifugables() {
		return Collections.unmodifiableCollection(recipeList.keySet());
	}

	@Override
	public void addPostLoadRecipes() {

		if (ModList.FORESTRY.isLoaded()) {
			for (ItemStack in : ForestryRecipeHelper.getInstance().getSqueezerRecipes()) {
				ImmutablePair<ChancedOutputList, FluidStack> out = ForestryRecipeHelper.getInstance().getSqueezerOutput(in);
				ChancedOutputList co = out.left != null ? out.left.copy() : null;
				if (co != null) {
					co.manipulateChances(new ChanceExponentiator(2.5));
					co.manipulateChances(new ChanceRounder());
				}
				else {
					co = new ChancedOutputList(false);
				}
				FluidStack fs = out.right;
				if (fs != null) {
					fs = fs.copy();
					fs.amount *= 1.25;
				}
				this.addRecipe(in, co, fs != null ? new FluidOut(fs, 100) : null, RecipeLevel.MODINTERACT);
			}

			for (ItemStack in : ForestryRecipeHelper.getInstance().getCentrifugeRecipes()) {
				ChancedOutputList out = ForestryRecipeHelper.getInstance().getCentrifugeOutput(in);
				out.manipulateChances(new ChanceExponentiator(3));
				out.manipulateChances(new ChanceRounder());
				this.addRecipe(in, out, null, RecipeLevel.MODINTERACT);
			}

			ItemStack drop = new ItemStack(ForestryHandler.ItemEntry.HONEY.getItem());
			ChancedOutputList out = new ChancedOutputList(true);
			out.addItem(new ItemStack(ForestryHandler.ItemEntry.PROPOLIS.getItem()), 25);
			this.addRecipe(drop, out, new FluidOut(new FluidStack(FluidRegistry.getFluid("for.honey"), 150), 100), RecipeLevel.MODINTERACT);

			drop = new ItemStack(ForestryHandler.ItemEntry.HONEYDEW.getItem());
			this.addRecipe(drop, new FluidStack(FluidRegistry.getFluid("for.honey"), 150), 100, RecipeLevel.MODINTERACT);

			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 0); //enchanting
			if (drop != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(30);
				this.addRecipe(drop, fs, 100, RecipeLevel.MODINTERACT, ItemStacks.lapisoreflakes, 2);
			}

			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 1); //intellect
			if (drop != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(15);
				this.addRecipe(drop, fs, 100, RecipeLevel.MODINTERACT);
			}

			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 2); //redstone
			if (drop != null) {
				Fluid f = FluidRegistry.getFluid("redstone");
				if (f != null) {
					this.addRecipe(drop, new FluidStack(f, 50), 100, RecipeLevel.MODINTERACT);
				}
			}

			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 3); //coal
			if (drop != null) {
				Fluid f = FluidRegistry.getFluid("coal");
				if (f != null) {
					this.addRecipe(drop, new FluidStack(f, 50), 100, RecipeLevel.MODINTERACT);
				}
			}

			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 4); //lux
			if (drop != null) {
				Fluid f = FluidRegistry.getFluid("glowstone");
				if (f != null) {
					this.addRecipe(drop, new FluidStack(f, 50), 100, RecipeLevel.MODINTERACT);
				}
			}

			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 5); //endearing
			if (drop != null) {
				Fluid f = FluidRegistry.getFluid("ender");
				if (f != null) {
					this.addRecipe(drop, new FluidStack(f, 50), 100, RecipeLevel.MODINTERACT);
				}
			}

			/* progression problem
			drop = ReikaItemHelper.lookupItem("MagicBees", "drop", 1); //intellect
			if (drop != null) {
				Fluid f = FluidRegistry.getFluid("chroma");
				if (f != null) {
					this.addRecipe(drop, new FluidStack(f, 20), 100, RecipeLevel.MODINTERACT);
				}
			}
			 */
		}

		if (ReikaItemHelper.oreItemsExist("dustLead", "dustSilver")) {
			ItemStack lead = OreDictionary.getOres("dustLead").get(0);
			ItemStack silver = OreDictionary.getOres("dustSilver").get(0);
			this.addRecipe(ExtractorModOres.getSmeltedIngot(ModOreList.GALENA), null, RecipeLevel.PERIPHERAL, lead, 100, silver, 100);
		}

		if (ModList.TINKERER.isLoaded()) {
			ItemStack berry = OreBerryBushHandler.BerryTypes.XP.getStack();
			if (berry != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(30);
				this.addRecipe(berry, fs, 100, RecipeLevel.MODINTERACT);
			}

			ItemStack chaff = ModList.IC2.isLoaded() && IC2Handler.IC2Stacks.BIOCHAFF.getItem() != null ? IC2Handler.IC2Stacks.BIOCHAFF.getItem() : ReikaItemHelper.tallgrass;
			ItemStack blueslime = ReikaItemHelper.lookupItem("TConstruct:strangeFood");
			this.addRecipe(ModWoodList.SLIME.getSaplingID(), null, RecipeLevel.MODINTERACT, new ItemStack(Items.slime_ball), 70, blueslime, 20, chaff, 100);
		}

		if (ModList.MAGICCROPS.isLoaded()) {
			//ItemStack drop = LegacyMagicCropHandler.getInstance().dropID != null ? new ItemStack(LegacyMagicCropHandler.getInstance().dropID) : null;
			//if (drop == null)
			ItemStack drop = EssenceType.XP.getEssence();
			if (drop != null && ReikaXPFluidHelper.fluidsExist()) {
				FluidStack fs = ReikaXPFluidHelper.getFluid(5);
				this.addRecipe(drop, fs, 100, RecipeLevel.MODINTERACT);
			}
		}

		if (Loader.isModLoaded("Automagy")) {
			ItemStack phial = ReikaItemHelper.lookupItem("Automagy:phialExtra");
			if (phial != null && ReikaXPFluidHelper.fluidsExist()) {
				try {
					Class c = phial.getItem().getClass();
					Method m = c.getMethod("getXPValue");
					int val = (int)m.invoke(phial.getItem());
					FluidStack fs = ReikaXPFluidHelper.getFluid(val);
					ChancedOutputList co = new ChancedOutputList(false);
					co.addItem(ThaumItemHelper.ItemEntry.PHIAL.getItem(), 100);
					co.addItem(ThaumItemHelper.getCrystallizedEssentia(Aspect.WATER), 10);
					co.addItem(ThaumItemHelper.getCrystallizedEssentia(Aspect.ORDER), 10);
					co.addItem(ThaumItemHelper.getCrystallizedEssentia(Aspect.MAGIC), 5);
					co.addItem(ThaumItemHelper.getCrystallizedEssentia(Aspect.MIND), 4);
					co.addItem(ThaumItemHelper.getCrystallizedEssentia(Aspect.CRAFT), 2);
					this.addRecipe(phial, co, new FluidOut(fs, 100), RecipeLevel.MODINTERACT);
				}
				catch (Exception e) {
					RotaryCraft.logger.logError("Could not determine XP value of enchanting phial!");
					ReflectiveFailureTracker.instance.logModReflectiveFailure(new BasicModEntry("Automagy"), e);
				}
			}
		}

		ItemStack is = ModList.IC2.isLoaded() && IC2Handler.IC2Stacks.BIOCHAFF.getItem() != null ? IC2Handler.IC2Stacks.BIOCHAFF.getItem() : ReikaItemHelper.tallgrass;
		this.addRecipe(new ItemStack(Blocks.clay), new FluidStack(FluidRegistry.WATER, 20), 40, RecipeLevel.PERIPHERAL, new ItemStack(Blocks.dirt), 100, ItemStacks.silicondust, 75, ItemStacks.ironoreflakes, 0.5F, ItemStacks.goldoreflakes, 0.2F, is, 2.5F);

		this.addGrassToSeeds();
	}

	private void addGrassToSeeds() {
		ChancedOutputList c = new ChancedOutputList(false);
		ItemHashMap<Integer> weights = new ItemHashMap();
		float total = 0;
		List<WeightedRandom.Item> li = (List<net.minecraft.util.WeightedRandom.Item>)ReikaObfuscationHelper.get("seedList", null);
		for (WeightedRandom.Item wi : li) {
			ItemStack seed = (ItemStack)ReikaObfuscationHelper.get("seed", wi);
			if (HarvestCraftHandler.getInstance().isSeedItem(seed)) //pollutes with 113435948745609 seeds
				continue;
			if (weights.size() >= (weights.containsKey(ItemRegistry.CANOLA.getItemInstance(), 0) ? 9 : 8))
				continue;
			total += wi.itemWeight;
			weights.put(seed, wi.itemWeight);
		}
		for (ItemStack is : weights.keySet()) {
			int wt = weights.get(is);
			c.addItem(is, ItemRegistry.CANOLA.matchItem(is) ? 10 : wt/total);
		}
		this.addRecipe(ReikaItemHelper.tallgrass, c, null, RecipeLevel.PERIPHERAL);
		this.addRecipe(ReikaItemHelper.fern, c, null, RecipeLevel.PERIPHERAL);
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipeList.removeValue((CentrifugeRecipe)recipe);
	}

	private static class ChanceRounder implements ChanceManipulator {

		private ChanceRounder() {

		}

		@Override
		public float getChance(float original) { //round up to nearest 0.5F
			return Math.round(2D*original)/2F;
		}
	}

	private static class FluidOut {

		private final FluidStack fluid;
		private final float chance;

		private FluidOut(FluidStack fs, float c) {
			fluid = fs;
			chance = c;
		}

		public int getRandomAmount(Random r) {
			return (int)(r.nextFloat()*fluid.amount);
		}

		public int getAmount() {
			return fluid.amount;
		}

		@Override
		public final String toString() {
			return fluid.amount+" mB of "+fluid.getFluid().getName()+" ("+chance+"%)";
		}

	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), null, false);
		FluidOut fo = null;
		ChancedOutputList co = new ChancedOutputList(false);
		if (lb.hasChild("output_fluid")) {
			LuaBlock fluid = lb.getChild("output_fluid");
			String s = fluid.getString("type");
			Fluid f = FluidRegistry.getFluid(s);
			if (f == null)
				throw new IllegalArgumentException("Fluid '"+s+"' does not exist!");
			this.verifyOutputFluid(f);
			FluidStack fs = new FluidStack(f, fluid.getInt("amount"));
			fo = new FluidOut(fs, (float)fluid.getDouble("chance"));
		}
		if (lb.hasChild("output_items")) {
			LuaBlock items = lb.getChild("output_items");
			for (LuaBlock entry : items.getChildren()) {
				ItemStack is = crl.parseItemString(entry.getString("item"), entry.getChild("nbt"), true);
				this.verifyOutputItem(is);
				if (is != null) {
					co.addItem(is, (float)entry.getDouble("chance"));
				}
			}
		}
		this.addRecipe(in, co, fo, RecipeLevel.CUSTOM);
		return true;
	}
}
