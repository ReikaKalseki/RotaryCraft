/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.common.ICraftingHandler;

public class ItemChargingRecipeHandler implements ICraftingHandler {

	private boolean checkHasTool(IInventory ic) {
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack is = ic.getStackInSlot(i);
			if (is != null) {
				if (is.getItem() instanceof ItemChargedTool || is.itemID == ItemRegistry.NVG.getID())
					return true;
			}
		}
		return false;
	}

	private int getTool(IInventory ic) {
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack is = ic.getStackInSlot(i);
			if (is != null) {
				if (is.getItem() instanceof ItemChargedTool || is.itemID == ItemRegistry.NVG.getID())
					return ic.getStackInSlot(i).itemID;
			}
		}
		return -1;
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,	IInventory ii) {
		boolean hasSpring = ReikaInventoryHelper.hasItem(ItemRegistry.SPRING.getID(), ii);
		boolean hasTool = this.checkHasTool(ii);
		boolean onlyThose = ReikaInventoryHelper.hasNEmptyStacks(ii, 7);

		if (hasSpring && hasTool && onlyThose) {
			int toolid = this.getTool(ii);
			int toolslot = ReikaInventoryHelper.locateIDInInventory(toolid, ii);
			int springslot = ReikaInventoryHelper.locateIDInInventory(ItemRegistry.SPRING.getID(), ii);
			int toolmeta = ii.getStackInSlot(toolslot).getItemDamage();
			int springmeta = ii.getStackInSlot(springslot).getItemDamage();
			//ItemStack newtool = new ItemStack(toolid, 1, springmeta);
			ItemStack newspring = new ItemStack(ItemRegistry.SPRING.getID(), 1, toolmeta);
			item.setItemDamage(springmeta);
			if (!player.inventory.addItemStackToInventory(newspring))
				ReikaItemHelper.dropItem(player.worldObj, player.posX, player.posY, player.posZ, newspring);
		}
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
