/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools.Steel;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.ItemSickleBase;

public class ItemSteelSickle extends ItemSickleBase {

	public ItemSteelSickle(int index) {
		super(index);
	}

	@Override
	public int getLeafRange() {
		return 4;
	}

	@Override
	public int getCropRange() {
		return 4;
	}

	@Override
	public int getPlantRange() {
		return 4;
	}

	@Override
	public boolean canActAsShears() {
		return false;
	}

	@Override
	public boolean isBreakable() {
		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack item) {
		return tool.getItem() == this && ReikaItemHelper.matchStacks(item, ItemStacks.steelingot);
	}

}
