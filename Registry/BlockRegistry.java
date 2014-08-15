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

import Reika.DragonAPI.Base.BlockTEBase;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Instantiable.Data.PluralMap;
import Reika.DragonAPI.Interfaces.BlockEnum;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Base.BlockBasicMachine;
import Reika.RotaryCraft.Blocks.BlockAdvGear;
import Reika.RotaryCraft.Blocks.BlockBeam;
import Reika.RotaryCraft.Blocks.BlockBedrockSlice;
import Reika.RotaryCraft.Blocks.BlockBlastGlass;
import Reika.RotaryCraft.Blocks.BlockBlastPane;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Blocks.BlockDMIMachine;
import Reika.RotaryCraft.Blocks.BlockDMMachine;
import Reika.RotaryCraft.Blocks.BlockDMachine;
import Reika.RotaryCraft.Blocks.BlockDeco;
import Reika.RotaryCraft.Blocks.BlockDecoTank;
import Reika.RotaryCraft.Blocks.BlockEngine;
import Reika.RotaryCraft.Blocks.BlockFlywheel;
import Reika.RotaryCraft.Blocks.BlockGPR;
import Reika.RotaryCraft.Blocks.BlockGearbox;
import Reika.RotaryCraft.Blocks.BlockIMachine;
import Reika.RotaryCraft.Blocks.BlockLightBridge;
import Reika.RotaryCraft.Blocks.BlockLightblock;
import Reika.RotaryCraft.Blocks.BlockMIMachine;
import Reika.RotaryCraft.Blocks.BlockMMachine;
import Reika.RotaryCraft.Blocks.BlockMachine;
import Reika.RotaryCraft.Blocks.BlockMiningPipe;
import Reika.RotaryCraft.Blocks.BlockModEngine;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.Blocks.BlockShaft;
import Reika.RotaryCraft.Blocks.BlockSolar;
import Reika.RotaryCraft.Blocks.BlockTrans;
import Reika.RotaryCraft.Items.ItemBlockDeco;
import Reika.RotaryCraft.Items.ItemBlockDecoTank;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum BlockRegistry implements BlockEnum {

	ADVANCEDGEAR(BlockAdvGear.class, 0),
	DIR(BlockDMachine.class, 0),
	DIRMODELINV(BlockDMIMachine.class, 0),
	DIRMODEL(BlockDMMachine.class, 0),
	ENGINE(BlockEngine.class, 0),
	GPR(BlockGPR.class, 0),
	FLYWHEEL(BlockFlywheel.class, 0),
	GEARBOX(BlockGearbox.class, 0),
	INV(BlockIMachine.class, 0),
	BASIC(BlockMachine.class, 0),
	MODELINV(BlockMIMachine.class, 0),
	MODEL(BlockMMachine.class, 0),
	PIPING(BlockPiping.class, 0),
	SHAFT(BlockShaft.class, 0),
	TRANS(BlockTrans.class, 0),
	MODELINV2(BlockMIMachine.class, 1),
	SOLAR(BlockSolar.class, 0),
	BCENGINE(BlockModEngine.class, 0),
	MODEL2(BlockMMachine.class, 1),
	DIRMODEL2(BlockDMMachine.class, 1),
	DECO(BlockDeco.class, 					"block.deco",			ItemBlockDeco.class),
	CANOLA(BlockCanola.class, 				"block.canola"),
	LIGHT(BlockLightblock.class, 			"block.light"),
	BEAM(BlockBeam.class, 					"block.beam"),
	BRIDGE(BlockLightBridge.class, 			"block.bridge"),
	MININGPIPE(BlockMiningPipe.class, 		"block.miningpipe"),
	BLASTGLASS(BlockBlastGlass.class, 		"block.blastglass"),
	BLASTPANE(BlockBlastPane.class, 		"block.blastpane"),
	BEDROCKSLICE(BlockBedrockSlice.class, 	"block.bedrockslice"),
	DECOTANK(BlockDecoTank.class, 			"block.decotank",		ItemBlockDecoTank.class);

	private final Class block;
	private final Class itemBlock;
	private final int offset;
	private final String name;

	public static final BlockRegistry[] blockList = values();

	private static final HashMap<Block, BlockRegistry> IDMap = new HashMap();
	private static final PluralMap<BlockRegistry> classMap = new PluralMap(2);

	private BlockRegistry(Class cl, String name) {
		this(cl, null, -1, name);
	}

	private BlockRegistry(Class cl, String name, Class <? extends ItemBlock> item) {
		this(cl, item, -1, name);
	}

	private BlockRegistry(Class cl, int offset) {
		this(cl, null, offset, "");
	}

	private BlockRegistry (Class cl, Class<? extends ItemBlock> item, int offset) {
		this(cl, item, offset, "");
	}

	private BlockRegistry(Class cl, int offset, String name) {
		this(cl, null, offset, name);
	}

	private BlockRegistry (Class cl, Class<? extends ItemBlock> item, int offset, String name) {
		block = cl;
		itemBlock = item;
		this.offset = offset;
		this.name = name;
	}

	public static BlockRegistry getBlock(Block b) {
		return IDMap.get(b);
	}

	public static boolean isMachineBlock(Block id) {
		BlockRegistry br = getBlock(id);
		return br != null && br.isMachine();
	}

	public static boolean isTechnicalBlock(Block id) {
		BlockRegistry br = getBlock(id);
		return br != null && br.isTechnical();
	}

	static BlockRegistry getBlockFromClassAndOffset(Class<? extends Block> c, int i) {
		return classMap.get(c, i);
	}

	public Class getObjectClass() {
		return block;
	}

	public Block getBlockInstance() {
		return RotaryCraft.blocks[this.ordinal()];
	}

	public String getUnlocalizedName() {
		return name;
	}

	private String getLocalizedName() {
		return StatCollector.translateToLocal(this.getUnlocalizedName());
	}

	public String getBasicName() {
		return this.isMachine() ? "(TECHNICAL BLOCK) "+block.getSimpleName()+":"+this.ordinal() : this.getLocalizedName();
	}

	public boolean isFundamentalType() {
		return BlockBasicMachine.class.isAssignableFrom(block);
	}

	public boolean isTechnical() {
		switch(this) {
		case DECO:
		case BLASTGLASS:
		case BLASTPANE:
		case DECOTANK:
			return false;
		default:
			return true;
		}
	}

	public boolean isMachine() {
		return BlockTEBase.class.isAssignableFrom(block);
	}

	@Override
	public Class[] getConstructorParamTypes() {
		return this.isMachine() ? new Class[]{Material.class} : new Class[0];
	}

	@Override
	public Object[] getConstructorParams() {
		return this.isMachine() ? new Object[]{Material.iron} : new Object[0];
	}

	@Override
	public String getMultiValuedName(int meta) {
		if (!this.hasMultiValuedName())
			return this.getBasicName();
		switch(this) {
		case DECO:
			return StatCollector.translateToLocal(RotaryNames.blockNames[meta]);
		case DECOTANK:
			return StatCollector.translateToLocal("block.decotank."+meta);
		default:
			throw new RegistrationException(RotaryCraft.instance, "No multiname for "+this+"!");
		}
	}

	@Override
	public boolean hasMultiValuedName() {
		return this.getNumberMetadatas() > 1;
	}

	@Override
	public int getNumberMetadatas() {
		switch(this) {
		case DECO:
			return RotaryNames.blockNames.length;
			//case MININGPIPE:
			//	return 4;
		case DECOTANK:
			return 2;
		default:
			return 1;
		}
	}

	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		return itemBlock;
	}

	@Override
	public boolean hasItemBlock() {
		return itemBlock != null;
	}

	public boolean isDummiedOut() {
		return block == null;
	}

	public Item getItem() {
		return Item.getItemFromBlock(this.getBlockInstance());
	}

	public ItemStack getStackOf() {
		return this.getStackOfMetadata(0);
	}

	public ItemStack getStackOfMetadata(int meta) {
		return new ItemStack(this.getBlockInstance(), 1, meta);
	}

	public ItemStack getCraftedProduct(int amt) {
		return this.getCraftedMetadataProduct(amt, 0);
	}

	public ItemStack getCraftedMetadataProduct(int amt, int meta) {
		return new ItemStack(this.getBlockInstance(), amt, meta);
	}

	public static void loadMappings() {
		for (int i = 0; i < blockList.length; i++) {
			BlockRegistry block = blockList[i];
			IDMap.put(block.getBlockInstance(), block);
			classMap.put(block, block.block, block.offset);
		}
	}
}