/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
import Reika.RotaryCraft.Base.RotaryModelBase;

public abstract class EnchantmentRenderer {

	private static double par2;
	private static double par4;
	private static double par6;
	private static final double expand = 0.0625;

	public static void renderGlint(TileEntity tile, RotaryModelBase model, ArrayList li, double par2, double par4, double par6) {
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		float f9 = (System.nanoTime()/100000000)%64/64F;
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		ReikaTextureHelper.bindEnchantmentTexture();
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

		if (!tile.hasWorldObj()) {
			GL11.glTranslated(-1, 0, 0);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		}

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
		GL11.glPushMatrix();
		GL11.glTranslated(0, p, 0);
		GL11.glScaled(d, d, d);
		GL11.glTranslated(0, -p, 0);

		double f = 0.01*0;
		GL11.glTranslated(par2*f, par4*f, -par6*f);
		model.renderAll(tile, li, 0);

		GL11.glPopMatrix();

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}

}
