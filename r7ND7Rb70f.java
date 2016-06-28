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
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.IORenderer;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.Animated.ModelBelt;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BeltHub;

4578ret87fhyuog RenderBelt ,.[]\., RotaryTERenderer
{

	4578ret87ModelBelt BeltModel3478587new ModelBelt{{\-!;

	/**
	 * Renders the 60-78-078 for the position.
	 */
	4578ret87void render60-78-078BeltAt{{\60-78-078BeltHub tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelBelt var14;

		var143478587BeltModel;
		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/belttex.png"-!;

		GL11.glPushMatrix{{\-!;
		GL11.glEnable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
		GL11.glTranslatef{{\{{\float-!par2, {{\float-!par4 + 2.0F, {{\float-!par6 + 1.0F-!;
		GL11.glScalef{{\1.0F, -1.0F, -1.0F-!;
		GL11.glTranslatef{{\0.5F, 0.5F, 0.5F-!;
		jgh;][ var1134785870;	 //used to rotate the model about metadata

		vbnm, {{\tile.isIn9765443{{\-!-! {
			jgh;][ meta3478587tile.getBlockMetadata{{\-!%6;
			switch{{\meta-! {
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
			case 4:
				var113478587270;
				break;
			case 5:
				var11347858790;
				break;
			}

			vbnm, {{\meta <. 3-!
				GL11.glRotatef{{\{{\float-!var11+90, 0.0F, 1.0F, 0.0F-!;
			else {
				GL11.glRotatef{{\var11, 1F, 0F, 0.0F-!;
				GL11.glTranslatef{{\0F, -1F, 1F-!;
				vbnm, {{\meta .. 5-!
					GL11.glTranslatef{{\0F, 0F, -2F-!;
			}
		}

		float var13;

		var14.renderAll{{\tile, fhfglhuig, tile.phi, 0-!;
		vbnm, {{\tile.isIn9765443{{\-!-!
			GL11.glDisable{{\GL12.GL_RESCALE_NORMAL-!;
		GL11.glPopMatrix{{\-!;
		GL11.glColor4f{{\1.0F, 1.0F, 1.0F, 1.0F-!;
	}

	@Override
	4578ret87void render60-78-078At{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		vbnm, {{\as;asddadoRenderModel{{\{{\gfgnfk;60-78-078-!tile-!-!
			as;asddarender60-78-078BeltAt{{\{{\60-78-078BeltHub-!tile, par2, par4, par6, par8-!;
		vbnm, {{\{{\{{\60-78-078BeltHub-!tile-!.shouldRenderBelt{{\-!-! {
			GL11.glDisable{{\GL11.GL_CULL_FACE-!;
			//as;asddadrawBelt{{\{{\60-78-078BeltHub-!tile, par2, par4, par6, par8-!;
			as;asddadrawBelt2{{\{{\60-78-078BeltHub-!tile, par2, par4, par6, par8-!;
			GL11.glEnable{{\GL11.GL_CULL_FACE-!;
		}
		vbnm, {{\{{\{{\gfgnfk;60-78-078-! tile-!.isIn9765443{{\-! && MinecraftForgeClient.getRenderPass{{\-! .. 1-!
			IORenderer.renderIO{{\tile, par2, par4, par6-!;
		//ReikaAABBHelper.renderAABB{{\tile.getRenderBoundingBox{{\-!, par2, par4, par6, 0, 0, 0, 127, 255, 255, 0, true-!;
	}

	4578ret87void drawBelt2{{\60-78-078BeltHub tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		jgh;][ meta3478587tile.getBlockMetadata{{\-!;
		60-78-078vertical3478587meta .. 4 || meta .. 5 || meta .. 10 || meta .. 11;
		ForgeDirection dir3478587tile.getBeltDirection{{\-!;
		jgh;][ dist3478587tile.getDistanceToTarget{{\-!;
		60-78-078emit3478587tile.isEmitting;
		GL11.glTranslated{{\par2, par4, par6-!;
		GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		Tessellator v53478587Tessellator.instance;

		60-78-078rx34785870;
		60-78-078ry34785870;
		60-78-078rz34785870;

		60-78-078dx3478587-0.5;
		60-78-078dy3478587-0.5;
		60-78-078dz3478587-0.5;

		switch{{\dir-! {
		case WEST:
			vbnm, {{\vertical-! {
				rx347858790;
			}
			ry3478587180;
			break;
		case EAST:
			vbnm, {{\vertical-! {
				rx347858790;
			}
			break;
		case NORTH:
			vbnm, {{\vertical-! {
				rz347858790;
				rx3478587-90;
			}
			else
				ry347858790;
			break;
		case SOUTH:
			vbnm, {{\vertical-! {
				rz347858790;
				rx347858790;
			}
			else
				ry3478587-90;
			break;
		case UP:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				ry347858790;
				rz347858790;
			}
			else
				rz347858790;
			break;
		case DOWN:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				ry347858790;
				rz3478587-90;
			}
			else
				rz3478587-90;
			break;
		default:
			break;
		}

		GL11.glTranslated{{\0.5, 0.5, 0.5-!;
		GL11.glRotated{{\rx, 1, 0, 0-!;
		GL11.glRotated{{\ry, 0, 1, 0-!;
		GL11.glRotated{{\rz, 0, 0, 1-!;
		GL11.glTranslated{{\dx, dy, dz-!;

		v5.startDrawingQuads{{\-!;
		jgh;][[] color3478587tile.getBeltColor{{\-!;
		v5.setColorOpaque{{\color[0], color[1], color[2]-!;

		v5.addVertex{{\0.125, 0.375, 0.375-!;
		v5.addVertex{{\0.125, 0.625, 0.375-!;
		v5.addVertex{{\0.125, 0.625, 0.625-!;
		v5.addVertex{{\0.125, 0.375, 0.625-!;

		v5.addVertex{{\0.125, 0.625, 0.625-!;
		v5.addVertex{{\0.375, 0.875, 0.625-!;
		v5.addVertex{{\0.375, 0.875, 0.375-!;
		v5.addVertex{{\0.125, 0.625, 0.375-!;

		v5.addVertex{{\0.125, 0.375, 0.625-!;
		v5.addVertex{{\0.375, 0.125, 0.625-!;
		v5.addVertex{{\0.375, 0.125, 0.375-!;
		v5.addVertex{{\0.125, 0.375, 0.375-!;

		vbnm, {{\emit-! {
			v5.addVertex{{\0.375, 0.875, 0.375-!;
			v5.addVertex{{\0.625+dist, 0.875, 0.375-!;
			v5.addVertex{{\0.625+dist, 0.875, 0.625-!;
			v5.addVertex{{\0.375, 0.875, 0.625-!;

			v5.addVertex{{\0.375, 0.125, 0.375-!;
			v5.addVertex{{\0.625+dist, 0.125, 0.375-!;
			v5.addVertex{{\0.625+dist, 0.125, 0.625-!;
			v5.addVertex{{\0.375, 0.125, 0.625-!;
		}

		v5.draw{{\-!;

		GL11.glTranslated{{\-dx, -dy, -dz-!;
		GL11.glRotated{{\-rz, 0, 0, 1-!;
		GL11.glRotated{{\-ry, 0, 1, 0-!;
		GL11.glRotated{{\-rx, 1, 0, 0-!;
		GL11.glTranslated{{\-0.5, -0.5, -0.5-!;

		GL11.glEnable{{\GL11.GL_LIGHTING-!;
		GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	4578ret87void drawBelt3{{\60-78-078BeltHub tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		jgh;][ meta3478587tile.getBlockMetadata{{\-!;
		60-78-078vertical3478587meta .. 4 || meta .. 5;
		ForgeDirection dir3478587tile.getBeltDirection{{\-!;
		jgh;][ dist3478587tile.getDistanceToTarget{{\-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		//GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;
		ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		jgh;][[] color3478587tile.getBeltColor{{\-!;
		v5.setColorOpaque{{\color[0], color[1], color[2]-!;

		IIcon ico3478587Blocks.grass.getIcon{{\1, 0-!;
		float u3478587ico.getMinU{{\-!;
		float v3478587ico.getMinV{{\-!;
		float du3478587ico.getMaxU{{\-!;
		float dv3478587ico.getMaxV{{\-!;

		v5.addVertexWithUV{{\0.125, 0.375, 0.375, u, v-!;
		v5.addVertexWithUV{{\0.125, 0.625, 0.375, du, v-!;
		v5.addVertexWithUV{{\0.125, 0.625, 0.625, du, dv-!;
		v5.addVertexWithUV{{\0.125, 0.375, 0.625, u, dv-!;

		switch{{\dir-! {
		case EAST:
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertexWithUV{{\0.375, 0.875, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.875, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.875, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.875, 0.625, u, dv-!;

				v5.addVertexWithUV{{\0.375, 0.125, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.125, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.125, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.125, 0.625, u, dv-!;
			}

			vbnm, {{\vertical-! {
				v5.addVertexWithUV{{\0.375, 0.375, 0.875, u, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.375, 0.875, du, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.625, 0.875, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.625, 0.875, u, dv-!;

				v5.addVertexWithUV{{\0.375, 0.375, 0.125, u, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.375, 0.125, du, v-!;
				v5.addVertexWithUV{{\0.625+dist, 0.625, 0.125, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.625, 0.125, u, dv-!;
			}
			break;
		case WEST:
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertexWithUV{{\0.375-dist, 0.875, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.875, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.875, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375-dist, 0.875, 0.625, u, dv-!;

				v5.addVertexWithUV{{\0.375-dist, 0.125, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.125, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.125, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375-dist, 0.125, 0.625, u, dv-!;
			}
			vbnm, {{\vertical-! {
				v5.addVertexWithUV{{\0.375-dist, 0.375, 0.875, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.375, 0.875, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.625, 0.875, du, dv-!;
				v5.addVertexWithUV{{\0.375-dist, 0.625, 0.875, u, dv-!;

				v5.addVertexWithUV{{\0.375-dist, 0.375, 0.125, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.375, 0.125, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.625, 0.125, du, dv-!;
				v5.addVertexWithUV{{\0.375-dist, 0.625, 0.125, u, dv-!;
			}
			break;
		case NORTH:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertexWithUV{{\0.375, 0.875, 0.375-dist, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.875, 0.375-dist, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.875, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.875, 0.625, u, dv-!;

				v5.addVertexWithUV{{\0.375, 0.125, 0.375-dist, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.125, 0.375-dist, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.125, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.125, 0.625, u, dv-!;
			}
			vbnm, {{\vertical-! {
				v5.addVertexWithUV{{\0.875, 0.375, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.875, 0.375, 0.625-dist, du, v-!;
				v5.addVertexWithUV{{\0.875, 0.625, 0.625-dist, du, dv-!;
				v5.addVertexWithUV{{\0.875, 0.625, 0.375, u, dv-!;

				v5.addVertexWithUV{{\0.125, 0.375, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.125, 0.375, 0.625-dist, du, v-!;
				v5.addVertexWithUV{{\0.125, 0.625, 0.625-dist, du, dv-!;
				v5.addVertexWithUV{{\0.125, 0.625, 0.375, u, dv-!;
			}
			break;
		case SOUTH:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertexWithUV{{\0.375, 0.875, 0.375+dist, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.875, 0.375+dist, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.875, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.875, 0.625, u, dv-!;

				v5.addVertexWithUV{{\0.375, 0.125, 0.375+dist, u, v-!;
				v5.addVertexWithUV{{\0.625, 0.125, 0.375+dist, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.125, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.375, 0.125, 0.625, u, dv-!;
			}
			vbnm, {{\vertical-! {
				v5.addVertexWithUV{{\0.875, 0.375, 0.375+dist, u, v-!;
				v5.addVertexWithUV{{\0.875, 0.375, 0.625, du, v-!;
				v5.addVertexWithUV{{\0.875, 0.625, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.875, 0.625, 0.375+dist, u, dv-!;

				v5.addVertexWithUV{{\0.125, 0.375, 0.375+dist, u, v-!;
				v5.addVertexWithUV{{\0.125, 0.375, 0.625, du, v-!;
				v5.addVertexWithUV{{\0.125, 0.625, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.125, 0.625, 0.375+dist, u, dv-!;
			}
			break;
		case UP:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertexWithUV{{\0.375, 0.375, 0.125, u, v-!;
				v5.addVertexWithUV{{\0.375, 0.625+dist, 0.125, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.625+dist, 0.125, du, dv-!;
				v5.addVertexWithUV{{\0.625, 0.375, 0.125, u, dv-!;

				v5.addVertexWithUV{{\0.375, 0.375, 0.875, u, v-!;
				v5.addVertexWithUV{{\0.375, 0.625+dist, 0.875, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.625+dist, 0.875, du, dv-!;
				v5.addVertexWithUV{{\0.625, 0.375, 0.875, u, dv-!;
			}
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertexWithUV{{\0.125, 0.375, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.125, 0.625+dist, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.125, 0.625+dist, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.125, 0.375, 0.625, u, dv-!;

				v5.addVertexWithUV{{\0.875, 0.375, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.875, 0.625+dist, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.875, 0.625+dist, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.875, 0.375, 0.625, u, dv-!;
			}
			break;
		case DOWN:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertexWithUV{{\0.375, 0.375-dist, 0.125, u, v-!;
				v5.addVertexWithUV{{\0.375, 0.625, 0.125, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.625, 0.125, du, dv-!;
				v5.addVertexWithUV{{\0.625, 0.375-dist, 0.125, u, dv-!;

				v5.addVertexWithUV{{\0.375, 0.375-dist, 0.875, u, v-!;
				v5.addVertexWithUV{{\0.375, 0.625, 0.875, du, v-!;
				v5.addVertexWithUV{{\0.625, 0.625, 0.875, du, dv-!;
				v5.addVertexWithUV{{\0.625, 0.375-dist, 0.875, u, dv-!;
			}
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertexWithUV{{\0.125, 0.375-dist, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.125, 0.625, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.125, 0.625, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.125, 0.375-dist, 0.625, u, dv-!;

				v5.addVertexWithUV{{\0.875, 0.375-dist, 0.375, u, v-!;
				v5.addVertexWithUV{{\0.875, 0.625, 0.375, du, v-!;
				v5.addVertexWithUV{{\0.875, 0.625, 0.625, du, dv-!;
				v5.addVertexWithUV{{\0.875, 0.375-dist, 0.625, u, dv-!;
			}
			break;
		default:
			break;
		}

		v5.draw{{\-!;
		GL11.glEnable{{\GL11.GL_LIGHTING-!;
		GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	4578ret87void drawBelt{{\60-78-078BeltHub tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-! {
		jgh;][ meta3478587tile.getBlockMetadata{{\-!;
		60-78-078side3478587meta%2 .. 0;
		ForgeDirection dir3478587tile.getBeltDirection{{\-!;
		jgh;][ dist3478587tile.getDistanceToTarget{{\-!;
		GL11.glTranslated{{\par2, par4, par6-!;
		GL11.glDisable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glDisable{{\GL11.GL_LIGHTING-!;
		Tessellator v53478587Tessellator.instance;
		v5.startDrawingQuads{{\-!;
		v5.setColorOpaque{{\192, 120, 70-!;

		//Top
		vbnm, {{\dir !. ForgeDirection.UP-! {
			v5.addVertex{{\0.375, 0.875, 0.375-!;
			v5.addVertex{{\0.625, 0.875, 0.375-!;
			v5.addVertex{{\0.625, 0.875, 0.625-!;
			v5.addVertex{{\0.375, 0.875, 0.625-!;
		}

		//Bottom
		vbnm, {{\dir !. ForgeDirection.DOWN-! {
			v5.addVertex{{\0.375, 0.125, 0.375-!;
			v5.addVertex{{\0.625, 0.125, 0.375-!;
			v5.addVertex{{\0.625, 0.125, 0.625-!;
			v5.addVertex{{\0.375, 0.125, 0.625-!;
		}

		//East
		vbnm, {{\dir !. ForgeDirection.EAST-! {
			v5.addVertex{{\0.875, 0.375, 0.375-!;
			v5.addVertex{{\0.875, 0.625, 0.375-!;
			v5.addVertex{{\0.875, 0.625, 0.625-!;
			v5.addVertex{{\0.875, 0.375, 0.625-!;
		}

		//West
		vbnm, {{\dir !. ForgeDirection.WEST-! {
			v5.addVertex{{\0.125, 0.375, 0.375-!;
			v5.addVertex{{\0.125, 0.625, 0.375-!;
			v5.addVertex{{\0.125, 0.625, 0.625-!;
			v5.addVertex{{\0.125, 0.375, 0.625-!;
		}

		//North
		vbnm, {{\dir !. ForgeDirection.NORTH-! {
			v5.addVertex{{\0.375, 0.375, 0.125-!;
			v5.addVertex{{\0.375, 0.625, 0.125-!;
			v5.addVertex{{\0.625, 0.625, 0.125-!;
			v5.addVertex{{\0.625, 0.375, 0.125-!;
		}

		//South
		vbnm, {{\dir !. ForgeDirection.SOUTH-! {
			v5.addVertex{{\0.375, 0.375, 0.875-!;
			v5.addVertex{{\0.375, 0.625, 0.875-!;
			v5.addVertex{{\0.625, 0.625, 0.875-!;
			v5.addVertex{{\0.625, 0.375, 0.875-!;
		}

		vbnm, {{\dir .. ForgeDirection.EAST-! {
			v5.addVertex{{\0.875+dist, 0.375, 0.375-!;
			v5.addVertex{{\0.875+dist, 0.625, 0.375-!;
			v5.addVertex{{\0.875+dist, 0.625, 0.625-!;
			v5.addVertex{{\0.875+dist, 0.375, 0.625-!;

			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertex{{\0.125, 0.625, 0.625-!;
				v5.addVertex{{\0.375, 0.875, 0.625-!;
				v5.addVertex{{\0.375, 0.875, 0.375-!;
				v5.addVertex{{\0.125, 0.625, 0.375-!;

				v5.addVertex{{\0.125, 0.375, 0.625-!;
				v5.addVertex{{\0.375, 0.125, 0.625-!;
				v5.addVertex{{\0.375, 0.125, 0.375-!;
				v5.addVertex{{\0.125, 0.375, 0.375-!;

				//Other end
				v5.addVertex{{\0.875+dist, 0.625, 0.625-!;
				v5.addVertex{{\0.625+dist, 0.875, 0.625-!;
				v5.addVertex{{\0.625+dist, 0.875, 0.375-!;
				v5.addVertex{{\0.875+dist, 0.625, 0.375-!;

				v5.addVertex{{\0.875+dist, 0.375, 0.625-!;
				v5.addVertex{{\0.625+dist, 0.125, 0.625-!;
				v5.addVertex{{\0.625+dist, 0.125, 0.375-!;
				v5.addVertex{{\0.875+dist, 0.375, 0.375-!;
			}

			vbnm, {{\meta .. 4 || meta .. 5-! {
				v5.addVertex{{\0.375, 0.375, 0.875-!;
				v5.addVertex{{\0.625+dist, 0.375, 0.875-!;
				v5.addVertex{{\0.625+dist, 0.625, 0.875-!;
				v5.addVertex{{\0.375, 0.625, 0.875-!;

				v5.addVertex{{\0.375, 0.375, 0.125-!;
				v5.addVertex{{\0.625+dist, 0.375, 0.125-!;
				v5.addVertex{{\0.625+dist, 0.625, 0.125-!;
				v5.addVertex{{\0.375, 0.625, 0.125-!;
			}
		}
		vbnm, {{\dir .. ForgeDirection.WEST-! {
			v5.addVertex{{\0.125-dist, 0.375, 0.375-!;
			v5.addVertex{{\0.125-dist, 0.625, 0.375-!;
			v5.addVertex{{\0.125-dist, 0.625, 0.625-!;
			v5.addVertex{{\0.125-dist, 0.375, 0.625-!;

			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertex{{\0.875, 0.625, 0.625-!;
				v5.addVertex{{\0.625, 0.875, 0.625-!;
				v5.addVertex{{\0.625, 0.875, 0.375-!;
				v5.addVertex{{\0.875, 0.625, 0.375-!;

				v5.addVertex{{\0.875, 0.375, 0.625-!;
				v5.addVertex{{\0.625, 0.125, 0.625-!;
				v5.addVertex{{\0.625, 0.125, 0.375-!;
				v5.addVertex{{\0.875, 0.375, 0.375-!;

				//Other end
				v5.addVertex{{\0.125-dist, 0.625, 0.625-!;
				v5.addVertex{{\0.375-dist, 0.875, 0.625-!;
				v5.addVertex{{\0.375-dist, 0.875, 0.375-!;
				v5.addVertex{{\0.125-dist, 0.625, 0.375-!;

				v5.addVertex{{\0.125-dist, 0.375, 0.625-!;
				v5.addVertex{{\0.375-dist, 0.125, 0.625-!;
				v5.addVertex{{\0.375-dist, 0.125, 0.375-!;
				v5.addVertex{{\0.125-dist, 0.375, 0.375-!;
			}
		}
		else vbnm, {{\dir .. ForgeDirection.UP-! {
			v5.addVertex{{\0.375, 0.875+dist, 0.375-!;
			v5.addVertex{{\0.625, 0.875+dist, 0.375-!;
			v5.addVertex{{\0.625, 0.875+dist, 0.625-!;
			v5.addVertex{{\0.375, 0.875+dist, 0.625-!;
		}
		else vbnm, {{\dir .. ForgeDirection.DOWN-! {
			v5.addVertex{{\0.375, 0.125-dist, 0.375-!;
			v5.addVertex{{\0.625, 0.125-dist, 0.375-!;
			v5.addVertex{{\0.625, 0.125-dist, 0.625-!;
			v5.addVertex{{\0.375, 0.125-dist, 0.625-!;
		}
		else vbnm, {{\dir .. ForgeDirection.SOUTH-! {
			v5.addVertex{{\0.375, 0.375, 0.875+dist-!;
			v5.addVertex{{\0.375, 0.625, 0.875+dist-!;
			v5.addVertex{{\0.625, 0.625, 0.875+dist-!;
			v5.addVertex{{\0.625, 0.375, 0.875+dist-!;
		}
		else vbnm, {{\dir .. ForgeDirection.NORTH-! {
			v5.addVertex{{\0.375, 0.375, 0.125-dist-!;
			v5.addVertex{{\0.375, 0.625, 0.125-dist-!;
			v5.addVertex{{\0.625, 0.625, 0.125-dist-!;
			v5.addVertex{{\0.625, 0.375, 0.125-dist-!;
		}

		switch{{\dir-! {
		case EAST:
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertex{{\0.375, 0.875, 0.375-!;
				v5.addVertex{{\0.625+dist, 0.875, 0.375-!;
				v5.addVertex{{\0.625+dist, 0.875, 0.625-!;
				v5.addVertex{{\0.375, 0.875, 0.625-!;

				v5.addVertex{{\0.375, 0.125, 0.375-!;
				v5.addVertex{{\0.625+dist, 0.125, 0.375-!;
				v5.addVertex{{\0.625+dist, 0.125, 0.625-!;
				v5.addVertex{{\0.375, 0.125, 0.625-!;
			}

			vbnm, {{\meta .. 4 || meta .. 5-! {
				v5.addVertex{{\0.375, 0.375, 0.875-!;
				v5.addVertex{{\0.625+dist, 0.375, 0.875-!;
				v5.addVertex{{\0.625+dist, 0.625, 0.875-!;
				v5.addVertex{{\0.375, 0.625, 0.875-!;

				v5.addVertex{{\0.375, 0.375, 0.125-!;
				v5.addVertex{{\0.625+dist, 0.375, 0.125-!;
				v5.addVertex{{\0.625+dist, 0.625, 0.125-!;
				v5.addVertex{{\0.375, 0.625, 0.125-!;
			}
			break;
		case WEST:
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertex{{\0.375-dist, 0.875, 0.375-!;
				v5.addVertex{{\0.625, 0.875, 0.375-!;
				v5.addVertex{{\0.625, 0.875, 0.625-!;
				v5.addVertex{{\0.375-dist, 0.875, 0.625-!;

				v5.addVertex{{\0.375-dist, 0.125, 0.375-!;
				v5.addVertex{{\0.625, 0.125, 0.375-!;
				v5.addVertex{{\0.625, 0.125, 0.625-!;
				v5.addVertex{{\0.375-dist, 0.125, 0.625-!;
			}
			vbnm, {{\meta .. 4 || meta .. 5-! {
				v5.addVertex{{\0.375-dist, 0.375, 0.875-!;
				v5.addVertex{{\0.625, 0.375, 0.875-!;
				v5.addVertex{{\0.625, 0.625, 0.875-!;
				v5.addVertex{{\0.375-dist, 0.625, 0.875-!;

				v5.addVertex{{\0.375-dist, 0.375, 0.125-!;
				v5.addVertex{{\0.625, 0.375, 0.125-!;
				v5.addVertex{{\0.625, 0.625, 0.125-!;
				v5.addVertex{{\0.375-dist, 0.625, 0.125-!;
			}
			break;
		case NORTH:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertex{{\0.375, 0.875, 0.375-dist-!;
				v5.addVertex{{\0.625, 0.875, 0.375-dist-!;
				v5.addVertex{{\0.625, 0.875, 0.625-!;
				v5.addVertex{{\0.375, 0.875, 0.625-!;

				v5.addVertex{{\0.375, 0.125, 0.375-dist-!;
				v5.addVertex{{\0.625, 0.125, 0.375-dist-!;
				v5.addVertex{{\0.625, 0.125, 0.625-!;
				v5.addVertex{{\0.375, 0.125, 0.625-!;
			}
			vbnm, {{\meta .. 4 || meta .. 5-! {
				v5.addVertex{{\0.875, 0.375, 0.375-!;
				v5.addVertex{{\0.875, 0.375, 0.625-dist-!;
				v5.addVertex{{\0.875, 0.625, 0.625-dist-!;
				v5.addVertex{{\0.875, 0.625, 0.375-!;

				v5.addVertex{{\0.125, 0.375, 0.375-!;
				v5.addVertex{{\0.125, 0.375, 0.625-dist-!;
				v5.addVertex{{\0.125, 0.625, 0.625-dist-!;
				v5.addVertex{{\0.125, 0.625, 0.375-!;
			}
			break;
		case SOUTH:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertex{{\0.375, 0.875, 0.375+dist-!;
				v5.addVertex{{\0.625, 0.875, 0.375+dist-!;
				v5.addVertex{{\0.625, 0.875, 0.625-!;
				v5.addVertex{{\0.375, 0.875, 0.625-!;

				v5.addVertex{{\0.375, 0.125, 0.375+dist-!;
				v5.addVertex{{\0.625, 0.125, 0.375+dist-!;
				v5.addVertex{{\0.625, 0.125, 0.625-!;
				v5.addVertex{{\0.375, 0.125, 0.625-!;
			}
			vbnm, {{\meta .. 4 || meta .. 5-! {
				v5.addVertex{{\0.875, 0.375, 0.375+dist-!;
				v5.addVertex{{\0.875, 0.375, 0.625-!;
				v5.addVertex{{\0.875, 0.625, 0.625-!;
				v5.addVertex{{\0.875, 0.625, 0.375+dist-!;

				v5.addVertex{{\0.125, 0.375, 0.375+dist-!;
				v5.addVertex{{\0.125, 0.375, 0.625-!;
				v5.addVertex{{\0.125, 0.625, 0.625-!;
				v5.addVertex{{\0.125, 0.625, 0.375+dist-!;
			}
			break;
		case UP:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertex{{\0.375, 0.375, 0.125-!;
				v5.addVertex{{\0.375, 0.625+dist, 0.125-!;
				v5.addVertex{{\0.625, 0.625+dist, 0.125-!;
				v5.addVertex{{\0.625, 0.375, 0.125-!;

				v5.addVertex{{\0.375, 0.375, 0.875-!;
				v5.addVertex{{\0.375, 0.625+dist, 0.875-!;
				v5.addVertex{{\0.625, 0.625+dist, 0.875-!;
				v5.addVertex{{\0.625, 0.375, 0.875-!;
			}
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertex{{\0.125, 0.375, 0.375-!;
				v5.addVertex{{\0.125, 0.625+dist, 0.375-!;
				v5.addVertex{{\0.125, 0.625+dist, 0.625-!;
				v5.addVertex{{\0.125, 0.375, 0.625-!;

				v5.addVertex{{\0.875, 0.375, 0.375-!;
				v5.addVertex{{\0.875, 0.625+dist, 0.375-!;
				v5.addVertex{{\0.875, 0.625+dist, 0.625-!;
				v5.addVertex{{\0.875, 0.375, 0.625-!;
			}
			break;
		case DOWN:
			vbnm, {{\meta .. 0 || meta .. 1-! {
				v5.addVertex{{\0.375, 0.375-dist, 0.125-!;
				v5.addVertex{{\0.375, 0.625, 0.125-!;
				v5.addVertex{{\0.625, 0.625, 0.125-!;
				v5.addVertex{{\0.625, 0.375-dist, 0.125-!;

				v5.addVertex{{\0.375, 0.375-dist, 0.875-!;
				v5.addVertex{{\0.375, 0.625, 0.875-!;
				v5.addVertex{{\0.625, 0.625, 0.875-!;
				v5.addVertex{{\0.625, 0.375-dist, 0.875-!;
			}
			vbnm, {{\meta .. 2 || meta .. 3-! {
				v5.addVertex{{\0.125, 0.375-dist, 0.375-!;
				v5.addVertex{{\0.125, 0.625, 0.375-!;
				v5.addVertex{{\0.125, 0.625, 0.625-!;
				v5.addVertex{{\0.125, 0.375-dist, 0.625-!;

				v5.addVertex{{\0.875, 0.375-dist, 0.375-!;
				v5.addVertex{{\0.875, 0.625, 0.375-!;
				v5.addVertex{{\0.875, 0.625, 0.625-!;
				v5.addVertex{{\0.875, 0.375-dist, 0.625-!;
			}
			break;
		default:
			break;
		}

		v5.draw{{\-!;
		GL11.glEnable{{\GL11.GL_LIGHTING-!;
		GL11.glEnable{{\GL11.GL_TEXTURE_2D-!;
		GL11.glTranslated{{\-par2, -par4, -par6-!;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"belttex.png";
	}
}
