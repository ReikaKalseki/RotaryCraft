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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.Maps.FluidHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerToolHandler;
import Reika.DragonAPI.ModInteract.RecipeHandlers.SmelteryRecipeHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.CrystallizerManager;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class RecipesCrystallizer extends RecipeHandler implements CrystallizerManager {

	private static final RecipesCrystallizer CrystallizerBase = new RecipesCrystallizer();

	private final FluidHashMap<CrystallizerRecipe> recipeList = new FluidHashMap();

	public static final RecipesCrystallizer getRecipes()
	{
		return CrystallizerBase;
	}

	private RecipesCrystallizer() {
		super(MachineRegistry.CRYSTALLIZER);
		RecipeInterface.crystallizer = this;

		this.addRecipe(FluidRegistry.WATER, 1000, new ItemStack(Blocks.ice), RecipeLevel.CORE);
		this.addRecipe(FluidRegistry.WATER, 200, new ItemStack(Items.snowball), RecipeLevel.PROTECTED);
		this.addRecipe(FluidRegistry.LAVA, 1000, new ItemStack(Blocks.stone), RecipeLevel.PERIPHERAL);

		this.addRecipe("rc ethanol", 1000, ItemRegistry.ETHANOL.getStackOf(), RecipeLevel.PROTECTED);
	}

	public static class CrystallizerRecipe implements MachineRecipe {

		private final FluidStack input;
		private final ItemStack output;

		private CrystallizerRecipe(FluidStack fs, ItemStack is) {
			input = fs;
			output = is;
		}

		@Override
		public String getUniqueID() {
			return input.getFluid().getName()+":"+input.amount+">"+fullID(output);
		}

		@Override
		public String getAllInfo() {
			return "Freezing "+input.amount+" of "+input.getLocalizedName()+" into "+fullID(output);
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(ReikaFluidHelper.getFluidStackAsItem(input));
		}

		public ItemStack getOutput() {
			return output.copy();
		}

		public FluidStack getFluid() {
			return input.copy();
		}

	}

	public void addAPIRecipe(Fluid f, int amount, ItemStack out) {
		this.addRecipe(f, amount, out, RecipeLevel.API);
	}

	public void addRecipe(Fluid f, int amount, ItemStack out, RecipeLevel rl) {
		CrystallizerRecipe rec = new CrystallizerRecipe(new FluidStack(f, amount), out);
		recipeList.put(f, amount, rec);
		this.onAddRecipe(rec, rl);
	}

	private void addRecipe(String s, int amount, ItemStack out, RecipeLevel rl) {
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(f, amount, out, rl);
	}

	public ItemStack getFreezingResult(FluidStack liquid) {
		Fluid f = liquid.getFluid();
		CrystallizerRecipe cr = recipeList.getForValue(liquid);
		if (cr == null)
			return null;
		int req = cr.input.amount;
		if (req > liquid.amount)
			return null;
		return cr.output.copy();
	}

	public CrystallizerRecipe getRecipe(ItemStack result) {
		for (CrystallizerRecipe cr : recipeList.values()) {
			/*
			if (cr == null) {
				StringBuilder sb = new StringBuilder();
				sb.append("Looking up recipe for "+result+": "+ReikaFluidHelper.fluidStackToString(f)+", despite being in the keyset of the map, returned null on get()!");
				sb.append("\nMap data: "+recipeList.toString());
				sb.append("\nReport this to Reika!");
				throw new IllegalStateException(sb.toString());
			}
			 */
			if (ReikaItemHelper.matchStacks(result, cr.output))
				return cr;
		}
		return null;
	}

	public int getRecipeConsumption(ItemStack result) {
		CrystallizerRecipe cr = this.getRecipe(result);
		return cr != null ? cr.input.amount : 0;
	}

	public boolean isValidFluid(Fluid f) {
		return recipeList.containsKey(f);
	}

	public Collection<CrystallizerRecipe> getAllRecipes() {
		HashSet<CrystallizerRecipe> c = new HashSet();
		for (CrystallizerRecipe cr : recipeList.values()) {
			c.add(cr);
		}
		return c;
	}

	@Override
	public void addPostLoadRecipes() {
		this.addRecipe("ender", 250, new ItemStack(Items.ender_pearl), RecipeLevel.MODINTERACT);
		this.addRecipe("redstone", 100, new ItemStack(Items.redstone), RecipeLevel.MODINTERACT);
		this.addRecipe("glowstone", 250, new ItemStack(Items.glowstone_dust), RecipeLevel.MODINTERACT);
		this.addRecipe("coal", 100, new ItemStack(Items.coal), RecipeLevel.MODINTERACT);

		if (ModList.TINKERER.isLoaded()) {
			List li = SmelteryRecipeHandler.getCastingRecipes();
			if (li != null) {
				for (Object o : li) {
					ItemStack cast = SmelteryRecipeHandler.getRecipeCast(o);
					if (ReikaItemHelper.matchStacks(cast, TinkerToolHandler.getInstance().getIngotCast())) {
						ItemStack out = SmelteryRecipeHandler.getRecipeOutput(o);
						FluidStack fs = SmelteryRecipeHandler.getRecipeFluid(o);

						if (ModList.IC2.isLoaded() && fs.getFluid().getName().toLowerCase(Locale.ENGLISH).equals("steel.molten")) { //prevent steel -> refined iron
							out = getNonRefinedIronSteel(out);
						}

						this.addRecipe(fs.getFluid(), fs.amount, out, RecipeLevel.MODINTERACT);
					}
				}
			}
		}
	}

	public static ItemStack getNonRefinedIronSteel(ItemStack out) {
		ItemStack refiron = IC2Handler.IC2Stacks.REFINEDIRON.getItem();
		ItemStack refblock = ReikaItemHelper.lookupItem("IC2:blockMetal:5");
		if (ReikaItemHelper.matchStacks(refiron, out) || ReikaItemHelper.matchStacks(refblock, out)) {
			RotaryCraft.logger.log("Handling steel casting to refined iron, finding alternate.");
			String tag = null;
			int[] ids = OreDictionary.getOreIDs(out);
			for (int id : ids) {
				String s = OreDictionary.getOreName(id);
				if (s.toLowerCase(Locale.ENGLISH).contains("steel")) {
					tag = s;
					break;
				}
			}
			RotaryCraft.logger.log("OreDict tag is '"+tag+"'.");
			if (tag == null)
				return out;
			List<ItemStack> ingots = OreDictionary.getOres(tag);
			if (ingots.size() > 1) {
				for (ItemStack ing : ingots) {
					if (!ReikaItemHelper.matchStacks(refiron, ing) && !ReikaItemHelper.matchStacks(refblock, ing)) {
						out = ReikaItemHelper.getSizedItemStack(ing, out.stackSize);
						RotaryCraft.logger.log("Converting to "+out+" ("+fullID(out)+")");
						break;
					}
				}
			}
		}
		return out;
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipeList.removeValue((CrystallizerRecipe)recipe);
	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		this.verifyOutputItem(out);
		String fluid = lb.getString("input_fluid");
		Fluid f = FluidRegistry.getFluid(fluid);
		if (f == null)
			throw new IllegalArgumentException("Fluid '"+fluid+"' does not exist!");
		int amt = lb.getInt("input_amount");
		this.addRecipe(f, amt, out, RecipeLevel.CUSTOM);
		return true;
	}
}
