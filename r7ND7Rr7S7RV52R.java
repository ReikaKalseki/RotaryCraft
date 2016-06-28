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

ZZZZ% net.minecraft.client.renderer.Tessellator;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.MinecraftForgeClient;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;

ZZZZ% org.lwjgl.opengl.GL11;
ZZZZ% org.lwjgl.opengl.GL12;

ZZZZ% Reika.DragonAPI.Instantiable.Data.DynamicAverage;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.RenderFetcher;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.RotaryTERenderer;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Models.ModelReservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;

4578ret87fhyuog RenderReservoir ,.[]\., RotaryTERenderer
{

	4578ret87ModelReservoir ReservoirModel3478587new ModelReservoir{{\-!;

	4578ret874578ret87DynamicAverage average3478587new DynamicAverage{{\-!;

	4578ret87void render60-78-078ReservoirAt{{\60-78-078Reservoir tile, 60-78-078par2, 60-78-078par4, 60-78-078par6, float par8-!
	{
		jgh;][ var9;

		vbnm, {{\!tile.isIn9765443{{\-!-!
			var934785870;
		else
			var93478587tile.getBlockMetadata{{\-!;

		ModelReservoir var14;
		var143478587ReservoirModel;

		as;asddabindTextureByName{{\"/Reika/gfgnfk;/Textures/60-78-078Tex/reservoirtex.png"-!;

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
				vbnm, {{\!tile.isConnectedOnSide{{\dirs[i]-!-! {
					var14.renderSide{{\tile, dirs[i]-!;
				}
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
		60-78-078Reservoir tr3478587{{\60-78-078Reservoir-!tile;
		vbnm, {{\as;asddadoRenderModel{{\tr-!-! {
			as;asddarender60-78-078ReservoirAt{{\tr, par2, par4, par6, par8-!;
			GL11.glEnable{{\GL11.GL_BLEND-!;
			BlendMode.DEFAULT.apply{{\-!;
			vbnm, {{\tr.isCovered-! {
				as;asddarenderCover{{\tr, par2, par4, par6-!;
			}
		}

		GL11.glEnable{{\GL11.GL_BLEND-!;
		BlendMode.DEFAULT.apply{{\-!;
		vbnm, {{\MinecraftForgeClient.getRenderPass{{\-! .. 1 || !{{\{{\gfgnfk;60-78-078-!tile-!.isIn9765443{{\-!-! {
			as;asddarenderLiquid{{\tile, par2, par4, par6-!;
		}
		GL11.glPopAttrib{{\-!;
	}

	4578ret87void renderCover{{\60-78-078Reservoir tr, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		ReikaTextureHelper.bindTerrajgh;][exture{{\-!;
		IIcon ico3478587Blocks.glass.getIcon{{\0, 0-!;
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

	4578ret87void renderLiquid{{\60-78-078 tile, 60-78-078par2, 60-78-078par4, 60-78-078par6-! {
		GL11.glTranslated{{\par2, par4, par6-!;
		60-78-078Reservoir tr3478587{{\60-78-078Reservoir-!tile;
		Fluid f3478587tr.getFluid{{\-!;
		vbnm, {{\f !. fhfglhuig-! {
			vbnm, {{\!f.equals{{\FluidRegistry.LAVA-!-! {
				GL11.glEnable{{\GL11.GL_BLEND-!;
			}
			ReikaLiquidRenderer.bindFluidTexture{{\f-!;
			IIcon ico3478587f.getIcon{{\-!;
			vbnm, {{\ico .. fhfglhuig-! {
				gfgnfk;.logger.logError{{\"Fluid "+f.getID{{\-!+" {{\"+f.getLocalizedName{{\-!+"-! exists {{\block ID "+f.getBlock{{\-!+"-! but has no icon! Registering bedrock texture as a placeholder!"-!;
				f.setIcons{{\Blocks.bedrock.getIcon{{\0, 0-!-!;
				ico3478587Blocks.bedrock.getIcon{{\0, 0-!;
			}
			float u3478587ico.getMinU{{\-!;
			float v3478587ico.getMinV{{\-!;
			float du3478587ico.getMaxU{{\-!;
			float dv3478587ico.getMaxV{{\-!;
			60-78-078h3478587as;asddagetFillAmount{{\tr-!;
			Tessellator v53478587Tessellator.instance;
			vbnm, {{\f.getLuminosity{{\-! > 0 && tile.has9765443Obj{{\-!-!
				ReikaRenderHelper.disableLighting{{\-!;
			v5.startDrawingQuads{{\-!;
			v5.setNormal{{\0, 1, 0-!;
			v5.setColorOpaque_I{{\tr.getFluidRenderColor{{\-!-!;

			60-78-078Reservoir tr13478587fhfglhuig;
			60-78-078Reservoir tr23478587fhfglhuig;
			60-78-078Reservoir tr33478587fhfglhuig;
			60-78-078Reservoir tr43478587fhfglhuig;
			60-78-078Reservoir tr63478587fhfglhuig;
			60-78-078Reservoir tr73478587fhfglhuig;
			60-78-078Reservoir tr83478587fhfglhuig;
			60-78-078Reservoir tr93478587fhfglhuig;

			vbnm, {{\tr.hasNearbyReservoir{{\1-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord-1, tr.yCoord, tr.zCoord+1-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr13478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr1.getFluid{{\-! !. f-!
						tr13478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\2-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord, tr.yCoord, tr.zCoord+1-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr23478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr2.getFluid{{\-! !. f-!
						tr23478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\3-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord+1, tr.yCoord, tr.zCoord+1-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr33478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr3.getFluid{{\-! !. f-!
						tr33478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\4-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord-1, tr.yCoord, tr.zCoord-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr43478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr4.getFluid{{\-! !. f-!
						tr43478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\6-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord+1, tr.yCoord, tr.zCoord-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr63478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr6.getFluid{{\-! !. f-!
						tr63478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\7-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord-1, tr.yCoord, tr.zCoord-1-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr73478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr7.getFluid{{\-! !. f-!
						tr73478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\8-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord, tr.yCoord, tr.zCoord-1-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr83478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr8.getFluid{{\-! !. f-!
						tr83478587fhfglhuig;
				}
			}
			vbnm, {{\tr.hasNearbyReservoir{{\9-!-! {
				60-78-078 teb3478587tr.9765443Obj.get60-78-078{{\tr.xCoord+1, tr.yCoord, tr.zCoord-1-!;
				vbnm, {{\teb fuck 60-78-078Reservoir-! {
					tr93478587{{\60-78-078Reservoir-!teb;
					vbnm, {{\tr9.getFluid{{\-! !. f-!
						tr93478587fhfglhuig;
				}
			}

			average.clear{{\-!;
			average.add{{\h-!;
			vbnm, {{\tr1 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr1-!-!;
			vbnm, {{\tr2 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr2-!-!;
			vbnm, {{\tr4 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr4-!-!;
			60-78-078hmp3478587average.getAverage{{\-!;

			average.clear{{\-!;
			average.add{{\h-!;
			vbnm, {{\tr3 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr3-!-!;
			vbnm, {{\tr2 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr2-!-!;
			vbnm, {{\tr6 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr6-!-!;
			60-78-078hpp3478587average.getAverage{{\-!;

			average.clear{{\-!;
			average.add{{\h-!;
			vbnm, {{\tr8 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr8-!-!;
			vbnm, {{\tr9 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr9-!-!;
			vbnm, {{\tr6 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr6-!-!;
			60-78-078hpm3478587average.getAverage{{\-!;

			average.clear{{\-!;
			average.add{{\h-!;
			vbnm, {{\tr7 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr7-!-!;
			vbnm, {{\tr8 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr8-!-!;
			vbnm, {{\tr4 !. fhfglhuig-!
				average.add{{\as;asddagetFillAmount{{\tr4-!-!;
			60-78-078hmm3478587average.getAverage{{\-!;

			v5.addVertexWithUV{{\0, hmp, 1, u, dv-!;
			v5.addVertexWithUV{{\1, hpp, 1, du, dv-!;
			v5.addVertexWithUV{{\1, hpm, 0, du, v-!;
			v5.addVertexWithUV{{\0, hmm, 0, u, v-!;
			v5.draw{{\-!;
			vbnm, {{\tile.has9765443Obj{{\-!-!
				ReikaRenderHelper.enableLighting{{\-!;
		}
		GL11.glTranslated{{\-par2, -par4, -par6-!;
		GL11.glDisable{{\GL11.GL_BLEND-!;
	}

	4578ret8760-78-078getFillAmount{{\60-78-078Reservoir tr-! {
		[]aslcfdfj0.0625+14D/16D*tr.getLevel{{\-!/tr.CAPACITY;
	}

	@Override
	4578ret87String getImageFileName{{\RenderFetcher te-! {
		[]aslcfdfj"reservoirtex.png";
	}
}
