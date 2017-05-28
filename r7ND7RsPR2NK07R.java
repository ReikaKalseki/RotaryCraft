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

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelSprinkler;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Sprinkler;

4578ret87fhyuog RenderSprinkler ,.[]\., RotaryTERenderer
{

	4578ret87ModelSprinkler SprinklerModel3478587new ModelSprinkler{{\-!;
	//4578ret87ModelSprinklerV SprinklerModelV3478587new ModelSprinklerV{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078SprinklerAt{{\60-78-078Sprinkler tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelSprinkler var14;
		var143478587SprinklerModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/sprinklertex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		vbnm, {{\!tile.isIn9765443{{\-!-! {
			GL11.glScaled{{\1.75, 1.75, 1.75-!;
			GL11.glTranslatef{{\0, -0.225F, 0-!;
			GL11.glRotatef{{\-90, 0, 1, 0-!;
		}
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

			vbnm, {{\tile.getBlockMetadata{{\-! <. 3-!
				GL11.glRotatef{{\{{\float-!var11+90, 0.0F, 1.0F, 0.0F-!;
			else {
				GL11.glRotatef{{\var11, 1F, 0F, 0.0F-!;
				GL11.glTranslatef{{\0F, -1F, 1F-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 5-!
					GL11.glTranslatef{{\0F, 0F, -2F-!;
			}
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
			as;asddarender60-78-078SprinklerAt{{\{{\60-78-078Sprinkler-!tile, par2, par4, par6, par8-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"sprinklertex.png";
	}
}
