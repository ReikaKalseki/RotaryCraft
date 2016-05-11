/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

public class GuiOneSlotInv extends GuiMachine {

	protected IInventory iinv;
	public GuiOneSlotInv(EntityPlayer pl, Container par1Container, RotaryCraftTileEntity te) {
		super(par1Container, te);
		iinv = (IInventory)te;
		xSize = 176;
		ySize = 166;
		ep = pl;
	}

	@Override
	protected final void drawPowerTab(int j, int k) {
		if (recv != null) {
			String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
			this.drawTexturedModalRect(xSize+j, k+4, 0, 4, 42, ySize-4);

			long frac = (recv.power*29L)/recv.MINPOWER;
			if (frac > 29)
				frac = 29;
			this.drawTexturedModalRect(xSize+j+5, ySize+k-144, 0, 0, (int)frac, 4);

			frac = (int)(recv.omega*29L)/recv.MINSPEED;
			if (frac > 29)
				frac = 29;
			this.drawTexturedModalRect(xSize+j+5, ySize+k-84, 0, 0, (int)frac, 4);

			frac = (int)(recv.torque*29L)/recv.MINTORQUE;
			if (frac > 29)
				frac = 29;
			this.drawTexturedModalRect(xSize+j+5, ySize+k-24, 0, 0, (int)frac, 4);

			api.drawCenteredStringNoShadow(fontRendererObj, "Power:", xSize+j+20, k+9, 0xff000000);
			api.drawCenteredStringNoShadow(fontRendererObj, "Speed:", xSize+j+20, k+69, 0xff000000);
			api.drawCenteredStringNoShadow(fontRendererObj, "Torque:", xSize+j+20, k+129, 0xff000000);
			//this.drawCenteredStringNoShadow(fontRendererObj, String.format("%d/%d", recv.power, recv.MINPOWER), xSize+j+16, k+16, 0xff000000);

		}
	}

	@Override
	protected String getGuiTexture() {
		return "basic_gui_oneslot";
	}

}
