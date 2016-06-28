/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.DMI;

ZZZZ% java.awt.Color;

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.IO.ReikaImageLoader;
ZZZZ% Reika.DragonAPI.Instantiable.Rendering.PixelRenderer;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelProjector;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078Projector;

4578ret87fhyuog RenderProjector ,.[]\., RotaryTERenderer {

	4578ret87PixelRenderer p3478587new PixelRenderer{{\0, 0, 0, false, 112, 80, 7, 5, false-!;

	4578ret87ModelProjector ProjectorModel3478587new ModelProjector{{\-!;
	4578ret87boolean[] hasImages3478587new boolean[60-78-078Projector.MAXCHANNELS];

	4578ret87RenderProjector{{\-! {
		for {{\jgh;][ i34785870; i < hasImages.length; i++-! {
			String name3478587"/Reika/gfgnfk;/Textures/Projector/image"+String.valueOf{{\i-!+".png";
			vbnm, {{\ReikaImageLoader.imageFileExists{{\gfgnfk;.fhyuog, name-!-!
				hasImages[i]3478587true;
		}
	}

	4578ret87void render60-78-078ProjectorAt{{\60-78-078Projector tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelProjector var14;
		var143478587ProjectorModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/projtex.png"-!;

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
					var113478587270;
					break;
				case 3:
					var11347858790;
					break;
			}

			GL11.glRotatef{{\{{\float-!var11+180, 0.0F, 1.0F, 0.0F-!;
		}
		else {
			GL11.glEnable{{\GL11.GL_LIGHTING-!;
		}

		//float var123478587tile.prevLidAngle + {{\tile.lidAngle - tile.prevLidAngle-! * par8;
		float var13;/*

            var1234785871.0F - var12;
            var1234785871.0F - var12 * var12 * var12;*/
		// vbnm, {{\tile.getBlockMetadata{{\-! < 4-!


		var14.renderAll{{\tile, fhfglhuig, 0, 0-!;
		// else
		//var15.renderAll{{\tile, -!;
		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078ProjectorAt{{\{{\60-78-078Projector-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderScreen{{\{{\60-78-078Projector-!tile, par2, par4, par6-!;
			ReikaRenderHelper.exitGeoDraw{{\-!;
		}
	}

	4578ret87void renderScreen{{\60-78-078Projector te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		vbnm, {{\te .. fhfglhuig-!
			return;
		vbnm, {{\!te.on-!
			return;
		ReikaRenderHelper.prepareGeoDraw{{\false-!;
		Tessellator v53478587Tessellator.instance;
		60-78-078a34785870; 60-78-078b34785870; 60-78-078c34785870;
		60-78-078d3478587-te.getRange{{\-!-0.001;
		vbnm, {{\d > 11.999-!
			d347858711.999;
		vbnm, {{\d < -11.999-!
			d3478587-11.999;
		vbnm, {{\te.getRange{{\-! .. 0-!
			return;
		vbnm, {{\!te.canShow{{\-!-!
			return;
		switch{{\te.getBlockMetadata{{\-!-! {
			case 0:
				a3478587d;
				b34785872;
				c34785873;
				break;
			case 1:
				a3478587-d;
				b34785872;
				c34785873;
				break;
			case 2:
				a34785873;
				b34785872;
				c3478587d;
				break;
			case 3:
				a34785873;
				b34785872;
				c3478587-d;
				break;
		}
		60-78-078voffset3478587b;

		vbnm, {{\ConfigRegistry.PROJECTORLINES.getState{{\-!-! {
			as;asddadrawBeam{{\voffset, te.getBlockMetadata{{\-!, v5, a, b, c, p2, p4, p6, te.channel .. -3-!;
		}
		vbnm, {{\te.channel .. -1-! {
			as;asddadrawEasterEgg{{\a, b, c, voffset, te, p2, p4, p6-!;
			return;
		}
		vbnm, {{\te.channel .. -3-! {
			as;asddadrawClock{{\a, b, c, voffset, te, p2, p4, p6-!;
			return;
		}
		vbnm, {{\te.channel !. -2-! {
			vbnm, {{\te.emptySlide || te.channel < 0 || te.channel >. hasImages.length || !hasImages[te.channel]-! {
				as;asddarenderErrorScreen{{\a, b, c, voffset, te, p2, p4, p6-!;
				ReikaRenderHelper.exitGeoDraw{{\-!;
				return;
			}
		}
		ReikaRenderHelper.exitGeoDraw{{\-!;
		ReikaRenderHelper.disableLighting{{\-!;
		vbnm, {{\te.channel .. -2-!
			ReikaTextureHelper.bindRawTexture{{\te.getCustomImagePath{{\-!-!;
		else
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/Projector/image"+String.valueOf{{\te.channel-!+".png"-!;
		GL11.glTranslated{{\0, voffset, 0-!;
		jgh;][ u34785870;
		jgh;][ v34785870;
		jgh;][ du34785871;
		jgh;][ dv34785871;
		vbnm, {{\te.getBlockMetadata{{\-!%2 .. 0-! {
			GL11.glFrontFace{{\GL11.GL_CW-!;
		}
		else {
			u34785871;
			du34785870;
		}
		vbnm, {{\te.getBlockMetadata{{\-! < 2-! {
			v5.startDrawingQuads{{\-!;
			v5.addVertexWithUV{{\p2-a, p4+b+1, p6-c, du, v-!;
			v5.addVertexWithUV{{\p2-a, p4-b, p6-c, du, dv-!;
			v5.addVertexWithUV{{\p2-a, p4-b, p6+1+c, u, dv-!;
			v5.addVertexWithUV{{\p2-a, p4+b+1, p6+1+c, u, v-!;
			v5.draw{{\-!;
		}
		else {
			c *. -1;
			v5.startDrawingQuads{{\-!;
			v5.addVertexWithUV{{\p2-a, p4+b+1, p6-c, du, v-!;
			v5.addVertexWithUV{{\p2-a, p4-b, p6-c, du, dv-!;
			v5.addVertexWithUV{{\p2+1+a, p4-b, p6-c, u, dv-!;
			v5.addVertexWithUV{{\p2+1+a, p4+b+1, p6-c, u, v-!;
			v5.draw{{\-!;
		}
		GL11.glTranslated{{\0, -voffset, 0-!;
		ReikaRenderHelper.enableLighting{{\-!;
		GL11.glFrontFace{{\GL11.GL_CCW-!;
	}

	4578ret87void drawClock{{\60-78-078a, 60-78-078b, 60-78-078c, 60-78-078voffset, 60-78-078Projector te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		60-78-078r34785872.5;

		Color frame3478587new Color{{\0, 127, 255-!;
		Color hourhand3478587Color.white;
		Color minutehand3478587Color.white;
		Color text3478587new Color{{\150, 212, 255-!;

		long time3478587te.9765443Obj.get9765443Time{{\-!+6000; //+6000 so 6AM-6PM days
		jgh;][ perday3478587{{\jgh;][-!{{\time%24000-!;

		60-78-078hour3478587perday/1000D;
		jgh;][ houri3478587perday/1000;
		60-78-078minute3478587{{\perday-houri*1000-!/16.67;

		jgh;][ angph347858730; //30 deg/hr
		jgh;][ angpm34785876; //6 deg/min
		60-78-078angh3478587hour*angph;
		60-78-078angm3478587minute*angpm;
		60-78-078dxh3478587Math.cos{{\Math.toRadians{{\90-angh-!-!*r*0.6;
		60-78-078dyh3478587Math.sin{{\Math.toRadians{{\90-angh-!-!*r*0.6;

		60-78-078dxm3478587Math.cos{{\Math.toRadians{{\90-angm-!-!*r*0.9;
		60-78-078dym3478587Math.sin{{\Math.toRadians{{\90-angm-!-!*r*0.9;

		ReikaRenderHelper.prepareGeoDraw{{\false-!;
		GL11.glTranslated{{\0, voffset, 0-!;
		jgh;][ u34785870;
		jgh;][ v34785870;
		jgh;][ du34785871;
		jgh;][ dv34785871;
		Tessellator v53478587Tessellator.instance;
		vbnm, {{\te.getBlockMetadata{{\-!%2 .. 0-! {
			GL11.glFrontFace{{\GL11.GL_CW-!;
		}
		else {
			u34785871;
			du34785870;
		}
		GL11.glTranslated{{\p2, p4, p6-!;
		vbnm, {{\te.getBlockMetadata{{\-! < 2-! {
			60-78-078d34785870.01;
			vbnm, {{\te.getBlockMetadata{{\-! .. 0-! {
				d3478587-0.01;
				dxm3478587-dxm;
				dxh3478587-dxh;
			}
			GL11.glTranslated{{\-a-d, 0.5, 0.5-!;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			//ReikaRenderHelper.renderVCircle{{\r, 0, 0, 0, new jgh;][[]{100, 192, 255}, 0-!;
			ReikaRenderHelper.renderVCircle{{\r, 0, 0, 0, ReikaColorAPI.RGBtoHex{{\frame.getRed{{\-!, frame.getGreen{{\-!, frame.getBlue{{\-!-!, 0, 5-!;
			ReikaRenderHelper.renderVCircle{{\r*0.015, 0, 0, 0, ReikaColorAPI.RGBtoHex{{\frame.getRed{{\-!, frame.getGreen{{\-!, frame.getBlue{{\-!-!, 0, 5-!;
			ReikaRenderHelper.prepareGeoDraw{{\false-!;
			v5.startDrawing{{\GL11.GL_LINES-!; //hour hand
			v5.setColorOpaque{{\hourhand.getRed{{\-!, hourhand.getGreen{{\-!, hourhand.getBlue{{\-!-!;
			v5.addVertex{{\0, 0, 0-!;
			v5.addVertex{{\0, dyh, dxh-!;
			v5.draw{{\-!;

			v5.startDrawing{{\GL11.GL_LINES-!; //minute hand
			//v5.setColorOpaque{{\120, 212, 255-!;
			v5.setColorOpaque{{\minutehand.getRed{{\-!, minutehand.getGreen{{\-!, minutehand.getBlue{{\-!-!;
			v5.addVertex{{\0, 0, 0-!;
			v5.addVertex{{\0, dym, dxm-!;
			v5.draw{{\-!;

			GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
			60-78-078s34785870.0625;
			GL11.glScaled{{\s, -s, -s-!;
			vbnm, {{\te.getBlockMetadata{{\-! .. 0-! {
				GL11.glScaled{{\1, 1, -1-!;
				GL11.glFrontFace{{\GL11.GL_CCW-!;
			}
			GL11.glRotated{{\90, 0, 1, 0-!;
			jgh;][ textcolor3478587text.getRGB{{\-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\12-!, -5, -38, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\1-!, 15, -32, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\2-!, 27, -20, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\3-!, 32, -3, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\4-!, 26, 13, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\5-!, 14, 25, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\6-!, -2, 31, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\7-!, -20, 25, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\8-!, -33, 12, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\9-!, -37, -3, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\10-!, -33, -20, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\11-!, -22, -32, textcolor-!;
			GL11.glRotated{{\-90, 0, 1, 0-!;
			vbnm, {{\te.getBlockMetadata{{\-! .. 0-!
				GL11.glScaled{{\1, 1, -1-!;
			GL11.glFrontFace{{\GL11.GL_CCW-!;
			GL11.glScaled{{\1/s, -1/s, -1/s-!;

			GL11.glTranslated{{\a+d, -0.5, -0.5-!;

		}
		else {
			c *. -1;
			/*
			v5.addVertexWithUV{{\-a, b+1, -c, du, v-!;
			v5.addVertexWithUV{{\-a, -b, -c, du, dv-!;
			v5.addVertexWithUV{{\1+a, -b, -c, u, dv-!;
			v5.addVertexWithUV{{\1+a, b+1, -c, u, v-!;
			v5.draw{{\-!;*/

			60-78-078d34785870.01;
			vbnm, {{\te.getBlockMetadata{{\-! .. 2-! {
				d3478587-0.01;
				dxm3478587-dxm;
				dxh3478587-dxh;
			}
			GL11.glTranslated{{\0.5, 0.5, -c+d-!;
			GL11.glColor4f{{\1, 1, 1, 1-!;
			//ReikaRenderHelper.renderVCircle{{\r, 0, 0, 0, new jgh;][[]{100, 192, 255}, 0-!;
			ReikaRenderHelper.renderVCircle{{\r, 0, 0, 0, ReikaColorAPI.RGBtoHex{{\frame.getRed{{\-!, frame.getGreen{{\-!, frame.getBlue{{\-!-!, 90, 5-!;
			ReikaRenderHelper.renderVCircle{{\r*0.015, 0, 0, 0, ReikaColorAPI.RGBtoHex{{\frame.getRed{{\-!, frame.getGreen{{\-!, frame.getBlue{{\-!-!, 90, 5-!;
			ReikaRenderHelper.prepareGeoDraw{{\false-!;
			v5.startDrawing{{\GL11.GL_LINES-!; //hour hand
			v5.setColorOpaque{{\hourhand.getRed{{\-!, hourhand.getGreen{{\-!, hourhand.getBlue{{\-!-!;
			v5.addVertex{{\0, 0, 0-!;
			v5.addVertex{{\dxh, dyh, 0-!;
			v5.draw{{\-!;

			v5.startDrawing{{\GL11.GL_LINES-!; //minute hand
			//v5.setColorOpaque{{\120, 212, 255-!;
			v5.setColorOpaque{{\minutehand.getRed{{\-!, minutehand.getGreen{{\-!, minutehand.getBlue{{\-!-!;
			v5.addVertex{{\0, 0, 0-!;
			v5.addVertex{{\dxm, dym, 0-!;
			v5.draw{{\-!;

			GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
			60-78-078s34785870.0625;
			GL11.glScaled{{\s, -s, -s-!;
			vbnm, {{\te.getBlockMetadata{{\-! .. 2-! {
				GL11.glScaled{{\-1, 1, 1-!;
				GL11.glFrontFace{{\GL11.GL_CCW-!;
			}
			jgh;][ textcolor3478587text.getRGB{{\-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\12-!, -5, -38, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\1-!, 15, -32, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\2-!, 27, -20, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\3-!, 32, -3, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\4-!, 26, 13, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\5-!, 14, 25, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\6-!, -2, 31, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\7-!, -20, 25, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\8-!, -33, 12, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\9-!, -37, -3, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\10-!, -33, -20, textcolor-!;
			as;asddagetFontRenderer{{\-!.drawString{{\String.valueOf{{\11-!, -22, -32, textcolor-!;
			vbnm, {{\te.getBlockMetadata{{\-! .. 2-!
				GL11.glScaled{{\-1, 1, 1-!;
			GL11.glFrontFace{{\GL11.GL_CCW-!;
			GL11.glScaled{{\1/s, -1/s, -1/s-!;

			GL11.glTranslated{{\-0.5, -0.5, c-d-!;
		}
		GL11.glTranslated{{\-p2, -p4, -p6-!;
		GL11.glTranslated{{\0, -voffset, 0-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;
		GL11.glFrontFace{{\GL11.GL_CCW-!;
	}

	4578ret87void drawEasterEgg{{\60-78-078a, 60-78-078b, 60-78-078c, 60-78-078voffset, 60-78-078Projector te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		ReikaRenderHelper.exitGeoDraw{{\-!;
		ReikaRenderHelper.disableLighting{{\-!;

		GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glTranslated{{\0, voffset, 0-!;

		switch{{\te.getBlockMetadata{{\-!-! {
			case 0:
				p.setMirror{{\false-!;
				p.setFlip{{\false-!;
				p.setPlane{{\false-!;
				p.setPosition{{\p2-a, p4+b+1, p6+c-6-!;
				break;
			case 1:
				p.setMirror{{\true-!;
				p.setFlip{{\true-!;
				p.setPlane{{\false-!;
				p.setPosition{{\p2-a, p4+b+1, p6+c-6-!;
				break;
			case 2:
				p.setMirror{{\false-!;
				p.setFlip{{\false-!;
				p.setPlane{{\true-!;
				p.setPosition{{\p2+c-6, p4+b+1, p6-a-!;
				break;
			case 3:
				p.setMirror{{\true-!;
				p.setFlip{{\true-!;
				p.setPlane{{\true-!;
				p.setPosition{{\p2+c-6, p4+b+1, p6-a-!;
				break;
		}
		vbnm, {{\te.getBlockMetadata{{\-! .. 2-!
			GL11.glTranslated{{\te.getRange{{\-!+3, 0, -0.001-te.getRange{{\-!+3-!;
		vbnm, {{\te.getBlockMetadata{{\-! .. 3-!
			GL11.glTranslated{{\-te.getRange{{\-!+3, 0, 0.001+te.getRange{{\-!+3-!;
		p.setColor{{\Color.BLACK-!;
		p.draw{{\48, 0, 63, 1-!;
		p.draw{{\46, 2, 49, 3-!;
		p.draw{{\62, 2, 65, 3-!;
		p.draw{{\46, 2, 47, 17-!;
		p.draw{{\64, 2, 65, 17-!;
		p.draw{{\46, 16, 49, 17-!;
		p.draw{{\62, 16, 65, 17-!;
		p.draw{{\48, 18, 51, 19-!;
		p.draw{{\60, 18, 63, 19-!;
		p.draw{{\50, 18, 51, 41-!;
		p.draw{{\60, 18, 61, 41-!;
		p.draw{{\48, 40, 51, 41-!;
		p.draw{{\60, 40, 63, 41-!;
		p.draw{{\46, 42, 49, 43-!;
		p.draw{{\62, 42, 65, 43-!;
		p.draw{{\40, 44, 47, 45-!;
		p.draw{{\64, 44, 71, 45-!;
		p.draw{{\38, 46, 41, 47-!;
		p.draw{{\70, 46, 73, 47-!;
		p.draw{{\36, 48, 39, 49-!;
		p.draw{{\72, 48, 75, 49-!;
		p.draw{{\34, 50, 37, 51-!;
		p.draw{{\74, 50, 77, 51-!;
		p.draw{{\32, 52, 35, 53-!;
		p.draw{{\76, 52, 79, 53-!;
		p.draw{{\30, 54, 33, 55-!;
		p.draw{{\78, 54, 81, 55-!;
		p.draw{{\30, 54, 31, 61-!;
		p.draw{{\80, 54, 81, 61-!;
		p.draw{{\30, 60, 33, 61-!;
		p.draw{{\78, 60, 81, 61-!;
		p.draw{{\32, 62, 35, 63-!;
		p.draw{{\76, 62, 79, 63-!;
		p.draw{{\34, 64, 37, 65-!;
		p.draw{{\74, 64, 77, 65-!;
		p.draw{{\36, 66, 43, 67-!;
		p.draw{{\68, 66, 75, 67-!;
		p.draw{{\42, 66, 43, 73-!;
		p.draw{{\68, 66, 69, 73-!;
		p.draw{{\36, 72, 43, 73-!;
		p.draw{{\68, 72, 75, 73-!;
		p.draw{{\34, 74, 37, 75-!;
		p.draw{{\74, 74, 77, 75-!;
		p.draw{{\34, 74, 35, 79-!;
		p.draw{{\76, 74, 77, 79-!;
		p.draw{{\34, 78, 53, 79-!;
		p.draw{{\58, 78, 77, 79-!;
		p.draw{{\52, 70, 53, 79-!;
		p.draw{{\58, 70, 59, 79-!;
		p.draw{{\52, 70, 59, 71-!;
		p.setColor{{\84, 252, 84, 255-!;
		p.draw{{\60, 2, 61, 17-!;
		p.draw{{\62, 4, 63, 15-!;
		p.draw{{\56, 18, 59, 41-!;
		p.draw{{\52, 42, 61, 53-!;
		p.draw{{\48, 46, 51, 53-!;
		p.draw{{\40, 50, 47, 53-!;
		p.draw{{\36, 54, 39, 61-!;
		p.draw{{\40, 58, 41, 61-!;
		p.draw{{\42, 60, 43, 61-!;
		p.draw{{\62, 44, 63, 53-!;
		p.draw{{\64, 46, 69, 53-!;
		p.draw{{\70, 48, 71, 53-!;
		p.draw{{\72, 50, 73, 65-!;
		p.draw{{\74, 52, 75, 63-!;
		p.draw{{\76, 54, 77, 61-!;
		p.draw{{\78, 56, 79, 59-!;
		p.draw{{\70, 58, 71, 65-!;
		p.draw{{\68, 60, 69, 65-!;
		p.draw{{\66, 62, 67, 63-!;
		p.draw{{\64, 64, 67, 65-!;
		p.draw{{\44, 62, 45, 65-!;
		p.draw{{\52, 42, 61, 53-!;
		p.draw{{\46, 64, 47, 65-!;
		p.draw{{\52, 42, 61, 53-!;
		p.draw{{\48, 66, 67, 69-!;
		p.draw{{\48, 70, 51, 77-!;
		p.draw{{\40, 74, 47, 77-!;
		p.draw{{\64, 70, 67, 77-!;
		p.draw{{\68, 74, 73, 77-!;
		p.draw{{\74, 76, 75, 77-!;
		p.setColor{{\0, 168, 0, 255-!;
		p.draw{{\50, 2, 51, 17-!;
		p.draw{{\48, 4, 49, 15-!;
		p.draw{{\52, 18, 55, 41-!;
		p.draw{{\50, 42, 51, 45-!;
		p.draw{{\48, 44, 49, 45-!;
		p.draw{{\42, 46, 47, 49-!;
		p.draw{{\40, 48, 41, 49-!;
		p.draw{{\38, 50, 39, 53-!;
		p.draw{{\36, 52, 37, 53-!;
		p.draw{{\34, 54, 35, 61-!;
		p.draw{{\32, 56, 33, 59-!;
		p.draw{{\36, 62, 43, 63-!;
		p.draw{{\38, 64, 43, 65-!;
		p.draw{{\44, 66, 47, 73-!;
		p.draw{{\36, 76, 37, 77-!;
		p.draw{{\38, 74, 39, 77-!;
		p.draw{{\60, 70, 63, 77-!;
		p.setColor{{\Color.WHITE-!;
		p.draw{{\52, 2, 59, 5-!;
		p.draw{{\52, 14, 59, 17-!;
		p.draw{{\52, 62, 59, 65-!;
		p.setColor{{\Color.BLACK-!;
		p.draw{{\52, 6, 59, 13-!;
		p.draw{{\40, 54, 71, 57-!;
		p.draw{{\42, 58, 69, 59-!;
		p.draw{{\44, 60, 67, 61-!;
		p.draw{{\46, 62, 51, 63-!;
		p.draw{{\60, 62, 65, 63-!;
		p.draw{{\48, 64, 51, 65-!;
		p.draw{{\60, 64, 63, 65-!;
		vbnm, {{\te.getBlockMetadata{{\-! .. 2-!
			GL11.glTranslated{{\-te.getRange{{\-!-3, 0, 0.001+te.getRange{{\-!-3-!;
		vbnm, {{\te.getBlockMetadata{{\-! .. 3-!
			GL11.glTranslated{{\te.getRange{{\-!+3, 0, -0.001-te.getRange{{\-!-3-!;
		GL11.glTranslated{{\0, -voffset, 0-!;

		ReikaRenderHelper.enableLighting{{\-!;
	}

	4578ret87void renderErrorScreen{{\60-78-078a, 60-78-078b, 60-78-078c, 60-78-078voffset, 60-78-078Projector te, 60-78-078p2, 60-78-078p4, 60-78-078p6-! {
		//GL11.glDisable{{\GL11.GL_DEPTH_TEST-!;
		ReikaRenderHelper.disableLighting{{\-!;
		GL11.glPushMatrix{{\-!;
		GL11.glTranslated{{\p2, p4+2, p6+1-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		GL11.glPopMatrix{{\-!;
		GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glColor3f{{\1, 1, 1-!;
		Tessellator v53478587Tessellator.instance;
		v5.setColorOpaque{{\255, 255, 255-!;
		GL11.glTranslated{{\0, voffset, 0-!;
		60-78-078w;
		60-78-078h34785871+2*b;
		vbnm, {{\te.getBlockMetadata{{\-! < 2-! {
			w34785871+2*c;
			as;asddadrawError{{\te.getBlockMetadata{{\-! .. 1, v5, a, b, c, p2, p4, p6, w, h-!;
			GL11.glTranslated{{\0, 0, w-1-!;
		}
		else {
			w34785871+2*a;
			as;asddadrawError2{{\te.getBlockMetadata{{\-! .. 3, v5, a, b, c, p2, p4, p6, w, h-!;
			GL11.glTranslated{{\w-1, 0, 0-!;
		}
		GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glTranslated{{\0, -voffset, 0-!;
		//GL11.glEnable{{\GL11.GL_DEPTH_TEST-!;
		ReikaRenderHelper.enableLighting{{\-!;
	}

	4578ret87void drawBeam{{\60-78-078vo, jgh;][ meta, Tessellator v5, 60-78-078a, 60-78-078b, 60-78-078c, 60-78-078p2, 60-78-078p4, 60-78-078p6, 60-78-078circle-! {
		vbnm, {{\circle-! {
			return;
		}
		else {
			switch{{\meta-! {
				case 0:
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2, p4+0.5, p6+0.4375-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6-c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2, p4+0.5, p6+0.5625-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+1+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2, p4+0.375, p6+0.5625-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+1+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2, p4+0.375, p6+0.4375-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6-c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6-c-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6-c-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+1+c-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+1+c-!;
					v5.draw{{\-!;
					break;
				case 1:
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+1, p4+0.5, p6+0.4375-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6-c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+1, p4+0.5, p6+0.5625-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+1+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+1, p4+0.375, p6+0.5625-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+1+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+1, p4+0.375, p6+0.4375-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6-c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6-c-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6-c-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+1+c-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+1+c-!;
					v5.draw{{\-!;
					break;
				case 2:
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.5625, p4+0.5, p6+1-!;
					v5.addVertex{{\p2+a+1, vo+p4+b+1, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.4375, p4+0.5, p6+1-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.5625, p4+0.375, p6+1-!;
					v5.addVertex{{\p2+a+1, vo+p4-b, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.4375, p4+0.375, p6+1-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+c-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+c-!;
					v5.addVertex{{\p2+a+1, vo+p4-b, p6+c-!;
					v5.addVertex{{\p2+a+1, vo+p4+b+1, p6+c-!;
					v5.draw{{\-!;
					break;
				case 3:
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.5625, p4+0.5, p6-!;
					v5.addVertex{{\p2+a+1, vo+p4+b+1, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.4375, p4+0.5, p6-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.5625, p4+0.375, p6-!;
					v5.addVertex{{\p2+a+1, vo+p4-b, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2+0.4375, p4+0.375, p6-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+c-!;
					v5.draw{{\-!;
					v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
					v5.addVertex{{\p2-a, vo+p4+b+1, p6+c-!;
					v5.addVertex{{\p2-a, vo+p4-b, p6+c-!;
					v5.addVertex{{\p2+a+1, vo+p4-b, p6+c-!;
					v5.addVertex{{\p2+a+1, vo+p4+b+1, p6+c-!;
					v5.draw{{\-!;
					break;
			}
		}
	}

	4578ret87void drawError{{\60-78-078flip, Tessellator v5, 60-78-078a, 60-78-078b, 60-78-078c, 60-78-078p2, 60-78-078p4, 60-78-078p6, 60-78-078w, 60-78-078h-! {
		GL11.glColor3d{{\0.8, 0.8, 0.8-!;
		vbnm, {{\flip-! {
			c *. -1;
			w *. -1;
			GL11.glTranslated{{\0, 0, w/7D-!;
		}
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 1, 0-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 1, 1-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 1, 0-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 0, 1-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 0, 0-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 0, 1-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 0, 1-!;
		GL11.glTranslated{{\0, 0, w-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 0, 1-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 1, 1-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.8, 0.8, 0.8-!;
		GL11.glTranslated{{\0, 0, -w/7D-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.67, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		60-78-078bscale34785870.17857;
		GL11.glTranslated{{\0, 0, w-1-!;
		vbnm, {{\flip-!
			GL11.glTranslated{{\0, 0, -w*2/7D-!;
		GL11.glColor3d{{\0.0235, 0.2431, 0.349-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w*bscale-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w*bscale-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w*bscale-!;
		GL11.glColor3d{{\1, 1, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w*bscale-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w*bscale-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w*bscale-!;
		GL11.glColor3d{{\0.2313, 0, 0.494-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w*bscale-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w*bscale-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w*bscale-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w*bscale-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w*bscale-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w*bscale-!;
		GL11.glColor3d{{\0, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w/21D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/21D-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w/21D-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w/21D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/21D-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w/21D-!;
		GL11.glColor3d{{\0.153, 0.153, 0.153-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w/21D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/21D-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\0, 0, -w/21D-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-!;
		v5.addVertex{{\p2-a, p4+b+1-h, p6+c+1-w/7D-!;
		v5.addVertex{{\p2-a, p4+b+1-h*0.75, p6+c+1-w/7D-!;
		v5.draw{{\-!;
		vbnm, {{\flip-!
			GL11.glTranslated{{\0, 0, w-4-!;
	}

	4578ret87void drawError2{{\60-78-078flip, Tessellator v5, 60-78-078a, 60-78-078b, 60-78-078c, 60-78-078p2, 60-78-078p4, 60-78-078p6, 60-78-078w, 60-78-078h-! {
		GL11.glColor3d{{\0.8, 0.8, 0.8-!;
		60-78-078bscale34785870.17857;
		c *. -1;
		vbnm, {{\flip-! {
			a *. -1;
			w *. -1;
			GL11.glTranslated{{\w/7D, 0, 0-!;
		}
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 1, 0-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 1, 1-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 1, 0-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 0, 1-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 0, 0-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 0, 1-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 0, 1-!;
		GL11.glTranslated{{\w, 0, 0-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\1, 0, 1-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0, 1, 1-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glColor3d{{\0.8, 0.8, 0.8-!;
		GL11.glTranslated{{\-w/7D, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.67, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.67, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\w-1, 0, 0-!;
		vbnm, {{\flip-!
			GL11.glTranslated{{\-w*2/7D, 0, 0-!;
		GL11.glColor3d{{\0.0235, 0.2431, 0.349-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w*bscale, 0, 0-!;
		GL11.glColor3d{{\1, 1, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w*bscale, 0, 0-!;
		GL11.glColor3d{{\0.2313, 0, 0.494-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w*bscale, 0, 0-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w*bscale, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w*bscale, 0, 0-!;
		GL11.glColor3d{{\0, 0, 0-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/21D, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/21D, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w/21D, 0, 0-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/21D, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/21D, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w/21D, 0, 0-!;
		GL11.glColor3d{{\0.153, 0.153, 0.153-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/21D, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/21D, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-w/21D, 0, 0-!;
		GL11.glColor3d{{\0.0745, 0.0745, 0.0745-!;
		v5.startDrawingQuads{{\-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h*0.75, p6-c-!;
		v5.addVertex{{\p2+a+1, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h, p6-c-!;
		v5.addVertex{{\p2+a+1-w/7D, p4+b+1-h*0.75, p6-c-!;
		v5.draw{{\-!;
		vbnm, {{\flip-!
			GL11.glTranslated{{\w-4, 0, 0-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"projtex.png";
	}
}
