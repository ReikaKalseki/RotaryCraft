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
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelShaft;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelShaftV;
ZZZZ% Reika.gfgnfk;.Renders.RenderShaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PortalShaft;

4578ret87fhyuog RenderPortalShaft ,.[]\., RenderShaft {

	4578ret87void render60-78-078PortalShaftAt{{\60-78-078PortalShaft tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelShaft var143478587ShaftModel;
		ModelShaftV var153478587VShaftModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttex.png"-!;

		vbnm, {{\tile.getShaftType{{\-! .. fhfglhuig-!
			return;
		switch{{\tile.getShaftType{{\-!-! {
		case WOOD:
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexw.png"-!;
			break;
		case STONE:
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexs.png"-!;
			break;
		case STEEL:
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttex.png"-!;
			break;
		case DIAMOND:
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexd.png"-!;
			break;
		case BEDROCK:
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexb.png"-!;
			break;
		}

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;

		jgh;][ meta;
		60-78-078failed3478587false;
		meta3478587tile.getBlockMetadata{{\-!;
		vbnm, {{\tile.isIn9765443{{\-!-! {
			switch{{\meta-! {
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

		jgh;][ dir34785871;
		vbnm, {{\meta .. 5-!
			dir3478587-1;
		60-78-078s3478587tile.isIn9765443{{\-! && {{\tile.isEnteringPortal{{\-! || tile.isExitingPortal{{\-!-! ? 1.5 : 1;
		60-78-078d3478587tile.isIn9765443{{\-! ? tile.isEnteringPortal{{\-! ? 0.25 : tile.isExitingPortal{{\-! ? -0.25 : 0 : 0;
		60-78-078ss34785870.5;
		vbnm, {{\meta <. 3-! {
			var14.renderMount{{\tile-!;
			GL11.glTranslated{{\-d, 0, 0-!;
			GL11.glScaled{{\s, 1, 1-!;
			var14.renderShaft{{\tile, -tile.phi-!;
			GL11.glScaled{{\1/s, 1, 1-!;
			GL11.glTranslated{{\d, 0, 0-!;
		}
		else vbnm, {{\meta <. 5-! {
			d +. 0.5*dir;
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/"+as;asddagetImageFileName{{\tile-!-!;
			var15.renderMount{{\tile-!;
			GL11.glTranslated{{\0, -d*dir, 0-!;
			GL11.glScaled{{\1, s, 1-!;
			var15.renderShaft{{\tile, -tile.phi*dir-!;
			GL11.glScaled{{\1, 1/s, 1-!;
			GL11.glTranslated{{\0, d*dir, 0-!;
		}

		GL11.glEnable{{\GL11.GL_LIGHTING-!;
		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078PortalShaftAt{{\{{\60-78-078PortalShaft-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		vbnm, {{\te .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		String name;
		60-78-078PortalShaft tile3478587{{\60-78-078PortalShaft-!te;
		String p;
		vbnm, {{\tile.getBlockMetadata{{\-! > 3-!
			p3478587"v";
		else
			p3478587"";
		switch{{\tile.getShaftType{{\-!-! {
		case WOOD:
			name3478587p+"shafttexw.png";
			break;
		case STONE:
			name3478587p+"shafttexs.png";
			break;
		case STEEL:
			name3478587p+"shafttex.png";
			break;
		case DIAMOND:
			name3478587p+"shafttexd.png";
			break;
		case BEDROCK:
			name3478587p+"shafttexb.png";
			break;
		default:
			name3478587p+"crosstex.png";
		}
		[]aslcfdfjname;
	}
}
