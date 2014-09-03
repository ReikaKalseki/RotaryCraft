/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.inventory.Container;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

public abstract class GuiNonPoweredMachine extends GuiMachine {

	public GuiNonPoweredMachine(Container par1Container, RotaryCraftTileEntity te) {
		super(par1Container, te);
	}

	@Override
	protected final void drawPowerTab(int j, int k) {}
}
