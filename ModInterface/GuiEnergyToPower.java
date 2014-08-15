/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiNonPoweredMachine;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.PacketRegistry;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class GuiEnergyToPower extends GuiNonPoweredMachine {

	private EnergyToPowerBase engine;
	private static final int SHIFT = -12;
	private boolean flexible = false;

	public GuiEnergyToPower(EntityPlayer pl, EnergyToPowerBase te) {
		super(new ContainerEnergyToPower(pl, te), te);
		engine = te;
		ySize = 99;
		xSize = 207;
		ep = pl;
	}

	@Override
	public void initGui() {
		super.initGui();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		int dx = 4;
		int inset = 21+dx;
		if (flexible) {
			buttonList.add(new GuiButton(0, SHIFT+j+inset-1+dx, k+ySize-30-48+0, 20, 20, "-"));;
			buttonList.add(new GuiButton(1, SHIFT+j+xSize-20-inset-dx, k+ySize-30-48+0, 20, 20, "+"));
		}
		buttonList.add(new GuiButton(2, SHIFT+j+inset-10+dx, k+ySize-30-48+25, 20, 20, "-"));;
		buttonList.add(new GuiButton(3, SHIFT+j+xSize-20-inset-dx, k+ySize-30-48+25, 20, 20, "+"));

		buttonList.add(new GuiButton(4, SHIFT+j+xSize-20-inset-dx, k+ySize-30-48+50, 20, 20, ""));

	}

	@Override
	public void actionPerformed(GuiButton b) {
		super.actionPerformed(b);
		if (b.id == 4) {
			ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.PNEUMATIC.getMinValue()+2, engine);
		}
		else if (b.id < 24000) {
			ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.PNEUMATIC.getMinValue()+b.id-2, engine);
		}
		this.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int a, int b)
	{
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerForegroundLayer(a, b);

		int torque = engine.getGenTorque(engine.getTier());
		int omega = engine.getMaxSpeed();
		long power = (long)torque*(long)omega;
		int inset = 1;
		int w = 55;
		int h = 20;
		int dy = h+5;
		int dx = 4;

		for (int i = 0; i < 3; i++) {
			this.drawRect(SHIFT+xSize/2-w-dx, ySize-30-48+i*dy, SHIFT+xSize/2+w-dx, ySize-30-48+h+i*dy, 0xff777777);
			this.drawRect(SHIFT+xSize/2-w+inset-dx, ySize-30-48+inset+i*dy, SHIFT+xSize/2+w-inset-dx, ySize-30-48+h-inset+i*dy, 0xff000000);
		}

		this.drawCenteredString(fontRendererObj, String.format("Torque: %d Nm", torque), SHIFT+xSize/2-dx, ySize-30-48+6, 0xffffff);

		this.drawCenteredString(fontRendererObj, String.format("Speed: %d rad/s", omega), SHIFT+xSize/2-dx, ySize-30-48+6+dy, 0xffffff);
		this.drawCenteredString(fontRendererObj, String.format("Power: %.3f %sW", ReikaMathLibrary.getThousandBase(power), ReikaEngLibrary.getSIPrefix(power)), SHIFT+xSize/2-dx, ySize-30-48+6+dy*2, 0xffffff);

		if (ReikaGuiAPI.instance.isMouseInBox(j+171, j+188, k+21, k+90)) {
			int e = engine.getStoredPower();
			String sg = String.format("%d/%d %s", e, engine.getMaxStorage(), engine.getUnitDisplay());
			ReikaGuiAPI.instance.drawTooltipAt(fontRendererObj, sg, ReikaGuiAPI.instance.getMouseRealX()-j+fontRendererObj.getStringWidth(sg)+24, ReikaGuiAPI.instance.getMouseRealY()-k);
			//this.drawHoveringText(ReikaJavaLibrary.makeListFrom(sg), ReikaGuiAPI.instance.getMouseRealX()-j, ReikaGuiAPI.instance.getMouseRealY()-k, fontRendererObj);
		}

		if (ReikaGuiAPI.instance.isMouseInBox(j+192, j+200, k+21, k+90)) {
			int e = engine.getLubricant();
			String sg = String.format("%d/%d mB", e, engine.getMaxLubricant());
			ReikaGuiAPI.instance.drawTooltipAt(fontRendererObj, sg, ReikaGuiAPI.instance.getMouseRealX()-j+fontRendererObj.getStringWidth(sg)+24, ReikaGuiAPI.instance.getMouseRealY()-k);
			//this.drawHoveringText(ReikaJavaLibrary.makeListFrom(sg), ReikaGuiAPI.instance.getMouseRealX()-j, ReikaGuiAPI.instance.getMouseRealY()-k, fontRendererObj);
		}

		if (ReikaGuiAPI.instance.isMouseInBox(-12+j+xSize-20-23, -12+j+xSize-23, k+ySize-30-48+50, k+ySize-30-28+50)) {
			String sg = "Redstone Control";
			ReikaGuiAPI.instance.drawTooltipAt(fontRendererObj, sg, ReikaGuiAPI.instance.getMouseRealX()-24-fontRendererObj.getStringWidth(sg), ReikaGuiAPI.instance.getMouseRealY()-k);
			//this.drawHoveringText(ReikaJavaLibrary.makeListFrom(sg), ReikaGuiAPI.instance.getMouseRealX()-j, ReikaGuiAPI.instance.getMouseRealY()-k, fontRendererObj);
		}

		int ddy = engine.isRedstoneControlEnabled() ? 0 : 1;
		api.drawItemStack(itemRender, fontRendererObj, engine.getRedstoneStateIcon(), 148, 71+ddy);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int px = engine.getEnergyScaled(68);
		Color c = engine.getPowerColor();
		GL11.glColor3f(c.getRed()/255F, c.getGreen()/255F, c.getBlue()/255F);
		this.drawTexturedModalRect(j+172, k+90-px, 208, 69-px, 16, px);

		int px2 = engine.getLubricantScaled(68);
		GL11.glColor3f(1, 1, 1);
		this.drawTexturedModalRect(j+193, k+90-px2, 244, 69-px2, 7, px2);
	}

	@Override
	public String getGuiTexture() {
		return "pneugui";
	}

}