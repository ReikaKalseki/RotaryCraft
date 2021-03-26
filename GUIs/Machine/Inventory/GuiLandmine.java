/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerLandmine;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLandmine;

public class GuiLandmine extends GuiNonPoweredMachine {

	public GuiLandmine(EntityPlayer p5ep, TileEntityLandmine te) {
		super(new ContainerLandmine(p5ep, te), te);
		ep = p5ep;
	}

	@Override
	protected String getGuiTexture() {
		return "landminegui";
	}
}
