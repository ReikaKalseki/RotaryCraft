package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.item.ItemStack;

public interface UpgradeableMachine {

	public void upgrade();
	public boolean canUpgradeWith(ItemStack item);

}
