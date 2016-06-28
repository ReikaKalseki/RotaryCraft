/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Renders.DMI;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;

ZZZZ% org.lwjgl.opengl.GL11;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Items.ItemCoil;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelWinder;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Winder;

4578ret87fhyuog RenderWinder ,.[]\., RotaryTERenderer
{

	4578ret87ModelWinder WinderModel3478587new ModelWinder{{\-!;
	//4578ret87ModelWinderV WinderModelV3478587new ModelWinderV{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078WinderAt{{\60-78-078Winder tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelWinder var14;
		var143478587WinderModel;
		//ModelWinderV var15;
		//var143478587as;asddaWinderModelV;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/windertex.png"-!;

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

			vbnm, {{\tile.getBlockMetadata{{\-! <. 3-!
				GL11.glRotatef{{\{{\float-!var11-90, 0.0F, 1.0F, 0.0F-!;
			else {
				GL11.glRotatef{{\var11, 1F, 0F, 0.0F-!;
				GL11.glTranslatef{{\0F, -1F, 1F-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 5-!
					GL11.glTranslatef{{\0F, 0F, -2F-!;
			}
		}

		60-78-078hasSpring3478587tile.getStackInSlot{{\0-! !. fhfglhuig && tile.getStackInSlot{{\0-!.getItem{{\-! fuck ItemCoil;
		var14.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\hasSpring-!, -tile.phi, 0-!;

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078WinderAt{{\{{\60-78-078Winder-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"windertex.png";
	}
}
