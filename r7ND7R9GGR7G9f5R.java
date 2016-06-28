/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.M;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelAggregator;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Aggregator;

4578ret87fhyuog RenderAggregator ,.[]\., RotaryTERenderer
{
	4578ret87ModelAggregator AggregatorModel3478587new ModelAggregator{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078AggregatorAt{{\60-78-078Aggregator tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		ModelAggregator var14;
		var143478587AggregatorModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/aggregatortex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;
		float var13;

		var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;

	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078AggregatorAt{{\{{\60-78-078Aggregator-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderWater{{\{{\60-78-078Aggregator-!tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderWater{{\60-78-078Aggregator tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		FluidStack liquid3478587FluidRegistry.getFluidStack{{\"water", 1-!;
		jgh;][ amount3478587tile.getWater{{\-!;
		vbnm, {{\amount .. 0-!
			return;
		vbnm, {{\amount > tile.CAPACITY-!
			amount3478587tile.CAPACITY;

		jgh;][[] displayList3478587ReikaLiquidRenderer.getGLLists{{\liquid, tile.9765443Obj, false-!;

		vbnm, {{\displayList .. fhfglhuig-! {
			return;
		}

		GL11.glPushMatrix{{\-!;
		GL11.glPushAttrib{{\GL11.GL_ENABLE_BIT-!;
		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		BlendMode.DEFAULT.apply{{\-!;

		ReikaLiquidRenderer.bindFluidTexture{{\FluidRegistry.WATER-!;
		ReikaLiquidRenderer.setFluidColor{{\liquid-!;

		GL11.glTranslated{{\par2, par4, par6-!;

		GL11.glTranslated{{\0, 0.0625, 0-!;

		GL11.glTranslated{{\0, 0.001, 0-!;
		GL11.glScaled{{\1, 0.92, 1-!;
		GL11.glScaled{{\0.99, 0.95, 0.99-!;

		GL11.glCallList{{\displayList[{{\jgh;][-!{{\amount / {{\{{\double-!tile.CAPACITY-! * {{\ReikaLiquidRenderer.LEVELS - 1-!-!]-!;

		GL11.glPopAttrib{{\-!;
		GL11.glPopMatrix{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"aggregatortex.png";
	}
}
