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

import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.MagnetizerManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;



public class RecipesMagnetizer extends RecipeHandler implements MagnetizerManager {

	private final ItemHashMap<MagnetizerRecipe> recipes = new ItemHashMap();

	private static final RecipesMagnetizer instance = new RecipesMagnetizer();

	public static final RecipesMagnetizer getRecipes() {
		return instance;
	}

	private RecipesMagnetizer() {
		super(MachineRegistry.MAGNETIZER);
		RecipeInterface.magnetizer = this;

		this.addRecipe(ItemStacks.shaftcore, 0, 2, 2, false, RecipeLevel.CORE);
		this.addRecipe(ItemStacks.tungstenshaftcore, 0, 2, 1, false, RecipeLevel.CORE);
		this.addRecipe(ItemRegistry.UPGRADE.getStackOfMetadata(Upgrades.MAGNETOSTATIC2.ordinal()), 32768, 1, 4, true, RecipeLevel.CORE);
	}

	private void addRecipe(ItemStack in, int minSpeed, int reqSpeedPerMicroTesla, int timeFactor, boolean allowStacks, RecipeLevel rl) {
		this.addRecipe(in, minSpeed, reqSpeedPerMicroTesla, timeFactor, allowStacks, null, rl);
	}

	private void addRecipe(ItemStack in, int minSpeed, int reqSpeedPerMicroTesla, int timeFactor, boolean allowStacks, MagnetizationAction a, RecipeLevel rl) {
		MagnetizerRecipe rec = new MagnetizerRecipe(in, timeFactor, minSpeed, reqSpeedPerMicroTesla, allowStacks, a);
		recipes.put(in, rec);
		this.onAddRecipe(rec, rl);
	}

	public void addAPIRecipe(ItemStack in, int minSpeed, int reqSpeedPerMicroTesla, int timeFactor, boolean allowStacks, MagnetizationAction a) {
		this.addRecipe(in, minSpeed, reqSpeedPerMicroTesla, timeFactor, allowStacks, a, RecipeLevel.API);
	}

	@Override
	public void addPostLoadRecipes() {

	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipes.removeValue((MagnetizerRecipe)recipe);
	}

	public MagnetizerRecipe getRecipe(ItemStack is) {
		return recipes.get(is);
	}

	public Collection<MagnetizerRecipe> getAllRecipes() {
		return recipes.values();
	}

	public static class MagnetizerRecipe implements MachineRecipe {

		private final ItemStack item;

		public final int timeFactor;
		public final int minSpeed;
		public final int speedPeruT;

		public final boolean allowStacking;

		public final MagnetizationAction action;

		private MagnetizerRecipe(ItemStack is, int time, int omega, int sput, boolean stack, MagnetizationAction a) {
			item = is;
			timeFactor = time;
			minSpeed = omega;
			speedPeruT = sput;

			action = a;
			allowStacking = stack;
		}

		public ItemStack getItem() {
			return item.copy();
		}

		@Override
		public String getUniqueID() {
			return fullID(item)+"/"+timeFactor+"/"+minSpeed+"/"+speedPeruT;
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(this.getItem());
		}

		@Override
		public String getAllInfo() {
			return "Magnetizing "+fullID(item)+" @ speed "+minSpeed+" to "+speedPeruT+"/rads @ "+timeFactor+"x time factor";
		}

	}

	public String getRecipesAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Required Speeds:\n\n");
		for (MagnetizerRecipe mr : recipes.values()) {
			sb.append(mr.item.getDisplayName()+" @ "+Math.max(PowerReceivers.MAGNETIZER.getMinSpeed(), mr.minSpeed)+" rad/s;\n");
			sb.append(mr.timeFactor+"x time cost, "+mr.speedPeruT+"rad/s per uT\n\n");
		}
		return sb.toString();
	}

	@Override
	protected boolean addCustomRecipe(String n, LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), null, false);
		int speed = lb.getInt("min_speed");
		int spuT = lb.getInt("speed_per_microtesla");
		int time = lb.getInt("time_factor");
		boolean stack = lb.getBoolean("allow_stacks");
		this.addRecipe(in, speed, spuT, time, stack, RecipeLevel.CUSTOM);
		return true;
	}

}
