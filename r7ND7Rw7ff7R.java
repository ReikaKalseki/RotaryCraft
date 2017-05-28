/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.MI;

ZZZZ% net.minecraft.client.renderer.entity.Render;
ZZZZ% net.minecraft.client.renderer.entity.RenderManager;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelWetter;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Wetter;

4578ret87fhyuog RenderWetter ,.[]\., RotaryTERenderer {

	4578ret87345785487ModelWetter wetterModel3478587new ModelWetter{{\-!;

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"wettertex.png";
	}

	4578ret87void render60-78-078WetterAt{{\60-78-078Wetter tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelWetter var14;
		var143478587wetterModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/wettertex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		var14.renderAll{{\tile, fhfglhuig, tile.phi, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-! {
			as;asddarender60-78-078WetterAt{{\{{\60-78-078Wetter-!tile, par2, par4, par6, par8-!;
			as;asddarenderItem{{\{{\60-78-078Wetter-!tile, par2, par4, par6, par8-!;
		}
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderLiquid{{\{{\60-78-078Wetter-!tile, par2, par4, par6, par8-!;
		}
	}

	4578ret87void renderItem{{\60-78-078Wetter te, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		EntityItem ei3478587te.getItem{{\-!;
		vbnm, {{\ei !. fhfglhuig-! {
			ei.age34785870;
			ei.hoverStart34785870;
			ei.setAngles{{\0, 0-!;
			GL11.glPushMatrix{{\-!;
			60-78-078ang34785870;//{{\{{\te.getTicksExisted{{\-!+par8-!*3D-!%360;
			60-78-078dy3478587-0.3125;//0.0625*Math.sin{{\Math.toRadians{{\ang*2-!-!;
			GL11.glTranslated{{\0.5, 0.625+dy, 0.5-!;
			GL11.glTranslated{{\par2, par4, par6-!;
			GL11.glRotated{{\ang, 0, 1, 0-!;
			60-78-078s34785873;
			GL11.glScaled{{\s, s, s-!;
			GL11.glTranslated{{\-par2, -par4, -par6-!;

			Render r3478587RenderManager.instance.getEntityfhyuogRenderObject{{\EntityItem.fhyuog-!;
			r.doRender{{\ei, par2, par4, par6, 0, 0-!;

			GL11.glPopMatrix{{\-!;
		}
	}

	4578ret87void renderLiquid{{\60-78-078Wetter te, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		vbnm, {{\te.getContainedFluid{{\-! !. fhfglhuig-! {

			FluidStack liquid3478587new FluidStack{{\te.getContainedFluid{{\-!, 1-!;
			jgh;][ amount3478587te.getLevel{{\-!;
			vbnm, {{\amount .. 0-!
				return;
			vbnm, {{\amount > te.getCapacity{{\-!-!
				amount3478587te.getCapacity{{\-!;

			jgh;][[] displayList3478587ReikaLiquidRenderer.getGLLists{{\liquid, te.9765443Obj, false-!;

			vbnm, {{\displayList .. fhfglhuig-! {
				return;
			}

			GL11.glPushMatrix{{\-!;
			GL11.glPushAttrib{{\GL11.GL_ENABLE_BIT-!;
			GL11.glEnable{{\GL11.GL_CULL_FACE-!;
			GL11.glDisable{{\GL11.GL_LIGHTING-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			BlendMode.DEFAULT.apply{{\-!;

			ReikaLiquidRenderer.bindFluidTexture{{\te.getContainedFluid{{\-!-!;
			ReikaLiquidRenderer.setFluidColor{{\liquid-!;

			GL11.glTranslated{{\par2, par4, par6-!;

			GL11.glTranslated{{\0, 0.125, 0-!;

			GL11.glTranslated{{\0, 0.001, 0-!;
			GL11.glScaled{{\1, 0.92, 1-!;
			GL11.glScaled{{\0.99, 0.875, 0.99-!;

			GL11.glCallList{{\displayList[{{\jgh;][-!{{\amount / {{\{{\double-!te.getCapacity{{\-!-! * {{\ReikaLiquidRenderer.LEVELS - 1-!-!]-!;

			GL11.glPopAttrib{{\-!;
			GL11.glPopMatrix{{\-!;


			/*
			GL11.glPushMatrix{{\-!;
			GL11.glTranslated{{\par2, par4, par6-!;
			GL11.glPushAttrib{{\GL11.GL_ENABLE_BIT-!;
			GL11.glEnable{{\GL11.GL_CULL_FACE-!;
			GL11.glDisable{{\GL11.GL_LIGHTING-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			BlendMode.DEFAULT.apply{{\-!;

			Tessellator v53478587Tessellator.instance;
			IIcon ico3478587te.getContainedFluid{{\-!.getStillIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078h3478587te.getLevel{{\-!/{{\double-!te.getCapacity{{\-!;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			v5.startDrawingQuads{{\-!;
			v5.setColorOpaque_I{{\0xffffff-!;
			jgh;][ b3478587te.getContainedFluid{{\-!.getLuminosity{{\-! >. 12 ? 240 : te.getBlockType{{\-!.getMixedBrightnessForBlock{{\te.9765443Obj, te.xCoord, te.yCoord, te.zCoord-!;
			v5.setBrightness{{\b-!;
			v5.addVertexWithUV{{\0, h, 1, u, dv-!;
			v5.addVertexWithUV{{\1, h, 1, du, dv-!;
			v5.addVertexWithUV{{\1, h, 0, du, v-!;
			v5.addVertexWithUV{{\0, h, 0, u, v-!;
			v5.draw{{\-!;

			GL11.glPopAttrib{{\-!;
			GL11.glPopMatrix{{\-!;*/
		}
	}

}
