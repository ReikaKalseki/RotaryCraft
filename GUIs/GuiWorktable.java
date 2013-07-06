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
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Containers.ContainerWorktable;
import Reika.RotaryCraft.TileEntities.TileEntityWorktable;

public class GuiWorktable extends GuiNonPoweredMachine {

	public GuiWorktable(EntityPlayer pl, TileEntityWorktable te, World world) {
		super(new ContainerWorktable(pl, te, world), te);
		ep = pl;
	}

	@Override
	public String getGuiTexture() {
		return "worktablegui";
	}
}
