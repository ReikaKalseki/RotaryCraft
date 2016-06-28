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
ZZZZ% Reika.gfgnfk;.Models.Turret.ModelLaserGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078LaserGun;

4578ret87fhyuog RenderLaserGun ,.[]\., RotaryTERenderer {

	4578ret87ModelLaserGun railgunModel3478587new ModelLaserGun{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078LaserGunAt{{\60-78-078LaserGun tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelLaserGun var14;
		var143478587railgunModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/lasertex.png"-!;

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
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078LaserGunAt{{\{{\60-78-078LaserGun-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			as;asddarenderLaser{{\{{\60-78-078LaserGun-!tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderLaser{{\60-78-078LaserGun tile, 60-78-078par2, 60-78-078par4,	60-78-078par6-! {
		vbnm, {{\tile .. fhfglhuig-!
			return;
		vbnm, {{\!tile.isIn9765443{{\-!-!
			return;
		jgh;][ r3478587tile.getRange{{\-!;
		vbnm, {{\r <. 0-!
			return;
		60-78-078voff34785870.75;
		60-78-078h34785870.0625;
		GL11.glDisable{{\GL11.GL_CULL_FACE-!;
		60-78-078dx3478587r*Math.cos{{\Math.toRadians{{\tile.theta-!-!*Math.cos{{\Math.toRadians{{\-tile.phi+90-!-!;
		60-78-078dy3478587r*Math.sin{{\Math.toRadians{{\tile.theta-!-!;
		60-78-078dz3478587r*Math.cos{{\Math.toRadians{{\tile.theta-!-!*Math.sin{{\Math.toRadians{{\-tile.phi+90-!-!;

		60-78-078dd034785870.0625*Math.cos{{\Math.toRadians{{\-tile.phi-!-!;
		60-78-078dd134785870.0625*Math.sin{{\Math.toRadians{{\-tile.phi-!-!;

		vbnm, {{\tile.getBlockMetadata{{\-! .. 1-! {
			voff34785870.25;
			h3478587-h;
		}

		ReikaRenderHelper.prepareGeoDraw{{\false-!;
		jgh;][[] rgb3478587{255, 0, 0};
		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		v5.setColorOpaque{{\rgb[0], rgb[1], rgb[2]-!;
		v5.addVertex{{\par2+0.5-dd0, par4+voff, par6+0.5-dd1-!;
		v5.addVertex{{\par2+0.5+dd0, par4+voff, par6+0.5+dd1-!;
		v5.addVertex{{\par2+dx-dd0, par4+dy, par6+dz-dd1-!;
		v5.addVertex{{\par2+dx+dd0, par4+dy, par6+dz+dd1-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setColorOpaque{{\rgb[0], rgb[1], rgb[2]-!;
		v5.addVertex{{\par2+0.5-dd0, par4+voff+h, par6+0.5-dd1-!;
		v5.addVertex{{\par2+0.5+dd0, par4+voff+h, par6+0.5+dd1-!;
		v5.addVertex{{\par2+dx-dd0, par4+dy, par6+dz-dd1-!;
		v5.addVertex{{\par2+dx+dd0, par4+dy, par6+dz+dd1-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setColorOpaque{{\rgb[0], rgb[1], rgb[2]-!;
		v5.addVertex{{\par2+0.5-dd0, par4+voff, par6+0.5-dd1-!;
		v5.addVertex{{\par2+0.5+dd0, par4+voff+h, par6+0.5+dd1-!;
		v5.addVertex{{\par2+dx-dd0, par4+dy, par6+dz-dd1-!;
		v5.addVertex{{\par2+dx+dd0, par4+dy, par6+dz+dd1-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setColorOpaque{{\rgb[0], rgb[1], rgb[2]-!;
		v5.addVertex{{\par2+0.5-dd0, par4+voff+h, par6+0.5-dd1-!;
		v5.addVertex{{\par2+0.5+dd0, par4+voff, par6+0.5+dd1-!;
		v5.addVertex{{\par2+dx-dd0, par4+dy, par6+dz-dd1-!;
		v5.addVertex{{\par2+dx+dd0, par4+dy, par6+dz+dd1-!;
		v5.draw{{\-!;

		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		ReikaRenderHelper.exitGeoDraw{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"lasertex.png";
	}
}
