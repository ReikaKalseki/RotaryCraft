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

import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.FrictionHeaterManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class RecipesFrictionHeater extends RecipeHandler implements FrictionHeaterManager {

	private static final RecipesFrictionHeater instance = new RecipesFrictionHeater();

	private final ItemHashMap<FrictionRecipe> recipes = new ItemHashMap();
	private final ItemHashMap<Collection<FrictionRecipe>> outputs = new ItemHashMap();

	public static RecipesFrictionHeater getRecipes() {
		return instance;
	}

	private RecipesFrictionHeater() {
		super(MachineRegistry.FRICTION);
		RecipeInterface.friction = this;

		this.addRecipe(ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1350, 600, RecipeLevel.CORE);
		this.addRecipe(ItemStacks.silicondust, ItemStacks.silicon, 800, 200, RecipeLevel.PROTECTED);
	}

	private void addRecipe(ItemStack in, ItemStack out, int temp, int time, RecipeLevel rl) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp, time);
		recipes.put(in, rec);
		Collection<FrictionRecipe> c = outputs.get(out);
		if (c == null) {
			c = new ArrayList();
			outputs.put(out, c);
		}
		c.add(rec);
		this.onAddRecipe(rec, rl);
	}

	public void addCoreRecipe(ItemStack in, ItemStack out, int temp, int time) {
		this.addRecipe(in, out, temp, time, RecipeLevel.CORE);
	}

	public void addAPIRecipe(ItemStack in, ItemStack out, int temp, int time) {
		this.addRecipe(in, out, temp, time, RecipeLevel.API);
	}

	public void addCustomRecipe(ItemStack in, ItemStack out, int temp, int time) {
		this.addRecipe(in, out, temp, time, RecipeLevel.CUSTOM);
	}

	public FrictionRecipe getSmelting(ItemStack in, int temperature) {
		FrictionRecipe rec = recipes.get(in);
		return rec != null ? (temperature >= rec.requiredTemperature ? rec : null) : null;
	}

	public Collection<FrictionRecipe> getRecipesByOutput(ItemStack out) {
		return Collections.unmodifiableCollection(outputs.get(out));
	}

	public FrictionRecipe getRecipeByInput(ItemStack in) {
		return recipes.get(in);
	}

	public static final class FrictionRecipe implements MachineRecipe {

		public final int requiredTemperature;
		public final int duration;
		private final ItemStack input;
		private final ItemStack output;

		private FrictionRecipe(ItemStack in, ItemStack out, int temp, int time) {
			requiredTemperature = temp;
			duration = Math.abs(time);
			input = in;
			output = out;
		}

		public ItemStack getInput() {
			return input.copy();
		}

		public ItemStack getOutput() {
			return output.copy();
		}

		@Override
		public String getUniqueID() {
			return fullID(input)+">"+fullID(output)+"@"+requiredTemperature+"#"+duration;
		}

		@Override
		public String getAllInfo() {
			return "Smelting "+fullID(input)+" into "+fullID(output)+" @ "+requiredTemperature+"C over "+duration+" ticks";
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(input, output);
		}
	}

	public Collection<ItemStack> getAllSmeltables() {
		return Collections.unmodifiableCollection(recipes.keySet());
	}

	@Override
	public void addPostLoadRecipes() {

	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipes.removeValue((FrictionRecipe)recipe) && outputs.get(((FrictionRecipe)recipe).output).remove(recipe);
	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), null, false);
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		this.verifyOutputItem(out);
		this.addCustomRecipe(in, out, lb.getInt("temperature"), lb.getInt("duration"));
		return true;
	}

}
