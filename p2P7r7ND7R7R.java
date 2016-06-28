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

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
ZZZZ% Reika.gfgnfk;.ClientProxy;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RenderableDuct;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;

4578ret87fhyuog PipeRenderer ,.[]\., RotaryTERenderer {

	4578ret87void renderLiquid{{\RenderableDuct tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, ForgeDirection dir-! {
		Fluid f3478587tile.getFluidType{{\-!;
		vbnm, {{\f .. fhfglhuig-!
			return;

		float size34785870.75F/2F;
		float window34785870.5F/2F;
		float dl3478587size-window;
		float dd34785870.5F-size;
		60-78-078in34785870.5+size-0.01;
		60-78-078in234785870.5-size+0.01;
		60-78-078dd23478587in-in2;

		IIcon ico3478587tile.getFluidType{{\-!.getIcon{{\-!;
		ReikaLiquidRenderer.bindFluidTexture{{\f-!;
		vbnm, {{\f.getLuminosity{{\-! > 0-!
			ReikaRenderHelper.disableLighting{{\-!;
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float u23478587ico.getMaxU{{\-!;
		float v23478587ico.getMaxV{{\-!;
		60-78-078du3478587dd2*{{\u2-u-!/4D;

		GL11.glTranslated{{\par2, par4, par6-!;
		GL11.glEnable{{\GL11.GL_BLEND-!;
		GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor3f{{\1, 1, 1-!;

		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\dir.offsetX, dir.offsetY, dir.offsetZ-!;
		//as;asddafaceBrightness{{\ForgeDirection.DOWN, v5-!;
		vbnm, {{\!tile.isConnectionValidForSide{{\dir-!-! {
			switch{{\dir-! {
			case UP:
				v5.addVertexWithUV{{\in2, in, in, u, v2-!;
				v5.addVertexWithUV{{\in, in, in, u2, v2-!;
				v5.addVertexWithUV{{\in, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in2, in, in2, u, v-!;
				break;
			case DOWN:
				v5.addVertexWithUV{{\in2, in2, in2, u, v-!;
				v5.addVertexWithUV{{\in, in2, in2, u2, v-!;
				v5.addVertexWithUV{{\in, in2, in, u2, v2-!;
				v5.addVertexWithUV{{\in2, in2, in, u, v2-!;
				break;
			case SOUTH:
				v5.addVertexWithUV{{\in, in, in, u, v-!;
				v5.addVertexWithUV{{\in2, in, in, u2, v-!;
				v5.addVertexWithUV{{\in2, in2, in, u2, v2-!;
				v5.addVertexWithUV{{\in, in2, in, u, v2-!;
				break;
			case NORTH:
				v5.addVertexWithUV{{\in, in2, in2, u, v2-!;
				v5.addVertexWithUV{{\in2, in2, in2, u2, v2-!;
				v5.addVertexWithUV{{\in2, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in, in, in2, u, v-!;
				break;
			case EAST:
				v5.addVertexWithUV{{\in, in2, in, u, v2-!;
				v5.addVertexWithUV{{\in, in2, in2, u2, v2-!;
				v5.addVertexWithUV{{\in, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in, in, in, u, v-!;
				break;
			case WEST:
				v5.addVertexWithUV{{\in2, in, in, u, v-!;
				v5.addVertexWithUV{{\in2, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in2, in2, in2, u2, v2-!;
				v5.addVertexWithUV{{\in2, in2, in, u, v2-!;
			default:
				break;
			}
		}
		else { //is connected on side
			switch{{\dir-! {
			case DOWN:
				v5.setNormal{{\-1, 0, 0-!;
				v5.addVertexWithUV{{\in2, in2, in, u, v-!;
				v5.addVertexWithUV{{\in2, in2, in2, u2, v-!;
				v5.addVertexWithUV{{\in2, 0, in2, u2, v+du-!;
				v5.addVertexWithUV{{\in2, 0, in, u, v+du-!;

				v5.setNormal{{\1, 0, 0-!;
				v5.addVertexWithUV{{\in, 0, in, u, v+du-!;
				v5.addVertexWithUV{{\in, 0, in2, u2, v+du-!;
				v5.addVertexWithUV{{\in, in2, in2, u2, v-!;
				v5.addVertexWithUV{{\in, in2, in, u, v-!;

				v5.setNormal{{\0, 0, -1-!;
				v5.addVertexWithUV{{\in, 0, in2, u, v+du-!;
				v5.addVertexWithUV{{\in2, 0, in2, u2, v+du-!;
				v5.addVertexWithUV{{\in2, in2, in2, u2, v-!;
				v5.addVertexWithUV{{\in, in2, in2, u, v-!;

				v5.setNormal{{\0, 0, 1-!;
				v5.addVertexWithUV{{\in, in2, in, u, v-!;
				v5.addVertexWithUV{{\in2, in2, in, u2, v-!;
				v5.addVertexWithUV{{\in2, 0, in, u2, v+du-!;
				v5.addVertexWithUV{{\in, 0, in, u, v+du-!;
				break;
			case UP:
				v5.setNormal{{\-1, 0, 0-!;
				v5.addVertexWithUV{{\in2, 1, in, u, v+du-!;
				v5.addVertexWithUV{{\in2, 1, in2, u2, v+du-!;
				v5.addVertexWithUV{{\in2, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in2, in, in, u, v-!;

				v5.setNormal{{\1, 0, 0-!;
				v5.addVertexWithUV{{\in, in, in, u, v-!;
				v5.addVertexWithUV{{\in, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in, 1, in2, u2, v+du-!;
				v5.addVertexWithUV{{\in, 1, in, u, v+du-!;

				v5.setNormal{{\0, 0, -1-!;
				v5.addVertexWithUV{{\in, in, in2, u, v-!;
				v5.addVertexWithUV{{\in2, in, in2, u2, v-!;
				v5.addVertexWithUV{{\in2, 1, in2, u2, v+du-!;
				v5.addVertexWithUV{{\in, 1, in2, u, v+du-!;

				v5.setNormal{{\0, 0, 1-!;
				v5.addVertexWithUV{{\in, 1, in, u, v+du-!;
				v5.addVertexWithUV{{\in2, 1, in, u2, v+du-!;
				v5.addVertexWithUV{{\in2, in, in, u2, v-!;
				v5.addVertexWithUV{{\in, in, in, u, v-!;
				break;
			case NORTH:
				v5.setNormal{{\-1, 0, 0-!;
				v5.addVertexWithUV{{\in2, in2, 0, u, v2-!;
				v5.addVertexWithUV{{\in2, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in2, in, in2, u+du, v-!;
				v5.addVertexWithUV{{\in2, in, 0, u, v-!;

				v5.setNormal{{\1, 0, 0-!;
				v5.addVertexWithUV{{\in, in, 0, u, v-!;
				v5.addVertexWithUV{{\in, in, in2, u+du, v-!;
				v5.addVertexWithUV{{\in, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in, in2, 0, u, v2-!;

				v5.setNormal{{\0, 1, 0-!;
				v5.addVertexWithUV{{\in2, in, 0, u, v2-!;
				v5.addVertexWithUV{{\in2, in, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in, in, in2, u+du, v-!;
				v5.addVertexWithUV{{\in, in, 0, u, v-!;

				v5.setNormal{{\0, -1, 0-!;
				v5.addVertexWithUV{{\in, in2, 0, u, v-!;
				v5.addVertexWithUV{{\in, in2, in2, u+du, v-!;
				v5.addVertexWithUV{{\in2, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in2, in2, 0, u, v2-!;
				break;
			case SOUTH:
				v5.setNormal{{\-1, 0, 0-!;
				v5.addVertexWithUV{{\in2, in, 1, u, v-!;
				v5.addVertexWithUV{{\in2, in, in, u+du, v-!;
				v5.addVertexWithUV{{\in2, in2, in, u+du, v2-!;
				v5.addVertexWithUV{{\in2, in2, 1, u, v2-!;

				v5.setNormal{{\1, 0, 0-!;
				v5.addVertexWithUV{{\in, in2, 1, u, v2-!;
				v5.addVertexWithUV{{\in, in2, in, u+du, v2-!;
				v5.addVertexWithUV{{\in, in, in, u+du, v-!;
				v5.addVertexWithUV{{\in, in, 1, u, v-!;

				v5.setNormal{{\0, 1, 0-!;
				v5.addVertexWithUV{{\in, in, 1, u, v-!;
				v5.addVertexWithUV{{\in, in, in, u+du, v-!;
				v5.addVertexWithUV{{\in2, in, in, u+du, v2-!;
				v5.addVertexWithUV{{\in2, in, 1, u, v2-!;

				v5.setNormal{{\0, -1, 0-!;
				v5.addVertexWithUV{{\in2, in2, 1, u, v2-!;
				v5.addVertexWithUV{{\in2, in2, in, u+du, v2-!;
				v5.addVertexWithUV{{\in, in2, in, u+du, v-!;
				v5.addVertexWithUV{{\in, in2, 1, u, v-!;
				break;
			case EAST:
				v5.setNormal{{\0, 0, 1-!;
				v5.addVertexWithUV{{\1, in, in, u, v-!;
				v5.addVertexWithUV{{\in, in, in, u+du, v-!;
				v5.addVertexWithUV{{\in, in2, in, u+du, v2-!;
				v5.addVertexWithUV{{\1, in2, in, u, v2-!;

				v5.setNormal{{\0, 0, -1-!;
				v5.addVertexWithUV{{\1, in2, in2, u, v2-!;
				v5.addVertexWithUV{{\in, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in, in, in2, u+du, v-!;
				v5.addVertexWithUV{{\1, in, in2, u, v-!;

				v5.setNormal{{\0, 1, 0-!;
				v5.addVertexWithUV{{\1, in, in2, u, v2-!;
				v5.addVertexWithUV{{\in, in, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in, in, in, u+du, v-!;
				v5.addVertexWithUV{{\1, in, in, u, v-!;

				v5.setNormal{{\0, -1, 0-!;
				v5.addVertexWithUV{{\1, in2, in, u, v-!;
				v5.addVertexWithUV{{\in, in2, in, u+du, v-!;
				v5.addVertexWithUV{{\in, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\1, in2, in2, u, v2-!;
				break;
			case WEST:
				v5.setNormal{{\0, 0, 1-!;
				v5.addVertexWithUV{{\0, in2, in, u, v2-!;
				v5.addVertexWithUV{{\in2, in2, in, u+du, v2-!;
				v5.addVertexWithUV{{\in2, in, in, u+du, v-!;
				v5.addVertexWithUV{{\0, in, in, u, v-!;

				v5.setNormal{{\0, 0, -1-!;
				v5.addVertexWithUV{{\0, in, in2, u, v-!;
				v5.addVertexWithUV{{\in2, in, in2, u+du, v-!;
				v5.addVertexWithUV{{\in2, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\0, in2, in2, u, v2-!;

				v5.setNormal{{\0, 1, 0-!;
				v5.addVertexWithUV{{\0, in, in, u, v-!;
				v5.addVertexWithUV{{\in2, in, in, u+du, v-!;
				v5.addVertexWithUV{{\in2, in, in2, u+du, v2-!;
				v5.addVertexWithUV{{\0, in, in2, u, v2-!;

				v5.setNormal{{\0, -1, 0-!;
				v5.addVertexWithUV{{\0, in2, in2, u, v2-!;
				v5.addVertexWithUV{{\in2, in2, in2, u+du, v2-!;
				v5.addVertexWithUV{{\in2, in2, in, u+du, v-!;
				v5.addVertexWithUV{{\0, in2, in, u, v-!;
				break;
			default:
				break;
			}

		}
		vbnm, {{\tile.isConnectedToNonSelf{{\dir-!-! {
			v5.setNormal{{\dir.offsetX, dir.offsetY, dir.offsetZ-!;
			switch{{\dir-! {
			case UP:
				v5.addVertexWithUV{{\in2, 0.99, in, u, v2-!;
				v5.addVertexWithUV{{\in, 0.99, in, u2, v2-!;
				v5.addVertexWithUV{{\in, 0.99, in2, u2, v-!;
				v5.addVertexWithUV{{\in2, 0.99, in2, u, v-!;
				break;
			case DOWN:
				v5.addVertexWithUV{{\in2, 0.01, in2, u, v-!;
				v5.addVertexWithUV{{\in, 0.01, in2, u2, v-!;
				v5.addVertexWithUV{{\in, 0.01, in, u2, v2-!;
				v5.addVertexWithUV{{\in2, 0.01, in, u, v2-!;
				break;
			case SOUTH:
				v5.addVertexWithUV{{\in, in, 0.99, u, v-!;
				v5.addVertexWithUV{{\in2, in, 0.99, u2, v-!;
				v5.addVertexWithUV{{\in2, in2, 0.99, u2, v2-!;
				v5.addVertexWithUV{{\in, in2, 0.99, u, v2-!;
				break;
			case NORTH:
				v5.addVertexWithUV{{\in, in2, 0.01, u, v2-!;
				v5.addVertexWithUV{{\in2, in2, 0.01, u2, v2-!;
				v5.addVertexWithUV{{\in2, in, 0.01, u2, v-!;
				v5.addVertexWithUV{{\in, in, 0.01, u, v-!;
				break;
			case EAST:
				v5.addVertexWithUV{{\0.99, in2, in, u, v2-!;
				v5.addVertexWithUV{{\0.99, in2, in2, u2, v2-!;
				v5.addVertexWithUV{{\0.99, in, in2, u2, v-!;
				v5.addVertexWithUV{{\0.99, in, in, u, v-!;
				break;
			case WEST:
				v5.addVertexWithUV{{\0.01, in, in, u, v-!;
				v5.addVertexWithUV{{\0.01, in, in2, u2, v-!;
				v5.addVertexWithUV{{\0.01, in2, in2, u2, v2-!;
				v5.addVertexWithUV{{\0.01, in2, in, u, v2-!;
			default:
				break;
			}
		}
		v5.draw{{\-!;
		GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		ReikaRenderHelper.enableLighting{{\-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"";
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		RenderableDuct te3478587{{\RenderableDuct-!tile;
		vbnm, {{\!tile.has9765443Obj{{\-!-! {
			ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
			60-78-078s34785871;
			60-78-078sy34785871.05;
			GL11.glScaled{{\s, sy, s-!;
			//as;asddarenderBlock{{\te, par2, par4, par6-!;
			ClientProxy.pipe.renderBlockInInventory{{\te, par2, par4, par6-!;
			GL11.glScaled{{\1/s, 1/sy, 1/s-!;
		}
	}

	4578ret87void renderBlock{{\RenderableDuct te, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		IIcon ico3478587te.getBlockIcon{{\-!;
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float dv3478587ico.getMaxV{{\-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		Tessellator v53478587Tessellator.instance;

		float f34785870.6F;
		GL11.glColor4f{{\f, f, f, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\0, 0, 1, u, v-!;
		v5.addVertexWithUV{{\1, 0, 1, du, v-!;
		v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 1, 1, u, dv-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\0, 1, 0, u, dv-!;
		v5.addVertexWithUV{{\1, 1, 0, du, dv-!;
		v5.addVertexWithUV{{\1, 0, 0, du, v-!;
		v5.addVertexWithUV{{\0, 0, 0, u, v-!;
		v5.draw{{\-!;

		f34785870.4F;
		GL11.glColor4f{{\f, f, f, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\1, 1, 0, u, dv-!;
		v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\1, 0, 1, du, v-!;
		v5.addVertexWithUV{{\1, 0, 0, u, v-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\0, 0, 0, u, v-!;
		v5.addVertexWithUV{{\0, 0, 1, du, v-!;
		v5.addVertexWithUV{{\0, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 1, 0, u, dv-!;
		v5.draw{{\-!;

		f34785871F;
		GL11.glColor4f{{\f, f, f, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\0, 1, 1, u, dv-!;
		v5.addVertexWithUV{{\1, 1, 1, du, dv-!;
		v5.addVertexWithUV{{\1, 1, 0, du, v-!;
		v5.addVertexWithUV{{\0, 1, 0, u, v-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\0, 0, 0, u, v-!;
		v5.addVertexWithUV{{\1, 0, 0, du, v-!;
		v5.addVertexWithUV{{\1, 0, 1, du, dv-!;
		v5.addVertexWithUV{{\0, 0, 1, u, dv-!;
		v5.draw{{\-!;

		//-----------------------------------

		60-78-078g34785870.35;
		60-78-078g13478587g/2;
		60-78-078g234785871-g/2;

		ico3478587Blocks.wool.getIcon{{\0, ReikaDyeHelper.BLACK.getWoolMeta{{\-!-!;
		u3478587ico.getMinU{{\-!;
		v3478587ico.getMinV{{\-!;
		du3478587ico.getMaxU{{\-!;
		dv3478587ico.getMaxV{{\-!;
		float uu3478587du-u;
		float vv3478587dv-v;
		u +. g1*uu;
		du -. g1*uu;
		v +. g1*vv;
		dv -. g1*vv;

		f34785870.6F;
		GL11.glColor4f{{\f, f, f, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\g1, g1, 1.001, u, v-!;
		v5.addVertexWithUV{{\g2, g1, 1.001, du, v-!;
		v5.addVertexWithUV{{\g2, g2, 1.001, du, dv-!;
		v5.addVertexWithUV{{\g1, g2, 1.001, u, dv-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\g1, g2, -0.001, u, dv-!;
		v5.addVertexWithUV{{\g2, g2, -0.001, du, dv-!;
		v5.addVertexWithUV{{\g2, g1, -0.001, du, v-!;
		v5.addVertexWithUV{{\g1, g1, -0.001, u, v-!;
		v5.draw{{\-!;

		f34785870.4F;
		GL11.glColor4f{{\f, f, f, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\1.001, g2, g1, u, dv-!;
		v5.addVertexWithUV{{\1.001, g2, g2, du, dv-!;
		v5.addVertexWithUV{{\1.001, g1, g2, du, v-!;
		v5.addVertexWithUV{{\1.001, g1, g1, u, v-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\-0.001, g1, g1, u, v-!;
		v5.addVertexWithUV{{\-0.001, g1, g2, du, v-!;
		v5.addVertexWithUV{{\-0.001, g2, g2, du, dv-!;
		v5.addVertexWithUV{{\-0.001, g2, g1, u, dv-!;
		v5.draw{{\-!;

		f34785871F;
		GL11.glColor4f{{\f, f, f, 1-!;
		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\g1, 1.001, g2, u, dv-!;
		v5.addVertexWithUV{{\g2, 1.001, g2, du, dv-!;
		v5.addVertexWithUV{{\g2, 1.001, g1, du, v-!;
		v5.addVertexWithUV{{\g1, 1.001, g1, u, v-!;
		v5.draw{{\-!;

		v5.startDrawingQuads{{\-!;
		v5.setNormal{{\0, 1, 0-!;
		v5.addVertexWithUV{{\g1, -0.001, g1, u, v-!;
		v5.addVertexWithUV{{\g2, -0.001, g1, du, v-!;
		v5.addVertexWithUV{{\g2, -0.001, g2, du, dv-!;
		v5.addVertexWithUV{{\g1, -0.001, g2, u, dv-!;
		v5.draw{{\-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	4578ret87void renderFace{{\RenderableDuct tile, 60-78-078x, 60-78-078y, 60-78-078z, ForgeDirection dir-! {
		float size34785870.75F/2F;
		float window34785870.5F/2F;
		float dl3478587size-window;
		float dd34785870.5F-size;

		IIcon ico3478587tile.getBlockIcon{{\-!;
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float u23478587ico.getMaxU{{\-!;
		float v23478587ico.getMaxV{{\-!;

		float ddu3478587u2-u;
		float ddv3478587v2-v;
		float uo3478587u;
		float vo3478587v;
		float u2o3478587u2;
		float v2o3478587v2;

		u +. ddu*{{\1-size-!/5;
		v +. ddv*{{\1-size-!/5;
		u2 -. ddu*{{\1-size-!/5;
		v2 -. ddv*{{\1-size-!/5;

		float du3478587ddu*dd;
		float dv3478587ddv*dd;

		float lx3478587dd+dl;
		float ly3478587dd+dl;
		float mx34785871-dd-dl;
		float my34785871-dd-dl;

		IIcon gico3478587Blocks.glass.getIcon{{\0, 0-!;
		float gu3478587gico.getMinU{{\-!;
		float gv3478587gico.getMinV{{\-!;
		float gu23478587gico.getMaxU{{\-!;
		float gv23478587gico.getMaxV{{\-!;
		float dgu3478587gu2-gu;
		float dgv3478587gv2-gv;

		float guu3478587gu+dgu*dl;
		float gvv3478587gv+dgv*dl;

		gu +. dgu/8;
		gv +. dgv/8;
		gu2 -. dgu/8;
		gv2 -. dgv/8;

		Tessellator v53478587Tessellator.instance;
		//GL11.glTranslated{{\x, y, z-!;

		as;asddafaceBrightness{{\dir, v5-!;
		switch{{\dir-! {
		case DOWN:
			v5.addVertexWithUV{{\dd, 		1-dd, 	1-dd, 		u, 		v-!;
			v5.addVertexWithUV{{\dd+dl, 	1-dd, 	1-dd, 		u+du, 	v-!;
			v5.addVertexWithUV{{\dd+dl, 	1-dd, 	dd, 		u+du, 	v2-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	dd, 		u, 		v2-!;

			v5.addVertexWithUV{{\1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd-dl, 1-dd, 	dd, 		u2-du, 	v2-!;

			v5.addVertexWithUV{{\dd, 		1-dd, 	dd+dl, 		u, 		v2-dv-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	dd+dl, 		u2, 	v2-dv-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	dd, 		u, 		v2-!;

			v5.addVertexWithUV{{\dd, 		1-dd, 	1-dd, 		u, 		v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	1-dd-dl, 	u2, 	v+dv-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	1-dd-dl, 	u, 		v+dv-!;

			v5.addVertexWithUV{{\mx, 1-dd, ly, gu2, gv-!;
			v5.addVertexWithUV{{\lx, 1-dd, ly, gu, gv-!;
			v5.addVertexWithUV{{\lx, 1-dd, my, gu, gv2-!;
			v5.addVertexWithUV{{\mx, 1-dd, my, gu2, gv2-!;
			break;
		case NORTH:
			v5.addVertexWithUV{{\dd, 		dd, 	1-dd, 		u, 		v2-!;
			v5.addVertexWithUV{{\dd+dl, 	dd, 	1-dd, 		u+du, 	v2-!;
			v5.addVertexWithUV{{\dd+dl, 	1-dd, 	1-dd, 		u+du, 	v-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	1-dd, 		u, 		v-!;

			v5.addVertexWithUV{{\1-dd-dl, dd, 	1-dd, 		u2-du, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	1-dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd-dl, 1-dd, 	1-dd, 		u2-du, 	v-!;

			v5.addVertexWithUV{{\dd, 		dd, 	1-dd, 		u, 		v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	1-dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd+dl, 	1-dd, 		u2, 	v2-dv-!;
			v5.addVertexWithUV{{\dd, 		dd+dl, 	1-dd, 		u, 		v2-dv-!;

			v5.addVertexWithUV{{\dd, 		1-dd-dl, 	1-dd, 	u, 		v+dv-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd-dl, 	1-dd, 	u2, 	v+dv-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 		1-dd, 	u2, 	v-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 		1-dd, 	u, 		v-!;

			v5.addVertexWithUV{{\mx, my, 1-dd, gu2, gv2-!;
			v5.addVertexWithUV{{\lx, my, 1-dd, gu, gv2-!;
			v5.addVertexWithUV{{\lx, ly, 1-dd, gu, gv-!;
			v5.addVertexWithUV{{\mx, ly, 1-dd, gu2, gv-!;
			break;
		case EAST:
			v5.addVertexWithUV{{\1-dd, 		1-dd, 	dd, 		u, 		v-!;
			v5.addVertexWithUV{{\1-dd, 		1-dd, 	dd+dl, 		u+du, 	v-!;
			v5.addVertexWithUV{{\1-dd, 		dd, 	dd+dl, 		u+du, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 		dd, 	dd, 		u, 		v2-!;

			v5.addVertexWithUV{{\1-dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v-!;
			v5.addVertexWithUV{{\1-dd, 		1-dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd, 		dd, 	1-dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 		dd, 	1-dd-dl, 	u2-du, 	v2-!;

			v5.addVertexWithUV{{\1-dd, 		dd+dl, 	dd, 		u, 		v2-dv-!;
			v5.addVertexWithUV{{\1-dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv-!;
			v5.addVertexWithUV{{\1-dd, 		dd, 	1-dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 		dd, 	dd, 		u, 		v2-!;

			v5.addVertexWithUV{{\1-dd, 		1-dd, 		dd, 	u, 		v-!;
			v5.addVertexWithUV{{\1-dd, 		1-dd, 		1-dd, 	u2, 	v-!;
			v5.addVertexWithUV{{\1-dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv-!;
			v5.addVertexWithUV{{\1-dd, 		1-dd-dl, 	dd, 	u, 		v+dv-!;

			v5.addVertexWithUV{{\1-dd, ly, mx, gu2, gv-!;
			v5.addVertexWithUV{{\1-dd, ly, lx, gu, gv-!;
			v5.addVertexWithUV{{\1-dd, my, lx, gu, gv2-!;
			v5.addVertexWithUV{{\1-dd, my, mx, gu2, gv2-!;
			break;
		case WEST:
			v5.addVertexWithUV{{\dd, 		dd, 	dd, 		u, 		v2-!;
			v5.addVertexWithUV{{\dd, 		dd, 	dd+dl, 		u+du, 	v2-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	dd+dl, 		u+du, 	v-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	dd, 		u, 		v-!;

			v5.addVertexWithUV{{\dd, 		dd, 	1-dd-dl, 	u2-du, 	v2-!;
			v5.addVertexWithUV{{\dd, 		dd, 	1-dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 	1-dd-dl, 	u2-du, 	v-!;

			v5.addVertexWithUV{{\dd, 		dd, 	dd, 		u, 		v2-!;
			v5.addVertexWithUV{{\dd, 		dd, 	1-dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\dd, 		dd+dl, 	1-dd, 		u2, 	v2-dv-!;
			v5.addVertexWithUV{{\dd, 		dd+dl, 	dd, 		u, 		v2-dv-!;

			v5.addVertexWithUV{{\dd, 		1-dd-dl, 	dd, 	u, 		v+dv-!;
			v5.addVertexWithUV{{\dd, 		1-dd-dl, 	1-dd, 	u2, 	v+dv-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 		1-dd, 	u2, 	v-!;
			v5.addVertexWithUV{{\dd, 		1-dd, 		dd, 	u, 		v-!;

			v5.addVertexWithUV{{\dd, my, mx, gu2, gv2-!;
			v5.addVertexWithUV{{\dd, my, lx, gu, gv2-!;
			v5.addVertexWithUV{{\dd, ly, lx, gu, gv-!;
			v5.addVertexWithUV{{\dd, ly, mx, gu2, gv-!;
			break;
		case UP:
			v5.addVertexWithUV{{\dd, 		dd, 	dd, 		u, 		v2-!;
			v5.addVertexWithUV{{\dd+dl, 	dd, 	dd, 		u+du, 	v2-!;
			v5.addVertexWithUV{{\dd+dl, 	dd, 	1-dd, 		u+du, 	v-!;
			v5.addVertexWithUV{{\dd, 		dd, 	1-dd, 		u, 		v-!;

			v5.addVertexWithUV{{\1-dd-dl, dd, 	dd, 		u2-du, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd-dl, dd, 	1-dd, 		u2-du, 	v-!;

			v5.addVertexWithUV{{\dd, 		dd, 	dd, 		u, 		v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	dd+dl, 		u2, 	v2-dv-!;
			v5.addVertexWithUV{{\dd, 		dd, 	dd+dl, 		u, 		v2-dv-!;

			v5.addVertexWithUV{{\dd, 		dd, 	1-dd-dl, 	u, 		v+dv-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	1-dd-dl, 	u2, 	v+dv-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	1-dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\dd, 		dd, 	1-dd, 		u, 		v-!;

			v5.addVertexWithUV{{\mx, dd, my, gu2, gv2-!;
			v5.addVertexWithUV{{\lx, dd, my, gu, gv2-!;
			v5.addVertexWithUV{{\lx, dd, ly, gu, gv-!;
			v5.addVertexWithUV{{\mx, dd, ly, gu2, gv-!;
			break;
		case SOUTH:
			v5.addVertexWithUV{{\dd, 		1-dd, 	dd, 		u, 		v-!;
			v5.addVertexWithUV{{\dd+dl, 	1-dd, 	dd, 		u+du, 	v-!;
			v5.addVertexWithUV{{\dd+dl, 	dd, 	dd, 		u+du, 	v2-!;
			v5.addVertexWithUV{{\dd, 		dd, 	dd, 		u, 		v2-!;

			v5.addVertexWithUV{{\1-dd-dl, 1-dd, 	dd, 		u2-du, 	v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 	dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\1-dd-dl, dd, 	dd, 		u2-du, 	v2-!;

			v5.addVertexWithUV{{\dd, 		dd+dl, 	dd, 		u, 		v2-dv-!;
			v5.addVertexWithUV{{\1-dd, 	dd+dl, 	dd, 		u2, 	v2-dv-!;
			v5.addVertexWithUV{{\1-dd, 	dd, 	dd, 		u2, 	v2-!;
			v5.addVertexWithUV{{\dd, 		dd, 	dd, 		u, 		v2-!;

			v5.addVertexWithUV{{\dd, 		1-dd, 		dd, 		u, 		v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd, 		dd, 		u2, 	v-!;
			v5.addVertexWithUV{{\1-dd, 	1-dd-dl, 	dd, 	u2, 	v+dv-!;
			v5.addVertexWithUV{{\dd, 		1-dd-dl, 	dd, 	u, 		v+dv-!;

			v5.addVertexWithUV{{\mx, ly, dd, gu2, gv-!;
			v5.addVertexWithUV{{\lx, ly, dd, gu, gv-!;
			v5.addVertexWithUV{{\lx, my, dd, gu, gv2-!;
			v5.addVertexWithUV{{\mx, my, dd, gu2, gv2-!;
			break;
		default:
			break;
		}

		//GL11.glTranslated{{\-x, -y, -z-!;
	}

	4578ret87void faceBrightness{{\ForgeDirection dir, Tessellator v5-! {
		float f34785871;
		switch{{\dir.getOpposite{{\-!-! {
		case DOWN:
			f34785870.4F;
			break;
		case EAST:
			f34785870.5F;
			break;
		case NORTH:
			f34785870.65F;
			break;
		case SOUTH:
			f34785870.65F;
			break;
		case UP:
			f34785871F;
			break;
		case WEST:
			f34785870.5F;
			break;
		default:
			break;
		}
		v5.setColorOpaque_F{{\f, f, f-!;
	}
}
