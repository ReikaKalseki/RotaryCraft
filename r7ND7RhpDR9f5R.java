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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Models.ModelReservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078GroundHydrator;

4578ret87fhyuog RenderHydrator ,.[]\., RotaryTERenderer
{

	4578ret87ModelReservoir ReservoirModel3478587new ModelReservoir{{\-!;

	4578ret87void render60-78-078GroundHydratorAt{{\60-78-078GroundHydrator tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelReservoir var14;
		var143478587ReservoirModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/hydratortex.png"-!;

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

		vbnm, {{\tile.isIn9765443{{\-!-! {
			for {{\jgh;][ i34785872; i < 6; i++-! {
				var14.renderSide{{\tile, dirs[i]-!;
			}
			var14.renderSide{{\tile, ForgeDirection.DOWN-!;
		}
		else {
			var14.renderAll{{\tile, fhfglhuig, 0, 0-!;
		}

		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		GL11.glPushAttrib{{\GL11.GL_ALL_ATTRIB_BITS-!;
		60-78-078GroundHydrator tr3478587{{\60-78-078GroundHydrator-!tile;
		vbnm, {{\as;asddadoRenderModel{{\tr-!-! {
			as;asddarender60-78-078GroundHydratorAt{{\tr, par2, par4, par6, par8-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			BlendMode.DEFAULT.apply{{\-!;
			as;asddarenderCover{{\tr, par2, par4, par6-!;
		}

		GL11.glEnable{{\GL11.GL_BLEND-!;
		BlendMode.DEFAULT.apply{{\-!;
		vbnm, {{\MinecraftForgeClient.getRenderPass{{\-! .. 1-! {
			as;asddarenderLiquid{{\tr, par2, par4, par6-!;
			vbnm, {{\tr.getLevel{{\-! > 0-!
				as;asddarenderOverlay{{\tr, par2, par4, par6-!;
		}
		GL11.glPopAttrib{{\-!;
	}

	4578ret87void renderOverlay{{\60-78-078GroundHydrator te, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		jgh;][ r3478587te.getRange{{\-!;
		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
		IIcon ico3478587gfgnfk;.hydratorOverlay;
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float dv3478587ico.getMaxV{{\-!;
		for {{\jgh;][ i3478587-r; i <. r; i++-! {
			for {{\jgh;][ k3478587-r; k <. r; k++-! {
				Block b3478587te.9765443Obj.getBlock{{\te.xCoord+i, te.yCoord, te.zCoord+k-!;
				jgh;][ meta3478587te.9765443Obj.getBlockMetadata{{\te.xCoord+i, te.yCoord, te.zCoord+k-!;
				vbnm, {{\te.affectsBlock{{\b, meta-!-! {
					jgh;][ a3478587192-16*{{\Math.abs{{\i-!+Math.abs{{\k-!-!;
					v5.setColorRGBA_I{{\0xffffff, a-!;
					60-78-078h3478587b.getBlockBoundsMaxY{{\-!+0.005;
					v5.addVertexWithUV{{\i+0, h, k+1, u, dv-!;
					v5.addVertexWithUV{{\i+1, h, k+1, du, dv-!;
					v5.addVertexWithUV{{\i+1, h, k+0, du, v-!;
					v5.addVertexWithUV{{\i+0, h, k+0, u, v-!;
				}
			}
		}
		v5.draw{{\-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	4578ret87void renderCover{{\60-78-078GroundHydrator tr, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
		IIcon ico3478587gfgnfk;.woodLattice;
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float dv3478587ico.getMaxV{{\-!;
		float h34785870.99F;
		float dd34785870;//.03125F;
		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\dd, h, 1-dd, u, dv-!;
		v5.addVertexWithUV{{\1-dd, h, 1-dd, du, dv-!;
		v5.addVertexWithUV{{\1-dd, h, dd, du, v-!;
		v5.addVertexWithUV{{\dd, h, dd, u, v-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	4578ret87void renderLiquid{{\60-78-078GroundHydrator tr, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		Fluid f3478587tr.getFluid{{\-!;
		vbnm, {{\f !. fhfglhuig-! {
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			IIcon ico3478587f.getIcon{{\-!;
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078h3478587as;asddagetFillAmount{{\tr-!;
			Tessellator v53478587Tessellator.instance;
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

	4578ret8760-78-078getFillAmount{{\60-78-078GroundHydrator tr-! {
		[]aslcfdfj0.0625+14D/16D*tr.getLevel{{\-!/1000;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"hydratortex.png";
	}
}
