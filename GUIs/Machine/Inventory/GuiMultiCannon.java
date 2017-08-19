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
import net.minecraft.inventory.Slot;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.Machine.Inventory.ContainerMultiCannon;
import Reika.RotaryCraft.TileEntities.Weaponry.Turret.TileEntityMultiCannon;


public class GuiMultiCannon extends GuiMachine {

	private final TileEntityMultiCannon tile;

	public GuiMultiCannon(EntityPlayer ep, TileEntityMultiCannon te) {
		super(new ContainerMultiCannon(ep, te), te);
		this.ep = ep;
		tile = te;
		ySize = 204;
	}

	@Override
	protected String getGuiTexture() {
		return "gatlinggui";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		if (tile.isReloading()) {
			int j = (width - xSize) / 2;
			int k = (height - ySize) / 2;

			String i = "/Reika/RotaryCraft/Textures/GUI/"+this.getGuiTexture()+".png";
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ReikaTextureHelper.bindTexture(RotaryCraft.class, i);


			this.drawTexturedModalRect(j+12, k+31, 178, 2, 16, 5);
		}
	}

	@Override
	protected void drawPowerTab(int j, int k) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+j, k+4, 0, 4, 42, ySize-4-40);

		long frac = (tile.power*29L)/tile.MINPOWER;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+j+5, ySize+k-182, 0, 0, (int)frac, 4);

		frac = (int)(tile.omega*29L)/tile.MINSPEED;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+j+5, ySize+k-122, 0, 0, (int)frac, 4);

		frac = (int)(tile.torque*29L)/tile.MINTORQUE;
		if (frac > 29)
			frac = 29;
		this.drawTexturedModalRect(xSize+j+5, ySize+k-62, 0, 0, (int)frac, 4);

		api.drawCenteredStringNoShadow(fontRendererObj, "Power:", xSize+j+20, k+9, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Speed:", xSize+j+20, k+69, 0xff000000);
		api.drawCenteredStringNoShadow(fontRendererObj, "Torque:", xSize+j+20, k+129, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRendererObj, String.format("%d/%d", tile.power, tile.MINPOWER), xSize+j+16, k+16, 0xff000000);
	}

	@Override
	public boolean isMouseOverSlot(Slot p_146981_1_, int p_146981_2_, int p_146981_3_)
	{
		return this.func_146978_c(p_146981_1_.xDisplayPosition, p_146981_1_.yDisplayPosition, 15, 15, p_146981_2_, p_146981_3_); //reduced slot size
	}

}
