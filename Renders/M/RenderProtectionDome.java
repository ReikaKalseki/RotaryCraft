/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders.M;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityProtectionDome;
import Reika.RotaryCraft.Models.ModelDomeEmitter;

public class RenderProtectionDome extends RotaryTERenderer
{

	protected final ModelDomeEmitter model = new ModelDomeEmitter();

	public void renderTileEntityProtectionDomeAt(TileEntityProtectionDome tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelDomeEmitter var14;
		var14 = model;

		this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/"+this.getImageFileName(tile));

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int var11 = 0;	 //used to rotate the model about metadata

		float var13;

		var14.renderAll(tile, null);

		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityProtectionDomeAt((TileEntityProtectionDome)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			this.renderField(((TileEntityProtectionDome)tile), par2+0.5, par4+0.5, par6+0.5);
		}
	}

	protected void renderField(TileEntityProtectionDome te, double x, double y, double z) {
		if (!te.isInWorld())
			return;
		if (te.getRange() <= 0)
			return;
		int color = te.getDomeColor();
		int r = te.getRange();

		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		ReikaRenderHelper.disableEntityLighting();
		BlendMode.ADDITIVEDARK.apply();
		GL11.glDepthMask(false);

		ReikaTextureHelper.bindTexture(RotaryCraft.class, "Textures/forcefield.png");
		Tessellator var5 = Tessellator.instance;
		var5.startDrawingQuads();
		var5.setColorRGBA_I(color, color >> 24 & 255);
		double dk = 0.5*r/16;
		double di = 10;
		for (double k = -r; k <= r; k += dk) {
			double r2 = Math.sqrt(r*r-k*k);
			double r3 = Math.sqrt(r*r-(k+dk)*(k+dk));
			for (int i = 0; i < 360; i += di) {
				double a = Math.toRadians(i);
				double a2 = Math.toRadians(i+di);
				double ti = i+(System.currentTimeMillis()/50D%360);
				double tk = k+(System.currentTimeMillis()/220D%360);
				double u = ti/360D;
				double du = (ti+di)/360D;
				double v = tk/r;
				double dv = (tk+dk)/r;
				double s1 = Math.sin(a);
				double s2 = Math.sin(a2);
				double c1 = Math.cos(a);
				double c2 = Math.cos(a2);
				var5.addVertexWithUV(x+r2*c1, y+k, z+r2*s1, u, v);
				var5.addVertexWithUV(x+r2*c2, y+k, z+r2*s2, du, v);
				var5.addVertexWithUV(x+r3*c2, y+k+dk, z+r3*s2, du, dv);
				var5.addVertexWithUV(x+r3*c1, y+k+dk, z+r3*s1, u, dv);
			}
		}
		var5.draw();

		var5.startDrawing(GL11.GL_TRIANGLE_FAN);
		var5.setColorRGBA_I(color, color >> 24 & 255);
		var5.addVertexWithUV(x, y+0.5, z, 0.5, 0.5);
		double dr = 2;
		for (int i = 0; i < 360; i += 10) {
			double a = Math.toRadians(i);
			double a2 = a+Math.toRadians(System.currentTimeMillis()/20D%360);
			double dx = Math.cos(a);
			double dz = Math.sin(a);
			double ux = (System.currentTimeMillis()/3100D)%10;
			double uy = (System.currentTimeMillis()/4700D)%10;
			double u = Math.cos(a2)+ux;
			double v = Math.sin(a2)+uy;
			u = u*0.25;
			v = v*0.25;
			var5.addVertexWithUV(x+dx*dr, y+r-0.25, z+dz*dr, u, v);
		}
		var5.draw();

		GL11.glPopAttrib();

		/*
		ReikaRenderHelper.prepareGeoDraw(false);
		for (double k = -r; k <= r; k += 0.5*r/8)
			ReikaRenderHelper.renderCircle(Math.sqrt(r*r-k*k), x, y+k, z, color, 15);
		for (int k = 0; k < 360; k += 15)
			ReikaRenderHelper.renderVCircle(r, x, y, z, color, k, 15);

		double ang = 7;
		ReikaRenderHelper.renderLine(x, y, z, x, y+r, z, color);
		for (int k = 0; k < 360; k += 15) {
			ReikaRenderHelper.renderVCircle(0.125, x, y+0.5, z, color, (System.nanoTime()/7500000)%360+k, 15);
			ReikaRenderHelper.renderLine(x, y, z, x+r*Math.sin(Math.toRadians(ang)*Math.cos(Math.toRadians(k))), y+r-Math.sin(Math.toRadians(ang)), z+r*Math.sin(Math.toRadians(ang)*Math.sin(Math.toRadians(k))), color);
		}
		ReikaRenderHelper.exitGeoDraw();
		 */
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "containtex.png";
	}
}
