/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerCCTV;
import Reika.RotaryCraft.TileEntities.TileEntityCCTV;

public class GuiCCTV extends GuiNonPoweredMachine {

	public GuiCCTV(EntityPlayer p5ep, TileEntityCCTV te) {
		super(new ContainerCCTV(p5ep, te), te);
	}

	@Override
	public String getGuiTexture() {
		return "cctvgui";
	}
}
