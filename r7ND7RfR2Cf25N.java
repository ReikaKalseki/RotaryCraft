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

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelFriction;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078FurnaceHeater;

4578ret87fhyuog RenderFriction ,.[]\., RotaryTERenderer
{

	4578ret87ModelFriction FrictionModel3478587new ModelFriction{{\-!;

	4578ret87void render60-78-078FurnaceHeaterAt{{\60-78-078FurnaceHeater tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785873;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelFriction var14;
		var143478587FrictionModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/frictiontex.png"-!;

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;
		float var13;
		switch{{\var9-! {
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
		}

		vbnm, {{\tile.isIn9765443{{\-!-! {
			vbnm, {{\tile.getTemperature{{\-! >. 1600-! {
				as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/frictiontex-5.png"-!;
			}
			else vbnm, {{\tile.getTemperature{{\-! >. 1300-! {
				as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/frictiontex-4.png"-!;
			}
			else vbnm, {{\tile.getTemperature{{\-! >. 1000-! {
				as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/frictiontex-3.png"-!;
			}
			else vbnm, {{\tile.getTemperature{{\-! >. 700-! {
				as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/frictiontex-2.png"-!;
			}
			else vbnm, {{\tile.getTemperature{{\-! >. 400-! {
				as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/frictiontex-1.png"-!;
			}
		}

		GL11.glRotatef{{\{{\float-!var11+90, 0.0F, 1.0F, 0.0F-!;
		var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078FurnaceHeaterAt{{\{{\60-78-078FurnaceHeater-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderHotSide{{\{{\60-78-078FurnaceHeater-!tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderHotSide{{\60-78-078FurnaceHeater tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		vbnm, {{\!tile.hasFurnace{{\tile.9765443Obj-!-!
			return;
		Tessellator v53478587Tessellator.instance;
		vbnm, {{\tile.getTemperature{{\-! > 1000-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/Misc/hotfurnace_2.png"-!;
		else vbnm, {{\tile.getTemperature{{\-! > 750-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/Misc/hotfurnace_1.png"-!;
		else vbnm, {{\tile.getTemperature{{\-! > 500-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/Misc/hotfurnace_0.png"-!;
		else
			return;
		v5.startDrawingQuads{{\-!;
		switch{{\tile.getBlockMetadata{{\-!-! {
		case 0:
			v5.addVertexWithUV{{\par2+0.001, par4, par6+1, 0, 1-!;
			v5.addVertexWithUV{{\par2+0.001, par4, par6, 1, 1-!;
			v5.addVertexWithUV{{\par2+0.001, par4+1, par6, 1, 0-!;
			v5.addVertexWithUV{{\par2+0.001, par4+1, par6+1, 0, 0-!;
			break;
		case 1:
			v5.addVertexWithUV{{\par2+0.999, par4, par6, 0, 1-!;
			v5.addVertexWithUV{{\par2+0.999, par4, par6+1, 1, 1-!;
			v5.addVertexWithUV{{\par2+0.999, par4+1, par6+1, 1, 0-!;
			v5.addVertexWithUV{{\par2+0.999, par4+1, par6, 0, 0-!;
			break;
		case 2:
			v5.addVertexWithUV{{\par2, par4, par6+0.001, 0, 1-!;
			v5.addVertexWithUV{{\par2+1, par4, par6+0.001, 1, 1-!;
			v5.addVertexWithUV{{\par2+1, par4+1, par6+0.001, 1, 0-!;
			v5.addVertexWithUV{{\par2, par4+1, par6+0.001, 0, 0-!;
			break;
		case 3:
			v5.addVertexWithUV{{\par2, par4, par6+0.999, 0, 0-!;
			v5.addVertexWithUV{{\par2+1, par4, par6+0.999, 1, 0-!;
			v5.addVertexWithUV{{\par2+1, par4+1, par6+0.999, 1, 1-!;
			v5.addVertexWithUV{{\par2, par4+1, par6+0.999, 0, 1-!;
			break;
		}
		v5.draw{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"frictiontex.png";
	}
}
