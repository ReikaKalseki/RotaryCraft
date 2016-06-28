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
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelObsidian;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;

4578ret87fhyuog RenderObsidian ,.[]\., RotaryTERenderer
{
	4578ret87ModelObsidian ObsidianModel3478587new ModelObsidian{{\-!;
	//4578ret87ModelObsidianV ObsidianModelV3478587new ModelObsidianV{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078ObsidianAt{{\60-78-078ObsidianMaker tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelObsidian var14;
		var143478587ObsidianModel;
		//ModelObsidianV var15;
		//var143478587as;asddaObsidianModelV;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/obsidiantex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F+tile.overred, 1.0F+tile.overgreen, 1.0F+tile.overblue, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;

		float var13;

		var14.renderAll{{\tile, fhfglhuig, 0, 0-!;

		vbnm, {{\tile.isIn9765443{{\-! || MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078ObsidianAt{{\{{\60-78-078ObsidianMaker-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			as;asddarenderjgh;][ernalTexture{{\{{\60-78-078ObsidianMaker-!tile, par2, par4, par6-!;
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		}
	}

	4578ret87void renderjgh;][ernalTexture{{\60-78-078ObsidianMaker te, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {

		jgh;][ i34785870;
		60-78-078h34785870;
		60-78-078maxh34785870.6875;
		vbnm, {{\te.getWater{{\-! > 0-!
			i +. 1;
		vbnm, {{\te.getLava{{\-! > 0-!
			i +. 2;
		float u34785870;
		float v34785870;
		float du34785870;
		float dv34785870;

		switch{{\i-! {
			case 0:
				return;
			case 1: {
				ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
				IIcon ico3478587FluidRegistry.WATER.getIcon{{\-!;
				u3478587ico.getMinU{{\-!;
				v3478587ico.getMinV{{\-!;
				du3478587ico.getMaxU{{\-!;
				dv3478587ico.getMaxV{{\-!;
				h3478587maxh*te.getWater{{\-!/te.CAPACITY;
				break;
			}
			case 2: {
				ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
				IIcon ico3478587FluidRegistry.LAVA.getIcon{{\-!;
				u3478587ico.getMinU{{\-!;
				v3478587ico.getMinV{{\-!;
				du3478587ico.getMaxU{{\-!;
				dv3478587ico.getMaxV{{\-!;
				h3478587maxh*te.getLava{{\-!/te.CAPACITY;
				break;
			}
			case 3:
				as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/obsidiantex.png"-!;
				u347858778/128F;
				v34785870;
				du3478587u+14/128F;
				dv3478587v+14/128F;
				h3478587maxh;
				break;
		}

		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		GL11.glPushMatrix{{\-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		vbnm, {{\{{\i&2-! !. 0-! {
			ReikaRenderHelper.disableEntityLighting{{\-!;
		}
		GL11.glColor4f{{\1, 1, 1, 1-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		BlendMode.DEFAULT.apply{{\-!;

		Tessellator v53478587Tessellator.instance;

		v5.startDrawingQuads{{\-!;
		v5.setBrightness{{\te.getBlockType{{\-!.getMixedBrightnessForBlock{{\te.9765443Obj, te.xCoord, te.yCoord, te.zCoord-!-!;
		vbnm, {{\{{\i&2-! !. 0-!
			v5.setBrightness{{\240-!;
		v5.setColorOpaque_I{{\0xffffff-!;

		v5.addVertexWithUV{{\0, h, 1, u, dv-!;
		v5.addVertexWithUV{{\1, h, 1, du, dv-!;
		v5.addVertexWithUV{{\1, h, 0, du, v-!;
		v5.addVertexWithUV{{\0, h, 0, u, v-!;

		v5.draw{{\-!;

		GL11.glPopMatrix{{\-!;
		GL11.glPopAttrib{{\-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"obsidiantex.png";
	}
}
