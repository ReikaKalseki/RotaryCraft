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
import java.util.HashMap;
import java.util.Locale;

import com.google.common.collect.HashBiMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap.CollectionType;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.SensitiveFluidRegistry;
import Reika.DragonAPI.ModInteract.DeepInteract.SensitiveItemRegistry;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.ItemBlockPlacer;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public abstract class RecipeHandler {

	private static final boolean enableRegistries = ConfigRegistry.RECIPEMOD.getState();

	private final MultiMap<RecipeLevel, String> recipesByLevel = new MultiMap(CollectionType.HASHSET);
	private final HashMap<String, RecipeLevel> recipeLevels = new HashMap();

	private final HashBiMap<MachineRecipe, String> recipeKeys = HashBiMap.create();

	public final MachineRegistry machine;

	protected RecipeHandler(MachineRegistry m) {
		machine = m;
	}

	protected final void onAddRecipe(MachineRecipe recipe, RecipeLevel rl) {
		if (enableRegistries) {
			String s = recipeKeys.get(recipe);
			if (s == null) {
				s = this.generateKey(recipe);
			}
			if (s == null)
				return;
			recipesByLevel.addValue(rl, s);
			recipeLevels.put(s, rl);
		}
	}

	private String generateKey(MachineRecipe recipe) {
		String s = machine.name()+"$"+recipe.getClass().getSimpleName()+"#("+recipe.getUniqueID();
		if (RotaryCraft.logger.shouldDebug())
			ReikaJavaLibrary.pConsole("Recipe Loaded: "+recipe+"="+s);
		if (recipeKeys.containsValue(s)) {
			MachineRecipe pre = recipeKeys.inverse().get(s);
			if (pre == null || pre.equals(recipe)) {
				return null; //do nothing
			}
			else {
				RotaryCraft.logger.logError("Found duplicate recipe key when adding recipe "+recipe.getAllInfo()+" in place of "+pre.getAllInfo());
				RotaryCraft.logger.log("Original Recipe Items:");
				for (ItemStack is : pre.getAllUsedItems()) {
					RotaryCraft.logger.log(is+" from mod '"+ReikaItemHelper.getRegistrantMod(is)+"', NBT="+is.stackTagCompound);
				}
				RotaryCraft.logger.log("New Recipe Items:");
				for (ItemStack is : recipe.getAllUsedItems()) {
					RotaryCraft.logger.log(is+" from mod '"+ReikaItemHelper.getRegistrantMod(is)+"', NBT="+is.stackTagCompound);
				}
				throw new RegistrationException(RotaryCraft.instance, "Two recipes have the same key: '"+s+"'");
			}
		}
		recipeKeys.put(recipe, s);
		return s;
	}

	protected static final String fullIDKeys(Collection<KeyedItemStack> c) {
		StringBuilder sb = new StringBuilder();
		for (KeyedItemStack is : c) {
			sb.append(fullID(is.getItemStack()));
			sb.append("|");
		}
		return sb.toString();
	}

	protected static final String fullID(Collection<ItemStack> c) {
		StringBuilder sb = new StringBuilder();
		for (ItemStack is : c) {
			sb.append(fullID(is));
			sb.append("|");
		}
		return sb.toString();
	}

	protected static final String fullID(ItemStack is) {
		if (is == null)
			return "[null]";
		else if (is.getItem() == null)
			return "[null-item stack]";
		return is.stackSize+"x"+Item.itemRegistry.getNameForObject(is.getItem())+"@"+is.getItemDamage()+"{"+is.stackTagCompound+"}["+ReikaItemHelper.getRegistrantMod(is)+"]";
	}

	protected final Collection getRecipes(RecipeLevel rl) {
		return recipesByLevel.get(rl);
	}

	public final RecipeLevel getRecipeLevel(String rec) {
		return recipeLevels.get(rec);
	}

	public final boolean removeRecipe(String rec) {
		RecipeLevel rl = this.getRecipeLevel(rec);
		RecipeModificationPower power = getModificationPower();
		if (power.canRemove(rl)) {
			MachineRecipe recipe = recipeKeys.inverse().get(rec);
			if (rec == null) {
				RotaryCraft.logger.log("Recipe removal of '"+rec+"' from "+machine+" not possible; No such recipe with that key.");
				return false;
			}
			try {
				if (this.removeRecipe(recipe)) {
					recipesByLevel.remove(rl, rec);
					recipeLevels.remove(rec);
					recipeKeys.remove(recipe);
					return true;
				}
				else {
					RotaryCraft.logger.log("Recipe removal of '"+rec+"' from "+machine+" failed; Potential code error.");
					return false;
				}
			}
			catch (Exception e) {
				RotaryCraft.logger.log("Recipe removal of '"+rec+"' from "+machine+" failed and threw an exception; Potential code error.");
				e.printStackTrace();
				return false;
			}
		}
		else {
			RotaryCraft.logger.log("Recipe removal of '"+rec+"' from "+machine+" rejected; This is a '"+rl+"' recipe and cannot be modified with '"+power+"' modify power.");
			return false;
		}
	}

	public abstract void addPostLoadRecipes();

	protected abstract boolean removeRecipe(MachineRecipe recipe);
	//protected abstract boolean addCustomRecipe(String key);

	private static RecipeModificationPower getRequiredPowerForOutput(ItemStack is) {
		if (!ReikaItemHelper.getNamespace(is.getItem()).contains("RotaryCraft"))
			return RecipeModificationPower.DEFAULT;
		if (!ReikaItemHelper.getNamespace(is.getItem()).contains("ReactorCraft"))
			return RecipeModificationPower.DEFAULT;
		if (!ReikaItemHelper.getNamespace(is.getItem()).contains("ElectriCraft"))
			return RecipeModificationPower.DEFAULT;
		return SensitiveItemRegistry.instance.contains(is) ? RecipeModificationPower.FULL : RecipeModificationPower.NORMAL;
	}

	private static RecipeModificationPower getRequiredPowerForOutput(Fluid f) {
		return SensitiveFluidRegistry.instance.contains(f) ? RecipeModificationPower.FULL : RecipeModificationPower.DEFAULT;
	}

	public static boolean isOutputPermitted(ItemStack is) {
		return getModificationPower().ordinal() <= getRequiredPowerForOutput(is).ordinal();
	}

	public static boolean isOutputPermitted(Fluid f) {
		return getModificationPower().ordinal() <= getRequiredPowerForOutput(f).ordinal();
	}

	public static enum RecipeLevel {

		CORE(), //Core native recipesByLevel
		PROTECTED(), //Non-core but native and fairly important
		PERIPHERAL(), //Non-core but native
		MODINTERACT(), //Ones for mod interaction; also native
		API(), //API-level
		CUSTOM(); //Minetweaker

		private static final RecipeLevel[] list = values();

	}

	private static enum RecipeModificationPower {
		FULL(RecipeLevel.CORE),
		STRONG(RecipeLevel.PROTECTED),
		NORMAL(RecipeLevel.PERIPHERAL),
		DEFAULT(RecipeLevel.CUSTOM);

		private final RecipeLevel maxLevel;

		private static final RecipeModificationPower[] list = values();

		private RecipeModificationPower(RecipeLevel rl) {
			maxLevel = rl;
		}

		public final boolean canRemove(RecipeLevel rl) {
			return rl.ordinal() >= maxLevel.ordinal();
		}
	}

	private static RecipeModificationPower getModificationPower() {
		int get = Math.min(RecipeModificationPower.DEFAULT.ordinal(), Math.max(0, ConfigRegistry.getRecipeModifyPower()));
		return RecipeModificationPower.list[RecipeModificationPower.DEFAULT.ordinal()-get];
	}

	protected static interface MachineRecipe {

		String getUniqueID();
		Collection<ItemStack> getAllUsedItems();
		String getAllInfo();

	}

	public final void loadCustomRecipeFiles() {
		CustomRecipeList crl = new CustomRecipeList(RotaryCraft.instance, machine.name().toLowerCase(Locale.ENGLISH));
		crl.addFieldLookup("rotarycraft_stack", ItemStacks.class);
		crl.load();
		for (LuaBlock lb : crl.getEntries()) {
			Exception e = null;
			boolean flag = false;
			try {
				flag = this.addCustomRecipe(lb, crl);
			}
			catch (Exception ex) {
				e = ex;
				flag = false;
			}
			if (flag) {
				RotaryCraft.logger.log("Loaded custom recipe '"+lb.getString("type")+"' for "+machine.name()+"");
			}
			else {
				RotaryCraft.logger.logError("Could not load custom recipe '"+lb.getString("type")+"' for "+machine.name()+"");
				if (e != null)
					e.printStackTrace();
			}
		}
	}

	protected abstract boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception;

	protected final void verifyOutputItem(ItemStack is) {
		if (is.getItem() instanceof ItemBlockPlacer || is.getItem() == ItemRegistry.ETHANOL.getItemInstance())
			throw new IllegalArgumentException("This item is not permitted as an output (it is a gating item).");
		if (ReikaItemHelper.matchStacks(is, ItemStacks.tungstenflakes) || ReikaItemHelper.matchStacks(is, ItemStacks.tungsteningot))
			throw new IllegalArgumentException("This item is not permitted as an output (it is a gating item).");
		if (ReikaItemHelper.matchStacks(is, ItemStacks.silicondust) || ReikaItemHelper.matchStacks(is, ItemStacks.silicon))
			throw new IllegalArgumentException("This item is not permitted as an output (it is a gating item).");
		if (ReikaItemHelper.matchStacks(is, ItemStacks.redgoldingot) || ReikaItemHelper.matchStacks(is, ItemStacks.silumin))
			throw new IllegalArgumentException("This item is not permitted as an output (it is a gating item).");
		if (ReikaItemHelper.matchStacks(is, ItemStacks.bedrockdust) || ReikaItemHelper.matchStacks(is, ItemStacks.bedingot))
			throw new IllegalArgumentException("This item is not permitted as an output (it is a gating item).");
		if (ReikaItemHelper.matchStacks(is, ItemStacks.springingot) || ReikaItemHelper.matchStacks(is, ItemStacks.steelingot))
			throw new IllegalArgumentException("This item is not permitted as an output (it is a gating item).");
	}

	protected final void verifyOutputFluid(Fluid f) {
		if (f.getName().equalsIgnoreCase("rc jet fuel") || f.getName().equalsIgnoreCase("rc ethanol") || f.getName().equalsIgnoreCase("rc lubricant"))
			throw new IllegalArgumentException("This fluid is not permitted as an output (it is a gating item).");
	}

}
