/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.RotaryModelBase;

public abstract class EnchantmentRenderer {

	private static double par2;
	private static double par4;
	private static double par6;
	private static final double expand = 0.0625;

	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	public static void bindGlint() {
		Minecraft.getMinecraft().renderEngine.bindTexture(RES_ITEM_GLINT);
	}

	public static void renderGlint(TileEntity tile, RotaryModelBase model, ArrayList li, double par2, double par4, double par6) {
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		float f9 = (System.nanoTime()/100000000)%64/64F;
		bindGlint();
		GL11.glEnable(GL11.GL_BLEND);
		BlendMode.OVERLAYDARK.apply();
		float f10 = 0.5F;
		GL11.glColor4f(f10, f10, f10, 1.0F);

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glTranslated(f9, f9, f9);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		float var11 = 0;
		switch(tile.getBlockMetadata()) {
		case 0:
			var11 = 180;
			break;
		case 1:
			var11 = 0;
			break;
		case 2:
			var11 = 270;
			break;
		case 3:
			var11 = 90;
			break;
		}
		float f11 = 0.76F;
		GL11.glColor4f(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
		GL11.glRotatef(var11-90, 0.0F, 1.0F, 0.0F);
		GL11.glDepthMask(false);

		if (tile.hasWorldObj())
			ReikaRenderHelper.disableLighting();

		double d = 1.0125;
		int p = 2;
		GL11.glTranslated(0, p, 0);
		GL11.glScaled(d, d, d);
		GL11.glTranslated(0, -p, 0);

		double f = 0.01*0;
		GL11.glTranslated(par2*f, par4*f, -par6*f);
		model.renderAll(tile, li, 0, 0);

		GL11.glTranslated(0, p, 0);
		GL11.glScaled(1D/d, 1D/d, 1D/d);
		GL11.glTranslated(0, -p, 0);

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glDepthMask(true);

		if (tile.hasWorldObj())
			ReikaRenderHelper.enableLighting();

		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glPopMatrix();
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		BlendMode.ADDITIVE2.apply();
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void renderShine(double p2, double p4, double p6, double dx, double dy, double dz) {
		boolean textured = true;
		par2 = p2;
		par4 = p4;
		par6 = p6;
		int[] color = {127, 0, 255, 255};
		if (textured)
			ReikaRenderHelper.disableLighting();
		else
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
		if (color[3] > 255)
			color[3] = 255;

		Tessellator var5 = Tessellator.instance;
		var5.startDrawing(GL11.GL_QUADS);
		//var5.setBrightness(255);

		if (textured) {
			ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Misc/glint.png");
			GL11.glEnable(GL11.GL_BLEND);
			double off = ((System.nanoTime()/50000000)%25)/25D;
			var5.addVertexWithUV(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 0+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off);

			var5.addVertexWithUV(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 0+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off);

			var5.addVertexWithUV(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 0+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 1+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off);

			var5.addVertexWithUV(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 0+off, 0+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 0+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off);

			var5.addVertexWithUV(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 0+off, 0+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 1+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand, 1+off, 0+off);

			var5.addVertexWithUV(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 0+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand, 0+off, 1+off);
			var5.addVertexWithUV(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 1+off);
			var5.addVertexWithUV(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand, 1+off, 0+off);
			GL11.glColor4d(1, 1, 1, 1);
		}
		else {
			var5.setColorRGBA(color[0], color[1], color[2], (int)(color[3]*0.375));

			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);

			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);

			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);

			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);

			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);

			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
		}
		var5.draw();

		if (!textured) {
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz-0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz-0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(dx+1+0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx+1+0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.draw();
			var5.startDrawing(GL11.GL_LINE_LOOP);
			var5.setColorRGBA(color[0], color[1], color[2], color[3]);
			var5.addVertex(dx-0.0625*expand, dy-0.0625*expand, dz+1+0.0625*expand);
			var5.addVertex(dx-0.0625*expand, dy+1+0.0625*expand, dz+1+0.0625*expand);
			var5.draw();
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		ReikaRenderHelper.exitGeoDraw();
	}

}
