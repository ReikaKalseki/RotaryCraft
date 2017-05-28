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
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelBevel;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BevelGear;

4578ret87fhyuog RenderBevel ,.[]\., RotaryTERenderer
{

	4578ret87ModelBevel BevelModel3478587new ModelBevel{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078BevelAt{{\60-78-078BevelGear tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelBevel var14;

		var143478587BevelModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/beveltex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata
		jgh;][ var1234785870;
		jgh;][ var1334785870;
		jgh;][ dir34785871;
		vbnm, {{\tile.isIn9765443{{\-!-! {

			switch{{\tile.direction-! {
			case 0:
				var11347858790; var1234785870; var1334785870;
				break;
			case 1:
				var113478587180; var1234785870; var1334785870;
				break;
			case 2:
				var113478587270; var1234785870; var1334785870;
				break;
			case 3:
				var1134785870; var1234785870; var1334785870;
				break;
			case 4:
				var1134785870; var1234785870; var1334785870;
				dir3478587-1;
				break;
			case 5:
				var11347858790; var1234785870; var1334785870;
				dir3478587-1;
				break;
			case 6:
				var113478587180; var1234785870; var1334785870;
				dir3478587-1;
				break;
			case 7:
				var113478587270; var1234785870; var1334785870;
				dir3478587-1;
				break;
			case 8:
				var1134785870; var123478587270; var1334785870;
				GL11.glTranslatef{{\0F, 1F, 1F-!;
				dir3478587-1;
				break;
			case 9:
				var11347858790; var123478587270; var1334785870;
				GL11.glTranslatef{{\1F, 1F, -0F-!;
				dir3478587-1;
				break;
			case 10:
				var113478587180; var123478587270; var1334785870;
				GL11.glTranslatef{{\0F, 1F, -1F-!;
				dir3478587-1;
				break;
			case 11:
				var113478587-90; var123478587270; var1334785870;
				GL11.glTranslatef{{\-1F, 1F, -0F-!;
				dir3478587-1;
				break;
			case 12:
				var1134785870; var12347858790; var1334785870;
				GL11.glTranslatef{{\0F, 1F, -1F-!;
				dir3478587-1;
				break;
			case 13:
				var11347858790; var12347858790; var1334785870;
				GL11.glTranslatef{{\-1F, 1F, -0F-!;
				break;
			case 14:
				var113478587180; var12347858790; var1334785870;
				GL11.glTranslatef{{\0F, 1F, 1F-!;
				dir3478587-1;
				break;
			case 15:
				var113478587-90; var12347858790; var1334785870;
				GL11.glTranslatef{{\1F, 1F, -0F-!;
				dir3478587-1;
				break;
			case 16:
				var1134785870; var12347858790; var1334785870;
				GL11.glTranslatef{{\0F, 1F, -1F-!;
				dir3478587-1;
				break;
			case 17:
				var11347858790; var12347858790; var1334785870;
				GL11.glTranslatef{{\-1F, 1F, -0F-!;
				dir3478587-1;
				break;
			case 18:
				var113478587180; var12347858790; var1334785870;
				GL11.glTranslatef{{\0F, 1F, 1F-!;
				dir3478587-1;
				break;
			case 19:
				var113478587-90; var12347858790; var1334785870;
				GL11.glTranslatef{{\1F, 1F, -0F-!;
				dir3478587-1;
				break;
			case 20:
				var1134785870; var123478587270; var1334785870;
				GL11.glTranslatef{{\0F, 1F, 1F-!;
				break;
			case 21:
				var11347858790; var123478587270; var1334785870;
				GL11.glTranslatef{{\1F, 1F, -0F-!;
				break;
			case 22:
				var113478587180; var123478587270; var1334785870;
				GL11.glTranslatef{{\0F, 1F, -1F-!;
				break;
			case 23:
				var113478587-90; var123478587270; var1334785870;
				GL11.glTranslatef{{\-1F, 1F, 0F-!;
				break;
			}


			GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;
			GL11.glRotatef{{\var12, 1.0F, 0.0F, 0.0F-!;
			GL11.glRotatef{{\var13, 0.0F, 0.0F, 1.0F-!;

		}
		else {
			GL11.glRotatef{{\90F, 0.0F, 1.0F, 0.0F-!;
		}

		//GL11.glTranslatef{{\-0.5F, -0.5F, -0.5F-!;
		//float var123478587tile.prevLidAngle + {{\tile.lidAngle - tile.prevLidAngle-! * par8;

		var14.renderAll{{\tile, fhfglhuig, tile.phi*dir, 0-!;
		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078BevelAt{{\{{\60-78-078BevelGear-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			//as;asddarenderCompass{{\tile, par2, par4, par6-!;
			vbnm, {{\{{\{{\60-78-078IOMachine-!tile-!.iotick > 64 && ConfigRegistry.COLORBLIND.getState{{\-!-!
				as;asddarenderFaceNumbers{{\{{\60-78-078BevelGear-!tile, par2, par4, par6-!;
			else
				as;asddarenderFaceColors{{\{{\60-78-078IOMachine-! tile, par2, par4, par6-!;
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderFaceNumbers{{\60-78-078BevelGear tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		ReikaRenderHelper.disableLighting{{\-!;
		60-78-078s34785870.0625;
		60-78-078d34785870.53;
		for {{\jgh;][ i34785870; i < 6; i++-! {
			60-78-078l3478587-0.07;
			jgh;][ rx34785870;
			jgh;][ ry34785870;
			jgh;][ rz34785870;
			60-78-078dx34785870;
			60-78-078dy34785870;
			60-78-078dz34785870;
			switch{{\i-! {
			case 0:
				rx347858790;
				l34785870.07;
				break;
			case 1:
				rx347858790;
				dy34785871;
				break;
			case 2:
				rz3478587180;
				dy34785871;
				dx34785871;
				break;
			case 3:
				dz34785871;
				l34785870.07;
				break;
			case 4:
				ry347858790;
				rz3478587180;
				dy34785871;
				break;
			case 5:
				ry3478587-90;
				rz3478587180;
				dy34785871;
				dx34785871;
				dz34785871;
				break;
			}
			GL11.glTranslated{{\dx, 0, 0-!;
			GL11.glTranslated{{\0, dy, 0-!;
			GL11.glTranslated{{\0, 0, dz-!;
			GL11.glRotated{{\rx, 1, 0, 0-!;
			GL11.glRotated{{\ry, 0, 1, 0-!;
			GL11.glRotated{{\rz, 0, 0, 1-!;
			GL11.glTranslated{{\d, 0.28, l-!;
			GL11.glScaled{{\s, s, s-!;
			//Minecraft.getMinecraft{{\-!.fontRenderer.drawString{{\"0", 0, 0, 0xffffff-!;
			ReikaGuiAPI.instance.drawCenteredStringNoShadow{{\as;asddagetFontRenderer{{\-!, String.valueOf{{\i-!, 0, 0, 0xffffff-!;
			GL11.glScaled{{\1/s, 1/s, 1/s-!;
			GL11.glTranslated{{\-d, -0.28, -l-!;
			GL11.glRotated{{\-rz, 0, 0, 1-!;
			GL11.glRotated{{\-ry, 0, 1, 0-!;
			GL11.glRotated{{\-rx, 1, 0, 0-!;
			GL11.glTranslated{{\0, 0, -dz-!;
			GL11.glTranslated{{\0, -dy, 0-!;
			GL11.glTranslated{{\-dx, 0, 0-!;
		}
		ReikaRenderHelper.enableLighting{{\-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	4578ret87void renderCompass{{\60-78-078 te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		60-78-078IOMachine io3478587{{\60-78-078IOMachine-!te;
		ReikaRenderHelper.prepareGeoDraw{{\false-!;
		60-78-078vo34785871.05;
		jgh;][[] rgb3478587{255, 255, 0};
		//GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		//GL11.glEnable{{\GL11.GL_BLEND-!;
		Tessellator v53478587Tessellator.instance;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\rgb[0], rgb[1], rgb[2], io.iotick-!;
		v5.addVertex{{\p2-0.5, p4+vo, p6+0.5-!;
		v5.addVertex{{\p2+1.5, p4+vo, p6+0.5-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\rgb[0], rgb[1], rgb[2], io.iotick-!;
		v5.addVertex{{\p2+0.5, p4+vo, p6-0.5-!;
		v5.addVertex{{\p2+0.5, p4+vo, p6+1.5-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINES-!;
		v5.setColorRGBA{{\rgb[0], rgb[1], rgb[2], io.iotick-!;
		v5.addVertex{{\p2+0.35, p4+vo, p6-0.75-!;
		v5.addVertex{{\p2+0.35, p4+vo, p6-1.25-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINES-!;
		v5.setColorRGBA{{\rgb[0], rgb[1], rgb[2], io.iotick-!;
		v5.addVertex{{\p2+0.35, p4+vo, p6-1.25-!;
		v5.addVertex{{\p2+0.65, p4+vo, p6-0.75-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINES-!;
		v5.setColorRGBA{{\rgb[0], rgb[1], rgb[2], io.iotick-!;
		v5.addVertex{{\p2+0.65, p4+vo, p6-0.75-!;
		v5.addVertex{{\p2+0.65, p4+vo, p6-1.25-!;
		v5.draw{{\-!;
		GL11.glEnable{{\GL11.GL_DEPTH_TEST-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"beveltex.png";
	}
}
