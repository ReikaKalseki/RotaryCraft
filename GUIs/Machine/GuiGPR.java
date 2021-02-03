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

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;

public class GuiGPR extends GuiPowerOnlyMachine
{

	private TileEntityGPR gpr;

	public static final int UNIT = 2;

	private boolean pressL;
	private boolean pressR;
	private boolean pressC;

	public GuiGPR(EntityPlayer p5ep, TileEntityGPR GPR) {
		super(new CoreContainer(p5ep, GPR), GPR);
		gpr = GPR;
		ySize = 215;
		ep = p5ep;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		boolean keyL = Keyboard.isKeyDown(Keyboard.KEY_LBRACKET);
		boolean keyR = Keyboard.isKeyDown(Keyboard.KEY_RBRACKET);
		boolean keyC = Keyboard.isKeyDown(Keyboard.KEY_BACKSLASH);

		if (keyL && !pressL) {
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.GPR.ordinal(), gpr, 1);
			gpr.shift(gpr.getGuiDirection(), 1);
			pressL = true;
		}
		else if (keyL && pressL) {
			pressL = false;
		}

		if (keyR && !pressR) {
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.GPR.ordinal(), gpr, -1);
			gpr.shift(gpr.getGuiDirection(), -1);
			pressR = true;
		}
		else if (keyR && pressR) {
			pressR = false;
		}

		if (keyC && !pressC) {
			ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.GPR.ordinal(), gpr, 0);
			gpr.shift(gpr.getGuiDirection(), 0);
			pressC = true;
		}
		else if (keyC && pressC) {
			pressC = false;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2+1;

		this.drawRadar(j, k);
	}

	private void drawRadar(int a, int b) {
		int r = gpr.getRange();
		for (int x = -r; x <= r; x++) {
			int[] yy = gpr.getVerticalInterval();
			for (int dy = yy[0]; dy > yy[1]; dy--) {
				int dd = gpr.yCoord-dy;
				int color = 0xff000000 | gpr.getColor(x, dd);
				int x0 = a+7+UNIT*(x+gpr.MAX_WIDTH/2);
				int y0 = b+16+UNIT*dd-2;
				this.drawRect(x0, y0, x0+UNIT, y0+UNIT, color);
			}
		}

		GL11.glPushMatrix();
		double d = 0.5;
		GL11.glScaled(d, d, d);
		String s = gpr.getLookDirection().toString();
		int w = fontRendererObj.getStringWidth(s);
		fontRendererObj.drawStringWithShadow(s, (int)((a+w/2)/d), (int)((b+16)/d), 0xffffff);
		GL11.glPopMatrix();
	}

	@Override
	protected String getGuiTexture() {
		return "gprgui";
	}
}
