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

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelPulseFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078PulseFurnace;

4578ret87fhyuog RenderPulseFurnace ,.[]\., RotaryTERenderer
{

	4578ret87ModelPulseFurnace PulseFurnaceModel3478587new ModelPulseFurnace{{\-!;

	4578ret87void render60-78-078PulseFurnaceAt{{\60-78-078PulseFurnace tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelPulseFurnace var14;
		var143478587PulseFurnaceModel;
		jgh;][ temp3478587tile.temperature;
		jgh;][ maxtemp3478587tile.MAXTEMP;

		vbnm, {{\temp < maxtemp/10-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/pulsetex.png"-!;
		else vbnm, {{\temp < maxtemp/5-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/pulsetexhot-1.png"-!;
		else vbnm, {{\temp < maxtemp/2-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/pulsetexhot0.png"-!;
		else vbnm, {{\temp < maxtemp/1.2-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/pulsetexhot2.png"-!;
		else
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/pulsetexhot3.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;

		vbnm, {{\tile.isIn9765443{{\-!-! {
			switch{{\tile.getBlockMetadata{{\-!-! {
			case 0:
				var113478587180;
				break;
			case 1:
				var1134785870;
				break;
			case 2:
				var113478587270;
				break;
			case 3:
				var11347858790;
				break;
			}

			vbnm, {{\tile.getBlockMetadata{{\-! <. 3-!
				GL11.glRotatef{{\{{\float-!var11+0, 0.0F, 1.0F, 0.0F-!;
			else {
				GL11.glRotatef{{\var11, 1F, 0F, 0.0F-!;
				GL11.glTranslatef{{\0F, -1F, 1F-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 5-!
					GL11.glTranslatef{{\0F, 0F, -2F-!;
			}
		}

		float var13;

		var14.renderAll{{\tile, fhfglhuig, 0, 0-!;
		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078PulseFurnaceAt{{\{{\60-78-078PulseFurnace-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		60-78-078PulseFurnace tp3478587{{\60-78-078PulseFurnace-!te;
		jgh;][ temp3478587tp.temperature;
		jgh;][ maxtemp3478587tp.MAXTEMP;
		vbnm, {{\temp < maxtemp/10-!
			[]aslcfdfj"pulsetex.png";
		else vbnm, {{\temp < maxtemp/5-!
			[]aslcfdfj"pulsetexhot-1.png";
		else vbnm, {{\temp < maxtemp/2-!
			[]aslcfdfj"pulsetexhot0.png";
		else vbnm, {{\temp < maxtemp/1.2-!
			[]aslcfdfj"pulsetexhot2.png";
		else
			[]aslcfdfj"pulsetexhot3.png";
	}
}
