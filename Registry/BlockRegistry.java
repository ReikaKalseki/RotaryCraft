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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import Reika.DragonAPI.Exception.IDConflictException;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
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
import Reika.RotaryCraft.Blocks.BlockSolar;
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
	TRANS(BlockTrans.class),
	MODELINV2(BlockMIMachine.class),
	SOLAR(BlockSolar.class);

	private Class block;
	public static final BlockRegistry[] blockList = BlockRegistry.values();
	private static final String[] blockNames = {"Advanced Gears", "D-Type Machines", "DMI-Type Machines", "DM-Type Machines", "Engines",
		"GPR", "Flywheels", "Gearboxes", "I-Type Machines", "Basic Machines", "MI-Type Machines", "M-Type Machines", "Piping", "Shaft",
		"Transmission", "MI-Machines 2", "Solar Receiver"
	};

	private BlockRegistry(Class cl) {
		block = cl;
	}

	public boolean isNthBlock(int n) {
		return this.getOffset() == n-1;
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

	public Block createInstance() {
		try {
			Constructor c = this.getBlockClass().getConstructor(int.class, Material.class);
			Block instance = (Block)(c.newInstance(RotaryConfig.machineids[this.ordinal()], this.getBlockMaterial()));
			return (instance.setUnlocalizedName(this.getUnlocName()));
		}
		catch (NoSuchMethodException e) {
			throw new RegistrationException(RotaryCraft.instance, this.getBlockClass().getSimpleName()+" does not have the specified constructor! Check visibility!");
		}
		catch (SecurityException e) {
			throw new RegistrationException(RotaryCraft.instance, this.getBlockClass().getSimpleName()+" threw security exception!");
		}
		catch (InstantiationException e) {
			throw new RegistrationException(RotaryCraft.instance, this.getBlockClass().getSimpleName()+" did not allow instantiation!");
		}
		catch (IllegalAccessException e) {
			throw new RegistrationException(RotaryCraft.instance, this.getBlockClass().getSimpleName()+" threw illegal access exception! (Nonpublic constructor)");
		}
		catch (IllegalArgumentException e) {
			throw new RegistrationException(RotaryCraft.instance, this.getBlockClass().getSimpleName()+" was given invalid parameters!");
		}
		catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			if (t instanceof IllegalArgumentException)
				throw new IDConflictException(RotaryCraft.instance, t.getMessage());
			else
				throw new RegistrationException(RotaryCraft.instance, this.getBlockClass().getSimpleName()+" threw invocation target exception!");
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
		return RotaryCraft.machineBlocks[this.ordinal()].blockID;
	}

	public String getName() {
		return this.getBlockVariableName();
	}

}
