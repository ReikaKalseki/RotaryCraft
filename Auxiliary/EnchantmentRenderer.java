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

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;

public abstract class EnchantmentRenderer {

	private static double par2;
	private static double par4;
	private static double par6;
	private static final double expand = 0.0625;

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

		Tessellator var5 = new Tessellator();
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
