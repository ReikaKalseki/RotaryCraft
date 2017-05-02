/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerProjector;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityProjector;

public class GuiProjector extends GuiPowerOnlyMachine {

	private TileEntityProjector proj;

	public GuiProjector(EntityPlayer p5ep, TileEntityProjector te) {
		super(new ContainerProjector(p5ep, te), te);
		ySize = 222;
		proj = te;
		ep = p5ep;
	}

	@Override
	protected String getGuiTexture() {
		return "projectorgui";
	}

}
