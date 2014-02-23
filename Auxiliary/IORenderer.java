/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public abstract class IORenderer {
	private static double par2;
	private static double par4;
	private static double par6;
	private static final double expand = 0.5;
	private static final ForgeDirection[] dirs = ForgeDirection.values();

	public static void renderOut(double x, double y, double z, int a) {
		int[] color = {255, 0, 0, a};
		if (ConfigRegistry.COLORBLIND.getState())
			color[0] = 0;
		renderBox(x, y, z, color);
	}

	public static void renderIn(double x, double y, double z, int a) {
		int[] color = {0, 255, 0, a};
		if (ConfigRegistry.COLORBLIND.getState()) {
			color[0] = 255;
			color[2] = 255;
		}
		renderBox(x, y, z, color);
	}

	public static void renderIO(TileEntity teb, double p2, double p4, double p6) {
		par2 = p2;
		par4 = p4;
		par6 = p6;
		ItemStack is = Minecraft.getMinecraft().thePlayer.getCurrentArmor(3);
		boolean flag = ReikaItemHelper.matchStacks(is, ItemRegistry.IOGOGGLES.getStackOf());
		if (teb instanceof TileEntityIOMachine) {
			TileEntityIOMachine te = (TileEntityIOMachine)teb;
			if (flag)
				te.iotick = 512;
			if (te.iotick <= 0)
				return;
			if (teb instanceof TileEntitySplitter) {
				TileEntitySplitter ts = (TileEntitySplitter)teb;
				if (ts.getBlockMetadata() < 8) { //Merging
					double xdiff = ts.writex-ts.xCoord;
					double zdiff = ts.writez-ts.zCoord;
					renderOut(par2+xdiff, par4, par6+zdiff, ts.iotick);

					xdiff = ts.readx-ts.xCoord;
					zdiff = ts.readz-ts.zCoord;
					renderIn(par2+xdiff, par4, par6+zdiff, ts.iotick);

					xdiff = ts.readx2-ts.xCoord;
					zdiff = ts.readz2-ts.zCoord;
					renderIn(par2+xdiff, par4, par6+zdiff, ts.iotick);
				}
				else { //Splitting
					double xdiff = ts.writeinline[0]-ts.xCoord;
					double zdiff = ts.writeinline[1]-ts.zCoord;
					renderOut(par2+xdiff, par4, par6+zdiff, ts.iotick);

					xdiff = ts.writebend[0]-ts.xCoord;
					zdiff = ts.writebend[1]-ts.zCoord;
					renderOut(par2+xdiff, par4, par6+zdiff, ts.iotick);

					xdiff = ts.readx-ts.xCoord;
					zdiff = ts.readz-ts.zCoord;
					renderIn(par2+xdiff, par4, par6+zdiff, ts.iotick);
				}
				return;
			}
			if (teb instanceof TileEntityShaft && teb.getBlockMetadata() >= 6) { //cross
				TileEntityShaft ts = (TileEntityShaft)teb;
				double xdiff = ts.writex-ts.xCoord;
				double zdiff = ts.writez-ts.zCoord;
				renderOut(par2+xdiff, par4, par6+zdiff, ts.iotick);

				xdiff = ts.writex2-ts.xCoord;
				zdiff = ts.writez2-ts.zCoord;
				renderOut(par2+xdiff, par4, par6+zdiff, ts.iotick);

				xdiff = ts.readx-ts.xCoord;
				zdiff = ts.readz-ts.zCoord;
				renderIn(par2+xdiff, par4, par6+zdiff, ts.iotick);

				xdiff = ts.readx2-ts.xCoord;
				zdiff = ts.readz2-ts.zCoord;
				renderIn(par2+xdiff, par4, par6+zdiff, ts.iotick);
				return;
			}
			if (teb instanceof TileEntityWinder) {
				TileEntityWinder ts = (TileEntityWinder)teb;
				if (!ts.winding) {
					double xdiff = ts.readx-ts.xCoord;
					double ydiff = ts.ready-ts.yCoord;
					double zdiff = ts.readz-ts.zCoord;
					renderOut(par2+xdiff, par4+ydiff, par6+zdiff, ts.iotick);
				}
				else {
					double xdiff = ts.readx-ts.xCoord;
					double zdiff = ts.readz-ts.zCoord;
					renderIn(par2+xdiff, par4, par6+zdiff, ts.iotick);
				}
				return;
			}
			if (te.isOmniSided) {
				if (te.getMachine().getMaxY(te) == 1)
					renderIn(par2, par4+1, par6, te.iotick);
				if (te.getMachine().getMinY(te) == 0)
					renderIn(par2, par4-1, par6, te.iotick);
				if (te.getMachine().getMaxX(te) == 1)
					renderIn(par2+1, par4, par6, te.iotick);
				if (te.getMachine().getMinX(te) == 0)
					renderIn(par2-1, par4, par6, te.iotick);
				if (te.getMachine().getMaxZ(te) == 1)
					renderIn(par2, par4, par6+1, te.iotick);
				if (te.getMachine().getMinZ(te) == 0)
					renderIn(par2, par4, par6-1, te.iotick);
				return;
			}
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d %d %d", te.writex, te.writey, te.writez));
			if (te.writex != Integer.MIN_VALUE && te.writey != Integer.MIN_VALUE && te.writez != Integer.MIN_VALUE) {
				double xdiff = te.writex-te.xCoord;
				double ydiff = te.writey-te.yCoord;
				double zdiff = te.writez-te.zCoord;
				renderOut(par2+xdiff, par4+ydiff, par6+zdiff, te.iotick);
			}
			if (te.writex2 != Integer.MIN_VALUE && te.writey2 != Integer.MIN_VALUE && te.writez2 != Integer.MIN_VALUE) {
				double xdiff = te.writex2-te.xCoord;
				double ydiff = te.writey2-te.yCoord;
				double zdiff = te.writez2-te.zCoord;
				renderOut(par2+xdiff, par4+ydiff, par6+zdiff, te.iotick);
			}
			//ReikaChatHelper.writeInt(te.ready);
			if (te.readx != Integer.MIN_VALUE && te.ready != Integer.MIN_VALUE && te.readz != Integer.MIN_VALUE) {
				double xdiff = te.readx-te.xCoord;
				double ydiff = te.ready-te.yCoord;
				double zdiff = te.readz-te.zCoord;
				renderIn(par2+xdiff, par4+ydiff, par6+zdiff, te.iotick);
			}
			if (te.readx2 != Integer.MIN_VALUE && te.ready2 != Integer.MIN_VALUE && te.readz2 != Integer.MIN_VALUE) {
				double xdiff = te.readx2-te.xCoord;
				double ydiff = te.ready2-te.yCoord;
				double zdiff = te.readz2-te.zCoord;
				renderIn(par2+xdiff, par4+ydiff, par6+zdiff, te.iotick);
			}
			if (te.readx3 != Integer.MIN_VALUE && te.ready3 != Integer.MIN_VALUE && te.readz3 != Integer.MIN_VALUE) {
				double xdiff = te.readx3-te.xCoord;
				double ydiff = te.ready3-te.yCoord;
				double zdiff = te.readz3-te.zCoord;
				renderIn(par2+xdiff, par4+ydiff, par6+zdiff, te.iotick);
			}
			if (te.readx4 != Integer.MIN_VALUE && te.ready4 != Integer.MIN_VALUE && te.readz4 != Integer.MIN_VALUE) {
				double xdiff = te.readx4-te.xCoord;
				double ydiff = te.ready4-te.yCoord;
				double zdiff = te.readz4-te.zCoord;
				renderIn(par2+xdiff, par4+ydiff, par6+zdiff, te.iotick);
			}
		}
		else {
			if (teb instanceof ShaftPowerReceiver) {
				ShaftPowerReceiver sr = (ShaftPowerReceiver)teb;
				int io = sr.getIORenderAlpha();
				if (flag)
					io = 255;
				if (io <= 0)
					return;
				for (int i = 0; i < 6; i++) {
					ForgeDirection dir = dirs[i];
					int dx = dir.offsetX+sr.getMachineX();
					int dy = dir.offsetY+sr.getMachineY();
					int dz = dir.offsetZ+sr.getMachineZ();
					if (sr.canReadFromBlock(dx, dy, dz)) {
						renderIn(par2+dir.offsetX, par4+dir.offsetY, par6+dir.offsetZ, io);
					}
				}
			}
			if (teb instanceof ShaftPowerEmitter) {
				ShaftPowerEmitter se = (ShaftPowerEmitter)teb;
				int io = se.getIORenderAlpha();
				if (flag)
					io = 255;
				if (io <= 0)
					return;
				for (int i = 0; i < 6; i++) {
					ForgeDirection dir = dirs[i];
					int dx = dir.offsetX+se.getMachineX();
					int dy = dir.offsetY+se.getMachineY();
					int dz = dir.offsetZ+se.getMachineZ();
					if (se.canWriteToBlock(dx, dy, dz)) {
						renderOut(par2+dir.offsetX, par4+dir.offsetY, par6+dir.offsetZ, io);
					}
				}
			}
		}
	}

	private static void renderBox(double x, double y, double z, int[] color) {
		ReikaRenderHelper.prepareGeoDraw(true);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if (color[3] > 255)
			color[3] = 255;
		boolean filled = true;

		Tessellator var5 = Tessellator.instance;
		var5.startDrawing(GL11.GL_LINE_LOOP);
		var5.setColorRGBA(color[0], color[1], color[2], color[3]);
		var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
		var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
		var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
		var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
		var5.draw();
		var5.startDrawing(GL11.GL_LINE_LOOP);
		var5.setColorRGBA(color[0], color[1], color[2], color[3]);
		var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand);
		var5.addVertex(x-0.0625*expand, y-0.0625*expand, z-0.0625*expand);
		var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
		var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
		var5.draw();
		var5.startDrawing(GL11.GL_LINE_LOOP);
		var5.setColorRGBA(color[0], color[1], color[2], color[3]);
		var5.addVertex(x-0.0625*expand, y-0.0625*expand, z-0.0625*expand);
		var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
		var5.draw();
		var5.startDrawing(GL11.GL_LINE_LOOP);
		var5.setColorRGBA(color[0], color[1], color[2], color[3]);
		var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand);
		var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
		var5.draw();
		var5.startDrawing(GL11.GL_LINE_LOOP);
		var5.setColorRGBA(color[0], color[1], color[2], color[3]);
		var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
		var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
		var5.draw();
		var5.startDrawing(GL11.GL_LINE_LOOP);
		var5.setColorRGBA(color[0], color[1], color[2], color[3]);
		var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
		var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
		var5.draw();

		if (filled)
		{
			var5.startDrawing(GL11.GL_QUADS);
			//var5.setBrightness(255);
			var5.setColorRGBA(color[0], color[1], color[2], (int)(color[3]*0.375));
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);

			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);

			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);

			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);

			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z-0.0625*expand);

			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.draw();
		}
		else {
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+0.5);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+0.5);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+0.5);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+0.5);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+0.25);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+0.25);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+0.25);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+0.25);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+1+0.0625*expand, y+1+0.0625*expand, z+0.75);
			var5.addVertex(x-0.0625*expand, y+1+0.0625*expand, z+0.75);
			var5.addVertex(x-0.0625*expand, y-0.0625*expand, z+0.75);
			var5.addVertex(x+1+0.0625*expand, y-0.0625*expand, z+0.75);
			var5.draw();

			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+0.5, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+0.5, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+0.5, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+0.5, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+0.25, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+0.25, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+0.25, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+0.25, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+0.75, y+1+0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+0.75, y-0.0625*expand, z-0.0625*expand);
			var5.addVertex(x+0.75, y-0.0625*expand, z+1+0.0625*expand);
			var5.addVertex(x+0.75, y+1+0.0625*expand, z+1+0.0625*expand);
			var5.draw();

			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+1+0.0625*expand, y+0.5, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+0.5, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+0.5, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+0.5, z+1+0.0625*expand);
			var5.draw();

			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+1+0.0625*expand, y+0.25, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+0.25, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+0.25, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+0.25, z+1+0.0625*expand);
			var5.draw();

			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(x+1+0.0625*expand, y+0.75, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+0.75, z-0.0625*expand);
			var5.addVertex(x-0.0625*expand, y+0.75, z+1+0.0625*expand);
			var5.addVertex(x+1+0.0625*expand, y+0.75, z+1+0.0625*expand);
			var5.draw();
		}

		//GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		ReikaRenderHelper.exitGeoDraw();
	}
}
