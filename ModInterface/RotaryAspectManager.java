/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaThaumHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import thaumcraft.api.aspects.Aspect;


public class RotaryAspectManager {

	public static void addThaumAspects() {
		RotaryCraft.logger.log("Adding ThaumCraft aspects.");
		ReikaThaumHelper.addAspects(ItemStacks.canolaSeeds, Aspect.EXCHANGE, 2, Aspect.CROP, 1, Aspect.MECHANISM, 1);
		ReikaThaumHelper.addAspects(ItemStacks.denseCanolaSeeds, Aspect.EXCHANGE, 16, Aspect.CROP, 8, Aspect.MECHANISM, 8);
		ReikaThaumHelper.addAspects(ItemStacks.canolaHusks, Aspect.EXCHANGE, 2, Aspect.CROP, 1, Aspect.MECHANISM, 1);

		ReikaThaumHelper.addAspects(ItemRegistry.YEAST.getStackOf(), Aspect.EXCHANGE, 4);

		ReikaThaumHelper.clearAspects(ItemRegistry.HANDBOOK.getStackOf());

		ReikaThaumHelper.addAspects(ItemRegistry.BEDAXE.getStackOf(), Aspect.TOOL, 96);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDPICK.getStackOf(), Aspect.TOOL, 96);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDPICK.getStackOf(), Aspect.MINE, 48);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDHOE.getStackOf(), Aspect.TOOL, 80);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDHOE.getStackOf(), Aspect.HARVEST, 20);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSWORD.getStackOf(), Aspect.TOOL, 80);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSHEARS.getStackOf(), Aspect.TOOL, 80);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSHOVEL.getStackOf(), Aspect.TOOL, 72);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSICKLE.getStackOf(), Aspect.TOOL, 96);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDSICKLE.getStackOf(), Aspect.HARVEST, 20);
		if (ModList.MULTIPART.isLoaded())
			ReikaThaumHelper.addAspects(ItemRegistry.BEDSAW.getStackOf(), Aspect.TOOL, 80);
		if (ModList.FORESTRY.isLoaded())
			ReikaThaumHelper.addAspects(ItemRegistry.BEDGRAFTER.getStackOf(), Aspect.TOOL, 72);

		ReikaThaumHelper.addAspects(ItemRegistry.STEELAXE.getStackOf(), Aspect.TOOL, 18);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELPICK.getStackOf(), Aspect.TOOL, 18);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELPICK.getStackOf(), Aspect.MINE, 9);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELHOE.getStackOf(), Aspect.TOOL, 16);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELHOE.getStackOf(), Aspect.HARVEST, 4);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELSWORD.getStackOf(), Aspect.TOOL, 14);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELSHEARS.getStackOf(), Aspect.TOOL, 14);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELSHOVEL.getStackOf(), Aspect.TOOL, 12);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELSICKLE.getStackOf(), Aspect.TOOL, 18);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELSICKLE.getStackOf(), Aspect.HARVEST, 4);

		ReikaThaumHelper.addAspects(ItemRegistry.BEDLEGS.getStackOf(), Aspect.ARMOR, 140);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDHELM.getStackOf(), Aspect.ARMOR, 100);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDBOOTS.getStackOf(), Aspect.ARMOR, 80);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDCHEST.getStackOf(), Aspect.ARMOR, 160);
		ReikaThaumHelper.addAspects(ItemRegistry.BEDREVEAL.getStackOf(), Aspect.ARMOR, 140, Aspect.SENSES, 20, Aspect.AURA, 20, Aspect.MAGIC, 20);

		ReikaThaumHelper.addAspects(ItemRegistry.BEDPACK.getStackOf(), Aspect.ARMOR, 160, Aspect.FLIGHT, 40);
		ReikaThaumHelper.addAspects(ItemRegistry.STEELPACK.getStackOf(), Aspect.ARMOR, 40, Aspect.FLIGHT, 40);
		ReikaThaumHelper.addAspects(ItemRegistry.JETPACK.getStackOf(), Aspect.TOOL, 40, Aspect.FLIGHT, 40);

		ReikaThaumHelper.addAspects(ItemRegistry.BEDJUMP.getStackOf(), Aspect.ARMOR, 120, Aspect.TRAVEL, 20);
		ReikaThaumHelper.addAspects(ItemRegistry.JUMP.getStackOf(), Aspect.TOOL, 20, Aspect.TRAVEL, 20);

		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(0), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.MOTION, 2, Aspect.MECHANISM, 2);
		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(1), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.FIRE, 3, Aspect.ENERGY, 12);
		ReikaThaumHelper.addAspects(ItemRegistry.BUCKET.getStackOfMetadata(2), Aspect.VOID, 1, Aspect.METAL, 13, Aspect.ENERGY, 7, Aspect.PLANT, 3);

		ReikaThaumHelper.addAspects(ItemRegistry.SHELL.getStackOf(), Aspect.FIRE, 8, Aspect.WEAPON, 8);

		ReikaThaumHelper.addAspects(ItemStacks.steelingot, Aspect.METAL, 10, Aspect.MECHANISM, 6);
		ReikaThaumHelper.addAspects(ItemStacks.netherrackdust, Aspect.FIRE, 4);
		ReikaThaumHelper.addAspects(ItemStacks.sludge, Aspect.ENERGY, 1);
		ReikaThaumHelper.addAspects(ItemStacks.sawdust, Aspect.TREE, 1);
		ReikaThaumHelper.addAspects(ItemStacks.anthracite, Aspect.FIRE, 4, Aspect.ENERGY, 4);
		ReikaThaumHelper.addAspects(ItemStacks.coke, Aspect.FIRE, 2, Aspect.MECHANISM, 2);

		ReikaThaumHelper.addAspects(ItemRegistry.HANDBOOK.getStackOf(), Aspect.MIND, 4, Aspect.MECHANISM, 3, Aspect.TREE, 1);

		ReikaThaumHelper.addAspects(BlockRegistry.BLASTGLASS.getStackOf(), Aspect.CRYSTAL, 4, Aspect.ARMOR, 8, Aspect.FIRE, 2);
		ReikaThaumHelper.addAspects(BlockRegistry.BLASTPANE.getStackOf(), Aspect.CRYSTAL, 2, Aspect.ARMOR, 4, Aspect.FIRE, 1);

		MachineAspectMapper.instance.register();
	}

}
