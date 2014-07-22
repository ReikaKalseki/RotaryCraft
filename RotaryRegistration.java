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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Libraries.ReikaRegistryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Entities.EntityCustomTNT;
import Reika.RotaryCraft.Entities.EntityDischarge;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityFlakShot;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityGasMinecart;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityLiquidBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Entities.EntitySonicShot;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;
import Reika.RotaryCraft.Items.Placers.ItemBlockDeco;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityDecoTank;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RotaryRegistration {

	public static  void addBlocks() {
		GameRegistry.registerBlock(RotaryCraft.bedrockslice, "BedrockSlice");

		GameRegistry.registerBlock(RotaryCraft.decoblock, ItemBlockDeco.class, "MachineBlock");
		GameRegistry.registerBlock(RotaryCraft.blastpane, "BlastGlass");
		GameRegistry.registerBlock(RotaryCraft.blastglass, "BlastGlassPane");

		GameRegistry.registerBlock(RotaryCraft.lightblock, "LightBlock");
		GameRegistry.registerBlock(RotaryCraft.beamblock, "BeamBlock");
		GameRegistry.registerBlock(RotaryCraft.lightbridge, "Bridge");

		GameRegistry.registerBlock(RotaryCraft.miningpipe, "MiningPipe");

		GameRegistry.registerBlock(RotaryCraft.canola, "Canola");
		GameRegistry.registerBlock(RotaryCraft.decoTank, ItemBlockDecoTank.class, "DecoTank");
	}

	public static void addTileEntities() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			String label = "RC"+MachineRegistry.machineList[i].getDefaultName().toLowerCase().replaceAll("\\s","");
			GameRegistry.registerTileEntity(MachineRegistry.machineList[i].getTEClass(), label);
			ReikaJavaLibrary.initClass(MachineRegistry.machineList[i].getTEClass());
		}
		for (int i = 0; i < EngineType.engineList.length; i++) {
			String label = "RC"+EngineType.engineList[i].name().toLowerCase().replaceAll("\\s","");
			GameRegistry.registerTileEntity(EngineType.engineList[i].engineClass, label);
			ReikaJavaLibrary.initClass(EngineType.engineList[i].engineClass);
		}
		GameRegistry.registerTileEntity(TileEntityDecoTank.class, "RCDecoTank");
	}

	public static void addEntities() {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(EntityRailGunShot.class, "RailGun Shot", id, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerGlobalEntityID(EntityRailGunShot.class, "RailGun Shot", id);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityFreezeGunShot.class, "Freeze Gun Shot", id);
		EntityRegistry.registerModEntity(EntityFreezeGunShot.class, "Freeze Gun Shot", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityIceBlock.class, "Ice Block", id);
		EntityRegistry.registerModEntity(EntityIceBlock.class, "Ice Block", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityGasMinecart.class, "Gas Minecart", id);
		EntityRegistry.registerModEntity(EntityGasMinecart.class, "Gas Minecart", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityExplosiveShell.class, "Explosive Shell", id);
		EntityRegistry.registerModEntity(EntityExplosiveShell.class, "Explosive Shell", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntitySonicShot.class, "Shock Wave", id);
		EntityRegistry.registerModEntity(EntitySonicShot.class, "Shock Wave", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLiquidBlock.class, "Liquid Block", id);
		EntityRegistry.registerModEntity(EntityLiquidBlock.class, "Liquid Block", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityDischarge.class, "Discharge", id);
		EntityRegistry.registerModEntity(EntityDischarge.class, "Discharge", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityCustomTNT.class, "CustomTNT", id);
		EntityRegistry.registerModEntity(EntityCustomTNT.class, "CustomTNT", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		//id = EntityRegistry.findGlobalUniqueEntityId();
		//EntityRegistry.registerGlobalEntityID(EntityFlamethrowerFire.class, "CustomTNT", id);
		//EntityRegistry.registerModEntity(EntityFlamethrower.class, "CustomTNT", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);

		id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityFlakShot.class, "Flak Shot", id);
		EntityRegistry.registerModEntity(EntityFlakShot.class, "Flak Shot", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 64, 20, true);
	}

	public static void instantiateMachines() {
		ReikaRegistryHelper.instantiateAndRegisterBlocks(RotaryCraft.instance, BlockRegistry.blockList, RotaryCraft.machineBlocks);
	}

	public static void instantiateItems() {
		ReikaRegistryHelper.instantiateAndRegisterItems(RotaryCraft.instance, ItemRegistry.itemList, RotaryCraft.basicItems);
	}

	public static void loadOreDictionary() {
		if (!ModList.GREGTECH.isLoaded()) {//GT unificator causes an exploit, and no mods even use this anyways
			OreDictionary.registerOre("ingotHSLA", ItemStacks.steelingot);

			OreDictionary.registerOre("dustNetherrack", ItemStacks.netherrackdust);
			OreDictionary.registerOre("dustSoulSand", ItemStacks.tar);
		}
		if (ConfigRegistry.HSLADICT.getState())
			OreDictionary.registerOre("ingotSteel", ItemStacks.steelingot);
		OreDictionary.registerOre("ingotSilver", ItemStacks.silveringot);
		OreDictionary.registerOre("ingotAluminum", ItemStacks.aluminumingot);

		OreDictionary.registerOre("RotaryCraft:dustBedrock", ItemStacks.bedrockdust);
		OreDictionary.registerOre("RotaryCraft:ingotBedrock", ItemStacks.bedingot);

		OreDictionary.registerOre("glassHardened", RotaryCraft.blastglass);

		ExtractorModOres.registerRCIngots();
		ItemStacks.registerSteels();

		OreDictionary.registerOre("dustCoal", ItemStacks.coaldust);
		OreDictionary.registerOre("dustSalt", ItemStacks.salt);
		OreDictionary.registerOre("foodSalt", ItemStacks.salt);

		OreDictionary.registerOre("dustWood", ItemStacks.sawdust);
		OreDictionary.registerOre("pulpWood", ItemStacks.sawdust);
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

		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.lubeFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(0), new ItemStack(Item.bucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.jetFuelFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(1), new ItemStack(Item.bucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.ethanolFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(2), new ItemStack(Item.bucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RotaryCraft.nitrogenFluid, FluidContainerRegistry.BUCKET_VOLUME), ItemRegistry.BUCKET.getStackOfMetadata(3), new ItemStack(Item.bucketEmpty));
	}

	@SideOnly(Side.CLIENT)
	public static void setupLiquidIcons(TextureStitchEvent.Pre event) {
		RotaryCraft.logger.log("Loading Liquid Icons");

		if (event.map.textureType == 0) {
			Icon jeticon = event.map.registerIcon("RotaryCraft:jetfuel_anim");
			Icon lubeicon = event.map.registerIcon("RotaryCraft:lubricant_anim");
			Icon ethanolicon = event.map.registerIcon("RotaryCraft:ethanol_anim");
			Icon nitrogenicon = event.map.registerIcon("RotaryCraft:nitrogen_anim");
			RotaryCraft.jetFuelFluid.setIcons(jeticon);
			RotaryCraft.lubeFluid.setIcons(lubeicon);
			RotaryCraft.ethanolFluid.setIcons(ethanolicon);
			RotaryCraft.nitrogenFluid.setIcons(nitrogenicon);
		}
	}
}
