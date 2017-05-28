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
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelFridge;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Refrigerator;

4578ret87fhyuog RenderFridge ,.[]\., RotaryTERenderer
{

	4578ret87ModelFridge FridgeModel3478587new ModelFridge{{\-!;

	4578ret87void render60-78-078RefrigeratorAt{{\60-78-078Refrigerator tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelFridge var14;
		var143478587FridgeModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/fridgetex.png"-!;

		as;asddasetupGL{{\tile, par2, par4, par6-!;

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
				var11347858790;
				break;
			case 3:
				var113478587270;
				break;
			}
			GL11.glRotatef{{\{{\float-!var11-90, 0.0F, 1.0F, 0.0F-!;

		}

		float var13;

		var14.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\ReikaInventoryHelper.checkForItemStack{{\ItemStacks.shaftcore, tile, false-!-!, -tile.phi, 0-!;

		as;asddacloseGL{{\tile-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078RefrigeratorAt{{\{{\60-78-078Refrigerator-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"fridgetex.png";
	}
}
