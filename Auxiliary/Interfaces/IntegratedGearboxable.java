package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;


public interface IntegratedGearboxable extends BreakAction {

	public boolean applyIntegratedGear(ItemStack is);

}
