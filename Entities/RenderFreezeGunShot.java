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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFreezeGunShot extends Render
{
	public RenderFreezeGunShot()
	{
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}

	public void renderTheFreezeGunShot(EntityFreezeGunShot par1EntityFreezeGunShot, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/Entity/freezegun.png");
		Tessellator var11 = Tessellator.instance;
		float var16 = 1.0F;
		float var17 = 0.5F;
		float var18 = 0.25F;
		int var19 = par1EntityFreezeGunShot.getBrightnessForRender(par9);
		int var20 = var19 % 65536;
		int var21 = var19 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var20 / 1.0F, var21 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var26 = 255.0F;
		int var22 = (int)var26;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		float var25 = 0.3F;
		GL11.glScalef(var25, var25, var25);
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
		this.renderTheFreezeGunShot((EntityFreezeGunShot)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
