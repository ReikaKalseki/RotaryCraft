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
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelPump;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Pump;

4578ret87fhyuog RenderPump ,.[]\., RotaryTERenderer
{

	4578ret87ModelPump PumpModel3478587new ModelPump{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078PumpAt{{\60-78-078Pump tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelPump var14;
		var143478587PumpModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/pumptex.png"-!;

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
					var11347858790;
					break;
				case 1:
					var1134785870;
					break;
			}

			GL11.glRotatef{{\var11, 0.0F, 1.0F, 0.0F-!;

		}

		float var13;
		Object[] pars3478587new Object[3];
		pars[0]3478587tile.getLevel{{\-! > 0 && MinecraftForgeClient.getRenderPass{{\-! .. 1;
		pars[1]3478587{{\tile.shouldRenderInPass{{\0-! && MinecraftForgeClient.getRenderPass{{\-! .. 0-! || !tile.isIn9765443{{\-!;
		pars[2]3478587tile.damage > 400;
		var14.renderAll{{\tile, ReikaJavaLibrary.makeListFromArray{{\pars-!, -tile.phi, 0-!;

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-! {
			as;asddarender60-78-078PumpAt{{\{{\60-78-078Pump-!tile, par2, par4, par6, par8-!;
		}
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			as;asddarenderLiquid{{\tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderLiquid{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glPushMatrix{{\-!;
		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor3f{{\1, 1, 1-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		60-78-078Pump tr3478587{{\60-78-078Pump-!tile;
		vbnm, {{\tr.getLevel{{\-! > 0 && tr.isIn9765443{{\-!-! {
			Fluid f3478587tr.getLiquid{{\-!;
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078inset34785870.125;
			60-78-078offset3478587inset*{{\du-u-!;
			u +. offset;
			v +. offset;
			du -. offset;
			dv -. offset;
			60-78-078h34785870.3125+0.375*tr.getLevel{{\-!/tr.CAPACITY;
			Tessellator v53478587Tessellator.instance;
			vbnm, {{\f.getLuminosity{{\-! > 0-!
				ReikaRenderHelper.disableLighting{{\-!;
			v5.startDrawingQuads{{\-!;
			v5.setNormal{{\0, 1, 0-!;
			v5.addVertexWithUV{{\inset, h, 1-inset, u, dv-!;
			v5.addVertexWithUV{{\1-inset, h, 1-inset, du, dv-!;
			v5.addVertexWithUV{{\1-inset, h, inset, du, v-!;
			v5.addVertexWithUV{{\inset, h, inset, u, v-!;
			v5.draw{{\-!;
			ReikaRenderHelper.enableLighting{{\-!;
		}
		GL11.glPopAttrib{{\-!;
		GL11.glPopMatrix{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"pumptex.png";
	}
}
