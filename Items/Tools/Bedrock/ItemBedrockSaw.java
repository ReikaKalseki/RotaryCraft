package Reika.RotaryCraft.Items.Tools.Bedrock;

import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import codechicken.microblock.Saw;

public class ItemBedrockSaw extends ItemRotaryTool implements Saw {

	public ItemBedrockSaw(int ID, int index) {
		super(ID, index);
		this.setContainerItem(this);
	}

	@Override
	public int getMaxCuttingStrength() {
		return 5;
	}

	@Override
	public int getCuttingStrength(ItemStack stack) {
		return this.getMaxCuttingStrength();
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack is)
	{
		return false;
	}

}
