package Reika.RotaryCraft.ModInterface;

import net.minecraft.item.ItemStack;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.modifiers.tools.ModReinforced;
import Reika.RotaryCraft.Auxiliary.ItemStacks;


public class UnbreakabilityModifier extends ModReinforced {

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

}
