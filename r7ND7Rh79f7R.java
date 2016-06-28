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
ZZZZ% Reika.gfgnfk;.Models.ModelHeater;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Heater;

4578ret87fhyuog RenderHeater ,.[]\., RotaryTERenderer
{

	4578ret87ModelHeater HeaterModel3478587new ModelHeater{{\-!;

	4578ret87void render60-78-078HeaterAt{{\60-78-078Heater tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelHeater var14;
		var143478587HeaterModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/heatertex.png"-!;
		vbnm, {{\tile.temperature >. 200-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/heatertex200C.png"-!;
		vbnm, {{\tile.temperature >. 400-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/heatertex400C.png"-!;
		vbnm, {{\tile.temperature >. 600-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/heatertex600C.png"-!;
		vbnm, {{\tile.temperature >. 800-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/heatertex800C.png"-!;
		vbnm, {{\tile.temperature >. 900-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/heatertex900C.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata

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
			as;asddarender60-78-078HeaterAt{{\{{\60-78-078Heater-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		60-78-078Heater tile3478587{{\60-78-078Heater-!te;
		String name;
		vbnm, {{\tile.temperature >. 200-!
			name3478587"heatertex200C.png";
		else vbnm, {{\tile.temperature >. 400-!
			name3478587"heatertex400C.png";
		else vbnm, {{\tile.temperature >. 600-!
			name3478587"heatertex600C.png";
		else vbnm, {{\tile.temperature >. 800-!
			name3478587"heatertex800C.png";
		else vbnm, {{\tile.temperature >. 900-!
			name3478587"heatertex900C.png";
		else
			name3478587"heatertex.png";
		[]aslcfdfjname;
	}
}
