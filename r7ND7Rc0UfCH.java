/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders;

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelClutch;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelVClutch;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Clutch;

4578ret87fhyuog RenderClutch ,.[]\., RotaryTERenderer
{

	4578ret87ModelClutch ClutchModel3478587new ModelClutch{{\-!;
	4578ret87ModelVClutch ClutchModelV3478587new ModelVClutch{{\-!;

	4578ret87void render60-78-078ClutchAt{{\60-78-078Clutch tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelClutch var14;
		ModelVClutch var15;

		var143478587ClutchModel;
		var153478587ClutchModelV;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttex.png"-!;

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;	 //used to rotate the model about metadata

		vbnm, {{\tile.isIn9765443{{\-!-! {

			switch{{\tile.getBlockMetadata{{\-!-! {
			case 0:
				var1134785870;
				break;
			case 1:
				var113478587180;
				break;
			case 2:
				var11347858790;
				break;
			case 3:
				var113478587270;
				break;
			case 4:
			case 5:
				var1134785870;
				break;
			}

			GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;

		}
		float var13;
		vbnm, {{\tile.getBlockMetadata{{\-! < 4-!
			var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;
		else {
			var15.renderAll{{\tile, fhfglhuig, tile.getBlockMetadata{{\-! .. 5 ? tile.phi : -tile.phi, 0-!;
		}

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078ClutchAt{{\{{\60-78-078Clutch-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderConnection{{\{{\60-78-078Clutch-!tile, par2, par4, par6, par8-!;
		}
	}

	4578ret87void renderConnection{{\60-78-078Clutch tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		jgh;][ c3478587tile.isOutputEnabled{{\-! ? 0xff0000 : 0x900000;
		jgh;][ c23478587tile.isOutputEnabled{{\-! ? 0xffa7a7 : 0xda0000;
		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		GL11.glPushMatrix{{\-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		vbnm, {{\tile.isOutputEnabled{{\-!-!
			GL11.glDisable{{\GL11.GL_LIGHTING-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;

		60-78-078h3478587tile.getBlockMetadata{{\-! >. 4 ? 0.5625 : 0.35;
		60-78-078h23478587h-0.125;
		60-78-078w34785870.225;

		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		v5.setBrightness{{\240-!;
		v5.setColorRGBA_I{{\c, 240-!;
		v5.addVertex{{\0.5-w, h, 0.5+w-!;
		v5.addVertex{{\0.5+w, h, 0.5+w-!;
		v5.addVertex{{\0.5+w, h, 0.5-w-!;
		v5.addVertex{{\0.5-w, h, 0.5-w-!;

		v5.addVertex{{\0.5+w, h2, 0.5-w-!;
		v5.addVertex{{\0.5-w, h2, 0.5-w-!;
		v5.addVertex{{\0.5-w, h, 0.5-w-!;
		v5.addVertex{{\0.5+w, h, 0.5-w-!;

		v5.addVertex{{\0.5+w, h, 0.5+w-!;
		v5.addVertex{{\0.5-w, h, 0.5+w-!;
		v5.addVertex{{\0.5-w, h2, 0.5+w-!;
		v5.addVertex{{\0.5+w, h2, 0.5+w-!;

		v5.addVertex{{\0.5-w, h, 0.5+w-!;
		v5.addVertex{{\0.5-w, h, 0.5-w-!;
		v5.addVertex{{\0.5-w, h2, 0.5-w-!;
		v5.addVertex{{\0.5-w, h2, 0.5+w-!;

		v5.addVertex{{\0.5+w, h2, 0.5+w-!;
		v5.addVertex{{\0.5+w, h2, 0.5-w-!;
		v5.addVertex{{\0.5+w, h, 0.5-w-!;
		v5.addVertex{{\0.5+w, h, 0.5+w-!;
		v5.draw{{\-!;

		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setBrightness{{\240-!;
		v5.setColorRGBA_I{{\c2, 240-!;
		v5.addVertex{{\0.5-w, h, 0.5+w-!;
		v5.addVertex{{\0.5+w, h, 0.5+w-!;
		v5.addVertex{{\0.5+w, h, 0.5-w-!;
		v5.addVertex{{\0.5-w, h, 0.5-w-!;
		v5.draw{{\-!;

		v5.startDrawing{{\GL11.GL_LINES-!;
		v5.setBrightness{{\240-!;
		v5.setColorRGBA_I{{\c2, 240-!;
		v5.addVertex{{\0.5-w, h, 0.5+w-!;
		v5.addVertex{{\0.5-w, h2, 0.5+w-!;

		v5.addVertex{{\0.5+w, h, 0.5+w-!;
		v5.addVertex{{\0.5+w, h2, 0.5+w-!;

		v5.addVertex{{\0.5+w, h, 0.5-w-!;
		v5.addVertex{{\0.5+w, h2, 0.5-w-!;

		v5.addVertex{{\0.5-w, h, 0.5-w-!;
		v5.addVertex{{\0.5-w, h2, 0.5-w-!;
		v5.draw{{\-!;

		GL11.glPopMatrix{{\-!;
		GL11.glPopAttrib{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"shafttex.png";
	}
}
