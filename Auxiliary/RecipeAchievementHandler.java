/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.common.ICraftingHandler;

public class RecipeAchievementHandler implements ICraftingHandler {

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,	IInventory ii) {
		this.checkAchievements(player, item);
	}



	private void checkAchievements(EntityPlayer player, ItemStack item) {
		if (ReikaItemHelper.matchStacks(item, MachineRegistry.RAILGUN.getCraftedProduct()))
			RotaryAchievements.MAKERAILGUN.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.JET.ordinal())))
			RotaryAchievements.MAKEJET.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(RotaryCraft.shaftitems.itemID, 1, MaterialRegistry.STEEL.ordinal())))
			RotaryAchievements.STEELSHAFT.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(RotaryCraft.shaftitems.itemID, 1, MaterialRegistry.BEDROCK.ordinal())))
			RotaryAchievements.BEDROCKSHAFT.triggerAchievement(player);
		if (ReikaItemHelper.matchStacks(item, new ItemStack(RotaryCraft.advgearitems.itemID, 1, 1)))
			RotaryAchievements.CVT.triggerAchievement(player);
		if (ItemRegistry.isRegistered(item) && ItemRegistry.getEntry(item).isBedrockTool())
			RotaryAchievements.BEDROCKTOOLS.triggerAchievement(player);
		for (int i = 0; i < 4; i++) {
			if (ReikaItemHelper.matchStacks(item, new ItemStack(RotaryCraft.gbxitems.itemID, 1, MaterialRegistry.DIAMOND.ordinal()+i*5)))
				RotaryAchievements.DIAMONDGEARS.triggerAchievement(player);
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {}
}
