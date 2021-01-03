/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.DragonAPI.Libraries.Rendering.ReikaRenderHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.EntityTurretShot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFlameTurretShot extends Render
{
	public RenderFlameTurretShot()
	{
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}

	public void renderTheFlameTurretShot(EntityTurretShot par1EntityTurretShot, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glDisable(GL11.GL_LIGHTING);
		ReikaRenderHelper.disableEntityLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Entity/flameturret.png");
		Tessellator var11 = Tessellator.instance;
		float var16 = 1.0F;
		float var17 = 0.5F;
		float var18 = 0.25F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		float var25 = 0.5F;
		GL11.glScalef(var25, var25, var25);
		BlendMode.ADDITIVEDARK.apply();
		for (int i = 0; i < 360; i += 45) {
			GL11.glRotatef(i, 0, 1, 0);
			var11.startDrawingQuads();
			var11.setNormal(0.0F, 1.0F, 0.0F);
			var11.addVertexWithUV(0.0F - var17, 0.0F - var18, 0.0D, 0, 0);
			var11.addVertexWithUV(var16 - var17, 0.0F - var18, 0.0D, 0, 1);
			var11.addVertexWithUV(var16 - var17, 1.0F - var18, 0.0D, 1, 1);
			var11.addVertexWithUV(0.0F - var17, 1.0F - var18, 0.0D, 1, 0);
			var11.draw();
		}

		GL11.glPopAttrib();
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
		this.renderTheFlameTurretShot((EntityTurretShot)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
