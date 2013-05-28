/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Blocks.BlockAdvGear;
import Reika.RotaryCraft.Blocks.BlockDMIMachine;
import Reika.RotaryCraft.Blocks.BlockDMMachine;
import Reika.RotaryCraft.Blocks.BlockDMachine;
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
import Reika.RotaryCraft.Blocks.BlockTrans;

public enum BlockRegistry {

	ADVANCEDGEAR(BlockAdvGear.class),
	DIR(BlockDMachine.class),
	DIRMODELINV(BlockDMIMachine.class),
	DIRMODEL(BlockDMMachine.class),
	ENGINE(BlockEngine.class),
	GPR(BlockGPR.class),
	FLYWHEEL(BlockFlywheel.class),
	GEARBOX(BlockGearbox.class),
	INV(BlockIMachine.class),
	BASIC(BlockMachine.class),
	MODELINV(BlockMIMachine.class),
	MODEL(BlockMMachine.class),
	PIPING(BlockPiping.class),
	SHAFT(BlockShaft.class),
	TRANS(BlockTrans.class);

	private Class block;
	public static final BlockRegistry[] blockList = BlockRegistry.values();
	private static final String[] blockNames = {"Advanced Gears", "D-Type Machines", "DMI-Type Machines", "DM-Type Machines", "Engines",
		"GPR", "Flywheels", "Gearboxes", "I-Type Machines", "Basic Machines", "MI-Type Machines", "M-Type Machines", "Piping", "Shaft",
		"Transmission"
	};

	private BlockRegistry(Class cl) {
		block = cl;
	}

	public static int getBlockVariableIndexFromClass(Class cl) {
		for (int i = 0; i < blockList.length; i++) {
			if (blockList[i].block == cl)
				return i;
		}
		throw new RuntimeException("Unregistered block class "+cl);
	}

	public Block getBlockVariable() {
		return mod_RotaryCraft.machineBlocks[this.ordinal()];
	}

	public Block createInstance() {
		try {
			Constructor<?> c = this.getBlockClass().getConstructor(int.class, Material.class);
			Block instance = (Block)(c.newInstance(RotaryConfig.machineids[this.ordinal()], this.getBlockMaterial()));
			return (instance.setUnlocalizedName(this.getUnlocName()));
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(this.getBlockClass().toString()+" does not have the specified constructor!");
		}
		catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(this.getBlockClass().toString()+" threw security exception!");
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(this.getBlockClass().toString()+" did not allow instantiation!");
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(this.getBlockClass().toString()+" threw illegal access exception! (Nonpublic constructor)");
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(this.getBlockClass().toString()+" was given invalid parameters!");
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(this.getBlockClass().toString()+" threw invocation target exception! Check Block ID conflicts!");
		}
	}

	public String getUnlocName() {
		return ReikaJavaLibrary.stripSpaces(this.getBlockClass().toString());
	}

	public String getBlockVariableName() {
		return blockNames[this.ordinal()];
	}

	public Material getBlockMaterial() {
		if (this.ordinal() == MachineRegistry.HOSE.getBlockVariableIndex())
			return Material.ground;
		if (this.ordinal() == MachineRegistry.PIPE.getBlockVariableIndex())
			return Material.ground;
		if (this.ordinal() == MachineRegistry.FUELLINE.getBlockVariableIndex())
			return Material.ground;
		if (this.ordinal() == MachineRegistry.SPILLER.getBlockVariableIndex())
			return Material.ground;
		return Material.iron;
	}

	public Class getBlockClass() {
		return block;
	}

	public int getBlockID() {
		return mod_RotaryCraft.machineBlocks[this.ordinal()].blockID;
	}

	public String getName() {
		return this.getBlockVariableName();
	}

}
