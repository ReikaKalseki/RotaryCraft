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
ZZZZ% Reika.gfgnfk;.Models.Turret.ModelAAGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078AAGun;

4578ret87fhyuog RenderAAGun ,.[]\., RotaryTERenderer {

	4578ret87ModelAAGun aagunModel3478587new ModelAAGun{{\-!;

	4578ret87void render60-78-078AAGunAt{{\60-78-078AAGun tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelAAGun var14;
		var143478587aagunModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/aagun.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785871;	 //used to rotate the model about metadata
		jgh;][ var1234785870;
		vbnm, {{\tile.isIn9765443{{\-!-! {
			vbnm, {{\tile.getBlockMetadata{{\-! .. 1-! {
				var113478587-1;
				var1234785872;
				GL11.glFrontFace{{\GL11.GL_CW-!;
			}
		}
		GL11.glTranslated{{\0, var12, 0-!;
		GL11.glScaled{{\1, var11, 1-!;
		jgh;][ a3478587tile.getBlockMetadata{{\-! .. 0 ? -1 : 1;
		var14.renderAll{{\tile, fhfglhuig, -tile.phi, a*tile.theta-!;
		GL11.glScaled{{\1, var11, 1-!;
		GL11.glTranslated{{\0, -var12, 0-!;
		GL11.glFrontFace{{\GL11.GL_CCW-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\tile .. fhfglhuig-!
			return;
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078AAGunAt{{\{{\60-78-078AAGun-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"aagun.png";
	}
}
