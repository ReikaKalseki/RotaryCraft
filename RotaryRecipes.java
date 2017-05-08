/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import ic2.api.recipe.ISemiFluidFuelManager.BurnProperty;
import ic2.api.recipe.Recipes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import minechem.api.RecipeAPI;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.Trackers.ItemMaterialController;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Instantiable.ItemMaterial;
import Reika.DragonAPI.Instantiable.PreferentialItemStack;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWayList;
import Reika.DragonAPI.Instantiable.Formula.MathExpression;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.MoleculeHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaThaumHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.TinkerMaterialHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ThaumItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ThaumOreHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerBlockHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerToolHandler.ToolParts;
import Reika.DragonAPI.ModInteract.ItemHandlers.TinkerToolHandler.WeaponParts;
import Reika.DragonAPI.ModInteract.Power.ReikaBuildCraftHelper;
import Reika.DragonAPI.ModInteract.RecipeHandlers.SmelteryRecipeHandler;
import Reika.DragonAPI.ModInteract.RecipeHandlers.ThermalRecipeHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.ReactorCraft.Auxiliary.ReactorStacks;
import Reika.ReactorCraft.Registry.CraftingItems;
import Reika.RotaryCraft.Auxiliary.DecoTankSettingsRecipe;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecyclingRecipe;
import Reika.RotaryCraft.Auxiliary.ReservoirComboRecipe;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipeHandler;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipeHandler.RecipeLevel;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCompactor;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCrystallizer;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesDryingBed;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesLavaMaker;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesMagnetizer;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesWetter;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Items.Tools.ItemEngineUpgrade.Upgrades;
import Reika.RotaryCraft.ModInterface.BedrockRevealingInfusion;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.api.energy.DieselHandler.SqueezerRecipe;
import buildcraft.energy.fuels.CoolantManager;
import buildcraft.energy.fuels.FuelManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import forestry.api.fuels.GeneratorFuel;

public class RotaryRecipes {

	public static void addRecipes() {
		if (RotaryCraft.instance.isLocked())
			return;
		addMachines();
		addCraftItems();
		addMultiTypes();
		addToolItems();
		addMisc();
		addFurnace();

		if (ModList.THERMALEXPANSION.isLoaded())
			addThermalExpansion();
		Fluid ethanol = FluidRegistry.getFluid("rc ethanol");
		if (ModList.BCENERGY.isLoaded()) {
			FuelManager.INSTANCE.addFuel(ethanol, ReikaBuildCraftHelper.getFuelRFPerTick()*3/2, 1500); //ethanol generates about 50% more power, but burns fast
			CoolantManager.INSTANCE.addCoolant(FluidRegistry.getFluid("rc liquid nitrogen"), 0.01F);
		}
		if (ModList.FORESTRY.isLoaded()) {
			int power = forestry.api.fuels.FuelManager.generatorFuel.get(FluidRegistry.getFluid("bioethanol")).eu*36/32; //36/32 (1.125x) forestry ethanol
			forestry.api.fuels.FuelManager.generatorFuel.put(ethanol, new GeneratorFuel(new FluidStack(ethanol, 1), power, 3)); //25% less burn time
		}
		if (ModList.IC2.isLoaded()) {
			double power = Recipes.semiFluidGenerator.getBurnProperties().get("bioethanol").power*18/16; //1.125x again
			BurnProperty fuel = Recipes.semiFluidGenerator.getBurnProperties().get("fuel");
			power = Math.max(power, fuel.power*3/2); //50% more than fuel
			Recipes.semiFluidGenerator.addFluid(ethanol.getName(), Math.max(8, fuel.amount*4), power);
		}
		if (ModList.IMMERSIVEENG.isLoaded()) {
			DieselHandler.squeezerList.add(new SqueezerRecipe(ItemRegistry.CANOLA.getStackOf(), 15, new FluidStack(FluidRegistry.getFluid("plantoil"), 20), null)); //4x less but 6x faster
		}
	}

	private static void addProps() {
		ItemMaterialController.instance.addItem(ItemStacks.scrap, ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemStacks.ironscrap, ItemMaterial.IRON);
		ItemMaterialController.instance.addItem(ItemStacks.steelblock, ItemMaterial.STEEL);

		ItemMaterialController.instance.addItem(ItemRegistry.STEELAXE.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELPICK.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELSHOVEL.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELHELMET.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELLEGS.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELBOOTS.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELCHEST.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELSWORD.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELSICKLE.getStackOf(), ItemMaterial.STEEL);
		ItemMaterialController.instance.addItem(ItemRegistry.STEELSHEARS.getStackOf(), ItemMaterial.STEEL);
	}

