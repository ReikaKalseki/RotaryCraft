/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class GuiEnergyToPower extends GuiNonPoweredMachine {

	private EnergyToPowerBase engine;
	private int base;
	private static final int SHIFT = -12;

	public GuiEnergyToPower(EntityPlayer pl, EnergyToPowerBase te) {
		super(new ContainerEnergyToPower(pl, te), te);
		engine = te;
		ySize = 99;
		xSize = 199;
		ep = pl;
		base = te.getTier();
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int inset = 23;
		buttonList.add(new GuiButton(0, SHIFT+j+inset, k+ySize-30-48+0, 20, 20, "-"));;
		buttonList.add(new GuiButton(1, SHIFT+j+xSize-20-inset, k+ySize-30-48+0, 20, 20, "+"));

		buttonList.add(new GuiButton(2, SHIFT+j+inset, k+ySize-30-48+25, 20, 20, "-"));;
		buttonList.add(new GuiButton(3, SHIFT+j+xSize-20-inset, k+ySize-30-48+25, 20, 20, "+"));
	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		if (b.id < 24000) {
			ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.PNEUMATIC.getMinValue()+b.id, engine);
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerForegroundLayer(a, b);

		int torque = engine.getTorque();
		int omega = engine.getSpeed();
		long power = engine.getPowerLevel();
		int inset = 1;
		int w = 50;
		int h = 20;
		int dy = h+5;


		for (int i = 0; i < 3; i++) {
			this.drawRect(SHIFT+xSize/2-w, ySize-30-48+i*dy, SHIFT+xSize/2+w, ySize-30-48+h+i*dy, 0xff777777);
			this.drawRect(SHIFT+xSize/2-w+inset, ySize-30-48+inset+i*dy, SHIFT+xSize/2+w-inset, ySize-30-48+h-inset+i*dy, 0xff000000);
		}

		this.drawCenteredString(fontRenderer, String.format("Torque: %d Nm", torque), SHIFT+xSize/2, ySize-30-48+6, 0xffffff);

		this.drawCenteredString(fontRenderer, String.format("Speed: %d rad/s", omega), SHIFT+xSize/2, ySize-30-48+6+dy, 0xffffff);
		this.drawCenteredString(fontRenderer, String.format("Power: %.3f %sW", ReikaMathLibrary.getThousandBase(power), ReikaEngLibrary.getSIPrefix(power)), SHIFT+xSize/2, ySize-30-48+6+dy*2, 0xffffff);

		if (ReikaGuiAPI.instance.isMouseInBox(j+171, j+188, k+21, k+90)) {
			int e = engine.getStoredPower();
			String sg = String.format("%d/%d %s", e, engine.getMaxStorage(), engine.getUnitDisplay());
			ReikaGuiAPI.instance.drawTooltipAt(fontRenderer, sg, ReikaGuiAPI.instance.getMouseRealX()-j+fontRenderer.getStringWidth(sg)+24, ReikaGuiAPI.instance.getMouseRealY()-k);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int px = engine.getEnergyScaled(68);
		Color c = engine.getPowerColor();
		GL11.glColor3f(c.getRed()/255F, c.getGreen()/255F, c.getBlue()/255F);
		this.drawTexturedModalRect(j+172, k+90-px, 200, 69-px, 16, px);
	}

	@Override
	public String getGuiTexture() {
		return "pneugui";
	}

}
