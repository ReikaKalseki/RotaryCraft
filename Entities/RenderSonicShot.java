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

import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSonicShot extends Render
{
	public RenderSonicShot()
	{
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}

	public void renderSonicShot(EntitySonicShot par1EntitySonicShot, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		//ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Entity/shockwave.png");
		Tessellator var11 = Tessellator.instance;
		float var16 = 1.0F;
		float var17 = 0.5F;
		float var18 = 0.25F;
		ReikaRenderHelper.prepareGeoDraw(true);
		int var19 = par1EntitySonicShot.getBrightnessForRender(par9);
		int var20 = var19 % 65536;
		int var21 = var19 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var20 / 1.0F, var21 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var26 = 255.0F;
		int var22 = (int)var26;
		//GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		float var25 = 0.3F;
		int[] dirs = par1EntitySonicShot.getSteps();
		GL11.glScalef(var25, var25, var25);
		int color = ReikaColorAPI.RGBtoHex(127,222,255);
		int rx = 0;
		int ry = 0;
		int rz = 0;
		if (dirs[0] == 1) {

		}
		if (dirs[0] == -1) {
			ry = 180;
		}
		if (dirs[1] == 1) {
			rz = 90;
		}
		if (dirs[1] == -1) {
			rz = -90;
		}
		if (dirs[2] == 1) {
			ry = -90;
		}
		if (dirs[2] == -1) {
			ry = 90;
		}

		GL11.glRotated(rx, 1, 0, 0);
		GL11.glRotated(ry, 0, 1, 0);
		GL11.glRotated(rz, 0, 0, 1);
		double[] x = {0, 0, -0.0625, -0.1875, -0.4375, -0.75, -1.5, -2.5};
		for (int i = 0; i < 360; i += 60) {
			GL11.glRotatef(i, 1, 0, 0);
			for (int k = 0; k < (x.length-1); k++) {
				ReikaRenderHelper.renderLine(x[k], k, 0, x[k+1], k+1, 0, color);
			}
			GL11.glRotatef(-i, 1, 0, 0);
		}

		ReikaRenderHelper.prepareGeoDraw(false);

		for (int i = 1; i < x.length; i++)
			ReikaRenderHelper.renderVCircle(i, x[i], 0, 0, color, 0, 15);
		ReikaRenderHelper.exitGeoDraw();

		GL11.glRotated(-rz, 0, 0, 1);
		GL11.glRotated(-ry, 0, 1, 0);
		GL11.glRotated(-rx, 1, 0, 0);

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
		this.renderSonicShot((EntitySonicShot)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
