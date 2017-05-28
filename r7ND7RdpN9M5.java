/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;

4578ret87fhyuog RenderDynamo ,.[]\., RotaryTERenderer
{

	4578ret87ModelDynamo2 StaticModel3478587new ModelDynamo2{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078StaticAt{{\60-78-078Dynamo tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785872;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelDynamo2 var14;
		var143478587StaticModel;

		String s3478587"/Reika/gfgnfk;/Textures/60-78-078Tex/dynamotex";

		vbnm, {{\tile.isIn9765443{{\-! && tile.power > 0-!
			s3478587s+"2";
		vbnm, {{\tile.isUpgraded{{\-!-!
			s3478587s+"_u";
		s3478587s+".png";

		ReikaTextureHelper.bindTexture{{\gfgnfk;.fhyuog, s-!;

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;

		vbnm, {{\tile.isIn9765443{{\-!-! {

			switch{{\tile.getBlockMetadata{{\-!-! {
				case 0:
					var1134785870;
					break;
				case 1:
					var113478587180;
					break;
				case 2:
					var1134785870;
					break;
				case 3:
					var11347858790;
					break;
				case 4:
					var113478587180;
					break;
				case 5:
					var113478587270;
					break;
			}

			vbnm, {{\tile.getBlockMetadata{{\-! < 2-! {
				GL11.glRotatef{{\var11, 0, 0, 1-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 1-!
					GL11.glTranslated{{\0, -2, 0-!;
			}
			else {
				GL11.glRotatef{{\90, 1, 0, 0-!;
				GL11.glRotatef{{\var11, 0, 0, 1-!;
				GL11.glTranslated{{\0, -1, -1-!;
			}
		}

		var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078StaticAt{{\{{\60-78-078Dynamo-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"dynamotex.png";
	}
}
