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

import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDischarge extends Render
{
	public RenderDischarge()
	{
		shadowSize = 0.0F;
		shadowOpaque = 0.0F;
	}

	public void renderDischarge(EntityDischarge e, double par2, double par4, double par6, float par8, float par9) {
		GL11.glTranslated(par2, par4, par6);
		GL11.glColor3f(1, 1, 1);

		ReikaRenderHelper.prepareGeoDraw(true);

		Tessellator v5 = Tessellator.instance;
		Color c = e.getColor();
		double x = e.targetX-e.posX;
		double y = e.targetY-e.posY;
		double z = e.targetZ-e.posZ;
		List<double[]> li = new ArrayList();
		Random r = new Random();
		double d = ReikaMathLibrary.py3d(x, y, z);
		int s = 20;
		for (int i = 0; i < s; i++) {
			double[] a = {x*i/s, y*i/s, z*i/s};
			for (int k = 0; k < 3; k++) {
				a[k] += (r.nextDouble()-0.5)*0.1+r.nextDouble()*0.1;
			}
			li.add(a);
		}
		v5.startDrawing(GL11.GL_LINE_STRIP);
		v5.setColorOpaque(c.getRed(), c.getGreen(), c.getBlue());
		v5.addVertex(0, 0, 0);
		for (int i = 0; i < li.size(); i++) {
			double[] a = li.get(i);
			v5.addVertex(a[0], a[1], a[2]);
		}
		v5.addVertex(x, y, z);
		v5.draw();
		ReikaRenderHelper.exitGeoDraw();

		GL11.glTranslated(-par2, -par4, -par6);
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
		this.renderDischarge((EntityDischarge)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}