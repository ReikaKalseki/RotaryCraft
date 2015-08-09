/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;



public class UnbreakabilityModifier/* extends ModReinforced */{
	/*
	public UnbreakabilityModifier() {
		super(new ItemStack[] {ItemStacks.bedingot, ItemStacks.bedingot}, 16, 1); //increase is unused, so 1
	}

	public void register() {
		ModifyBuilder.registerModifier(this);
	}

	@Override
	public void modify(ItemStack[] recipe, ItemStack tool) {
		super.modify(recipe, tool);
		tool.stackTagCompound.setInteger(key, 10);
		tool.stackTagCompound.setInteger("Unbreaking", 10);
		tool.stackTagCompound.setInteger("Reinforced", 10);
	}

	@Override
	protected int addToolTip (ItemStack tool, String tooltip, String modifierTip) {
		int ret = 0;
		for (int i = 0; i < 10; i++)
			ret += super.addToolTip(tool, tooltip, modifierTip);
		return ret;
	}
	 */
}
