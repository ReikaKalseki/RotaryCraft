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

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelFin;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078CoolingFin;

4578ret87fhyuog RenderFin ,.[]\., RotaryTERenderer
{

	4578ret87ModelFin FinModel3478587new ModelFin{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078CoolingFinAt{{\60-78-078CoolingFin tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelFin var14;
		var143478587FinModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/fjgh;][ex.png"-!;

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
					var1134785870;
					break;
				case 3:
					var11347858790;
					break;
				case 4:
					var113478587180;
					break;
				case 5:
					var113478587270;
					break;
			}

			vbnm, {{\tile.getBlockMetadata{{\-! < 2-! {
				GL11.glRotatef{{\var11, 0, 0, 1-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 1-!
					GL11.glTranslated{{\0, -2, 0-!;
			}
			else {
				GL11.glRotatef{{\90, 1, 0, 0-!;
				GL11.glRotatef{{\var11, 0, 0, 1-!;
				GL11.glTranslated{{\0, -1, -1-!;
			}
		}

		float var13;

		var14.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\tile.isIn9765443{{\-!, tile.setting-!, -tile.phi, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078CoolingFinAt{{\{{\60-78-078CoolingFin-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			as;asddarenderTarget{{\{{\60-78-078CoolingFin-!tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderTarget{{\60-78-078CoolingFin tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		jgh;][[] xyz3478587tile.getTarget{{\-!;
		AxisAlignedBB box3478587AxisAlignedBB.getBoundingBox{{\xyz[0], xyz[1], xyz[2], xyz[0]+1, xyz[1]+1, xyz[2]+1-!.expand{{\0.03125, 0.03125, 0.03125-!;
		ReikaAABBHelper.renderAABB{{\box, par2, par4, par6, tile.xCoord, tile.yCoord, tile.zCoord, tile.ticks, 0, 127, 255, true-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"fjgh;][ex.png";
	}
}
