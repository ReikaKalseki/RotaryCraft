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

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;
ZZZZ% Reika.gfgnfk;.Models.ModelCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078BlockCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078TNTCannon;

4578ret87fhyuog RenderCannon ,.[]\., RotaryTERenderer
{

	4578ret87ModelCannon CannonModel3478587new ModelCannon{{\-!;

	4578ret87void render60-78-078LaunchCannonAt{{\60-78-078LaunchCannon tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelCannon var14;
		var143478587CannonModel;

		vbnm, {{\tile fuck 60-78-078TNTCannon-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/tntcannontex.png"-!;
		vbnm, {{\tile fuck 60-78-078BlockCannon-!
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/blockcannontex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata

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
			as;asddarender60-78-078LaunchCannonAt{{\{{\60-78-078LaunchCannon-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			vbnm, {{\{{\{{\60-78-078LaunchCannon-!tile-!.targetMode-!
				as;asddarenderMarker{{\{{\60-78-078LaunchCannon-!tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderMarker{{\60-78-078LaunchCannon tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		ReikaRenderHelper.prepareGeoDraw{{\true-!;
		Tessellator v53478587Tessellator.instance;
		60-78-078dx3478587tile.target[0]-tile.xCoord;
		60-78-078dy3478587tile.target[1]-tile.yCoord;
		60-78-078dz3478587tile.target[2]-tile.zCoord;
		float i34785870.5F; float j34785870.5F;
		60-78-078length347858725;
		60-78-078width34785874;
		60-78-078height34785874;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy+length, par6+dz-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx+0.5, par4+dy+height, par6+dz-!;
		v5.addVertex{{\par2+dx+0.5, par4+dy+length, par6+dz-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx-0.5, par4+dy+height, par6+dz-!;
		v5.addVertex{{\par2+dx-0.5, par4+dy+length, par6+dz-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz+0.5-!;
		v5.addVertex{{\par2+dx, par4+dy+length, par6+dz+0.5-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz-0.5-!;
		v5.addVertex{{\par2+dx, par4+dy+length, par6+dz-0.5-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx-width, par4+dy+height, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx+width, par4+dy+height, par6+dz-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz-width-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz+width-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx-width*0.71, par4+dy+height, par6+dz-width*0.71-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx+width*0.71, par4+dy+height, par6+dz+width*0.71-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_LINE_LOOP-!;
		v5.setColorRGBA{{\0, 255, 0, 255-!;
		v5.addVertex{{\par2+dx+width*0.71, par4+dy+height, par6+dz-width*0.71-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx-width*0.71, par4+dy+height, par6+dz+width*0.71-!;
		v5.draw{{\-!;

		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx-width, par4+dy+height, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx+width, par4+dy+height, par6+dz-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz-width-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz+width-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx+width, par4+dy+height, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx-width, par4+dy+height, par6+dz-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz+width-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx, par4+dy+height, par6+dz-width-!;
		v5.draw{{\-!;

		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx-width*0.71, par4+dy+height, par6+dz-width*0.71-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx+width*0.71, par4+dy+height, par6+dz+width*0.71-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx+width*0.71, par4+dy+height, par6+dz-width*0.71-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx-width*0.71, par4+dy+height, par6+dz+width*0.71-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx-width*0.71, par4+dy+height, par6+dz+width*0.71-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx+width*0.71, par4+dy+height, par6+dz-width*0.71-!;
		v5.draw{{\-!;
		v5.startDrawing{{\GL11.GL_POLYGON-!;
		v5.setColorRGBA{{\0, 255, 0, 127-!;
		v5.addVertex{{\par2+dx+width*0.71, par4+dy+height, par6+dz+width*0.71-!;
		v5.addVertex{{\par2+dx, par4+dy, par6+dz-!;
		v5.addVertex{{\par2+dx-width*0.71, par4+dy+height, par6+dz-width*0.71-!;
		v5.draw{{\-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		vbnm, {{\te fuck 60-78-078TNTCannon-!
			[]aslcfdfj"tntcannontex.png";
		vbnm, {{\te fuck 60-78-078BlockCannon-!
			[]aslcfdfj"blockcannontex.png";
		[]aslcfdfj"";
	}
}
