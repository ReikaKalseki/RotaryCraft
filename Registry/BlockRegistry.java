/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.DragonAPI.Interfaces.RegistrationList;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Blocks.BlockAdvGear;
import Reika.RotaryCraft.Blocks.BlockBCEngine;
import Reika.RotaryCraft.Blocks.BlockDMIMachine;
import Reika.RotaryCraft.Blocks.BlockDMMachine;
import Reika.RotaryCraft.Blocks.BlockDMachine;
import Reika.RotaryCraft.Blocks.BlockDeadMachine;
import Reika.RotaryCraft.Blocks.BlockEngine;
import Reika.RotaryCraft.Blocks.BlockFlywheel;
import Reika.RotaryCraft.Blocks.BlockGPR;
import Reika.RotaryCraft.Blocks.BlockGearbox;
import Reika.RotaryCraft.Blocks.BlockIMachine;
import Reika.RotaryCraft.Blocks.BlockMIMachine;
import Reika.RotaryCraft.Blocks.BlockMMachine;
import Reika.RotaryCraft.Blocks.BlockMachine;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.Blocks.BlockShaft;
import Reika.RotaryCraft.Blocks.BlockSolar;
import Reika.RotaryCraft.Blocks.BlockTrans;

public enum BlockRegistry implements RegistrationList, IDRegistry {

	ADVANCEDGEAR(BlockAdvGear.class, Material.iron),
	DIR(BlockDMachine.class, Material.iron),
	DIRMODELINV(BlockDMIMachine.class, Material.iron),
	DIRMODEL(BlockDMMachine.class, Material.iron),
	ENGINE(BlockEngine.class, Material.iron),
	GPR(BlockGPR.class, Material.iron),
	FLYWHEEL(BlockFlywheel.class, Material.iron),
	GEARBOX(BlockGearbox.class, Material.iron),
	INV(BlockIMachine.class, Material.iron),
	BASIC(BlockMachine.class, Material.iron),
	MODELINV(BlockMIMachine.class, Material.iron),
	MODEL(BlockMMachine.class, Material.iron),
	PIPING(BlockPiping.class, Material.iron),
	SHAFT(BlockShaft.class, Material.iron),
	TRANS(BlockTrans.class, Material.iron),
	MODELINV2(BlockMIMachine.class, Material.iron),
	SOLAR(BlockSolar.class, Material.iron),
	BCENGINE(BlockBCEngine.class, Material.iron),
	DEADMACHINE(BlockDeadMachine.class, Material.wood);

	private Class block;
	private Material mat;
	public static final BlockRegistry[] blockList = BlockRegistry.values();
	private static final String[] blockNames = {"Advanced Gears", "D-Type Machines", "DMI-Type Machines", "DM-Type Machines", "Engines",
		"GPR", "Flywheels", "Gearboxes", "I-Type Machines", "Basic Machines", "MI-Type Machines", "M-Type Machines", "Piping", "Shaft",
		"Transmission", "MI-Machines 2", "Solar Receiver", "BuildCraft Interface", "Disabled Machine"
	};

	private BlockRegistry(Class cl, Material m) {
		block = cl;
		mat = m;
	}

	public boolean isNthBlock(int n) {
		return this.getOffset() == n-1;
	}

	public static boolean isMachineBlock(int id) {
		return getMachineBlock(id) != null;
	}

	public static BlockRegistry getMachineBlock(int id) {
		for (int i = 0; i < blockList.length; i++) {
			if (blockList[i].getBlockID() == id)
				return blockList[i];
		}
		return null;
	}

	public int getOffset() {
		String name = this.getBlockVariableName();
		String num = name.substring(name.length()-1);
		if (!ReikaJavaLibrary.isValidInteger(num))
			return 0;
		return (Integer.parseInt(num)-1);
	}

	public static int getBlockVariableIndexFromClassAndMetadata(Class cl, int metadata) {
		int offset = 1+metadata/16;
		for (int i = 0; i < blockList.length; i++) {
			if (blockList[i].block == cl && blockList[i].isNthBlock(offset))
				return i;
		}
		throw new RuntimeException("Unregistered block class "+cl);
	}

	public static int getOffsetFromBlockID(int id) {
		for (int i = 0; i < blockList.length; i++) {
			if (blockList[i].getBlockID() == id)
				return blockList[i].getOffset();
		}
		throw new RuntimeException("Unregistered block ID "+id);
	}

	public Block getBlockVariable() {
		return RotaryCraft.machineBlocks[this.ordinal()];
	}

	public String getUnlocalizedName() {
		return ReikaJavaLibrary.stripSpaces(this.getObjectClass().toString());
	}

	public String getBlockVariableName() {
		return blockNames[this.ordinal()];
	}

	public Material getBlockMaterial() {/*
		if (this.ordinal() == MachineRegistry.HOSE.getBlockVariableIndex())
			return Material.;
		if (this.ordinal() == MachineRegistry.PIPE.getBlockVariableIndex())
			return Material.ground;
		if (this.ordinal() == MachineRegistry.FUELLINE.getBlockVariableIndex())
			return Material.ground;
		if (this.ordinal() == MachineRegistry.SPILLER.getBlockVariableIndex())
			return Material.ground;*/
		return mat;
	}

	public Class getObjectClass() {
		return block;
	}

	public int getBlockID() {
		return RotaryCraft.config.getBlockID(this.ordinal());
	}

	public String getBasicName() {
		return "TECHNICAL BLOCK "+this.getBlockVariableName();
	}

	public Class[] getConstructorParamTypes() {
		return new Class[]{int.class, Material.class};
	}

	public Object[] getConstructorParams() {
		return new Object[]{RotaryCraft.config.getBlockID(this.ordinal()), this.getBlockMaterial()};
	}

	@Override
	public String getMultiValuedName(int meta) {
		return null;
	}

	@Override
	public boolean hasMultiValuedName() {
		return false;
	}

	@Override
	public int getNumberMetadatas() {
		return 1;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		return null;
	}

	@Override
	public boolean hasItemBlock() {
		return false;
	}

	@Override
	public String getConfigName() {
		return this.getBlockVariableName();
	}

	@Override
	public int getDefaultID() {
		return 490+this.ordinal();
	}

	@Override
	public boolean isBlock() {
		return true;
	}

	@Override
	public boolean isItem() {
		return false;
	}

	@Override
	public String getCategory() {
		return "Machine Blocks";
	}

	public boolean isDummiedOut() {
		return block == null;
	}
}
