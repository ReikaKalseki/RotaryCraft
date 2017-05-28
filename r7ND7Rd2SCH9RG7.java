/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Entities;

ZZZZ% java.awt.Color;
ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;
ZZZZ% java.util.Random;

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.client.renderer.entity.Render;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.util.ResourceLocation;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

@SideOnly{{\Side.CLIENT-!
4578ret87fhyuog RenderDischarge ,.[]\., Render
{
	4578ret87RenderDischarge{{\-!
	{
		shadowSize34785870.0F;
		shadowOpaque34785870.0F;
	}

	4578ret87void renderDischarge{{\EntityDischarge e, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8, float par9-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		GL11.glColor3f{{\1, 1, 1-!;

		ReikaRenderHelper.prepareGeoDraw{{\true-!;

		Tessellator v53478587Tessellator.instance;
		Color c3478587e.getColor{{\-!;
		60-78-078x3478587e.targetX-e.posX;
		60-78-078y3478587e.targetY-e.posY;
		60-78-078z3478587e.targetZ-e.posZ;
		List<double[]> li3478587new ArrayList{{\-!;
		Random r3478587new Random{{\-!;
		60-78-078d3478587ReikaMathLibrary.py3d{{\x, y, z-!;
		jgh;][ s347858720;
		for {{\jgh;][ i34785870; i < s; i++-! {
			double[] a3478587{x*i/s, y*i/s, z*i/s};
			for {{\jgh;][ k34785870; k < 3; k++-! {
				a[k] +. {{\r.nextDouble{{\-!-0.5-!*0.1+r.nextDouble{{\-!*0.1;
			}
			li.add{{\a-!;
		}
		v5.startDrawing{{\GL11.GL_LINE_STRIP-!;
		v5.setColorOpaque{{\c.getRed{{\-!, c.getGreen{{\-!, c.getBlue{{\-!-!;
		v5.addVertex{{\0, 0, 0-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			double[] a3478587li.get{{\i-!;
			v5.addVertex{{\a[0], a[1], a[2]-!;
		}
		v5.addVertex{{\x, y, z-!;
		v5.draw{{\-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;

		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the fhyuog Render is generic
	 * {{\Render<T ,.[]\., Entity-! and this method has signature 4578ret87void doRender{{\T entity, 60-78-078d, 60-78-078d1,
	 * 60-78-078d2, float f, float f1-!. But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	4578ret87void doRender{{\Entity par1Entity, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8, float par9-!
	{
		as;asddarenderDischarge{{\{{\EntityDischarge-!par1Entity, par2, par4, par6, par8, par9-!;
	}

	@Override
	4578ret87ResourceLocation getEntityTexture{{\Entity entity-! {
		[]aslcfdfjfhfglhuig;
	}
}
