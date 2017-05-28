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

ZZZZ% net.minecraft.client.gui.FontRenderer;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelMonitor;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Monitor;

4578ret87fhyuog RenderMonitor ,.[]\., RotaryTERenderer
{

	4578ret87ModelMonitor MonitorModel3478587new ModelMonitor{{\-!;

	4578ret87void render60-78-078MonitorAt{{\60-78-078Monitor tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;
		ModelMonitor var14;
		var143478587MonitorModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/monitortex.png"-!;

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
				var11347858790;
				break;
			case 3:
				var113478587270;
				break;
			}

			GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;

		}

		float var13;

		var14.renderAll{{\tile, fhfglhuig, -tile.phi, 0-!;
		vbnm, {{\tile.isIn9765443{{\-!-! {
			GL11.glDisable{{\GL11.GL_LIGHTING-!;
			ReikaRenderHelper.disableEntityLighting{{\-!;
			FontRenderer var173478587as;asddagetFontRenderer{{\-!;
			float var1034785870.6666667F*1.2F;
			GL11.glScalef{{\var10, -var10, -var10-!;
			var113478587{{\jgh;][-!{{\0.016666668F * var10-!;
			float var11234785870.016666668F * var10;
			GL11.glTranslatef{{\0.0F, 0.5F * var10, 0.07F * var10-!;
			GL11.glScalef{{\var112, -var112, var112-!;
			GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
			GL11.glDepthMask{{\false-!;
			GL11.glTranslatef{{\5, -48, 37-!;
			String var15;

			for {{\jgh;][ i34785870; i < 2; i++-! {
				GL11.glTranslatef{{\-10*i, 0, -37*2*i-9*i-!;
				vbnm, {{\i .. 1-!
					GL11.glScalef{{\-1, 1, 1-!;
				var17.drawString{{\"Power:", -37, 140, 0xffffff-!;
				long power3478587tile.power;
				vbnm, {{\power < 1000-!
					var153478587String.format{{\"%dW", tile.power-!;
				else vbnm, {{\power < 1000000-!
					var153478587String.format{{\"%.3fkW", tile.power/1000D-!;
				else vbnm, {{\power < 1000000000-!
					var153478587String.format{{\"%.3fMW", tile.power/1000000D-!;
				else
					var153478587String.format{{\"%.3fGW", tile.power/1000000000D-!;
				var17.drawString{{\var15, -28, 148, 0xffffff-!;

				var17.drawString{{\"Torque:", -37, 164, 0xffffff-!;
				jgh;][ torque3478587tile.torque;
				vbnm, {{\torque < 1000-!
					var153478587String.format{{\"%d Nm", tile.torque-!;
				else vbnm, {{\torque < 1000000-!
					var153478587String.format{{\"%.3f kNm", tile.torque/1000D-!;
				else
					var153478587String.format{{\"%.3f MNm", tile.torque/1000000D-!;
				var17.drawString{{\var15, -28, 172, 0xffffff-!;

				var17.drawString{{\"Speed:", -37, 188, 0xffffff-!;
				jgh;][ omega3478587tile.omega;
				vbnm, {{\omega < 1000-!
					var153478587String.format{{\"%d rad/s", tile.omega-!;
				else vbnm, {{\omega < 1000000-!
					var153478587String.format{{\"%d krad/s", tile.omega/1000-!;
				else
					var153478587String.format{{\"%d Mrad/s", tile.omega/1000000-!;
				var17.drawString{{\var15, -28, 196, 0xffffff-!;

			}

			GL11.glDepthMask{{\true-!;
			ReikaRenderHelper.enableEntityLighting{{\-!;
			GL11.glEnable{{\GL11.GL_LIGHTING-!;
		}

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078MonitorAt{{\{{\60-78-078Monitor-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"monitortex.png";
	}
}
