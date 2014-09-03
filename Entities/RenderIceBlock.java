/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;

public class RenderIceBlock extends Render {

	public RenderIceBlock() {
		shadowSize = 0F;
		shadowOpaque = 0F;
	}

	public void renderIce(EntityIceBlock e, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(par2, par4, par6);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Entity/ice.png");
		Tessellator var11 = Tessellator.instance;
		int var19 = e.getBrightnessForRender(par9);
		int var20 = var19 % 65536;
		int var21 = var19 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var20 / 1.0F, var21 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glRotatef(e.rotationYaw, 0, 1, 0);
		double dd = 0.5*0;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		var11.startDrawingQuads();

		var11.addVertexWithUV(-e.xWidth-dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0);
		var11.addVertexWithUV(e.xWidth+dd/2, 	-dd,			-e.zWidth-dd/2, 1, 0);
		var11.addVertexWithUV(e.xWidth+dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 1, 1);
		var11.addVertexWithUV(-e.xWidth-dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1);

		var11.addVertexWithUV(-e.xWidth-dd/2, 	-dd,			e.zWidth+dd/2, 0, 0);
		var11.addVertexWithUV(e.xWidth+dd/2, 	-dd,			e.zWidth+dd/2, 1, 0);
		var11.addVertexWithUV(e.xWidth+dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1);
		var11.addVertexWithUV(-e.xWidth-dd/2, 	e.yWidth+dd, 	e.zWidth+dd/2, 0, 1);

		var11.addVertexWithUV(-e.xWidth-dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0);
		var11.addVertexWithUV(-e.xWidth-dd/2, 	-dd,			e.zWidth+dd/2, 1, 0);
		var11.addVertexWithUV(-e.xWidth-dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1);
		var11.addVertexWithUV(-e.xWidth-dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1);

		var11.addVertexWithUV(e.xWidth+dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0);
		var11.addVertexWithUV(e.xWidth+dd/2, 	-dd,			e.zWidth+dd/2, 1, 0);
		var11.addVertexWithUV(e.xWidth+dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1);
		var11.addVertexWithUV(e.xWidth+dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1);

		var11.addVertexWithUV(-e.xWidth-dd/2, 	-dd,			-e.zWidth-dd/2, 0, 0);
		var11.addVertexWithUV(-e.xWidth-dd/2, 	-dd,			e.zWidth+dd/2, 1, 0);
		var11.addVertexWithUV(e.xWidth+dd/2,	-dd,			e.zWidth+dd/2, 1, 1);
		var11.addVertexWithUV(e.xWidth+dd/2, 	-dd,			-e.zWidth-dd/2, 0, 1);

		var11.addVertexWithUV(-e.xWidth-dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 0);
		var11.addVertexWithUV(-e.xWidth-dd/2, 	e.yWidth+dd, 	e.zWidth+dd/2, 1, 0);
		var11.addVertexWithUV(e.xWidth+dd/2,	e.yWidth+dd, 	e.zWidth+dd/2, 1, 1);
		var11.addVertexWithUV(e.xWidth+dd/2, 	e.yWidth+dd, 	-e.zWidth-dd/2, 0, 1);

		var11.draw();
		GL11.glRotatef(-e.rotationYaw, 0, 1, 0);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		//ReikaChatHelper.write("Rendering.");
		this.renderIce((EntityIceBlock)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
