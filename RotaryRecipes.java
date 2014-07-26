/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.ItemMaterialController;
import Reika.DragonAPI.Instantiable.ItemMaterial;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.CustomThaumResearch;
import Reika.DragonAPI.ModInteract.ThaumOreHandler;
import Reika.DragonAPI.ModInteract.ThermalRecipeHelper;
import Reika.DragonAPI.ModInteract.TinkerBlockHandler;
import Reika.DragonAPI.ModInteract.XMLResearch;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class RotaryRecipes {

	public static void addRecipes() {
		if (RotaryCraft.instance.isLocked())
			return;
		OreDictionary.initVanillaEntries();
		RotaryRegistration.loadOreDictionary();
		addMachines();
		addCraftItems();
		addItemBlocks();
		addToolItems();
		addMisc();
		addFurnace();

		if (ModList.THERMALEXPANSION.isLoaded())
			addThermalExpansion();
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
	}

	private static void addCompat() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			String[] tags = ore.getOreDictIngots();
			for (int k = 0; k < tags.length; k++) {
				String tag = tags[k];
				ArrayList<ItemStack> in = OreDictionary.getOres(tag);
				for (int h = 0; h < in.size(); h++) {
					ItemStack from = in.get(h);
					ItemStack to = ItemStacks.getModOreIngot(ore);
					if (from.itemID != RotaryCraft.modingots.itemID)
						GameRegistry.addShapelessRecipe(to, from);
				}
			}
		}
	}

	private static void addThermalExpansion() {
		FluidStack ethanol = FluidRegistry.getFluidStack("rc ethanol", 100);
		int energy = 750;
		ThermalRecipeHelper.addCrucibleRecipe(ItemRegistry.ETHANOL.getStackOf(), ethanol, energy);

		ItemStack bedrock = ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, 4).copy();
		//ThermalRecipeHelper.addInductionSmelter(ItemStacks.steelingot.copy(), bedrock, ItemStacks.bedingot.copy(), 48000);

		ItemStack transmissionCoil = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilSilver", 1);
		ItemStack receptionCoil = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilGold", 1);
		//ItemStack energyCell = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "cellReinforced", 1);
		ItemStack conductanceCoil = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "powerCoilElectrum", 1);

		MachineRegistry.DYNAMO.addOreRecipe(" C ", "GIG", "IRI", 'C', transmissionCoil, 'I', ItemStacks.steelingot, 'G', ItemStacks.steelgear, 'R', Item.redstone);
		MachineRegistry.MAGNETIC.addOreRecipe("lCl", "scs", "PSP", 'c', "ingotCopper", 'C', receptionCoil, 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem, 'l', "ingotLead", 's', "ingotSilver");

		ThermalRecipeHelper.addCoolant(RotaryCraft.nitrogenFluid, 40000);
		ThermalRecipeHelper.addCompressionFuel(RotaryCraft.ethanolFluid, 125000); //1/4 of forestry
	}

	public static void addPostLoadRecipes() {
		if (RotaryCraft.instance.isLocked())
			return;

		addProps();
		RecipesGrinder.getRecipes().addModRecipes();

		if (ModList.TINKERER.isLoaded())
			GameRegistry.addRecipe(new ItemStack(RotaryCraft.decoTank.blockID, 4, 1), "SGS", "GGG", "SGS", 'S', ItemStacks.steelingot, 'G', new ItemStack(TinkerBlockHandler.getInstance().clearPaneID, 1, 0));

		if (ModList.THERMALEXPANSION.isLoaded()) {
			ItemStack hardGlass = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "hardenedGlass", 1);
			if (hardGlass == null)
				hardGlass = GameRegistry.findItemStack(ModList.THERMALEXPANSION.modLabel, "glassHardened", 1);
			if (ConfigRegistry.TEGLASS.getState() && hardGlass != null) {
				//GameRegistry.addRecipe(new ShapedOreRecipe(hardGlass, " L ", "LGL", " L ", 'L', "nuggetLead", 'G', RotaryCraft.obsidianglass));
				ReikaRecipeHelper.replaceIngredientInAllRecipes(hardGlass, new ItemStack(RotaryCraft.blastglass), true);
			}
		}

		if (ModList.THAUMCRAFT.isLoaded()) {
			try {
				ResourceLocation rl1 = new ResourceLocation("rotarycraft", "textures/blocks/worktable_top.png");
				ResourceLocation rl2 = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");
				ResearchCategories.registerCategory("rotarycraft", rl1, rl2);

				ItemStack in = ItemRegistry.BEDHELM.getEnchantedStack();
				ItemStack out = ItemRegistry.BEDREVEAL.getEnchantedStack();
				ItemStack meter = GameRegistry.findItemStack(ModList.THAUMCRAFT.modLabel, "ItemThaumometer", 1);
				ItemStack resource = GameRegistry.findItemStack(ModList.THAUMCRAFT.modLabel, "ItemResource", 1);
				int saltDmg = 14;
				ItemStack salis = new ItemStack(resource.itemID, 1, saltDmg);
				AspectList al = new AspectList();
				al.add(Aspect.MIND, 10);
				al.add(Aspect.SENSES, 25);
				al.add(Aspect.AURA, 10);
				al.add(Aspect.ARMOR, 25);
				al.add(Aspect.MAGIC, 25);
				ItemStack[] recipe = {
						meter,
						new ItemStack(Item.ingotGold),
						salis,
						ThaumOreHandler.getInstance().getItem(ModOreList.CINNABAR),
						meter,
						new ItemStack(Item.ingotGold),
						salis,
						ThaumOreHandler.getInstance().getItem(ModOreList.CINNABAR),

				};
				InfusionRecipe ir = ThaumcraftApi.addInfusionCraftingRecipe("GOGGLES", out, 2, al, in, recipe);
				String name = "Bedrock Helmet of Revealing";
				CustomThaumResearch res = new CustomThaumResearch("BEDREVEAL", "rotarycraft", al, 0, 0, 0, out).setName(name);
				res.setDescription("Combining the protection of bedrock with the power of a Thaumometer");
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
					String path = RotaryDescriptions.PARENT+"thaum.xml";
					XMLResearch xml = new XMLResearch("bedreveal", RotaryCraft.class, path, ir, 2);
					res.setPages(xml.getPages());
				}
				res.registerResearchItem();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void addMachines() {
		MachineRegistry.COMPACTOR.addCrafting("SPS", "PGP", "#P#", '#', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'P', ItemStacks.presshead, 'G', ItemStacks.gearunit16);

		MachineRegistry.FAN.addOreRecipe("WWW", "WIW", "#s#", '#', ItemStacks.basepanel, 'W', "plankWood", 'I', ItemStacks.impeller, 's', ItemStacks.shaftitem);

		MachineRegistry.AEROSOLIZER.addCrafting("BRB", "RIR", "BRB", 'B', ItemStacks.basepanel, 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'I', ItemStacks.impeller);

		MachineRegistry.HEATRAY.addCrafting("OOO", "BLb", "#P#", '#', ItemStacks.basepanel, 'B', ItemStacks.bulb, 'b', ItemStacks.barrel, 'P', ItemStacks.power, 'L', ItemStacks.lens, 'O', Block.obsidian);

		MachineRegistry.FLOODLIGHT.addCrafting("ISO", "Ggd", "I#O", '#', ItemStacks.basepanel, 'I', Item.ingotIron, 'd', Item.ingotGold, 'S', ItemStacks.steelingot, 'G', Block.glass, 'g', Block.glowStone, 'O', Block.obsidian);

		if (ReikaItemHelper.oreItemsExist("ingotCopper", "ingotSilver"))
			MachineRegistry.FLOODLIGHT.addOreRecipe("ISO", "Ggd", "I#O", '#', ItemStacks.basepanel, 'I', "ingotSilver", 'd', "ingotCopper", 'S', ItemStacks.steelingot, 'G', Block.glass, 'g', Block.glowStone, 'O', Block.obsidian);

		MachineRegistry.SHAFT.addMetaCrafting(RotaryNames.getNumberShaftTypes()-1, " S ", "SSS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem); //Shaft cross

		addRecipeToBoth(MachineRegistry.BLASTFURNACE.getCraftedProduct(), "SSS", "SrS", "SSS", 'r', Item.redstone, 'S', ReikaItemHelper.stoneBricks);

		addRecipeToBoth(MachineRegistry.WORKTABLE.getCraftedProduct(), " C ", "SBS", "srs", 'r', Item.redstone, 'S', ItemStacks.steelingot, 'B', Block.brick, 'C', Block.workbench, 's', ReikaItemHelper.stoneSlab);

		MachineRegistry.BEVELGEARS.addSizedCrafting(4, "ISB", "SGB", "BBB", 'B', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear);

		MachineRegistry.SPLITTER.addSizedCrafting(2, "ISP", "SGP", "ISP", 'P', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear);

		MachineRegistry.CLUTCH.addCrafting("S", "M", "R", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'R', Item.redstone);
		MachineRegistry.CLUTCH.addCrafting("S", "R", 'S', MachineRegistry.SHAFT.getCraftedMetadataProduct(2), 'R', Item.redstone);

		MachineRegistry.DYNAMOMETER.addSizedCrafting(2, " S ", " E ", " Ms", 's', ItemStacks.screen, 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'E', Item.enderPearl);

		MachineRegistry.BEDROCKBREAKER.addCrafting("BDt", "BSO", "BDt", 't', ItemStacks.tungsteningot, 'S', ItemStacks.steelingot, 'D', Item.diamond, 'O', Block.obsidian, 'B', ItemStacks.basepanel);

		MachineRegistry.FERMENTER.addCrafting("BPB", "PIP", "BPB", 'B', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel);
		MachineRegistry.FERMENTER.addOreRecipe("BPB", "PIP", "BPB", 'B', "ingotTin", 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel);

		MachineRegistry.GRINDER.addCrafting("B B", "SGS", "PPP", 'B', ItemStacks.steelingot, 'G', ItemStacks.steelgear, 'P', ItemStacks.basepanel, 'S', ItemStacks.saw);

		MachineRegistry.RESERVOIR.addCrafting("B B", "B B", "BBB", 'B', ItemStacks.basepanel);

		MachineRegistry.FIREWORK.addCrafting("BEB", "BDB", "BRB", 'B', ItemStacks.basepanel, 'R', Item.redstone, 'E', Item.eyeOfEnder, 'D', Block.dispenser);

		MachineRegistry.AUTOBREEDER.addCrafting("B B", "BBB", 'B', ItemStacks.basepanel);

		MachineRegistry.FRACTIONATOR.addCrafting("GFG", "GIG", "GPG", 'P', ItemStacks.basepanel, 'I', ItemStacks.mixer, 'G', Item.ingotGold, 'F', ItemStacks.fuelline);

		MachineRegistry.BAITBOX.addCrafting("BBB", "BAB", "BBB", 'B', Block.fenceIron, 'A', MachineRegistry.AUTOBREEDER.getCraftedProduct());

		MachineRegistry.WINDER.addCrafting(" ss", " hg", "ppp", 'h', ItemStacks.shaftitem, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel);

		MachineRegistry.ECU.addCrafting("IPI", "IGI", "IRI", 'I', ItemStacks.steelingot, 'G', Item.ingotGold, 'P', ItemStacks.pcb, 'R', Item.redstone);

		if (ReikaItemHelper.oreItemsExist("ingotCopper", "ingotElectrum"))
			MachineRegistry.ECU.addOreRecipe("IPI", "IGI", "IRI", 'I', ItemStacks.steelingot, 'G', "ingotElectrum", 'P', ItemStacks.pcb, 'R', Item.redstone);

		MachineRegistry.WOODCUTTER.addCrafting("IS ", "PGS", "PPI", 'I', ItemStacks.steelingot, 'S', ItemStacks.saw, 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit);

		MachineRegistry.VACUUM.addCrafting("SwS", "wIw", "SCS", 'C', Block.chest, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'w', ReikaItemHelper.blackWool);

		MachineRegistry.BORER.addCrafting("SSS", "DGC", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', ItemStacks.drill, 'G', ItemStacks.gearunit, 'C', ItemStacks.pcb);

		MachineRegistry.SPRINKLER.addSizedCrafting(4, " s ", " p ", " i ", 's', ItemStacks.steelingot, 'p', ItemStacks.pipe, 'i', ItemStacks.impeller);
		MachineRegistry.SPRINKLER.addSizedOreRecipe(4, " s ", " p ", " i ", 's', "ingotTin", 'p', ItemStacks.pipe, 'i', ItemStacks.impeller);

		MachineRegistry.SPAWNERCONTROLLER.addCrafting("PCP", "OGO", "g g", 'O', Block.obsidian, 'P', ItemStacks.basepanel, 'G', Item.ingotGold, 'g', Block.glowStone, 'C', ItemStacks.pcb);

		MachineRegistry.PLAYERDETECTOR.addCrafting("LRL", "OGO", "OPO", 'L', ReikaItemHelper.lapisDye, 'R', ItemStacks.radar, 'O', Block.obsidian, 'P', ItemStacks.basepanel, 'G', Item.ingotGold);

		MachineRegistry.OBSIDIAN.addCrafting("SpS", "PMP", "BBB", 'M', ItemStacks.mixer, 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'p', Block.thinGlass, 'P', ItemStacks.pipe);
		MachineRegistry.OBSIDIAN.addOreRecipe("SpS", "PMP", "BBB", 'M', ItemStacks.mixer, 'B', ItemStacks.basepanel, 'S', "ingotInvar", 'p', Block.thinGlass, 'P', ItemStacks.pipe);

		MachineRegistry.HEATER.addCrafting("sBs", "prp", "scs", 's', ItemStacks.steelingot, 'B', Block.fenceIron, 'p', ItemStacks.basepanel, 'c', ItemStacks.combustor);

		MachineRegistry.GPR.addCrafting("SsS", "PCP", "SRS", 'S', ItemStacks.steelingot, 's', ItemStacks.screen, 'P', ItemStacks.basepanel, 'R', ItemStacks.radar, 'C', ItemStacks.pcb);

		MachineRegistry.PULSEJET.addCrafting("OCD", "PcO", "BBB", 'B', ItemStacks.basepanel, 'O', Block.obsidian, 'C', ItemStacks.compressor, 'D', ItemStacks.diffuser, 'c', ItemStacks.combustor, 'P', ItemStacks.pipe);

		MachineRegistry.EXTRACTOR.addOreRecipe("SWS", "siD", "PIN", 'D', ItemStacks.drill, 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.shaftitem, 's', Block.stone, 'i', ItemStacks.impeller, 'N', Block.netherrack, 'W', "plankWood");

		MachineRegistry.LIGHTBRIDGE.addCrafting("GgG", "BgS", "BBD", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', Item.diamond, 'G', Item.ingotGold, 'g', Block.glass);

		MachineRegistry.PILEDRIVER.addCrafting("PGP", "gFg", "PDP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit8, 'g', ItemStacks.shaftitem, 'F', ItemStacks.flywheelcore3, 'D', ItemStacks.drill);

		MachineRegistry.PUMP.addCrafting("SGS", "pIp", "PpP", 'P', ItemStacks.basepanel, 'p', ItemStacks.pipe, 'I', ItemStacks.impeller, 'G', Block.thinGlass, 'S', ItemStacks.steelingot);

		MachineRegistry.MOBRADAR.addCrafting(" rs", " g ", "pcp", 'r', ItemStacks.radar, 's', ItemStacks.screen, 'c', ItemStacks.pcb, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel);

		MachineRegistry.TNTCANNON.addCrafting("sgc", "pcp", "pCr", 'g', Block.blockRedstone, 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Block.chest, 'p', ItemStacks.basepanel);

		MachineRegistry.SONICWEAPON.addCrafting("psp", "sts", "psp", 't', ItemStacks.turbine, 's', ItemStacks.sonar, 'p', ItemStacks.basepanel);

		MachineRegistry.FORCEFIELD.addCrafting("lnl", "ddd", "sgs", 'd', Item.diamond, 's', ItemStacks.basepanel, 'n', Item.netherStar, 'g', Item.ingotGold, 'l', ReikaItemHelper.lapisDye);

		MachineRegistry.MUSICBOX.addSizedCrafting(4, "sns", "ncn", "sns", 'n', Block.music, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb);
		MachineRegistry.MUSICBOX.addSizedOreRecipe(4, "sns", "ncn", "sns", 'n', Block.music, 's', "ingotSilver", 'c', ItemStacks.pcb);

		MachineRegistry.WEATHERCONTROLLER.addCrafting("s s", "sls", "pcp", 'l', Block.daylightSensor, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel);

		MachineRegistry.MOBHARVESTER.addCrafting("shs", "sps", 'h', MachineRegistry.HEATER.getCraftedProduct(), 'p', Item.enderPearl, 's', ItemStacks.basepanel);

		MachineRegistry.PROJECTOR.addCrafting("sss", "gcl", "ppp", 'c', ItemStacks.pcb, 's', ItemStacks.steelingot, 'g', Block.glass, 'l', Block.glowStone, 'p', ItemStacks.basepanel);

		MachineRegistry.REFRESHER.addCrafting("ses", "epe", "ses", 'p', Item.enderPearl, 's', ItemStacks.steelingot, 'e', ReikaItemHelper.lapisDye);

		MachineRegistry.CAVESCANNER.addCrafting("sps", "pcp", "sns", 'n', ItemStacks.sonar, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel);

		MachineRegistry.SCALECHEST.addCrafting("sss", "scs", "sss", 'c', Block.chest, 's', ItemStacks.steelingot);

		MachineRegistry.SPILLER.addCrafting("sps", "s s", 'p', ItemStacks.pipe, 's', ItemStacks.steelingot);

		MachineRegistry.SMOKEDETECTOR.addCrafting(" S ", "RRR", " N ", 'S', ReikaItemHelper.stoneSlab, 'R', Item.redstone, 'N', Block.music);

		MachineRegistry.IGNITER.addCrafting("OGO", "GCG", "OGO", 'O', Block.obsidian, 'G', Item.ingotGold, 'C', ItemStacks.combustor);

		MachineRegistry.CONTAINMENT.addCrafting("lnl", "ddd", "sgs", 'd', Item.diamond, 's', ItemStacks.basepanel, 'n', Item.netherStar, 'g', Item.ingotGold, 'l', ReikaItemHelper.purpleDye);

		MachineRegistry.MAGNETIZER.addCrafting("p p", "gmg", "prp", 'r', Item.redstone, 'p', ItemStacks.basepanel, 'm', ItemStacks.mount, 'g', ItemStacks.goldcoil);

		MachineRegistry.FREEZEGUN.addCrafting(" ss", "iig", "sb ", 'b', ItemStacks.railbase, 'i', Block.ice, 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit);

		MachineRegistry.SCREEN.addCrafting("sss", "mcs", "ppp", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'm', ItemStacks.screen, 'c', ItemStacks.pcb);

		MachineRegistry.CCTV.addCrafting(" g ", "brs", " p ", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'b', Block.thinGlass, 'r', Item.redstone, 'g', Item.ingotGold);

		MachineRegistry.PURIFIER.addCrafting("sbs", "prp", "sps", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'r', Item.redstone, 'b', Block.fenceIron);

		MachineRegistry.MIRROR.addCrafting("bmb", " g ", "pcp", 'b', RotaryCraft.blastpane, 'p', ItemStacks.basepanel, 'c', ItemStacks.pcb, 'm', ItemStacks.mirror, 'g', ItemStacks.steelgear);

		MachineRegistry.SOLARTOWER.addCrafting("pPp", "iPi", "pPp", 'p', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'i', ReikaItemHelper.inksac);

		MachineRegistry.RAILGUN.addCrafting(" H ", " A ", " B ", 'B', ItemStacks.railbase, 'A', ItemStacks.railaim, 'H', ItemStacks.railhead);

		MachineRegistry.LASERGUN.addCrafting("CLB", "APG", " b ", 'b', ItemStacks.railbase, 'C', ItemStacks.bulb, 'L', ItemStacks.lens, 'P', ItemStacks.power, 'B', ItemStacks.barrel, 'A', ItemStacks.railaim, 'G', ItemStacks.gearunit);

		MachineRegistry.ITEMCANNON.addCrafting("s c", "pcp", "pCr", 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.gearunit, 'r', Block.chest, 'p', ItemStacks.basepanel);

		MachineRegistry.BLOCKCANNON.addCrafting("s c", "pcp", "pCr", 'C', ItemStacks.compressor,  'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Block.chest, 'p', ItemStacks.basepanel);

		MachineRegistry.FRICTION.addCrafting("S  ", "Sss", "SPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem);

		MachineRegistry.LANDMINE.addCrafting(" P ", "RGR", "SIS", 'P', Block.pressurePlateStone, 'S', ItemStacks.steelingot, 'I', ItemStacks.igniter, 'R', Item.redstone, 'G', Item.ingotGold);

		MachineRegistry.BUCKETFILLER.addCrafting("SPS", "PCP", "SPS", 'P', ItemStacks.pipe, 'S', ItemStacks.steelingot, 'C', Block.chest);

		MachineRegistry.SPYCAM.addCrafting("SCS", "PRP", "SGS", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Block.thinGlass, 'R', Item.redstone);

		MachineRegistry.COOLINGFIN.addSizedCrafting(3, "SSS", "SSS", "PPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem);
		MachineRegistry.COOLINGFIN.addSizedOreRecipe(2, "SSS", "SSS", "PPP", 'P', "ingotTin", 'S', "ingotCopper");

		MachineRegistry.SELFDESTRUCT.addCrafting("STS", "TCs", "STS", 'T', Block.tnt, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem, 'C', ItemStacks.pcb);

		MachineRegistry.DISPLAY.addCrafting("SES", "SCS", " P ", 'P', ItemStacks.basepanel, 'E', Item.enderPearl, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb);

		MachineRegistry.COMPRESSOR.addCrafting("SSS", " G ", "CPC", 'S', ItemStacks.steelingot, 'G', Block.glass, 'P', Block.pistonBase, 'C', ItemStacks.compressor);

		MachineRegistry.PNEUENGINE.addCrafting("ppS", "sT ", "PPP", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem, 'p', ItemStacks.pipe, 'P', ItemStacks.basepanel, 'T', ItemStacks.impeller);

		MachineRegistry.LAMP.addCrafting("SGS", "GgG", "SGS", 'S', ItemStacks.steelingot, 'G', Block.glass, 'g', Block.glowStone);

		MachineRegistry.MULTICLUTCH.addCrafting("PSP", "SGS", "RSR", 'R', Item.redstone, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.gearunit, 'P', ItemStacks.basepanel);

		MachineRegistry.FUELENHANCER.addCrafting("PGP", "gMg", "PGP", 'G', Block.thinGlass, 'M', ItemStacks.mixer, 'P', ItemStacks.basepanel, 'g', Item.ingotGold);

		MachineRegistry.LINEBUILDER.addCrafting("sbs", "sps", "PgP", 'g', ItemStacks.gearunit, 'p', Block.pistonBase, 'P', ItemStacks.basepanel, 'b', ItemStacks.bedingot, 's', ItemStacks.steelingot);

		MachineRegistry.TERRAFORMER.addCrafting("SsS", "ici", "PiP", 'i', ItemStacks.impeller, 'S', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'P', ItemStacks.basepanel, 's', ItemStacks.screen);

		MachineRegistry.EMP.addCrafting("GDG", "GsG", "PnP", 'P', ItemStacks.basepanel, 'n', Item.netherStar, 'G', ItemStacks.goldcoil, 'D', Block.blockDiamond, 's', ItemStacks.shaftcore);

		MachineRegistry.ARROWGUN.addCrafting("SSS", "BDB", "SBS", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', Block.dispenser);

		MachineRegistry.STEAMTURBINE.addCrafting("SPS", "GTG", "SPS", 'G', Block.glass, 'S', ItemStacks.steelingot, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel);

		MachineRegistry.BOILER.addCrafting("SPS", "G G", "SIS", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.pipe, 'G', Block.glass);

		MachineRegistry.FERTILIZER.addCrafting("PIP", " S ", "BCB", 'P', ItemStacks.pipe, 'S', ItemStacks.shaftitem, 'I', ItemStacks.impeller, 'C', Block.chest, 'B', ItemStacks.basepanel);

		MachineRegistry.LAVAMAKER.addCrafting("SRS", "PGP", "SsS", 's', ItemStacks.shaftitem, 'S', ItemStacks.steelingot, 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'P', ItemStacks.basepanel, 'G', ItemStacks.steelgear);

		MachineRegistry.BEAMMIRROR.addCrafting(" m ", " s ", " p ", 'p', ItemStacks.basepanel, 'm', ItemStacks.mirror, 's', ItemStacks.steelingot);

		MachineRegistry.VALVE.addSizedCrafting(4, "sGs", "OGO", "sGs", 'O', Block.blockRedstone, 'G', Block.glass, 's', ItemStacks.steelingot);

		MachineRegistry.BYPASS.addSizedCrafting(4, "sGs", "OGO", "sGs", 'O', Block.netherBrick, 'G', Block.glass, 's', ItemStacks.steelingot);

		MachineRegistry.SEPARATION.addSizedCrafting(4, "sGs", "OGO", "sGs", 'O', Block.blockLapis, 'G', Block.glass, 's', ItemStacks.steelingot);

		MachineRegistry.GENERATOR.addCrafting("gps", "iGs", "ppp", 'p', ItemStacks.basepanel, 'g', Item.ingotGold, 's', ItemStacks.steelingot, 'G', ItemStacks.generator, 'i', ItemStacks.impeller, 's', ItemStacks.shaftcore);

		MachineRegistry.ELECTRICMOTOR.addCrafting("S S", "BCB", "S S", 'S', ItemStacks.steelingot, 'B', ItemStacks.basepanel, 'C', ItemStacks.shaftcore);

		MachineRegistry.SONICBORER.addCrafting("ss ", "Icp", "bbb", 'p', ItemStacks.pipe, 's', ItemStacks.steelingot, 'c', ItemStacks.compressor, 'b', ItemStacks.basepanel, 'I', Block.fenceIron);

		MachineRegistry.AIRGUN.addCrafting("sps", "I S", "sps", 'I', ItemStacks.impeller, 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'S', ItemStacks.sonar);

		MachineRegistry.FUELENGINE.addCrafting("CGC", "fgs", "bIb", 'g', ItemStacks.gearunit8, 'C', ItemStacks.cylinder, 'G', Item.ingotGold, 'f', ItemStacks.gearunit, 'b', ItemStacks.basepanel, 'I', ItemStacks.impeller, 's', ItemStacks.shaftcore);

		MachineRegistry.AGGREGATOR.addCrafting("SPS", "GCG", "SsS", 's', ItemStacks.shaftitem, 'G', Block.thinGlass, 'S', ItemStacks.steelingot, 'P', ItemStacks.basepanel, 'C', ItemStacks.compressor);

		MachineRegistry.FILLINGSTATION.addCrafting("ppS", " iR", "ppB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'i', ItemStacks.impeller, 'p', ItemStacks.pipe, 'R', MachineRegistry.RESERVOIR.getCraftedProduct());

		MachineRegistry.BELT.addSizedCrafting(2, "sBs", " G ", "sBs", 'B', ItemStacks.basepanel, 'G', ItemStacks.hub, 's', ItemStacks.steelingot);

		MachineRegistry.VANDEGRAFF.addCrafting("shs", "bgb", "php", 'h', ItemStacks.hub, 'p', ItemStacks.basepanel, 'b', Item.brick, 'g', Block.thinGlass, 's', ItemStacks.steelingot);

		MachineRegistry.DISTILLER.addCrafting("PGP", "gMg", "PGP", 'G', Block.thinGlass, 'M', ItemStacks.mixer, 'P', ItemStacks.basepanel, 'g', Item.ingotIron);

		MachineRegistry.BIGFURNACE.addCrafting("SFS", "FRF", "SRS", 'S', ItemStacks.basepanel, 'F', Block.furnaceIdle, 'R', MachineRegistry.RESERVOIR.getCraftedProduct());

		MachineRegistry.SUCTION.addSizedCrafting(4, "SGS", "SGS", "SGS", 'S', Block.sandStone, 'G', Block.glass);

		MachineRegistry.SORTING.addCrafting("SHS", " C ", "P P", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'H', Block.hopperBlock, 'C', ItemStacks.pcb);

		MachineRegistry.CRYSTALLIZER.addCrafting("SFS", "FIF", "BBB", 'S', ItemStacks.steelingot, 'B', ItemStacks.basepanel, 'F', MachineRegistry.COOLINGFIN.getCraftedProduct(), 'I', ItemStacks.impeller);

		MachineRegistry.POWERBUS.addSizedCrafting(4, "SMS", "MCM", "SMS", 'S', ItemStacks.steelingot, 'M', ItemStacks.bearing, 'C', ItemStacks.belt);

		MachineRegistry.BUSCONTROLLER.addCrafting("SMS", "MCM", "SMS", 'S', ItemStacks.steelingot, 'M', ItemStacks.bearing, 'C', ItemStacks.pcb);

		MachineRegistry.PARTICLE.addSizedCrafting(4, "SDS", "PCP", "SIS", 'S', ItemStacks.steelingot, 'P', ItemStacks.basepanel, 'C', ItemStacks.pcb, 'D', Block.dispenser, 'I', ItemStacks.impeller);
		MachineRegistry.PARTICLE.addSizedOreRecipe(4, "SDS", "PCP", "SIS", 'S', "ingotTin", 'P', ItemStacks.basepanel, 'C', ItemStacks.pcb, 'D', Block.dispenser, 'I', ItemStacks.impeller);

		MachineRegistry.LAWNSPRINKLER.addCrafting("PPP", " P ", "BIB", 'I', ItemStacks.impeller, 'P', ItemStacks.pipe, 'B', ItemStacks.basepanel);

		MachineRegistry.GRINDSTONE.addCrafting("S S", "sBs", "ppp", 'p', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'S', ItemStacks.steelingot, 'B', Block.stone);

		MachineRegistry.BLOWER.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "BBB", "PIP", "BBB", 'B', ItemStacks.basepanel, 'I', ItemStacks.impeller, 'P', ItemStacks.pipe);

		MachineRegistry.DEFOLIATOR.addCrafting("P P", "SPS", "BIB", 'B', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'I', ItemStacks.impeller, 'S', ItemStacks.steelingot);

		MachineRegistry.REFRIGERATOR.addCrafting("SPS", "CcD", "pPp", 'p', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'D', ItemStacks.diffuser, 'C', ItemStacks.compressor, 'c', ItemStacks.condenser, 'S', ItemStacks.steelingot);

		MachineRegistry.COMPOSTER.addCrafting(" S ", "S S", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot);
		MachineRegistry.COMPOSTER.addOreRecipe(" S ", "S S", "BBB", 'B', ItemStacks.basepanel, 'S', "ingotTin");

		MachineRegistry.GASTANK.addCrafting("SIS", "PRP", "PPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'R', MachineRegistry.RESERVOIR.getCraftedProduct());

		//MachineRegistry.CRAFTER.addCrafting("SCS", "PcP", "SPS", 'S', ItemStacks.steelingot, 'C', Block.workbench, 'P', ItemStacks.basepanel, 'c', ItemStacks.pcb);

		MachineRegistry.ANTIAIR.addCrafting("sss", "ppc", " Ba", 'p', ItemStacks.pipe, 'c', ItemStacks.compressor, 's', ItemStacks.steelingot, 'a', ItemStacks.railaim, 'B', ItemStacks.railbase);

		MachineRegistry.PIPEPUMP.addCrafting("BBB", "PIP", "BBB", 'B', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.pipe);

		MachineRegistry.CHAIN.addSizedCrafting(2, "sBs", " G ", "sBs", 'B', ItemStacks.basepanel, 'G', ItemStacks.steelgear, 's', ItemStacks.steelingot);

		MachineRegistry.CENTRIFUGE.addCrafting("SGS", "S S", "PgP", 'P', ItemStacks.basepanel, 'g', ItemStacks.gearunit4, 'S', ItemStacks.steelingot, 'G', Block.thinGlass);
	}

	private static void addCraftItems() {
		GameRegistry.addRecipe(ItemStacks.impeller, " S ", "SGS", " S ", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear);
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.impeller, " S ", "SGS", " S ", 'S', "ingotTin", 'G', ItemStacks.steelgear));
		GameRegistry.addRecipe(ItemStacks.compressor, "SSS", "SGS", "SSS", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear);
		GameRegistry.addRecipe(ItemStacks.turbine, "sss", "sGs", "sss", 's', ItemStacks.prop, 't', ItemStacks.tungsteningot, 'G', ItemStacks.compressor);
		GameRegistry.addRecipe(ItemStacks.diffuser, " SS", "S  ", " SS", 'S', ItemStacks.steelingot);
		GameRegistry.addRecipe(ItemStacks.combustor, "SSS", "SRS", "SGS", 'S', ItemStacks.steelingot, 'G', ItemStacks.igniter, 'R', Item.redstone);
		GameRegistry.addRecipe(ItemStacks.radiator, "GGG", "PPP", "SSS", 'G', Item.ingotGold, 'S', ItemStacks.steelingot, 'P', ItemStacks.pipe);
		GameRegistry.addRecipe(ItemStacks.condenser, "SPS", "PSP", "SPS", 'S', ItemStacks.steelingot, 'P', ItemStacks.pipe);
		GameRegistry.addRecipe(ItemStacks.goldcoil, "GGG", "GSG", "GGG", 'S', ItemStacks.steelingot, 'G', Item.ingotGold);

		ReikaRecipeHelper.addOreRecipe(ItemStacks.goldcoil, "GGG", "GSG", "GGG", 'S', ItemStacks.steelingot, 'G', "ingotElectrum");

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.cylinder, 2), new Object[]{
			"SSS", "S S", "SSS", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.compoundturb, new Object[]{
				" tS", "tst", "St ", 'S', ItemStacks.turbine, 's', ItemStacks.shaftcore, 't', ItemStacks.tungsteningot});

		GameRegistry.addRecipe(ItemStacks.shaftcore, new Object[]{
				"  s", " S ", "s  ", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem});
		GameRegistry.addRecipe(ItemStacks.igniter, new Object[]{
				"G G", "SRS", "SSS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', Item.ingotGold});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.igniter, "G G", "SRS", "SSS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', "ingotElectrum");

		GameRegistry.addRecipe(ItemStacks.waterplate, new Object[]{
				"PPP", "PPP", "PPP", 'P', ItemStacks.basepanel});
		GameRegistry.addRecipe(ItemStacks.prop, new Object[]{
				" S ", " I ", " P ", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem, 'I', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.hub, new Object[]{
				"  B", " C ", "G  ", 'G', ItemStacks.steelgear, 'B', ItemStacks.bearing, 'C', ItemStacks.shaftcore});
		GameRegistry.addRecipe(ItemStacks.mirror, new Object[]{
				"GGG", "III", 'G', Block.glass, 'I', Item.ingotIron});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.mirror, "GGG", "III", 'G', Block.glass, 'I', "ingotSilver");

		GameRegistry.addRecipe(ItemStacks.railhead, new Object[]{
				"LLL", "LGL", "LLL", 'G', ItemStacks.power, 'L', ItemStacks.lim});
		GameRegistry.addRecipe(ItemStacks.railbase, new Object[]{
				" S ", "PGP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit, 'S', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.railaim, new Object[]{
				"sds", "CRC", "sgs", 'R', ItemStacks.radar, 'C', ItemStacks.pcb, 's', ItemStacks.steelingot, 'd', Item.diamond, 'g', ItemStacks.generator});
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
				"SOD", "ODB", "DBB", 'S', ItemStacks.steelingot, 'D', Item.diamond, 'O', Block.obsidian, 'B', ItemStacks.bedrockdust});
		GameRegistry.addRecipe(ItemStacks.screen, new Object[]{
				"SGS", "SCS", 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Block.thinGlass});
		GameRegistry.addRecipe(ItemStacks.mixer, new Object[]{
				" S ", "SIS", " S ", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller});
		GameRegistry.addRecipe(ItemStacks.saw, new Object[]{
				"S S", " C ", "S S", 'S', ItemStacks.steelingot, 'C', ItemStacks.steelgear});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.pcb, 2), new Object[]{
			"PGP", "RER", "GPG", 'P', ItemStacks.steelingot, 'G', Item.ingotGold, 'R', Item.redstone, 'E', Item.enderPearl});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.pcb, "PGP", "RER", "GPG", 'P', ItemStacks.steelingot, 'G', "ingotElectrum", 'R', Item.redstone, 'E', "ingotCopper");

		GameRegistry.addRecipe(ItemStacks.sonar, new Object[]{
				" S ", "SNS", "RCR", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'N', Block.music, 'C', ItemStacks.pcb});
		GameRegistry.addRecipe(ItemStacks.radar, new Object[]{
				"SSS", " G ", "RMR", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', Item.ingotGold, 'M', new ItemStack(RotaryCraft.engineitems.itemID, 1, 0)});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.belt, DifficultyEffects.BELTCRAFT.getInt()), new Object[]{
			"LLL", "LSL", "LLL", 'L', Item.leather, 'S', ItemStacks.steelingot});
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
				" D ", "DGD", " D ", 'D', Item.diamond, 'G', RotaryCraft.blastglass});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.power, 2), new Object[]{
			"RER", "GGG", "SSS", 'R', Item.redstone, 'G', Item.ingotGold, 'E', Item.eyeOfEnder, 'S', ItemStacks.steelingot});

		ReikaRecipeHelper.addOreRecipe(ItemStacks.power, "RER", "GGG", "SSS", 'R', Item.redstone, 'G', "ingotElectrum", 'E', "ingotCopper", 'S', ItemStacks.steelingot);

		GameRegistry.addRecipe(ItemStacks.barrel, new Object[]{
				"OOO", "gtG", "OOO", 't', ItemStacks.tungsteningot, 'O', Block.obsidian, 'G', RotaryCraft.blastglass, 'g', Block.glowStone});
		GameRegistry.addRecipe(ItemStacks.bulb, new Object[]{
				"GGG", "BDB", "BRB", 'D', Item.netherStar, 'G', Block.glowStone, 'R', Item.redstone, 'B', Item.blazeRod});

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
			"  B", " B ", "B  ", 'B', Block.stone});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.shaftitem, DifficultyEffects.PARTCRAFT.getInt()), new Object[]{
			"  B", " B ", "B  ", 'B', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.diamondshaft, DifficultyEffects.PARTCRAFT.getInt()), new Object[]{
			"  B", " B ", "B  ", 'B', Item.diamond});

		Object[] params = new Object[]{" D ", "DSD", " D ", 'D', ItemStacks.bedrockdust, 'S', ItemStacks.shaftitem};
		//GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockshaft, 4), params);
		RecipesBlastFurnace.getRecipes().add3x3Crafting(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockshaft, 4), 1000, 2, 0, params);

		GameRegistry.addRecipe(ItemStacks.wormgear, new Object[]{
				"S  ", " G ", "  S", 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.woodgear, new Object[]{
				" W ", "WWW", " W ", 'W', "plankWood"}));
		GameRegistry.addRecipe(new ItemStack(ItemStacks.stonegear.itemID, 2, ItemStacks.stonegear.getItemDamage()), new Object[]{
			" W ", "WWW", " W ", 'W', Block.stone});
		GameRegistry.addRecipe(new ItemStack(ItemStacks.diamondgear.itemID, 8, ItemStacks.diamondgear.getItemDamage()), new Object[]{
			" W ", "WWW", " W ", 'W', Item.diamond});

		params = new Object[]{"bWb", "WWW", "bWb", 'b', ItemStacks.bedrockdust, 'W', ItemStacks.steelingot};
		//GameRegistry.addRecipe(new ItemStack(ItemStacks.bedrockgear.itemID, 8, ItemStacks.bedrockgear.getItemDamage()), params);
		RecipesBlastFurnace.getRecipes().add3x3Crafting(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockgear, 8), 1000, 2, 0, params);


		GameRegistry.addRecipe(new ShapedOreRecipe(ItemStacks.flywheelcore, new Object[]{
				"WWW", "WGW", "WWW", 'W', "plankWood", 'G', ItemStacks.steelgear}));
		GameRegistry.addRecipe(ItemStacks.flywheelcore2, new Object[]{
				"WWW", "WGW", "WWW", 'W', Block.stone, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore3, new Object[]{
				"WWW", "WGW", "WWW", 'W', Item.ingotIron, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore4, new Object[]{
				"WWW", "WGW", "WWW", 'W', Item.ingotGold, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(ItemStacks.lim, new Object[]{
				"WRW", "NNN", 'W', ItemStacks.goldcoil, 'N', ItemStacks.steelingot, 'R', Item.redstone});

		GameRegistry.addRecipe(ItemStacks.generator, new Object[]{
				"  G", " C ", "G  ", 'G', ItemStacks.goldcoil, 'C', ItemStacks.shaftcore});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.chain, 4), "s s", " s ", "s s", 's', ItemStacks.steelingot);
	}

	private static void addToolItems() {
		ItemRegistry.SPRING.addRecipe(" S ", "S S", " S ", 'S', ItemStacks.steelingot);
		ItemRegistry.STRONGCOIL.addBlastRecipe(1000, 4, "SDS", "BCB", "SDS", 'S', ItemStacks.steelingot, 'C', ItemRegistry.SPRING.getStackOf(), 'B', ItemStacks.bedrockdust, 'D', Item.diamond);

		ItemRegistry.TARGET.addRecipe(" E ", "SRS", "SLS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'E', Item.enderPearl, 'L', ReikaItemHelper.lapisDye);

		if (ModList.HARVESTCRAFT.isLoaded()) {
			RotaryCraft.logger.log("HarvestCraft found, not loading iron screwdriver recipe.");
		}
		else {
			ItemRegistry.SCREWDRIVER.addOreRecipe("I  ", " S ", "  W", 'S', "stickWood", 'I', Item.ingotIron, 'W', "plankWood");
			RotaryCraft.logger.log("HarvestCraft not found, loading iron screwdriver recipe.");
		}

		ItemRegistry.SCREWDRIVER.addOreRecipe("I  ", " S ", "  W", 'S', "stickWood", 'I', ItemStacks.steelingot, 'W', "plankWood");
		ItemRegistry.METER.addOreRecipe(" W ", "WEW", "SSS", 'S', ItemStacks.steelingot, 'E', Item.redstone, 'I', Item.ingotIron, 'W', "plankWood");
		ItemRegistry.HANDBOOK.addRecipe("RSR", "PPP", "PPP", 'R', Item.redstone, 'S', Item.ingotIron, 'P', Item.paper);

		ItemRegistry.BEDPICK.addEnchantedBlastRecipe(1000, 4, "BBB", " S ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDAXE.addBlastRecipe(1000, 4, "BB ", "BS ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDSHOVEL.addBlastRecipe(1000, 4, "B", "S", "S", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDSWORD.addEnchantedBlastRecipe(1000, 4, "B", "B", "S", 'B', ItemStacks.bedingot, 'S', ItemStacks.shaftitem);
		ItemRegistry.BEDHOE.addBlastRecipe(1000, 4, "II", " S", " S", 'S', ItemStacks.shaftitem, 'I', ItemStacks.bedingot);
		ItemRegistry.BEDHOE.addBlastRecipe(1000, 4, "II", "S ", "S ", 'S', ItemStacks.shaftitem, 'I', ItemStacks.bedingot);
		ItemRegistry.BEDSHEARS.addBlastRecipe(1000, 4, " B", "B ", 'B', ItemStacks.bedingot);
		ItemRegistry.BEDSICKLE.addEnchantedBlastRecipe(1000, 4, " B ", "  B", "SB ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot);
		ItemRegistry.BEDGRAFTER.addBlastRecipe(1000, 4, "  S", " s ", "s  ", 'S', ItemStacks.bedingot, 's', ItemStacks.shaftitem);
		ItemRegistry.BEDSAW.addBlastRecipe(1000, 4, "sss", " SS", " bb", 'b', ItemStacks.bedingot, 's', ItemStacks.shaftitem, 'S', Item.ingotIron);
		ItemRegistry.BEDHELM.addEnchantedBlastRecipe(1200, 4, "III", "I I", 'I', ItemStacks.bedingot);
		ItemRegistry.BEDBOOTS.addEnchantedBlastRecipe(1200, 4, "I I", "I I", 'I', ItemStacks.bedingot);
		ItemRegistry.BEDCHEST.addEnchantedBlastRecipe(1200, 4, "I I", "III", "III", 'I', ItemStacks.bedingot);
		ItemRegistry.BEDLEGS.addEnchantedBlastRecipe(1200, 4, "III", "I I", "I I", 'I', ItemStacks.bedingot);

		ItemRegistry.STEELPICK.addRecipe(new ShapedOreRecipe(ItemRegistry.STEELPICK.getStackOf(), "BBB", " S ", " S ", 'S', "stickWood", 'B', ItemStacks.steelingot));
		ItemRegistry.STEELAXE.addRecipe(new ShapedOreRecipe(ItemRegistry.STEELAXE.getStackOf(), "BB ", "BS ", " S ", 'S', "stickWood", 'B', ItemStacks.steelingot));
		ItemRegistry.STEELSHOVEL.addRecipe(new ShapedOreRecipe(ItemRegistry.STEELSHOVEL.getStackOf(), " B ", " S ", " S ", 'S', "stickWood", 'B', ItemStacks.steelingot));
		ItemRegistry.STEELSWORD.addEnchantedRecipe("B", "B", "S", 'B', ItemStacks.steelingot, 'S', Item.stick);
		ItemRegistry.STEELHOE.addRecipe("II ", " S ", " S ", 'S', Item.stick, 'I', ItemStacks.steelingot);
		ItemRegistry.STEELHOE.addRecipe(" II", " S ", " S ", 'S', Item.stick, 'I', ItemStacks.steelingot);
		ItemRegistry.STEELSHEARS.addRecipe(" B", "B ", 'B', ItemStacks.steelingot);
		ItemRegistry.STEELSICKLE.addRecipe(" B ", "  B", "SB ", 'S', Item.stick, 'B', ItemStacks.steelingot);

		ItemRegistry.GRAFTER.addRecipe("  S", "Ss ", "CS ", 'C', ItemRegistry.SPRING.getStackOf(), 'S', ItemStacks.steelingot, 's', Item.stick);


		ItemRegistry.STEELHELMET.addRecipe("III", "I I", 'I', ItemStacks.steelingot);
		ItemRegistry.STEELBOOTS.addRecipe("I I", "I I", 'I', ItemStacks.steelingot);
		ItemRegistry.STEELCHEST.addRecipe("I I", "III", "III", 'I', ItemStacks.steelingot);
		ItemRegistry.STEELLEGS.addRecipe("III", "I I", "I I", 'I', ItemStacks.steelingot);

		ItemRegistry.NVH.addShapelessRecipe(Item.helmetDiamond, ItemRegistry.NVG.getStackOf());

		ItemRegistry.ULTRASOUND.addRecipe(" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar);
		ItemRegistry.MOTION.addRecipe(" nr", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar, 'r', ItemStacks.radar);
		ItemRegistry.VACUUM.addRecipe(" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.impeller, 'n', ItemStacks.diffuser);
		ItemRegistry.STUNGUN.addRecipe(" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.sonar, 'n', ItemStacks.diffuser);
		ItemRegistry.GRAVELGUN.addRecipe(" d ", "gcg", "sss", 's', ItemStacks.steelingot, 'c', Block.chest, 'd', Block.dispenser, 'g', ItemStacks.steelgear);
		ItemRegistry.FIREBALL.addRecipe("b b", "scs", "srs", 's', ItemStacks.steelingot, 'c', ItemStacks.combustor, 'r', Item.redstone, 'b', Item.blazeRod);
		ItemRegistry.HANDCRAFT.addRecipe(" g ", "scs", " g ", 's', ItemStacks.steelingot, 'g', Item.ingotGold, 'c', Block.workbench);
		ItemRegistry.NVG.addRecipe("scs", "ese", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'e', Item.eyeOfEnder);

		ItemRegistry.IOGOGGLES.addRecipe("scs", "ese", 's', ItemStacks.steelingot, 'c', Item.enderPearl, 'e', Item.redstone);

		ItemRegistry.KEY.addRecipe("s", "s", "P", 'P', ItemStacks.basepanel, 's', ItemStacks.steelingot);

		ItemRegistry.TILESELECTOR.addRecipe(" l ", "srs", "ses", 'e', Item.enderPearl, 'r', Item.redstone, 'l', ReikaItemHelper.lapisDye, 's', ItemStacks.steelingot);

		ItemRegistry.JETPACK.addRecipe("CRC", "cBc", "d d", 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'B', ItemStacks.basepanel, 'd', ItemStacks.diffuser, 'c', ItemStacks.compressor, 'C', ItemStacks.combustor);

		ItemRegistry.PUMP.addRecipe(" sP", "sIs", "sRs", 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 's', ItemStacks.steelingot, 'P', ItemStacks.pipe, 'I', ItemStacks.impeller);

		ItemRegistry.JUMP.addRecipe("GbG", "SgS", "B B", 'B', ItemStacks.basepanel, 'G', ItemStacks.steelgear, 'b', ItemRegistry.STEELBOOTS.getStackOf(), 'g', ItemStacks.gearunit, 'S', ItemRegistry.SPRING.getStackOf());

		ItemRegistry.FUEL.addRecipe("SBS", "BGB", "SPS", 'P', ItemStacks.pipe, 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'G', Block.glass);

		ItemRegistry.DISK.addSizedRecipe(4, "wRw", "RSR", "wRw", 'w', ReikaItemHelper.blackWool, 'R', Item.redstone, 'S', ItemStacks.steelingot);

		ItemRegistry.CRAFTPATTERN.addSizedRecipe(4, " S ", " B ", " S ", 'S', ItemStacks.steelingot, 'B', ItemStacks.basepanel);

		ItemRegistry.UPGRADE.addMetaRecipe(0, " R ", "gGg", " b ", 'b', ItemStacks.basepanel, 'R', ItemStacks.radiator, 'G', ItemStacks.gearunit, 'g', Item.ingotGold);

		ItemRegistry.UPGRADE.addMetaRecipe(1, "SES", "ERE", "SES", 'R', Item.redstone, 'S', ItemStacks.steelingot, 'E', ItemRegistry.ETHANOL.getStackOf());
		ItemRegistry.UPGRADE.addMetaRecipe(2, "SES", "ERE", "SES", 'R', ItemStacks.shaftcore, 'S', ItemStacks.steelingot, 'E', Item.ingotGold);
		ItemRegistry.UPGRADE.addMetaRecipe(3, "SES", "ERE", "SES", 'R', ItemStacks.goldcoil, 'S', ItemStacks.steelingot, 'E', ItemStacks.redgoldingot);
		ItemRegistry.UPGRADE.addMetaRecipe(4, "SES", "ERE", "SES", 'R', ItemStacks.redgoldingot, 'S', ItemStacks.steelingot, 'E', ItemStacks.tungsteningot);
		ItemRegistry.UPGRADE.addMetaBlastRecipe(1800, 8, 5, "SES", "ERE", "SES", 'R', ItemStacks.bedingot, 'S', ItemStacks.redgoldingot, 'E', ItemStacks.bedrockdust);
	}

	private static void addMisc() {
		if (ConfigRegistry.CRAFTABLEBEDROCK.getState())
			GameRegistry.addRecipe(new ItemStack(Block.bedrock.blockID, 1, 0), new Object[]{
				"DDD", "DSD", "DDD", 'D', ItemStacks.bedrockdust, 'S', Block.stone});

		GameRegistry.addRecipe(ItemRegistry.CANOLA.getCraftedMetadataProduct(1, 1), new Object[]{
			"DDD", "DDD", "DDD", 'D', ItemRegistry.CANOLA.getStackOf()});

		GameRegistry.addShapelessRecipe(ItemRegistry.CANOLA.getCraftedProduct(9),
				ItemRegistry.CANOLA.getStackOfMetadata(1));

		GameRegistry.addRecipe(ReikaItemHelper.oakWood, new Object[]{
				"WW", "WW", 'W', ItemStacks.sawdust});
		GameRegistry.addRecipe(ReikaItemHelper.spruceWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.BLACK.getStackOf()});
		GameRegistry.addRecipe(ReikaItemHelper.birchWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.WHITE.getStackOf()});
		GameRegistry.addRecipe(ReikaItemHelper.jungleWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.RED.getStackOf()});

		GameRegistry.addRecipe(new ItemStack(Item.paper.itemID, 8, 0), new Object[]{
			" W ", "SSS", "RRR", 'R', Block.stone, 'S', ItemStacks.sawdust, 'W', Item.bucketWater});

		GameRegistry.addRecipe(new ItemStack(RotaryCraft.blastpane, 16, 0), new Object[]{
			"OOO", "OOO", 'O', RotaryCraft.blastglass});

		GameRegistry.addRecipe(new ItemStack(Block.tnt, 2), "sns", "nSn", "sns", 's', Item.sugar, 'S', Block.sand, 'n', ItemStacks.nitrate);

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 15), ItemStacks.steelgear);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 48), ItemStacks.gearunit);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 114), ItemStacks.gearunit4);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 180), ItemStacks.gearunit8);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 244), ItemStacks.gearunit16);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.shaftitem);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.basepanel);
		//GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 45), ItemStacks.mount);

		ReikaRecipeHelper.addSmelting(ItemStacks.flour, new ItemStack(Item.bread), 0.2F);

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"rrr", "rrr", "rr ", 'r', Block.rail});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"rrr", "rrr", "rr ", 'r', Block.fenceIron});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"b", 'b', Item.bucketEmpty});

		GameRegistry.addRecipe(ItemStacks.steelblock, "BBB", "BBB", "BBB", 'B', ItemStacks.steelingot);
		WorktableRecipes.getInstance().addRecipe(ItemStacks.steelblock, "BBB", "BBB", "BBB", 'B', ItemStacks.steelingot);
		GameRegistry.addRecipe(ItemStacks.anthrablock, "BBB", "BBB", "BBB", 'B', ItemStacks.anthracite);
		GameRegistry.addRecipe(ItemStacks.lonsblock, "BBB", "BBB", "BBB", 'B', ItemStacks.lonsda);

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.shieldblock, 4), " S ", "SOS", " S ", 'S', ItemStacks.steelingot, 'O', Block.obsidian);

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 9), ItemStacks.steelblock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.anthracite, 9), ItemStacks.anthrablock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.lonsda, 9), ItemStacks.lonsblock);

		GameRegistry.addShapelessRecipe(ItemStacks.salt, Item.bucketWater);
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemStacks.silveriodide, ItemStacks.salt, "ingotSilver"));
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.nitrate, 4), Item.gunpowder, Item.redstone, ItemStacks.salt, ItemStacks.coaldust);
		GameRegistry.addRecipe(new ShapelessOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.redgolddust, 2), Item.redstone, "dustGold"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.redgolddust, 2), Item.redstone, ItemStacks.goldoreflakes));

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
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(3), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 5), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(4), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 6), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(5), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 7), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(6), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 8), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(7), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 9), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(8), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 10), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(9), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 11), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(10), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 12), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(11), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 13), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(12), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 14), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(13), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(amt, 15), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(14), 'p', ItemStacks.bedingot});

		GameRegistry.addRecipe(ItemRegistry.MINECART.getCraftedProduct(1), new Object[]{
			"g", "m", 'g', EngineType.GAS.getCraftedProduct(), 'm', new ItemStack(Item.minecartEmpty)});

		GameRegistry.addRecipe(ItemRegistry.SHELL.getCraftedProduct(16), new Object[]{
			" s ", "sns", " s ", 's', ItemStacks.steelingot, 'n', ItemStacks.nitrate});

		addSlideRecipes();

		GameRegistry.addRecipe(new ItemStack(RotaryCraft.decoTank.blockID, 4, 0), "SGS", "GGG", "SGS", 'S', ItemStacks.steelingot, 'G', Block.thinGlass);
	}

	private static void addSlideRecipes() {
		GameRegistry.addRecipe(ItemRegistry.SLIDE.getCraftedProduct(4), new Object[]{
			"PPP", "PGP", "PPP", 'G', Block.thinGlass, 'P', Item.paper});

		ItemStack is = ItemRegistry.SLIDE.getCraftedMetadataProduct(2, 24);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("file", "[NO FILE]");
		GameRegistry.addRecipe(is, new Object[]{
				"rPr", "PGP", "rPr", 'G', Block.thinGlass, 'P', Item.paper, 'r', Item.redstone});

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
				GameRegistry.addShapelessRecipe(ItemRegistry.SLIDE.getCraftedMetadataProduct(1, color+j), new ItemStack(Item.dyePowder.itemID, 1, i), ItemRegistry.SLIDE.getStackOfMetadata(j));
				//ReikaJavaLibrary.pConsole("Registering recipe with slide "+j+" and color "+i+" to result slide "+(color+j));
			}
		}
	}

	private static void addItemBlocks() {

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

		MachineRegistry.ENGINE.addMetaCrafting(EngineType.DC.ordinal(), "SSS", "SRs", "PRP", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.WIND.ordinal(), "SSS", "SHS", "SSS", 'S', ItemStacks.prop, 'H', ItemStacks.hub);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.STEAM.ordinal(), "ccc", "CIs", "PGP", 'c', Block.cobblestone, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', Item.ingotGold, 'C', ItemStacks.condenser);

		if (ReikaItemHelper.oreItemExists("ingotCopper"))
			MachineRegistry.ENGINE.addMetaOreRecipe(EngineType.STEAM.ordinal(), "ccc", "CIs", "PGP", 'c', Block.cobblestone, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', "ingotCopper", 'C', ItemStacks.condenser);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.GAS.ordinal(), "CgC", "SGs", "PIP", 'g', Item.ingotGold, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.gearunit, 'C', ItemStacks.cylinder);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.AC.ordinal(), "SSS", "SGs", "PRP", 'S', Item.ingotGold, 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.goldcoil);

		if (ReikaItemHelper.oreItemExists("ingotElectrum"))
			MachineRegistry.ENGINE.addMetaOreRecipe(EngineType.AC.ordinal(), "SSS", "SGs", "PRP", 'S', "ingotElectrum", 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.goldcoil);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.SPORT.ordinal(), "CrC", "SGs", "PIP", 'C', ItemStacks.cylinder, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'r', ItemStacks.radiator, 'G', ItemStacks.gearunit);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.HYDRO.ordinal(), "PPP", "PGP", "PPP", 'P', ItemStacks.waterplate, 'G', ItemStacks.shaftcore);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.MICRO.ordinal(), "CSS", "cTs", "PPP", 'S', ItemStacks.steelingot, 'C', ItemStacks.compressor, 'c', ItemStacks.combustor, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem);
		MachineRegistry.ENGINE.addMetaCrafting(EngineType.JET.ordinal(), "DCS", "ScS", "PTs", 'S', ItemStacks.steelingot, 'D', ItemStacks.diffuser, 'C', ItemStacks.compressor, 'c', ItemStacks.combustor, 'T', ItemStacks.compoundturb, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem);

		if (ConfigRegistry.ROTATEHOSE.getState()) {
			MachineRegistry.HOSE.addSizedOreRecipe(DifficultyEffects.PIPECRAFT.getInt(), "WWW", "GGG", "WWW", 'G', Block.glass, 'W', "plankWood");
			MachineRegistry.PIPE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "SSS", "GGG", "SSS", 'S', ItemStacks.steelingot, 'G', Block.glass);
			MachineRegistry.FUELLINE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "OOO", "GGG", "OOO", 'O', Block.obsidian, 'G', Block.glass);
		}
		else {
			MachineRegistry.HOSE.addSizedOreRecipe(DifficultyEffects.PIPECRAFT.getInt(), "WGW", "WGW", "WGW", 'G', Block.glass, 'W', "plankWood");
			MachineRegistry.PIPE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "SGS", "SGS", "SGS", 'S', ItemStacks.steelingot, 'G', Block.glass);
			MachineRegistry.FUELLINE.addSizedCrafting(DifficultyEffects.PIPECRAFT.getInt(), "OGO", "OGO", "OGO", 'O', Block.obsidian, 'G', Block.glass);
		}

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

		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 24, new ItemStack(Item.coal.itemID, 1, 0), 0.1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 25, new ItemStack(Item.ingotIron.itemID, 1, 0), 0.7F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 26, new ItemStack(Item.ingotGold.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 27, new ItemStack(Item.redstone.itemID, 4, 0), 0.5F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 28, new ItemStack(Item.dyePowder.itemID, 6, 4), 0.6F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 29, new ItemStack(Item.diamond.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 30, new ItemStack(Item.emerald.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 31, new ItemStack(Item.netherQuartz.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 32, new ItemStack(ItemStacks.silveringot.itemID, 1, ItemStacks.silveringot.getItemDamage()), 1F);

		ExtractorModOres.addSmelting();

		//ReikaRecipeHelper.addSmelting(ItemStacks.scrap, ItemStacks.steelingot, 0.4F);
		ReikaRecipeHelper.addSmelting(ItemStacks.ironscrap, new ItemStack(Item.ingotIron), 0.4F);
	}

	private static ItemStack addDamageNBT(ItemStack is) {
		is.setTagCompound(new NBTTagCompound());
		is.stackTagCompound.setInteger("damage", 0);
		return is;
	}

	private static void addRecipeToBoth(ItemStack out, Object... in) {
		GameRegistry.addRecipe(out, in);
		WorktableRecipes.getInstance().addRecipe(out, in);
	}
}
