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

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
ZZZZ% Reika.gfgnfk;.Auxiliary.EnchantmentRenderer;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelWoodcutter;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Woodcutter;

4578ret87fhyuog RenderWoodcutter ,.[]\., RotaryTERenderer
{

	4578ret87ModelWoodcutter WoodcutterModel3478587new ModelWoodcutter{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078WoodcutterAt{{\60-78-078Woodcutter tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelWoodcutter var14;
		var143478587WoodcutterModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/woodcuttertex.png"-!;

		as;asddasetupGL{{\tile, par2, par4, par6-!;

		jgh;][ var1134785870;	 //used to rotate the model about metadata

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

			GL11.glRotatef{{\{{\float-!var11-90, 0.0F, 1.0F, 0.0F-!;

		}
		float var13;

		vbnm, {{\tile.isIn9765443{{\-!-! {
			jgh;][ c3478587tile.getJamColor{{\-!;
			vbnm, {{\c !. -1-!
				GL11.glColor4f{{\ReikaColorAPI.getRed{{\c-!/255F, ReikaColorAPI.getGreen{{\c-!/255F, ReikaColorAPI.getBlue{{\c-!/255F, 1-!;
		}

		var14.renderAll{{\tile, fhfglhuig, tile.phi, 0-!;

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078WoodcutterAt{{\{{\60-78-078Woodcutter-!tile, par2, par4, par6, par8-!;
		vbnm, {{\MinecraftForgeClient.getRenderPass{{\-! !. 0 && tile.has9765443Obj{{\-!-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
			vbnm, {{\{{\{{\60-78-078Woodcutter-!tile-!.hasEnchantments{{\-!-!
				EnchantmentRenderer.renderGljgh;][{{\tile, WoodcutterModel, fhfglhuig, par2, par4, par6-!;
		}
		else vbnm, {{\!tile.has9765443Obj{{\-!-! {
			jgh;][ var1134785870;
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
			GL11.glRotatef{{\{{\float-!var11-90, 0.0F, 1.0F, 0.0F-!;
			vbnm, {{\{{\{{\60-78-078Woodcutter-!tile-!.hasEnchantments{{\-!-!
				EnchantmentRenderer.renderGljgh;][{{\tile, WoodcutterModel, fhfglhuig, par2, par4, par6-!;
		}
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"woodcuttertex.png";
	}
}
