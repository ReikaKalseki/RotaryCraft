/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerScreen;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityScreen;

public class GuiCCTVScreen extends GuiMachine {

	public GuiCCTVScreen(EntityPlayer p5ep, TileEntityScreen te) {
		super(new ContainerScreen(p5ep, te), te);
		ep = p5ep;
		ySize = 166;
		xSize = 176;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b) {
		super.drawGuiContainerForegroundLayer(a, b);

		api.drawCenteredStringNoShadow(fontRenderer, "Camera Select", xSize/2, 54, 4210752);
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		int a = 2;
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+0+a, 85, 4, 42, ySize-1);

		long frac = (recv.power*29L)/recv.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-149+a, 0, 0, (int)frac, 4);

		frac = recv.omega*29L/recv.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-90+a, 0, 0, (int)frac, 4);

		frac = recv.torque*29L/recv.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-31+a, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+4+a, 0xff000000);
		api.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+63+a, 0xff000000);
		api.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+122+a, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "screengui";
	}

}
