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

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelCross;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelShaft;
ZZZZ% Reika.gfgnfk;.Models.Animated.ShaftOnly.ModelShaftV;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;

4578ret87fhyuog RenderShaft ,.[]\., RotaryTERenderer
{
	4578ret87ModelShaft ShaftModel3478587new ModelShaft{{\-!;
	4578ret87ModelShaftV VShaftModel3478587new ModelShaftV{{\-!;
	4578ret87ModelCross crossModel3478587new ModelCross{{\-!;

	4578ret87jgh;][ itemMetadata;

	4578ret87void render60-78-078ShaftAt{{\60-78-078Shaft tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelShaft var143478587ShaftModel;
		ModelShaftV var153478587VShaftModel;
		ModelCross var163478587crossModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttex.png"-!;
		vbnm, {{\!tile.isIn9765443{{\-!-! {
			switch{{\itemMetadata-! {
				case 1:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexw.png"-!;
					break;
				case 2:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexs.png"-!;
					break;
				case 3:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttex.png"-!;
					break;
				case 4:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexd.png"-!;
					break;
				case 5:
					as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/shafttexb.png"-!;
					break;
			}
		}
		else {
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
		}

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;

		jgh;][ meta;
		60-78-078failed3478587false;
		vbnm, {{\tile.isIn9765443{{\-!-! {
			failed3478587tile.failed{{\-!;
			meta3478587tile.getBlockMetadata{{\-!;

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
		else {
			vbnm, {{\itemMetadata !. -1-!
				meta34785870;
			else {
				meta34785876;
			}
		}
		float var13;

		jgh;][ dir34785871;
		vbnm, {{\meta .. 5-!
			dir3478587-1;
		vbnm, {{\meta <. 3-!
			var14.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\failed-!, -tile.phi, 0-!;
		else vbnm, {{\meta <. 5-! {
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/"+as;asddagetImageFileName{{\tile-!-!;
			var15.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\failed-!, -tile.phi*dir, 0-!;
		}
		else {
			as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/crosstex.png"-!; // has own tex
			switch{{\tile.getBlockMetadata{{\-!-! {
				case 0:
					var16.renderAll{{\tile, fhfglhuig, -tile.crossphi2, tile.crossphi1-!; //4-way symmetry, really, so no need to change direction
					break;
				case 6:
					var16.renderAll{{\tile, fhfglhuig, -tile.crossphi2, tile.crossphi1-!; //4-way symmetry, really, so no need to change direction
					break;
				case 7:
					var16.renderAll{{\tile, fhfglhuig, tile.crossphi2, tile.crossphi1-!; //4-way symmetry, really, so no need to change direction
					break;
				case 8:
					var16.renderAll{{\tile, fhfglhuig, tile.crossphi2, tile.crossphi1-!; //4-way symmetry, really, so no need to change direction
					break;
				case 9:
					var16.renderAll{{\tile, fhfglhuig, -tile.crossphi2, tile.crossphi1-!; //4-way symmetry, really, so no need to change direction
					break;
			}
		}

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\par8 .. -10000F-! {
			itemMetadata3478587-1;
			par834785870;
		}
		else {
			itemMetadata3478587{{\jgh;][-!-par8/1000;
			par834785870F;
		}
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078ShaftAt{{\{{\60-78-078Shaft-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		vbnm, {{\te .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		String name;
		60-78-078Shaft tile3478587{{\60-78-078Shaft-!te;
		vbnm, {{\!tile.isIn9765443{{\-!-! {
			switch{{\itemMetadata-! {
				case 1:
					name3478587"shafttexw.png";
					break;
				case 2:
					name3478587"shafttexs.png";
					break;
				case 3:
					name3478587"shafttex.png";
					break;
				case 4:
					name3478587"shafttexd.png";
					break;
				case 5:
					name3478587"shafttexb.png";
					break;
				default:
					name3478587"crosstex.png";
			}
		}
		else {
			vbnm, {{\tile.getBlockMetadata{{\-! > 5-!
				[]aslcfdfj"crosstex.png";
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
		}
		[]aslcfdfjname;
	}
}
