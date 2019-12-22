/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Auxiliary.Trackers.SpecialDayTracker;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Base.TileEntityRenderBase;
import Reika.DragonAPI.Interfaces.TextureFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.OldTextureLoader;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.ModInterface.Conversion.TileEntityGenerator;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RotaryTERenderer extends TileEntityRenderBase implements TextureFetcher {

	@Override
	public final String getTextureFolder() {
		return "/Reika/RotaryCraft/Textures/TileEntityTex/";
	}

	@Override
	protected Class getModClass() {
		return RotaryCraft.class;
	}

	@Override
	protected boolean loadXmasTextures() {
		return SpecialDayTracker.instance.loadXmasTextures();
	}

	protected void renderFaceColors(TileEntityIOMachine te, double p2, double p4, double p6) {
		double offset = 0.0625;
		int alpha = te.iotick;
		Color[] colors = RotaryAux.sideColors;
		ReikaRenderHelper.prepareGeoDraw(true);
		BlendMode.DEFAULT.apply();
		Tessellator v5 = Tessellator.instance;
		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[1].getRed(), colors[1].getGreen(), colors[1].getBlue(), alpha);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[1].getRed(), colors[1].getGreen(), colors[1].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[2].getRed(), colors[2].getGreen(), colors[2].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[2].getRed(), colors[2].getGreen(), colors[2].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[3].getRed(), colors[3].getGreen(), colors[3].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[3].getRed(), colors[3].getGreen(), colors[3].getBlue(), (int)(alpha/2.4));
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), alpha);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), alpha/3);
		v5.addVertex(p2-offset, p4-offset, p6-offset);
		v5.addVertex(p2-offset, p4-offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2-offset, p4+1+offset, p6-offset);
		v5.draw();

		v5.startDrawing(GL11.GL_LINE_LOOP);
		v5.setColorRGBA(colors[5].getRed(), colors[5].getGreen(), colors[5].getBlue(), alpha);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.draw();
		v5.startDrawing(GL11.GL_QUADS);
		v5.setColorRGBA(colors[5].getRed(), colors[5].getGreen(), colors[5].getBlue(), alpha/3);
		v5.addVertex(p2+1+offset, p4+1+offset, p6-offset);
		v5.addVertex(p2+1+offset, p4+1+offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6+1+offset);
		v5.addVertex(p2+1+offset, p4-offset, p6-offset);
		v5.draw();

		for (int i = 0; i < 6; i++) {
			int a = 0; int b = 0; int c = 0;
			switch(i) {
				case 0:
					b = -3;
					break;
				case 1:
					b = 3;
					break;
				case 2:
					c = -3;
					break;
				case 3:
					c = 3;
					break;
				case 4:
					a = -3;
					break;
				case 5:
					a = 3;
					break;
			}
			v5.startDrawing(GL11.GL_LINES);
			v5.setColorRGBA(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), alpha);
			v5.addVertex(p2+0.5, p4+0.5, p6+0.5);
			v5.addVertex(p2+0.5+a, p4+0.5+b, p6+0.5+c);
			v5.draw();
		}

		ReikaRenderHelper.exitGeoDraw();
	}

	protected void setupGL(RotaryCraftTileEntity tile, double par2, double par4, double par6) {
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslated(par2, par4, par6);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslated(0, -2, -1);

		if (tile.isInWorld() && tile.isFlipped && MinecraftForgeClient.getRenderPass() == 0) {
			GL11.glRotated(180, 1, 0, 0);
			GL11.glTranslated(0, -2, 0);
			MachineRegistry m = tile.getMachine();
			boolean rot = false;
			if (m.isEnergyToPower()) {
				rot = ((EnergyToPowerBase)tile).getFacing().offsetZ != 0;
			}
			else if (tile instanceof TileEntityGenerator) {
				rot = ((TileEntityGenerator)tile).getFacing().offsetZ != 0;
			}
			else {
				rot = tile.getBlockMetadata() > 1;
			}
			if (rot)
				GL11.glRotated(180, 0, 1, 0);
		}
	}

	protected void closeGL(RotaryCraftTileEntity tile) {
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	protected final DragonAPIMod getOwnerMod() {
		return RotaryCraft.instance;
	}

	@Override
	protected final boolean doRenderModel(TileEntityBase te) {
		if (OldTextureLoader.instance.loadOldTextures()) {/*
			RotaryCraftTileEntity tile = (RotaryCraftTileEntity)te;
			MachineRegistry m = tile.getMachine();
			Tessellator v5 = Tessellator.instance;
			ReikaTextureHelper.bindFinalTexture(RotaryCraft.class, "Textures/terrain/original.png");
			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslated(te.xCoord-TileEntityRendererDispatcher.staticPlayerX, te.yCoord-TileEntityRendererDispatcher.staticPlayerY, te.zCoord-TileEntityRendererDispatcher.staticPlayerZ);
			v5.startDrawingQuads();
			v5.setBrightness(tile.getBlockType().getMixedBrightnessForBlock(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord));
			v5.setColorOpaque_I(0xffffff);
			int n = RotaryAux.func_39467_b_(m.getBlock(), m.getMachineMetadata(), 0, null);
			double u = (n%16)/16D;
			double v = (n/16)/16D;
			double du = u+0.0625;
			double dv = v+0.0625;
			double ox = m.getMinX(tile);
			double oy = m.getMinY(tile);
			double oz = m.getMinZ(tile);
			double w = m.getMaxX(tile);
			double h = m.getMaxY(tile);
			double l = m.getMaxZ(tile);

			v5.setColorOpaque_I(0x777777);

			v5.addVertexWithUV(ox, oy, oz, u, v);
			v5.addVertexWithUV(w, oy, oz, du, v);
			v5.addVertexWithUV(w, oy, l, du, dv);
			v5.addVertexWithUV(ox, oy, l, u, dv);

			n = RotaryAux.func_39467_b_(m.getBlock(), m.getMachineMetadata(), 1, null);
			u = (n%16)/16D;
			v = (n/16)/16D;
			du = u+0.0625;
			dv = v+0.0625;

			v5.setColorOpaque_I(0xffffff);

			v5.addVertexWithUV(ox, h, l, u, dv);
			v5.addVertexWithUV(w, h, l, du, dv);
			v5.addVertexWithUV(w, h, oz, du, v);
			v5.addVertexWithUV(ox, h, oz, u, v);

			n = RotaryAux.func_39467_b_(m.getBlock(), m.getMachineMetadata(), 2, null);
			u = (n%16)/16D;
			v = (n/16)/16D;
			du = u+0.0625;
			dv = v+0.0625;

			v5.setColorOpaque_I(0xcccccc);

			v5.addVertexWithUV(ox, h, oz, du, v);
			v5.addVertexWithUV(w, h, oz, u, v);
			v5.addVertexWithUV(w, oy, oz, u, dv);
			v5.addVertexWithUV(ox, oy, oz, du, dv);

			n = RotaryAux.func_39467_b_(m.getBlock(), m.getMachineMetadata(), 3, null);
			u = (n%16)/16D;
			v = (n/16)/16D;
			du = u+0.0625;
			dv = v+0.0625;

			v5.addVertexWithUV(ox, oy, l, u, dv);
			v5.addVertexWithUV(w, oy, l, du, dv);
			v5.addVertexWithUV(w, h, l, du, v);
			v5.addVertexWithUV(ox, h, l, u, v);

			n = RotaryAux.func_39467_b_(m.getBlock(), m.getMachineMetadata(), 4, null);
			u = (n%16)/16D;
			v = (n/16)/16D;
			du = u+0.0625;
			dv = v+0.0625;

			v5.setColorOpaque_I(0xa2a2a2);

			v5.addVertexWithUV(w, h, oz, du, v);
			v5.addVertexWithUV(w, h, l, u, v);
			v5.addVertexWithUV(w, oy, l, u, dv);
			v5.addVertexWithUV(w, oy, oz, du, dv);

			n = RotaryAux.func_39467_b_(m.getBlock(), m.getMachineMetadata(), 5, null);
			u = (n%16)/16D;
			v = (n/16)/16D;
			du = u+0.0625;
			dv = v+0.0625;

			v5.addVertexWithUV(ox, oy, oz, u, dv);
			v5.addVertexWithUV(ox, oy, l, du, dv);
			v5.addVertexWithUV(ox, h, l, du, v);
			v5.addVertexWithUV(ox, h, oz, u, v);
			v5.draw();
			GL11.glPopMatrix();*/
			return false;
		}
		return this.isValidMachineRenderPass(te);
	}
}
