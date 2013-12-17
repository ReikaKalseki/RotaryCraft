/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import java.util.ArrayList;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEI_RotaryConfig implements IConfigureNEI {

	private static final WorktableRecipeHandler worktable = new WorktableRecipeHandler();
	private static final ToolChargingHandler toolCharge = new ToolChargingHandler();
	private static final ToolCraftingHandler toolCrafting = new ToolCraftingHandler();
	private static final BlastFurnaceHandler blastFurnace = new BlastFurnaceHandler();
	private static final GrinderHandler grinder = new GrinderHandler();
	private static final ExtractorHandler extractor = new ExtractorHandler();
	private static final FermenterHandler fermenter = new FermenterHandler();
	private static final CompactorHandler compactor = new CompactorHandler();
	private static final PulseJetHandler pulsejet = new PulseJetHandler();
	private static final FractionHandler fractionator = new FractionHandler();

	@Override
	public void loadConfig() {
		RotaryCraft.logger.log("Loading NEI Compatibility!");
		API.registerRecipeHandler(worktable);
		API.registerUsageHandler(worktable);

		API.registerRecipeHandler(toolCharge);
		API.registerUsageHandler(toolCharge);

		API.registerRecipeHandler(toolCrafting);
		API.registerUsageHandler(toolCrafting);

		API.registerRecipeHandler(blastFurnace);
		API.registerUsageHandler(blastFurnace);

		API.registerRecipeHandler(grinder);
		API.registerUsageHandler(grinder);

		API.registerRecipeHandler(extractor);
		API.registerUsageHandler(extractor);

		API.registerRecipeHandler(fermenter);
		API.registerUsageHandler(fermenter);

		API.registerRecipeHandler(compactor);
		API.registerUsageHandler(compactor);

		API.registerRecipeHandler(pulsejet);
		API.registerUsageHandler(pulsejet);

		API.registerRecipeHandler(fractionator);
		API.registerUsageHandler(fractionator);

		RotaryCraft.logger.log("Hiding technical blocks from NEI!");
		for (int i = 0; i < BlockRegistry.blockList.length; i++)
			API.hideItem(BlockRegistry.blockList[i].getBlockID());

		API.hideItem(RotaryCraft.lightblock.blockID);
		API.hideItem(RotaryCraft.lightbridge.blockID);
		API.hideItem(RotaryCraft.beamblock.blockID);
		API.hideItem(RotaryCraft.canola.blockID);
		API.hideItem(RotaryCraft.miningpipe.blockID);
		API.hideItem(RotaryCraft.waterblock.blockID);

		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry ir = ItemRegistry.itemList[i];
			if (ir.isContinuousCreativeMetadatas()) {
				int max = ir.getNumberMetadatas()-1;
				int id = ir.getShiftedID();
				ArrayList<int[]> li = new ArrayList();
				li.add(new int[]{0, max});
				API.setItemDamageVariants(id, li);
			}
		}

		ArrayList<Integer> li = new ArrayList();
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.isAvailableInCreativeInventory())
				li.add(i);
		}
		API.setItemDamageVariants(RotaryCraft.machineplacer.itemID, li);
	}

	@Override
	public String getName() {
		return "RotaryCraft NEI Handlers";
	}

	@Override
	public String getVersion() {
		return "Gamma";
	}

}
