/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.entity.player.EntityPlayer;

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.TileEntities.TileEntityWorktable;

public class GuiWorktable extends GuiNonPoweredMachine {

	public GuiWorktable(EntityPlayer pl, TileEntityWorktable te) {
		super(new ContainerWorktable(pl, te.worldObj, te), te);
		ep = pl;
	}

	@Override
	public String getGuiTexture() {
		return null;
	}
}
