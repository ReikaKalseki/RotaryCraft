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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import Reika.DragonAPI.Interfaces.TileEntity.RenderFetcher;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Models.ModelEMP;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;

public class RenderEMP extends RotaryTERenderer
{

	private ModelEMP EMPModel = new ModelEMP();
	//private ModelEMPV EMPModelV = new ModelEMPV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityEMPAt(TileEntityEMP tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
			var9 = 0;
		else
			var9 = tile.getBlockMetadata();

		ModelEMP var14;
		var14 = EMPModel;
		//ModelEMPV var15;
		//var14 = this.EMPModelV;
		if (tile.isLoading())
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/emptex2.png");
		else
			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/emptex.png");

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		if (!tile.isInWorld()) {
			GL11.glScaled(1.125, 1.125, 1.125);
			GL11.glTranslatef(0, -0.25F, 0);
			GL11.glRotatef(-90, 0, 1, 0);
		}
		int var11 = 0;	 //used to rotate the model about metadata


		//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
		float var13;/*

	            var12 = 1.0F - var12;
	            var12 = 1.0F - var12 * var12 * var12;*/
		// if (tile.getBlockMetadata() < 4)

		var14.renderAll(tile, null);
		if (tile.isInWorld())
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void renderEffect(TileEntityEMP te, double x, double y, double z, float ptick) {
		double r = te.effectRender.getRadius(ptick);

		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		ReikaRenderHelper.disableEntityLighting();
		BlendMode.ADDITIVEDARK.apply();
		GL11.glDepthMask(false);

		ReikaTextureHelper.bindTexture(RotaryCraft.class, "Textures/emp1.png");
		this.renderSphere(te, x, y, z, r, te.effectRender.getColor2());
		ReikaTextureHelper.bindTexture(RotaryCraft.class, "Textures/emp2.png");
		this.renderSphere(te, x, y, z, r, te.effectRender.getColor1());

		GL11.glPopAttrib();
	}

	private void renderSphere(TileEntityEMP te, double x, double y, double z, double r, int color) {
		Tessellator var5 = Tessellator.instance;
		var5.startDrawingQuads();
		color = ReikaColorAPI.getModifiedSat(color, 1.5F);
		var5.setColorOpaque_I(color);
		double dk = 0.5*r/4;
		double di = 20;
		for (double k = -r; k <= r; k += dk) {
			double r2 = Math.sqrt(r*r-k*k);
			double r3 = Math.sqrt(r*r-(k+dk)*(k+dk));
			for (int i = 0; i < 360; i += di) {
				double a = Math.toRadians(i);
				double a2 = Math.toRadians(i+di);
				double ti = /*i*/0+(System.currentTimeMillis()/2000D%360);
				double tk = /*k/(r*2)*/0+(System.currentTimeMillis()/3000D%360);
				double u = ti;//ti/360D;
				double du = ti+1;//(ti+di)/360D;
				double v = tk;//tk/r;
				double dv = tk+1;//(tk+dk)/r;

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
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.doRenderModel((RotaryCraftTileEntity)tile))
			this.renderTileEntityEMPAt((TileEntityEMP)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
			IORenderer.renderIO(tile, par2, par4, par6);
			if (((TileEntityEMP)tile).effectRender != null)
				this.renderEffect((TileEntityEMP)tile, par2+0.5, par4+0.5, par6+0.5, par8);
			if (((TileEntityEMP)tile).isLoading())
				this.renderCharging((TileEntityEMP)tile, par2, par4, par6);
		}
	}

	private void renderCharging(TileEntityEMP te, double par2, double par4, double par6) {

	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		return "emptex.png";
	}
}
