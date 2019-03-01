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

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaThermoHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.MagicCropHandler;
import Reika.GeoStrata.Registry.GeoBlocks;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.RockMelterManager;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesLavaMaker extends RecipeHandler implements RockMelterManager {

	private static final RecipesLavaMaker recipes = new RecipesLavaMaker();

	public static final RecipesLavaMaker getRecipes() {
		return recipes;
	}

	private final ItemHashMap<MeltingRecipe> recipeList = new ItemHashMap();

	private RecipesLavaMaker() {
		super(MachineRegistry.LAVAMAKER);
		RecipeInterface.rockmelt = this;

		this.addRecipe(Blocks.stone, FluidRegistry.LAVA, 1000, 1000, ReikaThermoHelper.ROCK_MELT_ENERGY, RecipeLevel.PROTECTED);
		this.addRecipe(Blocks.cobblestone, FluidRegistry.LAVA, 500, 1000, 3120000, RecipeLevel.PROTECTED);
		this.addRecipe(Blocks.netherrack, FluidRegistry.LAVA, 2000, 600, 480000, RecipeLevel.PROTECTED);
		this.addRecipe(Blocks.stonebrick, FluidRegistry.LAVA, 1000, 1200, 4160000, RecipeLevel.PROTECTED);

		this.addRecipe("stone", FluidRegistry.LAVA, 1000, 1000, 5200000, RecipeLevel.PROTECTED);
		this.addRecipe("cobblestone", FluidRegistry.LAVA, 500, 1000, 2820000, RecipeLevel.PROTECTED);

		this.addRecipe(ItemRegistry.ETHANOL.getStackOf(), "rc ethanol", 1000, 180, 6000, RecipeLevel.PERIPHERAL);
	}

	private static class MeltingRecipe implements MachineRecipe {

		private final ItemStack input;
		private final FluidStack output;
		private final int temperature;
		private final long requiredEnergy;

		private MeltingRecipe(ItemStack is, FluidStack fs, int t, long e) {
			input = is;
			output = fs;
			temperature = t;
			requiredEnergy = e;
		}

		@Override
		public String getUniqueID() {
			return fullID(input)+"@"+temperature+"#"+output.getFluid().getName()+":"+output.amount;
		}

		@Override
		public String getAllInfo() {
			return "Melting "+fullID(input)+"into "+output.amount+" of "+output.getLocalizedName()+" @ "+temperature+"C using "+requiredEnergy+" J";
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(input);
		}
	}

	private void addRecipe(String in, String out, int amt, int temperature, long energy, RecipeLevel rl) {
		if (this.validateFluid(out)) {
			ArrayList<ItemStack> li = OreDictionary.getOres(in);
			for (int i = 0; i < li.size(); i++)
				this.addRecipe(li.get(i), new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy, rl);
		}
	}

	private void addRecipe(String in, Fluid out, int amt, int temperature, long energy, RecipeLevel rl) {
		ArrayList<ItemStack> li = OreDictionary.getOres(in);
		for (ItemStack sin : li) {
			if (!recipeList.containsKey(sin))
				this.addRecipe(sin, new FluidStack(out, amt), temperature, energy, rl);
		}
	}

	private void addRecipe(ItemStack in, String out, int amt, int temperature, long energy, RecipeLevel rl) {
		if (this.validateFluid(out))
			this.addRecipe(in, new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy, rl);
	}

	private void addRecipe(Item in, String out, int amt, int temperature, long energy, RecipeLevel rl) {
		if (this.validateFluid(out))
			this.addRecipe(new ItemStack(in), new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy, rl);
	}

	private void addRecipe(Block in, String out, int amt, int temperature, long energy, RecipeLevel rl) {
		if (this.validateFluid(out))
			this.addRecipe(new ItemStack(in), new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy, rl);
	}

	private void addRecipe(Block in, Fluid out, int amt, int temperature, long energy, RecipeLevel rl) {
		this.addRecipe(new ItemStack(in), new FluidStack(out, amt), temperature, energy, rl);
	}

	private void addRecipe(Item in, Fluid out, int amt, int temperature, long energy, RecipeLevel rl) {
		this.addRecipe(new ItemStack(in), new FluidStack(out, amt), temperature, energy, rl);
	}

	public void addAPIRecipe(ItemStack in, FluidStack out, int temperature, long energy) {
		this.addRecipe(in, out, temperature, energy, RecipeLevel.API);
	}

	private void addRecipe(Block in, FluidStack out, int temperature, long energy, RecipeLevel rl) {
		this.addRecipe(new ItemStack(in), out, temperature, energy, rl);
	}

	private void addRecipe(Item in, FluidStack out, int temperature, long energy, RecipeLevel rl) {
		this.addRecipe(new ItemStack(in), out, temperature, energy, rl);
	}

	private void addRecipe(ItemStack in, FluidStack out, int temperature, long energy, RecipeLevel rl) {
		if (in != null) {
			MeltingRecipe rec = new MeltingRecipe(in, out, temperature, energy);
			recipeList.put(in, rec);
			this.onAddRecipe(rec, rl);
		}
		else {
			RotaryCraft.logger.logError("Null itemstack for recipe for "+out+"!");
		}
	}

	private boolean validateFluid(String s) {
		return FluidRegistry.getFluid(s) != null;
	}

	public FluidStack getMelting(ItemStack is) {
		MeltingRecipe r = recipeList.get(is);
		return r != null ? r.output.copy() : null;
	}

	public int getMeltTemperature(ItemStack is) {
		MeltingRecipe r = recipeList.get(is);
		return r != null ? r.temperature : Integer.MIN_VALUE;
	}

	public long getMeltingEnergy(ItemStack is) {
		MeltingRecipe r = recipeList.get(is);
		return r != null ? r.requiredEnergy : Integer.MIN_VALUE;
	}

	public boolean isValidFuel(ItemStack is) {
		return recipeList.containsKey(is);
	}

	public ArrayList<ItemStack> getSourceItems(Fluid f) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack key : recipeList.keySet()) {
			MeltingRecipe r = recipeList.get(key);
			if (r.output.getFluid().equals(f))
				li.add(key.copy());
		}
		return li;
	}

	public Collection<ItemStack> getAllRecipes() {
		return Collections.unmodifiableCollection(recipeList.keySet());
	}

	@Override
	public void addPostLoadRecipes() {
		this.addRecipe("dustGlowstone", "glowstone", 250, 400, 80000, RecipeLevel.MODINTERACT);
		this.addRecipe(Blocks.glowstone, "glowstone", 1000, 500, 320000, RecipeLevel.MODINTERACT);
		this.addRecipe("dustRedstone", "redstone", 100, 600, 120000, RecipeLevel.MODINTERACT);
		this.addRecipe(Blocks.redstone_block, "redstone", 900, 750, 1080000, RecipeLevel.MODINTERACT);
		this.addRecipe(Items.ender_pearl, "ender", 250, 400, 240000, RecipeLevel.MODINTERACT);
		this.addRecipe("blockEnder", "ender", 1000, 400, 240000, RecipeLevel.MODINTERACT);
		this.addRecipe("dustCoal", "coal", 100, 300, 60000, RecipeLevel.MODINTERACT);

		if (ModList.THERMALFOUNDATION.isLoaded()) {
			ItemStack pyro = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "dustPyrotheum", 1);
			this.addRecipe(pyro, "pyrotheum", 250, 1800, 9000000, RecipeLevel.MODINTERACT);

			ItemStack cryo = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "dustCryotheum", 1);
			this.addRecipe(cryo, "cryotheum", 250, -200, 2000, RecipeLevel.MODINTERACT);

			ItemStack petro = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "dustPetrotheum", 1);
			this.addRecipe(petro, "petrotheum", 250, 800, 12000000, RecipeLevel.MODINTERACT);

			ItemStack aero = GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "dustAerotheum", 1);
			this.addRecipe(aero, "aerotheum", 250, 400, 40000, RecipeLevel.MODINTERACT);
		}

		this.addRecipe("shardCrystal", "potion crystal", 8000, 500, 80000, RecipeLevel.MODINTERACT);

		if (ModList.MAGICCROPS.isLoaded() && MagicCropHandler.EssenceType.XP.getEssence() != null)
			this.addRecipe(MagicCropHandler.EssenceType.XP.getEssence(), "mobessence", 200, 600, 360000, RecipeLevel.MODINTERACT);

		if (ModList.GEOSTRATA.isLoaded()) {
			this.addLavaRock();
		}
	}

	@ModDependent(ModList.GEOSTRATA)
	private void addLavaRock() {
		for (int i = 0; i < 4; i++) {
			ItemStack is = new ItemStack(GeoBlocks.LAVAROCK.getBlockInstance(), 1, i);
			this.addRecipe(is, new FluidStack(FluidRegistry.LAVA, 1000), 900-200*i, ReikaThermoHelper.ROCK_MELT_ENERGY/(i+1), RecipeLevel.MODINTERACT);
		}
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipeList.removeValue((MeltingRecipe)recipe);
	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), null, false);
		LuaBlock fluid = lb.getChild("output_fluid");
		String s = fluid.getString("type");
		Fluid f = FluidRegistry.getFluid(s);
		if (f == null)
			throw new IllegalArgumentException("Fluid '"+s+"' does not exist!");
		this.verifyOutputFluid(f);
		FluidStack fs = new FluidStack(f, fluid.getInt("amount"));
		int temp = lb.getInt("temperature");
		long energy = lb.getLong("energy");
		this.addRecipe(in, fs, temp, energy, RecipeLevel.CUSTOM);
		return true;
	}

}
