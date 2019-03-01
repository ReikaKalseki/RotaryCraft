/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine;

import net.minecraft.entity.player.EntityPlayer;

import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Base.TileEntity.RemoteControlMachine;
import Reika.RotaryCraft.Containers.Machine.ContainerRemoteControl;

public class GuiRemoteControl extends GuiNonPoweredMachine {

	public GuiRemoteControl(EntityPlayer p5ep, RemoteControlMachine te) {
		super(new ContainerRemoteControl(p5ep, te), te);
		ep = p5ep;
	}

	@Override
	protected String getGuiTexture() {
		return "cctvgui";
	}
}
