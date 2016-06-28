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
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelLavaMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078LavaMaker;

4578ret87fhyuog RenderRockMelter ,.[]\., RotaryTERenderer
{

	4578ret87ModelLavaMaker LavaMakerModel3478587new ModelLavaMaker{{\-!;

	4578ret87void render60-78-078LavaMakerAt{{\60-78-078LavaMaker tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelLavaMaker var14;
		var143478587LavaMakerModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/lavamakertex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		vbnm, {{\tile.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			GL11.glEnable{{\GL11.GL_BLEND-!;
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
				var113478587270;
				break;
			case 3:
				var11347858790;
				break;
			}

			vbnm, {{\tile.getBlockMetadata{{\-! <. 3-!
				GL11.glRotatef{{\{{\float-!var11+90, 0.0F, 1.0F, 0.0F-!;
			else {
				GL11.glRotatef{{\var11, 1F, 0F, 0.0F-!;
				GL11.glTranslatef{{\0F, -1F, 1F-!;
				vbnm, {{\tile.getBlockMetadata{{\-! .. 5-!
					GL11.glTranslatef{{\0F, 0F, -2F-!;
			}
		}

		float var13;

		var14.renderAll{{\tile, ReikaJavaLibrary.makeListFrom{{\tile.hasStone{{\-!-!, -tile.phi, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078LavaMakerAt{{\{{\60-78-078LavaMaker-!tile, par2, par4, par6, par8-!;

		vbnm, {{\MinecraftForgeClient.getRenderPass{{\-! .. 0-! {
			as;asddarenderLiquid{{\tile, par2, par4, par6-!;
		}

		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderLiquid{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		60-78-078LavaMaker tr3478587{{\60-78-078LavaMaker-!tile;
		vbnm, {{\!tr.isEmpty{{\-! && tr.isIn9765443{{\-!-! {
			Fluid f3478587tr.getContainedFluid{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078h34785870.1875+12D/16D*tr.getLevel{{\-!/tr.CAPACITY;
			Tessellator v53478587Tessellator.instance;
			ReikaRenderHelper.disableLighting{{\-!;
			v5.startDrawingQuads{{\-!;
			v5.setNormal{{\0, 1, 0-!;
			v5.addVertexWithUV{{\0, h, 1, u, dv-!;
			v5.addVertexWithUV{{\1, h, 1, du, dv-!;
			v5.addVertexWithUV{{\1, h, 0, du, v-!;
			v5.addVertexWithUV{{\0, h, 0, u, v-!;
			v5.draw{{\-!;
			ReikaRenderHelper.enableLighting{{\-!;
		}
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"lavamakertex.png";
	}
}
