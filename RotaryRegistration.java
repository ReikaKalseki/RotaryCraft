/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaRegistryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.ItemLiquid;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityGasMinecart;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Items.Placers.ItemBlockDeco;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.LiquidRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RotaryRegistration {

	public static  void addBlocks() {
		GameRegistry.registerBlock(RotaryCraft.bedrockslice, "BedrockSlice");

		GameRegistry.registerBlock(RotaryCraft.decoblock, ItemBlockDeco.class, "MachineBlock");
		GameRegistry.registerBlock(RotaryCraft.blastglass, "BlastGlass");
		GameRegistry.registerBlock(RotaryCraft.obsidianglass, "BlastGlassPane");

		GameRegistry.registerBlock(RotaryCraft.lightblock, "LightBlock");
		GameRegistry.registerBlock(RotaryCraft.beamblock, "BeamBlock");
		GameRegistry.registerBlock(RotaryCraft.lightbridge, "Bridge");

		GameRegistry.registerBlock(RotaryCraft.miningpipe, "MiningPipe");
		//GameRegistry.registerBlock(RotaryCraft.gravlog, ItemBlockGravLog.class, "GravLog");
		//GameRegistry.registerBlock(RotaryCraft.gravleaves, ItemBlockGravLeaves.class, "GravLeaves");

		GameRegistry.registerBlock(RotaryCraft.canola, "Canola");
		/*
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			String regname = BlockRegistry.blockList[i].getName(0).toLowerCase().replaceAll("\\s","");
			GameRegistry.registerBlock(BlockRegistry.blockList[i].getBlockVariable(), regname);
		}*/
		/*
		GameRegistry.registerTileEntity(TileEntityAerosolizer.class, "RCaero");
		GameRegistry.registerTileEntity(TileEntityAutoBreeder.class, "RCbreeder");
		GameRegistry.registerTileEntity(TileEntityBaitBox.class, "RCbait");
		GameRegistry.registerTileEntity(TileEntityCaveFinder.class, "RCcave");*/
	}

	public static void addTileEntities() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			String label = "RC"+MachineRegistry.machineList[i].getDefaultName().toLowerCase().replaceAll("\\s","");
			GameRegistry.registerTileEntity(MachineRegistry.machineList[i].getTEClass(), label);
			ReikaJavaLibrary.initClass(MachineRegistry.machineList[i].getTEClass());
		}
	}

	public static void addEntities() {
		EntityRegistry.registerModEntity(EntityRailGunShot.class, "RailGun Shot", EntityRegistry.findGlobalUniqueEntityId()+2, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityFreezeGunShot.class, "Freeze Gun Shot", EntityRegistry.findGlobalUniqueEntityId()+3, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityIceBlock.class, "Ice Block", EntityRegistry.findGlobalUniqueEntityId()+4, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityGasMinecart.class, "Gas Minecart", EntityRegistry.findGlobalUniqueEntityId()+5, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityExplosiveShell.class, "Explosive Shell", EntityRegistry.findGlobalUniqueEntityId()+6, RotaryCraft.instance, 64, 20, true);
	}

	public static void instantiateMachines() {
		ReikaRegistryHelper.instantiateAndRegisterBlocks(RotaryCraft.instance, BlockRegistry.blockList, RotaryCraft.machineBlocks, RotaryCraft.logger.shouldLog());
	}

	public static void instantiateItems() {
		ReikaRegistryHelper.instantiateAndRegisterItems(RotaryCraft.instance, ItemRegistry.itemList, RotaryCraft.basicItems, RotaryCraft.logger.shouldLog());
	}

	public static void loadOreDictionary() {
		OreDictionary.registerOre("HSLA", ItemStacks.steelingot);
		OreDictionary.registerOre("sawdust", ItemStacks.sawdust);
		OreDictionary.registerOre("ingotSilver", ItemStacks.silveringot);
		OreDictionary.registerOre("ingotAluminum", ItemStacks.aluminumingot);
		ExtractorModOres.registerRCIngots();
		ItemStacks.registerSteels();
	}

	public static void setupLiquids() {
		RotaryCraft.logger.log("Loading And Registering Liquids");
		RotaryCraft.jetFuel = new ItemLiquid(ExtraConfigIDs.JETFUEL.getValue()).setUnlocalizedName("jetfuel");
		RotaryCraft.lubricant = new ItemLiquid(ExtraConfigIDs.LUBE.getValue()).setUnlocalizedName("lubricant");
		RotaryCraft.ethanol = new ItemLiquid(ExtraConfigIDs.ETHANOL.getValue()).setUnlocalizedName("ethanol");

		LanguageRegistry.addName(RotaryCraft.jetFuel, "Jet Fuel");
		LanguageRegistry.addName(RotaryCraft.lubricant, "Lubricant");
		LanguageRegistry.addName(RotaryCraft.ethanol, "Ethanol");

		FluidRegistry.registerFluid(RotaryCraft.ethanolFluid);
		FluidRegistry.registerFluid(RotaryCraft.jetFuelFluid);
		FluidRegistry.registerFluid(RotaryCraft.lubeFluid);

		//ReikaJavaLibrary.spamConsole(new FluidStack(RotaryCraft.ethanolFluid, FluidContainerRegistry.BUCKET_VOLUME)+":"+LiquidRegistry.ETHANOL.getHeldItemFor()+":"+new ItemStack(Item.bucketEmpty));

		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.lubeFluid, FluidContainerRegistry.BUCKET_VOLUME), LiquidRegistry.LUBRICANT.getHeldItemFor(), new ItemStack(Item.bucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.jetFuelFluid, FluidContainerRegistry.BUCKET_VOLUME), LiquidRegistry.JETFUEL.getHeldItemFor(), new ItemStack(Item.bucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.ethanolFluid, FluidContainerRegistry.BUCKET_VOLUME), LiquidRegistry.ETHANOL.getHeldItemFor(), new ItemStack(Item.bucketEmpty));
	}

	@SideOnly(Side.CLIENT)
	public static void setupLiquidIcons() {
		RotaryCraft.logger.log("Loading Liquid Icons");

		RotaryCraft.jetFuelFluid.setIcons(RotaryCraft.jetFuel.getIconFromDamage(0));
		RotaryCraft.lubeFluid.setIcons(RotaryCraft.lubricant.getIconFromDamage(0));
		RotaryCraft.ethanolFluid.setIcons(RotaryCraft.ethanol.getIconFromDamage(0));
	}
}
