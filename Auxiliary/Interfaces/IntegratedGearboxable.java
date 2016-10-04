/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;


public interface IntegratedGearboxable extends BreakAction {

	public boolean applyIntegratedGear(ItemStack is);

}