	private static void addCompat() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			String tag = ore.getProductOreDictName();
			ArrayList<ItemStack> in = OreDictionary.getOres(tag);
			for (int h = 0; h < in.size(); h++) {
				ItemStack from = in.get(h);
				ItemStack to = ItemStacks.getModOreIngot(ore);
				if (!ItemRegistry.MODINGOTS.matchItem(from))
					GameRegistry.addShapelessRecipe(to.copy(), from.copy());
			}
		}
	}

	private static void addThermalExpansion() {
		FluidStack ethanol = FluidRegistry.getFluidStack("rc ethanol", 1000);
		int energy = 750;
		ThermalRecipeHelper.addCrucibleRecipe(ItemRegistry.ETHANOL.getStackOf(), ethanol, energy);
		//ThermalRecipeHelper.addInductionSmelter(ItemStacks.steelingot.copy(), bedrock, ItemStacks.bedingot.copy(), 48000);

		//ItemStack transmissionCoil = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilSilver", 1);
		//ItemStack energyCell = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "cellReinforced", 1);
		//ItemStack conductanceCoil = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilElectrum", 1);

		ThermalRecipeHelper.addCoolant(RotaryCraft.nitrogenFluid, 40000);
		ThermalRecipeHelper.addCompressionFuel(RotaryCraft.ethanolFluid, 250000); //1/2 of forestry
	}

	public static void addPostLoadRecipes() {
		if (RotaryCraft.instance.isLocked())
			return;

		Object[] bin = getBlastFurnaceIngredients();
		addOreRecipeToBoth(MachineRegistry.BLASTFURNACE.getCraftedProduct(), bin);
		RotaryCraft.logger.log("Blast Furnace gating materials set to "+Arrays.toString(bin));

		addProps();

		for (RecipeHandler h : recipeHandlers) {
			h.addPostLoadRecipes();
			h.loadCustomRecipeFiles();
		}

		//RecipesExtractor.recipes().addModRecipes();

		MachineRegistry.COMPRESSOR.addCrafting("SsS", " G ", "CPC", 's', getConverterGatingItem(), 'S', ItemStacks.steelingot, 'G', Blocks.glass, 'P', Blocks.piston, 'C', ItemStacks.compressor);

		MachineRegistry.PNEUENGINE.addCrafting("ppS", "sT ", "PPP", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem, 'p', ItemStacks.pipe, 'P', ItemStacks.basepanel, 'T', ItemStacks.impeller);

		ItemStack plate = ModList.RAILCRAFT.isLoaded() ? new ItemStack(GameRegistry.findItem(ModList.RAILCRAFT.modLabel, "part.plate"), 1, 1) : null;
		MachineRegistry.STEAMTURBINE.addCrafting("sPs", "GTG", "ScS", 's', ConfigRegistry.HARDCONVERTERS.getState() && plate != null ? plate : ItemStacks.steelingot, 'c', ItemStacks.diamondshaftcore, 'G', Blocks.glass, 'S', ItemStacks.steelingot, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel);

		MachineRegistry.BOILER.addCrafting("SPS", "G G", "sIs", 's', getConverterGatingItem(), 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.pipe, 'G', Blocks.glass);

		MachineRegistry.GENERATOR.addCrafting("gpS", "iGs", "psp", 'S', getConverterGatingItem(), 'p', ItemStacks.basepanel, 'g', Items.gold_ingot, 's', ItemStacks.steelingot, 'G', ItemStacks.generator, 'i', ItemStacks.impeller, 's', ItemStacks.shaftcore);

		ItemStack cool = null;
		if (ModList.IC2.isLoaded()) {
			try {
				Class c = ModList.IC2.getItemClass();
				Field icf = c.getField("reactorCoolantSix"); //coolant cell
				cool = (ItemStack)icf.get(null);
			}
			catch (Exception e) {
				e.printStackTrace();
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.IC2, e);
			}
		}
		MachineRegistry.ELECTRICMOTOR.addCrafting("cGS", "BCB", "SGS", 'c', ConfigRegistry.HARDCONVERTERS.getState() && cool != null ? cool : ItemStacks.steelingot, 'G', ItemStacks.goldcoil, 'S', ItemStacks.steelingot, 'B', ItemStacks.basepanel, 'C', ItemStacks.diamondshaftcore);

		ItemStack coil = ModList.THERMALEXPANSION.isLoaded() ? GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilSilver", 1) : ItemStacks.power;
		MachineRegistry.DYNAMO.addOreRecipe(" C ", "GiG", "IRI", 'C', coil, 'i', getConverterGatingItem(), 'I', ItemStacks.steelingot, 'G', ItemStacks.steelgear, 'R', Items.redstone);
		coil = ModList.THERMALEXPANSION.isLoaded() ? GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilGold", 1) : ItemStacks.goldcoil;
		Object ps = new PreferentialItemStack(Items.iron_ingot, "ingotLead").blockItem(ItemRegistry.MODINGOTS.getItemInstance()).getItem();
		Item crys = ModList.BUILDCRAFT.isLoaded() ? GameRegistry.findItem(ModList.BCSILICON.modLabel, "redstoneCrystal") : null;
		MachineRegistry.MAGNETIC.addOreRecipe("LCl", "scs", "PSP", 'L', ConfigRegistry.HARDCONVERTERS.getState() && crys != null ? crys : ps, 'c', ItemStacks.conductive.getItem(), 'C', coil, 'P', ItemStacks.basepanel, 'S', ItemStacks.diamondshaftcore, 'l', ps, 's', "ingotSilver");

		ItemStack enderium = ModList.THERMALFOUNDATION.isLoaded() ? GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "ingotEnderium", 1) : ItemStacks.bedingot;
		ItemStack electrum = ModList.THERMALFOUNDATION.isLoaded() ? GameRegistry.findItemStack(ModList.THERMALFOUNDATION.modLabel, "ingotElectrum", 1) : ItemStacks.redgoldingot;
		ItemRegistry.UPGRADE.addMetaRecipe(Upgrades.FLUX.ordinal(), "BeB", "tEt", "BeB", 'e', electrum, 'B', ItemStacks.basepanel, 'E', enderium, 't', ItemStacks.tungsteningot);

		if (ModList.TINKERER.isLoaded()) {
			GameRegistry.addRecipe(BlockRegistry.DECOTANK.getCraftedMetadataProduct(4, 1), "SGS", "GGG", "SGS", 'S', ItemStacks.steelingot, 'G', new ItemStack(TinkerBlockHandler.getInstance().clearPaneID, 1, 0));

			String f = "molten hsla";
			int temp = 750;
			ItemStack bk = ItemStacks.steelblock;
			int base = SmelteryRecipeHandler.INGOT_AMOUNT;
			SmelteryRecipeHandler.addIngotMelting(ItemStacks.steelingot, bk, temp, f);
			SmelteryRecipeHandler.addIngotCasting(ItemStacks.steelingot, f, 40);
			SmelteryRecipeHandler.addMelting(ItemStacks.steelblock, bk, temp, base*9, f);
			SmelteryRecipeHandler.addMelting(ItemStacks.scrap, bk, temp, base/9, f);
			SmelteryRecipeHandler.addMelting(ItemStacks.ballbearing, bk, temp, base/4, f);
			SmelteryRecipeHandler.addMelting(ItemStacks.wormgear, bk, temp, base*5/DifficultyEffects.PARTCRAFT.getInt()+2*27/DifficultyEffects.PARTCRAFT.getInt(), f);
			SmelteryRecipeHandler.addBlockCasting(ItemStacks.steelblock, base*9, f, 120);
			SmelteryRecipeHandler.addReversibleCasting(ItemStacks.gearCast, ItemStacks.steelgear, bk, temp, f, base*5/DifficultyEffects.PARTCRAFT.getInt(), 80);
			SmelteryRecipeHandler.addReversibleCasting(ItemStacks.drillCast, ItemStacks.drill, bk, temp, f, base*7, 80);
			SmelteryRecipeHandler.addReversibleCasting(ItemStacks.panelCast, ItemStacks.basepanel, bk, temp, f, base*3/DifficultyEffects.PARTCRAFT.getInt(), 80);
			SmelteryRecipeHandler.addReversibleCasting(ItemStacks.shaftCast, ItemStacks.shaftitem, bk, temp, f, base*3/DifficultyEffects.PARTCRAFT.getInt(), 80);
			SmelteryRecipeHandler.addReversibleCasting(ItemStacks.propCast, ItemStacks.prop, bk, temp, f, base*3, 80);

			SmelteryRecipeHandler.addMelting(ItemStacks.ironscrap, new ItemStack(Blocks.iron_block), 600, base, "iron.molten");

			//Bedrock parts
			int id = ExtraConfigIDs.BEDROCKID.getValue();
			int id2 = ExtraConfigIDs.HSLAID.getValue();
			for (int i = 0; i < ToolParts.partList.length; i++) {
				ToolParts p = ToolParts.partList[i];
				if (TinkerMaterialHelper.instance.isPartEnabled(id, p)) {
					ItemStack is = p.getItem(id);
					if (is != null) {
						ItemStack part = p.getItem(id2);
						RecipesBlastFurnace.getRecipes().add3x3Crafting(is, 1000, 4, 0, " D ", "DPD", " D ", 'D', ItemStacks.bedrockdust, 'P', part);
					}
				}
			}
			for (int i = 0; i < WeaponParts.partList.length; i++) {
				WeaponParts p = WeaponParts.partList[i];
				if (TinkerMaterialHelper.instance.isPartEnabled(id, p)) {
					ItemStack is = p.getItem(id);
					if (is != null) {
						ItemStack part = p.getItem(id2);
						RecipesBlastFurnace.getRecipes().add3x3Crafting(is, 1000, 4, 0, " D ", "DPD", " D ", 'D', ItemStacks.bedrockdust, 'P', part);
					}
				}
			}
		}

		/* No longer necessary
		if (ModList.THERMALEXPANSION.isLoaded()) {
			ItemStack hardGlass = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "hardenedGlass", 1);
			if (hardGlass == null)
				hardGlass = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "glassHardened", 1);
			if (ConfigRegistry.TEGLASS.getState() && hardGlass != null) {
				//GameRegistry.addRecipe(new ShapedOreRecipe(hardGlass, " L ", "LGL", " L ", 'L', "nuggetLead", 'G', RotaryCraft.obsidianglass));
				ReikaRecipeHelper.replaceIngredientInAllRecipes(hardGlass, BlockRegistry.BLASTGLASS.getStackOf(), true);
			}
		}
		 */

		if (ModList.PROJRED.isLoaded()) {
			ItemStack saw = ItemRegistry.BEDSAW.getStackOf();
			ItemStack siliconWafer = GameRegistry.findItemStack(ModList.PROJRED.modLabel, "projectred.core.part", 1);
			ItemStack siliconCylinder = GameRegistry.findItemStack(ModList.PROJRED.modLabel, "projectred.core.part", 1);
			siliconWafer.setItemDamage(12);
			siliconCylinder.setItemDamage(11);

			GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(siliconWafer, 16), "S", "s", 'S', saw, 's', siliconCylinder);
		}

		if (ModList.THAUMCRAFT.isLoaded()) {
			addThaumcraft();
		}

		if (ModList.BOTANIA.isLoaded()) {
			ItemStack livingwood = new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingwood"));
			ItemStack livingwoodslab = new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingwood0Slab"));
			ItemStack livingrock = new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingrock"));
			ItemStack livingrockslab = new ItemStack(GameRegistry.findBlock(ModList.BOTANIA.modLabel, "livingrock0Slab"));

			GameRegistry.addRecipe(ItemStacks.livingwoodgear, " s ", "sss", " s ", 's', livingwood);
			GameRegistry.addRecipe(ItemStacks.livingwoodrod, "  s", " s ", "s  ", 's', livingwood);

			GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.livingrockgear, 2), " s ", "sss", " s ", 's', livingrock);
			GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.livingrockrod, 2), "  s", " s ", "s  ", 's', livingrock);

			GameRegistry.addRecipe(ItemStacks.livingwood2x, new Object[]{
					" GB", "BG ", 'B', ItemStacks.livingwoodrod, 'G', ItemStacks.livingwoodgear});
			GameRegistry.addRecipe(ItemStacks.livingwood4x, new Object[]{
					" GB", "BG ", 'B', ItemStacks.livingwoodrod, 'G', ItemStacks.livingwood2x});
			GameRegistry.addRecipe(ItemStacks.livingwood8x, new Object[]{
					" gB", "BG ", 'B', ItemStacks.livingwoodrod, 'G', ItemStacks.livingwood4x, 'g', ItemStacks.livingwood2x});
			GameRegistry.addRecipe(ItemStacks.livingwood16x, new Object[]{
					" gB", "BG ", 'B', ItemStacks.livingwoodrod, 'G', ItemStacks.livingwood8x, 'g', ItemStacks.livingwood2x});
			GameRegistry.addRecipe(ItemStacks.livingwood16x, new Object[]{
					"BGB", "BGB", 'B', ItemStacks.livingwoodrod, 'G', ItemStacks.livingwood4x});

			GameRegistry.addRecipe(ItemStacks.livingrock2x, new Object[]{
					" GB", "BG ", 'B', ItemStacks.livingrockrod, 'G', ItemStacks.livingrockgear});
			GameRegistry.addRecipe(ItemStacks.livingrock4x, new Object[]{
					" GB", "BG ", 'B', ItemStacks.livingrockrod, 'G', ItemStacks.livingrock2x});
			GameRegistry.addRecipe(ItemStacks.livingrock8x, new Object[]{
					" gB", "BG ", 'B', ItemStacks.livingrockrod, 'G', ItemStacks.livingrock4x, 'g', ItemStacks.livingrock2x});
			GameRegistry.addRecipe(ItemStacks.livingrock16x, new Object[]{
					" gB", "BG ", 'B', ItemStacks.livingrockrod, 'G', ItemStacks.livingrock8x, 'g', ItemStacks.livingrock2x});
			GameRegistry.addRecipe(ItemStacks.livingrock16x, new Object[]{
					"BGB", "BGB", 'B', ItemStacks.livingrockrod, 'G', ItemStacks.livingrock4x});

			ItemStack gear;
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(0));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingwood, 'G', ItemStacks.livingwood2x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(5));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingwood, 'G', ItemStacks.livingwood4x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(10));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingwood, 'G', ItemStacks.livingwood8x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(15));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingwood, 'G', ItemStacks.livingwood16x});

			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(1));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingrockslab, 'G', ItemStacks.livingrock2x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(6));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingrockslab, 'G', ItemStacks.livingrock4x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(11));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingrockslab, 'G', ItemStacks.livingrock8x});
			gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(16));
			gear.stackTagCompound.setBoolean("living", true);
			MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', livingrockslab, 'G', ItemStacks.livingrock16x});
		}

		if (ModList.MINECHEM.isLoaded()) {
			RecipeAPI.addDecompositionRecipe(ItemRegistry.ETHANOL.getStackOf(), "8 ethanol");
			RecipeAPI.addDecompositionRecipe(ItemStacks.steelingot.copy(), "8 Fe", "1 S", "0.25 P", "1 C", "0.5 Si");
			RecipeAPI.addDecompositionRecipe(ItemStacks.aluminumpowder.copy(), "1 Al");
			MoleculeHelper.addMoleculeWithDecomposition("octane", "8 C", "18 H");
			MoleculeHelper.addMoleculeWithDecomposition("hexane", "6 C", "14 H");
			MoleculeHelper.addMoleculeWithDecomposition("decane", "10 C", "22 H");
			MoleculeHelper.addMoleculeWithDecomposition("methylhexane", "7 C", "16 H");
			MoleculeHelper.addMoleculeWithDecomposition("cyclohexane", "6 C", "12 H");
			MoleculeHelper.addMoleculeWithDecomposition("methylcyclohexane", "7 C", "14 H");
			MoleculeHelper.addMoleculeWithDecomposition("benzene", "6 C", "6 H");
			MoleculeHelper.addMoleculeWithDecomposition("napthalene", "10 C", "8 H");
			RecipeAPI.addDecompositionFluidRecipe(new FluidStack(FluidRegistry.getFluid("rc jet fuel"), 100), "4 octane", "2.2 hexane", "2.1 decane", "3.7 methylhexane", "1.2 cyclohexane", "2.3 methylcyclohexane", "0.5 benzene", "1.3 toluene", "0.5 napthalene");
		}
	}

	@ModDependent(ModList.THAUMCRAFT)
	private static void addThaumcraft() {
		ReikaThaumHelper.addBookCategory(new ResourceLocation("rotarycraft", "textures/blocks/worktable_top.png"), "rotarycraft");

		MathExpression cost = new MathExpression() {
			@Override
			public double evaluate(double arg) throws ArithmeticException {
				return arg/5D;
			}

			@Override
			public double getBaseValue() {
				return 0;
			}

			@Override
			public String toString() {
				return "/5";
			}
		};

		//ItemStack in = ItemRegistry.BEDHELM.getEnchantedStack();
		//ItemStack out = ItemRegistry.BEDREVEAL.getEnchantedStack();
		ItemStack meter = GameRegistry.findItemStack(ModList.THAUMCRAFT.modLabel, "ItemThaumometer", 1);
		AspectList al = new AspectList();
		al.add(Aspect.MIND, 10);
		al.add(Aspect.SENSES, 25);
		al.add(Aspect.AURA, 10);
		al.add(Aspect.ARMOR, 25);
		al.add(Aspect.MAGIC, 25);
		ItemStack[] recipe = {
				meter,
				new ItemStack(Items.gold_ingot),
				ThaumItemHelper.ItemEntry.SALIS.getItem(),
				ThaumOreHandler.getInstance().getItem(ModOreList.CINNABAR),
				meter,
				new ItemStack(Items.gold_ingot),
				ThaumItemHelper.ItemEntry.SALIS.getItem(),
				ThaumOreHandler.getInstance().getItem(ModOreList.CINNABAR),

		};
		String desc = "Combining the protection of bedrock with the power of a Thaumometer";
		//InfusionRecipe ir = ThaumcraftApi.addInfusionCraftingRecipe("GOGGLES", out, 7, al, in, recipe);
		InfusionRecipe ir = new BedrockRevealingInfusion(7, al, recipe);
		ThaumcraftApi.getCraftingRecipes().add(ir);
		String page = FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? RotaryDescriptions.getParentPage()+"thaum.xml" : "";
		ReikaThaumHelper.addInfusionRecipeBookEntryViaXML("BEDREVEAL", desc, "rotarycraft", ir, cost, 0, 0, RotaryCraft.class, page).setParents("GOGGLES");
	}

	public static ItemStack getConverterGatingItem() {
		ItemStack ctr = ItemStacks.steelingot;
		switch(ConfigRegistry.LATEDYNAMO.getValue()) {
			case 1:
				ctr = ItemStacks.redgoldingot;
				break;
			case 2:
				ctr = ItemStacks.tungsteningot;
				break;
			case 3:
				ctr = ItemStacks.bedingot;
				break;
			case 4:
				if (ModList.REACTORCRAFT.isLoaded())
					ctr = CraftingItems.ALLOY.getItem();
				break;
			case 5:
				if (ModList.REACTORCRAFT.isLoaded())
					ctr = ReactorStacks.maxMagnet;
				break;
			default:
				break;
		}
		return ctr;
	}

	private static void addMachines() {
		MachineRegistry.COMPACTOR.addCrafting("SPS", "PGP", "#P#", '#', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'P', ItemStacks.presshead, 'G', ItemStacks.gearunit16);

		MachineRegistry.FAN.addOreRecipe("WWW", "WIW", "#s#", '#', ItemStacks.basepanel, 'W', "plankWood", 'I', ItemStacks.impeller, 's', ItemStacks.shaftitem);

		MachineRegistry.AEROSOLIZER.addCrafting("BRB", "RIR", "BRB", 'B', ItemStacks.basepanel, 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'I', ItemStacks.impeller);

		MachineRegistry.HEATRAY.addCrafting("OOO", "BLb", "#P#", '#', ItemStacks.basepanel, 'B', ItemStacks.bulb, 'b', ItemStacks.barrel, 'P', ItemStacks.power, 'L', ItemStacks.lens, 'O', Blocks.obsidian);

		MachineRegistry.FLOODLIGHT.addCrafting("ISO", "Ggd", "I#O", '#', ItemStacks.basepanel, 'I', Items.iron_ingot, 'd', Items.gold_ingot, 'S', ItemStacks.steelingot, 'G', Blocks.glass, 'g', Blocks.glowstone, 'O', Blocks.obsidian);

		if (ReikaItemHelper.oreItemsExist("ingotCopper", "ingotSilver"))
			MachineRegistry.FLOODLIGHT.addOreRecipe("ISO", "Ggd", "I#O", '#', ItemStacks.basepanel, 'I', "ingotSilver", 'd', "ingotCopper", 'S', ItemStacks.steelingot, 'G', Blocks.glass, 'g', Blocks.glowstone, 'O', Blocks.obsidian);

		MachineRegistry.SHAFT.addMetaCrafting(RotaryNames.getNumberShaftTypes()-1, " S ", "SSS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem); //Shaft cross

		addRecipeToBoth(MachineRegistry.WORKTABLE.getCraftedProduct(), " C ", "SBS", "srs", 'r', Items.redstone, 'S', ItemStacks.steelingot, 'B', Blocks.brick_block, 'C', Blocks.crafting_table, 's', ReikaItemHelper.stoneSlab);

		MachineRegistry.BEVELGEARS.addSizedCrafting(4, "ISB", "SGB", "BBB", 'B', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear);

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("bedrock", false);
		MachineRegistry.SPLITTER.addSizedNBTCrafting(nbt, 2, "ISP", "SGP", "ISP", 'P', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear);

		MachineRegistry.CLUTCH.addCrafting("S", "M", "R", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'R', Items.redstone);
		MachineRegistry.CLUTCH.addCrafting("S", "R", 'S', MachineRegistry.SHAFT.getCraftedMetadataProduct(2), 'R', Items.redstone);

		MachineRegistry.DYNAMOMETER.addSizedCrafting(2, " S ", " E ", " Ms", 's', ItemStacks.screen, 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'E', Items.ender_pearl);
		MachineRegistry.DYNAMOMETER.addSizedCrafting(4, " S ", " E ", " Ms", 's', ItemStacks.screen, 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'E', ItemStacks.silicon);

		MachineRegistry.BEDROCKBREAKER.addCrafting("BDt", "BSO", "BDt", 't', ItemStacks.tungsteningot, 'S', ItemStacks.steelingot, 'D', Items.diamond, 'O', Blocks.obsidian, 'B', ItemStacks.basepanel);

		MachineRegistry.FERMENTER.addCrafting("BPB", "PIP", "BPB", 'B', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel);
		MachineRegistry.FERMENTER.addOreRecipe("BPB", "PIP", "BPB", 'B', "ingotTin", 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel);

		MachineRegistry.GRINDER.addCrafting("B B", "SGS", "PPP", 'B', ItemStacks.steelingot, 'G', ItemStacks.steelgear, 'P', ItemStacks.basepanel, 'S', ItemStacks.saw);

		MachineRegistry.RESERVOIR.addCrafting("B B", "B B", "BBB", 'B', ItemStacks.basepanel);
		nbt = new NBTTagCompound();
		nbt.setBoolean("cover", true);
		MachineRegistry.RESERVOIR.addNBTCrafting(nbt, "BPB", "B B", "BBB", 'B', ItemStacks.basepanel, 'P', Blocks.glass_pane);

		MachineRegistry.FIREWORK.addCrafting("BEB", "BDB", "BRB", 'B', ItemStacks.basepanel, 'R', Items.redstone, 'E', Items.ender_eye, 'D', Blocks.dispenser);

		MachineRegistry.AUTOBREEDER.addCrafting("B B", "BBB", 'B', ItemStacks.basepanel);

		MachineRegistry.FRACTIONATOR.addCrafting("GFG", "GIG", "GPG", 'P', ItemStacks.basepanel, 'I', ItemStacks.mixer, 'G', Items.gold_ingot, 'F', ItemStacks.fuelline);

		MachineRegistry.BAITBOX.addCrafting("BBB", "BAB", "BBB", 'B', Blocks.iron_bars, 'A', MachineRegistry.AUTOBREEDER.getCraftedProduct());

		MachineRegistry.WINDER.addCrafting(" ss", " hg", "ppp", 'h', ItemStacks.shaftitem, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel);

		MachineRegistry.ECU.addCrafting("IPI", "IGI", "IRI", 'I', ItemStacks.steelingot, 'G', Items.gold_ingot, 'P', ItemStacks.pcb, 'R', Items.redstone);

		if (ReikaItemHelper.oreItemsExist("ingotCopper", "ingotElectrum"))
			MachineRegistry.ECU.addOreRecipe("IPI", "IGI", "IRI", 'I', ItemStacks.steelingot, 'G', "ingotElectrum", 'P', ItemStacks.pcb, 'R', Items.redstone);

		MachineRegistry.WOODCUTTER.addCrafting("IS ", "PGS", "PPI", 'I', ItemStacks.steelingot, 'S', ItemStacks.saw, 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit);

		MachineRegistry.VACUUM.addCrafting("SwS", "wIw", "SCS", 'C', Blocks.chest, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'w', ReikaItemHelper.blackWool);

		MachineRegistry.BORER.addCrafting("SSS", "DGC", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', ItemStacks.drill, 'G', ItemStacks.gearunit, 'C', ItemStacks.pcb);

		MachineRegistry.SPRINKLER.addSizedCrafting(4, " s ", " p ", " i ", 's', ItemStacks.steelingot, 'p', ItemStacks.pipe, 'i', ItemStacks.impeller);
		MachineRegistry.SPRINKLER.addSizedOreRecipe(4, " s ", " p ", " i ", 's', "ingotTin", 'p', ItemStacks.pipe, 'i', ItemStacks.impeller);

		MachineRegistry.SPAWNERCONTROLLER.addCrafting("PCP", "OGO", "g g", 'O', Blocks.obsidian, 'P', ItemStacks.basepanel, 'G', Items.gold_ingot, 'g', Blocks.glowstone, 'C', ItemStacks.pcb);

		MachineRegistry.PLAYERDETECTOR.addCrafting("LRL", "OGO", "OPO", 'L', ReikaItemHelper.lapisDye, 'R', ItemStacks.radar, 'O', Blocks.obsidian, 'P', ItemStacks.basepanel, 'G', Items.gold_ingot);

		MachineRegistry.OBSIDIAN.addCrafting("SpS", "PMP", "BBB", 'M', ItemStacks.mixer, 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'p', Blocks.glass_pane, 'P', ItemStacks.pipe);
		MachineRegistry.OBSIDIAN.addOreRecipe("SpS", "PMP", "BBB", 'M', ItemStacks.mixer, 'B', ItemStacks.basepanel, 'S', "ingotInvar", 'p', Blocks.glass_pane, 'P', ItemStacks.pipe);

		MachineRegistry.HEATER.addCrafting("sBs", "prp", "scs", 'r', ItemStacks.tungsteningot, 's', ItemStacks.steelingot, 'B', Blocks.iron_bars, 'p', ItemStacks.basepanel, 'c', ItemStacks.combustor);

		MachineRegistry.GPR.addCrafting("SsS", "PCP", "SRS", 'S', ItemStacks.steelingot, 's', ItemStacks.screen, 'P', ItemStacks.basepanel, 'R', ItemStacks.radar, 'C', ItemStacks.pcb);

		MachineRegistry.PULSEJET.addCrafting("OCD", "PcO", "BBB", 'B', ItemStacks.basepanel, 'O', Blocks.obsidian, 'C', ItemStacks.compressor, 'D', ItemStacks.diffuser, 'c', ItemStacks.combustor, 'P', ItemStacks.pipe);

		MachineRegistry.EXTRACTOR.addOreRecipe("SWS", "siD", "PIN", 'D', ItemStacks.drill, 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.shaftitem, 's', Blocks.stone, 'i', ItemStacks.impeller, 'N', Blocks.netherrack, 'W', "plankWood");

		MachineRegistry.LIGHTBRIDGE.addCrafting("GgG", "BgS", "BBD", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', Items.diamond, 'G', Items.gold_ingot, 'g', Blocks.glass);

		MachineRegistry.PILEDRIVER.addCrafting("PGP", "gFg", "PDP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit8, 'g', ItemStacks.shaftitem, 'F', ItemStacks.flywheelcore3, 'D', ItemStacks.drill);

		MachineRegistry.PUMP.addCrafting("SGS", "pIp", "PpP", 'P', ItemStacks.basepanel, 'p', ItemStacks.pipe, 'I', ItemStacks.impeller, 'G', Blocks.glass_pane, 'S', ItemStacks.steelingot);

		MachineRegistry.MOBRADAR.addCrafting(" rs", " g ", "pcp", 'r', ItemStacks.radar, 's', ItemStacks.screen, 'c', ItemStacks.pcb, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel);

		MachineRegistry.TNTCANNON.addCrafting("sgc", "pcp", "pCr", 'g', Blocks.redstone_block, 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Blocks.chest, 'p', ItemStacks.basepanel);

		MachineRegistry.SONICWEAPON.addCrafting("psp", "sts", "psp", 't', ItemStacks.turbine, 's', ItemStacks.sonar, 'p', ItemStacks.basepanel);

		MachineRegistry.FORCEFIELD.addCrafting("lnl", "ddd", "sgs", 'd', Items.diamond, 's', ItemStacks.basepanel, 'n', Items.nether_star, 'g', Items.gold_ingot, 'l', ReikaItemHelper.lapisDye);

		MachineRegistry.MUSICBOX.addSizedCrafting(4, "sns", "ncn", "sns", 'n', Blocks.noteblock, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb);
		MachineRegistry.MUSICBOX.addSizedOreRecipe(4, "sns", "ncn", "sns", 'n', Blocks.noteblock, 's', "ingotSilver", 'c', ItemStacks.pcb);

		MachineRegistry.WEATHERCONTROLLER.addCrafting("s s", "sls", "pcp", 'l', Blocks.daylight_detector, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel);

		MachineRegistry.MOBHARVESTER.addCrafting("shs", "sps", 'h', ItemStacks.igniter, 'p', Items.ender_pearl, 's', ItemStacks.basepanel);

		MachineRegistry.PROJECTOR.addCrafting("sss", "gcl", "ppp", 'c', ItemStacks.pcb, 's', ItemStacks.steelingot, 'g', Blocks.glass, 'l', Blocks.glowstone, 'p', ItemStacks.basepanel);

		MachineRegistry.REFRESHER.addCrafting("ses", "epe", "ses", 'p', Items.ender_pearl, 's', ItemStacks.steelingot, 'e', ReikaItemHelper.lapisDye);

		MachineRegistry.CAVESCANNER.addCrafting("sps", "pcp", "sns", 'n', ItemStacks.sonar, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel);

		MachineRegistry.SCALECHEST.addCrafting("sss", "scs", "sss", 'c', Blocks.chest, 's', ItemStacks.steelingot);

		MachineRegistry.SPILLER.addCrafting("sps", "s s", 'p', ItemStacks.pipe, 's', ItemStacks.steelingot);

		MachineRegistry.FILLER.addCrafting("sss", "sps", "s s", 'p', Blocks.chest, 's', ItemStacks.steelingot);

		MachineRegistry.SMOKEDETECTOR.addCrafting(" S ", "RRR", " N ", 'S', ReikaItemHelper.stoneSlab, 'R', Items.redstone, 'N', Blocks.noteblock);

		MachineRegistry.IGNITER.addCrafting("OGO", "GCG", "OGO", 'O', Blocks.obsidian, 'G', Items.gold_ingot, 'C', ItemStacks.combustor);

		MachineRegistry.CONTAINMENT.addCrafting("lnl", "ddd", "sgs", 'd', Items.diamond, 's', ItemStacks.basepanel, 'n', Items.nether_star, 'g', Items.gold_ingot, 'l', ReikaItemHelper.purpleDye);

		MachineRegistry.MAGNETIZER.addCrafting("p p", "gmg", "prp", 'r', Items.redstone, 'p', ItemStacks.basepanel, 'm', ItemStacks.mount, 'g', ItemStacks.goldcoil);

		MachineRegistry.FREEZEGUN.addCrafting(" ss", "iig", "sb ", 'b', ItemStacks.railbase, 'i', Blocks.ice, 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit);

		MachineRegistry.SCREEN.addCrafting("sss", "mcs", "ppp", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'm', ItemStacks.screen, 'c', ItemStacks.pcb);

		MachineRegistry.CCTV.addCrafting(" g ", "brs", " p ", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'b', Blocks.glass_pane, 'r', Items.redstone, 'g', Items.gold_ingot);

		MachineRegistry.PURIFIER.addCrafting("sbs", "prp", "sps", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'r', Items.redstone, 'b', Blocks.iron_bars);

		MachineRegistry.MIRROR.addCrafting("bmb", " g ", "pcp", 'b', BlockRegistry.BLASTGLASS.getStackOf(), 'p', ItemStacks.basepanel, 'c', ItemStacks.pcb, 'm', ItemStacks.mirror, 'g', ItemStacks.steelgear);

		MachineRegistry.SOLARTOWER.addOreRecipe("pPp", "iPi", "pPp", 'p', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'i', "dyeBlack");

		MachineRegistry.RAILGUN.addCrafting(" H ", " A ", " B ", 'B', ItemStacks.railbase, 'A', ItemStacks.railaim, 'H', ItemStacks.railhead);

		MachineRegistry.LASERGUN.addCrafting("CLB", "APG", " b ", 'b', ItemStacks.railbase, 'C', ItemStacks.bulb, 'L', ItemStacks.lens, 'P', ItemStacks.power, 'B', ItemStacks.barrel, 'A', ItemStacks.railaim, 'G', ItemStacks.gearunit);

		MachineRegistry.ITEMCANNON.addCrafting("s c", "pcp", "pCr", 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.gearunit, 'r', Blocks.chest, 'p', ItemStacks.basepanel);

		MachineRegistry.BLOCKCANNON.addCrafting("s c", "pcp", "pCr", 'C', ItemStacks.compressor,  'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Blocks.chest, 'p', ItemStacks.basepanel);

		MachineRegistry.FRICTION.addCrafting("S  ", "Sss", "SPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem);

		MachineRegistry.LANDMINE.addCrafting(" P ", "RGR", "SIS", 'P', Blocks.stone_pressure_plate, 'S', ItemStacks.steelingot, 'I', ItemStacks.igniter, 'R', Items.redstone, 'G', Items.gold_ingot);

		MachineRegistry.BUCKETFILLER.addCrafting("SPS", "PCP", "SPS", 'P', ItemStacks.pipe, 'S', ItemStacks.steelingot, 'C', Blocks.chest);

		MachineRegistry.SPYCAM.addCrafting("SCS", "PRP", "SGS", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Blocks.glass_pane, 'R', Items.redstone);

		MachineRegistry.COOLINGFIN.addSizedCrafting(3, "SSS", "SSS", "PPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem);
		MachineRegistry.COOLINGFIN.addSizedOreRecipe(2, "SSS", "SSS", "PPP", 'P', "ingotTin", 'S', "ingotCopper");

		MachineRegistry.SELFDESTRUCT.addCrafting("STS", "TCs", "STS", 'T', Blocks.tnt, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem, 'C', ItemStacks.pcb);

		//MachineRegistry.DISPLAY.addCrafting("SES", "SCS", " P ", 'P', ItemStacks.basepanel, 'E', Items.ender_pearl, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb);
		MachineRegistry.DISPLAY.addCrafting("SES", "SCS", " P ", 'P', ItemStacks.basepanel, 'E', ItemStacks.silicon, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb);

		MachineRegistry.LAMP.addCrafting("SGS", "GgG", "SGS", 'S', ItemStacks.steelingot, 'G', Blocks.glass, 'g', Blocks.glowstone);

		MachineRegistry.MULTICLUTCH.addCrafting("PSP", "SGS", "RSR", 'R', Items.redstone, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.gearunit, 'P', ItemStacks.basepanel);

		MachineRegistry.FUELENHANCER.addCrafting("PGP", "gMg", "PGP", 'G', Blocks.glass_pane, 'M', ItemStacks.mixer, 'P', ItemStacks.basepanel, 'g', Items.gold_ingot);

		MachineRegistry.LINEBUILDER.addCrafting("sbs", "sps", "PgP", 'g', ItemStacks.gearunit, 'p', Blocks.piston, 'P', ItemStacks.basepanel, 'b', ItemStacks.bedingot, 's', ItemStacks.steelingot);

		MachineRegistry.TERRAFORMER.addCrafting("SsS", "ici", "PiP", 'i', ItemStacks.impeller, 'S', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'P', ItemStacks.basepanel, 's', ItemStacks.screen);

		MachineRegistry.EMP.addCrafting("GDG", "GsG", "PnP", 'P', ItemStacks.basepanel, 'n', Items.nether_star, 'G', ItemStacks.goldcoil, 'D', Blocks.diamond_block, 's', ItemStacks.shaftcore);

		MachineRegistry.ARROWGUN.addCrafting("SSS", "BDB", "SBS", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', Blocks.dispenser);

		MachineRegistry.FERTILIZER.addCrafting("PIP", " S ", "BCB", 'P', ItemStacks.pipe, 'S', ItemStacks.shaftitem, 'I', ItemStacks.impeller, 'C', Blocks.chest, 'B', ItemStacks.basepanel);

		MachineRegistry.LAVAMAKER.addCrafting("SRS", "PGP", "SsS", 's', ItemStacks.shaftitem, 'S', ItemStacks.steelingot, 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'P', ItemStacks.basepanel, 'G', ItemStacks.steelgear);

		MachineRegistry.BEAMMIRROR.addCrafting(" m ", " s ", " p ", 'p', ItemStacks.basepanel, 'm', ItemStacks.mirror, 's', ItemStacks.steelingot);

		MachineRegistry.VALVE.addSizedCrafting(4, "sGs", "OGO", "sGs", 'O', Blocks.redstone_block, 'G', Blocks.glass, 's', ItemStacks.steelingot);

		MachineRegistry.BYPASS.addSizedCrafting(4, "OGO", "OGO", "OGO", 'O', Blocks.sandstone, 'G', Blocks.glass, 's', ItemStacks.steelingot);

		MachineRegistry.SEPARATION.addSizedCrafting(4, "sGs", "OGO", "sGs", 'O', Blocks.lapis_block, 'G', Blocks.glass, 's', ItemStacks.steelingot);

		MachineRegistry.SONICBORER.addCrafting("ss ", "Icp", "bbb", 'p', ItemStacks.pipe, 's', ItemStacks.steelingot, 'c', ItemStacks.compressor, 'b', ItemStacks.basepanel, 'I', Blocks.iron_bars);

		MachineRegistry.AIRGUN.addCrafting("sps", "I S", "sps", 'I', ItemStacks.impeller, 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'S', ItemStacks.sonar);

		MachineRegistry.FUELENGINE.addCrafting("CGC", "fgs", "bIb", 'g', ItemStacks.gearunit8, 'C', ItemStacks.cylinder, 'G', ItemStacks.tungsteningot, 'f', ItemStacks.gearunit, 'b', ItemStacks.basepanel, 'I', ItemStacks.impeller, 's', ItemStacks.shaftcore);

		MachineRegistry.AGGREGATOR.addCrafting("SPS", "GCG", "SsS", 's', ItemStacks.shaftitem, 'G', Blocks.glass_pane, 'S', ItemStacks.steelingot, 'P', ItemStacks.basepanel, 'C', ItemStacks.compressor);

		MachineRegistry.FILLINGSTATION.addCrafting("ppS", " iR", "ppB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'i', ItemStacks.impeller, 'p', ItemStacks.pipe, 'R', MachineRegistry.RESERVOIR.getCraftedProduct());

		MachineRegistry.BELT.addSizedCrafting(2, "sBs", " G ", "sBs", 'B', ItemStacks.basepanel, 'G', ItemStacks.hub, 's', ItemStacks.steelingot);

		MachineRegistry.VANDEGRAFF.addCrafting("shs", "gbg", "php", 'h', ItemStacks.hub, 'p', ItemStacks.basepanel, 'b', ItemStacks.belt, 'g', Blocks.glass_pane, 's', ItemStacks.steelingot);

		MachineRegistry.DISTILLER.addCrafting("PGP", "gMg", "PGP", 'G', Blocks.glass_pane, 'M', ItemStacks.mixer, 'P', ItemStacks.basepanel, 'g', Items.iron_ingot);

		MachineRegistry.BIGFURNACE.addCrafting("SFS", "FRF", "SRS", 'S', ItemStacks.basepanel, 'F', Blocks.furnace, 'R', MachineRegistry.RESERVOIR.getCraftedProduct());

		MachineRegistry.SUCTION.addSizedCrafting(4, "SGS", "SGS", "SGS", 'S', Blocks.nether_brick, 'G', Blocks.glass);

		MachineRegistry.SORTING.addCrafting("SHS", " C ", "P P", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'H', Blocks.hopper, 'C', ItemStacks.pcb);

		MachineRegistry.CRYSTALLIZER.addCrafting("SFS", "FIF", "BBB", 'S', ItemStacks.steelingot, 'B', ItemStacks.basepanel, 'F', MachineRegistry.COOLINGFIN.getCraftedProduct(), 'I', ItemStacks.impeller);

		MachineRegistry.POWERBUS.addSizedCrafting(4, "SMS", "MCM", "SMS", 'S', ItemStacks.steelingot, 'M', ItemStacks.bearing, 'C', ItemStacks.belt);

		MachineRegistry.BUSCONTROLLER.addCrafting("SMS", "MCM", "SMS", 'S', ItemStacks.steelingot, 'M', ItemStacks.bearing, 'C', ItemStacks.pcb);

		MachineRegistry.PARTICLE.addSizedCrafting(4, "SDS", "PCP", "SIS", 'S', ItemStacks.steelingot, 'P', ItemStacks.basepanel, 'C', ItemStacks.pcb, 'D', Blocks.dispenser, 'I', ItemStacks.impeller);
		MachineRegistry.PARTICLE.addSizedOreRecipe(4, "SDS", "PCP", "SIS", 'S', "ingotTin", 'P', ItemStacks.basepanel, 'C', ItemStacks.pcb, 'D', Blocks.dispenser, 'I', ItemStacks.impeller);

		MachineRegistry.LAWNSPRINKLER.addCrafting("PPP", " P ", "BIB", 'I', ItemStacks.impeller, 'P', ItemStacks.pipe, 'B', ItemStacks.basepanel);

		MachineRegistry.GRINDSTONE.addCrafting("S S", "sBs", "ppp", 'p', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'S', ItemStacks.steelingot, 'B', Blocks.stone);

		MachineRegistry.BLOWER.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "BBB", "PIP", "BBB", 'B', ItemStacks.basepanel, 'I', ItemStacks.impeller, 'P', ItemStacks.pipe);

		MachineRegistry.DEFOLIATOR.addCrafting("P P", "SPS", "BIB", 'B', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'I', ItemStacks.impeller, 'S', ItemStacks.steelingot);

		MachineRegistry.REFRIGERATOR.addCrafting("SPS", "CcD", "pPp", 'p', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'D', ItemStacks.diffuser, 'C', ItemStacks.compressor, 'c', ItemStacks.condenser, 'S', ItemStacks.steelingot);

		MachineRegistry.COMPOSTER.addCrafting(" S ", "S S", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot);
		MachineRegistry.COMPOSTER.addOreRecipe(" S ", "S S", "BBB", 'B', ItemStacks.basepanel, 'S', "ingotTin");

		MachineRegistry.GASTANK.addCrafting("SIS", "PRP", "PPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'R', MachineRegistry.RESERVOIR.getCraftedProduct());

		MachineRegistry.CRAFTER.addCrafting("SCS", "PcP", "SPS", 'S', ItemStacks.steelingot, 'C', Blocks.crafting_table, 'P', ItemStacks.basepanel, 'c', ItemStacks.pcb);

		MachineRegistry.ANTIAIR.addCrafting("sss", "ppc", " Ba", 'p', ItemStacks.pipe, 'c', ItemStacks.compressor, 's', ItemStacks.steelingot, 'a', ItemStacks.railaim, 'B', ItemStacks.railbase);

		MachineRegistry.PIPEPUMP.addCrafting("BBB", "PIP", "BBB", 'B', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.pipe);

		MachineRegistry.CHAIN.addSizedCrafting(2, "sBs", " G ", "sBs", 'B', ItemStacks.basepanel, 'G', ItemStacks.steelgear, 's', ItemStacks.steelingot);

		MachineRegistry.CENTRIFUGE.addCrafting("SGS", "S S", "PgP", 'P', ItemStacks.basepanel, 'g', ItemStacks.gearunit4, 'S', ItemStacks.steelingot, 'G', Blocks.glass_pane);

		MachineRegistry.DRYING.addCrafting("S S", "SPS", "S S", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot);

		MachineRegistry.WETTER.addCrafting("S S", "gmg", "SPS", 'g', Blocks.glass_pane, 'm', ItemStacks.mixer, 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot);

		MachineRegistry.CHUNKLOADER.addCrafting("sSs", "BSB", "PGP", 'B', ItemStacks.steelingot, 'S', ItemStacks.bedrockshaft, 's', Items.nether_star, 'P', ItemStacks.basepanel, 'G', ItemStacks.bedrock16x);

		MachineRegistry.DROPS.addCrafting("PSP", "PDP", "PSP", 'S', ItemStacks.steelingot, 'D', ItemStacks.drill, 'P', ItemStacks.basepanel);

		MachineRegistry.ITEMFILTER.addCrafting("sSs", "CCC", "PRP", 's', ItemStacks.steelingot, 'S', ItemStacks.screen, 'C', ItemStacks.pcb, 'R', Items.redstone, 'P', ItemStacks.basepanel);

		MachineRegistry.HYDRATOR.addOreRecipe("sls", "p p", "PpP", 's', ItemStacks.steelingot, 'p', "plankWood", 'l', Blocks.ladder, 'P', ItemStacks.basepanel);

		MachineRegistry.GATLING.addCrafting("PPG", " GA", "  B", 'B', ItemStacks.railbase, 'A', ItemStacks.railaim, 'G', ItemStacks.steelgear, 'P', ItemStacks.cylinder);

		MachineRegistry.SPILLWAY.addCrafting("S  ", "PSP", "PpP", 'S', ItemStacks.steelingot, 'P', ItemStacks.basepanel, 'p', ItemStacks.pipe);
	}

	private static void addCraftItems() {
		GameRegistry.addRecipe(ItemStacks.impeller, " S ", "SGS", " S ", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear);
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.impeller, " S ", "SGS", " S ", 'S', "ingotTin", 'G', ItemStacks.steelgear));
		GameRegistry.addRecipe(ItemStacks.compressor, "SSS", "SGS", "SSS", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear);
		GameRegistry.addRecipe(ItemStacks.turbine, "sss", "sGs", "sss", 's', ItemStacks.prop, 't', ItemStacks.tungsteningot, 'G', ItemStacks.compressor);
		GameRegistry.addRecipe(ItemStacks.diffuser, " SS", "S  ", " SS", 'S', ItemStacks.steelingot);
		GameRegistry.addRecipe(ItemStacks.radiator, "GGG", "PPP", "SSS", 'G', Items.gold_ingot, 'S', ItemStacks.steelingot, 'P', ItemStacks.pipe);
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.condenser, DifficultyEffects.SMALLERCRAFT.getInt()), "SPS", "PSP", "SPS", 'S', ItemStacks.steelingot, 'P', ItemStacks.pipe);
		GameRegistry.addRecipe(ItemStacks.goldcoil, "GGG", "GSG", "GGG", 'S', ItemStacks.steelingot, 'G', Items.gold_ingot);

		ReikaRecipeHelper.addOreRecipe(ItemStacks.goldcoil, "GGG", "GSG", "GGG", 'S', ItemStacks.steelingot, 'G', "ingotElectrum");

		GameRegistry.addRecipe(ItemStacks.combustor, "SSS", "SRS", "SGS", 'S', ItemStacks.steelingot, 'G', ItemStacks.igniter, 'R', Items.redstone);
		GameRegistry.addRecipe(ItemStacks.highcombustor, "SiS", "iRi", "SGS", 'i', ItemStacks.redgoldingot, 'S', ItemStacks.steelingot, 'G', ItemStacks.igniter, 'R', Items.redstone);

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.cylinder, 2), new Object[]{
			"SSS", "S S", "SSS", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.aluminumcylinder, 2), new Object[]{
			"SSS", "S S", "SSS", 'S', ItemStacks.silumin});

		GameRegistry.addRecipe(ItemStacks.compoundturb, new Object[]{
				" tS", "tst", "St ", 'S', ItemStacks.turbine, 's', ItemStacks.shaftcore, 't', ItemStacks.tungsteningot});
		GameRegistry.addRecipe(ItemStacks.compoundcompress, new Object[]{
				" tS", "tst", "St ", 'S', ItemStacks.compressor, 's', ItemStacks.shaftcore, 't', ItemStacks.tungsteningot});

		GameRegistry.addRecipe(ItemStacks.shaftcore, new Object[]{
				"  s", " S ", "s  ", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem});
		GameRegistry.addRecipe(ItemStacks.diamondshaftcore, new Object[]{
				"  s", " S ", "s  ", 'S', Items.diamond, 's', ItemStacks.diamondshaft});

		GameRegistry.addRecipe(ItemStacks.igniter, new Object[]{
				"G G", "SRS", "SSS", 'S', ItemStacks.steelingot, 'R', Items.redstone, 'G', Items.gold_ingot});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.igniter, "G G", "SRS", "SSS", 'S', ItemStacks.steelingot, 'R', Items.redstone, 'G', "ingotElectrum");

		GameRegistry.addRecipe(ItemStacks.waterplate, new Object[]{
				"PPP", "PPP", "SSS", 'P', ItemStacks.basepanel, 'S', ItemStacks.springingot});

		GameRegistry.addRecipe(ItemStacks.prop, new Object[]{
				" S ", " I ", " P ", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem, 'I', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.prop, new Object[]{
				" P ", " I ", " S ", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem, 'I', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.hub, new Object[]{
				"  B", " C ", "G  ", 'G', ItemStacks.steelgear, 'B', ItemStacks.bearing, 'C', ItemStacks.shaftcore});
		GameRegistry.addRecipe(ItemStacks.mirror, new Object[]{
				"GGG", "III", 'G', Blocks.glass, 'I', Items.iron_ingot});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.mirror, "GGG", "III", 'G', Blocks.glass, 'I', "ingotSilver");

		GameRegistry.addRecipe(ItemStacks.railhead, new Object[]{
				"LLL", "LGL", "LLL", 'G', ItemStacks.power, 'L', ItemStacks.lim});
		GameRegistry.addRecipe(ItemStacks.railbase, new Object[]{
				" S ", "PGP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit, 'S', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.railaim, new Object[]{
				"sds", "CRC", "sgs", 'R', ItemStacks.radar, 'C', ItemStacks.pcb, 's', ItemStacks.steelingot, 'd', Items.diamond, 'g', ItemStacks.generator});
		/*
		GameRegistry.addRecipe(ItemStacks.bedingot, new Object[]{
				" B ", "BSB", " B ", 'S', ItemStacks.steelingot, 'B', ItemStacks.bedrockdust});*/

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.basepanel, DifficultyEffects.PARTCRAFT.getInt()), new Object[]{
			"SSS", 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.mount, "S S","SBS", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot);
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.mount, "S S", "SBS", 'B', ItemStacks.basepanel, 'S', "ingotTin"));

		GameRegistry.addRecipe(ItemStacks.drill, new Object[]{
				"SSS", "SSS", " S ", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.presshead, new Object[]{
				"SOD", "ODB", "DBB", 'S', ItemStacks.steelingot, 'D', Items.diamond, 'O', Blocks.obsidian, 'B', ItemStacks.bedrockdust});
		GameRegistry.addRecipe(ItemStacks.screen, new Object[]{
				"SGS", "SCS", 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Blocks.glass_pane});
		GameRegistry.addRecipe(ItemStacks.mixer, new Object[]{
				" S ", "SIS", " S ", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller});
		GameRegistry.addRecipe(ItemStacks.saw, new Object[]{
				"S S", " C ", "S S", 'S', ItemStacks.steelingot, 'C', ItemStacks.steelgear});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.pcb, 2), new Object[]{
			"PGP", "RER", "GPG", 'P', ItemStacks.steelingot, 'G', Items.gold_ingot, 'R', Items.redstone, 'E', Items.ender_pearl});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.pcb, 3), new Object[]{
			"PGP", "RER", "GPG", 'P', ItemStacks.steelingot, 'G', Items.gold_ingot, 'R', Items.redstone, 'E', ItemStacks.silicon});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.pcb, "PGP", "RER", "GPG", 'P', ItemStacks.steelingot, 'G', "ingotElectrum", 'R', Items.redstone, 'E', "ingotCopper");

		GameRegistry.addRecipe(ItemStacks.sonar, new Object[]{
				" S ", "SNS", "RCR", 'S', ItemStacks.steelingot, 'R', Items.redstone, 'N', Blocks.noteblock, 'C', ItemStacks.pcb});
		GameRegistry.addRecipe(ItemStacks.radar, new Object[]{
				"SSS", " G ", "RMR", 'S', ItemStacks.steelingot, 'R', Items.redstone, 'G', Items.gold_ingot, 'M', EngineType.DC.getCraftedProduct()});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.belt, DifficultyEffects.BELTCRAFT.getInt()), new Object[]{
			"LLL", "LSL", "LLL", 'L', Items.leather, 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.bearing, new Object[]{
				"LLL", "LSL", "LLL", 'L', ItemStacks.ballbearing, 'S', ItemStacks.steelingot});
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ballbearing, 4), ItemStacks.steelingot);
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ballbearing, 16), "SS", "SS", 'S', ItemStacks.steelingot);

		GameRegistry.addRecipe(ItemStacks.brake, new Object[]{
				" g ", "SBS", " G ", 'g', ItemStacks.gearunit, 'G', ItemStacks.steelgear, 'S', ItemStacks.shaftitem, 'B', ItemStacks.bearing});
		GameRegistry.addRecipe(ItemStacks.tenscoil, new Object[]{
				"WWW", "WSW", "WWW", 'W', ItemRegistry.SPRING.getStackOf(), 'S', ItemStacks.shaftitem});
		GameRegistry.addRecipe(ItemStacks.bedrockcoil, new Object[]{
				"WWW", "WSW", "WWW", 'W', ItemRegistry.STRONGCOIL.getStackOf(), 'S', ItemStacks.shaftitem});

		GameRegistry.addRecipe(ItemStacks.lens, new Object[]{
				" D ", "DGD", " D ", 'D', Items.diamond, 'G', BlockRegistry.BLASTGLASS.getStackOf()});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.power, 2), new Object[]{
			"RER", "GGG", "SSS", 'R', Items.redstone, 'G', Items.gold_ingot, 'E', Items.ender_eye, 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.power, 3), new Object[]{
			"RER", "GGG", "SSS", 'R', Items.redstone, 'G', Items.gold_ingot, 'E', ItemStacks.silicon, 'S', ItemStacks.steelingot});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.power, "RER", "GGG", "SSS", 'R', Items.redstone, 'G', "ingotElectrum", 'E', "ingotCopper", 'S', ItemStacks.steelingot);

		GameRegistry.addRecipe(ItemStacks.barrel, new Object[]{
				"OOO", "gtG", "OOO", 't', ItemStacks.tungsteningot, 'O', Blocks.obsidian, 'G', BlockRegistry.BLASTGLASS.getStackOf(), 'g', Blocks.glowstone});
		GameRegistry.addRecipe(ItemStacks.bulb, new Object[]{
				"GGG", "BDB", "BRB", 'D', Items.nether_star, 'G', Blocks.glowstone, 'R', Items.redstone, 'B', Items.blaze_rod});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelgear, DifficultyEffects.PARTCRAFT.getInt()), new Object[]{
			" B ", "BBB", " B ", 'B', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.gearunit, " GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.steelgear);
		GameRegistry.addRecipe(ItemStacks.gearunit4, " GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit);
		GameRegistry.addRecipe(ItemStacks.gearunit8, " gB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit4, 'g', ItemStacks.gearunit);
		GameRegistry.addRecipe(ItemStacks.gearunit16, " gB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit8, 'g', ItemStacks.gearunit);
		GameRegistry.addRecipe(ItemStacks.gearunit16, "BGB", "BGB", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit4);

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.wood2x, new Object[]{
				" GB", "BG ", 'B', "stickWood", 'G', ItemStacks.woodgear}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.wood4x, new Object[]{
				" GB", "BG ", 'B', "stickWood", 'G', ItemStacks.wood2x}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.wood8x, new Object[]{
				" gB", "BG ", 'B', "stickWood", 'G', ItemStacks.wood4x, 'g', ItemStacks.wood2x}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.wood16x, new Object[]{
				" gB", "BG ", 'B', "stickWood", 'G', ItemStacks.wood8x, 'g', ItemStacks.wood2x}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.wood16x, new Object[]{
				"BGB", "BGB", 'B', "stickWood", 'G', ItemStacks.wood4x}));

		GameRegistry.addRecipe(ItemStacks.stone2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stonegear});
		GameRegistry.addRecipe(ItemStacks.stone4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone2x});
		GameRegistry.addRecipe(ItemStacks.stone8x, new Object[]{
				" gB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone4x, 'g', ItemStacks.stone2x});
		GameRegistry.addRecipe(ItemStacks.stone16x, new Object[]{
				" gB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone8x, 'g', ItemStacks.stone2x});
		GameRegistry.addRecipe(ItemStacks.stone16x, new Object[]{
				"BGB", "BGB", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone4x});

		GameRegistry.addRecipe(ItemStacks.diamond2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamondgear});
		GameRegistry.addRecipe(ItemStacks.diamond4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond2x});
		GameRegistry.addRecipe(ItemStacks.diamond8x, new Object[]{
				" gB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond4x, 'g', ItemStacks.diamond2x});
		GameRegistry.addRecipe(ItemStacks.diamond16x, new Object[]{
				" gB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond8x, 'g', ItemStacks.diamond2x});
		GameRegistry.addRecipe(ItemStacks.diamond16x, new Object[]{
				"BGB", "BGB", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond4x});

		GameRegistry.addRecipe(ItemStacks.bedrock2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrockgear});
		GameRegistry.addRecipe(ItemStacks.bedrock4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock2x});
		GameRegistry.addRecipe(ItemStacks.bedrock8x, new Object[]{
				" gB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock4x, 'g', ItemStacks.bedrock2x});
		GameRegistry.addRecipe(ItemStacks.bedrock16x, new Object[]{
				" gB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock8x, 'g', ItemStacks.bedrock2x});
		GameRegistry.addRecipe(ItemStacks.bedrock16x, new Object[]{
				"BGB", "BGB", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock4x});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.stonerod, 2), new Object[]{
			"  B", " B ", "B  ", 'B', Blocks.stone});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.shaftitem, DifficultyEffects.PARTCRAFT.getInt()), new Object[]{
			"  B", " B ", "B  ", 'B', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.diamondshaft, DifficultyEffects.PARTCRAFT.getInt()), new Object[]{
			"  B", " B ", "B  ", 'B', Items.diamond});

		Object[] params = new Object[]{" D ", "DSD", " D ", 'D', ItemStacks.bedrockdust, 'S', ItemStacks.shaftitem};
		//GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockshaft, 4), params);
		RecipesBlastFurnace.getRecipes().add3x3Crafting(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockshaft, 4), 1000, 2, 0, params);

		GameRegistry.addRecipe(ItemStacks.wormgear, new Object[]{
				"S  ", " G ", "  S", 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.woodgear, new Object[]{
				" W ", "WWW", " W ", 'W', "plankWood"}));
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.stonegear, 2), new Object[]{
			" W ", "WWW", " W ", 'W', Blocks.stone});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.diamondgear, 8), new Object[]{
			" W ", "WWW", " W ", 'W', Items.diamond});

		params = new Object[]{"bWb", "WWW", "bWb", 'b', ItemStacks.bedrockdust, 'W', ItemStacks.steelingot};
		//GameRegistry.addRecipe(new ItemStack(ItemStacks.bedrockgear, 8, ItemStacks.bedrockgear.getItemDamage()), params);
		RecipesBlastFurnace.getRecipes().add3x3Crafting(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockgear, 8), 1000, 2, 0, params);


		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.flywheelcore, new Object[]{
				"WWW", "WGW", "WWW", 'W', "plankWood", 'G', ItemStacks.steelgear}));
		GameRegistry.addRecipe(ItemStacks.flywheelcore2, new Object[]{
				"WWW", "WGW", "WWW", 'W', Blocks.stone, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore3, new Object[]{
				"WWW", "WGW", "WWW", 'W', Items.iron_ingot, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore4, new Object[]{
				"WWW", "WGW", "WWW", 'W', Items.gold_ingot, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(ItemStacks.lim, new Object[]{
				"WRW", "NNN", 'W', ItemStacks.goldcoil, 'N', ItemStacks.steelingot, 'R', Items.redstone});

		GameRegistry.addRecipe(ItemStacks.generator, new Object[]{
				"  G", " C ", "G  ", 'G', ItemStacks.goldcoil, 'C', ItemStacks.shaftcore});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.chain, 4), "s s", " s ", "s s", 's', ItemStacks.steelingot);
	}

	private static void addToolItems() {
		ItemRegistry.SPRING.addRecipe(" S ", "S S", " S ", 'S', ItemStacks.steelingot);
		ItemRegistry.STRONGCOIL.addBlastRecipe(1000, 4, "SDS", "BCB", "SDS", 'S', ItemStacks.steelingot, 'C', ItemRegistry.SPRING.getStackOf(), 'B', ItemStacks.bedrockdust, 'D', Items.diamond);

		ItemRegistry.TARGET.addRecipe(" E ", "SRS", "SLS", 'S', ItemStacks.steelingot, 'R', Items.redstone, 'E', Items.ender_pearl, 'L', ReikaItemHelper.lapisDye);

		if (ModList.HARVESTCRAFT.isLoaded()) {
			RotaryCraft.logger.log("HarvestCraft found, not loading iron screwdriver recipe.");
		}
		else {
			ItemRegistry.SCREWDRIVER.addOreRecipe("I  ", " S ", "  W", 'S', "stickWood", 'I', Items.iron_ingot, 'W', "plankWood");
			RotaryCraft.logger.log("HarvestCraft not found, loading iron screwdriver recipe.");
		}

		ItemRegistry.SCREWDRIVER.addOreRecipe("I  ", " S ", "  W", 'S', "stickWood", 'I', ItemStacks.steelingot, 'W', "plankWood");
		ItemRegistry.METER.addOreRecipe(" W ", "WEW", "SSS", 'S', ItemStacks.steelingot, 'E', Items.redstone, 'I', Items.iron_ingot, 'W', "plankWood");
		ItemRegistry.HANDBOOK.addRecipe("RSR", "PPP", "PPP", 'R', Items.redstone, 'S', Items.iron_ingot, 'P', Items.paper);

		ItemRegistry.BEDPICK.addEnchantedBlastRecipe(1000, 4, "BBB", " S ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDAXE.addBlastRecipe(1000, 4, "BB ", "BS ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDSHOVEL.addBlastRecipe(1000, 4, "B", "S", "S", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDSWORD.addEnchantedBlastRecipe(1000, 4, "B", "B", "S", 'B', ItemStacks.bedingot, 'S', ItemStacks.shaftitem);
		ItemRegistry.BEDHOE.addBlastRecipe(1000, 4, "II", " S", " S", 'S', ItemStacks.shaftitem, 'I', ItemStacks.bedingot);
		ItemRegistry.BEDHOE.addBlastRecipe(1000, 4, "II", "S ", "S ", 'S', ItemStacks.shaftitem, 'I', ItemStacks.bedingot);
		ItemRegistry.BEDSHEARS.addBlastRecipe(1000, 4, " B", "B ", 'B', ItemStacks.bedingot);
		ItemRegistry.BEDSICKLE.addEnchantedBlastRecipe(1000, 4, " B ", "  B", "SB ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDGRAFTER.addBlastRecipe(1000, 4, "  S", " s ", "s  ", 'S', ItemStacks.bedingot, 's', ItemStacks.shaftitem);
		ItemRegistry.BEDSAW.addBlastRecipe(1000, 4, "sss", " SS", " bb", 'b', ItemStacks.bedingot, 's', ItemStacks.shaftitem, 'S', Items.iron_ingot);
		//ItemRegistry.BEDKNIFE.addBlastRecipe(1000, 4, "  s", "qs ", "bb ", 'b', ItemStacks.bedingot, 's', ItemStacks.shaftitem, 'q', AppEngHandler.getInstance().getCertusQuartz());
		ItemRegistry.BEDHELM.addEnchantedBlastRecipe(1200, 4, "III", "I I", 'I', ItemStacks.bedingot);
		ItemRegistry.BEDBOOTS.addEnchantedBlastRecipe(1200, 4, "I I", "I I", 'I', ItemStacks.bedingot);
		ItemRegistry.BEDCHEST.addEnchantedBlastRecipe(1200, 4, "I I", "III", "III", 'I', ItemStacks.bedingot);
		ItemRegistry.BEDLEGS.addEnchantedBlastRecipe(1200, 4, "III", "I I", "I I", 'I', ItemStacks.bedingot);

		ItemRegistry.STEELPICK.addRecipe(new ShapedOreRecipe(ItemRegistry.STEELPICK.getStackOf(), "BBB", " S ", " S ", 'S', "stickWood", 'B', ItemStacks.steelingot));
		ItemRegistry.STEELAXE.addRecipe(new ShapedOreRecipe(ItemRegistry.STEELAXE.getStackOf(), "BB ", "BS ", " S ", 'S', "stickWood", 'B', ItemStacks.steelingot));
		ItemRegistry.STEELSHOVEL.addRecipe(new ShapedOreRecipe(ItemRegistry.STEELSHOVEL.getStackOf(), " B ", " S ", " S ", 'S', "stickWood", 'B', ItemStacks.steelingot));
		ItemRegistry.STEELSWORD.addEnchantedRecipe("B", "B", "S", 'B', ItemStacks.steelingot, 'S', Items.stick);
		ItemRegistry.STEELHOE.addRecipe("II ", " S ", " S ", 'S', Items.stick, 'I', ItemStacks.steelingot);
		ItemRegistry.STEELHOE.addRecipe(" II", " S ", " S ", 'S', Items.stick, 'I', ItemStacks.steelingot);
		ItemRegistry.STEELSHEARS.addRecipe(" B", "B ", 'B', ItemStacks.steelingot);
		ItemRegistry.STEELSICKLE.addRecipe(" B ", "  B", "SB ", 'S', Items.stick, 'B', ItemStacks.steelingot);

		ItemRegistry.GRAFTER.addRecipe("  S", "Ss ", "CS ", 'C', ItemRegistry.SPRING.getStackOf(), 'S', ItemStacks.steelingot, 's', Items.stick);

		ItemRegistry.STEELHELMET.addRecipe("III", "I I", 'I', ItemStacks.steelingot);
		ItemRegistry.STEELBOOTS.addRecipe("I I", "I I", 'I', ItemStacks.steelingot);
		ItemRegistry.STEELCHEST.addRecipe("I I", "III", "III", 'I', ItemStacks.steelingot);
		ItemRegistry.STEELLEGS.addRecipe("III", "I I", "I I", 'I', ItemStacks.steelingot);

		//ItemRegistry.NVH.addShapelessRecipe(Items.diamond_helmet, ItemRegistry.NVG.getStackOf());

		ItemRegistry.ULTRASOUND.addRecipe(" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar);
		ItemRegistry.MOTION.addRecipe(" nr", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar, 'r', ItemStacks.radar);
		ItemRegistry.VACUUM.addRecipe(" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.impeller, 'n', ItemStacks.diffuser);
		ItemRegistry.STUNGUN.addRecipe(" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.sonar, 'n', ItemStacks.diffuser);
		ItemRegistry.GRAVELGUN.addRecipe(" d ", "gcg", "sss", 's', ItemStacks.steelingot, 'c', Blocks.chest, 'd', Blocks.dispenser, 'g', ItemStacks.steelgear);
		ItemRegistry.RANGEFINDER.addRecipe(" e ", "rGr", "sss", 'G', Blocks.glowstone, 's', ItemStacks.steelingot, 'r', Items.redstone, 'e', Items.ender_pearl);
		ItemRegistry.FIREBALL.addRecipe("b b", "scs", "srs", 's', ItemStacks.steelingot, 'c', ItemStacks.combustor, 'r', Items.redstone, 'b', Items.blaze_rod);
		ItemRegistry.HANDCRAFT.addRecipe(" g ", "scs", " g ", 's', ItemStacks.steelingot, 'g', Items.gold_ingot, 'c', Blocks.crafting_table);
		ItemRegistry.NVG.addRecipe("scs", "ese", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'e', Items.ender_eye);

		ItemRegistry.IOGOGGLES.addRecipe("scs", "ese", 's', ItemStacks.steelingot, 'c', Items.ender_pearl, 'e', Items.redstone);

		ItemRegistry.KEY.addRecipe("s", "s", "P", 'P', ItemStacks.basepanel, 's', ItemStacks.steelingot);

		ItemRegistry.TILESELECTOR.addRecipe(" l ", "srs", "ses", 'e', Items.ender_pearl, 'r', Items.redstone, 'l', ReikaItemHelper.lapisDye, 's', ItemStacks.steelingot);

		ItemRegistry.JETPACK.addRecipe("CRC", "cBc", "d d", 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'B', ItemStacks.basepanel, 'd', ItemStacks.diffuser, 'c', ItemStacks.compressor, 'C', ItemStacks.combustor);

		ItemRegistry.PUMP.addRecipe(" sP", "sIs", "sRs", 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 's', ItemStacks.steelingot, 'P', ItemStacks.pipe, 'I', ItemStacks.impeller);

		ItemRegistry.HELDPISTON.addRecipe(" sP", "sRs", "gs ", 'g', Items.glowstone_dust, 's', ItemStacks.steelingot, 'P', Blocks.piston, 'R', Blocks.redstone_block);

		ItemRegistry.JUMP.addRecipe("GbG", "SgS", "B B", 'B', ItemStacks.basepanel, 'G', ItemStacks.steelgear, 'b', ItemRegistry.STEELBOOTS.getStackOf(), 'g', ItemStacks.gearunit, 'S', ItemRegistry.SPRING.getStackOf());

		ItemRegistry.FUEL.addRecipe("SBS", "BGB", "SPS", 'P', ItemStacks.pipe, 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'G', Blocks.glass);

		ItemRegistry.DISK.addSizedRecipe(4, "wRw", "RSR", "wRw", 'w', ReikaItemHelper.blackWool, 'R', Items.redstone, 'S', ItemStacks.steelingot);

		ItemRegistry.CRAFTPATTERN.addSizedRecipe(4, " S ", " B ", " S ", 'S', ItemStacks.steelingot, 'B', ItemStacks.basepanel);

		ItemRegistry.UPGRADE.addMetaRecipe(0, "sRs", "gGg", " b ", 's', ItemStacks.silumin, 'b', ItemStacks.basepanel, 'R', ItemStacks.radiator, 'G', ItemStacks.gearunit, 'g', Items.gold_ingot);

		ItemRegistry.UPGRADE.addMetaRecipe(1, "gRg", "RER", "SGS", 'g', Items.gold_ingot, 'G', ItemStacks.impeller, 'R', Items.redstone, 'S', ItemStacks.steelingot, 'E', ItemRegistry.ETHANOL.getStackOf());
		ItemRegistry.UPGRADE.addMetaRecipe(2, "SCS", "ERE", "SCS", 'C', ItemStacks.redgoldingot, 'R', ItemStacks.goldcoil, 'S', ItemStacks.steelingot, 'E', ItemStacks.shaftcore);
		ItemRegistry.UPGRADE.addMetaRecipe(3, "SES", "ERE", "ScS", 'c', ItemStacks.pcb, 'R', ItemStacks.tungsteningot, 'S', ItemStacks.steelingot, 'E', ItemStacks.redgoldingot);
		ItemRegistry.UPGRADE.addMetaBlastRecipe(1000, 4, 4, "cEc", "ERE", "SES", 'c', MachineRegistry.COOLINGFIN.getCraftedProduct(), 'R', ItemStacks.bedingot, 'S', ItemStacks.steelingot, 'E', ItemStacks.tungsteningot);
		ItemRegistry.UPGRADE.addMetaBlastRecipe(1800, 8, 5, "SES", "ERE", "SES", 'R', ItemStacks.bedrockgear, 'S', ItemStacks.steelingot, 'E', ItemStacks.springingot);

		ItemRegistry.UPGRADE.addMetaRecipe(6, "SEI", "ERE", "SEI", 'R', ItemStacks.compoundturb, 'S', ItemStacks.highcombustor, 'I', ItemStacks.igniter, 'E', ItemStacks.bedrockdust);

		if (!ModList.REACTORCRAFT.isLoaded())
			ItemRegistry.UPGRADE.addMetaBlastRecipe(1980, 32, Upgrades.EFFICIENCY.ordinal(), "IGI", "FTF", "BPB", 'G', ItemStacks.generator, 'I', ItemStacks.redgoldingot, 'B', ItemStacks.waterplate, 'P', ItemStacks.power, 'F', ItemStacks.bedingot, 'T', ItemStacks.tungsteningot);

		ItemRegistry.GEARUPGRADE.addMetaBlastRecipe(1200, 2, 0, "sSS", "SsS", "SSG", 'S', ItemStacks.steelingot, 'G', Blocks.glass_pane, 's', ItemStacks.bedrockshaft);

		ItemRegistry.GEARUPGRADE.addRecipe(ReikaRecipeHelper.getShapelessRecipeFor(ItemRegistry.GEARUPGRADE.getStackOfMetadata(1), ItemRegistry.GEARUPGRADE.getStackOfMetadata(0), ItemStacks.bedrock2x));
		ItemRegistry.GEARUPGRADE.addRecipe(ReikaRecipeHelper.getShapelessRecipeFor(ItemRegistry.GEARUPGRADE.getStackOfMetadata(2), ItemRegistry.GEARUPGRADE.getStackOfMetadata(0), ItemStacks.bedrock4x));
		ItemRegistry.GEARUPGRADE.addRecipe(ReikaRecipeHelper.getShapelessRecipeFor(ItemRegistry.GEARUPGRADE.getStackOfMetadata(3), ItemRegistry.GEARUPGRADE.getStackOfMetadata(0), ItemStacks.bedrock8x));
		ItemRegistry.GEARUPGRADE.addRecipe(ReikaRecipeHelper.getShapelessRecipeFor(ItemRegistry.GEARUPGRADE.getStackOfMetadata(4), ItemRegistry.GEARUPGRADE.getStackOfMetadata(0), ItemStacks.bedrock16x));
	}

	private static void addMisc() {
		if (ConfigRegistry.CRAFTABLEBEDROCK.getState())
			GameRegistry.addRecipe(new ItemStack(Blocks.bedrock), new Object[]{
				"DDD", "DSD", "DDD", 'D', ItemStacks.bedrockdust, 'S', Blocks.stone});

		GameRegistry.addRecipe(ItemStacks.denseCanolaSeeds, new Object[]{"DDD", "DDD", "DDD", 'D', ItemStacks.canolaSeeds});

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.canolaSeeds, 9), ItemStacks.denseCanolaSeeds);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.torch, 8, 0), "C", "S", 'C', ItemStacks.coke, 'S', "stickWood"));

		GameRegistry.addRecipe(new ReservoirComboRecipe());
		GameRegistry.addShapelessRecipe(MachineRegistry.RESERVOIR.getCraftedProduct(), MachineRegistry.RESERVOIR.getCraftedProduct()); //empty
		GameRegistry.addShapelessRecipe(ItemRegistry.CRAFTPATTERN.getStackOf(), ItemRegistry.CRAFTPATTERN.getStackOf()); //empty

		//GameRegistry.addRecipe(new ScrapCombinationRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(ReikaItemHelper.oakWood, new Object[]{
				"WW", "WW", 'W', ItemStacks.sawdust}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ReikaItemHelper.spruceWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.BLACK.getOreDictName()}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ReikaItemHelper.birchWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.WHITE.getOreDictName()}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ReikaItemHelper.jungleWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.RED.getOreDictName()}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ReikaItemHelper.acaciaWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.ORANGE.getOreDictName()}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ReikaItemHelper.darkOakWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.BROWN.getOreDictName()}));

		GameRegistry.addRecipe(new ItemStack(Items.paper, 8, 0), new Object[]{
			" W ", "SSS", "RRR", 'R', Blocks.stone, 'S', ItemStacks.sawdust, 'W', Items.water_bucket});

		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), Blocks.sand, ItemStacks.compost);

		GameRegistry.addRecipe(BlockRegistry.BLASTPANE.getCraftedProduct(16), new Object[]{
			"OOO", "OOO", 'O', BlockRegistry.BLASTGLASS.getStackOf()});

		GameRegistry.addRecipe(new ItemStack(Blocks.tnt, 2), "sns", "nSn", "sns", 's', Items.sugar, 'S', Blocks.sand, 'n', ItemStacks.nitrate);

		RecipesBlastFurnace.getRecipes().add3x3Crafting(ItemStacks.bedrockdrill, 1000, 8, 0, "BBB", "BBB", " B ", 'B', ItemStacks.bedingot);

		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.steelgear, 45/DifficultyEffects.PARTCRAFT.getInt()));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.gearunit, 48));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.gearunit4, 114));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.gearunit8, 180));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.gearunit16, 244));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.shaftitem, 27/DifficultyEffects.PARTCRAFT.getInt()));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.basepanel, 27/DifficultyEffects.PARTCRAFT.getInt()));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.wormgear, 45/DifficultyEffects.PARTCRAFT.getInt()+2*27/DifficultyEffects.PARTCRAFT.getInt()));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(9, ItemStacks.ballbearing, 4));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.mount, 9));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.shaftcore, 15));
		WorktableRecipes.getInstance().addRecyclingRecipe(new RecyclingRecipe(ItemStacks.waterplate, 81));

		for (int i = 0; i < MaterialRegistry.matList.length; i++) {
			MaterialRegistry mat = MaterialRegistry.matList[i];
			//for (int k = 2; k < 16; k *= 2) {
			//	GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(mat.getGearUnitItem(k), 2), mat.getGearUnitItem(k*2));
			//}
			//anything else is not an even split
			GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(mat.getGearUnitItem(4), 2), mat.getGearUnitItem(16));
			GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(mat.getGearUnitItem(2), 2), mat.getGearUnitItem(4));
			GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(mat.getGearItem(), 2), mat.getGearUnitItem(2));
		}

		ReikaRecipeHelper.addSmelting(ItemStacks.flour, new ItemStack(Items.bread), 0.2F);

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"rrr", "rrr", "rr ", 'r', Blocks.rail});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"rrr", "rrr", "rr ", 'r', Blocks.iron_bars});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"b", 'b', Items.bucket});

		GameRegistry.addRecipe(ItemStacks.steelblock, "BBB", "BBB", "BBB", 'B', ItemStacks.steelingot);
		WorktableRecipes.getInstance().addRecipe(ItemStacks.steelblock, RecipeLevel.PROTECTED, "BBB", "BBB", "BBB", 'B', ItemStacks.steelingot);
		GameRegistry.addRecipe(ItemStacks.anthrablock, "BBB", "BBB", "BBB", 'B', ItemStacks.anthracite);
		GameRegistry.addRecipe(ItemStacks.lonsblock, "BBB", "BBB", "BBB", 'B', ItemStacks.lonsda);
		GameRegistry.addRecipe(ItemStacks.bedingotblock, "BBB", "BBB", "BBB", 'B', ItemStacks.bedingot);
		GameRegistry.addRecipe(ItemStacks.cokeblock, "BBB", "BBB", "BBB", 'B', ItemStacks.coke);

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.shieldblock, 4), " S ", "SOS", " S ", 'S', ItemStacks.steelingot, 'O', Blocks.obsidian);

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 9), ItemStacks.steelblock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.anthracite, 9), ItemStacks.anthrablock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.lonsda, 9), ItemStacks.lonsblock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bedingot, 9), ItemStacks.bedingotblock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.coke, 9), ItemStacks.cokeblock);

		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemStacks.silveriodide, ItemStacks.salt, "ingotSilver"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.nitrate, 4), Items.gunpowder, "dustRedstone", "dustSalt", "dustCoal"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.nitrate, 4), Items.gunpowder, "dustRedstone", "foodSalt", "dustCoal"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.redgolddust, 2), Items.redstone, "dustGold"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.redgolddust, 2), Items.redstone, ItemStacks.goldoreflakes));

		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedProduct(3), new Object[]{
			"ss ", "s  ", 's', ItemStacks.steelingot});

		int amt = DifficultyEffects.RAILGUNCRAFT.getInt();
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 1), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(0), 'p', "plankWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 2), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(1), 'p', "plankWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 3), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(2), 'p', "plankWood"}));
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 4), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(3), 'p', Blocks.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 5), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(4), 'p', Blocks.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 6), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(5), 'p', Blocks.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 7), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(6), 'p', Items.iron_ingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 8), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(7), 'p', Items.iron_ingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 9), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(8), 'p', Items.iron_ingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 10), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(9), 'p', Items.gold_ingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 11), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(10), 'p', Items.gold_ingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 12), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(11), 'p', Items.gold_ingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 13), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(12), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 14), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(13), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 15), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(14), 'p', ItemStacks.bedingot});

		GameRegistry.addRecipe(ItemRegistry.MINECART.getCraftedProduct(1), new Object[]{
			"g", "m", 'g', EngineType.GAS.getCraftedProduct(), 'm', new ItemStack(Items.minecart)});

		GameRegistry.addRecipe(ItemRegistry.SHELL.getCraftedProduct(16), new Object[]{
			" s ", "sns", " s ", 's', ItemStacks.steelingot, 'n', ItemStacks.nitrate});

		addSlideRecipes();

		GameRegistry.addRecipe(BlockRegistry.DECOTANK.getCraftedProduct(4), "SGS", "GGG", "SGS", 'S', ItemStacks.steelingot, 'G', Blocks.glass_pane);
		GameRegistry.addRecipe(new DecoTankSettingsRecipe());
	}

	private static void addSlideRecipes() {
		GameRegistry.addRecipe(ItemRegistry.SLIDE.getCraftedProduct(4), new Object[]{
			"PPP", "PGP", "PPP", 'G', Blocks.glass_pane, 'P', Items.paper});

		ItemStack is = ItemRegistry.SLIDE.getCraftedMetadataProduct(2, 24);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("file", "[NO FILE]");
		GameRegistry.addRecipe(is, new Object[]{
				"rPr", "PGP", "rPr", 'G', Blocks.glass_pane, 'P', Items.paper, 'r', Items.redstone});

		Random r = new Random();
		HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		boolean[] hasMapping = new boolean[16];
		for (int i = 0; i < 16; i++) {
			int randVal = r.nextInt(16);
			while (hasMapping[randVal]) {
				randVal = r.nextInt(16);
			}
			colors.put(i, randVal);
			hasMapping[randVal] = true;
			//ReikaJavaLibrary.pConsole("Color "+i+" registered to value "+randVal);
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				int color = colors.get(i);
				while (color+j >= 24) {
					color -= 24;
				}
				if (color+j < 0)
					throw new RuntimeException("Color mapping < 0 at color "+color+" and dye color "+i+" for slide "+j);
				GameRegistry.addShapelessRecipe(ItemRegistry.SLIDE.getCraftedMetadataProduct(1, color+j), new ItemStack(Items.dye, 1, i), ItemRegistry.SLIDE.getStackOfMetadata(j));
				//ReikaJavaLibrary.pConsole("Registering recipe with slide "+j+" and color "+i+" to result slide "+(color+j));
			}
		}
	}

	private static void addMultiTypes() {

		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(0, "SW ", " GS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'W', ItemStacks.wormgear, 'G', ItemStacks.steelgear); //Worm gear
		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(1, "BSB", "BSB", "sMc", 'c', ItemStacks.screen, 's', ItemStacks.pcb, 'M', ItemStacks.mount, 'S', ItemStacks.bedrockshaft, 'B', ItemStacks.bearing); //CVT
		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(2, "BCS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftcore, 'B', ItemStacks.brake, 'C', ItemStacks.tenscoil); //Coil
		NBTTagCompound NBT = new NBTTagCompound();
		NBT.setBoolean("bedrock", true);
		MachineRegistry.ADVANCEDGEARS.addNBTMetaCrafting(NBT, 2, "BCS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftcore, 'B', ItemStacks.brake, 'C', ItemStacks.bedrockcoil); //Coil
		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(3, "SGS", "SGS", "BMB", 'S', ItemStacks.bedrockshaft, 'B', ItemStacks.bearing, 'M', ItemStacks.mount, 'G', ItemStacks.bedrock16x); //256x

		MachineRegistry.FLYWHEEL.addMetaCrafting(0, "W", "M", 'W', ItemStacks.flywheelcore, 'M', ItemStacks.mount);
		MachineRegistry.FLYWHEEL.addMetaCrafting(1, "W", "M", 'W', ItemStacks.flywheelcore2, 'M', ItemStacks.mount);
		MachineRegistry.FLYWHEEL.addMetaCrafting(2, "W", "M", 'W', ItemStacks.flywheelcore3, 'M', ItemStacks.mount);
		MachineRegistry.FLYWHEEL.addMetaCrafting(3, "W", "M", 'W', ItemStacks.flywheelcore4, 'M', ItemStacks.mount);

		MachineRegistry.SHAFT.addSizedOreRecipe(8, "BSB", "BBB", 'B', "plankWood", 'S', "stickWood");
		MachineRegistry.SHAFT.addSizedMetaCrafting(8, 1, "sSs", "sss", 's', ReikaItemHelper.stoneSlab, 'S', ItemStacks.stonerod);
		MachineRegistry.SHAFT.addSizedMetaCrafting(8, 2, "S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem);
		MachineRegistry.SHAFT.addSizedMetaCrafting(8, 3, "S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.diamondshaft);
		MachineRegistry.SHAFT.addSizedMetaCrafting(8, 4, "S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.bedrockshaft);

		MachineRegistry.ENGINE.addMetaCrafting(EngineType.DC.ordinal(), "SSS", "SRs", "PRP", 'S', ItemStacks.steelingot, 'R', Items.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem);
		MachineRegistry.ENGINE.addSizedMetaCrafting(2, EngineType.WIND.ordinal(), "SSS", "SHS", "SSS", 'S', ItemStacks.prop, 'H', ItemStacks.hub);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.STEAM.ordinal(), "ccc", "CIs", "PGP", 'c', Blocks.cobblestone, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', Items.gold_ingot, 'C', ItemStacks.condenser);

		if (ReikaItemHelper.oreItemExists("ingotCopper"))
			MachineRegistry.ENGINE.addMetaOreRecipe(EngineType.STEAM.ordinal(), "ccc", "CIs", "PGP", 'c', Blocks.cobblestone, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', "ingotCopper", 'C', ItemStacks.condenser);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.GAS.ordinal(), "CgC", "SGs", "PIP", 'g', Items.gold_ingot, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.gearunit, 'C', ItemStacks.cylinder);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.AC.ordinal(), "SSS", "SGs", "PRP", 'S', Items.gold_ingot, 'R', Items.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.goldcoil);

		if (ReikaItemHelper.oreItemExists("ingotElectrum"))
			MachineRegistry.ENGINE.addMetaOreRecipe(EngineType.AC.ordinal(), "SSS", "SGs", "PRP", 'S', "ingotElectrum", 'R', Items.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.goldcoil);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.SPORT.ordinal(), "CrC", "SGs", "PIP", 'C', ItemStacks.aluminumcylinder, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'r', ItemStacks.radiator, 'G', ItemStacks.gearunit);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.HYDRO.ordinal(), "PPP", "PGP", "PPP", 'P', ItemStacks.waterplate, 'G', ItemStacks.diamondshaftcore);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.MICRO.ordinal(), "CSS", "cTs", "PPP", 'S', ItemStacks.silumin, 'C', ItemStacks.compressor, 'c', ItemStacks.highcombustor, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.JET.ordinal(), "DCS", "ScS", "PTs", 'S', ItemStacks.silumin, 'D', ItemStacks.diffuser, 'C', ItemStacks.compoundcompress, 'c', ItemStacks.highcombustor, 'T', ItemStacks.compoundturb, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem);

		if (ConfigRegistry.ROTATEHOSE.getState()) {
			MachineRegistry.HOSE.addSizedOreRecipe(DifficultyEffects.PIPECRAFT.getInt(), "WWW", "GGG", "WWW", 'G', Blocks.glass, 'W', "plankWood");
			MachineRegistry.PIPE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "SSS", "GGG", "SSS", 'S', ItemStacks.steelingot, 'G', Blocks.glass);
			MachineRegistry.FUELLINE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "OOO", "GGG", "OOO", 'O', Blocks.obsidian, 'G', Blocks.glass);
			MachineRegistry.BEDPIPE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "BBB", "GGG", "BBB", 'B', ItemStacks.bedingot, 'G', BlockRegistry.BLASTGLASS.getBlockInstance());
		}
		else {
			MachineRegistry.HOSE.addSizedOreRecipe(DifficultyEffects.PIPECRAFT.getInt(), "WGW", "WGW", "WGW", 'G', Blocks.glass, 'W', "plankWood");
			MachineRegistry.PIPE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "SGS", "SGS", "SGS", 'S', ItemStacks.steelingot, 'G', Blocks.glass);
			MachineRegistry.FUELLINE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "OGO", "OGO", "OGO", 'O', Blocks.obsidian, 'G', Blocks.glass);
			MachineRegistry.BEDPIPE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "BGB", "BGB", "BGB", 'B', ItemStacks.bedingot, 'G', BlockRegistry.BLASTGLASS.getBlockInstance());
		}

		addGearboxes();
	}

	private static void addGearboxes() {
		ItemStack gear;
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(0));
		MachineRegistry.GEARBOX.addRecipe(new ShapedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', "plankWood", 'G', ItemStacks.wood2x}));
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(5));
		MachineRegistry.GEARBOX.addRecipe(new ShapedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', "plankWood", 'G', ItemStacks.wood4x}));
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(10));
		MachineRegistry.GEARBOX.addRecipe(new ShapedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', "plankWood", 'G', ItemStacks.wood8x}));
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(15));
		MachineRegistry.GEARBOX.addRecipe(new ShapedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', "plankWood", 'G', ItemStacks.wood16x}));

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(1));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(6));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(11));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(16));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone16x});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(2));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(7));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit4});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(12));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit8});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(17));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit16});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(3));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(8));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(13));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(18));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond16x});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(4));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(9));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(14));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(19));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock16x});
	}

	private static void addFurnace() {
		ReikaRecipeHelper.addSmelting(ItemStacks.aluminumpowder, ItemStacks.aluminumingot, 0.4F);
		ReikaRecipeHelper.addSmelting(ItemStacks.sludge, ItemRegistry.ETHANOL.getStackOf(), 0.5F);

		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(24), new ItemStack(Items.coal, 1, 0), 0.1F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(25), new ItemStack(Items.iron_ingot, 1, 0), 0.7F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(26), new ItemStack(Items.gold_ingot, 1, 0), 1F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(27), new ItemStack(Items.redstone, 4, 0), 0.5F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(28), new ItemStack(Items.dye, 6, 4), 0.6F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(29), new ItemStack(Items.diamond, 1, 0), 1F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(30), new ItemStack(Items.emerald, 1, 0), 1F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(31), new ItemStack(Items.quartz, 1, 0), 1F);
		ReikaRecipeHelper.addSmelting(ItemRegistry.EXTRACTS.getStackOfMetadata(32), ItemStacks.silveringot.copy(), 1F);

		ExtractorModOres.addSmelting();

		//ReikaRecipeHelper.addSmelting(ItemStacks.scrap, ItemStacks.steelingot, 0.4F);
		ReikaRecipeHelper.addSmelting(ItemStacks.ironscrap, new ItemStack(Items.iron_ingot), 0.4F);

		//RecipesBlastFurnace.getRecipes().addRecipe(ItemStacks.scrap, 600, new ScrapMeltingRecipe(), 1, 0);
	}

	private static ItemStack addDamageNBT(ItemStack is) {
		is.setTagCompound(new NBTTagCompound());
		is.stackTagCompound.setInteger("damage", 0);
		return is;
	}

	private static void addOreRecipeToBoth(ItemStack out, Object... in) {
		ShapedOreRecipe sr = new ShapedOreRecipe(out, in);
		GameRegistry.addRecipe(sr);
		WorktableRecipes.getInstance().addRecipe(sr, RecipeLevel.CORE);
	}

	private static void addRecipeToBoth(ItemStack out, Object... in) {
		GameRegistry.addRecipe(out, in);
		WorktableRecipes.getInstance().addRecipe(out, RecipeLevel.CORE, in);
	}

	public static Object[] getBlastFurnaceIngredients() {
		Object obj1 = ReikaItemHelper.stoneBricks;
		Object obj2 = ReikaItemHelper.stoneBricks;
		Object obj3 = ReikaItemHelper.stoneBricks;
		Object obj4 = ReikaItemHelper.stoneBricks;

		ArrayList<Object> c = RotaryCraft.config.getBlastFurnaceGatingMaterials();
		switch(c.size()) {
			case 1:
				obj1 = obj2 = obj3 = obj4 = c.get(0);
				break;
			case 2:
				obj1 = obj4 = c.get(0);
				obj2 = obj3 = c.get(1);
				break;
			case 3:
				obj1 = obj4 = c.get(0);
				obj2 = c.get(1);
				obj3 = c.get(2);
				break;
			case 4:
				obj1 = c.get(0);
				obj2 = c.get(1);
				obj3 = c.get(2);
				obj4 = c.get(3);
				break;
			default:
				break;
		}

		Object[] args = {
				"BaB", "brc", "BdB", 'B', ReikaItemHelper.stoneBricks, 'r', Items.redstone, 'a', obj1, 'b', obj2, 'c', obj3, 'd', obj4
		};
		return args;
	}

	private static final Collection<RecipeHandler> recipeHandlers = new OneWayList();

	public static void loadMachineRecipeHandlers() {
		loadRecipeHandler(WorktableRecipes.getInstance());
		loadRecipeHandler(RecipesBlastFurnace.getRecipes());
		loadRecipeHandler(RecipesCentrifuge.getRecipes());
		loadRecipeHandler(RecipesCompactor.getRecipes());
		loadRecipeHandler(RecipesCrystallizer.getRecipes());
		loadRecipeHandler(RecipesDryingBed.getRecipes());
		loadRecipeHandler(RecipesFrictionHeater.getRecipes());
		loadRecipeHandler(RecipesGrinder.getRecipes());
		loadRecipeHandler(RecipesLavaMaker.getRecipes());
		loadRecipeHandler(RecipesPulseFurnace.getRecipes());
		loadRecipeHandler(RecipesWetter.getRecipes());
		loadRecipeHandler(RecipesMagnetizer.getRecipes());
	}

	private static void loadRecipeHandler(RecipeHandler handler) {
		recipeHandlers.add(handler);
	}
}
