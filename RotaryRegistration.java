/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Entities.EntityCustomTNT;
import Reika.RotaryCraft.Entities.EntityFallingBlock;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Entities.EntityIceBlock;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Items.Placers.ItemBlockDeco;
import Reika.RotaryCraft.Items.Placers.ItemBlockGravLeaves;
import Reika.RotaryCraft.Items.Placers.ItemBlockGravLog;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.TileEntityCaveFinder;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
		GameRegistry.registerBlock(RotaryCraft.gravlog, ItemBlockGravLog.class, "GravLog");
		GameRegistry.registerBlock(RotaryCraft.gravleaves, ItemBlockGravLeaves.class, "GravLeaves");

		GameRegistry.registerBlock(RotaryCraft.canola, "Canola");

		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			String regname = BlockRegistry.blockList[i].getName().toLowerCase().replaceAll("\\s","");
			GameRegistry.registerBlock(BlockRegistry.blockList[i].getBlockVariable(), regname);
		}

		GameRegistry.registerTileEntity(TileEntityAerosolizer.class, "RCaero");
		GameRegistry.registerTileEntity(TileEntityAutoBreeder.class, "RCbreeder");
		GameRegistry.registerTileEntity(TileEntityBaitBox.class, "RCbait");
		GameRegistry.registerTileEntity(TileEntityCaveFinder.class, "RCcave");
	}

	public static void addTileEntities() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			String label = "RC"+MachineRegistry.machineList[i].getName().toLowerCase().replaceAll("\\s","");
			GameRegistry.registerTileEntity(MachineRegistry.machineList[i].getTEClass(), label);
		}
	}

	public static void addEntities() {
		EntityRegistry.registerModEntity(EntityFallingBlock.class, "Falling Block", EntityRegistry.findGlobalUniqueEntityId(), RotaryCraft.instance, 160, 20, true);
		EntityRegistry.registerModEntity(EntityCustomTNT.class, "Custom TNT", EntityRegistry.findGlobalUniqueEntityId()+1, RotaryCraft.instance, 160, 20, true);
		EntityRegistry.registerModEntity(EntityRailGunShot.class, "RailGun Shot", EntityRegistry.findGlobalUniqueEntityId()+2, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityFreezeGunShot.class, "Freeze Gun Shot", EntityRegistry.findGlobalUniqueEntityId()+3, RotaryCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityIceBlock.class, "Ice Block", EntityRegistry.findGlobalUniqueEntityId()+4, RotaryCraft.instance, 64, 20, true);
	}

	public static void instantiateMachines() {
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			RotaryCraft.machineBlocks[i] = BlockRegistry.blockList[i].createInstance();
			if (RotaryConfig.consoleMsg)
				ReikaJavaLibrary.pConsole("ROTARYCRAFT: Instantiating Block "+BlockRegistry.blockList[i].getName()+" with ID "+BlockRegistry.blockList[i].getBlockID()+" to Block Variable "+RotaryCraft.machineBlocks[i]+" (slot "+i+")");
		}
	}
}
