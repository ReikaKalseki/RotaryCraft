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

import java.util.Locale;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.ReikaRegistryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryEntities;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RotaryRegistration {

	public static void addTileEntities() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			String label = "RC"+MachineRegistry.machineList.get(i).name().toLowerCase(Locale.ENGLISH).replaceAll("\\s","");
			String aux = "RC"+MachineRegistry.machineList.get(i).getDefaultName().toLowerCase(Locale.ENGLISH).replaceAll("\\s","");
			if (label.equals(aux)) {
				GameRegistry.registerTileEntity(MachineRegistry.machineList.get(i).getTEClass(), label);
			}
			else {
				GameRegistry.registerTileEntityWithAlternatives(MachineRegistry.machineList.get(i).getTEClass(), label, aux);
			}
			ReikaJavaLibrary.initClass(MachineRegistry.machineList.get(i).getTEClass());
		}
		for (int i = 0; i < EngineType.engineList.length; i++) {
			String label = "RC"+EngineType.engineList[i].name().toLowerCase(Locale.ENGLISH).replaceAll("\\s","");
			GameRegistry.registerTileEntity(EngineType.engineList[i].engineClass, label);
			ReikaJavaLibrary.initClass(EngineType.engineList[i].engineClass);
		}
		GameRegistry.registerTileEntity(TileEntityDecoTank.class, "RCDecoTank");
	}

	public static void addEntities() {
		ReikaRegistryHelper.registerModEntities(RotaryCraft.instance, RotaryEntities.entityList);
	}

	public static void loadOreDictionary() {
		if (!ModList.GREGTECH.isLoaded()) {//GT unificator causes an exploit, and no mods even use this anyways
			OreDictionary.registerOre("ingotHSLA", ItemStacks.steelingot); //though he has an entry, he does not add an alternative manufacture

			OreDictionary.registerOre("dustNetherrack", ItemStacks.netherrackdust);
			OreDictionary.registerOre("dustSoulSand", ItemStacks.tar);

			if (ConfigRegistry.HSLADICT.getState())
				OreDictionary.registerOre("ingotSteel", ItemStacks.steelingot);
		}
		else {
			RotaryCraft.logger.log("Gregtech is installed, a few OreDict entries are not being added to prevent interchangeability or unification issues");
		}
		OreDictionary.registerOre("ingotSilver", ItemStacks.silveringot);
		OreDictionary.registerOre("ingotAluminum", ItemStacks.aluminumingot);

		OreDictionary.registerOre("dustAluminum", ItemStacks.aluminumpowder);

		OreDictionary.registerOre("RotaryCraft:dustBedrock", ItemStacks.bedrockdust);
		OreDictionary.registerOre("RotaryCraft:ingotBedrock", ItemStacks.bedingot);

		OreDictionary.registerOre("glassHardened", BlockRegistry.BLASTGLASS.getBlockInstance());
		OreDictionary.registerOre("blockGlassHardened", BlockRegistry.BLASTGLASS.getBlockInstance());

		ExtractorModOres.registerRCIngots();
		ItemStacks.registerSteels();

		OreDictionary.registerOre("dustCoal", ItemStacks.coaldust);
		OreDictionary.registerOre("dustSalt", ItemStacks.salt);
		OreDictionary.registerOre("foodSalt", ItemStacks.salt);

		OreDictionary.registerOre("dustWood", ItemStacks.sawdust);
		OreDictionary.registerOre("pulpWood", ItemStacks.sawdust);

		OreDictionary.registerOre("silicon", ItemStacks.silicon);
		OreDictionary.registerOre("itemSilicon", ItemStacks.silicon);
		OreDictionary.registerOre("gemSilicon", ItemStacks.silicon);

		OreDictionary.registerOre("fertilizer", ItemStacks.compost);
		OreDictionary.registerOre("itemFertilizer", ItemStacks.compost);

		OreDictionary.registerOre("fuelCoke", ItemStacks.coke);
		OreDictionary.registerOre("coalCoke", ItemStacks.coke);
		OreDictionary.registerOre("coke", ItemStacks.coke);

		OreDictionary.registerOre("sourceVegetableOil", ItemRegistry.CANOLA.getStackOf());
		/*
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			String name = ReikaStringParser.stripSpaces(ore.displayName);
			ItemStack is = ExtractorModOres.getFlakeProduct(ore);
			OreDictionary.registerOre("dust"+name, is);
		}
		OreDictionary.registerOre("dustGold", ItemStacks.goldoreflakes);
		OreDictionary.registerOre("dustIron", ItemStacks.ironoreflakes);
		 */
	}

	public static void setupLiquids() {
		RotaryCraft.logger.log("Loading And Registering Liquids");

		FluidRegistry.registerFluid(RotaryCraft.ethanolFluid);
		FluidRegistry.registerFluid(RotaryCraft.jetFuelFluid);
		FluidRegistry.registerFluid(RotaryCraft.lubeFluid);
		FluidRegistry.registerFluid(RotaryCraft.nitrogenFluid);
		FluidRegistry.registerFluid(RotaryCraft.poisonFluid);
		FluidRegistry.registerFluid(RotaryCraft.hslaFluid);

		ReikaFluidHelper.registerNameSwap("lubricant", "rc lubricant");
		ReikaFluidHelper.registerNameSwap("jet fuel", "rc jet fuel");
		ReikaFluidHelper.registerNameSwap("liquid nitrogen", "rc liquid nitrogen");

		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.lubeFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(0), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.jetFuelFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(1), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.ethanolFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(2), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.nitrogenFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(3), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.hslaFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(4), new ItemStack(Items.bucket));
	}

	@SideOnly(Side.CLIENT)
	public static void setupLiquidIcons(TextureStitchEvent.Pre event) {
		RotaryCraft.logger.log("Loading Liquid Icons");

		if (event.map.getTextureType() == 0) {
			IIcon jeticon = event.map.registerIcon("RotaryCraft:jetfuel_anim");
			IIcon lubeicon = event.map.registerIcon("RotaryCraft:lubricant_anim");
			IIcon ethanolicon = event.map.registerIcon("RotaryCraft:ethanol_anim");
			IIcon nitrogenicon = event.map.registerIcon("RotaryCraft:nitrogen_anim");
			IIcon hslastill = event.map.registerIcon("RotaryCraft:hsla_still");
			IIcon hslaflow = event.map.registerIcon("RotaryCraft:hsla_flow");
			RotaryCraft.jetFuelFluid.setIcons(jeticon);
			RotaryCraft.lubeFluid.setIcons(lubeicon);
			RotaryCraft.ethanolFluid.setIcons(ethanolicon);
			RotaryCraft.nitrogenFluid.setIcons(nitrogenicon);
			RotaryCraft.hslaFluid.setIcons(hslastill, hslaflow);
		}
	}
}
