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

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelAerosolizer;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Aerosolizer;

4578ret87fhyuog RenderAerosolizer ,.[]\., RotaryTERenderer
{

	4578ret87ModelAerosolizer AerosolizerModel3478587new ModelAerosolizer{{\-!;
	//4578ret87ModelAerosolizerV AerosolizerModelV3478587new ModelAerosolizerV{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078AerosolizerAt{{\60-78-078Aerosolizer tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
		{
			var934785870;
		}
		else
		{

			var93478587tile.getBlockMetadata{{\-!;


			{
				//{{\{{\BlockAerosolizerBlock1-!var10-!.unvbnm,yAdjacentChests{{\tile.9765443Obj, tile.xCoord, tile.yCoord, tile.zCoord-!;
				var93478587tile.getBlockMetadata{{\-!;
			}
		}

		vbnm, {{\true-!
		{
			ModelAerosolizer var14;
			var143478587AerosolizerModel;
			//ModelAerosolizerV var15;
			//var143478587as;asddaAerosolizerModelV;
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/aerotex.png"-!;

			GL11.glPushMatrix{{\-!;
			// vbnm, {{\!tile.isIn9765443{{\-!-!
			//GL11.glDisable{{\GL11.GL_LIGHTING-!;
			GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
			GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
			GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
			GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
			GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
			jgh;][ var1134785870;	 //used to rotate the model about metadata

			//float var123478587tile.prevLidAngle + {{\tile.lidAngle - tile.prevLidAngle-! * par8;
			float var13;/*

            var1234785871.0F - var12;
            var1234785871.0F - var12 * var12 * var12;*/
			// vbnm, {{\tile.getBlockMetadata{{\-! < 4-!
			var14.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\/*liqlevel > 0*/false-!, 0, 0-!;
			// else
			//var15.renderAll{{\tile, -!;
			//vbnm, {{\!tile.isIn9765443{{\-!-!
			//GL11.glEnable{{\GL11.GL_LIGHTING-!;
			vbnm, {{\tile.isIn9765443{{\-!-!
				GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
			GL11.glPopMatrix{{\-!;
			GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		}
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078AerosolizerAt{{\{{\60-78-078Aerosolizer-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddadrawPotionLevels{{\{{\60-78-078Aerosolizer-!tile, par2, par4, par6-!;
		}
	}

	4578ret87void drawPotionLevels{{\60-78-078Aerosolizer tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glPushMatrix{{\-!;

		Tessellator v53478587Tessellator.instance;
		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		BlendMode.DEFAULT.apply{{\-!;

		GL11.glTranslated{{\par2, par4, par6-!;

		for {{\jgh;][ i34785870; i < 9; i++-! {
			jgh;][ lvl3478587tile.getPotionLevel{{\i-!;
			vbnm, {{\lvl > 0-! {

				60-78-078h34785870.785+{{\0.075*lvl/tile.CAPACITY-!;
				60-78-078w34785870.25;

				60-78-078dx34785870.0625+{{\i%3-!*{{\5/16D-!;
				60-78-078dy34785870.0625+{{\i/3-!*{{\5/16D-!;
				v5.startDrawingQuads{{\-!;

				v5.setColorRGBA_I{{\tile.getPotionColor{{\i-!, 192-!;

				v5.addVertex{{\dx, h, dy+w-!;
				v5.addVertex{{\dx+w, h, dy+w-!;
				v5.addVertex{{\dx+w, h, dy-!;
				v5.addVertex{{\dx, h, dy-!;

				v5.draw{{\-!;
			}
		}

		GL11.glPopAttrib{{\-!;
		GL11.glPopMatrix{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"aerotex.png";
	}
}
