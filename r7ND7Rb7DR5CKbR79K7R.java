/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.DM;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelBedrockBreaker;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelBedrockBreakerV;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BedrockBreaker;

4578ret87fhyuog RenderBedrockBreaker ,.[]\., RotaryTERenderer
{
	4578ret87ModelBedrockBreaker bbm3478587new ModelBedrockBreaker{{\-!;
	4578ret87ModelBedrockBreakerV bbmV3478587new ModelBedrockBreakerV{{\-!;

	4578ret87void render60-78-078BedrockBreakerAt{{\60-78-078BedrockBreaker tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelBedrockBreaker var14;
		ModelBedrockBreakerV var15;

		var143478587bbm;
		var153478587bbmV;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/bedrocktex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
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
				var113478587-270;
				break;
			case 3:
				var113478587-90;
				break;
			case 4:
				var113478587270;
				break;
			case 5:
				var11347858790;
				break;
			}

			vbnm, {{\tile.getBlockMetadata{{\-! <. 3-!
				GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;
			else {
				vbnm, {{\tile.getBlockMetadata{{\-! .. 4-!
					GL11.glRotatef{{\180, 0.0F, 1.0F, 0.0F-!;
				GL11.glRotatef{{\var11, 0F, 0F, 1F-!;
				GL11.glTranslatef{{\-1F, -1F, 0F-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 5-!
					GL11.glTranslatef{{\2F, 0F, 0F-!;
			}
		}
		else {
			GL11.glEnable{{\GL11.GL_LIGHTING-!;
		}

		float var13;

		ArrayList li3478587ReikaJavaLibrary.makeListFrom{{\tile.getStep{{\-!-!;
		vbnm, {{\tile.isIn9765443{{\-! && tile.getBlockMetadata{{\-! > 3-! {
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/bedrockvtex.png"-!;
			var15.renderAll{{\tile, li, tile.phi, 0-!;
		}
		else
			var14.renderAll{{\tile, li, tile.phi, 0-!;
		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078BedrockBreakerAt{{\{{\60-78-078BedrockBreaker-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		60-78-078BedrockBreaker tb3478587{{\60-78-078BedrockBreaker-!te;
		vbnm, {{\tb.isIn9765443{{\-! && tb.getBlockMetadata{{\-! > 3-!
			[]aslcfdfj"bedrockvtex.png";
		[]aslcfdfj"bedrocktex.png";
	}
}
