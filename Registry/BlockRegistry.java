/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import Reika.DragonAPI.Interfaces.RegistryEnum;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
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
import Reika.RotaryCraft.Blocks.BlockModEngine;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.Blocks.BlockShaft;
import Reika.RotaryCraft.Blocks.BlockSolar;
import Reika.RotaryCraft.Blocks.BlockTrans;

public enum BlockRegistry implements RegistryEnum {

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
	BCENGINE(BlockModEngine.class, Material.iron),
	MODEL2(BlockMMachine.class, Material.iron),
	DIRMODEL2(BlockDMMachine.class, Material.iron);

	private Class block;
	private Material mat;
	public final int indexOffset;
	public static final BlockRegistry[] blockList = values();

	private static final HashMap<Integer, BlockRegistry> IDMap = new HashMap();
	private static final HashMap<Class, ArrayList<BlockRegistry>> classMap = new HashMap();

	private static final String[] blockNames = {"Advanced Gears", "D-Type Machines", "DMI-Type Machines", "DM-Type Machines", "Engines",
		"GPR", "Flywheels", "Gearboxes", "I-Type Machines", "Basic Machines", "MI-Type Machines", "M-Type Machines", "Piping", "Shaft",
		"Transmission", "MI-Machines 2", "Solar Receiver", "Mod Interface", "M-Machines 2", "DM-Type Machines 2"
	};

	private BlockRegistry(Class cl, Material m) {
		block = cl;
		mat = m;
		indexOffset = this.getOffsetFromName();
	}

	private int getOffsetFromName() {
		String s = this.name().toLowerCase();
		String last = s.substring(s.length()-1);
		int off = ReikaJavaLibrary.safeIntParse(last);
		return off > 0 ? off-1 : 0;
	}

	public boolean isNthBlock(int n) {
		return indexOffset == n-1;
	}

	public static boolean isMachineBlock(int id) {
		return getMachineBlock(id) != null;
	}

	public static BlockRegistry getMachineBlock(int id) {
		BlockRegistry block = IDMap.get(id);
		if (block == null) {
			for (int i = 0; i < blockList.length; i++) {
				if (blockList[i].getBlockID() == id) {
					IDMap.put(id, blockList[i]);
					return blockList[i];
				}
			}
		}
		else
			return block;
		return null;
	}

	public static int getBlockVariableIndexFromClassAndMetadata(Class cl, int metadata) {
		ArrayList<BlockRegistry> blocks = classMap.get(cl);
		int offset = 1+metadata/16;
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).isNthBlock(offset))
				return blocks.get(i).ordinal();
		}
		throw new RuntimeException("Unregistered block class "+cl+" with metadata "+metadata);
	}

	public static int getOffsetFromBlockID(int id) {
		BlockRegistry b = getMachineBlock(id);
		if (b != null)
			return b.indexOffset;
		//throw new RuntimeException("Unregistered block ID "+id);
		RotaryCraft.logger.logError("Unregistered block ID "+id);
		Thread.dumpStack();
		return 0;
	}

	public Block getBlockVariable() {
		return RotaryCraft.machineBlocks[this.ordinal()];
	}

	public String getUnlocalizedName() {
		return ReikaStringParser.stripSpaces(this.getObjectClass().toString());
	}

	public String getBlockVariableName() {
		return blockNames[this.ordinal()];
	}

	public Material getBlockMaterial() {
		return mat;
	}

	public Class getObjectClass() {
		return block;
	}

	public int getBlockID() {
		return RotaryCraft.config.getBlockID(this.ordinal());
	}

	public String getBasicName() {
		return "[TECHNICAL BLOCK] "+this.getBlockVariableName();
	}

	@Override
	public Class[] getConstructorParamTypes() {
		return new Class[]{int.class, Material.class};
	}

	@Override
	public Object[] getConstructorParams() {
		return new Object[]{this.getBlockID(), this.getBlockMaterial()};
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

	public int getID() {
		return this.getBlockID();
	}

	@Override
	public boolean overwritingItem() {
		return false;
	}

	static {
		for (int i = 0; i < blockList.length; i++) {
			BlockRegistry block = blockList[i];
			Class c = block.block;
			ArrayList<BlockRegistry> li = classMap.get(c);
			if (li == null) {
				li = new ArrayList();
				classMap.put(c, li);
			}
			if (!li.contains(block));
			li.add(block);
		}
	}
}
