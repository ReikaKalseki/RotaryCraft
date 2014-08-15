/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiWorktable;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;

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
	private static final CrystallizerHandler crystallizer = new CrystallizerHandler();
	private static final FridgeHandler fridge = new FridgeHandler();
	private static final FillingStationHandler filling = new FillingStationHandler();
	//private static final RightClickHandler rightclick = new RightClickHandler();
	private static final FrictionHandler friction = new FrictionHandler();
	private static final ComposterHandler compost = new ComposterHandler();
	private static final LavaMakerHandler melter = new LavaMakerHandler();
	private static final CentrifugeHandler centrifuge = new CentrifugeHandler();

	private static final NEITabOccluder occlusion = new NEITabOccluder();

	@Override
	public void loadConfig() {
		RotaryCraft.logger.log("Loading NEI Compatibility!");

		API.registerNEIGuiHandler(occlusion);

		API.registerRecipeHandler(worktable);
		API.registerUsageHandler(worktable);
		API.registerGuiOverlayHandler(GuiWorktable.class, new DefaultOverlayHandler(), "crafting");

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

		API.registerRecipeHandler(crystallizer);
		API.registerUsageHandler(crystallizer);

		API.registerRecipeHandler(fridge);
		API.registerUsageHandler(fridge);

		API.registerRecipeHandler(filling);
		API.registerUsageHandler(filling);

		//API.registerRecipeHandler(rightclick);
		//API.registerUsageHandler(rightclick);

		API.registerRecipeHandler(friction);
		API.registerUsageHandler(friction);

		API.registerRecipeHandler(compost);
		API.registerUsageHandler(compost);

		API.registerRecipeHandler(melter);
		API.registerUsageHandler(melter);

		API.registerRecipeHandler(centrifuge);
		API.registerUsageHandler(centrifuge);

		RotaryCraft.logger.log("Hiding technical blocks from NEI!");
		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			if (BlockRegistry.blockList[i].isTechnical())
				this.hideBlock(BlockRegistry.blockList[i].getBlockInstance());
		}

		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry ir = ItemRegistry.itemList[i];
			if (ir.isContinuousCreativeMetadatas()) {
				int max = ir.getNumberMetadatas()-1;
				Item id = ir.getItemInstance();
				ArrayList<ItemStack> li = new ArrayList();
				for (int k = 0; k < ir.getNumberMetadatas(); k++) {
					li.add(ir.getStackOfMetadata(k));
				}
				API.setItemListEntries(id, li);
			}
		}

		ArrayList<ItemStack> li = new ArrayList();
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.isAvailableInCreativeInventory() && !m.hasCustomPlacerItem())
				li.add(m.getCraftedProduct());
		}
		API.setItemListEntries(ItemRegistry.MACHINE.getItemInstance(), li);

		if (RotaryCraft.instance.isLocked()) {
			for (int i = 0; i < ItemRegistry.itemList.length; i++) {
				ItemRegistry ir = ItemRegistry.itemList[i];
				API.hideItem(new ItemStack(ir.getItemInstance()));
			}
		}
	}

	private void hideBlock(Block b) {
		API.hideItem(new ItemStack(b));
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