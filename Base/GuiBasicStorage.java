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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.ContainerBasicStorage;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Registry.PowerReceivers;

public class GuiBasicStorage extends GuiMachine {

	private int inventoryRows = 0;
	private IInventory upperInventory;

	public GuiBasicStorage(EntityPlayer p5ep, RotaryCraftTileEntity te) {
		super(new ContainerBasicStorage(p5ep, te), te);
		ep = p5ep;
		upperInventory = ep.inventory;
		allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		inventoryRows = ((IInventory)te).getSizeInventory() / 9;
		ySize = var4 + inventoryRows * 18;
	}

	@Override
	protected final void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		String var4 = this.getGuiTexture();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		int var5 = (width - xSize) / 2;
		int var6 = (height - ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, xSize, inventoryRows * 18 + 17);
		this.drawTexturedModalRect(var5, var6 + inventoryRows * 18 + 17, 0, 126, xSize, 96);

		if (PowerReceivers.getEnumFromMachineIndex(tile.getMachineIndex()).isMinPowerOnly())
			this.drawPowerOnly(var5, var6);
		else
			this.drawPowerTab(var5, var6);
	}

	protected final void drawPowerOnly(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, 24);
		this.drawTexturedModalRect(xSize+var5, var6+4+23, 0, 157, 42, 6);

		long frac = recv.getScaledPower(29);
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6-53-(ySize-75), 0, 0, (int)frac, 4);

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", spawnercontroller.power, spawnercontroller.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		int spacing = 60;
		int dy = -92;
		int u = 0;
		if (inventoryRows < 3) {
			dy -= 2;
			spacing -= -5+10*(3-inventoryRows);
			u = 169;
			if (inventoryRows == 1)
				u += 42;
		}
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, u, 4, 42, 145);
		this.drawTexturedModalRect(xSize+var5, var6+4+145, u, 157, 42, 6);
		//this.drawTexturedModalRect(this.xSize+var5, var6+156, 0, this.ySize-12, 42, 8);
		long frac = recv.getScaledPower(29);
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6+dy-inventoryRows*18, 0, 0, (int)frac, 4);
		dy += spacing;
		frac = recv.getScaledOmega(29);
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6+dy-inventoryRows*18, 0, 0, (int)frac, 4);
		dy += spacing;
		frac = recv.getScaledTorque(29);
		this.drawTexturedModalRect(xSize+var5+5, ySize+var6+dy-inventoryRows*18, 0, 0, (int)frac, 4);
		dy = 9;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+dy, 0xff000000);
		dy += spacing;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+dy, 0xff000000);
		dy += spacing;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+dy, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", tile.power, tile.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/basicstorage.png";
	}

}
